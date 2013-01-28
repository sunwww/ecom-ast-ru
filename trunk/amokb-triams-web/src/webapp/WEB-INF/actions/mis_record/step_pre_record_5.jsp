<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="side" type="string">  
    <tags:sideMenu/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ОФОРМЛЕНИЕ ПРЕДВАРИТЕЛЬНОЙ ЗАПИСИ. Шаг 6. Выбор времени</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
 
#liTime,#liTimeBusyForRemoteUser {
font:bold 1.8em/2.2em Arial,Helvetica;
width:90px;
}
#liTimeBusyForRemoteUser {
	background: #F33;
}
#liTime p.label, #liTimeBusyForRemoteUser p.label {
	color:white ;
}


#liTimeBusyForRemoteUser:BEFORE{
	content: "\2718";
} 

    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
     <form id='fmrMain' action="javascript:step6()">
	   <div style="display: inline; position: relative; float: left; margin-right: 40px;">${listTimes}</div>

	    </form>
    </tiles:put>
    <tiles:put name="javascript" type="string">
   	<script type="text/javascript">
    	
    		function step6(aParam) {
    			window.location = "step_pre_record_6.do?"+aParam ;
    		}
    	</script>
    </tiles:put>
</tiles:insert>