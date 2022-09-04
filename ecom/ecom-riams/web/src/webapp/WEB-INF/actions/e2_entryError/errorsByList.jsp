<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Список ошибок по заполнению</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>

        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>

    <%
String listId = request.getParameter("id");
String errorCode = request.getParameter("errorCode");
String dontShowComplexCase=request.getParameter("dontShow");
StringBuilder sqlAdd = new StringBuilder();
if (listId!=null) {
    if (errorCode!=null&&!errorCode.equals("")) { //Реестр записей по ошибке
        sqlAdd.append(" err.errorcode='").append(errorCode).append("'");
        if ("1".equals(dontShowComplexCase)) {
            if(sqlAdd.length()>0)sqlAdd.append(" and ");
            sqlAdd.append(" e.serviceStream!='COMPLEXCASE'");
        }
        request.setAttribute("sqlAdd",sqlAdd.toString());
    %>
        <select id="replaceSelect">
            <option value="SERVICESTREAM">Поток обслуживания</option>
        </select>
        <input type="text" name="replaceFrom" id="replaceFrom" placeholder="Заменить с">
        <input type="text" name="replaceTo" id="replaceTo" placeholder="Заменить на">
        <input type="button" id="replaceClick" value="Заменить" onclick="replaceValue(this)">
        <input type="checkbox" id="dontShowComplexCase" name="dontShowComplexCase" onclick="dontShow(this)">


        <ecom:webQuery name="entriesReestr" nativeSql="select e.id as id, e.lastname||' '||e.firstname||' '|| e.middlename||' '|| to_char(e.birthdate,'dd.MM.yyyy') as name
        , e.historyNumber as statNumber, e.serviceStream as serviceStream
                from e2entryerror err
                left join e2entry e on e.id=err.entry_id
                where err.listentry_id=${param.id} and (err.isdeleted='0' or err.isdeleted='0') and ${sqlAdd}
                group by e.id ,e.lastname,e.firstname, e.middlename,e.birthdate
                order by e.lastname, e.firstname, e.middlename"/>
        <msh:table idField="1" name="entriesReestr" action="entityParentView-e2_entry.do" noDataMessage="Нет случаев по ошибке">
            <msh:tableColumn columnName="ФИО пациента" property="2" />
            <msh:tableColumn columnName="Номер ИБ" property="3"/>
            <msh:tableColumn columnName="Поток обслуживания" property="4" />
        </msh:table>

    <% } else { //Выводим список всех ошибок за заполнению
%>
<input type="button" onclick="cleanAllErrors()" value="Удалить все ошибки"/>
            <ecom:webQuery name="entriesList" nativeSql="select '${param.id}&errorCode='||err.errorcode as fldId, err.errorcode as error , count( distinct err.entry_id) as cnt
                from e2entryerror err
                where err.listentry_id=${param.id} and (err.isdeleted='0' or err.isdeleted='0') group by err.errorcode"/>
            <msh:table idField="1" name="entriesList" action="entityParentList-e2_entry.do" noDataMessage="Нет ошибок по заполнению">
                <msh:tableColumn columnName="Ошибка" property="2" />
                <msh:tableColumn columnName="Кол-во записей с ошибкой" property="3"/>
            </msh:table>

    <% }} %>
    </tiles:put>

    <tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
    <script type="text/javascript">
        function cleanAllErrors () {
            Expert2Service.cleanAllErrorsByList(${param.id}, {callback: function(){window.document.location.reload();}});
        }
        function dontShow(chk) {
        //    if (this.ch)
        }
        function replaceValue(btn) {
            btn.disabled=true;
            var fld = $('replaceSelect').value;
            if (fld &&$('replaceTo').value) {
                Expert2Service.replaceFieldByError(${param.id},'${param.errorCode}',fld, $('replaceFrom').value, $('replaceTo').value, {
                    callback: function (a) {
                        alert(a);
                        window.document.location.reload();
                    }
                });
            } else {
                alert ('Выберите поле для замены и на что менять');
            }
        }
    </script>
    </tiles:put>
</tiles:insert>