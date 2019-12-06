package lab6.kochanova.zoo.keeeeeper;

import akka.actor.ActorRef;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.ZooKeeper;

public class CustomWatcher {
    private final ZooKeeper zoo;
    private final ActorRef storage;
    private final String serverPath;

    public CustomWatcher (ZooKeeper zoo, ActorRef storage, String serverPath) {
        this.zoo = zoo;
        this.storage = storage;
        this.serverPath = serverPath;
    }

    public CustomWatcher(){

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        // process
    }

    void sendDataToActor(){
       // sending data
    }
}
