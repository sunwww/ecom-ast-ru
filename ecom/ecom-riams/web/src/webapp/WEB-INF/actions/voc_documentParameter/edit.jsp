<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="voc_documentParameter" />
    <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="Медицинская услуга">
      <msh:ifFormTypeIsView formName="voc_documentParameterForm" guid="e2054544-85-a21c-3bb9b4569efc">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-voc_documentParameter" name="Изменить" roles="/Policy/Mis/MedService/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="voc_documentParameterForm" guid="a6802286-1d60-46ea-b7f4-f588331a09f7">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-voc_documentParameter" name="Удалить" roles="/Policy/Mis/MedService/Delete" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="voc_documentParameterForm" guid="8db06246-c49c-496a-bb1f-2de391e40631">
      <msh:sideMenu title="Добавить" guid="adding">
        <msh:sideLink action="/entityParentPrepareCreate-voc_documentParameter_workFunction" name="Прикрепление раб.функции" params="id" roles="/Policy/Mis/MedService/VocWorkFunction/Create" />
        <msh:sideLink action="/entityParentPrepareCreate-voc_documentParameter" name="Категорию" params="id" roles="/Policy/Mis/MedService/Create"  />
        <msh:sideLink roles="/Policy/Diary/Template/Create" params="id" action="/entityParentPrepareCreate-diary_template" name="Шаблон заключения" title="Добавить шаблон заключения"  />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно" guid="9e0388c8-2666-4d66-b865-419c53ef9f89">
                    <tags:voc_menu currentAction="diary_param_list" />

      </msh:sideMenu>
      
    </msh:ifFormTypeAreViewOrEdit>
    
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Медицинских услуг
    	  -->
    <msh:form action="/entityParentSaveGoView-voc_documentParameter.do" defaultField="parameterGroupName" >
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />

      <msh:panel>
        <msh:row>
          <msh:autoComplete vocName="vocDocumentParameterGroup" property="parameterGroup" label="Группа" horizontalFill="true" fieldColSpan="3" />
        </msh:row>
        <msh:row guid="bb6f7393-5e65-498c-8279-b849d7e9f6b4">
          <msh:textField property="code" label="Код"  horizontalFill="true" />

        </msh:row>
        <msh:row guid="bb6f7393-5e65-498c-8279-b849d7e9f6b4">
          <msh:textField property="name" label="Наименование"  guid="b87e9cee-cf5d-43bc-b50d-1911d5e87e40" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>

        <msh:row>
        	<msh:textField property="normMinimum" label="Мин.значение"/>
        	<msh:textField property="normMaximum" label="Макс.значение"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="norm" label="Норма"/>
        	<msh:textField property="dimension" label="Разрядность"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
      <msh:ifFormTypeIsView formName="voc_documentParameterForm">
    <msh:ifInRole roles="/Policy/Mis/MedService/VocWorkFunction/View">
    	<msh:section createRoles="/Policy/Mis/MedService/VocWorkFunction/Create"
    		createUrl="entityParentPrepareCreate-voc_documentParameter_workFunction.do?id=${param.id}"
    		title="Прикрепление к рабочим функциям по отделениям" >
    	<ecom:webQuery name="workFunc" nativeSql="
    	select wfs.id as wfsid,vwf.name as vwfname,lpu.name as lpuname
    	from WorkFunctionService wfs 
    	left join MisLpu lpu on lpu.id=wfs.lpu_id 
    	left join VocWorkFunction vwf on vwf.id=wfs.vocWorkFunction_id 
    	where wfs.medService_id='${param.id}'
    	"/>
  		<msh:table selection="true" name="workFunc" 
  		action="entityParentView-voc_documentParameter_workFunction.do"
  		editUrl="entityParentEdit-voc_documentParameter_workFunction.do" 
  		idField="1"  deleteUrl="entityParentDeleteGoParentView-voc_documentParameter_workFunction.do">
            <msh:tableColumn property="2" columnName="Рабочая функция"  />
            <msh:tableColumn property="3" columnName="ЛПУ" />
        </msh:table>
    	
    	</msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Mis/MedService/View" guid="5e3d7e52-5747-4b60-aab3-f99027a64117">
        <msh:section title="Категории" guid="e681be03-dea7-4bce-96cf-aa600185f156">
          <ecom:webQuery  name="childMedService" nativeSql="
          	select ms.id,ms.name as msname,vms.name as vmsname, ms.startDate,ms.finishDate,
          	 (select list(' '||vwf.name||coalesce('-'||lpu.name,'')) from WorkFunctionService wfs left join MisLpu lpu on lpu.id=wfs.lpu_id left join VocWorkFunction vwf on vwf.id=wfs.vocWorkFunction_id where wfs.medService_id=ms.id)
          	 ,ms.code 
          	 from MedService ms left join VocMedService vms on vms.id=ms.vocMedService_id where ms.parent_id='${param.id}'
          	 order by ms.code
          " guid="childMedService" />

  	<msh:table selection="true" name="childMedService" action="entityParentView-voc_documentParameter.do" idField="1" guid="16cdff9b-c2ac-4629-8997-eebc80ecc49c">
            <msh:tableColumn  property="7" columnName="Код"  />
            <msh:tableColumn  property="2" columnName="Название" guid="2fd022ea-59b0-4cc9-a8ce-0ed4a3ddc91f" />
            <msh:tableColumn columnName="Прикрепленная услуга" identificator="false" property="3" guid="0c0e08bc-a8af-47b7-ae6d-89e52e73b2e5" />
		      <msh:tableColumn columnName="Дата начала" property="4"/>
		      <msh:tableColumn columnName="Дата окончания" property="5"/>
		      <msh:tableColumn columnName="Прикреп. рабочие функции" property="6"/>
          </msh:table>
        </msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Diary/Template/View" guid="3a4d6eb2-8dac-420a-9dcf-4f47584d9d61">
        <msh:section title="Шаблоны заключений" guid="e681be03-d0185f156">
          <ecom:parentEntityListAll attribute="templates" formName="diary_templateForm" guid="templates" />
          <msh:table name="templates" action="diary_templateView.do" idField="id" guid="16cdff9b--8997-eebc80ecc49c">
            <msh:tableColumn property="title" columnName="Заголовок" guid="2fd022ea-59b0-4cc9a3ddc91f" />
            <msh:tableColumn columnName="Информация" identificator="false" property="information" guid="0c047b7-ae6d-89e52e73b2e5" />
          </msh:table>
        </msh:section>
    </msh:ifInRole>
      </msh:ifFormTypeIsView>
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="voc_documentParameterForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsView formName="voc_documentParameterForm">
 
  	<script type="text/javascript">
  		if ($('planDate')) new dateutil.DateField($('planDate')) ;
  		function updateStartDate() {
	  		var ids = theTableArrow.getInsertedIdsAsParams("ids","childMedService") ;
	  		if (ids) {
  				document.location.href = "js-voc_documentParameter-updateStartDate.do?id=${param.id}&"+ids+"&plandate="+$('planDate').value;
  			} else {
  				alert("Нет выделенных категорий") ;
  			}
  		}
  		function updateEndDate() {
	  		var ids = theTableArrow.getInsertedIdsAsParams("ids","childMedService") ;
	  		alert(ids);
	  		if (ids) {
  				document.location.href = "js-voc_documentParameter-updateEndDate.do?id=${param.id}&"+ids+"&plandate="+$('planDate').value;
  			} else {
  				alert("Нет выделенных категорий") ;
  			}
  		}
  		function deleteMedService() {
	  		var ids = theTableArrow.getInsertedIdsAsParams("ids","childMedService") ;
	  		if (ids) {
	  		  			document.location.href = "js-voc_documentParameter-deleteMedService.do?id=${param.id}&"+ids;
  			} else {
  				alert("Нет выделенных категорий") ;
  			}	  		  			
  		}
  		function updateWorkFunction(aAction) {
			var ids = theTableArrow.getInsertedIdsAsParams("ids","childMedService") ;
	  		if (ids) {
  				document.location.href = "js-voc_documentParameter-updateWorkFunction.do?id=${param.id}&"+ids+"&lpu="+$('lpu').value+"&workFunction="+$('function').value+"&action="+aAction;
  			} else {
  				alert("Нет выделенных категорий") ;
  			}  		
  		}
  	</script>
  
  </msh:ifFormTypeIsView>
   
  
  </tiles:put>
</tiles:insert>

