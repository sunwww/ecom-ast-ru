<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
		<div class='titleTrail'>
			<span><a href='js-ecom_vocEntity-list.do' title='Список справочников'>Список справочников</a></span> » <span>Справочник : ${vocEntityInfo.comment}</span>
		</div>    
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    
    <tiles:put name='body' type='string' >

    	
<div style="" class="">
        <div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>
        <div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc">
            <h3 style="margin-bottom:5px;">${vocEntityInfo.comment}</h3>
            <div id="topic-grid" style="border:1px solid #99bbe8;overflow: hidden; 
            	 height: 600px;position:relative;left:0;top:0;"></div>
        </div></div></div>
        <div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>
  </div>
        	
    </tiles:put>
    
    <tiles:put name='javascript' type='string'>
    	<script type="text/javascript" src="/skin/ext/ext-base.js"></script>
    	<script type="text/javascript" src="/skin/ext/ext-all.js"></script>
    	<script type="text/javascript" src="/skin/ext/examples.js"></script>
	   <script type='text/javascript' src='./dwr/interface/VocEntityService-CA113b8ec45f6.js'></script>   
    	
    	<script type='text/javascript'>
    	
Ext.onReady(function(){
	VocEntityService.getVocEntityInfo('${param.id}', {
		callback : function(aInfo) {
			//alert(1);
			onVocEntityInfo(aInfo) ;
			//alert(2) ;
		}
	}) ;
		
}) ;

/*
[
  {name: 'name', mapping: 'name'},
            {name: 'id', mapping: 'id'},
            {name: 'code', mapping: 'code'}
        ]
*/
function createDataMapping(aInfo) {
	var arr = new Array() ;
	var id = new Object() ;
	id.name = 'id' ;
	id.mapping = 'id';
	arr[0] = id ;
	for(var i = 0 ; i<aInfo.properties.length; i++) {
		var prop = aInfo.properties[i] ;
		var o = new Object() ;
		o.name = prop.name ;
		o.mapping = prop.name ;
		arr[i+1] = o ;
	}
	return arr ;
}

function createNewRecord(aInfo) {
	var arr = new Array() ;
	var id = new Object() ;
	var obj = new Object() ;
	obj.id="" ;
	id.name = 'id' ;
	arr[0] = id ;
	for(var i = 0 ; i<aInfo.properties.length; i++) {
		var prop = aInfo.properties[i] ;
		var o = new Object() ;
		o.name = prop.name ;
		arr[i+1] = o ;
		obj[prop.name] = "" ;
	}
	var Record = Ext.data.Record.create(arr) ;
	return new Record(obj) ;
}

/*
 *          id: 'topic', // id assigned so we can apply custom css (e.g. .x-grid-col-topic b { color:#333 })
 *          header: "Topic",
 *          dataIndex: 'name',
 *          width: 490,
 *          css: 'white-space:normal;',
 *          editor: new Ed(new fm.TextField({
 *              allowBlank: false
 *          }))
 *
 */
function createColumnModel(aInfo) {
	var arr = new Array() ;
	var id = new Object() ;
	id.id = 'id' ;
	id.header = 'ИД' ;
	id.dataIndex = 'id' ;
	id.width = 60 ;
	id.sortable = true ;
	arr[0] = id ;
	for(var i = 0 ; i<aInfo.properties.length; i++) {
		var prop = aInfo.properties[i] ;
		var o = new Object() ;
		o.id = prop.name ;
		o.header = prop.comment ;
		o.dataIndex = prop.name ;
		if("name"==prop.name) {
			o.width = 300 ;
		} else {
			o.width = 100 ;
		}
		o.editor = new Ext.grid.GridEditor(new Ext.form.TextField({
               allowBlank: false
           })) ;
		o.sortable =  true   ;
		arr[i+1] = o ;
	}
	return arr ;
}

function onVocEntityInfo(aVocEntityInfo) {
	Ext.QuickTips.init();
	
    // create the Data Store
    var ds = new Ext.data.Store({
        // load using script tags for cross domain, if the data in on the same domain as
        // this page, an HttpProxy would be better
        proxy: new Ext.data.HttpProxy({
            url: 'vocEntity/'+aVocEntityInfo.classname
        }),

		
        // create reader that reads the Topic records
        reader: new Ext.data.JsonReader({
            root: 'topics',
            totalProperty: 'totalCount',
            id: 'id'
        }, createDataMapping(aVocEntityInfo) ),

        // turn on remote sorting
        remoteSort: true
    });


    // the column model has information about grid columns
    // dataIndex maps the column to the specific data field in
    // the data store
    var cm = new Ext.grid.ColumnModel(createColumnModel(aVocEntityInfo)) ;
    
	var grid = new Ext.grid.EditorGrid('topic-grid', {
        ds: ds,
        cm: cm,
        enableColLock:false
    });    

    // make the grid resizable, do before render for better performance
    var rz = new Ext.Resizable('topic-grid', {
        wrap:true,
        minHeight:100,
        minWidth: 100,
        pinned:true,
        handles: 's'
    });
    rz.on('resize', grid.autoSize, grid);

    // render it
    grid.render();

    var gridFoot = grid.getView().getFooterPanel(true);

    // add a paging toolbar to the grid's footer
    var paging = new Ext.PagingToolbar(gridFoot, ds, {
        pageSize: 25,
        displayInfo: true,
        displayMsg: 'Показывается {0} - {1} из {2}',
        emptyMsg: "No topics to display"
        , beforePageText : "Страница" 
        ,  	afterPageText : "из {0}" 
    });
    paging.beforePageText = "Страница" ;
    // add the detailed view button
    paging.add('-', {
        text: 'Добавить',
        cls:'x-btn-text-icon add-opt'
        , handler : function(){
			grid.stopEditing();
            ds.insert(0, createNewRecord(aVocEntityInfo));
            grid.startEditing(0, 1);    	
        }}
        , {
        	text: 'Удалить'
        	, cls:'x-btn-text-icon remove'
        	, handler : function() {
        		if(grid.selModel.selection!=null) {
        			var id = grid.selModel.selection.record.data['id'] ;
					Ext.MessageBox.confirm('Удаление...'
						, 'Удалить запись с ИД '+id+"?", function(aBtn) {
							if(aBtn=='yes') {
								VocEntityService.removeVocEntity(aVocEntityInfo.classname, id, {
									callback: function() {
										grid.getView().refresh(true);
										Ext.example.msg('Удаление...', 'Запись с ИД {0} удалена', id);
									}
								}) ;	
							}
							
						});
        			
        		}
        		//document.grid = grid ; //alert(grid.getSelected()) ;
        	}
        }
    );

    // trigger the data store load
    ds.load({params:{start:0, limit:25}});

	function onEdit(aEvent) {
		VocEntityService.setVocEntityValue(aVocEntityInfo.classname
			, aEvent.record.data['id']
			, aEvent.field
			, aEvent.value, {
			callback : function(aId) {
				if(aEvent.record.data['id']==null || aEvent.record.data['id']=='') {
					aEvent.record.data['id'] = aId ;
					Ext.example.msg('Добавление записи...', 'Запись с ИД {0} добавлена', aId);
				}
			}
		}) ;
	}
	
	grid.addListener("afteredit", onEdit) ;
	
}
    		
    	</script>
    </tiles:put>
    
	
	<tiles:put name="style" type="string">
		<link rel="stylesheet" type="text/css" href="/skin/ext/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="/skin/ext/css/xtheme-gray.css" />
	</tiles:put>
	    
</tiles:insert>