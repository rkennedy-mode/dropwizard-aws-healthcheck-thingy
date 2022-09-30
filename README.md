# Dropwizard AWS Health Check Thingy

A proof of concept for adding Dropwizard health checks for AWS SDK clients.

## Configuration

Configuring, for example, an S3 client would be done via an 
[AwsS3Configuration](src/main/java/com/mode/ryankennedy/aws/AwsS3Configuration.java) 
instance added to your Dropwizard Configuration object. This client 
would take a `name` and a list of strings named `testBuckets`, which 
contains a list of buckets to test as part of the health check.

## Creating a Client

Creating the client instance is accomplished via a static 
[Aws.S3](src/main/java/com/mode/ryankennedy/aws/Aws.java) method, which 
takes the above AwsS3Configuration object as well as the Dropwizard 
Environment object. This method will create the corresponding health 
check and also register the S3Client object as a managed object for 
Dropwizard to shut down when the server is shut down.

## Invoking the Health Check

The [AwsS3HealthCheck](src/main/java/com/mode/ryankennedy/aws/AwsS3HealthCheck.java) 
class will attempt to execute a [HeadBucket](https://docs.aws.amazon.com/AmazonS3/latest/API/API_HeadBucket.html) 
operation on all of the `testBuckets` listed in the configuration. If any 
of those operations fail the health check will be considered to have failed.

Given a configured S3 client with the name `stuff` and a test bucket of 
`mode.production`, the following error will be observed if there are 
no AWS credentials:

```
{
  "deadlocks": {
    "healthy": true,
    "duration": 0,
    "timestamp": "2022-09-30T14:30:09.331-07:00"
  },
  "s3-stuff": {
    "healthy": false,
    "message": "The following S3 bucket checks failed:\n    mode.production: Unable to load credentials from any of the providers in the chain AwsCredentialsProviderChain(credentialsProviders=[SystemPropertyCredentialsProvider(), EnvironmentVariableCredentialsProvider(), WebIdentityTokenCredentialsProvider(), ProfileCredentialsProvider(profileName=default, profileFile=ProfileFile(profilesAndSectionsMap=[])), ContainerCredentialsProvider(), InstanceProfileCredentialsProvider()]) : [SystemPropertyCredentialsProvider(): Unable to load credentials from system settings. Access key must be specified either via environment variable (AWS_ACCESS_KEY_ID) or system property (aws.accessKeyId)., EnvironmentVariableCredentialsProvider(): Unable to load credentials from system settings. Access key must be specified either via environment variable (AWS_ACCESS_KEY_ID) or system property (aws.accessKeyId)., WebIdentityTokenCredentialsProvider(): Either the environment variable AWS_WEB_IDENTITY_TOKEN_FILE or the javaproperty aws.webIdentityTokenFile must be set., ProfileCredentialsProvider(profileName=default, profileFile=ProfileFile(profilesAndSectionsMap=[])): Profile file contained no credentials for profile 'default': ProfileFile(profilesAndSectionsMap=[]), ContainerCredentialsProvider(): Cannot fetch credentials from container - neither AWS_CONTAINER_CREDENTIALS_FULL_URI or AWS_CONTAINER_CREDENTIALS_RELATIVE_URI environment variables are set., InstanceProfileCredentialsProvider(): Failed to load credentials from IMDS.]",
    "duration": 2008,
    "timestamp": "2022-09-30T14:30:11.339-07:00"
  }
}
```
