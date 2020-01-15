<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <ecom:titleTrail beginForm="e2_entryListForm" mainMenu="Expert2" title="Записи по заполнению" guid="3c259ba8-b962-4333-9aab-3316f984fdde" />
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
        boolean isFirst = true;
        for (String field: fields) {
            String[] data = field.split(":");
            String fldName = data[0], fldValue = data.length>1?data[1]:"";
            if (fldValue!=null&&!fldValue.trim().equals("")) {
                if (!isFirst) {
                    filterSql.append(" and ");
                } else {
                    isFirst=false;
                }
                if (fldName.equals("lastname")) {
                    String[] fio = fldValue.split(" ");
                    filterSql.append(" e.lastname like upper('").append(fio[0]).append("%')");
                    if (fio.length>1) {filterSql.append(" and e.firstname like upper('").append(fio[1]).append("%')");}
                    if (fio.length>2) {filterSql.append(" and e.middlename like upper('").append(fio[2]).append("%')");}
                    if (fio.length>3) {filterSql.append(" and e.birthdate =to_date('").append(fio[3]).append("','dd.MM.yyyy')");}
                } else if (fldName.equals("billStatus")) {
               //     filterSql.append(" and vbs.code='").append(fldValue).append("'");
                    filterSql.append(" vbs.code='").append("PAID").append("'");
                } else {
                    filterSql.append(" e.").append(fldName).append("='").append(fldValue).append("'");
                }
            }
        }
      //  if (!filterFound) {filterSql.append("and 1=2");}
    }

    ActionUtil.setParameterFilterSql("entryType","e.entryType",request);
    ActionUtil.setParameterFilterSql("serviceStream","e.serviceStream",request);
    ActionUtil.setParameterFilterSql("fileType","e.fileType",request);
    String billNumber = request.getParameter("billNumber");
        String isForeign = request.getParameter("isForeign");

    ActionUtil.setParameterFilterSql("defect","e.isDefect",request);
    String listId=request.getParameter("id");
    if ("".equals(listId) || "0".equals(listId)) { //Нет листа - ищем все оплаченные случаи **Нет листа - ищем все случаи. Есть фильтр что оплачен - ищем оплаченные
        listId=null;
    }
    String billDate = request.getParameter("billDate");
    StringBuilder sqlAdd = new StringBuilder();
    //if (entryType!=null&&!entryType.equals("")) {sqlAdd.append(" and e.entryType='").append(entryType).append("'");}
    //if (serviceStream!=null&&!serviceStream.equals("")) {sqlAdd.append(" and e.serviceStream='").append(serviceStream).append("'");}
   // if (billNumber!=null&&!billDate.equals("")) {sqlAdd.append(" and e.billNumber='").append(billNumber).append("'");}
    if (billDate!=null&&!billDate.equals("")) {sqlAdd.append(" and e.billDate=to_date('").append(billDate).append("','dd.MM.yyyy')");}
    if ("firstNew".equals(orderBy)) {
        orderBy="id desc";
    } else if (orderBy==null || orderBy.equals("")) {
      orderBy = "e.lastname, e.firstname, e.middlename, e.birthdate, e.startDate, e.finishDate"  ;
    }
    String errorCode = request.getParameter("errorCode");
    String searchFromSql ,searchWhereSql;


    request.setAttribute("orderBySql",orderBy);
    //request.setAttribute("sqlAdd",sqlAdd.toString());
    request.setAttribute("filterSql",filterSql.toString());
