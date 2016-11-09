import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by FSTMP on 2016/11/4.
 */
public class TestES {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch-app").build();
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(""), 9300));

        /*IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder()
                                .startObject()
                                .field("user", "kimchy")
                                .field("postDate", new Date())
                                .field("message", "trying out Elasticsearch")
                                .endObject()
                )
                .get();

        String _index = response.getIndex();
        String _type = response.getType();
        String _id = response.getId();
        long _version = response.getVersion();*/

        GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
        System.out.println(response.getSource().get("user"));

        client.close();
    }

}
