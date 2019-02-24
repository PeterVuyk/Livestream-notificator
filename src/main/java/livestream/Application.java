package livestream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import livestream.exception.MessageNotSupportedException;
import livestream.processor.ProcessMessages;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application implements RequestHandler<SNSEvent, Void> {

    @Override
    public Void handleRequest(SNSEvent snsEvent, Context context) {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, snsEvent.toString())) {
            ProcessMessages processMessages = ctx.getBean(ProcessMessages.class);
            processMessages.process(snsEvent);
        } catch (MessageNotSupportedException exception) {
            context.getLogger().log(String.format("info.%n Message not supported for notification, continue"));
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            context.getLogger().log(String.format("error.%n Message: %s", exception.getMessage()));
        }
        return null;
    }
}
