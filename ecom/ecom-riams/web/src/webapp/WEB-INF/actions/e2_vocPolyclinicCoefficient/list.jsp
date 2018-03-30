<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Коэффициенты уровня оказания мед. помощи</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить" guid="fdcda21a-c1c6-4e0e-a74e-1bf843a8c1c8">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocPolyclinicCoefficient" name="Сформировать новое" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="listAll" nativeSql="select voc.id, v015.code||' '||v015.name as f2_spec, voc.visitKz as f3_pos, voc.LongCaseKz as f4_obr
               ,voc.MobileKz as f5_konsMobile, voc.KdoKz as f6_kdo
               ,case when voc.finishDate is not null then 'color:red' end as f7_style
               from VocE2PolyclinicCoefficients voc
               left join VocE2FondV015 v015 on v015.id=voc.speciality_id
    order by v015.name, voc.startdate "/>
                <msh:table  name="listAll" action="entityView-e2_vocPolyclinicCoefficient.do" idField="1" disableKeySupport="true" styleRow="7">
                    <msh:tableColumn columnName="Специальность врача" property="2" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="КЗ посещения"  property="3" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="КЗ обращения"  property="4" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="КЗ конс. приема/моб. пол-ки"  property="5" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="КЗ КДО"  property="6" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>