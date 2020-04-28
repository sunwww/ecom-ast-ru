<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="mis_medService" />
      <tags:medserviceInfoCopy name="medserviceInfoCopy" />
    <msh:sideMenu title="Медицинская услуга">
      <msh:ifFormTypeIsView formName="mis_medServiceForm">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-mis_medService" name="Изменить" roles="/Policy/Mis/MedService/Edit" />
            </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_medServiceForm">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-mis_medService" name="Удалить" roles="/Policy/Mis/MedService/Delete" />
        <msh:sideLink params="" action="/javascript:showmedserviceInfoCopy(${param.id },3)" name="Создать дубль" title="Создать дубль этой услуги" roles="/Policy/Mis/MedService/Create" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="mis_medServiceForm">
      <msh:sideMenu title="Добавить">
        <msh:sideLink action="/entityParentPrepareCreate-mis_medService_workFunction" name="Прикрепление раб.функции" params="id" roles="/Policy/Mis/MedService/VocWorkFunction/Create" />
        <msh:sideLink action="/entityParentPrepareCreate-mis_medService" name="Категорию" params="id" roles="/Policy/Mis/MedService/Create"  />
        <msh:sideLink roles="/Policy/Diary/Template/Create" params="id" action="/entityParentPrepareCreate-diary_template" name="Шаблон заключения" title="Добавить шаблон заключения"  />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
        <tags:voc_menu currentAction="medService" />
      </msh:sideMenu>
      
    </msh:ifFormTypeAreViewOrEdit>
    
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Медицинских услуг
    	  -->
    <msh:form action="/entityParentSaveGoView-mis_medService.do" defaultField="vocMedService">
      <msh:hidden property="id"/>
      <msh:hidden property="saveType"/>
      <msh:panel colsWidth="20% 30%">
          <msh:row>
              <msh:autoComplete vocName="medServiceGroupAll" property="parent" label="Родитель" horizontalFill="true" fieldColSpan="3" />
          </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocMedService" property="vocMedService" label="Услуга" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocServiceType" property="serviceType" label="Тип услуги" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete parentAutocomplete="serviceType" vocName="vocServiceSubType" property="serviceSubType" label="Подтип" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:textField property="code" label="Код"  horizontalFill="true" />
          <msh:textField property="additionCode" label="Код доп."   horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:textField property="uet" label="УЕТ"/>
        </msh:row>
        <msh:row>
          <msh:textField property="name" label="Наименование" horizontalFill="true" fieldColSpan="3"/>
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
        	<msh:checkBox property="isNoFederal" label="Не входит фед.справочник"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isForLabDoctor" label="Может назначаться врачом лаборатории"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isReqComment" label="Обязательно заполнить комментарий при назначении" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="showInReport" label="Отображать результаты выполнения анализа в журнале состоящих в отделении" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isAbortRequired" label="Обязательно указывать тип аборта при создании операции" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
      <msh:row>
          <msh:checkBox property="isShowSiteAsDefault" label="Отображать на сайте как услугу по умолчанию у специалиста" fieldColSpan="3" horizontalFill="true"/>
      </msh:row>
      <msh:row>
          <msh:checkBox property="printCodeLabReestr" label="Отображать код услуги при печати в реестре назначений для лаборатории" fieldColSpan="3" horizontalFill="true"/>
      </msh:row>
      <msh:row>
          <msh:autoComplete property="vocColorIdentity" label="Браслет"  horizontalFill="true" vocName="vocColorIdentityPatientWithPat"/>
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
        <msh:submitCancelButtonsRow colSpan="2"/>
      </msh:panel>
    </msh:form>
      <msh:ifFormTypeIsView formName="mis_medServiceForm">
    <msh:ifInRole roles="/Policy/Mis/MedService/VocWorkFunction/View">
    	<msh:section createRoles="/Policy/Mis/MedService/VocWorkFunction/Create"
    		createUrl="entityParentPrepareCreate-mis_medService_workFunction.do?id=${param.id}"
    		title="Прикрепление к рабочим функциям по отделениям <a href='javascript:void(0)' onclick='showmedserviceInfoCopy(${param.id },2)'> Копировать в услугу</a>" >
    	<ecom:webQuery name="workFunc" nativeSql="
    	select wfs.id as wfsid,vwf.name as vwfname,lpu.name as lpuname
    	,vbt.name as vbtname,vbst.name as vbstname
    	,vrt.name as vrtname
    	,case when wf.dtype='GroupWorkFunction' then wf.groupname else vwfP.name||' '|| wp.lastname||' '||wp.firstname||' '||wp.middlename end as wfName
    	,coalesce(vpt.shortname, vpt.name) as prescriptName
    	
    	from WorkFunctionService wfs
    	left join vocPrescriptType vpt on vpt.id=wfs.prescripttype_id
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
            <msh:tableColumn property="8" columnName="запр. тип назначения" />
        </msh:table>
    	
    	</msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Diary/Template/View">
        <msh:section title="Шаблоны заключений <a href='javascript:void(0)' onclick='showmedserviceInfoCopy(${param.id },1)'> Копировать в услугу</a>" createRoles="/Policy/Diary/Template/Create" createUrl="entityParentPrepareCreate-diary_template.do?id=${param.id}">
          <ecom:parentEntityListAll attribute="templates" formName="diary_templateForm"/>
          <msh:table name="templates" action="diary_templateView.do" idField="id">
            <msh:tableColumn property="title" columnName="Заголовок"/>
            <msh:tableColumn columnName="Информация" identificator="false" property="information"/>
          </msh:table>
        </msh:section>
    </msh:ifInRole>
    
    		<msh:ifInRole roles="/Policy/Mis/Contract/PriceList/PricePosition/PriceMedService/View">
			<msh:section title="Соответствия с прейскурантом">
			<ecom:webQuery name="pricePosition" 
			nativeSql="select pms.id as pmsid,pl.name as plname,pg.name as pgname,pp.code as ppcode
			,pp.name as ppname,pp.cost
			,case when pms.dateTo is null and pp.dateTo is null then '' else 'color:red' end as f7_style
			from PriceMedService pms
			left join PricePosition pp on pp.id=pms.pricePosition_id
			left join priceposition pg on pg.id=pp.parent_id
			left join pricelist pl on pl.id=pp.priceList_id
			where pms.medService_id=${param.id}
			"
			/>
				<msh:table name="pricePosition" styleRow="7"
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

          <msh:ifInRole roles="/Policy/Mis/MedService/View">
              <msh:section title="Услуги, входящие в данную комплексную программу"
                           createUrl="entityParentPrepareCreate-mis_medServiceComplexLink.do?id=${param.id}">
                  <ecom:webQuery name="innerComplex"
                                 nativeSql="select cmpl.id,ms.code,ms.name,cmpl.countinnermedservice
                                  from medservice ms
                                  left join MedServiceComplexLink cmpl on ms.id=cmpl.innermedservice_id
                                  where cmpl.complexmedservice_id =${param.id}"
                  />
                  <msh:table name="innerComplex"
                             action="entityParentView-mis_medServiceComplexLink.do" idField="1"
                             editUrl="entityParentEdit-mis_medServiceComplexLink.do"
                             deleteUrl="entityParentDeleteGoParentView-mis_medServiceComplexLink.do">
                      <msh:tableColumn columnName="#" property="sn"/>
                      <msh:tableColumn columnName="Код" property="2"/>
                      <msh:tableColumn columnName="Услуга" property="3"/>
                      <msh:tableColumn columnName="Кол-во" property="4"/>
                  </msh:table>
              </msh:section>
          </msh:ifInRole>

      </msh:ifFormTypeIsView>
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="mis_medServiceForm"/>
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
	      	     if (!$('code').value) {
                     var text ;
                     text = $('vocMedServiceName').value ;
                     var cnt = text.indexOf(' ') ;
                     if (cnt>0) {
                         $('code').value=text.substring(0,cnt) ;
                         $('name').value=text.substring(cnt+1);
                     }
                 }
	      	 }
    	</script>
    </msh:ifFormTypeIsNotView>
  
  </tiles:put>
</tiles:insert>

