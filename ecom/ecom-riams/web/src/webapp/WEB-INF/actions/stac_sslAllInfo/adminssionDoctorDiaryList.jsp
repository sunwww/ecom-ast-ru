<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Журнал пациентов, осмотренных врачами приемного отделения</msh:title>
  </tiles:put>

  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_reestrByHospital"/>
  </tiles:put>

  <tiles:put name="body" type="string">
      <%
          if (request.getParameter("short")==null) {
      %>
    <msh:form action="/stac_admissionDoctorDiaryList.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">

      <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="1">  Журнал госпитализированных
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeView" value="2"  >  Свод по врачам
	        </td>
            <td onclick="this.childNodes[1].checked='checked';">
                <input type="radio" name="typeView" value="3">  Журнал по отказникам
            </td>
            <td onclick="this.childNodes[1].checked='checked';">
                <input type="radio" name="typeView" value="4"  >  Свод по врачам (отказники)
            </td>
        </msh:row>
        <msh:row>
        <msh:textField fieldColSpan="2" property="dateBegin" label="Период с" />
        <msh:textField fieldColSpan="2" property="dateEnd" label="Период по" />
      </msh:row>
          <msh:submitCancelButtonsRow colSpan="4" notDisplayCancel="true" submitLabel="Найти" />

    </msh:panel>
    </msh:form>
      <%  }
      %>
       <script type='text/javascript'>
           checkFieldUpdate('typeView','${param.typeView}','1');

           function checkFieldUpdate(aField,aValue,aDefaultValue) {
                eval('var chk =  document.forms[0].'+aField) ;
                var aMax=chk.length ;
                //alert(aField+" "+aValue+" "+aMax+" "+chk) ;
                if ((+aValue)==0 || (+aValue)>(+aMax)) {
                    chk[+aDefaultValue-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
           }

        if ($('dateBegin').value=="") {
            $('dateBegin').value=getCurrentDate() ;
        }
    </script>
    <%
    String date = request.getParameter("dateBegin") ;

    if (date!=null && !date.equals(""))  {
        String finishDate = request.getParameter("dateEnd");
        request.setAttribute("finishDate", finishDate!=null && !finishDate.equals("") ? finishDate : date);
        request.setAttribute("startDate",date);
    	String view = request.getParameter("typeView") ;
    	String doctor = request.getParameter("doctorId");
    	if (doctor!=null && !doctor.equals("")) {
    	    request.setAttribute("doctorSql"," and wf.id="+doctor);
        }

        String ifDenied = "3".equals(view) || "4".equals(view)? " " +
                " and sls.deniedhospitalizating_id is not null " :
                " and sls.deniedhospitalizating_id is null ";
    	request.setAttribute("ifDenied",ifDenied);

    	if ("1".equals(view) || "3".equals(view)) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Журнал за ${startDate} - ${finishDate}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql="
    select sls.id, pat.lastname||' '||pat.firstname||' '||pat.middlename as f2_patient
    ,to_char(pat.birthday,'yyyy') as f3_bdate ,wpat.lastname||' '||wpat.firstname||' '||wpat.middlename as f4_doctor, ml.name as dep
    ,sls.datestart as f6_datestart
    from medcase sls
    left join patient pat on pat.id=sls.patient_id
    left join diary d on d.medcase_id=sls.id
    left join workfunction wf on wf.id=d.specialist_id
    left join vocworkfunction vwf on vwf.id=wf.workfunction_id
    left join worker w on w.id=wf.worker_id
    left join patient wpat on wpat.id=w.person_id
    left join mislpu ml on ml.id=sls.department_id
    where sls.datestart between to_date('${startDate}','dd.MM.yyyy') and to_date('${finishDate}','dd.MM.yyyy') and sls.dtype='HospitalMedCase'
    ${ifDenied} and d.dtype='Protocol'
    ${doctorSql} and vwf.code='48'
    group by sls.id, pat.lastname, pat.firstname, pat.middlename, pat.birthday, wpat.lastname, wpat.firstname, wpat.middlename, ml.name
      " />
    <msh:table name="journal_priem" viewUrl="entityShortView-stac_ssl.do" action="entityParentView-stac_ssl.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Дата госпитализации" property="6" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="2" />
      <msh:tableColumn columnName="Год рождения" property="3" />
      <msh:tableColumn columnName="ФИО врача приемного отделения" property="4" />
      <msh:tableColumn columnName="Отделение" property="5" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }  else { // СВОД
    	    String tmpTypeView = "2".equals(view)? "1" : "3";
    	    request.setAttribute("tmpTypeView",tmpTypeView);
    		%>
    		    <msh:section>
    <msh:sectionTitle>Свод по врачам приемного отделения среди пациентов за ${startDate} - ${finishDate}
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql="
    select '&dateBegin=${startDate}&dateEnd=${finishDate}&typeView=${tmpTypeView}&doctorId='||wf.id,  wpat.lastname||' '||wpat.firstname||' '||wpat.middlename as f2_doctor, count(distinct sls.id) as f3_cnt
    from medcase sls
    left join patient pat on pat.id=sls.patient_id
    left join diary d on d.medcase_id=sls.id
    left join workfunction wf on wf.id=d.specialist_id
    left join vocworkfunction vwf on vwf.id=wf.workfunction_id
    left join worker w on w.id=wf.worker_id
    left join patient wpat on wpat.id=w.person_id
    where sls.datestart between to_date('${startDate}','dd.MM.yyyy') and to_date('${finishDate}','dd.MM.yyyy')
     and sls.dtype='HospitalMedCase' ${ifDenied}
     and d.dtype='Protocol' and vwf.code='48'
    group by wf.id, wpat.lastname, wpat.firstname, wpat.middlename
    order by wpat.lastname, wpat.firstname, wpat.middlename
      " />
    <msh:table name="journal_priem" action="stac_admissionDoctorDiaryList.do" idField="1" cellFunction="true">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="ФИО врача приемного отделения" property="2" />
      <msh:tableColumn columnName="Кол-во историй болезни" isCalcAmount="true" property="3" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    		<%
    	}} else {%>
    	<i>Нет данных </i>
    	<% }   %>
  </tiles:put>
</tiles:insert>