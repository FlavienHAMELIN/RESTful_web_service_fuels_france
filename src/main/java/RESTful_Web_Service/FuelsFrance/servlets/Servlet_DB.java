package RESTful_Web_Service.FuelsFrance.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

import RESTful_Web_Service.FuelsFrance.objets.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class servlet
 */

public class Servlet_DB extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet_DB() {
        super();
    }
    
    public static Date StringToDate(String s)
    {
    	Date result = null;
    	try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result  = dateFormat.parse(s);
        }

        catch(ParseException e){
            e.printStackTrace();

        }
        return result ;
    }
    
    public ArrayList<Pdv> parseXML(String path) {

      try {
         File inputFile = new File(path);
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         NodeList nList = doc.getElementsByTagName("pdv");
         
         ArrayList<Pdv> stations = new ArrayList<Pdv>();
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            Pdv station = new Pdv();
            ArrayList<Carburant> carburants = new ArrayList<Carburant>();
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               	
               Element eElement = (Element) nNode;
               String id_string = eElement.getAttribute("id");
               int id = Integer.parseInt(id_string);
               station.setId(id);
               String cp_string = eElement.getAttribute("cp");
               station.setCp(cp_string);
               station.setVille((String) eElement.getElementsByTagName("ville").item(0).getTextContent());
               station.setAdresse((String) eElement.getElementsByTagName("adresse").item(0).getTextContent());
               stations.add(station);
               
               NodeList prixList = eElement.getElementsByTagName("prix");
        	   for (int i = 0; i < prixList.getLength(); i++) {
        		   Carburant carburant = new Carburant();
        		   Node prixNode = prixList.item(i);
        		   Element prixElement = (Element) prixNode;
        		   carburant.setNom(prixElement.getAttribute("nom"));
        		   String valeur_string = prixElement.getAttribute("valeur");
        		   carburant.setValeur(valeur_string);
        		   String maj_string = prixElement.getAttribute("maj");
        		   Date maj = StringToDate(maj_string);
        		   carburant.setMaj(maj);
        		   
           		   Date date_actuelle = new Date();
                   long diff = (long) (date_actuelle.getTime() - 8.64*10000000);
                   Date date_actuelle_moins_24h = new Date(diff);
                   
                   
                   if(maj.after(date_actuelle_moins_24h))
                   {
                	   carburants.add(carburant); 
                   }
                   
        	   }
        	   
        	   station.setCarburants(carburants);
        	   
            }
            
         }
         return stations;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;

    }
    
    public static void downloadFile(String adresse, File dest) {
		BufferedReader reader = null;
		FileOutputStream fos = null;
		InputStream in = null;
		try {

			// Creating the connection
			URL url = new URL(adresse);
			URLConnection conn = url.openConnection();
			System.out.println(adresse);

			String FileType = conn.getContentType();
			System.out.println("FileType : " + FileType);

			int FileLenght = conn.getContentLength();
			if (FileLenght == -1) {
				throw new IOException("Fichier non valide.");
			}

			// Reading the answer
			in = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(in));
			if (dest == null) {
				String FileName = url.getFile();
				FileName = FileName.substring(FileName.lastIndexOf('/') + 1);
				dest = new File(FileName);
			}
			fos = new FileOutputStream(dest);
			byte[] buff = new byte[1024];
			int l = in.read(buff);
			while (l > 0) {
				fos.write(buff, 0, l);
				l = in.read(buff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
    
    public static void decompress(final File file, final File folder,
    		final boolean deleteZipAfter)
        throws IOException {
            final ZipInputStream zis = new ZipInputStream(new BufferedInputStream(
            		new FileInputStream(file.getCanonicalFile())));
            ZipEntry ze;
            try {    
    	        // Browse through all files
    	        while (null != (ze = zis.getNextEntry())) {
    	            final File f = new File(folder.getCanonicalPath(), ze.getName());
    	            if (f.exists())
    	            	f.delete();
    	            
    	            // Creating folders
    	            if (ze.isDirectory()) {
    	                f.mkdirs();
    	                continue;
    	            }
    	            f.getParentFile().mkdirs();
    	            final OutputStream fos = new BufferedOutputStream(
    	            		new FileOutputStream(f));
    	            
    	            // Writing files
    	            try {
    	                try {
    	                    final byte[] buf = new byte[8192];
    	                    int bytesRead;
    	                    while (-1 != (bytesRead = zis.read(buf)))
    	                        fos.write(buf, 0, bytesRead);
    	                } finally {
    	                    fos.close();
    	                }
    	            } catch (final IOException ioe) {
    	                f.delete();
    	                throw ioe;
    	            }
    	        }
            } finally {
            	zis.close();
            }
            if (deleteZipAfter)
            	file.delete();
        }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		File ZIPFile = new File("PrixCarburants_instantane.zip");
		downloadFile("https://donnees.roulez-eco.fr/opendata/instantane", ZIPFile);
		File XMLFolder = new File("");
		decompress(ZIPFile, XMLFolder, true);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager em = emf.createEntityManager();
		ArrayList<Pdv> stations = parseXML("PrixCarburants_instantane.xml");
		
		em.getTransaction().begin();
		for(int i = 0; i < stations.size(); i++)
		{
			em.persist(stations.get(i));
		}
		em.getTransaction().commit();
		PrintWriter out = response.getWriter();
		out.println("<body>Database updated</body>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
