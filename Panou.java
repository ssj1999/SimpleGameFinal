package principal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import dale.GestionatorDale;
import entitati.Jucator;
import entitati.Premiu;

public class Panou extends JPanel implements Runnable{
	//ecranul jocului
	//final = devin constante, nu le pot modifica nimeni in interiorul codului
	final int marimeOriginalaDale = 10;
	final int scala = 5;
	public final int marimeaDale = marimeOriginalaDale*scala;
	public final int maxEcranColoane = 12;
	public final int maxEcranRanduri = 12;
	public final int latimeEcran = marimeaDale*maxEcranColoane;
	public final int inaltimeEcran = marimeaDale*maxEcranRanduri;
	
	//configurarea valorilor lumii jocului
	public final int maxLumeColoane = 50;
	public final int maxLumeRanduri = 50;
	public final int latimeaLumii = marimeaDale*maxLumeColoane;
	public final int inaltimeaLumii = marimeaDale*maxLumeRanduri;
	
	//ceas, ceasul final
	public int ceas = 0, ceasFinal = 0;
	public Cronometru cronometru = new Cronometru(this);
	
	//valorile x si y ale premiului
	
	public final int premiuX=40*marimeaDale;
	public final int premiuY=2*marimeaDale;
	
	//obiect premiu/banana
	public Premiu premiu = new Premiu(this);
	
	//cream obiect pentru a gestiona dalele
	GestionatorDale gestiuneDale = new GestionatorDale(this);
	GestionatorTaste gestiuneTaste = new GestionatorTaste();
	
	int FPS=60; //frames per second sau cadre pe secunda
	Thread firulJocului;
	public VerificColiziune verificatorColiziune = new VerificColiziune(this);
	
	public Jucator jucatorul = new Jucator(this,gestiuneTaste);
	public Panou() {
		//setez dimensiunile panoului
		this.setPreferredSize(new Dimension(latimeEcran,inaltimeEcran));
		//(Color.culoareaDorita) sau (new Color(valoareptrosu,valoareptverde,valoareptalbastru));
		this.setBackground(Color.pink);
		//ajuta cu timpul ca imaginile pe care le obtin sa nu se vada urat
		this.setDoubleBuffered(true);
		this.addKeyListener(gestiuneTaste);
		//setez focusul pe aceast panou
		this.setFocusable(true);
	}
	//declar firul jocului
	public void pornesteFirulJocului() {
		//this = clasa in care suntem
		firulJocului=new Thread(this);
		//pornesc
		firulJocului.start();
	}
	
	@Override
	public void run() {
		double intervalDesenare = 1000000000/FPS;
		double urmatorulMomentDesenare = System.nanoTime()+intervalDesenare;
		//cat timp firul jocului e pornit:
		while(firulJocului != null) {
			update();
			repaint();
			try {
				double timpulRamas = urmatorulMomentDesenare-System.nanoTime();
				timpulRamas=timpulRamas/1000000;
				//ca sa nu avem valori negative:
				if(timpulRamas<0) {
					timpulRamas=0;
				}
				//am trnsformat in long mai intai
				//firul de executie se va opri de fiecare data cat timp e timpul ramas
				Thread.sleep((long) timpulRamas);
				urmatorulMomentDesenare=urmatorulMomentDesenare+intervalDesenare;
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//declar ca sa pot desena cercul,patratul (in 2D):
		Graphics2D g2 = (Graphics2D) g;
		gestiuneDale.deseneaza(g2);
		if(premiu.verificaPremiu(jucatorul)==true) {
			premiu.deseneaza(g2);
		}
		jucatorul.deseneaza(g2);
		//chemam aceasta clasa pt ca elibereaza resursele memoriei:
		g2.dispose();
	}
	
	public void update() {
		jucatorul.update();
	}

}
