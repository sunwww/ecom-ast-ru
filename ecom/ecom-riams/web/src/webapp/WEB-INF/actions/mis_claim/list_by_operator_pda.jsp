<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true" >
<%
String typeStatus =ActionUtil.updateParameter("ClaimList","typeStatus","1", request) ;
String typeUser =ActionUtil.updateParameter("ClaimList","typeUser","1", request) ;
String typeDate =ActionUtil.updateParameter("ClaimList","typeDate","1", request) ;
String beginDate = request.getParameter("beginDate");
String endDate = request.getParameter("endDate");
String login = LoginInfo.find(request.getSession(true)).getUsername();
request.setAttribute("login", login);
String statusSql = " ";
if (beginDate!=null&&!beginDate.equals("")) {
	
	if (typeDate.equals("1")) {typeDate="cl.createdate";}
	else if (typeDate.equals("2")) {typeDate="cl.viewdate";}
	else if (typeDate.equals("3")) {typeDate="cl.startworkdate";}
	else if (typeDate.equals("4")) {typeDate="cl.finishdate";}
	else if (typeDate.equals("5")) {typeDate="cl.canceldate";}
	if (endDate==null|| endDate.equals("")) {
		statusSql +=" and "+typeDate+ ">= to_date('"+beginDate+"','dd.MM.yyyy')";
	} else {
		statusSql += " and "+typeDate+" between to_date('"+beginDate+"','dd.MM.yyyy') and to_date('"+endDate+"','dd.MM.yyyy')";
	}
} else {
	statusSql +=" and cl.createdate <=current_date";
}
if (typeStatus==null) {
	statusSql = " nulla";
} else if (typeStatus.equals("1")) {
	statusSql += " and cl.viewDate is null and cl.finishDate is null and cl.cancelDate is null and cl.startworkdate is null";
} else if (typeStatus.equals("2")) {
	statusSql += " and (cl.viewDate is not null and cl.startworkdate is null and cl.canceldate is null and cl.finishdate is null)";
} else if (typeStatus.equals("3")) {
	statusSql += " and cl.finishdate is null and cl.canceldate is null and cl.startworkdate is not null";
} else if (typeStatus.equals("5")||typeStatus.equals("6")||typeStatus.equals("7")) {
	statusSql += " and cl.finishDate is not null";
	if (typeStatus.equals("6")) {
		statusSql +=" and cl.completeConfirmed='1'";
	} else if (typeStatus.equals("7")) {
		statusSql +=" and cl.completeConfirmed='0'";
	}
} else if (typeStatus.equals("4")) {
	statusSql += " and cl.canceldate is not null";
}
	//if (typeUser!=null&&typeUser.equals("2")) {
		statusSql += " and cl.startworkusername ='"+login+"'";
	//}
	request.setAttribute("statusSql", statusSql);
%>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Заявки в техподдержку</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
           
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
   <msh:form  action="/pda_claims.do" defaultField="typeStatus" disableFormDataConfirm="true" >
  <msh:panel>

    <msh:separator label="Параметры поиска" colSpan="7" />
      
   
    <msh:row>
        <td class="label" title="Статус заявки (typeStatus)" colspan="1"><label for="typeStatusName" id="typeStatusLabel">Статус:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();">
        	<input type="radio" name="typeStatus" value="1">  Новая
        </td>
	    <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
	     	<input type="radio" name="typeStatus" value="2">  К назначению 
	    </td>
	    <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
	     	<input type="radio" name="typeStatus" value="3">  В работе 
	    </td>
	    <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
	     	<input type="radio" name="typeStatus" value="4">  Выполненные
	    </td>
	    <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
	     	<input type="radio" name="typeStatus" value="5">  Отмененные
	    </td>	        
	    <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
	     	<input type="radio" name="typeStatus" value="6">  Все
	    </td>	        
       </msh:row>

