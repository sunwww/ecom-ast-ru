<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >
  <tiles:put name="body" type="string">
    <%
    String date = request.getParameter("dateBegin") ;
    if (date!=null && !date.equals(""))  {
    	%>
    
    <p>Результаты поиска ${infoTypePat}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch} ${hospInfo}
    </p>
    <ecom:webQuery name="journal_ticket" nativeSql="select m.id as mid
    	, ss.code as sscode, p.lastname|| ' ' || p.firstname ||' '||p.middlename as pfio
    	,to_char(p.birthday,'dd.mm.yyyy') as pbirthday,m.datestart as mdatestart, d.name as dname
    	,vdh.name as vdhname  
    	from MedCase as m 
    	left join Patient p on p.id=m.patient_id 
    	left join statisticstub as ss on ss.id=m.statisticstub_id 
    	left join VocDeniedHospitalizating as vdh on vdh.id=m.deniedHospitalizating_id  
    	left join mislpu as d on d.id=m.department_id 
    	left join VocSocialStatus pvss on pvss.id=p.socialStatus_id 
    	left join Omc_Oksm ok on p.nationality_id=ok.id
    	where m.DTYPE='HospitalMedCase' and m.datestart between to_date('${param.dateBegin}','dd.mm.yyyy') 
    	and to_date('${dateEnd}','dd.mm.yyyy') 
    	and cast(m.ambulanceTreatment as int)=1 ${hospT} 
    	  ${add}" />
        <msh:table name="journal_ticket" action="stac_groupByBedFundData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Стат.карта" property="2"/>
            <msh:tableColumn columnName="ФИО пациента" property="3"/>
            <msh:tableColumn columnName="Год рождения" property="4"/>
            <msh:tableColumn columnName="Дата обращения" property="5"/>
            
            <msh:tableColumn columnName="Причина отказа" property="7"/>
        </msh:table>

    <% } %>
  </tiles:put>
</tiles:insert>

