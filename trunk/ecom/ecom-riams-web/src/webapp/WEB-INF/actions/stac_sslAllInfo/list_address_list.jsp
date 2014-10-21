<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal" title="Адресный листок"></msh:title>
  </tiles:put>
  <tiles:put name="style" type="string">
      <style type="text/css">td.tdradio {
            cursor: pointer;
        }</style>
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
      	<msh:autoComplete property="pigeonHole" fieldColSpan="3" 
      		horizontalFill="true" label="Приемник"
      		vocName="vocPigeonHole"
      		/>
      </msh:row>
      <msh:row>
      	<msh:autoComplete property="registrationType" fieldColSpan="6" 
      		horizontalFill="true" label="Отделение"
      		vocName="lpu"
      		/>
      </msh:row>
      <msh:row>
        <td class="label" title="Печать (typeStatus)" colspan="1"><label for="typeStatusName" id="typeStatusLabel">Печать:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeStatus" value="1">  всех
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeStatus" value="2">  представителей
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeStatus" value="2">  без представителей
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Просмотр (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Просмотр:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="1">  адресные листки
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeView" value="2">  иногор+иностран
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeView" value="3">  иностран
        </td>
      </msh:row>      
      <msh:row>
        <td class="label" title="Искать по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="1"> поступления  
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
      </msh:row>      
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
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
     		<msh:autoComplete property="serviceStream" vocName="vocServiceStream" label="Поток обслуживания"
     			horizontalFill="true" fieldColSpan="12"
     		/>
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
    String typeView = (String) request.getAttribute("typeView") ;
    if (dateEnd==null || dateEnd.equals("")) dateEnd=(String)request.getAttribute("dateBegin") ;
    ActionUtil.setParameterFilterSql("serviceStream", "m.serviceStream_id", request) ;
    request.setAttribute("dateEnd", dateEnd) ;
    if (date!=null && !date.equals("")) {
    	if (typeView!=null && typeView.equals("1")) {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска обращений для формирования адресных листков за период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch} ${printStatus} </msh:sectionTitle>
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
    , case when m.kinsman_id is null then 'Нет' else 'Есть' End as kinsman  
    from MedCase m 
    left outer join MisLpu dep on m.department_id = dep.id
	left outer join Patient pat on m.patient_id = pat.id  
	left outer join StatisticStub stat on m.statisticstub_id=stat.id 
	left outer join MisLpu lpu on m.department_id = lpu.id 
	left join Omc_Oksm ok on pat.nationality_id=ok.id
	where m.DTYPE='HospitalMedCase' 
	and m.${dateSearch} between '${dateBegin}' and '${dateEnd}'
	${hospType} and m.deniedHospitalizating_id is null and (ok.voc_code is null or ok.voc_code='643')
	${pigeonHole} ${status} ${department} ${serviceStreamSql}
	order by pat.lastname
	" guid="ac83420f-43a0-4ede-b576-394b4395a23a" />
    <msh:table viewUrl="entityShortView-stac_ssl.do" selection="multiply" name="datelist" idField="1" action="entityView-stac_ssl.do" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
                    <msh:tableNotEmpty>
                                <msh:toolbar>
                        <msh:row>
                            <th colspan='12'>
                                    Печать:<a href='javascript:printList("listAdmission")'> адрес. листков прибытия</a>
                                    <a href='javascript:printList("listDischarge")'>адрес. листков убытиях</a>
                                    <a href='javascript:printList("reestrAdmission")'>реестр для листков прибытия</a>
                                    <a href='javascript:printList("reestrDischarge")'>реестр для листков убытиях</a>
                                    <a href='javascript:printList("reestrAddressPatient")'>реестр пациентов</a>
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
    <% } else if (typeView!=null && typeView.equals("2")) {
%>
<msh:section>
    <msh:sectionTitle>Результаты поиска обращений для формирования реестра по иногородним и иностранцам за период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch} ${printStatus} </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="select m.id
    ,m.dateStart,m.dateFinish
    ,m.username
    ,stat.code
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
    ,pat.birthday
    ,dep.name as depname
    ,case when cast(m.emergency as int)=1 then 'Да' else 'Нет' end as emergency
    ,m.noActuality
    , case when m.kinsman_id is null then 'Нет' else 'Есть' End as kinsman
    , ok.name as okname  
    from MedCase m 
    left outer join MisLpu dep on m.department_id = dep.id
	left outer join Patient pat on m.patient_id = pat.id  
	left outer join StatisticStub stat on m.statisticstub_id=stat.id 
	left outer join MisLpu lpu on m.department_id = lpu.id 
	left join Omc_Oksm ok on pat.nationality_id=ok.id
	left join Address2 adr on adr.addressid=pat.address_addressid
	where m.DTYPE='HospitalMedCase' 
	and m.${dateSearch} between '${dateBegin}' and '${dateEnd}'
	${hospType} and m.deniedHospitalizating_id is null and (ok.voc_code is not null and ok.voc_code != '643' or adr.kladr is not null and adr.kladr not like  '30%')
	${pigeonHole} ${status} ${serviceStreamSql}
	order by pat.lastname
	" guid="ac83420f-43a0-4ede-b576-394b4395a23a" />
    <msh:table viewUrl="entityShortView-stac_ssl.do" selection="multiply" name="datelist" idField="1" action="entityView-stac_ssl.do" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
                    <msh:tableNotEmpty>
                                <msh:toolbar>
                        <msh:row>
                            <th colspan='12'>
                                    Печать:<a href='javascript:printList("reestrInogByHospital")'> реестра</a>
                            </th>
                          </msh:row>
                                </msh:toolbar>
                    </msh:tableNotEmpty>
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Гражданство" property="12" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
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
    <% } else if (typeView!=null && typeView.equals("3")) {
%>
<msh:section>
    <msh:sectionTitle>Результаты поиска обращений для формирования реестра по иностранцам за период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch} ${printStatus} </msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="select m.id
    ,m.dateStart,m.dateFinish
    ,m.username
    ,stat.code
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
    ,pat.birthday
    ,dep.name as depname
    ,case when cast(m.emergency as int)=1 then 'Да' else 'Нет' end as emergency
    ,m.noActuality
    , case when m.kinsman_id is null then 'Нет' else 'Есть' End as kinsman
    , ok.name as okname  
    , vss.name as vssname    
    from MedCase m 
    left outer join MisLpu dep on m.department_id = dep.id
	left outer join Patient pat on m.patient_id = pat.id  
	left outer join StatisticStub stat on m.statisticstub_id=stat.id 
	left outer join MisLpu lpu on m.department_id = lpu.id 
	left join Omc_Oksm ok on pat.nationality_id=ok.id
	left join Address2 adr on adr.addressid=pat.address_addressid
	left join VocServiceStream vss on vss.id=m.serviceStream_id
	where m.DTYPE='HospitalMedCase' 
	and m.${dateSearch} between '${dateBegin}' and '${dateEnd}'
	${hospType} and m.deniedHospitalizating_id is null and ok.voc_code is not null and ok.voc_code != '643'
	${pigeonHole} ${status} ${serviceStreamSql}
	order by pat.lastname
	" guid="ac83420f-43a0-4ede-b576-394b4395a23a" />
    <msh:table viewUrl="entityShortView-stac_ssl.do" selection="multiply" name="datelist" idField="1" action="entityView-stac_ssl.do" guid="d579127c-69a0-4eca-b3e3-950381d1585c">
                    <msh:tableNotEmpty>
                                <msh:toolbar>
                        <msh:row>
                            <th colspan='12'>
                                    Печать:<a href='javascript:printList("reestrInogByHospital")'> реестра</a>
                            </th>
                          </msh:row>
                                </msh:toolbar>
                    </msh:tableNotEmpty>
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Гражданство" property="12" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="6" guid="fc26523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Год рождения" property="7" guid="fc223a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Отделение" property="8" guid="e9g8f5-8b9e-4a3e-966f-4d435g76bbc96" />
      <msh:tableColumn columnName="Экстренность" property="9" guid="e9g8f5-8b9e-4a3e-966f-4d435g76bbc96" />
      <msh:tableColumn columnName="Дата открытия" property="2" guid="f6523a-eb9c-44bc-b12e-42cb7ca9ac5b" />
      <msh:tableColumn columnName="Кем открыт" property="4" guid="35347247-b552-4154-a82a-ee484a1714ad" />
      <msh:tableColumn columnName="Стат.карта" property="5" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Дата закрытия" property="3" guid="e98f5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Поток обслуживания" property="13" guid="e98f5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Недействителен" property="10" guid="e98f5-8b9e-4a3e-966f-4d43576bbc96" />
    </msh:table>

    </msh:sectionContent>
    </msh:section>
