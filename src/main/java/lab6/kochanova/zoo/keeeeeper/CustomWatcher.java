package lab6.kochanova.zoo.keeeeeper;

import akka.actor.ActorRef;
import message.PutServer;
import org.apache.zookeeper.*;

import java.util.List;
import java.util.stream.Collectors;

public class CustomWatcher {
    private final ZooKeeper zoo;
    private final ActorRef storage;
    private final String serverPath;

    public CustomWatcher (ZooKeeper zoo, ActorRef storage, String serverPath) {
        this.zoo = zoo;
        this.storage = storage;
        this.serverPath = serverPath;
        watchChildrenCallback(null);
    }

    private void watchChildrenCallback(WatchedEvent event) {
        try {
            saveServer(
                    zoo.getChildren(serverPath, this::watchChildrenCallback)
                            .stream()
                            .map(s -> serverPath + "/" + s)
                            .collect(Collectors.toList())
            );
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveServer(List<String> nodes) {
        this.storage.tell(new PutServer(nodes), ActorRef.noSender());
    }

    public void createServer(String name, String host, int port) throws KeeperException, InterruptedException {
        String serverPathNew = zoo.create(
                serverPath + "/" + name,
                (host + ":" + port).getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL
        );
    }

    public void removeWatches() throws KeeperException, InterruptedException {
        zoo.removeAllWatches(serverPath, Watcher.WatcherType.Any, true);
    }
}
