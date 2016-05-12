/* Copyright (c) 2006 Yahoo! Inc. All rights reserved. */
YAHOO.util.Anim=function(el,attributes,duration,method)
{if(el){this.init(el,attributes,duration,method);}};YAHOO.util.Anim.prototype={doMethod:function(attribute,start,end){return this.method(this.currentFrame,start,end-start,this.totalFrames);},setAttribute:function(attribute,val,unit){YAHOO.util.Dom.setStyle(this.getEl(),attribute,val+unit);},getAttribute:function(attribute){return parseFloat(YAHOO.util.Dom.getStyle(this.getEl(),attribute));},defaultUnits:{opacity:' '},defaultUnit:'px',init:function(el,attributes,duration,method){var isAnimated=false;var startTime=null;var endTime=null;var actualFrames=0;var defaultValues={};el=YAHOO.util.Dom.get(el);this.attributes=attributes||{};this.duration=duration||1;this.method=method||YAHOO.util.Easing.easeNone;this.useSeconds=true;this.currentFrame=0;this.totalFrames=YAHOO.util.AnimMgr.fps;this.getEl=function(){return el;};this.setDefault=function(attribute,val){if(val=='auto'){switch(attribute){case'width':val=el.clientWidth||el.offsetWidth;break;case'height':val=el.clientHeight||el.offsetHeight;break;case'left':if(YAHOO.util.Dom.getStyle(el,'position')=='absolute'){val=el.offsetLeft;}else{val=0;}
break;case'top':if(YAHOO.util.Dom.getStyle(el,'position')=='absolute'){val=el.offsetTop;}else{val=0;}
break;default:val=0;}}
defaultValues[attribute]=val;}
this.getDefault=function(attribute){return defaultValues[attribute];};this.isAnimated=function(){return isAnimated;};this.getStartTime=function(){return startTime;};this.animate=function(){this.onStart.fire();this._onStart.fire();this.totalFrames=(this.useSeconds)?Math.ceil(YAHOO.util.AnimMgr.fps*this.duration):this.duration;YAHOO.util.AnimMgr.registerElement(this);var attributes=this.attributes;var el=this.getEl();var val;for(var attribute in attributes){val=this.getAttribute(attribute);this.setDefault(attribute,val);}
isAnimated=true;actualFrames=0;startTime=new Date();};this.stop=function(){this.currentFrame=0;endTime=new Date();var data={time:endTime,duration:endTime-startTime,frames:actualFrames,fps:actualFrames/this.duration};isAnimated=false;actualFrames=0;this.onComplete.fire(data);};var onTween=function(){var start;var end=null;var val;var unit;var attributes=this['attributes'];for(var attribute in attributes){unit=attributes[attribute]['unit']||this.defaultUnits[attribute]||this.defaultUnit;if(typeof attributes[attribute]['from']!='undefined'){start=attributes[attribute]['from'];}else{start=this.getDefault(attribute);}
if(typeof attributes[attribute]['to']!='undefined'){end=attributes[attribute]['to'];}else if(typeof attributes[attribute]['by']!='undefined'){end=start+attributes[attribute]['by'];}
if(end!==null&&typeof end!='undefined'){val=this.doMethod(attribute,start,end);if((attribute=='width'||attribute=='height'||attribute=='opacity')&&val<0){val=0;}
this.setAttribute(attribute,val,unit);}}
actualFrames+=1;};this._onStart=new YAHOO.util.CustomEvent('_onStart',this);this.onStart=new YAHOO.util.CustomEvent('start',this);this.onTween=new YAHOO.util.CustomEvent('tween',this);this._onTween=new YAHOO.util.CustomEvent('_tween',this);this.onComplete=new YAHOO.util.CustomEvent('complete',this);this._onTween.subscribe(onTween);}};YAHOO.util.AnimMgr=new function(){var thread=null;var queue=[];var tweenCount=0;this.fps=200;this.delay=1;this.registerElement=function(tween){if(tween.isAnimated()){return false;}
queue[queue.length]=tween;tweenCount+=1;this.start();};this.start=function(){if(thread===null){thread=setInterval(this.run,this.delay);}};this.stop=function(tween){if(!tween)
{clearInterval(thread);for(var i=0,len=queue.length;i<len;++i){if(queue[i].isAnimated()){queue[i].stop();}}
queue=[];thread=null;tweenCount=0;}
else{tween.stop();tweenCount-=1;if(tweenCount<=0){this.stop();}}};this.run=function(){for(var i=0,len=queue.length;i<len;++i){var tween=queue[i];if(!tween||!tween.isAnimated()){continue;}
if(tween.currentFrame<tween.totalFrames||tween.totalFrames===null)
{tween.currentFrame+=1;if(tween.useSeconds){correctFrame(tween);}
tween.onTween.fire();tween._onTween.fire();}
else{YAHOO.util.AnimMgr.stop(tween);}}};var correctFrame=function(tween){var frames=tween.totalFrames;var frame=tween.currentFrame;var expected=(tween.currentFrame*tween.duration*1000/tween.totalFrames);var elapsed=(new Date()-tween.getStartTime());var tweak=0;if(elapsed<tween.duration*1000){tweak=Math.round((elapsed/expected-1)*tween.currentFrame);}else{tweak=frames-(frame+1);}
if(tweak>0&&isFinite(tweak)){if(tween.currentFrame+tweak>=frames){tweak=frames-(frame+1);}
tween.currentFrame+=tweak;}};}
YAHOO.util.Bezier=new function()
{this.getPosition=function(points,t)
{var n=points.length;var tmp=[];for(var i=0;i<n;++i){tmp[i]=[points[i][0],points[i][1]];}
for(var j=1;j<n;++j){for(i=0;i<n-j;++i){tmp[i][0]=(1-t)*tmp[i][0]+t*tmp[parseInt(i+1,10)][0];tmp[i][1]=(1-t)*tmp[i][1]+t*tmp[parseInt(i+1,10)][1];}}
return[tmp[0][0],tmp[0][1]];};};YAHOO.util.Easing=new function(){this.easeNone=function(t,b,c,d){return b+c*(t/=d);};this.easeIn=function(t,b,c,d){return b+c*((t/=d)*t*t);};this.easeOut=function(t,b,c,d){var ts=(t/=d)*t;var tc=ts*t;return b+c*(tc+-3*ts+3*t);};this.easeBoth=function(t,b,c,d){var ts=(t/=d)*t;var tc=ts*t;return b+c*(-2*tc+3*ts);};this.backIn=function(t,b,c,d){var ts=(t/=d)*t;var tc=ts*t;return b+c*(-3.4005*tc*ts+10.2*ts*ts+-6.2*tc+0.4*ts);};this.backOut=function(t,b,c,d){var ts=(t/=d)*t;var tc=ts*t;return b+c*(8.292*tc*ts+-21.88*ts*ts+22.08*tc+-12.69*ts+5.1975*t);};this.backBoth=function(t,b,c,d){var ts=(t/=d)*t;var tc=ts*t;return b+c*(0.402*tc*ts+-2.1525*ts*ts+-3.2*tc+8*ts+-2.05*t);};};YAHOO.util.Motion=function(el,attributes,duration,method){if(el){this.initMotion(el,attributes,duration,method);}};YAHOO.util.Motion.prototype=new YAHOO.util.Anim();YAHOO.util.Motion.prototype.defaultUnits.points='px';YAHOO.util.Motion.prototype.doMethod=function(attribute,start,end){var val=null;if(attribute=='points'){var translatedPoints=this.getTranslatedPoints();var t=this.method(this.currentFrame,0,100,this.totalFrames)/100;if(translatedPoints){val=YAHOO.util.Bezier.getPosition(translatedPoints,t);}}else{val=this.method(this.currentFrame,start,end-start,this.totalFrames);}
return val;};YAHOO.util.Motion.prototype.getAttribute=function(attribute){var val=null;if(attribute=='points'){val=[this.getAttribute('left'),this.getAttribute('top')];if(isNaN(val[0])){val[0]=0;}
if(isNaN(val[1])){val[1]=0;}}else{val=parseFloat(YAHOO.util.Dom.getStyle(this.getEl(),attribute));}
return val;};YAHOO.util.Motion.prototype.setAttribute=function(attribute,val,unit){if(attribute=='points'){YAHOO.util.Dom.setStyle(this.getEl(),'left',val[0]+unit);YAHOO.util.Dom.setStyle(this.getEl(),'top',val[1]+unit);}else{YAHOO.util.Dom.setStyle(this.getEl(),attribute,val+unit);}};YAHOO.util.Motion.prototype.initMotion=function(el,attributes,duration,method){YAHOO.util.Anim.call(this,el,attributes,duration,method);attributes=attributes||{};attributes.points=attributes.points||{};attributes.points.control=attributes.points.control||[];this.attributes=attributes;var start;var end=null;var translatedPoints=null;this.getTranslatedPoints=function(){return translatedPoints;};var translateValues=function(val,self){var pageXY=YAHOO.util.Dom.getXY(self.getEl());val=[val[0]-pageXY[0]+start[0],val[1]-pageXY[1]+start[1]];return val;};var onStart=function(){start=this.getAttribute('points');var attributes=this.attributes;var control=attributes['points']['control']||[];if(control.length>0&&control[0].constructor!=Array){control=[control];}
if(YAHOO.util.Dom.getStyle(this.getEl(),'position')=='static'){YAHOO.util.Dom.setStyle(this.getEl(),'position','relative');}
if(typeof attributes['points']['from']!='undefined'){YAHOO.util.Dom.setXY(this.getEl(),attributes['points']['from']);start=this.getAttribute('points');}
else if((start[0]===0||start[1]===0)){YAHOO.util.Dom.setXY(this.getEl(),YAHOO.util.Dom.getXY(this.getEl()));start=this.getAttribute('points');}
var i,len;if(typeof attributes['points']['to']!='undefined'){end=translateValues(attributes['points']['to'],this);for(i=0,len=control.length;i<len;++i){control[i]=translateValues(control[i],this);}}else if(typeof attributes['points']['by']!='undefined'){end=[start[0]+attributes['points']['by'][0],start[1]+attributes['points']['by'][1]];for(i=0,len=control.length;i<len;++i){control[i]=[start[0]+control[i][0],start[1]+control[i][1]];}}
if(end){translatedPoints=[start];if(control.length>0){translatedPoints=translatedPoints.concat(control);}
translatedPoints[translatedPoints.length]=end;}};this._onStart.subscribe(onStart);};YAHOO.util.Scroll=function(el,attributes,duration,method){if(el){YAHOO.util.Anim.call(this,el,attributes,duration,method);}};YAHOO.util.Scroll.prototype=new YAHOO.util.Anim();YAHOO.util.Scroll.prototype.defaultUnits.scroll=' ';YAHOO.util.Scroll.prototype.doMethod=function(attribute,start,end){var val=null;if(attribute=='scroll'){val=[this.method(this.currentFrame,start[0],end[0]-start[0],this.totalFrames),this.method(this.currentFrame,start[1],end[1]-start[1],this.totalFrames)];}else{val=this.method(this.currentFrame,start,end-start,this.totalFrames);}
return val;}
YAHOO.util.Scroll.prototype.getAttribute=function(attribute){var val=null;var el=this.getEl();if(attribute=='scroll'){val=[el.scrollLeft,el.scrollTop];}else{val=parseFloat(YAHOO.util.Dom.getStyle(el,attribute));}
return val;};YAHOO.util.Scroll.prototype.setAttribute=function(attribute,val,unit){var el=this.getEl();if(attribute=='scroll'){el.scrollLeft=val[0];el.scrollTop=val[1];}else{YAHOO.util.Dom.setStyle(el,attribute,val+unit);}};/* Copyright (c) 2006 Yahoo! Inc. All rights reserved. *//* Copyright (c) 2006 Yahoo! Inc. All rights reserved. */
YAHOO.util.Dom=new function(){this.get=function(el){if(typeof el=='string'){el=document.getElementById(el);}
return el;};this.getStyle=function(el,property){var value=null;var dv=document.defaultView;el=this.get(el);if(property=='opacity'&&el.filters){value=1;try{value=el.filters.item('DXImageTransform.Microsoft.Alpha').opacity/100;}catch(e){try{value=el.filters.item('alpha').opacity/100;}catch(e){}}}
else if(el.style[property]){value=el.style[property];}
else if(el.currentStyle&&el.currentStyle[property]){value=el.currentStyle[property];}
else if(dv&&dv.getComputedStyle)
{var converted='';for(i=0,len=property.length;i<len;++i){if(property.charAt(i)==property.charAt(i).toUpperCase()){converted=converted+'-'+property.charAt(i).toLowerCase();}else{converted=converted+property.charAt(i);}}
if(dv.getComputedStyle(el,'').getPropertyValue(converted)){value=dv.getComputedStyle(el,'').getPropertyValue(converted);}}
return value;};this.setStyle=function(el,property,val){el=this.get(el);switch(property){case'opacity':if(el.filters){el.style.filter='alpha(opacity='+val*100+')';if(!el.currentStyle.hasLayout){el.style.zoom=1;}}else{el.style.opacity=val;el.style['-moz-opacity']=val;el.style['-khtml-opacity']=val;}
break;default:el.style[property]=val;}};this.getXY=function(el){el=this.get(el);if(el.parentNode===null||this.getStyle(el,'display')=='none'){return false;}
var parent=null;var pos=[];var box;if(el.getBoundingClientRect){box=el.getBoundingClientRect();var scrollTop=document.documentElement.scrollTop||document.body.scrollTop;var scrollLeft=document.documentElement.scrollLeft||document.body.scrollLeft;return[box.left+scrollLeft,box.top+scrollTop];}
else if(document.getBoxObjectFor){box=document.getBoxObjectFor(el);pos=[box.x,box.y];}
else{pos=[el.offsetLeft,el.offsetTop];parent=el.offsetParent;if(parent!=el){while(parent){pos[0]+=parent.offsetLeft;pos[1]+=parent.offsetTop;parent=parent.offsetParent;}}
var ua=navigator.userAgent.toLowerCase();if(ua.indexOf('opera')!=-1||(ua.indexOf('safari')!=-1&&this.getStyle(el,'position')=='absolute')){pos[1]-=document.body.offsetTop;}}
if(el.parentNode){parent=el.parentNode;}
else{parent=null;}
while(parent&&parent.tagName!='BODY'&&parent.tagName!='HTML'){pos[0]-=parent.scrollLeft;pos[1]-=parent.scrollTop;if(parent.parentNode){parent=parent.parentNode;}
else{parent=null;}}
return pos;};this.getX=function(el){return this.getXY(el)[0];};this.getY=function(el){return this.getXY(el)[1];};this.setXY=function(el,pos,noRetry){el=this.get(el);var pageXY=YAHOO.util.Dom.getXY(el);if(pageXY===false){return false;}
var delta=[parseInt(YAHOO.util.Dom.getStyle(el,'left'),10),parseInt(YAHOO.util.Dom.getStyle(el,'top'),10)];if(isNaN(delta[0])){delta[0]=0;}
if(isNaN(delta[1])){delta[1]=0;}
if(pos[0]!==null){el.style.left=pos[0]-pageXY[0]+delta[0]+'px';}
if(pos[1]!==null){el.style.top=pos[1]-pageXY[1]+delta[1]+'px';}
var newXY=this.getXY(el);if(!noRetry&&(newXY[0]!=pos[0]||newXY[1]!=pos[1])){this.setXY(el,pos,true);}
return true;};this.setX=function(el,x){return this.setXY(el,[x,null]);};this.setY=function(el,y){return this.setXY(el,[null,y]);};this.getRegion=function(el){el=this.get(el);return new YAHOO.util.Region.getRegion(el);};this.getClientWidth=function(){return(document.documentElement.offsetWidth||document.body.offsetWidth);};this.getClientHeight=function(){return(self.innerHeight||document.documentElement.clientHeight||document.body.clientHeight);};};YAHOO.util.Region=function(t,r,b,l){this.top=t;this.right=r;this.bottom=b;this.left=l;};YAHOO.util.Region.prototype.contains=function(region){return(region.left>=this.left&&region.right<=this.right&&region.top>=this.top&&region.bottom<=this.bottom);};YAHOO.util.Region.prototype.getArea=function(){return((this.bottom-this.top)*(this.right-this.left));};YAHOO.util.Region.prototype.intersect=function(region){var t=Math.max(this.top,region.top);var r=Math.min(this.right,region.right);var b=Math.min(this.bottom,region.bottom);var l=Math.max(this.left,region.left);if(b>=t&&r>=l){return new YAHOO.util.Region(t,r,b,l);}else{return null;}};YAHOO.util.Region.prototype.union=function(region){var t=Math.min(this.top,region.top);var r=Math.max(this.right,region.right);var b=Math.max(this.bottom,region.bottom);var l=Math.min(this.left,region.left);return new YAHOO.util.Region(t,r,b,l);};YAHOO.util.Region.prototype.toString=function(){return("Region {"+"  t: "+this.top+", r: "+this.right+", b: "+this.bottom+", l: "+this.left+"}");}
YAHOO.util.Region.getRegion=function(el){var p=YAHOO.util.Dom.getXY(el);var t=p[1];var r=p[0]+el.offsetWidth;var b=p[1]+el.offsetHeight;var l=p[0];return new YAHOO.util.Region(t,r,b,l);};YAHOO.util.Point=function(x,y){this.x=x;this.y=y;this.top=y;this.right=x;this.bottom=y;this.left=x;};YAHOO.util.Point.prototype=new YAHOO.util.Region();/* Copyright (c) 2006 Yahoo! Inc. All rights reserved. */
YAHOO.util.CustomEvent=function(type,oScope){this.type=type;this.scope=oScope||window;this.subscribers=[];if(YAHOO.util["Event"]){YAHOO.util.Event.regCE(this);}};YAHOO.util.CustomEvent.prototype={subscribe:function(fn,obj,bOverride){this.subscribers.push(new YAHOO.util.Subscriber(fn,obj,bOverride));},unsubscribe:function(fn,obj){var found=false;for(var i=0;i<this.subscribers.length;++i){var s=this.subscribers[i];if(s&&s.contains(fn,obj)){this._delete(i);found=true;}}
return found;},fire:function(){for(var i=0;i<this.subscribers.length;++i){var s=this.subscribers[i];if(s){var scope=(s.override)?s.obj:this.scope;s.fn.call(scope,this.type,arguments,s.obj);}}},unsubscribeAll:function(){for(var i=0;i<this.subscribers.length;++i){this._delete(i);}},_delete:function(index){var s=this.subscribers[index];if(s){delete s.fn;delete s.obj;}
delete this.subscribers[index];}};YAHOO.util.Subscriber=function(fn,obj,bOverride){this.fn=fn;this.obj=obj||null;this.override=(bOverride);};YAHOO.util.Subscriber.prototype.contains=function(fn,obj){return(this.fn==fn&&this.obj==obj);};if(!YAHOO.util.Event){YAHOO.util.Event=function(){var loadComplete=false;var listeners=[];var delayedListeners=[];var unloadListeners=[];var customEvents=[];var legacyEvents=[];var legacyHandlers=[];return{EL:0,TYPE:1,FN:2,WFN:3,SCOPE:3,ADJ_SCOPE:4,isSafari:(navigator.userAgent.match(/safari/gi)),isIE:(!this.isSafari&&navigator.userAgent.match(/msie/gi)),addListener:function(el,sType,fn,oScope,bOverride){if(this._isValidCollection(el)){var ok=true;for(var i=0;i<el.length;++i){ok=(this.on(el[i],sType,fn,oScope,bOverride)&&ok);}
return ok;}else if(typeof el=="string"){if(loadComplete){el=this.getEl(el);}else{delayedListeners[delayedListeners.length]=[el,sType,fn,oScope,bOverride];return true;}}
if(!el){return false;}
if("unload"==sType&&oScope!==this){unloadListeners[unloadListeners.length]=[el,sType,fn,oScope,bOverride];return true;}
var scope=(bOverride)?oScope:el;var wrappedFn=function(e){return fn.call(scope,YAHOO.util.Event.getEvent(e),oScope);};var li=[el,sType,fn,wrappedFn,scope];var index=listeners.length;listeners[index]=li;if(this.useLegacyEvent(el,sType)){var legacyIndex=this.getLegacyIndex(el,sType);if(legacyIndex==-1){legacyIndex=legacyEvents.length;legacyEvents[legacyIndex]=[el,sType,el["on"+sType]];legacyHandlers[legacyIndex]=[];el["on"+sType]=function(e){YAHOO.util.Event.fireLegacyEvent(YAHOO.util.Event.getEvent(e),legacyIndex);};}
legacyHandlers[legacyIndex].push(index);}else if(el.addEventListener){el.addEventListener(sType,wrappedFn,false);}else if(el.attachEvent){el.attachEvent("on"+sType,wrappedFn);}
return true;},fireLegacyEvent:function(e,legacyIndex){var ok=true;var le=legacyHandlers[legacyIndex];for(i=0;i<le.length;++i){var index=le[i];if(index){var li=listeners[index];var scope=li[this.ADJ_SCOPE];var ret=li[this.WFN].call(scope,e);ok=(ok&&ret);}}
return ok;},getLegacyIndex:function(el,sType){for(var i=0;i<legacyEvents.length;++i){var le=legacyEvents[i];if(le&&le[0]==el&&le[1]==sType){return i;}}
return-1;},useLegacyEvent:function(el,sType){return((!el.addEventListener&&!el.attachEvent)||(sType=="click"&&this.isSafari));},removeListener:function(el,sType,fn){if(typeof el=="string"){el=this.getEl(el);}else if(this._isValidCollection(el)){var ok=true;for(var i=0;i<el.length;++i){ok=(this.removeListener(el[i],sType,fn)&&ok);}
return ok;}
var cacheItem=null;var index=this._getCacheIndex(el,sType,fn);if(index>=0){cacheItem=listeners[index];}
if(!el||!cacheItem){return false;}
if(el.removeEventListener){el.removeEventListener(sType,cacheItem[this.WFN],false);}else if(el.detachEvent){el.detachEvent("on"+sType,cacheItem[this.WFN]);}
delete listeners[index][this.WFN];delete listeners[index][this.FN];delete listeners[index];return true;},getTarget:function(ev,resolveTextNode){var t=ev.target||ev.srcElement;if(resolveTextNode&&t&&"#text"==t.nodeName){return t.parentNode;}else{return t;}},getPageX:function(ev){var x=ev.pageX;if(!x&&0!==x){x=ev.clientX||0;if(this.isIE){x+=this._getScrollLeft();}}
return x;},getPageY:function(ev){var y=ev.pageY;if(!y&&0!==y){y=ev.clientY||0;if(this.isIE){y+=this._getScrollTop();}}
return y;},getRelatedTarget:function(ev){var t=ev.relatedTarget;if(!t){if(ev.type=="mouseout"){t=ev.toElement;}else if(ev.type=="mouseover"){t=ev.fromElement;}}
return t;},getTime:function(ev){if(!ev.time){var t=new Date().getTime();try{ev.time=t;}catch(e){return t;}}
return ev.time;},stopEvent:function(ev){this.stopPropagation(ev);this.preventDefault(ev);},stopPropagation:function(ev){if(ev.stopPropagation){ev.stopPropagation();}else{ev.cancelBubble=true;}},preventDefault:function(ev){if(ev.preventDefault){ev.preventDefault();}else{ev.returnValue=false;}},getEvent:function(e){var ev=e||window.event;if(!ev){var c=this.getEvent.caller;while(c){ev=c.arguments[0];if(ev&&Event==ev.constructor){break;}
c=c.caller;}}
return ev;},getCharCode:function(ev){return ev.charCode||(ev.type=="keypress")?ev.keyCode:0;},_getCacheIndex:function(el,sType,fn){for(var i=0;i<listeners.length;++i){var li=listeners[i];if(li&&li[this.FN]==fn&&li[this.EL]==el&&li[this.TYPE]==sType){return i;}}
return-1;},_isValidCollection:function(o){return(o&&o.length&&typeof o!="string"&&!o.tagName&&!o.alert&&typeof o[0]!="undefined");},elCache:{},getEl:function(id){return document.getElementById(id);},clearCache:function(){for(i in this.elCache){delete this.elCache[i];}},regCE:function(ce){customEvents.push(ce);},_load:function(e){loadComplete=true;},_tryPreloadAttach:function(){var tryAgain=!loadComplete;for(var i=0;i<delayedListeners.length;++i){var d=delayedListeners[i];if(d){var el=this.getEl(d[this.EL]);if(el){this.on(el,d[this.TYPE],d[this.FN],d[this.SCOPE],d[this.ADJ_SCOPE]);delete delayedListeners[i];}}}
if(tryAgain){setTimeout("YAHOO.util.Event._tryPreloadAttach()",50);}},_unload:function(e,me){for(var i=0;i<unloadListeners.length;++i){var l=unloadListeners[i];if(l){var scope=(l[this.ADJ_SCOPE])?l[this.SCOPE]:window;l[this.FN].call(scope,this.getEvent(e),l[this.SCOPE]);}}
if(listeners&&listeners.length>0){for(i=0;i<listeners.length;++i){l=listeners[i];if(l){this.removeListener(l[this.EL],l[this.TYPE],l[this.FN]);}}
this.clearCache();}
for(i=0;i<customEvents.length;++i){customEvents[i].unsubscribeAll();delete customEvents[i];}
for(i=0;i<legacyEvents.length;++i){delete legacyEvents[i][0];delete legacyEvents[i];}},_getScrollLeft:function(){return this._getScroll()[1];},_getScrollTop:function(){return this._getScroll()[0];},_getScroll:function(){var dd=document.documentElement;db=document.body;if(dd&&dd.scrollTop){return[dd.scrollTop,dd.scrollLeft];}else if(db){return[db.scrollTop,db.scrollLeft];}else{return[0,0];}}};}();YAHOO.util.Event.on=YAHOO.util.Event.addListener;if(document&&document.body){YAHOO.util.Event._load();}else{YAHOO.util.Event.on(window,"load",YAHOO.util.Event._load,YAHOO.util.Event,true);}
YAHOO.util.Event.on(window,"unload",YAHOO.util.Event._unload,YAHOO.util.Event,true);YAHOO.util.Event._tryPreloadAttach();}/* Copyright (c) 2006 Yahoo! Inc. All rights reserved. */