String defectColumnName = "Дефект";
//String entryType = request.getParameter("entryType");
    if (errorCode!=null&&!errorCode.equals("")) {
        searchFromSql=", list(err.comment) as errComment from e2entryerror err left join e2entry e on e.id=err.entry_id";
        searchWhereSql=(listId!=null?" err.listentry_id="+listId:"")+" and err.errorCode='"+errorCode+"'";
        request.setAttribute("searchTitle"," по ошибке: "+errorCode);
    } else {
        if (listId==null) {
            searchFromSql=" ,e.billNumber||' от '||to_char(e.billDate,'dd.MM.yyyy') as f13_billNumber from e2entry e";
            defectColumnName="Счет";
        } else {
            searchFromSql=" ,list (es.dopCode) as f13_defects from e2entry e";
   /*         switch (entryType) {
                case "STACKDP":
                    searchFromSql=",vdv.code||' '||vdv.name as f13_kdpName from e2entry e";
                    break;
                default:
                    searchFromSql=" ,list (es.dopCode) as f13_defects from e2entry e";
            }*/

        }

        searchWhereSql=(listId!=null ? " e.listentry_id="+listId : "")
            +(request.getAttribute("entryTypeSql")!=null?request.getAttribute("entryTypeSql"):"")
            +(request.getAttribute("serviceStreamSql")!=null?request.getAttribute("serviceStreamSql"):"")
            +(billNumber!=null?" and e.billNumber='"+billNumber+"'":"")
            + (request.getAttribute("defectSql")!=null?request.getAttribute("defectSql"):"")
            +sqlAdd;
        request.setAttribute("searchTitle"," ");
    }
    request.setAttribute("defectColumnName",defectColumnName);
    if (filterSql.length()>0) {
        if (searchWhereSql.length()>1) searchWhereSql+=" and ";
        searchWhereSql+=filterSql;
    }

    if (isForeign!=null){searchWhereSql+=" and e.isForeign='"+(isForeign.equals("1")?"1":"0")+"'";}
    request.setAttribute("searchFromSql",searchFromSql);
    request.setAttribute("searchWhereSql",searchWhereSql);
%>
        <msh:panel>
            <input type="text" name="searchField" id="lastname" placeholder="Фамилия пациента">
            <input type="text" name="searchField" id="historyNumber" placeholder="Номер ИБ">
            <br>
            <input type="text" name="searchField" id="startDate" placeholder="Дата начала случая">
            <input type="radio" name="typeSearchStartDate" value="more#">
            <input type="radio" name="typeSearchStartDate" value="less#">
            <input type="radio" name="typeSearchStartDate" value="equals#">
            <input type="text" name="searchField" id="finishDate" placeholder="Дата окончания случая">
            <input type="radio" name="typeSearchFinishDate" value="more#">
            <input type="radio" name="typeSearchFinishDate" value="less#">
            <input type="radio" name="typeSearchFinishDate" value="equals#">
            <br>
            <input type="button" value="Найти" onclick="findAndSubmit()">
            <input type="button" value="Сортировать по ФИО" onclick="setOrderBy('e.lastname,e.firstname, e.middlename')">
            <input type="button" value="Сортировать по № ИБ" onclick="setOrderBy('historyNumber')">
            <label><input type="checkbox" id="chkDefect" name="chkDefect">Только дефекты</label>
            <br>
            <select id="replaceSelect">
                <option value="SERVICESTREAM">Поток обслуживания</option>
                <option value="SNILS_DOCTOR">СНИЛС лечащего врача</option>
            </select>
            <input type="text" name="replaceFrom" id="replaceFrom" placeholder="Заменить с">
            <input type="text" name="replaceTo" id="replaceTo" placeholder="Заменить на">
            <input type="button" id="replaceClick" value="Заменить" onclick="replaceValue(this)">
            <input type="checkbox" id="dontShowComplexCase" name="dontShowComplexCase" onclick="dontShow(this)">
            <br>
            <input type="button" onclick="exportErrorsNewListEntry()" value="Перенести ошибки в новое заполнение">
            <input type="button" onclick="fixSomeError()" value="Поправить 503">


        </msh:panel>
        <ecom:webQuery nameFldSql="entriesSql" name="entries" nativeSql="
