package org.example.socket;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.OutputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SSLTest {
    private static final Logger log = LoggerFactory.getLogger(SSLTest.class);

    @Test
    void test() throws Exception {
        SSLContext context = SSLContext.getDefault();
        SSLSocketFactory factory = context.getSocketFactory();

        try (Socket socket = factory.createSocket("github.com", 443)) {
            log.info("connect {}", socket.getInetAddress());

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("HEAD / HTTP/1.1\r\n".getBytes());
            outputStream.write("Host: github.com\r\n".getBytes());
            outputStream.write("Accept: */*\r\n".getBytes());
            outputStream.write("\r\n".getBytes());

            byte[] bytes = new byte[2048];
            int read = socket.getInputStream().read(bytes);
            System.out.println(new String(bytes, 0, read));
        }

        assertTrue(true);
    }
}
