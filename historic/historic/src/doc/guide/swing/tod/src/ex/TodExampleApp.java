package ex;

import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import net.sf.japi.swing.action.ActionBuilder;
import net.sf.japi.swing.action.ActionBuilderFactory;
import net.sf.japi.swing.tod.TipOfTheDayManager;

public class TodExampleApp {
    public static void main(final String... args) {
        //System.setProperty("net.sf.japi.swing.tod", "ex.tod");
        new PrefsExampleApp();
    }
    final JFrame frame;
    public TodExampleApp() {
        final ActionBuilder actionBuilder = ActionBuilderFactory.getInstance().getActionBuilder("ex");
        frame = new JFrame(actionBuilder.getString("frame.title"));
        frame.add(new JButton(actionBuilder.createAction(false, "showTod", this)));
        frame.pack();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        TipOfTheDayManager.showAtStartup(frame);
    }
    public void showTod() {
        TipOfTheDayManager.show(frame);
    }
}
