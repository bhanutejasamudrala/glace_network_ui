var canvas = null;
var circle;
var line;
var startNode;
var window;
var handlersInitialized = false;
var isNodeDraggable = false;
var state = 1;
var networkId = -1;
var networkMode = -1;
var nodeList = null;
var linkList = null;
var nodeCount = 0;
var x,y;
var totalProgress = 0; 
var currentProgress = 0;


var LabeledRect = fabric.util.createClass(fabric.Rect, {

	type: 'labeledRect',

	initialize: function(options) {
		options || (options = { });

		this.callSuper('initialize', options);
		this.set('label', options.label || '');
	},

	toObject: function() {
		return fabric.util.object.extend(this.callSuper('toObject'), {
			label: this.get('label')
		});
	},

	_render: function(ctx) {
		this.callSuper('_render', ctx);

		ctx.font = '10px Helvetica';
		ctx.fillStyle = '#000';
		
	}
});


var CustomLine = fabric.util.createClass(fabric.Line, {

	type: 'customLine',
	
	/**
	 * startNodeId of line edge
	 * @type String
	 * @default
	 */
	startNodeId : null,

	/**
	 * endNodeId of line edge
	 * @type String
	 * @default
	 */
	endNodeId : null,
	
	/**
	 * Link Strength
	 * @type Double
	 * @default
	 * **/
	strength : null,
	

	initialize: function(points, options) {

		options || (options = { });
		this.fx = points[0];
		this.tx = points[2]; 
		this.callSuper('initialize', points, options);
		this.set('startNodeId', options.sId || '');
		this.set('endNodeId', options.eId || '');
		this.set('strength', options.strength || 0.0);

		var count = 'arrows' in options ? options.arrows : 0;
		this.headlen = 'headLength' in options ? options.headLength : 10;
		if (count >= 2 || count <= -2) {
			this.arrows = 2;
		} else if (count === 1 || count === -1) {
			this.arrows =  count;
		}
		
		this.cutLevel = 'cutLevel' in options ? options.cutLevel : 0;

	},

	toObject: function() {
		return fabric.util.object.extend(this.callSuper('toObject'), {
		});
	},
	
	
	/**
	 * This method gets executed to draw link between two nodes
	 * @private
	 * @param {CanvasRenderingContext2D} ctx Context to render on
	 */
	_render: function(ctx, noTransform) {
		ctx.beginPath();

		if (noTransform) {
			//  Line coords are distances from left-top of canvas to origin of line.
			//  To render line in a path-group, we need to translate them to
			//  distances from center of path-group to center of line.
			var cp = this.getCenterPoint();
			ctx.translate(
					cp.x - this.strokeWidth / 2,
					cp.y - this.strokeWidth / 2
			);
		}

		if (!this.strokeDashArray || this.strokeDashArray && supportsLineDash) {
			// move from center (of virtual box) to its left/top corner
			// we can't assume x1, y1 is top left and x2, y2 is bottom right
			var p = this.calcLinePoints();
			var startX = p.x1;
			var startY = p.y1;
			var endX = p.x2;
			var endY = p.y2;
			ctx.moveTo( startX, startY );
			ctx.lineTo( (startX+this.cutLevel*10)/2, startY );
				
			ctx.moveTo( (startX+this.cutLevel*10)/2, startY );
			ctx.lineTo( (startX+this.cutLevel*10)/2, endY );
				
			ctx.moveTo( (startX+this.cutLevel*10)/2, endY );
			ctx.lineTo( endX, endY );			
			
			var p = this.calcLinePoints();
			var p = this.calcLinePoints();
			var fromx = p.x1+5;
			var tox = p.x2+5;
			var fromy = p.y1;
			var toy = p.y2;
			if(this.arrows){
				var angle = Math.atan2(toy-toy,tox-fromx);
				if(this.arrows >=2 || this.arrows <= -2 || this.arrows <= -1) {
					ctx.moveTo(fromx, fromy);
					ctx.lineTo(fromx+this.headlen*Math.cos(angle-Math.PI/6),fromy+this.headlen*Math.sin(angle-Math.PI/6));
					ctx.moveTo(fromx, fromy);
					ctx.lineTo(fromx+this.headlen*Math.cos(angle+Math.PI/6),fromy+this.headlen*Math.sin(angle+Math.PI/6));
				}
				if(this.arrows >=2 || this.arrows <= -2 ||this.arrows >= 1){
					ctx.moveTo(tox, toy);
					ctx.lineTo(tox-this.headlen*Math.cos(angle-Math.PI/6),toy-this.headlen*Math.sin(angle-Math.PI/6));
					ctx.moveTo(tox, toy);
					ctx.lineTo(tox-this.headlen*Math.cos(angle+Math.PI/6),toy-this.headlen*Math.sin(angle+Math.PI/6));
				}
			}
		}
		
		if(this.strength < 0.2) {
			ctx.strokeStyle = "#999999";
		} else if(this.strength >= 0.2 && this.strength < 0.4) {
			ctx.strokeStyle = "#777777";
		} else if(this.strength >= 0.4 && this.strength < 0.6) {
			ctx.strokeStyle = "#555555";
		} else if(this.strength >= 0.6 && this.strength < 0.8) {
			ctx.strokeStyle = "#333333";
		}  else if(this.strength >= 0.8) {
			ctx.strokeStyle = "#000000";
		}

		ctx.lineWidth = this.strokeWidth;
		var origStrokeStyle = ctx.strokeStyle;
		//ctx.strokeStyle = this.stroke || ctx.fillStyle;
		this.stroke && this._renderStroke(ctx);
		ctx.strokeStyle = origStrokeStyle;
	},
	
	 /**
     * Recalculates line points given width and height
     * @private
     */
    calcLinePoints: function() {
      var xMult = this.x1 <= this.x2 ? -1 : 1,
          yMult = this.y1 <= this.y2 ? -1 : 1,
          x1 = (xMult * this.width * 0.5),
          y1 = (yMult * this.height * 0.5),
          x2 = (xMult * this.width * -0.5),
          y2 = (yMult * this.height * -0.5);

      return {
        x1: x1,
        x2: x2,
        y1: y1,
        y2: y2
      };
    }
});

