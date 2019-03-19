package livestream.messaging.library;

import livestream.exception.MessageNotValidException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CameraStateChangedEvent implements MessageInterface {

    public static final String RESOURCE_ID = "CameraStateChangedEvent";

    private static final String CAMERA_STATE_KEY = "cameraState";
    private static final String PREVIOUS_CAMERA_STATE_KEY = "previousCameraState";
    private static final String CHANNEL = "channel";

    private String cameraState;

    private String previousCameraState;

    private String resourceId;

    private String resourceIdKey;

    private String messageDate;

    private String methodAction;

    private String channel;

    public CameraStateChangedEvent(JSONObject message) throws MessageNotValidException {
        this.validate(message);
        this.cameraState = message.get(CAMERA_STATE_KEY).toString();
        this.previousCameraState = message.get(PREVIOUS_CAMERA_STATE_KEY).toString();
        this.resourceId = message.get(RESOURCE_ID_KEY).toString();
        this.resourceIdKey = message.get(RESOURCE_ID_KEY_KEY).toString();
        this.messageDate = message.get(MESSAGE_DATE_KEY).toString();
        this.methodAction = message.get(METHOD_ACTION_KEY).toString();
        this.channel = message.get(CHANNEL).toString();
    }

    public String getCameraState() {
        return cameraState;
    }

    public String getPreviousCameraState() {
        return previousCameraState;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getResourceIdKey() {
        return resourceIdKey;
    }

    public String getMessageAction() {
        return methodAction;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public String getChannel() {
        return channel;
    }

    private void validate(JSONObject message) throws MessageNotValidException {
        String[] requiredFields = {
                CAMERA_STATE_KEY,
                PREVIOUS_CAMERA_STATE_KEY,
                RESOURCE_ID_KEY,
                RESOURCE_ID_KEY_KEY,
                MESSAGE_DATE_KEY,
                METHOD_ACTION_KEY,
                CHANNEL,
        };

        List<String> missingFields = new ArrayList<String>();
        for (String requiredField : requiredFields) {
            if (!message.has(requiredField)) {
                missingFields.add(requiredField);
            }
        }

        if (!missingFields.isEmpty()) {
            throw new MessageNotValidException(message, missingFields);

        }
    }
}
