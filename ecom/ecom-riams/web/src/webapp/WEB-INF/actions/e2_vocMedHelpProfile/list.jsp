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
        <msh:sideMenu title="Добавить" guid="fdcda21a-c1c6-4e0e-a74e-1bf843a8c1c8">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocMedHelpProfile" name="Сформировать новое" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="listAll" nativeSql="select voc.id as f1, voc.code as f2, voc.name as f3, voc.profileK as f4, list(vbt.code||' '||vbt.name) as f5
                ,v015.code||' '||v015.name as f6_medSpec
                ,list(v020.code||' '||v020.name) as f7_bed
                 from VocE2MedHelpProfile voc
  left join E2MedHelpProfileBedType stac on stac.profile_id=voc.id left join vocbedtype vbt on vbt.id=stac.bedtype_id
  left join voce2fondv015 v015 on v015.id=voc.medspec_id
  left join voce2fondv020 v020 on v020.id=voc.profilebed_id
  group by voc.id, voc.code, voc.name, voc.profileK,v015.code,v015.name
  order by cast(voc.profilek as int)
"/>

                <msh:table  name="listAll" action="entityView-e2_vocMedHelpProfile.do" idField="1" disableKeySupport="true">
                    <msh:tableColumn columnName="Название" property="3" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Профиль"  property="4" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Привязки"  property="5" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Мед. специальность (V015)"  property="6" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Профиль койки"  property="7" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
</tiles:insert>