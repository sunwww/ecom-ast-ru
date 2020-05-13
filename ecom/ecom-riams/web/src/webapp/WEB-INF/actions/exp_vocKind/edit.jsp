<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник видов оценок качества
    	  -->
    <msh:form  action="/entitySaveGoView-exp_vocKind.do" defaultField="name">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:panel>
        <msh:row>
        	<msh:textField label="Код" property="code" />
        </msh:row>
        <msh:row>
        	<msh:textField label="Наименование" property="name" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" />
      </msh:panel>
    </msh:form>
    
    <msh:ifFormTypeIsView formName="exp_vocKindForm">
    	<msh:ifInRole roles="/Policy/Voc/VocQualityEstimationCrit/View">
    		<ecom:webQuery name="crit" nativeSql="select vqec.id as vqecid,vqec.code as vqeccode
    		,vqec.name as vqecname, vqec1.code 
    		from VocQualityEstimationCrit vqec
    		left join VocQualityEstimationCrit vqec1 on vqec1.id=vqec.parent_id
    		where vqec.kind_id='${param.id}' 
    		
    		order by vqec.code"/>
    		<msh:table name="crit" action="entityParentView-exp_vocCrit.do" idField="1">
    			<msh:tableColumn property="2" columnName="Код"/>
    			<msh:tableColumn property="3" columnName="Наименование"/>
    			<msh:tableColumn property="4" columnName="Основ. критерий"/>
    		</msh:table>
    	</msh:ifInRole>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Voc" beginForm="exp_vocKindForm" />
  </tiles:put>

  
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Вид оценки качества">
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationKind/Edit" key="ALT+2" params="id" action="/entityEdit-exp_vocKind" name="Изменить" title="Изменить данные" />
      <msh:sideLink roles="/Policy/Voc/VocQualityEstimationKind/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-exp_vocKind" name="Удалить" title="Удалить данные" />
    </msh:sideMenu>
    <msh:ifFormTypeAreViewOrEdit formName="exp_vocKindForm">
    <msh:sideMenu title="Добавить">
    	<msh:sideLink roles="/Policy/Voc/VocQualityEstimationCrit/Create" key="ALT+3" action="/entityPrepareCreate-exp_vocCrit.do?kind=${param.id}" name="Критерий" title="Добавить критерий"/>
    </msh:sideMenu>
    </msh:ifFormTypeAreViewOrEdit>
    <tags:voc_menu currentAction="exp_voc_kind"/>
  </tiles:put>
</tiles:insert>