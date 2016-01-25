<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
<%
String login = LoginInfo.find(request.getSession(true)).getUsername();
request.setAttribute("login", login);

%>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Заявки в техподдержку</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
           
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
    <ecom:webQuery name="claimList" nativeSql="
    select cl.id as clid, upat.patientinfo, cl.description
    , to_char(cl.createdate, 'dd.MM.yyyy') ||' '||to_char(cl.createtime,'HH24:MI') as crdatetime
,case when cl.canceldate is not null then 'Отменена'
 when cl.finishdate is not null then 'Выполнено'
 when cl.startworkdate is not null then 'В работе '
 when cl.viewdate is not null then 'В процессе назначения'
 when cl.createdate is not null then 'Новая'
 else 'ВАХВАХ' end as status
 ,cl.id||':'||vct.id as idvocid
,case when cl.canceldate is null then cl.id else null end as btnCancel
,case when cl.finishdate is null and cl.canceldate is null then cl.id else null end as btnFinish
,case when cl.startworkdate is null and cl.finishdate is null and cl.canceldate is null then cl.id||':'||cl.claimtype else null end as btnStartWork
,case when cl.viewdate is null then cl.id else null end as btnView

from claim cl
left join workfunction uwf on uwf.id=cl.workfunction
left join worker uw on uw.id=uwf.worker_id
left join patient upat on upat.id=uw.person_id

left join vocclaimtype vct on vct.id=cl.claimtype
left join workfunctionclaimtype wfct on wfct.claimtype=vct.id
left join workfunction gwf on gwf.id=wfct.workfunction
left join workfunction pwf on pwf.group_id=gwf.id
left join secuser su on su.id=pwf.secuser_id
where su.login='${login}'
order by cl.createdate , cl.createtime 
"/>
        <msh:table name="claimList" action="entityView-mis_claim.do" idField="1">
            <msh:tableColumn columnName="Пользователь" property="2" />
            <msh:tableColumn columnName="Заявка" property="3" />
            <msh:tableColumn columnName="Дата и время создания" property="4" />
            <msh:tableColumn columnName="Статус" property="5" />
            <msh:tableButton hideIfEmpty="true" property="10" buttonFunction="setView" buttonShortName='Просмотрено' buttonName="Просмотрено" />
            <msh:tableButton hideIfEmpty="true" property="9" buttonFunction="setStartWork" buttonShortName="Передать в работу" buttonName="Передать в работу"/>
            <msh:tableButton hideIfEmpty="true" property="7" buttonFunction="setCancel" buttonShortName="Отменить" buttonName="Отменить"/>
            <msh:tableButton hideIfEmpty="true" property="8" buttonFunction="setFinish" buttonShortName="Выполнено" buttonName="Выполнено"/>
        </msh:table>
        <tags:mis_claimStart name="New" status="id" />
    </tiles:put>
    
    <tiles:put name='javascript' type='string'>
    <script type='text/javascript' src='./dwr/interface/ClaimService.js'></script>
    <script type='text/javascript'>
    
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
    function setStatus(aIds, aStatus) {
    	var arr = aIds.split(':');
    	$('NewClaimId').value = ''+arr[0];
    	if (arr.length>1) {
    		$('NewClaimType').value = ''+arr[1];
    	}
    	$('NewClaimStatus').value = aStatus;
    	showNewClaimStart();
    }
    
   
    </script>
</tiles:put>
</tiles:insert>