package de.riedquat.webserver;

import de.riedquat.java.io.NullOutputStream;
import de.riedquat.server.Server;
import de.riedquat.java.io.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static de.riedquat.java.lang.Lambda.repeat;
import static org.junit.Assert.fail;

public class WebServerStressTest {

    private static final int NUMBER_OF_THREADS = 8;
    private static final int NUMBER_OF_REQUESTS_PER_THREAD = 4;
    private final Collection<Throwable> exceptions = Collections.synchronizedCollection(new ArrayList<>());

    private Server webServer;
    private URL url;

    @Before
    public void startWebServer() throws IOException {
        webServer = new WebServerFactory().createHttpServer((SocketAddress) null);
        url = new URL("http://127.0.0.1:" + webServer.getPort() + "/foo.txt");
    }

    @After
    public void stopWebServer() throws IOException {
        webServer.close();
    }

    @Test
    public void stressTest() throws IOException, InterruptedException {
        runStressTest();
        checkForExceptions();
    }

    private void checkForExceptions() {
        if (exceptions.size() != 0) fail("exceptions" + exceptions);
    }

    private void runStressTest() throws InterruptedException {
        final ExecutorService threadPool = Executors.newCachedThreadPool();
        repeat(NUMBER_OF_THREADS, () -> threadPool.execute(this::requestMany));
        threadPool.shutdown();
        threadPool.awaitTermination(10, TimeUnit.MINUTES);
    }

    private void requestMany() {
        repeat(NUMBER_OF_REQUESTS_PER_THREAD, this::readFromServerToNull);
    }

    private void readFromServerToNull() {
        try (final InputStream in = connect()) {
            Util.copy(NullOutputStream.getInstance(), in);
        } catch (final IOException e) {
            exceptions.add(e);
        }
    }

    private InputStream connect() throws IOException {
        final URLConnection connection = url.openConnection();
        connection.setRequestProperty("Connection", "close");
        return connection.getInputStream();
    }
}
