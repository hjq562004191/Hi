package com.example.map.converter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.map.utils.FileUtil.getFfmpegPath;

/**
 * 将其它格式的音频转换为mp3格式的
 * 由于转换太慢没有使用
 */
public class AudioConverter {

    public static void audioConverter(File file) {
        String fileInputName = file.getAbsolutePath();
        String fileOutputName = fileInputName.substring(0, fileInputName.lastIndexOf(".") - 1) + ".mp3";
        new Thread() {
            @Override
            public void run() {
                List<String> audioCov = new ArrayList<>();
                audioCov.add(getFfmpegPath());
                audioCov.add("-i");
                audioCov.add(fileInputName);
                audioCov.add("-vn");
                audioCov.add("-c:a");
                audioCov.add("libmp3lame");
                audioCov.add("-aq");
                audioCov.add("4");
                audioCov.add("-ac");
                audioCov.add("2");
                audioCov.add(fileOutputName);
                try {
                    Process process = new ProcessBuilder(audioCov).redirectErrorStream(true).start();
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

class PrintStream extends Thread {
    InputStream is = null;
    public PrintStream(InputStream is) {
        this.is = is;
    }

    @Override
    public void run() {
        try {
            while (this != null) {
                int ch = is.read();
                if (ch != -1) {
                    System.out.print((char)ch);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

