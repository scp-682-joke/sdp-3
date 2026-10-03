package implementor;

public class SMSChannel implements Channel {
    @Override
    public String send(String topic, String message) {
        return "SMS [ " + topic + ": " + message + " ]";
    }
}