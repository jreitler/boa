package com.reitler.boa.pdfgen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.reitler.boa.core.interfaces.ISongAssignment;
import com.reitler.boa.core.interfaces.ISongList;

public class PdfGenerator {

	private PdfGenerationParameter parameter;

	public void generate(final PdfGenerationParameter param) {
		this.parameter = param;

		Document document = new Document();
		try (FileOutputStream stream = new FileOutputStream(param.getTargetFile())) {
			PdfWriter.getInstance(document, stream);
			document.open();

			Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 16, BaseColor.BLACK);
			ISongList list = this.parameter.getList();
			Chunk chunk = new Chunk(list.getName(), font);

			Paragraph para = new Paragraph(chunk);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setSpacingAfter(10);
			document.add(para);

			List<ISongAssignment> assignments = this.parameter.getSortOrder() == 0 ? list.getByPage()
					: list.getByTitle();

			if (this.parameter.isSplitTable()) {
				addSplitTable(document, assignments);
			} else {
				addSimpleTable(document, assignments);
			}

			document.close();
		} catch (DocumentException | IOException e) {
			Logger.getLogger(PdfGenerator.class.getCanonicalName()).log(Level.SEVERE, "Error generating the PDF", e);
		}

	}

	private void addSimpleTable(final Document document, final List<ISongAssignment> assignments)
			throws DocumentException {
		Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 9, BaseColor.BLACK);
		float[] widths = this.parameter.includePages() ? new float[] { 10, 90 } : new float[] { 100 };

		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(60);

		for (ISongAssignment assign : assignments) {
			if (this.parameter.includePages()) {
				table.addCell(new Phrase(assign.getPage(), font));
			}
			table.addCell(new Phrase(assign.getSong().getTitle(), font));
		}
		document.add(table);
	}

	private void addSplitTable(final Document document, final List<ISongAssignment> assignments)
			throws DocumentException {
		Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 9, BaseColor.BLACK);
		float[] widths = this.parameter.includePages() ? new float[] { 7, 43, 7, 43 } : new float[] { 50, 50 };

		int mid = assignments.size() / 2;
		if ((assignments.size() % 2) == 1) {
			mid++;
		}

		List<ISongAssignment> left = new LinkedList<>(assignments.subList(0, mid));
		List<ISongAssignment> right = new LinkedList<>(assignments.subList(mid, assignments.size()));

		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(90);

		for (int i = 0; i < mid; i++) {
			if (this.parameter.includePages()) {
				table.addCell(new Phrase(left.get(i).getPage(), font));
			}
			table.addCell(new Phrase(left.get(i).getSong().getTitle(), font));

			if (right.size() > i) {
				if (this.parameter.includePages()) {
					table.addCell(new Phrase(right.get(i).getPage(), font));
				}
				table.addCell(new Phrase(right.get(i).getSong().getTitle(), font));
			} else {
				if (this.parameter.includePages()) {
					table.addCell("");
				}
				table.addCell("");
			}
		}
		document.add(table);
	}

}
