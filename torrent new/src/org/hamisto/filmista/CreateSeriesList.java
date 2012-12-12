package org.hamisto.filmista;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class CreateSeriesList {

	ArrayList<Serie> listaSerie = new ArrayList<Serie>();
	public ArrayList<Serie> createSeriesList(String seriesName) {
        
		String requestUrl="http://www.thetvdb.com/api/GetSeries.php?seriesname="+seriesName;
		NodeList idNode = null;
		NodeList nameNode = null;
		NodeList overviewNode = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc;//ottengo un file tipo documento vuoto
			doc = db.parse(requestUrl);//ottengo tramite il metodo parse sempre 
			//un file di tipo document ma in questo caso nn vuoto come prima 
			//ma costituente la radice del file XML
			doc.getDocumentElement().normalize();
			idNode = doc.getElementsByTagName("seriesid");
			nameNode = doc.getElementsByTagName("SeriesName");
			overviewNode = doc.getElementsByTagName("Overview");


		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(int i = 0; i <= idNode.getLength(); i++) {//stampa tutti nomi e descrizione

			if(idNode.item(i) != null && nameNode.item(i) != null && overviewNode.item(i) != null  ) {

				Serie s = new Serie();
				s.setId(idNode.item(i).getTextContent());
				s.setNome(nameNode.item(i).getTextContent());
				s.setOverview(overviewNode.item(i).getTextContent());
				String ris=s.shortOverview(s.getOverview());
				s.setOverview(ris);
				listaSerie.add(s);
			}
		}

		return listaSerie;

	}	
}
