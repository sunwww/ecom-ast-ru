
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

<tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал прикрепленного населения"/>
</tiles:put>
<tiles:put name="body" type="string">
    <%
    String typeRead =ActionUtil.updateParameter("PatientAttachment","typeRead","1", request) ;
    ActionUtil.updateParameter("PatientAttachment","typeAge","3", request) ;
    ActionUtil.updateParameter("PatientAttachment","typeDispensaryStatus","3", request) ;
    %>

    <msh:form action="/exportDispensary.do" defaultField="startDate" disableFormDataConfirm="true" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f" >
        <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
            <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
                <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
            </msh:row>
            
            <msh:row>
                <msh:textField  property="startDate" label="Период с" />
                <msh:textField  property="finishDate" label="до" />
            </msh:row>

            <msh:row>
                <td class="label" title="Возраст  (typeAge)" colspan="1"><label for="typeAgeName" id="typeAgeLabel">Возраст:</label></td>
                <td onclick="this.childNodes[1].checked='checked';">
                    <input type="radio" name="typeAge" value="young">  до 18 лет
                </td>
                <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                    <input type="radio" name="typeAge" value="old">  от 18 лет
                </td>
                <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                    <input type="radio" name="typeAge" value="all">  все
                </td>

            </msh:row>

            <msh:row>
                <td class="label" title="Прикрепление  (typeDispensaryStatus)" colspan="1"><label for="typeDispensaryStatusName" id="typeDispensaryStatusLabel">Состояние Д учета:</label></td>
                <td onclick="this.childNodes[1].checked='checked';">
                    <input type="radio" name="typeDispensaryStatus" value="in">  Только состоящие на Д учете
                </td>
                <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                    <input type="radio" name="typeDispensaryStatus" value="out">  Только выбывших
                </td>
                <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                    <input type="radio" name="typeDispensaryStatus" value="all">  все
                </td>

            </msh:row>
            <msh:row><td>
                <label for="packetNumber">Номер пакета</label>
            </td><td>
                <input type="text" id="packetNumber" name="packetNumber" >
            </td>
            </msh:row>
        </msh:panel>
        <msh:panel colsWidth="systemTable">
            <msh:row>
                <msh:textField  property="changedDateFrom" label="Измененные с" />
            </msh:row>

            <msh:row>
                <td colspan="11">  <label>Импорт дефектов: </label>
                    <input type="file"  name="filenameDefect" id="filenameDefect" size="50" value="Импорт дефектов" onchange="importDefects(event)">

                    <!--  <input type="button" name="run_import" value="Импорт дефектов"  onclick="this.form.submit()" /> -->
                </td>

            </msh:row>

            <msh:row>
                <msh:hidden property="filename" />
                <td colspan="4">
                    Файл <span id='aView'></span>
                </td>
            </msh:row>
            <msh:row>
                <td colspan="11">
                    <input type="button" value="Найти" onclick="exportCard()"/>

                </td>
            </msh:row>
            <table id="defectTable" border="1" style="padding: 15px; display: none">

                <tbody id="defectElements" >
                </tbody>
            </table>
            <script type="text/javascript" src="./dwr/interface/DispensaryService.js"></script>
            <script type="text/javascript">
                checkFieldUpdate('typeAge','${typeRead}','all') ;
                checkFieldUpdate('typeDispensaryStatus','${typeDispensaryStatus}','all') ;

                var text="";
                //Выгрузка

                function formToJson(form) {
                    var o = {};
                    var a = form.serializeArray();
                    jQuery.each(a, function() {
                        if (o[this.name]) {
                            alert ('1'+o[this.name]);
                            if (!o[this.name].push) {
                                o[this.name] = [o[this.name]];
                                alert ('2'+o[this.name]+"<>"+[o[this.name]]);
                            }
                            o[this.name].push(this.value || '');
                        } else {
                            o[this.name] = this.value || '';
                        }
                    });
                    return o;
                }

                function exportCard(){
                    if (!$('startDate').value) {
                        alert("Укажите дату начала!");
                        return;
                    }
                    var formJson=formToJson(jQuery('#mainForm'));

                DispensaryService.exportDispendaryCards(JSON.stringify(formJson), {
                    callback: function(ret) {
                        ret = JSON.parse(ret);
                        if (ret.status=="ok") {
                        showToastMessage("<a href='/rtf/"+ret.filename+"'>Файл сформирован</a>",null,false);
                        } else {
                            showToastMessage("Ошибка формирования файла: "+ret.errorCode,null,false);
                        }

                    }
                });

                }
              function flushTable() {
                    var table = document.getElementById("defectElements");
                    var aRows = table.childNodes;
                    if (aRows.length>1) {
                        var j=aRows.length;
                        for (var i=1;i<j;i++) {
                            table.deleteRow(0);
                        }
                    }

                }


                function checkFieldUpdate(aField,aValue,aDefaultValue) {
                    if (jQuery(":radio[name="+aField+"][value='"+aValue+"']").val()!=undefined) {
                        jQuery(":radio[name="+aField+"][value='"+aValue+"']").prop('checked',true);
                    } else {
                        jQuery(":radio[name="+aField+"][value='"+aDefaultValue+"']").prop('checked',true);
                    }
                }
            </script>
        </msh:panel>
        <%if (request.getAttribute("defectWQR")!=null){ %>
        <p style="color:red">Внимание! Следующие записи не выгружены!</p>
        <msh:table   viewUrl="entityParentView-mis_lpuAttachedByDepartment.do" editUrl="entityParentView-mis_lpuAttachedByDepartment.do" deleteUrl="entityParentDeleteGoParentView-mis_lpuAttachedByDepartment.do" name="defectWQR" action="entityView-mis_lpuAttachedByDepartment.do" idField="2" noDataMessage="Не найдено">
            <msh:tableColumn columnName="Ошибка" property="3"/>
        </msh:table>
        <%} %>
    </msh:form>




    <%
    String date = request.getParameter("period") ;
    String date1 = request.getParameter("periodTo") ;
    String sqlAdd = (String)request.getAttribute("sqlAdd");

    if (sqlAdd!=null &&date!=null && !date.equals("") && typeRead!=null)  {
    if (date1==null ||date1.equals("")) {
    request.setAttribute("periodTo", date);
    } else {
    request.setAttribute("periodTo", date1) ;
    }


    %>


    <%    if (typeRead!=null && (typeRead.equals("2"))) {%>
    <ecom:webQuery nameFldSql="journal_ticket_sql" name="journal_ticket" maxResult="250" nativeSql="
		select lp.id,p.lastname,p.firstname,case when p.middlename='' or p.middlename='Х' or p.middlename is null then 'НЕТ' else p.middlename end as middlename,to_char(p.birthday,'dd.MM.yyyy') as birthday
    	 , p.commonNumber
    	 , case when lp.id is null then '1' else coalesce(vat.code,'2') end as spprik
    	 , case when lp.id is null then '01.01.2013' else coalesce(to_char(lp.dateFrom,'dd.MM.yyyy'),'01.01.2014') end as tprik
    	 , to_char(lp.dateTo,'dd.MM.yyyy') as otkprikdate
         , case when lp.dateTo is null then 'Прикреплен' else 'Откреплен' end as otkorprik
         , lp.defectperiod
         , lp.defecttext
         ,smo.name
    	 from LpuAttachedByDepartment lp
    	 left join Patient p on lp.patient_id=p.id
    	 left join MisLpu ml1 on ml1.id=p.lpu_id
    	 left join MisLpu ml2 on ml2.id=lp.lpu_id
         left join VocAttachedType vat on lp.attachedType_id=vat.id
         left join reg_ic smo on smo.id=lp.company_id

   		where (p.noActuality='0' or p.noActuality is null) and p.deathDate is null ${sqlAdd} group by p.id,p.lastname,p.firstname,p.middlename,p.birthday,p.snils,p.commonNumber,lp.id,lp.dateFrom,lp.dateTo,vat.code, lp.defectperiod
         , lp.defecttext, smo.name
    	 order by p.lastname,p.firstname,p.middlename,p.birthday  " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />

    <msh:table   viewUrl="entityParentView-mis_lpuAttachedByDepartment.do" editUrl="entityParentView-mis_lpuAttachedByDepartment.do" deleteUrl="entityParentDeleteGoParentView-mis_lpuAttachedByDepartment.do" name="journal_ticket" action="entityView-mis_lpuAttachedByDepartment.do" idField="1" noDataMessage="Не найдено">
        <msh:tableColumn columnName="#" property="sn"/>
        <msh:tableColumn columnName="Фамилия" property="2"/>
        <msh:tableColumn columnName="Имя" property="3"/>
        <msh:tableColumn columnName="Отчетство" property="4"/>
        <msh:tableColumn columnName="Дата рождения" property="5"/>
        <msh:tableColumn columnName="RZ" property="6"/>
        <msh:tableColumn columnName="Способ прикрепления" property="7"/>
        <msh:tableColumn columnName="Дата прикрепления" property="8"/>
        <msh:tableColumn columnName="Дата открепления" property="9"/>
        <msh:tableColumn columnName="Статус" property="10"/>
        <msh:tableColumn columnName="Дата импорта" property="11"/>
        <msh:tableColumn columnName="Дефект" property="12"/>
        <msh:tableColumn columnName="Страх. компания" property="13"/>
    </msh:table>
    <%
    }} else {%>
    <i>Введите данные </i>
    <%}%>

    <script type="text/javascript">

    </script>
</tiles:put>
</tiles:insert>