<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Счета</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>

        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <ecom:webQuery name="billList" nativeSql="select b.id, b.billNumber, b.billDate, ric.name as f4_ric
            ,vbs.name as f5_status, b.sum as f6_sum
            from e2bill b
            left join voce2billstatus vbs on vbs.id=b.status_id
            left join reg_ic ric on ric.id=b.company_id
            order by billdate desc"/>
            <msh:section title='Результат поиска'>
                <msh:table  name="billList" action="entityEdit-e2_bill.do" idField="1" disableKeySupport="true" styleRow="6">
                    <msh:tableColumn columnName="Номер счета" property="2" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Дата счета"  property="3" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Статус"  property="5" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Сумма" property="6" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Страх. компания" property="4" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>