var CustomStraightLine = fabric.util.createClass(fabric.Line, {

	type: 'customstraightline',
	
	/**
	 * startNodeId of line edge
	 * @type String
	 * @default
	 */
	startNodeId : null,

	/**
	 * endNodeId of line edge
	 * @type String
	 * @default
	 */
	endNodeId : null,
	
	/**
	 * Link Strength
	 * @type Double
	 * @default
	 * **/
	strength : null,

	initialize: function(points, options) {

		options || (options = { });
		this.fx = points[0];
		this.tx = points[2]; 
		this.callSuper('initialize', points, options);
		this.set('startNodeId', options.sId || '');
		this.set('endNodeId', options.eId || '');
		this.set('strength', options.strength || 0.0);
		
		var count = 'arrows' in options ? options.arrows : 0;
		this.headlen = 'headLength' in options ? options.headLength : 10;
		if (count >= 2 || count <= -2) {
			this.arrows = 2;
		} else if (count === 1 || count === -1) {
			this.arrows =  count;
		}
		
	},

	toObject: function() {
		return fabric.util.object.extend(this.callSuper('toObject'), {	});
	},
	
	
	/**
	 * This method gets executed to draw link between two nodes
	 * @private
	 * @param {CanvasRenderingContext2D} ctx Context to render on
	 */
	_render: function(ctx, noTransform) {
		ctx.beginPath();

		if (noTransform) {
			//  Line coords are distances from left-top of canvas to origin of line.
			//  To render line in a path-group, we need to translate them to
			//  distances from center of path-group to center of line.
			var cp = this.getCenterPoint();
			ctx.translate(
					cp.x - this.strokeWidth / 2,
					cp.y - this.strokeWidth / 2
			);
		}

		if (!this.strokeDashArray || this.strokeDashArray && supportsLineDash) {
			// move from center (of virtual box) to its left/top corner
			// we can't assume x1, y1 is top left and x2, y2 is bottom right
			var p = this.calcLinePoints();
			var startX = p.x1+3;
			var startY = p.y1;
			var endX = p.x2+3;
			var endY = p.y2;
			ctx.moveTo( startX, startY );
			ctx.lineTo( endX, endY );
			
			
			
			var p = this.calcLinePoints();
			var p = this.calcLinePoints();
			var fromx = p.x1+3;
			var tox = p.x2+3;
			var fromy = p.y1;
			var toy = p.y2;
			if(this.arrows){
				var angle = Math.atan2(toy-fromy,tox-fromx);
				if(this.arrows >=2 || this.arrows <= -2 || this.arrows <= -1) {
					ctx.moveTo(fromx, fromy);
					ctx.lineTo(fromx+this.headlen*Math.cos(angle-Math.PI/6),fromy+this.headlen*Math.sin(angle-Math.PI/6));
					ctx.moveTo(fromx, fromy);
					ctx.lineTo(fromx+this.headlen*Math.cos(angle+Math.PI/6),fromy+this.headlen*Math.sin(angle+Math.PI/6));
				}
				if(this.arrows >=2 || this.arrows <= -2 ||this.arrows >= 1){
					ctx.moveTo(tox, toy);
					ctx.lineTo(tox-this.headlen*Math.cos(angle-Math.PI/6),toy-this.headlen*Math.sin(angle-Math.PI/6));
					ctx.moveTo(tox, toy);
					ctx.lineTo(tox-this.headlen*Math.cos(angle+Math.PI/6),toy-this.headlen*Math.sin(angle+Math.PI/6));
				}
			}
			
			
		}
		
		if(this.strength < 0.2) {
			ctx.strokeStyle = "#999999";
		} else if(this.strength >= 0.2 && this.strength < 0.4) {
			ctx.strokeStyle = "#777777";
		} else if(this.strength >= 0.4 && this.strength < 0.6) {
			ctx.strokeStyle = "#555555";
		} else if(this.strength >= 0.6 && this.strength < 0.8) {
			ctx.strokeStyle = "#333333";
		}  else if(this.strength >= 0.8) {
			ctx.strokeStyle = "#000000";
		}

		ctx.lineWidth = this.strokeWidth;
		//ctx.lineWidth = this.strength;
		
		// make sure setting "fill" changes color of a line
		// (by copying fillStyle to strokeStyle, since line is stroked, not filled)
		var origStrokeStyle = ctx.strokeStyle;
		//ctx.strokeStyle = this.stroke || ctx.fillStyle;
		this.stroke && this._renderStroke(ctx);
		ctx.strokeStyle = origStrokeStyle;
	},
	
	 /**
     * Recalculates line points given width and height
     * @private
     */
    calcLinePoints: function() {
      var xMult = this.x1 <= this.x2 ? -1 : 1,
          yMult = this.y1 <= this.y2 ? -1 : 1,
          x1 = (xMult * this.width * 0.5),
          y1 = (yMult * this.height * 0.5),
          x2 = (xMult * this.width * -0.5),
          y2 = (yMult * this.height * -0.5);

      return {
        x1: x1,
        x2: x2,
        y1: y1,
        y2: y2
      };
    }
});

