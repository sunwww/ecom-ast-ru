/**
* @class The MSH global namespace
*/
var msh = function() {
    return {

    /**
    * MSH presentation platform widgets namespace
    */
        widget: {},
    /**
    * MSH presentation platform widgets namespace
    */
        util: {},
    /**
         * Эффекты
         */
        effect: {},

        log: {},

        idemode: {}
    };
}() ;

Function.prototype.bindNoArgs = function (object) {
    var method = this;
    return function () {
        return method.apply(object);
    };
}

function entries(collection) {
    var result = [];
    // This is our real duck.

    for (var i = 0; i < collection.length; i++)
        result.push(collection[i]);

    return result;
}

Function.prototype.bind = function (object) {
    var method = this;
    var oldArguments = entries(arguments).slice(1);
    return function () {
        var newArguments = entries(arguments);
        return method.apply(object, oldArguments.concat(newArguments));
    };
}

Function.prototype.bindRequest = function (object) {
    var method = this;
    return function (aRequest) {
        var args = new Array() ;
        args.push(aRequest);
        //        try {
        return method.apply(object, args);
        //        } catch (e) {
        //            alert(e) ;
        //            return null ;
        //        }
    };
}

msh.log.error = function (aMessage) {
	try {
		console.error(aMessage) ;
	} catch (e) {
	}
}

msh.idemode.goInIdeMode = function() {
	setCookie("showTags","true") ;
	window.location.reload() ;
}	
