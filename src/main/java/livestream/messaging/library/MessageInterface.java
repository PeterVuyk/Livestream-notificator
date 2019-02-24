package livestream.messaging.library;

public interface MessageInterface {

    public static final String RESOURCE_ID_KEY_KEY = "resourceIdKey";

    static final String RESOURCE_ID_KEY = "resourceId";
    static final String MESSAGE_DATE_KEY = "messageDate";
    static final String METHOD_ACTION_KEY = "methodAction";

    public String getResourceId();

    public String getResourceIdKey();

    public String getMessageAction();

    public String getMessageDate();
}
