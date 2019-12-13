package lab6.kochanova.zoo.keeeeeper;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.stream.ActorMaterializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.asynchttpclient.*;

import java.io.IOException;
import java.util.logging.Logger;

/*
ZooKeeper лежит в корневой папке пользователя
запускается в папке bin/zkServer.sh [stop/start/status] конфиг файл лежит в папке confs (порт 127.0.0.1:2181)
new ZooKeper(127.0.0.1:2181) -> подключается к уже запущенному на компьютере локально ZooKeeper
watcher срабатывает строго один раз, поэтому его нужно обновлять
*/

public class Main {
    private static final String zookeeperConnect = "127.0.0.1:2181";
    private static final Logger log = Logger.getLogger(Main.class.getName());
    //private static final String

    public static void main(String[] args) throws IOException {
        // creating server
        if (args.length != 2) {
            System.err.println("Usage: Anonymizer <host><port>");
            System.exit(-1);
        }
        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        final ZooKeeper zoo = new ZooKeeper(zookeeperConnect, 2000, e -> log.info(e.toString()));
        final ActorSystem system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        //final AsyncHttpClient asyncHttpClient = asyncHttpClient();
        ActorRef storage = system.actorOf(ActorStorageConfig.)

    }

    private void createZooKeeper(String ZooKeeperHost, String serverHost) throws IOException, KeeperException, InterruptedException {
        zooKeeper = new ZooKeeper(ZooKeeperHost, 2000, new CustomWatcher(zooKeeper));
        zooKeeper.create("/servers/" + serverHost, serverHost.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

    }
}
