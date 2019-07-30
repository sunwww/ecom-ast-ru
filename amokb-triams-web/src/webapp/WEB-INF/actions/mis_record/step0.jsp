<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

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
		<table>
			<tr>
				<td>
					<div class="button fb " onclick="next_pre_record()">
	    				<p class="label">Оформить предварительную запись</p>
	    				<p class="info">Необходимо будет подойти в регистратуру</p>
	    				<p class="info"> за 10-20 минут до приема</p>
	    			</div>
					<br/>
				</td>
				<td>
					<div class="button fb " onclick="next_record_view_table_specialist()">
						<p class="label">Прием врачей-специалистов</p>
						<p class="info">Просмотр расписания специалистов</p>
						<p class="info"> </p>
					</div>
					<br/>
				</td>
			</tr>
			<tr>
				<td>
					<div class="button fb " onclick="next_record_view_table_all_specialist()">
						<p class="label">Расписание всех специалистов</p>
						<p class="info">Просмотр расписания всех специалистов</p>
						<p class="info"> </p>
					</div>
					<br/>
				</td>
				<td>
					<div class="button fb " onclick="next_gosgarant()">
						<p class="label">Программа гос. гарантий</p>
						<p class="info">Программа государственных гарантий</p>
						<p class="info">бесплатного оказания медицинской помощи</p>
					</div>
					<br>
				</td>
			</tr>
			<tr>
				<td>
					<div class="button fb " onclick="next_jnnvlp()">
						<p class="label">перечень ЖНВЛП</p>
						<p class="info">Перечень жизненно необходимых и важнейших </p>
						<p class="info">лекарственных препаратов</p>
					</div>
				</td>
			</tr>
		</table>
    </tiles:put>
    <tiles:put name="javascript" type="string">
    	<script type="text/javascript">
    	function next_pre_record() {
    		window.location = "step_pre_record_info.do" ;
    	}
    	function next_record_direct() {
    		window.location = "step_record_info.do" ;
    	}
    	
    	function next_record_view_table_specialist() {
    		window.location = "step_table_0.do" ;
    	}
    	function next_record_view_table_diag() {
    		window.location = "step_diag_0.do" ;
    	}
    	function next_record_view_table_specialist() {
    		window.location = "step_table_0.do" ;
    	}
    	function next_record_view_table_all_specialist() {
    		window.location = "step_table_1.do" ;
    	}
    	function next_gosgarant() {
    		window.location = "step_gosgarant_info.do" ;
    	}
        function next_jnnvlp() {
            window.location = "step_jnnvlp_info.do?infomat=Infomat" ;
        }
    	</script>
    	<tags:timerGoMain interval="600000"/>
    </tiles:put>
</tiles:insert>