package com.example.map.converter;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 将其它格式的图片转换为jpg
 */

@Component
public class ImageConverter {

    public static boolean imageConverter(String parentPath, String fileName, InputStream in) throws IOException {
        File outputFile = new File(parentPath + "/photo" ,fileName + ".jpg");
        BufferedImage bufferedImage = ImageIO.read(in);
        ImageIO.write(bufferedImage, "jpg", outputFile);
        return false;
    }
}
