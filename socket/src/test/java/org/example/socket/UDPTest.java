package org.example.socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UDPTest {
    private static final Logger log = LoggerFactory.getLogger(UDPTest.class);

    @BeforeEach
    void setUp() {
        ForkJoinPool.commonPool().submit(() -> {
            @SuppressWarnings("all")
            DatagramSocket socket = new DatagramSocket(8080);
            while (true) {
                var buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                log.info("receive from client, {}", packet.getSocketAddress());
                log.info("receive message: {}", new String(packet.getData(), packet.getOffset(), packet.getLength()));

                var data = "Hello Client".getBytes();
                packet.setData(data);
                log.info("send to client");
                socket.send(packet);
            }
        });
    }

    @Test
    void test() throws Exception {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getLocalHost(), 8080);

            var data = "Hello Server".getBytes();
            log.info("send to server");
            socket.send(new DatagramPacket(data, data.length));

            var buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            log.info("receive from server, {}", packet.getSocketAddress());
            log.info("receive message: {}", new String(packet.getData(), packet.getOffset(), packet.getLength()));

            assertEquals("Hello Client", new String(packet.getData(), packet.getOffset(), packet.getLength()));
        }
    }
}
