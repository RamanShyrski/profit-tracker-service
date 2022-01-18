package com.shyrski.profit.tracker.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.ServerException;

public class FileUtil {

    public static String convertImageToBase64(String imageUrl) {
        byte[] bytes = convertFileToByteArray(imageUrl);

        return Base64.getEncoder().encodeToString(bytes);
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
}
