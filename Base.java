import java.util.ArrayList;
import java.util.Objects;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.lang.Math;

public class Base
{
	private HashSet<Regle> liste_regle;
	private HashSet<Fait> liste_fait;
   private int nr = -1; //niveau de récursivité
   
   //Toutes les lignes commentées exp servent pour l'expérimentation du chaînage avant
   static int nbE = 0;   //nombre d'étapes
   static int nbFD = 0;  //       de faits déduits
   static int nbRU = 0;  //       de règles utilisées
	
	
	/** Constructeurs **/
	public Base()
	{
		liste_regle = new HashSet<Regle>();
		liste_fait = new HashSet<Fait>();
	}
	
	public Base(String filename)
	{
		liste_regle = new HashSet<Regle>();
		liste_fait = new HashSet<Fait>();
		BufferedReader br;
		ArrayList<String> s = new ArrayList<String>();
		String tmp = "";
	
	//Lecture dans le fichier
		try
		{
			br = new BufferedReader(new FileReader(filename));
			tmp = br.readLine();
			while(tmp != null)
			{
				s.add(tmp);
				tmp = br.readLine();
			}
		}	
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		//Construction de la Base
		//1) Nombres de faits et de règles
		tmp = s.get(0);
		String[] t = tmp.split(" "); //réutilisé par la suite
		int fait = Integer.parseInt(t[0]);
		int regle = Integer.parseInt(t[1]);
		
		//2) Les faits
		tmp = s.get(1);
		t = tmp.split(" ");
		for(int i = 0 ; i < t.length ; i++)
		{
			this.liste_fait.add(new Fait(t[i]));
		}
		
		//3)Les règles
		
		for(int i = 2 ; i < s.size() ; i++)
		{
			HashSet<Fait> premisses = new HashSet<Fait>();
			HashSet<Fait> conclusion = new HashSet<Fait>();
			tmp = s.get(i);
			t = tmp.split(" -> ");
			String[] t2 = t[0].split(" ");
			for(int j = 0 ; j < t2.length ; j++)
			{
				premisses.add(new Fait(t2[j]));
			}
			
			t2 = t[1].split(" ");
			for(int y = 0 ; y < t2.length ; y++)
			{
				conclusion.add(new Fait(t2[y]));
			}
			this.ajouter_regle(new Regle(premisses, conclusion));
		}
	}
   
	
	/** getters **/
	public Fait getFait(String s) //obtention du fait par son nom
	{
		for(Fait f : this.liste_fait)
		{
			if(f.getNom().equals(s))
				return f;
		}
		System.out.print("fait non trouvé");
		return null;
	}
   
   public Fait getNFait(int n) //obtention du fait par son index
	{
		int i = 0;
      
      for(Fait f : this.liste_fait)
		{
			if(i == n)
				return f;
            
         i++;
		}
		System.out.print("fait non trouvé");
		return null;
	}
   
	
	/** setters **/
	public void ajouter_regle(Regle r)
	{
		this.liste_regle.add(r);
	}
	
	public void ajouter_fait(Fait f)
	{
		this.liste_fait.add(f);
	}
   
   public void setValidite(Fait f, String valid) 
   {
      this.getFait(f.getNom()).setValidite(valid); //modification de la validité du fait dans la liste de faits
      
      for(Regle r : this.liste_regle)  //modification de la validité du fait dans la liste de règles
      {
         for(Fait f2 : r.getPremisse())
         {
            if(f2.getNom().equals(f.getNom()))
               f2.setValidite(valid);
         }
         for(Fait f3 : r.getConclusion())
         {
            if(f3.getNom().equals(f.getNom()))
               f3.setValidite(valid);
         }
      }
      
   }
	
	/** affichage **/
	public String toString()
	{
		String ret;
		
		ret = "Faits : \n";
		for (Fait f : this.liste_fait)
		{
			ret += f.toString() + "\n";
		}
		
		ret += "\nRègles :\n";
		for (Regle r : this.liste_regle)
		{
			ret += r.toString() + "\n";
		}
		
		return ret;
	}
   
