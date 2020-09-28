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

        <tags:expertvoc_menu currentAction="e2_bill_st"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <ecom:webQuery name="billList" nativeSql="select b.id, b.billNumber, b.billDate, ric.name as f4_ric
            ,vbs.name as f5_status, b.sum as f6_sum
            ,count(e2.id) as f7_cnt
            , list(el.name) as f8_listName
            ,b.comment as f9_comment
            from e2bill b
            left join voce2billstatus vbs on vbs.id=b.status_id
            left join reg_ic ric on ric.id=b.company_id
            left join e2entry e2 on e2.bill_id=b.id
            left join e2listEntry el on el.id=e2.listEntry_id
            group by b.id, b.billNumber, b.billDate, ric.name,vbs.name, b.sum, b.comment
            order by billdate desc"/>
            <msh:section title='Результат поиска'>
                <msh:table  name="billList" action="entityEdit-e2_bill.do" idField="1" disableKeySupport="true" styleRow="6" deleteUrl="entityDelete-e2_bill.do">
                    <msh:tableColumn columnName="Номер счета" property="2" />
                    <msh:tableColumn columnName="Дата счета"  property="3" />
                    <msh:tableColumn columnName="Статус"  property="5" />
                    <msh:tableColumn columnName="Сумма" property="6" />
                    <msh:tableColumn columnName="Заполнение" property="8" />
                    <msh:tableColumn columnName="Примечание" property="9" />
                    <msh:tableColumn columnName="Кол-во случаев" property="7" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>