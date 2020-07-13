<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8"  %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <%
        String typeResult = ActionUtil.updateParameter("CovidAnalysisImportAction","typeResult","4", request) ;
    %>
    <tiles:put name="title" type="string">
        <msh:title mainMenu="StacJournal" title="Анализы COVID"/>
    </tiles:put>
    <tiles:put name="body" type="string">
        <% if (request.getParameter("short")==null) { %>
        <msh:form action="/covidImportJournal_journal.do" defaultField="beginDate">
            <msh:panel>
                <msh:row>
                    <msh:textField property="filterAdd" label="Фамилия" />
                </msh:row>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:row>
                        <td>Результат анализа</td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeResult" value="1">  Положительные
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeResult" value="2">  Отрицательные
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeResult" value="3">  Результат не введён
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeResult" value="4">  Все
                        </td>
                    </msh:row>
            <msh:row>
                        <msh:hidden property="dateBegin" />
                        <msh:hidden property="dateEnd" />
                    </msh:row>
                </msh:row>

                <msh:submitCancelButtonsRow colSpan="4" notDisplayCancel="true" labelSave="Найти" labelSaving="Поиск..."/>
                <msh:ifInRole roles="/Policy/Mis/MedCase/Covid19/Export">
                    <msh:row>
                        <td><input type="button" onclick="deleteAllInmportedAnalyses();" value="Очистить данные по импорту в БД"></td>
                    </msh:row>
                </msh:ifInRole>
            </msh:panel>
        </msh:form>
        <form action="covidImportJournal_import.do" method="post" enctype="multipart/form-data" >
            <msh:panel>
            <msh:row>
                <td>
                    <input type="file" name="file" id="file" value="Загрузить CSV с результатами">
                </td>
                <td><input type="submit" value="Загрузить CSV с результатами"></td>
            </msh:row>
                <msh:ifInRole roles="/Policy/Mis/Config/Elmed">
                    <msh:row><td><input type="button" value="Показать магию" onclick="jQuery('#hiddenDiv').style.display='block';"></td></msh:row>
                </msh:ifInRole>
            </msh:panel>
            <div id="hiddenDiv" style="display: none">
                <msh:panel>
                    <msh:row>
                        <msh:autoComplete vocName="vocWorkPlaceType" property="workPlaceId" label="Место обслуживания" horizontalFill="true" />
                        <msh:autoComplete vocName="workFunction" property="workFunctionId" label="Специалист"  fieldColSpan="4" size="100"/>
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete vocName="vocReason" property="visitReasonId" label="Цель посещения" horizontalFill="true" />
                        <msh:autoComplete vocName="vocVisitResult" property="visitResultId" label="Результат обращения" horizontalFill="true" />
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete vocName="vocIdc10" property="mkbId" label="Диагноз" horizontalFill="true" fieldColSpan="3"/>
                        <msh:autoComplete vocName="vocIllnesPrimary" property="primaryId" label="Характер заболевания" horizontalFill="true" fieldColSpan="3"/>
                    </msh:row>
                </msh:panel>
            </div>
        </form>
        <% }
        String id = request.getParameter("id");
        if (id==null) { //все результаты
            String filterSql ;
            if (typeResult!=null && !"4".equals(typeResult)) {
                filterSql = " where upper(l.resultText)='" +("3".equals(typeResult) ? "РЕЗУЛЬТАТ НЕ ВНЕСЕН"
                        : "2".equals(typeResult) ? "ОТРИЦАТЕЛЬНЫЙ" : "ПОЛОЖИТЕЛЬНЫЙ")+"'";
            } else filterSql="";
            request.setAttribute("filterSql", filterSql);

            String lastnameSql = request.getParameter("filterAdd")!=null && !request.getParameter("filterAdd").equals("")?
                    " where upper( l.lastname) like upper('" + request.getParameter("filterAdd") + "')"
                    : "";
            request.setAttribute("lastnameSql", lastnameSql);
        %>
        <ecom:webQuery name="labList" nativeSql="
            select l.uniqNumber, l.lastname||' '||l.firstname ||' '|| l.middlename||' '||to_char(l.birthday,'dd.MM.yyyy')
            ,l.dateDirect , l.resultText, l.dateResult
            , coalesce(l.protocolNumber,'')||' от '||to_char(l.dateProtocol,'dd.MM.yyyy') as protInfo
            ,l.laboratory
            from ExternalCovidAnalysis l
            ${filterSql} ${lastnameSql}
            order by l.createDate desc , l.lastname, l.firstname, l.middlename, l.createTime desc
        " />
        <msh:table name="labList" action="covidImportJournal_journal.do?short=Short" idField="1" cellFunction="true">
            <msh:tableColumn columnName="ФИО" property="2" addParam="&lastname=1" />
            <msh:tableColumn columnName="Дата направления" property="3" />
            <msh:tableColumn columnName="Результат" property="4" />
            <msh:tableColumn columnName="Дата результата" property="5" />
            <msh:tableColumn columnName="Протокол" property="6" />
            <msh:tableColumn columnName="Лаборатория" property="7" />
        </msh:table>
        <%
            } else {
                String ids = request.getParameter("lastname")!=null ? " like '"+id.split("#")[0]+"#%'" : "='"+id+"'";
                request.setAttribute("ids",ids);
        %>
        <ecom:webQuery name="labList" nativeSql="
            select l.uniqNumber, l.lastname||' '||l.firstname ||' '|| l.middlename||' '|| to_char(l.birthday,'dd.MM.yyyy')
            ,l.dateDirect , l.resultText, l.dateResult
            , coalesce(l.protocolNumber,'')||' от '||to_char(l.dateProtocol,'dd.MM.yyyy') as protInfo
            ,l.laboratory
            from ExternalCovidAnalysis l
            where l.uniqNumber ${ids} order by l.dateResult desc

        " />
        <msh:table name="labList" action="covidImportJournal.do?short=Short" idField="1">
            <msh:tableColumn columnName="ФИО" property="2" />
            <msh:tableColumn columnName="Дата направления" property="3" />
            <msh:tableColumn columnName="Результат" property="4" />
            <msh:tableColumn columnName="Дата результата" property="5" />
            <msh:tableColumn columnName="Протокол" property="6" />
            <msh:tableColumn columnName="Лаборатория" property="7" />
        </msh:table>

        <%
         }
            String date = request.getParameter("dateBegin") ;
            String dateEnd = request.getParameter("dateEnd") ;

            if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
        %>
        <script type='text/javascript' src='./dwr/interface/PatientService.js'></script>
        <script type='text/javascript'>

            checkFieldUpdate('typeResult','${typeResult}',4) ;
            function checkFieldUpdate(aField,aValue,aDefault) {
                console.log('var chk =  document.forms[0].'+aField+"<>"+aValue+"<>"+aDefault) ;
                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)>max) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }

            function generTickets() {
                //only for ***52
                TicketService.generateTalons (val,$('dateStart').value, $('dateFinish').value, $('serviceStream').value
                    ,$('workPlaceType').value, $('visitReason').value,$('visitResult').value
                    ,$('diagnosisList').value, $('concludingActuity').value, $('recordCount').value
                    ,$('ageFrom').value,$('ageTo').value, $('sex').value,$('lpu').value,$('patientIds').value, isProfOsmotr,{
                        callback: function (a) {
                            alert (a);
                        }
                    });
            }

            //очистить данные по импорту в бд
            function deleteAllInmportedAnalyses() {
                PatientService.deleteAllInmpirtedAnalyses( {callback: function(msg) {
                        showToastMessage(msg, null, true);
                        setTimeout(() => {  window.location.reload(); }, 1500);
                }});
            }

        </script>
    </tiles:put>
</tiles:insert>