YAHOO.util.Key = new function() {
	// this.logger	= new ygLogger("ygEventUtil");
	// DOM key constants 
	this.DOM_VK_UNDEFINED               = 0x0;
	this.DOM_VK_RIGHT_ALT               = 0x12;
	this.DOM_VK_LEFT_ALT                = 0x12;
	this.DOM_VK_LEFT_CONTROL            = 0x11;
	this.DOM_VK_RIGHT_CONTROL           = 0x11;
	this.DOM_VK_LEFT_SHIFT              = 0x10;
	this.DOM_VK_RIGHT_SHIFT             = 0x10;
	this.DOM_VK_META                    = 0x9D;
	this.DOM_VK_BACK_SPACE              = 0x08;
	this.DOM_VK_CAPS_LOCK               = 0x14;
	this.DOM_VK_DELETE                  = 0x7F;
	this.DOM_VK_END                     = 0x23;
	this.DOM_VK_ENTER                   = 0x0D;
	this.DOM_VK_ESCAPE                  = 0x1B;
	this.DOM_VK_HOME                    = 0x24;
	this.DOM_VK_NUM_LOCK                = 0x90;
	this.DOM_VK_PAUSE                   = 0x13;
	this.DOM_VK_PRINTSCREEN             = 0x9A;
	this.DOM_VK_SCROLL_LOCK             = 0x91;
	this.DOM_VK_SPACE                   = 0x20;
	this.DOM_VK_TAB                     = 0x09;
	this.DOM_VK_LEFT                    = 0x25;
	this.DOM_VK_RIGHT                   = 0x27;
	this.DOM_VK_UP                      = 0x26;
	this.DOM_VK_DOWN                    = 0x28;
	this.DOM_VK_PAGE_DOWN               = 0x22;
	this.DOM_VK_PAGE_UP                 = 0x21;
};

