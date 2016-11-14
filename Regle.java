import java.util.HashSet;

public class Regle
{
	private HashSet<Fait> premisse;
	private HashSet<Fait> conclusion;
   
/** constructeurs **/
	public Regle()
	{
		this.premisse = new HashSet<Fait>();
		this.conclusion = new HashSet<Fait>();
	}
   
	public Regle(HashSet<Fait> premisse, HashSet<Fait> conclusion)
	{
		this.premisse = premisse;
		this.conclusion = conclusion;
	}
   
/** getters **/
	public HashSet<Fait> getPremisse ()
	{
		return this.premisse;
	}
   
	public HashSet<Fait> getConclusion ()
	{
		return this.conclusion;
	}
   
/** affichage **/
	public String toString ()
	{
		return this.premisse+"->"+this.conclusion;
	}
}