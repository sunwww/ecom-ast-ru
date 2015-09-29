<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu"/>
    </tiles:put>

    <tiles:put name='body' type='string' >
<msh:section>
<msh:sectionTitle>Список объемов коечного фонда</msh:sectionTitle>
<msh:sectionContent>
<ecom:webQuery name="list" nativeSql="select bfc.id, vbt.name as vbtname, fs.name as fsname, to_char(startdate,'dd.MM.yyyy') as dateS,to_char(finishdate,'dd.MM.yyyy') as dateF 
,vbst.name as vbst_name  
,ml.name as ml_name
from bedfundcapacity bfc
left join mislpu ml on ml.id=bfc.department
left join vocbedtype vbt on vbt.id=bfc.bedtype
left join vocbedsubtype vbst on vbst.id=bfc.bedsubtype
left join vocfinancesource fs on fs.id=bfc.financesource"/>
<msh:table name="list" action="entityView-mis_bedFundCapacity.do" idField="1">
            <msh:tableColumn columnName="Тип коек" property="2"/>
            <msh:tableColumn columnName="Источник оплаты" property="3"/>
            <msh:tableColumn columnName="Тип коек" property="6"/>
            <msh:tableColumn columnName="Отделение" property="7"/>
            <msh:tableColumn columnName="Дата начала действия" property="4"/>
            <msh:tableColumn columnName="Дата окончания действия" property="5"/>
        </msh:table>
</msh:sectionContent>
</msh:section>

        
    </tiles:put>
<tiles:put name="side" type="string">
<msh:sideMenu>
	<msh:sideLink key="ALT+2" action="/entityPrepareCreate-mis_bedFundCapacity.do" name="Создать" title="Содать" roles="/Policy/Mis/BedFund/Create"/>
</msh:sideMenu>
	</tiles:put>
</tiles:insert>