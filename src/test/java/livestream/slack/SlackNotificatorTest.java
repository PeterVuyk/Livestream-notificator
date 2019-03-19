package livestream.slack;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import livestream.exception.MessageNotValidException;
import livestream.exception.SlackNotificationException;
import livestream.messaging.library.CameraStateChangedEvent;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SlackNotificatorTest {

    private static Slack slack = mock(Slack.class);

    private static SlackNotificator slackNotificator;

    @BeforeClass
    public static void setUp() {
        slackNotificator = new SlackNotificator("url", "name", "channel", slack);
    }

    @Test
    public void testPublishSuccess() throws MessageNotValidException, SlackNotificationException, IOException {
        WebhookResponse response = mock(WebhookResponse.class);
        when(response.getCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(slack.send(anyString(), any(Payload.class))).thenReturn(response);

        slackNotificator.publish(getCameraStateChangedEvent());
    }

    @Test(expected=SlackNotificationException.class)
    public void testPublishFailed() throws MessageNotValidException, SlackNotificationException, IOException {
        WebhookResponse response = mock(WebhookResponse.class);
        when(response.getCode()).thenReturn(HttpURLConnection.HTTP_INTERNAL_ERROR);
        when(slack.send(anyString(), any(Payload.class))).thenReturn(response);

        slackNotificator.publish(getCameraStateChangedEvent());
    }

    private CameraStateChangedEvent getCameraStateChangedEvent() throws MessageNotValidException {
        String content = "{\"methodAction\":\"event\",\"resourceId\":\"713e88c4-bfa3-4aa3-a376-eb8b85573c7b\",\"resourceIdKey\":\"CameraStateChangedEvent\",\"channel\":\"ChannelName\",\"messageDate\":\"2019-02-24 08:53:31\",\"cameraState\":\"stopping\",\"previousCameraState\":\"running\"}";
        JSONObject message = new JSONObject(content);
        return new CameraStateChangedEvent(message);
    }
}
