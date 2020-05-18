<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page import="com.google.gson.JsonParser" %>
<%@ page import="com.google.gson.JsonObject" %>
<%@ page import="com.google.gson.JsonArray" %>
<%@ page import="com.google.gson.JsonElement" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <%
        String typeDate = ActionUtil.updateParameter("BrList","typeDate","1", request) ;
        String typeView = ActionUtil.updateParameter("BrList","typeView","1", request) ;
        String typeViewBr = ActionUtil.updateParameter("BrList","typeViewBr","1", request) ;
    %>
    <tiles:put name="title" type="string">
        <msh:title mainMenu="StacJournal" title="Отчет по браслетам пациентов"></msh:title>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/journal_bracelets.do" defaultField="department" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:row>
                        <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
                    </msh:row>
                    <msh:row>
                        <ecom:oneToManyOneAutocomplete label="Браслеты" vocName="vocColorIdentityPatientWithPat" property="filterAdd1" colSpan="16"/>
                    </msh:row>
                    <msh:row>
                        <td></td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeViewBr" value="1">  хотя бы один
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeViewBr" value="2">  только выбранные
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeViewBr" value="3">  включая все выбранные
                        </td>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="dateBegin" label="Период с" />
                        <msh:textField property="dateEnd" label="по" />
                    </msh:row>
                    <msh:row>
                        <td class="label" title="Поиск по дате  (typeDate)"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeDate" value="1">  поступления
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeDate" value="2">  выписки
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeDate" value="3">  регистрации браслета
                        </td>
                    </msh:row>


                    <msh:row>
                        <td></td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeView" value="1">  свод по отделениям
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeView" value="2">  реестр пациентов
                        </td>
                    </msh:row>
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <script type='text/javascript'>

            checkFieldUpdate('typeDate','${typeDate}',1) ;
            checkFieldUpdate('typeView','${typeView}',1) ;
            checkFieldUpdate('typeViewBr','${typeViewBr}',1) ;
            function checkFieldUpdate(aField,aValue,aDefault) {

                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)>max) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
        </script>
        <%!
            /**
             * Получить массив из json
             * @param brs String json
             * @return Long[] массив с id
             */
            public Long[] getMasFromJson(String brs){
                List<Long> list = new ArrayList<>();
                JsonParser parser = new JsonParser();
                JsonObject jparsr = parser.parse(brs).getAsJsonObject();
                JsonArray brEls = jparsr.getAsJsonArray("childs");
                for (JsonElement el : brEls) {
                    JsonObject elo = el.getAsJsonObject();
                    String val = elo.get("value").toString().replaceAll("\"", "");
                    if (!val.equals("")) {
                        Long l=0L;
                        try {
                            l = Long.parseLong(val);
                        } catch (NumberFormatException e) { }
                        if (l>0L)
                            list.add(l);
                    }
                }
                return list.toArray(new Long[list.size()]);
            }
        %>
        <%!
            /**
             * Получить строку в формате, удобном для сравнения с list(varchar) в postgres
             * @param arr Long[] отсортированный массив
             * @return String id через запятую
             */
            public String getStrWithChoosenIds(Long[] arr){
                StringBuilder brSb = new StringBuilder();
                String brString="";
                for (long c : arr) {
                    brSb.append(c).append(", ");
                }
                brString = brSb.toString();
                if (brSb.length() > 1)
                    brString = brString.substring(0, brString.length() - 2);
                return brString;
            }
        %>
        <%!
            /**
             * Сортировка массива
             */
            public void bubbleSort(Long[] arr) {
                for (int i = arr.length - 1; i > 0; i--) {
                    for (int j = 0; j < i; j++) {
                        if (arr[j] > arr[j + 1]) {
                            Long tmp = arr[j];
                            arr[j] = arr[j + 1];
                            arr[j + 1] = tmp;
                        }
                    }
                }
            }
        %>
        <%
            String department = request.getParameter("department") ;
            if (department!=null && !department.equals("")) request.setAttribute("department"," and m.department_id="+department);

            String date = (String)request.getParameter("dateBegin") ;
            String dateEnd = (String)request.getParameter("dateEnd") ;

            if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;


            if (typeDate.equals("1")) {typeDate="sls.dateStart";}
            else if (typeDate.equals("2")) {typeDate="sls.dateFinish";}
            else if (typeDate.equals("3")) {typeDate="cip.startdate";}

            String sqlDate = date!=null && !date.equals("")?
                    " and " + typeDate + " between to_date('"+date+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')" :
                    " and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)";
            request.setAttribute("sqlDate", sqlDate) ;

            String timeStampSql = date!=null && !date.equals("") && typeDate.equals("3") ?
                    //если есть даты и учитываем регистрацию браслета
                    " and (cip.startdate<=to_date('" + date + "','dd.mm.yyyy') and cip.finishdate<=to_date('" + dateEnd + "','dd.mm.yyyy') " :
                    //если всё по текущий момент (в периоде будут выведены все браслеты)
                    " and (cip.startdate<=current_date and cip.finishdate is null" +
                            " or (cast ((cip.finishdate||' '||cip.finishtime) as TIMESTAMP) > current_timestamp)) " ;
            request.setAttribute("timeStampSql",timeStampSql);
            String brs = request.getParameter("brs");

            String brString = "";
            String brSql = "";
            if (brs==null || brs.isEmpty()) {
                brs = request.getParameter("filterAdd1");
                if (brs != null && !brs.equals("") && !brs.equals("null")) {
                    Long[] mas = getMasFromJson(brs);
                    bubbleSort(mas);
                    brString = getStrWithChoosenIds(mas);
                }
            }
            else
                brString=brs;
              if (!brString.isEmpty()) {
                  if (typeViewBr.equals("1")) {
                      brSql = " and vcid.id in (" + brString + ")";
                  } else if (typeViewBr.equals("2")) { //только выбранные
                      /*
                      Сравниваю list(id voc-браслетов), отсортированные по возрастанию, как и массив
                      т.е., например "1, 2, 3".
                      Если такая же строка получена после сортировки выбранных на форме
                      - в слс есть исключительно браслеты, которые указаны на форме
                      * */
                      brSql = " and (select list(cast(t.ids as varchar)) from (" +
                              " select list(cast(vcid.id as varchar)) as ids from medcase_coloridentitypatient mcid " +
                              " left join ColorIdentityPatient cip on cip.id=mcid.colorsidentity_id" +
                              " left join VocColorIdentityPatient vcid on vcid.id=cip.voccoloridentity_id" +
                              " where mcid.medcase_id=sls.id " + timeStampSql + "group by vcid.id order by vcid.id) as t) = '" + brString + "'";
                  } else if (typeViewBr.equals("3")){ //включая все выбранные
                      brSql = " and (select sqluser.checkBracelet('" + brString + "',list(cast(vcid.id as varchar))) from medcase_coloridentitypatient mcid" +
                              " left join ColorIdentityPatient cip on cip.id=mcid.colorsidentity_id" +
                              " left join VocColorIdentityPatient vcid on vcid.id=cip.voccoloridentity_id" +
                              " where mcid.medcase_id=sls.id "+ timeStampSql + ")=true ";
                  }
              }
            request.setAttribute("brSql", brSql);
            request.setAttribute("brs", brString);

            String title = "Журнал браслетов пациентов";
            title += " в отделении " + request.getParameter("departmentName");
            if (brs!=null && !brs.equals("")) title += " с выбранными браслетами ";
            title += date!=null && !date.equals("")?
                    " за период " + date + " - " + dateEnd : " на текущий момент";
            request.setAttribute("title",title);
            if (typeView.equals("2") || (department!=null && !department.equals(""))) {
        %>
        <msh:section>
            <ecom:webQuery name="brList" nameFldSql="brList_sql" nativeSql="
       select m.id
    ,sc.code as sccode
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
    ,to_char(pat.birthday,'dd.mm.yyyy') as birthday
    ,to_char(m.dateStart,'dd.mm.yyyy')
    ||case when m.datestart!=sls.dateStart then '(госп. с '||to_char(sls.dateStart,'dd.mm.yyyy')||')' else '' end
    ||case when m.dateFinish is not null then ' выписка '||to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) else '' end as datestar
    	,wp.lastname||' '||wp.firstname||' '||wp.middlename as worker
   ,max(dep.name) as depn
    ,list(vdrt.name||' '||vpd.name||' '||mkb.code) as diag
