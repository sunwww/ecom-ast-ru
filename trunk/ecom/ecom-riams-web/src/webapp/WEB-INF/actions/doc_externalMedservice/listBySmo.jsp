<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name='title' type='string'>
		<ecom:titleTrail mainMenu="Lpu" beginForm="mis_medCaseForm" title="Список лабораторных исследований"/>
	</tiles:put>
	<tiles:put name='side' type='string'>
		<msh:sideMenu title="Добавить">
			<msh:sideLink key='ALT+N' roles="/Policy/Mis/MedCase/Document/External/Medservice/Create" params="" action="/entityPrepareCreate-doc_externalMedservice" title="" name="" />
		</msh:sideMenu>
	</tiles:put>
	<tiles:put name='body' type='string' >
		<msh:section>
			<ecom:webQuery name="list" 
			nativeSql="select em.id,em.comment from Document em 
			left join patient p on p.lastname=em.patientLastname  
			left join medcase m on m.patient_id=p.id
			where m.id='${param.id}' and p.firstname=em.patientFirstname 
				and p.middlename=em.patientMiddlename
				and em.dtype='ExternalMedservice'
				
				" />
			<msh:table name="list" action="entityView-doc_externalMedservice.do" idField="id">
				<msh:tableColumn columnName="#" property="sn" />
				<msh:tableColumn columnName="ИД" property="1" />
				<msh:tableColumn property="2" columnName="Описание"/>
			</msh:table>
		</msh:section>
	</tiles:put>
</tiles:insert>
