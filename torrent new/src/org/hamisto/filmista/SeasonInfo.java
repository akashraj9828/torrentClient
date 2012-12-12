package org.hamisto.filmista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


public class SeasonInfo extends JFrame implements HyperlinkListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String numbS;
	String id;
     public SeasonInfo(){
    	 
     }
     public SeasonInfo(String numbS,String id){
    	 this.id=id;
    	 this.numbS=numbS;
     }
	public void hyperlinkUpdate(HyperlinkEvent e)
	//con eventType.ACTIVATED genero un evento  che si attiva solo quando premo il JEditorPane
	{   if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
		//attento ad inserire l'url privo di spazi che vengono mal interpretati e generano un errore
	    String url="http://www.thetvdb.com/api/55D4BDC0A1305510/series/"+id+"/all/en.xml";
		CreateEpisodesDetail crd = new CreateEpisodesDetail();
		ArrayList<SingleEpisode> ase = new ArrayList<SingleEpisode>();
		ase=crd.episodesDetail(url,numbS);
		JTable table = null ;
	    String [] columnsName={"Episode Number","Episode Name","First Aired"};
	    JEditorPane jep=new JEditorPane("text/html","<a href> Previous Season </a></html>");
	    jep.setOpaque(false);
	    jep.setEditable(false);
	    JEditorPane jep1=new JEditorPane("text/html","<a href> Next Season </a></html>");
	    jep1.setOpaque(false);
	    jep1.setEditable(false);
	    JPanel p1= new JPanel();
	    FlowLayout flow = new FlowLayout();
	    flow.setAlignment(FlowLayout.RIGHT);
	    p1.setLayout(flow);
	    p1.add(jep);
	    p1.add(jep1);
		//imposto come panello del jframe di default un jpanel definito da me
	    String[][] data;
	    int k=0;
		data= new String [ase.size()][columnsName.length] ;
		for(int i=0;i<ase.size();i++){
		   for( k=0;k<columnsName.length;k++) { 
			//dove &nbsp viene utilizzato per lasciare degli spazi
			data[i][k]=ase.get(i).episodeNumber;
		     k++;
			data[i][k]=ase.get(i).episodeName;
			k++;
		    data[i][k]=ase.get(i).first_Aired;}
		    
		}
		 
		
		table=new JTable(data,columnsName);
		table.setGridColor(Color.LIGHT_GRAY);//setta colore griglia
		table.setOpaque(true);
		JScrollPane scroll=new JScrollPane(table);
		
		
    
    this.getContentPane().add(scroll,BorderLayout.CENTER);
    this.getContentPane().add(p1,BorderLayout.SOUTH);
    this.setSize(500,300);
	this.setVisible(true);
	}	
	}




}