/* Copyright (c) 2006 Yahoo! Inc. All rights reserved. */

/**
 * @class a general logging class.  Expects to be initalized with a reference
 * to a html element to write to.
 *
 * @constructor
 * @param {String} sModuleName the name of the module this instance belongs 
 * to.  Used in the log message to help id where the msg came from.
 */
function ygLogger(sModuleName) {
	if (this.setModuleName)
	this.setModuleName(sModuleName);
}

ygLogger.DEBUG_ENABLED = true;
ygLogger.targetEl = null;
ygLogger.logStack = [];
ygLogger.startLog = new Date().getTime();
ygLogger.lastLog = new Date().getTime();
ygLogger.locked = true;
ygLogger.logTimeout = null;

ygLogger.init = function(oHostElement) {
	if (oHostElement) {
		ygLogger.targetEl = oHostElement;
	} else {
		// create element or create window?
	}
};

ygLogger.prototype.setModuleName = function(sModuleName) {
	this.logName = sModuleName;
};

ygLogger.prototype.debug = function() {
	if (ygLogger.DEBUG_ENABLED) {
		var newDate = new Date();
		var newTime = newDate.getTime();
		var timeStamp = newTime - ygLogger.lastLog;
		var totalSeconds = (newTime - ygLogger.startLog) / 1000;

		ygLogger.lastLog = newTime;

		for (var i = 0; i < arguments.length; i++) {
			ygLogger.logStack[ygLogger.logStack.length] = 
					timeStamp + " ms(" + totalSeconds + ") " + 
					newDate.toLocaleTimeString() + "<br />" + 
					this.logName + ": <b>" + arguments[i] + "</b>";
		}

		if (ygLogger.logTimeout == null) {
			ygLogger.logTimeout = setTimeout("ygLogger._outputMessages()" , 1);
		}

	}
};

