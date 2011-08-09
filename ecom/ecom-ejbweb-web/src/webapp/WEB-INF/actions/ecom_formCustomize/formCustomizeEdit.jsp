<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>


<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
       <h1>Настройка поля</h1>

       <%@ include file="formCustomizeForm.jsp" %>

	<msh:section title='Подсказка'>
		<ul style='margin-left: 1em'>
			<li><a href='#' onclick='setDefault("\${currentDate}")'>\${currentDate}</a> Текущая дата</li>
			<li><a href='#' onclick='setDefault("\${currentTime}")'>\${currentTime}</a> Текущее время</li>
		</ul>
	</msh:section>
		
     </tiles:put>

	
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
        	function setDefault(aDefault) {
        		$("default").value = $("default").value + aDefault ;
        	}
        </script>
    </tiles:put>

</tiles:insert>
