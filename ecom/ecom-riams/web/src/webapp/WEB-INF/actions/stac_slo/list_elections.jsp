<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="StacJournal">Журнал обращений по отделению</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_elections"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  String typeView=ActionUtil.updateParameter("Report14","typeView","1", request) ;
  %>
    <msh:form action="/stac_elections.do" defaultField="departmentName" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title=" (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Реестр:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1"> участок
        </td>
        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="2"> все госпитализированные
        </td>
       </msh:row>
          <msh:row>
	      	<msh:autoComplete property="department"  size="100" vocName="vocLpuHospOtdAll" label="Отделение" fieldColSpan="6" horizontalFill="true"/>
	      </msh:row>
      <msh:row >
        <msh:textField property="dateBegin" label="Дата начала госпитализации"  />
        
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>

                  </msh:row>
                 
    </msh:panel>
    </msh:form>
     <script type='text/javascript'>
           checkFieldUpdate('typeView','${typeView}',1) ;
           
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
    <%
    String date = (String)request.getParameter("dateBegin") ;
    ActionUtil.setParameterFilterSql("department", "slo.department_id", request);
    String addSql = "";
    String electionDate = "18.03.2018"; //Возраст считаем на дату выборов
    request.setAttribute("electionDate",electionDate);
    if (typeView.equals("1")) {
    	addSql=" and (select count(*) from lpuareaaddresspoint p where pat.address_addressid=p.address_addressid )>0" ;
    }

    if (date!=null && !date.equals("") )  {
        addSql+=" and sls.dateStart>=to_date('"+date+"','dd.MM.yyyy') ";
    }
        request.setAttribute("addSql", addSql) ;

    	%>
    
    <msh:section>

    <ecom:webQuery name="journal_priem" nameFldSql="journal_priem_sql" nativeSql="

select
sls.id as slsid,
case when ml.shortname='' or ml.shortname is null then ml.name else ml.shortname end as mlname,
ml.name,
coalesce(vr.name,vrr.name),
substring(pat.lastname,1,1)|| lower(substring(pat.lastname,2)) as lastname,
substring(pat.firstname,1,1)|| lower(substring(pat.firstname,2)) as fname , 
substring(pat.middlename,1,1)|| lower(substring(pat.middlename,2)) as mname
,case when
cast(to_char(to_date('${electionDate}','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(pat.birthday,'yyyy') as int)
 +(case when (cast(to_char(to_date('${electionDate}','dd.mm.yyyy'), 'mm') as int)
 -cast(to_char(pat.birthday, 'mm') as int)+(case when (cast(to_char(to_date('${electionDate}','dd.mm.yyyy'),'dd') as int)
 - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) = 18
 then to_char(pat.birthday,'dd.mm.yyyy')  else to_char(pat.birthday,'yyyy') end

,adr.fullname
 ||''||case when pat.houseNumber is not null and pat.houseNumber!='' then ', д. '||pat.houseNumber else '' end ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ', К'|| pat.houseBuilding else '' end||case when pat.flatNumber is not null and pat.flatNumber!='' then ', кв.'|| pat.flatNumber else '' end
 as adr
,pat.passportseries||' '||pat.passportnumber as passport
 from medcase sls
left join medcase slo on slo.parent_id=sls.id and slo.transferdepartment_id is null
left join mislpu ml on ml.id=slo.department_id
left join patient pat on pat.id=sls.patient_id
left join address2 adr on adr.addressid=pat.address_addressid
left join vocrayon vr on vr.id=pat.rayon_id
left join vocrayon vrr on vrr.id=pat.realrayon_id
left join addresstype vat on vat.id=adr.type_Id
left join OMC_OKSM nat on nat.id=pat.nationality_id
where sls.dischargetime is null and sls.dtype='HospitalMedCase'
and sls.deniedhospitalizating_id is null
and slo.dtype='DepartmentMedCase'
and cast(to_char(to_date('${electionDate}','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(pat.birthday,'yyyy') as int)
 +(case when (cast(to_char(to_date('${electionDate}','dd.mm.yyyy'), 'mm') as int)
 -cast(to_char(pat.birthday, 'mm') as int)+(case when (cast(to_char(to_date('${electionDate}','dd.mm.yyyy'),'dd') as int)
 - cast(to_char(pat.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) >= 18
 and (pat.nationality_id is null or nat.voc_code='643')
${departmentSql} ${addSql}
 order by ml.shortname,ml.name,pat.lastname,pat.firstname,pat.middlename,pat.birthday

    
    " />
   <msh:sectionTitle>
    
    	    <form action="print-stac_elections.do" method="post" target="_blank">
	    Реестр для выборов
	    <input type='hidden' name="sqlText" id="sqlText" value="${journal_priem_sql}"> 
	    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Реестр для выборов">
	    <input type='hidden' name="sqlColumn" id="sqlColumn" value="${groupName}">
	    <input type='hidden' name="s" id="s" value="PrintService"><input type='hidden' name="isReportBase" id="isReportBase" value="${isReportBase}">
	    <input type='hidden' name="m" id="m" value="printNativeQuery">
	    <input type="submit" value="Печать"> 
	    </form>     
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table printToExcelButton="Сохранить в excel" name="journal_priem" action="entityParentView-stac_ssl.do" idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Район" property="4"/>
      <msh:tableColumn columnName="Фамилия" property="5"/>
      <msh:tableColumn columnName="Имя" property="6" />
      <msh:tableColumn columnName="Отчество" property="7" />
      <msh:tableColumn columnName="Год рождения" property="8" />
      <msh:tableColumn columnName="Адрес" property="9" />
      <msh:tableColumn columnName="Паспортные данные" property="10" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    
     <script type='text/javascript'>

    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_elections.do' ;
    }

    
    </script>

   </tiles:put>  
</tiles:insert>

