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
        byte[] bytes;
        try {
            URL url = new URL(imageUrl);
            URLConnection ucon = url.openConnection();
            InputStream is = ucon.getInputStream();
            bytes = org.apache.commons.io.IOUtils.toByteArray(is);
        } catch (IOException e) {
            throw new ServerException(ExceptionDetails.internalServerError("Error while converting image to Base64", e));
        }

        return Base64.getEncoder().encodeToString(bytes);
    }


}
