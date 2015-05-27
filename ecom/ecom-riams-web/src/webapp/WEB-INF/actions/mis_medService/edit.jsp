<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="mis_medService" />
    <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Медицинская услуга">
      <msh:ifFormTypeIsView formName="mis_medServiceForm" guid="e2054544-85-a21c-3bb9b4569efc">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-mis_medService" name="Изменить" roles="/Policy/Mis/MedService/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_medServiceForm" guid="a6802286-1d60-46ea-b7f4-f588331a09f7">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-mis_medService" name="Удалить" roles="/Policy/Mis/MedService/Delete" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="mis_medServiceForm" guid="8db06246-c49c-496a-bb1f-2de391e40631">
      <msh:sideMenu title="Добавить" guid="adding">
        <msh:sideLink action="/entityParentPrepareCreate-mis_medService_workFunction" name="Прикрепление раб.функции" params="id" roles="/Policy/Mis/MedService/VocWorkFunction/Create" />
        <msh:sideLink action="/entityParentPrepareCreate-mis_medService" name="Категорию" params="id" roles="/Policy/Mis/MedService/Create"  />
        <msh:sideLink roles="/Policy/Diary/Template/Create" params="id" action="/entityParentPrepareCreate-diary_template" name="Шаблон заключения" title="Добавить шаблон заключения"  />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно" guid="9e0388c8-2666-4d66-b865-419c53ef9f89">
        <tags:voc_menu currentAction="medService" />
      </msh:sideMenu>
      
    </msh:ifFormTypeAreViewOrEdit>
    
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Медицинских услуг
    	  -->
    <msh:form action="/entityParentSaveGoView-mis_medService.do" defaultField="vocMedService" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:hidden property="parent" guid="bd32944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01" colsWidth="20% 30%">
        <msh:row guid="1d32ce64-883b-4be9-8db1-a421709f4470">
          <msh:autoComplete vocName="vocMedService" property="vocMedService" label="Услуга" horizontalFill="true" guid="968469ce-dd95-40f4-af14-deef6cd3e4f" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="1d32ce-a421709f4470">
          <msh:autoComplete vocName="vocServiceType" property="serviceType" label="Тип услуги" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete parentAutocomplete="serviceType" vocName="vocServiceSubType" property="serviceSubType" label="Подтип" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row guid="bb6f7393-5e65-498c-8279-b849d7e9f6b4">
          <msh:textField property="code" label="Код"  horizontalFill="true" />
          <msh:textField property="additionCode" label="Код доп."   horizontalFill="true" />
        </msh:row>
        <msh:row guid="bb6f7393-5e65-498c-8279-b849d7e9f6b4">
          <msh:textField property="name" label="Наименование"  guid="b87e9cee-cf5d-43bc-b50d-1911d5e87e40" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:textField property="shortName" label="Короткое наим." horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="complexity" label="Уровень сложности"/>
        	<msh:checkBox property="isNoOmc" label="Не входит в ОМС"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="startDate" label="Дата начала"/>
        	<msh:textField property="finishDate" label="Дата окончания"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Услуга может оказываться:" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isHospital" label="В стационаре"/>
        	<msh:checkBox property="isDayHospital" label="В дн.стационаре"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isPoliclinic" label="В поликлинике"/>
        	<msh:ifFormTypeIsNotView formName="mis_medServiceForm">
        		<msh:checkBox property="vocMedServiceIsCreate" label="Генерировать услугу внеш."/>
        	</msh:ifFormTypeIsNotView>
        </msh:row>

        <msh:row>
         	<msh:textField property="createUsername" label="Пользователь" viewOnlyField="true"/>
         	<msh:textField property="createDate" label="Дата создания" viewOnlyField="true"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
      <msh:ifFormTypeIsView formName="mis_medServiceForm">
    <msh:ifInRole roles="/Policy/Mis/MedService/VocWorkFunction/View">
    	<msh:section createRoles="/Policy/Mis/MedService/VocWorkFunction/Create"
    		createUrl="entityParentPrepareCreate-mis_medService_workFunction.do?id=${param.id}"
    		title="Прикрепление к рабочим функциям по отделениям" >
    	<ecom:webQuery name="workFunc" nativeSql="
    	select wfs.id as wfsid,vwf.name as vwfname,lpu.name as lpuname
    	,vbt.name as vbtname,vbst.name as vbstname
    	,vrt.name as vrtname
    	,case when wf.dtype='GroupWorkFunction' then wf.groupname else vwfP.name||' '|| wp.lastname||' '||wp.firstname||' '||wp.middlename end as wfName
    	
    	from WorkFunctionService wfs
    	
    	left join MisLpu lpu on lpu.id=wfs.lpu_id 
    	left join VocWorkFunction vwf on vwf.id=wfs.vocWorkFunction_id 
     	 left join VocBedType vbt on vbt.id=wfs.bedType_id
     	 left join VocBedSubType vbst on vbst.id=wfs.bedSubType_id
     	 left join VocRoomType vrt on vrt.id=wfs.roomType_id
     	 left join WorkFunction wf on wf.id=wfs.workfunction_id
     	 left join vocworkfunction vwfP on vwfP.id=wf.workfunction_id
		left join worker w on w.id=wf.worker_id
		left join patient wp on wp.id=w.person_id 
    	
    	where wfs.medService_id='${param.id}'
    	"/>
  		<msh:table selection="true" name="workFunc" 
  		action="entityParentView-mis_medService_workFunction.do"
  		editUrl="entityParentEdit-mis_medService_workFunction.do" 
  		idField="1"  deleteUrl="entityParentDeleteGoParentView-mis_medService_workFunction.do">
            <msh:tableColumn property="2" columnName="Рабочая функция"  />
            <msh:tableColumn property="7" columnName="Специалист (групповая функция)"  />
            <msh:tableColumn property="3" columnName="ЛПУ" />
            <msh:tableColumn property="4" columnName="Профиль коек" />
            <msh:tableColumn property="5" columnName="Тип коек" />
            <msh:tableColumn property="6" columnName="Уровень палат" />
        </msh:table>
    	
    	</msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Diary/Template/View" guid="3a4d6eb2-8dac-420a-9dcf-4f47584d9d61">
        <msh:section title="Шаблоны заключений" createRoles="/Policy/Diary/Template/Create" createUrl="entityParentPrepareCreate-diary_template.do?id=${param.id}">
          <ecom:parentEntityListAll attribute="templates" formName="diary_templateForm" guid="templates" />
          <msh:table name="templates" action="diary_templateView.do" idField="id" guid="16cdff9b--8997-eebc80ecc49c">
            <msh:tableColumn property="title" columnName="Заголовок" guid="2fd022ea-59b0-4cc9a3ddc91f" />
            <msh:tableColumn columnName="Информация" identificator="false" property="information" guid="0c047b7-ae6d-89e52e73b2e5" />
          </msh:table>
        </msh:section>
    </msh:ifInRole>
    
    		<msh:ifInRole roles="/Policy/Mis/Contract/PriceList/PricePosition/PriceMedService/View">
			<msh:section title="Соответствия с прейскурантом">
			<ecom:webQuery name="pricePosition" 
			nativeSql="select pms.id as pmsid,pl.name as plname,pg.name as pgname,pp.code as ppcode
			,pp.name as ppname,pp.cost from PriceMedService pms
			left join PricePosition pp on pp.id=pms.pricePosition_id
			left join priceposition pg on pg.id=pp.parent_id
			left join pricelist pl on pl.id=pp.priceList_id
			where pms.medService_id=${param.id}
			"
			/>
				<msh:table name="pricePosition" 
				viewUrl="entityParentView-contract_priceMedService.do?short=Short"
				action="entityParentView-contract_priceMedService.do" idField="1">
					<msh:tableColumn columnName="#" property="sn"/>
					<msh:tableColumn columnName="Прейскурант" property="2"/>
					<msh:tableColumn columnName="Группа" property="3"/>
					<msh:tableColumn columnName="Код по прейскуранту" property="4"/>
					<msh:tableColumn columnName="Наименование" property="5"/>
					<msh:tableColumn columnName="Цена" property="6"/>
				</msh:table>
			</msh:section>
		</msh:ifInRole>
		
      </msh:ifFormTypeIsView>
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="mis_medServiceForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsView formName="mis_medServiceForm">
 
  	<script type="text/javascript">
  		if ($('planDate')) new dateutil.DateField($('planDate')) ;
  		function updateStartDate() {
	  		var ids = theTableArrow.getInsertedIdsAsParams("ids","childMedService") ;
	  		if (ids) {
  				document.location.href = "js-mis_medService-updateStartDate.do?id=${param.id}&"+ids+"&plandate="+$('planDate').value;
  			} else {
  				alert("Нет выделенных категорий") ;
  			}
  		}
  		function updateEndDate() {
	  		var ids = theTableArrow.getInsertedIdsAsParams("ids","childMedService") ;
	  		alert(ids);
	  		if (ids) {
  				document.location.href = "js-mis_medService-updateEndDate.do?id=${param.id}&"+ids+"&plandate="+$('planDate').value;
  			} else {
  				alert("Нет выделенных категорий") ;
  			}
  		}
  		function deleteMedService() {
	  		var ids = theTableArrow.getInsertedIdsAsParams("ids","childMedService") ;
	  		if (ids) {
	  		  			document.location.href = "js-mis_medService-deleteMedService.do?id=${param.id}&"+ids;
  			} else {
  				alert("Нет выделенных категорий") ;
  			}	  		  			
  		}
  		function updateWorkFunction(aAction) {
			var ids = theTableArrow.getInsertedIdsAsParams("ids","childMedService") ;
	  		if (ids) {
  				document.location.href = "js-mis_medService-updateWorkFunction.do?id=${param.id}&"+ids+"&lpu="+$('lpu').value+"&workFunction="+$('function').value+"&action="+aAction;
  			} else {
  				alert("Нет выделенных категорий") ;
  			}  		
  		}
  	</script>
  
  </msh:ifFormTypeIsView>
    <msh:ifFormTypeIsNotView formName="mis_medServiceForm">
    	<script type="text/javascript">
    		
    		vocMedServiceAutocomplete.addOnChangeCallback(function() {
	      	 	update() ;
	      	 });
	      	 function update() {
	      	 	var text ;
	      	 	text = $('vocMedServiceName').value ;
	      	 	var cnt = text.indexOf(' ') ;
	      	 	if (cnt>0) {
		      	 	$('code').value=text.substring(0,cnt) ;
		      	 	$('name').value=text.substring(cnt+1);
	      	 	}
	      	 }
    	</script>
    </msh:ifFormTypeIsNotView>
  
  </tiles:put>
</tiles:insert>

