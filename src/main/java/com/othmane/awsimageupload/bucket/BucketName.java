package com.othmane.awsimageupload.bucket;


import lombok.Getter;

@Getter
public enum BucketName {
    PROFILE_IMAGE("othmane-image-upload");
    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
