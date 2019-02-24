package livestream.exception;

import org.json.JSONObject;

import java.util.List;

public class MessageNotValidException extends MessageNotSupportedException {

    public MessageNotValidException(JSONObject message, List missingFields) {
        super(String.format(
                "Message not valid, missing fields, message: %s, missingFields: %s",
                message.toString(),
                missingFields.toString())
        );
    }
}
