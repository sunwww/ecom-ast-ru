<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Voc">Просмотр справочника рабочих функций</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink key="ALT+N" roles="/Policy/Voc/VocWorkFunction/Create" action="/entityPrepareCreate-voc_workFunction" name="Рабочую функцию" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="mnWorkFunction" />
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:section>
      <msh:sectionTitle>Используемые в системе</msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery nativeSql="select vwf.id as vwfid,vwf.code as vwfcode,vwf.name as vwfname
        ,vp.name as f4_vpname,vp.code as vpcode,(select list(distinct ms.name) from WorkFunctionService wfs left join MedService ms on ms.id=wfs.medService_id where wfs.vocWorkFunction_id=vwf.id) as f6_medService
        ,v021.code||' '||v021.name as f7_v021
        from VocWorkFunction vwf left join WorkFunction wf on vwf.id=wf.workFunction_id left join VocPost vp on vp.id=vwf.vocpost_id
         left join Voce2FondV021 v021 on v021.id=vwf.fondSpeciality_id where wf.id is not null group by vwf.id,vwf.code,vwf.name,vp.name,vp.code ,v021.code, v021.name" name="used" />
        <msh:table name="used" action="entityView-voc_workFunction.do" idField="1" disableKeySupport="true">
          <msh:tableColumn columnName="Код" property="2" />
          <msh:tableColumn columnName="Название" property="3" />
          <msh:tableColumn columnName="Код должности" property="5" />
          <msh:tableColumn columnName="Название по справочнику V021" property="7" />
          <msh:tableColumn columnName="Название должности" property="4" />
          <msh:tableColumn columnName="Прикрепленные услуги" property="6" />
        </msh:table>
      </msh:sectionContent>
      <msh:sectionTitle>Неиспользуемые в системе</msh:sectionTitle>
      <msh:sectionContent>
        <ecom:webQuery nativeSql="select vwf.id as vwfid,vwf.code as vwfcode,vwf.name as vwfname,vp.name as vpname,vp.code as vpcode
        ,(select list(distinct ms.name) from WorkFunctionService wfs left join MedService ms on ms.id=wfs.medService_id where wfs.vocWorkFunction_id=vwf.id) as medService
        ,v021.code||' '||v021.name as f7_v021
        from VocWorkFunction vwf
        left join WorkFunction wf on vwf.id=wf.workFunction_id
        left join VocPost vp on vp.id=vwf.vocpost_id
         left join Voce2FondV021 v021 on v021.id=vwf.fondSpeciality_id
        where wf.id is null group by vwf.id,vwf.code,vwf.name,vp.name,vp.code,v021.code, v021.name" name="noused" />
        <msh:table name="noused" action="entityView-voc_workFunction.do" idField="1" disableKeySupport="true">
          <msh:tableColumn columnName="Код" property="2" />
          <msh:tableColumn columnName="Название" property="3" />
          <msh:tableColumn columnName="Код должности" property="5" />
          <msh:tableColumn columnName="Название по справочнику V021" property="7" />
          <msh:tableColumn columnName="Название должности" property="4" />
          <msh:tableColumn columnName="Прикрепленные услуги" property="6" />
        </msh:table>
      </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>