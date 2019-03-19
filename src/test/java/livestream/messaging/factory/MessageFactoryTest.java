package livestream.messaging.factory;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNS;
import com.amazonaws.services.lambda.runtime.events.SNSEvent.SNSRecord;
import livestream.exception.MessageNotFoundException;
import livestream.exception.MessageNotSupportedException;
import livestream.messaging.library.CameraStateChangedEvent;
import livestream.messaging.library.MessageInterface;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class MessageFactoryTest {

    private static MessageFactory factory;

    @BeforeClass
    public static void setUp() {
        factory = new MessageFactory();
    }

    @Test
    public void testGetMessageSuccess() throws MessageNotSupportedException {
        String message = "{\"methodAction\":\"event\",\"resourceId\":\"713e88c4-bfa3-4aa3-a376-eb8b85573c7b\",\"resourceIdKey\":\"CameraStateChangedEvent\",\"channel\":\"Channel-name\",\"messageDate\":\"2019-02-24 08:53:31\",\"cameraState\":\"stopping\",\"previousCameraState\":\"running\"}";
        MessageInterface event = factory.getMessage(this.createSNSEvent(message));
        assertThat(event, instanceOf(CameraStateChangedEvent.class));
    }

    @Test(expected = MessageNotFoundException.class)
    public void testGetMessageMissingResourceIdKey() throws MessageNotSupportedException {
        String message = "{\"methodAction\":\"event\",\"resourceId\":\"713e88c4-bfa3-4aa3-a376-eb8b85573c7b\",\"messageDate\":\"2019-02-24 08:53:31\",\"cameraState\":\"stopping\",\"channel\":\"Channel-name\",\"previousCameraState\":\"running\"}";
        MessageInterface event = factory.getMessage(this.createSNSEvent(message));
        assertThat(event, instanceOf(CameraStateChangedEvent.class));
    }

    @Test(expected = MessageNotFoundException.class)
    public void testGetMessageNotExistingEvent() throws MessageNotSupportedException {
        String message = "{\"methodAction\":\"event\",\"resourceId\":\"713e88c4-bfa3-4aa3-a376-eb8b85573c7b\",\"resourceIdKey\":\"NonExistingEventName\",\"channel\":\"Channel-name\",\"messageDate\":\"2019-02-24 08:53:31\",\"cameraState\":\"stopping\",\"previousCameraState\":\"running\"}";
        MessageInterface event = factory.getMessage(this.createSNSEvent(message));
        assertThat(event, instanceOf(CameraStateChangedEvent.class));
    }

    private SNSEvent createSNSEvent(String message) {
        SNS sns = new SNS();
        sns.setTopicArn("arn:aws:sns:region:123:topic");
        sns.setSubject("example subject");
        sns.setMessage(message);

        SNSRecord snsRecord = new SNSRecord();
        snsRecord.setSns(sns);

        SNSEvent snsEvent = new SNSEvent();
        snsEvent.setRecords(Collections.singletonList(snsRecord));

        return snsEvent;
    }
}
