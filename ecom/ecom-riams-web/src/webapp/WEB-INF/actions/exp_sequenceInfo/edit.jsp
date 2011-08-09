<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/exp" prefix="exp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='body' type='string'>
    	<!-- 
    	  - Последовательность
    	  -->
        <msh:form action="/entitySaveGoView-exp_sequenceInfo.do" defaultField="uniqueName">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>

            <msh:panel>

                <msh:row>
                    <msh:textField property="uniqueName" label="Наименование" size="50"/>
                </msh:row>

                <msh:row>
                    <msh:textArea property="comment" label="Комментарий" horizontalFill="true"/>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="2"/>
            </msh:panel>
        </msh:form>
        
    </tiles:put>
    
    <tiles:put name='title' type='string'>
    	<msh:title guid='hello' mainMenu="Config">Название</msh:title>
    </tiles:put>



    <tiles:put name='side' type='string'>
        <msh:sideMenu>
            <msh:sideLink key="ALT+2" params="id" action="/entityEdit-exp_sequenceInfo" name="Изменить" roles="/Policy/Exp/FillTime/Edit"/>
            <msh:sideLink key="ALT+DEL" confirm="Удалить последовательность?" params="id" action="/entityDelete-exp_fillTime" name="Удалить" roles="/Policy/Exp/FillTime/Delete"/>
        </msh:sideMenu>
    </tiles:put>
    
</tiles:insert>