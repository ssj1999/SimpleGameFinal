package entitati;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entitate {
	//coordonatele pe ox si oy a lumii:
	public int worldX,worldY;
	//viteza cu care se va misca jucatorul
	public int viteza;
	public String directie;
	
	//permite sa continem o imagine
	public  BufferedImage jucatorSus1,jucatorSus2,
	jucatorJos1,jucatorJos2,jucatorStanga1,
	jucatorStanga2,jucatorDreapta1,jucatorDreapta2,generica;
	
	//numaram la ce imagine suntem pt a pune o imagine sau alta
	public int numaratorImagine = 0;
	public int numarImagine = 1;
	
	//rectangla=facem zona solida un patrat
	public Rectangle zonaSolida;
	public boolean coliziunePornita = false;
}
