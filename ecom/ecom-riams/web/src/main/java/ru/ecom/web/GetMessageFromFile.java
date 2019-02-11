package ru.ecom.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
/**
 * Created by rkurbanov on 05.06.2018.
 */
public class GetMessageFromFile extends HttpServlet {

    private String message;

    public void init() throws ServletException {
        message = "Hello World";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //response.setContentType("text/html");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,origin, content-type, accept, authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods","GET");

        String paramValue = request.getParameter("username");
        String path = new GetMessageFromFile().getClass().getResource("/../../").getFile();
        path= path.replace("%20"," ");

        String file = ReadFile(path+"/WEB-INF/message.txt");
        file= file.replace("[username]",paramValue);
        response.getWriter().write(file);
    }

    public void destroy() {
        // do nothing.
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

