<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Medcard">Поиск медицинских карт</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    
        <msh:sideMenu title="Печать">
        	<msh:sideLink action="/print-ticketfirst.do?s=PrintTicketService&amp;m=back" name="1-ая часть талона" 
        		params="" title="1-ая часть талона"/>
        	<msh:sideLink action="/print-ticketback.do?s=PrintTicketService&amp;m=back" name="2-ая часть талона" 
        		params="" title="2-ая часть талона"/>
        </msh:sideMenu>
        <msh:sideMenu>
                <tags:ticket_finds currentAction="medcard"/>
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:form action="/poly_medcards.do" defaultField="number" method="GET">
                <msh:panel>
                    <msh:row>
                        <msh:textField property="number" label="Номер мед. карты" size="30"/>
                        <td><input type='submit' value='Найти'></td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            
        <msh:hideException>
            <msh:section title='Результат поиска'>
                <msh:table 
                	viewUrl="entityShortView-poly_medcard.do"
                	 editUrl="entityParentEdit-poly_medcard.do" deleteUrl="entityParentDeleteGoParentView-poly_medcard.do" name="list" action="entityView-poly_medcard.do" idField="id" disableKeySupport="true">
                    <msh:tableColumn columnName="№мед.карты" property="number"/>                    
                    <msh:tableColumn columnName="Фамилия" property="lastname"/>
                    <msh:tableColumn columnName="Имя" property="firstname"/>
                    <msh:tableColumn columnName="Отчество" property="middlename"/>
                    <msh:tableColumn columnName="Дата рождения" property="birthday"/>
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
    <tiles:put name="javascript" type="string">
    <script type="text/javascript">// <![CDATA[//
    	
    	$('number').focus() ;
    	$('number').select() ;
    //]]></script>    </tiles:put>

</tiles:insert>