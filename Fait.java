import java.util.Objects;
import java.util.HashSet;

public class Fait
{
	private String nom;
   String validite = ""; //pour le chainage arrière
	
/** Constructeur **/
	public Fait (String nom)
	{
		this.nom = nom;
      this.validite = "non déduit";
	}
	
	public Fait(String nom, String valid)
	{
		this.nom = nom;
      this.validite = valid;
	}
	
/** getters **/
	public String getNom()
	{
		return this.nom;
	}
	
	public String getValidite()
	{
		return (this.validite);
	}
	
/** setters **/
	public void setValidite(String valid)
	{
		this.validite = valid;
	}
	
/** affichage **/
	public String toString()
	{
		return this.nom;
	}
   
   
/** surcharges utiles pour le HashSet **/
    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fait other = (Fait) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        return true;
    }
}