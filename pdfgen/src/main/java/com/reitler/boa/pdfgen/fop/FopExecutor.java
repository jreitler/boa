package com.reitler.boa.pdfgen.fop;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.System.Logger.Level;
import java.util.Date;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;

public class FopExecutor {

	public boolean generate(final InputStream stream, final File file) {
		FopFactory factory = FopFactory.newInstance(new File(".").toURI());
		FOUserAgent agent = factory.newFOUserAgent();
		agent.setAuthor(System.getProperty("user.name"));
		agent.setCreator(System.getProperty("user.name"));
		agent.setCreationDate(new Date());
		agent.setProducer("BOA SongList generator");

		try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
			Fop fop = factory.newFop(MimeConstants.MIME_PDF, agent, out);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			StreamSource source = new StreamSource(stream);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.transform(source, new SAXResult(fop.getDefaultHandler()));
		} catch (Exception e) {
			System.getLogger(FopExecutor.class.getCanonicalName()).log(Level.ERROR, "Error while generating PDF", e);
			return false;
		}
		return true;
	}
}
