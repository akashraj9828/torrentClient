package org.hamisto.filmista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class GUIseries extends JFrame implements ActionListener {

	/**
	 * 
	 */

	private static final long serialVersionUID = -387998709815643706L;
	JPanel flow = new JPanel();
	JPanel results = new JPanel();
	JPanel results1 = new JPanel();

	ImageIcon im = new ImageIcon("images/Search-icon.png");
	JButton button = new JButton("Search", im);
	JTextField text = new JTextField(15);
	// progress bar
	JProgressBar progressBar = new JProgressBar();

	JScrollPane scroll = new JScrollPane(results1);
    
	public GUIseries() {
		super("serieTV");

		// molto importante!!!...setta la JTextField stile macusers
		text.putClientProperty("JTextField.variant", "search");

		progressBar.setIndeterminate(true);
		progressBar.putClientProperty("JProgressBar.style", "circular");

		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// inserisco quindi a partire da sinistra un textfield e un bottone
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);// di default
															// posiziono gli
															// elementi a
															// partire
		// da sinistra
		
		//scroll.setLayout(layout);
		setSize(1000, 700);
		flow.setLayout(layout);
		flow.add(text);
		
		// progressBar

		// bottone Search
		// flow.add(button);

		results.setLayout(new BoxLayout(results, BoxLayout.PAGE_AXIS));// posiziono
																		// in
																		// verticale
																		// i
																		// componenti
		results.setBorder(BorderFactory.createEtchedBorder(
				EtchedBorder.LOWERED, Color.black, Color.black));

		results1.setLayout(new BoxLayout(results1, BoxLayout.PAGE_AXIS));

		button.addActionListener(this);
		text.addActionListener(this);

		// progressBar.setVisible(false);

		// flow.add(progressBar);

		results1.add(flow);
		// results1.add(results);

		this.add(scroll);
		// this.setLayout(new BorderLayout());

		// scrollPane.createVerticalScrollBar();

		// this.add(box,BorderLayout.NORTH);
		// this.add(scrollPane,BorderLayout.CENTER);

	}

	public void actionPerformed(ActionEvent e) {
		// EpisodiSingoli es=new EpisodiSingoli();
		if (e.getSource() == this.button || e.getSource() == this.text) {
			results.removeAll();
			results1.removeAll();
            
			flow.add(progressBar);
			System.out.println("ciao miki è uno stronzo");
			flow.updateUI();
			results.updateUI();
			results1.updateUI();
			results.add(flow);
            results1.add(flow);
			// results.removeAll();//pulisco il panello e i miei arrayList//mi
			// cancella la ricerca
			// String
			// url="http://www.thetvdb.com/api/GetSeries.php?seriesname="+text.getText();
			// CreateSeriesList cs=new CreateSeriesList();
			// NumbSeason ns = new NumbSeason();

			// TODO: visualizare caricamento
			Serie.CreateSeriesListWorker(text.getText(),
					new SerieWorkerListener() {
						@Override
						public void WorkerDone(List<Serie> series) {
							// TODO: rimuovere indicatore caricameto
							
							flow.remove(progressBar);
							flow.updateUI();
							results.updateUI();
							results1.updateUI();
							// TODO: visualizare lista serie
							showSeriesList(series);

						}
					});
			// ***************

		}

	}

	private void showSeriesList(List<Serie> series) {

		// progressBar.setVisible(false);
		results.setLayout(new BoxLayout(results, BoxLayout.Y_AXIS));// li mette
																	// in
																	// verticale
		// i compenenti

		int listSize = series.size();

		JPanel test = null;

		for (int i = 0; i < listSize; i++) {

			System.out.println(i + "...");

			List<Stagione> listaSeason = series.get(i).getStagioni();

			// JPanel flow1 = new JPanel();

			JPanel flow2 = new JPanel();// *********
			BorderLayout border = new BorderLayout(10, 0);

			flow2.setLayout(border);
			flow2.setBackground(Color.WHITE);
			Border compound;
			Border raisedbevel;
			Border loweredbevel;
			raisedbevel = BorderFactory.createRaisedBevelBorder();
			loweredbevel = BorderFactory.createLoweredBevelBorder();
			compound = BorderFactory.createCompoundBorder(raisedbevel,
					loweredbevel);

			flow2.setBorder(compound);
			FlowLayout layout1 = new FlowLayout(FlowLayout.LEFT);
			// flow1.setLayout(layout1);

			// flow1.add(lista.get(i).getPoster());

			JLabel l = new JLabel();

			JButton b = new JButton("More...");
			b.setToolTipText("Click here for more information");
			b.addActionListener(new MoreInfo(series.get(i), b, this));
			JTextArea t = new JTextArea(series.get(i).getOverview());
			t.setOpaque(false);// setta il colore della textarea uguale a quello
								// del panello
			t.setLineWrap(true);// va a capo in relazione alle larghezza del
								// frame
			t.setWrapStyleWord(true); // a capo in base alle parole
			t.setEditable(false); // non modificabile

			l.setText(series.get(i).getNome());
			l.setFont(new Font("Serif", Font.BOLD, 30));

			FlowLayout layout2 = new FlowLayout(FlowLayout.LEFT);
			JPanel pSeason = new JPanel();
			pSeason.setLayout(layout2);
			JLabel l1 = new JLabel("<html><b>Season:</b></html>");

			pSeason.add(l1);
			for (int k = 0; k < listaSeason.size(); k++) {
				JEditorPane jep = new JEditorPane("text/html", "<a href>"
						+ listaSeason.get(k) + "</a></html>");
				jep.setOpaque(false);
				jep.addHyperlinkListener(new SeasonInfo(listaSeason.get(k)
						.toString(), series.get(i).getId()));
				// questo codice html fa comparire con l'uso di un jeditor pane
				// la manina
				pSeason.add(jep);
				jep.setEditable(false);

			}
			listaSeason.clear();// rimuovo la lista cosi nn scrive le stagione
			// anche dei telefim trovati precedentemente

			// aggiungere poster e titolo

			// l.setAlignmentX(1.0f);

			// t.setAlignmentX(0.25f);

			flow2.setMaximumSize(new Dimension(40000000, 450));
			flow2.add(series.get(i).getPoster(), BorderLayout.WEST);

			flow2.add(t, BorderLayout.CENTER);
			flow2.add(l, BorderLayout.NORTH);

			// results1.add(flow1);//riaggiungo la barra di ricerca in quanto
			// dopo la ricerca
			// me lo cancella ma cosi facendo la scritta precedente viene
			// sottolineata
			// cosi da poter esssere cancellata semplicemente con canc
			// results.add(flow2);
			// results.add(pSeason);
			// results.add(t);
			results.add(flow2);
			test = flow2;

			// results.add(flow2);
			results1.add(results);

			this.add(scroll);
			listaSeason.removeAll(listaSeason);
		}
		flow.updateUI();
		results.updateUI();
		results1.updateUI();
		System.out.println("fatto");

		System.out.println(test.getMaximumSize());
	}

}
// updateUI() serve per aggiornare il jframe una volta che io rinfresco gli
// elementi presenti al suo interno
// setContentPane ��� usato per settare il panello di default del frame
// quindi una volta che vado a creare il mio panello dove ho contenuto tutti i
// miei oggetti
// uso il setContentPane e inserisco tale panello.

// setColor serve per settare lo sfondo del colore del jpanel oltre che di un
// generico frame
// per settare un contenitore come raccoglitore degli eventi viene usato
// l'action listener che risulta
// essere un interfaccia
// una jlist risulta essere una lista che contiene vari elementi di vario tipo e
// quindi possono essere utili
// quando ho una lista di vari elementi meglio se sono dello stesso tipo
// in quesyto esempio ho usato un layout che setta le dimensioni o meglio dire
// con il quale io setto le dimensioni automaticamente

// request("http://www.thetvdb.com/api/GetSeries.php?seriesname="+titolo,"listaserie");
// request("http://www.thetvdb.com/api/55D4BDC0A1305510/series/"+id+"/it.xml","dettaglioserie");