select e.id, e.lastname||' '||e.firstname||' '||coalesce(e.middlename,'')||' '||to_char(e.birthDate,'dd.MM.yyyy') as f2_fio, e.startDate as f3_startDate, e.finishDate as f4_finishDate
        , e.departmentName as f5_depName
        , list(
            case when e.entryType='POL_KDP' then vdv.name else ksg.code||' '||ksg.name end
             ) as f6_ksg
        ,e.historyNumber as f7_hisNum, e.cost as f8_cost, vbt.code||' '||vbt.name as f9_bedType
        , list(coalesce(e.mainMkb,mkb.code)) as f10_diagnosis, rslt.code||' '||rslt.name as f11_result
        ,case when e.isDefect='1' then 'color:blue' when (e.doNotSend='1') then 'color: red'  when e.serviceStream='COMPLEXCASE' then 'color: gray' else '' end as f12_style

        ${searchFromSql}
        left join voce2medhelpprofile vbt on vbt.id=e.medhelpprofile_id
        left join VocE2FondV009 rslt on rslt.id=e.fondresult_id
        left join vocksg ksg on ksg.id=e.ksg_id
        left join entrydiagnosis d on d.entry_id=e.id and d.priority_id=1
        left join vocidc10 mkb on mkb.id=d.mkb_id
        left join e2entrysanction es on es.entry_id=e.id and (es.isDeleted is null or es.isDeleted='0')
        left join e2bill bill on bill.id=e.bill_id
        left join voce2billstatus vbs on vbs.id=bill.status_id
        left join VocDiagnosticVisit vdv on vdv.id=e.kdpVisit_id
 where ${searchWhereSql} ${fileTypeSql}
 and (e.isDeleted is null or e.isDeleted='0')
 group by e.id, e.lastname, e.firstname, e.middlename, e.startDate, e.finishDate
        , e.departmentName, ksg.code, ksg.name, e.historyNumber, e.cost, vbt.code,vbt.name, rslt.code,rslt.name,e.doNotSend,e.birthdate,e.isdefect,e.servicestream
  order by ${orderBySql} "/>
        <msh:hideException>
            <msh:section title='Результат поиска ${searchTitle}'>
                <msh:table name="entries" printToExcelButton="в excel" action="entityParentView-e2_entry.do" idField="1" disableKeySupport="true" styleRow="12" cellFunction="true" openNewWindow="true">
                    <msh:tableColumn columnName="№" property="sn" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="ИД" property="1" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Фамилия Имя Отчество" property="2" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="ИБ" property="7" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Отделение" property="5" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Дата начала"  property="3" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Дата окончания"  property="4" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="КСГ" property="6" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Диагноз" property="10" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Цена случая" property="8" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Профиль" property="9" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="Результат" property="11" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                    <msh:tableColumn columnName="${defectColumnName}" property="13" guid="8c2a3f9b-89d7-46a9-a8c3-c08029ec047e" />
                </msh:table>
            </msh:section>
        </msh:hideException>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
        <script type="text/javascript">
            var errorCode = '${param.errorCode}';
            function fixSomeError() {
           //     if (errorCode) {
                    let conf = prompt("Подтвердите ошибку","N2");
                    if (conf) {
                        Expert2Service.fixSomeErrors(${param.id}, "503", conf, {
                            callback: function (res) {
                                alert(res);
                            }
                        })
                    }
         //       }
            }
            function exportErrorsNewListEntry() {
                if (errorCode) {
                    Expert2Service.exportErrorsNewListEntry(${param.id},errorCode, {
                       callback: function () {
                           alert('Кажется, перенесены!');
                       }
                    });
                } else {
                    alert ('Нет ошибки, нельзя так делать!');
                }


            }

            function replaceValue(btn) {
                btn.disabled=true;
                var fld = $('replaceSelect').value;
                if (fld &&$('replaceTo').value) {
                    Expert2Service.replaceFieldByError(${param.id},errorCode ? errorCode : null,fld, $('replaceFrom').value, $('replaceTo').value, {
                        callback: function (a) {
                            alert(a);
                            window.document.location.reload();
                        }
                    });
                } else {
                    alert ('Выберите поле для замены и на что менять');
                }
            }

            function setOrderBy(fld) {
                alert('orderBy='+fld);
                window.location.href = setGetParameter("orderBy",fld)
            }
            new dateutil.DateField($('startDate'));
            new dateutil.DateField($('finishDate'));

            function findAndSubmit() {
                var url = window.location.href;
                var filter = "";
                jQuery("input[name='searchField']").each(function (ind, el) {
                    if (el.value) {
                        filter+=el.id+":"+el.value+";";
                    }
                })
                url=setGetParameter("filter",filter,url);
                url=setGetParameter("defect",$('chkDefect').checked?"1":"",url);
                window.location.href = url;
            }

            function setGetParameter(paramName, paramValue, url){
                url = url ? url : window.location.href;
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