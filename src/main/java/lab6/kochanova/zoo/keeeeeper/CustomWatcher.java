package lab6.kochanova.zoo.keeeeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class CustomWatcher implements Watcher {
    boolean init;

    public CustomWatcher(boolean init){
        this.init = init;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }

    void refreshWatcher(){
        
    }
}
