<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <msh:title mainMenu="StacJournal" title="Отчёт по анализам на COVID-19" />

    </tiles:put>
    <tiles:put name="side" type="string" >

    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:sectionTitle>По текущим пациентам</msh:sectionTitle>
        <ecom:webQuery name="analysisCovidList" nativeSql=" select m.id
,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
,cast('-' as varchar(1)) as tempId
, cast ((select to_json(array_agg(t)) from	(select cip.id
,vc.code as colorCode,vc.picture as picture
,to_char(cip.startdate,'dd.mm.yyyy') as stDate
, cip.info as info
,vcip.name as vsipnameJust
from vocColorIdentityPatient vcip
left join coloridentitypatient cip on cip.voccoloridentity_id=vcip.id
left join voccolor vc on vcip.color_id=vc.id
 left join medcase_coloridentitypatient
 ss on ss.colorsidentity_id=cip.id where
(medcase_id=sls.id or medcase_id=m.id)   and (cip.startdate<=current_date and cip.finishdate is null
and (vcip.code='LAB_COVID_PLUS' or vcip.code='LAB_COVID_MINUS')
 or (cast ((cip.finishdate||' '||cip.finishtime) as TIMESTAMP) > current_timestamp)) order by cip.startdate asc) as t) as varchar) as jsonAr
from medCase m
left join MedCase as sls on sls.id = m.parent_id
left join medcase sloAll on sloAll.parent_id=sls.id and sloAll.dtype='DepartmentMedCase'
left join Patient pat on m.patient_id = pat.id
left join medcase_coloridentitypatient mcid on mcid.medcase_id=sls.id
left join ColorIdentityPatient cid on cid.id=mcid.colorsidentity_id
left join VocColorIdentityPatient vcid on vcid.id=cid.voccoloridentity_id
left join voccolor vcr on vcr.id=vcid.color_id
where m.DTYPE='DepartmentMedCase'
and m.transferDate is null and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)
and exists(select mcidi.* from medcase_coloridentitypatient mcidi
left join ColorIdentityPatient cidi on cidi.id=mcidi.colorsidentity_id
left join VocColorIdentityPatient vcidi on vcidi.id=cidi.voccoloridentity_id
where (mcidi.medcase_id=sls.id or mcidi.medcase_id=m.id) and (vcidi.code='LAB_COVID_PLUS' or vcidi.code='LAB_COVID_MINUS'))
group by  m.id,pat.lastname,pat.firstname,pat.middlename,sls.id
order by pat.lastname,pat.firstname,pat.middlename"
        />
        <msh:table name="analysisCovidList" action="entityParentView-stac_slo.do" idField="1">
            <msh:tableColumn property="sn" columnName="#"/>
            <msh:tableColumn columnName="Пациент" property="2" />
            <msh:tableColumn columnName="Анализы" property="3"/>
            <msh:tableColumn columnName="Анализы пациента hidden" property="4" hidden="true"/>
        </msh:table>
    </tiles:put>
    <tiles:put name="side" type="string">
        <tags:stac_journal currentAction="${param.action}"/>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
        <script type="text/javascript">

            /**
             * Вывести браслет и дату регистрации
             * @param table Таблица
             * @param tdResNum Номер столбца для вывода результата
             * @param tdJsonNum Номер столбца с json
             */
            function setBrWithDateReg(table, tdResNum, tdJsonNum) {
                if (typeof table !== 'undefined') {
                    for (var i = 1; i < table.rows.length; i++) {
                        var row = table.rows[i];
                        var tdRes=row.cells[tdResNum];
                        var td=row.cells[tdJsonNum];
                        var json = jQuery(td).text();
                        var str = "";
                        if (+json!=0) {
                            var aResult = JSON.parse(json);
                            str = '<table><tr>';
                            var size = 25;
                            for (var j = 0; j < aResult.length; j++) {
                                str += '<td><table><tr>';
                                var brace = aResult[j];
                                var style = 'style="width:' + size + 'px;height: ' + size + 'px;outline: 1px solid gray; border:2px;';
                                style += brace.picture ? '">' : ' background: ' + brace.colorcode + ';">';
                                if (brace.picture)
                                    style += '<img src="/skin/images/bracelet/' + brace.picture + '" title="' + brace.vsipnamejust +
                                        '" height="' + size + 'px" width="' + size + 'px">';
                                str += '<td align="center"><div' + style + '</div></td>';
                                str += '<tr><td align="center" style="font-size:10px">' + brace.info + '</td></tr>';
                                str += "</tr></table></td>";
                            }
                            str += "</tr></table>";
                        }
                        tdRes.innerHTML = str == '' ? '-' : str
                    }
                }
            }

            var table = getTableToSetBracelets('analysisCovidList');
            if (table!=null)
                setBrWithDateReg(table,2,3);
        </script>
    </tiles:put>
</tiles:insert>

