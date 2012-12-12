package org.hamisto.filmista;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;


import java.net.MalformedURLException;

import java.net.URL;
public class MoreInfo implements ActionListener {
	Serie s = new Serie();
	JButton b = new JButton();
	JFrame f = new JFrame();
	public MoreInfo(){

	}
	public MoreInfo(Serie s,JButton b,JFrame f){
		this.b=b;
		this.s=s;
		this.f=f;
	}
	//"+s.getId()+"
	public void actionPerformed(ActionEvent e) {
		JDialog dialog = null;
        JEditorPane jep=new JEditorPane ("text/html","<a href><font Color=White>LinkTVDB</font></a></html>");
    	jep.setEditable(false);
		jep.setOpaque(false);
        jep.addHyperlinkListener(new LinkTVDB(s.getId()));
		//il jdialog assume il comportamento di un frame con l'eccezione
		//che il jdialog pu˜ essere usato per stabilire un predominio della finestra di dialogo
		//ripetto a quella da cui dipende.Ci sono tre modalitˆ di uso per un jdialog
		dialog = new JDialog( f, "More Information", Dialog.ModalityType.DOCUMENT_MODAL);
		JPanel p = new JPanel();
		JTextArea t = new JTextArea();
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1,BoxLayout.PAGE_AXIS));
		p1.setPreferredSize(null);
		String url = new String("http://www.thetvdb.com/api/55D4BDC0A1305510/series/"+s.getId()+"/en.xml");
		CreateDetailList cd=new CreateDetailList();
		SerieCompleta sc = new SerieCompleta();
		sc=cd.createDetailList(url);
		java.net.URL where = null;//occorre inizializzarla
		try {//con questo try/catch mi procuro l'immagine dall'url specificato
			where = new URL("http://thetvdb.com/banners/_cache/"+sc.getPoster());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ImageIcon image = new ImageIcon(where);
		
		//ImageIcon im1=new ImageIcon("images/star_gold_16.png");***********************
		JLabel l=new JLabel("<html><b>Name:</b>"+sc.getNome()+
				"<br><b>Genre:</b>"+sc.getGenre()+"<br><b>Rating:</b>"+sc.getRating()+
				"<br><b>Status:</b>"+sc.getStatus());
		
		//setta colore della label
		l.setForeground(Color.WHITE);
		//JLabel l1=new JLabel("<html><b>Network:</b>"+sc.getNetwork()+"</html>");
		//JLabel l2=new JLabel("<html><b>Orario onda:</b>"+sc.getAir_time()+"</html>");
		JLabel l3 = new JLabel(image);
		t.setText(sc.getOverview());
		//setto font e colore del testo della TextArea
		//Font font = new Font("Italic", Font.HANGING_BASELINE, 12);
		//t.setFont(font);
		t.setForeground(Color.WHITE);
		t.setOpaque(false);//adeguo il colore della mia TextArea al mio frame
		t.setLineWrap(true);//mi permette di andare a capo settando il valore a true
		t.setWrapStyleWord (true); // a capo in base alle parole
		t.setEditable (false);//rende la JTextField nn modificabile
		t.setDisabledTextColor(null);
		p.setSize(200,200);
		p1.add(t);
		//p1.add(jep);//**************************
		p1.setBackground(Color.BLACK);
		//setto il bordo e il titolo del panelo
		TitledBorder border = new TitledBorder(
                new LineBorder(Color.WHITE),
                "Description",
                TitledBorder.LEFT,
                TitledBorder.DEFAULT_POSITION);
		border.setTitleColor(Color.WHITE);
		p1.setBorder(border);
        
		//3 passi per dar vita a un GridBagLayout
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints lim = new GridBagConstraints();
		p.setLayout(layout);
		lim.insets.top = 10;
		lim.insets.bottom = 10;
		lim.insets.left = 10;
		lim.insets.right =10;
		lim.fill = GridBagConstraints.BOTH;
		//setto le posizioni dei componenti del GridBag
		//JScrollPane scroll=new JScrollPane(p);************************************
//		scroll.getVerticalScrollBar().setVisible(false);
		lim.gridx = 0;
		lim.gridy = 0;
	
		lim.gridwidth = 2;
		lim.gridheight = 1;
		lim.weightx = 0;
		lim.weighty = 0;
		lim.anchor = GridBagConstraints.NORTH;
		layout.setConstraints(l, lim);;
		p.add(l);
		lim.gridx = 0;
		lim.gridy = 1;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		lim.weightx = 1;
		lim.weighty = 1;
		layout.setConstraints(l3, lim);
		p.add(l3);
		lim.gridx = 1;
		lim.gridy = 1;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		lim.weightx = 1;
		lim.weighty = 0;
		lim.insets.top = 20;//alineo titolo descrizione con l'immagine
		lim.fill = GridBagConstraints.HORIZONTAL;//in questo modo i bordi si settano il base
		//al contenuto che ho da riempire e nn hanno quindi una dimensione fissa
		layout.setConstraints(p1, lim);
		p.add(p1);
		p.setBackground(Color.BLACK);//setto colore panelli
		//scroll.add(p);
	    // p.add(jep);***********************
		//dialog.add(scroll);
		dialog.setResizable(false);
	    dialog.add(p);
		dialog.setSize(1000,600);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);



	}

}