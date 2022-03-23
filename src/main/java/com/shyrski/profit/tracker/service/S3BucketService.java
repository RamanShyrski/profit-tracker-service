package com.shyrski.profit.tracker.service;

public interface S3BucketService {

    String uploadImage(String based64Image, String bucketName);

}
