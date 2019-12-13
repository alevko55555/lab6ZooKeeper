package lab6.kochanova.zoo.keeeeeper;

import akka.actor.ActorRef;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.stream.ConnectionException;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.asynchttpclient.AsyncHttpClient;

import message.DeleteServer;
import message.GetRandomServer;
import message.ServerList;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;

public class Anonymizator {
    private final AsyncHttpClient asyncHttpClient;
    private final ActorRef storage;
    private final ZooKeeper zoo;

    public Anonymizator (ActorRef storage, AsyncHttpClient asyncHttpClient, ZooKeeper zoo) {
        this.storage = storage;
        this.asyncHttpClient = asyncHttpClient;
        this.zoo = zoo;
    }

    public Route createRoute() {
        return route(
                get(() ->
                        parameter("url", url ->
                                parameter("count", count ->
                                    getUrlCount(url, Integer.parseInt(count))
                                )))
        );
    }

    public Route getUrlCount(String url , int count) {
        CompletionStage<Response> resp;
        if(count == 0) {
            resp = asyncHttpClient.executeRequest(asyncHttpClient.prepareGet(url).build()).toCompletableFuture();
        } else {
            resp = Patterns.ask(storage, new GetRandomServer(), Duration.ofSeconds(5))
                    .thenApply(o -> ((ServerList)o).getServer())
                    .thenCompose(znode -> asyncHttpClient.executeRequest(
                            createServerRequest(getServerUrl(znode), url, count-1)
                    ).toCompletableFuture()
                                    .handle((response, throwable) ->
                                            handleBadRedirection(response, throwable, znode))
                    );
        }
        return completeOKWithFutureString(resp.thenApply(Response::getResponseBody));
    }

    public Response handleBadRedirection(Response response, Throwable throwable, String znode) {
        if(throwable instanceof ConnectionException) {
            storage.tell(new DeleteServer(znode), ActorRef.noSender());
        }
        return response;
    }

    public Request createServerRequest(String serverUrl, String url, int count) {
        return asyncHttpClient.prepareGet(serverUrl)
                .addQueryParam("url", url)
                .addQueryParam("count", Integer.toString(count))
                .build();
    }

    public String getServerUrl(String znode) {
        try {
            return new String(zoo.getData(znode, false, null));
        }
        catch(KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
