<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
<%
String sho =request.getParameter("short");
request.setAttribute("info_password", ActionUtil.getDefaultDescriptionParameterByConfig("PASSWORDREGEXP", "", request)) ;

if (sho==null ||sho.equals(""))  {%>
    <tiles:put name='title' type='string'>
        <h1>Смена пароля</h1>
    </tiles:put>

<%} 

 if (sho!=null &&sho.equals("Short")){
	out.print("<p align='center' style='color: red; font-size:20'>Срок действия вашего пароля истёк. Для дальнейшей работы в системе необходимо его изменить.</p>"); 
 }
	 %>
 



    <tiles:put name='body' type='string'>
    
    <div align='center'>
    <table>
    	<tr><td align="right">Старый пароль:</td><td><input type='password' id='oldPassword'></td></tr>
        <tr><td align="right">Новый пароль:</td><td><input type='password' id='newPassword'></td></tr>
        <tr><td align="right">Подтвердите новый пароль:</td><td><input type='password' id='newPasswordRetype'></td></tr>
        <tr><td colspan="2" ><input id="btnChangePassword" type='button' onclick='changePassword()'  value ='Сменить пароль' ></td></tr>
</table>
        <h2 style="color: red; font-size: xx-large; " align="center"><pre>${info_password}</pre></h2>

</div>
   
    <script type='text/javascript' src='./dwr/interface/RolePoliciesService.js'></script>
        <script type="text/javascript">
        
        	$('shortContentClose').style.display='none';
        	//alert('as');
       
function changePassword () {
	if ($('newPassword').value=='') {
		alert ("Введите пароль!");
		return;
	}
	if ($('newPassword').value==$('newPasswordRetype').value) {
		if ($('oldPassword').value!='') {
			$('btnChangePassword').disabled=true;
			$('btnChangePassword').value="Подождите...";
			
			RolePoliciesService.changePassword($('newPassword').value,$('oldPassword').value, {
				callback: function (aResult) {
					alert (aResult.substring(1));
					if (aResult.substring(0,1)==("1")) {
						window.location = "ecom_relogin.do?next=/start.do";
					} else {
						$('btnChangePassword').disabled=false;
						$('btnChangePassword').value="Сменить пароль";
					}
				}
			});
	} else {
		alert ('Введите текущий пароль!');
	}	
	} else {
		alert ('Введенные пароли не совпадают!');
	}

}
function getPasswordAge(){
	RolePoliciesService.getPasswordAge({
	callback: function (aResult) {
		if (aResult!=null&&aResult!='null'){
		if (+aResult>0&&+aResult<10){
			alert("Надо бы поменять пароль, осталось "+aResult+" дней");
		} else if (+aResult==0) {
			alert ("Ахтунг, пароль кончился!!!");
		} else {
			alert ("Всё ништяк, чин чинарем!! Паролю еще "+aResult+ "дней");
		}
	}
	}
});
}

        </script>
    </tiles:put>


</tiles:insert>