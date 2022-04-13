<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных о выписанных в другое ЛПУ</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:stac_journal currentAction="stac_journalByMoveToAnotherLpu"/>
    </tiles:put>
    
  <tiles:put name="body" type="string">
    <msh:form action="/stac_groupByMoveToAnotherLpuList.do" defaultField="department" disableFormDataConfirm="true" method="GET">
    <msh:panel>
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>

      <msh:row>
        <td class="label" title="Поиск по пациентам (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Пациенты:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="1">  региональные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="2">  иногородные
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="3">  иностранцы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typePatient" value="4">  все
        </td>
        </msh:row>
        <msh:row>
        <msh:textField property="dateBegin" label="Период с" />
        <msh:textField property="dateEnd" label="по" />
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
        </msh:row>

    </msh:panel>
    </msh:form>
    
    <%
        String date = request.getParameter("dateBegin");
        String date1 = request.getParameter("dateEnd");
        if (date != null && !date.equals("")) {
            if (date1 != null && !date1.equals("")) {
                request.setAttribute("dateEnd", date1);
            } else {
                request.setAttribute("dateEnd", date);
            }
    %>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска ${infoTypePat}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch} ${dateInfo}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_ticket" nativeSql="select  
    	m.moveToAnotherLpu_id||':${param.dateBegin}:${dateEnd}:${param.typePatient }:'
    		||case when p.additionStatus_id is null then '' else cast(p.additionStatus_id as varchar(10)) end as idparam
    	,vas.name as vasname
    	,aLpu.name as alpuname
    	, count(*) as cnt
    from MedCase as m 
    left join mislpu as aLpu on aLpu.id=m.moveToAnotherLPU_id 
    left join mislpu as d on d.id=m.department_id 
    left join vocservicestream as vss on vss.id=m.servicestream_id 
    left join patient p on p.id=m.patient_id
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
    left join VocAdditionStatus vas on vas.id=p.additionStatus_id
    left join Omc_Oksm ok on p.nationality_id=ok.id
    where m.DTYPE='HospitalMedCase' 
    and m.dateFinish between to_date('${param.dateBegin}','dd.mm.yyyy')  
    and to_date('${param.dateEnd}','dd.mm.yyyy') and m.moveToAnotherLPU_id is not null ${add} 
    group by m.moveToAnotherLPU_id,aLpu.name,p.additionStatus_id,vas.name
    " />
        <msh:table name="journal_ticket" action="stac_groupByMoveToAnotherLpuData.do" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="Статус пациента" property="2"/>
            <msh:tableColumn columnName="ЛПУ перевода" property="3"/>
            <msh:tableColumn columnName="Кол-во" property="4"/>
        </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% } else {%>
    	<i>Выберите параметры и нажмите найти </i>
    	<% }   %>
    <script type='text/javascript'>
    
    checkFieldUpdate('typePatient','${typePatient}',4) ;

    function checkFieldUpdate(aField,aValue,aDefaultValue) {
       	eval('var chk =  document.forms[0].'+aField) ;
       	aValue=+aValue ;
       	var max=chk.length ;
       	if (aValue==0 || (aValue)>(max)) {
       		chk[+aDefaultValue-1].checked='checked' ;
       	} else {
       		chk[+aValue-1].checked='checked' ;
       	}
    }
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_groupByMoveToAnotherLpuList.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='stac_groupByMoveToAnotherLpuList.do' ;
    }
    </script>
  </tiles:put>
</tiles:insert>

