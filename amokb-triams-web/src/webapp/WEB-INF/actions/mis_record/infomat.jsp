<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainInfomatLayout.jsp" flush="true">

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
	    <div class="button fb " onclick="next_record_view_table_specialist()">
	    	<p class="label">Прием врачей-специалистов</p>
	    	<p class="info">Просмотр расписания специалистов</p>
	    	<p class="info"> </p>
	    </div>
	    <br/>
	    <div class="button fb " onclick="next_record_view_table_all_specialist()">
	    	<p class="label">Расписание всех специалистов</p>
	    	<p class="info">Просмотр расписания всех специалистов</p>
	    	<p class="info"> </p>
	    </div>
	    <br/>
	    <div class="button fb " onclick="next_record_view_table_diag()">
	    	<p class="label">Услуги, оказываемые в больнице</p>
	    	<p class="info">Просмотр прейскуранта по услугам</p>
	    	<p class="info">специалистов </p>
	    </div>
		<br/>
	    <div class="button fb " onclick="next_gosgarant()">
	    	<p class="label">Программа гос. гарантий</p>
	    	<p class="info">Постановление Правительства Астраханской области</p>
	    	<p class="info">от 29 декабря 2017г №546-П.</p>
	    </div>
		<br/>
		<div class="button fb " onclick="next_jnnvlp()">
			<p class="label">перечень ЖННВЛП</p>
			<p class="info">Перечень жизненно необхдимых и важнейших лек. преп.</p>
			<p class="info">от 23 октября 2017г №2323-р.</p>
		</div>
    </tiles:put>
    <tiles:put name="javascript" type="string">
    	<script type="text/javascript">
    	function next_pre_record() {
    		window.location = "step_pre_record_info.do?infomat=Infomat" ;
    	}
    	function next_record_view_table_diag() {
    		window.location = "step_diag_0.do?infomat=Infomat" ;
    	}
    	function next_record_view_table_specialist() {
    		window.location = "step_table_0.do?infomat=Infomat" ;
    	}
    	function next_record_view_table_all_specialist() {
    		window.location = "step_table_1.do?infomat=Infomat" ;
    	}
    	function next_gosgarant() {
    		window.location = "step_gosgarant_info.do?infomat=Infomat" ;
    	}
        function next_jnnvlp() {
            window.location = "step_jnnvlp.do?infomat=Infomat" ;
        }
    	</script>
    </tiles:put>
</tiles:insert>