   //Affichage niveau de récursivité
   public void afficheNR()
   {
      System.out.print("["+this.nr+"] ");
      if(this.nr != 0)
      {
         for(int i = 0 ; i < this.nr ; i++)
         {
            System.out.print("\t");
         }
      }
   }
   
   //Pour l'expérimentation
   public static void experimentations(Base b, int nbExp)
   {
      int alea = (int)(Math.random()*3)+1;
      int[] t = new int[9];
      for(int n = 0 ; n < t.length ; n++)
         t[n] = 0;
      
      HashSet<Fait> init = new HashSet<Fait>();
      Fait but;
      
      for(int i = 0 ; i < nbExp ; i++)
      {
         but = new Fait(b.getNFait((int)(Math.random()*19)).getNom());
         
         for(int j = 0 ; j < (Math.random()*19) ; j++)
         {
            init.add(new Fait(b.getNFait((int)(Math.random()*19)).getNom()));
         }
         
         b.chainageAvantSaturation(init);
         t[0] += b.nbE;
         b.nbE = 0;
         t[1] += b.nbFD;
         b.nbFD = 0;
         t[2] += b.nbRU;
         b.nbRU = 0;
         
         b.chainageAvant(init, but);
         t[3] += b.nbE;
         b.nbE = 0;
         t[4] += b.nbFD;
         b.nbFD = 0;
         t[5] += b.nbRU;
         b.nbRU = 0;
         
         b.chainageAvantSelection(init, but);
         t[6] += b.nbE;
         b.nbE = 0;
         t[7] += b.nbFD;
         b.nbFD = 0;
         t[8] += b.nbRU;
         b.nbRU = 0;
      }
      
      for(int n = 0 ; n < t.length ; n++)
         t[n] = (int)(t[n] / nbExp);
      
      System.out.println("Pour le chaînage avant jusqu'à saturation :\n\tNombre moyen d'étapes : "+t[0]+"\n\tNombre moyen de faits déduits : "+t[1]+"\n\tNombre moyen de règles utilisées : "+t[2]+"\n");
      System.out.println("Pour le chaînage avant jusqu'à atteindre un but :\n\tNombre moyen d'étapes : "+t[3]+"\n\tNombre moyen de faits déduits : "+t[4]+"\n\tNombre moyen de règles utilisées : "+t[5]+"\n");
      System.out.println("Pour le chaînage avant avec sélection de la règle :\n\tNombre moyen d'étapes : "+t[6]+"\n\tNombre moyen de faits déduits : "+t[7]+"\n\tNombre moyen de règles utilisées : "+t[8]+"\n");
   }
   
   
	/** traitement des règles **/
   
   /* chainage avant */
	public HashSet<Regle> declChainageAvant(HashSet<Fait> X)
	{	
	  HashSet<Regle> retour = new HashSet<Regle>();
	  
	  for (Regle r : this.liste_regle)
	  {
	  		if (X.containsAll(r.getPremisse()) && !X.containsAll(r.getConclusion()))
			{
	 			retour.add(r);
			}
	  }
	  return retour;
	}
   
	public HashSet<Fait> chainageAvantSaturation(HashSet<Fait> faitsInitiaux)
	{
		HashSet<Fait> deduits = new HashSet<Fait>(faitsInitiaux);
		HashSet<Fait> deduitsPrec = new HashSet<Fait>();
		HashSet<Regle> Decl;
		
		while(!deduits.equals(deduitsPrec))
		{
			this.nbE++; //exp
         
         deduitsPrec = new HashSet<Fait>(deduits);
			Decl = this.declChainageAvant(deduits);
			for(Regle r : Decl)
			{
				deduits.addAll(r.getConclusion());
            this.nbRU++; //exp
			}
		}
      
      this.nbFD += deduits.size(); //exp
		return deduits;
	}
	
