<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Voc">Просмотр согласий пациента на передачу данных</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить" guid="dcf2e072-d44e-47ca-ad16-db0ec61e35c8" >
            <msh:sideLink key="ALT+N" roles="/Policy/Voc/VocKiliDefect/Create" action="/entityPrepareCreate-voc_post" name="Дефект" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:section>
            <msh:sectionTitle>Справочник дефектов</msh:sectionTitle>
            <msh:sectionContent>
                <ecom:webQuery nativeSql="
	       			select id, externalcode , dateStart, dateTo, phoneNumber, email from patientExternalServiceAccount where patient_id=${param.id}" name="list"/>
                <msh:table name="list" action="entityParentEdit-mis_patientExternalServiceAccount.do" idField="1" disableKeySupport="true">
                    <msh:tableColumn columnName="Код" property="2"/>
                    <msh:tableColumn columnName="Дата получения согласия" property="3"/>
                    <msh:tableColumn columnName="Телефон" property="5"/>
                    <msh:tableColumn columnName="Адрес электронной почты" property="6"/>
                    <msh:tableColumn columnName="Дата аннулирования согласия" property="4"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

    </tiles:put>

</tiles:insert>