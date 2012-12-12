package test.ca.benow.transmission;

import java.net.URL;
import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;

import ca.benow.transmission.TransmissionClient;
import ca.benow.transmission.model.TorrentStatus;

public class Example {

  /**
   * @param args
   */
  public static void main(
      String[] args) {
    try {
      // initialize log4j
      DOMConfigurator.configure("etc/logging.xml");

      TransmissionClient client = new TransmissionClient(new URL("http://server:server@192.168.1.150:9091/transmission/rpc"));
      List<TorrentStatus> torrents = client.getAllTorrents();
      for (TorrentStatus curr : torrents)
        System.out.println(curr);

      System.exit(0);
    } catch (Throwable e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

}
