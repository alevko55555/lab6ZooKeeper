package lab6.kochanova.zoo.keeeeeper;

import akka.actor.ActorRef;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class CustomWatcher implements Watcher {
    private boolean init;
    private ZooKeeper zoo;
    private ActorRef actor;

    public CustomWatcher(boolean init, ZooKeeper zoo){
        this.init = init;
        this.zoo = zoo;
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
