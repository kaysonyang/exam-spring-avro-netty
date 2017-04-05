package test.server;

import org.apache.avro.Protocol;
import org.apache.avro.Protocol.Message;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.HttpServer;
import org.apache.avro.ipc.generic.GenericResponder;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author kayson Yang
 * @description
 * @create 2017-03-24 9:45
 */
public class Server extends GenericResponder{

    private final Logger logger = LoggerFactory.getLogger(Server.class);
    private Protocol protocol = null;
    private int port;

    public Server(Protocol protocol, int port) {
        super(protocol);
        this.protocol = protocol;
        this.port = port;
    }

    @Override
    public Object respond(Message message, Object request) throws Exception {
        GenericRecord req = (GenericRecord) request;
        GenericRecord reMessage = null;

        if (message.getName().equals("getProductList")) {

            Object msg = req.get("productId");
            if(msg instanceof GenericRecord){
                GenericRecord msg1 = (GenericRecord)msg;
                logger.info("接收到数据：{}",msg1);
            }else {
                logger.info("接收到数据：{}",msg);
            }


            //取得返回值的类型


            reMessage =  new GenericData.Record(protocol.getType("ProductList"));
            //直接构造回复
            List list =new ArrayList();
           for(int i=0;i<=8;i++){
               GenericRecord sub =  new GenericData.Record(protocol.getType("Product"));
               sub.put("productId","20170324"+ RandomUtils.nextInt(100));
               sub.put("productName","物料"+i);
               sub.put("productSerial", RandomUtils.nextInt(100000));
               sub.put("status", RandomUtils.nextInt(10));
               sub.put("logic", RandomUtils.nextInt(10)+10l);
               sub.put("isStock",i%2==0?true:false);
               /*list.add(sub);*/
               //reMessage.put(i,sub);
               if (measurePerf(Server::sequentialSum,(long)sub.get("logic"))){
                   list.add(sub);
               }
           }
            GenericRecord sub1 =  new GenericData.Record(protocol.getType("Product"));
            sub1.put("productId","2017032400");
            sub1.put("productName","物料10");
            sub1.put("productSerial",RandomUtils.nextInt(100000));
            sub1.put("status", RandomUtils.nextInt(10));
            sub1.put("isStock",true);
            sub1.put("logic", RandomUtils.nextInt(10)+10l);
            //reMessage.put(10,sub1);

            if (measurePerf(Server::sequentialSum,(long)sub1.get("logic"))){
                list.add(sub1);
            }
            reMessage.put(0,list);



        }
        return reMessage;
    }

    public void run() {
        try {
            HttpServer server = new HttpServer(this, port);
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server(Utils.getProtocol(), 9090).run();
    }

    public static <T, R> boolean measurePerf(Function<T, R> f, T input) {

        Predicate<Long> atLeast = x -> (x * x)>1000 ;
        R result = f.apply(input);
        return atLeast.test((Long)result);
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n).reduce(Long::sum).get();
    }

}
