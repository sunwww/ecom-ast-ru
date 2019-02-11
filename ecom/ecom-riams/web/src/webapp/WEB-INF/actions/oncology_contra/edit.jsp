<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="style" type="string">
        <style>
            .borderedDiv {
                display: inline-block;
                border: 1px solid;
                width: 40%;
                border-color: rgb(59,92,105) ;
                padding-top:5px;
                padding-bottom:10px;
                padding-left:10px;
            }
        </style>
    </tiles:put>

    <tiles:put name="title" type="string">
        <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="oncology_contraForm" />
    </tiles:put>

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-oncology_contra.do" defaultField="hello" title="Противопоказания и отказы">

            <msh:hidden guid="hiddenId" property="id" />
            <msh:hidden guid="hiddenSaveType" property="saveType" />
            <msh:hidden guid="hiddenParent" property="oncologyCase" />

            <div class="borderedDiv" id="oncologyCase">
                <msh:textField property="date" label="Дата регистрации противопоказания/отказа"/>
                <br>
                <msh:autoComplete  property="contraindicationAndRejection" label="Повод обращения" vocName="vocOncologyN001" fieldColSpan="3" horizontalFill="true" />
            </div>
            <br>
            <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
        </msh:form>
    </tiles:put>


      <tiles:put name="side" type="string">
            <msh:sideMenu guid="sideMenu-123" title="Действия">
                <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityEdit-oncology_contra" name="Изменить" roles="/Policy/Mis/Oncology/Case/Delete" />
                <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-oncology_contra" name="Удалить" roles="/Policy/Mis/Oncology/Case/Delete" />
            </msh:sideMenu>
        </tiles:put>
</tiles:insert>

