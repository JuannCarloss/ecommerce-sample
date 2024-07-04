package com.shop.ecommerce.services;

import com.amazonaws.services.s3.AmazonS3;
import com.shop.ecommerce.enterprise.ValidationException;
import com.shop.ecommerce.utils.MultipartfileConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private static final String BUCKET_NAME = "ecommerce-products-imgs";
    private final AmazonS3 s3Client;


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
