package quepierts.iter.client.event;

import quepierts.iter.observer.Event;
import quepierts.iter.observer.IEvent;

@Event.Register
public final class KeyEvent implements IEvent {
    public final int key;
    public final boolean press;

    public KeyEvent(int key, boolean press) {
        this.key = key;
        this.press = press;
    }
}
