<%@ page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/printLayout.jsp" flush="true" >

  <tiles:put name="body" type="string">
  <msh:section>
    <msh:sectionContent>
  	<ecom:webQuery name="list" nativeSql="select m.id,ss.code,m.dateStart,m.dateFinish
  	,p.lastname||' '||p.firstname||' '||p.middlename,m.username,d.name as dname
  	,case when m.emergency=1 then 'Да' else 'Нет' end  as memergency,vdh.name as vdhname
  	,m.dateStart,m.dateFinish from MedCase as m 
  	left join patient p on p.id=m.patient_id 
    left join VocSocialStatus pvss on pvss.id=p.socialStatus_id
  	left join vocdeniedhospitalizating vdh on vdh.id = m.deniedhospitalizating_id 
  	left join Omc_Oksm ok on p.nationality_id=ok.id
  	left join mislpu d on d.id=m.department_id 
  	left join StatisticStub ss on ss.id=m.statisticStub_id 
  	where m.DTYPE='HospitalMedCase' and m.dateFinish between to_date('${dateBegin }','dd.mm.yyyy') and to_date('${dateEnd }','dd.mm.yyyy') 
    and m.moveToAnotherLpu_id=${anotherLpu} ${addStatus} ${add}"/>
    <msh:table name="list" action="entityParentView-stac_ssl.do" idField="1" noDataMessage="Не найдено" guid="03092441-0d8d-421d-95ea-b110dd539b50">
      <msh:tableColumn columnName="Стат.карта" property="2" guid="e98f73b5-8b9e-4a3e-966f-4d43576bbc96" />
      <msh:tableColumn columnName="Дата начала" property="3" guid="4370bd26-12ec-4ad1-bffe-46159824c0f0" />
      <msh:tableColumn columnName="Дата окончания" property="4" guid="4370bd26-12ec-4ad1-bffe-46159824c0f0" />
      <msh:tableColumn columnName="Пациент" property="5" guid="4370bd26-12ec-4ad1-bffe-46159824c0f0" />
      <msh:tableColumn columnName="Отделение" property="7" guid="d2eebfd0-f043-4230-8d24-7ab99f0d5b45" />
      <msh:tableColumn columnName="Экстренность" property="8" guid="b00c3a27-82cd-4d68-8a12-6f71bc8a7867" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
  </tiles:put>
</tiles:insert>

