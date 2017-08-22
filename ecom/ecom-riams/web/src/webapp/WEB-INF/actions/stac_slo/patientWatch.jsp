<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по пациентам под наблюдением на дежурстве</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
      
    </tiles:put>
    <tiles:put name="body" type="string">
     <msh:form action="/patientWatch.do" defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
       <msh:row>   
       <msh:textField property="dateBegin"/> 
       </msh:row> 
      <msh:row>  
      <tr></tr> <td></td>
      <td colspan="1">   
              <input type="submit" onclick="find()" value="Найти" />
          </td> 
      </msh:row>  
      <msh:row> 
       <msh:separator label="Результат" colSpan="7"/> 
      </msh:row>  
      </msh:panel>
    </msh:form>
     <%
    String dateBegin=request.getParameter("dateBegin") ;
    String depCode=request.getParameter("id") ;
         if (dateBegin!=null & dateBegin!="") {
    	 if (depCode==null || depCode=="") {
             Calendar rightNow = Calendar.getInstance();
             int hour = rightNow.get(Calendar.HOUR_OF_DAY);
             if (hour <= 12 && hour >= 0) {//12 часов - ночь с 00 до 12 утра следующего дня - ночная смена - беру пред. дату
                 String strday = dateBegin.substring(0, 2);
                 int day = Integer.parseInt(strday);
                 int prev = day - 1;
                 dateBegin = dateBegin.replace(String.valueOf(day), String.valueOf(prev));
                 if (dateBegin.length() < 10) dateBegin = dateBegin.replace("0" + dateBegin, dateBegin);
                 request.setAttribute("dateBegin", dateBegin);
             }
	    	%>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Journal/ByHospital">
	    	<ecom:webQuery name="patList" nativeSql="select dep.id,dep.name,count(pw.id)
			from MisLpu dep left join patientwatch pw on
			dep.id=(select m.department_id from medcase m where m.id=pw.medcase_id)
			where dep.omccode='1' and pw.listwatch_id=(select lw.id from listwatch lw
			where lw.datewatch=to_date('${param.dateBegin}','dd.mm.yyyy'))
			group by dep.id,dep.name
			"/>
	    	 <msh:table hideTitle="false" styleRow="2" idField="1" name="patList" action="patientWatch.do?dateBegin=${param.dateBegin}&depCode=${param.id}"
	    	  viewUrl="patientWatch.do?dateBegin=${param.dateBegin}&depCode=${param.id}">

	                    <msh:tableColumn columnName="Код" property="1"/>
	                    <msh:tableColumn columnName="Наименование отделения" property="2"/>
	                    <msh:tableColumn columnName="Количество пациентов" property="3"/>
	                </msh:table>
        </msh:ifNotInRole>
        <%@page import="ru.ecom.web.login.LoginInfo"%>
        <%
            String username = LoginInfo.find(request.getSession()).getUsername();
            request.setAttribute("username", username);
        %>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ByHospital">
            <ecom:webQuery name="patList" nativeSql="select p.lastname,p.firstname,p.middlename,st.code, dep.name
            from patientwatch pw
            left join listwatch lw on lw.id=pw.listwatch_id
            left join medcase mc on mc.id=pw.medcase_id
            left join patient p on p.id=mc.patient_id
            left join statisticstub st on st.medcase_id=mc.id
            left join mislpu dep on dep.id=mc.department_id
            left join worker w on w.lpu_id=dep.id
            left join workfunction wf on wf.worker_id=w.id
            where lw.datewatch=to_date('${param.dateBegin}','dd.mm.yyyy') and wf.secuser_id=(select id from secuser where login='${username}')"/>
            <msh:table hideTitle="false" styleRow="2" idField="1" name="patList" action="javascript:void(0)">
                <msh:tableColumn columnName="Фамилия" property="1"/>
                <msh:tableColumn columnName="Имя" property="2"/>
                <msh:tableColumn columnName="Отчество" property="3"/>
                <msh:tableColumn columnName="Номер карты" property="4"/>
                <msh:tableColumn columnName="Отделение" property="5"/>
            </msh:table>
        </msh:ifInRole>
	    	<%
    		}
	    else {
	    	%>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Journal/ByHospital">
	    	<ecom:webQuery name="patList" nativeSql="select p.lastname,p.firstname,p.middlename,st.code
	    	from patientwatch pw left join listwatch lw on lw.id=pw.listwatch_id left join medcase mc on
	    	mc.id=pw.medcase_id  left join patient p on p.id=mc.patient_id left join statisticstub st
	    	on st.medcase_id=mc.id where lw.datewatch=to_date('${param.dateBegin}','dd.mm.yyyy') and mc.department_id='${param.id}'"/>
	    	 <msh:table hideTitle="false" styleRow="2" idField="1" name="patList" action="javascript:void(0)">
	                    <msh:tableColumn columnName="Фамилия" property="1"/>
	                    <msh:tableColumn columnName="Имя" property="2"/>
	                    <msh:tableColumn columnName="Отчество" property="3"/>
	                    <msh:tableColumn columnName="Номер карты" property="4"/>
	                </msh:table>
        </msh:ifNotInRole>
	    	<%
	    }
    }
    %>
    <script>  
    window.onload = function checkDate() { 
    if (document.getElementById('dateBegin').value=="") {
	   	var date = new Date();  
	    var day = date.getDate();
	    var month = date.getMonth() + 1;
	    var year = date.getFullYear(); 
	    if (month < 10) month = "0" + month;
	    if (day < 10) day = "0" + day; 
	    var today = day + "." + month + "." + year;  
	    document.getElementById('dateBegin').value = today;
 		}
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='patientWatch.do' ;
    }
 </script>  
    </tiles:put> 
 </tiles:insert>