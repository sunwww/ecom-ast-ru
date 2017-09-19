
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Voc">Лекарственные средства</msh:title>
    </tiles:put>

    <tiles:put name='body' type='string' >

        <msh:section>
            <msh:sectionTitle>Номенклатуры на складах отделений</msh:sectionTitle>
            <msh:sectionContent>
                <ecom:webQuery name="list" nativeSql="select pdn.id, vd.name as ls, pdn.amount,pdn.party,ml.name as dep, pop.dateoperation from pharmdrugnomenklatr pdn
left join vocdrug vd on vd.id = pdn.vocdrug_id
left join pharmstorage ps on ps.id= pdn.storage_id
left join mislpu ml on ml.id = ps.department_id
left join pharmoperation pop on pop.id = pdn.incomeoperation_id
        "/>
                <msh:table name="list" action="entityEdit-directory_department.do" idField="1">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Лек.средство" property="2"/>
                    <msh:tableColumn columnName="Кол-во" property="3"/>
                    <msh:tableColumn columnName="Партия" property="4"/>
                    <msh:tableColumn columnName="Отделение" property="5"/>
                    <msh:tableColumn columnName="Дата прихода" property="6"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>


        <msh:section>
        <msh:sectionTitle>Остатки на складах отделений</msh:sectionTitle>
        <msh:sectionContent>
        <ecom:webQuery name="list" nativeSql="select pd.id,vd.name,ml.name as lpu,CAST(pd.amount as float8),ceiling(pd.amount/ vd.packqn) as countpack
        from pharmdrug pd
        left join vocdrug vd on vd.id = pd.drug_id
        left join pharmstorage ps on ps.id = pd.pharmstorage_id
        left join mislpu ml on ml.id = ps.department_id
        where pd.nextstate_id is null
        "/>
        <msh:table name="list" action="entityEdit-directory_department.do" idField="1">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Лек.средство" property="2"/>
            <msh:tableColumn columnName="Отделение" property="3"/>
            <msh:tableColumn columnName="Кол-во" property="4"/>
            <msh:tableColumn columnName="Кол-во упаковок" property="5"/>
        </msh:table>
        </msh:sectionContent>
        </msh:section>


        <msh:section>
            <msh:sectionTitle>Операции</msh:sectionTitle>
            <msh:sectionContent>
                <ecom:webQuery name="list" nativeSql="select po.id,
case when po.dtype='PharmIncomeOperation' then 'Приход' when po.dtype='PharmReserveOperation' then 'Резервирование' end
,coalesce(vd.name,vd2.name),
CAST(po.amount as float8),po.countpack,to_char(po.dateoperation,'dd.MM.yyyy')||' '||to_char(po.timeoperation,'HH24:MI:ss') as datetime,
coalesce(ml.name,ml2.name) as lpu
from pharmoperation po
left join vocdrug vd on vd.id = po.vocdrug_id
left join pharmdrug pd on pd.id = po.pharmdrug_id
left join vocdrug vd2 on vd2.id = pd.drug_id
left join pharmstorage ps on ps.id = po.pharmstorage_id
left join mislpu ml on ml.id = ps.department_id
left join prescription pres on pres.id = po.prescription or pres.id = po.prescript_id
left join prescriptionlist pl on pl.id = pres.prescriptionlist_id
left join medcase mc on mc.id = pl.medcase_id
left join mislpu ml2 on ml2.id = mc.department_id
        "/>
                <msh:table name="list" action="entityEdit-directory_department.do" idField="1">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Операция" property="2"/>
                    <msh:tableColumn columnName="Наименование" property="3"/>
                    <msh:tableColumn columnName="Кол-во единиц" property="4"/>
                    <msh:tableColumn columnName="Кол-во упаковок" property="5"/>
                    <msh:tableColumn columnName="Дата" property="6"/>
                    <msh:tableColumn columnName="Отделение" property="7"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

    </tiles:put>
</tiles:insert>