<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="ru.ecom.web.login.LoginErrorMessage" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ru-RU" xml:lang="ru-RU">
<head>
    <title>МедОС</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <%@ include file="/WEB-INF/tiles/libscache.jsp" %>
    <%
    int idimg = (int)(17*Math.random()) ;
    request.setAttribute("idimg", ""+idimg) ;
    Calendar cal = Calendar.getInstance() ;
    int month = cal.get(Calendar.MONTH) ;
    int day = cal.get(Calendar.DAY_OF_MONTH) ;
    String path_curdate = "" ;
    switch (month) {
    case Calendar.JANUARY: 
    	if (day<10) {path_curdate="0101";break;}
    	if (day==13) {path_curdate="0113";break;}
    case Calendar.FEBRUARY:
    	if (day==14) {path_curdate="0214";break;}
    	if (day>20 && day<24) {path_curdate="0223";break;}
    case Calendar.MARCH:
    	if (day==1) {path_curdate="0301";break;}
    	if (day==5) {path_curdate="0305";break;}
    	if (day>5 && day<9) {path_curdate="0308";break;}
    case Calendar.APRIL:
    	if (day==1) {path_curdate="0401";break;}
    	if (day==12) {path_curdate="0412";break;}
    case Calendar.MAY:
    	if (day==1) {path_curdate="0501";break;}
    	if (day>5 && day<10) {path_curdate="0509";break;}
    case Calendar.JUNE:
    	if (day==1) {path_curdate="0601";break;}
    	int mc = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    	if (mc==3 &&cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY) {
    		if (day==1) {path_curdate="06medic";break;}
    	}
    case Calendar.DECEMBER:
    	if (day>20) {path_curdate="1231";break;}
    default:
    	path_curdate="default" ;
    	
    }
    request.setAttribute("curdate_MMdd", ""+path_curdate) ;
    %>
	<style type="text/css">
		body {
			background: url("/customer/images/${curdate_MMdd}/${idimg}.jpg") no-repeat 90px bottom ;
		}
		#header {
			border-bottom: none ;
		}
	</style>

</head>

<body>
<div id='header'>
    <h1>МедОС</h1>
    <a href="#1">
        <img src='/customer/images/main/logo-75x50.jpg' width='75' height="50"
             alt='На главную' title='Переход на основную страницу'/>
    </a>
</div>

<div id="content" style='margin-left: 1em'>

    <table width='90%'><tr><td width='60%' style='vertical-align: top'>
    </td><td width='40%'>

        <!-- Форма входа -->
        <%

            String error = LoginErrorMessage.getMessage(request);

            if (error != null) {
        %>
        <style type="text/css" xml:space="preserve">
            form table {
                border: 1px solid red;
            }

            span.error {
                color: red;
                font-weight: bold;
            }
        </style>
        <%
            }
        %>


        <div id='login' style='padding: 1em '>
            <msh:form action="/ecom_loginSave" defaultField="username" title='Вход в систему'>
                <msh:hidden property="next"/>
                <table>
                    <%
                        if (error != null) {
                    %>
                    <tr><td colspan='2'><span style='color: red'><%=error%></span></td></tr>
                    <%
                        }
                    %>

                    <tr>
                        <msh:textField property="username" size="20" label="Пользователь:"/>
                    </tr>
                    <tr>
                        <msh:textField property="password" passwordEnabled="true" size="20" label='Пароль'/>
                    </tr>
                    <tr>
                        <td align="right" colspan='2'>
                            <input class='default' type="submit" value='Войти' id='enter'/>
                        </td>
                    </tr>
                </table>
            </msh:form>
        </div>

        <!-- Форма входа END -->

    </td></tr></table>
</div>

<div id="footer">
    <a id='1' href='#2'></a>

    <div id='copyright'>&copy; МедОС (v. <%@ include file="/WEB-INF/buildnumber.txt" %> )</div>
</div>

    <script type="text/javascript">
        $('username').focus() ;
    </script>


</body>
</html>
