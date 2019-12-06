package lab6.kochanova.zoo.keeeeeper;

import akka.actor.AbstractActor;

import java.util.List;

public class ActorStorageConfig extends AbstractActor {
    private final List<String> storage;

    public ActorStorageConfig(List<String> storage) {
        this.storage = storage;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match()
                .build();
    }
}
