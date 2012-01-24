<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Журнал обращений по отделению</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_journalByInformationBesk"/>
  </tiles:put>
  <tiles:put name="body" type="string">    
	    <msh:form action="/stac_journalByInformationBesk.do" defaultField="lastname" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
	    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff"  >
      	  	<msh:hidden property="checkedDischarge" />
	      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9" >
	        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
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
      	  	<msh:checkBox property="isSearchDischarge" label="Искать в выписанных"/>
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
		    	String isDis = (String)request.getParameter("checkedDischarge") ;
		    	if (isDis!=null & isDis.equals("on")) {
		    		request.setAttribute("dateFinish","") ;
		    	} else {
		    		request.setAttribute("dateFinish"," and sls.dateFinish is null ") ;
		    	}
    	%>
    <msh:section>
    <msh:sectionTitle>Поиск по поступившим в стационар</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery  name="staclist" nativeSql="select sls.id
    ,sls.dateStart,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename
    ,pat.birthday,sc.code,d.name as dname  ,sls.dateFinish as slsdateFinish from medCase sls 
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left join MisLpu d on d.id=sls.department_id
    left outer join Patient pat on sls.patient_id = pat.id 
    where sls.DTYPE='HospitalMedCase' 
      ${dateFinish} ${lastname} ${firstname} ${middlename} and (sls.noActuality is null or cast(sls.noActuality as integer)=0)"
     guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/InformationBesk">
	    <msh:table selection="multiply"  
	    viewUrl="entityParentShortList-stac_slo.do" name="staclist" 
	    action="entitySubclassView-mis_medCase.do" idField="1" 
	    guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
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
	      <msh:tableColumn columnName="Стат.карта" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
	      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
	      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
	      <msh:tableColumn columnName="Направлен в отделение" property="6" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
	      <msh:tableColumn columnName="Дата выписки" property="7" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
	    </msh:table>
    </msh:ifInRole>
    <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Discharge/InformationBesk">
	    <msh:table disableKeySupport="true" 
	    viewUrl="entityParentShortList-stac_slo.do" name="staclist" action="entitySubclassView-mis_medCase.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
	      <msh:tableColumn property="sn" columnName="#"/>
	      <msh:tableColumn columnName="Стат.карта" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
	      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
	      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
	      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
	      <msh:tableColumn columnName="Направлен в отделение" property="6" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
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
     guid="81cbfcaf-6737-4785-bac0-6691c6e6b501" />
    <msh:table disableKeySupport="true" name="datelist" action="entitySublassView-stac_ssl.do" idField="1" guid="be9cacbc-17e8-4a04-8d57-bd2cbbaeba30">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="4" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Отделение" property="6" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
    </msh:table>
    </msh:sectionContent>

    </msh:section>
    <% } %>

  </tiles:put>
  <tiles:put type="string" name="javascript">
  	<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
  	<script type="text/javascript">
  	if ($('checkedDischarge').value!=null && $('checkedDischarge').value=="off") {
  		$('isSearchDischarge').checked = false ;
  	}
  	if ($('isSearchDischarge')) {
  		eventutil.addEventListener($('isSearchDischarge'), "change", 
  	
  		  	function() {
  		  		if ($('isSearchDischarge').checked) {
  		  		$('checkedDischarge').value="on" ;
  		  		} else {
  		  		$('checkedDischarge').value="off" ;
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