,case when cast(max(cast(vcid.isfornewborn as int)) as boolean) and cast(max(cast(dep.isnewborn as int)) as boolean) then 'background-color:'||max(vcr.code)||'; color:black' else '' end as styleRow
     ,cast('-' as varchar(1)) as tempId
       ,cast ((select to_json(array_agg(t)) from	(select cip.id,vc.name||' ('||vcip.name||')' as colName
    ,vc.code as colorCode,vcip.name as vsipnameJust,vc.picture as picture, substring(cip.info from 0 for 30) as info
,case when vcip.isforpatology then '1' else '0' end as isforpat
				from vocColorIdentityPatient vcip
				left join coloridentitypatient cip on cip.voccoloridentity_id=vcip.id
				left join voccolor vc on vcip.color_id=vc.id
				 left join medcase_coloridentitypatient
				 ss on ss.colorsidentity_id=cip.id where
				(medcase_id=sls.id or medcase_id=m.id)  ${timeStampSql}) as t) as varchar) as jsonAr
    from medCase m
    left join Diagnosis diag on diag.medcase_id=m.id
    left join vocidc10 mkb on mkb.id=diag.idc10_id
	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id

    left join MedCase as sls on sls.id = m.parent_id
    left join medcase sloAll on sloAll.parent_id=sls.id and sloAll.dtype='DepartmentMedCase'
