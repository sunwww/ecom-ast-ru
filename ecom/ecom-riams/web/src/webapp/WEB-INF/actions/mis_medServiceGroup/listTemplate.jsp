<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Voc" title="Список шаблонов по услугам" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
  <ecom:webQuery name="list" nativeSql="
  select ms.id,ms1.name,ms.code,ms.name,ms.shortname,ms.additioncode,tp.title
,(select
list(distinct case when +pf.position>9 then '' else '0' end || pf.position||'. '||p.name||' - '||case when p.type='1' then 'числ' when p.type='2' then 'спр' when p.type='6' then 'спр (мн.)' when p.type='7' then 'спр (текст)' when p.type='3' then 'текст' when p.type='4' then 'число зн.'||p.cntdecimal when p.type='5' then 'текст с огр' end
||'<'||'br/>'
) as txtParam
from parameterbyform pf
left join parameter p on p.id=pf.parameter_id
where pf.template_id = tp.id

)
from medservice ms
left join medservice ms1 on ms1.id=ms.parent_id
left join templateprotocol tp on ms.id=tp.medservice_id

where ms1.parent_id in (${param.id}) or ms1.id in (${param.id}) and ms.dtype='MedService'
group by ms1.name,tp.id,tp.title,ms.id,ms.code,ms.name,ms.shortname,ms.additioncode
order by ms1.name,ms.code

  
  "/>
    <msh:table name="list" action="entityParentView-mis_medService.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Категория" property="2" />
      <msh:tableColumn columnName="Код" property="3" />
      <msh:tableColumn columnName="Наименование" property="4" />
      <msh:tableColumn columnName="Короткое наименование" property="5" />
      <msh:tableColumn columnName="Доп.код" property="6" />
      <msh:tableColumn columnName="Наименование шаблона" property="7" />
      <msh:tableColumn columnName="Параметры" property="8" />
    </msh:table>
  </tiles:put>
  <tiles:put name="side" type="string">
    <tags:voc_menu currentAction="medService"/>
  </tiles:put>
</tiles:insert>