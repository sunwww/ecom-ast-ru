<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Справочник подтипов случая</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocEntrySubType" name="Создать" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="e2_vocEntrySubType_st"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="tariffList" nativeSql="select voc.id, voc.code||' '||coalesce(voc.name,'') as name,voc.tariffCode, usl.code||' '||usl.name as uslName
                ,case when voc.isArchival='1' then 'color:red' end as f5_color
                ,vvs.code||' '||vvs.name
                ,v025.code||' '||v025.name as а7_visitPurpose
    from VocE2EntrySubType voc
    left join VocE2VidSluch vvs on vvs.id=voc.vidSluch_id
    left join VocE2FondV006 usl on usl.id=voc.uslok_id
    left join VocE2FondV025 v025 on v025.id=voc.visitPurpose_id
    order by voc.code
    " />
                <msh:table  name="tariffList" action="entityView-e2_vocEntrySubType.do" idField="1" disableKeySupport="true" styleRow="5">
                    <msh:tableColumn columnName="Тип записи" property="2" />
                    <msh:tableColumn columnName="Код тарифа" property="3" />
                    <msh:tableColumn columnName="Условия оказания" property="4" />
                    <msh:tableColumn columnName="Вид случая" property="6" />
                    <msh:tableColumn columnName="Цель посещения" property="7" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>