ygLogger.disable = function() {
	ygLogger.DEBUG_ENABLED = false;
	try { ygLogger.targetEl.style.visibility = "hidden"; } catch(e) {}

};

ygLogger.enable = function() {
	ygLogger.DEBUG_ENABLED = true;
	try { ygLogger.targetEl.style.visibility = ""; } catch(e) {}
};

ygLogger._outputMessages = function() {
	if (ygLogger.targetEl != null) {

		for (var i = 0; i < ygLogger.logStack.length; i++) {
			var sMsg = ygLogger.logStack[i];
			var oNewElement = ygLogger.targetEl.appendChild(
					document.createElement("p"));
			oNewElement.innerHTML = sMsg;
		}
		
		ygLogger.logStack = [];

		ygLogger.targetEl.scrollTop = ygLogger.targetEl.scrollHeight;

		// debugger;
	}

	ygLogger.logTimeout = null;
};

/* Copyright (c) 2006 Yahoo! Inc. All rights reserved. */

/**
 * Emulates OmniOutliner's task view.  The check box marks a task complete.
 * It is a simulated form field with three states ...
 * 0=unchecked, 1=some children checked, 2=all children checked
 * When a task is clicked, the state of the nodes and parent and children
 * are updated, and this behavior cascades.
 *
 * @extends YAHOO.widget.TextNode
 * @constructor
 * @param oData {object} a string or object containing the data that will
 * be used to render this node
 * @param oParent {YAHOO.widget.Node} this node's parent node
 * @param expanded {boolean} the initial expanded/collapsed state
 * @param checked {boolean} the initial checked/unchecked state
 */
