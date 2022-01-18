package com.shyrski.profit.tracker.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.shyrski.profit.tracker.exception.ExceptionDetails;
import com.shyrski.profit.tracker.exception.ServerException;
import com.shyrski.profit.tracker.service.S3BucketService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3BucketServiceImpl implements S3BucketService {

    private final AmazonS3 s3client;

    @Override
    public String retrieveBased64Image(String imageKey, String bucketName) {
        S3Object s3object = s3client.getObject(bucketName, imageKey);
        S3ObjectInputStream inputStream = s3object.getObjectContent();

        byte[] bytes = new byte[0];
        try {
            bytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public String uploadImage(String based64Image, String bucketName) {
        byte[] contents = Base64.getDecoder().decode(based64Image);
        InputStream stream = new ByteArrayInputStream(contents);

        String mimeType;

        try {
            mimeType = URLConnection.guessContentTypeFromStream(stream);
        } catch (IOException e) {
            throw new ServerException(ExceptionDetails.internalServerError("Cannot extract mime type from input stream", e));
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(contents.length);
        objectMetadata.setContentType(mimeType);

        String fileName = UUID.randomUUID() + getFileExtensionFromMimeType(mimeType);

        s3client.putObject(new PutObjectRequest(bucketName, fileName, stream, objectMetadata));

        URL url = s3client.getUrl(bucketName, fileName);

        return url.toString();
    }

    private String getFileExtensionFromMimeType(String mimeType) {
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        MimeType type;
        try {
            type = allTypes.forName(mimeType);
        } catch (MimeTypeException e) {
            throw new ServerException(ExceptionDetails.internalServerError("Cannot extract file extension from mime type", e));
        }
        return type.getExtension();
    }
}
