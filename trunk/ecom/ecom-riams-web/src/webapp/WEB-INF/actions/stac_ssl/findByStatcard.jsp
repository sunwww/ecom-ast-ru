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
					    <input type='checkbox' name='onlyYear' id='onlyYear' onClick='javascript:document.location.href="stac_findSlsByStatCard.do?number="+$("number").value+"&onlyYear="+this.checked'>
					    </td>
					    <td colspan=3 class='onlyYear'>
						    <label id='onlyYearLabel' for="onlyYear"> Отображать стат.карты за текущий год</label>
					    
					    </td>
				    </msh:row>
                    <msh:row>
                        <msh:textField property="number" label="Номер стат.карты" size="30"/>
                        <td><input type='submit' value='Найти'></td>
                    </msh:row>
                </msh:panel>
            </msh:form>
            
        <%  if(request.getParameter("number") != null &&request.getParameter("number").length()>0) {  %>
            <msh:section title='Результат поиска'>
            	<ecom:webQuery name="list" 
            		nativeSql="select m.id as mid,ss.year as ssyear, ss.code as sscode
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
            			where ss.dtype='StatisticStubExist' 
            			and ss.code like '%${param.number}%' ${onlyYearS}
            			
            			"
            	/>
                <msh:table viewUrl="entityShortView-stac_ssl.do" name="list" action="entityParentView-stac_ssl.do" idField="1" disableKeySupport="true">
				      <msh:tableColumn columnName="Год" property="2" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
				      <msh:tableColumn columnName="Стат.карта" property="3" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
				      <msh:tableColumn columnName="К/дни" property="4" guid="bb789d76-55bb-46b1-9438-9b4e408779dd" />
				      <msh:tableColumn columnName="Наличие СЛО" property="5" guid="bb789d76-55bb-46b1-9438-9b4e408779dd" />
				      <msh:tableColumn columnName="Пациент" property="6" guid="4370bd26-12ec-4ad1-bffe-46159824c0f0" />
				      <msh:tableColumn columnName="Отделение" property="7" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
				      <msh:tableColumn columnName="Дата поступления" property="10"/>
				      <msh:tableColumn columnName="Дата выписки" property="11"/>
				      <msh:tableColumn columnName="Экстренность" property="8"/>
				      <msh:tableColumn columnName="Отказ от госпитализации" property="9" guid="b00c3a27-82cd-4d68-8a12-6f71bc8a7867" />
                </msh:table>
                <%-- 
                <msh:table name="list" action="entityParentView-stac_ssl.do" idField="id" disableKeySupport="true">
				      <msh:tableColumn columnName="Стат.карта" property="statCardNumber" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
				      <msh:tableColumn columnName="К/дни" property="daysCount" guid="bb789d76-55bb-46b1-9438-9b4e408779dd" />
				      <msh:tableColumn columnName="Наличие СЛО" property="isDepartmentMedCase" guid="bb789d76-55bb-46b1-9438-9b4e408779dd" />
				      <msh:tableColumn columnName="Пациент" property="patientInfo" guid="4370bd26-12ec-4ad1-bffe-46159824c0f0" />
				      <msh:tableColumn columnName="Кем открыт" property="username" guid="35347247-b552-4154-a82a-ee484a1714ad" />
				      <msh:tableColumn columnName="Отделение" property="departmentInfo" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
				      <msh:tableColumn columnName="Экстренность" property="emergency" guid="b00c3a27-82cd-4d68-8a12-6f71bc8a7867" />
				      <msh:tableColumn columnName="Отказ от госпитализации" property="deniedHospitalizatingInfo" guid="b00c3a27-82cd-4d68-8a12-6f71bc8a7867" />
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
    	}    //]]
  	</script>
  </tiles:put>

</tiles:insert>