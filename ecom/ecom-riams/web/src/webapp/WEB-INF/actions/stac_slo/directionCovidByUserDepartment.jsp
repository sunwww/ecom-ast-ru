<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.s}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Направление на исследование образцов крови COVID</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_directionCovidByUserDepartment"/>
  </tiles:put>
  <tiles:put name="body" type="string">
        <%
		    Long department = (Long)request.getAttribute("department") ;
		    if (department!=null && department.intValue()>0 )  {
    	%>
    <msh:section>
    <ecom:webQuery name="datelist" nameFldSql="datelist_sql" nativeSql="
    select pat.id
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
    ,cast(date_part('year',age(m.dateStart, pat.birthday)) as int) as birthday
    ,to_char(m.dateStart,'dd.mm.yyyy')  as datestart
    from medCase m
    left join Patient pat on m.patient_id = pat.id
    where m.DTYPE='DepartmentMedCase' and m.department_id='${department}'
    and m.transferDate is null and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)
    group by pat.id,m.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,m.dateFinish,m.dischargeTime
    order by pat.lastname,pat.firstname,pat.middlename
    "
     />
     <msh:sectionTitle>
       <form action="print-dirCovid.do" method="post" target="_blank" id="printForm">
         Журнал состоящих пациентов в отделении  ${departmentInfo} на текущий момент
         <input type='hidden' name="info" id="info" value="">
         <input type='hidden' name="s" id="s" value="HospitalPrintService">
         <input type='hidden' name="m" id="m" value="printDirectionCovid">
         <input type="button" value="ПЕЧАТЬ НАПРАВЛЕНИЯ" onclick="print()">
       </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="datelist" viewUrl="entityShortView-mis_patient.do" action="javascript:void()" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Выгрузить" property="1"/>
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="2" />
      <msh:tableColumn columnName="Возраст" property="3" />
      <msh:tableColumn columnName="Дата поступления" property="4" />
      <msh:tableColumn columnName="Номер пробирки" property="1" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
    <msh:section>
    <msh:sectionTitle>Свод состоящих пациентов в отделении  ${departmentInfo} на текущий момент
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nameFldSql="datelist_sql" nativeSql="
    select m.department_id,ml.name, count(distinct sls.id) as cntSls
    ,count(distinct case when m.emergency='1' then sls.id else null end) as cntEmergency
    ,count(distinct case when so.id is not null or so1.id is not null then sls.id else null end) as cntOper
    from medCase m
    left join MedCase as sls on sls.id = m.parent_id
    left join StatisticStub as sc on sc.medCase_id=sls.id
    left join SurgicalOperation so on so.medCase_id=m.id
    left join SurgicalOperation so1 on so1.medCase_id =sls.id
    left join MisLpu ml on ml.id=m.department_id
    where m.DTYPE='DepartmentMedCase'
    and m.transferDate is null and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)
    group by m.department_id,ml.name
    order by ml.name
    "
     />
    <msh:table name="datelist" viewUrl="stac_directionCovidByUserDepartment.do?s=Short&" action="stac_directionCovidByUserDepartment.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Кол-во состоящих" property="3" />
      <msh:tableColumn columnName="кол-во экстренных" property="4" />
      <msh:tableColumn columnName="кол-во опер. пациентов" property="5" />
    </msh:table>
     </msh:sectionContent>
     </msh:section>
     </msh:ifInRole>
    <% } %>

  </tiles:put>
  <%
    Long department = (Long)request.getAttribute("department") ;
    if (department!=null && department>0L )  {
  %>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">
        var table=document.getElementsByTagName('table')[0];


        //создать checkbox
        function createChkBox(td,ii) {
            td.innerHTML="<input id=\"chk_"+ii+"\" name=\"chk_"+ii+"\" type=\"checkbox\" >";
        }

        //создать текстовое поле
        function createTextField(td,ii) {
            td.innerHTML="<input title=\"номер пробирки\" id=\"regNum_"+ii+"\" name=\"regNum_"+ii+"\" size=\"10\" value=\"\" type=\"text\" autocomplete=\"off\">";
        }

        //Milamesher #179 - вывод списка кода обследуемого
        function setVocCode() {
            table.className='';
            if (typeof table !== 'undefined') {
                for (var ii = 1; ii < table.rows.length; ii++) {
                    createChkBox(table.rows[ii].cells[2],ii);
                    createTextField(table.rows[ii].cells[6],ii);
                }
            }
        }

        setVocCode();

        function print() {
            var params='';
            if (table) {
                for (var ii = 1; ii < table.rows.length; ii++) {
                    var row = table.rows[ii];
                    if ($('chk_'+ii).checked) {
                      if (params.length>0) params+="!";
                        params += $(row).children[3].textContent+'-'+$(row).children[5].textContent+'-'+$(row).children[4].textContent
                            +'-'+($('regNum_'+ii).value?$('regNum_'+ii).value:"_");
                    }
                }
            }
            if (params.length>0){
                $('info').value = params;
              document.forms[0].submit();
            }

            else
                showToastMessage('Не выбрано ни одного пациента!',null,true);
        }
    </script>
  </tiles:put>
  <%}%>
</tiles:insert>