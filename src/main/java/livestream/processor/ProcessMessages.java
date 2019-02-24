package livestream.processor;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import livestream.exception.MessageNotSupportedException;
import livestream.exception.SlackNotificationException;
import livestream.messaging.factory.MessageFactory;
import livestream.messaging.library.CameraStateChangedEvent;
import livestream.messaging.library.MessageInterface;
import livestream.slack.SlackNotificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessMessages {

    private SlackNotificator slackNotificator;

    private MessageFactory messageFactory;

    @Autowired
    public ProcessMessages(SlackNotificator slackNotificator, MessageFactory messageFactory) {
        this.slackNotificator = slackNotificator;
        this.messageFactory = messageFactory;
    }

    public void process(SNSEvent snsEvent) throws MessageNotSupportedException, SlackNotificationException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        MessageInterface message = messageFactory.getMessage(snsEvent);

        if (message instanceof CameraStateChangedEvent) {
            slackNotificator.publish((CameraStateChangedEvent)message);
        }
    }
}
