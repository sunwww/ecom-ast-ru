<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainInfomatLayout.jsp" flush="true">
    <tiles:put name="side" type="string">  
    <tags:sideMenu/>  	
    </tiles:put>
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Lpu">${infoRecord} Шаг 1. Выбор категории </msh:title>
    </tiles:put>
    <tiles:put name="style" type="string">
    <style type="text/css">
/*    tr.listFunctions {
    margin-top: 1px; margin-bottom: 1px;
    font:bold 1em Times;
    width:200px ;
    background-color:#080E73;
    font: bold 0.9em/1.2em Arial,Helvetica;
    margin-bottom: 1px;
    margin-top: 1px;
    width: 200px;
    margin: 0.1em;
    padding-bottom: 5px;
    padding-top: 5px;    
    color: white;
}*/
    
#side {
  display: none;
}
#content {
 margin-left: 0;
}
#header {
	display:none ;
}
    </style>
    </tiles:put>

    <tiles:put name='body' type='string'>
	    
	    <div id='navig'>

	    </div>
	    	    <div id='div1' style="float: left; height: 300px">
	    <msh:table name="listFunctions" action="step_diag_1.do" idField="1" hideTitle="true">
	    	<msh:tableColumn property="2" columnName="Наименование специальности"/>
	    </msh:table>
	    <a href='infomat.do' style='font-size: 2em;font-weight: bolder;text-align: center;'>НАЗАД</a>
	    </div>
	    <script type="text/javascript">
	    	//$('div1').style.display='none' ;
	    	var cntElementsList = 14 ;
	    	//viewList(1) ;
	    	
	    	function viewList(aList) {
	    		var tbodyDiv1 = $('div1').childNodes[1].childNodes[1].childNodes[1].childNodes ;
	    		var cntList = tbodyDiv1.length ; 
		    	var cntListAll = cntList/(cntElementsList*2) ;
		    	if ((cntList%(cntElementsList*2))>0) {
		    		cntListAll++ ;
		    	}
		    	var navigText="" ;
		    	
		    	for (var i=1;i<=cntListAll;i++) {
		    		navigText=navigText+" <div class='button' style='width:30px' onclick='viewList("+i+")'>";
		    		if (i==aList) {
		    			navigText=navigText+"<b>["+i+"]</b>" ;
		    		} else {
		    			navigText=navigText+"["+i+"]" ;
		    		}
		    		navigText=navigText+"</div>" ;
		    	}
		    	$('navig').innerHTML=navigText  ;
	    		
	    		
    			for (var i=0;i<cntList;i++) {
    				if (i%2==0) {
    					tbodyDiv1[i].style.display='none' ;
    				}
    			}
    			
		    	for (var i=(aList-1)*2*cntElementsList;i<aList*2*cntElementsList;i++) {
		    		if (i<cntList) {
		    			if (i%2==0) {
		    				tbodyDiv1[i].style.display='inline-block' ;
		    			}
		    		} else {
		    			break ;
		    		}
		    	}
		    	
	    		
	    	}
	    </script>
    </tiles:put>

</tiles:insert>