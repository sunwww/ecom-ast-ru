<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.infomat}Layout.jsp" flush="true">

    <tiles:put name="side" type="string">
      
    <tags:sideMenu/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Расписание на неделю по специалистам</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
    tr.dep {
    	display: block;
    	width: 97% ;
    }
    td {
    	background-color: white ;
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
padding:5px ;
}
.busyTd{
	text-decoration: line-through;
	color: red ;
}
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
	    ${tableTime}
	    <a href='step_table_1.do?vocWorkFunction=${param.vocWorkFunction}&infomat=${param.infomat}' style='font-size: 2em;font-weight: bolder;text-align: center;'>НАЗАД</a>
	    <tags:timerGoMain interval="600000"/>
    </tiles:put>

</tiles:insert>