package com.reitler.boa.app.gui;

import java.awt.Component;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class JTabbedPaneWithCloseButton extends JTabbedPane implements ITabbedParent {

	private static final long serialVersionUID = 9192503522392132704L;

	public JTabbedPaneWithCloseButton() {
		super();
	}

	public void addTabNoClose(final String title, final Component component) {
		super.addTab(title, component);
		updateUI();
	}

	@Override
	public void addTab(final Component c, final JTextField l) {
		super.addTab(l.getText(), c);
		int count = this.getTabCount() - 1;
		setTabComponentAt(count, new ButtonTabComponent(this, l));
		updateUI();
		java.awt.EventQueue.invokeLater(() -> setSelectedIndex(count));
	}
}
