package com.bestna39.exam.springavronetty;

import com.bestna39.exam.springavronetty.protocol.Calculator;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.net.InetSocketAddress;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)*/
public class AvroTest {

    @Test
    public void testClient() throws IOException {

        NettyTransceiver calcClient = new NettyTransceiver(new InetSocketAddress(8888));

        Calculator proxy = SpecificRequestor.getClient(Calculator.class, calcClient);


        System.out.println(proxy.add(2, 3));
        System.out.println(proxy.subtract(5, 1));
        assertThat(proxy.add(2, 3), is(5.0));
        assertThat(proxy.subtract(5, 1), is(4.0));

        calcClient.close();
    }
}