/**
 * To set styles
 * **/
function setDimensions(){
	var graph = document.getElementById('graph');
	var canvasWrapper = document.getElementById('canvas-wrapper');
	var upperCanvas = graph.nextSibling;
	canvasWrapper.style.width = "100%";
	canvasWrapper.style.height = "100%";
		
	var canvasContainer = graph.parentNode;
	canvasContainer.style.width = "100%";
	canvasContainer.style.height = "100%";
	
	if(canvas != null){
		canvas.setWidth(canvasContainer.offsetWidth+1);
		canvas.setHeight(canvasContainer.offsetHeight+1);
		canvas.viewport.position.x = 0;
		canvas.viewport.position.y = 0;
		canvas.renderAll();
		console.log('------>'+ (canvasContainer.offsetWidth+1)+ ":" +(canvasContainer.offsetHeight+1) );
		return (canvasContainer.offsetWidth+1)+ ":" +(canvasContainer.offsetHeight+1);  
	} else {
		graph.width = canvasContainer.offsetWidth+1;
		graph.height = canvasContainer.offsetHeight+1;
		return graph.width + ":" + graph.height;
	}

	

}



/**
 * To remove duplicate elements in array and return unique one
 * **/
function arrayUnique(a) {
	for(var i=0; i<a.length; ++i) {
		for(var j=i+1; j<a.length; ++j) {
			if(a[i].nodeName === a[j].nodeName && a[i].nodeId === a[j].nodeId && a[i].nodeType === a[j].nodeType)
				a.splice(j--, 1);
		}
	}
	return a;
};

