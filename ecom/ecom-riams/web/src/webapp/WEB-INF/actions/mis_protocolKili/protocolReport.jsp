<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal" guid="f6e72e89-0ba7-4f9e-97f6-0a1ecaf5b162">Отчет по КИЛИ</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="677746d8-d63e-44a3-8e8b-e227dea8decb">
      <msh:sideLink roles="/Policy/MedCase" key="ALT+N" action="/" name="По отделению" guid="b6f99225-3f13-4e39-91a4-3b371f8dce53" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
  <% String shor = request.getParameter("short");
  if (shor==null|| shor.equals("")){
  %>
     <msh:form action="/protocolReport.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
     <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" />
        </msh:row><msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" /></msh:row>
        <msh:row>
         
            <td><input type="submit" value="Найти" /></td>
      </msh:row>
          </msh:panel>
</msh:form>
<%} %>
 <ecom:webQuery name="diag_typeReg_vip_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='3'"/>
 <ecom:webQuery name="diag_typeReg_klin_sql" nativeSql="select id,name from VocDiagnosisRegistrationType where code='4'"/>
 <ecom:webQuery name="diag_mainPriority_sql" nativeSql="select id,name from VocPrimaryDiagnosis where code='1'"/>
 
  <%
  String isReestr = request.getParameter("reestr");
  //String department = (String) request.getParameter("department");
  //String typeDate=ActionUtil.updateParameter("Report14","typeDate","2", request) ;
	String startDate = (String) request.getParameter("dateBegin"); //Получаем значение текстового поля с первой датой
	String finishDate = (String) request.getParameter("dateEnd");	//Получаем значение текстового поля со второй датой
	request.setAttribute("dateBegin", startDate);
	request.setAttribute("dateEnd", finishDate);
  //String clinicalMkbName = "ml.name || '<br> '|| mkb2.code||' ' ||mkb2.name";
	String addSql = "";
	
  if (startDate!=null&&!startDate.equals("")) {
	  if (finishDate!=null&&!finishDate.equals("")) {
		  addSql+="between to_date('"+startDate+"','dd.MM.yyyy') and to_date('"+finishDate+"','dd.MM.yyyy')";
	  } else {
		  addSql += " and >=to_date('"+startDate+"','dd.MM.yyyy')";
	  }
  } else {
	  addSql +="between current_date and current_date";
  }
	request.setAttribute("addSql", addSql);
//	out.print("<H1>"+rees)
	if (isReestr!=null&&isReestr.equals("1")) {
		String addParam = request.getParameter("addParam");
		if (addParam!=null&&addParam.equals("1")) {
			addParam = " and dc.id is not null";
		} else if (addParam!=null&&addParam.equals("2")) {
			addParam = " and pk.id is not null";
		}
		request.setAttribute("addParam", addParam!=null?addParam:"");
		%> 
		<ecom:webQuery name="showProfile" nameFldSql="showProfile_sql" nativeSql="
SELECT sls.id, pat.patientinfo, sls.datefinish
, case when dc.id is null then 'НЕТ' else 'ДА' end as dc
, case when pk.id is null then 'НЕТ' else '№ '|| pk.protocolnumber|| ' от '||to_char(pk.protocoldate,'dd.MM.yyyy') end as pk
from medcase sls

left join medcase slo on slo.parent_id=sls.id 
left join patient pat on pat.id = sls.patient_id
left join mislpu dep on dep.id=slo.department_id
left join medcase sloPrev on sloPrev.id=slo.prevmedcase_id
left join mislpu depPrev on depPrev.id=sloPrev.department_id

left join deathcase dc on dc.medcase_id=sls.id
left join protocolkili pk on pk.deathcase_id=dc.id
left join vockiliprofile vkp on vkp.id=case when dep.isnoomc='1' then depPrev.kiliprofile_id else dep.kiliprofile_id end
left join vochospitalizationresult vhr on vhr.id=sls.result_id
WHERE sls.dtype='HospitalMedCase' and sls.datefinish ${addSql} 
and slo.dtype='DepartmentMedCase'  
and slo.datefinish is not null and vhr.code='11' 
and sls.deniedhospitalizating_id is null and vkp.id=${param.id} ${addParam}
"/>
    <msh:section>
    <msh:sectionContent>
    <msh:table name="showProfile" idField="1" action="entityParentView-stac_ssl.do" guid="d579127c-69a0-4eca-b3e3-950381d1585c"> 
      <msh:tableColumn columnName="Пациент" property="2" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Дата смерти" property="3" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Случай смерти" property="4" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Протокол КИЛИ" property="5" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
		
		<%
	} else {
	 	
 %>

    <ecom:webQuery name="datelist" nameFldSql="datelist_sql" nativeSql="    
SELECT vkp.id, vkp.name, COUNT(sls.id) as cnt_sls, COUNT(dc.id) as cnt_dc, count(pk.id) as cnt_pk
from medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.datefinish is not null
left join mislpu dep on dep.id=slo.department_id
left join medcase sloPrev on sloPrev.id=slo.prevmedcase_id
left join mislpu depPrev on depPrev.id=sloPrev.department_id

left join deathcase dc on dc.medcase_id=sls.id
left join protocolkili pk on pk.deathcase_id=dc.id
left join vockiliprofile vkp on vkp.id=case when dep.isnoomc='1' then depPrev.kiliprofile_id else dep.kiliprofile_id end
left join vochospitalizationresult vhr on vhr.id=sls.result_id
WHERE sls.dtype='HospitalMedCase' and sls.datefinish ${addSql} and slo.dtype='DepartmentMedCase'  and vhr.code='11' 
and sls.deniedhospitalizating_id is null

GROUP BY vkp.id, vkp.name
 


" guid="ac83420f-43a0-4ede-b576-394b4395a23a" />


        <msh:section>
    <msh:sectionContent>
    <msh:table name="datelist" idField="1" cellFunction="true" action="protocolReport.do?short=Short&reestr=1&dateBegin=${dateBegin}&dateEnd=${dateEnd}" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
      <msh:tableColumn columnName="Наименование профиля" property="2" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Умерших всего" property="3" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Случаев смерти" property="4" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" addParam="&addParam=1" />
      <msh:tableColumn columnName="Протоколов КИЛИ" property="5" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" addParam="&addParam=2"/>
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    
  <% } %>

  <script type='text/javascript'>
  checkFieldUpdate('typeDate','${typeDate}',2) ;

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
   </script>    
  </tiles:put>


  
</tiles:insert>

