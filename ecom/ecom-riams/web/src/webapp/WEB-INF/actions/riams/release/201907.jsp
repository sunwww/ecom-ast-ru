<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

	<tiles:put name='title' type='string'>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
					<% // Версия от 03 июля %>
				<h1>Июль 2019 года</h1>
				
				<ul>
					<li></li>
					<h2>Стационар:</h2>
					<ul>
						<li> * В ежеквартальном отчёте по внутреннему контролю качества добавлено: вывод ИТОГО и разбивка процентов по дефектам </li>
					</ul>
					<h2>Лаборатория:</h2>
					<ul>
						<li> * Если отметить БРАК в уже подтверждённом назначении, в начале связанного с анализом дневника появится причина и дата-время брака </li>
						<li> * В меню "Забор биотмат.", "Передача биомат. в лаборатории", "Рабочее место" теперь выводится отметка о ВМП перед фамилией пациента  </li>
					</ul>
                    <h2>Общее:</h2>
                    <ul>
                        <li> * К каждой услуге в направлении теперь можно добавить комментарий </li>
                    </ul>


					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
							<br>
						</msh:ifInRole>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>