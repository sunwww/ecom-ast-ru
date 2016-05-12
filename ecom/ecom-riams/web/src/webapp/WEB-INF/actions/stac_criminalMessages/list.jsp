<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="stac_sslForm" mainMenu="Patient" title="Сообщения в полицию" guid="7c2f862e-0345-431a-a391-39b33538ad1b" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Дополнительно" guid="0ddb2181-a1e1-4f7c-93f6-aa77081d080b">
      <msh:sideLink action="/mis_patients" name="Новая госпитализация" guid="78548581-88f4-46e3-bad5-ec71ea02dc72" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/View" guid="2ccd6a7e-21cc-45b9-8e22-6f0b911309b1">
    <msh:section>
    <msh:sectionTitle>Список сообщений по случаю</msh:sectionTitle>
    <msh:sectionContent>
    
    <ecom:webQuery name="journal_militia" nativeSql="
    select pm.id, pm.phoneDate
    ,vpht.name||coalesce(' '||vpmst.name,'')
    ,to_char(pm.whenDateEventOccurred,'dd.mm.yyyy')||' '||cast(pm.whenTimeEventOccurred as varchar(5)) as whenevent
    ,pm.place as pmplace
    ,coalesce(vpme.name,pm.recieverFio) as reciever
    ,vpmo.name as vphoname,wp.lastname as wplastname
    ,p.lastname||' '||p.firstname||' '||p.middlename||' г.р.'||to_char(p.birthday,'dd.mm.yyyy') as fiopat
    ,coalesce(vpmorg.name,pm.phone,pm.recieverOrganization) as organization
    ,pm.diagnosis as pmdiagnosis
    from PhoneMessage pm 
    left join VocPhoneMessageType vpht on vpht.id=pm.phoneMessageType_id
    left join VocPhoneMessageSubType vpmst on vpmst.id=pm.phoneMessageSubType_id
    left join VocPhoneMessageOrganization vpmorg on vpmorg.id=pm.organization_id
    left join VocPhoneMessageEmploye vpme on vpme.id=pm.recieverEmploye_id
    left join VocPhoneMessageOutcome vpmo on vpmo.id=pm.outcome_id
    left join WorkFunction wf on wf.id=pm.workFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join medcase m on m.id=pm.medCase_id
    left join Patient p on p.id=m.patient_id
	left join MisLpu as ml on ml.id=m.department_id 
	left join SecUser su on su.login=m.username
	left join WorkFunction wf1 on wf1.secUser_id=su.id
	left join Worker w1 on w1.id=wf1.worker_id
	left join MisLpu ml1 on ml1.id=w1.lpu_id     
    where pm.dtype='CriminalPhoneMessage'
    and pm.medCase_id=${param.id}
    order by pm.phoneDate
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia"
    viewUrl="entityShortView-stac_criminalMessages.do" 
     action="entityParentView-stac_criminalMessages.do" idField="1" >
      <msh:tableColumn columnName="Дата" property="2" />
      <msh:tableColumn property="9" columnName="Пациент"/>
      <msh:tableColumn columnName="Тип" property="3" />
      <msh:tableColumn columnName="Когда" property="4" />
      <msh:tableColumn columnName="Место" property="5" />
      <msh:tableColumn columnName="Фамилия принявшего" property="6" />
      <msh:tableColumn columnName="Фамилия передавшего" property="8" />
      <msh:tableColumn columnName="Диагноз" property="11" />
      <msh:tableColumn columnName="Исход" property="7" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="6372e109-9e1b-49dc-840b-9b38f524efeb">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_criminalMessages" name="Сообщение" title="Добавить сообщение в полицию" guid="436bbb7b-497c-4b10-ba0e-c5601675a713" roles="/Policy/Mis/MedCase/Stac/Ssl/PhoneMessage/CriminalMessage/Create" />
    </msh:sideMenu>
  </tiles:put>
</tiles:insert>

