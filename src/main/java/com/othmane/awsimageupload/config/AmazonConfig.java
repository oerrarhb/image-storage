package com.othmane.awsimageupload.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class AmazonConfig {

    @Bean
    public AmazonS3 s3() throws IOException {
        var reader = new FileReader("/home/oer-dw/Documents/oer_Dev_Workplace/personal-projects/img-storage/image-storage/credentials.properties");
        var properties = new Properties();
        properties.load(reader);
        AWSCredentials awsCredentials = new BasicAWSCredentials(properties.getProperty("accessKey"), properties.getProperty("secretKey"));
        return AmazonS3ClientBuilder.standard()
                .withRegion("eu-west-3")
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
