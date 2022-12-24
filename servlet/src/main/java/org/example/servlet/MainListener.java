package org.example.servlet;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class MainListener implements ServletRequestListener {
    private static final Logger log = LoggerFactory.getLogger(MainListener.class);

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        log.info("request initialized, {}", event);
    }
}
