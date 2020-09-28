<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <tiles:put name="side" type="string">
            <msh:ifFormTypeAreViewOrEdit formName="calc_riskForm">
                <msh:sideMenu>
                    <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-calc_risk" name="Изменить" title="Изменить" roles="/Policy/Mis/Calc/Calculation/Edit"/>
                    <msh:sideLink key="ALT+DEL" confirm="Вы точно хотите удалить?" params="id" action="/entityParentDeleteGoParentView-calc_risk" name="Удалить" title="Удалить" roles="/Policy/Mis/Calc/Calculation/Edit"/>
                </msh:sideMenu>
            </msh:ifFormTypeAreViewOrEdit>
        </tiles:put>
        <msh:form action="/entityParentSaveGoParentView-calc_risk.do" defaultField="riskValue">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="calculator" />
            <msh:panel>
                <msh:row>
                    <msh:textField property="riskValue" label="Название" size="150" />
                </msh:row>
                <msh:row>
                    <msh:textField property="lowScore" label="Нижнее значение баллов" size="15" />
                </msh:row>
                <msh:row>
                    <msh:textField property="upScore" label="Верхнее значение баллов" size="15" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="2" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Calculator" beginForm="calc_riskForm"/>
    </tiles:put>
    <tiles:put name="javascript" type="string">
    <script type="text/javascript" >
        eventutil.addEventListener($('lowScore'), "change",function(){
            $('lowScore').value=parseInt($('lowScore').value);
            if ($('lowScore').value=="NaN") $('lowScore').value="";
        }) ;
        eventutil.addEventListener($('upScore'), "change",function(){
            $('upScore').value=parseInt($('upScore').value);
            if ($('upScore').value=="NaN") $('upScore').value="";
        }) ;
    </script>
    </tiles:put>
</tiles:insert>