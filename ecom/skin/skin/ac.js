var Prototype={Version:"1.3.1",emptyFunction:function(){
}};
var Class={create:function(){
return function(){
this.initialize.apply(this,arguments);
};
}};
var Abstract=new Object();
Object.extend=function(_1,_2){
for(property in _2){
_1[property]=_2[property];
}
return _1;
};
Object.prototype.extend=function(_3){
return Object.extend.apply(this,[this,_3]);
};
Function.prototype.bind=function(_4){
var _5=this;
return function(){
_5.apply(_4,arguments);
};
};
Function.prototype.bindAsEventListener=function(_6){
var _7=this;
return function(_8){
var _9=new Array();
_9[0]=_8||window.event;
_7.apply(_6,_9);
};
};
Number.prototype.toColorPart=function(){
var _a=this.toString(16);
if(this<16){
return "0"+_a;
}
return _a;
};
var Try={these:function(){
var _b;
for(var i=0;i<arguments.length;i++){
var _d=arguments[i];
try{
_b=_d();
break;
}
catch(e){
}
}
return _b;
}};
var PeriodicalExecuter=Class.create();
PeriodicalExecuter.prototype={initialize:function(_e,_f){
this.callback=_e;
this.frequency=_f;
this.currentlyExecuting=false;
this.registerCallback();
},registerCallback:function(){
setInterval(this.onTimerEvent.bind(this),this.frequency*1000);
},onTimerEvent:function(){
if(!this.currentlyExecuting){
try{
this.currentlyExecuting=true;
this.callback();
}
finally{
this.currentlyExecuting=false;
}
}
}};
function $(){
var _10=new Array();
for(var i=0;i<arguments.length;i++){
var _12=arguments[i];
if(typeof _12=="string"){
_12=document.getElementById(_12);
}
if(arguments.length==1){
return _12;
}
_10.push(_12);
}
return _10;
}
if(!Array.prototype.push){
Array.prototype.push=function(){
var _13=this.length;
for(var i=0;i<arguments.length;i++){
this[_13+i]=arguments[i];
}
return this.length;
};
}
if(!Function.prototype.apply){
Function.prototype.apply=function(_15,_16){
var _17=new Array();
if(!_15){
_15=window;
}
if(!_16){
_16=new Array();
}
for(var i=0;i<_16.length;i++){
_17[i]="parameters["+i+"]";
}
_15.__apply__=this;
var _19=eval("object.__apply__("+_17.join(", ")+")");
_15.__apply__=null;
return _19;
};
}
String.prototype.extend({stripTags:function(){
return this.replace(/<\/?[^>]+>/gi,"");
},escapeHTML:function(){
var div=document.createElement("div");
var _1b=document.createTextNode(this);
div.appendChild(_1b);
return div.innerHTML;
},unescapeHTML:function(){
var div=document.createElement("div");
div.innerHTML=this.stripTags();
return div.childNodes[0].nodeValue;
}});
var Ajax={getTransport:function(){
return Try.these(function(){
return new ActiveXObject("Msxml2.XMLHTTP");
},function(){
return new ActiveXObject("Microsoft.XMLHTTP");
},function(){
return new XMLHttpRequest();
})||false;
}};
Ajax.Base=function(){
};
Ajax.Base.prototype={setOptions:function(_1d){
this.options={method:"POST",asynchronous:true,parameters:""}.extend(_1d||{});
},responseIsSuccess:function(){
var ret=this.transport.status=="undefined"||this.transport.status==0||(this.transport.status>=200&&this.transport.status<300);
return ret;
},responseIsFailure:function(){
return !this.responseIsSuccess();
}};
Ajax.Request=Class.create();
Ajax.Request.Events=["Uninitialized","Loading","Loaded","Interactive","Complete"];
Ajax.Request.prototype=(new Ajax.Base()).extend({initialize:function(url,_20){
this.transport=Ajax.getTransport();
this.setOptions(_20);
this.request(url);
},request:function(url){
var _22=this.options.parameters||"";
if(_22.length>0){
_22+="&_=";
}
try{
if(this.options.method=="get"){
url+="?"+_22;
}
this.transport.open(this.options.method,url,this.options.asynchronous);
if(this.options.asynchronous){
this.transport.onreadystatechange=this.onStateChange.bind(this);
setTimeout((function(){
this.respondToReadyState(1);
}).bind(this),10);
}
this.setRequestHeaders();
var _23=this.options.postBody?this.options.postBody:_22;
this.transport.send(this.options.method=="POST"?_23:null);
}
catch(e){
}
},setRequestHeaders:function(){
var _24=["X-Requested-With","XMLHttpRequest","X-Prototype-Version",Prototype.Version];
if(this.options.method=="POST"){
_24.push("Content-type","application/x-www-form-urlencoded");
if(this.transport.overrideMimeType){
_24.push("Connection","close");
}
}
if(this.options.requestHeaders){
_24.push.apply(_24,this.options.requestHeaders);
}
for(var i=0;i<_24.length;i+=2){
this.transport.setRequestHeader(_24[i],_24[i+1]);
}
},onStateChange:function(){
var _26=this.transport.readyState;
if(_26!=1){
this.respondToReadyState(this.transport.readyState);
}
},respondToReadyState:function(_27){
var _28=Ajax.Request.Events[_27];
if(_28=="Complete"){
(this.options["on"+this.transport.status]||this.options["on"+(this.responseIsSuccess()?"Success":"Failure")]||Prototype.emptyFunction)(this.transport);
}
(this.options["on"+_28]||Prototype.emptyFunction)(this.transport);
if(_28=="Complete"){
this.transport.onreadystatechange=Prototype.emptyFunction;
}
}});
Ajax.Updater=Class.create();
Ajax.Updater.ScriptFragment="(?:<script.*?>)((\n|.)*?)(?:</script>)";
Ajax.Updater.prototype.extend(Ajax.Request.prototype).extend({initialize:function(_29,url,_2b){
this.containers={success:_29.success?$(_29.success):$(_29),failure:_29.failure?$(_29.failure):(_29.success?null:$(_29))};
this.transport=Ajax.getTransport();
this.setOptions(_2b);
var _2c=this.options.onComplete||Prototype.emptyFunction;
this.options.onComplete=(function(){
this.updateContent();
_2c(this.transport);
}).bind(this);
this.request(url);
},updateContent:function(){
var _2d=this.responseIsSuccess()?this.containers.success:this.containers.failure;
var _2e=new RegExp(Ajax.Updater.ScriptFragment,"img");
var _2f=this.transport.responseText.replace(_2e,"");
var _30=this.transport.responseText.match(_2e);
if(_2d){
if(this.options.insertion){
new this.options.insertion(_2d,_2f);
}else{
_2d.innerHTML=_2f;
}
}
if(this.responseIsSuccess()){
if(this.onComplete){
setTimeout((function(){
this.onComplete(this.transport);
}).bind(this),10);
}
}
if(this.options.evalScripts&&_30){
_2e=new RegExp(Ajax.Updater.ScriptFragment,"im");
setTimeout((function(){
for(var i=0;i<_30.length;i++){
eval(_30[i].match(_2e)[1]);
}
}).bind(this),10);
}
}});
Ajax.PeriodicalUpdater=Class.create();
Ajax.PeriodicalUpdater.prototype=(new Ajax.Base()).extend({initialize:function(_32,url,_34){
this.setOptions(_34);
this.onComplete=this.options.onComplete;
this.frequency=(this.options.frequency||2);
this.decay=1;
this.updater={};
this.container=_32;
this.url=url;
this.start();
},start:function(){
this.options.onComplete=this.updateComplete.bind(this);
this.onTimerEvent();
},stop:function(){
this.updater.onComplete=undefined;
clearTimeout(this.timer);
(this.onComplete||Ajax.emptyFunction).apply(this,arguments);
},updateComplete:function(_35){
if(this.options.decay){
this.decay=(_35.responseText==this.lastText?this.decay*this.options.decay:1);
this.lastText=_35.responseText;
}
this.timer=setTimeout(this.onTimerEvent.bind(this),this.decay*this.frequency*1000);
},onTimerEvent:function(){
this.updater=new Ajax.Updater(this.container,this.url,this.options);
}});
document.getElementsByClassName=function(_36){
var _37=document.getElementsByTagName("*")||document.all;
var _38=new Array();
for(var i=0;i<_37.length;i++){
var _3a=_37[i];
var _3b=_3a.className.split(" ");
for(var j=0;j<_3b.length;j++){
if(_3b[j]==_36){
_38.push(_3a);
break;
}
}
}
return _38;
};
if(!window.Element){
var Element=new Object();
}
Object.extend(Element,{toggle:function(){
for(var i=0;i<arguments.length;i++){
var _3e=$(arguments[i]);
_3e.style.display=(_3e.style.display=="none"?"":"none");
}
},hide:function(){
for(var i=0;i<arguments.length;i++){
var _40=$(arguments[i]);
_40.style.display="none";
}
},show:function(){
for(var i=0;i<arguments.length;i++){
var _42=$(arguments[i]);
_42.style.display="";
}
},remove:function(_43){
_43=$(_43);
_43.parentNode.removeChild(_43);
},getHeight:function(_44){
_44=$(_44);
return _44.offsetHeight;
},hasClassName:function(_45,_46){
_45=$(_45);
if(!_45){
return;
}
var a=_45.className.split(" ");
for(var i=0;i<a.length;i++){
if(a[i]==_46){
return true;
}
}
return false;
},addClassName:function(_49,_4a){
_49=$(_49);
if(_49){
Element.removeClassName(_49,_4a);
_49.className+=" "+_4a;
}
},removeClassName:function(_4b,_4c){
_4b=$(_4b);
if(!_4b){
return;
}
var _4d="";
var a=_4b.className.split(" ");
for(var i=0;i<a.length;i++){
if(a[i]!=_4c){
if(i>0){
_4d+=" ";
}
_4d+=a[i];
}
}
_4b.className=_4d;
},cleanWhitespace:function(_50){
var _50=$(_50);
for(var i=0;i<_50.childNodes.length;i++){
var _52=_50.childNodes[i];
if(_52.nodeType==3&&!/\S/.test(_52.nodeValue)){
Element.remove(_52);
}
}
}});
var Toggle=new Object();
Toggle.display=Element.toggle;
Abstract.Insertion=function(_53){
this.adjacency=_53;
};
Abstract.Insertion.prototype={initialize:function(_54,_55){
this.element=$(_54);
this.content=_55;
if(this.adjacency&&this.element.insertAdjacentHTML){
this.element.insertAdjacentHTML(this.adjacency,this.content);
}else{
this.range=this.element.ownerDocument.createRange();
if(this.initializeRange){
this.initializeRange();
}
this.fragment=this.range.createContextualFragment(this.content);
this.insertContent();
}
}};
var Insertion=new Object();
Insertion.Before=Class.create();
Insertion.Before.prototype=(new Abstract.Insertion("beforeBegin")).extend({initializeRange:function(){
this.range.setStartBefore(this.element);
},insertContent:function(){
this.element.parentNode.insertBefore(this.fragment,this.element);
}});
Insertion.Top=Class.create();
Insertion.Top.prototype=(new Abstract.Insertion("afterBegin")).extend({initializeRange:function(){
this.range.selectNodeContents(this.element);
this.range.collapse(true);
},insertContent:function(){
this.element.insertBefore(this.fragment,this.element.firstChild);
}});
Insertion.Bottom=Class.create();
Insertion.Bottom.prototype=(new Abstract.Insertion("beforeEnd")).extend({initializeRange:function(){
this.range.selectNodeContents(this.element);
this.range.collapse(this.element);
},insertContent:function(){
this.element.appendChild(this.fragment);
}});
Insertion.After=Class.create();
Insertion.After.prototype=(new Abstract.Insertion("afterEnd")).extend({initializeRange:function(){
this.range.setStartAfter(this.element);
},insertContent:function(){
this.element.parentNode.insertBefore(this.fragment,this.element.nextSibling);
}});
var Field={clear:function(){
for(var i=0;i<arguments.length;i++){
$(arguments[i]).value="";
}
},focus:function(_57){
$(_57).focus();
},present:function(){
for(var i=0;i<arguments.length;i++){
if($(arguments[i]).value==""){
return false;
}
}
return true;
},select:function(_59){
$(_59).select();
},activate:function(_5a){
$(_5a).focus();
$(_5a).select();
}};
var Form={serialize:function(_5b){
var _5c=Form.getElements($(_5b));
var _5d=new Array();
for(var i=0;i<_5c.length;i++){
var _5f=Form.Element.serialize(_5c[i]);
if(_5f){
_5d.push(_5f);
}
}
return _5d.join("&");
},getElements:function(_60){
var _60=$(_60);
var _61=new Array();
for(tagName in Form.Element.Serializers){
var _62=_60.getElementsByTagName(tagName);
for(var j=0;j<_62.length;j++){
_61.push(_62[j]);
}
}
return _61;
},getInputs:function(_64,_65,_66){
var _64=$(_64);
var _67=_64.getElementsByTagName("input");
if(!_65&&!_66){
return _67;
}
var _68=new Array();
for(var i=0;i<_67.length;i++){
var _6a=_67[i];
if((_65&&_6a.type!=_65)||(_66&&_6a.name!=_66)){
continue;
}
_68.push(_6a);
}
return _68;
},disable:function(_6b){
var _6c=Form.getElements(_6b);
for(var i=0;i<_6c.length;i++){
var _6e=_6c[i];
_6e.blur();
_6e.disabled="true";
}
},enable:function(_6f){
var _70=Form.getElements(_6f);
for(var i=0;i<_70.length;i++){
var _72=_70[i];
_72.disabled="";
}
},focusFirstElement:function(_73){
var _73=$(_73);
var _74=Form.getElements(_73);
for(var i=0;i<_74.length;i++){
var _76=_74[i];
if(_76.type!="hidden"&&!_76.disabled){
Field.activate(_76);
break;
}
}
},reset:function(_77){
$(_77).reset();
}};
Form.Element={serialize:function(_78){
var _78=$(_78);
var _79=_78.tagName.toLowerCase();
var _7a=Form.Element.Serializers[_79](_78);
if(_7a){
return encodeURIComponent(_7a[0])+"="+encodeURIComponent(_7a[1]);
}
},getValue:function(_7b){
var _7b=$(_7b);
var _7c=_7b.tagName.toLowerCase();
var _7d=Form.Element.Serializers[_7c](_7b);
if(_7d){
return _7d[1];
}
}};
Form.Element.Serializers={input:function(_7e){
switch(_7e.type.toLowerCase()){
case "submit":
case "hidden":
case "password":
case "text":
return Form.Element.Serializers.textarea(_7e);
case "checkbox":
case "radio":
return Form.Element.Serializers.inputSelector(_7e);
}
return false;
},inputSelector:function(_7f){
if(_7f.checked){
return [_7f.name,_7f.value];
}
},textarea:function(_80){
return [_80.name,_80.value];
},select:function(_81){
var _82="";
if(_81.type=="select-one"){
var _83=_81.selectedIndex;
if(_83>=0){
_82=_81.options[_83].value||_81.options[_83].text;
}
}else{
_82=new Array();
for(var i=0;i<_81.length;i++){
var opt=_81.options[i];
if(opt.selected){
_82.push(opt.value||opt.text);
}
}
}
return [_81.name,_82];
}};
var $F=Form.Element.getValue;
Abstract.TimedObserver=function(){
};
Abstract.TimedObserver.prototype={initialize:function(_86,_87,_88){
this.frequency=_87;
this.element=$(_86);
this.callback=_88;
this.lastValue=this.getValue();
this.registerCallback();
},registerCallback:function(){
setInterval(this.onTimerEvent.bind(this),this.frequency*1000);
},onTimerEvent:function(){
var _89=this.getValue();
if(this.lastValue!=_89){
this.callback(this.element,_89);
this.lastValue=_89;
}
}};
Form.Element.Observer=Class.create();
Form.Element.Observer.prototype=(new Abstract.TimedObserver()).extend({getValue:function(){
return Form.Element.getValue(this.element);
}});
Form.Observer=Class.create();
Form.Observer.prototype=(new Abstract.TimedObserver()).extend({getValue:function(){
return Form.serialize(this.element);
}});
Abstract.EventObserver=function(){
};
Abstract.EventObserver.prototype={initialize:function(_8a,_8b){
this.element=$(_8a);
this.callback=_8b;
this.lastValue=this.getValue();
if(this.element.tagName.toLowerCase()=="form"){
this.registerFormCallbacks();
}else{
this.registerCallback(this.element);
}
},onElementEvent:function(){
var _8c=this.getValue();
if(this.lastValue!=_8c){
this.callback(this.element,_8c);
this.lastValue=_8c;
}
},registerFormCallbacks:function(){
var _8d=Form.getElements(this.element);
for(var i=0;i<_8d.length;i++){
this.registerCallback(_8d[i]);
}
},registerCallback:function(_8f){
if(_8f.type){
switch(_8f.type.toLowerCase()){
case "checkbox":
case "radio":
_8f.target=this;
_8f.prev_onclick=_8f.onclick||Prototype.emptyFunction;
_8f.onclick=function(){
this.prev_onclick();
this.target.onElementEvent();
};
break;
case "password":
case "text":
case "textarea":
case "select-one":
case "select-multiple":
_8f.target=this;
_8f.prev_onchange=_8f.onchange||Prototype.emptyFunction;
_8f.onchange=function(){
this.prev_onchange();
this.target.onElementEvent();
};
break;
}
}
}};
Form.Element.EventObserver=Class.create();
Form.Element.EventObserver.prototype=(new Abstract.EventObserver()).extend({getValue:function(){
return Form.Element.getValue(this.element);
}});
Form.EventObserver=Class.create();
Form.EventObserver.prototype=(new Abstract.EventObserver()).extend({getValue:function(){
return Form.serialize(this.element);
}});
if(!window.Event){
var Event=new Object();
}
Object.extend(Event,{KEY_BACKSPACE:8,KEY_TAB:9,KEY_RETURN:13,KEY_ESC:27,KEY_LEFT:37,KEY_UP:38,KEY_RIGHT:39,KEY_DOWN:40,KEY_DELETE:46,element:function(_90){
return _90.target||_90.srcElement;
},isLeftClick:function(_91){
return (((_91.which)&&(_91.which==1))||((_91.button)&&(_91.button==1)));
},pointerX:function(_92){
return _92.pageX||(_92.clientX+(document.documentElement.scrollLeft||document.body.scrollLeft));
},pointerY:function(_93){
return _93.pageY||(_93.clientY+(document.documentElement.scrollTop||document.body.scrollTop));
},stop:function(_94){
if(_94.preventDefault){
_94.preventDefault();
_94.stopPropagation();
}else{
_94.returnValue=false;
}
},findElement:function(_95,_96){
var _97=Event.element(_95);
while(_97.parentNode&&(!_97.tagName||(_97.tagName.toUpperCase()!=_96.toUpperCase()))){
_97=_97.parentNode;
}
return _97;
},observers:false,_observeAndCache:function(_98,_99,_9a,_9b){
if(!this.observers){
this.observers=[];
}
if(_98.addEventListener){
this.observers.push([_98,_99,_9a,_9b]);
_98.addEventListener(_99,_9a,_9b);
}else{
if(_98.attachEvent){
this.observers.push([_98,_99,_9a,_9b]);
_98.attachEvent("on"+_99,_9a);
}
}
},unloadCache:function(){
if(!Event.observers){
return;
}
for(var i=0;i<Event.observers.length;i++){
Event.stopObserving.apply(this,Event.observers[i]);
Event.observers[i][0]=null;
}
Event.observers=false;
},observe:function(_9d,_9e,_9f,_a0){
var _9d=$(_9d);
_a0=_a0||false;
if(_9e=="keypress"&&((navigator.appVersion.indexOf("AppleWebKit")>0)||_9d.attachEvent)){
_9e="keydown";
}
this._observeAndCache(_9d,_9e,_9f,_a0);
},stopObserving:function(_a1,_a2,_a3,_a4){
var _a1=$(_a1);
_a4=_a4||false;
if(_a2=="keypress"&&((navigator.appVersion.indexOf("AppleWebKit")>0)||_a1.detachEvent)){
_a2="keydown";
}
if(_a1.removeEventListener){
_a1.removeEventListener(_a2,_a3,_a4);
}else{
if(_a1.detachEvent){
_a1.detachEvent("on"+_a2,_a3);
}
}
}});
Event.observe(window,"unload",Event.unloadCache,false);
var Position={includeScrollOffsets:false,prepare:function(){
this.deltaX=window.pageXOffset||document.documentElement.scrollLeft||document.body.scrollLeft||0;
this.deltaY=window.pageYOffset||document.documentElement.scrollTop||document.body.scrollTop||0;
},realOffset:function(_a5){
var _a6=0,_a7=0;
do{
_a6+=_a5.scrollTop||0;
_a7+=_a5.scrollLeft||0;
_a5=_a5.parentNode;
}while(_a5);
return [_a7,_a6];
},cumulativeOffset:function(_a8){
var _a9=0,_aa=0;
do{
_a9+=_a8.offsetTop||0;
_aa+=_a8.offsetLeft||0;
_a8=_a8.offsetParent;
}while(_a8);
return [_aa,_a9];
},within:function(_ab,x,y){
if(this.includeScrollOffsets){
return this.withinIncludingScrolloffsets(_ab,x,y);
}
this.xcomp=x;
this.ycomp=y;
this.offset=this.cumulativeOffset(_ab);
return (y>=this.offset[1]&&y<this.offset[1]+_ab.offsetHeight&&x>=this.offset[0]&&x<this.offset[0]+_ab.offsetWidth);
},withinIncludingScrolloffsets:function(_ae,x,y){
var _b1=this.realOffset(_ae);
this.xcomp=x+_b1[0]-this.deltaX;
this.ycomp=y+_b1[1]-this.deltaY;
this.offset=this.cumulativeOffset(_ae);
return (this.ycomp>=this.offset[1]&&this.ycomp<this.offset[1]+_ae.offsetHeight&&this.xcomp>=this.offset[0]&&this.xcomp<this.offset[0]+_ae.offsetWidth);
},overlap:function(_b2,_b3){
if(!_b2){
return 0;
}
if(_b2=="vertical"){
return ((this.offset[1]+_b3.offsetHeight)-this.ycomp)/_b3.offsetHeight;
}
if(_b2=="horizontal"){
return ((this.offset[0]+_b3.offsetWidth)-this.xcomp)/_b3.offsetWidth;
}
},clone:function(_b4,_b5){
_b4=$(_b4);
_b5=$(_b5);
_b5.style.position="absolute";
var _b6=this.cumulativeOffset(_b4);
_b5.style.top=_b6[1]+"px";
_b5.style.left=_b6[0]+"px";
_b5.style.width=_b4.offsetWidth+"px";
_b5.style.height=_b4.offsetHeight+"px";
}};
var tablearrow={Version:"1.0"};
var theTableArrow=null;
tablearrow.TableArrow=function(_b7){
var _b8=document;
var _b9=null;
var _ba=0;
var _bb=null;
_b9=getRows();
if(_b9!=null&&_b9.length!=0){
try{
document.attachEvent("onkeydown",onKey);
}
catch(e){
try{
document.addEventListener("keypress",onKey,false);
}
catch(e){
}
}
selectFirst();
}
this.onCheckBoxClickAll=function(_bc){
var _bd=_bc.checked;
_bc.checked=false;
var _be=_bc.id;
var _bf=getRows();
var atr;
for(var i=0;i<_bf.length;i++){
var row=_bf[i];
if(row!=null&&Element.hasClassName(row,_be)){
if((_bd&&Element.hasClassName(row,"inserted"))||(!_bd&&!Element.hasClassName(row,"inserted"))){
}else{
insert(row);
}
}
}
};
this.onCheckBoxClickInvert=function(_c3){
var _c4=_c3.checked;
_c3.checked=false;
var _c5=_c3.id;
var _c6=getRows();
var atr;
for(var i=0;i<_c6.length;i++){
var row=_c6[i];
if(row!=null&&Element.hasClassName(row,_c5)){
insert(row);
}
}
};
this.getInsertedIdsAsParams=function(_ca,_cb){
var ids=this.getInsertedIds(_cb);
if(ids){
var str="";
for(var i=0;i<ids.length;i++){
if(ids[i]!="inserted"){
if(_ca!=null&&_ca!=""){
str+=_ca+"="+ids[i];
if(i!=ids.length-1){
str+="&";
}
}else{
str+=ids[i];
if(i!=ids.length-1){
str+=",";
}
}
}
}
return str;
}else{
return null;
}
};
this.remove=function(){
try{
document.detachEvent("onkeydown",onKey);
}
catch(e){
try{
document.removeEventListener("keypress",onKey,false);
}
catch(e){
}
}
};
this.onCheckBoxClick=function(_cf){
var row=_cf.parentNode.parentNode;
insert(row);
};
this.getInsertedIds=function(_d1){
var ret=new Array();
var _d3=getRows();
for(var i=0;i<_d3.length;i++){
var row=_d3[i];
if(row!=null&&(_d1==null||_d1==""||Element.hasClassName(row,_d1))&&Element.hasClassName(row,"inserted")){
var _d6=row.className.split(" ");
if(_d6.length>1){
ret.push(_d6[1]);
}
}else{
}
}
return ret.length!=0?ret:null;
};
function getRows(){
if(_b9==null){
_b9=_b8.getElementsByTagName("tr");
}
return _b9;
}
function select(_d7){
Element.addClassName(_d7,"selected");
}
function deselect(_d8){
Element.removeClassName(_d8,"selected");
}
function insert(_d9){
var _da=_d9.className.split(" ");
var _db=$(_da[0]+"_"+_da[1]);
if(Element.hasClassName(_d9,"inserted")){
Element.removeClassName(_d9,"inserted");
if(_db){
_db.checked=false;
}
}else{
Element.addClassName(_d9,"inserted");
if(_db){
_db.checked=true;
}
}
}
function insertCurrent(){
var row=getSelectedRow();
if(row){
insert(row);
}
}
function getSelectedIndex(){
if(_bb==null){
var _dd=getRows();
for(var i=0;i<_dd.length;i++){
if(Element.hasClassName(_dd[i],"selected")){
_bb=i;
break;
}
}
}
return _bb;
}
function isUndefinedRowAction(_df){
try{
return _df.onclick==undefined;
}
catch(e){
}
try{
return _df.onclick==null;
}
catch(e){
}
return false;
}
function setSelectedIndex(_e0){
var _e1=getRows();
deselect(_e1[_bb]);
select(_e1[_e0]);
_bb=_e0;
}
function selectLast(){
var _e2=getRows();
setSelectedIndex(_e2.length-1);
}
function selectFirst(){
var _e3=getRows();
_ba=0;
for(var i=0;i<_e3.length;i++){
if(!isUndefinedRowAction(_e3[i])){
_ba=i;
break;
}
}
setSelectedIndex(_ba);
}
function getNextAllowedIndex(_e5){
var _e6=getRows();
for(var i=_e5;i<_e6.length;i++){
if(!isUndefinedRowAction(_e6[i])){
return i;
}
}
return null;
}
function getPreviousAllowedIndex(_e8){
var _e9=getRows();
for(var i=_e8;i>=_ba;i--){
if(!isUndefinedRowAction(_e9[i])){
return i;
}
}
return null;
}
function selectPageDown(){
if(getSelectedIndex()!=getRows().length-1){
for(var i=0;i<10;i++){
selectNext();
if(getSelectedIndex()==getRows().length-1){
break;
}
}
}
}
function selectPageUp(){
if(getSelectedIndex()!=_ba){
for(var i=0;i<10;i++){
selectPrevious();
if(getSelectedIndex()==_ba){
break;
}
}
}
}
function selectNext(){
var _ed=getSelectedIndex();
if(_ed==null){
selectFirst();
}else{
var sel=getNextAllowedIndex(_ed+1);
if(sel!=null&&sel<getRows().length){
setSelectedIndex(sel);
}else{
selectFirst();
}
}
}
function selectPrevious(){
var _ef=getSelectedIndex();
if(_ef==null){
selectLast();
}else{
var sel=getPreviousAllowedIndex(_ef-1);
if(sel!=null&&sel>=_ba){
setSelectedIndex(sel);
}else{
selectLast();
}
}
}
function getSelectedRow(){
var sel=getSelectedIndex();
if(sel!=null){
return getRows()[sel];
}else{
return null;
}
}
function enter(){
var tr=getSelectedRow();
if(tr!=null){
if(!isUndefinedRowAction(tr)){
try{
var _f3=tr.childNodes.length-2;
tr.childNodes[_f3].onclick();
}
catch(e){
tr.childNodes[0].onclick();
}
}
}
}
function onKey(_f4){
var _f5=_f4.keyCode;
if(40==_f5){
selectNext();
}else{
if(38==_f5){
selectPrevious();
}else{
if(eventutil.VK_INSERT==_f5){
insertCurrent();
selectNext();
}else{
if(13==_f5){
enter();
}else{
if(35==_f5){
selectLast();
}else{
if(36==_f5){
selectFirst();
}else{
if(34==_f5){
selectPageDown();
}else{
if(33==_f5){
selectPageUp();
}else{
if(112==_f5){
window.status=_f5;
return false;
}
}
}
}
}
}
}
}
}
return false;
}
};
var JSON=function(){
var m={"\b":"\\b","\t":"\\t","\n":"\\n","\f":"\\f","\r":"\\r","\"":"\\\"","\\":"\\\\"},s={"boolean":function(x){
return String(x);
},number:function(x){
return isFinite(x)?String(x):"null";
},string:function(x){
if(/["\\\x00-\x1f]/.test(x)){
x=x.replace(/([\x00-\x1f\\"])/g,function(a,b){
var c=m[b];
if(c){
return c;
}
c=b.charCodeAt();
return "\\u00"+Math.floor(c/16).toString(16)+(c%16).toString(16);
});
}
return "\""+x+"\"";
},object:function(x){
if(x){
var a=[],b,f,i,l,v;
if(x instanceof Array){
a[0]="[";
l=x.length;
for(i=0;i<l;i+=1){
v=x[i];
f=s[typeof v];
if(f){
v=f(v);
if(typeof v=="string"){
if(b){
a[a.length]=",";
}
a[a.length]=v;
b=true;
}
}
}
a[a.length]="]";
}else{
if(x instanceof Object){
a[0]="{";
for(i in x){
v=x[i];
f=s[typeof v];
if(f){
v=f(v);
if(typeof v=="string"){
if(b){
a[a.length]=",";
}
a.push(s.string(i),":",v);
b=true;
}
}
}
a[a.length]="}";
}else{
return;
}
}
return a.join("");
}
return "null";
}};
return {copyright:"(c)2005 JSON.org",license:"http://www.JSON.org/license.html",stringify:function(v){
var f=s[typeof v];
if(f){
v=f(v);
if(typeof v=="string"){
return v;
}
}
return null;
},parse:function(text){
try{
return !(/[^,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]/.test(text.replace(/"(\\.|[^"\\])*"/g,"")))&&eval("("+text+")");
}
catch(e){
return false;
}
}};
}();
function getExpDate(days,_109,_10a){
var _10b=new Date();
if(typeof days=="number"&&typeof _109=="number"&&typeof _109=="number"){
_10b.setDate(_10b.getDate()+parseInt(days));
_10b.setHours(_10b.getHours()+parseInt(_109));
_10b.setMinutes(_10b.getMinutes()+parseInt(_10a));
return _10b.toGMTString();
}
}
function getCookieVal(_10c){
var _10d=document.cookie.indexOf(";",_10c);
if(_10d==-1){
_10d=document.cookie.length;
}
return unescape(document.cookie.substring(_10c,_10d));
}
function getCookie(name){
var arg=name+"=";
var alen=arg.length;
var clen=document.cookie.length;
var i=0;
while(i<clen){
var j=i+alen;
if(document.cookie.substring(i,j)==arg){
return getCookieVal(j);
}
i=document.cookie.indexOf(" ",i)+1;
if(i==0){
break;
}
}
return null;
}
function setCookie(name,_115,_116,path,_118,_119){
document.cookie=name+"="+escape(_115)+((_116)?"; expires="+_116:"")+((path)?"; path="+path:"")+((_118)?"; domain="+_118:"")+((_119)?"; secure":"");
}
function deleteCookie(name,path,_11c){
if(getCookie(name)){
document.cookie=name+"="+((path)?"; path="+path:"")+((_11c)?"; domain="+_11c:"")+"; expires=Thu, 01-Jan-70 00:00:01 GMT";
}
}
var msh=function(){
return {widget:{},util:{},effect:{},log:{},idemode:{}};
}();
Function.prototype.bindNoArgs=function(_11d){
var _11e=this;
return function(){
return _11e.apply(_11d);
};
};
function entries(_11f){
var _120=[];
for(var i=0;i<_11f.length;i++){
_120.push(_11f[i]);
}
return _120;
}
Function.prototype.bind=function(_122){
var _123=this;
var _124=entries(arguments).slice(1);
return function(){
var _125=entries(arguments);
return _123.apply(_122,_124.concat(_125));
};
};
Function.prototype.bindRequest=function(_126){
var _127=this;
return function(_128){
var args=new Array();
args.push(_128);
return _127.apply(_126,args);
};
};
msh.log.error=function(_12a){
try{
console.error(_12a);
}
catch(e){
}
};
msh.idemode.goInIdeMode=function(){
setCookie("showTags","true");
window.location.reload();
};
msh.effect.FadeEffect=function(){
};
msh.effect.FadeEffect.putFade=function(){
var div=$("fadeEffect");
if(div==null){
div=document.createElement("div");
div.id="fadeEffect";
document.body.insertBefore(div,document.body.firstChild);
var ua=navigator.userAgent.toLowerCase();
var _12d=(ua.indexOf("opera")>-1);
var isIE=(!_12d&&ua.indexOf("msie")>-1);
var _12f=Math.max(document.compatMode!="CSS1Compat"?document.body.scrollHeight:document.documentElement.scrollHeight,(((document.compatMode||isIE)&&!_12d)?(document.compatMode=="CSS1Compat")?document.documentElement.clientHeight:document.body.clientHeight:(document.parentWindow||document.defaultView).innerHeight));
div.style.height=_12f+"px";
}
if(div.style.zIndex==null){
div.style.zIndex=0;
}
if(div.style.zIndex==1){
div.style.zIndex=2;
}
div.style.zIndex++;
};
msh.effect.FadeEffect.getIndex=function(){
var div=$("fadeEffect");
if(div.style.zIndex==null){
div.style.zIndex=0;
}
return div.style.zIndex;
};
msh.effect.FadeEffect.pushFade=function(){
var div=$("fadeEffect");
if(div!=null){
if(div.style.zIndex==null){
div.style.zIndex=0;
}
if(div.style.zIndex==2){
div.style.zIndex=1;
}
div.style.zIndex--;
if(div.style.zIndex==0){
div.parentNode.removeChild(div);
}
}
};
msh.effect.FadeEffect.pushFadeAll=function(){
var div=$("fadeEffect");
if(div!=null){
div.parentNode.removeChild(div);
}
};
msh.widget.Dialog=function(_133){
this.show=function(){
_133.style.display="block";
_133.style.visibility="visible";
msh.effect.FadeEffect.putFade();
_133.style.zIndex=1+Math.round(msh.effect.FadeEffect.getIndex());
_133.parentNode.removeChild(_133);
document.body.appendChild(_133);
center(_133);
};
this.hide=function(){
msh.effect.FadeEffect.pushFade();
_133.style.display="none";
_133.style.visibility="hidden";
};
};
function center(_134){
try{
_134=$(_134);
}
catch(e){
return;
}
var _135=0;
var _136=0;
if(typeof (window.innerWidth)=="number"){
_135=window.innerWidth;
_136=window.innerHeight;
}else{
if(document.documentElement&&(document.documentElement.clientWidth||document.documentElement.clientHeight)){
_135=document.documentElement.clientWidth;
_136=document.documentElement.clientHeight;
}else{
if(document.body&&(document.body.clientWidth||document.body.clientHeight)){
_135=document.body.clientWidth;
_136=document.body.clientHeight;
}
}
}
_134.style.position="absolute";
_134.style.zIndex=99;
var _137=0;
if(document.documentElement&&document.documentElement.scrollTop){
_137=document.documentElement.scrollTop;
}else{
if(document.body&&document.body.scrollTop){
_137=document.body.scrollTop;
}else{
if(window.pageYOffset){
_137=window.pageYOffset;
}else{
if(window.scrollY){
_137=window.scrollY;
}
}
}
}
var _138=getDimensions(_134);
var setX=(_135-_138.width)/2;
var setY=(_136-_138.height)/3+_137;
setX=(setX<0)?0:setX;
setY=(setY<0)?0:setY;
_134.style.left=setX+"px";
_134.style.top=setY+"px";
_134.style.display="block";
}
function getDimensions(_13b){
_13b=$(_13b);
var _13c=_13b.style.display;
if(_13c!="none"&&_13c!=null){
return {width:_13b.offsetWidth,height:_13b.offsetHeight};
}
var els=_13b.style;
var _13e=els.visibility;
var _13f=els.position;
var _140=els.display;
els.visibility="hidden";
els.position="absolute";
els.display="block";
var _141=_13b.clientWidth;
var _142=_13b.clientHeight;
els.display=_140;
els.position=_13f;
els.visibility=_13e;
return {width:_141,height:_142};
}
msh.widget.TreeTableDialog=function(_143,_144,_145){
this.theName=_143;
this.theDialog=null;
this.theTitle=_144;
this.theDialogId=_143+"Dialog";
this.theDialog=null;
this.theRootPane=null;
this.theLastSearch="";
this.theData=null;
this.theGoFunction=null;
this.theObject=null;
this.theControlObject=_145;
this.thePageDown=this.createForwardTd();
this.thePageUp=this.createBackTd();
this.init();
};
msh.widget.TreeTableDialog.prototype.init=function(){
var div=document.createElement("div");
div.id=this.theDialogId;
div.className="dialog";
var h2=document.createElement("h2");
h2.appendChild(document.createTextNode(this.theTitle));
var _148=document.createElement("div");
_148.className="rootPane treeTable";
div.appendChild(h2);
div.appendChild(_148);
document.body.appendChild(div);
this.theDialog=new msh.widget.Dialog(div);
this.theRootPane=_148;
};
msh.widget.TreeTableDialog.prototype.setMessage=function(_149){
this.theRootPane.appendChild(document.createTextNode(_149));
};
msh.widget.TreeTableDialog.prototype.show=function(){
this.theDialog.show();
};
msh.widget.TreeTableDialog.prototype.updateContent=function(_14a,_14b,_14c){
this.theData=_14a;
this.theGoFunction=_14b;
this.theObject=_14c;
var _14d=document.createElement("table");
_14d.className="tabview sel tableArrow";
var _14e=document.createElement("tbody");
_14d.appendChild(_14e);
var _14f=document.createElement("tr");
_14e.appendChild(_14f);
for(i=0;i<_14a.titles.length;i++){
var th=document.createElement("th");
th.appendChild(document.createTextNode(_14a.titles[i]));
_14f.appendChild(th);
}
for(row=0;row<_14a.rows.length;row++){
var tr=document.createElement("tr");
tr.onclick="";
for(col=0;col<_14a.rows[row].cols.length;col++){
var td=document.createElement("td");
td.appendChild(document.createTextNode(_14a.rows[row].cols[col]));
td.onclick=_14b.bind(_14c,null,_14a.rows[row].id,true,null);
tr.appendChild(td);
}
if(row==0){
tr.appendChild(this.thePageUp);
}
if(row==1){
var td=document.createElement("td");
td.appendChild(document.createTextNode(" "));
td.style.cssText="background-color: white; color: blue";
td.rowSpan=_14a.rows.length-2;
tr.appendChild(td);
}
if(row==_14a.rows.length-1){
tr.appendChild(this.thePageDown);
}
_14e.appendChild(tr);
}
var ul=document.createElement("ul");
for(i=0;i<_14a.path.length;i++){
var li=document.createElement("li");
li.appendChild(document.createTextNode(" >> "));
var a=document.createElement("a");
li.appendChild(a);
a.appendChild(document.createTextNode(_14a.path[i].name));
a.onclick=_14b.bind(_14c,_14a.path[i].id,_14a.path[i].parentId,true,null);
a.href="#";
li.appendChild(document.createTextNode(" "));
ul.appendChild(li);
}
var link=document.createElement("a");
link.href="#";
link.onclick=this.search.bind(this,_14a.firstId,_14a.firstParentId,_14b,_14c);
link.appendChild(document.createTextNode("\u041d\u0430\u0439\u0442\u0438"));
var _157=document.createElement("input");
_157.value="\u0412\u044b\u0431\u0440\u0430\u0442\u044c";
_157.type="button";
_157.onclick=_14c.selectCurrent;
var _158=document.createElement("input");
_158.value="\u0421\u0431\u0440\u043e\u0441\u0438\u0442\u044c";
_158.type="button";
_158.onclick=_14c.clear;
this.theRootPane.innerHTML="";
this.theRootPane.appendChild(ul);
this.theRootPane.appendChild(document.createElement("hr"));
this.theRootPane.appendChild(link);
this.theRootPane.appendChild(document.createElement("hr"));
this.theRootPane.appendChild(_14d);
this.theRootPane.appendChild(_157);
this.theRootPane.appendChild(_158);
};
msh.widget.TreeTableDialog.prototype.search=function(_159,_15a,_15b,_15c){
this.theLastSearch=prompt("\u041d\u0430\u0439\u0442\u0438",this.theLastSearch);
var args=new Array();
args.push(_159);
args.push(_15a);
args.push(true);
args.push(this.theLastSearch);
_15b.apply(_15c,args);
};
msh.widget.TreeTableDialog.prototype.createBackTd=function(){
var link=document.createElement("td");
link.appendChild(document.createTextNode("\u25b2"));
link.style.cssText="background-color: white; color: blue";
link.onclick=this.theControlObject.onPagePrevious;
return link;
};
msh.widget.TreeTableDialog.prototype.createForwardTd=function(){
var _15f=document.createElement("td");
_15f.style.cssText="background-color: white; color: blue";
_15f.appendChild(document.createTextNode("\u25bc"));
_15f.onclick=this.theControlObject.onPageNext;
return _15f;
};
msh.widget.TreeTableDialog.prototype.hide=function(){
this.theDialog.hide();
};
msh.widget.AutocompleteTableView=function(_160,_161){
var _162=0;
this.setEditabled=function(_163){
if(_163){
Element.addClassName(_160,"vocEditabled");
if(_160.title==null){
_160.title="";
}
_160.title=_160.title+"  \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c - [ALT+INSERT], \u0438\u0437\u043c\u0435\u043d\u0438\u0442\u044c - [F2]";
}
};
this.setSearching=function(_164){
if(_164){
Element.addClassName(_160,"searching");
}else{
Element.removeClassName(_160,"searching");
}
};
this.selectNext=function(){
};
this.showBox=function(aXml){
var id=getValue(aXml,"requestId");
var _167=document.createElement("table");
var _168=document.createElement("tbody");
var rows=aXml.getElementsByTagName("row");
if(rows.length==0){
var tr=createTr(0,null);
_168.appendChild(tr);
}else{
for(var i=0;i<rows.length;i++){
var tr=createTr(i,rows[i]);
_168.appendChild(tr);
}
}
_167.appendChild(_168);
_161.style.display="block";
_161.className="autocomplete";
if(_160.clientWidth){
_161.style.width=_160.clientWidth+"px";
_167.style.width=_160.clientWidth+"px";
}else{
_161.style.width="30em";
_167.style.width="30em";
}
_161.style.visibility="visible";
_161.innerHTML="";
_161.appendChild(_167);
if(!setSelectedId(id)){
setSelected(0);
}
};
this.hide=function(){
_161.innerHTML="";
_161.style.visibility="hidden";
_161.style.display="none";
};
this.selectNext=function(){
return setSelected(_162+1);
};
this.selectPrevious=function(){
return setSelected(_162-1);
};
function setSelectedId(aId){
var _16d=false;
var lis=_161.getElementsByTagName("tr");
for(var i=0;i<lis.length;i++){
var tr=lis[i];
if(aId==lis[i].myId){
setSelected(i);
_16d=true;
break;
}
}
return _16d;
}
this.getSelectedId=function(){
var trs=_161.getElementsByTagName("tr");
for(var i=0;i<trs.length;i++){
if(trs[i].className=="selected"){
return trs[i].myId;
}
}
return null;
};
this.getSelectedName=function(){
var trs=_161.getElementsByTagName("tr");
for(var i=0;i<trs.length;i++){
if(trs[i].className=="selected"){
return trs[i].myName;
}
}
return null;
};
this.getLastId=function(){
var trs=_161.getElementsByTagName("tr");
if(trs.length>0){
var tr=trs[trs.length-1];
return tr.myId;
}else{
return "";
}
};
this.getFirstId=function(){
var trs=_161.getElementsByTagName("tr");
var tr=trs[0];
return tr.myId;
};
function setSelected(_179){
_162=_179;
var lis=_161.getElementsByTagName("tr");
var _17b=false;
for(var i=0;i<lis.length;i++){
if(i==_179){
lis[i].className="selected";
_17b=true;
}else{
lis[i].className="";
}
}
return _17b;
}
function test123(){
alert("hello");
}
function createTr(_17d,_17e){
var name=getValue(_17e,"name");
if(name==""){
name="-";
}
var id=getValue(_17e,"id");
var tr=document.createElement("tr");
tr.myId=id;
tr.myName=name;
var tdId=document.createElement("td");
var _183=document.createElement("td");
tdId.appendChild(document.createTextNode(id));
_183.appendChild(document.createTextNode(name));
tdId.className="id";
_183.className="name";
tr.appendChild(tdId);
tr.appendChild(_183);
tr.onmousedown=setSelected.bind(this,_17d);
return tr;
}
function getValue(_184,_185){
if(_184==null){
return "";
}
var _186=_184.getElementsByTagName(_185);
if(_186.length>0&&_186[0].firstChild){
return _186[0].firstChild.nodeValue;
}else{
return "";
}
}
};
msh.widget.TreeAutocompleteTableView=function(_187,_188){
var _189=0;
var _18a=0;
this.getPage=function(){
return _18a;
};
this.setPage=function(_18b){
_18a=_18b;
};
this.setEditabled=function(_18c){
if(_18c){
Element.addClassName(_187,"vocEditabled");
if(_187.title==null){
_187.title="";
}
_187.title=_187.title+"  \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c - [ALT+INSERT], \u0438\u0437\u043c\u0435\u043d\u0438\u0442\u044c - [F2]";
}
};
this.setSearching=function(_18d){
if(_18d){
Element.addClassName(_187,"searching");
}else{
Element.removeClassName(_187,"searching");
}
};
this.selectNext=function(){
};
this.showBox=function(aXml){
var id=getValue(aXml,"requestId");
var _190=document.createElement("table");
var _191=document.createElement("tbody");
var rows=aXml.getElementsByTagName("row");
if(rows.length==0){
var tr=createTr(0,null);
_191.appendChild(tr);
}else{
for(var i=0;i<rows.length;i++){
var j=0;
if(i==0){
j=1;
}
if(i==rows.length-1){
j=2;
}
document.title=""+j+document.title;
var tr=createTr(i,rows[i],j);
_191.appendChild(tr);
}
}
_190.appendChild(_191);
_188.style.display="block";
_188.className="autocomplete";
if(_187.clientWidth){
_188.style.width=_187.clientWidth+"px";
_190.style.width=_187.clientWidth+"px";
}else{
_188.style.width="30em";
_190.style.width="30em";
}
_188.style.visibility="visible";
_188.innerHTML="";
_188.appendChild(_190);
if(!setSelectedId(id)){
setSelected(0);
}
};
this.hide=function(){
_188.innerHTML="";
_188.style.visibility="hidden";
_188.style.display="none";
};
this.selectNext=function(){
return setSelected(_189+1);
};
this.selectPrevious=function(){
return setSelected(_189-1);
};
function setSelectedId(aId){
var _197=false;
var lis=_188.getElementsByTagName("tr");
for(var i=0;i<lis.length;i++){
var tr=lis[i];
if(aId==lis[i].myId){
setSelected(i);
_197=true;
break;
}
}
return _197;
}
this.getSelectedId=function(){
var trs=_188.getElementsByTagName("tr");
for(var i=0;i<trs.length;i++){
if(trs[i].className=="selected"){
return trs[i].myId;
}
}
return null;
};
this.getSelectedName=function(){
var trs=_188.getElementsByTagName("tr");
for(var i=0;i<trs.length;i++){
if(trs[i].className=="selected"){
return trs[i].myName;
}
}
return null;
};
this.getLastId=function(){
var trs=_188.getElementsByTagName("tr");
if(trs.length>0){
var tr=trs[trs.length-1];
return tr.myId;
}else{
return "";
}
};
this.getFirstId=function(){
var trs=_188.getElementsByTagName("tr");
var tr=trs[0];
return tr.myId;
};
function setPage(_1a3){
_18a=_1a3;
}
function setSelected(_1a4){
switch(_18a){
case 1:
document.title="1down_"+document.title;
return true;
break;
case 2:
document.title="2down_"+document.title;
return true;
break;
default:
_189=_1a4;
var lis=_188.getElementsByTagName("tr");
var _1a6=false;
for(var i=0;i<lis.length;i++){
if(i==_1a4){
lis[i].className="selected";
document.title="s"+i+""+document.title;
_1a6=true;
}else{
lis[i].className="";
}
}
document.title=_1a6+"+"+document.title;
return _1a6;
}
}
function test123(_1a8){
alert(_1a8);
}
function createTr(_1a9,_1aa,_1ab){
var name=getValue(_1aa,"name");
if(name==""){
name="-";
}
var id=getValue(_1aa,"id");
var tr=document.createElement("tr");
tr.myId=id;
tr.myName=name;
var _1af=document.createElement("td");
var tdId=document.createElement("td");
var _1b1=document.createElement("td");
var _1b2=document.createElement("td");
var _1b3=document.createElement("td");
tdId.appendChild(document.createTextNode(id));
_1b2.appendChild(document.createTextNode("<"));
_1b1.appendChild(document.createTextNode(name));
_1af.appendChild(document.createTextNode(">"));
_1b2.className="navig";
_1af.className="navig";
tdId.className="id";
_1b1.className="name";
_1b3.className="navig";
switch(_1ab){
case 1:
_1b3.appendChild(document.createTextNode("^"));
_1b3.onmousedown=setPage.bind(this,1);
break;
case 2:
_1b3.appendChild(document.createTextNode("*"));
_1b3.onmousedown=setPage.bind(this,2);
break;
default:
break;
}
_18a=0;
tr.appendChild(_1b2);
tr.appendChild(tdId);
tr.appendChild(_1b1);
tr.appendChild(_1af);
tr.appendChild(_1b3);
tr.onmousedown=setSelected.bind(this,_1a9);
return tr;
}
function getValue(_1b4,_1b5){
if(_1b4==null){
return "";
}
var _1b6=_1b4.getElementsByTagName(_1b5);
if(_1b6.length>0&&_1b6[0].firstChild){
return _1b6[0].firstChild.nodeValue;
}else{
return "";
}
}
};
msh.widget.TreeTable=function(_1b7,_1b8,_1b9,_1ba){
var _1bb="";
var _1bc=null;
var _1bd=null;
var _1be=null;
var _1bf=this;
this.onPageNext=function(){
go(_1be.lastId,_1be.lastParentId,true,null);
};
this.onPagePrevious=function(){
go(_1be.firstId,_1be.lastParentId,false,null);
};
var _1c0=new msh.widget.TreeTableDialog(_1b8,_1b9,this);
this.selectCurrent=function(){
selectData(_1be);
};
this.clear=function(){
_1c0.hide();
_1bc.innerHTML="";
_1ba.value="";
};
function selectData(data){
_1c0.hide();
var _1c2="";
for(var i=0;i<data.path.length;i++){
_1c2=_1c2+" "+data.path[i].name;
}
_1bc.innerHTML=_1c2;
_1ba.value=data.path[data.path.length-1].id;
}
this.setCurrentData=function(_1c4){
_1be=_1c4;
};
this.installTo=function(_1c5){
theField=_1c5;
var _1c6=_1c5.parentNode;
_1c6.removeChild(_1c5);
var span=document.createElement("span");
_1bc=span;
span.appendChild(document.createTextNode(_1c5.value));
_1c6.appendChild(span);
var _1c8=document.createElement("input");
_1c8.id=_1b8+"EditButton";
_1c8.type="button";
_1c8.onclick=this.onEditButtonClick;
_1c8.value="\u0418\u0437\u043c\u0435\u043d\u0438\u0442\u044c";
_1c6.appendChild(_1c8);
_1bd=_1c8;
var _1c9=document.createElement("div");
_1c9.id=_1b8+"Holder";
_1c6.appendChild(_1c9);
};
this.onEditButtonClick=function(){
_1bd.blur();
_1c0.show();
go(_1ba.value!=""?_1ba.value:null,null,true);
};
function go(_1ca,_1cb,_1cc,_1cd){
if(_1cd){
_1c0.setMessage("\u041f\u043e\u0438\u0441\u043a "+_1cd+" ...");
}else{
_1c0.setMessage("\u0416\u0434\u0438\u0442\u0435 ...");
}
if(theTableArrow!=null){
theTableArrow.remove();
}
var pars="";
if(_1cb!=null){
pars+="parentId="+_1cb+"&";
}
if(_1ca!=null){
pars+="fromId="+_1ca+"&";
}
if(!_1cc){
pars+="direction=backward&";
}
if(_1cd!=null){
pars+="search="+_1cd+"&";
}
var cObj=YAHOO.util.Connect.asyncRequest("POST",_1b7,{success:onComplete,failure:function(_1d0){
alert(_1d0.statusText);
}},pars);
}
function onComplete(_1d1){
var data=eval("("+_1d1.responseText+")");
_1be=data;
if(data.rows.length==0){
_1c0.hide();
var _1d3="";
for(var i=0;i<data.path.length;i++){
_1d3=_1d3+" "+data.path[i].name;
}
_1bc.innerHTML=_1d3;
_1ba.value=data.path[data.path.length-1].id;
}else{
_1c0.updateContent(data,go,_1bf);
}
if(theTableArrow!=null){
theTableArrow.remove();
}
theTableArrow=new tablearrow.TableArrow("tab1");
}
function search(_1d5,_1d6,_1d7){
_1bb=prompt("\u041d\u0430\u0439\u0442\u0438:","\u041f\u043e\u0438\u0441\u043a \u043f\u043e \u041a\u041b\u0410\u0414\u0420",_1bb);
go(_1d5,_1d6,_1d7,_1bb);
}
};
function createTestData(){
return ({titles:["hello","heee123","asdf"],rows:[{id:"1",parentId:"123",cols:[123,123,123]},{id:"1",parentId:"123",cols:[123,123,123]},{id:"1",parentId:"123",cols:[123,123,123]},{id:"1",parentId:"123",cols:[123,123,123]},{id:"1",parentId:"123",cols:[123,123,123]},{id:"1",parentId:"123",cols:[123,123,123]}]});
}
var theMainFormDataInstance=null;
msh.util.FormData=function(){
var _1d8=null;
var _1d9=null;
this.init=function(_1da){
if(_1da==null){
alert("\u041d\u0435\u0442 \u0444\u043e\u0440\u043c\u044b");
}
if(_1d8==null){
_1d8=Form.serialize(_1da);
_1d9=_1da;
}else{
alert("msh.util.FormData: \u0424\u043e\u0440\u043c\u0430 \u0443\u0436\u0435 \u043f\u0440\u043e\u0438\u043d\u0438\u0446\u0438\u0430\u043b\u0438\u0437\u0438\u0440\u043e\u0432\u0430\u043d\u0430");
}
};
this.isChanged=function(){
return _1d8!=Form.serialize(_1d9);
};
this.isChangedForLink=function(){
if(_1d9==null){
return true;
}
var _1db=_1d8!=Form.serialize(_1d9);
if(_1db){
return confirm("\u0414\u0430\u043d\u043d\u044b\u0435 \u0432 \u0444\u043e\u0440\u043c\u0435 \u0438\u0437\u043c\u0435\u043d\u0438\u043b\u0438\u0441\u044c. \u041f\u0440\u043e\u0434\u043e\u043b\u0436\u0438\u0442\u044c \u0431\u0435\u0437 \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0438\u044f \u0434\u0430\u043d\u043d\u044b\u0445?");
}
return !_1db;
};
};
msh.util.FormData.getInstance=function(){
if(theMainFormDataInstance==null){
theMainFormDataInstance=new msh.util.FormData();
}
return theMainFormDataInstance;
};
var errorutil={Version:"1.0"};
errorutil.ShowFieldError=function(_1dc,_1dd){
var _1de=_1dc;
var _1df=_1dd;
_1dc.title=_1dd;
var _1e0=document.createElement("DIV");
_1e0.innerHTML=_1dd;
_1e0.id="errorDiv";
_1e0.style.borderColor="#F06";
_1e0.style.color="#F06";
_1dc.parentNode.style.border="2px solid #F06";
_1dc.parentNode.appendChild(_1e0);
};
errorutil.HideError=function(_1e1){
var _1e2=_1e1;
_1e1.parentNode.style.border="";
_1e1.title="";
try{
var _1e3=_1e1.parentNode.lastChild;
if(_1e3.tagName=="DIV"&&_1e3.id=="errorDiv"){
_1e1.parentNode.removeChild(_1e3);
}
}
catch(e){
}
};
errorutil.SetErrorObj=function(msg){
var err=new Error(msg);
if(!err.message){
err.message=msg;
}
return err;
};
var eventutil={Version:"1.0",EVENT_BLUR:"blur",EVENT_KEY_PRESS:"keypress",EVENT_KEY_UP:"keyup",EVENT_KEY_DOWN:"keydown",EVENT_CLICK:"click",EVENT_MOUSE_OVER:"mouseover",EVENT_MOUSE_OUT:"mouseout",VK_BACKSPACE:8,VK_TAB:9,VK_ENTER:13,VK_INSERT:45,VK_BACK_QUOTE:192,VK_SPACE:32,VK_ESCAPE:27,VK_PAGE_DOWN:34,VK_PAGE_UP:33,VK_HOME:36,VK_LEFT:37,VK_UP:38,VK_RIGHT:39,VK_DOWN:40,VK_DEL:46,VK_0:48,VK_1:49,VK_2:50,VK_3:51,VK_4:52,VK_5:53,VK_6:54,VK_7:55,VK_8:56,VK_9:57,VK_A:65,VK_B:66,VK_C:67,VK_D:68,VK_N:78,VK_F12:123};
EnterSupporter=function(_1e6,_1e7){
this.select=function(_1e8){
var _1e9=_1e8.keyCode;
if(_1e8.shiftKey&&eventutil.isKey(_1e8,eventutil.VK_ESCAPE)){
$("cancelButton").click();
}else{
if(_1e8.ctrlKey&&eventutil.isKey(_1e8,eventutil.VK_ENTER)){
$("submitButton").click();
}else{
if(eventutil.isKey(_1e8,eventutil.VK_ENTER)){
_1e6.blur();
try{
_1e7.focus();
_1e7.select();
return false;
}
catch(e){
}
}
}
}
};
};
eventutil.addEventListener=function(_1ea,_1eb,_1ec){
try{
_1ea.attachEvent("on"+_1eb,_1ec);
}
catch(e){
try{
_1ea.addEventListener(_1eb,_1ec,false);
}
catch(e){
}
}
};
eventutil.removeEventListener=function(_1ed,_1ee,_1ef){
try{
_1ed.detachEvent("on"+_1ee,_1ef);
}
catch(e){
try{
alert(_1ef);
_1ed.removeEventListener(_1ee,_1ef,true);
}
catch(e){
alert(e);
}
}
};
eventutil.addObserveListener=function(_1f0,_1f1,_1f2){
Event.observe(_1f0,_1f1,_1f2,false);
};
eventutil.addEnterSupport=function(_1f3,_1f4){
try{
var _1f5=$(_1f3);
var _1f6=$(_1f4);
if(_1f5!=null&&_1f6!=null){
eventutil.addEventListener(_1f5,"keyup",new EnterSupporter(_1f5,_1f6).select);
_1f5.setAttribute("autocomplete","off");
_1f6.setAttribute("autocomplete","off");
}
}
catch(e){
}
};
eventutil.isKey=function(_1f7,_1f8){
return _1f7.keyCode==_1f8;
};
PrivateAccessKeyEvent=function(_1f9,_1fa,_1fb,_1fc){
this.theKeyCode=_1f9;
this.theIsCtrl=_1fa;
this.theIsShift=_1fc;
this.theIsAlt=_1fb;
};
function createSingleKey(_1fd){
return new PrivateAccessKeyEvent(_1fd,false,false,false);
}
function createCtrl(_1fe){
return new PrivateAccessKeyEvent(_1fe,true,false,false);
}
function createCtrlShift(_1ff){
return new PrivateAccessKeyEvent(_1ff,true,false,true);
}
function createShift(_200){
return new PrivateAccessKeyEvent(_200,false,false,true);
}
function createAlt(_201){
return new PrivateAccessKeyEvent(_201,false,true,false);
}
var accesskeyutil={Version:"1.0",ALT_0:createAlt(eventutil.VK_0),ALT_1:createAlt(eventutil.VK_1),ALT_2:createAlt(eventutil.VK_2),ALT_3:createAlt(eventutil.VK_3),ALT_4:createAlt(eventutil.VK_4),ALT_5:createAlt(eventutil.VK_5),ALT_6:createAlt(eventutil.VK_6),ALT_7:createAlt(eventutil.VK_7),ALT_8:createAlt(eventutil.VK_8),ALT_9:createAlt(eventutil.VK_9),ALT_A:createAlt(eventutil.VK_A),ALT_B:createAlt(eventutil.VK_B),ALT_C:createAlt(eventutil.VK_C),ALT_D:createAlt(eventutil.VK_D),ALT_N:createAlt(eventutil.VK_N),ALT_DEL:createAlt(eventutil.VK_DEL),CTRL_0:createCtrl(eventutil.VK_0),CTRL_1:createCtrl(eventutil.VK_1),CTRL_2:createCtrl(eventutil.VK_2),CTRL_3:createCtrl(eventutil.VK_3),CTRL_4:createCtrl(eventutil.VK_4),CTRL_5:createCtrl(eventutil.VK_5),CTRL_6:createCtrl(eventutil.VK_6),CTRL_7:createCtrl(eventutil.VK_7),CTRL_8:createCtrl(eventutil.VK_8),CTRL_9:createCtrl(eventutil.VK_9),CTRL_A:createCtrl(eventutil.VK_A),CTRL_B:createCtrl(eventutil.VK_B),CTRL_C:createCtrl(eventutil.VK_C),CTRL_D:createCtrl(eventutil.VK_D),CTRL_N:createCtrl(eventutil.VK_N),CTRL_DEL:createCtrl(eventutil.VK_DEL),SHIFT_0:createShift(eventutil.VK_0),SHIFT_1:createShift(eventutil.VK_1),SHIFT_2:createShift(eventutil.VK_2),SHIFT_3:createShift(eventutil.VK_3),SHIFT_4:createShift(eventutil.VK_4),SHIFT_5:createShift(eventutil.VK_5),SHIFT_6:createShift(eventutil.VK_6),SHIFT_7:createShift(eventutil.VK_7),SHIFT_8:createShift(eventutil.VK_8),SHIFT_9:createShift(eventutil.VK_9),SHIFT_A:createShift(eventutil.VK_A),SHIFT_B:createShift(eventutil.VK_B),SHIFT_C:createShift(eventutil.VK_C),SHIFT_D:createShift(eventutil.VK_D),SHIFT_N:createShift(eventutil.VK_N),SHIFT_DEL:createShift(eventutil.VK_DEL),SHIFT_CTRL_0:createCtrlShift(eventutil.VK_0),SHIFT_CTRL_1:createCtrlShift(eventutil.VK_1),SHIFT_CTRL_2:createCtrlShift(eventutil.VK_2),SHIFT_CTRL_3:createCtrlShift(eventutil.VK_3),SHIFT_CTRL_4:createCtrlShift(eventutil.VK_4),SHIFT_CTRL_5:createCtrlShift(eventutil.VK_5),SHIFT_CTRL_6:createCtrlShift(eventutil.VK_6),SHIFT_CTRL_7:createCtrlShift(eventutil.VK_7),SHIFT_CTRL_8:createCtrlShift(eventutil.VK_8),SHIFT_CTRL_9:createCtrlShift(eventutil.VK_9),SHIFT_CTRL_A:createCtrlShift(eventutil.VK_A),SHIFT_CTRL_B:createCtrlShift(eventutil.VK_B),SHIFT_CTRL_C:createCtrlShift(eventutil.VK_C),SHIFT_CTRL_D:createCtrlShift(eventutil.VK_D),SHIFT_CTRL_N:createCtrlShift(eventutil.VK_N),SHIFT_CTRL_DEL:createCtrlShift(eventutil.VK_DEL),F12:createSingleKey(eventutil.VK_F12)};
accesskeyutil.AccessKeyListener=function(_202,_203){
this.onKey=function(_204){
var _205=_204.keyCode;
if(_205==_203.theKeyCode&&_204.ctrlKey==_203.theIsCtrl&&_204.shiftKey==_203.theIsShift&&_204.altKey==_203.theIsAlt){
_202.focus();
if(_202.onclick){
var ok=_202.onclick();
if(ok){
window.location=_202.href;
}
}else{
window.location=_202.href;
}
return false;
}
};
};
accesskeyutil.registerKey=function(_207,_208){
eventutil.addEventListener(document,"keydown",new accesskeyutil.AccessKeyListener(_207,_208).onKey);
};
var theTimeout=null;
var snilsutil={Version:"1.0"};
snilsutil.SnilsField=function(_209){
_209.onblur=check;
var _20a=_209;
var _20b=null;
function liveCheck(_20c){
var _20d=_20c.keyCode;
if(_20d==eventutil.VK_BACKSPACE){
}else{
var size=_20a.value.length;
if(size==3||size==7){
_20a.value=_20a.value+"-";
}else{
if(size==11){
_20a.value=_20a.value+" ";
}else{
if(size>14){
_20a.value=_20a.value.trim().substring(0,14);
}
}
}
}
}
eventutil.addEventListener(_209,"keydown",liveCheck);
eventutil.addEventListener(_209,"keyup",liveCheck);
function check(_20f){
try{
_20b=errorutil.HideError(_20a);
var _210=_20a.value;
if(!(_210==null||_210=="")){
var _211=parseSnils(_210);
}
}
catch(e){
_20b=errorutil.ShowFieldError(_20a,e.message);
return false;
}
}
};
function parseSnils(_212){
var _213=new RegExp("[0-9]{3}[-]{1}[0-9]{3}[-]{1}[0-9]{3}[ ]{1}[0-9]{2}");
if(!_213.test(_212)){
throw errorutil.SetErrorObj("\u0424\u043e\u0440\u043c\u0430 \u0432\u0432\u043e\u0434\u0430 \u0421\u041d\u0418\u041b\u0421: NNN-NNN-NNN NN. \u041d\u0430\u043f\u0440\u0438\u043c\u0435\u0440: 111-111-111 11");
}
return "";
}
var theTimeout=null;
var dateutil={Version:"1.0"};
dateutil.DateField=function(_214){
eventutil.addEventListener(_214,"dblclick",function(){
if(self.gfPop2){
gfPop2.fPopCalendar($(_214.id));
}
});
_214.onblur=check;
var _215=_214;
var _216=null;
function liveCheck(_217){
var _218=_217.keyCode;
if(_218==eventutil.VK_BACKSPACE){
}else{
var size=_215.value.length;
if(size==2||size==5){
_215.value=_215.value+".";
}else{
if(size>10){
_215.value=_215.value.substring(0,10);
}
}
}
}
eventutil.addEventListener(_214,"keydown",liveCheck);
eventutil.addEventListener(_214,"keyup",liveCheck);
function check(_21a){
try{
_216=errorutil.HideError(_215);
var _21b=_215.value;
if(!(_21b==null||_21b=="")){
var date=parseDate(_21b);
if(_21b!=date){
_215.value=date;
}
}
}
catch(e){
_216=errorutil.ShowFieldError(_215,e.message);
return false;
}
}
};
dateutil.PeriodFields=function(_21d,_21e,_21f,_220,_221){
try{
_21d.attachEvent("onblur",onLinker);
}
catch(e){
try{
_21d.addEventListener("blur",onLinker,false);
}
catch(e){
}
}
var _222=_21d;
var _223=_222.value;
var _224=_21e;
var _225=parseInt(_21f,10);
var _226=parseInt(_220,10);
var _227=parseInt(_221,10);
function onLinker(_228){
try{
var _229=parseDate(_222.value);
var yyyy=parseInt(_229.substring(6,_229.length),10);
var dd=parseInt(_229.substring(0,2),10);
var mm=parseInt(_229.substring(3,5),10);
var date=new Date(yyyy,mm-1,dd);
_222.value=monthDayFormat(date.getDate())+"."+monthDayFormat(date.getMonth()+1)+"."+date.getYear();
if(_227!=null&&_227>0){
date.setDate(dd+_227);
}
if(_226!=null&&_226>0){
date.setMonth(mm+_226);
}
if(_225!=null&&_225>0){
date.setYear(yyyy+_225);
}
_224.value=monthDayFormat(date.getDate())+"."+monthDayFormat(date.getMonth()+1)+"."+date.getYear();
}
catch(e){
}
}
};
dateutil.YearLinkAction=function(_22e,_22f){
try{
_22e.attachEvent("onblur",onLinker);
}
catch(e){
try{
_22e.addEventListener("blur",onLinker,false);
}
catch(e){
}
}
var _230=_22e;
var _231=_230.value;
function onLinker(_232){
try{
if(_22f.value==""){
var _233=parseDate(_230.value);
var yyyy=parseInt(_233.substring(6,_233.length),10);
yyyy+=1;
_22f.value=_233.substring(0,6)+yyyy;
}
}
catch(e){
}
}
};
function parseDate(_235){
while(_235.indexOf("-")!=-1){
_235=replaceString(_235,"-","/");
}
while(_235.indexOf(",")!=-1){
_235=replaceString(_235,",","/");
}
while(_235.indexOf(".")!=-1){
_235=replaceString(_235,".","/");
}
var _236=_235.indexOf("/");
var _237=_235.lastIndexOf("/");
if(_236!=-1&&_236==_237){
throw errorutil.SetErrorObj("\u0424\u043e\u0440\u043c\u0430 \u0432\u0432\u043e\u0434\u0430 \u0434\u0430\u0442\u044b: \u0414\u0414.\u041c\u041c.\u0413\u0413\u0413\u0413. \u041d\u0430\u043f\u0440\u0438\u043c\u0435\u0440: 31.12.2004");
}
if(_236!=-1){
var dd=parseInt(_235.substring(0,_236),10);
var mm=parseInt(_235.substring(_236+1,_237),10);
var yyyy=parseInt(_235.substring(_237+1,_235.length),10);
}else{
var dd=parseInt(_235.substring(0,2),10);
var mm=parseInt(_235.substring(2,4),10);
var yyyy=parseInt(_235.substring(4,_235.length),10);
}
if(isNaN(mm)||isNaN(dd)||isNaN(yyyy)){
throw errorutil.SetErrorObj("\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d\u0430 \u0434\u0430\u0442\u0430");
}
if(mm<1||mm>12){
throw errorutil.SetErrorObj("\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d \u043c\u0435\u0441\u044f\u0446");
}
if(dd<1||dd>31){
throw errorutil.SetErrorObj("\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d \u0434\u0435\u043d\u044c");
}
if(yyyy<100){
if(yyyy>=25){
yyyy+=1900;
}else{
yyyy+=2000;
}
}
if(yyyy>10000||yyyy<1000){
throw errorutil.SetErrorObj("\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d \u0433\u043e\u0434");
}
return monthDayFormat(dd)+"."+monthDayFormat(mm)+"."+yyyy;
}
function onDateFld(_23b,_23c){
if(isDate(_23b)){
var _23d=_23b.value;
var _23e=_23d.indexOf(".");
var _23f=_23d.lastIndexOf(".");
var dd=parseInt(_23d.substring(0,_23e),10);
var mm=parseInt(_23d.substring(_23e+1,_23f),10);
var yyyy=parseInt(_23d.substring(_23f+1,_23d.length),10);
_23c.value=dd+"."+mm+"."+(yyyy+1);
}
}
function replaceString(aStr,_244,_245){
var _246=getFront(aStr,_244);
var end=getEnd(aStr,_244);
if(_246!=null&&end!=null){
return _246+_245+end;
}
return null;
}
function getFront(aStr,_249){
var _24a=aStr.indexOf(_249);
if(_24a==-1){
return null;
}
return aStr.substring(0,_24a);
}
function getEnd(aStr,_24c){
var _24d=aStr.indexOf(_24c);
if(_24d==-1){
return null;
}
return aStr.substring(_24d+_24c.length,aStr.length);
}
function monthDayFormat(val){
if(isNaN(val)||val==0){
return "01";
}else{
if(val<10){
return "0"+val;
}
}
return ""+val;
}
var timeutil={Version:"1.0"};
timeutil.TimeField=function(_24f){
_24f.onblur=check;
var _250=_24f;
eventutil.addEventListener(_24f,"dblclick",function(){
if(self.gfPop1){
gfPop1.fPopCalendar($(_24f.id));
}
});
var _251=null;
function liveCheck(_252){
var _253=_252.keyCode;
if(_253==eventutil.VK_BACKSPACE){
}else{
var size=_250.value.length;
if(size==2){
_250.value=_250.value+":";
}else{
if(size>5){
_250.value=_250.value.substring(0,5);
}
}
}
}
eventutil.addEventListener(_24f,"keydown",liveCheck);
eventutil.addEventListener(_24f,"keyup",liveCheck);
function check(_255){
_251=errorutil.HideError(_250);
if(_250.value==""){
return;
}
var ar=_250.value.split(":");
if(ar.length<2){
errorutil.ShowFieldError(_250,"\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d\u043e \u0432\u0440\u0435\u043c\u044f");
}else{
var hour=0;
var _258=0;
try{
hour=parseInt(ar[0],10);
if(hour<0||hour>23){
errorutil.ShowFieldError(_250,"\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d\u044b \u0447\u0430\u0441\u044b");
}
try{
_258=parseInt(ar[1],10);
if(_258<0||_258>59){
errorutil.ShowFieldError(_250,"\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d\u044b \u043c\u0438\u043d\u0443\u0442\u044b");
}else{
_250.value=format(hour)+":"+format(_258);
}
}
catch(e){
errorutil.ShowFieldError(_250,"\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d\u044b \u043c\u0438\u043d\u0443\u0442\u044b");
}
}
catch(e){
errorutil.ShowFieldError(_250,"\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d\u044b \u0447\u0430\u0441\u044b");
}
}
}
function format(_259){
var str=(!isNaN(_259)?_259:"00")+"";
for(var i=0;str.length<2;i++){
str="0"+str;
}
return str;
}
};
function onKeyPress(_25c){
var evt=(_25c)?_25c:window.event;
var _25e=(evt.which)?evt.which:evt.keyCode;
if(evt.ctrlKey&&(_25e==13||_25e==10)){
var _25f=$("defaultSaveButton");
if(_25f){
_25f.click();
}
}else{
if(_25e==27||((_25e==26||_25e==122)&&evt.ctrlKey)){
var _25f=$("defaultCancelButton");
if(_25f){
_25f.click();
}
}
}
}
document.onkeypress=onKeyPress;
var theTabs=new Array();
var theLastSelectedTabId=null;
function getElementsByClassName(_260){
var rl=new Array();
var re=new RegExp("(^| )"+_260+"( |$)");
var ael=document.getElementsByTagName("*");
var op=(navigator.userAgent.indexOf("Opera")!=-1)?true:false;
if(document.all&&!op){
ael=document.all;
}
for(i=0,j=0;i<ael.length;i++){
if(re.test(ael[i].className)){
rl[j]=ael[i];
j++;
}
}
return rl;
}
function showTab3(_265){
Form.enable(document.forms[0]);
Element.removeClassName($("tabbedPaneContent"),"preview");
var _266=getElementsByClassName("tabPane");
var _267=100;
for(var i=0;i<_266.length;i++){
$(_266[i].id).style.visibility="hidden";
$(_266[i].id).style.position="absolute";
if(_267<_266[i].clientWidth){
_267=_266[i].clientWidth;
}
if(_266[i].firstChild.className=="previewHeader"){
_266[i].removeChild(_266[i].firstChild);
}
Element.removeClassName($(_266[i].id+"Link"),"selected");
}
Element.addClassName($(_265+"Link"),"selected");
$(_265).style.visibility="visible";
$("tabbedPaneContent").style.height=($(_265).clientHeight+20)+"px";
$("tabbedPaneFooter").style.visibility="visible";
$("tabbedPaneHeader").style.visibility="visible";
}
function showTab(_269){
if(theLastSelectedTabId!=null){
theTabs[theLastSelectedTabId].style.visibility="hidden";
theTabs[theLastSelectedTabId].style.display="none";
Element.removeClassName(theLastSelectedTabId+"Link","selected");
}
theTabs[_269].style.visibility="visible";
theTabs[_269].style.display="block";
theLastSelectedTabId=_269;
Element.addClassName(theLastSelectedTabId+"Link","selected");
}
function showPreview(){
$("tabbedPaneHeader").style.visibility="hidden";
Element.addClassName($("tabbedPaneContent"),"preview");
Form.disable(document.forms[0]);
var _26a=getElementsByClassName("tabPane");
for(var i=0;i<_26a.length;i++){
var div=document.createElement("div");
Element.addClassName(div,"previewHeader");
var h2=document.createElement("h2");
h2.appendChild(document.createTextNode(_26a[i].title));
var a=document.createElement("a");
a.setAttribute("href","javascript:showTab('"+_26a[i].id+"')");
a.appendChild(document.createTextNode("[\u0418\u0437\u043c\u0435\u043d\u0438\u0442\u044c]"));
div.appendChild(h2);
div.appendChild(a);
_26a[i].appendChild(div);
_26a[i].insertBefore(div,_26a[i].firstChild);
$("tabbedPaneHeader").style.visibility="hidden";
}
}
function tabbedPaneInit(){
eventutil.addObserveListener(window,"load",_tabbedPaneInit);
}
function onKey(_26f){
var _270=_26f.keyCode;
if(_270==9){
showTab("tabMain");
return false;
}
return true;
}
function _tabbedPaneInit(){
var ul=document.createElement("ul");
var _272=getElementsByClassName("tabPane");
var _273=true;
var _274=null;
for(var i=0;i<_272.length;i++){
if(i==0){
_274=_272[i].id;
}
var li=document.createElement("li");
var a=document.createElement("a");
a.appendChild(document.createTextNode(_272[i].title));
a.setAttribute("href","javascript:showTab('"+_272[i].id+"')");
a.setAttribute("id",_272[i].id+"Link");
li.appendChild(a);
ul.appendChild(li);
theTabs[_272[i].id]=_272[i];
}
$("tabbedPaneHeader").innerHTML="";
$("tabbedPaneHeader").appendChild(ul);
$("tabbedPaneHeader").appendChild(document.createElement("hr"));
$("tabbedPaneFooter").style.visibility="visible";
showTab(_274);
$(theDefaultFieldName).focus();
$(theDefaultFieldName).select();
setFocusOnField(theDefaultFieldName);
}
var theTimeout=null;
function error(_278){
try{
console.error("AutocompleteError!");
}
catch(e){
}
}
var msh_autocomplete={Version:"1.0"};
var theMshaHttpRequest;
var theIsSearching=false;
if(window.XMLHttpRequest){
theMshaHttpRequest=new XMLHttpRequest();
}
function mshaGetElementsByClassName(_279,_27a){
var _27b=element.getElementsByTagName("*");
var _27c=new Array();
for(var i=0;i<_27b.length;i++){
var _27e=_27b[i];
var _27f=_27e.className.split(" ");
for(var j=0;j<_27f.length;j++){
if(_27f[j]==_27a){
_27c.push(_27e);
break;
}
}
}
return _27c;
}
function mshaDoRequestSync(aUrl,_282){
if(window.XMLHttpRequest){
theMshaHttpRequest=new XMLHttpRequest();
}else{
if(window.ActiveXObject){
theMshaHttpRequest=new ActiveXObject("Microsoft.XMLHTTP");
}
}
theMshaHttpRequest.open("POST",aUrl,false);
theMshaHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
theMshaHttpRequest.send(_282);
}
function mshaDoRequest(aUrl,_284,_285){
if(theIsSearching){
return;
}
theIsSearching=true;
if(window.XMLHttpRequest){
theMshaHttpRequest=new XMLHttpRequest();
}else{
if(window.ActiveXObject){
theMshaHttpRequest=new ActiveXObject("Microsoft.XMLHTTP");
}
}
theMshaHttpRequest.onreadystatechange=_285;
theMshaHttpRequest.open("POST",aUrl);
theMshaHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
theMshaHttpRequest.send(_284);
}
function debug(_286){
$("debug").value=$("debug").value+"\n"+_286;
}
msh_autocomplete.View=function(_287,aDiv){
var _289=_287;
var _28a=aDiv;
var _28b=0;
this.setEditabled=function(_28c){
if(_28c){
Element.addClassName(_289,"vocEditabled");
if(_289.title==null){
_289.title="";
}
_289.title=_289.title+"  \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c - [ALT+INSERT], \u0438\u0437\u043c\u0435\u043d\u0438\u0442\u044c - [F2]";
}
};
this.showBox=function(aXml){
var div=document.createElement("div");
div.className="autocomplete";
var ul=document.createElement("ul");
var rows=aXml.getElementsByTagName("row");
for(var i=0;i<rows.length;i++){
var li=createLineElement(rows[i]);
ul.appendChild(li);
}
div.appendChild(ul);
_28a.innerHTML="";
_28a.appendChild(div);
setSelected(0);
_28a.style.visibility="visible";
_28a.style.display="block";
};
this.setSearching=function(_293){
if(_293){
Element.addClassName(_289,"searching");
}else{
Element.removeClassName(_289,"searching");
}
};
this.hide=function(){
_28a.innerHTML="";
_28a.style.visibility="hidden";
_28a.style.display="none";
};
this.getLastId=function(){
var lis=_28a.getElementsByTagName("li");
var li=lis[lis.length-1];
return getValue(li,"span");
};
this.getFirstId=function(){
var lis=_28a.getElementsByTagName("li");
var li=lis[0];
return getValue(li,"span");
};
this.selectNext=function(){
return setSelected(_28b+1);
};
this.selectPrevious=function(){
return setSelected(_28b-1);
};
this.getSelectedName=function(){
return getSelectedBySpanClass("name");
};
this.getSelectedId=function(){
return getSelectedBySpanClass("id");
};
function getSelectedBySpanClass(_298){
var lis=_28a.getElementsByTagName("li");
for(var i=0;i<lis.length;i++){
if(lis[i].className=="selected"){
return getLiSpan(lis[i],_298);
}
}
return null;
}
function setSelected(_29b){
_28b=_29b;
var lis=_28a.getElementsByTagName("li");
var _29d=false;
for(var i=0;i<lis.length;i++){
if(i==_29b){
lis[i].className="selected";
_29d=true;
}else{
lis[i].className="";
}
}
return _29d;
}
function createLineElement(_29f){
var name=getValue(_29f,"name");
var id=getValue(_29f,"id");
var li=document.createElement("li");
var _2a3=document.createElement("span");
_2a3.className="id";
_2a3.appendChild(document.createTextNode(id));
li.appendChild(_2a3);
li.appendChild(document.createTextNode("    "));
var _2a4=document.createElement("span");
_2a4.className="name";
_2a4.appendChild(document.createTextNode(name!=""?name:" --- "));
if(name==""){
_2a4.className="name null";
}
li.appendChild(_2a4);
return li;
}
function getId(_2a5){
return getLiSpan(_2a5,"id");
}
function getName(_2a6){
return getLiSpan(_2a6,"name");
}
function getLiSpan(_2a7,_2a8){
var _2a9=_2a7.getElementsByTagName("span");
for(i=0;i<_2a9.length;i++){
var elm=_2a9[i];
if(elm.className==_2a8){
return elm.firstChild.nodeValue;
}
}
return null;
}
function getValue(_2ab,_2ac){
var _2ad=_2ab.getElementsByTagName(_2ac);
if(_2ad.length>0&&_2ad[0].firstChild){
return _2ad[0].firstChild.nodeValue;
}else{
return "";
}
}
};
msh_autocomplete.Actions=function(_2ae,_2af,_2b0,aUrl,_2b2,_2b3,_2b4){
_2ae.onblur=select;
_2ae.onfocus=onFocus;
eventutil.addEventListener(_2ae,eventutil.EVENT_KEY_DOWN,onKey);
eventutil.addEventListener(_2ae,eventutil.EVENT_KEY_UP,onKeyUp);
eventutil.addEventListener(_2ae,eventutil.EVENT_CLICK,onMouseClick);
var _2b5=aUrl;
var _2b6=_2b0;
var _2b7=false;
var _2b8=_2ae;
var _2b9=_2b8.value;
var _2ba=_2af;
var _2bb=null;
var _2bc;
var _2bd=false;
var _2be=null;
var _2bf=true;
function onFocus(){
_2bf=true;
}
this.setVocKey=function(_2c0){
_2b2=_2c0;
this.setVocId(_2ba.value);
};
this.setParentId=function(_2c1){
_2be=_2c1;
};
this.getParentId=function(){
return _2be;
};
this.setUrl=function(aUrl){
_2b5=aUrl;
this.setVocId(_2ba.value);
};
this.setShowIdInName=function(_2c3){
_2bd=_2c3;
};
this.setParent=function(_2c4){
_2b4=_2c4;
};
this.addOnChangeCallback=function(_2c5){
_2bc=_2c5;
};
this.clearValuesWithNoEvents=function(){
_2b8.value="";
_2ba.value="";
if(_2bc){
_2bc();
}
};
function getVocValueEdit(){
if(_2bb==null){
_2bb=new msh.widget.VocValueEdit(null,_2b2,_2b3,{valueChanged:onNewValue});
}
return _2bb;
}
this.setVocId=function(aId){
_2b6.setSearching(true);
VocService.getNameById(_2b2,aId,_2be,{callback:function(_2c7){
_2ba.value=aId;
_2b8.value=_2c7;
_2b6.setSearching(false);
},errorHandler:function(_2c8){
_2b8.value=_2c8;
_2b6.setSearching(false);
},warningHandler:function(_2c9){
_2b8.value=_2c9;
_2b6.setSearching(false);
}});
};
function onVocIdResponse(){
aResponse=theMshaHttpRequest;
if(theMshaHttpRequest.readyState==4){
if(theMshaHttpRequest.status==200){
alert(aResponse.responseText);
}else{
alert(aResponse.status+" "+aResponse.statusText);
error(aResponse.responseText);
}
}else{
}
}
function onNewValue(aId,_2cb){
_2b8.value=_2cb;
_2ba.value=aId;
_2b8.focus();
findFromEnteredId();
}
function selectEmpty(){
var _2cc=false;
if(isBoxShowed()){
_2cc=_2ba.value!="";
_2b8.value="";
_2ba.value="";
_2b9="";
}
setBoxShowed(false);
_2b6.hide();
if(_2cc&&_2bc){
_2bc();
}
}
function select(){
_2bf=false;
var _2cd=false;
if(isBoxShowed()){
var id=_2b6.getSelectedId();
var name=_2b6.getSelectedName();
if(name==null){
name="";
}
_2cd=_2ba.value!=id;
if(_2bd){
if(id!=null&&id!=""){
_2b8.value="("+id+") "+name;
}else{
_2b8.value=name;
}
}else{
_2b8.value=name;
}
_2ba.value=id;
_2b9=_2b8.value;
}
setBoxShowed(false);
_2b6.hide();
if(_2cd&&_2bc){
_2bc();
}
}
function findParentId(_2d0){
if(_2d0==null||_2d0=="undefined"){
return null;
}else{
if(_2d0.getVocId()==null||_2d0.getVocId()==""||_2d0.getVocId()=="undefined"){
return findParentId(_2d0.getParent());
}else{
return _2d0.getVocId();
}
}
}
function onMouseClick(_2d1){
_2bf=true;
findFromEnteredId();
_2b8.select();
}
function onKeyUp(_2d2){
_2bf=true;
var _2d3=_2d2.keyCode;
if(13==_2d3||9==_2d3){
select();
}else{
if(_2b9!=_2b8.value){
_2b9=_2b8.value;
findQuery();
}
}
}
function onKey(_2d4){
_2bf=true;
var _2d5=_2d4.keyCode;
if(_2d5==eventutil.VK_ESCAPE||_2d5==eventutil.VK_DEL){
return selectEmpty();
}else{
if(_2d4.ctrlKey||_2d4.shiftKey){
return false;
}else{
if(_2d4.altKey&&_2d5==eventutil.VK_INSERT){
getVocValueEdit().insertNewValue(findParentId(_2b4!=null?_2b4:null));
return true;
}else{
if(!isBoxShowed()&&_2d5==eventutil.VK_PAGE_DOWN){
findFromEnteredId();
}else{
switch(_2d5){
case eventutil.VK_PAGE_DOWN:
findNext();
break;
case eventutil.VK_PAGE_UP:
findPrevious();
break;
case eventutil.VK_DOWN:
if(!_2b6.selectNext()){
findNext();
}
break;
case eventutil.VK_UP:
if(!_2b6.selectPrevious()){
findPrevious();
}
break;
case eventutil.VK_ENTER:
case eventutil.VK_TAB:
select();
break;
case eventutil.VK_HOME:
mshaDoRequest(_2b5,"",onResponse);
break;
}
}
}
}
}
}
function findNextFromFirst(){
var _2d6="id="+createParentQuery();
mshaDoRequest(_2b5,_2d6,onResponse);
}
function findNext(){
var _2d7="id="+_2b6.getLastId()+createParentQuery();
mshaDoRequest(_2b5,_2d7,onResponse);
}
function findPrevious(){
mshaDoRequest(_2b5,"direction=backward&id="+_2b6.getFirstId()+createParentQuery(),onResponse);
}
function __findQuery(){
if(theTimeout){
window.clearTimeout(theTimeout);
}
theTimeout=window.setTimeout(_findQuery,500);
}
function findQuery(){
if(_2b8.value==""){
findNextFromFirst();
}else{
_2b6.setSearching(true);
mshaDoRequest(_2b5,"query="+_2b8.value+createParentQuery(),onResponse);
}
}
function createParentQuery(){
var _2d8="";
if(_2b4!=null){
var _2d9=_2b4.getVocIdForParent();
if(_2d9!=null){
_2d8="&parent="+_2d9;
}
}
if(_2d8==""){
if(_2be!=null){
_2d8="&parent="+_2be;
}
}
return _2d8;
}
function findFromEnteredId(){
mshaDoRequest(_2b5,"id="+_2ba.value+createParentQuery(),onResponse);
}
function onResponse(){
aResponse=theMshaHttpRequest;
if(theMshaHttpRequest.readyState==4){
theIsSearching=false;
if(theMshaHttpRequest.status==200){
if(_2bf){
_2b6.showBox(aResponse.responseXML);
setBoxShowed(true);
}
_2b6.setSearching(false);
}else{
alert(aResponse.status+" "+aResponse.statusText);
error(aResponse.responseText);
}
}else{
}
}
function isBoxShowed(){
return _2b7;
}
function setBoxShowed(_2da){
_2b7=_2da;
}
};
msh_autocomplete.Autocomplete=function(){
var _2db;
var _2dc;
var _2dd;
var _2de;
var _2df=null;
var _2e0;
var _2e1;
var _2e2;
var _2e3;
var _2e4=this;
this.getParent=function(){
return _2e2;
};
this.setVocTitle=function(_2e5){
_2e1=_2e5;
};
this.setVocKey=function(_2e6){
_2e0=_2e6;
if(_2df!=null){
_2df.setVocKey(_2e6);
}
};
this.setNameFieldId=function(_2e7){
_2db=$(_2e7);
};
this.setIdFieldId=function(_2e8){
_2dc=$(_2e8);
};
this.setDivId=function(_2e9){
_2dd=$(_2e9);
};
this.setUrl=function(aUrl){
_2de=aUrl;
if(_2df!=null){
_2df.setUrl(aUrl);
}
};
this.setParentId=function(_2eb){
_2df.setParentId(_2eb);
};
this.getParentId=function(){
return _2df.getParentId();
};
this.setParent=function(_2ec){
_2e2=_2ec;
_2df.setParent(_2ec);
_2e2.addOnChangeCallback(parentOnChange);
};
function parentOnChange(){
_2df.clearValuesWithNoEvents();
}
this.addOnChangeCallback=function(_2ed){
_2df.addOnChangeCallback(_2ed);
};
this.build=function(){
var view=new msh.widget.AutocompleteTableView(_2db,_2dd);
_2df=new msh_autocomplete.Actions(_2db,_2dc,view,_2de,_2e0,_2e1,_2e2);
try{
_2db.setAttribute("autocomplete","off");
}
catch(e){
}
};
this.requestFocus=function(){
_2db.focus();
};
this.getVocIdForParent=function(){
var ret=null;
var id=_2dc.value;
if(id==null||id==""){
if(_2e2!=null){
ret=_2e2.getVocIdForParent();
}
}else{
ret=id;
}
return ret;
};
this.getVocId=function(){
return _2dc.value;
};
this.getVocName=function(){
return _2db.value;
};
this.setVocId=function(aId){
_2df.setVocId(aId);
};
this.setShowIdInName=function(_2f2){
_2df.setShowIdInName(_2f2);
};
};
function showAutocompleteWindow(_2f3,_2f4,_2f5,_2f6){
window.open("autocompleteWindow.do?idField="+_2f3+"&nameField="+_2f4+"&selectedId="+_2f5+"&vocName="+_2f6,"","height=500,width=400,scrollbars=yes");
}
function showAutocomplete(_2f7,_2f8,aUrl){
alert(_2f7);
}
var ecom_tree_autocomplete={Version:"0.0"};
ecom_tree_autocomplete.Actions=function(_2fa,_2fb,_2fc,aUrl,_2fe,_2ff,_300){
_2fa.onblur=select;
_2fa.onfocus=onFocus;
eventutil.addEventListener(_2fa,eventutil.EVENT_KEY_DOWN,onKey);
eventutil.addEventListener(_2fa,eventutil.EVENT_CLICK,onMouseClick);
var _301=aUrl;
var _302=_2fc;
var _303=false;
var _304=_2fa;
var _305=_304.value;
var _306=_2fb;
var _307=null;
var _308;
var _309=false;
var _30a=null;
var _30b=true;
function onFocus(){
_30b=true;
}
this.setVocKey=function(_30c){
_2fe=_30c;
this.setVocId(_306.value);
};
this.setParentId=function(_30d){
_30a=_30d;
};
this.setUrl=function(aUrl){
_301=aUrl;
this.setVocId(_306.value);
};
this.setShowIdInName=function(_30f){
_309=_30f;
};
this.setParent=function(_310){
_300=_310;
};
this.addOnChangeCallback=function(_311){
_308=_311;
};
this.clearValuesWithNoEvents=function(){
_304.value="";
_306.value="";
if(_308){
_308();
}
};
function getVocValueEdit(){
if(_307==null){
_307=new msh.widget.VocValueEdit(null,_2fe,_2ff,{valueChanged:onNewValue});
}
return _307;
}
this.setVocId=function(aId){
_302.setSearching(true);
VocService.getNameById(_2fe,aId,_30a,{callback:function(_313){
_306.value=aId;
_304.value=_313;
_302.setSearching(false);
},errorHandler:function(_314){
_304.value=_314;
_302.setSearching(false);
},warningHandler:function(_315){
_304.value=_315;
_302.setSearching(false);
}});
};
function onVocIdResponse(){
aResponse=theMshaHttpRequest;
if(theMshaHttpRequest.readyState==4){
if(theMshaHttpRequest.status==200){
alert(aResponse.responseText);
}else{
alert(aResponse.status+" "+aResponse.statusText);
error(aResponse.responseText);
}
}else{
}
}
function onNewValue(aId,_317){
_304.value=_317;
_306.value=aId;
_304.focus();
findFromEnteredId();
}
function selectEmpty(){
var _318=false;
if(isBoxShowed()){
_318=_306.value!="";
_304.value="";
_306.value="";
_305="";
}
setBoxShowed(false);
_302.hide();
if(_318&&_308){
_308();
}
}
function select(){
document.title="sel"+_302.getPage()+"_"+document.title;
if(_302.getPage()>0){
if(isBoxShowed()){
}else{
}
_30b=true;
switch(_302.getPage()){
case 1:
_302.setPage(0);
findPrevious();
break;
case 2:
_302.setPage(0);
findNext();
break;
}
return false;
}else{
document.title="select_"+document.title;
_30b=false;
var _319=false;
if(isBoxShowed()){
var id=_302.getSelectedId();
var name=_302.getSelectedName();
if(name==null){
name="";
}
_319=_306.value!=id;
if(_309){
if(id!=null&&id!=""){
_304.value="("+id+") "+name;
}else{
_304.value=name;
}
}else{
_304.value=name;
}
_306.value=id;
_305=_304.value;
}
setBoxShowed(false);
_302.hide();
if(_319&&_308){
_308();
}
}
}
function findParentId(_31c){
if(_31c==null||_31c=="undefined"){
return null;
}else{
if(_31c.getVocId()==null||_31c.getVocId()==""||_31c.getVocId()=="undefined"){
return findParentId(_31c.getParent());
}else{
return _31c.getVocId();
}
}
}
function onMouseClick(_31d){
_30b=true;
findFromEnteredId();
_304.select();
}
function onKey(_31e){
_30b=true;
document.title="key_"+document.title;
var _31f=_31e.keyCode;
if(_31f==eventutil.VK_ESCAPE||_31f==eventutil.VK_DEL){
return selectEmpty();
}else{
if(_31e.ctrlKey||_31e.shiftKey){
return false;
}else{
if(_31e.altKey&&_31f==eventutil.VK_INSERT){
getVocValueEdit().insertNewValue(findParentId(_300!=null?_300:null));
return true;
}else{
if(!isBoxShowed()&&_31f==eventutil.VK_PAGE_DOWN){
findFromEnteredId();
}else{
switch(_31f){
case eventutil.VK_PAGE_DOWN:
findNext();
break;
case eventutil.VK_PAGE_UP:
findPrevious();
break;
case eventutil.VK_LEFT:
alert("\u041d\u0410\u0417\u0410\u0414");
break;
case eventutil.VK_RIGHT:
alert("\u0412\u041f\u0415\u0420\u0415\u0414");
break;
case eventutil.VK_DOWN:
if(!_302.selectNext()){
findNext();
}
break;
case eventutil.VK_UP:
if(!_302.selectPrevious()){
findPrevious();
}
break;
case eventutil.VK_ENTER:
case eventutil.VK_TAB:
select();
break;
case eventutil.VK_HOME:
mshaDoRequest(_301,"",onResponse);
break;
}
}
}
}
}
}
function findNextFromFirst(){
document.title="findNextFromFirst_"+document.title;
var _320="id="+createParentQuery();
mshaDoRequest(_301,_320,onResponse);
}
function findNext(){
document.title="findNext_"+document.title;
var _321="id="+_302.getLastId()+createParentQuery();
mshaDoRequest(_301,_321,onResponse);
}
function findPrevious(){
document.title="findPrevious_"+document.title;
mshaDoRequest(_301,"direction=backward&id="+_302.getFirstId(),onResponse);
}
function __findQuery(){
if(theTimeout){
window.clearTimeout(theTimeout);
}
theTimeout=window.setTimeout(_findQuery,500);
}
function findQuery(){
if(_304.value==""){
findNextFromFirst();
}else{
_302.setSearching(true);
mshaDoRequest(_301,"query="+_304.value+createParentQuery(),onResponse);
}
}
function createParentQuery(){
var _322="";
if(_300!=null){
var _323=_300.getVocIdForParent();
if(_323!=null){
_322="&parent="+_323;
}
}
if(_322==""){
if(_30a!=null){
_322="&parent="+_30a;
}
}
return _322;
}
function findFromEnteredId(){
document.title="findFromEnteredId_"+document.title;
mshaDoRequest(_301,"id="+_306.value+createParentQuery(),onResponse);
}
function onResponse(){
document.title="response_"+document.title;
aResponse=theMshaHttpRequest;
if(theMshaHttpRequest.readyState==4){
theIsSearching=false;
if(theMshaHttpRequest.status==200){
if(_30b){
_302.showBox(aResponse.responseXML);
setBoxShowed(true);
}
_302.setSearching(false);
}else{
alert(aResponse.status+" "+aResponse.statusText);
error(aResponse.responseText);
}
}else{
}
}
function isBoxShowed(){
document.title="isBoxShowed_"+document.title;
return _303;
}
function setBoxShowed(_324){
_303=_324;
}
};
ecom_tree_autocomplete.Autocomplete=function(){
var _325;
var _326;
var _327;
var _328;
var _329=null;
var _32a;
var _32b;
var _32c;
var _32d;
var _32e=this;
this.getParent=function(){
return _32c;
};
this.setVocTitle=function(_32f){
_32b=_32f;
};
this.setVocKey=function(_330){
_32a=_330;
if(_329!=null){
_329.setVocKey(_330);
}
};
this.setNameFieldId=function(_331){
_325=$(_331);
};
this.setIdFieldId=function(_332){
_326=$(_332);
};
this.setDivId=function(_333){
_327=$(_333);
};
this.setUrl=function(aUrl){
_328=aUrl;
if(_329!=null){
_329.setUrl(aUrl);
}
};
this.setParentId=function(_335){
_329.setParentId(_335);
};
this.setParent=function(_336){
_32c=_336;
_329.setParent(_336);
_32c.addOnChangeCallback(parentOnChange);
};
function parentOnChange(){
_329.clearValuesWithNoEvents();
}
this.addOnChangeCallback=function(_337){
_329.addOnChangeCallback(_337);
};
this.build=function(){
var view=new msh.widget.TreeAutocompleteTableView(_325,_327);
_329=new ecom_tree_autocomplete.Actions(_325,_326,view,_328,_32a,_32b,_32c);
try{
_325.setAttribute("autocomplete","off");
}
catch(e){
}
};
this.requestFocus=function(){
alert("focus");
_325.focus();
};
this.getVocIdForParent=function(){
var ret=null;
var id=_326.value;
if(id==null||id==""){
if(_32c!=null){
ret=_32c.getVocIdForParent();
}
}else{
ret=id;
}
return ret;
};
this.getVocId=function(){
return _326.value;
};
this.getVocName=function(){
return _325.value;
};
this.setVocId=function(aId){
_329.setVocId(aId);
};
this.setShowIdInName=function(_33c){
_329.setShowIdInName(_33c);
};
};
function showTreeAutocomplete(_33d,_33e,aUrl){
alert(_33d);
}
msh.widget.VocValueEdit=function(_340,_341,_342,_343){
var _344=null;
var _345=false;
var _346=this;
var _347=null;
function initDialog(){
if(_344==null){
_344=new msh.widget.VocValueEditDialog(_341+"Id",_342,_346);
_344.buildDialog();
}
}
this.insertNewValue=function(_348){
_347=_348;
initDialog();
_345=true;
_344.showInsert();
};
this.changeValue=function(aId,_34a){
initDialog();
_345=false;
_344.showEdit(aId,_34a);
};
this.onSave=function(_34b,_34c){
VocEditService.createVocValue(_341,_34b,_34c,_347,{callback:function(aId){
_344.hide();
_343.valueChanged(aId,_34c);
},errorHandler:function(_34e){
alert("\u041e\u0448\u0438\u0431\u043a\u0430: "+_34e);
},warningHandler:function(_34f){
alert("\u041f\u0440\u0435\u0434\u0443\u043f\u0440\u0435\u0436\u0434\u0435\u043d\u0438\u0435: "+_34f);
}});
};
};
msh.widget.VocValueEditDialog=function(_350,_351,_352){
var _353;
var _354;
var _355=null;
var _356=null;
var _357=null;
var _358=null;
this.hide=function(){
_353.hide();
};
this.showInsert=function(){
_353.show();
_358.innerHTML="\u0421\u043e\u0437\u0434\u0430\u043d\u0438\u0435 \u043d\u043e\u0432\u043e\u0433\u043e \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u044f";
_356.focus();
_355.value="";
_356.value="";
_357.value="\u0421\u043e\u0437\u0434\u0430\u0442\u044c \u043d\u043e\u0432\u043e\u0435 \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435";
};
this.showEdit=function(aId,_35a){
_353.show();
_358.innerHTML="\u0418\u0437\u043c\u0435\u043d\u0435\u043d\u0438\u0435";
_355.value=aId;
_356.value=_35a;
_356.focus();
_357.value="\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u0438\u044f";
};
function onPressButton(){
_352.onSave(_355.value,_356.value);
}
function hideDialog(){
_353.hide();
}
this.buildDialog=function(){
var div=document.createElement("div");
div.id=_350;
div.className="dialog";
var h2=document.createElement("h2");
h2.appendChild(document.createTextNode(_351));
var _35d=document.createElement("div");
_35d.className="rootPane";
_358=document.createElement("h3");
var form=document.createElement("form");
var _35f=document.createElement("table");
var tr=document.createElement("tr");
var td=document.createElement("td");
td.className="label";
td.appendChild(document.createTextNode("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u0438\u043a\u0430\u0442\u043e\u0440"));
tr.appendChild(td);
td=document.createElement("td");
var _362=document.createElement("input");
_362.type="text";
_355=_362;
td.appendChild(_362);
tr.appendChild(td);
tr=document.createElement("tr");
td=document.createElement("td");
td.className="label";
td.appendChild(document.createTextNode("\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435:"));
tr.appendChild(td);
td=document.createElement("td");
_362=document.createElement("input");
_362.type="text";
_356=_362;
td.appendChild(_362);
tr.appendChild(td);
_35f.appendChild(tr);
form.appendChild(_35f);
_357=document.createElement("input");
_357.type="button";
_357.value="\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c";
_357.onclick=onPressButton;
var _363=document.createElement("input");
_363.type="button";
_363.value="\u041e\u0442\u043c\u0435\u043d\u0438\u0442\u044c";
_363.onclick=hideDialog;
form.appendChild(_357);
form.appendChild(document.createTextNode("   "));
form.appendChild(_363);
div.appendChild(h2);
div.appendChild(_35d);
_35d.innerHTML="";
_35d.appendChild(_358);
_35d.appendChild(form);
document.body.appendChild(div);
_353=new msh.widget.Dialog(div);
_354=_35d;
h2.onclick=hideDialog;
};
};
var theDefaultTimeOut;
var theDefaultTimeOutCnt=4;
var theDefaultFieldName;
var theDefaultEvt;
function adjustMessage(text,aDiv){
var _366=document.getElementById(aDiv);
var _367=true;
if(_367){
var _368=theDefaultEvt;
}
var _369=getScrollXY();
_366.innerHTML=text;
if(aDiv=="divInstantMessage"){
_366.style.top=(_369[1]+50)+"px";
_366.style.left="100px";
_366.style.visibility="visible";
}
}
function getScrollXY(){
var _36a=0,_36b=0;
if(typeof (window.pageYOffset)=="number"){
_36b=window.pageYOffset;
_36a=window.pageXOffset;
}else{
if(document.body&&(document.body.scrollLeft||document.body.scrollTop)){
_36b=document.body.scrollTop;
_36a=document.body.scrollLeft;
}else{
if(document.documentElement&&(document.documentElement.scrollLeft||document.documentElement.scrollTop)){
_36b=document.documentElement.scrollTop;
_36a=document.documentElement.scrollLeft;
}
}
}
return [_36a,_36b];
}
function getDefinition(term,evt,aDiv){
if(aDiv==null||aDiv==""){
aDiv="divInstantMessage";
}
var _36f=document.getElementById(aDiv);
_36f.innerHTML="\u0417\u0430\u0433\u0440\u0443\u0437\u043a\u0430...";
var _370=term.split("?");
var _371=_370[1];
mshaDoRequest(_370[0],_371,function(){
onResponse1(aDiv);
});
return false;
}
function goToPage(_372,aId,_374){
if(_372.indexOf("javascript:")!=-1){
var func=_372.split("javascript:")[1];
if(func.indexOf("void(0)")>-1){
return;
}else{
if(func.indexOf("(")==-1){
func=func+"(";
}else{
if(!func.lastIndexOf(",")){
func=func+",";
}
}
event(func+"'"+aId+"','"+_374+"')");
alert(func+"'"+aId+"'"+")");
}
}else{
var _376=_372.indexOf("?")==-1?"?":"&";
var url=_372+_376+"id="+aId+"&tmp="+Math.random();
if(_374==null){
window.location=url;
}else{
getDefinition(url+_374+"&short=Short");
}
}
}
function onResponse1(aDiv){
var _379=theMshaHttpRequest;
if(theMshaHttpRequest.readyState==4){
var _37a=document.getElementById(aDiv);
theIsSearching=false;
if(theMshaHttpRequest.status==200){
adjustMessage(_379.responseText,aDiv);
}else{
adjustMessage(_379.status+" "+_379.statusText+" "+_379.responseText,aDiv);
}
}else{
}
}
function hideMessage(){
var _37b=document.getElementById("divInstantMessage");
_37b.style.visibility="hidden";
_37b.innerHTML="";
}
var funcemergencymessage={func:function(){
clearTimeout(theDefaultTimeOut);
VocService.getEmergencyMessages({callback:function(_37c){
viewEmergencyUserMessage(_37c);
}});
}};
function viewEmergencyUserMessage(_37d){
var _37e=JSON.parse(_37d);
var cnt=_37e.params.length;
var txt="";
var ids="";
if(cnt>0){
for(var ind=0;ind<cnt;ind++){
var _383=_37e.params[ind];
txt+=_383.messageTitle+" "+_383.messageText+" \u043e\u0442 "+_383.infoReceipt+".\n";
if(ids!=""){
ids+=",";
}
ids+=_383.id;
}
VocService.checkEmergencyMessages(ids,txt,{callback:function(_384){
alert(txt);
--theDefaultTimeOutCnt;
if(theDefaultTimeOutCnt>0){
theDefaultTimeOut=setTimeout(funcemergencymessage.func,180000);
}
}});
}
}
function hideUserMessage(aId){
VocService.hiddenMessage(aId,{callback:function(_386){
if($("userMessagePop"+aId)){
$("userMessagePop"+aId).style.display="none";
}
}});
}
function checkUserMessage(aId){
VocService.checkMessage(aId,{callback:function(_388){
if($("userMessageContainer"+aId)){
$("userMessageContainer"+aId).style.display="none";
}
},errorHandler:function(_389){
alert("\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u0440\u0438 \u043e\u0431\u0440\u0430\u0431\u043e\u0442\u043a\u0435 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f!");
},warningHandler:function(_38a){
alert("\u041e\u0448\u0438\u0431\u043a\u0430 \u043f\u0440\u0438 \u043e\u0431\u0440\u0430\u0431\u043e\u0442\u043a\u0435 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f!");
}});
}
function getCheckedRadio(aFrm,_38c){
var _38d=aFrm[_38c];
if(_38d){
if(_38d.length){
for(i=0;i<_38d.length;i++){
if(_38d[i].checked==true){
return _38d[i].value;
}
}
}else{
if(_38d.checked==true){
return _38d.value;
}
}
}
return "";
}
function getCheckedCheckBox(aFrm,_38f,_390){
var _391=aFrm.elements[_38f];
var _392="";
if(_391){
if(_391.length){
for(i=0;i<_391.length;i++){
if(_391[i].checked==true){
_392=_392+_390+_391[i].value;
}
}
}else{
if(_391.checked==true){
_392=_392+_390+_391.value;
}
}
}
if(_392!=""){
_392=_392.substring(_390.length);
}
return _392;
}
function setCheckedAllRadio(aFrm,_394,_395){
var _396=aFrm[_394];
if(_396){
if(_396.length){
_396[0].checked=_395;
}else{
_396.checked=_395;
}
}
}
function setCheckedAllCheckBox(aFrm,_398,_399){
var _39a=aFrm.elements[_398];
if(_39a){
if(_39a.length){
for(i=0;i<_39a.length;i++){
_39a[i].checked=_399;
}
}else{
_39a.checked=_399;
}
}
}
function setFocusOnField(_39b){
theDefaultFieldName=_39b;
eventutil.addObserveListener(window,"load",_zetFocusOnField);
}
function _zetFocusOnField(){
try{
$(theDefaultFieldName).focus();
}
catch(e){
}
try{
$(theDefaultFieldName).select();
}
catch(e){
}
}
function getCurrentDate(){
var dt=new Date();
return format2day(dt.getDate())+"."+format2day(dt.getMonth()+1)+"."+dt.getFullYear();
}
function format2day(aCnt){
if(aCnt>9){
return aCnt;
}else{
return "0"+aCnt;
}
}
msh.widget.OneToManyAuto=function(_39e,_39f,_3a0,_3a1,_3a2,_3a3,_3a4,_3a5,_3a6,_3a7,_3a8,_3a9){
var _3aa=null;
var _3ab=null;
var _3ac=false;
var _3ad=null;
var _3ae=null;
var _3af=null;
this.getSerial=function(){
return _39e;
};
function ce(_3b0){
return document.createElement(_3b0);
}
this.add=function(){
var _3b1=ce("td");
_3b1.style.className="label";
var _3b2=ce("label");
_3ab=_3b2;
var _3b3=ce("td");
var _3b4=ce("input");
if(_3a6){
if(_3a9!=null&&_3a9!=""&&_3a9!="null"){
_3b4=ce("a");
_3b4.href=_3a9+"?id="+(_39f.value?_39f.value:"");
_3b4.innerHTML=_39f.name?_39f.name:"";
}else{
_3b4=ce("input");
_3b4.value=_39f.name?_39f.name:"";
_3b4.className="viewOnly maxHorizontalSize";
_3b4.disabled=true;
_3b4.style.background="none";
_3b4.style.border="none";
_3b4.style.color="black";
_3b4.style.className="";
}
}else{
_3b4=ce("input");
_3b4.className="autocomplete maxHorizontalSize";
}
_3b3.appendChild(_3b4);
_3ad=_3b4;
_3ad.id="otma_input_"+_39e;
var div=ce("div");
_3b3.appendChild(div);
var _3b6=ce("hidden");
_3b6.id="otma_hidden_"+_39e;
_3ae=_3b6;
var _3b7=ce("td");
var a=ce("a");
a.innerHTML="\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c";
a.href="javascript:void(0)";
_3b7.appendChild(a);
_3aa=a;
_3a0.appendChild(_3b1);
_3a0.appendChild(_3b3);
if(!_3a6){
_3a0.appendChild(_3b7);
}
_3aa.onclick=onClick;
_3ae.value="";
_3ae.value=_39f.value?_39f.value:"";
if(_3a6){
}else{
_3ad.value=_39f.name?_39f.name:"";
}
addAutocomlete(div);
if(_3a6){
}
eventutil.addEventListener(_3ad,eventutil.EVENT_BLUR,_3a4);
};
function addAutocomlete(aDiv){
if(!_3a6){
_3af=new msh_autocomplete.Autocomplete();
_3af.setUrl("simpleVocAutocomplete/"+_3a5);
_3af.setIdFieldId(_3ae);
_3af.setNameFieldId(_3ad);
_3af.setDivId(aDiv);
_3af.setVocKey(_3a5);
_3af.setVocTitle(_3a1);
_3af.build();
if(_3a7!=null&&_3a7!=""&&_3a7!="null"){
_3af.setParentId(_3a7);
}
if(_3a8!=null&&_3a8!=""&&_3a8!="null"){
_3af.setParent(_3a8+"Autocomplete");
}
}
}
function onClick(){
if(_3ac){
_3a0.parentNode.removeChild(_3a0);
_3a2(_39e);
}else{
_3a3();
}
}
this.setIsRemoveAction=function(_3ba){
_3ac=_3ba;
_3aa.innerHTML=_3ba?"\u0423\u0431\u0440\u0430\u0442\u044c":"\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0435\u0449\u0435";
_3aa.title=_3ba?"\u0423\u0431\u0440\u0430\u0442\u044c "+_3a1:"\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0435\u0449\u0435 \u043e\u0434\u0438\u043d "+_3a1;
_3aa.className="manyToManyActionLink";
};
this.setTitleVisibled=function(_3bb){
_3ab.innerHTML=_3bb?_3a1:"";
};
this.focus=function(){
_3ad.focus();
_3ad.select();
};
this.clearData=function(){
_3ad.value="";
_3ae.value="";
};
this.setValue=function(aId,_3bd){
_3ad.value=_3bd;
_3ae.value=aId;
};
this.setParentId=function(_3be){
_3a7=_3be;
_3af.setParentId(_3a7);
};
this.getJson=function(){
_39f["value"]=_3ae.value;
return _39f;
};
};
msh.widget.OneToManyAutocompletes=function(_3bf,_3c0,_3c1,_3c2,_3c3,_3c4,_3c5,_3c6,_3c7){
var _3c8=null;
var _3c9=null;
var _3ca=new Array();
var _3cb=1;
this.install=function(){
buildSurroundView();
var _3cc=$(_3c1).value;
if(_3cc!=null&&_3cc!="null"&&_3cc!=""){
var json=eval("("+$(_3c1).value+")");
var _3ce=json.childs;
for(var i=0;i<_3ce.length;i++){
var tr=ce("tr");
_3c8.appendChild(tr);
var one=new msh.widget.OneToManyAuto(getNextSerial(),_3ce[i],tr,_3c2,onRemove,onAdd,recalc,_3c3,_3c4,_3c5,_3c6,_3c7);
one.add();
_3ca.push(one);
}
if(_3ce.length==0){
onAdd();
}
}else{
onAdd();
}
onUpdate();
};
this.setIds=function(_3d2){
if(_3d2!=null&&_3d2!="null"&&_3d2!=""){
var json=eval("("+_3d2+")");
var _3d4=json.childs;
var len=_3ca.length;
for(var i=0;i<_3d4.length;i++){
var _3d7=_3d4[i];
if(i<len){
_3ca[i].setValue(_3d7.value?_3d7.value:"",_3d7.name?_3d7.name:"");
}else{
var tr=ce("tr");
_3c8.appendChild(tr);
var one=new msh.widget.OneToManyAuto(getNextSerial(),_3d7,tr,_3c2,onRemove,onAdd,recalc,_3c3,_3c4,_3c5,_3c6);
one.add();
_3ca.push(one);
}
}
}
onUpdate();
};
this.setParentId=function(_3da){
_3c5=_3da;
for(var i=0;i<_3ca.length;i++){
_3ca[i].setParentId(_3c5);
}
};
this.clearData=function(){
for(var i=0;i<_3ca.length;i++){
_3ca[i].clearData();
}
};
function getNextSerial(){
return ++_3cb;
}
function onRemove(_3dd){
var ar=new Array();
var _3df=0;
for(var i=0;i<_3ca.length;i++){
if(_3dd!=_3ca[i].getSerial()){
ar.push(_3ca[i]);
}else{
_3ca[i<_3ca.length-1?i+1:0].focus();
}
}
_3ca=ar;
onUpdate();
}
function onAdd(){
var tr=ce("tr");
_3c8.appendChild(tr);
var _3e2=new Object();
_3e2["id"]="";
_3e2["value"]="";
var one=new msh.widget.OneToManyAuto(getNextSerial(),_3e2,tr,_3c2,onRemove,onAdd,recalc,_3c3,_3c4,_3c5,_3c6);
one.add();
one.focus();
_3ca.push(one);
onUpdate();
}
function onUpdate(){
setTitleVisibled(_3ca.length!=1);
for(var i=0;i<_3ca.length-1;i++){
_3ca[i].setIsRemoveAction(true);
}
_3ca[_3ca.length-1].setIsRemoveAction(false);
recalc();
}
function recalc(){
var json=new Object();
var _3e6=new Array();
for(var i=0;i<_3ca.length;i++){
_3e6.push(_3ca[i].getJson());
}
json["childs"]=_3e6;
$(_3c1).value=JSON.stringify(json);
}
function setTitleVisibled(_3e8){
if(_3e8){
_3c9.parentNode.style.border="1px solid #999";
}else{
_3c9.parentNode.style.border="none";
}
for(var i=0;i<_3ca.length;i++){
_3ca[i].setTitleVisibled(!_3e8);
}
_3c9.innerHTML=_3e8?_3c2:"";
}
function ce(_3ea){
return document.createElement(_3ea);
}
function buildSurroundView(){
var _3eb=ce("fieldset");
var _3ec=ce("legend");
var _3ed=ce("table");
_3ed.border="0";
var _3ee=ce("tbody");
_3ed.appendChild(_3ee);
_3eb.appendChild(_3ec);
_3eb.appendChild(_3ed);
_3bf.innerHTML="";
_3bf.appendChild(_3eb);
_3ec.innerHTML=_3c2;
_3c8=_3ee;
_3c9=_3ec;
}
};

