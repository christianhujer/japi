package de.riedquat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static de.riedquat.server.SocketUtils.createBoundEphemeralServerSocket;
import static de.riedquat.server.SocketUtils.createBoundServerSocket;

public final class Server implements AutoCloseable {

    private static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    private final String threadName;
    private final ServerSocket serverSocket;
    private final SocketHandler socketHandler;
    private final ExecutorService threadPoolExecutor = Executors.newCachedThreadPool();
    private final Thread serverThread;
    private final Logger logger;

    private int soTimeout = DEFAULT_SOCKET_TIMEOUT;

    public Server(final String threadName, final SocketHandler socketHandler, final ServerSocket serverSocket) {
        this.threadName = threadName;
        this.socketHandler = socketHandler;
        this.serverSocket = serverSocket;
        logger = Logger.getLogger(String.format("%s(%s)", getClass().getName(), threadName));
        this.serverThread = start();
    }

    public Server(final String threadName, final SocketHandler socketHandler, final SocketAddress socketAddress) throws IOException {
        this(threadName, socketHandler, createBoundServerSocket(socketAddress));
    }

    public Server(final String threadName, final SocketHandler socketHandler) throws IOException {
        this(threadName, socketHandler, createBoundEphemeralServerSocket());
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
        waitForAllThreadsToFinish();
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    private Thread start() {
        final Thread thread = new Thread(this::acceptIncomingConnections);
        thread.setName(threadName);
        thread.start();
        return thread;
    }

    private void acceptIncomingConnections() {
        try {
            tryAcceptIncomingConnections();
        } catch (final IOException e) {
            if (!serverSocket.isClosed()) {
                logger.log(Level.SEVERE, "Unexpected server death.", e);
            }
        } finally {
            threadPoolExecutor.shutdown();
        }
    }

    private void tryAcceptIncomingConnections() throws IOException {
        while (!serverSocket.isClosed()) {
            acceptIncomingConnection();
        }
    }

    private void acceptIncomingConnection() throws IOException {
        final Socket socket = serverSocket.accept();
        socket.setSoTimeout(soTimeout);
        threadPoolExecutor.execute(() -> clientJob(socket));
    }

    private void clientJob(final Socket socket) {
        setClientThreadName(socket);
        handleSocket(socket);
        assert socket.isClosed();
    }

    private void handleSocket(final Socket socket) {
        //noinspection UnnecessaryLocalVariable
        try (final Socket socketToClose = socket) {
            socketHandler.handleSocket(socketToClose);
        } catch (final IOException e) {
            logger.log(Level.WARNING, "Unexpected exception from handleSocket().", e);
        }
    }

    private void setClientThreadName(final Socket socket) {
        Thread.currentThread().setName(getClientThreadName(socket));
    }

    private String getClientThreadName(final Socket socket) {
        return threadName + " " + socket.getInetAddress() + ":" + socket.getPort();
    }

    private void waitForAllThreadsToFinish() {
        try {
            tryWaitForAllThreadsToFinish();
        } catch (final InterruptedException ignored) {
        }
    }

    private void tryWaitForAllThreadsToFinish() throws InterruptedException {
        tryWaitForServerThreadToFinish();
        tryWaitForClientThreadsToFinish();
    }

    private void tryWaitForServerThreadToFinish() throws InterruptedException {
        serverThread.join();
    }

    private void tryWaitForClientThreadsToFinish() throws InterruptedException {
        boolean terminated;
        do {
            terminated = threadPoolExecutor.awaitTermination(50, TimeUnit.MILLISECONDS);
        } while (!terminated);
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(final int soTimeout) {
        this.soTimeout = soTimeout;
    }
}
