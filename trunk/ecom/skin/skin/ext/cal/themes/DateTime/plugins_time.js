/////////////////// Plug-in file for CalendarXP 9.0 /////////////////
// This file is totally configurable. You may remove all the comments in this file to minimize the download size.
/////////////////////////////////////////////////////////////////////

_animPop=false;

function fOnChange(y,m,d,e) {
	if (d==0) {
		var lastDay=fGetDays(y)[m];
		fUpdSelect(y,m,lastDay<gdSelect[2]?lastDay:gdSelect[2]);	// keep day of month updated
	} else {
		updateTimeStr();
		self.focus();
	}
}



function fOnResize() {
	// update the time fields on the calendar
	// you may move the following lines into the fParseInput() if you don't want to support NN4.
	var bf=document.cxpBottomForm;
	var t=_timeVal.match(_timeFormat);
	if (t) {
		bf.hourF.value=t[1];
		bf.minF.value=t[2];
		if (!_is24H) bf.ampm.value=t[3];
	}
}




function fParseInput(str) {
	if (gbHideCalMiddle) {
		_timeVal=formatTime(str);
		return [0,0,1]; // make date > 0 so as not to clear the input field
	} else {
		var dt=str.split(_separator_datetime);
		_timeVal=formatTime(str.substring(dt[0].length+_separator_datetime.length));
		return fParseDate(dt[0]);
	}
}


function fFormatInput(y,m,d) {
	if (gbHideCalMiddle)
		return _timeVal;
	else
		return fFormatDate(y,m,d)+_separator_datetime+_timeVal;
}



// ====== predefined utility functions for use with agendas. ========

// load an url in the window/frame designated by "framename".
function popup(url,framename) {	
	var w=parent.open(url,framename,"top=200,left=200,width=400,height=200,scrollbars=1,resizable=1");
	if (w&&url.split(":")[0]=="mailto") w.close();
	else if (w&&!framename) w.focus();
}

// ====== Following are self-defined and/or custom-built functions! =======



// ======= the following plugin is coded for the time picker ========
// To enable time picker in other themes, simply copy this part of code into their plugins.js files
// and merge the fOnResize, fParseInput and fFormatInput functions.

// If you hide top and middle part, you will get a time only picker.
gbHideTop=true;
gbHideCalMiddle=true;
gbHideBottom=false;
var _is24H=true;	// use 24-hour format or not.
var _hour_marker=":"; // the char between hour and minute
var _time_marker=" "; // the char between the time and "AM|PM".
var _separator_datetime=" "; // the char between date and time.
var _scrollTime=200;	// scrolling delay in milliseconds
var _inc=15;	// incremental time interval in minutes
var _AM="AM", _PM="PM";

var _timeVal,_timeFormat=new RegExp(_is24H?"^([0-1]?[0-9]|2[0-3])[^0-9]+([0-5]?[0-9])$":"^([0]?[1-9]|1[0-2])[^0-9]+([0-5]?[0-9]).*("+_AM+"|"+_PM+")$");
var styleStr=NN4?'':'style=" border:1px solid white"';
var imgPlusStr=' src="plus.gif" onmouseup="stopTime()" width="13" height="9" onmouseover="this.style.borderColor=\'black\'" onmouseout="stopTime();this.style.borderColor=\'white\'" '+styleStr;
var imgMinusStr=' src="minus.gif" onmouseup="stopTime()" width="13" height="9" onmouseover="this.style.borderColor=\'black\'" onmouseout="stopTime();this.style.borderColor=\'white\'" '+styleStr;

