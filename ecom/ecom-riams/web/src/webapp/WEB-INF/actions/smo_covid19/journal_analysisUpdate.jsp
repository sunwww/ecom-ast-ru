<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <%
        //String typeDate = ActionUtil.updateParameter("BrList","typeDate","1", request) ;
    %>
    <tiles:put name="title" type="string">
        <msh:title mainMenu="StacJournal" title="Анализы COVID"></msh:title>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/journal_analysisUpdate.do" defaultField="dateBegin" method="POST">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:row>
                        <td></td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeViewBr" value="1">  Положительные
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeViewBr" value="2">  Отрицательные
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colSpan="2">
                            <input type="radio" name="typeViewBr" value="3">  Результат не введён
                        </td>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="dateBegin" label="Период с" />
                        <msh:textField property="dateEnd" label="по" />
                    </msh:row>
                </msh:row>
            </msh:panel>
        </msh:form>
        <form id="upload-form" class="upload-box" action="UpdateAnalysisCovid" method="post" enctype="multipart/form-data">
            <input type="file" id="file" name="file1" accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
            <span id="upload-error" class="error">${uploadError}</span>
            <input type="submit" id="upload-button" value="Загрузить файл" />
        </form>
        <%
            String date = (String)request.getParameter("dateBegin") ;
            String dateEnd = (String)request.getParameter("dateEnd") ;

            if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
        %>
        <script type='text/javascript'>

            //checkFieldUpdate('typeDate','${typeDate}',1) ;
            function checkFieldUpdate(aField,aValue,aDefault) {

                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)>max) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
            jQuery(function() {
                jQuery('#upload-form').ajaxForm({
                    success: function(res) {
                        alert(res);
                        alert("File has been uploaded successfully");
                    },
                    error: function(res) {
                        alert(res);
                        jQuery("#upload-error").text("Couldn't upload file");
                    }
                });
            });
        </script>
    </tiles:put>
</tiles:insert>