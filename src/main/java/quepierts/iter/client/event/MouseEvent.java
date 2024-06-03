package quepierts.iter.client.event;

import quepierts.iter.observer.Event;
import quepierts.iter.observer.IEvent;

public abstract class MouseEvent implements IEvent {
    public final double posX;
    public final double posY;

    protected MouseEvent(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @Event.Register
    public static class Update extends MouseEvent {
        public final double dy;
        public final double dx;

        public Update(double posX, double posY, double dy, double dx) {
            super(posX, posY);
            this.dy = dy;
            this.dx = dx;
        }
    }

    @Event.Register
    public static class Click extends MouseEvent {
        public final int button;

        public Click(double posX, double posY, int button) {
            super(posX, posY);
            this.button = button;
        }
    }

    @Event.Register
    public static class Release extends MouseEvent {
        public final int button;

        public Release(double posX, double posY, int button) {
            super(posX, posY);
            this.button = button;
        }
    }

    @Event.Register
    public static class Scroll extends MouseEvent {
        public final double scroll;

        public Scroll(double posX, double posY, double scroll) {
            super(posX, posY);
            this.scroll = scroll;
        }
    }
}