gsBottom=('<table align="center" border="0" cellpadding="0" cellspacing="0" width="1"><tr><td>&nbsp;</td><td><input type="text" name="hourF" size="2" maxlength="2" class="TimeBox" onchange="updateTimeStr()" onfocus="this.value=\'\'"></td><td><img onmousedown="incHour();" '+imgPlusStr+'><br><img onmousedown="decHour();" '+imgMinusStr+'></td><td nowrap>'+_hour_marker+'&nbsp;</td><td><input type="text" name="minF" size="2" maxlength="2" class="TimeBox" onchange="updateTimeStr()" onfocus="this.value=\'\'"></td><td><img onmousedown="incMin();" '+imgPlusStr+'><br><img onmousedown="decMin();" '+imgMinusStr+'></td>'+(_is24H?'':'<td>'+_time_marker+'&nbsp;</td><td><input type="Text" name="ampm" size="2" maxlength="2" class="TimeBox" readonly onfocus="flipAmPm();this.blur()"></td>')+'<td>&nbsp;&nbsp;</td><td valign="middle"><a href="javascript:void(0)" onclick="fHideCal();return false;"><img border="0" src="close.gif" title="Close" alt="Close" align="middle"></a></td><td>&nbsp;</td></tr></table>');
if(NN4)_nn4_css.push("TimeBox");

function time2str(hour, minute, ampm) { // format time and round it according to interval
	return padZero(hour)+_hour_marker+padZero(Math.floor(minute/_inc)*_inc)+(_is24H?'':_time_marker+ampm);
}

function formatTime(str) {
	if (_timeFormat.test(str)==false) { // use current time if str is invalid
		var nd=new Date(), h=nd.getHours(), sign=h>11?_PM:_AM;
		if (!_is24H&&(h>12||h==0)) h=Math.abs(h-12);
		return time2str(h,nd.getMinutes(),sign);
	} else
		return str;
}

function padZero(n) {
	n=parseInt(n,10);
	return n<10?'0'+n:n;
}

function updateTimeStr() {
	var bf=document.cxpBottomForm
	var hv=parseInt(bf.hourF.value,10), mv=parseInt(bf.minF.value,10);
	if (_is24H) bf.hourF.value=hv>=0&&hv<=23?padZero(hv):"00";
	else  bf.hourF.value=hv>=1&&hv<=12?padZero(hv):"12";
	bf.minF.value=mv>=0&&mv<=59?padZero(Math.floor(mv/_inc)*_inc):"00";
	_timeVal=time2str(bf.hourF.value,bf.minF.value,_is24H?"":bf.ampm.value);
	if (gdSelect[2]>0) gdCtrl.value=fFormatInput(gdSelect[0],gdSelect[1],gdSelect[2]);
}

var _th=null;
function incMin(){
	if (!_th) _th=setInterval(NN4?incMin:"incMin()",_scrollTime);  // must be first line
	var bf=document.cxpBottomForm, m=parseInt(bf.minF.value,10)+_inc;
	if (m>59) { m=0; incHour(); }
	bf.minF.value=padZero(m);
	updateTimeStr();
}
function decMin(){
	if (!_th) _th=setInterval(NN4?decMin:"decMin()",_scrollTime);  // must be first line
	var bf=document.cxpBottomForm, m=parseInt(bf.minF.value,10)-_inc;
	if (m<0) { m=60-_inc; decHour(); }
	bf.minF.value=padZero(m);
	updateTimeStr();
}
function incHour(){
	if (!_th) _th=setInterval(NN4?incHour:"incHour()",_scrollTime);
	var bf=document.cxpBottomForm, h=parseInt(bf.hourF.value,10), maxh=_is24H?23:12;
	if (++h>maxh) h=_is24H?0:1;
	if (h==12) flipAmPm();
	bf.hourF.value=padZero(h);
	updateTimeStr();
}
function decHour(){
	if (!_th) _th=setInterval(NN4?decHour:"decHour()",_scrollTime);
	var bf=document.cxpBottomForm, h=parseInt(bf.hourF.value,10);
	if (_is24H) {
		if (--h<0) h=23;
	} else
		if (--h==0) h=12; 
	if (h==11) flipAmPm();
	bf.hourF.value=padZero(h);
	updateTimeStr();
}
function stopTime(){
	clearInterval(_th);
	_th=null;
}
function flipAmPm() {
	if (_is24H) return;
	var bf=document.cxpBottomForm;
	bf.ampm.value=bf.ampm.value==_AM?_PM:_AM;
	updateTimeStr();
}
// ======= end of time picker plugin ========


