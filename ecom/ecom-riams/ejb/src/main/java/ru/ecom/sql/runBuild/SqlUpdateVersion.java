package ru.ecom.sql.runBuild;


//import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by rkurbanov on 11.07.2018.
 */
public class SqlUpdateVersion {
    //private static final Logger LOG = Logger.getLogger(SqlUpdateVersion.class);

    public static void main(String[] args) throws IOException {

        String path = SqlUpdateVersion.class.getResource("/").toString();
        path= path.replace("%20"," ");
        path= path.replace("file:/","");
        path= path.replace("target/sqlUpdater","src/main/resources/riams-ejb_jar/META-INF/sql");
        //LOG.info(path);

        List<File> list = getResourceFiles(path);

        for(File f:list){
            String filename= f.getName();
            if(filename.contains(".sql") || filename.contains(".SQL")){

                String file = readFile(path+"/"+f.getName());
                if(file.contains("{version}")){
                    try (FileOutputStream fileOut = new FileOutputStream(path+"/"+f.getName())){
                        //LOG.info(filename+" - is update");
                        file =file.replace("{version}",""+getUnixTime()+"#");
                        fileOut.write(file.getBytes());
                    } catch (Exception e) {
                        //LOG.error(e);
                    }

                }
            }
        }
    }
    private static List<File> getResourceFiles(String path)  {
        File dir = new File(path); //path указывает на директорию
        File[] arrFiles = dir.listFiles();
        return Arrays.asList(arrFiles);
    }

    private static int getUnixTime(){
        Date now = new Date();
        Long longTime = now.getTime()/1000;
        return longTime.intValue();
    }

    private static String readFile(String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        try ( FileReader reader = new FileReader(fileName)) {
            int c;
            while((c=reader.read())!=-1){
                stringBuilder.append((char)c);
            }
        }
        catch(IOException ex){
           //LOG.error(ex.getMessage());
        }
        return stringBuilder.toString();
    }
}
