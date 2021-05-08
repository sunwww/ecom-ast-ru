package ru.nuzmsh.web.filter.caching;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class PrintServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException {
        OutputStream out = aResponse.getOutputStream();
        File file = new File(aRequest.getPathTranslated());
        if (file.exists()) {
            aResponse.setHeader("Content-Length", String.valueOf(file.length()));
            byte[] buf = new byte[(int) file.length()];


            try (FileInputStream in = new FileInputStream(file)) {
                in.read(buf);
                out.write(buf);
            }
        } else {
            throw new FileNotFoundException(aRequest.getRequestURI());
        }
    }


}
