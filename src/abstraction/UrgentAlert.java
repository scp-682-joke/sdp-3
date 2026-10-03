package src.abstraction;

import src.implementor.Channel;

public class UrgentAlert extends Notification {
    private final String message;

    public UrgentAlert(String id, Channel channel, String message) {
        super(id, channel);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String execute() {
        String urgentMessage = "URGENT: " + message;
        return getChannel().send("Urgent Alert", urgentMessage);
    }
}