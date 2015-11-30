package lotto;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Dies ist die Hauptklasse des Lottoprojektes. Sie steuert sowohl die Generierung der graphischen Oberflaeche als auch die Steuerung des Spielalgorithmus.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Lotto {

	private JFrame frame1 = new JFrame("Lottoschein");
	private Kreuzflaeche kf = new Kreuzflaeche();
	private ArrayList<Integer> zahlen = new ArrayList<Integer>();
	private ArrayList<Integer> gewinnzahlen = new ArrayList<Integer>();
	private String umbruch = System.getProperty("line.separator");
	
	public Lotto() {
		JOptionPane.showMessageDialog(null,"Achtung! Glücksspiel kann süchtig machen!"+umbruch+"Die Gewinnchance liegt bei 1:13.983.816!","Suchtgefahr", JOptionPane.WARNING_MESSAGE);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(600,600);
		frame1.setMinimumSize(new Dimension(350,350));
		frame1.setResizable(true);
		Container cp = frame1.getContentPane();
		cp.setLayout(new GridBagLayout());
		
		JButton bestaetigung = new JButton("Einreichen");
		bestaetigung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zahlenpruefen();
			}
		});
		bestaetigung.setVisible(true);
		
		cp.add(kf,new GridBagFelder(1,1,1,1,1,0.8));
		cp.add(bestaetigung,new GridBagFelder(1,2,1,1,1,0.2));
		
		frame1.pack();
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
	
	/**
	 * Diese Methode ueberprueft, ob der Nutzer genau sechs Kreuze gesetzt hat. Bei nicht genau sechs Stueck, wird er zurueck in das Ankreuzverfahren geworfen.<br>
	 * Anschliessend werden sechs zufaellige Zahlen generiert und herausgefunden, wie viele Zahlen der Spieler korrekt getippt hat.
	 */
	private void zahlenpruefen() {
		zahlen.clear();
		for(JCheckBox jcb:kf.getKreuze()) {
			if(jcb.isSelected()) {
				zahlen.add(Integer.valueOf(jcb.getText()));
			}
		}
		if(zahlen.size()>6) {
			JOptionPane.showMessageDialog(null,"Du hast mehr als sechs Kreuze gemacht."+umbruch+"Dein Schein ist damit ungültig."+umbruch+"Wir geben Dir die Gelegenheit einen"+umbruch+"neuen Schein auszufüllen.","Ungültiger Schein", JOptionPane.ERROR_MESSAGE);
			for(JCheckBox jcb:kf.getKreuze()) {
				jcb.setSelected(false);
				frame1.repaint();
			}
		} else if(zahlen.size()<6) {
			JOptionPane.showMessageDialog(null,"Du hast weniger als sechs Kreuze gemacht."+umbruch+"Du hast Dich sicherlich verzählt."+umbruch+"Wir geben Dir die Gelegenheit den"+umbruch+"Fehler zu korrigieren.","Ungültiger Schein", JOptionPane.ERROR_MESSAGE);
		} else {
			Random lotto = new Random();
			gewinnzahlen.clear();
			while(gewinnzahlen.size()!=6) {
				int zahl = lotto.nextInt(49)+1;
				if(!gewinnzahlen.contains(zahl)) {
					gewinnzahlen.add(zahl);
				}
			}
			int richtig = 0;
			Collections.sort(gewinnzahlen);
			for(Integer i:gewinnzahlen) {
				if(zahlen.contains(i)) {
					richtig++;
				}
			}
			if(richtig==6) {
				JOptionPane.showMessageDialog(null,"Du hast alle sechs Zahlen richtig!"+umbruch+"Du gewinnst den Jackpot!","Gewinn", JOptionPane.INFORMATION_MESSAGE);
			} else if(richtig==0) {
				JOptionPane.showMessageDialog(null,gewinnreihe()+"Du hast keine Zahl richtig!"+umbruch+"Du verlierst Deinen Spieleinsatz!","Kein Gewinn", JOptionPane.INFORMATION_MESSAGE);
			} else if(richtig==1){
				JOptionPane.showMessageDialog(null,gewinnreihe()+"Du hast eine Zahl richtig!"+umbruch+"Du machst einen kleinen Gewinn!","Eine Zahl richtig", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null,gewinnreihe()+"Du hast "+richtig+" Zahlen richtig!"+umbruch+"Du machst einen kleinen Gewinn!",richtig+" Zahlen richtig", JOptionPane.INFORMATION_MESSAGE);
			}
			neustart();
		}
	}
	
	/**
	 * Diese Methode gibt die Reihe der richtigen Gewinnzahlen als String aus.
	 * @return Gibt den Gewinnzahlen-String zurueck.
	 */
	private String gewinnreihe() {
		String gewinn = "Die Gewinnzahlen sind:"+umbruch;
		for(int i=0;i<6;i++) {
			if(i==5) {
				gewinn += gewinnzahlen.get(i) + "." + umbruch;
			} else if(i==4) {
				gewinn += gewinnzahlen.get(i)+" und ";
			} else {
				gewinn += gewinnzahlen.get(i)+", ";
			}
		}
		return gewinn;
	}
	
	/**
	 * Diese Methode fragt ab, ob der Nutzer einen neuen Lotto-Schein ausfuellen moechte.<br>
	 * Wenn der Nutzer auf Ja klickt, wird ein neuer Schein generiert, bei Nein schliesst sich das Programm komplett.<br>
	 */
	private void neustart() {
		int dialogneustart = JOptionPane.showConfirmDialog(null, "Möchtest Du einen neuen Lottoschein ausfüllen?", "Neuer Lottoschein?", JOptionPane.YES_NO_OPTION);
        if(dialogneustart == 0) {
        	for(JCheckBox jcb:kf.getKreuze()) {
				jcb.setSelected(false);
				frame1.repaint();
			}
        } else {
     	   System.exit(0);
        }
	}
	
	public static void main(String[] args) {
		new Lotto();
	}
}