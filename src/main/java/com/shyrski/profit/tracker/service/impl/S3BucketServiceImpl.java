package com.shyrski.profit.tracker.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
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

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(contents.length);
        meta.setContentType("image/png");

        String fileName = UUID.randomUUID().toString();

        /*s3client.putObject(new PutObjectRequest(
                bucketName, fileName, stream, meta)
                .withCannedAcl(CannedAccessControlList.Private));*/

        return fileName;
    }
}
