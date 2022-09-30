package com.mode.ryankennedy.aws;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AwsS3Configuration {
    private final String name;
    private final String[] testBuckets;

    @JsonCreator
    public AwsS3Configuration(@JsonProperty("name") String name,
                              @JsonProperty("testBuckets") String[] testBuckets) {
        this.name = name;
        this.testBuckets = testBuckets;
    }

    public String getName() {
        return name;
    }

    public String[] getTestBuckets() {
        return testBuckets;
    }
}
