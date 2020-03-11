<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-e2_vocFondV009.do" defaultField="code">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
               <msh:row>
                   <msh:textField property="startDate" size="10"/>
                   <msh:textField property="finishDate" size="10"/>
                </msh:row>
                <msh:row>
                   <msh:textField property="code" size="100"/>
                </msh:row><msh:row>
                   <msh:textField property="name" size="100"/>
            </msh:row><msh:row>
                <msh:textField property="usl" size="100"/>
            </msh:row><msh:row>
                <msh:textField property="extDispCodes" size="100"/>
                </msh:row>


                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
        <msh:ifFormTypeIsView formName="e2_vocFondV009Form">
            <ecom:webQuery name="linkList" nativeSql="select link.id
                ,link.bedsubtype||''||coalesce(vbst.name,'') as vbst
                ,link.MedosReasonDischarge||' '||coalesce(vdr.name,'') as vdr
                ,link.MedosHospResult||' ' ||coalesce(vhr.name,'') as vhr
                ,link.MedosHospoutcome||' '||coalesce(vho.name,'') as vho
                from E2FondResultLink link
                left join vocBedSubType vbst on vbst.code=link.bedsubtype
                left join VocHospitalizationResult vhr on vhr.code=link.MedosHospResult
                left join vochospitalizationoutcome vho on vho.code=link.MedosHospoutcome
                left join vocreasondischarge vdr on vdr.code=link.MedosReasonDischarge
                where link.result_id=${param.id}"/>
            <msh:table idField="1" name="linkList" action="entityParentEdit-e2_v009Link.do" noDataMessage="Нет соответствий по справочнику">
                <msh:tableColumn columnName="Тип коек" property="2"/>
                <msh:tableColumn columnName="Причина выписки" property="3"/>
                <msh:tableColumn columnName="Результат госпитализации" property="4"/>
                <msh:tableColumn columnName="Причина выписки" property="5"/>
            </msh:table>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_vocFondV009Form" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_vocFondV009Form">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">
            function attachNewBedType () {

                    }
            </script>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_vocFondV009Form">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_vocFondV009" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-e2_v009Link" name="Создать привязку" roles="/Policy/E2/Edit" />
            </msh:sideMenu>
            <tags:expertvoc_menu currentAction="main"/>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

