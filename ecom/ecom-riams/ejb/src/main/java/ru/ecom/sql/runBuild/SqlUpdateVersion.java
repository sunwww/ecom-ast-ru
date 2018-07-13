package ru.ecom.sql.runBuild;


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

    public static void main(String[] args) throws IOException {

        //System.out.println("Hello");
        String path = new SqlUpdateVersion().getClass().getResource("/").toString();
        path= path.replace("%20"," ");
        path= path.replace("file:/","");
        path= path.replace("target/sqlUpdater","src/main/resources/riams-ejb_jar/META-INF/sql");
        System.out.println(path);

        List<File> list = getResourceFiles(path);

        for(File f:list){
            String filename= f.getName().toString();
            if(filename.contains(".sql") || filename.contains(".SQL")){

                String file = ReadFile(path+"/"+f.getName());
                if(file.contains("{version}")){

                    System.out.println(filename+" - is update");
                    file =file.replace("{version}",""+getUnixTime()+"#");
                    FileOutputStream fileOut = new FileOutputStream(path+"/"+f.getName());
                    fileOut.write(file.getBytes());
                    fileOut.close();
                }
            }
        }
    }
    private static List<File> getResourceFiles(String path) throws IOException {
        File dir = new File(path); //path указывает на директорию
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);
        return lst;
    }

    private static int getUnixTime(){
        Date now = new Date();
        Long longTime = new Long(now.getTime()/1000);
        return longTime.intValue();
    }

    public static String ReadFile(String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        try
        {
            FileReader reader = new FileReader(fileName);
            int c;
            while((c=reader.read())!=-1){
                stringBuilder.append((char)c);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return stringBuilder.toString();
    }
}
