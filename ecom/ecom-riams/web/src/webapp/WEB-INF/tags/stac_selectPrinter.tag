<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>


<%@ attribute name="name" required="true" description="Название поля" %>
<%@ attribute name="roles" required="true" description="Роль для выбора способа печати" %>




<style type="text/css">
    #${name}selectPrinter {
        visibility: hidden ;
        display: none ;
        position: absolute ;
    }

</style>

<div id='${name}selectPrinter' class='dialog'>
    <h2>Вы хотите распечатать документ на матричном принтере?</h2>
    <div class='rootPane'>


<form>
    <msh:panel>
        
 
        <tr>
            <td width = "20px" colspan="1">
                <input type="button" value='Да' onclick='print${name}Txt()'/>
                </td>
                <td width="100px" colspan="1" align='right' >
                <input type="button" value='Нет' onclick='print${name}NoTxt()'/>
            </td>
                <td width="250px" colspan="1" align='right' >
                <input type="button" value='Отмена' onclick='cancel${name}Printer()'/>
            </td>
        </tr>
           </msh:panel>
</form>

</div>
</div>

<msh:ifInRole roles="${roles}">
   
<script type="text/javascript">
function init${name}Printer(aPath, needNext, FormPost) {
	the${name}Path = aPath;
	the${name}NeedNext = needNext;
    the${name}FormPost = FormPost;
	show${name}Printer();
}
</script>
	 </msh:ifInRole>

<msh:ifNotInRole roles="${roles}">
   
	<script type="text/javascript">
	function init${name}Printer(aPath, needNext, FormPost) {
		the${name}Path = aPath;
        the${name}FormPost = FormPost;
		print${name}Print();
    }
	</script>
</msh:ifNotInRole>
<script type="text/javascript">



    var the${name}selectPrinter = new msh.widget.Dialog($('${name}selectPrinter')) ;
    
   var the${name}Path;
   var the${name}NeedNext;
    function print${name}Txt(){
    	the${name}Path+="&printTxtFirst=1";
    	if (the${name}NeedNext&&+the${name}NeedNext>0){
    		the${name}Path+="&next="+(document.location.href.replace("?","__"));
    	}
    	print${name}Print();
    }
    function print${name}NoTxt(){
    	the${name}Path+="&printTxtFirst=0";
    	print${name}Print();
    }
    function print${name}Print() {
    	if (the${name}FormPost) {
    	    //если нужен параметр printTxtFirst
    	    if ( the${name}Path.indexOf('&printTxtFirst') !=-1) {
                var par1 = document.createElement("input");
                par1.value = the${name}Path.indexOf('&printTxtFirst=1') !=-1 ? 1 : 0;
                par1.name = "printTxtFirst";
                the${name}FormPost.appendChild(par1);
            }
            if (the${name}NeedNext&&+the${name}NeedNext>0){
                var par2 = document.createElement("input");
                par2.value = document.location.href.replace("?","__");
                par2.name = "next";
                the${name}FormPost.appendChild(par2);
            }
            the${name}FormPost.submit();
        }
    	else
            window.location =the${name}Path;
    	cancel${name}Printer();
    }
    function show${name}Printer() {
    	the${name}selectPrinter.show();
    }
    
    function cancel${name}Printer() {
        the${name}selectPrinter.hide() ;
    }

 

    

</script>



