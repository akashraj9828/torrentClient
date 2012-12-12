package org.transmissionInterface;

import java.io.IOException;
import java.util.List;

import ca.benow.transmission.TransmissionClient;
import ca.benow.transmission.model.AddedTorrentInfo;
import ca.benow.transmission.model.TorrentStatus;
import ca.benow.transmission.model.TorrentStatus.TorrentField;



public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//Lancio demone transmission
			//Runtime.getRuntime().exec("torDaemon\\start.exe");
			Runtime.getRuntime().exec("torDaemon\\transmission-daemon.exe");
			System.out.println("Demone avviato...attendi\n");
			Thread.currentThread().sleep(15000);
			System.out.println("Attesa finita\n");
			
			//inizializzazione interfaccia transmission
			TransmissionClient client = new TransmissionClient("localhost",9091);
			
			//recupero lista completa torrent
			List<TorrentStatus> torrentlist=client.getAllTorrents();
			System.out.println(torrentlist.toString());
			
			//Recupero informazioni sui torrent (dimensione totale, nome file)
			TorrentField[] info = {TorrentField.totalSize, TorrentField.files};
			System.out.println(client.getTorrents(null, info).toString());
			
			//Inizializzazione variabili x caricamento torrent
			String downloadDir="C:\\Users\\Manuel Mantovani\\Downloads\\torrent";
			String torrentFileNameOrURL=downloadDir + "\\[isoHunt] PC-Need.For.Speed.Most.Wanted-FullDvD.ITALIANO-By.TXT-[TNTVillage.org]PC-Need.For.Speed.Most.Wanted-FullDvD.ITALIANO-By.TXT-[TNTVillage.org].torrent";
			boolean paused=false;
			int peerLimit=0;
			int bandwidthPriority=0;
			int[] filesWanted=null;
			int[] filesUnwanted=null;
			int[] priorityHigh=null;
			int[] priorityLow=null;
			int[] priorityNormal=null;
			//caricamento nuovo torrent
			Thread.currentThread().sleep(5000);
			AddedTorrentInfo newTorrentInfo = client.addTorrent(downloadDir, torrentFileNameOrURL, paused, peerLimit, bandwidthPriority, filesWanted, filesUnwanted, priorityHigh, priorityLow, priorityNormal);
			System.out.println(newTorrentInfo.toString());
			
			//rimozione torrent appena aggiunto
			Object ids[]=new Object[2];			//non capisco perchè devo mettere un array di due, con Object[1] non funziona
			ids[1]=(Object) (newTorrentInfo.getId());
			//newTorrentInfo.getId();
			Thread.currentThread().sleep(5000);
			client.removeTorrents(ids, true);
			
			Runtime.getRuntime().exec("torDaemon\\stop.exe");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
