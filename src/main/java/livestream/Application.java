package livestream;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import livestream.controller.PingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.naming.Context;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


@SpringBootApplication
// We use direct @Import instead of @ComponentScan to speed up cold starts
// @ComponentScan(basePackages = "livestream.controller")
@Import({ PingController.class })
public class Application extends SpringBootServletInitializer {

//    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
//
//    public Void handleRequest(SNSEvent input, Context context) {
//        String url = "https://hooks.slack.com/services/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";
//
//        LOGGER.log( Level.INFO, "message received");
//
//        Payload payload = Payload.builder()
//                .channel("#livestream-events")
//                .username("Livestream")
//                .iconEmoji(":smile_cat:")
//                .text("Hello World!")
//                .build();
//
//        Slack slack = Slack.getInstance();
//        WebhookResponse response = null;
//        try {
//            response = slack.send(url, payload);
//        } catch (IOException exception) {
//            LOGGER.log( Level.SEVERE, exception.toString(), exception);
//        }
//
//        System.out.println(response);
//        return null;
//    }

    /*
     * Create required HandlerMapping, to avoid several default HandlerMapping instances being created
     */
    @Bean
    public HandlerMapping handlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    /*
     * Create required HandlerAdapter, to avoid several default HandlerAdapter instances being created
     */
    @Bean
    public HandlerAdapter handlerAdapter() {
        return new RequestMappingHandlerAdapter();
    }

    public static void main(String[] args) {



        SpringApplication.run(Application.class, args);
    }
}
