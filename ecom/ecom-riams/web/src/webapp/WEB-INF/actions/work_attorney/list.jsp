<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Voc" guid="2281aa59-618c-47df-998a-8acaf0fc910f">Просмотр списка доверенностей</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Перейти" guid="ded8e3d3-a6a8-4c76-b484-92dfe52cc1b7">
      <msh:sideLink roles="/Policy/Mis/Worker/Attorney/View" key="ALT+2" params="id" action="/entityList-work_attorney" name="Список доверенностей" title="Список доверенностей" guid="75bdfecf-3673-4e79-85d1-caa9ec490608" />
    </msh:sideMenu>
    <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8">
      <msh:sideLink key="ALT+N" roles="/Policy/Mis/Worker/Attorney/Create" action="/entityPrepareCreate-work_attorney" name="Доверенность" guid="176203c0-0383-435c-8f78-263016d63671" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section guid="23eab7e5-cd83-46cd-a3db-48aaa7cb0f93">
      <msh:sectionTitle>Существующие доверенности</msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery nativeSql="select a.id, a.attorneyNumber, a.attorneyStartDate, a.isArchive, a.isDefault, vat.name
        , p.patientinfo from attorney a
        left join patient p on p.id=a.person_id
        left join vocattorneytype vat on vat.id=a.type_id
        " name="list" guid="f5edaceb-19a5-4a4f-b138-129be0c9aeb0" />
        <msh:table name="list" action="entityView-work_attorney.do" idField="1"guid="c7089820-d5c3-4597-8a65-c96189ff9931">
          <msh:tableColumn columnName="Номер" property="2" guid="2527ac20-b405-4b23-adc9-160b9e1eccfa" />
          <msh:tableColumn columnName="Дата выдачи" property="3" guid="488f3519-9e81-41db-9176-d2dc773f5e55" />
          <msh:tableColumn columnName="Кому выдана" property="7" guid="76296b7a-3c02-4a00-bd7d-7ae30f312154" />
          <msh:tableColumn columnName="В архиве" property="4" guid="4edaba60-e565-4c34-80c3-dd14065a21fb" />
          <msh:tableColumn columnName="Используется по умолчанию" property="5" guid="3966042c-d641-4c9b-8725-5f7662afc5b3" />
        </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

