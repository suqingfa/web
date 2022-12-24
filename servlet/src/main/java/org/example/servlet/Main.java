package org.example.servlet;

import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.EnumSet;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addEventListener(new MainListener());
        context.addServlet(MainServlet.class, "/*");
        context.addFilter(MainFilter.class, "/*", EnumSet.allOf(DispatcherType.class));

        server.start();
    }
}