/**
 * To remove duplicate elements in array and return unique one
 * **/
function arrayUniqueLink(a) {
	for(var i=0; i<a.length; ++i) {
		for(var j=i+1; j<a.length; ++j) {
			if(a[i].linkId === a[j].linkId)
				a.splice(j--, 1);
		}
	}
	return a;
};

/**
 * Function to remove duplicate nodes in two arrays
 * **/
function removeDuplicateNodes(a, b){
	for(var i=0; i<a.length; i++){
		for(var j=0;j<b.length;j++){
			if(a[i].nodeName === b[j].nodeName && a[i].nodeId === b[j].nodeId && a[i].nodeType === b[j].nodeType){
				a.splice(i--, 1);
				if(i=-1){
					i=0;
				}
			}
		}
	}
	return a;
}

/**Function to get radius**/
function getRadius(type){
	if(type == 'S'){
		return 7;
	}
	return 5;
}

/**
 * Function to draw circle
 * **/
function drawCircle(left, top, lines, nodeId, type, i){
	nodeId = nodeId+"";
	rad = getRadius(type);
	circle[nodeId] = new fabric.Circle({
		left: left,
		top: top,
		strokeWidth: 1,
		radius: rad,
		fill: '#fff',
		stroke: '#666',
		hasControls: false,
		hasBorders: false
	});
	circle[nodeId].line = new Array();
	circle[nodeId].direction = new Array();
	circle[nodeId].nodeId = nodeId;
	circle[nodeId].nodeNo = i;
	//to associate links with node
	try{

		for(var j=0; j<lines.length; j++){
			circle[nodeId].line[j] = line[lines[j]];
			if(nodeId == circle[nodeId].line[j].get('startNodeId')){
				circle[nodeId].direction[j] = 0;
			} else {
				circle[nodeId].direction[j] = 1;
			}
		};

	} catch (e) {
		console.log(e);
	}
	canvas.add(circle[nodeId]);
}

/**
 * Function to draw rectangle
 * **/
function drawRectangle(left, top, lines, nodeId, type, i){
	nodeId = nodeId+"";
	rectangleHeight = 5 * lines.length;
	circle[nodeId] = new fabric.Rect({
		left: left,
	    top: top,
	    width: 10,
	    height: rectangleHeight,
		strokeWidth: 1,
		fill: '#fff',
		stroke: '#666',
		hasControls: false,
		hasBorders: false
	});
	circle[nodeId].line = new Array();
	circle[nodeId].direction = new Array();
	circle[nodeId].nodeId = nodeId;
	circle[nodeId].nodeNo = i;
	//to associate links with node
	try{

		for(var j=0; j<lines.length; j++){
			circle[nodeId].line[j] = line[lines[j]];
			if(nodeId == circle[nodeId].line[j].get('startNodeId')){
				circle[nodeId].direction[j] = 0;
			} else {
				circle[nodeId].direction[j] = 1;
			}
		};

	} catch (e) {
		console.log(e);
	}
	canvas.add(circle[nodeId]);
}



/**
 * Function to draw triangle
 * **/
function drawTriangle(left, top, lines, nodeId, i){
	nodeId = nodeId+"";
	circle[nodeId] = new fabric.Triangle({
		left: left,
		top: top,
		strokeWidth: 1,
		height: 15,
		width: 15,
		fill: '#fff',
		stroke: '#666',
		hasControls: false,
		hasBorders: false
	});
	circle[nodeId].line = new Array();
	circle[nodeId].direction = new Array();
	circle[nodeId].nodeId = nodeId;
	circle[nodeId].nodeNo = i;

	
	
	try{

		for(var j=0; j<lines.length; j++){
			circle[nodeId].line[j] = line[lines[j]];
			if(nodeId == circle[nodeId].line[j].get('startNodeId')){
				circle[nodeId].direction[j] = 0;
			} else {
				circle[nodeId].direction[j] = 1;
			}
		};

	} catch (e) {
		console.log(e);
	}
	canvas.add(circle[nodeId]);
}

/**
 * Function to draw triangle
 * **/
