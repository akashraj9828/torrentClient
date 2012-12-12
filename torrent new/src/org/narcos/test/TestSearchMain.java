package org.narcos.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.transmissionInterface.RicercaTorrent;

import ca.benow.transmission.TransmissionClient;
import ca.benow.transmission.TransmissionException;
import ca.benow.transmission.model.AddedTorrentInfo;

public class TestSearchMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			long startTime = System.currentTimeMillis();
			
			
			//Process process = Runtime.getRuntime().exec("torDaemon\\transmission-daemon.exe");
			TransmissionClient client = new TransmissionClient("localhost",9091);
			client.startTorrents();

			System.out.println("TEST PER LA RICERCA DI TORRENT DAL TITOLO\n\n");
			//Dati prova ricerca
			String titolo = "Lost";
			int stagione=04;
			int puntata=03;
			String lingua = "ENG";

			RicercaTorrent ricercaTorrent = new RicercaTorrent();
			for(int i=1;i<=5;i++){
				stagione=i;
			String result1 = ricercaTorrent.RicercaTorrent(titolo, stagione, lingua);
			//System.out.println("-----------------------------------------------------------------------------\n-----------------------------------------------------------------------------");
			//String result2 = ricercaTorrent.RicercaTorrent(titolo, stagione, puntata, lingua);

			if(result1!=null) {
				System.out.println("\n\nRISULTATO SERIE COMPLETA:" + result1.toString());
				
				//Inizializzazione variabili x caricamento torrent
				String downloadDir="C:\\Users\\Manuel Mantovani\\Download\\torrent\\"+titolo+stagione;
				//String downloadDir="C:\\torrent";
				String torrentFileNameOrURL=result1;
				
				InputStream metaInfo = new URL(result1).openStream();
				
				boolean paused=false;
				int peerLimit=0;
				int bandwidthPriority=0;
				int[] filesWanted=null;
				int[] filesUnwanted=null;
				int[] priorityHigh=null;
				int[] priorityLow=null;
				int[] priorityNormal=null;
				
				try {
				AddedTorrentInfo newTorrentInfo = client.addTorrent(downloadDir, metaInfo, paused, peerLimit, bandwidthPriority, filesWanted, filesUnwanted, priorityHigh, priorityLow, priorityNormal);
				//AddedTorrentInfo newTorrentInfo = client.addTorrent(downloadDir, torrentFileNameOrURL, paused, peerLimit, bandwidthPriority, filesWanted, filesUnwanted, priorityHigh, priorityLow, priorityNormal);
				System.out.println(newTorrentInfo);
				client.startTorrents();
				}
				catch(TransmissionException te){
					System.out.println("ERRORE: TORRENT GIA' INSERITO");
				}
			}
			
			else System.out.println("\n\nSERIE COMPLETA " + stagione + " NON TROVATA");
			}
			/*
			if(result2!=null) System.out.println("\n\nRISULTATO SINGOLA PUNTATA:" + result2.toString());
			else System.out.println("\n\nPUNTATA s" + stagione + "e" + puntata + " NON TROVATA");
			*/
			//kill client
			
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("\n\n\nTEMPO TOTALE: " + totalTime/1000 + " secondi");
			//process.destroy();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
