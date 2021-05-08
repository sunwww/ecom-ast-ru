package ru.ecom.report.rtf;

import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.ReplaceHelper;

import java.io.*;

public class RtfPrintFileDriver implements IPrintFileDriver {

    private String theId;
    private File theTemplateDir;
    private String theKey;
    private File theWorkDir;

    public IPrintFileDriver newInstance(String id, File templateDir, String key, String theWorkDir) {
        RtfPrintFileDriver d = new RtfPrintFileDriver();
        d.theId = id;
        d.theTemplateDir = templateDir;
        d.theKey = key;
        d.theWorkDir = new File(theWorkDir);
        d.theLogin = "undefened";
        return d;
    }

    public boolean isRtfReplaceMode() {
        return true;
    }

    public void buildFile() {
    }

    public void buildFile(boolean aRemovedTempFile) {
    }

    public File getInputFile() {
        return new File(theTemplateDir, theKey + ".rtf");
    }

    public File getOutputFile() {
        return new File(theWorkDir, getResultFilename());
    }

    public String getResultFilename() {
        return theKey + "-" + theLogin + "-" + theId + ".rtf";
    }

    public boolean isAccept(File aDir, String aKeyName) {
        return new File(aDir, aKeyName + ".rtf").exists();
    }


    public void prepare() {
    }

    public String getEncoding() {
        return "Cp1251";
    }

    public void print(ReplaceHelper aReplaceHelper, IValueGetter aValueGetter) throws RtfPrintException {
        try (LineNumberReader in = new LineNumberReader(new InputStreamReader(new FileInputStream(getInputFile()), getEncoding()));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(getOutputFile()), getEncoding()))
        ) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            String outStr = aReplaceHelper.replaceWithValues(sb.toString(), aValueGetter).toString();
            out.println(outStr.replace("\\par", "\n\\par"));
        } catch (Exception e) {
            throw new RtfPrintException("Ошибка печати", e);
        }
    }

    /**
     * Логин
     */
    public String getLogin() {
        return theLogin;
    }

    public void setLogin(String aLogin) {
        theLogin = aLogin;
    }

    /**
     * Логин
     */
    private String theLogin;
}
