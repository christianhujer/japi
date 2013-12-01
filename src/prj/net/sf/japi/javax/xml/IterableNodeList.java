package net.sf.japi.javax.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** A NodeList which is Iterable.
 *
 * @param <N> Type of node in this NodeList.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @version 1.0
 * @since 1.0
 */
public interface IterableNodeList<N extends Node> extends Iterable<N>, NodeList {
}
