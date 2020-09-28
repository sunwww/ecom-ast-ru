package ru.ecom.sql;

import org.apache.log4j.Logger;
import org.jboss.annotation.ejb.Service;
import ru.ecom.ejb.services.util.ApplicationDataSourceHelper;

import javax.ejb.Local;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Service
@Local(ISqlUpdateService.class)
public class SqlUpdateServiceBean implements ISqlUpdateService {

    private static final Logger LOG = Logger.getLogger(SqlUpdateServiceBean.class);

    public SqlUpdateServiceBean() throws NamingException, SQLException {
        LOG.info("Try get scripts...");

        int min=-1000;
        Integer databaseVersion = Integer.valueOf(select("select keyvalue from softconfig where key='BD_version'"));

        List<String> files = getListFiles();
        for(String s : files){
            InputStream in = getClass().getResourceAsStream("/"+s);
            String sql = convertStreamToString(in);
            String[] tmp = sql.split("#");

            try {
                Integer currScriptVersion = Integer.valueOf(tmp[0]);
                if(databaseVersion<currScriptVersion){
                    LOG.info(">>>Work with file: "+s);
                    execute(tmp[1]);
                    if(min<currScriptVersion){
                        min=currScriptVersion;
                    }
                }
            } catch (Exception e) {
                LOG.error("Error sql script "+s);
            }
        }

        if(min>-1000)  execute("update softconfig set keyvalue='"+min+"' where key='BD_version'");
    }

    private DataSource findDataSource() throws NamingException {
        return ApplicationDataSourceHelper.getInstance().findDataSource();
    }

    private String select(String sql) throws NamingException, SQLException {
        DataSource ds =findDataSource();
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)){
                resultSet.next();
                return resultSet.getString(1);
            }
        }
    }

    private void execute(String sql) throws NamingException {
        DataSource ds =findDataSource();
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static List<String> getListFiles(){
        List<String> fileNames = new ArrayList<>();
        try {
            CodeSource src = SqlUpdateServiceBean.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                try (ZipInputStream zip = new ZipInputStream(jar.openStream())){
                    while(true) {
                        ZipEntry e = zip.getNextEntry();
                        if (e == null)
                            break;
                        String name = e.getName();
                        if (name.startsWith("META-INF/sql/")) {
                            if(name.contains(".sql") || name.contains(".SQL")){
                                fileNames.add(name);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileNames;
    }

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}