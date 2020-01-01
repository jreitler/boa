package com.reitler.boa.pdfgen.ui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.reitler.boa.core.api.Constants;
import com.reitler.boa.core.interfaces.ISongList;
import com.reitler.boa.pdfgen.PdfGenerationParameter;
import com.reitler.boa.pdfgen.PdfGenerator;

public class PdfGeneratorDialog extends JDialog {

	private static final long serialVersionUID = -6747616930902253892L;
	private static final String PDF_FILE_EXTENSION = ".pdf";

	private JButton okButton;
	private JButton cancelButton;

	private File selectedFile;
	private int sortOrder = 0; // 0 = bypage, 1 = bytitle
	private JCheckBox includePages;

	private final ISongList list;
	private JCheckBox splitTable;

	public PdfGeneratorDialog(final ISongList list) {
		this.list = list;
	}

	public void showDialog() {

		this.selectedFile = new File(
				"." + File.separator + this.list.getName() + PdfGeneratorDialog.PDF_FILE_EXTENSION);
		setLayout(new BorderLayout());

		JPanel contentPanel = createContentPanel();
		JPanel buttonPanel = createButtonPanel();

		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.AFTER_LAST_LINE);

		setMinimumSize(new Dimension(350, 200));
		setSize(350, 150);
		setBounds(320, 320, getWidth(), getHeight());
		setTitle(Constants.getGeneratePdfCaption());
		pack();
		setVisible(true);
	}

	private JPanel createContentPanel() {
		JPanel filePathPanel = createFilePathPanel();
		JPanel checkBoxPanel = createOptionsPanel();

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());

		GridBagConstraints contentConstraints = new GridBagConstraints();

		contentConstraints.fill = GridBagConstraints.BOTH;
		contentConstraints.weightx = 1;
		contentConstraints.gridx = 0;
		contentConstraints.gridy = 0;

		contentPanel.add(filePathPanel, contentConstraints);

		contentConstraints.gridy = 1;
		contentPanel.add(checkBoxPanel, contentConstraints);
		return contentPanel;
	}

	private JPanel createOptionsPanel() {
		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new GridBagLayout());
		checkBoxPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(Constants.getOptions()), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.gridheight = 1;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weighty = 1;
		constraints.weightx = 1;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.ipadx = 200;

		this.includePages = new JCheckBox(Constants.getIncludePages());
		this.includePages.setSelected(true);
		constraints.gridy = 0;
		checkBoxPanel.add(this.includePages, constraints);

		this.splitTable = new JCheckBox(Constants.getSplitTable());
		this.splitTable.setSelected(true);
		constraints.gridy = 1;
		checkBoxPanel.add(this.splitTable, constraints);

		JComboBox<String> orderComboBox = new JComboBox<>();
		orderComboBox.addItem(Constants.getSortByPage());
		orderComboBox.addItem(Constants.getSortByTitle());
		constraints.gridy = 2;

		orderComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent e) {
				PdfGeneratorDialog.this.sortOrder = orderComboBox.getSelectedIndex();
			}
		});

		checkBoxPanel.add(orderComboBox, constraints);

		return checkBoxPanel;
	}

	private JPanel createButtonPanel() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (e.getSource().equals(PdfGeneratorDialog.this.okButton)) {
					EventQueue.invokeLater(() -> generatePdf());
					setVisible(false);
				} else if (e.getSource().equals(PdfGeneratorDialog.this.cancelButton)) {
					setVisible(false);
				}
			}
		};

		this.okButton = new JButton(Constants.getButtonOk());
		this.cancelButton = new JButton(Constants.getButtonCancel());

		this.okButton.addActionListener(listener);
		this.cancelButton.addActionListener(listener);

		JPanel buttonPanel = new JPanel();

		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(this.okButton);
		buttonPanel.add(this.cancelButton);
		return buttonPanel;
	}

	private JPanel createFilePathPanel() {
		JPanel filePathPanel = new JPanel();
		filePathPanel.setLayout(new GridBagLayout());
		filePathPanel.setBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Constants.getOutputFile()),
						BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		GridBagConstraints fileConstraints = new GridBagConstraints();
		fileConstraints.gridx = 0;
		fileConstraints.gridy = 0;

		JTextField filePath = new JTextField(this.selectedFile.getAbsolutePath());
		fileConstraints.fill = GridBagConstraints.BOTH;
		fileConstraints.weightx = 0.3;
		filePathPanel.add(filePath, fileConstraints);

		JButton fileSelect = new JButton("...");
		fileConstraints.weightx = 0.0;
		fileConstraints.gridx = 1;
		filePathPanel.add(fileSelect, fileConstraints);
		fileSelect.addActionListener(e -> {

			selectFile();
			filePath.setText(this.selectedFile.getAbsolutePath());
		});
		return filePathPanel;
	}

	private void selectFile() {
		JFileChooser chooser = new JFileChooser(this.selectedFile.getParent());
		chooser.setDialogTitle(Constants.getGeneratePdfCaption());
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setFileFilter(new FileNameExtensionFilter(Constants.getPdfFileType(), "pdf"));
		chooser.setSelectedFile(this.selectedFile);

		int result = chooser.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			this.selectedFile = chooser.getSelectedFile();
		}
	}

	private void generatePdf() {
		PdfGenerationParameter pdfGenerationParameter = new PdfGenerationParameter();
		pdfGenerationParameter.setTargetFile(this.selectedFile);
		pdfGenerationParameter.setList(this.list);
		pdfGenerationParameter.setSortOrder(this.sortOrder);
		pdfGenerationParameter.setIncludePages(this.includePages.isSelected());
		pdfGenerationParameter.setSplitTable(this.splitTable.isSelected());

		PdfGenerator generator = new PdfGenerator();
		generator.generate(pdfGenerationParameter);

		if (this.selectedFile.exists()) {
			try {
				Desktop.getDesktop().open(this.selectedFile);
			} catch (IOException exception) {
				Logger.getLogger(PdfGeneratorDialog.class.getCanonicalName()).log(Level.WARNING,
						"Could not open file: " + this.selectedFile.getAbsolutePath(), exception);
			}
		}
	}

}
