package de.riedquat.webserver;

import de.riedquat.http.Http11StatusCode;
import de.riedquat.http.HttpOutputStream;
import de.riedquat.http.HttpStatusCode;
import de.riedquat.http.HttpVersion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static de.riedquat.http.Http11Header.CONTENT_LENGTH;
import static de.riedquat.java.io.Util.copy;

public class FileHttpResponse extends HttpResponse {
    private final File file;

    public FileHttpResponse(final HttpVersion httpVersionString, final File file, final HttpStatusCode responseCode) {
        super(responseCode, httpVersionString);
        this.file = file;
    }

    @Override
    public void sendResponse(final HttpOutputStream out) throws IOException {
        out.setHeader(CONTENT_LENGTH, String.valueOf(file.length()));
        try (final InputStream fin = new FileInputStream(file)) {
            copy(out, fin);
        }
    }
}
