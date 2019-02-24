# WIP: Livestream Notificator

Receive camera status updates via Slack when the camera state changes. The notificator is on the [Livestream-server](https://github.com/PeterVuyk/Livestream-server).

### Still a WIP, what needs to be done:

- Started as POC so write tests.
- Add steps how to do local development.
- Mayby later, add mail functionality.
- add a dead letter queue.

![alt text](https://github.com/PeterVuyk/Livestream-notificator/blob/master/src/main/resources/slack.png)

## Getting Started

This project uses SAM, an open-source framework to build serverless applications (Lambda) on AWS. With help of a template it is easy for local development and easy to deploy once you've made your adjustments. No Docker or whatsoever is needed it's already provided by SAM. Git clone the project and follow the steps below.

### Configuration

Update the parameters in the file `application.properties` and add the correct SNS topic in the `template.yaml`. 

Before you can start with (local)development and deployment, it is important to setup your credentials with AWS correctly. SAM needs administrator permissions. For more information read: [Quick Start](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-quick-start.html) 

### Local development

TODO: Add the required steps for local development.

[SAM Local invoke](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/sam-cli-command-reference-sam-local-invoke.html)

### Deployment

Step 1: Create the package:

    mvn clean package

Step 2: Create a `packaged.yaml` based on the `template.yaml` and send the project as a zip file to the S3 bucket:

    sam package --output-template-file packaged.yaml --s3-bucket <put-here-your-bucket-name>

Step 3: Run the command below to:

- Tell what region you would like to deploy the project
- Manage automatically the IAM Role
- Give the project a stack name
- Deploy the project based on the `packaged.yaml` file created in step 2.


    sam deploy --template-file packaged.yaml --stack-name livestream-notificator --capabilities CAPABILITY_IAM --region eu-central-1

Once you've run the command, Cloudformation creates the required IAM Role and Lambda function, and will configure the Lambda function to listen to the SNS topic configured in the `template.yaml`. 
