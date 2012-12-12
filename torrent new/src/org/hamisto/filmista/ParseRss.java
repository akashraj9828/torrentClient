package org.hamisto.filmista;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParseRss {

	private static DocumentBuilder documentBuilder;

	
	private static DocumentBuilder getDocumentBuilder() {

		if(documentBuilder == null) {
			try {
				documentBuilder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return documentBuilder;
	}



	public ParseRss(){

		DocumentBuilder db=getDocumentBuilder();
		try {

			Document doc;// ottengo un file tipo documento vuoto
			String requestUrl= "http://www.tntvillage.scambioetico.org/rss.php?c=29&p=20";
			doc = db.parse(requestUrl);// ottengo tramite il metodo parse sempre
			// un file di tipo document ma in questo caso nn vuoto come prima
			// ma costituente la radice del file XML
			doc.getDocumentElement().normalize();
			NodeList titleList = doc.getElementsByTagName("title");
			NodeList enclosureNode = doc.getElementsByTagName("enclosure");
			//NodeList overviewNode = doc.getElementsByTagName("Overview");
			//bannerNode=doc.getElementsByTagName()
			
			for(int i=0;i<enclosureNode.getLength();i++){
				String title = titleList.item(i+1).getTextContent();
				String urlTorrent = enclosureNode.item(i).getAttributes().getNamedItem("url").getTextContent();
				System.out.println(title + "\n"+ urlTorrent + "\n\n");
				
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