YAHOO.widget.TaskNode = function(oData, oParent, expanded, checked) {
    this.logger = new ygLogger("TaskNode");

	if (oParent) { 
		this.init(oData, oParent, expanded);
		this.setUpLabel(oData);
		this.checked = checked;
	}
};

YAHOO.widget.TaskNode.prototype = new YAHOO.widget.TextNode();

/**
 * True if checkstate is 1 (some children checked) or 2 (all children checked),
 * false if 0.
 *
 * @type boolean
 */
YAHOO.widget.TaskNode.prototype.checked = false;

/**
 * checkState
 * 0=unchecked, * 1=some children checked, 2=all children checked
 *
 * @type int
 */
YAHOO.widget.TaskNode.prototype.checkState = 0;

/**
 * The id of the check element
 *
 * @type string
 */
YAHOO.widget.TaskNode.prototype.getCheckElId = function() { 
	return "ygtvcheck" + this.index; 
};

/**
 * Returns the check box element
 *
 * @return the check html element (img)
 */
YAHOO.widget.TaskNode.prototype.getCheckEl = function() { 
	return document.getElementById(this.getCheckElId()); 
};

/**
 * The style of the check element, derived from its current state
 *
 * @return {string} the css style for the current check state
 */
YAHOO.widget.TaskNode.prototype.getCheckStyle = function() { 
	return "ygtvcheck" + this.checkState;
};

/**
 * Returns the link that will invoke this node's check toggle
 *
 * @return {string} returns the link required to adjust the checkbox state
 */
YAHOO.widget.TaskNode.prototype.getCheckLink = function() { 
	return "YAHOO.widget.TreeView.getNode(\'" + this.tree.id + "\'," + 
		this.index + ").checkClick()";
};

/**
 * Invoked when the user clicks the check box
 */
YAHOO.widget.TaskNode.prototype.checkClick = function() { 
	if (this.checkState === 0) {
		this.check();
	} else {
		this.uncheck();
	}
};

/**
 * Refresh the state of this node's parent, and cascade up.
 */
YAHOO.widget.TaskNode.prototype.updateParent = function() { 
	var p = this.parent;

	if (!p || !p.updateParent) {
		this.logger.debug("Abort udpate parent: " + this.index);
		return;
	}

	var somethingChecked = false;
	var somethingNotChecked = false;

	for (var i=0;i< p.children.length;++i) {
		if (p.children[i].checked) {
			somethingChecked = true;
			// checkState will be 1 if the child node has unchecked children
			if (p.children[i].checkState == 1) {
				somethingNotChecked = true;
			}
		} else {
			somethingNotChecked = true;
		}
	}

	if (somethingChecked) {
		p.setCheckState( (somethingNotChecked) ? 1 : 2 );
	} else {
		p.setCheckState(0);
	}

	p.updateCheckHtml();
	p.updateParent();
};

/**
 * If the node has been rendered, update the html to reflect the current
 * state of the node.
 */
YAHOO.widget.TaskNode.prototype.updateCheckHtml = function() { 
	if (this.parent && this.parent.childrenRendered) {
		this.getCheckEl().className = this.getCheckStyle();
	}
};

/**
 * Updates the state.  The checked property is true if the state is 1 or 2
 * 
 * @param the new check state
 */
YAHOO.widget.TaskNode.prototype.setCheckState = function(state) { 
	this.checkState = state;
	this.checked = (state > 0);
};

/**
 * Check this node
 */
