package lab6.kochanova.zoo.keeeeeper;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.asynchttpclient.*;

import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;
import static org.asynchttpclient.Dsl.asyncHttpClient;

/*
ZooKeeper лежит в корневой папке пользователя
запускается в папке bin/zkServer.sh [stop/start/status] конфиг файл лежит в папке confs (порт 127.0.0.1:2181)
new ZooKeper(127.0.0.1:2181) -> подключается к уже запущенному на компьютере локально ZooKeeper
watcher срабатывает строго один раз, поэтому его нужно обновлять
*/

public class Main {
    private static final String zookeeperConnect = "127.0.0.1:2181";
    private static final Logger log = Logger.getLogger(Main.class.getName());
    private static final String serverPath = "/servers";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
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
        final AsyncHttpClient asyncHttpClient = asyncHttpClient();
        ActorRef storage = system.actorOf(Props.create(ActorStorageConfig.class));
        final CustomWatcher customWatcher = new CustomWatcher(zoo, storage, serverPath);
        customWatcher.createServer("localhost:" + port, host, port);

        final Anonymizator anonymizatorServer = new Anonymizator(storage, asyncHttpClient, zoo);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = anonymizatorServer.createRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> bindingCompletionStage = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(host, port),
                materializer
        );

        System.out.println("Server online at " + host + ":" + port + "/");
        System.in.read();

        asyncHttpClient.close();
        customWatcher.removeWatches();
        zoo.close();
        bindingCompletionStage.thenCompose(ServerBinding::unbind).thenApply(unbound -> system.terminate());
    }
}
