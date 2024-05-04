package spring.testapp.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageStorageService {
    private AmazonS3 s3client;

    @Value("${aws.access_key_id}")
    private String awsId;

    @Value("${aws.secret_access_key}")
    private String awsKey;

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucket_name}")
    private String bucketName;

    @PostConstruct
    private void initializeAmazon() {

        AWSCredentials credentials = new BasicAWSCredentials(this.awsId, this.awsKey);
        this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.fromName(region)).build();
    }


    public String fetchImageURL(String bucketName, String fileName) {
        String fileUrl = "";
        try {
            fileUrl = s3client.getUrl(bucketName, fileName).toExternalForm();
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    public List<String> listImages(String bucketName) {
        ListObjectsV2Result result = s3client.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        return objects.stream()
                .map(obj -> s3client.getUrl(bucketName, obj.getKey()).toExternalForm())
                .collect(Collectors.toList());
    }

    public String uploadFile(MultipartFile file) {
        String fileUrl = "";
        try {
            File fileObj = convertMultiPartFileToFile(file);
            String fileName = file.getOriginalFilename();
            fileUrl =  fileName;
            s3client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
            fileObj.delete();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }
}

