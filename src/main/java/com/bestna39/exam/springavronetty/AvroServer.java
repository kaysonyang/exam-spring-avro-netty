package com.bestna39.exam.springavronetty;

import com.bestna39.exam.springavronetty.protocol.Calculator;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.InetSocketAddress;

@Component
public class AvroServer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int CALC_SERVER_PORT = 8888;

    private static Server calcServer;

    @Autowired
    private Calculator calculator;

    @PostConstruct
    public void startServer() {

        calcServer = new NettyServer(new SpecificResponder(Calculator.class, calculator),
                new InetSocketAddress(CALC_SERVER_PORT));

        logger.info("Avro Netty Server start.");
    }

    @PreDestroy
    public void stopServer() {
        calcServer.close();
        logger.info("Avro Netty Server stop.");
    }
}
