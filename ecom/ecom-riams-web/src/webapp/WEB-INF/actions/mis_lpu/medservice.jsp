<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:section>
    <msh:sectionContent>
    <ecom:webQuery name="journal_double_visit" nativeSql="
    select ms.id,ms.code,ms.name as msname,vwf.name as vwfname,vbt.name as vbtname,vbst.name as vbstname,vrt.name as vrtname
    from medservice ms
    left join workfunctionservice wfs on wfs.medservice_id=ms.id
    left join vocworkfunction vwf on vwf.id=wfs.vocworkfunction_id
    left join vocbedtype vbt on vbt.id=wfs.bedtype_id
    left join vocbedsubtype vbst on vbst.id=wfs.bedsubtype_id
    left join vocroomtype vrt on vrt.id=wfs.roomtype_id
    where wfs.lpu_id=${param.id}
 "/>
        <msh:table name="journal_double_visit" action="entityView-mis_medService.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Код" property="2"/>
            <msh:tableColumn columnName="Наименование услуги" property="3"/>
            <msh:tableColumn columnName="Раб. функция" property="4"/>
            <msh:tableColumn columnName="Профиль коек" property="5"/>
            <msh:tableColumn columnName="Тип коек" property="6"/>
            <msh:tableColumn columnName="Палата" property="7"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>