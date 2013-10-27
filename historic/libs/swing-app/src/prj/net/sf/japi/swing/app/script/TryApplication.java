/*
 * Copyright (C) 2009  Christian Hujer.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.sf.japi.swing.app.script;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Temporary test class.
 * @deprecated Do not use, this is just a temporary test class.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class TryApplication extends Application {

    /** {@inheritDoc} */
    public Document load(@NotNull final String uri) {
        return new Document();
    }

    /** {@inheritDoc} */
    public void quit() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /** {@inheritDoc} */
    public List getDocuments() {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }

    /** Document of this TryApplication. */
    public class Document extends Application.Document {

        /** {@inheritDoc} */
        public void save() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        /** {@inheritDoc} */
        public void saveAs(final String uri) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        /** {@inheritDoc} */
        public void close() {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        /** {@inheritDoc} */
        public List getViews() {
            return null; //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
