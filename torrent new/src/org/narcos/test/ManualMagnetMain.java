package org.narcos.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import ca.benow.transmission.TransmissionClient;
import ca.benow.transmission.TransmissionException;
import ca.benow.transmission.model.AddedTorrentInfo;

public class ManualMagnetMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TransmissionClient client = new TransmissionClient("localhost",9091);
			client.startTorrents();

			//Inizializzazione variabili x caricamento torrent
			String downloadDir="C:\\Users\\Manuel Mantovani\\Download\\torrent\\";
			String result1="magnet:?xt=urn:btih:5ce75059fefea05fb507fc9957ae5e2f03abe22c&dn=Dexter+2012+S07E11+HDTV+x264+Sub_Ita-%5BiDN_CreW%5D&tr=udp%3A%2F%2Ftracker.istole.it%2F";
			//String downloadDir="C:\\torrent";
			//String torrentFileNameOrURL=result1;

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
				System.out.println(newTorrentInfo);
				client.startTorrents();
			}
			catch(TransmissionException te){
				System.out.println("ERRORE: TORRENT GIA' INSERITO");
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
