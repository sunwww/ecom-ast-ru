<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <tiles:put name="side" type="string">
        <tags:vocObservRes name="vocObservRes"/>
        <msh:sideMenu title="Лист наблюдения">
            <msh:sideLink key="ALT+1" action="/javascript:showvocObservResJs()" name="Закрыть ЛН" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet/Edit" />
        </msh:sideMenu>
        <msh:sideMenu title="Добавить">
             <msh:sideLink key="ALT+2" action="/entityParentPrepareCreate-edkcProtocol.do?id=${param.id}&type=edkc_1" name="Протокол консультации" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet/Create" />
            <msh:sideLink key="ALT+3" action="/entityParentPrepareCreate-edkcProtocol.do?id=${param.id}&type=edkc_ev" name="Протокол ежесуточного наблюдения" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet/Create" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoView-edkcObsSheet.do" defaultField="startDate"
                  fileTransferSupports="true">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="patient" />
            <msh:panel>
                <msh:row>
                    <msh:textField viewOnlyField="true" property="startDate" label="Дата открытия" />
                    <msh:textField viewOnlyField="true" property="createUsername" label="Логин" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="specialistStart" label="Открыл" size="150" vocName="workFunction" fieldColSpan="3" viewOnlyField="true" horizontalFill="true"  />
                </msh:row>
                <msh:row>
                    <msh:textField viewOnlyField="true" property="finishDate" label="Дата закрытия" />
                    <msh:textField viewOnlyField="true" property="editUsername" label="Логин" />
                    <msh:autoComplete property="observResult" label="Результат наблюдения" size="100" vocName="vocObservationResult" fieldColSpan="3" viewOnlyField="true"  />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="specialistFin" label="Закрыл" size="150" vocName="workFunction" fieldColSpan="3" viewOnlyField="true" horizontalFill="true"  />
                </msh:row>
            </msh:panel>
        </msh:form>
        <msh:ifFormTypeIsView formName="edkcObsSheetForm">
            <msh:section title="Список протоколов ЕДКЦ">
                <ecom:webQuery name="protocols"  nativeSql="
          select d.id,  to_char(d.dateRegistration,'dd.mm.yyyy') ||' '|| cast(d.timeRegistration as varchar(5)) as dtimeRegistration, d.record
         , vwf.name||' '||pw.lastname||' '||pw.firstname||' '||pw.middlename as doc
         ,vtp.name as type
          from Diary as d
          left join WorkFunction wf on wf.id=d.specialist_id
          left join Worker w on w.id=wf.worker_id
          left join Patient pw on pw.id=w.person_id
          left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
          left join VocTypeProtocol vtp on vtp.id=d.type_id
                    where d.DTYPE='Protocol' and d.obssheet_id='${param.id}'
                    order by d.dateRegistration,d.timeRegistration"/>

                <msh:table hideTitle="false" selection="multiply" idField="1" name="protocols" action="entityParentView-edkcProtocol.do"
                           noDataMessage="Нет протоколов">
                    <msh:tableNotEmpty>
                        <tr>
                            <th colspan='8'>
                                <msh:toolbar>
                                    <a href='javascript:printProtocols("protocols")'>Печать протоколов</a>
                                </msh:toolbar>
                            </th>
                        </tr>
                    </msh:tableNotEmpty>
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Дата и время" property="2" width="10"/>
                    <msh:tableColumn columnName="Протокол" property="3" cssClass="preCell" width="70"/>
                    <msh:tableButton buttonFunction="createNew" property="1" cssClass="preCell" buttonName="Протокол на основе дневника" buttonShortName="Протокол на основе дневника"/>
                    <msh:tableColumn columnName="Специалист" property="4" width="10"/>
                    <msh:tableColumn columnName="Тип" property="5" width="10"/>
                </msh:table>
            </msh:section>
        </msh:ifFormTypeIsView>
        <tags:stac_selectPrinter  name="Select" roles="/Policy/Config/SelectPrinter" />
    </tiles:put>
    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Expert2" beginForm="edkcObsSheetForm"/>
    </tiles:put>
    <tiles:put name='javascript' type='string'>
        <script type="text/javascript" src="./dwr/interface/PatientService.js">/**/</script>
        <script type="text/javascript">
            function showvocObservResJs() {
                if ($('patient').value!='')
                    showvocObservRes($('patient').value,window.location.href);
                else
                    showToastMessage('Необходимо выбрать пациента!',null,true);
            }
            function printProtocols(aFile) {
                var ids = theTableArrow.getInsertedIdsAsParams("id","protocols") ;
                if(ids) {
                    var p = 'print-'+aFile+'.do?multy=1&m=printProtocols&s=HospitalPrintService1&'+ids ;
                    initSelectPrinter(p,0);


                } else {
                    alert("Нет выделенных протоколов");
                }

            }

            //Функция позовляет создать днвеник на основе выбранного =
            function createNew(id) {
                window.location.href = 'entityParentPrepareCreate-edkcProtocol.do?id='+$('id').value+'&type=edkc_ev&basis='+id;
            }
        </script>
    </tiles:put>
</tiles:insert>