package de.riedquat.webserver.redirect;

public interface Redirect {
    boolean canRedirect(String requestUri);

    String getRedirectTarget(String requestUri);
}
