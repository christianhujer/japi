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

package net.sf.japi.progs.jeduca.jtest.gui;

import static java.awt.BorderLayout.NORTH;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.Action;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import javax.swing.JToolBar;
import net.sf.japi.progs.jeduca.jtest.Program;
import net.sf.japi.progs.jeduca.jtest.Settings;
import net.sf.japi.progs.jeduca.swing.InternalFrameManager;
import net.sf.japi.progs.jeduca.swing.MenuManager;
import net.sf.japi.progs.jeduca.swing.OpenURLPane;
import net.sf.japi.progs.jeduca.swing.ToolBarManager;
import net.sf.japi.progs.jeduca.swing.io.ImporterFileFilter;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;
import net.sf.japi.swing.LookAndFeelManager;
import net.sf.japi.swing.ToolBarLayout;
import net.sf.japi.swing.about.AboutDialog;
import net.sf.japi.swing.app.Document;
import net.sf.japi.swing.bookmarks.BookmarkManager;
import net.sf.japi.swing.bookmarks.Bookmarkable;
import net.sf.japi.swing.prefs.PreferencesGroup;
import net.sf.japi.swing.prefs.PreferencesPane;
import net.sf.japi.swing.recent.RecentURLsMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Programmfenster.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 * @todo workout transience of program
 * @fixme #1 Once the menubar is hidden, even the KeyStroke won't make it visible anymore. Even other KeyStrokes from JMenuItems don't work anymore then. Fix this.
 * @fixme #2 F10 activates the wrong menubar.
 */
public class ProgramFrame extends JFrame implements WindowListener, Bookmarkable {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** The Program this Frame displays. */
    private transient Program program;

    /** The BookmarkManager. */
    private final transient BookmarkManager bookmarkManager = new BookmarkManager(this);

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.progs.jeduca.jtest.gui");

    /** The JFileChooser for loading and saving files.
     * @serial include
     */
    private JFileChooser fileChooser;

    /** QuestionCollectionUI.
     * @serial include
     */
    private QuestionCollectionGUI questionCollectionGUI;

    /** The Preferences modules.
     * @serial include
     */
    private PreferencesGroup prefsGroup;

    /** The URL of the current question collection.
     * @serial include
     */
    @Nullable private String url;

    /** Create a ProgramFrame.
     * @param program Program object to create frame for
     */
    public ProgramFrame(final Program program) {
        super("");
        setProgram(program);
        initUI();
    }

