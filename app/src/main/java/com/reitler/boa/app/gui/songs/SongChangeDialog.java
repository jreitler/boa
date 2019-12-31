package com.reitler.boa.app.gui.songs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.reitler.boa.core.api.Constants;
import com.reitler.boa.core.interfaces.ISong;
import com.reitler.boa.core.interfaces.factory.SongCreationParameter;

public class SongChangeDialog extends JDialog {

	private static final long serialVersionUID = -6924059195262743542L;
	private final JPanel centerPanel = new JPanel();
	private final JPanel buttonPanel = new JPanel();
	private final GridBagConstraints constraint = new GridBagConstraints();

	private final JTextField title = new JTextField();
	private final JTextField artist = new JTextField();
	private final JTextField publisher = new JTextField();
	private final JTextField tags = new JTextField();
	private final JButton okButton = new JButton(Constants.getButtonOk());
	private final JButton cancelButton = new JButton(Constants.getButtonCancel());

	private SongCreationParameter songParameter;

	public SongChangeDialog(final ISong parameter) {
		super();
		if (parameter != null) {
			this.title.setText(parameter.getTitle());
			this.artist.setText(parameter.getArtist());
			this.publisher.setText(parameter.getPublisher());
			this.tags.setText(String.join(" ", parameter.getTags()));
		}
		showDialog();
	}

	public SongChangeDialog() {
		this(null);
	}

	private void initializeParameter() {
		this.songParameter = new SongCreationParameter();
		this.songParameter.title = SongChangeDialog.this.title.getText();
		this.songParameter.artist = SongChangeDialog.this.artist.getText();
		this.songParameter.publisher = SongChangeDialog.this.publisher.getText();
		this.songParameter.tags = Arrays.asList(SongChangeDialog.this.tags.getText().split(" "));
	}

	public void showDialog() {

		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (e.getSource() == SongChangeDialog.this.okButton) {
					initializeParameter();
					setVisible(false);
				}
				if (e.getSource().equals(SongChangeDialog.this.cancelButton)) {
					setVisible(false);
				}

			}
		};

		super.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.centerPanel.setLayout(new GridBagLayout());
		this.buttonPanel.setLayout(new BorderLayout());
		this.constraint.fill = GridBagConstraints.BOTH;

		this.constraint.weightx = 0.1;
		this.constraint.gridx = 0;
		this.constraint.gridy = 0;
		this.constraint.anchor = GridBagConstraints.WEST;
		this.centerPanel.add(new JLabel(Constants.getSongTitleCaption()), this.constraint);
		this.constraint.gridy = 1;
		this.centerPanel.add(new JLabel(Constants.getSongArtistCaption()), this.constraint);
		this.constraint.gridy = 2;
		this.centerPanel.add(new JLabel(Constants.getSongPublisherCaption()), this.constraint);
		this.constraint.gridy = 3;
		this.centerPanel.add(new JLabel(Constants.getSongTagsCaption()), this.constraint);

		this.constraint.weightx = 1.0;
		this.constraint.gridx = 1;
		this.constraint.gridy = 0;
		this.constraint.anchor = GridBagConstraints.EAST;
		this.centerPanel.add(this.title, this.constraint);
		this.constraint.gridy = 1;
		this.centerPanel.add(this.artist, this.constraint);
		this.constraint.gridy = 2;
		this.centerPanel.add(this.publisher, this.constraint);
		this.constraint.gridy = 3;
		this.centerPanel.add(this.tags, this.constraint);

		super.add(this.centerPanel, BorderLayout.CENTER);

		this.okButton.addActionListener(listener);
		this.cancelButton.addActionListener(listener);

		this.buttonPanel.setLayout(new FlowLayout());
		this.buttonPanel.add(this.okButton);
		this.buttonPanel.add(this.cancelButton);

		super.add(this.buttonPanel, BorderLayout.SOUTH);

		setMinimumSize(new Dimension(350, 200));
		setSize(350, 150);
		pack();

	}

	public SongCreationParameter getParameter() {
		return this.songParameter;
	}

}
