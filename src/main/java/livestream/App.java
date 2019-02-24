package livestream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import livestream.messaging.factory.MessageFactory;
import livestream.messaging.library.MessageInterface;
import livestream.slack.SlackNotificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class App implements RequestHandler<SNSEvent, Void> {

    @Autowired
    private SlackNotificator slackNotificator;

    @Autowired
    private MessageFactory messageFactory;

    @Override
    public Void handleRequest(SNSEvent snsEvent, Context context) {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(App.class, snsEvent.toString())) {
            App app = ctx.getBean(App.class);
            app.run(snsEvent);
        } catch (Exception e) {
            e.printStackTrace();
            context.getLogger().log("error.\n");
        }
        return null;
    }

    public void run(SNSEvent snsEvent) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        MessageInterface message = messageFactory.getMessage(snsEvent);

        slackNotificator.publish(message.getMessageAction());
    }
}
