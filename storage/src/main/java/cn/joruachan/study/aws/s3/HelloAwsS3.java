package cn.joruachan.study.aws.s3;

import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

/**
 * 文件说明<br>
 * 使用细节说明
 *
 * @author JoruaChan
 */
public class HelloAwsS3 {

    private S3Client s3Client;

    public void createBucket(String bucketName) {
        try {
            S3Waiter waiter = s3Client.waiter();

            // 请求创建bucket
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName).build();
            s3Client.createBucket(createBucketRequest);

            // 获取bucket头信息
            HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                    .bucket(bucketName).build();

            // 等待bucket创建完成后，响应获取header的请求
            WaiterResponse<HeadBucketResponse> waiterResponse = waiter.waitUntilBucketExists(headBucketRequest);
            waiterResponse.matched().response().ifPresent(System.out::println);
        } catch (S3Exception e) {
            System.out.println(e.awsErrorDetails().errorMessage());
        }
    }

}
