package livestream.exception;

public class MessageNotFoundException extends MessageNotSupportedException {

    public MessageNotFoundException(String message) {
        super(String.format("Could not find message in library, message: %s", message));
    }
}
