<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Voc">Поиск Лек.Ср (торг.)</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    <msh:sideMenu title="Добавить" >
      <msh:sideLink  params="name" key="ALT+N" action="/entityPrepareCreate-voc_drug" name="Лек.Ср" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="drugTrade"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:form action="/voc_drugs.do" defaultField="name" method="GET" >
                <msh:panel  colsWidth="10%,60%,1%,1%,1%">
					<msh:row>
                        <msh:textField property="name" label="Наименование" fieldColSpan="1" horizontalFill="true"/>
                    
                         <td colspan=1 align="left"><input type='submit' value='Найти'></td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            
        <% if (request.getAttribute("name")!=null && !request.getAttribute("name").equals("")) {%>
        <msh:hideException>
        
            <msh:section title='Результат поиска'>
            
            	<ecom:webQuery name="drugs" nativeSql="select dt.id as dtid, dt.code as dtcode, dt.name as dtname, dn.name as dnname,dl.name as dlname from VocDrugClassify dt left join VocDrugUnlicensedName as dn on dn.id=dt.drugUnlicensedName_id left join VocLicensedName dl on dl.id=dt.licensedName_id where UPPER(dt.name) like UPPER('%${name}%') or UPPER(dl.name) like UPPER('%${name}%') or UPPER(dn.name) like UPPER('%${name}%')"/>
                <msh:table name="drugs" action="entityView-voc_drug.do" idField="1" disableKeySupport="false">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Код" property="2"/>
                    <msh:tableColumn columnName="Наим. торговое" property="3"/>
                    <msh:tableColumn columnName="Наим. патентованное" property="5"/>
                    <msh:tableColumn columnName="Наим. непатентованное" property="4"/>
                </msh:table>
            </msh:section>
        </msh:hideException>
        <%} else {%>
        	<i>Введите параметры поиска</i>
        <%} %>
    </tiles:put>

</tiles:insert>