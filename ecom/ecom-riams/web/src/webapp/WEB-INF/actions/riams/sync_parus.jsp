<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <h1>Синхронизация с Парусом 7</h1>
    </tiles:put>


    <tiles:put name='body' type='string'>
    	<%
    	String type=request.getParameter("typeR") ;
    	if (type==null || type.equals("")) type="start" ;
    	if (type.equals("start")) {
    		%>
    		<ecom:webQuery name="list" nativeSql="
    		select id,in_date,in_time,inval_date,inval_time
    		 from journalImportp7worker where parent_id is null
    		 order by in_date desc, in_time desc
    		"/>
    		<msh:table action="javascript:void(0)" name="list" idField="1">
    		<msh:tableButton buttonFunction="viewUrl" property="1" addParam="'err'" buttonShortName="ОШИБКИ"/>
    		<msh:tableButton buttonFunction="viewUrl" property="1" addParam="'del'" buttonShortName="УВОЛЕННЫЕ"/>
    		<msh:tableButton buttonFunction="viewUrl" property="1" addParam="'add'" buttonShortName="ПРИНЯТЫЕ"/>
    		<msh:tableButton buttonFunction="viewUrl" property="1" addParam="'adduser'" buttonShortName="ДОБАВЛ. ПОЛ-ЛИ"/>
    		<msh:tableButton buttonFunction="viewUrl" property="1" addParam="'deluser'" buttonShortName="УВОЛ. ПОЛ-ЛИ"/>
    			<msh:tableColumn property="sn" columnName="#"/>
    			<msh:tableColumn property="1" columnName="ИД"/>
    			<msh:tableColumn property="2" columnName="Дата начала запуска"/>
    			<msh:tableColumn property="3" columnName="время"/>
    			<msh:tableColumn property="4" columnName="Дата окончания запуска"/>
    			<msh:tableColumn property="5" columnName="время"/>
    		</msh:table>
    		<%
    	} else if (type.equals("err")) {
    		%>
    		<ecom:webQuery name="list" nativeSql="
    		select inval_reason,count(*) from journalImportp7worker
    		where parent_id='${param.id}' and acttype='err'
    		group by inval_reason
    		"/>
    		<msh:table action="javascript:void(0)" name="list" idField="1">
    			<msh:tableButton buttonFunction="viewUrl" property="1" addParam="'err_reason','${param.id}'" buttonShortName="РЕЕСТР" />
    			<msh:tableColumn property="1" columnName="ИД ошибки"/>
    			<msh:tableColumn property="2" columnName="Кол-во записей"/>
    		</msh:table>
		<%
    	} else if (type.equals("deluser")||type.equals("adduser")) {
    		%>
<table>
<tr><th>ЛОГИ</th><th>БАЗА МЕДОС</th></tr>
<tr><td valign="top">
		<ecom:webQuery name="list" nativeSql="
    		select id,in_reason,logstring from journalImportp7worker
    		where parent_id='${param.id}' and acttype='${param.typeR}'
    		order by logstring
    		"/>
    		<msh:table action="javascript:void(0)" name="list" idField="1">
    			<msh:tableColumn property="sn" columnName="#"/>
    			<msh:tableColumn property="1" columnName="ИД"/>
    			<msh:tableColumn property="2" columnName="Пользователь"/>
    			<msh:tableColumn property="3" columnName="Описание"/>
    		</msh:table>
</td><td valign="top">
		<%
    	if (type.equals("deluser")) {
    	
    		request.setAttribute("addParam","and su.disabled='1'") ;
    		request.setAttribute("addType","edit") ;
    	} else  {
    		request.setAttribute("addParam","") ;
    		request.setAttribute("addType","create") ;
    		} %>
		<ecom:webQuery name="list1" nativeSql="
    		select wf.id,vwf.name as vwfname
    		,coalesce(wp.lastname||' '||wp.firstname||' '||wp.middlename,wf.groupname) as fio
    		,lpu.name as lpuname
    		,gwf.groupname as gwfgroup
    		,su.login
    		 from WorkFunction wf
    		 left join secuser su on su.id=wf.secuser_id
    		left join Worker w on w.id=wf.worker_id
    		left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
    		left join Patient wp on wp.id=w.person_id
    		left join WorkFunction gwf on gwf.id=wf.group_id 
    		left join MisLpu lpu on lpu.id=w.lpu_id
    		where su.${addType}username='p7auto' and wf.${addType}date=(select max(in_date) from journalImportp7worker where id='${param.id}')
    		 ${addParam}
    		order by lpu.name,wp.lastname
    		"/>
    		<msh:table action="javascript:void(0)" name="list1" idField="1">
    			<msh:tableColumn property="sn" columnName="#"/>
    			<msh:tableColumn property="1" columnName="ИД"/>
    			<msh:tableColumn property="2" columnName="Должность"/>
    			<msh:tableColumn property="3" columnName="ФИО"/>
    			<msh:tableColumn property="4" columnName="Отделение"/>
    			<msh:tableColumn property="5" columnName="Группа (кабинет)"/>
    			<msh:tableColumn property="6" columnName="DTYPE"/>
    		</msh:table>
		

</td></tr>
</table>
		<%
    	} else if (type.equals("del")||type.equals("add")) {
    		%>
<table>
<tr><th>ЛОГИ</th><th>БАЗА МЕДОС</th></tr>
<tr><td valign="top">
		<ecom:webQuery name="list" nativeSql="
    		select id,in_reason,logstring from journalImportp7worker
    		where parent_id='${param.id}' and acttype='${param.typeR}'
    		order by logstring
    		"/>
    		<msh:table action="javascript:void(0)" name="list" idField="1">
    			<msh:tableColumn property="sn" columnName="#"/>
    			<msh:tableColumn property="1" columnName="ИД"/>
    			<msh:tableColumn property="2" columnName="Пользователь"/>
    			<msh:tableColumn property="3" columnName="Описание"/>
    		</msh:table>
</td><td valign="top">
		<%
    	if (type.equals("del")) {
    	
    		request.setAttribute("addParam","and wf.finishdate is not null") ;
    		request.setAttribute("addType","edit") ;
    	} else  {
    		request.setAttribute("addParam","") ;
    		request.setAttribute("addType","create") ;
    		} %>
		<ecom:webQuery name="list1" nativeSql="
    		select wf.id,vwf.name as vwfname
    		,coalesce(wp.lastname||' '||wp.firstname||' '||wp.middlename,wf.groupname) as fio
    		,lpu.name as lpuname
    		,gwf.groupname as gwfgroup
    		,wf.dtype
    		 from WorkFunction wf
    		left join Worker w on w.id=wf.worker_id
    		left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
    		left join Patient wp on wp.id=w.person_id
    		left join WorkFunction gwf on gwf.id=wf.group_id 
    		left join MisLpu lpu on lpu.id=w.lpu_id
    		where wf.${addType}username='p7auto' and wf.${addType}date=(select max(in_date) from journalImportp7worker where id='${param.id}')
    		 ${addParam}
    		order by lpu.name,wp.lastname
    		"/>
    		<msh:table action="javascript:void(0)" name="list1" idField="1">
    			<msh:tableColumn property="sn" columnName="#"/>
    			<msh:tableColumn property="1" columnName="ИД"/>
    			<msh:tableColumn property="2" columnName="Должность"/>
    			<msh:tableColumn property="3" columnName="ФИО"/>
    			<msh:tableColumn property="4" columnName="Отделение"/>
    			<msh:tableColumn property="5" columnName="Группа (кабинет)"/>
    			<msh:tableColumn property="6" columnName="DTYPE"/>
    		</msh:table>
		

</td></tr>
</table>
		<%
    	} else if (type.equals("err_reason")) {
    		%>
    		<ecom:webQuery name="list" nativeSql="
    		select id,logstring from journalImportp7worker
    		where parent_id='${param.idsync}' and acttype='err' and inval_reason='${param.id}'
    		order by logstring
    		"/>
    		<msh:table action="javascript:void(0)" name="list" idField="1">
    			<msh:tableColumn property="sn" columnName="#"/>
    			<msh:tableColumn property="1" columnName="ИД"/>
    			<msh:tableColumn property="2" columnName="Описание"/>
    		</msh:table>
		<%
    	}
    	%>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>

    <tiles:put name="style" type="string">
        
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
        function viewUrl(aId,aType,aId1) {
        //alert(aId1) ;
        	getDefinition("js-riams-sync_parus.do?short=Short&id="+aId+"&typeR="+aType+"&idsync="+aId1,null) ;
        }
        </script>
    </tiles:put>

</tiles:insert>