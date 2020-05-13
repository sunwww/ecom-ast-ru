<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Сервис мед.услуг
    	  -->
    	  <tags:templateProtocol version="Visit" idSmo="smo_medServiceForm.parent" voc="protocolVisitByPatient" />
    <msh:form action="/entityParentSaveGoSubclassView-smo_medService.do" defaultField="dateStart">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:hidden property="patient" />
      <msh:hidden property="printDate"/>
      <msh:hidden property="printTime"/>
        
      <msh:panel colsWidth="5%,20%,5%,30%">
        <msh:row>
          <msh:textField property="dateStart" label="Дата" />
          <msh:textField property="timeExecute" label="Время" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="serviceStream" label="Поток обслуживания" vocName="vocServiceStream" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete parentId="smo_medServiceForm.dateStart" property="categoryMedService" label="Категория услуги" vocName="medService" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete parentId="5#12.02.2010" property="medService" label="Медицинская услуга" 
          vocName="medServiceForCategory" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="medServiceAmount" label="Количество" />
          <msh:textField property="uet" label="Усл.един.трудоем."/>
        </msh:row>
        <msh:ifFormTypeIsCreate formName="smo_medServiceForm">
                <msh:row>
                    <msh:textArea property="record" label="Текст протокола:"
                                      horizontalFill="true" rows="30" fieldColSpan="6"  />
                    
                    <msh:ifFormTypeIsNotView formName="smo_medServiceForm">
                    
                    <td colspan="2">
                        <input type="button" value="Шаблон" onClick="showTemplateProtocol()"/>
                    </td>
                    
                    <tags:keyWord name="record" service="KeyWordService" methodService="getDecryption"/>
                    </msh:ifFormTypeIsNotView>
                    <msh:ifFormTypeIsView formName="smo_medServiceForm">
                    <td></td>
                    </msh:ifFormTypeIsView>
                </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:row>
        	<msh:ifInRole roles="/Policy/Mis/MedCase/MedService/EditWorker">
		          <msh:autoComplete property="workFunctionExecute" label="Исполнитель" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
        	</msh:ifInRole>
        	<msh:ifNotInRole roles="/Policy/Mis/MedCase/MedService/EditWorker">
		          <msh:autoComplete viewOnlyField="true" property="workFunctionExecute" label="Исполнитель" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
        	</msh:ifNotInRole>
        </msh:row>
        <msh:row>
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" />
          <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        	<msh:textField property="printDate" label="Дата печати" viewOnlyField="true"/>
        	<msh:textField property="printTime" label="Время" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="noActuality" viewOnlyField="false" label="Недействительность услуги" horizontalFill="false" fieldColSpan="2" labelColSpan="2" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="8" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="smo_medServiceForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View">
        <ecom:parentEntityListAll formName="smo_visitProtocolForm" attribute="protocols" />
        <msh:tableNotEmpty name="protocols">
          <msh:section title="Заключение">
            <msh:table hideTitle="false" disableKeySupport="false" idField="id" name="protocols" action="entityParentView-smo_visitProtocol.do" disabledGoFirst="false" disabledGoLast="false">
              <msh:tableColumn columnName="Дата" property="dateRegistration" />
                <msh:tableColumn columnName="Запись" identificator="false" property="record" cssClass="preCell" />
              <msh:tableColumn columnName="Специалист" property="specialistInfo" />
            </msh:table>
          </msh:section>
        </msh:tableNotEmpty>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsNotView formName="smo_medServiceForm">
    	<tags:mis_double name='MedService' title='Данная услуга оказана:'/>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_medServiceForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_medServiceForm">
      <msh:sideMenu title="Услуга">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-smo_medService" name="Изменить" roles="/Policy/Mis/MedCase/MedService/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-smo_medService" name="Удалить" roles="/Policy/Mis/MedCase/MedService/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_visitProtocol" name="Заключение" title="Добавить протокол" roles="/Policy/Mis/MedCase/Protocol/Create" key="ALT+6" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
        <msh:sideLink action="/javascript:printVisit('.do')" name="Мед.услуги по поликлинике" title="Печать мед.услуги и заключения" 
        	 params="" roles="/Policy/Mis/MedCase/Visit/PrintVisit" />
        <msh:sideLink action="/javascript:printHospital('.do')" name="Мед.услуги по стационару" title="Печать мед.услуги и заключения" 
        	 params="" roles="/Policy/Mis/MedCase/Stac/Ssl/PrintProtocol" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
</tiles:insert>