<%    	
    	}}
    else {%>
    	<i>Нет данных </i>
    	<% }   %>
    
    <script type='text/javascript'>
	    //new dateutil.DateField($('dateBegin'));
	    //new dateutil.DateField($('dateEnd'));
	 checkFieldUpdate('typeStatus','${typeStatus}',1) ;
     checkFieldUpdate('typeView','${typeView}',1) ;
     checkFieldUpdate('typeDate','${typeDate}',1) ;
     
   
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = +chk.length ;
    	var aDefault = +aDefault ;
    	var aValue= +aValue ;
    	if (aValue<1 || aValue>max) {
    		if (aDefault<1||aDefault>max) aDefault = max ;
    		chk[aDefault-1].checked='checked' ;
    	} else {
    		chk[aValue-1].checked='checked' ;
    	}
    }
	function printList(aFilename) {
		//listAdmission,listDischarge
    	var status = '${status}'; 
    	
        var ids = theTableArrow.getInsertedIdsAsParams("","datelist") ;
        if (ids) {
            window.location = 'print-'+aFilename+'.do?s=HospitalPrintService&m=printAddressSheetArrival&id='+ ids +'&spec='+$('spec').value+'&department='+$('department').value+'&dateBegin='+$('dateBegin').value+'&dateEnd='+$('dateEnd').value+"&status="+status;
            
        } else {
            alert("Нет выделенных данных");
        }
	}    /*
    function printAdmission() {
        	var status = '${status}'; 
	    	
            var ids = theTableArrow.getInsertedIdsAsParams("","datelist") ;
            if (ids) {
                window.location = 'print-listAdmission.do?s=HospitalPrintService&m=printAddressSheetArrival&id='+ ids +'&spec='+$('spec').value+'&department='+$('department').value+'&dateBegin='+$('dateBegin').value+'&dateEnd='+$('dateEnd').value+"&status="+status;
                
            } else {
                alert("Нет выделенных данных");
            }

	    }
	    function printDischarge() {
        	var status = '${status}'; 
	    	
            var ids = theTableArrow.getInsertedIdsAsParams("","datelist") ;
            if (ids) {
                window.location = 'print-listDischarge.do?s=HospitalPrintService&m=printAddressSheetArrival&id='+ ids+'&spec='+$('spec').value+'&department='+$('department').value+'&dateBegin='+$('dateBegin').value+'&dateEnd='+$('dateEnd').value+"&status="+status;
                
            } else {
                alert("Нет выделенных данных");
            }

	    }
	    function printAdmission1() {
	    	
        	var status = '${status}'; 
          var ids = theTableArrow.getInsertedIdsAsParams("","datelist") ;
            if (ids) {
                window.location = 'print-reestrAdmission.do?s=HospitalPrintService&m=printAddressSheetArrival&id='+ ids +'&spec='+$('spec').value+'&department='+$('department').value+'&dateBegin='+$('dateBegin').value+'&dateEnd='+$('dateEnd').value+"&status="+status;
                
            } else {
                alert("Нет выделенных данных");
            }

	    }
	    function printDischarge1() {
	    	
            var ids = theTableArrow.getInsertedIdsAsParams("","datelist") ;
            if (ids) {
            	var status = '${status}'; 
                window.location = 'print-reestrDischarge.do?s=HospitalPrintService&m=printAddressSheetArrival&id='+ ids+'&spec='+$('spec').value+'&department='+$('department').value+'&dateBegin='+$('dateBegin').value+'&dateEnd='+$('dateEnd').value+"&status="+status;
                
            } else {
                alert("Нет выделенных данных");
            }

	    }
	    function printInogReestr() {
	    	var ids = theTableArrow.getInsertedIdsAsParams("","datelist") ;
            if (ids) {
            	var status = '${status}'; 
                window.location = 'print-reestrInogByHospital.do?s=HospitalPrintService&m=printAddressSheetArrival&id='+ ids+'&spec='+$('spec').value+'&department='+$('department').value+'&dateBegin='+$('dateBegin').value+'&dateEnd='+$('dateEnd').value+"&status="+status;
                
            } else {
                alert("Нет выделенных данных");
            }
	    }*/
    </script>
    
  </tiles:put>
</tiles:insert>

