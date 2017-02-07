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
    	
    	<br>
    	<div class="button_gosgarant delete" style="margin-top:40px;" onclick="window.location='start.do?infomat=${param.infomat}'">
    	<p class="label_gosgarant">Отмена</p>
    	<p class="info_gosgarant"></p>
    	</div>