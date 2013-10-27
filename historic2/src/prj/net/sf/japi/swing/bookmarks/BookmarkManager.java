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

package net.sf.japi.swing.bookmarks;

import java.awt.BorderLayout;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.IconManager;
import net.sf.japi.util.EmptyEnumeration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.xml.sax.SAXException;

/** Class for managing and displaying Bookmarks.
 * Usage of this class works the following way:
 * <ul>
 *  <li>implement the interface {@link Bookmarkable} and its methods</li>
 *  <li>instanciate this class once for each gruop / kind of bookmarks you want to manage. Normally, one instance is enough per application</li>
 *  <li>invoke {@link #createBookmarkMenu} to create your bookmarks menu</li>
 * </ul>
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo documentation
 * @todo fix bookmark drag and drop indices
 * @todo think about serialization of Actions
 */
public class BookmarkManager implements Serializable {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** The Bookmarks.
     * @serial include
     */
    private BookmarkFolder bookmarks = new BookmarkFolder();

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.swing.bookmarks");

    /** The ProgramFrame this BookmarkManager manages bookmarks for.
     * @serial include
     */
    private final Bookmarkable bookmarkable;

    /** Create a new BookmarkManager.
     * @param bookmarkable The bookmarkable to use for this BookmarkManager.
     */
    public BookmarkManager(final Bookmarkable bookmarkable) {
        this.bookmarkable = bookmarkable;
        try {
            load();
        } catch (final IOException e) {
            e.printStackTrace();
            // TODO:2009-02-15:christianhujer:improve dialog
            showMessageDialog(bookmarkable.getBookmarkBlocker(), ACTION_BUILDER.getString("bookmarksCreated.message"));
        } catch (final ParserConfigurationException e) {
            e.printStackTrace();
            // TODO:2009-02-15:christianhujer:improve dialog
            showMessageDialog(bookmarkable.getBookmarkBlocker(), ACTION_BUILDER.getString("bookmarksCreated.message"));
        } catch (final SAXException e) {
            e.printStackTrace();
            // TODO:2009-02-15:christianhujer:improve dialog
            showMessageDialog(bookmarkable.getBookmarkBlocker(), ACTION_BUILDER.getString("bookmarksCreated.message"));
        }
    }

    /** Create a Bookmark ToolBar.
     * @return toolBar for Bookmarks
     */
    @NotNull public JToolBar createBookmarkToolBar() {
        // Variant 1: JToolBar with JMenuBar with one entry "Lesezeichen"
        final JToolBar toolBar = new JToolBar(ACTION_BUILDER.getString("bookmarkToolBar.name"));
        final JMenuBar mb = new JMenuBar();
        mb.add(bookmarks.createMenu());
        toolBar.add(mb);
        return toolBar;

        //// Variant 2: JToolBar with JMenus and JMenuItems (broken)
        //JToolBar toolBar = new JToolBar(ACTION_BUILDER.getString("bookmarkToolBar.name"));
        //for (Bookmark bookmark : bookmarks) {
        //    toolBar.add(bookmark.createMenu());
        //}
        //return toolBar;

        //// Variant 3: JToolBar with JMenuBar with JMenus and JMenuItems (JMenuItems don't hover)
        //JToolBar toolBar = new JToolBar(ACTION_BUILDER.getString("bookmarkToolBar.name"));
        //JMenuBar mb = new JMenuBar();
        //for (Bookmark bookmark : bookmarks) {
        //    mb.add(bookmark.createMenu());
        //}
        //toolBar.add(mb);
        //return toolBar;

        //// Variant 4: JToolBar with a button activating a PopupMenu.
        //// Doesn't work either, the popup menu is not correctly usable.
        //JToolBar toolBar = new JToolBar(ACTION_BUILDER.getString("bookmarkToolBar.name"));
        //final JPopupMenu menu = new JPopupMenu(ACTION_BUILDER.getString("bookmark.text"));
        //menu.add(bookmarks.createMenu());
        //toolBar.add(
        //    new javax.swing.JButton(
        //        new AbstractAction() {
        //            public void actionPerformed(final ActionEvent e) {
        //                menu.setVisible(true);
        //            }
        //        }
        //    )
        //);
        //return toolBar;
    }

