package org.transmissionInterface;

import java.net.URL;
import java.util.List;

import org.narcos.test.WebSearch;
import org.transdroid.search.SearchResult;

public class RicercaTorrent {

	public String RicercaTorrent(String titoloSerie, int numStagione, int numPuntata, String lingua) {
		//Ricerca singola puntata
		List<SearchResult> listResult;
		String url = null;
		//costruzione query ricerca
		String stagione = String.format("%02d", numStagione);
		String puntata = String.format("%02d", numPuntata);
		String titolo = this.convertSpaces(titoloSerie);
		System.out.println("TITOLO:"+titolo);
		String query = titolo + " s" + stagione + "e" + puntata;
		//String query = titoloSerie + " s" + numStagione + "e" + numPuntata;
		if(lingua!="ENG") query += " " + lingua;

		//Ricerca rss 

		//if(/*Risultato non trovato*/) //Ricerca web

		//.........................................
		System.out.println("\n\nQUERY = " + query);
		WebSearch webSearch= new WebSearch(); 
		//url = webSearch.WebSearch(query);
		listResult = webSearch.WebSearch(query);

		System.out.println("\n\nQUERY = " + query);

		//if(url == null) {
		if (listResult.isEmpty()){
			if (lingua=="ITA") query=titoloSerie + " stagione " + numStagione + " episodio " + numPuntata + " " + lingua;
			else query=titoloSerie + " season " + numStagione + " episode " + numPuntata;
			System.out.println("\n\nQUERY = " + query);
			//url = webSearch.WebSearch(query);
			listResult = webSearch.WebSearch(query);
		}


		return url;
	}

	public String RicercaTorrent(String titoloSerie, int numStagione, String lingua) {
		//Ricerca singola stagione completa
		List<SearchResult> listResult;
		String url = null;
		//costruzione query ricerca
		String stagione = String.format("%02d", numStagione);
		String titolo = this.convertSpaces(titoloSerie);
		//System.out.println("TITOLO:"+titolo);

		String query = titolo + " s" + stagione;
		if(lingua == "ITA") query += " " + lingua;

		//Ricerca rss 

		//if(/*Risultato non trovato*/) //Ricerca web

		//.........................................
		System.out.println("QUERY = " + query);
		//Ricerca web
		WebSearch webSearch= new WebSearch(); 
		//url = webSearch.WebSearch(query);
		listResult = webSearch.WebSearch(query);

		//if(url == null) {
		if (listResult.isEmpty()){
			if (lingua=="ITA") query=titoloSerie + " stagione " + stagione + " " + lingua;
			else query=titolo + " season " + stagione;
			//url = webSearch.WebSearch(query);
			listResult = webSearch.WebSearch(query);
		}
		if (! listResult.isEmpty()){
			int i=0;
			SearchResult listItem;
			while((listItem=listResult.get(i)) != null){
				if (this.ResultControl(listItem, titoloSerie, stagione))  return url=listItem.getTorrentUrl();
				i++;
			}
		}
		return url;
	}

	public boolean ResultControl(SearchResult result, String titolo, String stagione){
		//Metodo utilizzato per controllare la coerenza del torrent trovato con ciò che si vuole trovare (titolo, dimensioni,...)
		System.out.println("\n\nINIZIO CONTROLLO COERENZA");
		System.out.println("TITOLO RISULTATO:" + result.getTitle());

		if (	(
				result.getTitle().contains(titolo) ||
				result.getTitle().contains(titolo.toUpperCase()) ||
				result.getTitle().contains(titolo.toLowerCase()) ||
				result.getTitle().contains(this.convertSpaces(titolo)) ||
				result.getTitle().contains(this.convertSpaces(titolo).toLowerCase()) ||
				result.getTitle().contains(this.convertSpaces(titolo).toUpperCase())
				) 
				&&
				(
						result.getTitle().contains("complete") || 
						result.getTitle().contains("Complete") ||
						result.getTitle().contains("COMPLETE") ||
						(result.getTitle().contains("season") && !result.getTitle().contains("episode")) ||
						(result.getTitle().contains("Season") && !result.getTitle().contains("Episode")) ||
						(result.getTitle().contains("SEASON") && !result.getTitle().contains("EPISODE")) ||
						result.getTitle().contains("full") ||
						result.getTitle().contains("Full") ||
						result.getTitle().contains("FULL") ||
						!result.getTitle().contains("s%de%d")||
						!result.getTitle().contains("S%de%d")||
						!result.getTitle().contains("s%d%de%d%d")||
						!result.getTitle().contains("S%d%de%d%d")
						)
				)System.out.println ("Controllo titolo stagione completa superato");
		else {
			System.out.println ("Controllo titolo stagione completa FALLITO");
			return false;
		}
		//check size
		if (result.getSize().contains("KB") || 
				(result.getSize().contains("MiB") && (Integer.parseInt(result.getSize().substring(0, result.getSize().length() - 8) ) < 800) ) ) {
			System.out.println("Controllo dimensioni FALLITO");
			return false;
		}
		else System.out.println("Controllo dimensioni superato");
		//cerca numero stagione
		if (result.getTitle().contains(stagione)){
			System.out.println ("Controllo numero stagione completa superato");
			//return true;
		}
		else {
			System.out.println ("Controllo numero stagione completa FALLITO");
			return false;
		}
		//controllo lingua
		String[] lingue={"Francais","French","German","Deutche","Russian","Italian","Italiano","Spanish","Espanol","Español","Portuguese","Português"};
		String[] lingueShort={"FR","FRA","GER","DEU","DE","RU","RUS","ITA","IT","SPA","POR","PT"};
		for(int i=0;i<lingue.length-1;i++) {
			if(result.getTitle().contains(lingue[i]) || result.getTitle().contains(lingue[i].toLowerCase()) || result.getTitle().contains(lingue[i].toUpperCase())){
				System.out.println("Controllo lingua FALLITO");
				return false;
			}
		}
		for(int i=0;i<lingueShort.length-1;i++)
			if(result.getTitle().contains(lingueShort[i])){
				System.out.println("Controllo lingua FALLITO");
				return false;
			}
		System.out.println("Controllo lingua superato");
		//controlla elenco puntate

		return true;



		//else System.out.println ("Controllo stagione completa non passato");
		//System.out.println("\n\n");

		//return false;
	}

	private String convertSpaces(String string){
		String string1;
		//System.out.println(string);
		CharSequence space = " ";
		CharSequence point = ".";
		string1 = string.replace(space,point);
		//System.out.println(string1);

		return string1;
	}
}
