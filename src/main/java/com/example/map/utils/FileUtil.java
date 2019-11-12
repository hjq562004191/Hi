package com.example.map.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author Qiang
 */
public class FileUtil {

    private static final String WINDOWS_PATH = "E:\\Tomcat\\fileUpload";

    private static final String LINUX_PATH = "/home/upload";

    private static final String FFMPEG_WINDOWS = "F:\\下载\\ffmpeg-20180314-f706cdd-win64-static\\bin\\ffmpeg";

    public static final String PHOTO = "/photo";

    public static final String AUDIO = "/audio";

    public static final String VEDIO = "/vedio";

    /**
     * 用随机数加时间作为文件名
     */
    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);

        Random random = new Random();
        int name = (int) (random.nextDouble() * 90000 + 10000);

        return str + name;
    }

    /**
     * 判断环境获得路径
     *
     * @return
     */
    public static String getParentPath() {
        String osName = System.getProperties().getProperty("os.name");
        if (osName.contains("Windows")) {
            return WINDOWS_PATH;
        }
        if (osName.contains("Linux")) {
            return LINUX_PATH;
        }
        return LINUX_PATH;
    }

    public static String getMultiPartSuffix(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String suffix = new String(fileName.substring(fileName.lastIndexOf(".")));
        return suffix;
    }

    public static String getFfmpegPath() {
        return FFMPEG_WINDOWS;
    }

    public static File getFileByUrl(String fileUrl, String suffix) {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        BufferedOutputStream stream = null;

        InputStream inputStream = null;

        File file = null;

        try {

            URL imageUrl = new URL(fileUrl);

            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();

            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            inputStream = conn.getInputStream();

            byte[] buffer = new byte[1024];

            int len = 0;

            while ((len = inputStream.read(buffer)) != -1) {

                outStream.write(buffer, 0, len);

            }

            file = File.createTempFile("pattern", "." + suffix);

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            stream = new BufferedOutputStream(fileOutputStream);

            stream.write(outStream.toByteArray());

        } catch (Exception e) {

        } finally {

            try {

                if (inputStream != null) inputStream.close();

                if (stream != null) stream.close();

                outStream.close();

            } catch (Exception e) {
            }
        }
        return file;
    }
}
