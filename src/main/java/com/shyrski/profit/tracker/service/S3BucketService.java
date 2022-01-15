package com.shyrski.profit.tracker.service;

public interface S3BucketService {

    String retrieveBased64Image(String imageKey, String bucketName);

    String uploadImage(String based64Image, String bucketName);

    String uploadFile(byte[] content, String bucketName);

}
