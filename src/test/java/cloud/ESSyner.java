package cloud;

import java.io.IOException;

/**
 * Created by FSTMP on 2016/11/21.
 */
public class ESSyner {
/*

    YunDataService yunDataService = new YunDataService();


    public void syn() throws IOException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                  .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(InetAddress.getByName("localhost"),9300)));

        for(long i = 1; i <= yunDataService.count() / 1000; i++){
            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
            List<Map> list = yunDataService.find(i,1000);
            for(int j = 0; j < list.size(); j++){
                Map<String,Object> map = list.get(j);
                System.out.println("正在插入:"+map.get("_id") + ",资源名称:"+ map.get("share_name"));
                IndexRequestBuilder iresponse = client.prepareIndex("yun", "yun_data", map.get("_id").toString())
                        .setSource(jsonBuilder()
                                        .startObject()
                                        .field("share_id", map.get("share_id"))
                                        .field("share_name", map.get("share_name"))
                                        .field("share_time", (Date)map.get("share_time"))
                                        .field("description", map.get("description"))
                                        .field("picture", map.get("picture"))
                                        .field("username",  map.get("username"))
                                        .field("upic",  map.get("upic"))
                                        .field("uk",  map.get("uk"))
                                        .endObject()
                        );
                bulkRequestBuilder.add(iresponse);
            }
            bulkRequestBuilder.get();
        }

    }
*/

    public static void main(String[] args) throws IOException {
        //TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
      //          .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(InetAddress.getByName("localhost"),9300)));

        //new ESSyner().syn();


    }
}
