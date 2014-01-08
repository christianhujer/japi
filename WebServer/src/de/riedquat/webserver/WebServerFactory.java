package de.riedquat.webserver;

import de.riedquat.server.Server;
import de.riedquat.webserver.redirect.Redirect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;

// TODO it's not nice that for every session the "configuration" (rests, redirect) is passed around this way.
public class WebServerFactory {

    private static final String THREAD_NAME = "WebServer";
    private final Collection<Object> rests = new ArrayList<>();
    private Redirect redirect;

    private void handleSocket(final Socket socket) {
        try (final InputStream in = socket.getInputStream(); final OutputStream out = socket.getOutputStream()) {
            tryHandleSocket(in, out);
        } catch (final IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private void tryHandleSocket(final InputStream in, final OutputStream out) throws IOException {
        final HttpSession httpSession = new HttpSession(in, out);
        for (final Object rest : rests) {
            httpSession.addRest(rest);
        }
        httpSession.setRedirect(redirect);
        httpSession.requestResponseLoop();
    }

    public Server createWebServer(final ServerSocket serverSocket) {
        return new Server(THREAD_NAME, this::handleSocket, serverSocket);
    }

    public Server createWebServer(final SocketAddress socketAddress) throws IOException {
        return new Server(THREAD_NAME, this::handleSocket, socketAddress);
    }

    public Server createWebServer() throws IOException {
        return new Server(THREAD_NAME, this::handleSocket);
    }

    public Server createWebServer(final int port) throws IOException {
        return createWebServer(new ServerSocket(port));
    }

    public void addRest(final Object rest) {
        rests.add(rest);
    }

    public void setRedirect(final Redirect redirect) {
        this.redirect = redirect;
    }
}
