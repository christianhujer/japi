package net.sf.japi.examples.editor;

import static java.awt.BorderLayout.NORTH;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Action;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.text.DefaultEditorKit;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;
import net.sf.japi.swing.action.ActionProvider;
import org.jetbrains.annotations.Nullable;

/** Small text editor demo for {@link ActionProvider}.
 * @author <a href="mailto:cher@riedqat.de">Christian Hujer</a>
 */
public class Editor implements ActionProvider {

    /** Action Builder. */
    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("net.sf.japi.examples.editor");

    /** The supported editor action names and their corresponding kit action names. */
    private static final Map<String, String> editorActionNames = new HashMap<String, String>();
    static {
        editorActionNames.put("editCut",       DefaultEditorKit.cutAction);
        editorActionNames.put("editCopy",      DefaultEditorKit.copyAction);
        editorActionNames.put("editPaste",     DefaultEditorKit.pasteAction);
        editorActionNames.put("editSelectAll", DefaultEditorKit.selectAllAction);
    }

    /** Application frame. */
    private final JFrame frame = new JFrame(ACTION_BUILDER.getString("frame.title"));

    /** Editor component. */
    private final JTextPane textPane = new JTextPane();

    /** FileChooser for opening and saving files. */
    private final JFileChooser fileChooser = new JFileChooser();

    /** Currently opened file.
     * Maybe <code>null</code> in case the current document was not already saved.
     */
    @Nullable private File file;

    /** Create the Editor. */
    public Editor() {
        ACTION_BUILDER.addActionProvider(this);
        frame.setJMenuBar(ACTION_BUILDER.createMenuBar(true, "editor", this));
        frame.add(ACTION_BUILDER.createToolBar(this, "editor"), NORTH);
        frame.add(new JScrollPane(textPane));
        frame.pack();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    /** {@inheritDoc} */
    @Nullable public Action getAction(final String key) {
        for (final Action action : textPane.getActions()) {
            final String realKey = editorActionNames.get(key);
            if (realKey != null && realKey.equals(action.getValue(Action.NAME))) {
                ACTION_BUILDER.initAction(true, action, key);
                return action;
            }
        }
        return null;
    }

    /** Action method. */
    @ActionMethod
    public void fileNew() {
        textPane.setText("");
        file = null;
    }

    /** Action method.
     * @throws FileNotFoundException In case the file to open could not be opened or found.
     * @throws IOException In case of I/O problems when trying to open or read the file.
     */
    @ActionMethod public void fileOpen() throws FileNotFoundException, IOException {
        if (fileChooser.showOpenDialog(frame) == APPROVE_OPTION) {
            final StringBuilder newText = new StringBuilder();
            final File newFile = fileChooser.getSelectedFile();
            final Reader in = new FileReader(newFile);
            try {
                final char[] buf = new char[4096];
                //noinspection NestedAssignment
                for (int charsRead; (charsRead = in.read(buf)) != -1; ) {
                    newText.append(buf, 0, charsRead);
                }
                textPane.setText(newText.toString());
                file = newFile;
            } finally {
                in.close();
            }
        }
    }

    /** Action method. */
    @ActionMethod public void fileQuit() {
        if (ACTION_BUILDER.showQuestionDialog(frame, "reallyQuit")) {
            frame.dispose();
        }
    }

    /** Action method.
     * @throws IOException In case of I/O problems when saving.
     */
    @ActionMethod public void fileSave() throws IOException {
        if (file == null) {
            fileSaveAs();
        } else {
            final Writer out = new FileWriter(file);
            try {
                out.write(textPane.getText());
            } finally {
                out.close();
            }
        }
    }

    /** Action method.
     * @throws IOException In case of I/O problems when saving.
     */
    @ActionMethod public void fileSaveAs() throws IOException {
        if (fileChooser.showSaveDialog(frame) == APPROVE_OPTION) {
            final File newFile = fileChooser.getSelectedFile();
            final Writer out = new FileWriter(newFile);
            try {
                out.write(textPane.getText());
                file = newFile;
            } finally {
                out.close();
            }
        }
    }

    /** Main program.
     * @param args command line arguments
     */
    public static void main(final String... args) {
        new Editor();
    }

} // class Editor