    /** Create a Bookmark Menu.
     * @return JMenu for Bookmarks
     */
    @NotNull public JMenu createBookmarkMenu() {
        final JMenu menu = bookmarks.createMenu();
        menu.add(new JMenuItem(manageBookmarks));
        return menu;
    }

    /** Create a Bookmark ControlPanel.
     * @return ControlPanel for Bookmarks
     */
    @NotNull public JComponent createBookmarkControlPanel() {
        return new ControlPanel();
    }

    /** Recursive attach method.
     * @param menu JMenu to attach bookmarks to
     * @param bookmarks Bookmarks to attach
     */
    private static void attach(@NotNull final JMenu menu, @NotNull final BookmarkFolder bookmarks) {
        for (final Bookmark bookmark : bookmarks) {
            if (bookmark instanceof BookmarkFolder) {
                final JMenu newMenu = new JMenu(bookmark);
                attach(newMenu, (BookmarkFolder) bookmark);
            } else {
                assert bookmark instanceof BookmarkItem;
                menu.add(new JMenuItem(bookmark));
            }
        }
    }

    /** Action for managing the bookmarks.
     * @serial include
     */
    private final Action manageBookmarks = ACTION_BUILDER.createAction(true, "manageBookmarks", this);

    /** Action for managing the bookmarks. */
    public void manageBookmarks() {
        final JFrame f = new JFrame(ACTION_BUILDER.getString("manageBookmarks_shortdescription"));
        f.getContentPane().add(createBookmarkControlPanel());
        //f.getContentPane().add(new JScrollPane(new JTree(bookmarks)));
        f.pack();
        f.setVisible(true);
    }

    /** Set the AddBookmark enabled state.
     * @param enabled enabled state for AddBookmark action
     */
    public void setAddBookmarkEnabled(final boolean enabled) {
        bookmarks.setAddBookmarkEnabled(enabled);
    }

    /** Load bookmarks from default file.
     * @throws IOException in case of I/O problems
     * @throws ParserConfigurationException in case of XML configuration problems
     * @throws SAXException in case of XML document problems
     */
    public void load() throws IOException, ParserConfigurationException, SAXException {
        load(new File(System.getProperty("user.home"), ".jeduca.bookmarks.xml").toURI().toURL().toString());
    }

    /** Save bookmarks to default file.
     * @throws IOException in case of I/O problems
     * @throws ParserConfigurationException in case of XML configuration problems
     */
    public void save() throws IOException, ParserConfigurationException {
        save(new File(System.getProperty("user.home"), ".jeduca.bookmarks.xml").toURI().toURL().toString());
    }

    /** Save bookmarks to a file.
     * @param uri URI of file to save
     * @throws IOException in case of I/O problems
     * @throws ParserConfigurationException in case of XML configuration problems
     */
    public void save(@NotNull final String uri) throws IOException, ParserConfigurationException {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        final DocumentBuilder db = dbf.newDocumentBuilder();
        final Document doc = db.newDocument();
        bookmarks.store(doc);
        final DOMImplementation impl = doc.getImplementation();
        if (impl instanceof DOMImplementationLS) {
            final DOMImplementationLS ls = (DOMImplementationLS) impl;
            ls.createLSSerializer().writeToURI(doc, uri);
        }
    }

    /** Load bookmarks from a file.
     * @param uri URI of file to load
     * @throws IOException in case of I/O problems
     * @throws ParserConfigurationException in case of XML configuration problems
     * @throws SAXException in case of XML document problems
     */
    public void load(@NotNull final String uri) throws IOException, ParserConfigurationException, SAXException {
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        final DocumentBuilder db = dbf.newDocumentBuilder();
        final Document doc = db.parse(uri);
        bookmarks = new BookmarkFolder(doc);
    }


