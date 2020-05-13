<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Voc" title="Кодификатор" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
  <ecom:webQuery name="list" nativeSql="
  select ms.id,pmeds.name,ms.code,ms.name,ms.additionCode,vms.code as vmscode,list(pp.code) as ppcode from medservice ms
left join medservice pmeds on pmeds.id=ms.parent_id
left join vocmedservice vms on vms.id=ms.vocmedservice_id
left join pricemedservice pms on pms.medService_id=ms.id
left join priceposition pp on pp.id=pms.priceposition_id and pp.priceList_id=3
where pmeds.finishdate is null and ms.finishdate is null and ms.dtype='MedService'
group by ms.id,pmeds.name,ms.code,ms.name,ms.additionCode,vms.code 
order by pmeds.name,ms.code
  
  "/>
    <msh:table name="list" action="entityParentView-mis_medService.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Категория" property="2" />
      <msh:tableColumn columnName="Код" property="3" />
      <msh:tableColumn columnName="Наименование" property="4" />
      <msh:tableColumn columnName="Доп.код" property="5" />
      <msh:tableColumn columnName="Код КСГ" property="6" />
      <msh:tableColumn columnName="Код по прейскуранту" property="7" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:voc_menu currentAction="medService"/>
  </tiles:put>
</tiles:insert>