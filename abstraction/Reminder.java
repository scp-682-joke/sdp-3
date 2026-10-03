package abstraction;

import implementor.Channel;

public class Reminder extends Notification {
    private final String message;

    public Reminder(String id, Channel channel, String message) {
        super(id, channel);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String execute() {
        return getChannel().send("Reminder", message);
    }
}
