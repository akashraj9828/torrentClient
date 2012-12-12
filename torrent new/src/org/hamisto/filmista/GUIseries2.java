package org.hamisto.filmista;
import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Insets;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class GUIseries2 extends JFrame implements ActionListener,ListSelectionListener,KeyListener{
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	ArrayList<Serie> lista = new ArrayList<Serie>();
	private JTextArea ta;
	private JPanel p;
	private JScrollPane scroll;
	private JList l;
	private JPanel p1;
	private JTextField t;
	DefaultListModel modello=new DefaultListModel();
	public GUIseries2() {
          super("Series TV");


		/*URL url = null;
		try {//con questo try/catch mi procuro l'immagine dall'url specificato
			url = new URL("http://www.thetvdb.com//banners/_cache/posters/70366-1.jpg");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		//label=new JLabel(scale(vv.getImage(),0.4));
		//b.addActionListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		new JLabel();
		p1=new JPanel();
		BorderLayout layout = new BorderLayout();
		p=new JPanel();
		p.setLayout(layout);
		t=new JTextField(30);
		t.addKeyListener(this);
		l=new JList(modello);


		ta = new JTextArea();
		ta.setOpaque(false);
		ta.setLineWrap(true);
		ta.setWrapStyleWord (true); 
		ta.setEditable (false);
		ta.setDisabledTextColor(null);
		
		
		
		
		t.addActionListener(this);
        l.addListSelectionListener(this);

		p1.setLayout(null);
		//ta.setBorder(BorderFactory.createEtchedBorder(Color.BLACK,Color.BLACK));
		
		
		scroll=new JScrollPane(l);
		p1.add(scroll);
      
         




		Insets insets = p1.getInsets();
		
		
		Dimension size = scroll.getPreferredSize();
		scroll.setBounds( insets.left, 10+ insets.top, size.width-100, size.height+300);





		p.add(t,BorderLayout.NORTH);
		p.add(p1,BorderLayout.CENTER);
		this.add(p);
		this.setSize(600,500); this.setVisible(true);
	}

	public void keyPressed(KeyEvent arg0) {


	}

	public void keyReleased(KeyEvent arg0) {


	}

	public void keyTyped(KeyEvent e) {
		/*String url="http://www.thetvdb.com/api/GetSeries.php?seriesname="+t.getText();
		CreateSeriesList cs=new CreateSeriesList();
		ArrayList<Serie> lista = cs.createSeriesList(url);
		for(int i=0; i<lista.size(); i++) {
	             
           modello.addElement(lista.get(i).getNome());
			

		}*/
         
	}

	public void  ValueChanged(ListSelectionEvent e) {
		
		JButton b=new JButton("More...");
		b.add(p1);
		
		
		/*p1.remove(label);
		p1.remove(b);
		p1.remove(ta);
		 Serie s = new Serie();
		
		for(int i=0;i<lista.size();i++){
		if(lista.get(i).getNome().equals(modello.get(l.getSelectedIndex())));
			{
				s=lista.get(i);
			    b.addActionListener(new MoreInfo(s,b,this));
		 //label.setText("gasggdgs");
		 ta.setText("caifoadfoafsafasf");
		
		 
				 Insets insets = p1.getInsets();
				p1.add(b);
				 p1.add(ta);
				p1.add(label);
				 
				 
				 
			    Dimension size3 = b.getPreferredSize();
				b.setBounds( 480+insets.left, 130+ insets.top,size3.width, size3.height);
		
				
				

				Dimension size1 = label.getPreferredSize();
				label.setBounds(230 + insets.left, 10 + insets.top, size1.width, size1.height);


				Dimension size2 = ta.getPreferredSize();
				ta.setBounds(370+ insets.left, 10 + insets.top, size2.width+100, size2.height+100);

					
			     //ta.setText(s.getOverview());
			     
				//p.add(p1,BorderLayout.CENTER);
				//this.add(p);

				
			}
		}
			p1.updateUI();*/
	
	}
	
		



	public void actionPerformed(ActionEvent arg0) {
		modello.removeAllElements();
	if(arg0.getSource()==this.t){
		String url="http://www.thetvdb.com/api/GetSeries.php?seriesname="+t.getText();
		CreateSeriesList cs=new CreateSeriesList();
		ArrayList<Serie> lista = cs.createSeriesList(url);
		for(int i=0; i<lista.size(); i++) {
	             
           modello.addElement(lista.get(i).getNome());
			

	}
		}
	

	}


	public void valueChanged(ListSelectionEvent e) {
		
		
	}

}


	/*	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b){
			modello.addElement("ciaodsafasfsafafsgfdasdasdasdasdasdasdasdasfasdasfasfasfasfas");}
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		ta.setText("fafsasadasd");
		ta.updateUI();
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyReleased(KeyEvent arg0) {


	}
	
	public void keyTyped(KeyEvent arg0) {
		String url="http://www.thetvdb.com/api/GetSeries.php?seriesname="+t.getText();
		CreateSeriesList cs=new CreateSeriesList();
		ArrayList<Serie> lista = cs.createSeriesList(url);
		for(int i=0; i<lista.size(); i++) {
	
           modello.addElement(lista.get(i).getNome());
			

		}


	} 


	private ImageIcon scale(Image src, double scale) {
		int w = (int)(scale*src.getWidth(this));
		int h = (int)(scale*src.getHeight(this));
		int type = BufferedImage.TYPE_INT_RGB;
		BufferedImage dst = new BufferedImage(w, h, type);
		Graphics2D g2 = dst.createGraphics();
		g2.drawImage(src, 0, 0, w, h, this);
		g2.dispose();
		return new ImageIcon(dst);
	}*/












