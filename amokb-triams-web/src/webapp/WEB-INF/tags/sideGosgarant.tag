<%@ tag pageEncoding="utf8" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ attribute name="curUrl" required="true" description="Название" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<% String curUrl = ""+getJspContext().getAttribute("curUrl") ;

String[] url = {"info","0"
		,"1","2","3","4","5","6","7","8","9","10"
		,"11","12","13","14","15","16","17","18","19","20"
		,"21","22","23","24","25","26","27"} ;
for (int i=0;i<url.length;i++) {
	if (curUrl.equals(url[i])) {
		request.setAttribute("step"+url[i], " checkbut") ;
		
	} else {
		request.setAttribute("step"+url[i], " ") ;
	}
	request.setAttribute("stepclick"+url[i],"window.location='step_gosgarant_"+url[i]+".do?infomat="+(request.getParameter("infomat")==null?"":request.getParameter("infomat"))+"'") ; ;
}
%>
<style>
#header #titleHead h1 {
    font-size: 1.7em;

}
.topbutton {
z-index:99 ;
width:10px;
border:2px solid #ccc;
background:#f7f7f7;
text-align:center;
padding:9px;
position:fixed;
bottom:20px;
right:0px;
cursor:pointer;
color:#333;
font-family:verdana;
font-size:12px;
border-radius: 5px;
-moz-border-radius: 5px;
-webkit-border-radius: 5px;
-khtml-border-radius: 5px;
}
<!--
h4 {
font-size: 1.2em;
text-align: center;
padding: 25px;
color: #0A0A2A;
}


