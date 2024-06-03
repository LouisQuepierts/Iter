package quepierts.iter.observer;

@FunctionalInterface
public interface ISubscribedMethod<T extends IEvent> {
    void publish(T event);
}
