package livestream.processor;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import livestream.exception.MessageNotSupportedException;
import livestream.exception.MessageNotValidException;
import livestream.exception.SlackNotificationException;
import livestream.messaging.factory.MessageFactory;
import livestream.messaging.library.CameraStateChangedEvent;
import livestream.slack.SlackNotificator;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ProcessMessagesTest {

    private static SlackNotificator slackNotificator = mock(SlackNotificator.class);

    private static MessageFactory messageFactory = mock(MessageFactory.class);

    private static ProcessMessages processMessages;

    @BeforeClass
    public static void setUp() {
        processMessages = new ProcessMessages(slackNotificator, messageFactory);
    }

    @Test
    public void testProcessMessages() throws SlackNotificationException, MessageNotSupportedException {
        when(messageFactory.getMessage(any(SNSEvent.class))).thenReturn(this.getCameraStateChangedEvent());
        processMessages.process(mock(SNSEvent.class));
        verify(slackNotificator).publish(any(CameraStateChangedEvent.class));
    }

    private CameraStateChangedEvent getCameraStateChangedEvent() throws MessageNotValidException {
        String content = "{\"methodAction\":\"event\",\"resourceId\":\"713e88c4-bfa3-4aa3-a376-eb8b85573c7b\",\"resourceIdKey\":\"CameraStateChangedEvent\",\"channel\":\"ChannelName\",\"messageDate\":\"2019-02-24 08:53:31\",\"cameraState\":\"stopping\",\"previousCameraState\":\"running\"}";
        JSONObject message = new JSONObject(content);
        return new CameraStateChangedEvent(message);
    }
}
