package com.reitler.boa.app.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import com.reitler.boa.app.gui.songlists.SongListManagementContainer;
import com.reitler.boa.app.gui.songs.SongManagementContainer;
import com.reitler.boa.core.interfaces.ISongListManager;
import com.reitler.boa.core.interfaces.ISongManager;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -2599442231433868789L;

	public MainFrame(final ISongManager songManager, final ISongListManager songListManager) {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setMenuBar(createMenuBar());
		Container contentPane = getContentPane();

		contentPane.add(new Label(""), BorderLayout.PAGE_START);
		contentPane.add(new Label(""), BorderLayout.PAGE_END);
		contentPane.add(new Label(""), BorderLayout.LINE_START);
		contentPane.add(new Label(""), BorderLayout.LINE_END);

		JTabbedPane cards = new JTabbedPane();

		cards.add("Songs", new SongManagementContainer(songManager));
		cards.add("SongLists", new SongListManagementContainer(songListManager, songManager));

		contentPane.add(cards, BorderLayout.CENTER);

		setSize(500, 500);
		setVisible(true);
		setEnabled(true);

	}

	private MenuBar createMenuBar() {
		MenuBar result = new MenuBar();

		Menu menu = new Menu("test");
		MenuItem item = new MenuItem("item1");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				JOptionPane.showMessageDialog(getParent(), "item1 pressed");
			}
		});
		menu.add(item);
		result.add(menu);

		return result;
	}

}
