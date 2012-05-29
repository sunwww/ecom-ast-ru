<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Template" guid="a79e22af-e87a-45dd-9743-59a1f8f3d66a">Шаблоны протоколов</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="0d13c843-c26a-4ae2-ae97-d61b44618bae" title="Добавить">
      <msh:sideLink key="ALT+N" action="/entityPrepareCreate-temp_protocol" name="Шаблон протокола" guid="dc51a550-1158-41b8-89a4-bf3a90ffeedb" roles="/Policy/Diary/Template/Create" />
    </msh:sideMenu>
    <tags:template_menu currentAction="protocols"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  	
    
		  	<msh:toolbar >
			                	<tbody>
			                		<msh:toolbar>
			                		<form >
			                			<table>
				                		<msh:row>
										    <td colspan="1" class='label'>
										    <input type='checkbox' name='findByUsername' id='findByUsername' onClick='javascript:document.location.href="js-temp_protocol-listTemplate.do?findByUsername="+this.checked'>
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
    <msh:table name="list" action="entityView-temp_protocol.do" idField="id" guid="c0ba5c22-fda6-4d4f-89c4-aa58abb1e9d8" navigationAction="js-temp_protocol-listTemplate.do?findByUsername=${findByUsername}">
      <msh:tableColumn columnName="Заголовок" property="title" guid="69ec188c-d4e3-4be5-8a75-e825fd37e296" />
      <msh:tableColumn columnName="Текст протокола" property="text" cssClass="preCell" guid="81b717f5-f9db-4033-aa22-c680b2126544" />
      <msh:tableColumn columnName="Категории" property="categoriesInfo" guid="6c34a7-4512-90aa-75e4e5ae6d63" />
      <msh:tableColumn columnName="Дата создания" property="date" guid="6c3be340-b4a7-4512-90aa-75e4e5ae6d63" />
      <msh:tableColumn columnName="Пользователь" property="username" guid="c28f06f0-c64a-4cdd-b84f-b1e081186496" />
    </msh:table>
  </tiles:put>
  <tiles:put type="string" name="javascript">
  <script type="text/javascript">
  if (+'${findByUsername}'>0) {
	  $('findByUsername').checked=true ;
  }
  
  </script>
  </tiles:put>
</tiles:insert>

