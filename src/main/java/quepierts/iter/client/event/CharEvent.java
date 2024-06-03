package quepierts.iter.client.event;

import quepierts.iter.observer.Event;
import quepierts.iter.observer.IEvent;

@Event.Register
public class CharEvent implements IEvent {
    public final char c;

    public CharEvent(char c) {
        this.c = c;
    }
}
