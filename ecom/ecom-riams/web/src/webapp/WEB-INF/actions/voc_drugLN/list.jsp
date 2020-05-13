<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Voc">Поиск Лек.Ср (патен)</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    <msh:sideMenu title="Добавить" >
      <msh:sideLink params="name" key="ALT+N" action="/entityPrepareCreate-voc_drugLN" name="Лек.Ср (патен.)" />
    </msh:sideMenu>
    <tags:voc_menu currentAction="drugLN"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:form action="/voc_drugLNs.do" defaultField="name" method="GET" >
                <msh:panel  colsWidth="10%,60%,1%,1%,1%">
					<msh:row>
                        <msh:textField property="name" label="Наименование" fieldColSpan="1" horizontalFill="true"/>
                    
                         <td colspan=1 align="left"><input type='submit' value='Найти'></td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            
<% if (request.getAttribute("name")!=null && !request.getAttribute("name").equals("")) {%>        <msh:hideException>
            <msh:section title='Результат поиска'>
            	<ecom:webQuery name="drugLNs" nativeSql="select id, code, name from VocLicensedName where UPPER(name) like UPPER('%${name}%')"/>
                <msh:table name="drugLNs" action="entityView-voc_drugLN.do" idField="1" disableKeySupport="false">
                    <msh:tableColumn columnName="Код" property="2"/>
                    <msh:tableColumn columnName="Наименование" property="3"/>
                </msh:table>
            </msh:section>
        </msh:hideException>
        <%} else {%>
        	<i>Введите параметры поиска</i>
        <%} %>
            </tiles:put>

</tiles:insert>