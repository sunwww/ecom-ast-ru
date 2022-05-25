<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="style" type="string">
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoParentView-stac_surImplant.do" defaultField="serialNumber"
                  editRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit"
                  createRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create">
            <msh:panel colsWidth="15px,250px,15px">
                <msh:hidden property="id"/>
                <msh:hidden property="operation"/>
                <msh:hidden property="saveType"/>
                <msh:row>
                    <msh:textField property="serialNumber" labelColSpan="1"
                                   fieldColSpan="3" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="type" fieldColSpan="3"
                                      horizontalFill="true" vocName="vocSurgicalImplant"/>
                </msh:row>
            </msh:panel>
        </msh:form>
     


    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="StacJournal" beginForm="stac_surImplantForm"/>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="stac_surImplantForm">
            <msh:sideMenu title="Хир. операция">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-stac_surImplant" name="Изменить"
                              roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit"/>
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id"
                              action="/entityParentDeleteGoSubclassView-stac_surImplant" name="Удалить"
                              roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Delete"/>
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>

    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
          
        </script>
       
    </tiles:put>
</tiles:insert>

