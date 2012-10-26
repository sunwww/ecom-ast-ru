<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
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
			nativeSql="select em.id
			,'Фамилия '||em.PatientLastname||'Имя '||PatientFirstname||'Отчество '
			||em.PatientMiddlename
		||'Дата рождения'||em.PatientBirthday	as fio,	
		'ЛПУ: '||OrderLpu
		||'\nВрач: '||Orderer
		||'\nНомер направления: '||NumberDoc
		||'\nДата направления: '||to_char(em.OrderDate,'dd.mm.yyyy')
		||'\nВремя направления: '||cast(em.OrderTime as varchar(5))		
		||'\nДата получения результата: '||to_char(em.CreateDate,'dd.mm.yyyy')
		||'\nВремя получения результата: '||cast(em.CreateTime as varchar(5))
			
			as orderInfo
			,em.comment from Document em 
			left join patient p on p.id=em.patient_id  
			left join medcase m on m.patient_id=p.id
			where m.id='${param.id}' and em.dtype='ExternalMedservice'
				
				" />
			<msh:table name="list" action="entityView-doc_externalMedservice.do" idField="1">
				<msh:tableColumn columnName="#" property="sn" />
				<msh:tableColumn columnName="ИД" property="1" />
				<msh:tableColumn property="3" columnName="Направление" cssClass="preCell"/>
				<msh:tableColumn property="4" columnName="Описание" cssClass="preCell"/>
			</msh:table>
		</msh:section>
	</tiles:put>
</tiles:insert>
