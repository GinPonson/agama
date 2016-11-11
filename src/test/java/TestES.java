import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 * Created by FSTMP on 2016/11/4.
 */
public class TestES {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder()
                .put("cluster.name", "elasticsearch-app").build();
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(""), 9300));

        /*IndexResponse iresponse = client.prepareIndex("twitter", "tweet", "3")
                .setSource(jsonBuilder()
                                .startObject()
                                .field("user", "panyongjian")
                                .field("postDate", new Date())
                                .field("message", "trying Elasticsearch out")
                                .endObject()
                )
                .get();*/

        GetResponse response = client.prepareGet("twitter", "tweet", "1").get();
        //System.out.println(response.getSource().get("user"));


        SearchResponse sr = client.prepareSearch().setQuery(matchAllQuery()).get();
       // sr.getHits().forEach(hits -> System.out.println(hits.getSourceAsString()));

        SearchResponse sr1 = client.prepareSearch().setQuery(multiMatchQuery("trying out","user","message")).get();
        sr1.getHits().forEach(hits -> System.out.println(hits.getSourceAsString()));

        client.close();

    }

}
