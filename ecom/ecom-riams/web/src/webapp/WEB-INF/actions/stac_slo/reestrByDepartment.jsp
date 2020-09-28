<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title  mainMenu="StacJournal">Журнал обращений по отделению</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_reestrByDepartment"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/stac_reestrByDepartment.do" defaultField="department" disableFormDataConfirm="true" method="GET" >
    <msh:panel >
      <msh:row >
        <msh:separator label="Параметры поиска" colSpan="7"  />
      </msh:row>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
	      <msh:row>
	      	<msh:autoComplete property="department" vocName="lpu" label="Отделение" fieldColSpan="6" horizontalFill="true"/>
	      </msh:row>
      </msh:ifInRole>
      <msh:row >
        <msh:checkBox property="dischargeIs" label="Искать по дате выписки"  />
        <msh:textField property="dateBegin" label="Период с"  />
        <msh:textField property="dateEnd" label="по"  />
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
           <td>
            <input type="submit" onclick="print()" value="Печать" />
          </td>
                  </msh:row>

    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    Long department = (Long)request.getAttribute("department") ;
    if (date!=null && !date.equals("") && department!=null && department.intValue()>0 )  {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска обращений в отделение  ${departmentInfo}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select sls.id,m.dateStart as mdateStart,m.dateFinish,m.transferDate,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday,sc.code as sccode
    ,vas.name as vasname,vbst.name as vbstname,vss.name as vssname
    ,case when m.prevMedCase_id is not null then 'Да' else 'Нет' end,vhr.name,sls.dateStart as slsdateStart
    from medCase m 
    
    left join MedCase as sls on sls.id = m.parent_id
    left join BedFund bf on m.bedfund_id=bf.id
    left join VocBedSubType vbst on vbst.id=bf.bedSubType_id
    left join VocServiceStream vss on vss.id=bf.serviceStream_id
    left join VocHospitalizationResult vhr on vhr.id=sls.result_id
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left outer join Patient pat on m.patient_id = pat.id 
    left join VocAdditionStatus vas on pat.additionStatus_id=vas.id
    where m.DTYPE='DepartmentMedCase' and m.department_id='${department}'  and m.${dateSearch}  between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${param.dateEnd}','dd.mm.yyyy')
    order by vbst.name, m.${dateSearch}
    "  />
    <input type="button" value="Печать экс. карт по выбранным ИБ" onclick="printExpCard('stac_expcards_empty')">
    <msh:table selection="multiply" name="journal_priem" action="entityParentView-stac_ssl.do" idField="1" >
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="7"  />
      <msh:tableColumn columnName="Доп.статус" property="8"/>
      <msh:tableColumn columnName="Перевод из др. отд." property="11"/>
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5"  />
      <msh:tableColumn columnName="Год рождения" property="6"  />
      <msh:tableColumn columnName="Дата поступления в стационар" property="13"  />
      <msh:tableColumn columnName="Дата поступления в отделение" property="2"  />
      <msh:tableColumn columnName="Дата перевода" property="4"  />
      <msh:tableColumn columnName="Дата выписки" property="3"  />
      <msh:tableColumn columnName="Тип коек" property="9"/>      
      <msh:tableColumn columnName="Поток обслуживания" property="10"/>  
      <msh:tableColumn columnName="Результат госпитализации" property="12"/>  
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>

    <script type='text/javascript'>

    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_reestrByDepartment.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='stac_print_reestrByDepartment.do' ;
    }
    </script>
  </tiles:put>
   <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function printExpCard(aFile) {
            	var ids = theTableArrow.getInsertedIdsAsParams("id","journal_priem") ;
            	if(ids) {
            		//alert(ids) ;
            		window.location = 'print-'+aFile+'.do?multy=1&m=printBillings&s=HospitalPrintService1&'+ids ;
            		
            	} else {
            		alert("Нет выделенных случаев");
            	}
            	
            }
       </script>
   </tiles:put>  
</tiles:insert>

