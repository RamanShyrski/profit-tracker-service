package com.shyrski.profit.tracker.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.ServerException;
import com.shyrski.profit.tracker.model.dto.FileDto;
import com.shyrski.profit.tracker.model.dto.FileType;

public class FileUtil {
    private static final String MOV_EXTENSION = ".mov";

    public static String convertImageToBase64(String imageUrl) {
        byte[] bytes = convertFileToByteArray(imageUrl);

        return Base64.getEncoder().encodeToString(bytes);
    }

    public static FileDto createFileFromUrl(String url) {
        if (url.contains(".")) {
            String extension = url.substring(url.lastIndexOf("."));
            if (MOV_EXTENSION.equals(extension)) {
                return createMov(new String(convertFileToByteArray(url), StandardCharsets.UTF_8));
            }
        }

        return createImage(convertImageToBase64(url));
    }

    private static byte[] convertFileToByteArray(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            URLConnection ucon = url.openConnection();
            InputStream is = ucon.getInputStream();
            return org.apache.commons.io.IOUtils.toByteArray(is);
        } catch (IOException e) {
            throw new ServerException(ExceptionDetails.internalServerError("Error while converting image to Base64", e));
        }
    }

    private static FileDto createImage(String content) {
        return FileDto.builder()
                .type(FileType.IMAGE)
                .content(content)
                .build();
    }

    private static FileDto createMov(String content) {
        return FileDto.builder()
                .type(FileType.MOV)
                .content(content)
                .build();
    }
}
