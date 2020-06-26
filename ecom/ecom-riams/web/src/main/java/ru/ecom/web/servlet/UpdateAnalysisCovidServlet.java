package ru.ecom.web.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UpdateAnalysisCovidServlet extends HttpServlet {
    private ServletFileUpload uploader = null;

    @Override
    public void init() {
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE");
        fileFactory.setRepository(filesDir);
        this.uploader = new ServletFileUpload(fileFactory);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doPost(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            List<FileItem> fileItemsList = uploader.parseRequest(request);
            Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
            while (fileItemsIterator.hasNext()) {
                FileItem fileItem = fileItemsIterator.next();

                try (BufferedReader br = new BufferedReader(new InputStreamReader(fileItem.getInputStream(), "UTF-8"));) {
                    String line = "";
                    String cvsSplitBy = ";";
                    List<PojoAnalysis> list = new ArrayList<>();
                    while ((line = br.readLine()) != null) {
                        PojoAnalysis pojoAnalysis = new PojoAnalysis(line.split(cvsSplitBy));
                        list.add(pojoAnalysis);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception fne) {
            fne.printStackTrace();
        }
    }
}
