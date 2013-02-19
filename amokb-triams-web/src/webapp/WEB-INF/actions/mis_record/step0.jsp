<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ВЫБЕРИТЕ ПУНКТ МЕНЮ</msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
div.button {
    height: 100px ;
    width: 500px ;  
    font: bold 1.6em/2.2em Arial,Helvetica ;
}
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
	    <div class="button fb " onclick="next_pre_record()">
	    	<p class="label">Оформить предварительную запись</p>
	    	<p class="info">Необходимо будет подойти в регистратура</p>
	    	<p class="info"> за 10-20 минут до приема</p>
	    </div>
	    <br/>
	    <div class="button fb " onclick="next_record_direct()">
	    	<p class="label">Оформить направление</p>
	    	<p class="info">Необходимо будет оформить Ваши анкетные данные.</p>
	    	<p class="info">Затем подойти к врачу к назначенному времени.</p>
	    </div>
	    
    </tiles:put>
    <tiles:put name="javascript" type="string">
    	<script type="text/javascript">
    	function next_pre_record() {
    		window.location = "step_pre_record_info.do" ;
    	}
    	function next_record_direct() {
    		window.location = "step_record_info.do" ;
    	}
    	</script>
    </tiles:put>
</tiles:insert>