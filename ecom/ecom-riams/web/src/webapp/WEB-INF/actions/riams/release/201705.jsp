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
				<h1>Май 2017 года</h1>
				
				<ul>
				<li>Диспансеризация: возможность добавления в карту диспансеризации назначений</li>
				<li>Изменен алгоритм подсчета даты начала и окончания нетрудоспособности(считается правильно)</li>
					<li><msh:ifInRole roles="/Policy/Config/HelpAdmin">
						<br>
						
					</msh:ifInRole>
					</li>
				<li>1. Вызов калькулятора клубочковой фильтрации, если этот дневник первый и это госпитализация.</li>
<li>2. В сообщении об ограничении редактирования дневника выходит дата, указанная в настройках, а не 24 часа.</li>
<li>3. Переход на создания разрешениея на редактирование протокола/выписки из самого протокола/выписки</li>
<li>4. При объединении пациентов переносятся данные об экспертных картах.</li>
<li>5. Запрет всех значений поля причина выписки, кроме «Плановая» если результат «Смерть»</li>
<li>6. Добавлена возможность просматривать кто и когда отменил/создал/принял биоматериал.
    <msh:ifInRole roles="/Policy/Config/HelpAdmin">Просмотр по политике /Policy/Mis/Prescription/ViewInformation</msh:ifInRole></li>
<li>7. Уведомление об экстренной госпитализации иностранцев (при госпитализации иностранцев создается сообщение)</li>
<li>8. Вывод данных о предв. госпитализациях пациента на будущие даты</li>
<li>9. О отчет по родам добавлено поле "срок гестации", изменен алгоритм поиска данных: поиск идет по дате окончания родов, а не дате начала СЛО в родовом отделении.</li>
<li>10.Реализован запрет на экстренную госпитализацию на дневные койки
    <msh:ifInRole roles="/Policy/Config/HelpAdmin">Для исключения запрета необходимо добавить пользователю роль с политикой
    "/Policy/Mis/MedCase/Stac/Sso/CreateEmergencyDayHospBedFund"</msh:ifInRole></li>
<li>11. Реализован запрет на создание дневников с "будущей" датой регистрации
    <msh:ifInRole roles="/Policy/Config/HelpAdmin">Для разрешения создания таких дневников необходимо пользователю добавить роль с политикой " /Policy/Mis/MedCase/Protocol/AllowCreateDiaryFutureTime "</msh:ifInRole></li>
				</ul>
				</div>
			</tr>

			
		</table>
	</tiles:put>
</tiles:insert>