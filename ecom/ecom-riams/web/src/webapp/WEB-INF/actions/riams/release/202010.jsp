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
					<% // Версия от 1 октября %>
				<h1>Октябрь 2020 года</h1>
				
				<ul>
					<li></li>
					<li>Отчёт по умершим с ПЦР</li>
					<li>Отчёт по COVID для лаборатории по пациентам, состоящим во всех инфекционных отделениях</li>
					<li>Журнал COVID для лаборатории (пациенты по всем инфекционным отделениям за период)</li>
					<li>Механизм листов наблюдения и протоколов для ЕДКЦ (беременные)</li>
					<li>Чек-листы контроля качества по степеням тяжести заболевания + печать чек-листа</li>
					<li>Журнал чек-листов контроля качества по степеням тяжести заболевания</li>
					<li>Настройка в лаб. услуге: всегда доступно для реанимации (вне зависимости от запрета по типу назначений)</li>
					<li>Администрирование: обновление статуса консультаций</li>
					<li>При удалении СЛС - проверка на наличие карт Covid-19, степеней тяжести и чек-листов</li>
					<li>В карте коронавируса:</li>
					<ul>
						<li>- поле Диагноз (mkb) теперь обязательно</li>
						<li>- реализована возможность регистрации в системе первичной, повторной и выписной выгрузки</li>
						<li>- сохраняются дата, время и пользователь (трёх типов выгрузки)</li>
						<li>- результат госпитализации теперь ссылается на справочник из выписки</li>
						<li>- при выписке автоматически проставляется дата и результат в последнюю карту коронавируса в СЛС</li>
						<li>- поля Дата и Результат исхода госпитализации теперь недоступны для редактирования</li>
						<li>- в отчёт по картам добавлены дата,время выгрузок и выписки</li>
                        <li>- в форму 058 выгружаются дата и час первичной сигнализации, дата и время отсылки извещения</li>
                        <li>- в отчёте по картам добавлено открытие в новой вкладке</li>
                        <li>- карта коронавируса разбита на 3 секции</li>
						<li>- в отчёте по картам добавлены: эпид. номер, номер истории и МКБ последней карты в СЛС</li>
						<li>- в карте коронавируса убрано необязательное поле Диагноз</li>
						<li>- при редактировании (а именно при создании копии карты) сохраняются данные об экспорте на портал</li>
                        <li>- Механизм удаления отметки о выгрузке карты</li>
						<li></li>
					</ul>
                    <li>- Печать рестров (первичного, повторного и при выписке)</li>
					<li></li>
				</ul>
					</div>
			</tr>
		</table>
	</tiles:put>
</tiles:insert>