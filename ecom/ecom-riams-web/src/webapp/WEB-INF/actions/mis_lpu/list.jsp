<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">ЛПУ</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key='ALT+N' roles="/Policy/Mis/MisLpu/Create" params="" action="/entityPrepareCreate-mis_lpu" name="Добавить ЛПУ" />

        </msh:sideMenu>
        <msh:sideMenu title="Перейти">
	      <msh:sideLink key="ALT+1" action="/entityParentList-mis_buildingPlace.do?id=-1" name="Список зданий" roles="/Policy/Mis/WorkPlace/View"/>
        <msh:sideLink roles="/Policy/Mis/Asset/PermanentAsset/AutomatedWorkplace/Equipment/CopyingEquipment" action="/entityList-mis_copyingEquipment" name="Копировальное оборудование" title="Показать сведения по копировальному оборудованию" guid="27fe8bc3-ae8d-4e8b-88f2-d23a337f614b" />
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <msh:table name="list" action="entityView-mis_lpu.do" idField="id">
            <msh:tableColumn columnName="Код" property="id" />
            <msh:tableColumn columnName="Код ОМС" property="omcCode" />
            <msh:tableColumn columnName="Наименование ЛПУ" property="name" />
        </msh:table>
    </tiles:put>

</tiles:insert>