//*********************************************************************************
//
//	Auteur : AndreS Nunez			Projet : TP2	Classe Clinique
//
//*********************************************************************************

public class Clinique
{
	private final int DEFAULT_CAPACITY = 10;
	private int front, rear, count;
	private Patient[] queue;

//	Constructeur d'une clinique vide
	/**
	 *	Constructeur de Clinique
	 *
	 *	À la construction de la clinique le
	 *	tableau est instentié avec des Patients
	 *	et le front, rear et count sont mis a 0
	 *
 	*/
	public Clinique()
	{
		front = rear = count = 0;
		queue = new Patient[DEFAULT_CAPACITY];

		for(int i = 0; i < queue.length; i++)
			queue[i] = new Patient();
	}

//	Ajoute un patient
	/**
	 *	Ajoute un patient à la clinique
	 *
	 *	@param patient
	 *			le patient qui vient d'être ajouté à la clinique
	 *
 	*/
	public void enqueue(Patient patient)
	{
		if(count == queue.length)
			expandCapacity();

		queue[rear] = patient;
		rear = (rear + 1) % queue.length;
		count++;
	}

//	Methode qui enleve le patient lorsqui'il a été servi
	/**
	 *	Retourne le patient qui est rentré en premier
	 *
	 *	@return le patient qui est rentré en premier
	 *	@throws EmptyCollectionExceptions si jamais la clinique est vide
 	*/
	public Patient dequeue() throws EmptyCollectionException
	{
		if (isEmpty())
       		throw new EmptyCollectionException ("Clinique");

  		Patient result = queue[front];
  		queue[front] = new Patient();
  		front = (front+1) % queue.length;

 		count--;

 		return result;
	}

//	Methode pour elargir le tableau
	/**
	 *	Methode pour agrandir la capacité de la clinique
	 *
	 *
	 */
	private void expandCapacity()
	{
		Patient[] largeur = new Patient[queue.length * 2];

		for(int i = 0; i < count; i++)
			largeur[i] = queue[(front + i) % queue.length];

		front = 0;
		rear = count;
		queue = largeur;
	}

//	Methode qui te donne la les valeurs du premier patient
	/**
	 *
	 *	Retourne les caracteristiques du prochain patient à traiter
	 *
	 *	@return les caracteristiques du prochain patient à traiter
	 *	@throws EmptyCollectionException lorsque il n'a pas de prochain patient
	 */
	public Patient first() throws EmptyCollectionException
	{
  	    if (isEmpty())
  	    	throw new EmptyCollectionException("clinique");

   		return queue[front];
	}

//	Methode qui te retourne le nombre de patients à traiter
	/**
	 *	Retourne la grandeur de la clinique
	 *	@return la grandeur de la clinique
 	*/
	public int size()
	{
		return count;
	}

//	Methode qui donne true si la clinique est vide
	/**
	 *
	 *	Retourne un boolean indiquand si la clinique est vide
	 *
	 *	@return un boolean indiquand si la clinique est vide
	 */
	public boolean isEmpty()
	{
		return (count == 0);
	}

//	Methode toString
	/**
	 *
	 *	Retourne un paragraphe avec les patients de la clinique
	 *
	 *	@return un paragraphe avec les patients de la clinique
	 */
	public String toString()
	{
		String result = "";
	    int scan = 0;

   	    while(scan < (count + 1))
   	    {
     		if(queue[scan].getUrgence() != 0.0)
      		{
       			result += queue[scan].toString()+"\n------------------\n";
      		}
      		scan++;
    	}

  	  	return "Clinique : \n" + result;
	}

}