.button_gosgarant {  
	cursor:pointer;     
    display: inline-block;
    white-space: nowrap;
    background-color: #DDD;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#EEE), to(#CCC));
    background-image: -webkit-linear-gradient(top, #EEE, #CCC);
    background-image: -moz-linear-gradient(top, #EEE, #CCC);
    background-image: -ms-linear-gradient(top, #EEE, #CCC);
    background-image: -o-linear-gradient(top, #EEE, #CCC);
    background-image: linear-gradient(top, #EEE, #CCC);
    border: 1px solid #777;
    padding: 0 1em;
    margin: 0.1em;
    font: bold 1.5em/2.2em Arial, Helvetica;
    text-decoration: none;
    color: #333;
    text-shadow: 0 1px 0 rgba(255, 255, 255, .8);
    -moz-border-radius: .2em;
    -webkit-border-radius: .2em;
    border-radius: .2em;
    -moz-box-shadow: 0 0 1px 1px rgba(255, 255 ,255, .8) inset, 0 1px 0 rgba(0, 0, 0, .3);
    -webkit-box-shadow: 0 0 1px 1px rgba(255, 255, 255, .8) inset, 0 1px 0 rgba(0, 0, 0, .3);
    box-shadow: 0 0 1px 1px rgba(255, 255, 255, .8) inset, 0 1px 0 rgba(0, 0, 0, .3);
    width: 120px ;
    
}
#side p.label_gosgarant {
	color: black;
	font: bold 0.8em/1.2em Arial, Helvetica;
}

#side p.info_gosgarant {
    font: 0.5em/0.7em Arial, Helvetica;
} 
p {
text-align: justify;
}
.button_gosgarant:hover,.button_gosgarant:active{
background-color: #0A0A2A;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#0A0A2A), to(#CCC));
    background-image: -webkit-linear-gradient(top, #0A0A2A, #CCC);
    background-image: -moz-linear-gradient(top, #0A0A2A, #CCC);
    background-image: -ms-linear-gradient(top, #0A0A2A, #CCC);
    background-image: -o-linear-gradient(top, #0A0A2A, #CCC);
    background-image: linear-gradient(top, #0A0A2A, #CCC);
}
.checkbut{
background-color: #F4A9A9;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#F4A9A9), to(#CCC));
    background-image: -webkit-linear-gradient(top, #F4A9A9, #CCC);
    background-image: -moz-linear-gradient(top, #F4A9A9, #CCC);
    background-image: -ms-linear-gradient(top, #F4A9A9, #CCC);
    background-image: -o-linear-gradient(top, #F4A9A9, #CCC);
    background-image: linear-gradient(top, #F4A9A9, #CCC);
}
-->
</style>
    <a href="#" title="Вернуться к началу"class="topbutton">^</a>
    	<div class="button_gosgarant ${stepinfo}" onclick="${stepclickinfo}">
    	<p class="label_gosgarant">Постановление</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step0}" onclick="${stepclick0}">
    	<p class="label_gosgarant">Программа</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step1}"  onclick="${stepclick1}">
    	<p class="label_gosgarant">Приложение 1.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step2}" onclick="${stepclick2}">
    	<p class="label_gosgarant">Приложение 2.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step3}" onclick="${stepclick3}">
    	<p class="label_gosgarant">Приложение 3.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step4}" onclick="${stepclick4}">
    	<p class="label_gosgarant">Приложение 4.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step5}" onclick="${stepclick5}">
    	<p class="label_gosgarant">Приложение 5.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step6}" onclick="${stepclick6}">
    	<p class="label_gosgarant">Приложение 6.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step7}" onclick="${stepclick7}">
    	<p class="label_gosgarant">Приложение 7.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step8}" onclick="${stepclick8}">
    	<p class="label_gosgarant">Приложение 8.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step9}" onclick="${stepclick9}">
    	<p class="label_gosgarant">Приложение 9.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step10}" onclick="${stepclick10}">
    	<p class="label_gosgarant">Приложение 10.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step11}" onclick="${stepclick11}">
    	<p class="label_gosgarant">Приложение 11.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step12}" onclick="${stepclick12}">
    	<p class="label_gosgarant">Приложение 12.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step13}" onclick="${stepclick13}">
    	<p class="label_gosgarant">Приложение 13.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step14}" onclick="${stepclick14}">
    	<p class="label_gosgarant">Приложение 14.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step15}" onclick="${stepclick15}">
    	<p class="label_gosgarant">Приложение 15.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step16}" onclick="${stepclick16}">
    	<p class="label_gosgarant">Приложение 16.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step17}" onclick="${stepclick17}">
    	<p class="label_gosgarant">Приложение 17.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step18}" onclick="${stepclick18}">
    	<p class="label_gosgarant">Приложение 18.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step19}" onclick="${stepclick19}">
    	<p class="label_gosgarant">Приложение 19.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step20}" onclick="${stepclick20}">
    	<p class="label_gosgarant">Приложение 20.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step21}" onclick="${stepclick21}">
    	<p class="label_gosgarant">Приложение 21.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step22}" onclick="${stepclick22}">
    	<p class="label_gosgarant">Приложение 22.</p>
    	<p class="info_gosgarant"></p>
    	</div>
    	<div class="button_gosgarant ${step23}" onclick="${stepclick23}">
    	<p class="label_gosgarant">Приложение 22.</p>
    	<p class="info_gosgarant">продолжение</p>
    	</div>
    	<div class="button_gosgarant ${step24}" onclick="${stepclick24}">
    	<p class="label_gosgarant">Приложение 22.</p>
    	<p class="info_gosgarant">продолжение</p>
    	</div>
    	<%-- <div class="button_gosgarant ${step25}" onclick="${stepclick25}">
    	<p class="label_gosgarant">Приложение 22.</p>
    	<p class="info_gosgarant">продолжение</p>
    	</div>
    	<div class="button_gosgarant ${step26}" onclick="${stepclick26}">
    	<p class="label_gosgarant">Приложение 22.</p>
    	<p class="info_gosgarant">продолжение</p>
    	</div>
    	<div class="button_gosgarant ${step27}" onclick="${stepclick27}">
    	<p class="label_gosgarant">Приложение 22.</p>
    	<p class="info_gosgarant">продолжение</p>
    	</div> --%>
    	<br>
    	<div class="button_gosgarant delete" style="margin-top:40px;" onclick="window.location='start.do?infomat=${param.infomat}'">
    	<p class="label_gosgarant">Отмена</p>
    	<p class="info_gosgarant"></p>
    	</div>