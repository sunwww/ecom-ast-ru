<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
 <tiles:put name="body" type="string">
  <msh:form action="/eln_count.do" defaultField="hello">
      <msh:panel>
      <msh:row>
       <td>
            Период
          </td>
			<msh:textField property="dateBegin" label="c"/>
			<msh:textField property="dateEnd" label="по"/>
           <td>
            <input type="submit" value="Найти" />
          </td>
      </msh:row>
     </msh:panel>
    </msh:form>
     <%
     String beginDate = request.getParameter("dateBegin");
	if (beginDate!=null && !beginDate.equals("")) {
		String finishDate = request.getParameter("dateEnd");
		if (finishDate==null || finishDate.equals("")) {
			finishDate=beginDate ;
		}
		 request.setAttribute("dateStart", beginDate);
		 request.setAttribute("dateFinish", finishDate);
%>
     <ecom:webQuery isReportBase="true" name = "elnList" nativeSql="
select
count(distinct dd.id) as count1
,count(distinct elec.id) as count2
,(select count(distinct disabilitydocument) from exportfsslog exp where result= 'OK' and
 (exp.requestdate between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy'))) as count3
from disabilitydocument dd
left join electronicdisabilitydocumentnumber elec on elec.number = dd.number
left join disabilityrecord disrec on disrec.disabilitydocument_id = dd.id
left join vocdisabilitydocumentclosereason vdcr on vdcr.id = dd.closereason_id
where
disrec.id=(select max(id) from disabilityrecord where disabilitydocument_id=dd.id) and
disrec.dateto between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
and dd.anotherlpu_id is null
and dd.status_id = 1
and (dd.noactuality is null or dd.noactuality ='0')
and (dd.isclose='1' and vdcr.code='1')
" />
     <msh:section>
         <msh:sectionContent>
             <msh:table name="elnList" action="" idField="1">
                 <msh:tableColumn columnName="Общее кол-во закрытых ЛН" property="1" />
                 <msh:tableColumn columnName="Из них закрытых ЭЛН" property="2" />
                 <msh:tableColumn columnName="Отправлено в ФСС" property="3" />
             </msh:table>
         </msh:sectionContent>
     </msh:section>
     <ecom:webQuery isReportBase="true" name = "elnList2" nativeSql="
select
distinct dd.id,dd.number
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,case when elec.id is not null then 'ЭЛН' else 'БЛН' end as iseln
,case when dd.isclose='1' and vdcr.code='1' then 'Закрыт к труду' else 'Не закрыт' end as isclose
,case when exp.disabilitydocument is not null  then 'Экспортирован' else 'Не экспортирован' end as export
,case when dd.noactuality is null or dd.noactuality ='0'  then s.name else s.name end
,case when  dd.anotherlpu_id is null then 'АМОКБ' else 'Другая ЛПУ' end as another
,case when dd.anotherlpu_id is null
and  (dd.noactuality is null or dd.noactuality ='0')
and dd.isclose='1' and vdcr.code='1' then '&#9989;' else '&#10008;' end as t
from disabilitydocument dd
left join electronicdisabilitydocumentnumber elec on elec.number = dd.number
left join exportfsslog exp on exp.disabilitynumber = elec.number and result= 'OK'
left join disabilityrecord disrec on disrec.disabilitydocument_id = dd.id
left join vocdisabilitydocumentclosereason vdcr on vdcr.id = dd.closereason_id
left join disabilitycase dc on dc.id = dd.disabilitycase_id
left join patient p on p.id = dc.patient_id
left join vocdisabilitystatus s on s.id = dd.status_id
where
disrec.id=(select max(id) from disabilityrecord where disabilitydocument_id=dd.id) and
disrec.dateto between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
order by dd.number
" />
     <msh:section>
         <msh:sectionContent>
             <msh:table name="elnList2" action="entityParentView-dis_document.do" idField="1">
                 <msh:tableColumn columnName="#" property="sn" />
               <%--  <msh:tableColumn columnName="id" property="1" />--%>
                 <msh:tableColumn columnName="номер" property="2" />
                 <msh:tableColumn columnName="ФИО" property="3" />
                 <msh:tableColumn columnName="Тип" property="4" />
                 <msh:tableColumn columnName="Статус документа" property="5" />
                 <msh:tableColumn columnName="Статус экспорта" property="6" />
                 <msh:tableColumn columnName="Тип" property="7" />
                 <msh:tableColumn columnName="ЛПУ" property="8" />
                 <msh:tableColumn columnName="Подсчитан" property="9" />
             </msh:table>
         </msh:sectionContent>
     </msh:section>
     <%} else { %>
     <p>Укажите период!</p>
     <%}%>
 </tiles:put>
</tiles:insert>
    