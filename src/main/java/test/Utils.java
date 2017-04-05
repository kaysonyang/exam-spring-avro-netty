package test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.avro.Protocol;
/**
 * @author kayson Yang
 * @description
 * @create 2017-03-24 9:42
 */
public class Utils {

    public static Protocol getProtocol() {
        Protocol protocol = null;
        try {
            //System.out.println(Utils.class.getClassLoader().getResource(""));
            URL url = Utils.class.getClassLoader().getResource("product.avpr");
            protocol = Protocol.parse(new File(url.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return protocol;
    }
}