YAHOO.widget.TaskNode.prototype.check = function() { 
	this.setCheckState(2);
	for (var i=0; i<this.children.length; ++i) {
		this.children[i].check();
	}
	this.updateCheckHtml();
	this.updateParent();
};

/**
 * Uncheck this node
 */
YAHOO.widget.TaskNode.prototype.uncheck = function() { 
	this.setCheckState(0);
	for (var i=0; i<this.children.length; ++i) {
		this.children[i].uncheck();
	}
	this.updateCheckHtml();
	this.updateParent();
};

// Overrides YAHOO.widget.TextNode
YAHOO.widget.TaskNode.prototype.getNodeHtml = function() { 
	var sb = new Array();

	sb[sb.length] = '<table border="0" cellpadding="0" cellspacing="0">';
	sb[sb.length] = '<tr>';
	
	for (i=0;i<this.depth;++i) {
		sb[sb.length] = '<td class="' + this.getDepthStyle(i) + '">&nbsp;</td>';
	}

	sb[sb.length] = '<td';
	sb[sb.length] = ' id="' + this.getToggleElId() + '"';
	sb[sb.length] = ' class="' + this.getStyle() + '"';
	if (this.hasChildren(true)) {
		sb[sb.length] = ' onmouseover="this.className=';
		sb[sb.length] = 'YAHOO.widget.TreeView.getNode(\'';
		sb[sb.length] = this.tree.id + '\',' + this.index +  ').getHoverStyle()"';
		sb[sb.length] = ' onmouseout="this.className=';
		sb[sb.length] = 'YAHOO.widget.TreeView.getNode(\'';
		sb[sb.length] = this.tree.id + '\',' + this.index +  ').getStyle()"';
	}
	sb[sb.length] = ' onclick="javascript:' + this.getToggleLink() + '">&nbsp;';
	sb[sb.length] = '</td>';

	// check box
	sb[sb.length] = '<td';
	sb[sb.length] = ' id="' + this.getCheckElId() + '"';
	sb[sb.length] = ' class="' + this.getCheckStyle() + '"';
	sb[sb.length] = ' onclick="javascript:' + this.getCheckLink() + '">';
	sb[sb.length] = '&nbsp;</td>';
	

	sb[sb.length] = '<td>';
	sb[sb.length] = '<a';
	sb[sb.length] = ' id="' + this.labelElId + '"';
	sb[sb.length] = ' class="' + this.labelStyle + '"';
	sb[sb.length] = ' href="' + this.href + '"';
	sb[sb.length] = ' target="' + this.target + '"';
	if (this.hasChildren(true)) {
		sb[sb.length] = ' onmouseover="document.getElementById(\'';
		sb[sb.length] = this.getToggleElId() + '\').className=';
		sb[sb.length] = 'YAHOO.widget.TreeView.getNode(\'';
		sb[sb.length] = this.tree.id + '\',' + this.index +  ').getHoverStyle()"';
		sb[sb.length] = ' onmouseout="document.getElementById(\'';
		sb[sb.length] = this.getToggleElId() + '\').className=';
		sb[sb.length] = 'YAHOO.widget.TreeView.getNode(\'';
		sb[sb.length] = this.tree.id + '\',' + this.index +  ').getStyle()"';
	}
	sb[sb.length] = ' >';
	sb[sb.length] = this.label;
	sb[sb.length] = '</a>';
	sb[sb.length] = '</td>';
	sb[sb.length] = '</tr>';
	sb[sb.length] = '</table>';

	return sb.join("");

};

