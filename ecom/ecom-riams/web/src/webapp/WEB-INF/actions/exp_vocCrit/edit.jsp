<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник критериев оценки качества
    	  -->
    <msh:form  action="/entityParentSaveGoView-exp_vocCrit.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="kind" />
      <msh:hidden property="parent" />
      <msh:panel>
      	<msh:ifInRole roles="/Policy/Voc/VocQualityEstimationCrit/EditParent">
      		<msh:row>
      			<msh:autoComplete property="parent" parentId="exp_vocCritForm" vocName="vocQualityEstimationCritByKind" fieldColSpan="3" horizontalFill="true"/>
      		</msh:row>
      	</msh:ifInRole>
      	<msh:ifFormTypeIsView formName="exp_vocCritForm">
      	
	        <msh:row>
	        	<msh:autoComplete viewAction="entityView-exp_vocKind.do" property="kind" label="Вид оценки кач-ва" vocName="vocQualityEstimationKind" fieldColSpan="3" horizontalFill="true"/>
	        </msh:row>
      	</msh:ifFormTypeIsView>
        <msh:row>
        	<msh:textField  label="Код" property="code" />
        	<msh:textField label="Короткое название" property="shortName" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocQualityEstimationCritType" label="Тип" property="type" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:textField label="Наименование" property="name" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    <msh:ifFormTypeIsView formName="exp_vocCritForm">
    <msh:section title="Оценки">
    	<msh:ifInRole roles="/Policy/Voc/VocQualityEstimationMark/View">
    		<ecom:webQuery name="crit" nativeSql="select id,code,name,mark from VocQualityEstimationMark where criterion_id='${param.id}' order by code"/>
    		<msh:table name="crit" action="entityParentView-exp_vocMark.do" idField="1">
    			<msh:tableColumn property="2" columnName="Код"/>
    			<msh:tableColumn property="3" columnName="Наименование"/>
    			<msh:tableColumn property="4" columnName="Оценка"/>
    		</msh:table>
    	</msh:ifInRole>
    </msh:section>
    <msh:section title="Категории видов оценок">
    	<msh:ifInRole roles="/Policy/Voc/VocQualityEstimationCrit/View">
    		<ecom:webQuery name="crit" nativeSql="select vqec.id as vqecid,vqec.code as vqeccode
    		,vqec.name as vqecname, vqec1.code 
    		from VocQualityEstimationCrit vqec
    		left join VocQualityEstimationCrit vqec1 on vqec1.id=vqec.parent_id
    		where vqec.parent_id='${param.id}' 
    		
    		order by vqec.code"/>
    		<msh:table name="crit" action="entityParentView-exp_vocCrit.do" idField="1">
    			<msh:tableColumn property="2" columnName="Код"/>
    			<msh:tableColumn property="3" columnName="Наименование"/>
    			<msh:tableColumn property="4" columnName="Основ. критерий"/>
    		</msh:table>
    	</msh:ifInRole>
    </msh:section>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="exp_vocCritForm" />
  </tiles:put>
  
	<tiles:put name="javascript" type="string">
	<msh:ifFormTypeIsCreate formName="exp_vocCritForm">
		<script type="text/javascript">
			//if (+$("parent").value>0) {} else {
				if (+$("kind").value>0){} else {
					$("kind").value='${param.kind}' ;
				}
			//}
		</script>
		</msh:ifFormTypeIsCreate>
	</tiles:put>  
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Критерий качества">
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationCrit/Edit" key="ALT+2" params="id" action="/entityParentEdit-exp_vocCrit" name="Изменить" title="Изменить данные" />
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationCrit/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityParentDeleteGoParentView-exp_vocCrit" name="Удалить" title="Удалить данные" />
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="exp_vocCritForm">
	    <msh:sideMenu title="Добавить">
	    	<msh:sideLink roles="/Policy/Voc/VocQualityEstimationMark/Create" key="ALT+3" params="id" action="/entityParentPrepareCreate-exp_vocMark" name="Оценку" title="Добавить оценку"/>
	    	<msh:sideLink roles="/Policy/Voc/VocQualityEstimationCrit/Create" key="ALT+3" params="id" action="/javascript:window.location='entityParentPrepareCreate-exp_vocCrit.do?id=${param.id}&kind='+$('kind').value" name="Критерий" title="Добавить критерий"/>
	    </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
    <tags:voc_menu currentAction="exp_voc_kind"/>
  </tiles:put>
</tiles:insert>

