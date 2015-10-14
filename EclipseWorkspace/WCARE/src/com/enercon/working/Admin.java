package com.enercon.working;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

public class Admin {
	public static void main(String[] argv) throws Exception {

		MyFileChooser chooser = new MyFileChooser();
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		
		final JDialog dialog = chooser.createDialog(null);
		chooser.addActionListener(new AbstractAction() {
			
			public void actionPerformed(ActionEvent evt) {
				
				JFileChooser chooser = (JFileChooser) evt.getSource();
				if (JFileChooser.APPROVE_SELECTION.equals(evt.getActionCommand())) {
					dialog.setVisible(false);
				} else if (JFileChooser.CANCEL_SELECTION.equals(evt.getActionCommand())) {
					dialog.setVisible(false);
				}
			}
		});

		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dialog.setVisible(false);
			}
		});

		dialog.setVisible(true);
	}
}

class MyFileChooser extends JFileChooser {
	public JDialog createDialog(Component parent) throws HeadlessException {
		return super.createDialog(parent);
	}
}