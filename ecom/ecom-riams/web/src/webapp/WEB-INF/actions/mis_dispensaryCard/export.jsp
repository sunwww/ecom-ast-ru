<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/tiles/header.jsp" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

<tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Экспорт карт диспансерного наблюдения"/>
</tiles:put>
<tiles:put name="body" type="string">
    <%
    String typeRead =ActionUtil.updateParameter("PatientAttachment","typeRead","file", request) ;
    ActionUtil.updateParameter("PatientAttachment","typeAge","3", request) ;
    ActionUtil.updateParameter("PatientAttachment","typeDispensaryStatus","3", request) ;
    %>

    <msh:form action="/exportDispensary.do" defaultField="startDate" disableFormDataConfirm="true" >
        <msh:panel>
            <msh:row>
                <msh:separator label="Параметры поиска" colSpan="7" />
            </msh:row>
            
            <msh:row>
                <msh:textField property="startDate" label="Период с" />
                <msh:textField property="finishDate" label="до" />
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
            <msh:row>
                <td class="label" title="Отобразить" colspan="1"><label for="typeReadName" id="typeReadLabel">Выгрузка:</label></td>
                <td onclick="this.childNodes[1].checked='checked';">
                    <input type="radio" name="typeRead" value="file"> В файл
                </td>
                <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                    <input type="radio" name="typeRead" value="screen"> На экран
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
                <msh:textField property="changedDateFrom" label="Измененные с" />
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
                checkFieldUpdate('typeRead','${typeRead}','file') ;
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
                    if ("screen"==jQuery('input[name="typeRead"]:checked').val()) {
                        document.forms[0].submit();
                        return 0;
                    }
                    var formJson=formToJson(jQuery('#mainForm'));
                    console.log("1= "+ JSON.stringify(formJson));
                    formJson=getFormDataAsJson(jQuery('#mainForm'));
                    console.log("2= "+ JSON.stringify(formJson));


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

                function checkFieldUpdate(aField,aValue,aDefaultValue) {
                    if (jQuery(":radio[name="+aField+"][value='"+aValue+"']").val()!=undefined) {
                        jQuery(":radio[name="+aField+"][value='"+aValue+"']").prop('checked',true);
                    } else {
                        jQuery(":radio[name="+aField+"][value='"+aDefaultValue+"']").prop('checked',true);
                    }
                }
            </script>
        </msh:panel>
    </msh:form>

    <%
    String startDate = request.getParameter("startDate") ;
    String finishDate = request.getParameter("finishDate") ;

    if ("screen".equals(typeRead) && startDate!=null && !startDate.equals(""))  {
       String editDate = request.getParameter("changedDateFrom");
       StringBuilder sqlWhere = new StringBuilder();
           sqlWhere.append(" where coalesce(d.finishDate, d.startDate) ");
           if (finishDate!=null && !finishDate.equals("")) {
               sqlWhere.append(" between to_date('").append(startDate).append("','dd.MM.yyyy') and to_date('").append(finishDate).append("','dd.MM.yyyy')");
           } else{
               sqlWhere.append(">=to_date('").append(startDate).append("','dd.MM.yyyy')");
           }
           if (editDate!=null) {
               sqlWhere.append(" and coalesce(d.editDate, d.createDate)>=to_date('").append(editDate).append("','dd.MM.yyyy')");
           }
       request.setAttribute("sqlAdd", sqlWhere.toString());

    %>
        <ecom:webQuery nameFldSql="journal_ticket_sql" name="journal_ticket" maxResult="2500" nativeSql="
            select d.id as f1, pat.lastname as f2 ,pat.firstname AS f3 ,pat.middlename AS f4 ,pat.birthday as f5_dr
            ,coalesce(mkb.code,'') as f6_ds ,d.startdate AS f7_startDate
            ,vwf.name ||' '||wpat.lastname||' '||wpat.firstname||' '||wpat.middlename as f8_doctor
            ,dep.name as f9_dep
            ,to_char(d.finishdate,'dd.MM.yyyy') || ' '||vde.name AS f10_end
     from dispensarycard d
     left join vocdispensaryend vde on vde.id=d.endreason_id
     left join workfunction wf on wf.id=d.workfunction_id
     left join worker w on w.id=wf.worker_id
     left join mislpu dep on dep.id=w.lpu_id
     left join patient wpat on wpat.id=w.person_id
     left join vocworkfunction vwf on vwf.id=wf.workfunction_id
     left join patient pat on pat.id=d.patient_id
     left join vocidc10 mkb on mkb.id=d.diagnosis_id
     ${sqlAdd} order by pat.id
              " />

    <msh:table printToExcelButton="Сохранить в excel" name="journal_ticket" action="entityView-mis_dispensaryCard.do" idField="1" noDataMessage="Не найдено">
        <msh:tableColumn columnName="#" property="sn"/>
        <msh:tableColumn columnName="Фамилия" property="2"/>
        <msh:tableColumn columnName="Имя" property="3"/>
        <msh:tableColumn columnName="Отчетство" property="4"/>
        <msh:tableColumn columnName="Дата рождения" property="5"/>
        <msh:tableColumn columnName="Диагноз" property="6"/>
        <msh:tableColumn columnName="Дата установки Д наблюдения" property="7"/>
        <msh:tableColumn columnName="Врач" property="8"/>
        <msh:tableColumn columnName="Отделение" property="9"/>
        <msh:tableColumn columnName="Снят?" property="10"/>
    </msh:table>
    <%
    } else {%>
    <i>Введите данные </i>
    <%}%>

    <script type="text/javascript">

    </script>
</tiles:put>
</tiles:insert>