left join Mislpu dep on dep.id=sloAll.department_id
    left join bedfund as bf on bf.id=m.bedfund_id
    left join StatisticStub as sc on sc.medCase_id=sls.id
     left join WorkFunction wf on wf.id=m.ownerFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join Patient pat on m.patient_id = pat.id
   left join medcase_coloridentitypatient mcid on mcid.medcase_id=sls.id
left join ColorIdentityPatient cip on cip.id=mcid.colorsidentity_id
left join VocColorIdentityPatient vcid on vcid.id=cip.voccoloridentity_id
left join voccolor vcr on vcr.id=vcid.color_id
    where m.DTYPE='DepartmentMedCase' ${department} ${timeStampSql}
    and m.transferDate is null ${sqlDate}  ${brSql}
    and mcid.colorsidentity_id is not null
    group by  m.id,m.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,sc.code,wp.lastname,wp.firstname,wp.middlename,sls.dateStart
    ,bf.addCaseDuration,m.dateFinish,m.dischargeTime,sls.id
    order by pat.lastname,pat.firstname,pat.middlename
    "/>
            <msh:sectionTitle>
                ${title}
            </msh:sectionTitle>
            <msh:sectionContent>

                <msh:table name="brList" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" styleRow="9">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn columnName="Стат.карта" property="2" />
                    <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" />
                    <msh:tableColumn columnName="Год рождения" property="4" />
                    <msh:tableColumn columnName="Дата поступления" property="5" />
                    <msh:tableColumn columnName="Леч.врач" property="6"/>
                    <msh:tableColumn columnName="Отделение" property="7"/>
                    <msh:tableColumn columnName="Диагноз" property="8"/>
                    <msh:tableColumn columnName="Браслеты пациента" property="10"/>
                    <msh:tableColumn columnName="Браслеты пациента hidden" property="11" hidden="true"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
        } else {
%>
        <msh:section>
            <msh:sectionTitle>Результаты поиска. Если не введён период, показываются пациенты с браслетами в текущий момент</msh:sectionTitle>
        </msh:section>
        <msh:section>
        <msh:sectionContent>
            <ecom:webQuery name="braceletsListAll" nameFldSql="braceletsListAll_sql" nativeSql="
    select m.department_id,ml.name, count(distinct sls.id) as cntSls
    ,'&department='||coalesce(m.department_id,0)||'&departmentName='||coalesce(ml.name,'')
    from medCase m
    left join MedCase as sls on sls.id = m.parent_id
    left join MisLpu ml on ml.id=m.department_id
    left join patient pat on pat.id=sls.patient_id
    left join medcase_coloridentitypatient mci on mci.medcase_id = sls.id or mci.medcase_id = m.id
    left join ColorIdentityPatient cip on cip.id=mci.colorsidentity_id
    left join VocColorIdentityPatient vcid on vcid.id=cip.voccoloridentity_id
    where m.DTYPE='DepartmentMedCase'
    and m.transferDate is null ${sqlDate} ${brSql} ${department} ${timeStampSql}
    and mci.colorsidentity_id is not null
    group by m.department_id,ml.name
    order by ml.name
    " />
            <msh:table name="braceletsListAll"
                       action="journal_bracelets.do?dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&brs=${brs}"
                       idField="4">
                <msh:tableColumn property="sn" columnName="#"/>
                <msh:tableColumn columnName="Отделение" property="2"/>
                <msh:tableColumn columnName="Кол-во пациентов с браслетами" property="3" isCalcAmount="true"/>
            </msh:table>
        </msh:sectionContent>
    </msh:section>
        <%}%>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
        <script type="text/javascript">
            function setValOrNull(field,ifcase) {
                if (ifcase || typeof $(field).value === 'undefined' || typeof $(field+'Name').value === 'undefined'
                || $(field).value==null || $(field).value=='null' || $(field+'Name').value=='null') {
                    $(field).value='';
                    $(field+'Name').value='';
                }
            }
            setValOrNull('department',true);
            <%
            if (request.getParameter("departmentName")!=null && request.getParameter("department")!=null)
            %>
            $('department').value='<%= request.getParameter("department") %>';
            $('departmentName').value='<%= request.getParameter("departmentName")%>';

            <%
           if (request.getParameter("filterAdd1")!=null)
           %>
            $('filterAdd1').value='<%= request.getParameter("filterAdd1") %>';

            setValOrNull('department',false);

            var tableBr = getTableToSetBracelets('brList');
            if (tableBr!=null)
                setBr(tableBr,9,10);
        </script>
    </tiles:put>
</tiles:insert>