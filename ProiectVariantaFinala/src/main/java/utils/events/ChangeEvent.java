package utils.events;


public class ChangeEvent implements Event{

    private final ChangeEventType type;

    public ChangeEvent(ChangeEventType type) {
        this.type = type;
    }
}