    /** Class for a ControlPanel to manage the bookmarks.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    private class ControlPanel extends JComponent {

        /** Serial Version. */
        private static final long serialVersionUID = 1L;

        /** Create a new ControlPanel. */
        ControlPanel() {
            setLayout(new BorderLayout());
            final JTree tree = new JTree(bookmarks, true);
            tree.setRootVisible(false);
            tree.setCellRenderer(new BookmarkTreeCellRenderer());
            //tree.setEditable(true);
            tree.setDragEnabled(true);
            final DropTarget dt = new DropTarget(tree, new BookmarkDropTargetAdapter());
            tree.setTransferHandler(new BookmarkTransferHandler());
            add(new JScrollPane(tree));
        }

    } // class ControlPanel


    /** Base class for bookmarks.
     * There are two kinds of bookmarks:
     * <ul>
     *  <li>{@link BookmarkItem}s for normal Bookmarks with title and url</li>
     *  <li>{@link BookmarkFolder}s for Folders within Bookmarks with a title and (possibly) contents</li>
     * </ul>
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    @SuppressWarnings({"ClassReferencesSubclass"})
    public abstract static class Bookmark extends AbstractAction implements MutableTreeNode {

        /** Title for Bookmark.
         * @serial include
         */
        @NotNull private String title;

        /** The folder (parent) of this bookmark.
         * @serial include
         */
        @Nullable private BookmarkFolder folder;

        ///** Create a Bookmark without title.
        // * Should only be used by {@link BookmarkFolder#BookmarkFolder()}.
        // */
        //protected Bookmark() {
        //}

        /** Create a Bookmark.
         * You must not forget to invoke {@link #setFolder(BookmarkManager.BookmarkFolder)} in order to make {@link #getParent()} for JTrees working.
         * @param title title for Bookmark
         */
        protected Bookmark(@NotNull final String title) {
            setTitle(title);
            putValue(SMALL_ICON, IconManager.getDefaultIconManager().getIcon(ACTION_BUILDER.getString("bookmark.icon")));
        }

        /** {@inheritDoc}
         * @return title of this Bookmark
         */
        @Override @NotNull public String toString() {
            return title;
        }

        /** Get the folder (parent) of this bookmark.
         * @return folder (parent) of this bookmark
         */
        @Nullable public BookmarkFolder getFolder() {
            return folder;
        }

        /** Set the folder (parent) of this bookmark.
         * @param folder parent folder of this bookmark
         */
        public void setFolder(@Nullable final BookmarkFolder folder) {
            if (this.folder != null) {
                this.folder.remove(this);
            }
            this.folder = folder;
        }

        /** Set this Bookmark's title.
         * @param title new title for this Bookmark
         */
        public void setTitle(@NotNull final String title) {
            this.title = title;
            putValue(NAME, title);
        }

        /** Create a MenuItem for this Bookmark
         * @return MenuItem for this Bookmark
         */
        public abstract JMenuItem createMenu();

        /** Get this Bookmark's title.
         * @return Bookmark title
         */
        @NotNull public String getTitle() {
            return title;
        }

        /** Store bookmarks in an XML Document.
         * @param n Node (Element or Document) to attach to
         */
        public abstract void store(final Node n);

        /** {@inheritDoc} */
        public Enumeration<Bookmark> children() {
            return new EmptyEnumeration<Bookmark>();
        }

        /** {@inheritDoc} */
        public boolean getAllowsChildren() {
            return false;
        }

        /** {@inheritDoc} */
        public Bookmark getChildAt(final int childIndex) {
            throw new IndexOutOfBoundsException(Integer.toString(childIndex));
        }

        /** {@inheritDoc} */
        public int getChildCount() {
            return 0;
        }

        /** {@inheritDoc} */
        public int getIndex(final TreeNode node) {
            return 0;
        }

        /** {@inheritDoc} */
        public BookmarkFolder getParent() {
            return getFolder();
        }

        /** {@inheritDoc} */
        public boolean isLeaf() {
            return true;
        }

        /** {@inheritDoc} */
        public void insert(final MutableTreeNode child, final int index) {
            throw new IllegalStateException("A bookmark cannot have children");
        }

        /** {@inheritDoc} */
        public void remove(final int index) {
            throw new IllegalStateException("A bookmark has no children");
        }

        /** {@inheritDoc} */
        public void remove(final MutableTreeNode child) {
            throw new IllegalStateException("A bookmark has no children");
        }

        /** {@inheritDoc} */
        public void removeFromParent() {
            if (folder != null) {
                folder.remove(this);
            }
        }

        /** {@inheritDoc} */
        public void setParent(final MutableTreeNode newParent) {
            if (newParent instanceof BookmarkFolder) {
                setFolder((BookmarkFolder) newParent);
            } else {
                throw new IllegalArgumentException("Parent must be a BookmarkFolder, but was : " + newParent.getClass().getName());
            }
        }

        /** {@inheritDoc} */
        public void setUserObject(final Object object) {
            System.err.println("setUserObject was called but does not know what to do. Supplied object: " + object);
        }

    } // class Bookmark


    /** Class for Bookmark Separator.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    public static class BookmarkSeparator extends Bookmark {

        /** Serial Version. */
        private static final long serialVersionUID = 1L;

        /** Create a Bookmark Separator. */
        BookmarkSeparator() {
            super("--------------------------------");
        }

        /** {@inheritDoc}
         * @return always <code>null</code>
         */
        @Override public JMenuItem createMenu() {
            return null;
        }

        /** {@inheritDoc} */
        @Override public void store(final Node n) {
            final Element e = n.getOwnerDocument().createElement("separator");
            n.appendChild(e);
        }

        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent e) {
            /* Do nothing */
        }

    } // class BookmarkSeparator


    /** Class for Bookmark Items.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    public class BookmarkItem extends Bookmark {

        /** Serial Version. */
        private static final long serialVersionUID = 1L;

        /** URL of this Bookmark.
         * @serial include
         */
        private String url;

        /** Create a BookmarkItem.
         * @param title title for this BookmarkItem
         * @param url URL for this BookmarkItem
         */
        public BookmarkItem(final String title, final String url) {
            super(title);
            setURL(url);
        }

        /** Create a BookmarkItem from XML.
         * @param el Element to create from
         */
        BookmarkItem(final Element el) {
            this(el.getAttribute("title"), el.getAttribute("href"));
        }

        /** Set this BookmarkItem's url.
         * @param url new url for this BookmarkItem
         */
        public void setURL(final String url) {
            this.url = url;
            putValue(SHORT_DESCRIPTION, url);
        }

        /** Get this BookmarkItem's url.
         * @return BookmarkItem url
         */
        public String getURL() {
            return url;
        }

        /** {@inheritDoc} */
        @Override public JMenuItem createMenu() {
            return new JMenuItem(this);
        }

        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent e) {
            bookmarkable.load(url);
        }

        /** {@inheritDoc} */
        @Override public void store(final Node n) {
            final Element e = n.getOwnerDocument().createElement("bookmark");
            e.setAttribute("title", getTitle());
            e.setAttribute("href", getURL());
            n.appendChild(e);
        }

    } // class BookmarkItem


    /** Class for Bookmark folders.
     * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
     */
    public class BookmarkFolder extends Bookmark implements Iterable<Bookmark> {

        /** Serial Version. */
        private static final long serialVersionUID = 1L;

        /** The bookmarks in this folder.
         * @serial include
         */
        private final List<Bookmark> bookmarks = new ArrayList<Bookmark>();

        /** The Menus created for this folder.
         * @serial include
         */
        private final List<JMenu> menus = new ArrayList<JMenu>();

        /** Create a BookmarkFolder without a title.
         * This should only be used for the basic BookmarkFolder containing all other BookmarkItems and BookmarkFolders.
         */
        public BookmarkFolder() {
            super(ACTION_BUILDER.getString("bookmark.text"));
        }

        /** Create a BookmarkFolder.
         * @param title title for BookmarkFolder
         */
        public BookmarkFolder(final String title) {
            super(title);
            addBookmark.putValue(ACCELERATOR_KEY, null);
            newBookmarkFolder.putValue(ACCELERATOR_KEY, null);
        }

        /** Create a BookmarkFolder from XML.
         * @param doc Document to create from
         */
        BookmarkFolder(final Document doc) {
            this();
            readNodes(doc.getDocumentElement());
        }

        /** Create a BookmarkFolder from XML.
         * @param el Element to create from
         */
        BookmarkFolder(final Element el) {
            this(el.getAttribute("title"));
            readNodes(el);
        }

        /** Read nodes from XML.
         * @param el XML Element to read nodes from
         */
        private void readNodes(final Element el) {
            final NodeList children = el.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                final Node n = children.item(i);
                if (n instanceof Element) {
                    final Element e = (Element) n;
                    if ("bookmark".equals(e.getNodeName())) {
                        add(new BookmarkItem(e));
                    } else if ("bookmarks".equals(e.getNodeName())) {
                        add(new BookmarkFolder(e));
                    } else if ("separator".equals(e.getNodeName())) {
                        add(new BookmarkSeparator());
                    }
                }
            }
        }

        /** {@inheritDoc} */
        public Iterator<Bookmark> iterator() {
            return bookmarks.iterator();
        }

        /** Add a Bookmark to this BookmarkFolder.
         * @param bookmark Bookmark to add
         */
        public void add(final Bookmark bookmark) {
            final int pos = bookmarks.size();
            bookmarks.add(bookmark);
            bookmark.setFolder(this);
            for (final JMenu menu : menus) {
                menu.insert(bookmark.createMenu(), pos);
            }
            try {
                save();
            } catch (final Exception e) {
                showMessageDialog(bookmarkable.getBookmarkBlocker(), e, "Bookmarks-Fehler", ERROR_MESSAGE);
                System.err.println(e);
                e.printStackTrace();
            }
        }

        /** Insert a Bookmark into this BookmarkFolder.
         * @param bookmark Bookmark to add
         * @param index desired index
         */
        public void insert(final Bookmark bookmark, final int index) {
            if (index < 0 || index > bookmarks.size()) {
                throw new IllegalArgumentException("Invalid index: " + index + " (size: " + bookmarks.size() + ")");
            }
            bookmark.setFolder(this);
            bookmarks.add(index, bookmark);
            for (final JMenu menu : menus) {
                menu.insert(bookmark.createMenu(), index);
            }
            try {
                save();
            } catch (final Exception e) {
                showMessageDialog(bookmarkable.getBookmarkBlocker(), e, "Bookmarks-Fehler", ERROR_MESSAGE);
                System.err.println(e);
                e.printStackTrace();
            }
        }

        /** Remove a Bookmark from this BookmarkFolder.
         * @param bookmark Bookmark to remove
         */
        public void remove(final Bookmark bookmark) {
            if (bookmarks.contains(bookmark)) {
                remove(bookmarks.indexOf(bookmark));
            } else {
                for (final Bookmark folder : bookmarks) {
                    if (folder instanceof BookmarkFolder) {
                        ((BookmarkFolder) folder).remove(bookmark);
                    }
                }
            }
        }

        /** Remove a Bookmark from this BookmarkFolder.
         * @param index Index of Bookmark to remove
         */
        @Override public void remove(final int index) {
            final Bookmark bookmark = bookmarks.remove(index);
            bookmark.folder = null;
            for (final JMenu menu : menus) {
                menu.remove(index);
            }
        }

        /** {@inheritDoc} */
        @Override public JMenu createMenu() {
            final JMenu menu = new JMenu(getTitle());
            menu.setName(getTitle());
            for (final Bookmark bookmark : bookmarks) {
                if (bookmark instanceof BookmarkSeparator) {
                    menu.addSeparator();
                } else {
                    menu.add(bookmark.createMenu());
                }
            }
            menu.addSeparator();
            menu.add(new JMenuItem(addBookmark));
            menu.add(new JMenuItem(newBookmarkFolder));
            menus.add(menu);
            return menu;
        }

        /** {@inheritDoc} */
        public void actionPerformed(final ActionEvent e) {
            /* Do nothing */
        }

        /** Action for adding a bookmark.
         * @serial include
         */
        private final Action addBookmark = ACTION_BUILDER.createAction(true, "addBookmark", this);

        /** Action for creating a new bookmark folder.
         * @serial include
         */
        private final Action newBookmarkFolder = ACTION_BUILDER.createAction(true, "newBookmarkFolder", this);

        /** Add a bookmark for the currently selected Question from the currently selected QuestionCollection . */
        public void addBookmark() {
            if (bookmarkable.isBookmarkPossible()) {
                add(new BookmarkItem(bookmarkable.getBookmarkTitle(), bookmarkable.getBookmarkURL()));
            }
        }

        /** Create a new BookmarkFolder. */
        public void newBookmarkFolder() {
            final String folderName = showInputDialog(bookmarkable.getBookmarkBlocker(), "Name für Lesezeichen", "Neuer Lesezeichen-Ordner", QUESTION_MESSAGE);
            if (folderName == null) {
                return;
            }
            add(new BookmarkFolder(folderName));
        }

        /** Set the AddBookmark enabled state.
         * @param enabled enabled state for AddBookmark action
         */
        public void setAddBookmarkEnabled(final boolean enabled) {
            addBookmark.setEnabled(enabled);
            for (final Bookmark folder : bookmarks) {
                if (folder instanceof BookmarkFolder) {
                    ((BookmarkFolder) folder).setAddBookmarkEnabled(enabled);
                }
            }
        }

        /** {@inheritDoc} */
        @Override public void store(final Node n) {
            final Element e = (n instanceof Document ? (Document) n : n.getOwnerDocument()).createElement("bookmarks");
            e.setAttribute("title", getTitle());
            n.appendChild(e);
            for (final Bookmark bookmark : bookmarks) {
                bookmark.store(e);
            }
        }

        /** {@inheritDoc} */
        @Override public Enumeration<Bookmark> children() {
            return Collections.enumeration(bookmarks);
        }

        /** {@inheritDoc} */
        @Override public boolean getAllowsChildren() {
            return true;
        }

        /** {@inheritDoc} */
        @Override public Bookmark getChildAt(final int childIndex) {
            return bookmarks.get(childIndex);
        }

        /** {@inheritDoc} */
        @Override public int getChildCount() {
            return bookmarks.size();
        }

        /** {@inheritDoc} */
        @Override public int getIndex(final TreeNode node) {
            return bookmarks.indexOf(node);
        }

        /** {@inheritDoc} */
        @Override public boolean isLeaf() {
            return false;
        }

        /** {@inheritDoc} */
        @Override public void insert(final MutableTreeNode child, final int index) {
            if (child instanceof Bookmark) {
                insert((Bookmark) child, index);
            } else {
                throw new IllegalArgumentException("Children of BookmarkFolders must be instance of Bookmark but was " + child.getClass().getName());
            }
        }

        ///** {@inheritDoc} */
        //public void remove(final int index) {
        //    remove(index);
        //}

        /** {@inheritDoc} */
        @Override public void remove(final MutableTreeNode child) {
            if (child instanceof Bookmark) {
                remove((Bookmark) child);
            } else {
                throw new IllegalArgumentException("Node " + child + " not child of this BookmarkFolder.");
            }
        }

    } // class BookmarkFolder

} // class BookmarkManager
