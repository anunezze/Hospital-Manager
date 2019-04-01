//******************************************************************************
//
//	Auteur : AndreS Nunez	TP2		Classe EmptyCollectionException
//
//******************************************************************************
/**
 *	Cette classe est appell� lorsqu'il y a plus de personnes dans la clinique.
 *	@author AndreS Nunez
 *
 */

public class EmptyCollectionException extends RuntimeException
{
	/**
	 *	Constructeur du EmptyCollectionException
	 *
	 *	� la construction de cet objet on fait
	 *	appelle au parent pour mettre le message adequant
	 *
	 */
	public EmptyCollectionException(String collection)
	{
		super("Il y a plus de patients dans la " + collection);
	}
}