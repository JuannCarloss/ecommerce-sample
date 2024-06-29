package com.shop.ecommerce.services;

import com.amazonaws.services.s3.AmazonS3Client;
import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.utils.MultipartfileConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private AmazonS3Client s3Client;
    private static final String BUCKET_NAME = "ecommerce-products-imgs";

    public String uploadImg(MultipartFile multipartFile){
        String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try{
            File file = MultipartfileConverter.convertToFile(multipartFile);
            s3Client.putObject(BUCKET_NAME, filename, file);
            file.delete();
            return s3Client.getUrl(BUCKET_NAME, filename).toString();
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new ValidationException("Error while uploading image");
        }
    }
}
