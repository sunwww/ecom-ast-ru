<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Contract" >Отчет по услугам</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
        <tags:contractMenu currentAction="nationalityReportGroup"/>
    </tiles:put>
    <tiles:put name='body' type='string' >
        <%
            String typeGroup =ActionUtil.updateParameter("Form039Action","typeGroup","1", request) ;
            if (request.getParameter("short")==null) {
        %>
        <msh:form action="/contract_nationalityReport.do" defaultField="dateFrom">
            <msh:panel>
                <msh:row>
                    <msh:textField property="dateFrom" label="c"/>
                    <msh:textField property="dateTo" label="по"/>
                </msh:row>
                  <msh:row>
                    <msh:autoComplete property="nationality" fieldColSpan="4" label="Гражданство" vocName="omcOksm" horizontalFill="true"/>
                </msh:row>
                  <msh:row>
                    <msh:autoComplete property="positionType" fieldColSpan="4" label="Тип услуги" vocName="vocPositionType" horizontalFill="true"/>
                </msh:row>

                <msh:row>
                    <td class="label" title="Группировака (typePatient)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Группировка по:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeGroup" value="1"> Сведения об оказанной помощи иногородним гражданам
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeGroup" value="2"> Сведения об оказанной помощи иностранным гражданам
                    </td>
                </msh:row>
                <msh:row>
                    <msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
                </msh:row>
            </msh:panel>
        </msh:form>

        <%
        }

            String dateFrom = request.getParameter("dateFrom") ;
            String dateTo = request.getParameter("dateTo") ;

            if (dateFrom!=null && dateTo!=null) {
                String sqlAdd = "(select ca1.id"+
                        " from contractaccount CA1"+
                        " left join ContractAccountOperation CAO1 on CAO1.account_id=CA1.id"+
                        " left join ContractAccountOperationByService caos1 on cao1.id=caos1.accountOperation_id"+
                        " left join ContractAccountMedService cams1 on caos1.accountMedService_id=cams1.id"+
                        " left join PriceMedService pms1 on pms1.id=cams1.medService_id "+
                        " left join PricePosition pp1 on pp1.id=pms1.pricePosition_id"+
                        " left join medservice ms1 on ms1.id=pms1.medservice_id"+
                        " left join vocservicetype vst1 on vst1.id=ms1.servicetype_id"+
                        " WHERE CA1.id=ca.id and CAo1.operationdate between to_date('"+dateFrom+"', 'dd.mm.yyyy') AND to_date('"+dateTo+"', 'dd.mm.yyyy') and (cao1.dtype='OperationAccrual' or cao1.dtype='OperationReturn') and (pp1.isVat='0' or pp1.isVat is null)"+
                        " and vst1.code='к/д')";

                request.setAttribute("stacSqlAdd"," and ca.id in "+sqlAdd);

                if (!dateTo.equals("")) {

                    if (typeGroup.equals("1")) { //Разбивка по регионам
                        request.setAttribute("stacSelectSql", "ar.name as adrName") ;
                        request.setAttribute("polSelectSql", "ar.addressid,ar.name as adrName") ;
                        request.setAttribute("stacGroupSql", "lpu.id,  lpu.name, ar.addressid, ar.name ") ;
                        request.setAttribute("polGroupSql", "vwf.id,  vwf.name, ar.addressid, ar.name ") ;
                        request.setAttribute("stacOrderSql", "lpu.name, ar.name") ;
                        request.setAttribute("polOrderSql", "vwf.name, ar.name") ;
                        request.setAttribute("whereSql"," and ar.kladr not like '30%'");
                        request.setAttribute("groupName","Регион");
                        request.setAttribute("titleAdd","иногородним");
                    } else if (typeGroup.equals("2")) { //Разбивка по странам
                        request.setAttribute("stacSelectSql", "nat.name as natName") ;
                        request.setAttribute("polSelectSql", "nat.id, nat.name as natName") ;
                        request.setAttribute("stacGroupSql", "lpu.id, lpu.name, nat.id, nat.name") ;
                        request.setAttribute("polGroupSql", "vwf.id, vwf.name, nat.id, nat.name") ;
                        request.setAttribute("stacOrderSql", "lpu.name, nat.name") ;
                        request.setAttribute("polOrderSql", "vwf.name, nat.name") ;
                        request.setAttribute("whereSql"," and nat.id is not null and nat.voc_code!='643' ");
                        request.setAttribute("groupName","Гражданство");
                        request.setAttribute("titleAdd","иностранным");
                    } else if (typeGroup.equals("_1")) {
                        //Реестр
                        request.setAttribute("selectSql1","lpu.id as sqlId,lpu.name as lpuname");
                        request.setAttribute("groupSql1","lpu.id, lpu.name");
                        request.setAttribute("orderSql1","lpu.name");
                        request.setAttribute("groupSql", "pp.code||' '||pp.name") ;
                        request.setAttribute("groupSqlId", "'&priceMedService='||pms.id") ;
                        request.setAttribute("groupName", "Сотрудник") ;
                        request.setAttribute("groupGroup", "pms.id,pp.code,pp.name,pp.isVat") ;
                        request.setAttribute("groupOrder", "pp.code") ;
                        request.setAttribute("whereSql1","lpu.id");
                    }
                    ActionUtil.setParameterFilterSql("nationality","p.nationality_id", request) ;
                    ActionUtil.setParameterFilterSql("positionType","pp.positionType_id", request) ;
                    ActionUtil.setParameterFilterSql("departmentType","lpu.lpuFunction_id", request) ;
        %>
        <% if (typeGroup.equals("1") || typeGroup.equals("2")) { %>

        <ecom:setAttribute name="id_group" value="${groupSqlId1}||${operatorSqlId}||${priceMedServiceSqlId}||${departmentSqlId}||${positionTypeSqlId}||${priceListSqlId}||'&dateFrom=${param.dateFrom}&dateTo=${param.dateTo}"/>
        <ecom:setAttribute name="queryGroup_sql" value="
SELECT lpu.id as lpuId, lpu.name as lpuName, ${stacSelectSql}
,count(distinct p.id) as f5_cntPatientId
, count(distinct case when cao.dtype='OperationAccrual' then mc.id else null end) as cntDogMedService
, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end) as sumCountMedService
, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscount
, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountWithoutVat

, count(distinct case when cao.dtype='OperationReturn' then mc.id else null end) as cntDogMedServiceRet
, sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end) as sumCountMedServiceRet
, sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountRet
, sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)/100),2) else 0 end) sumNoAccraulMedServiceWithDiscountRetWithoutVat

