<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Voc" guid="2281aa59-618c-47df-998a-8acaf0fc910f">Просмотр справочника рабочих функций</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8">
      <msh:sideLink key="ALT+N" roles="/Policy/Voc/VocWorkFunction/Create" action="/entityPrepareCreate-voc_workFunction" name="Рабочую функцию" guid="176203c0-0383-435c-8f78-263016d63671" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="workFunction" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section guid="23eab7e5-cd83-46cd-a3db-48aaa7cb0f93">
      <msh:sectionTitle guid="b4da83f5-7d8b-4186-a457-64a60aac98c3">Используемые в системе</msh:sectionTitle>
      <msh:sectionContent guid="65213982-77dd-48d7-95c5-e3f8efd4822a">
        <ecom:webQuery nativeSql="            select vwf.id as vwfid,vwf.code as vwfcode,vwf.name as vwfname,vp.name as vpname,vp.code as vpcode,(select list(distinct ms.name) from WorkFunctionService wfs left join MedService ms on ms.id=wfs.medService_id where wfs.vocWorkFunction_id=vwf.id) as medService from VocWorkFunction vwf left join WorkFunction wf on vwf.id=wf.workFunction_id left join VocPost vp on vp.id=vwf.vocpost_id where wf.id is not null group by vwf.id,vwf.code,vwf.name,vp.name,vp.code           " name="used" guid="f5edaceb-19a5-4a4f-b138-129be0c9aeb0" />
        <msh:table name="used" action="entityView-voc_workFunction.do" idField="1" disableKeySupport="true" guid="c7089820-d5c3-4597-8a65-c96189ff9931">
          <msh:tableColumn columnName="Код" property="2" guid="2527ac20-b405-4b23-adc9-160b9e1eccfa" />
          <msh:tableColumn columnName="Название" property="3" guid="488f3519-9e81-41db-9176-d2dc773f5e55" />
          <msh:tableColumn columnName="Код должности" property="5" guid="76296b7a-3c02-4a00-bd7d-7ae30f312154" />
          <msh:tableColumn columnName="Название должности" property="4" guid="4edaba60-e565-4c34-80c3-dd14065a21fb" />
          <msh:tableColumn columnName="Прикрепленные услуги" property="6" guid="3966042c-d641-4c9b-8725-5f7662afc5b3" />
        </msh:table>
      </msh:sectionContent>
      <msh:sectionTitle guid="7088e039-0222-457a-b4a7-77ae251beb6a">Неиспользуемые в системе</msh:sectionTitle>
      <msh:sectionContent guid="67fd252e-48d4-4eb4-9830-d0891c16ca12">
        <ecom:webQuery nativeSql="            select vwf.id as vwfid,vwf.code as vwfcode,vwf.name as vwfname,vp.name as vpname,vp.code as vpcode,(select list(distinct ms.name) from WorkFunctionService wfs left join MedService ms on ms.id=wfs.medService_id where wfs.vocWorkFunction_id=vwf.id) as medService from VocWorkFunction vwf left join WorkFunction wf on vwf.id=wf.workFunction_id left join VocPost vp on vp.id=vwf.vocpost_id where wf.id is null group by vwf.id,vwf.code,vwf.name,vp.name,vp.code           " name="noused" guid="d20c8698-7dae-470f-b076-5fab174be3c3" />
        <msh:table name="noused" action="entityView-voc_workFunction.do" idField="1" disableKeySupport="true" guid="ff74e55e-a223-447c-863c-c1fa568d189a">
          <msh:tableColumn columnName="Код" property="2" guid="917da9a9-275a-4fcc-9281-a6a040e2c0b7" />
          <msh:tableColumn columnName="Название" property="3" guid="db17b80b-44ea-4f33-9e0d-07579d883e28" />
          <msh:tableColumn columnName="Код должности" property="5" guid="d64ce009-745e-4f8e-8af1-06d813de0f0e" />
          <msh:tableColumn columnName="Название должности" property="4" guid="fae276cd-ce8c-4d52-b6b2-f29a02c07988" />
          <msh:tableColumn columnName="Прикрепленные услуги" property="6" guid="78585c28-3205-49ef-8d50-3d6b0abbb1cd" />
        </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

