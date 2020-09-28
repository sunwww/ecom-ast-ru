<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Template" title="Шаблоны листов назначений" />
  </tiles:put>
  <tiles:put name="side" type="string">
    
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Mis/Prescription/Template/Create" key="ALT+N" action="/entityPrepareCreate-pres_template" name="Шаблон листа назначений" />
      <msh:sideLink action=" javascript:shownewTemplatePrescription(1,&quot;.do&quot;)" name="Шаблон ЛН на основе существующего" title="Создать шаблон лист назначения на основе существующего шаблона" />
    </msh:sideMenu>
        <tags:template_menu currentAction="prescriptions"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  	<msh:toolbar >
			                	<tbody>
			                		<msh:toolbar>
			                		<form >
			                			<table>
				                		<msh:row>
										    <td colspan="1" class='label'>
										    <input type='checkbox' name='findByUsername' id='findByUsername' onClick='javascript:document.location.href="js-pres_template-listTemplate.do?findByUsername="+this.checked'>
										    </td>
										    <td colspan=3 class='findByUsernameLabel'>
											    <label id='findByUsernameLabel' for="findByUsername"> Отображать только свои шаблоны </label>
										    
										    </td>
					     				</msh:row>

				                		</table>
				                		</form>
			                		</msh:toolbar>
			                	</tbody>
		  	</msh:toolbar>
    <msh:table name="list" action="entityParentView-pres_template.do" idField="id" navigationAction="js-pres_template-listTemplate.do?findByUsername=${findByUsername}">
      <msh:tableColumn columnName="Название" property="name" />
      <msh:tableColumn columnName="Комментарии" property="comments" cssClass="preCell"/>
      <msh:tableColumn columnName="Владелец" property="workFunctionInfo" />
      <msh:tableColumn columnName="Дата создания" property="createDate" />
      <msh:tableColumn columnName="Создан" property="createUsername" />
    </msh:table>
    <tags:templatePrescription record="2" parentId="0" name="new" />
  </tiles:put>
  <tiles:put type="string" name="javascript">
  <script type="text/javascript">
  if (+'${findByUsername}'>0) {
	  $('findByUsername').checked=true ;
  }
  
  </script>
  </tiles:put>
</tiles:insert>

