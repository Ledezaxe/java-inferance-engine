import java.io.IOException;
import java.util.HashSet;

public class Main 
{
	public static void main(String[] args) throws IOException 
	{
		/** Creation de la base Examen **/
		Base examen = new Base("baseExamen.txt");
		
		/** Affichage de la base **/
		System.out.println(" ----- Base examen ----- \n" + examen.toString() + "\n");
		
		/** Declenchement chainage avant sur Examen **/
		HashSet<Fait> examenX = new HashSet<Fait>();
		Fait B = new Fait("B");
		Fait E = new Fait("E");
		Fait G = new Fait("G");
		Fait K = new Fait("K");
		examenX.add(B);
		examenX.add(E);
		examenX.add(G);
		examenX.add(K);
		
		System.out.println("Decl chainage avant Examen: ");
		System.out.println("\tFaits initiaux: " + examenX.toString());
		System.out.println("\tRegles declenchables" + examen.declChainageAvant(examenX) + "\n");
		
		/** Chainage avant en saturation sur Examen **/
		System.out.println("Chainage avant en saturation Examen: ");
		System.out.println("\t Faits deduits: " + examen.chainageAvantSaturation(examenX) + "\n");
		
		/** Chainage avant jusqu'a atteindre un but sur Examen **/
		System.out.println("Chainage avant jusqu'a atteindre le but \"J\": ");
		System.out.println("\t But atteint: " + examen.chainageAvant(examenX, new Fait("J")));		
		System.out.println("Chainage avant jusqu'a atteindre le but \"A\": ");
		System.out.println("\t But atteint: " + examen.chainageAvant(examenX, new Fait("A")) + "\n");
		
		/** Declenchement chainage arriere sur Examen **/
		System.out.println("Decl chainage arriere but \"J\": " + examen.declChainageArriere(new Fait("J")) + "\n");
		
		/** Chainage arriere sur Examen **/
		System.out.println("Chainage arriere sur Examen, but: \"F\": ");
		System.out.println("\t But atteint: " + examen.chainageArriere(new Fait("F")) + "\n");
	}
}