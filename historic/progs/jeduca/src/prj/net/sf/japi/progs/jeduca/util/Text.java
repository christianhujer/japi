/*
 * Copyright (C) 2009  Christian Hujer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.japi.progs.jeduca.util;

import java.io.Serializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Class for holding some text information of an arbitrary mime type.
 * Mutable.
 * Supports MIME.
 * Just a little String container.
 * For convenience, this method implements some interfaces, especially <code>CharSequence</code>.
 * Mutability is only possible by <code>String</code> exchange.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class Text implements Serializable, Comparable<Text>, CharSequence, MimeData {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The Text.
     * @serial include
     */
    @NotNull private String text;

    /** The MIME Type.
     * @serial include
     */
    @NotNull private String type;

    /** Create an empty text.
     * The text is the empty String, the type is <code>text/plain</code>.
     */
    public Text() {
        text = "";
        type = "text/plain";
    }

    /** Create Text.
     * @param text Text
     * @param type MIME Type
     * @throws NullPointerException in case text or type is null
     */
    public Text(@NotNull final String text, @NotNull final String type) {
        setText(text);
        setType(type);
    }

    /** Get the Text.
     * @return text
     */
    @NotNull public String getText() {
        return text;
    }

    /** Set the Text.
     * @param text new text
     */
    public void setText(@NotNull final String text) {
        //noinspection ConstantConditions
        if (text == null) {
            throw new NullPointerException("text for Text may not be null");
        }
        this.text = text;
    }

    /** {@inheritDoc} */
    @NotNull public String getType() {
        return type;
    }

    /** {@inheritDoc} */
    public void setType(@NotNull final String type) {
        //noinspection ConstantConditions
        if (type == null) {
            throw new NullPointerException("type for Text may not be null");
        }
        this.type = type;
    }

    /** {@inheritDoc} */
    public byte[] getData() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    public void setData(final byte[] data) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /** {@inheritDoc} */
    @Override public boolean equals(@Nullable final Object o) {
        if (o == null || !(o instanceof Text)) {
            return false;
        }
        final Text t = (Text) o;
        return type.equals(t.type) && text.equals(t.text);
    }

    /** {@inheritDoc} */
    @Override public int hashCode() {
        return type.hashCode() ^ text.hashCode();
    }

    /** {@inheritDoc}
     * Simple returns the text the same way as {@link #getText()}.
     */
    @Override public String toString() {
        return text;
    }

    /** {@inheritDoc} */
    public int compareTo(final Text o) {
        int ret = type.compareTo(o.type);
        if (ret == 0) {
            ret = text.compareTo(o.text);
        }
        return ret;
    }

    /** {@inheritDoc} */
    public char charAt(final int index) {
        return text.charAt(index);
    }

    /** {@inheritDoc} */
    public int length() {
        return text.length();
    }

    /** {@inheritDoc} */
    @NotNull public Text subSequence(final int start, final int end) {
        return new Text(text.substring(start, end), type);
    }

} // class Text
