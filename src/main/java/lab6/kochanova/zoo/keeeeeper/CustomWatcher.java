package lab6.kochanova.zoo.keeeeeper;

import akka.actor.ActorRef;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class CustomWatcher implements Watcher {
    private ZooKeeper zoo;
    private ActorRef storage;
    private final String serverPath;





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
