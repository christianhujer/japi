package de.riedquat.webserver;

import de.riedquat.http.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FileUtil {
    static File getErrorFile(final HttpException e) {
        final HttpStatusCode responseCode = e.getResponseCode();
        return getErrorFile(responseCode);
    }

    static File getErrorFile(final HttpStatusCode responseCode) {
        return new File(responseCode.getStatusCode() + ".html");
    }

    static File getFile(final String uriString) throws HttpException {
        try {
            if (!uriString.startsWith("/")) {
                throw new BadRequestException();
            }
            return getFile(new URI(uriString.substring(1)).normalize());
        } catch (final URISyntaxException e) {
            throw new BadRequestException();
        }
    }

    private static File getFile(final URI relativeURI) throws HttpException {
        final File file = resolveFile(relativeURI);
        checkFile(file);
        return file;
    }

    private static URI resolveAbsoluteURI(final URI relativeURI) {
        return getHtdocsDirectory().toURI().resolve(relativeURI);
    }

    private static void checkFile(final File file) throws HttpException {
        checkFileSecurity(file);
        checkFileExistence(file);
    }

    private static void checkFileSecurity(final File file) throws ForbiddenException {
        if (!isInDirectory(getHtdocsDirectory(), file))
            throw new ForbiddenException();
    }

    private static void checkFileExistence(final File file) throws NotFoundException {
        if (!file.exists())
            throw new NotFoundException();
    }

    private static File resolveFile(final URI relativeURI) {
        final File file = new File(resolveAbsoluteURI(relativeURI));
        return file.isDirectory() ? new File(file, "index.html") : file;
    }

    private static boolean isInDirectory(final File htdocsDirectory, final File file) {
        try {
            return tryIsInDirectory(htdocsDirectory, file);
        } catch (final IOException e) {
            return false;
        }
    }

    private static boolean tryIsInDirectory(final File htdocsDirectory, final File file) throws IOException {
        return file.getCanonicalFile().toPath().startsWith(htdocsDirectory.getCanonicalFile().toPath());
    }

    private static File getHtdocsDirectory() {
        return new File("htdocs");
    }
}
