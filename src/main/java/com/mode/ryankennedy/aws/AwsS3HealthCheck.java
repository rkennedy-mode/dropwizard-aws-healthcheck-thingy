package com.mode.ryankennedy.aws;

import com.codahale.metrics.health.HealthCheck;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AwsS3HealthCheck extends HealthCheck {
    private final S3Client client;
    private final String[] testBuckets;

    public AwsS3HealthCheck(S3Client client, String[] testBuckets) {
        this.client = client;
        this.testBuckets = testBuckets;
    }

    @Override
    protected Result check() {
        Map<String, Exception> errors = new HashMap<>();

        for (String testBucket : testBuckets) {
            try {
                client.headBucket(builder -> builder.bucket(testBucket));
            } catch (Exception e) {
                errors.put(testBucket, e);
            }
        }

        if (errors.isEmpty()) {
            return Result.healthy("Buckets are visible: " + String.join(", ", testBuckets));
        } else {
            return Result.unhealthy(buildErrorString(errors));
        }
    }

    private String buildErrorString(Map<String, Exception> errors) {
        return "The following S3 bucket checks failed:\n" +
                errors.entrySet().stream().map(entry -> "    " + entry.getKey() + ": " + entry.getValue().getMessage())
                        .collect(Collectors.joining("\n"));
    }
}