    /** Initialize GUI. */
    private void initUI() {
        setLayout(new ToolBarLayout());
        prefsGroup = new PreferencesGroup("Dummy title", new FirstSettingsModule(program.getSettings()), new GUIOptionsSettingsModule(program.getSettings()));
        fileChooser = new JFileChooser();
        final JMenuBar menuBar = new JMenuBar();
        final JMenu mView     = new JMenu(view);
        final JMenu mDoc      = createProgramMenu();
        final JMenu mSettings = new JMenu(settings);
        final JMenu mHelp     = new JMenu(help);
        //updateLast();
        questionCollectionGUI = new QuestionCollectionGUI(program.getSettings());

        ImporterFileFilter.attachFileFilters(fileChooser, program.getIOModuleProvider());
        final ToolBarManager       tm = new ToolBarManager();
        final MenuManager          mm = new MenuManager();
        final InternalFrameManager fm = new InternalFrameManager();
        final LookAndFeelManager   lm = new LookAndFeelManager();
        lm.add(this);

        setTitle("JEduca");

        add(questionCollectionGUI);
        final JToolBar menuToolBar       = new JToolBar(ACTION_BUILDER.getString("menuToolBar.name"));
        menuToolBar.add(menuBar);
        //menuToolBar.putClientProperty(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(ACTION_BUILDER.getString("menuToolBar.accel"))); // FIXME:2009-02-22:christianhujer:Doesn't work somehow.
        final JToolBar bookmarkToolBar   = bookmarkManager.createBookmarkToolBar();
        final JToolBar programToolBar    = ACTION_BUILDER.createToolBar(this, "programToolBar");
        final JToolBar navigationToolBar = questionCollectionGUI.createNavigationToolBar();
        add(menuToolBar,    NORTH);
        add(programToolBar,    NORTH);
        add(bookmarkToolBar,   NORTH);
        add(navigationToolBar, NORTH);
        //tm.add(menuToolBar); // FIXME:2009-02-22:christianhujer:Doesn't work somehow.
        tm.add(programToolBar);
        tm.add(bookmarkToolBar);
        tm.add(navigationToolBar);
        //add(questionCollectionGUI.createNavigationToolBar(), SOUTH); // FIXME:2009-02-22:christianhujer:Doesn't work somehow.
        menuBar.add(mDoc);
        menuBar.add(mView);
        final JMenu navigationMenu = questionCollectionGUI.createNavigationMenu();
        menuBar.add(navigationMenu);
        bookmarkManager.setAddBookmarkEnabled(false);
        final JMenu bookmarkMenu = bookmarkManager.createBookmarkMenu();
        final JMenu lafMenu = lm.createMenu();
        mm.add(navigationMenu);
        mm.add(bookmarkMenu);
        mm.add(lafMenu);
        menuBar.add(bookmarkMenu);
        menuBar.add(lafMenu);
        menuBar.add(mSettings);
        menuBar.add(mHelp);
        mView.add(tm.createManagedMenu());
        mView.add(mm.createManagedMenu());
        mView.add(fm.createManagedMenu());
        mSettings.add(new JMenuItem(config));
        mHelp.add(about);
        //setJMenuBar(menuBar);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        pack();
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }

    /** Set the Program this Frame displays.
     * @param program Program to display
     */
    private void setProgram(final Program program) {
        this.program = program;
    }

    /** Load Action. */
    @ActionMethod
    public void open() {
        final String newUrl = OpenURLPane.showOpenURLDialog(this, fileChooser);
        if (newUrl != null) {
            load(newUrl);
        }
    }

    /** Get the URL to display.
     * @return diplayed URL
     */
    @Nullable public String getURL() {
        try {
            URL url = new URL(this.url);
            final int index = questionCollectionGUI.getIndex();
            if (index >= 0) {
                url = new URL(url, "#question" + index);
            }
            return this.url = url.toString();
        } catch (final MalformedURLException e) {
            System.err.println(e);
            return null;
        }
    }

    /** Set the URL to display.
     * @param url URL to display
     */
    public void setURL(final String url) {
        load(url);
    }

    /** {@inheritDoc} */
    // TODO:2009-02-22:christianhujer:Do not return null if loading was successful.
    @NotNull
    public Document<Object> load(@NotNull final String uri) {
        boolean okay;
        try {
            // TODO:2009-02-22:christianhujer:IOManager
            // program.eduImport(url);
            program.load(uri);
            questionCollectionGUI.setQuestionCollection(program.getQuestionCollection());
            setTitle("JEduca: " + program.getQuestionCollection().getTitle() + " (" + uri + ')');
            final String fragmentId = new URL(uri).getRef();
            if (fragmentId != null && fragmentId.startsWith("question")) {
                final String questionNumber = fragmentId.substring("question".length());
                questionCollectionGUI.displayQuestion(Integer.parseInt(questionNumber));
            }
            okay = true;
            url = uri;
        } catch (final Exception e) {
            JOptionPane.showMessageDialog(this, e, ACTION_BUILDER.getString("open.error.text") + ':', ERROR_MESSAGE);
            System.err.println(e);
            okay = false;
            url = null;
        }
        bookmarkManager.setAddBookmarkEnabled(isBookmarkPossible());
        return okay ? null : null;
    }

