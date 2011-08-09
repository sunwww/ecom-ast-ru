<%@ page import="java.io.Reader" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.LineNumberReader" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="ru.ecom.ejb.services.file.IJbossGetFileService" %>
<%@ page import="ru.ecom.web.util.Injection" %>
<%--
  @author ikouzmin 19.03.2007 16:26:01
  
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>Журнал импорта</title></head><body>
  <!--a href="javascript:history.back(1)">Назад</a><br/-->
  <%
      //String fileName = (String) session.getAttribute("logfile");
      IJbossGetFileService fileService = (IJbossGetFileService) Injection.find(request).getService(IJbossGetFileService.class);
      String fileIdString = request.getParameter("file");
      long fileId = 0;
      try {
          fileId = new Long(fileIdString).longValue();
      } catch (Exception e) {
          out.write(e.getMessage());
          e.printStackTrace();
          return;
      }
      
      String fileName = fileService.getAbsoluteFilename(fileId);

      LineNumberReader in = new LineNumberReader(new FileReader(fileName));

      out.write("<pre>\n");
      while (in.ready()) {
          String s = in.readLine();

          s = s.replace("><", ">\n<");
          s = s.replace("&", "&amp;");
          s = s.replace("<", "&lt;");
          s = s.replace(">", "&gt;");
          if (s.length() > 23 && s.charAt(23) == ':') {
              s = s.substring(11);
          }
          if (s.indexOf("Импорт таблицы") >= 0) {
              if (s.indexOf("завершен") >= 0) {
                  int i = s.indexOf("'");
                  int j = s.indexOf("'", i + 1);
                  s = "<a name='rpt_" + s.substring(i + 1, j) + "'></name>" + s;

              } else {
                  int i = s.indexOf("class");

                  if (i > 0) {
                      String st = s.substring(i + 6);
                      int j = st.lastIndexOf(".");
                      s = "<a name='" + st.substring(j + 1) + "'></name>" +
                              "<h2>" + s.substring(0, i) + "</h2><h3>" + s.substring(i) + "</h3>";
                  } else {
                      s = "<h2>" + s + "</h2>";
                  }
              }

          } else if (s.indexOf("Обновлено с ID:") >= 0 || s.indexOf("==&gt;") > 0) {
              s = "<font color='blue'><b>" + s + "</b></font>";
          } else if (s.indexOf("Запись  добавлена") >= 0) {
              s = "<font color='green'><b>" + s + "</b></font>";
          } else if (s.indexOf("Загружено:") > 0 ||
                  s.indexOf("Добавлено:") > 0 || s.indexOf("Обновлено:") > 0) {
              s = "<b>" + s + "</b>";
          } else if (s.indexOf("Ошибка верификации:") > 0) {
              s = "<font color='red' size='+1'><b>" + s + "</b></font>";
          } else if (s.indexOf("Импортируемые сущности:") > 0) {
              int j = s.indexOf("ти:");
              String[] strings = s.substring(j + 3).split("[: ]+");
              StringBuilder sb = new StringBuilder("\nИмпортируемые сущности:     ");
              for (int i = 1; i < strings.length; i++) {
                  if (i > 1) sb.append(" | ");
                  sb.append("<a href='#" + strings[i] + "'>" + strings[i] + "</a>" +
                          " <a href='#rpt_" + strings[i] + "'>(отчет)</a>");
              }
              s = sb.toString();
          }

          out.write(s + "\n");
      }
      out.write("</pre>\n");

  %>
</body></html>