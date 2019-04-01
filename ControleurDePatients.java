/**
 *	@author AndreS Nunez
 *
 *	TP2		Classe ControleurDePatients
 *
 *
 *
 */

import java.io.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
/**
 *	Classe ControleurDePatients classe main.
 *
 *	Cette classe roule le programme.
 *
 *	@see ControleurDePatientsPanel
 */
public class ControleurDePatients
{
	public static void main(String[] args) throws IOException
	{
		String question;

		JFrame frame = new JFrame("Andres.Iris Company");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		ControleurDePatientsPanel panel = new ControleurDePatientsPanel();

		frame.add(panel);

		frame.pack();
	    frame.setVisible(true);
	}
}