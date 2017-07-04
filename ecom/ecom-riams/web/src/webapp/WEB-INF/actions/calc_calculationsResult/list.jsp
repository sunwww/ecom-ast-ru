<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

<!-- Upper -->
<tiles:put name="title" type="string">
<ecom:titleTrail mainMenu="Patient" beginForm="stac_sloForm"/>
</tiles:put>


<tiles:put name="body" type="string">
<ecom:webQuery name="list" nativeSql="select cr.id,c.name, cr.result from calculationsresult cr 
left join calculator c on c.id=cr.calculator_id left join medcase m on cr.departmentmedcase_id = m.id
where m.id =${param.id}" />
           
           <msh:table name="list" action="entityParentView-calc_calculationsResult.do" idField="1">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Название" property="2"/>
            <msh:tableColumn columnName="Результат" property="3"/>
             </msh:table>

</tiles:put>

  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-calc_calculationsResult" name="Новый расчет" title="Добавить случай стационарного лечения в отделении" 
      roles="/Policy/Mis/Calc/Calculation/Create" key="ALT+2"/>
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>
