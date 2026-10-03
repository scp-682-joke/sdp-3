package src.implementor;

public class EmailChannel implements Channel {
    @Override
    public String send(String topic, String message) {
        return "Email Envelope [Subject: " + topic + " | Body: " + message + "]";
    }
}