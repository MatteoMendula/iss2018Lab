/*
 * -----------------------------------------------
 * it.unibo.mbot2018/nodeCode/robotFrontend/appServer/routes/routesGenerator.js
 * -----------------------------------------------
*/
var express = require('express'),
  router    = express.Router();
 
exports.create = function (model) {
//Extend the model with data
//  createDefaultData(model.links.robot.resources);
//  createDefaultData(model.links.robotenv.resources);

  // Let's create the routes
  createRootRoute(model);
  createModelRoutes(model);
//  createPropertiesRoutes(model);
//  createActionsRoutes(model);

  return router;
};

function createDefaultData(resources) {
	Object.keys(resources).forEach(function (resKey) {
	    var resource  = resources[resKey];
	    resource.data = [];
	});
}

function createRootRoute(model) {
	  router.route('/root').get(function (req, res, next) {

	    req.model = model;
	    req.type = 'root';

	    var fields = ['id', 'name', 'description', 'customFields', 'help'];
	    req.result = utils.extractFields(fields, model);

	    type = 'http://model.webofthings.io/';

	    res.links({
	        model: '/model/',
	        properties: '/properties/',
	        actions: '/actions/',
	        help: '/help/',
	        type: type
	    });

	    next();
	  });
};

function createModelRoutes(model) {
	  // GET /showmodel
	  router.route('/showmodel').get(function (req, res, next) {
	    req.result    = model;
	    req.model     = model;
	    req.modelStr  = "\""+JSON.stringify(model, null, 2)+"\"";
	    req.type   = 'showmodel';
	    type = 'http://model.webofthings.io/';
	    res.links({
	      type: type
	    });

	    next();
	  });
};