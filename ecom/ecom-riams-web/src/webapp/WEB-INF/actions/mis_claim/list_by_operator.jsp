<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
<%
String typeStatus =ActionUtil.updateParameter("ClaimList","typeStatus","1", request) ;
String typeUser =ActionUtil.updateParameter("ClaimList","typeUser","1", request) ;
String typeDate =ActionUtil.updateParameter("ClaimList","typeDate","1", request) ;
String beginDate = request.getParameter("beginDate");
String endDate = request.getParameter("endDate");
String login = LoginInfo.find(request.getSession(true)).getUsername();
String executorUserName = request.getParameter("number");
String searchField = request.getParameter("searchField");
request.setAttribute("login", login);
String statusSql = " ", orderBySql=" cl.createdate , cl.createtime";




if (beginDate!=null&&!beginDate.equals("")) {
	
	if (typeDate.equals("1")) {typeDate="cl.createdate";}
	else if (typeDate.equals("2")) {typeDate="cl.viewdate";}
	else if (typeDate.equals("3")) {typeDate="cl.startworkdate";}
	else if (typeDate.equals("4")) {typeDate="cl.finishdate";}
	else if (typeDate.equals("5")) {typeDate="cl.canceldate";}

	if (typeDate.length()>1) {
		orderBySql = typeDate;
	}
	
	if (endDate==null|| endDate.equals("")) {
		statusSql +=" and "+typeDate+ ">= to_date('"+beginDate+"','dd.MM.yyyy')";
	} else {
		statusSql += " and "+typeDate+" between to_date('"+beginDate+"','dd.MM.yyyy') and to_date('"+endDate+"','dd.MM.yyyy')";
	}
} else {
	statusSql +=" and cl.createdate <=current_date";
}

if (executorUserName!=null&&!executorUserName.equals("")) {
	statusSql +=" and cl.startWorkUserName='"+executorUserName+"'";
}
if (searchField!=null&&!searchField.equals("")&&searchField.length()>3) {
	statusSql +=" and upper(cl.description) like upper('%"+searchField+"%')";
}
	if (typeStatus==null) {
		statusSql = " nulla";
	} else if (typeStatus.equals("1")) {
		statusSql += " and cl.viewDate is null and cl.finishDate is null and cl.cancelDate is null and cl.startworkdate is null";
	} else if (typeStatus.equals("2")) {
		statusSql += " and (cl.viewDate is not null and cl.startworkdate is null)";
	} else if (typeStatus.equals("3")) {
		statusSql += " and cl.finishdate is null and cl.canceldate is null and cl.startworkdate is not null";
	} else if (typeStatus.equals("4")) {
		statusSql += " and cl.finishDate is not null";
	} else if (typeStatus.equals("5")) {
		statusSql += " and cl.canceldate is not null";
	}
	if (typeUser!=null&&typeUser.equals("2")) {
		statusSql += " and cl.startworkusername ='"+login+"'";
	}
	request.setAttribute("statusSql", statusSql);
	request.setAttribute("orderBySql", orderBySql);
%>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Заявки в техподдержку</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
           
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
   <msh:form  action="/all_claims.do" defaultField="typeStatus" disableFormDataConfirm="true" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f" > 
  <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
     
       <a href='js-mis_claim-list_pda.do'><input type='button' value='pda'></a>
    <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
     <msh:row>
     <msh:autoComplete label="Исполнитель" property="number" parentId="${login}" vocName="executorsByCurrentUserName" size="50" fieldColSpan="10"/>  
   </msh:row>
   <msh:row>
   <td>
   	<p>Поиск по тексту заявки:</p>
   </td>
   <td colspan="10">
   	<input type='text' name='searchField' id='searchField' size="50">
   </td></msh:row>
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

    <msh:row>
        <td class="label" title="Заявки (typeUser)" colspan="1"><label for="typeUserName" id="typeUserLabel">Заявки:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkfrm();">
        	<input type="radio" name="typeUser" value="1">  Все заявки
        </td>
	    <td onclick="this.childNodes[1].checked='checked';checkfrm();" colspan="2">
	     	<input type="radio" name="typeUser" value="2">  Только мои 
	    </td>
	  </msh:row>
	  
	   <msh:row>
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
	    </msh:row>
	   <msh:row>
        <msh:textField property="beginDate" label="Период с" />
        <msh:textField property="endDate" label="по" />
           <td>
            <input type="submit" value="Отобразить данные" />
          </td>
          </msh:row>
    </msh:panel>
    </msh:form> 
    <ecom:webQuery name="claimList" nameFldSql="claimListSql" nativeSql="
    select cl.id as clid, vwf.name ||' '|| upat.lastname ||' '|| upat.firstname||' '||upat.middlename, cl.description
  , to_char(cl.createdate, 'dd.MM.yyyy') ||' '||to_char(cl.createtime,'HH24:MI') as crdatetime
,case when cl.canceldate is not null then 'Отменена ' || to_char(cl.canceldate, 'dd.MM.yyyy')||' '||to_char(cl.canceltime,'HH24:MI')
 when cl.finishdate is not null then 'Выполнена ' || to_char(cl.finishdate, 'dd.MM.yyyy')||' '||to_char(cl.finishtime,'HH24:MI')||' '||coalesce (cl.finishusername,cl.startworkusername)
 when cl.startworkdate is not null then 'В работе ' || to_char(cl.startworkdate, 'dd.MM.yyyy')||' '||to_char(cl.startworktime,'HH24:MI') ||' '||cl.startworkusername
 when cl.viewdate is not null then 'В процессе назначения ' || to_char(cl.viewdate, 'dd.MM.yyyy')||' '||to_char(cl.viewtime,'HH24:MI')
 when cl.createdate is not null then 'Новая (создана '||to_char(cl.createdate, 'dd.MM.yyyy')||')'
 else 'ВАХВАХ' end as status
 ,cl.id||':'||vct.id as idvocid
