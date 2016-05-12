<%@ page import="ru.ecom.expomc.web.actions.importdata.ImportDataViewHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/exp" prefix="exp" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <h1>/ Документ / По дате / Список сообщений</h1>
    </tiles:put>


    <tiles:put name='body' type='string'>
        <msh:section>
            <msh:sectionTitle>Изменения</msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="changes" action="" idField="messageId" noDataMessage="Не было изменений">
                    <msh:tableColumn columnName="Свойство" property="property"/>
                    <msh:tableColumn columnName="Старое значение" property="oldValue"/>
                    <msh:tableColumn columnName="Новое значение" property="newValue"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>


        <msh:section title='Поля, на которые следует обратить внимание'>
        	<exp:messageBadProperties />
        </msh:section>
        
		<msh:section title='Найденные полисы'>
            <msh:table name="policySuggest" action="exp_policyAccept.do" idField="id">
                <msh:tableColumn columnName="Тип поиска" property="findedType"/>
                <msh:tableColumn columnName="Компания" property="insuranceCompany"/>
                <msh:tableColumn columnName="Полис" property="policySeriesNumber"/>
                <msh:tableColumn columnName="Снилс" property="snils"/>
                <msh:tableColumn columnName="Дата рождения" property="birthDate"/>
                <msh:tableColumn columnName="Год рождения" property="birthYear"/>
                <msh:tableColumn columnName="ФИО" property="fio"/>
            </msh:table>
		</msh:section>
		
        <msh:section>
            <msh:sectionTitle>Данные</msh:sectionTitle>
            <msh:sectionContent>
            </msh:sectionContent>
        </msh:section>
        <%
            ImportDataViewHelper.print(request.getAttribute("entity"), out);
        %>

    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
	        <exp:messageUrlEdit/>
	        <msh:sideLink action="/exp_nextMessage" params="id" title="Переход на следующее сообщение" name="Следующее сообщение"/>
        </msh:sideMenu>
    </tiles:put>

</tiles:insert>