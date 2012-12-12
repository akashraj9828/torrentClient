package org.hamisto.filmista;
import java.io.IOException;
import java.util.ArrayList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
public class CreateEpisodesDetail {
	
	 ArrayList<SingleEpisode> es=new ArrayList<SingleEpisode>();
	public ArrayList<SingleEpisode> episodesDetail(String url,String numbS){//ricorda di modicafare passando un vettore 
		//di episodi singoli
	
			NodeList epi_nameNode = null;
			NodeList seasonNumbNode=null;
			NodeList epi_numberNode = null;
			NodeList first_airedNode=null;
			NodeList filenameNode=null;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			try {
				db = dbf.newDocumentBuilder();
				Document doc;
				doc = db.parse(url);
				doc.getDocumentElement().normalize();
				epi_nameNode= doc.getElementsByTagName("EpisodeName");
				seasonNumbNode=doc.getElementsByTagName("SeasonNumber");
				epi_numberNode = doc.getElementsByTagName("EpisodeNumber");
				first_airedNode = doc.getElementsByTagName("FirstAired");
			    filenameNode=doc.getElementsByTagName("filename");
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       
           for(int i=0;i <= seasonNumbNode.getLength() ;i++){
        	   if(seasonNumbNode.item(i)!=null && epi_nameNode.item(i)!=null && epi_numberNode.item(i)!=null && first_airedNode.item(i)!=null && filenameNode.item(i)!=null)
        	   {
        		   if(seasonNumbNode.item(i).getTextContent().equals(numbS)==true){
           SingleEpisode es1 = new SingleEpisode();
		es1.setEpisodeName(epi_nameNode.item(i).getTextContent());
		es1.setEpisodeNumber(epi_numberNode.item(i).getTextContent());
		es1.setFirst_Aired(first_airedNode.item(i).getTextContent());
		es1.setFilename(filenameNode.item(i).getTextContent());
		es.add(es1);
	
		}
        	   }
           }
		return es;
	}

}
