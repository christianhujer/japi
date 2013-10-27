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

package net.sf.japi.progs.batcher;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import net.sf.japi.swing.misc.JFileField;
import org.jetbrains.annotations.NotNull;

/** View for a Job.
 * @author <a href="mailto:cher@riedquat.de">Christian Hujer</a>
 * @since 0.1
 */
public class JobView extends JComponent implements ActionListener {

    /** The Job of this JobView.
     * @serial include
     */
    private Job job;

    /** The textfield for the title.
     * @serial include
     */
    private JTextField titleField;

    /** The filefield for the current working directory.
     * @serial include
     */
    private JFileField currentWorkingDirectoryField;

    /** The view of the current JobRun. */
    private JobRunView out;

    /** Creates a JobView.
     * @param job Job to view.
     */
    public JobView(@NotNull final Job job) {
        this.job = job;

        //setLayout(new GridBagLayout());
        setLayout(new java.awt.FlowLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        titleField = new JTextField(20);
        currentWorkingDirectoryField = new JFileField("", "" + job.getCurrentWorkingDirectory(), JFileChooser.DIRECTORIES_ONLY);

        add(new JScrollPane(new JList(job.getCommand())), gbc);

        add(new JLabel("XXX title"), gbc);
        add(titleField, gbc);

        add(new JLabel("XXX wd"), gbc);
        add(currentWorkingDirectoryField, gbc);

        final JButton start = new JButton("run");
        start.addActionListener(this);
        add(start, gbc);

        out = new JobRunView();
        add(out, gbc);
    }

    public void actionPerformed(@NotNull final ActionEvent e) {
        System.out.println("Run");
        new JobRun(out, job).execute();
    }
}
