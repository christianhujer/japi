package de.riedquat.webserver;

import java.io.IOException;

public interface Hook {
    Hook DUMMY_HOOK = () -> { };

    void run() throws IOException;
}