function drawLabeledRectangle(left, top, lines, nodeId, nodeName, otherValue, i){
	nodeId = nodeId+"";

	circle[nodeId] = new LabeledRect({
		height: lines.length*5,
		width: 20,
		left: left,
		top: top,
		label: nodeName,
		fill: '#fff',
		stroke: '1px' , 
		hasControls: false,
		hasBorders: false
	});

	circle[nodeId].line = new Array();
	circle[nodeId].direction = new Array();
	circle[nodeId].nodeId = nodeId;
	circle[nodeId].nodeNo = i;
	circle[nodeId].nodeName = nodeName;
	circle[nodeId].networkId = otherValue;

	try{

		for(var j=0; j<lines.length; j++){
			circle[nodeId].line[j] = line[lines[j]];
			if(nodeId == circle[nodeId].line[j].get('startNodeId')){
				circle[nodeId].direction[j] = 0;
			} else {
				circle[nodeId].direction[j] = 1;
			}
		};

	} catch (e) {
		console.log(e);
	}
	canvas.add(circle[nodeId]);
}

/**
 * function to draw line
 * **/
function drawLine(cords, i, sNodeId, eNodeId, lStrength, direction, cutLvl){
	if(networkMode == 3){
		line[i] = new CustomLine(cords, {
			fill: 'black',
			stroke: 'black',
			strokeWidth: 1,
			selectable: false,
			sId:sNodeId,
			eId:eNodeId,
			strength:lStrength,
			arrows: direction*-1,
			cutLevel : cutLvl 
		});
		canvas.add(line[i]);
	} else{
		line[i] = new CustomStraightLine(cords, {
			fill: 'black',
			stroke: 'black',
			strokeWidth: 1,
			selectable: false,
			sId:sNodeId,
			eId:eNodeId,
			strength:lStrength,
			arrows: direction * -1
		});
		canvas.add(line[i]);
	}
	
};


/**
 * function to drag graph -- start here
 * **/
function drawGraph(nList,lList, $wnd, startNodeId, nwId, nwMode){
	if(canvas == null){
		canvas = new fabric.CanvasWithViewport('graph');
		canvas.isGrabMode = true;
		canvas.isNodeEdit = false;
		canvas.setCoords = function(){
			x = this.viewport.position.x;
			y = this.viewport.position.y;
		};

	}
	canvas.positionchanged = function(){
		try{
			if(Math.abs( x - this.viewport.position.x ) != 0 || Math.abs( y - this.viewport.position.y ) != 0 ){
				window.changeviewport(this.viewport.position.x, this.viewport.position.y);
			}
		} catch(e){
			console.log(e);
		}
	};
	if(networkId != nwId || nwMode != networkMode){
		nodeList = nList;
		linkList = lList;
		networkId = nwId;
		networkMode = nwMode;
		circle = {};
		line = new Array();
		window = $wnd;
		startNode = startNodeId;
		canvas.clear();
		drawElements(lList, nList);
	} else {
		nodeList = nList;
		linkList = lList;
		drawElements(lList, nList);
	}
	canvas.selection = false;
	return canvas;
};

/**
 * Method to draw lines recursively
 * **/
function drawLines(lList, i, nList){
	window.setProgress(currentProgress++, totalProgress);
	if(i<lList.length){
		var fromNX, fromNY, toNX, toNY;
		fromNX = lList[i].startX + 3;
		fromNY = lList[i].startY + 3;
		toNX = lList[i].endX + 3;
		toNY = lList[i].endY + 3;
		drawLine([ lList[i].startX+3, lList[i].startY+3, lList[i].endX+3, lList[i].endY+3 ], lList[i].linkId, lList[i].startNodeId, lList[i].endNodeId, lList[i].linkStrength, lList[i].direction, lList[i].cutLevel);
		setTimeout(function(){
			drawLines(lList, i+1, nList);
		},1);
	} else{
		drawCircles(nList,0);
	}
}

/**
 * Method to drawcircles recursively
 * **/
