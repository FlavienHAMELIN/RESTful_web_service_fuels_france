# RESTful_web_service_fuels_france

RESTful_web_service_fuels_france allows you to observe the price of fuel on the French territory

## Installation

Install Apache Maven

Go to the project root

Compile
```bash
mvn compile
```

Install the packages
```bash
mvn package
```

Run
```bash
mvn tomcat7:run
```

## How to use it

The web service offers the following possibilities:

  - display the data in XML format, filtering by postcode, using the following query, for example :
      [http://localhost:8080/FuelsFrance/Servlet?action=xml&cp=28000](http://localhost:8080/FuelsFrance/Servlet?action=xml&cp=28000)
		
		
  - display the data in JSON format, filtering by postcode, using the following query, for example : 
      [http://localhost:8080/FuelsFrance/Servlet?action=json&cp=28000](http://localhost:8080/FuelsFrance/Servlet?action=json&cp=28000)
		
		
  - display the data in XML format, filtering by department using the following query, for example : 
      [http://localhost:8080/FuelsFrance/Servlet?action=xml&departement=28](http://localhost:8080/FuelsFrance/Servlet?action=xml&departement=28)
		
		
  - display the data in JSON format, filtering by department, using the following query for example : 
      [http://localhost:8080/FuelsFrance/Servlet?action=json&departement=28](http://localhost:8080/FuelsFrance/Servlet?action=json&departement=28)
		
		
  - change the price of a fuel whose name is known at a station whose identifier is known, using the following query for example : [http://localhost:8080/FuelsFrance/Servlet?action=modifier&pdv_id=1000001&carburant_nom=Gazole&carburant_valeur=2](http://localhost:8080/FuelsFrance/Servlet?action=modifier&pdv_id=1000001&carburant_nom=Gazole&carburant_valeur=2)
		
		
  - delete a fuel whose name is known from a station whose identifier is known, using the following query for example :
      [http://localhost:8080/FuelsFrance/Servlet?action=supprimer&pdv_id=1000001&carburant_nom=Gazole](http://localhost:8080/FuelsFrance/Servlet?action=supprimer&pdv_id=1000001&carburant_nom=Gazole)
