package entitati;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

import principal.GestionatorTaste;
import principal.Panou;
import sunet.Sunet;

public class Jucator extends Entitate{

	Panou panouJoc;
	GestionatorTaste gestiuneTaste;
	//ma refer doar la ecran, lumea(ceea ce apare si cand ma misc cu jucatorul) fiind si mai mare
	public final int ecranX;
	public final int ecranY;
	public Sunet fondSonor = new Sunet("/sunete/sunetulBun.wav");
	public Sunet sunetPremiu = new Sunet("/sunete/premiu.wav");
	public Sunet sunetPierdut = new Sunet("/sunete/gameOver.wav");
	
	String sus1,sus2,jos1,jos2,stanga1,stanga2,dreapta1,dreapta2,personajul;
	
	public Jucator(Panou panou,GestionatorTaste gestiuneTaste) {
		this.panouJoc=panou;
		this.gestiuneTaste=gestiuneTaste;
		//pun jucatorul la mijlocul ecranului:
		ecranX=panou.latimeEcran/2 - (panou.marimeaDale/2);
		
		ecranY=panou.inaltimeEcran/2 - (panou.marimeaDale/2);
		
		zonaSolida=new Rectangle();
		zonaSolida.x=12;
		zonaSolida.y=12;
		zonaSolida.width=14;
		zonaSolida.height=14;
		
		configuramValorileInitiale();
		obtinemImagineaJucatorului();
	}
	
	private void obtinemImagineaJucatorului() {
		try {
			personajul="jucator";
			sus1="/jucator/"+personajul+"Sus1.png";
			sus2="/jucator/"+personajul+"Sus2.png";
			jos1="/jucator/"+personajul+"Jos1.png";
			jos2="/jucator/"+personajul+"Jos2.png";
			dreapta1="/jucator/"+personajul+"Dreapta1.png";
			dreapta2="/jucator/"+personajul+"Dreapta2.png";
			stanga1="/jucator/"+personajul+"Stanga1.png";
			stanga2="/jucator/"+personajul+"Stanga2.png";
			jucatorSus1=ImageIO.read(getClass().getResourceAsStream(sus1));
			jucatorSus2=ImageIO.read(getClass().getResourceAsStream(sus2));
			jucatorJos1=ImageIO.read(getClass().getResourceAsStream(jos1));
			jucatorJos2=ImageIO.read(getClass().getResourceAsStream(jos2));
			jucatorStanga1=ImageIO.read(getClass().getResourceAsStream(stanga1));
			jucatorStanga2=ImageIO.read(getClass().getResourceAsStream(stanga2));
			jucatorDreapta1=ImageIO.read(getClass().getResourceAsStream(dreapta1));
			jucatorDreapta2=ImageIO.read(getClass().getResourceAsStream(dreapta2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//configuram parametrii cu care incepe jocul
	private void configuramValorileInitiale() {
		worldX=panouJoc.marimeaDale*10;
		worldY=panouJoc.marimeaDale*6;
		viteza=4;
		directie ="dreapta";
		
	}
	public void deseneaza(Graphics2D g2) {
		
		BufferedImage image = null;
		switch(directie) {
			case "sus":
				if(numarImagine==1) {
					image=jucatorSus1;
				}
				if(numarImagine==2) {
					image=jucatorSus2;
				}
				break;
			case "jos":
				if(numarImagine==1) {
					image=jucatorJos1;
				}
				if(numarImagine==2) {
					image=jucatorJos2;
				}
				break;
			case "stanga":
				if(numarImagine==1) {
					image=jucatorStanga1;
				}
				if(numarImagine==2) {
					image=jucatorStanga2;
				}
				break;
			case "dreapta":
				if(numarImagine==1) {
					image=jucatorDreapta1;
				}
				if(numarImagine==2) {
					image=jucatorDreapta2;
				}
				break;
		}
		g2.setColor(Color.white);
		g2.setFont(new Font("TimesRoman",Font.BOLD,40));
		if(panouJoc.premiu.verificaPremiu(this)) {
			g2.drawString("Timp:"+this.panouJoc.ceas+" sec.",5,40);
		}else{
			g2.drawString("Timp final:"+this.panouJoc.ceasFinal+" sec.",5,40);
			g2.setColor(Color.YELLOW);
			g2.setFont(new Font("TimesRoman",Font.BOLD,70));
			g2.drawString("VICTORIE !",90,270);
		}
		if(this.panouJoc.premiu.timpDa==false && this.panouJoc.premiu.inViata==true) {
			g2.setColor(Color.GREEN);
			g2.setFont(new Font("TimesRoman",Font.BOLD,70));
			g2.drawString("GAME OVER !",90,270);
			this.viteza=0;
			this.fondSonor.clip.close();
			sunetPierdut.clip.start();
		}
		g2.drawImage(image, ecranX, ecranY, panouJoc.marimeaDale, panouJoc.marimeaDale, null);
		this.fondSonor.clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void update() {
		
		if(gestiuneTaste.susApasat==true || gestiuneTaste.josApasat==true || gestiuneTaste.stangaApasat==true || gestiuneTaste.dreaptaApasat==true) {
			//x -> 1940/1996
			//y -> 88/120
			System.out.println("worldX:"+worldX);
			System.out.println("worldY:"+worldY);
			if(panouJoc.premiu.verificaPremiu(this)==false && panouJoc.ceasFinal==0) {
				fondSonor.clip.close();
				sunetPremiu.clip.start();
				panouJoc.ceasFinal=panouJoc.ceas;
				System.out.println("Ai ajuns la banana!");
			}
			if(gestiuneTaste.susApasat) {
				directie="sus";
			}else if(gestiuneTaste.josApasat) {
				directie="jos";
			}else if(gestiuneTaste.stangaApasat) {
				directie="stanga";
			}else if(gestiuneTaste.dreaptaApasat) {
				directie="dreapta";
			}
			
			coliziunePornita=false;
			panouJoc.verificatorColiziune.verificDala(this);
			
			if(coliziunePornita==false) {
				switch(directie) {
				case"sus":
					worldY=worldY-viteza;
					break;
				case"jos":
					worldY=worldY+viteza;
					break;
				case"stanga":
					worldX=worldX-viteza;
					break;
				case"dreapta":
					worldX=worldX+viteza;
					break;
				}
			}
			numaratorImagine++;
			if(numaratorImagine>10) {
				if(numarImagine==1) {
					numarImagine=2;
				}else if(numarImagine==2) {
					numarImagine=1;
				}
				numaratorImagine=0;
			}
		}
	}

}
