package ${groupId}.reactive.transport;

public class ProcessorEvent<T> {
    private ProcessorEventType eventType;
    private T content;

    public ProcessorEventType getEventType() {
        return eventType;
    }

    public void setEventType(ProcessorEventType eventType) {
        this.eventType = eventType;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
