<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="style" type="string">
    <style type="text/css">tr.deniedHospitalizating td {
            color: red ;
        }</style>
  </tiles:put>
  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Список всех ССЛ</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu>
      <msh:sideLink roles="/Policy/MedCase" key="ALT+N" action="/" name="По отделению" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
     <ecom:webQuery name="sex_woman_sql" nativeSql="select id,name from VocSex where omccode='2'"/>
  
  <%
  String typeAge=ActionUtil.updateParameter("Report14","typeAge","8", request) ;
  String typeDate=ActionUtil.updateParameter("Report14","typeDate","2", request) ;
	String dateAge="dateStart" ;
	if (typeDate!=null && typeDate.equals("2")) {
		dateAge="dateFinish" ;
	}
  	ActionUtil.getValueByList("sex_woman_sql", "sex_woman", request) ;
	String sexWoman = (String)request.getAttribute("sex_woman") ;
  	
	if (typeAge!=null &&typeAge.equals("3")) {
		StringBuilder age = new StringBuilder() ;
		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
				.append(" -cast(to_char(p.birthday,'yyyy') as int)")
				.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
				.append(" -cast(to_char(p.birthday, 'mm') as int)")
				.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
				.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
				.append(" <0)")
				.append(" then -1 else 0 end) < 18 ") ;
		request.setAttribute("age_sql", age.toString()) ;
		request.setAttribute("age_info", "В. Дети") ;
	} else if (typeAge!=null &&typeAge.equals("2")) {
		StringBuilder age = new StringBuilder() ;
		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) > case when p.sex_id='").append(sexWoman).append("' then 55 else 60 end ") ;
		request.setAttribute("age_sql", age.toString()) ;
		request.setAttribute("reportInfo", "Б. Взрослые старше трудоспособного возраста") ;
	} else if (typeAge!=null &&typeAge.equals("1")) {
		StringBuilder age = new StringBuilder() ;
		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) >= 18 ") ;
		//.append(" then -1 else 0 end) between 18 and case when p.sex_id='").append(sexWoman).append("' then 55 else 60 end ") ;
		request.setAttribute("age_sql", age.toString()) ;
		request.setAttribute("reportInfo", "А. Взрослые") ;
	} else if (typeAge!=null &&typeAge.equals("7")) {
		StringBuilder age = new StringBuilder() ;
		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) between 18 and case when p.sex_id='")
			.append(sexWoman).append("' then 55 else 60 end ") ;
		request.setAttribute("age_sql", age.toString()) ;
		request.setAttribute("reportInfo", "А.1. Взрослые трудоспособного возраста") ;
	} else if (typeAge!=null &&typeAge.equals("4")) {
		StringBuilder age = new StringBuilder() ;
		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) < 1 ") ;
		request.setAttribute("age_sql", age.toString()) ;
		request.setAttribute("reportInfo", "до года") ;
	} else if (typeAge!=null &&typeAge.equals("5")) {
		StringBuilder age = new StringBuilder() ;
		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) between 0 and 14 ") ;
		request.setAttribute("age_sql", age.toString()) ;
		request.setAttribute("reportInfo", "0-14") ;
	} else if (typeAge!=null &&typeAge.equals("6")) {
		StringBuilder age = new StringBuilder() ;
		age.append(" and cast(to_char(m.").append(dateAge).append(",'yyyy') as int)")
			.append(" -cast(to_char(p.birthday,'yyyy') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(", 'mm') as int)")
			.append(" -cast(to_char(p.birthday, 'mm') as int)")
			.append(" +(case when (cast(to_char(m.").append(dateAge).append(",'dd') as int)") 
			.append(" - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)")
			.append(" <0)")
			.append(" then -1 else 0 end) between 15 and 17 ") ;
		request.setAttribute("age_sql", age.toString()) ;
		request.setAttribute("reportInfo", "15-17") ;
	} %>
    <ecom:webQuery name="datelist" nameFldSql="datelist_sql" nativeSql="
    select m.id,m.dateStart,m.dateFinish,m.username,stat.code
    ,p.lastname ||' ' ||p.firstname|| ' ' || p.middlename
    ,p.birthday,dep.name,case when m.emergency='1' then 'Экстр.' else 'План.' end as memergency,m.noActuality  
    ,
    case when (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsVillage='1' then 'С.ж'
      when (oo.voc_code='643' or oo.id is null) and substring(a.kladr,1,2)='30' and a.addressIsCity='1' then 'Гор.'
        when (oo.voc_code='643' or oo.id is null) and a.addressid is not null and substring(a.kladr,1,2)!='30' then 'Иногор.'
          when oo.voc_code!='643' then 'Иностр.'
            when (oo.voc_code='643' or oo.id is null) and a.addressid is null then 'Другое'
            when (oo.voc_code='643' or oo.id is null) and a.domen<3 then 'Другое адрес не полностью заполен'
            else ''
              end as jitel
     ,vr.name as vrname
     ,list(vdrt.name||' '||vpd.name||' '||mkb.code) as diagnosis
    from MedCase m 
left join VocPreAdmissionTime vpat on vpat.id=m.preAdmissionTime_id
    left join MisLpu dep on m.department_id = dep.id
    left join Patient p on m.patient_id = p.id  
    left join StatisticStub stat on m.statisticstub_id=stat.id 
    left join MisLpu lpu on m.department_id = lpu.id 
    left join Address2 a on p.address_addressid=a.addressid 
    left join Omc_Oksm oo on oo.id=p.nationality_id
    left join Omc_Frm of1 on of1.id=m.orderType_id
     
    left join VocRayon vr on vr.id=p.rayon_id
	left join Diagnosis diag on diag.medcase_id=m.id
	left join VocIdc10 mkb on mkb.id=diag.idc10_id
	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
	left join MisLpu as ml on ml.id=m.department_id
	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
    where m.DTYPE='HospitalMedCase' ${paramPeriod} ${addParam} ${emergency} ${department} ${pigeonHole}
     ${age_sql} ${patientSql} ${hospSql} ${frmSql} ${dostSql}
     group by m.id,m.dateStart,m.dateFinish,m.username,stat.code
    ,p.lastname ,p.firstname,p.middlename
    ,p.birthday,dep.name , m.emergency ,m.noActuality  ,oo.voc_code,oo.id,a.kladr
    ,a.addressIsVillage,a.addressIsCity,a.addressid,a.domen,vr.name
    order by p.lastname ,p.firstname,p.middlename
    " />
    <msh:section>
    <msh:sectionTitle>
            <form action="print-stac_journalByHospital_r.do" method="post" target="_blank">
        Реестр
         <input type='hidden' name="sqlText" id="sqlText" value="${datelist_sql}"> 
        <input type='hidden' name="sqlInfo" id="sqlInfo" value="">
        <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
        
        <input type='hidden' name="s" id="s" value="PrintService">
        <input type='hidden' name="m" id="m" value="printNativeQuery">
        <input type="submit" value="Печать"> 
                                </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table viewUrl="entityShortView-stac_ssl.do" name="datelist" idField="1" action="entityView-stac_ssl.do"  printToExcelButton="Сохранить в excel">
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Стат.карта" property="5" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="6" />
      <msh:tableColumn columnName="Дата рождения" property="7" />
      <msh:tableColumn columnName="Диагноз" property="13" />
      <msh:tableColumn columnName="Дата поступления" property="2" />
      <msh:tableColumn columnName="Дата выписки" property="3" />
      <msh:tableColumn columnName="Отделение поступления" property="8" />
      <msh:tableColumn columnName="Порядок поступления" property="9" />
      <msh:tableColumn columnName="Житель" property="11" />
      <msh:tableColumn columnName="Район" property="12" />
      <msh:tableColumn columnName="Кем открыт" property="4" />
    </msh:table>
    <msh:tableNotEmpty name="list">
      <div class="h3">
        <h3>Легенда</h3>
      </div>
      <table class="tabview">
        <tr class="deniedHospitalizating">
          <td>―</td>
          <td>Отказ в госпитализации</td>
        </tr>
        <tr class="current">
          <td />
          <td>Текущая госпитализация</td>
        </tr>
      </table>
    </msh:tableNotEmpty>
    </msh:sectionContent>
    </msh:section>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Перейти">
      <msh:sideLink key="ALT+2" params="id" action="/entityView-mis_patient" name="⇧ Пациент" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

