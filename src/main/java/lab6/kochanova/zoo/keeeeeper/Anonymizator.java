package lab6.kochanova.zoo.keeeeeper;

import akka.actor.ActorRef;
import akka.http.javadsl.server.Route;
import org.apache.zookeeper.ZooKeeper;
import org.asynchttpclient.AsyncHttpClient;

import message.DeleteServer;
import message.GetRandomServer;
import message.PutServer;
import message.ServerList;
import org.asynchttpclient.Response;

import static akka.http.javadsl.server.Directives.route;

public class Anonymizator {
    private final AsyncHttpClient asyncHttpClient;
    private final ActorRef storage;
    private final ZooKeeper zoo;

    public Anonymizator (ActorRef storage, AsyncHttpClient asyncHttpClient, ZooKeeper zoo) {
        this.storage = storage;
        this.asyncHttpClient = asyncHttpClient;
        this.zoo = zoo;
    }

    private Response 
}
