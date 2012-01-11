<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="stac_sslForm" mainMenu="Patient" title="Сообщения в милицию" guid="7c2f862e-0345-431a-a391-39b33538ad1b" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Дополнительно" guid="0ddb2181-a1e1-4f7c-93f6-aa77081d080b">
      <msh:sideLink action="/mis_patients" name="Новая госпитализация" guid="78548581-88f4-46e3-bad5-ec71ea02dc72" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/MilitiaMessages/View" guid="2ccd6a7e-21cc-45b9-8e22-6f0b911309b1">
      <msh:table name="list" action="entityParentView-stac_criminalMessages.do" idField="id" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
        <msh:tableColumn columnName="Номер сообщения" property="number" guid="ce16c32c-9459-4673-9ce8-d6e646f969ff" />
        <msh:tableColumn columnName="Дата регистрации" property="phoneDate" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
        <msh:tableColumn columnName="Время регистрации" property="phoneTime" guid="35347247-b552-4154-a82a-ee484a1714ad" />
        <msh:tableColumn columnName="Телефон" property="phone" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
        <msh:tableColumn columnName="Принявшая сообщение организация" property="recieverOrganization" guid="6b562107-5017-4559-9b94-ab525b579202" />
      </msh:table>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="6372e109-9e1b-49dc-840b-9b38f524efeb">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_criminalMessages" name="Сообщение" title="Добавить сообщение в милиция" guid="436bbb7b-497c-4b10-ba0e-c5601675a713" roles="/Policy/Mis/MedCase/Stac/Ssl/MilitiaMessages/Create" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

