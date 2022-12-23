package org.example.socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TCPTest {
    private static final Logger log = LoggerFactory.getLogger(UDPTest.class);

    @BeforeEach
    void setUp() {
        ForkJoinPool.commonPool().submit(() -> {
            @SuppressWarnings("all")
            ServerSocket server = new ServerSocket(8080);
            log.info("listen {}", server.getLocalSocketAddress());

            while (true) {
                try (Socket socket = server.accept()) {
                    log.info("accept, {}", socket);

                    byte[] data = new byte[16];
                    int read = socket.getInputStream().read(data);
                    log.info("receive client message: {}", new String(data, 0, read));

                    log.info("send to client");
                    socket.getOutputStream().write("hello client".getBytes());

                    log.info("close server");
                }
            }
        });
    }

    @Test
    void test() throws Exception {
        try (Socket socket = new Socket("localhost", 8080)) {
            log.info("connect, {}", socket);

            log.info("send to server");
            socket.getOutputStream().write("hello server".getBytes());

            byte[] data = new byte[16];
            int read = socket.getInputStream().read(data);
            String message = new String(data, 0, read);
            log.info("receive server message: {}", message);
            assertEquals("hello client", message);

            log.info("close client");
        }
    }
}
