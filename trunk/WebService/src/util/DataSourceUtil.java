package util;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.xml.stream.XMLStreamReader;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;




public class DataSourceUtil {

	Connection connection = null;
	BasicDataSource bdSource = new BasicDataSource();
	
	public static void main(String args[]) throws Exception {
				
		String config = SystemUtil.getRiamsDsConf();
		System.out.println(config);
		DataSourceUtil dsu = new DataSourceUtil(config);
		String jdbcDriverFileUrl="file:"+SystemUtil.getPostgresqlDriverFile();
		
		System.out.println(dsu.getUrlClass(jdbcDriverFileUrl, "org.postgresql.Driver").toString());
		
		System.out.println(dsu.getClassLoader(jdbcDriverFileUrl).toString());
		
		System.out.println(dsu.getUrlDriver(jdbcDriverFileUrl,"org.postgresql.Driver").toString());
		
		Connection con = dsu.createConnection();
		Statement stmt = con.createStatement();
		String query = "SELECT * FROM VocSex";
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			System.out.println("Name- " + rs.getString("Name"));
		}
		rs.close();
		stmt.close();
		con.close();
	}
	
	
	public DataSourceUtil(String aFileName) throws Exception{
		if(!StringUtil.isNotEmpty(aFileName)) aFileName=SystemUtil.getRiamsDsConf();
		String localName="";
		String value="";
		XMLStreamReader xsr = XmlUtil.getXMLReader(aFileName);
		while(xsr.hasNext()) {
		    xsr.next();
		    if(xsr.isStartElement()){
			    localName = xsr.getLocalName();
			    xsr.next();
		        if(xsr.isCharacters()){
			    	value = xsr.getText();
			    	if(localName=="connection-url") bdSource.setUrl(value);
			    	else if(localName=="driver-class") bdSource.setDriverClassName(value);
			    	else if(localName=="user-name") bdSource.setUsername(value);
			    	else if(localName=="password") bdSource.setPassword(value);
			    }
		    }
		}
	}

	public Connection createConnection() {
		Connection con = null;
		try {
			if (connection != null) {
				System.out.println("Cant create a New Connection");
			} else {
				//con = bdSource.getConnection();
				Properties info = new Properties();
				info.setProperty("user", bdSource.getUsername());
				info.setProperty("password", bdSource.getPassword());
				Driver driver = getUrlDriver("file:"+SystemUtil.getPostgresqlDriverFile(), bdSource.getDriverClassName());
				con = driver.connect(bdSource.getUrl(), info);
				System.out.println("Connection Done successfully");
			}
		} catch (Exception e) {
			FileUtil.writeErrorFile(e, "DataSourceUtilError.txt");
		}
		return con;
	}
	public ClassLoader getClassLoader(String aUrl) throws MalformedURLException{
		ClassLoader classLoader = null;
		URL[] url = {new URL(aUrl)};
        classLoader = new URLClassLoader(url, System.class.getClassLoader());
		return classLoader;
	}
	public Class getUrlClass(String aUrl, String aDriverName) throws MalformedURLException{
			Class driver = null;
	        try {
	 		   URL[] url = {new URL(aUrl)};
		        URLClassLoader loader = new URLClassLoader(url, System.class.getClassLoader());
	        	driver = loader.loadClass(aDriverName);
	        } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return driver;
	}
	public Driver getUrlDriver(String aUrl, String aDriverName) throws MalformedURLException, SQLException{
		Driver driver = null;
        try {
 		   URL[] url = {new URL(aUrl)};
	        URLClassLoader loader = new URLClassLoader(url, System.class.getClassLoader());
	        try {
				driver = (Driver) Class.forName(aDriverName, true, loader).newInstance();
				DriverManager.registerDriver(driver);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				DriverManager.registerDriver(driver);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return driver;
}

}