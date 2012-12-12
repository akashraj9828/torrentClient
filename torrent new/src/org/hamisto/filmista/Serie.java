package org.hamisto.filmista;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import QuickPool.ThreadPool;

public class Serie {
	
	
	private static class SerieWorker extends SwingWorker<List<Serie>,Object>{
		
		private String serieName;
		private SerieWorkerListener listener;
		
        public SerieWorker(String serieName, SerieWorkerListener listener) {
        	this.serieName=serieName;
        	this.listener = listener;
		}
		@Override
		protected List<Serie> doInBackground() throws Exception {
			List<Serie> lista=Serie.createSeriesList(serieName);
			return lista;
		}
		
		@Override
		protected void done() {
			super.done();
			try {
				listener.WorkerDone(get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
    
	public static void CreateSeriesListWorker(String text, SerieWorkerListener listener) {
		new SerieWorker(text, listener).execute();
    }
	private static DocumentBuilder documentBuilder;
	
	//dichiarazione threadpool per gestire thread
	private static ExecutorService executorPool ;
	
	private static DocumentBuilder getDocumentBuilder() {
		if(documentBuilder == null) {
			try {
				documentBuilder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return documentBuilder;
	}
	
	//get per il thread
	private static ExecutorService getThreadPool(){
		if(executorPool==null){
			executorPool = Executors.newFixedThreadPool(15);
		}
		
		return executorPool;
		
	}
	
	
	
	 //metodo per ridimensionare  il banner
    private ImageIcon scale(Image src, double scale) {
        int w = (int)(scale*src.getWidth(this.poster));
        int h = (int)(scale*src.getHeight(this.poster));
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage dst = new BufferedImage(w, h, type);
        Graphics2D g2 = dst.createGraphics();
        g2.drawImage(src, 0, 0, w, h, this.poster);
        g2.dispose();
        return new ImageIcon(dst);
    }
    
    
	public static List<Serie> createSeriesList(String seriesName) {
		long startTime = System.currentTimeMillis();
		
		Integer activeThreadCount = new Integer(0);
		ArrayList<Serie> listaSerie = new ArrayList<Serie>();

		String requestUrl = "http://www.thetvdb.com/api/GetSeries.php?seriesname="
				+ seriesName;
		NodeList idNodeList = null;
		NodeList nameNode = null;
		NodeList overviewNode = null;
		//banner
		NodeList bannerNode=null;
		
		DocumentBuilder db=getDocumentBuilder();
		try {
			
			Document doc;// ottengo un file tipo documento vuoto
			doc = db.parse(requestUrl);// ottengo tramite il metodo parse sempre
			// un file di tipo document ma in questo caso nn vuoto come prima
			// ma costituente la radice del file XML
			doc.getDocumentElement().normalize();
			idNodeList = doc.getElementsByTagName("seriesid");
			nameNode = doc.getElementsByTagName("SeriesName");
			overviewNode = doc.getElementsByTagName("Overview");
			//bannerNode=doc.getElementsByTagName()

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		//salvo in una variabile il threadpool
		ExecutorService executorPool = getThreadPool();
		
		
		
		List<Callable<Object>> tasks = new ArrayList<Callable<Object>>(); 
		for (int i = 0; i <= idNodeList.getLength(); i++) {// stampa tutti nomi
															// e descrizione

			if (idNodeList.item(i) != null && nameNode.item(i) != null
					&& overviewNode.item(i) != null) {

				final Serie s = new Serie();
				s.setId(idNodeList.item(i).getTextContent());
				s.setNome(nameNode.item(i).getTextContent());
				s.setOverview(overviewNode.item(i).getTextContent());
				//s.loadBanner();
				
				//creo il job e gli dico cosa fare tramiteil metodo runnable
				Runnable job = new Runnable() {
					@Override
					public void run() {
						
						s.loadStagioni();
						
					}
				};
				tasks.add(Executors.callable(job));
				Runnable job1 = new Runnable() {
					@Override
					public void run() {
						s.loadBanner();
					
					}
				};
				tasks.add(Executors.callable(job1));
				
				listaSerie.add(s);
				
			}
		}
		
        try {
			executorPool.invokeAll(tasks);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
        
		return listaSerie;

	}

	private String id;
	private String nome;
	private String overview;
	private String shortOverview;
	private List<Stagione> stagioni;
	//banner
	private JLabel poster;

	public Serie() {
		this.stagioni = new LinkedList<Stagione>();
	}

	public Serie(String id) {// con questo costruttore lavoro sugli oggetti
		// serie tramite solo l'id
		this.id = id;
	}

	// algoritmo per spezzare il contenuto di una descrizione
	public String getShortOverview() {
		if(this.shortOverview == null){
			this.shortOverview = createShortOverview();
		}
		return this.shortOverview;
	}
	
	public void setShortOverview(String shortOverview) {
		this.shortOverview = shortOverview;
	}
	
	private String createShortOverview(){
		int i = 151;
		String ris = new String();
		ris = " ";
		char spazio = ' ';// se nell'if al posto della variabile metto
							// direttamente lo spazio mi da errore
		char term = overview.charAt(overview.length() - 1);// in java nn esiste
															// il caratere
															// terminatore /0
		if (overview.isEmpty() == true) {
			return overview;
		}
		if ((overview.isEmpty() != true) && (overview.length() > 150)) {
			if (overview.charAt(150) == spazio)
				;
			{
				ris = overview.substring(0, 149);// copia in ris overview
													// dall'indice 0 al
													// trentesimo
				ris.concat("......");// concateno stringhe
			}
			if (overview.charAt(150) != spazio) {
				while ((overview.charAt(i) != spazio)
						&& overview.charAt(i) != term) {
					i++;
				}
			}
			if (overview.charAt(i) != term) {
				ris = overview.substring(0, i);
				ris = ris.concat("......");
			}

			if (overview.charAt(i) == term) {
				int k = overview.length();
				k = k - 1;
				while (overview.charAt(k) != spazio) {
					k--;
				}
				ris = overview.substring(0, k);
				ris = ris.concat("......");
				return ris;
			}
		} else if (overview.length() <= 150) {
			return overview;
		}

		return ris;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public List<Stagione> getStagioni() {
		return stagioni;
	}

	
	public JLabel getPoster() {
		return poster;
	}

    
	//banners->nn devi salvarlo...
	
    public JLabel loadBanner(){

		poster=new JLabel();
		URL u1 = null;
		ImageIcon image = null;
		
		try {
			
			u1 = new URL("http://www.thetvdb.com/banners/_cache/posters/"+this.getId()+"-1.jpg");
			
             
			    }
		catch (MalformedURLException e) {
			
		    e.printStackTrace();
			
		} 
		//setto immagine poster
		try {
			if(u1.openConnection().getContentLength() > 0){
				image = new ImageIcon(u1);
				System.out.println(u1);}
			
			else
				image = new ImageIcon("images/Imagenotfound_v2.png");
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		image = scale(image.getImage(),0.45);
	    this.poster.setIcon(image);
	    
	    //setto bordo poster
	    Border bevel;
	    bevel = new BevelBorder(BevelBorder.RAISED);
	    this.poster.setBorder(bevel); 
	    
	    return this.poster;
    }
	


	public List<Stagione> loadStagioni() {
	     
		//nn posso creare un istanza di un interfaccia
		this.stagioni=new ArrayList<Stagione>();
	
		URL u;
		try {
			u = new URL("http://www.thetvdb.com/api/55D4BDC0A1305510/series/"+this.getId()
				+"/all/en.xml");
			InputStream is = u.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			final String SEASON_NUMBER = "<SeasonNumber>";
			final int SEASON_NUMBER_LENGHT = SEASON_NUMBER.length();
			String preNum = null;
			final String ZERO = "0";
			while ((line = br.readLine()) != null) {
				int index = line.indexOf(SEASON_NUMBER);
				if (index < 0) {// not found
					continue;
				}
				int sNumber = index + SEASON_NUMBER_LENGHT;
				String number = line.substring(sNumber,
						line.indexOf("<", sNumber - 1));

				if (number.equals(ZERO)) {
					continue;
				}
				if (preNum != null && preNum.equals(number)) {// discard same sequential number
					continue;
				}
				preNum = number;
                
				Stagione st1=new Stagione(number);
				
				this.stagioni.add(st1);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return this.stagioni;

	}

}