<%--     <msh:row>
        <td class="label" title="Заявки (typeUser)" colspan="1"><label for="typeUserName" id="typeUserLabel">Заявки:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();">
        	<input type="radio" name="typeUser" value="1">  Все заявки
        </td>
	    <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
	     	<input type="radio" name="typeUser" value="2">  Только мои 
	    </td>
	  </msh:row>
	  --%> 
	    <%-- <msh:row>
        <td class="label" title="Искать по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();">
        	<input type="radio" name="typeDate" value="1">  Создания
        </td>
	    <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
	     	<input type="radio" name="typeDate" value="2">  Прочтения
	    </td>
	    <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
	     	<input type="radio" name="typeDate" value="3">  Начала работы
	    </td>
	    <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
	     	<input type="radio" name="typeDate" value="4">  Выполнения
	    </td>
	    <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
	     	<input type="radio" name="typeDate" value="5">  Отмены
	    </td>	        
	    </msh:row>  --%>
	  
    </msh:panel>
    </msh:form> 
    <ecom:webQuery name="claimList" nameFldSql="claimListSql" nativeSql="
    select cl.id as clid, vwf.name ||' '|| upat.lastname ||' '|| upat.firstname||' '||upat.middlename, cl.description
  , to_char(cl.createdate, 'dd.MM.yyyy') ||' '||to_char(cl.createtime,'HH24:MI') as crdatetime
,case when cl.canceldate is not null then 'Отменена ' || to_char(cl.canceldate, 'dd.MM.yyyy')||' '||to_char(cl.canceltime,'HH24:MI')
 when cl.finishdate is not null then 'Выполнена ' || to_char(cl.finishdate, 'dd.MM.yyyy')||' '||to_char(cl.finishtime,'HH24:MI')
 when cl.startworkdate is not null then 'В работе ' || to_char(cl.startworkdate, 'dd.MM.yyyy')||' '||to_char(cl.startworktime,'HH24:MI') ||' '||cl.startworkusername
 when cl.viewdate is not null then 'В процессе назначения ' || to_char(cl.viewdate, 'dd.MM.yyyy')||' '||to_char(cl.viewtime,'HH24:MI')
 when cl.createdate is not null then 'Новая'
 else 'ВАХВАХ' end as status
 ,cl.id||':'||vct.id as idvocid
,case when cl.canceldate is null then cl.id else null end as btnCancel
,case when cl.finishdate is null and cl.canceldate is null then cl.id else null end as btnFinish
,case when cl.startworkdate is null and cl.finishdate is null and cl.canceldate is null then cl.id||':'||cl.claimtype else null end as btnStartWork
,case when cl.viewdate is null and cl.canceldate is null and cl.finishdate is null then cl.id else null end as btnView
, cl.phone
,case when cl.canceldate is not null then 'height:50px; background-color:#F3F781; color:black; '
 when cl.finishdate is not null then 'height:50px; background-color:#81F781; color:black;'
 when cl.startworkdate is not null then 'height:50px; background-color:#F78181; '
 when cl.viewdate is not null then ''
 when cl.createdate is not null then ''
 else 'ВАХВАХ' end as color_status
,cl.address as address
,case when cl.startworkusername = '${login}' then cl.id else null end as btnComment
,coalesce(cl.executorcomment,'') as comment
from claim cl
left join workfunction uwf on uwf.id=cl.workfunction
left join vocworkfunction vwf on vwf.id=uwf.workfunction_id
left join worker uw on uw.id=uwf.worker_id
left join patient upat on upat.id=uw.person_id

left join vocclaimtype vct on vct.id=cl.claimtype
left join workfunctionclaimtype wfct on wfct.claimtype=vct.id
left join workfunction gwf on gwf.id=wfct.workfunction
left join workfunction pwf on pwf.group_id=gwf.id
left join secuser su on su.id=pwf.secuser_id
where su.login='${login}' 
${statusSql} 

order by cl.createdate , cl.createtime
"/>
        <msh:table styleRow="12" name="claimList" action="js-mis_claim-pda.do" idField="1">
            <msh:tableColumn columnName="Пользователь" property="2" />
            <msh:tableColumn columnName="Заявка" property="3" />
            <msh:tableColumn columnName="Телефон" property="11" />
            <msh:tableColumn columnName="Место" property="13" />
            <msh:tableColumn columnName="Дата и время создания" property="4" />
            <msh:tableColumn columnName="Статус" property="5" />
        </msh:table>
        <tags:mis_claimStart name="New" status="id" /> <!-- vocName='executorsByCurrentUserName' /> -->
    
    <script type='text/javascript'>
    function checkfrm() {
    	document.forms[0].submit() ;
    }
    </script>
    </tiles:put>

</tiles:insert>