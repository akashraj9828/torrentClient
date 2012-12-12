package org.narcos.test;

//import java.net.URL;
import java.net.URL;
import java.util.List;

import org.transdroid.search.SearchResult;
import org.transdroid.search.SortOrder;
import org.transdroid.search.Isohunt.IsohuntAdapter;
import org.transdroid.search.ThePirateBay.ThePirateBayAdapter;

public class WebSearch {

	/**
	 * @param args
	 */
	public /*String*/List WebSearch(String query) {
		SearchResult result = null;
		ThePirateBayAdapter adapterPirate = new ThePirateBayAdapter();
		IsohuntAdapter adapterHunt = new IsohuntAdapter();
		List<SearchResult> listPirate = null;
		List<SearchResult> listHunt;

		try {
			//query="LOST";
			listPirate = adapterPirate.search(query, SortOrder.BySeeders, 10);
			//List<SearchResult> list = adapterPirate.search(query, SortOrder.Combined, 10);
			
			
			System.out.println("\n\nLISTA PIRATEBAY\n");
		
			for(SearchResult r : listPirate){
				System.out.println("\n");
				System.out.println("\n\nTitle: " + r.getTitle());
				System.out.println("Seeds: " + r.getSeeds());
				System.out.println("URL: " + r.getTorrentUrl());
				System.out.println("Size: " + r.getSize());
				System.out.println("Details: " + r.getDetailsUrl());
				String id = r.getDetailsUrl();
				id=id.substring(32, 39);
				System.out.println(id);
				
				String fileListURL="http://thepiratebay.se/ajax_details_filelist.php?id=" + id;
				
			}
			
			listHunt = adapterHunt.search(query, SortOrder.BySeeders, 1);
			//List<SearchResult> listHunt = adapterHunt.search(query, SortOrder.Combined, 10);
			
			System.out.println("\n\nLISTA ISOHUNT\n");
			for(SearchResult r : listHunt){
				System.out.println("\n");
				System.out.println("\n\nTitle:" + r.getTitle());
				System.out.println("Seeds:" + r.getSeeds());
				System.out.println("URL:" + r.getTorrentUrl());
				System.out.println("Size: " + r.getSize());
				//System.out.println(r.)
			}
			
			SearchResult resultPirate;
			SearchResult SearchResult = null;
			if (!(listPirate.isEmpty())) resultPirate = listPirate.get(0);
			//else return null;
			else resultPirate = SearchResult;
			SearchResult resultHunt;
			if(!(listHunt.isEmpty())) resultHunt = listHunt.get(0);
			//else return null;
			else resultHunt=SearchResult;
			
			if(resultHunt!=null) System.out.println("Result Hunt:" + resultHunt.toString());

			if(resultPirate!=null)System.out.println("Result Pirate:" + resultPirate.toString());
			
			if (resultPirate==null) { 
				if (resultHunt==null) return null; 
				else result=resultHunt;
			}
			else if ( (resultPirate.getSeeds() > resultHunt.getSeeds()) || resultHunt==null) result=resultPirate;
			else result=resultHunt;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return listPirate;
		//if (this.ResultControl(result)) return result.getTorrentUrl();
		//else return null;
		//return result.getTorrentUrl();
	}

	
	/*
	
	public boolean ResultControl(SearchResult result){
		//Metodo utilizzato per controllare la coerenza del torrent trovato con ciò che si vuole trovare (titolo, dimensioni,...)
		System.out.println("\n\nINIZIO CONTROLLO COERENZA");
		System.out.println("TITOLO RISULTATO:" + result.getTitle());
		
		if (	(
					result.getTitle().contains("Lost") ||
					result.getTitle().contains("LOST") ||
					result.getTitle().contains("lost") 
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
					result.getTitle().contains("FULL") 
					)
				){
			System.out.println ("Controllo titolo stagione completa superato");
			
			//check size
			//cerca numero stagione
			//controlla elenco puntate
			return true;
		}
		
		else System.out.println ("Controllo stagione completa non passato");
		System.out.println("\n\n");
		
		return false;
	}*/
}
