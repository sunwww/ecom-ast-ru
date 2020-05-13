<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Disability">Поиск документов нетрудоспособности</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
       <tags:dis_menu currentAction="find_number"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:form action="/dis_documents.do" defaultField="numberTicket" method="GET">
                <msh:panel>
                    <msh:row>
                        <msh:textField property="numberTicket" label="Серия и номер" size="30"/>
                        <td><input type='submit' value='Найти'></td>
                    </msh:row>
			        <msh:row>
			          <msh:commentBox text="СЕРИЯ НОМЕР. &lt;i&gt;Например: AS 15463" colSpan="2" />
			        </msh:row>
                </msh:panel>
            </msh:form>
            
        <msh:hideException>
            <msh:section title='Результат поиска'>
                <msh:table viewUrl="entityShortView-dis_document.do" name="list" action="entityParentView-dis_document.do" idField="id" disableKeySupport="true">
			          <msh:tableColumn columnName="Дата выдачи" property="issueDate" />
			          <msh:tableColumn columnName="Пациент" property="patientFio" />
			          <msh:tableColumn columnName="Статус" property="statusInfo" />
			          <msh:tableColumn columnName="Тип документа" property="documentTypeInfo" />
			          <msh:tableColumn columnName="Первичность" property="primarityInfo" />
			          <msh:tableColumn columnName="Информация" property="info" />
			          <msh:tableColumn identificator="false" property="dateFrom" columnName="Дата начала" />
			          <msh:tableColumn columnName="Дата окончания" identificator="false" property="dateTo" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>

</tiles:insert>