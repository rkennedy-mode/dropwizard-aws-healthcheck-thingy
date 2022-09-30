package com.mode.ryankennedy;

import com.mode.ryankennedy.aws.AwsS3Configuration;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DropwizardAwsHealthConfiguration extends Configuration {
    @Valid
    @NotNull
    private AwsS3Configuration s3;

    public AwsS3Configuration getS3() {
        return s3;
    }
}
