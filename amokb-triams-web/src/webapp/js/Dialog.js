/**
 * @class Диалоговое окно
 *
 * @constructor
 * @param {String} theDialogDiv  элемент диалога
 */
msh.widget.Dialog = function(theDialogElement) {

    /**
        *  Показать диалог
        */
    this.show = function() {
        theDialogElement.style.display = "block";
        theDialogElement.style.visibility = "visible";
        msh.effect.FadeEffect.putFade();
        theDialogElement.style.zIndex = 1 + Math.round(msh.effect.FadeEffect.getIndex());
        theDialogElement.parentNode.removeChild(theDialogElement);
        document.body.appendChild(theDialogElement);
        center(theDialogElement) ;
    }
    /**
        *  Убрать диалог
        */
    this.hide = function() {
        msh.effect.FadeEffect.pushFade();
        theDialogElement.style.display = "none";
        theDialogElement.style.visibility = "hidden";
    }
    
    
    
}

function center(element){
    try{
        element = $(element);
    }catch(e){
        return;
    }

    var my_width  = 0;
    var my_height = 0;

    if ( typeof( window.innerWidth ) == 'number' ){
        my_width  = window.innerWidth;
        my_height = window.innerHeight;
    }else if ( document.documentElement && 
             ( document.documentElement.clientWidth ||
               document.documentElement.clientHeight ) ){
        my_width  = document.documentElement.clientWidth;
        my_height = document.documentElement.clientHeight;
    }
    else if ( document.body && 
            ( document.body.clientWidth || document.body.clientHeight ) ){
        my_width  = document.body.clientWidth;
        my_height = document.body.clientHeight;
    }

    element.style.position = 'absolute';
    element.style.zIndex   = 99;

    var scrollY = 0;

    if ( document.documentElement && document.documentElement.scrollTop ){
        scrollY = document.documentElement.scrollTop;
    }else if ( document.body && document.body.scrollTop ){
        scrollY = document.body.scrollTop;
    }else if ( window.pageYOffset ){
        scrollY = window.pageYOffset;
    }else if ( window.scrollY ){
        scrollY = window.scrollY;
    }

    var elementDimensions = getDimensions(element);

    var setX = ( my_width  - elementDimensions.width  ) / 2;
    var setY = ( my_height - elementDimensions.height ) / 3 + scrollY;

    setX = ( setX < 0 ) ? 0 : setX;
    setY = ( setY < 0 ) ? 0 : setY;

    element.style.left = setX + "px";
    element.style.top  = setY + "px";

    element.style.display  = 'block';
}

function getDimensions(element) {
    element = $(element);
    var display = element.style.display;
    if (display != 'none' && display != null) // Safari bug
      return {width: element.offsetWidth, height: element.offsetHeight};

    // All *Width and *Height properties give 0 on elements with display none,
    // so enable the element temporarily
    var els = element.style;
    var originalVisibility = els.visibility;
    var originalPosition = els.position;
    var originalDisplay = els.display;
    els.visibility = 'hidden';
    els.position = 'absolute';
    els.display = 'block';
    var originalWidth = element.clientWidth;
    var originalHeight = element.clientHeight;
    els.display = originalDisplay;
    els.position = originalPosition;
    els.visibility = originalVisibility;
    return {width: originalWidth, height: originalHeight};
  }
 