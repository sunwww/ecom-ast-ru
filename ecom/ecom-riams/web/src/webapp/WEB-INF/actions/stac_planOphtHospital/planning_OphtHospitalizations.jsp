<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <%
        String typeStatus = ActionUtil.updateParameter("ClaimList","typeStatus","1", request) ;
    %>
    <tiles:put name="style" type="string">
        <style type="text/css">

            ul#listSpecialists li,ul#listDates li,ul#listTimes li,ul#listPatients li
            ,ul#listFunctions li {
                list-style:none outside none;
            }
            li#liTime:HOVER,ul#listPatients li:HOVER,ul#listSpecialists li:HOVER
            ,ul#listFunctions li:HOVER,.busyDay:HOVER,.selectedVisitDay:HOVER
            ,.selectedBusyDay:HOVER
            ,.visitDay:HOVER
            {
                cursor: pointer;

            }
            .preDirectRemoteUsername {
                color:#fff ;

            }
            .directRemoteUsername {
                color:#fff ;
            }
            .freeDay{
                background-color: #DDD;
                /*font-size: medium;*/
                font-weight: bolder;
                text-align: center;
            }
            .busyDay{
                background-color: #ff3333;
                /*font-size: medium;*/
                font-weight: bolder;
                text-align: center;
            }
            .selectedVisitDay {
                background-color: navy;
                /*font-size: medium;*/
                font-weight: bolder;
                text-align: center;
                color: white;
            }
            .selectedVisitDay:HOVER{
                background-color: #4D90FE;
                /*font-size: medium;*/
                color:black;
                font-weight: bolder;
                text-align: center;
            }
            .selectedBusyDay {
                background-color: pink;
                /*font-size: medium;*/
                font-weight: bolder;
                text-align: center;
                color: white;
            }
            .selectedBusyDay:HOVER{
                background-color: #4D90FE;
                /*font-size: medium;*/
                color:black;
                font-weight: bolder;
                text-align: center;
            }
            .visitDay {
                background-color: #0066cc;
                color:white;
                /*font-size: medium;*/
                font-weight: bolder;
                text-align: center;
            }
            .visitDay:HOVER{
                background-color: #4D90FE;
                /*font-size: medium;*/
                font-weight: bolder;
                text-align: center;
                color:black;
            }
            .listDates {
                border: 2px;
                padding: 2px;
                margin: 2px;
                border: 2px black outset;
            }
            .listDates td,.listDates th {
                border: 2px black outset;
            }
            .listDates th {
                /*font-size: medium;*/
                font-weight: bolder;
                text-align: center;
                background-color: #BBCCFF;

            }
            .spanNavigMonth {
                /*font-size: medium;*/
                font-weight: bolder;
            }
            .spanNavigMonth a{
                /*font-size: medium;*/
                font-weight: bolder;
            }
            .spanNavigMonth a:HOVER{
                /*font-size: medium;*/
                font-weight: bolder;
                background-color: yellow;
            }

            ul li.title {
                font-weight: bolder;
            }
            ul.listTimes {
                margin-left: 0;
                padding-left: 0;
            }
            ul.listTimes li ul.ulTime {
                margin-left: 0;
                padding: 0;

                display: list-item;
                list-style: none;
                /*font-size: medium;*/
            }
            ul.listTimes li ul.ulTime li#liTimeDirect{
                margin-left:  0;
                padding-left:0;
                list-style: none;
                /*font-size: medium;*/
                background-color: #ff3333;
                font-weight: bold;
            }
            ul.listTimes li ul.ulTime li#liTimeBusyForRemoteUser{
                margin-left:  0;
                padding-left:0;
                list-style: none;
                /*font-size: medium;*/
                background-color: red;
                font-weight: bold;
            }
            ul#listDirects li.liTimeDirect {
                background-color: #ff3333;
                border-top: 1px solid;

            }
            ul#listDirects li.liTimePre {
                background-color: #33cc66;
                border-top: 1px solid;

            }
            li.liList{
                padding: 0;
            }
            ul.listTimes li ul.ulTime li#liTimePre{
                margin-left:  0;
                padding-left: 0;
                list-style: none;
                /*font-size: medium;*/
                background-color: #33cc66;
                font-weight: bold;
            }
            ul.listTimes {
                margin: 0;
                padding: 0;
                display: inline;
            }

            ul.listTimes li {
                padding-bottom: 0px ;
                padding-top: 0px ;
                padding-left: 0px ;
                padding-right: 0px ;
                list-style: none;
                display: inline;
            }

            ul.listTimes li.first {
                margin-left: 0;
                border-left: none;
                list-style: none;
                display: inline;
            }
            /*
            input.radio {
            display: none ;}*/
        </style>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/View">
            <msh:form action="/stac_planning_OphtHospitalizations.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
                <msh:panel>
                    <msh:hidden property="department" />
                    <msh:row>
                        <msh:separator label="ПЕРИОД" colSpan="4"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="dateBegin" label="Дата начала"/>
                        <msh:textField property="dateEnd" label="Окончания"/>
                    </msh:row>
                    <msh:row>
                        <td class="label" title="Статус заявки (typeStatus)" colspan="1"><label for="typeStatusName" id="typeStatusLabel">Статус:</label></td>
                        <td><label>
                            <input type="radio" name="typeStatus" value="1">  Все</label>
                        </td>
                        <td colspan="2"><label>
                            <input type="radio" name="typeStatus" value="2">  Не госпитализированные</label>
                        </td>
                        <td colspan="2"><label>
                            <input type="radio" name="typeStatus" value="3">  Только госпитализированные</label>
                        </td>
                    </msh:row>
                    <msh:row>
                        <td colspan="4">
                            <input type="submit" onclick="find()" value="Поиск"/>
                            <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Opht/Create">
                                <input type="button" onclick="newP()" value="Добавить" >
                            </msh:ifInRole>
                        </td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            <div id="dPicker"></div>
            <div id="dPickerData"></div>
            <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>

        </msh:ifInRole>
        <%

            String date = request.getParameter("dateBegin") ;
            String dateEnd = request.getParameter("dateEnd") ;

            if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
            request.setAttribute("dateBegin", date) ;
            request.setAttribute("dateEnd", dateEnd) ;

            String statusSql ="";
            if ("2".equals(typeStatus)) {
                statusSql = " and wct.medcase_id is null"; //не госпитализированные
            } else if ("3".equals(typeStatus)) {
                statusSql = " and wct.medcase_id is not null";
            }
            request.setAttribute("statusSql",statusSql);

        %>
        <msh:section title="Список направлений на введение ингибиторов ангиогенеза">
            <ecom:webQuery name="stac_planOphtHospital"
                           nativeSql=" select wct.id as id,pat.lastname||' '||pat.firstname||' '||pat.middlename||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as fio
