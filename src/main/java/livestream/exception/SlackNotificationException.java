package livestream.exception;

import com.github.seratch.jslack.api.webhook.WebhookResponse;

public class SlackNotificationException extends Exception {

    public SlackNotificationException(String message, Throwable cause) {
        super(String.format("Could not publish slack message: %s", message), cause);
    }

    public SlackNotificationException(WebhookResponse response) {
        super(String.format(
                "Could not publish, response not OK Body: %s, Code: %d",
                response.getBody(),
                response.getCode()
        ));
    }
}
