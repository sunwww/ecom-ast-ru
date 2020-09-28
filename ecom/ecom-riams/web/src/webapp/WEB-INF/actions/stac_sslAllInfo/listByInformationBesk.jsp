<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
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
  	<tags:stac_journal currentAction="stac_journalByInformationBesk"/>
  </tiles:put>
  <tiles:put name="body" type="string">    
	    <msh:form action="/stac_journalByInformationBesk.do" defaultField="lastname" disableFormDataConfirm="true" method="GET">
	    <msh:panel  >
      	  	<msh:hidden property="checkedDischarge" />
      	  	<msh:hidden property="checkedDischargeMonth" />
      	  	<msh:hidden property="checkedDenied" />
	      <msh:row >
	        <msh:separator label="Параметры поиска" colSpan="7" />
	      </msh:row>
	      <msh:row>
	      	<msh:textField property="lastname"  label="Фамилия Имя Отчество" size="60" horizontalFill="true" fieldColSpan="6"/>
	      </msh:row>
	      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/InformationBesk">
	      <msh:row>
	      	<msh:separator label="Параметры для выписки" colSpan="6"/>
	      </msh:row>
      	  <msh:row>
      	  	<msh:textField property="dischargeDate" label="Дата выписки"/>
      	  	<msh:checkBox property="isSearchDenied" label="Отображать отказы от госпитализаций"/>
      	  </msh:row>
      	  <msh:row>
      	    <msh:checkBox property="isSearchDischarge" label="Искать в выписанных"/>
      	    <msh:checkBox property="isSearchDischargeMonth" label="Искать в выписанных в течение месяца"/>
      	  
      	  </msh:row>
      	  
      	  </msh:ifInRole>
	      <msh:row>
	           <td align="left" colspan="2">
	            <input type="submit" value="Найти" />
	          </td>		      	
	      </msh:row>
		      
	    </msh:panel>
	    </msh:form>
	   
        <%
		    String lastname = (String)request.getParameter("lastname") ;
        	
		    if (lastname!=null && !lastname.equals("") )  {
		    	lastname = lastname.toUpperCase().trim() ;
		    	String[] s = lastname.split(" ") ;
		    	if (s.length>=1) {
		    		request.setAttribute("lastname","and pat.lastname like '"+s[0]+"%'") ;
		    	} else {
		    		request.setAttribute("lastname","") ;
		    	}
		    	if (s.length>=2) {
		    		request.setAttribute("firstname","and pat.firstname like '"+s[1]+"%'") ;
		    	} else {
		    		request.setAttribute("firstname","") ;
		    	}
		    	if (s.length>=3) {
		    		request.setAttribute("middlename","and pat.middlename like '"+s[2]+"%'") ;
		    	} else {
		    		request.setAttribute("middlename","") ;
		    	}
		    	String isDenied = (String)request.getParameter("checkedDenied") ;
		    	String isDis = (String)request.getParameter("checkedDischarge") ;
		    	String isDisMonth = (String)request.getParameter("checkedDischargeMonth") ;
		    	if (isDis!=null & isDis.equals("on")) {
		    		request.setAttribute("dateFinish","") ;
		    	} else if (isDisMonth!=null & isDisMonth.equals("on")) {
		    		Calendar cal = Calendar.getInstance() ;
		    		cal.add(Calendar.MONTH, -1) ;
		    		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		    		String cur30 = format.format(cal.getTime()) ;
		    		request.setAttribute("dateFinish"," and (sls.dateFinish is null or sls.dateFinish>=to_date('"+cur30+"','dd.mm.yyyy'))") ;
		    	} else{
		    		request.setAttribute("dateFinish"," and sls.dateFinish is null ") ;
		    	}
		    	if (isDenied!=null & isDenied.equals("on")) {
		    		request.setAttribute("isDenied","") ;
		    	} else {
		    		request.setAttribute("isDenied"," and sls.deniedHospitalizating_id is null") ;
		    	}
		    	
    	%>
    <msh:section>
    <msh:sectionTitle>Поиск по поступившим в стационар</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery  name="staclist" nativeSql="select sls.id
    ,sls.dateStart,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
    ,pat.birthday,coalesce(sc.code,vdh.name),d.name as dname  ,to_char(sls.dateFinish,'dd.mm.yyyy')
    ||' '||coalesce(cast(sls.dischargeTime as varchar(5)),'') as slsdateFinish
    , vht.name as vhtname 
    from medCase sls 
    left join VocHospType vht on vht.id=sls.hospType_id
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left join MisLpu d on d.id=sls.department_id
    left join Patient pat on sls.patient_id = pat.id 
    left join VocDeniedHospitalizating vdh on vdh.id=sls.deniedHospitalizating_id
    where (sls.DTYPE='HospitalMedCase' 
      ${dateFinish} 
      ${isDenied} or sls.DTYPE='HospitalMedCase' and sls.dateStart between current_date-1 and current_date 
      and sls.deniedHospitalizating_id is not null)
      ${lastname} ${firstname} ${middlename} and (sls.noActuality is null or sls.noActuality='0')"
     />
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/InformationBesk">
	    <msh:table selection="multiply"  
	    viewUrl="entityParentShortList-stac_slo.do" name="staclist" 
	    action="entitySubclassView-mis_medCase.do" idField="1" 
	   >
                        <msh:tableNotEmpty >
                                <msh:toolbar>
                        <msh:row>
                            <th colspan='12'>
                                    <a href='javascript:updateDischarge()'> выписать</a>
                            </th>
                          </msh:row>
                                </msh:toolbar>
                    </msh:tableNotEmpty>
	      <msh:tableColumn property="sn" columnName="#"/>
	      <msh:tableColumn columnName="Стат.карта" property="5" />
	      <msh:tableColumn property="8" columnName="Тип стационара"/>
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" />
	      <msh:tableColumn columnName="Год рождения" property="4" />
	      <msh:tableColumn columnName="Дата поступления" property="2" />
	      <msh:tableColumn columnName="Направлен в отделение" property="6" />
	      <msh:tableColumn columnName="Дата выписки" property="7" />
	    </msh:table>
    </msh:ifInRole>
    <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/InformationBesk">
	    <msh:table disableKeySupport="true" 
	    viewUrl="entityParentShortList-stac_slo.do" name="staclist" action="entitySubclassView-mis_medCase.do" idField="1">
	      <msh:tableColumn property="sn" columnName="#"/>
	      <msh:tableColumn columnName="Стат.карта" property="5" />
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" />
	      <msh:tableColumn columnName="Год рождения" property="4" />
	      <msh:tableColumn columnName="Дата поступления" property="2" />
	      <msh:tableColumn columnName="Направлен в отделение" property="6" />
	    </msh:table>
    </msh:ifNotInRole>
    </msh:sectionContent>

    <msh:sectionTitle>Поиск по зарегистрированным в отделениях</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="datelist" nativeSql="select m.id
    ,m.dateStart,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
    ,pat.birthday,sc.code,d.name as dname  from medCase m 
    left join MedCase as sls on sls.id = m.parent_id 
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left join MisLpu d on d.id=m.department_id
    left outer join Patient pat on m.patient_id = pat.id 
    where m.DTYPE='DepartmentMedCase' 
    and m.transferDate is null and m.dateFinish is null ${lastname} ${firstname} ${middlename} and (sls.noActuality is null or cast(sls.noActuality as integer)=0)"
     />
    <msh:table disableKeySupport="true" name="datelist" action="entitySubclassView-mis_medCase.do"
    viewUrl="entitySubclassShortView-mis_medCase.do" 
    
    idField="1">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="5" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" />
      <msh:tableColumn columnName="Год рождения" property="4" />
      <msh:tableColumn columnName="Дата поступления" property="2" />
      <msh:tableColumn columnName="Отделение" property="6" />
    </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } %>

  </tiles:put>
  <tiles:put type="string" name="javascript">
  	<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
  	<script type="text/javascript">
  	if ($('checkedDenied').value!=null && $('checkedDenied').value=="off") {
  		$('isSearchDenied').checked = false ;
  	}
  	if ($('checkedDischarge').value!=null && $('checkedDischarge').value=="off") {
  		$('isSearchDischarge').checked = false ;
  	}
  	if ($('checkedDischargeMonth').value!=null && $('checkedDischargeMonth').value=="off") {
  		$('isSearchDischargeMonth').checked = false ;
  	}
  	if ($('isSearchDischarge')) {
  		eventutil.addEventListener($('isSearchDischarge'), "change", 
  	
  		  	function() {
  		  		if ($('isSearchDischarge').checked) {
  		  		$('checkedDischarge').value="on" ;
  		  		$('checkedDischargeMonth').value="off" ;
  		  		$('isSearchDischargeMonth').checked=false ;
  		  		} else {
  		  		$('checkedDischarge').value="off" ;
  		  		}
  		  	}) ;
  	}
  	if ($('isSearchDischargeMonth')) {
  		eventutil.addEventListener($('isSearchDischargeMonth'), "change", 
  	
  		  	function() {
  		  		if ($('isSearchDischargeMonth').checked) {
  		  		$('checkedDischargeMonth').value="on" ;
  		  		$('checkedDischarge').value="off" ;
  		  		$('isSearchDischarge').checked=false ;
  		  		} else {
  		  		$('checkedDischargeMonth').value="off" ;
  		  		}
  		  	}) ;
  	}
  	if ($('isSearchDenied')) {
  		eventutil.addEventListener($('isSearchDenied'), "change", 
  	
  		  	function() {
  		  		if ($('isSearchDenied').checked) {
  		  		$('checkedDenied').value="on" ;
  		  		$('checkedDischarge').value="off" ;
  		  		$('isSearchDischarge').checked=false ;
  		  		} else {
  		  		$('checkedDenied').value="off" ;
  		  		}
  		  	}) ;
  	}
  		function updateDischarge() {
  			var ids = theTableArrow.getInsertedIdsAsParams("","staclist") ;
            if (ids) {
            	if ($('dischargeDate').value=="") {
            		alert('Не указана дата выписки') ;
            		$('dischargeDate').focus() ;
            		$('dischargeDate').select() ;
            	} else {
            		HospitalMedCaseService.updateDischargeDateByInformationBesk(ids,$('dischargeDate').value, {
                		callback: function(aResult) {
                			window.document.forms[0].submit()  ;
                		}
                	});
            		//$('dischargeDate').value,ids;
            	}
            	
            } else {
                alert("Нет выделенных данных");
            }
  		}
  	</script>
  </tiles:put>
</tiles:insert>