package com.example.map.controller;


import com.example.map.model.*;
import com.example.map.service.FileUploadService;
import com.example.map.service.InformationService;
import com.example.map.service.UserService;
import com.example.map.utils.FileUtil;
import com.example.map.utils.QiniuCloudUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.example.map.utils.FileUtil.getMultiPartSuffix;
import static com.example.map.utils.FileUtil.getParentPath;
import static com.example.map.utils.FileUtil.getRandomFileName;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@SuppressWarnings("all")
@RestController
public class FileUploadController {

    Logger logger = Logger.getLogger(FileUploadController.class);


    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private UserService userService;

    private final static int AUDIO = 2;
    private final static int VIDEO = 3;



    /**
     * 音频的上传
     */
    @RequestMapping(value = "/uploadAudio/{pointId}", method = POST)
    public ResultModel AudioUpload(@PathVariable int pointId,
                                  @RequestParam("file") MultipartFile file,
                                   @RequestParam("second") int second,
                                   @RequestParam("minutes") int minutes,
                                  HttpServletRequest request) throws JsonProcessingException {

        // 得到userId;
        Integer userId = (Integer) request.getAttribute("id");
        ResultModel resultModel = isFileNull(file);
        if (resultModel != null) {
            return resultModel;
        }

        // 获取文件上传的路径
        String parentPath = getParentPath();
        // 得到文件名
        String fileRandomName = getRandomFileName();

        String suffix = getMultiPartSuffix(file);

        String path;
        path = "/audio/" + fileRandomName + suffix;
        try {
            File realFile = new File(parentPath + path);
            File dir = realFile.getParentFile();
            System.out.println(dir);
            System.out.println(realFile.getPath());
            if (!dir.exists()) {
                dir.mkdir();
            }
            file.transferTo(realFile);
        } catch (IOException e) {
            logger.info("文件上传出错");
            e.printStackTrace();
            return ResultBuilder.getFailure(2, "文件上传出错");
        }

        AudioMessage audioMessage = new AudioMessage();
        audioMessage.setAudioMinutes(minutes);
        audioMessage.setAudioSecond(second);
        audioMessage.setUrl(path);

        ObjectMapper objectMapper = new ObjectMapper();
        String content;
            content = objectMapper.writeValueAsString(audioMessage);
        System.out.println(content);
            return fileUploadService.addAudio(pointId, userId, content);
    }

