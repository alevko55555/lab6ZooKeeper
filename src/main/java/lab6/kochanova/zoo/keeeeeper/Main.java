package lab6.kochanova.zoo.keeeeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class Main {
    private ZooKeeper zooKeeper;

    public static void main(String[] args){

    }

    private void createZooKeeper(String host) throws IOException, KeeperException, InterruptedException {
        zooKeeper = new ZooKeeper(host, 2000, new CustomWatcher());
    }
}
