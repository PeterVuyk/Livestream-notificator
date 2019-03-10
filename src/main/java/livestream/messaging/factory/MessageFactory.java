package livestream.messaging.factory;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import livestream.exception.MessageNotFoundException;
import livestream.exception.MessageNotSupportedException;
import livestream.messaging.library.CameraStateChangedEvent;
import livestream.messaging.library.MessageInterface;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class MessageFactory {

    public MessageInterface getMessage(SNSEvent snsEvent) throws MessageNotSupportedException {

        SNSEvent.SNSRecord record = snsEvent.getRecords().get(0);
        String snsMessage = record.getSNS().getMessage();

        JSONObject message = new JSONObject(snsMessage);
        if (!message.has(MessageInterface.RESOURCE_ID_KEY_KEY)) {
            throw new MessageNotFoundException(snsMessage);
        }

        switch (message.get(MessageInterface.RESOURCE_ID_KEY_KEY).toString()) {
            case CameraStateChangedEvent.RESOURCE_ID:
                return new CameraStateChangedEvent(message);
            //Add later more messages if needed so factory can process them.
            default:
                throw new MessageNotFoundException(snsMessage);
        }
    }
}
