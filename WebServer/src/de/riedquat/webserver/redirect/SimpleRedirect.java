package de.riedquat.webserver.redirect;

import java.util.HashMap;
import java.util.Map;

public class SimpleRedirect implements Redirect {

    private final Map<String, String> redirectionTable;
    private final String baseUri;

    public SimpleRedirect(final String baseUri, final Map<String, String> redirectionTable) {
        this.baseUri = baseUri;
        this.redirectionTable = new HashMap<>(redirectionTable);
    }

    @Override
    public boolean canRedirect(final String requestUri) {
        return requestUri.startsWith(baseUri) && redirectionTable.containsKey(getUriForLookup(requestUri));
    }

    @Override
    public String getRedirectTarget(final String requestUri) {
        assert canRedirect(requestUri);
        final String uriForLookup = getUriForLookup(requestUri);
        return redirectionTable.get(uriForLookup);
    }

    private String getUriForLookup(final String requestUri) {
        return requestUri.substring(baseUri.length());
    }
}
