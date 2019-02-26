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

    private static final String CAMERA_STATE_TEMPLATE = "Camera state changed, previous state: %s, new state: %s";
    private static final String CAMERA_STATE_FAILURE_TEMPLATE = "<!channel>, Camera state Failure! Previous state: %s";

    @Value("${slack.webhookUrl}")
    private String webhookUrl;

    @Value("${slack.username}")
    private String username;

    @Value("${slack.channel}")
    private String channel;

    private Slack slack;

    @Autowired
    public SlackNotificator(Slack slack) {
        this.slack = slack;
    }

    public void publish(CameraStateChangedEvent event) throws SlackNotificationException {

        String message;
        if (event.getCameraState().equals("failure")) {
            message = String.format(CAMERA_STATE_FAILURE_TEMPLATE, event.getPreviousCameraState());
        } else {
            message = String.format(CAMERA_STATE_TEMPLATE, event.getPreviousCameraState(), event.getCameraState());
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
