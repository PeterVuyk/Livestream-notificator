package livestream.messaging.library;

import livestream.exception.MessageNotValidException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class CameraStateChangedEventTest {

    private static CameraStateChangedEvent event;

    @BeforeClass
    public static void setUp() throws MessageNotValidException {
        String content = "{\"methodAction\":\"event\",\"resourceId\":\"713e88c4-bfa3-4aa3-a376-eb8b85573c7b\",\"resourceIdKey\":\"CameraStateChangedEvent\",\"messageDate\":\"2019-02-24 08:53:31\",\"cameraState\":\"stopping\",\"previousCameraState\":\"running\"}";
        JSONObject message = new JSONObject(content);
        event = new CameraStateChangedEvent(message);
    }

    @Test
    public void testConstructStateChangedEvent() {
        assertThat(event, instanceOf(CameraStateChangedEvent.class));
    }

    @Test
    public void testGetCameraState() {
        assertEquals("stopping", event.getCameraState());
    }

    @Test
    public void testGetPreviousCameraState() {
        assertEquals("running", event.getPreviousCameraState());
    }

    @Test
    public void testGetResourceId() {
        assertEquals("713e88c4-bfa3-4aa3-a376-eb8b85573c7b", event.getResourceId());
    }

    @Test
    public void testGetResourceIdKey() {
        assertEquals("CameraStateChangedEvent", event.getResourceIdKey());
    }

    @Test
    public void testGetMessageAction() {
        assertEquals("CameraStateChangedEvent", event.getResourceIdKey());
    }

    @Test
    public void testGetMessageDate() {
        assertEquals("2019-02-24 08:53:31", event.getMessageDate());
    }
}
