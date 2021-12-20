package com.shyrski.profit.tracker.service.impl;


import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.shyrski.profit.tracker.service.S3BucketService;

@Service
public class S3BucketServiceImpl implements S3BucketService {

    @Value("${aws.key-id}")
    private String awsKeyId;
    @Value("${aws.key-secret}")
    private String awsKeySecret;
    @Value("${aws.region}")
    private String regionName;

    @Override
    public String retrieveBased64Image(String imageKey, String bucketName) {
        AWSCredentials credentials = new BasicAWSCredentials(awsKeyId, awsKeySecret);

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.fromName(regionName))
                .build();

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
}
