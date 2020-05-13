<%@page import="ru.nuzmsh.web.tags.helper.RolesHelper"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Поиск по номеру стат.карты случая лечения в стационаре</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    	<tags:stac_journal currentAction="stac_findSlsByStatCard"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
            <msh:form action="/stac_findSlsByStatCard.do" defaultField="number" method="GET" disableFormDataConfirm="true">
                <msh:panel>
				    <msh:row>
					    <td colspan="1" class='label'>
					    <input type="hidden" name="onlyYearH" id="onlyYearH">
					    <input type='checkbox' name='onlyYear' id='onlyYear' onClick='javascript:document.location.href="stac_findSlsByStatCard.do?number="+$("number").value+"&exactMatch="+$("exactMatch").checked+"&onlyYear="+$("onlyYear").checked'>
					    </td>
					    <td colspan=3 class='onlyYear'>
						    <label id='onlyYearLabel' for="onlyYear"> Отображать стат.карты за текущий год</label>
					    
					    </td>
				    </msh:row>
				    <msh:row>
					    <td colspan="1" class='label'>
					    <input type="hidden" name="exactMatchH" id="exactMatchH">
					    <input type='checkbox' name='exactMatch' id='exactMatch' onClick='javascript:document.location.href="stac_findSlsByStatCard.do?number="+$("number").value+"&exactMatch="+$("exactMatch").checked+"&onlyYear="+$("onlyYear").checked'>
					    </td>
					    <td colspan=3 class='onlyYear'>
						    <label id='exactMatchLabel' for="exactMatch"> Полное совпадение номера</label>
					    
					    </td>
				    </msh:row>
                    <msh:row>
                        <msh:textField property="number" label="Номер стат.карты" size="30"/>
                        <td><input type='submit' value='Найти'></td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            
        <%  if(request.getParameter("number") != null &&request.getParameter("number").length()>0) {  %>
        <%
        if (RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/FindOnlyTheirCard/Enable", request) 
        		&& ! RolesHelper.checkRoles("/Policy/Mis/MedCase/Stac/Ssl/FindOnlyTheirCard/Disable", request)) {
	        String login = LoginInfo.find(request.getSession(true)).getUsername() ;
	        request.setAttribute("curLeft", "left join MedCase slo on slo.parent_id = m.id") ;
	        request.setAttribute("curWhere", " and slo.ownerFunction_id = (select swf.id from secuser su left join workfunction swf on swf.secuser_id=su.id where su.login='"+login+"')") ;
	        request.setAttribute("curWhere", " and slo.department_id = (select sw.lpu_id from secuser su left join workfunction swf on swf.secuser_id=su.id left join worker sw on sw.id=swf.worker_id where su.login='"+login+"')") ;
        }
        %>
            <msh:section title='Результат поиска'>
            	<ecom:webQuery name="list" 
            		nativeSql="select m.id as mid,ss.year as ssyear, 
            		coalesce(ss.code,'') || case when (ss.archivecase ='0' or ss.archivecase is not null) then ' (в архиве)'  else '' end as  achiv
            			, case 
            				when (coalesce(m.dateFinish,CURRENT_DATE)-m.dateStart)=0 then 1 
            				when vht.code='DAYTIMEHOSP' then ((coalesce(m.dateFinish,CURRENT_DATE)-m.dateStart)+1) 
            				else (coalesce(m.dateFinish,CURRENT_DATE)-m.dateStart)
            				end as countDays
            			, case when (select count(id) from medcase d where d.parent_id=m.id)>0 then 'Да' else 'Нет' end as sloIs 
            			, p.lastname||' '||p.firstname ||' '|| p.middlename ||' гр.'||to_char(p.birthday,'DD.MM.YYYY') as patInfo
            			, md.name as mdname,case when cast(m.emergency as int)=1 then 'Да' else 'Нет' end  as memergency
            			, vdh.name as vdhname,m.dateStart as mdateStart,m.dateFinish as mdateFinish
            			 from statisticstub ss 
            			left join medcase m on m.statisticstub_id=ss.id
            			left join VocHospType vht on vht.id=m.hospType_id
            			left join Patient p on p.id=m.patient_id
            			left join MisLpu md on md.id=m.department_id
            			left join VocDeniedHospitalizating vdh on vdh.id=m.deniedHospitalizating_id
            			${curLeft}
            			where ss.dtype='StatisticStubExist' 
            			and ${exactMatchS1}${param.number}${exactMatchS2} ${onlyYearS}
            			${curWhere}
            			group by m.id ,ss.year , ss.code 
            			, m.dateFinish,m.dateStart,vht.code
            			, p.lastname,p.firstname , p.middlename ,p.birthday
            			, md.name ,m.emergency
            			, vdh.name, ss.archivecase 
            			order by ss.year,ss.code
            			"
            	/>
                <msh:table viewUrl="entityShortView-stac_ssl.do" name="list" action="entityParentView-stac_ssl.do" idField="1" disableKeySupport="true">
				      <msh:tableColumn columnName="Год" property="2" />
				      <msh:tableColumn columnName="Стат.карта" property="3" />
				      <msh:tableColumn columnName="К/дни" property="4" />
				      <msh:tableColumn columnName="Наличие СЛО" property="5" />
				      <msh:tableColumn columnName="Пациент" property="6" />
				      <msh:tableColumn columnName="Отделение" property="7" />
				      <msh:tableColumn columnName="Дата поступления" property="10"/>
				      <msh:tableColumn columnName="Дата выписки" property="11"/>
				      <msh:tableColumn columnName="Экстренность" property="8"/>
				      <msh:tableColumn columnName="Отказ от госпитализации" property="9" />
                </msh:table>
                <%-- 
                <msh:table name="list" action="entityParentView-stac_ssl.do" idField="id" disableKeySupport="true">
				      <msh:tableColumn columnName="Стат.карта" property="statCardNumber" />
				      <msh:tableColumn columnName="К/дни" property="daysCount" />
				      <msh:tableColumn columnName="Наличие СЛО" property="isDepartmentMedCase" />
				      <msh:tableColumn columnName="Пациент" property="patientInfo" />
				      <msh:tableColumn columnName="Кем открыт" property="username" />
				      <msh:tableColumn columnName="Отделение" property="departmentInfo" />
				      <msh:tableColumn columnName="Экстренность" property="emergency" />
				      <msh:tableColumn columnName="Отказ от госпитализации" property="deniedHospitalizatingInfo" />
                </msh:table>
                --%>
            </msh:section>
        <%} else { %>
        <i>Выберите параметры для поиска и нажмите найти</i>
        <%} %>
    </tiles:put>
  <tiles:put name="javascript" type="string">
    <script type="text/javascript">
    // <![CDATA[//
    	
    	$('number').focus() ;
    	$('number').select() ;

  		if ((+'${onlyYear}')==1) {
    		$('onlyYear').checked='checked' ;
    		$('onlyYearH').value='1' ;
    	} else {
    		$('onlyYear').checked='' ;
    		$('onlyYearH').value='0' ;
    	}
  		if ((+'${exactMatch}')==1) {
    		$('exactMatch').checked='checked' ;
    		$('exactMatchH').value='1' ;
    	} else {
    		$('exactMatch').checked='' ;
    		$('exactMatchH').value='0' ;
    	}
  		//]]
  	</script>
  </tiles:put>

</tiles:insert>