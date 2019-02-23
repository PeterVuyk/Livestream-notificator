package livestream;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;


public class App implements RequestHandler {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);
        } catch (ContainerInitializationException exception) {
            // if we fail here. We re-throw the exception to force another cold start
            exception.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", exception);
        }
    }

    @Override
    public Object handleRequest(Object input, Context context) { //SNSEvent input
        String url = "webhook url";

        LOGGER.log( Level.INFO, "message received");

        Payload payload = Payload.builder()
                .channel("#channel")
                .username("username")
                .iconEmoji(":smile_cat:")
                .text(input.toString())
                .build();

        Slack slack = Slack.getInstance();
        WebhookResponse response = null;
        try {
            response = slack.send(url, payload);
        } catch (IOException exception) {
            LOGGER.log( Level.SEVERE, exception.toString(), exception);
        }
        return context;
    }
}
