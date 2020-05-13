<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник должностей
    	  -->
    <msh:form  action="/entitySaveGoView-voc_post.do" defaultField="code">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
      	<msh:row>
      		<msh:textField label="Код" property="code"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Наименование" property="name" size="40"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" property="omcDoctorPost" vocName="vocOmcDoctorPost" label="Врачебная должность по ОМС" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete horizontalFill="true" property="omcDepType" vocName="vocOmcDepType" label="Код профиля отд. для стац. (спец. для пол-ки)" fieldColSpan="3"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    
    <msh:ifFormTypeIsView formName="voc_postForm">
       <msh:section>
	       	<msh:sectionTitle>Рабочие функции, используемые в системе</msh:sectionTitle>
	       	<msh:sectionContent>
	       		<ecom:webQuery nativeSql="
	       			select vwf.id as vwfid,vwf.code as vwfcode,vwf.name as vwfname,(select list(ms.name) from WorkFunctionService wfs left join MedService ms on ms.id=wfs.medService_id where wfs.vocWorkFunction_id=vwf.id) as medService from VocWorkFunction vwf left join WorkFunction wf on vwf.id=wf.workFunction_id
left join VocPost vp on vp.id=vwf.vocpost_id where vp.id=${param.id} and wf.id is not null group by vwf.id,vwf.code,vwf.name
	       		" name="used"/>
	            <msh:table name="used" action="entityView-voc_workFunction.do" idField="1" disableKeySupport="true">
	                <msh:tableColumn columnName="Код" property="2"/>
	                <msh:tableColumn columnName="Название" property="3"/>
	                <msh:tableColumn columnName="Прикрепленные услуги" property="4"/>
	            </msh:table>
	       	</msh:sectionContent>
	       	<msh:sectionTitle>Рабочие функции, неиспользуемые в системе</msh:sectionTitle>
	       	<msh:sectionContent>
	       		<ecom:webQuery nativeSql="
	       			select vwf.id as vwfid,vwf.code as vwfcode,vwf.name as vwfname,(select list(ms.name) from WorkFunctionService wfs left join MedService ms on ms.id=wfs.medService_id where wfs.vocWorkFunction_id=vwf.id) as medService from VocWorkFunction vwf left join WorkFunction wf on vwf.id=wf.workFunction_id
left join VocPost vp on vp.id=vwf.vocpost_id where vp.id=${param.id} and wf.id is null group by vwf.id,vwf.code,vwf.name
	       		" name="noused"/>
	            <msh:table name="noused" action="entityView-voc_workFunction.do" idField="1" disableKeySupport="true">
	                <msh:tableColumn columnName="Код" property="2"/>
	                <msh:tableColumn columnName="Название" property="3"/>
	                <msh:tableColumn columnName="Прикрепленные услуги" property="4"/>
	            </msh:table>
	       	</msh:sectionContent>
       </msh:section>
    	
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="voc_postForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Справочник должностей">
      <msh:sideLink roles="/Policy/Voc/VocPost/Edit" key="ALT+2" params="id" action="/entityEdit-voc_post" name="Изменить" title="Изменить данные по должности" />
      <msh:sideLink roles="/Policy/Voc/VocPost/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-voc_post" name="Удалить" title="Удалить данные должности" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="post"/>
  </tiles:put>
</tiles:insert>