function drawCircles(nList, i){

	if(i<nList.length){
		var nodeId = nList[i].nodeId+"";
		if(networkMode == 3){
			if(nList[i].nodeType == 'T'){
				drawTriangle(nList[i].xValue, nList[i].yValue, nList[i].linkid, nList[i].nodeId, i);
			} else if(nList[i].nodeType == 'S'){
				drawRectangle(nList[i].xValue, nList[i].yValue, nList[i].linkid, nList[i].nodeId, nList[i].nodeType,  i);
			} else if(nList[i].nodeType == 'X'){
				drawLabeledRectangle(nList[i].xValue, nList[i].yValue, nList[i].linkid, nList[i].nodeId, nList[i].nodeName, nList[i].otherValue, i);
			} else {
				drawCircle(nList[i].xValue, nList[i].yValue, nList[i].linkid, nList[i].nodeId, nList[i].nodeType,  i);
			}
		} else {
			if(nList[i].nodeType == 'T'){
				drawTriangle(nList[i].xValue, nList[i].yValue, nList[i].linkid, nList[i].nodeId, i);
			} else if(nList[i].nodeType == 'X'){
				drawLabeledRectangle(nList[i].xValue, nList[i].yValue, nList[i].linkid, nList[i].nodeId, nList[i].nodeName, nList[i].otherValue, i);
			} else {
				drawCircle(nList[i].xValue, nList[i].yValue, nList[i].linkid, nList[i].nodeId, nList[i].nodeType,  i);
			}
		}
		
		if(nList[i].nodeType == 'C'){
			circle[nodeId].set('fill','#A80034');
		} else if(nList[i].nodeType == 'V'){
			circle[nodeId].set('fill', '#346E1A');
		} else if(nList[i].nodeType == 'S'){
			circle[nodeId].set('fill', '#1F2B6F');
		} else if(nList[i].nodeType == 'T'){
			circle[nodeId].set('fill', '#A0937B');
		} else if(nList[i].nodeType == 'X') {
			circle[nodeId].set('fill', '#674C47');
		}
		if(nList[i].linkid.length == 0){
			circle[nodeId].setOpacity(0.3);
		}
			
		if(circle[nodeId].nodeId == startNode){
			circle[nodeId].set({'lockMovementX':true, 'lockMovementY':true});
		}
		if(canvas.isGrabMode == false && canvas.isNodeEdit == true ){
			circle[nodeId].set({'lockMovementX':true, 'lockMovementY':true});
		}
		window.setProgress(currentProgress++, totalProgress);
		setTimeout(function(){
			drawCircles(nList, i+1);
		}, 1);

	} else {
		canvas.renderAll();
		ubindHandlers();
		initializeHandndlers();
		window.hideProgressBar();
	}
}

/**
 * Method for drawing elements in graph.
 * **/
function drawElements(lList, nList){
	window.clearProgress();
	window.showProgressBar();
	canvas.clear();
	totalProgress = lList.length+nList.length;
	currentProgress = 0;
	drawLines(lList, 0, nList);
};

/**
 * function to initialize handlers
 * **/
function initializeHandndlers(){
	canvas.on('object:moving', function(e) {
		var p = e.target;
		if(p.type == 'circle' || p.type == 'triangle'  || p.type == 'labeledRect'){
			window.hidePopup();
			for(var i=0; i<p.line.length; i++){
				if(p.direction[i] == 0){
					p.line[i] && p.line[i].set({ 'x1': Math.round(p.left+3) , 'y1': Math.round(p.top+3)  });
				}
				else if(p.direction[i] == 1){
					p.line[i] && p.line[i].set({ 'x2': Math.round(p.left+3), 'y2': Math.round(p.top+3)  });
				}
			};
		}
	});
	canvas.on({'mouse:up':function(e){
		var p= e.target;
		if(p.type == 'circle' || p.type == 'triangle' || p.type == 'rect'){
			if(p!= null && canvas.isNodeEdit == true){
				window.generatePopup(p.nodeId+'::'+p.nodeNo+'::'+Math.round(e.e.clientX+this.viewport.position.x)+'::'+Math.round(e.e.clientY+this.viewport.position.y));
			}
			if(p!= null && canvas.isNodeEdit == false && canvas.isNodeEdit==false && p.nodeId == startNode){
				alert('Start node cannot be moved');
			}
		} else if( p.type == 'labeledRect') {
			window.gotoNetwork(p.networkId);
		} else {}
	}});
	canvas.on({'mouse:down':function(e){
		
	}});
	canvas.on('mouse:over', function(e){
		if(e.target.type == 'circle' || e.target.type == 'triangle' || e.target.type == 'rect' ||   e.target.type == 'labeledRect'){
			window.showPopup(e.target.nodeId+'::'+Math.round(e.target.left+this.viewport.position.x)+'::'+Math.round(e.target.top+this.viewport.position.y));
		} 
	});
	canvas.on('mouse:out',function(e){
		if(e.target.type == 'circle' || e.target.type == 'triangle' ||  e.target.type == 'rect' ||   e.target.type == 'labeledRect'){
			window.hidePopup();
		}
	});
}

