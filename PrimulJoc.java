package principal;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class PrimulJoc {

	public static void main(String[] args) {
		JFrame fereastra = new JFrame();
		fereastra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fereastra.setResizable(false);
		//setez titlul ferestrei,altfel va lua numele JFrame-ului:
		fereastra.setTitle("Primul joc");
		
		Panou panoulJocului=new Panou();
		
		fereastra.add(panoulJocului);
		//deseneaza fereastra:
		fereastra.pack();
		//am spus ferestrei sa fie desenata in ecran, null=in centru
		fereastra.setLocationRelativeTo(null);
		//sa fie vizibila fereastra:
		fereastra.setVisible(true);
		
		//pornim firului jocului, de executie in java
		panoulJocului.pornesteFirulJocului();
		
	}

}
