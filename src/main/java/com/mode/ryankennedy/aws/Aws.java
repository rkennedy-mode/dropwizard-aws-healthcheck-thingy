package com.mode.ryankennedy.aws;

import io.dropwizard.lifecycle.AutoCloseableManager;
import io.dropwizard.setup.Environment;
import software.amazon.awssdk.services.s3.S3Client;

public class Aws {
    public static S3Client s3(AwsS3Configuration s3, Environment environment) {
        S3Client client = S3Client.create();
        environment.healthChecks().register("s3-" + s3.getName(), new AwsS3HealthCheck(client, s3.getTestBuckets()));
        environment.lifecycle().manage(new AutoCloseableManager(client));
        return client;
    }
}
