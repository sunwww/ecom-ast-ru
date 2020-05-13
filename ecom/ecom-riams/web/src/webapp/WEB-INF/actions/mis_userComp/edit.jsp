<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
  <%
  request.setAttribute("remote_address", request.getRemoteAddr()) ;
  %>
    <msh:form action="entityParentSaveGoSubclassView-mis_userComp.do" defaultField="userName">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent"/>
      <msh:panel>
      	<msh:row>
      	  <msh:textField property="name" label="Наименование" fieldColSpan="3"  horizontalFill="true"/>
      	</msh:row>
        <msh:row>
	        <msh:autoComplete property="user" fieldColSpan="3" vocName="secUser" horizontalFill="true"/>
        </msh:row>
        <msh:row>
          <msh:textField property="remoteAddress" label="IP" size="20" />
        	<msh:checkBox property="dynamicIp"/>
        </msh:row>
        <msh:row>
          <msh:textField property="comPort" label="COM порт" size="20" />
        </msh:row>
        <msh:row>
          <msh:textArea property="comment" label="Комментарий" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        
        <msh:submitCancelButtonsRow colSpan="2"/>
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Компьютер">
      <msh:ifFormTypeIsView formName="mis_userCompForm">
        <msh:sideLink key="ALT+2" roles="/Policy/Mis/WorkPlace/UserComputer/Edit" params="id" action="/entityEdit-mis_userComp" name="Изменить" />
      </msh:ifFormTypeIsView>
      <msh:ifFormTypeAreViewOrEdit formName="mis_userCompForm">
        <msh:sideLink key="ALT+DEL" params="id" roles="/Policy/Mis/WorkPlace/UserComputer/Delete" action="/entityParentDeleteGoParentView-mis_userComp" name="Удалить" confirm="Удалить палату?" />
      </msh:ifFormTypeAreViewOrEdit>
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
      <msh:sideLink key="ALT+6" action="/entityParentList-mis_buildingPlace.do?id=-1" name="Список зданий" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Lpu" beginForm="mis_userCompForm" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsCreate formName="mis_userCompForm" insideJavascript="true">
        <script type="text/javascript">// <![CDATA[//
        		$('remoteAddress').value = '${remote_address}' ;
	    	//]]></script>
      </msh:ifFormTypeIsCreate>
  </tiles:put>

</tiles:insert>