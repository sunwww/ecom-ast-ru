<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="style" type="string">
        <style type="text/css">            
            .protocols {
				left:0px;  width:99%; 
				top:0px;  height:55em;
			}
			#epicriPanel {
			width:100%;
			}
			.record {
			width:100%;
			}
        </style>

    </tiles:put>
  <tiles:put name="side" type="string">
	<msh:ifFormTypeIsNotView formName="preg_childBirthForPregnancyForm">
    	<tags:templateProtocol idSmo="preg_childBirthForPregnancyForm.medCase" version="Visit" name="tmp" property="histology" voc="protocolVisitByPatient"/>
    </msh:ifFormTypeIsNotView>  
    <msh:ifFormTypeIsView formName="preg_childBirthForPregnancyForm">
      <msh:sideMenu title="Роды">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_childBirthForPregnancy" name="Изменить" roles="/Policy/Mis/Pregnancy/ChildBirth/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_childBirthForPregnancy" name="Удалить" roles="/Policy/Mis/Pregnancy/ChildBirth/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-preg_newBorn" name="Данные о новорожденном" title="Показать данные о новорожденном" roles="/Policy/Mis/NewBorn/View" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Течение родов
    	  -->
    <msh:form action="/entityParentSaveGoParentView-preg_childBirthForPregnancy.do" defaultField="slsName">
      <msh:hidden property="id" />
      <msh:hidden property="pregnancy" />
      <msh:hidden property="saveType" />
      <msh:panel>
      	<msh:row>
      		<msh:autoComplete property="sls" fieldColSpan="3" horizontalFill="true" label="Госпитализация" vocName="slsByPregnancy" parentId="preg_childBirthForPregnancyForm.pregnancy"/>
      	</msh:row>
      	<msh:row>
      		<msh:autoComplete property="medCase" fieldColSpan="3" horizontalFill="true" label="Отделение" vocName="sloBySls" parentAutocomplete="sls"/>
      	</msh:row>
        <msh:row>
          <td width="1px" />
          <td width="1px" />
        </msh:row>
        <msh:row><msh:separator label="Начало родовой деятельности" colSpan="4"/>
        </msh:row>
        <msh:row>
          <msh:textField property="pangsStartDate" label="Дата" />
          <msh:textField property="pangsStartTime" label="Время" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Отхождение вод" colSpan="4"/>
        </msh:row>
        <msh:row>
          <msh:autoComplete property="watersPrematurity" vocName="vocBirthWatesPrematurity" label="Преждевременность отхождения вод" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="watersDate" label="Дата отхождения вод" />
          <msh:textField property="watersTime" label="Время" />
        </msh:row>
        <msh:row>
          <msh:textField property="downWatersAmount" label="Количество нижних вод (мл)" />
          <msh:textField property="upperWatersAmount" label="Количество верхних вод (мл)" />
        </msh:row>
        <msh:row>
          <msh:textField property="downWatersQuality" label="Качество нижних вод" />
          <msh:textField property="upperWatersQuality" label="Качество верхних вод" />
        </msh:row>
        <msh:row><msh:separator label="Начало потужной деятельности" colSpan="4"/>
        </msh:row>
        <msh:row>
          <msh:textField property="travailStartDate" label="Дата начала потуг" />
          <msh:textField property="travailStartTime" label="Время начала потуг" />
        </msh:row>
        <msh:separator label="Общая продолжительность родов" colSpan="9"/>
        <msh:row>
          <msh:textField property="period1Duration" label="1 периода (час)" />
          <msh:textField property="period2Duration" label="2 периода (час)" />
        </msh:row>
        <msh:row>
          <msh:textField property="period3Duration" label="3 периода (мин)" />
        </msh:row>
        <msh:row>
          <msh:textField property="birthFinishDate" label="Дата окончания родов" />
          <msh:textField property="birthFinishTime" label="Время" />
        </msh:row>
        <msh:separator label="Плацента и оболочки" colSpan="9" />
        <msh:row>
          <msh:autoComplete label="Послед выделился" property="placentaSeparation" vocName="vocPlacentaSeparation" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete fieldColSpan="3" vocName="vocPlacentaIntegrity" property="placentaIntegrity" label="Детское место" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="membranesIntegrity" label="Целостность оболочек" vocName="vocFetalMembranesIntegrity" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete fieldColSpan="3" vocName="vocMembranesBreakPlace" property="membranesBreakPlace" label="Место разрыва оболочек" horizontalFill="true" />
        </msh:row>
        
        <msh:row>
          <msh:textField property="placentaWeight" label="Вес плаценты (гр.)" />
          <msh:textField property="placentaSize" label="Размеры плаценты" />
        </msh:row>
        <msh:row>
          <msh:textField property="placentaFeatures" label="Особенности плаценты" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="placentaHistologyOrder" label="Направление плаценты на гистологию" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="histology" fieldColSpan="3" horizontalFill="true" label="Гистология плацента" vocName="vocHistologyResult"/>
        </msh:row>
        <msh:row>
          <msh:textField property="hemorrhageVolume" label="Объем кровопотери (мл)" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="placentaInspector" label="Кто исследовал плаценту" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:separator label="Послеродовый период" colSpan="9" />
        <msh:row>
          <msh:checkBox property="wrongPostNatalPeriod" fieldColSpan="3" label="Неправильный постнатальный период" />
        </msh:row>
        <msh:row>
          <msh:checkBox property="mammillaChaps" label="Трещины сосков" />
        </msh:row>
        <msh:row>
          <msh:textField property="perineumEdema" label="Отек промежности" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="haemorrhoids" label="Геморрой" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="postNatalDisease" label="Послеродовая болезнь" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="notPostNatalDisease" label="Болезнь, не зависящая от родов" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="feverWithoutDiagnosis" label="Повышение температуры без диагноза" vocName="vocFeverFeature" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textArea property="treatmentFeatures" label="Особенности лечения" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="dischangeMotherCondition" label="Состояние матери при выписке" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="preg_childBirthForPregnancyForm">
      <msh:ifInRole roles="/Policy/Mis/NewBorn/View">
        <ecom:parentEntityListAll attribute="newBorns" formName="preg_newBornForm" />
        <msh:tableNotEmpty name="newBorns">
          <msh:section title="Данные о новорожденных">
            <msh:table name="newBorns" action="entityParentView-preg_newBorn.do" idField="id">
              <msh:tableColumn columnName="Дата рождения" property="birthDate" />
              <msh:tableColumn columnName="Время рождения" property="birthTime" />
            </msh:table>
          </msh:section>
        </msh:tableNotEmpty>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_childBirthForPregnancyForm"/>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsNotView formName="preg_childBirthForPregnancyForm">
    	<script type="text/javascript">
    	var isChangeSizeEpicrisis=1 ;
		function changeSizeEpicrisis() {
			if (isChangeSizeEpicrisis==1) {
				Element.addClassName($('histology'), "protocols") ;
				if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Уменьшить' ;
				isChangeSizeEpicrisis=0 ;
			} else {
				Element.removeClassName($('histology'), "protocols") ;
				if ($('changeSizeEpicrisisButton')) $('changeSizeEpicrisisButton').value='Увеличить' ;
				isChangeSizeEpicrisis=1;
			}
		}
		eventutil.addEventListener($('histology'), "dblclick", 
	  		  	function() {
					changeSizeEpicrisis() ;
	  		  	}) ;
    	</script>
    	</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