/* Copyright (c) 2006 Yahoo! Inc. All rights reserved. */YAHOO.widget.TreeView=function(id){if(id){this.init(id);}};YAHOO.widget.TreeView.nodeCount=0;YAHOO.widget.TreeView.prototype={id:null,_nodes:null,locked:false,_expandAnim:null,_collapseAnim:null,_animCount:0,maxAnim:2,setExpandAnim:function(_2){if(YAHOO.widget.TVAnim.isValid(_2)){this._expandAnim=_2;}},setCollapseAnim:function(_3){if(YAHOO.widget.TVAnim.isValid(_3)){this._collapseAnim=_3;}},animateExpand:function(el){if(this._expandAnim&&this._animCount<this.maxAnim){var _5=this;var a=YAHOO.widget.TVAnim.getAnim(this._expandAnim,el,function(){_5.expandComplete();});if(a){++this._animCount;a.animate();}return true;}return false;},animateCollapse:function(el){if(this._collapseAnim&&this._animCount<this.maxAnim){var _7=this;var a=YAHOO.widget.TVAnim.getAnim(this._collapseAnim,el,function(){_7.collapseComplete();});if(a){++this._animCount;a.animate();}return true;}return false;},expandComplete:function(){--this._animCount;},collapseComplete:function(){--this._animCount;},init:function(id){this.id=id;this._nodes=new Array();YAHOO.widget.TreeView.trees[id]=this;this.root=new YAHOO.widget.RootNode(this);},draw:function(){var _8=this.root.getHtml();document.getElementById(this.id).innerHTML=_8;this.firstDraw=false;},regNode:function(_9){this._nodes[_9.index]=_9;},getRoot:function(){return this.root;},setDynamicLoad:function(_10){this.root.setDynamicLoad(_10);},expandAll:function(){if(!this.locked){this.root.expandAll();}},collapseAll:function(){if(!this.locked){this.root.collapseAll();}},getNodeByIndex:function(_11){var n=this._nodes[_11];return (n)?n:null;},getNodeByProperty:function(_13,_14){for(var i in this._nodes){var n=this._nodes[i];if(n.data&&_14==n.data[_13]){return n;}}return null;},onExpand:function(_16){},onCollapse:function(_17){}};YAHOO.widget.TreeView.trees=[];YAHOO.widget.TreeView.getTree=function(_18){var t=YAHOO.widget.TreeView.trees[_18];return (t)?t:null;};YAHOO.widget.TreeView.getNode=function(_20,_21){var t=YAHOO.widget.TreeView.getTree(_20);return (t)?t.getNodeByIndex(_21):null;};YAHOO.widget.TreeView.addHandler=function(el,_22,fn,_24){_24=(_24)?true:false;if(el.addEventListener){el.addEventListener(_22,fn,_24);}else{if(el.attachEvent){el.attachEvent("on"+_22,fn);}else{el["on"+_22]=fn;}}};YAHOO.widget.TreeView.preload=function(){var _25=["ygtvtn","ygtvtm","ygtvtmh","ygtvtp","ygtvtph","ygtvln","ygtvlm","ygtvlmh","ygtvlp","ygtvlph","ygtvloading"];var sb=[];for(var i=0;i<_25.length;++i){sb[sb.length]="<span class=\""+_25[i]+"\">&nbsp;</span>";}var f=document.createElement("div");var s=f.style;s.position="absolute";s.top="-1000px";s.left="-1000px";f.innerHTML=sb.join("");document.body.appendChild(f);};YAHOO.widget.TreeView.addHandler(window,"load",YAHOO.widget.TreeView.preload);YAHOO.widget.Node=function(_29,_30,_31){if(_30){this.init(_29,_30,_31);}};YAHOO.widget.Node.prototype={index:0,children:null,tree:null,data:null,parent:null,depth:-1,href:null,target:"_self",expanded:false,multiExpand:true,renderHidden:false,childrenRendered:false,previousSibling:null,nextSibling:null,_dynLoad:false,dataLoader:null,isLoading:false,hasIcon:true,init:function(_32,_33,_34){this.data=_32;this.children=[];this.index=YAHOO.widget.TreeView.nodeCount;++YAHOO.widget.TreeView.nodeCount;this.expanded=_34;if(_33){this.tree=_33.tree;this.parent=_33;this.href="javascript:"+this.getToggleLink();this.depth=_33.depth+1;this.multiExpand=_33.multiExpand;_33.appendChild(this);}},appendChild:function(_35){if(this.hasChildren()){var sib=this.children[this.children.length-1];sib.nextSibling=_35;_35.previousSibling=sib;}this.tree.regNode(_35);this.children[this.children.length]=_35;return _35;},getSiblings:function(){return this.parent.children;},showChildren:function(){if(!this.tree.animateExpand(this.getChildrenEl())){if(this.hasChildren()){this.getChildrenEl().style.display="";}}},hideChildren:function(){if(!this.tree.animateCollapse(this.getChildrenEl())){this.getChildrenEl().style.display="none";}},getElId:function(){return "ygtv"+this.index;},getChildrenElId:function(){return "ygtvc"+this.index;},getToggleElId:function(){return "ygtvt"+this.index;},getEl:function(){return document.getElementById(this.getElId());},getChildrenEl:function(){return document.getElementById(this.getChildrenElId());},getToggleEl:function(){return document.getElementById(this.getToggleElId());},getToggleLink:function(){return "YAHOO.widget.TreeView.getNode('"+this.tree.id+"',"+this.index+").toggle()";},collapse:function(){if(!this.expanded){return;}if(!this.getEl()){this.expanded=false;return;}this.hideChildren();this.expanded=false;if(this.hasIcon){this.getToggleEl().className=this.getStyle();}this.tree.onCollapse(this);},expand:function(){if(this.expanded){return;}if(!this.getEl()){this.expanded=true;return;}if(!this.childrenRendered){this.getChildrenEl().innerHTML=this.renderChildren();}this.expanded=true;if(this.hasIcon){this.getToggleEl().className=this.getStyle();}if(this.isLoading){this.expanded=false;return;}if(!this.multiExpand){var _37=this.getSiblings();for(var i=0;i<_37.length;++i){if(_37[i]!=this&&_37[i].expanded){_37[i].collapse();}}}this.showChildren();this.tree.onExpand(this);},getStyle:function(){if(this.isLoading){return "ygtvloading";}else{var loc=(this.nextSibling)?"t":"l";var _39="n";if(this.hasChildren(true)||this.isDynamic()){_39=(this.expanded)?"m":"p";}return "ygtv"+loc+_39;}},getHoverStyle:function(){var s=this.getStyle();if(this.hasChildren(true)&&!this.isLoading){s+="h";}return s;},expandAll:function(){for(var i=0;i<this.children.length;++i){var c=this.children[i];if(c.isDynamic()){alert("Not supported (lazy load + expand all)");break;}else{if(!c.multiExpand){alert("Not supported (no multi-expand + expand all)");break;}else{c.expand();c.expandAll();}}}},collapseAll:function(){for(var i=0;i<this.children.length;++i){this.children[i].collapse();this.children[i].collapseAll();}},setDynamicLoad:function(_41){this.dataLoader=_41;this._dynLoad=true;},isRoot:function(){return (this==this.tree.root);},isDynamic:function(){var _42=(!this.isRoot()&&(this._dynLoad||this.tree.root._dynLoad));return _42;},hasChildren:function(_43){return (this.children.length>0||(_43&&this.isDynamic()&&!this.childrenRendered));},toggle:function(){if(!this.tree.locked&&(this.hasChildren(true)||this.isDynamic())){if(this.expanded){this.collapse();}else{this.expand();}}},getHtml:function(){var sb=[];sb[sb.length]="<div class=\"ygtvitem\" id=\""+this.getElId()+"\">";sb[sb.length]=this.getNodeHtml();sb[sb.length]=this.getChildrenHtml();sb[sb.length]="</div>";return sb.join("");},getChildrenHtml:function(){var sb=[];sb[sb.length]="<div class=\"ygtvchildren\"";sb[sb.length]=" id=\""+this.getChildrenElId()+"\"";if(!this.expanded){sb[sb.length]=" style=\"display:none;\"";}sb[sb.length]=">";if(this.hasChildren(true)&&this.expanded){sb[sb.length]=this.renderChildren();}sb[sb.length]="</div>";return sb.join("");},renderChildren:function(){var _44=this;if(this.isDynamic()&&!this.childrenRendered){this.isLoading=true;this.tree.locked=true;if(this.dataLoader){setTimeout(function(){_44.dataLoader(_44,function(){_44.loadComplete();});},10);}else{if(this.tree.root.dataLoader){setTimeout(function(){_44.tree.root.dataLoader(_44,function(){_44.loadComplete();});},10);}else{return "Error: data loader not found or not specified.";}}return "";}else{return this.completeRender();}},completeRender:function(){var sb=[];for(var i=0;i<this.children.length;++i){sb[sb.length]=this.children[i].getHtml();}this.childrenRendered=true;return sb.join("");},loadComplete:function(){this.getChildrenEl().innerHTML=this.completeRender();this.isLoading=false;this.expand();this.tree.locked=false;},getAncestor:function(_45){if(_45>=this.depth||_45<0){return null;}var p=this.parent;while(p.depth>_45){p=p.parent;}return p;},getDepthStyle:function(_47){return (this.getAncestor(_47).nextSibling)?"ygtvdepthcell":"ygtvblankdepthcell";},getNodeHtml:function(){return "";}};YAHOO.widget.RootNode=function(_48){this.init(null,null,true);this.tree=_48;};YAHOO.widget.RootNode.prototype=new YAHOO.widget.Node();YAHOO.widget.RootNode.prototype.getNodeHtml=function(){return "";};YAHOO.widget.TextNode=function(_49,_50,_51){if(_50){this.init(_49,_50,_51);this.setUpLabel(_49);}};YAHOO.widget.TextNode.prototype=new YAHOO.widget.Node();YAHOO.widget.TextNode.prototype.labelStyle="ygtvlabel";YAHOO.widget.TextNode.prototype.labelElId=null;YAHOO.widget.TextNode.prototype.label=null;YAHOO.widget.TextNode.prototype.setUpLabel=function(_52){if(typeof _52=="string"){_52={label:_52};}this.label=_52.label;if(_52.href){this.href=_52.href;}if(_52.target){this.target=_52.target;}this.labelElId="ygtvlabelel"+this.index;};YAHOO.widget.TextNode.prototype.getLabelEl=function(){return document.getElementById(this.labelElId);};YAHOO.widget.TextNode.prototype.getNodeHtml=function(){var sb=new Array();sb[sb.length]="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";sb[sb.length]="<tr>";for(i=0;i<this.depth;++i){sb[sb.length]="<td class=\""+this.getDepthStyle(i)+"\">&nbsp;</td>";}var _53="YAHOO.widget.TreeView.getNode('"+this.tree.id+"',"+this.index+")";sb[sb.length]="<td";sb[sb.length]=" id=\""+this.getToggleElId()+"\"";sb[sb.length]=" class=\""+this.getStyle()+"\"";if(this.hasChildren(true)){sb[sb.length]=" onmouseover=\"this.className=";sb[sb.length]=_53+".getHoverStyle()\"";sb[sb.length]=" onmouseout=\"this.className=";sb[sb.length]=_53+".getStyle()\"";}sb[sb.length]=" onclick=\"javascript:"+this.getToggleLink()+"\">&nbsp;";sb[sb.length]="</td>";sb[sb.length]="<td>";sb[sb.length]="<a";sb[sb.length]=" id=\""+this.labelElId+"\"";sb[sb.length]=" class=\""+this.labelStyle+"\"";sb[sb.length]=" href=\""+this.href+"\"";sb[sb.length]=" target=\""+this.target+"\"";if(this.hasChildren(true)){sb[sb.length]=" onmouseover=\"document.getElementById('";sb[sb.length]=this.getToggleElId()+"').className=";sb[sb.length]=_53+".getHoverStyle()\"";sb[sb.length]=" onmouseout=\"document.getElementById('";sb[sb.length]=this.getToggleElId()+"').className=";sb[sb.length]=_53+".getStyle()\"";}sb[sb.length]=" >";sb[sb.length]=this.label;sb[sb.length]="</a>";sb[sb.length]="</td>";sb[sb.length]="</tr>";sb[sb.length]="</table>";return sb.join("");};YAHOO.widget.MenuNode=function(_54,_55,_56){if(_55){this.init(_54,_55,_56);this.setUpLabel(_54);}this.multiExpand=false;};YAHOO.widget.MenuNode.prototype=new YAHOO.widget.TextNode();YAHOO.widget.HTMLNode=function(_57,_58,_59,_60){if(_58){this.init(_57,_58,_59);this.initContent(_57,_60);}};YAHOO.widget.HTMLNode.prototype=new YAHOO.widget.Node();YAHOO.widget.HTMLNode.prototype.contentStyle="ygtvhtml";YAHOO.widget.HTMLNode.prototype.contentElId=null;YAHOO.widget.HTMLNode.prototype.content=null;YAHOO.widget.HTMLNode.prototype.initContent=function(_61,_62){if(typeof _61=="string"){_61={html:_61};}this.html=_61.html;this.contentElId="ygtvcontentel"+this.index;this.hasIcon=_62;};YAHOO.widget.HTMLNode.prototype.getContentEl=function(){return document.getElementById(this.contentElId);};YAHOO.widget.HTMLNode.prototype.getNodeHtml=function(){var sb=new Array();sb[sb.length]="<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";sb[sb.length]="<tr>";for(i=0;i<this.depth;++i){sb[sb.length]="<td class=\""+this.getDepthStyle(i)+"\">&nbsp;</td>";}if(this.hasIcon){sb[sb.length]="<td";sb[sb.length]=" id=\""+this.getToggleElId()+"\"";sb[sb.length]=" class=\""+this.getStyle()+"\"";sb[sb.length]=" onclick=\"javascript:"+this.getToggleLink()+"\">&nbsp;";if(this.hasChildren(true)){sb[sb.length]=" onmouseover=\"this.className=";sb[sb.length]="YAHOO.widget.TreeView.getNode('";sb[sb.length]=this.tree.id+"',"+this.index+").getHoverStyle()\"";sb[sb.length]=" onmouseout=\"this.className=";sb[sb.length]="YAHOO.widget.TreeView.getNode('";sb[sb.length]=this.tree.id+"',"+this.index+").getStyle()\"";}sb[sb.length]="</td>";}sb[sb.length]="<td";sb[sb.length]=" id=\""+this.contentElId+"\"";sb[sb.length]=" class=\""+this.contentStyle+"\"";sb[sb.length]=" >";sb[sb.length]=this.html;sb[sb.length]="</td>";sb[sb.length]="</tr>";sb[sb.length]="</table>";return sb.join("");};YAHOO.widget.TVAnim=new function(){this.FADE_IN="YAHOO.widget.TVFadeIn";this.FADE_OUT="YAHOO.widget.TVFadeOut";this.getAnim=function(_63,el,_64){switch(_63){case this.FADE_IN:return new YAHOO.widget.TVFadeIn(el,_64);case this.FADE_OUT:return new YAHOO.widget.TVFadeOut(el,_64);default:return null;}};this.isValid=function(_65){return ("undefined"!=eval("typeof "+_65));};};YAHOO.widget.TVFadeIn=function(el,_66){this.el=el;this.callback=_66;};YAHOO.widget.TVFadeIn.prototype={animate:function(){var _67=this;var s=this.el.style;s.opacity=0.1;s.filter="alpha(opacity=10)";s.display="";var dur=0.4;var a=new YAHOO.util.Anim(this.el,{opacity:{from:0.1,to:1,unit:""}},dur);a.onComplete.subscribe(function(){_67.onComplete();});a.animate();},onComplete:function(){this.callback();}};YAHOO.widget.TVFadeOut=function(el,_69){this.el=el;this.callback=_69;};YAHOO.widget.TVFadeOut.prototype={animate:function(){var _70=this;var dur=0.4;var a=new YAHOO.util.Anim(this.el,{opacity:{from:1,to:0.1,unit:""}},dur);a.onComplete.subscribe(function(){_70.onComplete();});a.animate();},onComplete:function(){var s=this.el.style;s.display="none";s.filter="alpha(opacity=100)";this.callback();}};/* Copyright (c) 2006 Yahoo! Inc. All rights reserved. */

/**
 * @class The Yahoo global namespace
 */
var YAHOO = function() {

    return {

        /**
         * Yahoo presentation platform utils namespace
         */
        util: {},

        /**
         * Yahoo presentation platform widgets namespace
         */
        widget: {},

        /**
         * Yahoo presentation platform examples namespace
         */
        example: {},

        /**
         * Returns the namespace specified and creates it if it doesn't exist
         *
         * YAHOO.namespace("property.package");
         * YAHOO.namespace("YAHOO.property.package");
         *
         * Either of the above would create YAHOO.property, then
         * YAHOO.property.package
         *
         * @param  {String} sNameSpace String representation of the desired 
         *                             namespace
         * @return {Object}            A reference to the namespace object
         */
        namespace: function( sNameSpace ) {

            if (!sNameSpace || !sNameSpace.length) {
                return null;
            }

            var levels = sNameSpace.split(".");

            var currentNS = YAHOO;

            // YAHOO is implied, so it is ignored if it is included
            for (var i=(levels[0] == "YAHOO") ? 1 : 0; i<levels.length; ++i) {
                currentNS[levels[i]] = currentNS[levels[i]] || {};
                currentNS = currentNS[levels[i]];
            }

            return currentNS;

        }
    };

} ();

