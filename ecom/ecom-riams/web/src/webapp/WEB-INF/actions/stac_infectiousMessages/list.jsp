<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="stac_sslForm" mainMenu="Patient" title="Сообщения об инфекции" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/View">
      <msh:table name="list" action="entityView-stac_infectiousMessages.do" idField="1">
        <msh:tableColumn columnName="Номер сообщения" property="2" />
        <msh:tableColumn columnName="Дата регистрации" property="3" />
        <msh:tableColumn columnName="Время регистрации" property="4" />
        <msh:tableColumn columnName="Телефон" property="5" />
        <msh:tableColumn columnName="Принявшая сообщение организация" property="6" />
      </msh:table>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_infectiousMessages" name="Сообщение" title="Добавить сообщение об инфекции" roles="/Policy/Mis/MedCase/Stac/Ssl/InfectiousMessages/Create" />
    </msh:sideMenu>
    <msh:sideMenu title="Дополнительно">
        <msh:sideLink action="/mis_patients" name="Новая госпитализация" />
	</msh:sideMenu>
  </tiles:put>
</tiles:insert>