,wct.phone as phone
,to_char(wct.dateokt,'dd.mm.yyyy') as dateokt
,e.name as eye
,wct.comment as cmnt
,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as creator
,to_char(wct.createdate,'dd.mm.yyyy')||' '||to_char(wct.createTime,'HH24:MI') as dt
,list(case when wct.medcase_id is not null then 'background-color:green' when wf.isAdministrator='1' then 'background-color:#add8e6' else '' end) as f9_styleRow
 ,wct.id as f10_changeDtsFlds
 ,wct.dateFrom as f11_dateh
from WorkCalendarHospitalBed wct
left join patient pat on wct.patient_id=pat.id
left join voceye e on e.id=wct.eye_id
left join workfunction wf on wf.id=wct.workfunction_id
left join MedCase mc on mc.id=wct.medcase_id
left join vocworkFunction vwf on vwf.id=wf.workFunction_id
left join worker w on w.id = wf.worker_id
left join patient wp on wp.id=w.person_id

where wct.createDate between to_date('${dateBegin}','dd.mm.yyyy')
	 and to_date('${dateEnd}','dd.mm.yyyy')
  ${statusSql}
  and e.id is not null
group by wct.id,wct.createDate,pat.id
,wct.createDate,mc.dateStart,mc.dateFinish,wct.phone,e.name
,vwf.name,wp.lastname,wp.firstname,wp.middlename
order by wct.createDate,pat.lastname,pat.firstname,pat.middlename
    "
            />
            <msh:table printToExcelButton="Сохранить в excel" name="stac_planOphtHospital" action="entityParentView-stac_planHospital.do"
                       idField="1" styleRow="9" >
                <msh:tableColumn columnName="#" property="sn"/>
                <msh:tableColumn columnName="ФИО" property="2" />
                <msh:tableColumn columnName="Телефон" property="3" />
                <msh:tableColumn columnName="Дата ОКТ" property="4" />
                <msh:tableColumn columnName="Глаз" property="5" />
                <msh:tableColumn columnName="Замечания" property="6" />
                <msh:tableColumn columnName="Создал" property="7" />
                <msh:tableColumn columnName="Дата и время создания" property="8" />
                <msh:tableColumn columnName="Дата предв. госп" property="11" />
                <msh:tableButton property="10" buttonShortName="Уст. дату" buttonFunction="setDate" hideIfEmpty="true" />

            </msh:table>
        </msh:section>
        <div id="divViewBed">
        </div>

    </tiles:put>
    <tiles:put name="side" type="string">
        <tags:stac_journal currentAction="stac_planning_Hospitalizations"/>
    </tiles:put>

    <tiles:put name="title" type="string">
    </tiles:put>
    <tiles:put type="string" name="javascript">

        <script type="text/javascript">
            checkFieldUpdate('typeStatus','${param.typeStatus}','1');

            showPreHospCalendar(new Date().getMonth()+1, new Date().getFullYear());
            function showPreHospCalendar(month,year) {
                HospitalMedCaseService.getOpthalmicDep({
                    callback: function(dep) {
                        if (dep!='')  {
                            $('department').value=dep;
                            HospitalMedCaseService.getPreHospCalendar(year,month,dep,true,{
                                callback: function(html) {
                                    jQuery('#dPicker').html(html);
                                }
                            });
                        }
                        else alert('Не настроено офтальмологическое отделение!')
                    }
                });

            }

            function showPreHospByDate(el,date) {
                jQuery('.selectedVisitDay').each(function(i,el){
                    jQuery(el).attr('class','visitDay');
                });
                jQuery(el).attr('class','selectedVisitDay');

                jQuery("#dPickerData").load("js-mis_hospitalBed-listByDateOpht.do?startDate="+date+"&finishDate="+date+"&department="+(+$('department').value)+" .sectionContent");
            }

            function checkFieldUpdate(aField,aValue,aDefaultValue) {
                if (jQuery(":radio[name="+aField+"][value='"+aValue+"']").val()!=undefined) {
                    jQuery(":radio[name="+aField+"][value='"+aValue+"']").prop('checked',true);
                } else {
                    jQuery(":radio[name="+aField+"][value='"+aDefaultValue+"']").prop('checked',true);
                }
            }
            function setDate(id) {
                var from = prompt('Введите дату dd.mm.yyyy', getCurrentDate());
                if (from != null && checkDate(from)) {
                    from = from.split(".");
                    var hDate = new Date(from[2], from[1] - 1, from[0]);
                    if (hDate < new Date().setHours(0, 0, 0, 0))
                        showToastMessage('Предварительная дата госпитализации не может быть меньше текущей!', null, true, true, 3000);
                    else {
                        HospitalMedCaseService.setPreHospOphtDate(id, from[0] + '.' + from[1] + '.' + from[2], {
                            callback: function () {
                                showToastMessage('Установлено.', null, true, false, 2000);
                                document.forms[0].submit();
                            }
                        });
                    }
                }
                else if (!from==null)
                    showToastMessage(from+' - некорректная дата! Формат должен быть dd.mm.yyyy', null, true, true, 3000);
            }

            function find() {

            }
            function newP() {
                window.location = 'entityPrepareCreate-stac_planOphtHospital.do?'+"&tmp="+Math.random() ;
            }
            function editPlanning(aWp) {
                window.location = 'entityEdit-stac_planOphtHospital.do?id='+aWp+"&tmp="+Math.random() ;
            }
            function viewSlo(aSlo) {
                getDefinition('entityShortView-stac_slo.do?id='+aSlo+"&tmp="+Math.random()) ;
            }
        </script>
    </tiles:put>
</tiles:insert>