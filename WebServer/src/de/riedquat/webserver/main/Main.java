package de.riedquat.webserver.main;

import de.riedquat.webserver.WebServerFactory;

import java.io.IOException;

public class Main {
    public static void main(final String... args) throws IOException {
        new WebServerFactory().createWebServer(8000);
    }
}