    /**
     * 上传视频
     * @param pointId
     * @param type
     * @param file
     * @param title
     * @param request
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/uploadVideo/{pointId}", method = POST)
    public ResultModel VideoUpload(@PathVariable int pointId, @RequestParam("type") int type,
                                  @RequestParam("file") MultipartFile file,
                                  @RequestParam(required = true) String title,
                                  HttpServletRequest request) throws JsonProcessingException {

        if (title == null) {
            return ResultBuilder.getFailure(5, "无效的标题");
        }

        // 得到userId;
        Integer userId = (Integer) request.getAttribute("id");

        //判断文件是否为空
        ResultModel resultModel = isFileNull(file);
        if (resultModel != null) {
            return resultModel;
        }

        // 获取文件上传的路径
        String parentPath = getParentPath();
        // 得到文件名
        String fileRandomName = getRandomFileName();

        String suffix = getMultiPartSuffix(file);

        String path;
        path = "/video/" + fileRandomName + suffix;
        try {
            File realFile = new File(parentPath + path);
            File dir = realFile.getParentFile();
            if (!dir.exists()) {
                dir.mkdir();
            }
            file.transferTo(realFile);
        } catch (IOException e) {
            logger.info("文件上传出错");
            e.printStackTrace();
            return ResultBuilder.getFailure(2, "文件上传出错");
        }
        VideoMessage videoMessage = new VideoMessage();
        if (type == VIDEO) {
            videoMessage.setTitle(title);
        } else {
            videoMessage.setTitle(null);
        }
        videoMessage.setUrl(path);
        ObjectMapper objectMapper = new ObjectMapper();
        String content;
        content = objectMapper.writeValueAsString(videoMessage);

        return fileUploadService.addVideo(pointId,userId, content);
    }



    @PostMapping("/uploadPhotos/{pointId:\\d+}")
    public ResultModel uploadPhotos(@PathVariable int pointId, String title,
                                         MultipartHttpServletRequest request) throws IOException {
        Integer userId = (Integer) request.getAttribute("id");
        List<MultipartFile> photos = request.getFiles("file");
        if (photos.isEmpty()) {
            return ResultBuilder.getFailure(1, "文件内容为空");
        }

        for (MultipartFile file : photos) {
            ResultModel resultModel = isFileNull(file);
            if (resultModel != null) {
                return resultModel;
            }
        }
        String[] paths = new String[photos.size()];
        String parentPath = getParentPath();

        File parentFile = new File(parentPath, "/photo");
        if (!parentFile.exists()) {
            parentFile.mkdir();
        }
        int count = 0;

        for (MultipartFile file : photos) {

            String fileRandomName = getRandomFileName();
            String suffix = FileUtil.getMultiPartSuffix(file);
            paths[count] = "/photo/" + fileRandomName + suffix;
            File realfile = new File(parentFile,fileRandomName+suffix);
            try {
                file.transferTo(realfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            count++;
        }
        ImageMessage imageMessage = new ImageMessage();

        imageMessage.setTitle(title);
        imageMessage.setUrls(paths);
        return fileUploadService.addPhotos(userId, pointId, imageMessage);
    }


    @ResponseBody
    @RequestMapping(value="/qiniuIcon", method=RequestMethod.POST)
    public ResultModel uploadImg(@RequestParam MultipartFile image, HttpServletRequest request) {
        int userId = (int) request.getAttribute("id");
        String url = null;
        if (image.isEmpty()) {
            return ResultBuilder.getFailure(1,"头像为空");
        }
        try {
            byte[] bytes = image.getBytes();
            String imageName = UUID.randomUUID().toString();

            QiniuCloudUtil qiniuUtil = new QiniuCloudUtil();
            try {
                //使用base64方式上传到七牛云
                url = qiniuUtil.put64image(bytes, imageName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            return ResultBuilder.getFailure(-1,"头像上传七牛云发生异常！");
        }
        System.out.println(url);
        return informationService.addtouxiang(userId,url);
    }

//    @PostMapping("/uploadMangPhotos/{pointId:\\d+}")
//    public ResultModel uploadMangPicture(@PathVariable int pointId,
//                                         HttpServletRequest request, HttpServletResponse response) {
//        Integer userId = (Integer) request.getAttribute("id");
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//
//        List<String> paths = new ArrayList<>();
//        String parentPath = getParentPath();
//
//        if (multipartResolver.isMultipart(request)) {
//            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//            Iterator<String> iter = multiRequest.getFileNames();
//            System.out.println(multiRequest.toString());
//            while (iter.hasNext()) {
//
//                // 获得上传文件
//                MultipartFile file = multiRequest.getFile(iter.next());
//                if (file != null) {
//                    // 获出去当前上传文件的文件名
//                    String fileName = file.getOriginalFilename();
//                    System.out.println(fileName);
//                    // 如果名称不为""，说明该文件存在
//                    if (fileName.trim() != "") {
//                        String fileRandomName = getRandomFileName();
//                        paths.add("/photo/" + fileRandomName + ".jpg");
//                        try {
//                            ImageConverter.imageConverter(parentPath, fileRandomName, file.getInputStream());
//                        } catch (IOException e) {
//                            logger.error("上传文件出错");
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        } else {
//            ResultBuilder.getFailure(1, "文件内容为空");
//        }
//        if (paths.size() == 0) {
//            ResultBuilder.getFailure(1, "文件内容为空");
//        }
//        String[] pathArray = new String[paths.size()];
//        for (int i = 0; i < paths.size(); i++) {
//            pathArray[i] = paths.get(i);
//        }
//        return informationService.addMangPhotosMessage(userId, pointId, pathArray);
//    }


    private ResultModel isFileNull(MultipartFile file) {
        if (file == null) {
            return ResultBuilder.getFailure(1, "文件内容为空");
        }
        if (file.isEmpty()) {
            return ResultBuilder.getFailure(1, "文件内容为空");
        }

        return null;
    }


}