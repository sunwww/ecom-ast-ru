YAHOO.widget.CheckTaskNode = function(oData, oParent, expanded, checked, aId) {
    this.logger = new ygLogger("CheckTaskNode");

    if (oParent) {
        this.init(oData, oParent, expanded);
        this.setUpLabel(oData);
        this.checked = checked;
        this.checkState = checked?2:0;
        this.theId = aId ;
    }
};

YAHOO.widget.CheckTaskNode.prototype = new YAHOO.widget.TaskNode();

YAHOO.widget.CheckTaskNode.prototype.theId = -1 ;