/**
 * Function to remove previous handlers
 * **/
function ubindHandlers(){
	canvas.off('mouse:over');
	canvas.off('mouse:up');
	canvas.off('mouse:out');
	canvas.off('object:moving');
	canvas.off('contextmenu');
	
}

/**
 * function to enable dragging view-port
 * **/
function enableDragViewport(){
	canvas.isGrabMode = true;
	canvas.isNodeEdit = false;
	state = 1;
};

/**
 * Function to clear canvas
 * **/
function clearCanvas(){
	if(canvas != null){
		canvas.clear();
	}
}

/**
 * function to enable dragging nodes
 * **/
function enableNodeDrag(){
	canvas.isGrabMode = false;
	state = 2;
	if(isNodeDraggable){
		for(var idNode in circle){
			circle[idNode].set({'lockMovementX':false, 'lockMovementY':false});
			if(circle[idNode].nodeId == startNode){
				circle[idNode].set({'lockMovementX':true, 'lockMovementY':true});
			}
		}
	}
	else{
		alert("You cannot drag nodes in this algorithm.");
		canvas.isGrabMode = true;
	}
	canvas.isNodeEdit = false;
};

/**
 * function to set edit node true
 * **/
function enableEditNode(){
	canvas.isGrabMode = false;
	state = 3;
	canvas.isNodeEdit = true;

	for(var idNode in circle){
		circle[idNode].set({'lockMovementX':true, 'lockMovementY':true});
	};
	window.saveNodes();
}

/**
 * function to change node position to its original state
 * **/
function repositionNode(xValue, yValue, nodeId){
	circle[nodeId].set({'left': xValue, 'top':yValue});
	circle[nodeId].setCoords();
	for(var i=0; i<circle[nodeId].line.length; i++){
		if(circle[nodeId].direction[i] == 0)
			circle[nodeId].line[i] && circle[nodeId].line[i].set({ 'x1': Math.round(circle[nodeId].left+3), 'y1': Math.round(circle[nodeId].top+3)  });
		else
			circle[nodeId].line[i] && circle[nodeId].line[i].set({ 'x2': Math.round(circle[nodeId].left+3), 'y2': Math.round(circle[nodeId].top+3)  });
	};
	canvas.renderAll();
}

/**
 * Functions to allow or block node drag feature basing on algorithm
 * **/
function allowNodeDrag(){
	isNodeDraggable = true;
	try{
		canvas.isGrabMode = false;
		if ( state == 1){
			enableDragViewport();
		} else if(state == 3){
			enableEditNode();
		} else if (state == 2){
			enableNodeDrag();
		}
	} catch(e) {

	}

}

/**
 * Function to block node drag
 * **/
function blockNodeDrag(){
	isNodeDraggable = false;
	try{
		canvas.isGrabMode = false;

		if ( state == 1 || state == 2){
			for(var idNode in circle){
				circle[idNode].set({'lockMovementX':true, 'lockMovementY':true});
			};
		} else if (state == 3){
			enableEditNode();
		}
	} catch(e) {

	}
}

/**
 * Function to set canvas null
 * **/
function setCanvasNull(){
	nodeList = new Array();
	linkList = new Array();
	circle = new Array();
	line = new Array();
	canvas.clear();
}

/**
 * Function to reset position of viewport
 * **/
function resetViewportPosition(){ 
	if(canvas!=null){
		canvas.viewport.position = new fabric.Point(0, 0);
	}
}

/**Function to set viewport positions to x,y
 * @Param x XValue of viewport
 * @Param y YValue of viewport
 * **/
function setViewportPosition(x,y){
	if(canvas!=null){
		canvas.viewport.position.setXY(x,y);
		canvas.viewport.position.x = x;
		canvas.viewport.position.y = y;
		canvas.renderAll();
	}
}
