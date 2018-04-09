<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Реестры омс</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
<%
    //String entryType = request.getParameter("entryType");
    String orderBy = request.getParameter("orderBy");
    String filter = request.getParameter("filter");
    StringBuilder filterSql= new StringBuilder();
    if (filter!=null&&!filter.equals("")) {
        String[] fields = filter.split(";");
        for (String field: fields) {
            String[] data = field.split(":");
            filterSql.append(" and e.").append(data[0]).append("='").append(data[1]).append("'");
        }
    }
    ActionUtil.setParameterFilterSql("entryType","e.entryType",request);
    ActionUtil.setParameterFilterSql("serviceStream","e.serviceStream",request);
    ActionUtil.setParameterFilterSql("billNumber","e.billNumber",request);
    ActionUtil.setParameterFilterSql("defect","e.isDefect",request);

    String billDate = request.getParameter("billDate");
    StringBuilder sqlAdd = new StringBuilder();
    //if (entryType!=null&&!entryType.equals("")) {sqlAdd.append(" and e.entryType='").append(entryType).append("'");}
    //if (serviceStream!=null&&!serviceStream.equals("")) {sqlAdd.append(" and e.serviceStream='").append(serviceStream).append("'");}
   // if (billNumber!=null&&!billDate.equals("")) {sqlAdd.append(" and e.billNumber='").append(billNumber).append("'");}
    if (billDate!=null&&!billDate.equals("")) {sqlAdd.append(" and e.billDate=to_date('").append(billDate).append("','dd.MM.yyyy')");}
    if (orderBy==null||orderBy.equals("")) {
      orderBy = "e.lastname, e.firstname, e.middlename"  ;
    }
    request.setAttribute("orderBySql",orderBy);
    request.setAttribute("sqlAdd",sqlAdd.toString());
    request.setAttribute("filterSql",filterSql.toString());
%>
        <msh:panel>
            <input type="text" name="lastname" id="lastname" placeholder="Фамилия пациента">
            <input type="text" name="historyNumber" id="historyNumber" placeholder="Номер ИБ">
            <input type="button" value="Найти" onclick="findAndSubmit()">
            <input type="button" value="Сбросить " onclick="findAndSubmit()">
            <input type="button" value="Сортировать по ФИО" onclick="setOrderBy('e.lastname,e.firstname, e.middlename')">
            <input type="button" value="Сортировать по № ИБ" onclick="setOrderBy('historyNumber')">
            <label><input type="checkbox" id="chkDefect" name="chkDefect">Только дефекты</label>

    </msh:panel>
        <ecom:webQuery nameFldSql="entriesSql" name="entries" nativeSql="
select e.id, e.lastname, e.firstname, e.middlename, e.startDate, e.finishDate
        , e.departmentName as f7_depName, ksg.code||' '||ksg.name as f8_ksg ,e.historyNumber as f9_hisNum, e.cost as f10_cost, vbt.code||' '||vbt.name as f11_bedType
        , list(coalesce(e.mainMkb,mkb.code)) as f12_diagnosis, rslt.code||' '||rslt.name as f13_result
        ,case when e.isDefect='1' then 'color:blue' when (e.doNotSend is null or e.doNotSend='0') then '' else 'color: red' end as f14_style
        ,list (es.dopCode) as f15_defects
        from e2entry e
        left join voce2medhelpprofile vbt on vbt.id=e.medhelpprofile_id
        left join VocE2FondV009 rslt on rslt.id=e.fondresult_id
        left join vocksg ksg on ksg.id=e.ksg_id
        left join entrydiagnosis d on d.entry_id=e.id and d.priority_id=1
        left join vocidc10 mkb on mkb.id=d.mkb_id
        left join e2entrysanction es on es.entry_id=e.id and es.isMainDefect='1' and (es.isDeleted is null or es.isDeleted='0')
 where e.listentry_id=${param.id} ${entryTypeSql} ${serviceStreamSql} ${billNumberSql} ${sqlAdd} ${defectSql}  ${filterSql} and (e.isDeleted is null or e.isDeleted='0')
 group by e.id, e.lastname, e.firstname, e.middlename, e.startDate, e.finishDate
        , e.departmentName, ksg.code, ksg.name, e.historyNumber, e.cost, vbt.code,vbt.name, rslt.code,rslt.name,e.doNotSend
  order by ${orderBySql} "/> ${entriesSql}
        <msh:hideException>
            <msh:section title='Результат поиска'>
                <msh:table name="entries" printToExcelButton="в excel" action="entityParentView-e2_entry.do" idField="1" disableKeySupport="true" styleRow="14" cellFunction="true" openNewWindow="true">
                    <msh:tableColumn columnName="№" property="sn" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="ИД" property="1" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Фамилия" property="2" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Имя" property="3" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Отчество" property="4" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="ИБ" property="9" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Отделение" property="7" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Дата начала" identificator="5" property="5" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Дата окончания" identificator="6" property="6" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="КСГ" property="8" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Диагноз" property="12" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Цена случая" property="10" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Профиль" property="11" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Результат" property="13" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Дефект" property="15" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />



                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
        <script type="text/javascript">
            function setOrderBy(fld) {
                alert('orderBy='+fld);
                window.location.href = setGetParameter("orderBy",fld)
            }
            function findAndSubmit() {
                var url = window.location.href;
                var filter = "";
                if ($('lastname').value) {
                    filter+="lastname:"+$('lastname').value;
                }
                if ($('historyNumber').value) {
                    if (filter){filter+=";"}
                    filter+="historyNumber:"+$('historyNumber').value;
                }
                  url=  setGetParameter("filter",filter);
             //   alert('url='+url);
                if ($('chkDefect').checked) {
                    url=setGetParameter("defect","1");
                } else {
                    url=setGetParameter("defect","");
                }
                window.location.href = url;



            }
            function setGetParameter(paramName, paramValue){
                var url = window.location.href;
                var hash = location.hash;
                url = url.replace(hash, '');
                if (url.indexOf(paramName + "=") >= 0){
                    var prefix = url.substring(0, url.indexOf(paramName));
                    var suffix = url.substring(url.indexOf(paramName));
                    suffix = suffix.substring(suffix.indexOf("=") + 1);
                    suffix = (suffix.indexOf("&") >= 0) ? suffix.substring(suffix.indexOf("&")) : "";
                    url = prefix + paramName + "=" + paramValue + suffix;
                } else {
                    if (url.indexOf("?") < 0) {
                        url += "?" + paramName + "=" + paramValue;
                    } else {
                        url += "&" + paramName + "=" + paramValue;
                    }
                }
                return url;


            }
        </script>
    </tiles:put>


</tiles:insert>