<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Реестры омс</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocMedServiceCost" name="Создать цену" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="e2_vocMedServiceCost_st"/>
    </tiles:put>
    <tiles:put name='body' type='string'>
        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="listAll" nativeSql="select voc.id as f1, vms.code||' '||vms.name as f2
                ,voc.cost, voc.startDate, voc.finishDate, v021.code||v021.name as f6_v021
                 from VocOmcMedServiceCost voc
  left join vocmedservice vms on vms.id=voc.medservice_id
  left join VocE2FondV021 v021 on v021.id=voc.workFunction_id
  order by voc.startDate, vms.code
"/>

                <msh:table  name="listAll" styleRow="6" action="entityView-e2_vocMedServiceCost.do" idField="1" disableKeySupport="true">
                    <msh:tableColumn columnName="Услуга"  property="2" />
                    <msh:tableColumn columnName="Цена" property="3" />
                    <msh:tableColumn columnName="Начало действия"  property="4" />
                    <msh:tableColumn columnName="Окончание действия"  property="5" />
                    <msh:tableColumn columnName="Должность"  property="6" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
    <tiles:put name="javascript" type="string">
            <script type="text/javascript">

            </script>
    </tiles:put>
</tiles:insert>