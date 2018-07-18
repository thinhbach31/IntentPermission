package com.example.admin.gallery_demo;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileLoader {
    public static List<String> loadFileName(File rootDirectory, String[] extensions) {
        List<String> fileName = new ArrayList<>();
        File[] files = rootDirectory.listFiles();

        // if files don't exist
        if (files == null) {
            Log.e("thinh", "file null");
            return null;
        }

        for (File file : files) {
            if (file.isFile()) {
                String name = file.getAbsolutePath() + "/" +fileName;
                if (name.endsWith(String.valueOf(R.string.png)) ||
                        name.endsWith(String.valueOf(R.string.PNG)) ||
                        name.endsWith(String.valueOf(R.string.jpg)))
                    fileName.add(file.getAbsolutePath());

            } else if (file.isDirectory())
                fileName.addAll(loadFileName(file, extensions));
        }
        return fileName;
    }
}
