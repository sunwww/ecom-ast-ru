<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">Браслеты пациента</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+1" params="id" action="/javascript:window.history.back()" name="⇧ Назад"/>
            <msh:sideLink key='ALT+N' params="id" action="/entityParentPrepareCreate-mis_colorIdentity" name="Добавить цвет" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name="body" type="string">
        <ecom:webQuery name="colorList" nameFldSql="colorList_sql" nativeSql="select vcip.id,vc.name as vcname,vcip.name as vsipname,case when isfornewborn=true then 'Да' else 'Нет' end from voccoloridentitypatient vcip  left join voccolor vc on vc.id=vcip.color_id where lpu_id=${param.id}"/>
        <msh:section title="Все цвета">
            <msh:table name="colorList" action="entityParentView-mis_colorIdentity.do" idField="1">
                <msh:tableColumn columnName="Цвет" property="2" />
                <msh:tableColumn columnName="Болезнь/примечание" property="3" />
                <msh:tableColumn columnName="Заполнение в родах" property="4" />
            </msh:table>
        </msh:section>
    </tiles:put>
</tiles:insert>