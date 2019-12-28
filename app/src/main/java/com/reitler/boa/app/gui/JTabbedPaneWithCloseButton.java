package com.reitler.boa.app.gui;

import java.awt.Component;

import javax.swing.JTabbedPane;

public class JTabbedPaneWithCloseButton extends JTabbedPane implements ITabbedParent {

	private static final long serialVersionUID = 9192503522392132704L;

	public JTabbedPaneWithCloseButton() {
		super();
	}

	@Override
	public void addTab(final String title, final Component component) {
		super.addTab(title, component);
		int count = this.getTabCount() - 1;
		setTabComponentAt(count, new ButtonTabComponent(this));
		setSelectedIndex(count);
		updateUI();
	}

	public void addTabNoClose(final String title, final Component component) {
		super.addTab(title, component);
		updateUI();
	}

}
