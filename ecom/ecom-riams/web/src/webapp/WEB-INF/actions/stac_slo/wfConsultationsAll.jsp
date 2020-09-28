<%@ page import="ru.ecom.web.login.LoginInfo" %>
<%@ page import="ru.nuzmsh.web.tags.helper.RolesHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Консультации в стационаре</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/wfConsultationsAll.do" defaultField="dateBegin" disableFormDataConfirm="true" method="POST">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="type" fieldColSpan="16" horizontalFill="true" label="Причина отмены" vocName="vocWfConsultationCancelReason"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="refreshType" fieldColSpan="16" horizontalFill="true" label="Направлен к" vocName="workFunctionCons"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td class="label" title="Показать  (typeGroup)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Показать:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup" value="1" checked> все
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup" value="2"> выполненные
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup" value="3"> НЕ выполненные
                    </td>
                </msh:row>
                <msh:row>
                    <td class="label" title="Показать  (typeGroup3)" colspan="1"><label for="typeGroup3Name" id="typeGroup3Label"></label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup3" value="1" checked> все
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup3" value="2"> cito
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup3" value="3"> plan
                    </td>
                </msh:row>
                <msh:row>
                    <td class="label" title="Показать  (typeGroup4)" colspan="1"><label for="typeGroup4Name" id="typeGroup4Label"></label></td>
                    <td id="td1" onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup4" value="1" checked> все
                    </td>
                    <td id="td2" onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup4" value="2"> за сегодня
                    </td>
                    <td id="td3" onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup4" value="3"> за вчера
                    </td>
                    <td id="td4" onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup4" value="4"> за сегодня и вчера
                    </td>
                    <td id="td5" onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup4" value="5"> ранее
                    </td>
                </msh:row>
                <msh:row>
                <msh:row>
                    <td class="label" title="Показать  (typeGroup5)" colspan="1"><label for="typeGroup5Name" id="typeGroup5Label"></label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup5" value="1" checked> все
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup5" value="2"> актуальные
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup5" value="3"> отменённые
                    </td>
                </msh:row>
                    <td colspan="3">
                        <input type="button" onclick="this.disabled=true;find();" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            StringBuilder typeSql=new StringBuilder();
            String type1 = (String)request.getParameter("typeGroup");
            if (type1==null || type1.equals("")) type1="3";
            if (type1!=null && !type1.equals("")) {
                if (type1.equals("2")) typeSql.append(" and scg.diary_id is not null");
                if (type1.equals("3")) typeSql.append(" and scg.diary_id is null");
            }
            String type3 = (String)request.getParameter("typeGroup3");
            if (type3==null || type3.equals("")) type3="1";
            if (type3!=null && !type3.equals("")) {
                if (type3.equals("2")) typeSql.append(" and vtype.code='cito'");
                if (type3.equals("3")) typeSql.append(" and vtype.code='plan'");
            }
            String type4 = (String)request.getParameter("typeGroup4");
            if (type4==null || type4.equals("")) type4="1";
            if (type4!=null && !type4.equals("")) {
                if (type4.equals("2")) typeSql.append(" and scg.createdate=current_date");
                if (type4.equals("3")) typeSql.append(" and scg.createdate=current_date-1");
                if (type4.equals("4")) typeSql.append(" and (scg.createdate=current_date or scg.createdate=current_date-1)");
                if (type4.equals("5")) typeSql.append(" and scg.createdate<current_date-1");
            }
            String type5 = (String)request.getParameter("typeGroup5");
            if (type5==null || type5.equals("")) type5="2";
            if (type5!=null && !type5.equals("")) {
                if (type5.equals("2")) typeSql.append(" and scg.canceldate is null");
                if (type5.equals("3")) typeSql.append(" and scg.canceldate is not null");
            }
            String department = request.getParameter("department") ;
            if (department!=null && !department.equals(""))  typeSql.append(" and dep.id="+department);
            String cancelreason = request.getParameter("type") ;
            if (cancelreason!=null && !cancelreason.equals(""))  typeSql.append(" and vcr.id='"+cancelreason+"'");
            String dateBegin = request.getParameter("dateBegin") ;
            if (dateBegin!=null && !dateBegin.equals("")) {
                String dateEnd = request.getParameter("dateEnd") ;
                if (dateEnd==null || dateEnd.equals("")) dateEnd=dateBegin;
                if (dateEnd!=null && !dateEnd.equals("")) {
                    typeSql.append(" and scg.createdate between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('" + dateEnd+"','dd.mm.yyyy') ");
                }
            }
            String prCab = request.getParameter("refreshType") ;
            if (prCab!=null && !prCab.equals(""))  typeSql.append(" and scg.prescriptcabinet_id="+prCab);
            request.setAttribute("typeSql",typeSql.toString());
            request.setAttribute("typeGroup",type1);
            request.setAttribute("typeGroup3",type3);
            request.setAttribute("typeGroup4",type4);
            request.setAttribute("typeGroup5",type5);
            request.setAttribute("department",department);
            if (!RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/ConsultJournal",request))
                request.setAttribute("wfLoginSql"," and scg.dtype='WfConsultation'" +
                        " and vwf.id=ANY(select vwf.id  from WorkFunction wf" +
                        " left join Worker w on w.id=wf.worker_id" +
                        " left join Worker sw on sw.person_id=w.person_id" +
                        " left join WorkFunction swf on swf.worker_id=sw.id" +
                        " left join vocworkfunction vwf on vwf.id=wf.workfunction_id" +
                        " left join SecUser su on su.id=swf.secUser_id" +
                        " where su.login='"+LoginInfo.find(request.getSession(true)).getUsername()+
                        "' and wf.group_id=scg.prescriptcabinet_id)");
            else
                request.setAttribute("wfLoginSql","");
        %>
            <msh:section>
                <msh:sectionTitle>Зелёные - выполненные, красные - не выполненные, серые - отменённые
                    <ecom:webQuery isReportBase="false" name="totalName" nameFldSql="totalName_sql" nativeSql="
select case when sls.id is not null then sls.id else slo.id end as slsid,vtype.code||' '||vtype.name as f00,wf.groupname as f01,
pat.lastname||' '||pat.firstname||' '||pat.middlename||' '||to_char(pat.birthday,'dd.mm.yyyy') as fpat,
dep.name||' '||scg.createusername as f1,to_char(scg.createdate,'dd.mm.yyyy')||' '||to_char(scg.createtime,'HH24:MI') as f2,
     vwf2.name||' '||wp2.lastname||' '||wp2.firstname||' '||wp2.middlename as f7
     ,to_char(scg.intakedate,'dd.mm.yyyy')||' '||to_char(scg.intaketime,'HH24:MI') as f8
, case
    when scg.diary_id is not null then 'background:#90EE90;color:black'
    else case when scg.canceldate is not null  then 'background:#C0C0C0;color:black'
    else case when scg.diary_id is null  then 'background:#CD5C5C;color:black'
    else '' end end end as f10style
    ,scg.cancelusername||' '||to_char(scg.canceldate,'dd.mm.yyyy')||' '||to_char(scg.canceltime,'HH24:MI')||': '||scg.cancelreasontext as cnsl
    ,scg.id as scgid
,case when scg.intakedate is not null then cast((scg.intakedate - scg.createdate) as int)
else cast((current_date - scg.createdate)as int) end as waitdays
from prescription scg
left join PrescriptionList pl on pl.id=scg.prescriptionList_id
left join workfunction wf on wf.id=scg.prescriptcabinet_id
left join vocworkFunction vwf on vwf.id=wf.workFunction_id
left join worker w on w.id = wf.worker_id
left join patient wp on wp.id=w.person_id
left join vocconsultingtype vtype on vtype.id=scg.vocconsultingtype_id
left join medcase slo on slo.id=pl.medcase_id
left join medcase sls on sls.id=slo.parent_id
left join patient pat on slo.patient_id=pat.id
left join workfunction wf2 on wf2.id=scg.intakespecial_id
left join vocworkFunction vwf2 on vwf2.id=wf2.workFunction_id
left join worker w2 on w2.id = wf2.worker_id
left join patient wp2 on wp2.id=w2.person_id
left join mislpu dep on dep.id=slo.department_id
left join VocWfConsultationCancelReason vcr on vcr.name=scg.cancelreasontext
where scg.dtype='WfConsultation' ${wfLoginSql} ${typeSql}
order by scg.createdate desc,dep.id
"/>
                    <form action="javascript:void(0)" method="post" target="_blank"></form>
                </msh:sectionTitle>
                <msh:sectionContent>
                    <msh:table printToExcelButton="Сохранить в excel" name="totalName" viewUrl="wfConsultationsAll.do" action="entityParentView-pres_wfConsultation.do" idField="11" styleRow="9">
                        <msh:tableColumn columnName="#" property="sn"/>
                       <msh:tableColumn columnName="Тип" property="2"/>
                        <msh:tableColumn columnName="Специалист" property="3"/>
                        <msh:tableColumn columnName="Пациент" property="4"/>
                        <msh:tableColumn columnName="Создал, отделение" property="5"/>
                        <msh:tableColumn columnName="Дата и время создания" property="6"/>
                        <msh:tableColumn columnName="Дней ожидания" property="12"/>
                        <msh:tableColumn columnName="Пользователь, который выполнил" property="7"/>
                        <msh:tableColumn columnName="Дата и время выполнения" property="8"/>
                        <msh:tableColumn columnName="Отмена" property="10"/>
                    </msh:table>
                </msh:sectionContent>
            </msh:section>
        <script type="text/javascript">
            checkFieldUpdate('typeGroup','${typeGroup}',3) ;
            checkFieldUpdate('typeGroup3','${typeGroup3}',1) ;
            checkFieldUpdate('typeGroup4','${typeGroup4}',1) ;
            checkFieldUpdate('typeGroup5','${typeGroup5}',2) ;
            function checkFieldUpdate(aField,aValue,aDefault) {
                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)==0 || (+aValue)>(+max)) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
            function find() {
                var frm = document.forms[0];
                frm.submit();
            }

            //если введён период, ты выбирать сегодня/вчера не нужно, по умолчанию - все
            function disableRbPeriod(disabled) {
                var btns = document.getElementsByName("typeGroup4");
                for (var i=0; i<btns.length; i++) {
                    if (disabled)  {
                        btns[i].setAttribute("disabled",true);
                        if (btns[i].value=='1') btns[i].checked='checked';
                    }
                    else btns[i].removeAttribute("disabled");
                }
                for (var i=1; i<=5; i++) {
                    if (disabled) document.getElementById('td'+i).onclick = function() {};
                    else document.getElementById('td'+i).onclick = function() {this.childNodes[1].checked='checked';};
                }
            }
            $('dateBegin').oninput = function() {disableRbPeriod($('dateBegin').value!='')};
            $('dateBegin').onfocus = function() {disableRbPeriod($('dateBegin').value!='')};
            disableRbPeriod($('dateBegin').value!='');
        </script>
    </tiles:put>
</tiles:insert>