<%--
  Предварительный просмотр результатов экспорта
  @author ikouzmin 01.03.2007 17:53:02
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<PRE>
<%
    String xresult = (String) request.getAttribute("xresult");
    xresult = xresult.replace("><",">\n<");
    xresult = xresult.replace(">\n</","></");
    xresult = xresult.replace("&","&amp;");
    xresult = xresult.replace("<","&lt;");
    xresult = xresult.replace(">","&gt;");
    out.println(xresult);
    request.removeAttribute("xresult");
%>
</PRE>
