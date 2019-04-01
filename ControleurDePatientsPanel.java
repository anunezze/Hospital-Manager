import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
/**
	*@author AndreS Nunez
	*
	*Projet : TP2	Classe ControleurDePatientsPanel
	*
*/
public class ControleurDePatientsPanel extends JPanel
{
	private	BufferedReader cbr, ubr;
	private PrintWriter cpw, upw;
	private final int DEFAULT_CAPACITY = 3;
	private Patient[] tabPatient = new Patient[DEFAULT_CAPACITY];
	private int counter = 0;
	private Clinique clinique = new Clinique();
	private JLabel titreLabel;
	private JButton addPatientButton, urgenceOutButton, cliniqueOutButton, exitButton;
	private JPanel infermieresPanel, buttonPanel;
	private JTextArea urgenceTextArea, cliniqueTextArea, nombreCliniqueTextArea, nombreUrgenceTextArea;

/**
 *	Constructeur ControleurDePatientsPanel.
 *
 *	À la construction d'un objet ControleurDePatientsPanel
 *	toutes les variables sont instentiés avec leur nom correspondants.
 *
 */
	ControleurDePatientsPanel() throws IOException
	{
		upw = new PrintWriter("urgence.txt");
		cpw = new PrintWriter("clinique.txt");
		cpw.write("Liste de patients qui ont été traités à la clinique :\n");
		cpw.flush();
		upw.write("Liste de patients qui on été traités à l'urgence :\n");
		upw.flush();
		for(int i = 0; i < DEFAULT_CAPACITY; i++)
			tabPatient[i] = new Patient();
		cbr = new BufferedReader(new FileReader("clinique.txt"));
		ubr = new BufferedReader(new FileReader("urgence.txt"));

		setLayout(new BorderLayout());

//	J'instentie mes variables

		infermieresPanel = new JPanel();
		buttonPanel = new JPanel();
		nombreUrgenceTextArea = new JTextArea("Nombre de personnes à l'urgence : 0 sur " + DEFAULT_CAPACITY);
		nombreUrgenceTextArea.setEditable(false);
		nombreCliniqueTextArea = new JTextArea("Nombre de personnes à la clinique : 0\nTemps estimé : 0 min");
		nombreCliniqueTextArea.setEditable(false);
		titreLabel = new JLabel("SUPER CONTROLEUR DES PATIENTS");
		addPatientButton = new JButton("Nouveau Patient");
		urgenceOutButton = new JButton("Traiter urgence");
		cliniqueOutButton = new JButton("Traiter clinique");
		exitButton = new JButton("Sortir");
		urgenceTextArea = new JTextArea(toStringtab());
		cliniqueTextArea = new JTextArea(clinique.toString());
		urgenceTextArea.setEditable(false);
		cliniqueTextArea.setEditable(false);
		urgenceTextArea.setPreferredSize (new Dimension(330, 150));
		cliniqueTextArea.setPreferredSize (new Dimension(330, 150));
		urgenceTextArea.setBackground(Color.GRAY);
		cliniqueTextArea.setBackground(Color.GRAY);

//	Mes listeners
		exitButton.addActionListener(new SortirProgrammeListener());
		addPatientButton.addActionListener(new AjouterPatientListener());
		urgenceOutButton.addActionListener(new SortirPatientUrgence());
		cliniqueOutButton.addActionListener(new SortirPatientClinique());


		buttonPanel.setPreferredSize(new Dimension(150, 50));
		titreLabel.setFont(new Font("Helvetica", Font.BOLD, 36));
		titreLabel.setBackground(Color.LIGHT_GRAY);
		titreLabel.setPreferredSize(new Dimension(230,50));
		addPatientButton.setPreferredSize(new Dimension(140, 200));
		urgenceOutButton.setPreferredSize(new Dimension(140, 200));
		cliniqueOutButton.setPreferredSize(new Dimension(140, 200));
		exitButton.setPreferredSize(new Dimension(140, 75));

		infermieresPanel.setLayout(new BorderLayout());
		infermieresPanel.setBackground(Color.LIGHT_GRAY);

		buttonPanel.add(addPatientButton);
		buttonPanel.add(urgenceOutButton);
		buttonPanel.add(cliniqueOutButton);
		buttonPanel.add(exitButton);

		infermieresPanel.add(urgenceTextArea, BorderLayout.WEST);
		infermieresPanel.add(cliniqueTextArea, BorderLayout.EAST);
		infermieresPanel.add(nombreUrgenceTextArea, BorderLayout.NORTH);
		infermieresPanel.add(nombreCliniqueTextArea, BorderLayout.SOUTH);

//	Je les add au ControleurDePatientsPanel
		this.add(titreLabel, BorderLayout.NORTH);
		this.add(infermieresPanel);
		this.add(buttonPanel, BorderLayout.EAST);
		this.setPreferredSize (new Dimension(900, 750));
		this.setBackground (Color.LIGHT_GRAY);

	}


//*******************************************************************************************************************************
//	Listener pout le bouton qui ajoute un patient
//*******************************************************************************************************************************
	private class AjouterPatientListener implements ActionListener
	{
		/**
 		*	Listener qui ajoute un patient
 		*
 		*	@param	event
 		*			le nouveau event du bouton
 		*/
		public void actionPerformed (ActionEvent event)
		{
			Patient pa1 = new Patient();
			try
			{
				pa1 = new Patient(JOptionPane.showInputDialog("Veuillez entrer le nom du patient :"),
										  JOptionPane.showInputDialog("Veuillez entrer le motif du patient :"),
										  Integer.parseInt(JOptionPane.showInputDialog("Veuillez entre l'urgence du patient de 1 à 10\nEtant 10 la plus grande urgence.")));

				if(pa1.getUrgence() <=10 && pa1.getUrgence() >= 0)
				{
					if(enqueueDonne(pa1))
					{
						enqueue(pa1);
						urgenceTextArea.setText(toStringtab());
						cliniqueTextArea.setText(clinique.toString());
					}
					else
					{
						clinique.enqueue(pa1);
						cliniqueTextArea.setText(clinique.toString());
					}
				}
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre entre 1 et 10", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
			nombreCliniqueTextArea.setText("Nombre de personnes à la clinique : " + clinique.size() + "\nTemps estimé : " + (clinique.size()*15) + " min");
			nombreUrgenceTextArea.setText("Nombre de personnes à l'urgence : " + counter + " sur " + DEFAULT_CAPACITY);
			if(pa1.getUrgence() <=10 && pa1.getUrgence() >= 0)
				JOptionPane.showMessageDialog(null, "Le patient a été ajouté à la liste.", "Message", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre entre 1 et 10", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}

//***************************************************************************************************************
//	Listener pout le bouton qui sort le patient le plus urgent de l'urgence
//***************************************************************************************************************
/**
 *	Classe SortirPatientUrgence
 *
 */
	private class SortirPatientUrgence implements ActionListener
	{
		/**
		 *	Sort un patient de l'urgence.
		 *
		 *	@param ev
		 *			le nouveau evenement du bouton		 *
		 *
		 *
		 */
		public void actionPerformed(ActionEvent ev)
		{
			if(counter != 0)
			{
				upw.write("\n" + tabPatient[getIndiceMaxUrgence()].toString() + "\n**********************************************************");
				upw.flush();

				JOptionPane.showMessageDialog(null,"Patient : \n" + tabPatient[getIndiceMaxUrgence()] + "\nEntre pour se faire traiter à l'urgence.", "Message de l'urgence", JOptionPane.INFORMATION_MESSAGE);
				tabPatient[getIndiceMaxUrgence()] = new Patient();
				urgenceTextArea.setText(toStringtab());
				counter--;
				nombreUrgenceTextArea.setText("Nombre de personnes à l'urgence : " + counter + " sur " + DEFAULT_CAPACITY);
			}

			else
				JOptionPane.showMessageDialog(null, "L'urgence est déjà vide.", "Message de l'urgence", JOptionPane.ERROR_MESSAGE);
		}
	}
//***************************************************************************************************************
//	Listener pout le bouton qui exit le programme
//***************************************************************************************************************
/**
 *	Classe SortirProgrammeListener
 *
 *	Cette classe est appellé lorsque le button sortir est appuyé.
 */
	private class SortirProgrammeListener implements ActionListener
	{
		String s = "";
		/**
		 *	Ferme l'application
		 *
		 *	@param	e
		 *			le nouveau evenement du bouton
		 *
		 */
		public void actionPerformed(ActionEvent e)
			{
				cpw.close();
				upw.close();
				try
				{
					while((s = ubr.readLine()) != null)
						System.out.println(s);
					System.out.println("-------------------------------------------------------------------------");
					while((s = cbr.readLine()) != null)
						System.out.println(s);
				}
				catch(IOException ex)
				{
				}
				JOptionPane.showMessageDialog(null, "Les fichiers urgence.txt et clinique.txt\n" +
													"viennent d'être imprimés. (console)");

				System.exit(0);
			}
	}
//***************************************************************************************************************
//	Listener pout le bouton qui sort le patient le plus urgent de l'urgence
//***************************************************************************************************************
/**
 *	Classe SortirPatient Clinique
 *
 *
 */
	private class SortirPatientClinique implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(clinique.size()!= 0)
			{
				Patient pa3 = clinique.dequeue();

				cpw.write("\n" + pa3.toString() + "\n**********************************************************");
				cpw.flush();

				JOptionPane.showMessageDialog(null, "Le patient : \n" + pa3.toString() + "\nEntre pour se faire traiter à la clinique.", "Message de la clinique", JOptionPane.INFORMATION_MESSAGE);
				nombreCliniqueTextArea.setText("Nombre de personnes à la clinique : " + clinique.size() + "\nTemps estimé : " + (clinique.size()*15) + " min");
				cliniqueTextArea.setText(clinique.toString());
			}
			else
				JOptionPane.showMessageDialog(null, "La clinique est déjà vide.", "Message de la clinique", JOptionPane.ERROR_MESSAGE);
		}
	}
//***************************************************************************************************************
//	Methode qui retourne un boolean true si on peut mettre un patient dans l'urgence et false si on peut pas
//***************************************************************************************************************
/**
 *	Donne la permition pour mettre le patient en attente
 *	Retourne la permition de mettre le patient en attente
 *
 *	@param pat
 *			le patient qui viens de rentrer
 *
 *	@return un boolean qui te permet a
 *			accéder a une autre methode
 */
	private boolean enqueueDonne(Patient pat)
	{
		boolean donne = false;

		if(counter == DEFAULT_CAPACITY)
		{
			if((tabPatient[getIndiceMinUrgence()]).getUrgenceHopital() < pat.getUrgenceHopital())
			{
				return true;
			}
			else
			{
				return donne;
			}
		}

		else
		{
			for(int i = 0; i < DEFAULT_CAPACITY; i++)
				if(tabPatient[i].getUrgenceHopital() == 0)
					return true;
		}
		return donne;
	}
//*********************************************************************************************************
//	Methode qui ajoute le patient
//*********************************************************************************************************
/**
 *	Rentre le patient dans a file d'attente
 *
 *	@param	pat
 *			le patient qui viens de rentrer
 */
	private void enqueue(Patient pat)
	{
		Patient pa2 = new Patient();

		if((counter) == DEFAULT_CAPACITY)
		{
			if((tabPatient[getIndiceMinUrgence()].getUrgenceHopital()) < pat.getUrgenceHopital())
			{
				clinique.enqueue(tabPatient[getIndiceMinUrgence()]);
				tabPatient[getIndiceMinUrgence()] = pat;
			}
		}

		else
		{
			for(int i = 0; i < DEFAULT_CAPACITY; i++)
				if(tabPatient[i].getUrgence() == 0.0)
				{
					tabPatient[i] = pat;
					counter++;
					i = DEFAULT_CAPACITY;
            	}
		}
	}
//**********************************************************************************************************
//	Methode toStringtab
//**********************************************************************************************************
/**
 *	Retourne la liste des patients a l'urgence
 *
 *	@return un texte des patients à l'urgence
 *
 */
	private String toStringtab()
	{
		String result = "";
	    int scan = 0;

   	    while(scan < counter)
   	    {
     		if(tabPatient[scan].getUrgence() != 0.0)
      		{
       			result += tabPatient[scan].toString()+"\n------------------\n";
      		}
      		scan++;
    	}

  	  	return ("Urgence : \n" + result);
	}
//***********************************************************************************************************
//	Methode qui donne la casse du tableau avec la plus petite urgence
//***********************************************************************************************************
/**
 *	Retourne la casse du tableau(urgence) avec la plus petite urgence.
 *
 *	@return un entier qui designe la casse du tableau(urgence)
 *		avec la plus petite urgence.
 */
	private int getIndiceMinUrgence()
	{
		int min = 0;

		for(int i = 0; i < tabPatient.length; i++)
			if(tabPatient[min].getUrgence() > tabPatient[i].getUrgence())
				min = i;
		return min;
	}

//***********************************************************************************************************
//	Methode qui donne la casse du tableau avec la plus grande urgence
//***********************************************************************************************************
/**
 *	Retourne la casse du tableau(urgence) avec la plus grande urgence.
 *
 *	@return un entier qui designe la casse du tableau(urgence)
 *		avec la plus grande urgence.
 *
 */
	private int getIndiceMaxUrgence()
	{
		int max = 0;

		for(int i = 0; i < tabPatient.length; i++)
			if(tabPatient[max].getUrgence() < tabPatient[i].getUrgence())
				max = i;
		return max;
	}
}