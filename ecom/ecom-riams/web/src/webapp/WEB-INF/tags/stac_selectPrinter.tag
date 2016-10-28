<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<%@ attribute name="name" required="true" description="Название поля Address" %>
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

<script src='./dwr/interface/AddressService.js' type="text/javascript"></script>
<msh:ifInRole roles="${roles}">
   
<script type="text/javascript">
function init${name}Printer(aPath) {
	the${name}Path = aPath;
	show${name}Printer();
}
</script>
	 </msh:ifInRole>

<msh:ifNotInRole roles="${roles}">
   
	<script type="text/javascript">
	function init${name}Printer(aPath) {
		the${name}Path = aPath;
		print${name}Print()
    }
	</script>
</msh:ifNotInRole>
<script type="text/javascript">



    var the${name}selectPrinter = new msh.widget.Dialog($('${name}selectPrinter')) ;
    
   var the${name}Path;
    function print${name}Txt(){
    	the${name}Path+="&printTxtFirst=1&next="+document.location.href;
    	print${name}Print();
    }
    function print${name}NoTxt(){
    	the${name}Path+="&printTxtFirst=0";
    	print${name}Print();
    }
    function print${name}Print() {
    	//alert (the${name}Path);
    	//alert ("href="+document.location.href);
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



