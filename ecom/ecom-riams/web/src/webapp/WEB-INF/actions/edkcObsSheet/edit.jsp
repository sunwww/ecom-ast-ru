<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>

<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <tiles:put name="side" type="string">
        <tags:vocObservRes name="vocObservRes"/>
        <msh:sideMenu guid="0d13c843-c26a-4ae2-ae97-d61b44618bae" title="Лист наблюдения">
            <msh:sideLink key="ALT+1" action="/javascript:showvocObservResJs()" name="Закрыть ЛН" guid="dc51a550-1158-41b8-89a4-bf3a90ffeedb" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet/Edit" />
        </msh:sideMenu>
        <msh:sideMenu guid="0d13c843-c26a-4ae2-ae97-d61b44618bae" title="Добавить">
             <msh:sideLink key="ALT+2" action="/entityParentPrepareCreate-edkcProtocol.do?id=${param.id}&type=edkc_1" name="Протокол консультации" guid="dc51a550-1158-41b8-89a4-bf3a90ffeedb" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet/Create" />
            <msh:sideLink key="ALT+3" action="/entityParentPrepareCreate-edkcProtocol.do?id=${param.id}&type=edkc_ev" name="Протокол ежесуточного наблюдения" guid="dc51a550-1158-41b8-89a4-bf3a90ffeedb" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet/Create" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name='body' type='string'>
        <msh:form action="entityParentSaveGoView-edkcObsSheet.do" defaultField="startDate" guid="b55hb-b971-441e-9a90-5155c07"
                  fileTransferSupports="true">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:hidden property="patient" />
            <msh:panel>
                <msh:row>
                    <msh:textField viewOnlyField="true" property="startDate" label="Дата открытия" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
                    <msh:textField viewOnlyField="true" property="createUsername" label="Логин" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="specialistStart" label="Открыл" size="150" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" viewOnlyField="true" horizontalFill="true"  />
                </msh:row>
                <msh:row>
                    <msh:textField viewOnlyField="true" property="finishDate" label="Дата закрытия" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
                    <msh:textField viewOnlyField="true" property="editUsername" label="Логин" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
                    <msh:autoComplete property="observResult" label="Результат наблюдения" size="100" vocName="vocObservationResult" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" viewOnlyField="true"  />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="specialistFin" label="Закрыл" size="150" vocName="workFunction" guid="c53e6f53-cc1b-44ec-967b-dc6ef09134fc" fieldColSpan="3" viewOnlyField="true" horizontalFill="true"  />
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

                <msh:table hideTitle="false" selection="multiply" idField="1" name="protocols" action="entityParentView-edkcProtocol.do" guid="d0267-9aec-4ee0-b20a-4f26b37"
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
                    <msh:tableColumn columnName="Специалист" property="4" width="10"/>
                    <msh:tableColumn columnName="Тип" property="5" width="10"/>
                </msh:table>
            </msh:section>
        </msh:ifFormTypeIsView>
        <tags:stac_selectPrinter  name="Select" roles="/Policy/Config/SelectPrinter" />
    </tiles:put>
    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="edkcObsSheetForm"/>
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
        </script>
    </tiles:put>
</tiles:insert>