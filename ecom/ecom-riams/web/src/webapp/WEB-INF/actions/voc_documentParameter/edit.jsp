<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <tags:style_currentMenu currentAction="voc_documentParameter" />
    <msh:sideMenu title="Медицинская услуга">
      <msh:ifFormTypeIsView formName="voc_documentParameterForm">
        <msh:sideLink key="ALT+1" params="id" action="/entityParentEdit-voc_documentParameter" name="Изменить" roles="/Policy/Mis/MedService/Edit" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="voc_documentParameterForm">
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDelete-voc_documentParameter" name="Удалить" roles="/Policy/Mis/MedService/Delete" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="voc_documentParameterForm">
      <msh:sideMenu title="Добавить">
        <msh:sideLink action="/entityParentPrepareCreate-voc_documentParameter_workFunction" name="Прикрепление раб.функции" params="id" roles="/Policy/Mis/MedService/VocWorkFunction/Create" />
        <msh:sideLink action="/entityParentPrepareCreate-voc_documentParameter" name="Категорию" params="id" roles="/Policy/Mis/MedService/Create"  />
        <msh:sideLink roles="/Policy/Diary/Template/Create" params="id" action="/entityParentPrepareCreate-diary_template" name="Шаблон заключения" title="Добавить шаблон заключения"  />
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
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
        <msh:row>
          <msh:textField property="code" label="Код"  horizontalFill="true" />

        </msh:row>
        <msh:row>
          <msh:textField property="name" label="Наименование"  horizontalFill="true" fieldColSpan="3"/>
        </msh:row>

        <msh:row>
        	<msh:textField property="normMinimum" label="Мин.значение"/>
        	<msh:textField property="normMaximum" label="Макс.значение"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="norm" label="Норма"/>
        	<msh:textField property="dimension" label="Разрядность"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
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
    <msh:ifInRole roles="/Policy/Mis/MedService/View">
        <msh:section title="Категории">
          <ecom:webQuery  name="childMedService" nativeSql="
          	select ms.id,ms.name as msname,vms.name as vmsname, ms.startDate,ms.finishDate,
          	 (select list(' '||vwf.name||coalesce('-'||lpu.name,'')) from WorkFunctionService wfs left join MisLpu lpu on lpu.id=wfs.lpu_id left join VocWorkFunction vwf on vwf.id=wfs.vocWorkFunction_id where wfs.medService_id=ms.id)
          	 ,ms.code 
          	 from MedService ms left join VocMedService vms on vms.id=ms.vocMedService_id where ms.parent_id='${param.id}'
          	 order by ms.code
          " />

  	<msh:table selection="true" name="childMedService" action="entityParentView-voc_documentParameter.do" idField="1">
            <msh:tableColumn  property="7" columnName="Код"  />
            <msh:tableColumn  property="2" columnName="Название" />
            <msh:tableColumn columnName="Прикрепленная услуга" identificator="false" property="3" />
		      <msh:tableColumn columnName="Дата начала" property="4"/>
		      <msh:tableColumn columnName="Дата окончания" property="5"/>
		      <msh:tableColumn columnName="Прикреп. рабочие функции" property="6"/>
          </msh:table>
        </msh:section>
    </msh:ifInRole>
    <msh:ifInRole roles="/Policy/Diary/Template/View">
        <msh:section title="Шаблоны заключений">
          <ecom:parentEntityListAll attribute="templates" formName="diary_templateForm" />
          <msh:table name="templates" action="diary_templateView.do" idField="id">
            <msh:tableColumn property="title" columnName="Заголовок" />
            <msh:tableColumn columnName="Информация" identificator="false" property="information" />
          </msh:table>
        </msh:section>
    </msh:ifInRole>
      </msh:ifFormTypeIsView>
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="voc_documentParameterForm" />
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

