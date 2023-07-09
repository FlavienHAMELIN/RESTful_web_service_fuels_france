package RESTful_Web_Service.FuelsFrance.servlets;


import java.io.IOException;

import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Statement;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONArray;
import org.json.JSONObject;

import RESTful_Web_Service.FuelsFrance.objets.*;

/**
 * Servlet implementation class Servlet
 */
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String url = "jdbc:mysql://localhost:3306/stations";
	private final String user = "root";
	private final String passwd = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
    }
    
    
    
    static Element racine = new Element("pdv_liste");

	//On cree un nouveau Document JDOM base sur la racine que l'on vient de creer
	static org.jdom.Document document = new Document(racine);
	   
	
	
    
    public void recherche_par_cp_xml(String code_postal) 
    {
      	
    	Connection conn = null;
    	
    	try {
    		
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    	conn = DriverManager.getConnection(url, user, passwd);
	    	
	    	String query = "SELECT Pdv.id as id, Pdv.adresse as adresse, Pdv.cp as cp, Pdv.ville as ville, Carburant.nom as nom, Carburant.maj as maj, Carburant.valeur as valeur FROM Pdv,Pdv_Carburant,Carburant WHERE cp="+code_postal+"  AND Pdv_Carburant.Pdv_id = Pdv.id AND Pdv_Carburant.carburants_id = Carburant.id;";
	    	PreparedStatement preparedStatement = conn.prepareStatement(query);           
            ResultSet result = preparedStatement.executeQuery();

	    	result.next();
	    	String id = result.getString("id");
	    	String adresse = result.getString("adresse");
	    	String cp = result.getString("cp");
	    	String ville = result.getString("ville");
	    	String nom = result.getString("nom");
	    	String maj = result.getString("maj");
	    	String valeur = result.getString("valeur");
	    	
	    	racine = new Element("pdv_liste");
	    	Element pdv = new Element("pdv");
	    	racine.addContent(pdv);
	    	document = new Document(racine);
	    	
	    	
	    	Attribute id_XML = new Attribute("id",id);
	    	pdv.setAttribute(id_XML);
	    	Attribute cp_XML = new Attribute("cp",cp);
	    	pdv.setAttribute(cp_XML);
	    	Element adresse_XML = new Element("adresse");
	        adresse_XML.setText(adresse);
	        pdv.addContent(adresse_XML);
	        Element ville_XML = new Element("ville");
	        ville_XML.setText(ville);
	        pdv.addContent(ville_XML);
	        
	        Element prix = new Element("prix");
	    	Attribute nom_XML = new Attribute("nom",nom);
	    	prix.setAttribute(nom_XML);
	    	Attribute maj_XML = new Attribute("maj",maj);
	    	prix.setAttribute(maj_XML);
	    	Attribute valeur_XML = new Attribute("valeur",valeur);
	    	prix.setAttribute(valeur_XML);
	    	pdv.addContent(prix);
	    	
	    	while (result.next()) 
	    	{
	    		
	    		String next_id = result.getString("id");
		    	String next_adresse = result.getString("adresse");
		    	String next_cp = result.getString("cp");
		    	String next_ville = result.getString("ville");
		    	String next_nom = result.getString("nom");
		    	String next_maj = result.getString("maj");
		    	String next_valeur = result.getString("valeur");
		    	
		    	if(next_id.equals(id))
		    	{
		    		Element next_prix = new Element("prix");
			    	Attribute next_nom_XML = new Attribute("nom",next_nom);
			    	next_prix.setAttribute(next_nom_XML);
			    	Attribute next_maj_XML = new Attribute("maj",next_maj);
			    	next_prix.setAttribute(next_maj_XML);
			    	Attribute next_valeur_XML = new Attribute("valeur",next_valeur);
			    	next_prix.setAttribute(next_valeur_XML);
			    	pdv.addContent(next_prix);
			    	
		    	}
		    	else
		    	{
		    		Element next_pdv = new Element("pdv");
			    	racine.addContent(next_pdv);
			    	
			    	Attribute next_id_XML = new Attribute("id",next_id);
			    	next_pdv.setAttribute(next_id_XML);
			    	Attribute next_cp_XML = new Attribute("cp",next_cp);
			    	next_pdv.setAttribute(next_cp_XML);
			    	Element next_adresse_XML = new Element("adresse");
			        next_adresse_XML.setText(next_adresse);
			        next_pdv.addContent(next_adresse_XML);
			        Element next_ville_XML = new Element("ville");
			        next_ville_XML.setText(next_ville);
			        next_pdv.addContent(next_ville_XML);
			        
			        Element next_prix = new Element("prix");
			        
			    	Attribute next_nom_XML = new Attribute("nom",next_nom);
			    	next_prix.setAttribute(next_nom_XML);
			    	Attribute next_maj_XML = new Attribute("maj",next_maj);
			    	next_prix.setAttribute(next_maj_XML);
			    	Attribute next_valeur_XML = new Attribute("valeur",next_valeur);
			    	next_prix.setAttribute(next_valeur_XML);
			    	
			    	next_pdv.addContent(next_prix);
			    	pdv = next_pdv;
			    	
		    	}
		    	id = next_id;
		    	
	    	}
	    	
	    	
    	} catch (Exception e) {
    		
    	}
    	finally {
	    	if (conn!=null) {
		    	try {
		    		conn.close();
		    	}
		    	catch (Exception e) {}
	    	}
    	}
    }
    
    public void recherche_par_departement_xml(String numero_departement)
    {
    	Connection conn = null;
    	
    	try {
    		
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    	conn = DriverManager.getConnection(url, user, passwd);
	    	
	    	String query = "SELECT Pdv.id as id, Pdv.adresse as adresse, Pdv.cp as cp, Pdv.ville as ville, Carburant.nom as nom, Carburant.maj as maj, Carburant.valeur as valeur FROM Pdv,Pdv_Carburant,Carburant WHERE cp LIKE '"+numero_departement+"%%%' AND Pdv_Carburant.Pdv_id = Pdv.id AND Pdv_Carburant.carburants_id = Carburant.id;";
	    	PreparedStatement preparedStatement = conn.prepareStatement(query);           
            ResultSet result = preparedStatement.executeQuery();

	    	result.next();
	    	String id = result.getString("id");
	    	String adresse = result.getString("adresse");
	    	String cp = result.getString("cp");
	    	String ville = result.getString("ville");
	    	String nom = result.getString("nom");
	    	String maj = result.getString("maj");
	    	String valeur = result.getString("valeur");
	    	
	    	racine = new Element("pdv_liste");
	    	Element pdv = new Element("pdv");
	    	racine.addContent(pdv);
	    	document = new Document(racine);
	    	
	    	Attribute id_XML = new Attribute("id",id);
	    	pdv.setAttribute(id_XML);
	    	Attribute cp_XML = new Attribute("cp",cp);
	    	pdv.setAttribute(cp_XML);
	    	Element adresse_XML = new Element("adresse");
	        adresse_XML.setText(adresse);
	        pdv.addContent(adresse_XML);
	        Element ville_XML = new Element("ville");
	        ville_XML.setText(ville);
	        pdv.addContent(ville_XML);
	        
	        Element prix = new Element("prix");
	    	Attribute nom_XML = new Attribute("nom",nom);
	    	prix.setAttribute(nom_XML);
	    	Attribute maj_XML = new Attribute("maj",maj);
	    	prix.setAttribute(maj_XML);
	    	Attribute valeur_XML = new Attribute("valeur",valeur);
	    	prix.setAttribute(valeur_XML);
	    	pdv.addContent(prix);
	    	
	    	while (result.next()) 
	    	{
	    		
	    		String next_id = result.getString("id");
		    	String next_adresse = result.getString("adresse");
		    	String next_cp = result.getString("cp");
		    	String next_ville = result.getString("ville");
		    	String next_nom = result.getString("nom");
		    	String next_maj = result.getString("maj");
		    	String next_valeur = result.getString("valeur");
		    	
		    	if(next_id.equals(id))
		    	{
		    		Element next_prix = new Element("prix");
			    	Attribute next_nom_XML = new Attribute("nom",next_nom);
			    	next_prix.setAttribute(next_nom_XML);
			    	Attribute next_maj_XML = new Attribute("maj",next_maj);
			    	next_prix.setAttribute(next_maj_XML);
			    	Attribute next_valeur_XML = new Attribute("valeur",next_valeur);
			    	next_prix.setAttribute(next_valeur_XML);
			    	pdv.addContent(next_prix);
			    	
		    	}
		    	else
		    	{
		    		Element next_pdv = new Element("pdv");
			    	racine.addContent(next_pdv);
			    	
			    	Attribute next_id_XML = new Attribute("id",next_id);
			    	next_pdv.setAttribute(next_id_XML);
			    	Attribute next_cp_XML = new Attribute("cp",next_cp);
			    	next_pdv.setAttribute(next_cp_XML);
			    	Element next_adresse_XML = new Element("adresse");
			        next_adresse_XML.setText(next_adresse);
			        next_pdv.addContent(next_adresse_XML);
			        Element next_ville_XML = new Element("ville");
			        next_ville_XML.setText(next_ville);
			        next_pdv.addContent(next_ville_XML);
			        
			        Element next_prix = new Element("prix");
			        
			    	Attribute next_nom_XML = new Attribute("nom",next_nom);
			    	next_prix.setAttribute(next_nom_XML);
			    	Attribute next_maj_XML = new Attribute("maj",next_maj);
			    	next_prix.setAttribute(next_maj_XML);
			    	Attribute next_valeur_XML = new Attribute("valeur",next_valeur);
			    	next_prix.setAttribute(next_valeur_XML);
			    	
			    	next_pdv.addContent(next_prix);
			    	pdv = next_pdv;
			    	
		    	}
		    	id = next_id;
		    	
	    	}
	    	
	    	
    	} catch (Exception e) {
    		
    	}
    	finally {
	    	if (conn!=null) {
		    	try {
		    		conn.close();
		    	}
		    	catch (Exception e) {}
	    	}
    	}
    }
    
    public ArrayList<Pdv> recherche_par_cp_json(String code_postal) 
    {
    	ArrayList<Pdv> pdvs = new ArrayList<Pdv>();
      	Connection conn = null;
    	
    	try {
    		
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    	conn = DriverManager.getConnection(url, user, passwd);
	    	
	    	String query = "SELECT Pdv.id as id, Pdv.adresse as adresse, Pdv.cp as cp, Pdv.ville as ville, Carburant.nom as nom, Carburant.maj as maj, Carburant.valeur as valeur FROM Pdv,Pdv_Carburant,Carburant WHERE cp="+code_postal+" AND Pdv_Carburant.Pdv_id = Pdv.id AND Pdv_Carburant.carburants_id = Carburant.id;";
	    	PreparedStatement preparedStatement = conn.prepareStatement(query);           
            ResultSet result = preparedStatement.executeQuery();

	    	
	    	
	    	result.next();
	    	int id = result.getInt("id");
	    	String adresse = result.getString("adresse");
	    	String cp = result.getString("cp");
	    	String ville = result.getString("ville");
	    	String nom = result.getString("nom");
	    	Date maj = result.getDate("maj");
	    	String valeur = result.getString("valeur");
	    	
	    	ArrayList<Carburant> carburants = new ArrayList<Carburant>();
	    	Carburant carburant = new Carburant(nom,valeur,maj); 
	    	carburants.add(carburant);
	    	
	    	while(result.next())
	    	{
	    		int next_id = result.getInt("id");
		    	String next_adresse = result.getString("adresse");
		    	String next_cp = result.getString("cp");
		    	String next_ville = result.getString("ville");
		    	String next_nom = result.getString("nom");
		    	Date next_maj = result.getDate("maj");
		    	String next_valeur = result.getString("valeur");
		    	
		    	if(id == next_id)
		    	{
		    		Carburant next_carburant = new Carburant(next_nom,next_valeur,next_maj); 
			    	carburants.add(next_carburant);
		    	}
		    	else
		    	{
		    		pdvs.add(new Pdv(id,cp,ville,adresse,carburants));
		    		
		    		ArrayList<Carburant> next_carburants = new ArrayList<Carburant>();
		    		Carburant next_carburant = new Carburant(next_nom,next_valeur,next_maj); 
		    		next_carburants.add(next_carburant);
		    		
		    		id = next_id;
		    		adresse = next_adresse;
		    		cp = next_cp;
		    		ville = next_ville;
		    		nom = next_nom;
		    		maj = next_maj;
		    		valeur = next_valeur;
		    		carburants = next_carburants;
		    	}
	    	}
	    	pdvs.add(new Pdv(id,cp,ville,adresse,carburants));
    	} catch (Exception e) {
    		
    	}
    	finally {
	    	if (conn!=null) {
		    	try {
		    		conn.close();
		    	}
		    	catch (Exception e) {}
	    	}
    	}
    	return(pdvs);
    }
    
    public ArrayList<Pdv> recherche_par_departement_json(String numero_departement)
    {
    	ArrayList<Pdv> pdvs = new ArrayList<Pdv>();
      	Connection conn = null;
    	
    	try {
    		
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    	conn = DriverManager.getConnection(url, user, passwd);	    	
	    	
	    	String query = "SELECT Pdv.id as id, Pdv.adresse as adresse, Pdv.cp as cp, Pdv.ville as ville, Carburant.nom as nom, Carburant.maj as maj, Carburant.valeur as valeur FROM Pdv,Pdv_Carburant,Carburant WHERE cp LIKE '"+numero_departement+"%%%' AND Pdv_Carburant.Pdv_id = Pdv.id AND Pdv_Carburant.carburants_id = Carburant.id;";
	    	PreparedStatement preparedStatement = conn.prepareStatement(query);           
            ResultSet result = preparedStatement.executeQuery();
	    	
	    	result.next();
	    	int id = result.getInt("id");
	    	String adresse = result.getString("adresse");
	    	String cp = result.getString("cp");
	    	String ville = result.getString("ville");
	    	String nom = result.getString("nom");
	    	Date maj = result.getDate("maj");
	    	String valeur = result.getString("valeur");
	    	
	    	ArrayList<Carburant> carburants = new ArrayList<Carburant>();
	    	Carburant carburant = new Carburant(nom,valeur,maj); 
	    	carburants.add(carburant);
	    	
	    	while(result.next())
	    	{
	    		int next_id = result.getInt("id");
		    	String next_adresse = result.getString("adresse");
		    	String next_cp = result.getString("cp");
		    	String next_ville = result.getString("ville");
		    	String next_nom = result.getString("nom");
		    	Date next_maj = result.getDate("maj");
		    	String next_valeur = result.getString("valeur");
		    	
		    	if(id == next_id)
		    	{
		    		Carburant next_carburant = new Carburant(next_nom,next_valeur,next_maj); 
			    	carburants.add(next_carburant);
		    	}
		    	else
		    	{
		    		pdvs.add(new Pdv(id,cp,ville,adresse,carburants));
		    		
		    		ArrayList<Carburant> next_carburants = new ArrayList<Carburant>();
		    		Carburant next_carburant = new Carburant(next_nom,next_valeur,next_maj); 
		    		next_carburants.add(next_carburant);
		    		
		    		id = next_id;
		    		adresse = next_adresse;
		    		cp = next_cp;
		    		ville = next_ville;
		    		nom = next_nom;
		    		maj = next_maj;
		    		valeur = next_valeur;
		    		carburants = next_carburants;
		    	}
	    	}
	    	pdvs.add(new Pdv(id,cp,ville,adresse,carburants));
    	} catch (Exception e) {
    		
    	}
    	finally {
	    	if (conn!=null) {
		    	try {
		    		conn.close();
		    	}
		    	catch (Exception e) {}
	    	}
    	}
    	return(pdvs);
    }
    
    public void modifier_prix_carburant(int id_station, String nom_carburant, String nouveau_prix)
    {
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = new Date();  
        String maj = formatter.format(date);
        System.out.println(maj);
        
      	Connection conn = null;
    	
    	try 
    	{
    		
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    	conn = DriverManager.getConnection(url, user, passwd);
	    	
	    		    	
	    	String query = "UPDATE Carburant, Pdv_Carburant SET Carburant.maj = '"+maj+"', Carburant.valeur = '"+nouveau_prix+"' WHERE Carburant.id = Pdv_Carburant.carburants_id AND Pdv_Carburant.Pdv_id = "+id_station+" AND Carburant.nom = '"+nom_carburant+"';";	    	
	    	PreparedStatement preparedStatement = conn.prepareStatement(query);           
            preparedStatement.executeUpdate();
	    	
		} catch (Exception e) {
    		
    	}
    	finally {
	    	if (conn!=null) {
		    	try {
		    		conn.close();
		    	}
		    	catch (Exception e) {}
	    	}
    	}
        
    }
    
    public void supprimer_prix_carburant(int id_station, String nom_carburant)
    {
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = new Date();  
        String maj = formatter.format(date);
        System.out.println(maj);
        
      	Connection conn = null;
    	
    	try 
    	{
    		
	    	Class.forName("com.mysql.cj.jdbc.Driver");
	    	conn = DriverManager.getConnection(url, user, passwd);
	    	
	    	String query = "DELETE Carburant, Pdv_Carburant FROM Carburant INNER JOIN Pdv_Carburant ON Carburant.id = Pdv_Carburant.carburants_id WHERE Pdv_Carburant.Pdv_id = "+id_station+" AND Carburant.nom = '"+nom_carburant+"';";
	    	
	    	PreparedStatement preparedStatement = conn.prepareStatement(query);           
            
            preparedStatement.executeUpdate();
	    	
		} catch (Exception e) {
    		
    	}
    	finally {
	    	if (conn!=null) {
		    	try {
		    		conn.close();
		    	}
		    	catch (Exception e) {}
	    	}
    	}
        
    }
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {     
        
		
		String action = request.getParameter("action");
		String cp = request.getParameter("cp");
		String departement = request.getParameter("departement");
		String pdv_id = request.getParameter("pdv_id");
		String carburant_nom = request.getParameter("carburant_nom");
		String carburant_valeur = request.getParameter("carburant_valeur");
		
		if(action != null)
		{
			if(action.equals("xml"))
			{
				if(cp != null)
					recherche_par_cp_xml(cp);
				else
				{
					if(departement != null)
					{
						recherche_par_departement_xml(departement);
					}
				}
				XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
				sortie.output(document, response.getOutputStream());
			}
			else
			{
				if(action.equals("json"))
				{
					ArrayList<Pdv> array = new ArrayList<Pdv>();
					if(cp != null)
						array = recherche_par_cp_json(cp);
					else
					{
						if(departement != null)
						{
							array = recherche_par_departement_json(departement);
						}
					}
					JSONArray json_array = new JSONArray(array);
					JSONObject json_object = new JSONObject();
					json_object.put("pdv_liste", json_array);
					
					PrintWriter out = response.getWriter();
					out.print(json_object);
					out.flush();
				}
				else
				{
					if(action.equals("modifier"))
					{
						if(pdv_id != null & carburant_nom != null & carburant_valeur != null)
						{
							int id = Integer.parseInt(pdv_id);
							modifier_prix_carburant(id, carburant_nom, carburant_valeur);
						}
					}
					else
					{
						if(action.equals("supprimer"))
						{
							if(pdv_id != null & carburant_nom != null)
							{
								int id = Integer.parseInt(pdv_id);
								supprimer_prix_carburant(id, carburant_nom);
							}
						}
					}
				}
			}
		}
		

		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
