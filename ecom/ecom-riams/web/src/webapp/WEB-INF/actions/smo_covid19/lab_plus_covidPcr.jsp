<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Журнал положительнх ПЦР на COVID-19</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String typeDate = ActionUtil.updateParameter("typeDate","typeDate","1", request) ;
            String typeRes = ActionUtil.updateParameter("typeRes","typeRes","1", request) ;
            
            String date = request.getParameter("dateBegin");
            request.setAttribute("dateBegin",date) ;

            String dateTo = "", addResSql = "";

            if (typeDate.equals("1")) {dateTo="p.intakeDate";}
            else if (typeDate.equals("2")) {dateTo="p.planStartDate";}

            if (typeRes.equals("1")) {addResSql=" and fiprRes.valuevoc_id=70";}
            else if (typeRes.equals("2")) {addResSql=" and (fiprRes.valuevoc_id=70 or fiprRes.valuevoc_id=1025)";}

            request.setAttribute("dateTo", dateTo) ;
            request.setAttribute("typeDate", typeDate) ;
            request.setAttribute("typeRes", typeRes) ;
            request.setAttribute("addResSql", addResSql) ;
        %>
        <msh:form action="/lab_plus_covidPcr.do" defaultField="dateBegin" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Дата" />
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeDate" value="1">  по дате забора
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeDate" value="2">  по дате направления
                    </td>
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по  (typeRes)" colspan="1"><label for="typeResName" id="typeResLabel">Результаты:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeRes" value="1">  только положительные
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeRes" value="2">  положительные и условно положительные
                    </td>
                </msh:row>
                <msh:row>
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            if (request.getParameter("dateBegin")!=null &&  !request.getParameter("dateBegin").equals("")) {
        %>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery name="lab_plus_list" nameFldSql="lab_plus_list_sql"  nativeSql="
select sls.id as slsId
,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio
 ,to_char(pat.birthday,'dd.mm.yyyy') as bday
 , case when pat.address_addressId is not null
          then coalesce(adr.fullname,adr.name)
               ||case when pat.houseNumber is not null and pat.houseNumber!='' then ' д.'||pat.houseNumber else '' end
               ||case when pat.houseBuilding is not null and pat.houseBuilding!='' then ' корп.'|| pat.houseBuilding else '' end
	       ||case when pat.flatNumber is not null and pat.flatNumber!='' then ' кв. '|| pat.flatNumber else '' end
       when pat.territoryRegistrationNonresident_id is not null
	  then okt.name||' '||pat.RegionRegistrationNonresident||' '||oq.name||' '||pat.SettlementNonresident
	       ||' '||ost.name||' '||pat.StreetNonresident
               ||case when pat.HouseNonresident is not null and pat.HouseNonresident!='' then ' д.'||pat.HouseNonresident else '' end
	       ||case when pat.BuildingHousesNonresident is not null and pat.BuildingHousesNonresident!='' then ' корп.'|| pat.BuildingHousesNonresident else '' end
	       ||case when pat.ApartmentNonresident is not null and pat.ApartmentNonresident!='' then ' кв. '|| pat.ApartmentNonresident else '' end
       else  pat.foreignRegistrationAddress end as address
 ,coalesce((select c.workplace from covid19 c where c.id=(select max(id) from covid19 where medcase_id=sls.id )),pat.works,'') as work
 ,to_char(p.intakedate,'dd.mm.yyyy')||cast('/' as varchar)||' '||to_char(mc.datestart,'dd.mm.yyyy')||cast(', №' as varchar)||fiprNum.valuetext
 from prescription p
 left join PrescriptionList pl on pl.id=p.prescriptionList_id
 left join MedCase slo on slo.id=pl.medCase_id
 left join medcase sls on sls.id=slo.parent_id
 left join Patient pat on pat.id=slo.patient_id
  left join Address2 adr on adr.addressid = pat.address_addressid
     left join Omc_KodTer okt on okt.id=pat.territoryRegistrationNonresident_id
     left join Omc_Qnp oq on oq.id=pat.TypeSettlementNonresident_id
     left join Omc_StreetT ost on ost.id=pat.TypeStreetNonresident_id
 left join MedService ms on ms.id=p.medService_id
 left join VocServiceType vst on vst.id=ms.serviceType_id
 left join MisLpu dep on dep.id=slo.department_id
 left join VocServiceSubType vsst on vsst.id=ms.serviceSubType_id
    left join MedCase mc on mc.id=p.medcase_id
    left join Diary d on d.medcase_id=mc.id
    left join templateprotocol t2 on t2.id=d.templateprotocol
   left join forminputprotocol fiprNum on fiprNum.docprotocol_id=d.id and fiprNum.parameter_id=1283
   left join forminputprotocol fiprRes on fiprRes.docprotocol_id=d.id and fiprRes.parameter_id=1284
where p.dtype='ServicePrescription'   and ${dateTo}=to_date('${dateBegin}','dd.mm.yyyy')
and vst.code='LABSURVEY'   and p.cancelDate is null
and ms.serviceSubType_id='24'
and p.intakeDate is not null and vsst.code='COVID'
and sls.dtype='HospitalMedCase'
and d.id is not null
${addResSql}
group by  pat.id,pat.lastname,pat.firstname
     ,pat.middlename,pat.birthday,
     pat.address_addressId ,adr.fullname,adr.name
                , pat.houseNumber , pat.houseBuilding ,pat.flatNumber
                , pat.territoryRegistrationNonresident_id , okt.name,pat.RegionRegistrationNonresident,oq.name,pat.SettlementNonresident
 	       ,ost.name,pat.StreetNonresident
               , pat.HouseNonresident , pat.BuildingHousesNonresident,pat.ApartmentNonresident
        , pat.foreignRegistrationAddress,p.intakedate,mc.datestart,fiprNum.valuetext,sls.id
        order by pat.lastname,pat.firstname,pat.middlename
                " />
                <msh:section>
                    <msh:sectionTitle>Результаты поиска за ${dateBegin}.</msh:sectionTitle>
                    <form  id="printForm" name="printForm" action="print-pres_lab_plus_covidPcr.do" method="post" target="_blank">
                    <input type='hidden' name="sqlText" id="sqlText" value="${lab_plus_list_sql}">
                    <input type='hidden' name="s" id="s" value="PrintService">
                    <input type='hidden' name="m" id="m" value="printNativeQuery">
                    <input type='hidden' name="m" id="m" value="printNativeQuery">
                    <input type='hidden' name="date" id="date" value="${dateBegin}">
                    <input type="submit" value="Печать">
                </msh:section>
                <msh:table name="lab_plus_list"  noDataMessage="Нет данных" openNewWindow="true"
                           action="entityParentView-stac_ssl.do" idField="1">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="2" columnName="ФИО"/>
                    <msh:tableColumn property="3" columnName="Дата рождения"/>
                    <msh:tableColumn property="4" columnName="Адрес"/>
                    <msh:tableColumn property="5" columnName="Место работы, учёбы"/>
                    <msh:tableColumn property="6" columnName="Результат (дата забора/дата результата, №)"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%}%>
        <script type='text/javascript'>
            checkFieldUpdate('typeDate','${typeDate}',1) ;
            checkFieldUpdate('typeRes','${typeRes}',1) ;
            function checkFieldUpdate(aField,aValue,aDefaultValue) {
                eval('var chk =  document.forms[0].'+aField) ;
                var aMax=chk.length ;
                if ((+aValue)==0 || (+aValue)>(+aMax)) {
                    chk[+aDefaultValue-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
        </script>
    </tiles:put>
</tiles:insert>