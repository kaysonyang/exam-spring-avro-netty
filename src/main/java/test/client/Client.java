package test.client;

import java.net.URL;

import org.apache.avro.Protocol;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.HttpTransceiver;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.generic.GenericRequestor;
import test.Utils;

/**
 * @author kayson Yang
 * @description
 * @create 2017-03-24 10:09
 */
public class Client {

    private Protocol protocol = null;
    private String host = null;
    private int port = 0;
    private int count = 0;

    public Client(Protocol protocol, String host, int port, int count) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.count = count;
    }

    public Object sendMessage() throws Exception {
       /* GenericRecord requestData = new GenericData.Record(protocol.getType("productId"));
        requestData.put("productId", "2017032400");
        */

        // 初始化请求数据
        GenericRecord request = new GenericData.Record(protocol.getMessages().get("getProductList").getRequest());
        request.put("productId", "2017032400");

        Transceiver t = new HttpTransceiver(new URL("http://" + host + ":" + port));
        GenericRequestor requestor = new GenericRequestor(protocol, t);

        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            Object result = requestor.request("getProductList", request);
            if (result instanceof GenericData.Record) {
                GenericData.Record record = (GenericData.Record) result;
                System.out.println(record);
            }
        }
        long end = System.currentTimeMillis();
        if (t.isConnected())
        t.close();
        System.out.println((end - start)+"ms");
        return end - start;
    }

    public Object run() {
        Object res = null;
        try {
            res = sendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        Client client =
        new Client(Utils.getProtocol(), "127.0.0.1", 9090, 5);
        client.run();

    }
}
