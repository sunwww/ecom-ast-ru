<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page = '/WEB-INF/tiles/mainLayout.jsp' flush = 'true'>

  <tiles:put name="body" type="string">
    <msh:form action="entityParentSaveGoView-voc_cardIndex.do" defaultField="code" guid="10826cd9-7e71-480c-9d53-c96e6805ce24">
      <msh:hidden property="id"/>
      <msh:hidden property="saveType"/>
      <msh:hidden property="lpu"/>
      <msh:panel>
        <msh:row>
          <msh:textField property="code" label="Код" />
        </msh:row>
        <msh:row>
          <msh:textField property="name" label="Наименование" 
          	horizontalFill="true" size="50" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="2" guid="a332e241-83f4-4e61-ad6f-d0f69cc6076e" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="b08ac525-aee5-493c-bd30-a245e7c80200">
      <msh:ifFormTypeIsView formName="voc_cardIndexForm" guid="24576531-bcea-4103-9c80-fe9e95c4dfaf">
        <msh:sideLink key="ALT+2" roles="/Policy/Voc/VocCardIndex/Edit" 
        	params="id" action="/entityParentEdit-voc_cardIndex" 
        	name="Изменить" />
      </msh:ifFormTypeIsView>
      <hr />
      <msh:ifFormTypeAreViewOrEdit formName="voc_cardIndexForm" guid="b0b9ca4d-8f54-4f15-87a7-34b467ed10d5">
        <msh:sideLink key="ALT+DEL" roles="/Policy/Voc/VocCardIndex/Delete" 
	        params="id" action="/entityParentDeleteGoParentView-voc_cardIndex" 
	        name="Удалить" confirm="Удалить картотеку?" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="voc_cardIndexForm" guid="04eb4fb1-03b4-4011-9e85-30cd955d2c41" />
  </tiles:put>
</tiles:insert>

