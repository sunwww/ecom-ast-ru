<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">V020 - Справочник профилей коек</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:expertvoc_menu currentAction="e2_vocFondV020_st"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="listAll" nativeSql="select voc.id, voc.code, voc.name, list(vp.code||' '||vp.name) as f4_vocPost
                , case when voc.finishDate is not null then 'color:red' else '' end as f5_styleRow
                ,vms.code||' '||cast(vms.name as varchar(30)) as f6_ms
                from voce2fondv020 voc
                left join E2MedHelpProfileBedType link on link.bedProfile_id=voc.id
                left join voce2medhelpprofile vp on vp.id=link.profile_id
                left join vocmedservice vms on vms.id=voc.defaultStacMedService_id
                group by voc.id, voc.code, voc.name, vms.id, vms.code, vms.name, voc.finishdate order by cast(voc.code as int)"/>
                <msh:table  name="listAll" styleRow="5" action="entityView-e2_vocFondV020.do" idField="1" disableKeySupport="true">
                    <msh:tableColumn columnName="Код"  property="2" />
                    <msh:tableColumn columnName="Название" property="3" />
                    <msh:tableColumn columnName="Профили помощи V002"  property="4" />
                    <msh:tableColumn columnName="Услуга по умолчанию"  property="6" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>