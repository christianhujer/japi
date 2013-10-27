package ex;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.action.ActionMethod;

/** Example application. */
public class Application extends WindowAdapter {

    /** The application frame. */
    private final JFrame frame;

    /** Main program.
     * @param args command line arguments (currently ignored)
     */
    public static void main(final String... args) {
        //noinspection ResultOfObjectAllocationIgnored
        new Application();
    }

    private final ActionBuilder actionBuilder = ActionBuilderFactory.getInstance().getActionBuilder("ex");

    public Application() {
        frame = new JFrame(actionBuilder.getString("appWindow.title"));
        frame.setJMenuBar(actionBuilder.createMenuBar(true, "app", this));
        frame.add(actionBuilder.createToolBar("app"), BorderLayout.NORTH);
        frame.pack();
        frame.addWindowListener(this);
        frame.setVisible(true);
    }

    @ActionMethod public void fileNew() {
        // Implement this method for creating a new file
    }

    @ActionMethod public void fileOpen() {
        // Implement this method for opening an existing file
    }

    @ActionMethod public void fileSave() {
        // Implement this method for saving the current document
    }

    @ActionMethod public void fileSaveAs() {
        // Implement this method for saving the current document in a different file
    }

    @ActionMethod public void fileClose() {
        // Implement this method for closing the current document
    }

    @ActionMethod
    public void fileQuit() {
        // Change this method for asking whether to really quit the application
        frame.dispose();
    }

    @ActionMethod public void editCut() {
        // Implement this method for cutting (edit operation)
    }

    @ActionMethod public void editCopy() {
        // Implement this method for copying (edit operation)
    }

    @ActionMethod public void editPaste() {
        // Implement this method for pasting (edit operation)
    }

    /** {@inheritDoc} */
    @Override public void windowClosing(final WindowEvent e) {
        fileQuit();
    }

} // class ex.App
