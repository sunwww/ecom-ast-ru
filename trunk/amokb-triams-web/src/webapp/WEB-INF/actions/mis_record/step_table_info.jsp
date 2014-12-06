<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="side" type="string">  
    <tags:sideMenu/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ПОРЯДОК ОКАЗАНИЯ МЕДИЦИНСКОЙ ПОМОЩИ</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
div.button {
    height: 100px ;
    width: 500px ;  
    font: bold 1.6em/2.2em Arial,Helvetica ;
}

.cntInfo {
	font-size: 1.5em;
}
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <p class="cntInfo">Необходимо при себе иметь следующие документы:</p>
    <ul>
    <li class="cntInfo">паспорт;</li>
    <li class="cntInfo">полис ОМС;</li>
    <li class="cntInfo">СНИЛС;</li>
    <li class="cntInfo">направления с результатами анализов и обследованиями.</li>
    </ul>
	    <div class="divRecord button fb" onclick="next_pre_record()">
	    	<p class="label">Далее</p>
	    	<p class="info">подтверждаю наличие документов</p>
	    </div>    
    </tiles:put>
    <tiles:put name="javascript" type="string">
    	<script type="text/javascript">
    	function next_pre_record() {
    		window.location = "${path_rec}0.do" ;
    	}

    	</script>
    	<tags:timerGoMain interval="600000"/>
    </tiles:put>
    
</tiles:insert>