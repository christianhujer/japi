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
package net.sf.japi.xml;

import org.jetbrains.annotations.NotNull;

import javax.xml.namespace.NamespaceContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** Configurable namespace context.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 */
public class SimpleNamespaceContext implements NamespaceContext {

    /** Default prefix to namespace mapping. */
    private static final Map<String, String> DEFAULT_PREFIX_TO_NAMESPACE;

    /** Default namespace to prefix mapping. */
    private static final Map<String, List<String>> DEFAULT_NAMESPACE_TO_PREFIX;

    /** Prefix to namespace mapping (unique). */
    private final Map<String, String> prefixToNamespace = new HashMap<String, String>();

    /** Namespace to prefix mapping (not unique). */
    private final Map<String, List<String>> namespaceToPrefix = new HashMap<String, List<String>>();

    static {
        // Create the default mappings which are always required.
        final Map<String, String> defaultPrefixToNamespace = new HashMap<String, String>();
        final Map<String, List<String>> defaultNamespaceToPrefix = new HashMap<String, List<String>>();
        mapImpl(defaultPrefixToNamespace, defaultNamespaceToPrefix, "", "");
        mapImpl(defaultPrefixToNamespace, defaultNamespaceToPrefix, "xml", "http://www.w3.org/XML/1998/namespace");
        mapImpl(defaultPrefixToNamespace, defaultNamespaceToPrefix, "xmlns", "http://www.w3.org/2000/xmlns/");
        for (final Map.Entry<String, List<String>> entry : defaultNamespaceToPrefix.entrySet()) {
            entry.setValue(Collections.unmodifiableList(entry.getValue()));
        }
        DEFAULT_NAMESPACE_TO_PREFIX = Collections.unmodifiableMap(defaultNamespaceToPrefix);
        DEFAULT_PREFIX_TO_NAMESPACE = Collections.unmodifiableMap((defaultPrefixToNamespace));
    }

    {
        // Copy the default mappings which are always required.
        prefixToNamespace.putAll(DEFAULT_PREFIX_TO_NAMESPACE);
        namespaceToPrefix.putAll(DEFAULT_NAMESPACE_TO_PREFIX);
        // The mappings stay unmodifiable except the default namespace / prefix "".
        namespaceToPrefix.put("", new ArrayList<String>(namespaceToPrefix.get("")));
    }

    /** Create a simple Namespace context.
     * @param mapping Strings which are pairs of prefix and namespace.
     */
    public SimpleNamespaceContext(@NotNull final String... mapping) {
        for (int i = 0; i < mapping.length; i += 2) {
            map(mapping[i], mapping[i + 1]);
        }
    }

    /** Adds a new mapping to this namespace context.
     * @param prefix Prefix to map. any previous mapping of <var>prefix</var> will be replaced.
     * @param namespaceURI Namespace to map. Any previous mapping of namespaceURI will be extended.
     */
    public void map(@NotNull final String prefix, @NotNull final String namespaceURI) {
        map(prefixToNamespace, namespaceToPrefix, prefix, namespaceURI);
    }

    /** Adds a new mapping to this namespace context.
     * @param prefixToNamespace Prefix to namespace mapping to extend.
     * @param namespaceToPrefix Namespace to prefix mapping to extend.
     * @param prefix Prefix to map. any previous mapping of <var>prefix</var> will be replaced.
     * @param namespaceURI Namespace to map. Any previous mapping of namespaceURI will be extended.
     */
    private static void map(@NotNull final Map<String, String> prefixToNamespace, @NotNull final Map<String, List<String>> namespaceToPrefix, @NotNull final String prefix, @NotNull final String namespaceURI) {
        if (!"".equals(prefix) && DEFAULT_PREFIX_TO_NAMESPACE.containsKey(prefix)) {
            if (DEFAULT_PREFIX_TO_NAMESPACE.get(prefix).equals(namespaceURI)) {
                return;
            }
            throw new IllegalArgumentException("Prefix " + prefix + " is fixed to namespace " + DEFAULT_PREFIX_TO_NAMESPACE.get(prefix) + " and MUST NOT be remapped.");
        }
        if (!"".equals(namespaceURI) && DEFAULT_NAMESPACE_TO_PREFIX.containsKey(namespaceURI)) {
            if (DEFAULT_NAMESPACE_TO_PREFIX.get(namespaceURI).get(0).equals(prefix)) {
                return;
            }
            throw new IllegalArgumentException("Namespace " + namespaceURI + " is fixed to prefix " + DEFAULT_NAMESPACE_TO_PREFIX.get(prefix).get(0) + " and MUST NOT be remapped.");
        }
        mapImpl(prefixToNamespace, namespaceToPrefix, prefix, namespaceURI);
    }

    /** Adds a new mapping to this namespace context.
     * @param prefixToNamespace Prefix to namespace mapping to extend.
     * @param namespaceToPrefix Namespace to prefix mapping to extend.
     * @param prefix Prefix to map. any previous mapping of <var>prefix</var> will be replaced.
     * @param namespaceURI Namespace to map. Any previous mapping of namespaceURI will be extended.
     */
    private static void mapImpl(@NotNull final Map<String, String> prefixToNamespace, @NotNull final Map<String, List<String>> namespaceToPrefix, @NotNull final String prefix, @NotNull final String namespaceURI) {
        if (prefixToNamespace.containsKey(prefix)) {
            namespaceToPrefix.get(prefixToNamespace.get(prefix)).remove(prefix);
        }
        prefixToNamespace.put(prefix, namespaceURI);
        if (!namespaceToPrefix.containsKey(namespaceURI)) {
            namespaceToPrefix.put(namespaceURI, new ArrayList<String>());
        }
        namespaceToPrefix.get(namespaceURI).add(prefix);
    }

    /** {@inheritDoc} */
    public String getNamespaceURI(@NotNull final String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Namespace prefix MUST NOT be null.");
        }
        final String namespaceURI = prefixToNamespace.get(prefix);
        return namespaceURI != null ? namespaceURI : "";
    }

    /** {@inheritDoc} */
    public String getPrefix(@NotNull final String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("Namespace prefix MUST NOT be null.");
        }
        final List<String> namespaces = namespaceToPrefix.get(namespaceURI);
        assert namespaces == null || namespaces.size() > 0;
        return namespaces == null ? null : namespaces.get(0);
    }

    /** {@inheritDoc} */
    public Iterator getPrefixes(@NotNull final String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("Namespace prefix MUST NOT be null.");
        }
        final List<String> namespaces = namespaceToPrefix.get(namespaceURI);
        return (namespaces == null ? Collections.EMPTY_SET : Collections.unmodifiableCollection(namespaces)).iterator();
    }
}
