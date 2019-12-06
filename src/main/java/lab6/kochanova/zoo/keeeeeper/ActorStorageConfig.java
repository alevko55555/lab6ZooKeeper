package lab6.kochanova.zoo.keeeeeper;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import message.DeleteServer;
import message.GetRandomServer;
import message.PutServer;
import message.ServerList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActorStorageConfig extends AbstractActor {
    private final List<String> storage;
    private final Random random;

    public ActorStorageConfig() {
        this.storage = new ArrayList<>();
        this.random = new Random();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(PutServer.class, msg -> {
                    storage.clear();
                    storage.addAll(msg.getServers());
                })
                .match(GetRandomServer.class, msg -> {
                    getSender().tell(
                            new ServerList(storage.get(random.nextInt(storage.size()))),
                            ActorRef.noSender()
                    );
                })
                .match(DeleteServer.class, msg -> {
                    storage.remove(msg.getServer());
                })
                .build();
    }
}
