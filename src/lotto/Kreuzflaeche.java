package lotto;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * Diese Klasse repraesentiert die Flaeche mit 7x7 Feldern zum Ankreuzen der gewaehlten sechs Lotto-Felder.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Kreuzflaeche extends JPanel {

	private JCheckBox[] kreuze = new JCheckBox[49];
	
	public Kreuzflaeche() {
		this.setLayout(new GridLayout(7,7));
		
		for(int i=0;i<49;i++) {
			kreuze[i] = new JCheckBox(String.valueOf(i+1));
			this.add(kreuze[i]);
		}
	}
	
	public JCheckBox getKreuz(int i) {
		return kreuze[i];
	}
	
	public JCheckBox[] getKreuze() {
		return kreuze;
	}
	
}