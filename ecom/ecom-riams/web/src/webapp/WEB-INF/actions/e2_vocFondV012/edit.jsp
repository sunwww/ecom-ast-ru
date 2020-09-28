<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-e2_vocFondV012.do" defaultField="code">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
               <msh:row>
                   <msh:textField property="code" size="100"/>
                </msh:row><msh:row>
                   <msh:textField property="name" size="100"/>
            </msh:row><msh:row>
                   <msh:textField property="usl" size="100"/>
            </msh:row><msh:row>
                </msh:row>


                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
        <msh:ifFormTypeIsView formName="e2_vocFondV012Form">
            <ecom:webQuery name="linkList" nativeSql="select link.id, link.MedosHospResult||' '||coalesce(vp.name,'') as name, link.bedSubType
            from E2FondIshodLink link
            left join VocHospitalizationResult vp on vp.code=link.MedosHospResult
            where link.ishod_id=${param.id}"/>
            <msh:table idField="1" name="linkList" action="entityParentEdit-e2_v012Link.do" noDataMessage="Нет услуг по случаю">
                <msh:tableColumn columnName="Результат" property="2"/>
                <msh:tableColumn columnName="Тип коек" property="3"/>
            </msh:table>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_vocFondV012Form" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_vocFondV012Form">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">
            function attachNewBedType () {

                    }
            </script>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_vocFondV012Form">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_vocFondV012" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-e2_v012Link" name="Создать привязку" roles="/Policy/E2/Edit" />
            </msh:sideMenu>
            <tags:expertvoc_menu currentAction="main"/>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

