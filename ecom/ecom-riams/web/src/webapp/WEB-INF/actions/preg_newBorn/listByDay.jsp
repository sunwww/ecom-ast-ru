<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="javax.naming.Context"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="java.util.List"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.Collection"%>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">




    <%
    String date = request.getParameter("date");
    if (date==null||date.equals("")) {
    	date = " nb.birthdate = current_date";
    } else if (date.equals("-1")){
    	date=" nb.birthdate = current_date-1";
    } else if (date.split("-").length>1) {
    	date=" nb.birthdate between to_date('"+date.split("-")[0]+"','dd.MM.yyyy') and to_date('"+date.split("-")[1]+"','dd.MM.yyyy')"; 
    } else {
    	date=" nb.birthdate = to_date('"+date+"','dd.MM.yyyy')";
    }
    request.setAttribute("dateSql", date!=null?date:"");
    %>
     
    <ecom:webQuery name="list" nativeSql="
select to_char(nb.birthdate, 'dd.MM.yyyy') as bDate
,count (case when vs.omccode='1' then nb.id else null end) as cntMale
,count (case when vs.omccode='2' then nb.id else null end) as cntFeMale
from newBorn nb
left join vocSex vs on vs.id=nb.sex_id
left join vocLiveBorn vlb on vlb.id=nb.liveborn_id
where ${dateSql} and vlb.code='1'  
group by nb.birthdate
order by nb.birthdate

"/>

<%
List l = (List) request.getAttribute("list");
out.print("newBornSize="+l.size()+";");

if (l!=null&&!l.isEmpty()) {
	int size=l.size();
	request.setAttribute("listSize",""+size);	
	//out.print("listSize="+size+";");	
		for (int i=0; i<size; i++) {
			WebQueryResult wqr = (WebQueryResult) l.get(i) ;
			//out.print("===="+wqr.get2());
		//request.setAttribute("birthDate"+(i+1),""+wqr.get1());
		out.print("birthDate="+wqr.get1()+";");
		//request.setAttribute("birthMale"+(i+1),""+wqr.get2());
		out.print("birthMale="+wqr.get2()+";");
		//request.setAttribute("birthFeMale"+(i+1),""+wqr.get3());
		out.print("birthFeMale="+wqr.get3()+"");
	
		
	}
} else {
	
}
%>
</tiles:insert>