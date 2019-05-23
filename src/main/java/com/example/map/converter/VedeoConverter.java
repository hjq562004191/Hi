package com.example.map.converter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.map.utils.FileUtil.getFfmpegPath;

/**
 * 将其他格式的视频转换为mp4
 * 由于转换太慢没有使用
 */
public class VedeoConverter {

    public static void videoConverter(File file) {
        String fileInputName = file.getAbsolutePath();
        String fileOutputName = fileInputName.substring(0, fileInputName.lastIndexOf(".") - 1) + ".mp4";
        new Thread() {
            @Override
            public void run() {
                List<String> vedioCov = new ArrayList<>();
                vedioCov.add(getFfmpegPath());
                vedioCov.add("-i");
                vedioCov.add(fileInputName);
                vedioCov.add("-c:v");
                vedioCov.add("libx264");
                vedioCov.add("-mbd");
                vedioCov.add("0");
                vedioCov.add("-c:a");
                vedioCov.add("aac");
                vedioCov.add("-strict");
                vedioCov.add("2");
                vedioCov.add("-pix_fmt");
                vedioCov.add("yuv420p");
                vedioCov.add("-movflags");
                vedioCov.add("faststart");
                vedioCov.add(fileOutputName);
                try {
                    Process process = new ProcessBuilder(vedioCov).redirectErrorStream(true).start();
                    new PrintStream(process.getErrorStream()).start();
                    new PrintStream(process.getInputStream()).start();
                    process.waitFor();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    file.delete();
                }
            }
        }.start();

    }

}
