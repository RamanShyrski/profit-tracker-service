package com.shyrski.profit.tracker.service;

public interface S3BucketService {

    String retrieveBased64Image(String imageKey, String bucketName);

}
