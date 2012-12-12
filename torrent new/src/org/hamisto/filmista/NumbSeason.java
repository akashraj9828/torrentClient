package org.hamisto.filmista;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//algoritmo per calcolare le stagioni associate ad una serie televisiva
public class NumbSeason {

	private static DocumentBuilder documentBuilder;

	private DocumentBuilder getDocumentBuilder() {
		if (documentBuilder == null) {
			try {
				this.documentBuilder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return documentBuilder;
	}

	public ArrayList<String> numbSeason(String requestUrl) {
	
		ArrayList<String> numb = new ArrayList<String>();
		
		URL u;
		try {
			u = new URL(requestUrl);
			InputStream is = u.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			final String SEASON_NUMBER = "<SeasonNumber>";
			final int SEASON_NUMBER_LENGHT = SEASON_NUMBER.length();
			String preNum = null;
			final String ZERO = "0";
			while ((line = br.readLine()) != null) {
				int index = line.indexOf(SEASON_NUMBER);
				if (index < 0) {// not found
					continue;
				}
				int sNumber = index + SEASON_NUMBER_LENGHT;
				String number = line.substring(sNumber,
						line.indexOf("<", sNumber - 1));

				if (number.equals(ZERO)) {
					continue;
				}
				if (preNum != null && preNum.equals(number)) {// discard same sequential number
					continue;
				}
				preNum = number;

				numb.add(number);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return numb;

	}
}
