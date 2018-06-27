var menu;

function setMenu(idMenu) {
    menu = document.querySelector('#'+idMenu);
}

function showMenu(x, y){
    menu.style.left = x + 'px';
    menu.style.top = y + 'px';
    menu.classList.add('show-menu');
}

function hideMenu(){
    menu.classList.remove('show-menu');
}

function onContextMenu(e){
    e.preventDefault();
    showMenu(e.pageX-140, e.pageY-60);
    document.addEventListener('mouseup', onMouseUp, false);
}

function onMouseUp(e){
     //if(e.target.nodeName!='BUTTON'){
        hideMenu();
        document.removeEventListener('contextmenu', onContextMenu, true);
        document.removeEventListener('mouseup', onMouseUp);
   // }
}
