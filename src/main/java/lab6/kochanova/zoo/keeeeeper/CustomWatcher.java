package lab6.kochanova.zoo.keeeeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class CustomWatcher implements Watcher {
    private boolean init;
    private ZooKeeper zoo;

    public CustomWatcher(boolean init, ZooKeeper zoo){
        this.init = init;
        this.zoo = zoo;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(init){

        }else{

        }
    }

    void refreshWatcher(){
        zoo.getChildren()
    }
}
