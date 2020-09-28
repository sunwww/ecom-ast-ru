<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Template">Шаблоны протоколов</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink key="ALT+N" action="/entityPrepareCreate-temp_protocol" name="Шаблон протокола" roles="/Policy/Diary/Template/Create" />
    </msh:sideMenu>
    <tags:template_menu currentAction="protocols"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  	
    
		  	<msh:toolbar >
			                	<tbody>
			                		<msh:toolbar>
			                		<form action="js-temp_protocol-listTemplate.do" >
			                			<table>
				                		<msh:row>
										    <td colspan="1" class='label'>
										    <input type='checkbox' name='findByUsername' id='findByUsername' onClick='javascript:document.location.href="js-temp_protocol-listTemplate.do?findByUsername="+this.checked'>
										    </td>
										    <td colspan=3 class='findByUsernameLabel'>
											    <label id='findByUsernameLabel' for="findByUsername"> Отображать только свои шаблоны </label>
										    
										    </td>
					     				</msh:row>
					     				<msh:row>
                                            <input type="text" id="search" name="search" placeholder="Введите текст для поиска">
                                            <input type="submit" >
					     				</msh:row>

				                		</table>
				                		</form>
			                		</msh:toolbar>
			                	</tbody>
		  	</msh:toolbar>
    <msh:table name="list" action="entityView-temp_protocol.do" idField="id" navigationAction="js-temp_protocol-listTemplate.do?findByUsername=${findByUsername}">
      <msh:tableColumn columnName="Заголовок" property="title" />
      <msh:tableColumn columnName="Текст протокола" property="text" cssClass="preCell" />
      <msh:tableColumn columnName="Категории" property="categoriesInfo" />
      <msh:tableColumn columnName="Дата создания" property="date" />
      <msh:tableColumn columnName="Пользователь" property="username" />
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

