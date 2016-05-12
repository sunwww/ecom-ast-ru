<%@page import="ru.ecom.mis.ejb.form.licence.ExternalDocumentForm"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">

      <msh:ifInRole roles="/Policy/Mis/MedCase/Document/External/View">
      <msh:section title="Внешние документы">
      <ecom:webQuery name="extDocument" nativeSql="
      	select d.id,vedt.name as vedtname,to_char(d.createDate,'dd.mm.yyyy')||coalesce(' '||cast(d.createTime as varchar(5)),''),d.referenceTo from Document d
      	left join VocExternalDocumentType vedt on vedt.id=d.type_id
      	where d.dtype='ExternalDocument' and d.patient_id='${param.id}'
      	and vedt.isMedical = '1'
      "/>
      	<msh:table name="extDocument"
      	viewUrl="entityParentView-doc_externalDocument.do?short=Short" 
      	action="javascript:void(0)" idField="1">
      		<msh:tableColumn property="2" columnName="Тип документа" />
      		<msh:tableColumn property="3" columnName="Дата и время создания"/>
      		<msh:tableColumn property="4" columnName="Ссылка"/>
      	</msh:table>
      	</msh:section>
      </msh:ifInRole>
  </tiles:put>
</tiles:insert>