    /** Save Action. */
    @ActionMethod
    public void save() {
        if (fileChooser.showSaveDialog(this) == APPROVE_OPTION) {
            try {
                program.eduExport(fileChooser.getSelectedFile());
                // TOOD: JTest
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, e, ACTION_BUILDER.getString("save.error.text") + ':', ERROR_MESSAGE);
                System.err.println(e);
            }
        }
    }

    /** Print Action. */
    @ActionMethod
    public void print() {
        // TODO:2009-02-22:christianhujer:Implement.
    }

    /** Prefs Action. */
    @ActionMethod
    public void config() {
        PreferencesPane.showPreferencesDialog(this, prefsGroup, true);
    }

    /** Quit Action. */
    @ActionMethod
    public void quit() {
        if (JOptionPane.showConfirmDialog(this, ACTION_BUILDER.getString("quit.question"), ACTION_BUILDER.getString("quit.text"), YES_NO_OPTION) == YES_OPTION) {
            dispose();
        }
    }

    /** The AboutDialog. */
    private final AboutDialog aboutDialog = new AboutDialog(ACTION_BUILDER);

    /** About Action. */
    @ActionMethod
    public void about() {
        aboutDialog.showAboutDialog(this);
    }

    /** Action for doc.
     * @serial include
     */
    private final Action doc      = ACTION_BUILDER.createAction(false, "doc");

    /** Action for open.
     * @serial include
     */
    private final Action open     = ACTION_BUILDER.createAction(true, "open",     this);

    /** Action for save.
     * @serial include
     */
    private final Action save     = ACTION_BUILDER.createAction(true, "save",     this);

    /** Action for print.
     * @serial include
     */
    private final Action print    = ACTION_BUILDER.createAction(true, "print",    this);

    /** Action for quit.
     * @serial include
     */
    private final Action quit     = ACTION_BUILDER.createAction(true, "quit",     this);

    /** Action for view.
     * @serial include
     */
    private final Action view     = ACTION_BUILDER.createAction(true, "view");

    /** Action for settings.
     * @serial include
     */
    private final Action settings = ACTION_BUILDER.createAction(true, "settings");

    /** Action for config.
     * @serial include
     */
    private final Action config   = ACTION_BUILDER.createAction(true, "config",   this);

    /** Action for help.
     * @serial include
     */
    private final Action help     = ACTION_BUILDER.createAction(true, "help");

    /** Action for about.
     * @serial include
     */
    private final Action about    = ACTION_BUILDER.createAction(true, "about",    this);

    /** Create Menu with essential program actions.
     * @return Menu with essential program actions
     */
    public JMenu createProgramMenu() {
        final JMenu mDoc      = new JMenu(doc);
        mDoc.add(new JMenuItem(open));
        mDoc.add(new RecentURLsMenu(Settings.class, this));
        mDoc.addSeparator();
        mDoc.add(new JMenuItem(save));
        mDoc.addSeparator();
        mDoc.add(new JMenuItem(print));
        mDoc.addSeparator();
        mDoc.add(new JMenuItem(quit));
        return mDoc;
    }

    /** {@inheritDoc} */
    public void windowActivated(final WindowEvent e) {
        /* ignore */
    }

    /** {@inheritDoc} */
    public void windowClosed(final WindowEvent e) {
        /* ignore */
    }

    /** {@inheritDoc} */
    public void windowClosing(final WindowEvent e) {
        quit();
    }

    /** {@inheritDoc} */
    public void windowDeactivated(final WindowEvent e) {
        /* ignore */
    }

    /** {@inheritDoc} */
    public void windowDeiconified(final WindowEvent e) {
        /* ignore */
    }

    /** {@inheritDoc} */
    public void windowIconified(final WindowEvent e) {
        /* ignore */
    }

    /** {@inheritDoc} */
    public void windowOpened(final WindowEvent e) {
        /* ignore */
    }

    /** {@inheritDoc} */
    public boolean isBookmarkPossible() {
        return program.getQuestionCollection() != null;
    }

    /** {@inheritDoc} */
    public String getBookmarkTitle() {
        return program.getQuestionCollection().getTitle();
    }

    /** {@inheritDoc} */
    public String getBookmarkURL() {
        return getURL();
    }

    /** {@inheritDoc} */
    public Component getBookmarkBlocker() {
        return this;
    }

} // class ProgramFrame
