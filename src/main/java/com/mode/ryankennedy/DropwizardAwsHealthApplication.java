package com.mode.ryankennedy;

import com.mode.ryankennedy.aws.Aws;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import software.amazon.awssdk.services.s3.S3Client;

public class DropwizardAwsHealthApplication extends Application<DropwizardAwsHealthConfiguration> {
    public static void main(String[] args) throws Exception {
        new DropwizardAwsHealthApplication().run(args);
    }

    @Override
    public void run(DropwizardAwsHealthConfiguration configuration, Environment environment) {
        S3Client s3 = Aws.s3(configuration.getS3(), environment);
    }
}
