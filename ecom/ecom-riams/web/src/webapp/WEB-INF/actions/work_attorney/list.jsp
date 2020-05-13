<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Voc">Просмотр списка доверенностей</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink key="ALT+N" roles="/Policy/Mis/Worker/Attorney/Create" action="/entityPrepareCreate-work_attorney" name="Доверенность" />
    </msh:sideMenu>
    <tags:contractMenu currentAction="attorneyGroup"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section>
      <msh:sectionTitle>Существующие доверенности</msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery nativeSql="select a.id, a.attorneyNumber, a.attorneyStartDate, a.isArchive, a.isDefault, vat.name
        , p.patientinfo from attorney a
        left join patient p on p.id=a.person_id
        left join vocattorneytype vat on vat.id=a.type_id
        " name="list" />
        <msh:table name="list" action="entityView-work_attorney.do" idField="1">
          <msh:tableColumn columnName="Номер" property="2" />
          <msh:tableColumn columnName="Дата выдачи" property="3" />
          <msh:tableColumn columnName="Кому выдана" property="7" />
          <msh:tableColumn columnName="В архиве" property="4" />
          <msh:tableColumn columnName="Используется по умолчанию" property="5" />
        </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

