<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainInfomatLayout.jsp" flush="true">

    <tiles:put name="side" type="string">
      
    <tags:sideMenu/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Список услуг</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    tr.dep {
    	display: block;
    	width: 97% ;
    }
    #side {
  display: none;
}
#content {
 margin-left: 0;
 width:100% ;
}
table {
	width: 100% ;
	font-size:1em ;
}
th{
background-color:#080E73;
color: white;
}
.busyTd{
	text-decoration: line-through;
	color: red ;
}
td {
}
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <ecom:webQuery name="listService" nativeSql="
    select id,name,cost,comment 
    from priceposition
    where parent_id='${param.id}'
    and dtype='PricePosition'
    "/>
	    <msh:table name="listService" action="javascript:void(0)" idField="1" >
	    	<msh:tableColumn property="2" columnName="Наименование услуги"/>
	    	<msh:tableColumn property="4" columnName="Подготовка к исследованию"/>
	    </msh:table>
	    <a href='step_diag_0.do' style='font-size: 2em;font-weight: bolder;text-align: center;'>НАЗАД</a>
	    <tags:timerGoMain interval="600000"/>
    </tiles:put>

</tiles:insert>