,case when cl.canceldate is null and cl.finishdate is null then cl.id else null end as btnCancel
,case when cl.finishdate is null and cl.canceldate is null then cl.id||':'||cl.claimtype else null end as btnFinish
,case when cl.startworkdate is null and cl.finishdate is null and cl.canceldate is null then cl.id||':'||cl.claimtype else null end as btnStartWork
,case when cl.viewdate is null and cl.canceldate is null and cl.finishdate is null then cl.id||':'||cl.claimtype else null end as btnView
, cl.phone
,case when cl.canceldate is not null then 'font-size:16px; background-color:#F3F781; color:black; '
 when cl.finishdate is not null then 'font-size:16px; background-color:#81F781; color:black;'
 when cl.startworkdate is not null then 'font-size:16px; background-color:#F78181; '
 when cl.viewdate is not null then 'font-size:16px;'
 when cl.createdate is not null then 'font-size:16px;'
 else 'font-size:16px; ВАХВАХ' end as color_status
,cl.address as address
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

order by ${orderBySql}
"/>
<msh:section>
	<msh:tableNotEmpty name="claimList">
	<msh:sectionTitle>
	<form action="print-claim_all_reestr.do" method="post" target="_blank">
    Список заявок
        <input type='hidden' name="sqlText" id="sqlText" value="${claimListSql}">
            <input type='hidden' name="sqlInfo" id="sqlInfo" value="">
                <input type='hidden' name="s" id="s" value="PrintService">
                    <input type='hidden' name="m" id="m" value="printNativeQuery">
                        <input type="submit" value="Печать">
                            </form>
	
	</msh:sectionTitle>
	</msh:tableNotEmpty>

        <msh:table styleRow="12" name="claimList" action="entityView-mis_claim.do" idField="1">
            <msh:tableColumn columnName="Номер заявки" property="1" />
            <msh:tableColumn columnName="Пользователь" property="2" />
            <msh:tableColumn columnName="Заявка" property="3" />
            <msh:tableColumn columnName="Телефон" property="11" />
            <msh:tableColumn columnName="Место" property="13" />
            <msh:tableColumn columnName="Дата и время создания" property="4" />
            <msh:tableColumn columnName="Статус" property="5" />
            <msh:tableColumn columnName="Комментарий исполнителя" property="14" />
            <msh:tableButton hideIfEmpty="true" property="10" buttonFunction="setView" buttonShortName='Просмотрено' buttonName="Просмотрено" />
            <msh:tableButton hideIfEmpty="true" property="9" buttonFunction="setStartWork" buttonShortName="В работу" buttonName="В работу"/>
            <msh:tableButton hideIfEmpty="true" property="7" buttonFunction="setCancel" buttonShortName="Отменить" buttonName="Отменить"/>
            <msh:tableButton hideIfEmpty="true" property="1" buttonFunction="setComment" buttonShortName="Комментарий" buttonName="Комментарий"/>
            <msh:tableButton hideIfEmpty="true" property="8" buttonFunction="setFinish" buttonShortName="Выполнено" buttonName="Выполнено"/>
        </msh:table>
	</msh:section>
        <tags:mis_claimStart name="New" status="id" />
    </tiles:put>
    
    <tiles:put name='javascript' type='string'>
    <script type='text/javascript' src='./dwr/interface/ClaimService.js'></script>
    <script type='text/javascript'>
    
   
     checkFieldUpdate('typeStatus','${typeStatus}',6) ;
    checkFieldUpdate('typeUser','${typeUser}',1) ;
    checkFieldUpdate('typeDate','${typeDate}',1) ;
    
    function checkFieldUpdate(aField,aValue,aDefaultValue) {
	   	eval('var chk =  document.forms[0].'+aField) ;
	   	var aMax=chk.length ;
	   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
	   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
	   		chk[+aDefaultValue-1].checked='checked' ;
	   	} else {
	   		chk[+aValue-1].checked='checked' ;
	   	}
	   }
    function setView (aId) {
    	setStatus(aId, 'View');
    }
    
    function setFinish (aId) {
    	setStatus(aId, 'Finish');
    }
    function setCancel (aId) {
    	setStatus(aId, 'Cancel');
    }
    function setStartWork(aId) {
    	setStatus(aId, 'StartWork')
    }
    function setComment (aId) {
    	var comment = prompt('Введите комментарий');
    	if (comment!=null&&comment!=''){
	    	ClaimService.setComment(aId, comment,{
	    		callback: function(){
	    		window.location.reload();
	    		}
	    	});
    	}
    }
    function setStatus(aIds, aStatus) {
    	var arr = aIds.split(':');
    	$('NewClaimId').value = ''+arr[0];
    	if (arr.length>1) {
    		$('NewClaimType').value = ''+arr[1];
    	}
    	$('NewClaimStatus').value = aStatus;
    	showNewClaimStart();
    }
    function checkfrm() {
    	document.forms[0].submit() ;
    }
   
    </script>
</tiles:put>
</tiles:insert>