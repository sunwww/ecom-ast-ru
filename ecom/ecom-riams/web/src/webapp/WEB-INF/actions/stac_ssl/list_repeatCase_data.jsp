<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Список случаев медицинского обслуживания </msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    	<tags:stac_journal currentAction="stac_journalRepeatCaseByHospital"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  	String[] ids = request.getParameter("id").split(":") ;
    String dtype=ids[4] ;
    request.setAttribute("patient", ids[0]) ;
    request.setAttribute("startDate", ids[1]) ;
    request.setAttribute("finishDate", ids[2]) ;
    request.setAttribute("count", ids[3]) ;
    request.setAttribute("dtype", dtype) ;
    request.setAttribute("addSql", ids[5].replaceAll("@", "'")) ;
    ActionUtil.setParameterFilterSql("department", "ml.id", request) ;
    if (dtype.equals("HospitalMedCase")) {
  %>
    <msh:section>
    <msh:sectionTitle>Реестр случаев лечения в стационаре 
    <a href="stac_journalRepeatCaseByHospital_list.do">Изменить...</a>
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_repeatCase" nativeSql="
    select m.id,p.lastname||' '||p.firstname||' '||p.middlename
    ,ml.name as depname,ss.code as sscode,p.birthday
    ,m.dateStart,m.dateFinish,vdh.name as vdhname,m.ambulanceTreatment 
    from MedCase as m 
    left join VocDeniedHospitalizating vdh on vdh.id=m.deniedHospitalizating_id 
    left join statisticstub as ss on ss.id=m.statisticstub_id 
    left join bedfund as bf on bf.id=m.bedfund_id 
    left join vocbedsubtype as vbst on vbst.id=bf.bedSubType_id 
    left join vocbedtype as vbt on vbt.id=bf.bedtype_id 
    left join mislpu as ml on ml.id=m.department_id 
    left join patient as p on p.id=m.patient_id 
    where 
    m.patient_id='${patient}'
    and m.DTYPE='${dtype}' 
    and m.dateStart between to_date('${startDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
    ${addSql} ${departmentSql}
    and  (m.noActuality='0' or m.noActuality is null)
    order by m.dateStart" />
    <msh:table name="journal_repeatCase" action="entitySubclassView-mis_medCase.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Стат.карта" property="4" />
      
      <msh:tableColumn columnName="Отделение" property="3" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="2" />
      <msh:tableColumn columnName="Амб.случаи" property="9" />
      <msh:tableColumn columnName="Отказ от госпитализации" property="8" />
      <msh:tableColumn columnName="Дата пост. " property="6" />
      <msh:tableColumn columnName="Дата выписки" property="7" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <%}else { %>
    <msh:section>
    <msh:sectionTitle>Реестр визитов 
    <a href="stac_journalRepeatCaseByHospital_list.do">Изменить...</a>
    </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_repeatCase" nativeSql="
    select m.id as mid
    ,m.dateStart  as mdatestart  ,vwf.name as vwfname
    ,wp.lastname||' '||substring(wp.firstname,1,1)||coalesce(substring(wp.middlename,1,1),'') as spec

    from MedCase as m 
    left join patient as p on p.id=m.patient_id 
    left join workfunction wf on wf.id=m.workFunctionExecute_id
left join vocworkfunction vwf on vwf.id=wf.workFunction_id
left join worker w on w.id=wf.worker_id
left join mislpu ml on ml.id=w.lpu_id

left join patient wp on wp.id=w.person_id
    where 
    m.patient_id='${patient}'
    and m.DTYPE='${dtype}' 
    and m.dateStart between to_date('${startDate}','dd.mm.yyyy') and to_date('${finishDate}','dd.mm.yyyy')
    ${addSql}  ${departmentSql}
    order by m.dateStart" />
    <msh:table name="journal_repeatCase" action="entitySubclassView-mis_medCase.do" idField="1">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Дата приема" property="2" />
      <msh:tableColumn columnName="Рабочая функция" property="3" />
      <msh:tableColumn columnName="Специалист" property="4" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    
    <%} %>
  </tiles:put>
</tiles:insert>

