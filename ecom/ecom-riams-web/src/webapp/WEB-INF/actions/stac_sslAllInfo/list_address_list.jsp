<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal" title="Адресный листок"></msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
    	<tags:stac_journal currentAction="stac_addressList"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/stac_address_list.do" defaultField="dischargeIs" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="hospType" fieldColSpan="2" 
      		horizontalFill="true" label="Тип стационара"
      		vocName="vocHospType"
      		/>
      	<msh:autoComplete property="pigeonHole" fieldColSpan="2" 
      		horizontalFill="true" label="Приемник"
      		vocName="vocPigeonHole"
      		/>
      </msh:row>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <msh:checkBox property="dischargeIs" label="Искать по дате выписки" guid="f5458f6-b5b8-4d48-a045-ba7b9f875245" />
        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
      <msh:separator label="Общая информация при печати" colSpan="12"/>
       <msh:row>
                 <msh:autoComplete property="department" vocName="lpu" label="ЛПУ" 
                 	horizontalFill="true" fieldColSpan="12"/>
     	</msh:row>
       <msh:row>
                 <msh:autoComplete property="spec" vocName="workFunction" label="Ответственный" 
                 	horizontalFill="true" fieldColSpan="12"/>
     	</msh:row>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    String dateEnd = (String)request.getAttribute("dateEnd") ;
    if (dateEnd==null || dateEnd.equals("")) dateEnd=(String)request.getAttribute("dateBegin") ;
    request.setAttribute("dateEnd", dateEnd) ;
    if (date!=null && !date.equals("")) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска обращений за период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="select m.id
    ,m.dateStart,m.dateFinish
    ,m.username
    ,stat.code
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
    ,pat.birthday
    ,dep.name
    ,case when cast(m.emergency as int)=1 then 'Да' else 'Нет' end as emergency
    ,m.noActuality
    , case when kinsman_id is null then 'Нет' else 'Есть' End as kinsman  
    from MedCase m 
    left outer join MisLpu dep on m.department_id = dep.id
	left outer join Patient pat on m.patient_id = pat.id  
	left outer join StatisticStub stat on m.statisticstub_id=stat.id 
	left outer join MisLpu lpu on m.department_id = lpu.id 
	left join Omc_Oksm ok on pat.nationality_id=ok.id
	where m.DTYPE='HospitalMedCase' 
	and m.${dateSearch} between '${dateBegin}' and '${dateEnd}'
	${hospType} and m.deniedHospitalizating_id is null and (ok.voc_code is null or ok.voc_code='643')
	${pigeonHole}
	order by pat.lastname
	" guid="ac83420f-43a0-4ede-b576-394b4395a23a" />
    <msh:table viewUrl="entityShortView-stac_ssl.do" selection="multiply" name="datelist" idField="1" action="entityView-stac_ssl.do" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
                    <msh:tableNotEmpty>
                                <msh:toolbar>
                        <msh:row>
                            <th colspan='12'>
                                    Печать:<a href='javascript:printAdmission()'> адрес. листков прибытия</a>
                                    <a href='javascript:printDischarge()'>адрес. листков убытиях</a>
                                    <a href='javascript:printAdmission1()'>реестр для листков прибытия</a>
                                    <a href='javascript:printDischarge1()'>реестр для листков убытиях</a>
                            </th>
                          </msh:row>
                                </msh:toolbar>
                    </msh:tableNotEmpty>
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn property="11" columnName="Представитель"/>                  
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="6" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Год рождения" property="7" guid="fc223a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Отделение" property="8" guid="e9g8f5-8b9e-4a3e-966f-4d435g76bbc96" />
      <msh:tableColumn columnName="Экстренность" property="9" guid="e9g8f5-8b9e-4a3e-966f-4d435g76bbc96" />
      <msh:tableColumn columnName="Дата открытия" property="2" guid="f6523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Кем открыт" property="4" guid="35347247-b552-4154-a82a-ee484a1714ad" />
      <msh:tableColumn columnName="Стат.карта" property="5" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Дата закрытия" property="3" guid="e98f5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Недействителен" property="10" guid="e98f5-8b9e-4a3e-966f-4d43576bbc96" />
    </msh:table>

    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
    <script type='text/javascript'>
	    //new dateutil.DateField($('dateBegin'));
	    //new dateutil.DateField($('dateEnd'));
	    function printAdmission() {
	    	
            var ids = theTableArrow.getInsertedIdsAsParams("","datelist") ;
            if (ids) {
                window.location = 'print-listAdmission.do?s=HospitalPrintService&m=printAddressSheetArrival&id='+ ids +'&spec='+$('spec').value+'&department='+$('department').value+'&dateBegin='+$('dateBegin').value+'&dateEnd='+$('dateEnd').value;
                
            } else {
                alert("Нет выделенных данных");
            }

	    }
	    function printDischarge() {
	    	
            var ids = theTableArrow.getInsertedIdsAsParams("","datelist") ;
            if (ids) {
                window.location = 'print-listDischarge.do?s=HospitalPrintService&m=printAddressSheetArrival&id='+ ids+'&spec='+$('spec').value+'&department='+$('department').value+'&dateBegin='+$('dateBegin').value+'&dateEnd='+$('dateEnd').value;
                
            } else {
                alert("Нет выделенных данных");
            }

	    }
	    function printAdmission1() {
	    	
            var ids = theTableArrow.getInsertedIdsAsParams("","datelist") ;
            if (ids) {
                window.location = 'print-reestrAdmission.do?s=HospitalPrintService&m=printAddressSheetArrival&id='+ ids +'&spec='+$('spec').value+'&department='+$('department').value+'&dateBegin='+$('dateBegin').value+'&dateEnd='+$('dateEnd').value;
                
            } else {
                alert("Нет выделенных данных");
            }

	    }
	    function printDischarge1() {
	    	
            var ids = theTableArrow.getInsertedIdsAsParams("","datelist") ;
            if (ids) {
                window.location = 'print-reestrDischarge.do?s=HospitalPrintService&m=printAddressSheetArrival&id='+ ids+'&spec='+$('spec').value+'&department='+$('department').value+'&dateBegin='+$('dateBegin').value+'&dateEnd='+$('dateEnd').value;
                
            } else {
                alert("Нет выделенных данных");
            }

	    }
    </script>
    
  </tiles:put>
</tiles:insert>

