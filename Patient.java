//******************************************************************************
//
//	Auteur : AndreS Nunez		TP2			Classe Patient
//
//******************************************************************************
public class Patient
{
	protected String nom;
	protected String motif;
	protected double urgence = 0.0;
	protected static double count = 0.0001;

//	Mes constructeurs
	/**
	 *	Constructeur Paient
	 *
	 *	À la construction d'un Objet Patient
	 *	le nom est prédefini comme "libre"
	 *	et le motif est prédefini comme " ".
 	*/
	public Patient()
	{
		nom = "Libre";
		motif = "";
	}

	/**
	 *	Modifie le nom, le motif, et l'urgence
	 *		des patients.
	 *
	 *	@param nom
	 *			le nom du patient
	 *	@param motif
	 *			le motif du patient
	 *	@param urgence
	 *			l'urgence du patient
	 */
	public Patient(String nom, String motif, int urgence)
	{
		this.nom = nom;
		this.motif = motif;
		this.urgence = (double)(urgence - count);
		count = count + 0.0001;
	}

//	Modificateurs
/**
 *	Retourne l'urgence du patient
 *
 *	@return l'urgence du patient
 *
 */
	public double getUrgence()
	{
		return urgence;
	}

/**
 *	Retourne l'urgence affiché par le tableau de l'urgence
 *
 *	@return l'urgence affiché par le tableau de l'urgence
 */
	public int getUrgenceHopital()
	{
		return ((int)getUrgence());
	}

/**
 *	Retourne les informations du patient
 *	
 *	@return les informations du patient
 */
	public String toString()
	{
		return "Nom : " + nom + "\n" +
			   "Motif : " + motif + "\n" +
			   "Urgence : " + (getUrgenceHopital() + 1);
	}
}