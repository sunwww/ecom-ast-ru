<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
  	<ecom:webQuery name="list" nativeSql="
  	select classname,count(strcode) from VocReportSetParameterType
group by classname
order by classname"/>
  	<msh:table name="list" viewUrl="js-rep_parameter-str_list.do?short=Short"
  	 action="js-rep_parameter-str_list.do" idField="1">
  		<msh:tableColumn property="1" columnName="Отчет"/>
  		<msh:tableColumn property="2" columnName="Кол-во строк"/>
  	</msh:table>
  </tiles:put>
    <tiles:put name="title" type="string">
    <msh:title mainMenu="Voc" title="Список отчетов" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink roles="/Policy/Voc/ReportConfig/Create" key="ALT+2" params="id" action="/entityPrepareCreate-rep_parameter" name="Параметр" title="Добавить параметр" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="repconfig"/>
  </tiles:put>
</tiles:insert>