package s3.storage;

import play.Play;
import play.PlayPlugin;
import play.exceptions.ConfigurationException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;

public class S3Plugin extends PlayPlugin {

    @Override
    public void onApplicationStart() {

        if (Play.mode == Play.Mode.DEV) {
            S3Blob.type = S3Blob.Type.DEV;
            return;
        }

        if (!Play.configuration.containsKey("aws.access.key")) {
            throw new ConfigurationException("Bad configuration for s3: no access key");
        } else if (!Play.configuration.containsKey("aws.secret.key")) {
            throw new ConfigurationException("Bad configuration for s3: no secret key");
        } else if (!Play.configuration.containsKey("s3.bucket")) {
            throw new ConfigurationException("Bad configuration for s3: no s3 bucket");
        }

        S3ProdBlob.s3Bucket = Play.configuration.getProperty("s3.bucket");
        String accessKey = Play.configuration.getProperty("aws.access.key");
        String secretKey = Play.configuration.getProperty("aws.secret.key");

        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        S3ProdBlob.s3Client = new AmazonS3Client(awsCredentials);

        if (!S3ProdBlob.s3Client.doesBucketExist(S3ProdBlob.s3Bucket)) {
            S3ProdBlob.s3Client.createBucket(S3ProdBlob.s3Bucket);
        }

        S3Blob.type = S3Blob.Type.PROD;
    }
}