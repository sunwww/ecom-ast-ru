<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">V009 - Результат обращения</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить" guid="fdcda21a-c1c6-4e0e-a74e-1bf843a8c1c8">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocFondV009" name="Создать" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="listAll" nativeSql="select voc.id, voc.code, voc.name, voc.usl
                , list('Результат:'||coalesce(vhr.code,'')||' '||coalesce(vhr.name,'')||', выписан:'||coalesce(vho.code,'')||' '||coalesce(vho.name,'')||',койки:'||coalesce(vbst.code,'')||' '||coalesce(vbst.name,'')) as f5_list  from voce2fondv009 voc
  left join E2FondResultLink link on link.result_id=voc.id
  left join VocHospitalizationResult vhr on vhr.code=link.MedosHospResult
  left join VocHospitalizationOutcome vho on vho.code=link.medosHospOutcome
  left join VocBedSubType vbst on vbst.code=link.bedSubType
  group by voc.id, voc.code, voc.name, voc.usl order by voc.usl, voc.code"/>
                <msh:table  name="listAll" action="entityView-e2_vocFondV009.do" idField="1" disableKeySupport="true">
                    <msh:tableColumn columnName="Условия оказания помощи"  property="4" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Название" property="3" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Код"  property="2" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Привязки"  property="5" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                </msh:table>

            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>