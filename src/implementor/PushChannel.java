package src.implementor;

public class PushChannel implements Channel {
    @Override
    public String send(String topic, String message) {
        return "Push Notification Envelope [ App Title: " + topic + " | Payload: " + message + " ]";
    }
}