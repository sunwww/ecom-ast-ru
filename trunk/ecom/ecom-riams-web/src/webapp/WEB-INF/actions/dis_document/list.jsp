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
			        <msh:row guid="b729833a-a47b-437e-8d1c-b3362a03ce80">
			          <msh:commentBox text="СЕРИЯ НОМЕР. &lt;i&gt;Например: AS 15463" guid="5c197db1-df55-446f-ada6-da48c26f4a6c" colSpan="2" />
			        </msh:row>
                </msh:panel>
            </msh:form>
            
        <msh:hideException>
            <msh:section title='Результат поиска'>
                <msh:table viewUrl="entityShortView-dis_document.do" name="list" action="entityParentView-dis_document.do" idField="id" disableKeySupport="true">
			          <msh:tableColumn columnName="Дата выдачи" property="issueDate" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
			          <msh:tableColumn columnName="Пациент" property="patientFio" />
			          <msh:tableColumn columnName="Статус" property="statusInfo" />
			          <msh:tableColumn columnName="Тип документа" property="documentTypeInfo" guid="71edd77-ddd1-4ed-453fa2687534" />
			          <msh:tableColumn columnName="Первичность" property="primarityInfo" guid="71edd774-ddd1-4e0b-ae7d-453fa2687534" />
			          <msh:tableColumn columnName="Информация" property="info" guid="d61b9d49-a94d-4cf2-af1b-05020213901f" />
			          <msh:tableColumn identificator="false" property="dateFrom" guid="7623c0df-b830-43de-9e73-957a91423b77" columnName="Дата начала" />
			          <msh:tableColumn columnName="Дата окончания" identificator="false" property="dateTo" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>

</tiles:insert>