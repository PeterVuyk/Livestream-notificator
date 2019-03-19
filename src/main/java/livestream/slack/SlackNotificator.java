package livestream.slack;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import livestream.exception.SlackNotificationException;
import livestream.messaging.library.CameraStateChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;

@Component
public class SlackNotificator {

    private static final String CAMERA_STATE_TEMPLATE = "Channel %s: Camera state changed, previous state: %s, new state: %s";
    private static final String CAMERA_STATE_FAILURE_TEMPLATE = "<!channel>, Channel %s: Camera state Failure! Previous state: %s";

    private String webhookUrl;

    private String username;

    private String channel;

    private Slack slack;

    @Autowired
    public SlackNotificator(
            @Value("${slack.webhookUrl}") String webhookUrl,
            @Value("${slack.username}") String username,
            @Value("${slack.channel}") String channel,
            Slack slack
    ) {
        this.webhookUrl = webhookUrl;
        this.username = username;
        this.channel = channel;
        this.slack = slack;
    }

    public void publish(CameraStateChangedEvent event) throws SlackNotificationException {

        String message;
        if (event.getCameraState().equals("failure")) {
            message = String.format(
                    CAMERA_STATE_FAILURE_TEMPLATE,
                    event.getChannel(),
                    event.getPreviousCameraState()
            );
        } else {
            message = String.format(
                    CAMERA_STATE_TEMPLATE,
                    event.getChannel(),
                    event.getPreviousCameraState(),
                    event.getCameraState()
            );
        }

        Payload payload = Payload.builder()
                .channel(channel)
                .username(username)
                .text(message)
                .build();

        WebhookResponse response;
        try {
            response = slack.send(webhookUrl, payload);
        } catch (IOException exception) {
            throw new SlackNotificationException(message, exception);
        }
        if (response.getCode() != HttpURLConnection.HTTP_OK) {
            throw new SlackNotificationException(response);
        }
    }
}
