<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/mis" prefix="mis" %>%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%
    String username = LoginInfo.find(request.getSession()).getUsername();
    request.setAttribute("username", username);
%>


<%--<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">--%>
    <tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <%--    <!-- Upper -->
        <tiles:put name="title" type="string">
            <ecom:titleTrail mainMenu="Patient" beginForm="pharm_reportForm"/>
        </tiles:put>--%>

    <tiles:put name="body" type="string">

        <input id="cancel1" type="button" value="Отмена" onclick="goBack()"/>
        <input id="save1" type="button" value="Сохранить списание" onclick="save()"/>
            <ecom:webQuery name="goodsleave" nativeSql="
            select
            gl.id, vg.drug ,vg.form,cast(gl.pricefabrwnds as numeric)  ,cast(gl.uqntost as numeric) ,cast(gl.qntost as numeric), gl.srokg
            from goodsleave gl
            left join medcase m on m.id = ${param.id}
            left join workfunction wf on wf.id = m.workfunctionplan_id
            left join pharmnetstorage ps on ps.groupworkfuncid = wf.id
            left join vocbranch b on  b.branchid = ps.branchid
            left join vocgoods vg on vg.regid = gl.regid
            where gl.branchid = ps.branchid and gl.nextstate is null"/>
            <msh:tableNotEmpty name="goodsleave" >
                <msh:table  name="goodsleave" action="" idField="1" >
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="Наименование" property="2" />
                    <msh:tableColumn columnName="Форма" property="3" />
                    <msh:tableColumn columnName="Цена за упаковку" property="4" />
                    <msh:tableColumn columnName="Кол-во упаковок" property="5" identificator="aeCount" />
                    <msh:tableColumn columnName="Кол-во единиц" property="6" identificator="aCount" />
                    <msh:tableColumn columnName="Срок годности" property="7"/>
                    <msh:tableTextfield textfieldShortName="0" textfieldName="text" property="8"/>
                </msh:table>
            </msh:tableNotEmpty>

        <input id="cancel" type="button" value="Отмена" onclick="goBack()"/>
        <input id="save" type="button" value="Сохранить списание" onclick="save()"/>
    </tiles:put>
    <script type="text/javascript" src="./dwr/interface/PharmnetService.js"></script>
    <script language="javascript" type="text/javascript">
        function save() {

            var Array="{\"array\":[";
            var tds = document.getElementsByTagName("td");
            var f=false;
            for (var i = 0; i < tds.length; i++) {

                if(tds[i].className=' 6'){
                    var count = tds[i].value;
                }

                var txtfield = tds[i].getElementsByTagName("input");
                var JSON = "";

                for(var j=0;j<txtfield.length;j++){

                    var fild =txtfield[j];
                    var txt = fild.value;
                    if(txt!=null && txt!=0 && txt!=""){
                     if(f){Array+=",";}
                    //alert(txt);
                    //alert(fild.id.split('text_')[1]);
                    JSON ="{\"count\":\""+txt+"\" ,\"goodleaveId\":\""+fild.id.split('text_')[1]+"\"}";
                    f=true;
                    }
                }
                Array+=JSON;
            }
            Array+="]}";
            //alert(Array);
            var medcase = "${param.id}";
            var userNames= "${username}";
            createOut(Array,medcase,userNames);
        }


        function goBack(){
            location.href = "entityParentView-smo_visit.do?id=${param.id}";
        }


        function createOut(JSON,medcase,user) {

            PharmnetService.createOutcome(JSON,medcase,user, {
                callback : function(aResult) {
                        alert("Списание выполнено!");
                        goBack();
                }
            });
        }
    </script>
</tiles:insert>
