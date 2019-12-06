package lab6.kochanova.zoo.keeeeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/*
ZooKeeper лежит в корневой папке пользователя
запускается в папке bin/zkServer.sh [stop/start/status] конфиг файл лежит в папке confs (порт 127.0.0.1:2181)
new ZooKeper(127.0.0.1:2181) -> подключается к уже запущенному на компьютере локально ZooKeeper
watcher срабатывает строго один раз, поэтому его нужно обновлять
*/

public class Main {
    private ZooKeeper zooKeeper;

    public static void main(String[] args){
        // creating server


    }

    private void createZooKeeper(String ZooKeeperHost, String serverHost) throws IOException, KeeperException, InterruptedException {
        zooKeeper = new ZooKeeper(ZooKeeperHost, 2000, new CustomWatcher(zoo));
        zooKeeper.create("/servers/" + serverHost, serverHost.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

    }
}
