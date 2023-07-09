package RESTful_Web_Service.FuelsFrance.objets;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="pdv")

@Entity
public class Pdv{
	
	public Pdv(int id, String cp, String ville, String adresse, Collection<Carburant> carburants)
	{
		this.id = id;
		this.cp = cp;
		this.ville = ville;
		this.adresse = adresse;
		this.carburants = carburants;
	}
	
	public Pdv() {}

	@Id
	private int id; 
	
	@Column
	private String cp;
	
	@Column
	private String ville;
	
	@Column
	private String adresse;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Collection<Carburant> carburants = new ArrayList<Carburant>();
	
	public int getId()
	{
		return id;
	}
	public String getCp()
	{
		return cp;
	}
	public String getVille()
	{
		return ville;
	}
	public String getAdresse()
	{
		return adresse;
	}
	public Collection<Carburant> getCarburants()
	{
		return carburants;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	public void setCp(String cp)
	{
		this.cp = cp;
	}
	public void setVille(String ville)
	{
		this.ville = ville;
	}
	public void setAdresse(String adresse)
	{
		this.adresse = adresse;
	}
	public void setCarburants(ArrayList<Carburant> carburants)
	{
		this.carburants = carburants;
	}
}
