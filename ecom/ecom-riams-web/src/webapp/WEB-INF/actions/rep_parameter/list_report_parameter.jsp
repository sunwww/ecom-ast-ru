<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
  	<ecom:webQuery name="list" nativeSql="
  	select pt.id,pt.strcode,pt.name,pt.code,vs.name as vsname,list(distinct codeFrom||'-'||codeTo) 
  	from VocReportSetParameterType pt
  	left join VocSex vs on vs.id=pt.sex_id
left join ReportSetTypeParameterType tpt on tpt.parameterType_id=pt.id
where pt.classname='${param.id}'
group by pt.id,pt.strcode,pt.name,pt.code,vs.name
order by pt.strcode
"/>
  	<msh:table name="list" viewUrl="entityParentView-rep_parameter.do?short=Short"
  	 action="entityParentView-rep_parameter.do" idField="1">
  		<msh:tableColumn property="2" columnName="Строка"/>
  		<msh:tableColumn property="3" columnName="Наименование"/>
  		<msh:tableColumn property="4" columnName="Код"/>
  		<msh:tableColumn property="5" columnName="Пол"/>
  		<msh:tableColumn property="6" columnName="Диапазон МКБ"/>
  	</msh:table>
  </tiles:put>
  <tiles:put name="title" type="string">
        <msh:title mainMenu="Voc" title="Список параметров отчета ${param.id}" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Voc/ReportConfig/Create" key="ALT+2" action="/entityPrepareCreate-rep_parameter" name="Параметр" title="Добавить параметр" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="repconfig"/>
  </tiles:put>  
</tiles:insert>