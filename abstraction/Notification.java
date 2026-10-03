package abstraction;
import implementor.Channel;
public abstract class Notification {
    private final String id;
    private Channel channel;

    public Notification(String id, Channel channel) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID cannot be null or empty.");
        }
        if (channel == null) {
            throw new IllegalArgumentException("Channel cannot be null.");
        }
        this.id = id;
        this.channel = channel;
    }

    public String getId() {
        return id;
    }
    public Channel getChannel() {
        return channel;
    }
    public void setImplementation(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("New channel cannot be null.");
        }
        this.channel = channel;
    }
    public abstract String execute();
}