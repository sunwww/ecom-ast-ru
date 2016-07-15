<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

	<tiles:put name='title' type='string'>
	</tiles:put>

	<tiles:put name='body' type='string'>
		<table class='mainMenu'>
			<tr>
				<td class='col1'>
				<div class='menu'>
				<h1>Июль 2016 года</h1>
				<ul>
					<li>Ежедневный отчет - отображение пациентов, лежащих по ДМС</li>
					<li>Доработана печать истории родов. В шаблон печатается данные по госпитализации, данные по родам и новорожденным</li>
					<li>Реализован экспорт плана ДД в ФОМС</li>
					<li>Добавлен запрет на создание операции позднее текущей даты
					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
						<br>
						для отключения ограничения нужно подключить роль с политикой: /Policy/Mis/MedCase/Stac/Ssl/SurOper/CreateAnyTime
					</msh:ifInRole>
					</li>
					<li>РОДЫ:</li>
					<li>Доработано создание новорожденного отдельно от родов: корректно заводится ФИО, создается госпитализация</li>
					<li>Добавлен запрет на создание перевод из отделения и выписку пациента без заполнения данных по родам
					<msh:ifInRole roles="/Policy/Config/HelpAdmin">
						<br>
						для отключения ограничения нужно подключить роль с политикой: /Policy/Mis/MedCase/Stac/Ssl/Discharge/DontCheckPregnancy
					</msh:ifInRole>
					</li>
					<li>Запрет на создание нескольких родов в отделении</li>
					<li>Если ребенок родился мертвый, госпитализация и пациент не создается</li>
					<li>Если заведен полный случай родов, запрет на создание новорожденных</li>
					<li></li>
					<li>Отчет по летальности: добавлен фильтр "тип коек"</li>
					<li>Создан запрет на заведение талонов на дату, позже даты смерти пациента</li>
				
				</ul>
				
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>