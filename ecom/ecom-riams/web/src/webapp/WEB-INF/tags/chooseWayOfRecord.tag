<%@ tag pageEncoding="utf8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<%@ attribute name="name" required="true" description="Название" %>


<style type="text/css">
    #chooseWayOfRecord {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }
</style>

<div id='${name}Choose' class='dialog'>

    <h2>Выберите способ обращения:</h2>
    <div class='rootPane'>
        <form action="javascript:void(0) ;" id="formId">
            <table width="100%" cellspacing="10" cellpadding="10" id="table${name}">
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var the${name}ChooseDialog = new msh.widget.Dialog($('${name}Choose')) ;
    function show${name}(aPatInfo,aPat,aTime,aFunc,aSpec,aDay,aServiceStream,oneOrMany) {
        preWayOfRecord='';
        var table = document.getElementById('table${name}');
        table.innerHTML="";
        WorkCalendarService.getWaysOfPreRecords({callback: function(res) {
                if (res!=null && res!='[]') {
                    var aResult = JSON.parse(res);
                    for (var i=0; i<aResult.length; i++) {
                        var tr = document.createElement('tr');
                        var td = document.createElement('td');
                        td.innerHTML = "<input type='button' style='width:100%;font-size:30px; margin:5px' value='"+aResult[i].name+
                            "' id='"+aResult[i].id+"' onclick=\" preWayOfRecord = this.id; the${name}ChooseDialog.hide() ; if ("+oneOrMany+") record('"
                            +aPatInfo+"','"+aPat+"','"+aFunc+"','"+aSpec+"','"+aDay+"','"+aTime+"','"+aServiceStream+"',this.id); else recordMany('"+aPatInfo+"','"+aPat+"','"+aTime+"',this.id);\"/>";
                        td.setAttribute("align","center");
                        tr.appendChild(td);
                        table.appendChild(tr);
                    }
                    the${name}ChooseDialog.show() ;
                }
                else {
                    showToastMessage('Ни одного способа записи не найдено.',null,true);
                    the${name}ChooseDialog.hide() ;
                }
            }
        });
    }
</script>