	public boolean chainageAvant(HashSet<Fait> faitsInitiaux, Fait but)
	{
		HashSet<Fait> resultat = chainageAvantSaturation(faitsInitiaux);
		
		for(Fait f : resultat)
		{
		   this.nbE++; //exp
         if(f.equals(but))
         {
            return true;
         }
		}
		return false;
	}
	
	public boolean chainageAvantSelection(HashSet<Fait> faitsInitiaux, Fait but)
	{
		HashSet<Fait> deduits = new HashSet<Fait>(faitsInitiaux);
		HashSet<Fait> deduitsPrec = new HashSet<Fait>();
		HashSet<Regle> Decl;
		Regle cardimax = new Regle();
		
		while(!deduits.equals(deduitsPrec))
		{
			this.nbE++; //exp
         
         deduitsPrec = new HashSet<Fait>(deduits);
			Decl = this.declChainageAvant(deduits);

			for(Regle r : Decl)
			{
				if(r.getPremisse().size() > cardimax.getPremisse().size())
            {
					cardimax = r;
               this.nbRU++; //exp
            }
			}
         
			deduits.addAll(cardimax.getConclusion());
         this.nbFD += deduits.size(); //exp
         
			for(Fait f : deduits)
			{
				this.nbE++; //exp
            if(f.equals(but))
			   {
               return true;
            }
			}
		}
		return false;
	}
   
   /* chainage arrière */
	
	public HashSet<Regle> declChainageArriere(Fait b)
	{
		HashSet<Regle> retour = new HashSet<Regle>();
	  
	  for (Regle regle : this.liste_regle)
	  {
	  		if (regle.getConclusion().contains(b))
			{
	 			retour.add(regle);
			}
	  }
	  return retour;
	}
	
	public boolean chainageArriere(Fait but)
	{
      nr++;
      //Affichage du nombre de règles déclenchables pour le but à déduire
      HashSet<Regle> Decl = declChainageArriere(but);
      afficheNR();
      System.out.println(Decl.size()+" règle(s) déclenchable(s) pour le but "+but);
		
		for(Regle r : Decl)  //traitement des règles déclenchables
		{
			boolean ok = true;
         
         //Affichage de la règle déclenchée courante
         afficheNR();
         System.out.println("Règle déclenchée : "+r.toString());
         
			for(Fait f : r.getPremisse()) //traitement des faits prémisses de la règle déclenchée
			{
				if(f.getValidite().equals("valide"))
            {
               afficheNR();
               System.out.println("Le fait "+f+" a déjà été déduit");
            }
            else if(f.getValidite().equals("invalide"))
            {
                 afficheNR();
                 System.out.println(">>> Le fait "+f+" a déjà été invalidé");
                 ok = false;
            }
            else
               if(ok != ok || chainageArriere(f) != ok)
   					ok = false;
			}
         
         if(ok == true) //déduction
         {
            afficheNR();
            System.out.println("Le but "+but+" est prouvé grâce à la règle "+r.toString());
            setValidite(but, "valide");
            nr--;
            return true;
         }
		}
      
      //En cas d'absence de règle déclanchable ou de non déduction d'au moins une des prémisses
      boolean verif = true;
      Scanner sc = new Scanner(System.in);
      
      if(but.getValidite().equals("non déduit"))
      {
		   afficheNR();
         System.out.print(">>> Le fait "+but+" est-il vrai ? [true/false]");
         verif = sc.nextBoolean();
		   if(verif == true)
         {
            afficheNR();
            System.out.println("Le but "+but+" est validé par l’utilisateur");
            setValidite(but, "valide");
            nr--;
            return true;
         }
         else
         {
			   setValidite(but, "invalide");
            afficheNR();
            System.out.println("Le fait "+but+" est invalidé par l'utilisateur");
            nr--;
            return false;
         }
      }
		
      nr--;
		return false;
	}
}