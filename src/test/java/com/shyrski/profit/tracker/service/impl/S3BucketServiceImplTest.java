package com.shyrski.profit.tracker.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amazonaws.services.s3.AmazonS3;
import com.shyrski.profit.tracker.exception.ServerException;

@ExtendWith(MockitoExtension.class)
class S3BucketServiceImplTest {

    @InjectMocks
    private S3BucketServiceImpl s3BucketService;
    @Mock
    private AmazonS3 s3client;

    private static final String TEST_BASE64_ENCODED_IMAGE = "dsf";
    private static final String TEST_BUCKET_NAME = "testBucketName";

    @Test
    public void shouldThrowExceptionOfMimeType() {
        assertThrows(ServerException.class, () -> s3BucketService.uploadImage(TEST_BASE64_ENCODED_IMAGE, TEST_BUCKET_NAME));
    }
}