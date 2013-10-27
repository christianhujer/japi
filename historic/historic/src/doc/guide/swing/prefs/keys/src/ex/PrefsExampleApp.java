package ex;

import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.prefs.PreferencesGroup;
import static net.sf.japi.swing.prefs.PreferencesPane.showPreferencesDialog;
import net.sf.japi.swing.prefs.keys.KeyStrokePrefs;

public class PrefsExampleApp {

    private static final ActionBuilder ACTION_BUILDER = ActionBuilderFactory.getInstance().getActionBuilder("ex");

    /** Main program.
     * @param args command line arguments
     */
    public static void main(final String... args) {
        ACTION_BUILDER.createAction(true, "open");
        ACTION_BUILDER.createAction(true, "save");
        final PreferencesGroup prefsGroup = new PreferencesGroup(
            "Test",
            new KeyStrokePrefs(ACTION_BUILDER)
        );
        showPreferencesDialog(null, prefsGroup, false);
    }

} // class PrefsExampleApp