, sum(case when cao.dtype='OperationAccrual' then cams.countMedService else 0 end)
- sum(case when cao.dtype='OperationReturn' then cams.countMedService else 0 end) as sumCountMedServiceItog

, sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)

as sumItog
, (sum(case when cao.dtype='OperationAccrual' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
-
 sum(case when cao.dtype='OperationReturn' then round(cams.countMedService*(case when pp.isVat='1' then 0.1*1000/118 else 1 end)*(cams.cost*(100-coalesce(cao.discount,0))/100),2) else 0 end)
 )
as sumItogWithoutVat
FROM medcontract MC
LEFT JOIN contractaccount as CA ON CA.contract_id=MC.id


left join ContractAccountOperation CAO on CAO.account_id=CA.id
left join ContractAccountOperationByService caos on cao.id=caos.accountOperation_id
left join ContractAccountMedService cams on caos.accountMedService_id=cams.id
left join servedperson sp on sp.id=cams.servedperson_id
LEFT JOIN contractPerson CC ON CC.id=sp.person_id
LEFT JOIN patient p ON p.id=CC.patient_id
left join address2 a on a.addressid=p.address_addressid
left join Address2 ar on ar.addressid=a.region_addressid
left join PriceMedService pms on pms.id=cams.medService_id
left join PricePosition pp on pp.id=pms.pricePosition_id
left join VocPositionType vpt on vpt.id=pp.positionType_id
left join PricePosition pg on pg.id=pp.parent_id
left join MisLpu lpu on lpu.id=pg.lpu_id
left join VocLpuFunction vlf on vlf.id=lpu.lpuFunction_id
left join WorkFunction wf on wf.id=cao.workFunction_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join Worker w on w.id=wf.worker_id
left join Patient wp on wp.id=w.person_id
left join omc_oksm  nat on nat.id=p.nationality_id
WHERE	CAo.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy')
and (cao.dtype='OperationAccrual' or cao.dtype='OperationReturn')  ${positionTypeSql}
${nationalitySql}  ${whereSql}
 ${stacSqlAdd}
group by ${stacGroupSql}
order by ${stacOrderSql}
			"/>
        <ecom:webQuery name="hospitalDataList" nativeSql="${queryGroup_sql}" />
        <h2>Сведения об объемах оказанной медицинской помощи ${titleAdd} гражданам в стационарных условиях</h2>
        <msh:table name="hospitalDataList" action="/javascript:void()" idField="1" printToExcelButton="Сохранить в excel">
            <msh:tableColumn columnName="#" property="sn" />
            <msh:tableColumn columnName="Отделение" property="2" />
            <msh:tableColumn columnName="Кол-во пациентов" property="4" isCalcAmount="true"/>
            <msh:tableColumn columnName="${groupName}" property="3" />
            <msh:tableColumn columnName="Сумма" property="7" isCalcAmount="true" />
        </msh:table>

        <ecom:webQuery name="policlinicDataList" nativeSql="
        select ${polSelectSql}
        ,vwf.name
,count(distinct p.id) as cntPatient
, sum(round(cams.countMedService*(cams.cost*(100-coalesce(cao.discount,0))/100),2)) as sumRender
from ContractAccountOperationByService caos
left join ContractAccountOperation cao on caos.accountOperation_id=cao.id and cao.dtype='OperationAccrual' and cao.repealOperation_id is null
left join medcase mc on mc.id=caos.medcase_id
left join WorkFunction wf on wf.id=mc.workFunctionExecute_id
left join Worker w on w.id=wf.worker_id left join Patient wp on wp.id=w.person_id
left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
left join ContractAccountMedService cams on cams.id=caos.accountMedService_id
left join ServedPerson sp on cao.account_id=sp.account_id
left join ContractPerson cp on sp.person_id=cp.id
left join Patient p on cp.patient_id=p.id
left join address2 a on a.addressid=p.address_addressid
left join Address2 ar on ar.addressid=a.region_addressid
left join omc_oksm  nat on nat.id=p.nationality_id
left join pricemedservice pms on cams.medService_id=pms.id
left join priceposition pp on pms.priceposition_id=pp.id
left join priceposition ppG on ppG.id=pp.parent_id
left join mislpu ml on ml.id=ppG.lpu_id
left join medservice ms on pms.medservice_id=ms.id
where cao.operationdate between to_date('${param.dateFrom}', 'dd.mm.yyyy') AND to_date('${param.dateTo}', 'dd.mm.yyyy')
and ppG.lpu_id in (184,180 )
and pp.positionType_id in (2,11) and vwf.id is not null

${nationalitySql} ${positionTypeSql} ${whereSql}

group by ${polGroupSql}
order by ${polOrderSql}
"/>
        <hr> <h2>Сведения об объемах оказанной медицинской помощи ${titleAdd} гражданам в амбутаторных условиях</h2>
        <msh:table name="policlinicDataList" action="/javascript:void()" idField="1" printToExcelButton="Сохранить в excel">
            <msh:tableColumn columnName="#" property="sn" />
            <msh:tableColumn columnName="Профиль" property="3" />
            <msh:tableColumn columnName="Кол-во пациентов" property="4" isCalcAmount="true"/>
            <msh:tableColumn columnName="${groupName}" property="2" />
            <msh:tableColumn columnName="Сумма" property="5"  isCalcAmount="true"/>
        </msh:table>

        <%}  %>

        </table>
        <%

        } else {
        %>
        Выберите параметры и нажмите  кнопку "СФОРМИРОВАТЬ"
        <%
                }}
        %>


    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">

            checkFieldUpdate('typeGroup','${typeGroup}',1) ;
            checkFieldUpdate('typeView','${typeView}',1) ;
            checkFieldUpdate('typeDtype','${typeDtype}',3) ;
            checkFieldUpdate('typeDate','${typeDate}',2) ;


            function checkFieldUpdate(aField,aValue,aDefault) {

                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)>max) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
        </script>
    </tiles:put>
</tiles:insert>