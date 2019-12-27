package com.reitler.boa.app.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
		setJMenuBar(createMenuBar());
		super.setLayout(new GridBagLayout());
		GridBagConstraints layoutContraints = new GridBagConstraints();
		
		JPanel contentPane = new JPanel();

		JTabbedPane cards = new JTabbedPane();

		cards.add("Songs", new SongManagementContainer(songManager));
		cards.add("SongLists", new SongListManagementContainer(songListManager, songManager));

		contentPane.add(cards, BorderLayout.CENTER);
		
		layoutContraints.insets = new Insets(10, 10, 10, 10);
		
		super.add(contentPane, layoutContraints);
		super.pack();
		setVisible(true);
		setEnabled(true);

	}

	private JMenuBar createMenuBar() {
		JMenuBar result = new JMenuBar();

		JMenu menu = new JMenu("test");
		JMenuItem item = new JMenuItem("item1");
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
