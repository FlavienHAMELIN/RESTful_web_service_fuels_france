package RESTful_Web_Service.FuelsFrance.objets;


import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="carburant")

@Entity
public class Carburant{
	
	public Carburant(String nom, String valeur, Date maj)
	{
		
		this.nom = nom;
		this.valeur = valeur;
		this.maj = maj;
	}
	
	public Carburant() {}

	@Id
	@GenericGenerator(name = "announcement", strategy = "increment")
	@GeneratedValue(generator = "announcement")
	private Integer id;
	
	@Column
	private String nom;
	
	@Column
	private String valeur;
	
	@Column
	private Date maj;
	
	public String getNom()
	{
		return nom;
	}
	public String getValeur()
	{
		return valeur;
	}
	public Date getMaj()
	{
		return maj;
	}
	
	public void setNom(String nom)
	{
		this.nom = nom;
	}
	public void setValeur(String valeur)
	{
		this.valeur = valeur;
	}
	public void setMaj(Date maj)
	{
		this.maj = maj;
	}
}

