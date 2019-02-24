package livestream.slack;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import livestream.exception.SlackNotificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SlackNotificator {

    private static final Logger LOGGER = Logger.getLogger(SlackNotificator.class.getName());

    @Value("${slack.webhookUrl}")
    private String webhookUrl;

    @Value("${slack.username}")
    private String username;

    @Value("${slack.channel}")
    private String channel;

    public void publish(String message) throws SlackNotificationException {

        Payload payload = Payload.builder()
                .channel(channel)
                .username(username)
                .text(message)
                .build();
        Slack slack = Slack.getInstance();

        WebhookResponse response;
        try {
            response = slack.send(webhookUrl, payload);
        } catch (IOException exception) {
            LOGGER.log( Level.SEVERE, exception.toString(), exception);
            throw new SlackNotificationException(message, exception);
        }
        if (response.getCode() != HttpURLConnection.HTTP_OK) {
            throw new SlackNotificationException(response);
        }
    }
}
