package org.hamisto.filmista;
import java.io.IOException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class CreateDetailList {
	public SerieCompleta createDetailList(String requestUrl) {
		NodeList genreNode = null;
		NodeList ratingNode = null;
		NodeList posterNode=null;
		NodeList seriesNameNode=null;
		NodeList overviewNode=null;
		NodeList statusNode=null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc;//ottengo un file tipo documento vuoto
			doc = db.parse(requestUrl);//ottengo tramite il metodo parse sempre 
			//un file di tipo document ma in questo caso nn vuoto come prima 
			//ma costituente la radice del file XML
			doc.getDocumentElement().normalize();
			genreNode = doc.getElementsByTagName("Genre");
			ratingNode = doc.getElementsByTagName("Rating");
			seriesNameNode = doc.getElementsByTagName("SeriesName");
			posterNode=doc.getElementsByTagName("poster");
			overviewNode=doc.getElementsByTagName("Overview");
			statusNode=doc.getElementsByTagName("Status");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SerieCompleta sc = new SerieCompleta();//se lo posizionavo fuori funzione come attributo
		//me dava problemi nn faccievo un cazzo
		sc.setNome(seriesNameNode.item(0).getTextContent());
	    sc.setOverview(overviewNode.item(0).getTextContent());
		sc.setStatus(statusNode.item(0).getTextContent());
		sc.setPoster(posterNode.item(0).getTextContent());
		sc.setGenre(genreNode.item(0).getTextContent());
		sc.setRating(ratingNode.item(0).getTextContent());

return sc;
	}

}







//String name=" ";

//name=actorsNode.item(0).getTextContent();



//System.out.println(name);


