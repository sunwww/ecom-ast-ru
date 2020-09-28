<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="body" type="string">
        <%  String shortForm = request.getParameter("short");
            String beginDate = request.getParameter("dateBegin");
            String typeView = ActionUtil.updateParameter("Report_nationality","typeView","3", request) ;
            if(shortForm==null || shortForm.equals(""))
            {%>
        <msh:form action="/smo_deniedHospitelByAttach.do" method="GET" defaultField="hello">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7"/>
                </msh:row>

                <msh:row>
                    Период
                    <msh:textField property="dateBegin" label="c"/>
                    <msh:textField property="dateEnd" label="по"/>
                </msh:row>

                <msh:row>
                <td class="label" title="Поиск по прикреплениям (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">По </label></td>
                <td onclick="this.childNodes[1].checked='checked';"><input type="radio" name="typeView" value="1">  ЛПУ прикрепления</td>
                <td onclick="this.childNodes[1].checked='checked';"><input type="radio" name="typeView" value="2">  рабочей функции
                </msh:row>

                <msh:row>
                    <msh:autoComplete vocName="vocWorkFunction" property="department" size="50" fieldColSpan="3" label="Рабочая функция"/>
                </msh:row>
                <msh:row>
                    <td>
                    <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%}
            if (beginDate!=null && !beginDate.equals("")) {
                String finishDate = request.getParameter("dateEnd");
                if (finishDate==null || finishDate.equals("")) {
                    finishDate=beginDate ;
                }
                request.setAttribute("dateStart", beginDate);
                request.setAttribute("dateFinish", finishDate);

                if(typeView.equals("1")){
                if(shortForm==null || shortForm.equals("")) {
        %>
            <ecom:webQuery isReportBase="false" name = "allByLpu" nameFldSql="listSQL"
                           nativeSql="select max(lpu.id) as lpuid,
                            max(case when p.patientfond_id is null then 'Прикрепление неизвестно' else lpu.name end) as lpuname
                            ,count(distinct m.id) as counts
                            from medcase m
                            left join patient p on p.id = m.patient_id
                            left join patientfond pf on pf.id = p.patientfond_id
                            left join mislpu lpu on lpu.codef = pf.lpuattached
                            where m.dtype = 'HospitalMedCase'
                            and m.deniedhospitalizating_id is not null
                            and m.datestart between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
                            group by pf.lpuattached
                            order by counts desc"/>

            <msh:table printToExcelButton="Сохранить в excel" name="allByLpu" cellFunction="true" action="smo_deniedHospitelByAttach.do?dateBegin=${dateStart}&dateEnd=${dateFinish}&short=Short" idField="1">
                <msh:tableColumn columnName="№" identificator="false" property="sn" />
                <msh:tableColumn columnName="ЛПУ прикрепления" property="2"/>
                <msh:tableColumn columnName="Отказов" property="3" isCalcAmount="true"/>
            </msh:table>

        <%}else {
            String lpuId = request.getParameter("id");
            if(lpuId!=null && !lpuId.equals(""))request.setAttribute("id", lpuId); %>
            <ecom:webQuery isReportBase="false" name = "allByLpu" nameFldSql="listSQL"
                           nativeSql="select
                            dep.id,dep.name as depname,
                            count(distinct m.id) as counts
                            from medcase m
                            left join patient p on p.id = m.patient_id
                            left join patientfond pf on pf.id = p.patientfond_id
                            left join mislpu lpu on lpu.codef = pf.lpuattached
                            left join diary d on d.medcase_id = m.id
                            left join workfunction wf on wf.id = d.specialist_id
                            left join vocworkfunction vwf on vwf.id = wf.workfunction_id
                            left join mislpu dep on dep.id = m.department_id
                            where
                            m.dtype = 'HospitalMedCase'
                            and m.deniedhospitalizating_id is not null
                            and m.datestart between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
                            and lpu.id = ${id}
                            group by depname,dep.id
                            order by counts desc"/>

            <msh:table printToExcelButton="Сохранить в Excel" name="allByLpu" cellFunction="true" action="smo_deniedHospitelByAttach.do?dateBegin=${dateStart}&dateEnd=${dateFinish}&short=Short&lpuId=${lpuId}" idField="1">
                <msh:tableColumn columnName="№" identificator="false" property="sn" />
                <msh:tableColumn columnName="Отделение" property="2"/>
                <msh:tableColumn columnName="Кол-во отказов" property="3" isCalcAmount="true"/>
            </msh:table>
        <%}
        }else{
            String dep = request.getParameter("department");
            String depSql = "";

            if (dep!=null&&!dep.equals("")&&!dep.equals("0")) {
                depSql+=" and vwf.id = '"+dep+"'";
                request.setAttribute("depSql",depSql);
            }
        %>


            <ecom:webQuery isReportBase="false" name = "allByLpu" nameFldSql="listSQL"
                           nativeSql="
                    select
                    lpu.name as lpuname,vwf.name,
                    count(ord.id) as count1,
                    count(skor.id) as count2,
                    count(plan.id) as count3
                    from medcase m
                    left join patient p on p.id = m.patient_id
                    left join patientfond pf on pf.id = p.patientfond_id
                    left join mislpu lpu on lpu.codef = pf.lpuattached
                    left join diary d on d.medcase_id = m.id
                    left join workfunction wf on wf.id = d.specialist_id
                    left join vocworkfunction vwf on vwf.id = wf.workfunction_id
                    left join omc_frm skor on skor.id = m.ordertype_id and skor.voc_code='К' and skor.id=3
                    left join omc_frm ord on ord.id = m.ordertype_id and ord.voc_code='О'
                    left join omc_frm plan on m.ordertype_id is null--plan.id = m.ordertype_id and plan.voc_code is null--plan.voc_code!='О' and plan.voc_code!='K'
                    where
                    m.dtype = 'HospitalMedCase'
                    and lpu.name is not null
                    and vwf.name is not null
                    and m.deniedhospitalizating_id is not null
                    and m.datestart between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
                    and lpu.parent_id is null ${depSql}
                    group by vwf.name,lpuname,lpu.id
                    order by lpuname"/>

            <msh:table printToExcelButton="Сохранить в Excel" name="allByLpu" cellFunction="true" action="smo_deniedHospitelByAttach.do?dateBegin=${dateStart}&dateEnd=${dateFinish}&short=Short&lpuId=${lpuId}" idField="1">
                <msh:tableColumn columnName="№" identificator="false" property="sn" />
                <msh:tableColumn columnName="ЛПУ прикр." property="1"/>
                <msh:tableColumn columnName="Рабочая функция" property="2"/>
                <msh:tableColumn columnName="Самообращений" property="3" isCalcAmount="true"/>
                <msh:tableColumn columnName="Каретой С/П" property="4" isCalcAmount="true"/>
                <msh:tableColumn columnName="Другим ЛПУ" property="5" isCalcAmount="true"/>
            </msh:table>
        <% }}else{ %>
        <i>Выберите параметры поиска и нажмите "Найти" </i>
        <%}%>

    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function mshPrintTextToExcelTable (html) {
                window.location.href='data:application/vnd.ms-excel,'+'\uFEFF'+encodeURIComponent(html); }
            function mshSaveTableToExcelById() {
                mshPrintTextToExcelTable(document.getElementById("allByLpu").outerHTML);}

            checkFieldUpdate('typeView','${typeView}',1) ;

            function checkFieldUpdate(aField,aValue,aDefault) {
                aValue=+aValue ;
                eval('var chk =  document.forms[0].'+aField) ;
                max = chk.length ;
                if (aValue<1) aValue=+aDefault ;
                if (aValue>max) {
                    if (aDefault>max) {
                        chk[max-1].checked='checked' ;
                    } else {
                        chk[aDefault-1].checked='checked' ;
                    }
                } else {
                    chk[aValue-1].checked='checked' ;
                }
            }
        </script>
    </tiles:put>
</tiles:insert>
