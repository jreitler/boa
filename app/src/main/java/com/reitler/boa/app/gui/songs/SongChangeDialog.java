package com.reitler.boa.app.gui.songs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.reitler.boa.app.gui.UIConstants;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.factory.SongCreationParameter;

public class SongChangeDialog extends JDialog {

	private static final long serialVersionUID = -6924059195262743542L;
	private final JPanel myPanel = new JPanel();
	private final GridBagConstraints constraint = new GridBagConstraints();

	private final JTextField title = new JTextField();
	private final JTextField artist = new JTextField();
	private final JTextField publisher = new JTextField();
	private final JTextField tags = new JTextField();

	public SongChangeDialog(final ISong parameter) {
		super();
		if (parameter != null) {
			this.title.setText(parameter.getTitle());
			this.artist.setText(parameter.getArtist());
			this.publisher.setText(parameter.getPublisher());
			this.tags.setText(String.join(" ", parameter.getTags()));
		}
	}

	public SongChangeDialog() {
		this(null);
	}

	public SongCreationParameter showDialog() {
		this.myPanel.setLayout(new GridBagLayout());
		this.constraint.fill = GridBagConstraints.BOTH;

		this.constraint.weightx = 0.1;
		this.constraint.gridx = 0;
		this.constraint.gridy = 0;
		this.constraint.anchor = GridBagConstraints.WEST;
		this.myPanel.add(new JLabel(UIConstants.getSongTitleCaption()), this.constraint);
		this.constraint.gridy = 1;
		this.myPanel.add(new JLabel(UIConstants.getSongArtistCaption()), this.constraint);
		this.constraint.gridy = 2;
		this.myPanel.add(new JLabel(UIConstants.getSongPublisherCaption()), this.constraint);
		this.constraint.gridy = 3;
		this.myPanel.add(new JLabel(UIConstants.getSongTagsCaption()), this.constraint);

		this.constraint.weightx = 1.0;
		this.constraint.gridx = 1;
		this.constraint.gridy = 0;
		this.constraint.anchor = GridBagConstraints.EAST;
		this.myPanel.add(this.title, this.constraint);
		this.constraint.gridy = 1;
		this.myPanel.add(this.artist, this.constraint);
		this.constraint.gridy = 2;
		this.myPanel.add(this.publisher, this.constraint);
		this.constraint.gridy = 3;
		this.myPanel.add(this.tags, this.constraint);

		int result = JOptionPane.showConfirmDialog(null, this.myPanel, UIConstants.getChangeSongMessage(),
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			SongCreationParameter songParameter = new SongCreationParameter();
			songParameter.title = this.title.getText();
			songParameter.artist = this.artist.getText();
			songParameter.publisher = this.publisher.getText();
			songParameter.tags = Arrays.asList(this.tags.getText().split(" "));
			return songParameter;
		} else {
			return null;
		}

	}

}
