package src.implementor;

public interface Channel {
    String send(String topic, String message);
}