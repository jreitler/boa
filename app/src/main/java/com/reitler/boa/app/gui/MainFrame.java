package com.reitler.boa.app.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;
import java.util.ServiceLoader;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import com.reitler.boa.app.gui.songlists.SongListManagementContainer;
import com.reitler.boa.app.gui.songs.SongManagementContainer;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.ISongList;
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

		ISongList songList = mockSongList(songManager, songListManager);
		cards.add("Songs", new SongManagementContainer(songManager));
		cards.add("SongLists", new SongListManagementContainer(songListManager, songManager));

		contentPane.add(cards, BorderLayout.CENTER);

		setSize(500, 500);
		setVisible(true);
		setEnabled(true);

		// TODO: for debug purposes only
		addWindowListener(new WindowAdapter() {

			/**
			 * Invoked when a window is in the process of being closed. The close operation
			 * can be overridden at this point.
			 */
			@Override
			public void windowClosing(final WindowEvent e) {
				songList.getByPage().forEach(a -> System.out
						.println(String.format("page:%s title:%s", a.getPage(), a.getSong().getTitle())));
			}
		});

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

	private ISongList mockSongList(final ISongManager iSongManager, final ISongListManager songListManager) {

		ISong song1 = iSongManager.createSong("song1");
		ISong song2 = iSongManager.createSong("song2");
		ISong song3 = iSongManager.createSong("song3");

		Optional<ISongListManager> manager = ServiceLoader.load(ISongListManager.class).findFirst();
		ISongList songList = manager.get().createSongList("testList");

		songListManager.assign(song1, songList, "1");
		songListManager.assign(song2, songList, "2");
		songListManager.assign(song3, songList, "2a");

		return songList;
	}

}
