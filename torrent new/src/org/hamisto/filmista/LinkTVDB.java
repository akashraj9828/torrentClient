package org.hamisto.filmista;

import java.awt.Desktop;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;



public class LinkTVDB implements HyperlinkListener{ 
	String s;
	     public LinkTVDB(){
	    	 
	     }
	     public LinkTVDB(String s){
	    	 this.s=s;
	     }
		      @Override public void hyperlinkUpdate(HyperlinkEvent e) {
		    	  if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
		    	 String ris="http://thetvdb.com/?tab=series&id="+s;
				URI uri = null;
				try {
					uri = new URI(ris);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    if (Desktop.isDesktopSupported()) {
		      try {
		        Desktop.getDesktop().browse(uri);
		      } catch (IOException e1) { /*TODO: error handling */}
		    } else { /* TODO: error handling */}
		  }
		      }   
}
