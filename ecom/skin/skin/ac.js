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
Element.removeClassName(_49,_4a);
_49.className+=" "+_4a;
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
msh.widget.Dialog=function(_12e){
this.show=function(){
_12e.style.display="block";
_12e.style.visibility="visible";
msh.effect.FadeEffect.putFade();
_12e.style.zIndex=1+Math.round(msh.effect.FadeEffect.getIndex());
_12e.parentNode.removeChild(_12e);
document.body.appendChild(_12e);
center(_12e);
};
this.hide=function(){
msh.effect.FadeEffect.pushFade();
_12e.style.display="none";
_12e.style.visibility="hidden";
};
};
function center(_12f){
try{
_12f=$(_12f);
}
catch(e){
return;
}
var _130=0;
var _131=0;
if(typeof (window.innerWidth)=="number"){
_130=window.innerWidth;
_131=window.innerHeight;
}else{
if(document.documentElement&&(document.documentElement.clientWidth||document.documentElement.clientHeight)){
_130=document.documentElement.clientWidth;
_131=document.documentElement.clientHeight;
}else{
if(document.body&&(document.body.clientWidth||document.body.clientHeight)){
_130=document.body.clientWidth;
_131=document.body.clientHeight;
}
}
}
_12f.style.position="absolute";
_12f.style.zIndex=99;
var _132=0;
if(document.documentElement&&document.documentElement.scrollTop){
_132=document.documentElement.scrollTop;
}else{
if(document.body&&document.body.scrollTop){
_132=document.body.scrollTop;
}else{
if(window.pageYOffset){
_132=window.pageYOffset;
}else{
if(window.scrollY){
_132=window.scrollY;
}
}
}
}
var _133=getDimensions(_12f);
var setX=(_130-_133.width)/2;
var setY=(_131-_133.height)/3+_132;
setX=(setX<0)?0:setX;
setY=(setY<0)?0:setY;
_12f.style.left=setX+"px";
_12f.style.top=setY+"px";
_12f.style.display="block";
}
function getDimensions(_136){
_136=$(_136);
var _137=_136.style.display;
if(_137!="none"&&_137!=null){
return {width:_136.offsetWidth,height:_136.offsetHeight};
}
var els=_136.style;
var _139=els.visibility;
var _13a=els.position;
var _13b=els.display;
els.visibility="hidden";
els.position="absolute";
els.display="block";
var _13c=_136.clientWidth;
var _13d=_136.clientHeight;
els.display=_13b;
els.position=_13a;
els.visibility=_139;
return {width:_13c,height:_13d};
}
msh.widget.TreeTableDialog=function(_13e,_13f,_140){
this.theName=_13e;
this.theDialog=null;
this.theTitle=_13f;
this.theDialogId=_13e+"Dialog";
this.theDialog=null;
this.theRootPane=null;
this.theLastSearch="";
this.theData=null;
this.theGoFunction=null;
this.theObject=null;
this.theControlObject=_140;
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
var _143=document.createElement("div");
_143.className="rootPane treeTable";
div.appendChild(h2);
div.appendChild(_143);
document.body.appendChild(div);
this.theDialog=new msh.widget.Dialog(div);
this.theRootPane=_143;
};
msh.widget.TreeTableDialog.prototype.setMessage=function(_144){
this.theRootPane.appendChild(document.createTextNode(_144));
};
msh.widget.TreeTableDialog.prototype.show=function(){
this.theDialog.show();
};
msh.widget.TreeTableDialog.prototype.updateContent=function(_145,_146,_147){
this.theData=_145;
this.theGoFunction=_146;
this.theObject=_147;
var _148=document.createElement("table");
_148.className="tabview sel tableArrow";
var _149=document.createElement("tbody");
_148.appendChild(_149);
var _14a=document.createElement("tr");
_149.appendChild(_14a);
for(i=0;i<_145.titles.length;i++){
var th=document.createElement("th");
th.appendChild(document.createTextNode(_145.titles[i]));
_14a.appendChild(th);
}
for(row=0;row<_145.rows.length;row++){
var tr=document.createElement("tr");
tr.onclick="";
for(col=0;col<_145.rows[row].cols.length;col++){
var td=document.createElement("td");
td.appendChild(document.createTextNode(_145.rows[row].cols[col]));
td.onclick=_146.bind(_147,null,_145.rows[row].id,true,null);
tr.appendChild(td);
}
if(row==0){
tr.appendChild(this.thePageUp);
}
if(row==1){
var td=document.createElement("td");
td.appendChild(document.createTextNode(" "));
td.style.cssText="background-color: white; color: blue";
td.rowSpan=_145.rows.length-2;
tr.appendChild(td);
}
if(row==_145.rows.length-1){
tr.appendChild(this.thePageDown);
}
_149.appendChild(tr);
}
var ul=document.createElement("ul");
for(i=0;i<_145.path.length;i++){
var li=document.createElement("li");
li.appendChild(document.createTextNode(" >> "));
var a=document.createElement("a");
li.appendChild(a);
a.appendChild(document.createTextNode(_145.path[i].name));
a.onclick=_146.bind(_147,_145.path[i].id,_145.path[i].parentId,true,null);
a.href="#";
li.appendChild(document.createTextNode(" "));
ul.appendChild(li);
}
var link=document.createElement("a");
link.href="#";
link.onclick=this.search.bind(this,_145.firstId,_145.firstParentId,_146,_147);
link.appendChild(document.createTextNode("\u041d\u0430\u0439\u0442\u0438"));
var _152=document.createElement("input");
_152.value="\u0412\u044b\u0431\u0440\u0430\u0442\u044c";
_152.type="button";
_152.onclick=_147.selectCurrent;
var _153=document.createElement("input");
_153.value="\u0421\u0431\u0440\u043e\u0441\u0438\u0442\u044c";
_153.type="button";
_153.onclick=_147.clear;
this.theRootPane.innerHTML="";
this.theRootPane.appendChild(ul);
this.theRootPane.appendChild(document.createElement("hr"));
this.theRootPane.appendChild(link);
this.theRootPane.appendChild(document.createElement("hr"));
this.theRootPane.appendChild(_148);
this.theRootPane.appendChild(_152);
this.theRootPane.appendChild(_153);
};
msh.widget.TreeTableDialog.prototype.search=function(_154,_155,_156,_157){
this.theLastSearch=prompt("\u041d\u0430\u0439\u0442\u0438",this.theLastSearch);
var args=new Array();
args.push(_154);
args.push(_155);
args.push(true);
args.push(this.theLastSearch);
_156.apply(_157,args);
};
msh.widget.TreeTableDialog.prototype.createBackTd=function(){
var link=document.createElement("td");
link.appendChild(document.createTextNode("\u25b2"));
link.style.cssText="background-color: white; color: blue";
link.onclick=this.theControlObject.onPagePrevious;
return link;
};
msh.widget.TreeTableDialog.prototype.createForwardTd=function(){
var _15a=document.createElement("td");
_15a.style.cssText="background-color: white; color: blue";
_15a.appendChild(document.createTextNode("\u25bc"));
_15a.onclick=this.theControlObject.onPageNext;
return _15a;
};
msh.widget.TreeTableDialog.prototype.hide=function(){
this.theDialog.hide();
};
msh.widget.AutocompleteTableView=function(_15b,_15c){
var _15d=0;
this.setEditabled=function(_15e){
if(_15e){
Element.addClassName(_15b,"vocEditabled");
if(_15b.title==null){
_15b.title="";
}
_15b.title=_15b.title+"  \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c - [ALT+INSERT], \u0438\u0437\u043c\u0435\u043d\u0438\u0442\u044c - [F2]";
}
};
this.setSearching=function(_15f){
if(_15f){
Element.addClassName(_15b,"searching");
}else{
Element.removeClassName(_15b,"searching");
}
};
this.selectNext=function(){
};
this.showBox=function(aXml){
var id=getValue(aXml,"requestId");
var _162=document.createElement("table");
var _163=document.createElement("tbody");
var rows=aXml.getElementsByTagName("row");
if(rows.length==0){
var tr=createTr(0,null);
_163.appendChild(tr);
}else{
for(var i=0;i<rows.length;i++){
var tr=createTr(i,rows[i]);
_163.appendChild(tr);
}
}
_162.appendChild(_163);
_15c.style.display="block";
_15c.className="autocomplete";
if(_15b.clientWidth){
_15c.style.width=_15b.clientWidth+"px";
_162.style.width=_15b.clientWidth+"px";
}else{
_15c.style.width="30em";
_162.style.width="30em";
}
_15c.style.visibility="visible";
_15c.innerHTML="";
_15c.appendChild(_162);
if(!setSelectedId(id)){
setSelected(0);
}
};
this.hide=function(){
_15c.innerHTML="";
_15c.style.visibility="hidden";
_15c.style.display="none";
};
this.selectNext=function(){
return setSelected(_15d+1);
};
this.selectPrevious=function(){
return setSelected(_15d-1);
};
function setSelectedId(aId){
var _168=false;
var lis=_15c.getElementsByTagName("tr");
for(var i=0;i<lis.length;i++){
var tr=lis[i];
if(aId==lis[i].myId){
setSelected(i);
_168=true;
break;
}
}
return _168;
}
this.getSelectedId=function(){
var trs=_15c.getElementsByTagName("tr");
for(var i=0;i<trs.length;i++){
if(trs[i].className=="selected"){
return trs[i].myId;
}
}
return null;
};
this.getSelectedName=function(){
var trs=_15c.getElementsByTagName("tr");
for(var i=0;i<trs.length;i++){
if(trs[i].className=="selected"){
return trs[i].myName;
}
}
return null;
};
this.getLastId=function(){
var trs=_15c.getElementsByTagName("tr");
if(trs.length>0){
var tr=trs[trs.length-1];
return tr.myId;
}else{
return "";
}
};
this.getFirstId=function(){
var trs=_15c.getElementsByTagName("tr");
var tr=trs[0];
return tr.myId;
};
function setSelected(_174){
_15d=_174;
var lis=_15c.getElementsByTagName("tr");
var _176=false;
for(var i=0;i<lis.length;i++){
if(i==_174){
lis[i].className="selected";
_176=true;
}else{
lis[i].className="";
}
}
return _176;
}
function test123(){
alert("hello");
}
function createTr(_178,_179){
var name=getValue(_179,"name");
if(name==""){
name="-";
}
var id=getValue(_179,"id");
var tr=document.createElement("tr");
tr.myId=id;
tr.myName=name;
var tdId=document.createElement("td");
var _17e=document.createElement("td");
tdId.appendChild(document.createTextNode(id));
_17e.appendChild(document.createTextNode(name));
tdId.className="id";
_17e.className="name";
tr.appendChild(tdId);
tr.appendChild(_17e);
tr.onmousedown=setSelected.bind(this,_178);
return tr;
}
function getValue(_17f,_180){
if(_17f==null){
return "";
}
var _181=_17f.getElementsByTagName(_180);
if(_181.length>0&&_181[0].firstChild){
return _181[0].firstChild.nodeValue;
}else{
return "";
}
}
};
msh.widget.TreeAutocompleteTableView=function(_182,_183){
var _184=0;
var _185=0;
this.getPage=function(){
return _185;
};
this.setPage=function(_186){
_185=_186;
};
this.setEditabled=function(_187){
if(_187){
Element.addClassName(_182,"vocEditabled");
if(_182.title==null){
_182.title="";
}
_182.title=_182.title+"  \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c - [ALT+INSERT], \u0438\u0437\u043c\u0435\u043d\u0438\u0442\u044c - [F2]";
}
};
this.setSearching=function(_188){
if(_188){
Element.addClassName(_182,"searching");
}else{
Element.removeClassName(_182,"searching");
}
};
this.selectNext=function(){
};
this.showBox=function(aXml){
var id=getValue(aXml,"requestId");
var _18b=document.createElement("table");
var _18c=document.createElement("tbody");
var rows=aXml.getElementsByTagName("row");
if(rows.length==0){
var tr=createTr(0,null);
_18c.appendChild(tr);
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
_18c.appendChild(tr);
}
}
_18b.appendChild(_18c);
_183.style.display="block";
_183.className="autocomplete";
if(_182.clientWidth){
_183.style.width=_182.clientWidth+"px";
_18b.style.width=_182.clientWidth+"px";
}else{
_183.style.width="30em";
_18b.style.width="30em";
}
_183.style.visibility="visible";
_183.innerHTML="";
_183.appendChild(_18b);
if(!setSelectedId(id)){
setSelected(0);
}
};
this.hide=function(){
_183.innerHTML="";
_183.style.visibility="hidden";
_183.style.display="none";
};
this.selectNext=function(){
return setSelected(_184+1);
};
this.selectPrevious=function(){
return setSelected(_184-1);
};
function setSelectedId(aId){
var _192=false;
var lis=_183.getElementsByTagName("tr");
for(var i=0;i<lis.length;i++){
var tr=lis[i];
if(aId==lis[i].myId){
setSelected(i);
_192=true;
break;
}
}
return _192;
}
this.getSelectedId=function(){
var trs=_183.getElementsByTagName("tr");
for(var i=0;i<trs.length;i++){
if(trs[i].className=="selected"){
return trs[i].myId;
}
}
return null;
};
this.getSelectedName=function(){
var trs=_183.getElementsByTagName("tr");
for(var i=0;i<trs.length;i++){
if(trs[i].className=="selected"){
return trs[i].myName;
}
}
return null;
};
this.getLastId=function(){
var trs=_183.getElementsByTagName("tr");
if(trs.length>0){
var tr=trs[trs.length-1];
return tr.myId;
}else{
return "";
}
};
this.getFirstId=function(){
var trs=_183.getElementsByTagName("tr");
var tr=trs[0];
return tr.myId;
};
function setPage(_19e){
_185=_19e;
}
function setSelected(_19f){
switch(_185){
case 1:
document.title="1down_"+document.title;
return true;
break;
case 2:
document.title="2down_"+document.title;
return true;
break;
default:
_184=_19f;
var lis=_183.getElementsByTagName("tr");
var _1a1=false;
for(var i=0;i<lis.length;i++){
if(i==_19f){
lis[i].className="selected";
document.title="s"+i+""+document.title;
_1a1=true;
}else{
lis[i].className="";
}
}
document.title=_1a1+"+"+document.title;
return _1a1;
}
}
function test123(_1a3){
alert(_1a3);
}
function createTr(_1a4,_1a5,_1a6){
var name=getValue(_1a5,"name");
if(name==""){
name="-";
}
var id=getValue(_1a5,"id");
var tr=document.createElement("tr");
tr.myId=id;
tr.myName=name;
var _1aa=document.createElement("td");
var tdId=document.createElement("td");
var _1ac=document.createElement("td");
var _1ad=document.createElement("td");
var _1ae=document.createElement("td");
tdId.appendChild(document.createTextNode(id));
_1ad.appendChild(document.createTextNode("<"));
_1ac.appendChild(document.createTextNode(name));
_1aa.appendChild(document.createTextNode(">"));
_1ad.className="navig";
_1aa.className="navig";
tdId.className="id";
_1ac.className="name";
_1ae.className="navig";
switch(_1a6){
case 1:
_1ae.appendChild(document.createTextNode("^"));
_1ae.onmousedown=setPage.bind(this,1);
break;
case 2:
_1ae.appendChild(document.createTextNode("*"));
_1ae.onmousedown=setPage.bind(this,2);
break;
default:
break;
}
_185=0;
tr.appendChild(_1ad);
tr.appendChild(tdId);
tr.appendChild(_1ac);
tr.appendChild(_1aa);
tr.appendChild(_1ae);
tr.onmousedown=setSelected.bind(this,_1a4);
return tr;
}
function getValue(_1af,_1b0){
if(_1af==null){
return "";
}
var _1b1=_1af.getElementsByTagName(_1b0);
if(_1b1.length>0&&_1b1[0].firstChild){
return _1b1[0].firstChild.nodeValue;
}else{
return "";
}
}
};
msh.widget.TreeTable=function(_1b2,_1b3,_1b4,_1b5){
var _1b6="";
var _1b7=null;
var _1b8=null;
var _1b9=null;
var _1ba=this;
this.onPageNext=function(){
go(_1b9.lastId,_1b9.lastParentId,true,null);
};
this.onPagePrevious=function(){
go(_1b9.firstId,_1b9.lastParentId,false,null);
};
var _1bb=new msh.widget.TreeTableDialog(_1b3,_1b4,this);
this.selectCurrent=function(){
selectData(_1b9);
};
this.clear=function(){
_1bb.hide();
_1b7.innerHTML="";
_1b5.value="";
};
function selectData(data){
_1bb.hide();
var _1bd="";
for(var i=0;i<data.path.length;i++){
_1bd=_1bd+" "+data.path[i].name;
}
_1b7.innerHTML=_1bd;
_1b5.value=data.path[data.path.length-1].id;
}
this.setCurrentData=function(_1bf){
_1b9=_1bf;
};
this.installTo=function(_1c0){
theField=_1c0;
var _1c1=_1c0.parentNode;
_1c1.removeChild(_1c0);
var span=document.createElement("span");
_1b7=span;
span.appendChild(document.createTextNode(_1c0.value));
_1c1.appendChild(span);
var _1c3=document.createElement("input");
_1c3.id=_1b3+"EditButton";
_1c3.type="button";
_1c3.onclick=this.onEditButtonClick;
_1c3.value="\u0418\u0437\u043c\u0435\u043d\u0438\u0442\u044c";
_1c1.appendChild(_1c3);
_1b8=_1c3;
var _1c4=document.createElement("div");
_1c4.id=_1b3+"Holder";
_1c1.appendChild(_1c4);
};
this.onEditButtonClick=function(){
_1b8.blur();
_1bb.show();
go(_1b5.value!=""?_1b5.value:null,null,true);
};
function go(_1c5,_1c6,_1c7,_1c8){
if(_1c8){
_1bb.setMessage("\u041f\u043e\u0438\u0441\u043a "+_1c8+" ...");
}else{
_1bb.setMessage("\u0416\u0434\u0438\u0442\u0435 ...");
}
if(theTableArrow!=null){
theTableArrow.remove();
}
var pars="";
if(_1c6!=null){
pars+="parentId="+_1c6+"&";
}
if(_1c5!=null){
pars+="fromId="+_1c5+"&";
}
if(!_1c7){
pars+="direction=backward&";
}
if(_1c8!=null){
pars+="search="+_1c8+"&";
}
var cObj=YAHOO.util.Connect.asyncRequest("POST",_1b2,{success:onComplete,failure:function(_1cb){
alert(_1cb.statusText);
}},pars);
}
function onComplete(_1cc){
var data=eval("("+_1cc.responseText+")");
_1b9=data;
if(data.rows.length==0){
_1bb.hide();
var _1ce="";
for(var i=0;i<data.path.length;i++){
_1ce=_1ce+" "+data.path[i].name;
}
_1b7.innerHTML=_1ce;
_1b5.value=data.path[data.path.length-1].id;
}else{
_1bb.updateContent(data,go,_1ba);
}
if(theTableArrow!=null){
theTableArrow.remove();
}
theTableArrow=new tablearrow.TableArrow("tab1");
}
function search(_1d0,_1d1,_1d2){
_1b6=prompt("\u041d\u0430\u0439\u0442\u0438:","\u041f\u043e\u0438\u0441\u043a \u043f\u043e \u041a\u041b\u0410\u0414\u0420",_1b6);
go(_1d0,_1d1,_1d2,_1b6);
}
};
function createTestData(){
return ({titles:["hello","heee123","asdf"],rows:[{id:"1",parentId:"123",cols:[123,123,123]},{id:"1",parentId:"123",cols:[123,123,123]},{id:"1",parentId:"123",cols:[123,123,123]},{id:"1",parentId:"123",cols:[123,123,123]},{id:"1",parentId:"123",cols:[123,123,123]},{id:"1",parentId:"123",cols:[123,123,123]}]});
}
var theMainFormDataInstance=null;
msh.util.FormData=function(){
var _1d3=null;
var _1d4=null;
this.init=function(_1d5){
if(_1d5==null){
alert("\u041d\u0435\u0442 \u0444\u043e\u0440\u043c\u044b");
}
if(_1d3==null){
_1d3=Form.serialize(_1d5);
_1d4=_1d5;
}else{
alert("msh.util.FormData: \u0424\u043e\u0440\u043c\u0430 \u0443\u0436\u0435 \u043f\u0440\u043e\u0438\u043d\u0438\u0446\u0438\u0430\u043b\u0438\u0437\u0438\u0440\u043e\u0432\u0430\u043d\u0430");
}
};
this.isChanged=function(){
return _1d3!=Form.serialize(_1d4);
};
this.isChangedForLink=function(){
if(_1d4==null){
return true;
}
var _1d6=_1d3!=Form.serialize(_1d4);
if(_1d6){
return confirm("\u0414\u0430\u043d\u043d\u044b\u0435 \u0432 \u0444\u043e\u0440\u043c\u0435 \u0438\u0437\u043c\u0435\u043d\u0438\u043b\u0438\u0441\u044c. \u041f\u0440\u043e\u0434\u043e\u043b\u0436\u0438\u0442\u044c \u0431\u0435\u0437 \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0438\u044f \u0434\u0430\u043d\u043d\u044b\u0445?");
}
return !_1d6;
};
};
msh.util.FormData.getInstance=function(){
if(theMainFormDataInstance==null){
theMainFormDataInstance=new msh.util.FormData();
}
return theMainFormDataInstance;
};
var errorutil={Version:"1.0"};
errorutil.ShowFieldError=function(_1d7,_1d8){
var _1d9=_1d7;
var _1da=_1d8;
_1d7.title=_1d8;
var _1db=document.createElement("DIV");
_1db.innerHTML=_1d8;
_1db.id="errorDiv";
_1db.style.borderColor="#F06";
_1db.style.color="#F06";
_1d7.parentNode.style.border="2px solid #F06";
_1d7.parentNode.appendChild(_1db);
};
errorutil.HideError=function(_1dc){
var _1dd=_1dc;
_1dc.parentNode.style.border="";
_1dc.title="";
try{
var _1de=_1dc.parentNode.lastChild;
if(_1de.tagName=="DIV"&&_1de.id=="errorDiv"){
_1dc.parentNode.removeChild(_1de);
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
EnterSupporter=function(_1e1,_1e2){
this.select=function(_1e3){
var _1e4=_1e3.keyCode;
if(_1e3.shiftKey&&eventutil.isKey(_1e3,eventutil.VK_ESCAPE)){
$("cancelButton").click();
}else{
if(_1e3.ctrlKey&&eventutil.isKey(_1e3,eventutil.VK_ENTER)){
$("submitButton").click();
}else{
if(eventutil.isKey(_1e3,eventutil.VK_ENTER)){
_1e1.blur();
try{
_1e2.focus();
_1e2.select();
return false;
}
catch(e){
}
}
}
}
};
};
eventutil.addEventListener=function(_1e5,_1e6,_1e7){
try{
_1e5.attachEvent("on"+_1e6,_1e7);
}
catch(e){
try{
_1e5.addEventListener(_1e6,_1e7,false);
}
catch(e){
}
}
};
eventutil.removeEventListener=function(_1e8,_1e9,_1ea){
try{
_1e8.detachEvent("on"+_1e9,_1ea);
}
catch(e){
try{
alert(_1ea);
_1e8.removeEventListener(_1e9,_1ea,true);
}
catch(e){
alert(e);
}
}
};
eventutil.addObserveListener=function(_1eb,_1ec,_1ed){
Event.observe(_1eb,_1ec,_1ed,false);
};
eventutil.addEnterSupport=function(_1ee,_1ef){
try{
var _1f0=$(_1ee);
var _1f1=$(_1ef);
if(_1f0!=null&&_1f1!=null){
eventutil.addEventListener(_1f0,"keyup",new EnterSupporter(_1f0,_1f1).select);
_1f0.setAttribute("autocomplete","off");
_1f1.setAttribute("autocomplete","off");
}
}
catch(e){
}
};
eventutil.isKey=function(_1f2,_1f3){
return _1f2.keyCode==_1f3;
};
PrivateAccessKeyEvent=function(_1f4,_1f5,_1f6,_1f7){
this.theKeyCode=_1f4;
this.theIsCtrl=_1f5;
this.theIsShift=_1f7;
this.theIsAlt=_1f6;
};
function createSingleKey(_1f8){
return new PrivateAccessKeyEvent(_1f8,false,false,false);
}
function createCtrl(_1f9){
return new PrivateAccessKeyEvent(_1f9,true,false,false);
}
function createCtrlShift(_1fa){
return new PrivateAccessKeyEvent(_1fa,true,false,true);
}
function createShift(_1fb){
return new PrivateAccessKeyEvent(_1fb,false,false,true);
}
function createAlt(_1fc){
return new PrivateAccessKeyEvent(_1fc,false,true,false);
}
var accesskeyutil={Version:"1.0",ALT_0:createAlt(eventutil.VK_0),ALT_1:createAlt(eventutil.VK_1),ALT_2:createAlt(eventutil.VK_2),ALT_3:createAlt(eventutil.VK_3),ALT_4:createAlt(eventutil.VK_4),ALT_5:createAlt(eventutil.VK_5),ALT_6:createAlt(eventutil.VK_6),ALT_7:createAlt(eventutil.VK_7),ALT_8:createAlt(eventutil.VK_8),ALT_9:createAlt(eventutil.VK_9),ALT_A:createAlt(eventutil.VK_A),ALT_B:createAlt(eventutil.VK_B),ALT_C:createAlt(eventutil.VK_C),ALT_D:createAlt(eventutil.VK_D),ALT_N:createAlt(eventutil.VK_N),ALT_DEL:createAlt(eventutil.VK_DEL),CTRL_0:createCtrl(eventutil.VK_0),CTRL_1:createCtrl(eventutil.VK_1),CTRL_2:createCtrl(eventutil.VK_2),CTRL_3:createCtrl(eventutil.VK_3),CTRL_4:createCtrl(eventutil.VK_4),CTRL_5:createCtrl(eventutil.VK_5),CTRL_6:createCtrl(eventutil.VK_6),CTRL_7:createCtrl(eventutil.VK_7),CTRL_8:createCtrl(eventutil.VK_8),CTRL_9:createCtrl(eventutil.VK_9),CTRL_A:createCtrl(eventutil.VK_A),CTRL_B:createCtrl(eventutil.VK_B),CTRL_C:createCtrl(eventutil.VK_C),CTRL_D:createCtrl(eventutil.VK_D),CTRL_N:createCtrl(eventutil.VK_N),CTRL_DEL:createCtrl(eventutil.VK_DEL),SHIFT_0:createShift(eventutil.VK_0),SHIFT_1:createShift(eventutil.VK_1),SHIFT_2:createShift(eventutil.VK_2),SHIFT_3:createShift(eventutil.VK_3),SHIFT_4:createShift(eventutil.VK_4),SHIFT_5:createShift(eventutil.VK_5),SHIFT_6:createShift(eventutil.VK_6),SHIFT_7:createShift(eventutil.VK_7),SHIFT_8:createShift(eventutil.VK_8),SHIFT_9:createShift(eventutil.VK_9),SHIFT_A:createShift(eventutil.VK_A),SHIFT_B:createShift(eventutil.VK_B),SHIFT_C:createShift(eventutil.VK_C),SHIFT_D:createShift(eventutil.VK_D),SHIFT_N:createShift(eventutil.VK_N),SHIFT_DEL:createShift(eventutil.VK_DEL),SHIFT_CTRL_0:createCtrlShift(eventutil.VK_0),SHIFT_CTRL_1:createCtrlShift(eventutil.VK_1),SHIFT_CTRL_2:createCtrlShift(eventutil.VK_2),SHIFT_CTRL_3:createCtrlShift(eventutil.VK_3),SHIFT_CTRL_4:createCtrlShift(eventutil.VK_4),SHIFT_CTRL_5:createCtrlShift(eventutil.VK_5),SHIFT_CTRL_6:createCtrlShift(eventutil.VK_6),SHIFT_CTRL_7:createCtrlShift(eventutil.VK_7),SHIFT_CTRL_8:createCtrlShift(eventutil.VK_8),SHIFT_CTRL_9:createCtrlShift(eventutil.VK_9),SHIFT_CTRL_A:createCtrlShift(eventutil.VK_A),SHIFT_CTRL_B:createCtrlShift(eventutil.VK_B),SHIFT_CTRL_C:createCtrlShift(eventutil.VK_C),SHIFT_CTRL_D:createCtrlShift(eventutil.VK_D),SHIFT_CTRL_N:createCtrlShift(eventutil.VK_N),SHIFT_CTRL_DEL:createCtrlShift(eventutil.VK_DEL),F12:createSingleKey(eventutil.VK_F12)};
accesskeyutil.AccessKeyListener=function(_1fd,_1fe){
this.onKey=function(_1ff){
var _200=_1ff.keyCode;
if(_200==_1fe.theKeyCode&&_1ff.ctrlKey==_1fe.theIsCtrl&&_1ff.shiftKey==_1fe.theIsShift&&_1ff.altKey==_1fe.theIsAlt){
_1fd.focus();
if(_1fd.onclick){
var ok=_1fd.onclick();
if(ok){
window.location=_1fd.href;
}
}else{
window.location=_1fd.href;
}
return false;
}
};
};
accesskeyutil.registerKey=function(_202,_203){
eventutil.addEventListener(document,"keydown",new accesskeyutil.AccessKeyListener(_202,_203).onKey);
};
var theTimeout=null;
var snilsutil={Version:"1.0"};
snilsutil.SnilsField=function(_204){
_204.onblur=check;
var _205=_204;
var _206=null;
function liveCheck(_207){
var _208=_207.keyCode;
if(_208==eventutil.VK_BACKSPACE){
}else{
var size=_205.value.length;
if(size==3||size==7){
_205.value=_205.value+"-";
}else{
if(size==11){
_205.value=_205.value+" ";
}else{
if(size>14){
_205.value=_205.value.substring(0,14);
}
}
}
}
}
eventutil.addEventListener(_204,"keydown",liveCheck);
eventutil.addEventListener(_204,"keyup",liveCheck);
function check(_20a){
try{
_206=errorutil.HideError(_205);
var _20b=_205.value;
if(!(_20b==null||_20b=="")){
var _20c=parseSnils(_20b);
}
}
catch(e){
_206=errorutil.ShowFieldError(_205,e.message);
return false;
}
}
};
function parseSnils(_20d){
var _20e=new RegExp("[0-9]{3}[-]{1}[0-9]{3}[-]{1}[0-9]{3}[ ]{1}[0-9]{2}");
if(!_20e.test(_20d)){
throw errorutil.SetErrorObj("\u0424\u043e\u0440\u043c\u0430 \u0432\u0432\u043e\u0434\u0430 \u0421\u041d\u0418\u041b\u0421: NNN-NNN-NNN NN. \u041d\u0430\u043f\u0440\u0438\u043c\u0435\u0440: 111-111-111 11");
}
return "";
}
var theTimeout=null;
var dateutil={Version:"1.0"};
dateutil.DateField=function(_20f){
eventutil.addEventListener(_20f,"dblclick",function(){
if(self.gfPop2){
gfPop2.fPopCalendar($(_20f.id));
}
});
_20f.onblur=check;
var _210=_20f;
var _211=null;
function liveCheck(_212){
var _213=_212.keyCode;
if(_213==eventutil.VK_BACKSPACE){
}else{
var size=_210.value.length;
if(size==2||size==5){
_210.value=_210.value+".";
}else{
if(size>10){
_210.value=_210.value.substring(0,10);
}
}
}
}
eventutil.addEventListener(_20f,"keydown",liveCheck);
eventutil.addEventListener(_20f,"keyup",liveCheck);
function check(_215){
try{
_211=errorutil.HideError(_210);
var _216=_210.value;
if(!(_216==null||_216=="")){
var date=parseDate(_216);
if(_216!=date){
_210.value=date;
}
}
}
catch(e){
_211=errorutil.ShowFieldError(_210,e.message);
return false;
}
}
};
dateutil.PeriodFields=function(_218,_219,_21a,_21b,_21c){
try{
_218.attachEvent("onblur",onLinker);
}
catch(e){
try{
_218.addEventListener("blur",onLinker,false);
}
catch(e){
}
}
var _21d=_218;
var _21e=_21d.value;
var _21f=_219;
var _220=parseInt(_21a,10);
var _221=parseInt(_21b,10);
var _222=parseInt(_21c,10);
function onLinker(_223){
try{
var _224=parseDate(_21d.value);
var yyyy=parseInt(_224.substring(6,_224.length),10);
var dd=parseInt(_224.substring(0,2),10);
var mm=parseInt(_224.substring(3,5),10);
var date=new Date(yyyy,mm-1,dd);
_21d.value=monthDayFormat(date.getDate())+"."+monthDayFormat(date.getMonth()+1)+"."+date.getYear();
if(_222!=null&&_222>0){
date.setDate(dd+_222);
}
if(_221!=null&&_221>0){
date.setMonth(mm+_221);
}
if(_220!=null&&_220>0){
date.setYear(yyyy+_220);
}
_21f.value=monthDayFormat(date.getDate())+"."+monthDayFormat(date.getMonth()+1)+"."+date.getYear();
}
catch(e){
}
}
};
dateutil.YearLinkAction=function(_229,_22a){
try{
_229.attachEvent("onblur",onLinker);
}
catch(e){
try{
_229.addEventListener("blur",onLinker,false);
}
catch(e){
}
}
var _22b=_229;
var _22c=_22b.value;
function onLinker(_22d){
try{
if(_22a.value==""){
var _22e=parseDate(_22b.value);
var yyyy=parseInt(_22e.substring(6,_22e.length),10);
yyyy+=1;
_22a.value=_22e.substring(0,6)+yyyy;
}
}
catch(e){
}
}
};
function parseDate(_230){
while(_230.indexOf("-")!=-1){
_230=replaceString(_230,"-","/");
}
while(_230.indexOf(",")!=-1){
_230=replaceString(_230,",","/");
}
while(_230.indexOf(".")!=-1){
_230=replaceString(_230,".","/");
}
var _231=_230.indexOf("/");
var _232=_230.lastIndexOf("/");
if(_231!=-1&&_231==_232){
throw errorutil.SetErrorObj("\u0424\u043e\u0440\u043c\u0430 \u0432\u0432\u043e\u0434\u0430 \u0434\u0430\u0442\u044b: \u0414\u0414.\u041c\u041c.\u0413\u0413\u0413\u0413. \u041d\u0430\u043f\u0440\u0438\u043c\u0435\u0440: 31.12.2004");
}
if(_231!=-1){
var dd=parseInt(_230.substring(0,_231),10);
var mm=parseInt(_230.substring(_231+1,_232),10);
var yyyy=parseInt(_230.substring(_232+1,_230.length),10);
}else{
var dd=parseInt(_230.substring(0,2),10);
var mm=parseInt(_230.substring(2,4),10);
var yyyy=parseInt(_230.substring(4,_230.length),10);
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
if(yyyy>=15){
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
function onDateFld(_236,_237){
if(isDate(_236)){
var _238=_236.value;
var _239=_238.indexOf(".");
var _23a=_238.lastIndexOf(".");
var dd=parseInt(_238.substring(0,_239),10);
var mm=parseInt(_238.substring(_239+1,_23a),10);
var yyyy=parseInt(_238.substring(_23a+1,_238.length),10);
_237.value=dd+"."+mm+"."+(yyyy+1);
}
}
function replaceString(aStr,_23f,_240){
var _241=getFront(aStr,_23f);
var end=getEnd(aStr,_23f);
if(_241!=null&&end!=null){
return _241+_240+end;
}
return null;
}
function getFront(aStr,_244){
var _245=aStr.indexOf(_244);
if(_245==-1){
return null;
}
return aStr.substring(0,_245);
}
function getEnd(aStr,_247){
var _248=aStr.indexOf(_247);
if(_248==-1){
return null;
}
return aStr.substring(_248+_247.length,aStr.length);
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
timeutil.TimeField=function(_24a){
_24a.onblur=check;
var _24b=_24a;
eventutil.addEventListener(_24a,"dblclick",function(){
if(self.gfPop1){
gfPop1.fPopCalendar($(_24a.id));
}
});
var _24c=null;
function liveCheck(_24d){
var _24e=_24d.keyCode;
if(_24e==eventutil.VK_BACKSPACE){
}else{
var size=_24b.value.length;
if(size==2){
_24b.value=_24b.value+":";
}else{
if(size>5){
_24b.value=_24b.value.substring(0,5);
}
}
}
}
eventutil.addEventListener(_24a,"keydown",liveCheck);
eventutil.addEventListener(_24a,"keyup",liveCheck);
function check(_250){
_24c=errorutil.HideError(_24b);
if(_24b.value==""){
return;
}
var ar=_24b.value.split(":");
if(ar.length<2){
errorutil.ShowFieldError(_24b,"\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d\u043e \u0432\u0440\u0435\u043c\u044f");
}else{
var hour=0;
var _253=0;
try{
hour=parseInt(ar[0],10);
if(hour<0||hour>23){
errorutil.ShowFieldError(_24b,"\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d\u044b \u0447\u0430\u0441\u044b");
}
try{
_253=parseInt(ar[1],10);
if(_253<0||_253>59){
errorutil.ShowFieldError(_24b,"\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d\u044b \u043c\u0438\u043d\u0443\u0442\u044b");
}else{
_24b.value=format(hour)+":"+format(_253);
}
}
catch(e){
errorutil.ShowFieldError(_24b,"\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d\u044b \u043c\u0438\u043d\u0443\u0442\u044b");
}
}
catch(e){
errorutil.ShowFieldError(_24b,"\u041d\u0435\u043f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u043e \u0432\u0432\u0435\u0434\u0435\u043d\u044b \u0447\u0430\u0441\u044b");
}
}
}
function format(_254){
var str=(!isNaN(_254)?_254:"00")+"";
for(var i=0;str.length<2;i++){
str="0"+str;
}
return str;
}
};
function onKeyPress(_257){
var evt=(_257)?_257:window.event;
var _259=(evt.which)?evt.which:evt.keyCode;
if(evt.ctrlKey&&(_259==13||_259==10)){
var _25a=$("defaultSaveButton");
if(_25a){
_25a.click();
}
}else{
if(_259==27||((_259==26||_259==122)&&evt.ctrlKey)){
var _25a=$("defaultCancelButton");
if(_25a){
_25a.click();
}
}
}
}
document.onkeypress=onKeyPress;
var theTabs=new Array();
var theLastSelectedTabId=null;
function getElementsByClassName(_25b){
var rl=new Array();
var re=new RegExp("(^| )"+_25b+"( |$)");
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
function showTab3(_260){
Form.enable(document.forms[0]);
Element.removeClassName($("tabbedPaneContent"),"preview");
var _261=getElementsByClassName("tabPane");
var _262=100;
for(var i=0;i<_261.length;i++){
$(_261[i].id).style.visibility="hidden";
$(_261[i].id).style.position="absolute";
if(_262<_261[i].clientWidth){
_262=_261[i].clientWidth;
}
if(_261[i].firstChild.className=="previewHeader"){
_261[i].removeChild(_261[i].firstChild);
}
Element.removeClassName($(_261[i].id+"Link"),"selected");
}
Element.addClassName($(_260+"Link"),"selected");
$(_260).style.visibility="visible";
$("tabbedPaneContent").style.height=($(_260).clientHeight+20)+"px";
$("tabbedPaneFooter").style.visibility="visible";
$("tabbedPaneHeader").style.visibility="visible";
}
function showTab(_264){
if(theLastSelectedTabId!=null){
theTabs[theLastSelectedTabId].style.visibility="hidden";
theTabs[theLastSelectedTabId].style.display="none";
Element.removeClassName(theLastSelectedTabId+"Link","selected");
}
theTabs[_264].style.visibility="visible";
theTabs[_264].style.display="block";
theLastSelectedTabId=_264;
Element.addClassName(theLastSelectedTabId+"Link","selected");
}
function showPreview(){
$("tabbedPaneHeader").style.visibility="hidden";
Element.addClassName($("tabbedPaneContent"),"preview");
Form.disable(document.forms[0]);
var _265=getElementsByClassName("tabPane");
for(var i=0;i<_265.length;i++){
var div=document.createElement("div");
Element.addClassName(div,"previewHeader");
var h2=document.createElement("h2");
h2.appendChild(document.createTextNode(_265[i].title));
var a=document.createElement("a");
a.setAttribute("href","javascript:showTab('"+_265[i].id+"')");
a.appendChild(document.createTextNode("[\u0418\u0437\u043c\u0435\u043d\u0438\u0442\u044c]"));
div.appendChild(h2);
div.appendChild(a);
_265[i].appendChild(div);
_265[i].insertBefore(div,_265[i].firstChild);
$("tabbedPaneHeader").style.visibility="hidden";
}
}
function tabbedPaneInit(){
eventutil.addObserveListener(window,"load",_tabbedPaneInit);
}
function onKey(_26a){
var _26b=_26a.keyCode;
if(_26b==9){
showTab("tabMain");
return false;
}
return true;
}
function _tabbedPaneInit(){
var ul=document.createElement("ul");
var _26d=getElementsByClassName("tabPane");
var _26e=true;
var _26f=null;
for(var i=0;i<_26d.length;i++){
if(i==0){
_26f=_26d[i].id;
}
var li=document.createElement("li");
var a=document.createElement("a");
a.appendChild(document.createTextNode(_26d[i].title));
a.setAttribute("href","javascript:showTab('"+_26d[i].id+"')");
a.setAttribute("id",_26d[i].id+"Link");
li.appendChild(a);
ul.appendChild(li);
theTabs[_26d[i].id]=_26d[i];
}
$("tabbedPaneHeader").innerHTML="";
$("tabbedPaneHeader").appendChild(ul);
$("tabbedPaneHeader").appendChild(document.createElement("hr"));
$("tabbedPaneFooter").style.visibility="visible";
showTab(_26f);
$(theDefaultFieldName).focus();
$(theDefaultFieldName).select();
setFocusOnField(theDefaultFieldName);
}
var theTimeout=null;
function error(_273){
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
function mshaGetElementsByClassName(_274,_275){
var _276=element.getElementsByTagName("*");
var _277=new Array();
for(var i=0;i<_276.length;i++){
var _279=_276[i];
var _27a=_279.className.split(" ");
for(var j=0;j<_27a.length;j++){
if(_27a[j]==_275){
_277.push(_279);
break;
}
}
}
return _277;
}
function mshaDoRequestSync(aUrl,_27d){
if(window.XMLHttpRequest){
theMshaHttpRequest=new XMLHttpRequest();
}else{
if(window.ActiveXObject){
theMshaHttpRequest=new ActiveXObject("Microsoft.XMLHTTP");
}
}
theMshaHttpRequest.open("POST",aUrl,false);
theMshaHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
theMshaHttpRequest.send(_27d);
}
function mshaDoRequest(aUrl,_27f,_280){
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
theMshaHttpRequest.onreadystatechange=_280;
theMshaHttpRequest.open("POST",aUrl);
theMshaHttpRequest.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
theMshaHttpRequest.send(_27f);
}
function debug(_281){
$("debug").value=$("debug").value+"\n"+_281;
}
msh_autocomplete.View=function(_282,aDiv){
var _284=_282;
var _285=aDiv;
var _286=0;
this.setEditabled=function(_287){
if(_287){
Element.addClassName(_284,"vocEditabled");
if(_284.title==null){
_284.title="";
}
_284.title=_284.title+"  \u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c - [ALT+INSERT], \u0438\u0437\u043c\u0435\u043d\u0438\u0442\u044c - [F2]";
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
_285.innerHTML="";
_285.appendChild(div);
setSelected(0);
_285.style.visibility="visible";
_285.style.display="block";
};
this.setSearching=function(_28e){
if(_28e){
Element.addClassName(_284,"searching");
}else{
Element.removeClassName(_284,"searching");
}
};
this.hide=function(){
_285.innerHTML="";
_285.style.visibility="hidden";
_285.style.display="none";
};
this.getLastId=function(){
var lis=_285.getElementsByTagName("li");
var li=lis[lis.length-1];
return getValue(li,"span");
};
this.getFirstId=function(){
var lis=_285.getElementsByTagName("li");
var li=lis[0];
return getValue(li,"span");
};
this.selectNext=function(){
return setSelected(_286+1);
};
this.selectPrevious=function(){
return setSelected(_286-1);
};
this.getSelectedName=function(){
return getSelectedBySpanClass("name");
};
this.getSelectedId=function(){
return getSelectedBySpanClass("id");
};
function getSelectedBySpanClass(_293){
var lis=_285.getElementsByTagName("li");
for(var i=0;i<lis.length;i++){
if(lis[i].className=="selected"){
return getLiSpan(lis[i],_293);
}
}
return null;
}
function setSelected(_296){
_286=_296;
var lis=_285.getElementsByTagName("li");
var _298=false;
for(var i=0;i<lis.length;i++){
if(i==_296){
lis[i].className="selected";
_298=true;
}else{
lis[i].className="";
}
}
return _298;
}
function createLineElement(_29a){
var name=getValue(_29a,"name");
var id=getValue(_29a,"id");
var li=document.createElement("li");
var _29e=document.createElement("span");
_29e.className="id";
_29e.appendChild(document.createTextNode(id));
li.appendChild(_29e);
li.appendChild(document.createTextNode("    "));
var _29f=document.createElement("span");
_29f.className="name";
_29f.appendChild(document.createTextNode(name!=""?name:" --- "));
if(name==""){
_29f.className="name null";
}
li.appendChild(_29f);
return li;
}
function getId(_2a0){
return getLiSpan(_2a0,"id");
}
function getName(_2a1){
return getLiSpan(_2a1,"name");
}
function getLiSpan(_2a2,_2a3){
var _2a4=_2a2.getElementsByTagName("span");
for(i=0;i<_2a4.length;i++){
var elm=_2a4[i];
if(elm.className==_2a3){
return elm.firstChild.nodeValue;
}
}
return null;
}
function getValue(_2a6,_2a7){
var _2a8=_2a6.getElementsByTagName(_2a7);
if(_2a8.length>0&&_2a8[0].firstChild){
return _2a8[0].firstChild.nodeValue;
}else{
return "";
}
}
};
msh_autocomplete.Actions=function(_2a9,_2aa,_2ab,aUrl,_2ad,_2ae,_2af){
_2a9.onblur=select;
_2a9.onfocus=onFocus;
eventutil.addEventListener(_2a9,eventutil.EVENT_KEY_DOWN,onKey);
eventutil.addEventListener(_2a9,eventutil.EVENT_KEY_UP,onKeyUp);
eventutil.addEventListener(_2a9,eventutil.EVENT_CLICK,onMouseClick);
var _2b0=aUrl;
var _2b1=_2ab;
var _2b2=false;
var _2b3=_2a9;
var _2b4=_2b3.value;
var _2b5=_2aa;
var _2b6=null;
var _2b7;
var _2b8=false;
var _2b9=null;
var _2ba=true;
function onFocus(){
_2ba=true;
}
this.setVocKey=function(_2bb){
_2ad=_2bb;
this.setVocId(_2b5.value);
};
this.setParentId=function(_2bc){
_2b9=_2bc;
};
this.setUrl=function(aUrl){
_2b0=aUrl;
this.setVocId(_2b5.value);
};
this.setShowIdInName=function(_2be){
_2b8=_2be;
};
this.setParent=function(_2bf){
_2af=_2bf;
};
this.addOnChangeCallback=function(_2c0){
_2b7=_2c0;
};
this.clearValuesWithNoEvents=function(){
_2b3.value="";
_2b5.value="";
if(_2b7){
_2b7();
}
};
function getVocValueEdit(){
if(_2b6==null){
_2b6=new msh.widget.VocValueEdit(null,_2ad,_2ae,{valueChanged:onNewValue});
}
return _2b6;
}
this.setVocId=function(aId){
_2b1.setSearching(true);
VocService.getNameById(_2ad,aId,_2b9,{callback:function(_2c2){
_2b5.value=aId;
_2b3.value=_2c2;
_2b1.setSearching(false);
},errorHandler:function(_2c3){
_2b3.value=_2c3;
_2b1.setSearching(false);
},warningHandler:function(_2c4){
_2b3.value=_2c4;
_2b1.setSearching(false);
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
function onNewValue(aId,_2c6){
_2b3.value=_2c6;
_2b5.value=aId;
_2b3.focus();
findFromEnteredId();
}
function selectEmpty(){
var _2c7=false;
if(isBoxShowed()){
_2c7=_2b5.value!="";
_2b3.value="";
_2b5.value="";
_2b4="";
}
setBoxShowed(false);
_2b1.hide();
if(_2c7&&_2b7){
_2b7();
}
}
function select(){
_2ba=false;
var _2c8=false;
if(isBoxShowed()){
var id=_2b1.getSelectedId();
var name=_2b1.getSelectedName();
if(name==null){
name="";
}
_2c8=_2b5.value!=id;
if(_2b8){
if(id!=null&&id!=""){
_2b3.value="("+id+") "+name;
}else{
_2b3.value=name;
}
}else{
_2b3.value=name;
}
_2b5.value=id;
_2b4=_2b3.value;
}
setBoxShowed(false);
_2b1.hide();
if(_2c8&&_2b7){
_2b7();
}
}
function findParentId(_2cb){
if(_2cb==null||_2cb=="undefined"){
return null;
}else{
if(_2cb.getVocId()==null||_2cb.getVocId()==""||_2cb.getVocId()=="undefined"){
return findParentId(_2cb.getParent());
}else{
return _2cb.getVocId();
}
}
}
function onMouseClick(_2cc){
_2ba=true;
findFromEnteredId();
_2b3.select();
}
function onKeyUp(_2cd){
_2ba=true;
var _2ce=_2cd.keyCode;
if(13==_2ce||9==_2ce){
select();
}else{
if(_2b4!=_2b3.value){
_2b4=_2b3.value;
findQuery();
}
}
}
function onKey(_2cf){
_2ba=true;
var _2d0=_2cf.keyCode;
if(_2d0==eventutil.VK_ESCAPE||_2d0==eventutil.VK_DEL){
return selectEmpty();
}else{
if(_2cf.ctrlKey||_2cf.shiftKey){
return false;
}else{
if(_2cf.altKey&&_2d0==eventutil.VK_INSERT){
getVocValueEdit().insertNewValue(findParentId(_2af!=null?_2af:null));
return true;
}else{
if(!isBoxShowed()&&_2d0==eventutil.VK_PAGE_DOWN){
findFromEnteredId();
}else{
switch(_2d0){
case eventutil.VK_PAGE_DOWN:
findNext();
break;
case eventutil.VK_PAGE_UP:
findPrevious();
break;
case eventutil.VK_DOWN:
if(!_2b1.selectNext()){
findNext();
}
break;
case eventutil.VK_UP:
if(!_2b1.selectPrevious()){
findPrevious();
}
break;
case eventutil.VK_ENTER:
case eventutil.VK_TAB:
select();
break;
case eventutil.VK_HOME:
mshaDoRequest(_2b0,"",onResponse);
break;
}
}
}
}
}
}
function findNextFromFirst(){
var _2d1="id="+createParentQuery();
mshaDoRequest(_2b0,_2d1,onResponse);
}
function findNext(){
var _2d2="id="+_2b1.getLastId()+createParentQuery();
mshaDoRequest(_2b0,_2d2,onResponse);
}
function findPrevious(){
mshaDoRequest(_2b0,"direction=backward&id="+_2b1.getFirstId(),onResponse);
}
function __findQuery(){
if(theTimeout){
window.clearTimeout(theTimeout);
}
theTimeout=window.setTimeout(_findQuery,500);
}
function findQuery(){
if(_2b3.value==""){
findNextFromFirst();
}else{
_2b1.setSearching(true);
mshaDoRequest(_2b0,"query="+_2b3.value+createParentQuery(),onResponse);
}
}
function createParentQuery(){
var _2d3="";
if(_2af!=null){
var _2d4=_2af.getVocIdForParent();
if(_2d4!=null){
_2d3="&parent="+_2d4;
}
}
if(_2d3==""){
if(_2b9!=null){
_2d3="&parent="+_2b9;
}
}
return _2d3;
}
function findFromEnteredId(){
mshaDoRequest(_2b0,"id="+_2b5.value+createParentQuery(),onResponse);
}
function onResponse(){
aResponse=theMshaHttpRequest;
if(theMshaHttpRequest.readyState==4){
theIsSearching=false;
if(theMshaHttpRequest.status==200){
if(_2ba){
_2b1.showBox(aResponse.responseXML);
setBoxShowed(true);
}
_2b1.setSearching(false);
}else{
alert(aResponse.status+" "+aResponse.statusText);
error(aResponse.responseText);
}
}else{
}
}
function isBoxShowed(){
return _2b2;
}
function setBoxShowed(_2d5){
_2b2=_2d5;
}
};
msh_autocomplete.Autocomplete=function(){
var _2d6;
var _2d7;
var _2d8;
var _2d9;
var _2da=null;
var _2db;
var _2dc;
var _2dd;
var _2de;
var _2df=this;
this.getParent=function(){
return _2dd;
};
this.setVocTitle=function(_2e0){
_2dc=_2e0;
};
this.setVocKey=function(_2e1){
_2db=_2e1;
if(_2da!=null){
_2da.setVocKey(_2e1);
}
};
this.setNameFieldId=function(_2e2){
_2d6=$(_2e2);
};
this.setIdFieldId=function(_2e3){
_2d7=$(_2e3);
};
this.setDivId=function(_2e4){
_2d8=$(_2e4);
};
this.setUrl=function(aUrl){
_2d9=aUrl;
if(_2da!=null){
_2da.setUrl(aUrl);
}
};
this.setParentId=function(_2e6){
_2da.setParentId(_2e6);
};
this.setParent=function(_2e7){
_2dd=_2e7;
_2da.setParent(_2e7);
_2dd.addOnChangeCallback(parentOnChange);
};
function parentOnChange(){
_2da.clearValuesWithNoEvents();
}
this.addOnChangeCallback=function(_2e8){
_2da.addOnChangeCallback(_2e8);
};
this.build=function(){
var view=new msh.widget.AutocompleteTableView(_2d6,_2d8);
_2da=new msh_autocomplete.Actions(_2d6,_2d7,view,_2d9,_2db,_2dc,_2dd);
try{
_2d6.setAttribute("autocomplete","off");
}
catch(e){
}
};
this.requestFocus=function(){
_2d6.focus();
};
this.getVocIdForParent=function(){
var ret=null;
var id=_2d7.value;
if(id==null||id==""){
if(_2dd!=null){
ret=_2dd.getVocIdForParent();
}
}else{
ret=id;
}
return ret;
};
this.getVocId=function(){
return _2d7.value;
};
this.getVocName=function(){
return _2d6.value;
};
this.setVocId=function(aId){
_2da.setVocId(aId);
};
this.setShowIdInName=function(_2ed){
_2da.setShowIdInName(_2ed);
};
};
function showAutocompleteWindow(_2ee,_2ef,_2f0,_2f1){
window.open("autocompleteWindow.do?idField="+_2ee+"&nameField="+_2ef+"&selectedId="+_2f0+"&vocName="+_2f1,"","height=500,width=400,scrollbars=yes");
}
function showAutocomplete(_2f2,_2f3,aUrl){
alert(_2f2);
}
var ecom_tree_autocomplete={Version:"0.0"};
ecom_tree_autocomplete.Actions=function(_2f5,_2f6,_2f7,aUrl,_2f9,_2fa,_2fb){
_2f5.onblur=select;
_2f5.onfocus=onFocus;
eventutil.addEventListener(_2f5,eventutil.EVENT_KEY_DOWN,onKey);
eventutil.addEventListener(_2f5,eventutil.EVENT_CLICK,onMouseClick);
var _2fc=aUrl;
var _2fd=_2f7;
var _2fe=false;
var _2ff=_2f5;
var _300=_2ff.value;
var _301=_2f6;
var _302=null;
var _303;
var _304=false;
var _305=null;
var _306=true;
function onFocus(){
_306=true;
}
this.setVocKey=function(_307){
_2f9=_307;
this.setVocId(_301.value);
};
this.setParentId=function(_308){
_305=_308;
};
this.setUrl=function(aUrl){
_2fc=aUrl;
this.setVocId(_301.value);
};
this.setShowIdInName=function(_30a){
_304=_30a;
};
this.setParent=function(_30b){
_2fb=_30b;
};
this.addOnChangeCallback=function(_30c){
_303=_30c;
};
this.clearValuesWithNoEvents=function(){
_2ff.value="";
_301.value="";
if(_303){
_303();
}
};
function getVocValueEdit(){
if(_302==null){
_302=new msh.widget.VocValueEdit(null,_2f9,_2fa,{valueChanged:onNewValue});
}
return _302;
}
this.setVocId=function(aId){
_2fd.setSearching(true);
VocService.getNameById(_2f9,aId,_305,{callback:function(_30e){
_301.value=aId;
_2ff.value=_30e;
_2fd.setSearching(false);
},errorHandler:function(_30f){
_2ff.value=_30f;
_2fd.setSearching(false);
},warningHandler:function(_310){
_2ff.value=_310;
_2fd.setSearching(false);
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
function onNewValue(aId,_312){
_2ff.value=_312;
_301.value=aId;
_2ff.focus();
findFromEnteredId();
}
function selectEmpty(){
var _313=false;
if(isBoxShowed()){
_313=_301.value!="";
_2ff.value="";
_301.value="";
_300="";
}
setBoxShowed(false);
_2fd.hide();
if(_313&&_303){
_303();
}
}
function select(){
document.title="sel"+_2fd.getPage()+"_"+document.title;
if(_2fd.getPage()>0){
if(isBoxShowed()){
}else{
}
_306=true;
switch(_2fd.getPage()){
case 1:
_2fd.setPage(0);
findPrevious();
break;
case 2:
_2fd.setPage(0);
findNext();
break;
}
return false;
}else{
document.title="select_"+document.title;
_306=false;
var _314=false;
if(isBoxShowed()){
var id=_2fd.getSelectedId();
var name=_2fd.getSelectedName();
if(name==null){
name="";
}
_314=_301.value!=id;
if(_304){
if(id!=null&&id!=""){
_2ff.value="("+id+") "+name;
}else{
_2ff.value=name;
}
}else{
_2ff.value=name;
}
_301.value=id;
_300=_2ff.value;
}
setBoxShowed(false);
_2fd.hide();
if(_314&&_303){
_303();
}
}
}
function findParentId(_317){
if(_317==null||_317=="undefined"){
return null;
}else{
if(_317.getVocId()==null||_317.getVocId()==""||_317.getVocId()=="undefined"){
return findParentId(_317.getParent());
}else{
return _317.getVocId();
}
}
}
function onMouseClick(_318){
_306=true;
findFromEnteredId();
_2ff.select();
}
function onKey(_319){
_306=true;
document.title="key_"+document.title;
var _31a=_319.keyCode;
if(_31a==eventutil.VK_ESCAPE||_31a==eventutil.VK_DEL){
return selectEmpty();
}else{
if(_319.ctrlKey||_319.shiftKey){
return false;
}else{
if(_319.altKey&&_31a==eventutil.VK_INSERT){
getVocValueEdit().insertNewValue(findParentId(_2fb!=null?_2fb:null));
return true;
}else{
if(!isBoxShowed()&&_31a==eventutil.VK_PAGE_DOWN){
findFromEnteredId();
}else{
switch(_31a){
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
if(!_2fd.selectNext()){
findNext();
}
break;
case eventutil.VK_UP:
if(!_2fd.selectPrevious()){
findPrevious();
}
break;
case eventutil.VK_ENTER:
case eventutil.VK_TAB:
select();
break;
case eventutil.VK_HOME:
mshaDoRequest(_2fc,"",onResponse);
break;
}
}
}
}
}
}
function findNextFromFirst(){
document.title="findNextFromFirst_"+document.title;
var _31b="id="+createParentQuery();
mshaDoRequest(_2fc,_31b,onResponse);
}
function findNext(){
document.title="findNext_"+document.title;
var _31c="id="+_2fd.getLastId()+createParentQuery();
mshaDoRequest(_2fc,_31c,onResponse);
}
function findPrevious(){
document.title="findPrevious_"+document.title;
mshaDoRequest(_2fc,"direction=backward&id="+_2fd.getFirstId(),onResponse);
}
function __findQuery(){
if(theTimeout){
window.clearTimeout(theTimeout);
}
theTimeout=window.setTimeout(_findQuery,500);
}
function findQuery(){
if(_2ff.value==""){
findNextFromFirst();
}else{
_2fd.setSearching(true);
mshaDoRequest(_2fc,"query="+_2ff.value+createParentQuery(),onResponse);
}
}
function createParentQuery(){
var _31d="";
if(_2fb!=null){
var _31e=_2fb.getVocIdForParent();
if(_31e!=null){
_31d="&parent="+_31e;
}
}
if(_31d==""){
if(_305!=null){
_31d="&parent="+_305;
}
}
return _31d;
}
function findFromEnteredId(){
document.title="findFromEnteredId_"+document.title;
mshaDoRequest(_2fc,"id="+_301.value+createParentQuery(),onResponse);
}
function onResponse(){
document.title="response_"+document.title;
aResponse=theMshaHttpRequest;
if(theMshaHttpRequest.readyState==4){
theIsSearching=false;
if(theMshaHttpRequest.status==200){
if(_306){
_2fd.showBox(aResponse.responseXML);
setBoxShowed(true);
}
_2fd.setSearching(false);
}else{
alert(aResponse.status+" "+aResponse.statusText);
error(aResponse.responseText);
}
}else{
}
}
function isBoxShowed(){
document.title="isBoxShowed_"+document.title;
return _2fe;
}
function setBoxShowed(_31f){
_2fe=_31f;
}
};
ecom_tree_autocomplete.Autocomplete=function(){
var _320;
var _321;
var _322;
var _323;
var _324=null;
var _325;
var _326;
var _327;
var _328;
var _329=this;
this.getParent=function(){
return _327;
};
this.setVocTitle=function(_32a){
_326=_32a;
};
this.setVocKey=function(_32b){
_325=_32b;
if(_324!=null){
_324.setVocKey(_32b);
}
};
this.setNameFieldId=function(_32c){
_320=$(_32c);
};
this.setIdFieldId=function(_32d){
_321=$(_32d);
};
this.setDivId=function(_32e){
_322=$(_32e);
};
this.setUrl=function(aUrl){
_323=aUrl;
if(_324!=null){
_324.setUrl(aUrl);
}
};
this.setParentId=function(_330){
_324.setParentId(_330);
};
this.setParent=function(_331){
_327=_331;
_324.setParent(_331);
_327.addOnChangeCallback(parentOnChange);
};
function parentOnChange(){
_324.clearValuesWithNoEvents();
}
this.addOnChangeCallback=function(_332){
_324.addOnChangeCallback(_332);
};
this.build=function(){
var view=new msh.widget.TreeAutocompleteTableView(_320,_322);
_324=new ecom_tree_autocomplete.Actions(_320,_321,view,_323,_325,_326,_327);
try{
_320.setAttribute("autocomplete","off");
}
catch(e){
}
};
this.requestFocus=function(){
alert("focus");
_320.focus();
};
this.getVocIdForParent=function(){
var ret=null;
var id=_321.value;
if(id==null||id==""){
if(_327!=null){
ret=_327.getVocIdForParent();
}
}else{
ret=id;
}
return ret;
};
this.getVocId=function(){
return _321.value;
};
this.getVocName=function(){
return _320.value;
};
this.setVocId=function(aId){
_324.setVocId(aId);
};
this.setShowIdInName=function(_337){
_324.setShowIdInName(_337);
};
};
function showTreeAutocomplete(_338,_339,aUrl){
alert(_338);
}
msh.widget.VocValueEdit=function(_33b,_33c,_33d,_33e){
var _33f=null;
var _340=false;
var _341=this;
var _342=null;
function initDialog(){
if(_33f==null){
_33f=new msh.widget.VocValueEditDialog(_33c+"Id",_33d,_341);
_33f.buildDialog();
}
}
this.insertNewValue=function(_343){
_342=_343;
initDialog();
_340=true;
_33f.showInsert();
};
this.changeValue=function(aId,_345){
initDialog();
_340=false;
_33f.showEdit(aId,_345);
};
this.onSave=function(_346,_347){
VocEditService.createVocValue(_33c,_346,_347,_342,{callback:function(aId){
_33f.hide();
_33e.valueChanged(aId,_347);
},errorHandler:function(_349){
alert("\u041e\u0448\u0438\u0431\u043a\u0430: "+_349);
},warningHandler:function(_34a){
alert("\u041f\u0440\u0435\u0434\u0443\u043f\u0440\u0435\u0436\u0434\u0435\u043d\u0438\u0435: "+_34a);
}});
};
};
msh.widget.VocValueEditDialog=function(_34b,_34c,_34d){
var _34e;
var _34f;
var _350=null;
var _351=null;
var _352=null;
var _353=null;
this.hide=function(){
_34e.hide();
};
this.showInsert=function(){
_34e.show();
_353.innerHTML="\u0421\u043e\u0437\u0434\u0430\u043d\u0438\u0435 \u043d\u043e\u0432\u043e\u0433\u043e \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u044f";
_351.focus();
_350.value="";
_351.value="";
_352.value="\u0421\u043e\u0437\u0434\u0430\u0442\u044c \u043d\u043e\u0432\u043e\u0435 \u0437\u043d\u0430\u0447\u0435\u043d\u0438\u0435";
};
this.showEdit=function(aId,_355){
_34e.show();
_353.innerHTML="\u0418\u0437\u043c\u0435\u043d\u0435\u043d\u0438\u0435";
_350.value=aId;
_351.value=_355;
_351.focus();
_352.value="\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u0438\u0437\u043c\u0435\u043d\u0435\u043d\u0438\u044f";
};
function onPressButton(){
_34d.onSave(_350.value,_351.value);
}
function hideDialog(){
_34e.hide();
}
this.buildDialog=function(){
var div=document.createElement("div");
div.id=_34b;
div.className="dialog";
var h2=document.createElement("h2");
h2.appendChild(document.createTextNode(_34c));
var _358=document.createElement("div");
_358.className="rootPane";
_353=document.createElement("h3");
var form=document.createElement("form");
var _35a=document.createElement("table");
var tr=document.createElement("tr");
var td=document.createElement("td");
td.className="label";
td.appendChild(document.createTextNode("\u0418\u0434\u0435\u043d\u0442\u0438\u0444\u0438\u043a\u0430\u0442\u043e\u0440"));
tr.appendChild(td);
td=document.createElement("td");
var _35d=document.createElement("input");
_35d.type="text";
_350=_35d;
td.appendChild(_35d);
tr.appendChild(td);
tr=document.createElement("tr");
td=document.createElement("td");
td.className="label";
td.appendChild(document.createTextNode("\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435:"));
tr.appendChild(td);
td=document.createElement("td");
_35d=document.createElement("input");
_35d.type="text";
_351=_35d;
td.appendChild(_35d);
tr.appendChild(td);
_35a.appendChild(tr);
form.appendChild(_35a);
_352=document.createElement("input");
_352.type="button";
_352.value="\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c";
_352.onclick=onPressButton;
var _35e=document.createElement("input");
_35e.type="button";
_35e.value="\u041e\u0442\u043c\u0435\u043d\u0438\u0442\u044c";
_35e.onclick=hideDialog;
form.appendChild(_352);
form.appendChild(document.createTextNode("   "));
form.appendChild(_35e);
div.appendChild(h2);
div.appendChild(_358);
_358.innerHTML="";
_358.appendChild(_353);
_358.appendChild(form);
document.body.appendChild(div);
_34e=new msh.widget.Dialog(div);
_34f=_358;
h2.onclick=hideDialog;
};
};
var theDefaultFieldName;
var theDefaultEvt;
function adjustMessage(text){
var _360=document.getElementById("divInstantMessage");
var _361=true;
if(_361){
var _362=theDefaultEvt;
}
var _363=getScrollXY();
_360.innerHTML=text;
_360.style.top=(_363[1]+50)+"px";
_360.style.left="100px";
_360.style.visibility="visible";
}
function getScrollXY(){
var _364=0,_365=0;
if(typeof (window.pageYOffset)=="number"){
_365=window.pageYOffset;
_364=window.pageXOffset;
}else{
if(document.body&&(document.body.scrollLeft||document.body.scrollTop)){
_365=document.body.scrollTop;
_364=document.body.scrollLeft;
}else{
if(document.documentElement&&(document.documentElement.scrollLeft||document.documentElement.scrollTop)){
_365=document.documentElement.scrollTop;
_364=document.documentElement.scrollLeft;
}
}
}
return [_364,_365];
}
function getDefinition(term,evt){
var _368=document.getElementById("divInstantMessage");
var _369=term.split("?");
var _36a=_369[1];
mshaDoRequest(_369[0],_36a,onResponse1);
theDefaultEvt=evt;
_368.innerHTML="\u0417\u0430\u0433\u0440\u0443\u0437\u043a\u0430...";
return false;
}
function goToPage(_36b,aId){
var _36d=_36b.indexOf("?")==-1?"?":"&";
var url=_36b+_36d+"id="+aId+"&tmp="+Math.random();
window.location=url;
}
function onResponse1(){
var _36f=theMshaHttpRequest;
if(theMshaHttpRequest.readyState==4){
var _370=document.getElementById("divInstantMessage");
theIsSearching=false;
if(theMshaHttpRequest.status==200){
adjustMessage(_36f.responseText);
}else{
adjustMessage(_36f.status+" "+_36f.statusText+" "+_36f.responseText);
}
}else{
}
}
function hideMessage(){
var _371=document.getElementById("divInstantMessage");
_371.style.visibility="hidden";
_371.innerHTML="";
}
function getCheckedRadio(aFrm,_373){
var _374=aFrm[_373];
if(_374){
if(_374.length){
for(i=0;i<_374.length;i++){
if(_374[i].checked==true){
return _374[i].value;
}
}
}else{
if(_374.checked==true){
return _374.value;
}
}
}
return "";
}
function getCheckedCheckBox(aFrm,_376,_377){
var _378=aFrm.elements[_376];
var _379="";
if(_378){
if(_378.length){
for(i=0;i<_378.length;i++){
if(_378[i].checked==true){
_379=_379+_377+_378[i].value;
}
}
}else{
if(_378.checked==true){
_379=_379+_377+_378.value;
}
}
}
if(_379!=""){
_379=_379.substring(_377.length);
}
return _379;
}
function setCheckedAllRadio(aFrm,_37b,_37c){
var _37d=aFrm[_37b];
if(_37d){
if(_37d.length){
_37d[0].checked=_37c;
}else{
_37d.checked=_37c;
}
}
}
function setCheckedAllCheckBox(aFrm,_37f,_380){
var _381=aFrm.elements[_37f];
if(_381){
if(_381.length){
for(i=0;i<_381.length;i++){
_381[i].checked=_380;
}
}else{
_381.checked=_380;
}
}
}
function setFocusOnField(_382){
theDefaultFieldName=_382;
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
msh.widget.OneToManyAuto=function(_385,_386,_387,_388,_389,_38a,_38b,_38c,_38d,_38e,_38f,_390){
var _391=null;
var _392=null;
var _393=false;
var _394=null;
var _395=null;
var _396=null;
this.getSerial=function(){
return _385;
};
function ce(_397){
return document.createElement(_397);
}
this.add=function(){
var _398=ce("td");
_398.style.className="label";
var _399=ce("label");
_392=_399;
var _39a=ce("td");
var _39b=ce("input");
if(_38d){
if(_390!=null&&_390!=""&&_390!="null"){
_39b=ce("a");
_39b.href=_390+"?id="+(_386.value?_386.value:"");
_39b.innerHTML=_386.name?_386.name:"";
}else{
_39b=ce("input");
_39b.value=_386.name?_386.name:"";
_39b.className="viewOnly maxHorizontalSize";
_39b.disabled=true;
_39b.style.background="none";
_39b.style.border="none";
_39b.style.color="black";
_39b.style.className="";
}
}else{
_39b=ce("input");
_39b.className="autocomplete maxHorizontalSize";
}
_39a.appendChild(_39b);
_394=_39b;
_394.id="otma_input_"+_385;
var div=ce("div");
_39a.appendChild(div);
var _39d=ce("hidden");
_39d.id="otma_hidden_"+_385;
_395=_39d;
var _39e=ce("td");
var a=ce("a");
a.innerHTML="\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c";
a.href="javascript:void(0)";
_39e.appendChild(a);
_391=a;
_387.appendChild(_398);
_387.appendChild(_39a);
if(!_38d){
_387.appendChild(_39e);
}
_391.onclick=onClick;
_395.value="";
_395.value=_386.value?_386.value:"";
if(_38d){
}else{
_394.value=_386.name?_386.name:"";
}
addAutocomlete(div);
if(_38d){
}
eventutil.addEventListener(_394,eventutil.EVENT_BLUR,_38b);
};
function addAutocomlete(aDiv){
if(!_38d){
_396=new msh_autocomplete.Autocomplete();
_396.setUrl("simpleVocAutocomplete/"+_38c);
_396.setIdFieldId(_395);
_396.setNameFieldId(_394);
_396.setDivId(aDiv);
_396.setVocKey(_38c);
_396.setVocTitle(_388);
_396.build();
if(_38e!=null&&_38e!=""&&_38e!="null"){
_396.setParentId(_38e);
}
if(_38f!=null&&_38f!=""&&_38f!="null"){
_396.setParent(_38f+"Autocomplete");
}
}
}
function onClick(){
if(_393){
_387.parentNode.removeChild(_387);
_389(_385);
}else{
_38a();
}
}
this.setIsRemoveAction=function(_3a1){
_393=_3a1;
_391.innerHTML=_3a1?"\u0423\u0431\u0440\u0430\u0442\u044c":"\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0435\u0449\u0435";
_391.title=_3a1?"\u0423\u0431\u0440\u0430\u0442\u044c "+_388:"\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0435\u0449\u0435 \u043e\u0434\u0438\u043d "+_388;
_391.className="manyToManyActionLink";
};
this.setTitleVisibled=function(_3a2){
_392.innerHTML=_3a2?_388:"";
};
this.focus=function(){
_394.focus();
_394.select();
};
this.clearData=function(){
_394.value="";
_395.value="";
};
this.setValue=function(aId,_3a4){
_394.value=_3a4;
_395.value=aId;
};
this.setParentId=function(_3a5){
_38e=_3a5;
_396.setParentId(_38e);
};
this.getJson=function(){
_386["value"]=_395.value;
return _386;
};
};
msh.widget.OneToManyAutocompletes=function(_3a6,_3a7,_3a8,_3a9,_3aa,_3ab,_3ac,_3ad,_3ae){
var _3af=null;
var _3b0=null;
var _3b1=new Array();
var _3b2=1;
this.install=function(){
buildSurroundView();
var _3b3=$(_3a8).value;
if(_3b3!=null&&_3b3!="null"&&_3b3!=""){
var json=eval("("+$(_3a8).value+")");
var _3b5=json.childs;
for(var i=0;i<_3b5.length;i++){
var tr=ce("tr");
_3af.appendChild(tr);
var one=new msh.widget.OneToManyAuto(getNextSerial(),_3b5[i],tr,_3a9,onRemove,onAdd,recalc,_3aa,_3ab,_3ac,_3ad,_3ae);
one.add();
_3b1.push(one);
}
if(_3b5.length==0){
onAdd();
}
}else{
onAdd();
}
onUpdate();
};
this.setIds=function(_3b9){
if(_3b9!=null&&_3b9!="null"&&_3b9!=""){
var json=eval("("+_3b9+")");
var _3bb=json.childs;
var len=_3b1.length;
for(var i=0;i<_3bb.length;i++){
var _3be=_3bb[i];
if(i<len){
_3b1[i].setValue(_3be.value?_3be.value:"",_3be.name?_3be.name:"");
}else{
var tr=ce("tr");
_3af.appendChild(tr);
var one=new msh.widget.OneToManyAuto(getNextSerial(),_3be,tr,_3a9,onRemove,onAdd,recalc,_3aa,_3ab,_3ac,_3ad);
one.add();
_3b1.push(one);
}
}
}
onUpdate();
};
this.setParentId=function(_3c1){
_3ac=_3c1;
for(var i=0;i<_3b1.length;i++){
_3b1[i].setParentId(_3ac);
}
};
this.clearData=function(){
for(var i=0;i<_3b1.length;i++){
_3b1[i].clearData();
}
};
function getNextSerial(){
return ++_3b2;
}
function onRemove(_3c4){
var ar=new Array();
var _3c6=0;
for(var i=0;i<_3b1.length;i++){
if(_3c4!=_3b1[i].getSerial()){
ar.push(_3b1[i]);
}else{
_3b1[i<_3b1.length-1?i+1:0].focus();
}
}
_3b1=ar;
onUpdate();
}
function onAdd(){
var tr=ce("tr");
_3af.appendChild(tr);
var _3c9=new Object();
_3c9["id"]="";
_3c9["value"]="";
var one=new msh.widget.OneToManyAuto(getNextSerial(),_3c9,tr,_3a9,onRemove,onAdd,recalc,_3aa,_3ab,_3ac,_3ad);
one.add();
one.focus();
_3b1.push(one);
onUpdate();
}
function onUpdate(){
setTitleVisibled(_3b1.length!=1);
for(var i=0;i<_3b1.length-1;i++){
_3b1[i].setIsRemoveAction(true);
}
_3b1[_3b1.length-1].setIsRemoveAction(false);
recalc();
}
function recalc(){
var json=new Object();
var _3cd=new Array();
for(var i=0;i<_3b1.length;i++){
_3cd.push(_3b1[i].getJson());
}
json["childs"]=_3cd;
$(_3a8).value=JSON.stringify(json);
}
function setTitleVisibled(_3cf){
if(_3cf){
_3b0.parentNode.style.border="1px solid #999";
}else{
_3b0.parentNode.style.border="none";
}
for(var i=0;i<_3b1.length;i++){
_3b1[i].setTitleVisibled(!_3cf);
}
_3b0.innerHTML=_3cf?_3a9:"";
}
function ce(_3d1){
return document.createElement(_3d1);
}
function buildSurroundView(){
var _3d2=ce("fieldset");
var _3d3=ce("legend");
var _3d4=ce("table");
_3d4.border="0";
var _3d5=ce("tbody");
_3d4.appendChild(_3d5);
_3d2.appendChild(_3d3);
_3d2.appendChild(_3d4);
_3a6.innerHTML="";
_3a6.appendChild(_3d2);
_3d3.innerHTML=_3a9;
_3af=_3d5;
_3b0=_3d3;
}
};

