<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Проба
    	  -->
    <msh:form action="/entitySaveGoView-mis_patientList.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:panel>
         <msh:hidden property="saveType"/>
      <msh:row>
      <msh:autoComplete property="type" vocName="patientListType" fieldColSpan="3" horizontalFill="true"/>
      </msh:row>
        <msh:row>
          <msh:textField property="name" label="Название списка" />
        </msh:row>
        <msh:row>
          <msh:textField property="colorName" label="Цвет фона" size="100" />
            </msh:row>
          <msh:row>
          <msh:textField property="colorText" label="Цвет текста" size="20" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isViewWhenSeaching" label="Отображать в поиске"/>
        	<msh:checkBox property="isViewInWebTrail" label="Отображать в WebTrail"/>
        </msh:row>
        <msh:row>
          <msh:textField property="message" label="Сообщение" size="50" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
       <msh:submitCancelButtonsRow colSpan="4" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="mis_patientListForm">
        <msh:section title="Пациенты в списке" >
    <ecom:webQuery name="patients" nativeSql="select plr.id, pat.patientinfo, plr.message 
     from patientlistrecord plr
     left join patient pat on pat.id=plr.patient
     where plr.patientList = '${param.id}' 
    "/>
    
		          <msh:table name="patients" action="entityView-mis_patientListRecord.do" idField="1" 
			          
			         >
		            <msh:tableColumn columnName="Пациент" property="2" />
		            <msh:tableColumn columnName="Сообщение" property="3" />
		          </msh:table>
        </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
   <ecom:titleTrail mainMenu="Voc" beginForm="mis_patientListForm"/>
   </tiles:put>
  <tiles:put name="side" type="string">
 <msh:ifFormTypeIsView formName="mis_patientListForm">
   <msh:sideMenu>
            <msh:sideLink key='ALT+E' params="id" action="/entityEdit-mis_patientList" name="Изменить" />
            <msh:sideLink key='ALT+DEL' params="id" action="/entityDelete-mis_patientList" name="Удалить" confirm="Удалить список?" />
            <msh:sideLink params="id" action="/entityParentPrepareCreate-mis_patientListRecord" name="Добавить пациента в список" />
        </msh:sideMenu>
   </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

