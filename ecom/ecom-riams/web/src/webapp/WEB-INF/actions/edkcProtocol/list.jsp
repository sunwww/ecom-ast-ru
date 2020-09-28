<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" title="Протоколы ЕДКЦ" beginForm="edkcObsSheetForm" />
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:sideMenu title="Добавить">
            <msh:sideLink key="ALT+2" action="/entityParentPrepareCreate-edkcProtocol.do?id=${param.id}&type=edkc_1" name="Протокол консультации" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet/Create" />
            <msh:sideLink key="ALT+3" action="/entityParentPrepareCreate-edkcProtocol.do?id=${param.id}&type=edkc_ev" name="Протокол ежесуточного наблюдения" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet/Create" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:section title="Список протоколов ЕДКЦ">
            <ecom:webQuery name="protocols"  nativeSql="
      select d.id, to_char(d.dateRegistration,'dd.mm.yyyy') ||' '|| cast(d.timeRegistration as varchar(5)) as dtimeRegistration, d.record
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
                <msh:tableColumn columnName="Специалист" property="4" width="10"/>
                <msh:tableColumn columnName="Тип" property="5" width="10"/>
            </msh:table>
        </msh:section>
    <tags:stac_selectPrinter  name="Select" roles="/Policy/Config/SelectPrinter" />
    </tiles:put>
<tiles:put name="javascript" type="string">
    <script type="text/javascript">
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