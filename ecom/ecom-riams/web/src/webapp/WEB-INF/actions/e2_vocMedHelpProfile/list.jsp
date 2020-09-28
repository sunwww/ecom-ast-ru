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
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocMedHelpProfile" name="Сформировать новое" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="e2_vocMedHelpProfile_st"/>
    </tiles:put>
<%
    request.setAttribute("whereSql",request.getParameter("show")!=null ? "" : " where voc.noActuality is null or voc.noActuality='0'");
%>
    <tiles:put name='body' type='string'>
        <label><input type="checkbox" title="Показать все записи" onclick="showAll()"/>Показать всё</label>
        <msh:hideException>
            <msh:section title='Результат поиска'>
                <ecom:webQuery name="listAll" nativeSql="select voc.id as f1, voc.code as f2, voc.name as f3, list(vbt.code||' '||vbt.name) as f4
                ,v021.code||' '||v021.name as f6_medSpec
                , case when voc.finishDate is not null then 'color:red' when voc.noActuality='1' then 'color:gray' else '' end as f7_styleRow
                 from VocE2MedHelpProfile voc
  left join E2MedHelpProfileBedType stac on stac.profile_id=voc.id
  left join voce2fondv020 vbt on vbt.id=stac.bedprofile_id
  left join voce2fondv021 v021 on v021.id=voc.medspecV021_id
  ${whereSql}
  group by voc.id, voc.code, voc.name, v021.code,v021.name, voc.finishdate, voc.noactuality
  order by cast(voc.code as int)
"/>

                <msh:table  name="listAll" styleRow="6" action="entityView-e2_vocMedHelpProfile.do" idField="1" disableKeySupport="true">
                    <msh:tableColumn columnName="Профиль"  property="2" />
                    <msh:tableColumn columnName="Название" property="3" />
                    <msh:tableColumn columnName="Привязки"  property="5" />
                    <msh:tableColumn columnName="Мед. специальность (V021)"  property="5" />
                    <msh:tableColumn columnName="Профиль койки"  property="4" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
    <tiles:put name="javascript" type="string">
            <script type="text/javascript">
                function showAll() {
                    window.location.href+="?show";
                }
            </script>
    </tiles:put>
</tiles:insert>