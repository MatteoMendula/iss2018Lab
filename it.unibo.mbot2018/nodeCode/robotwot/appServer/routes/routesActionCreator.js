var express = require('express'),
  router = express.Router();
//  	 = require('node-uuid'),
  utils = require('./../utils/utils');

exports.create = function (model) {
	  console.log("create  "   ); //AN
 	  createDefaultData(model.links.properties.resources);
	  createDefaultData(model.links.actions.resources);

	  // Let's create the routes
 	  createRootRoute(model);
 	  createModelRoutes(model);
 	  createPropertiesRoutes(model);
	  createActionsRoutes(model);

	  return router;
}; //create



function createRootRoute(model) {
	  router.route('/').get(function (req, res, next) {

	    req.model = model;
	    req.type = 'root';

	    var fields = ['id', 'name', 'description', 'tags', 'customFields'];
	    req.myresult = utils.extractFields(fields, model);

	    if (model['@context']) type = model['@context'];
	    else type = 'http://model.webofthings.io/';

	    res.links({
	      model: '/model/',
	      properties: '/properties/',
	      actions: '/actions/',
	      things: '/things/',
	      help: '/help/',
	      ui: '/',
	      type: type
	    });

	    next();
	  });
	};

	function createModelRoutes(model) {
		  // GET /model
		  router.route('/model').get(function (req, res, next) {
		    req.myresult = model;
		    req.model = model;

		    if (model['@context']) type = model['@context'];
		    else type = 'http://model.webofthings.io/';
		    res.links({
		      type: type
		    });

		    next();
		  });
		};

	function createPropertiesRoutes(model) {
		  var properties = model.links.properties;

		  // GET /properties
		  router.route(properties.link).get(function (req, res, next) {
		    req.model    = model;
		    req.type     = 'properties';
		    req.entityId = 'properties';

		    req.myresult = utils.modelToResources(properties.resources, true);

		    // Generate the Link headers 
		    if (properties['@context']) type = properties['@context'];
		    else type = 'http://model.webofthings.io/#properties-resource';

		    res.links({
		      type: type
		    });

		    next();
		  });

		  // GET /properties/{id}
		  router.route(properties.link + '/:id').get(function (req, res, next) {
		    req.model         = model;
		    req.propertyModel = properties.resources[req.params.id];
		    req.type          = 'property';
		    req.entityId      = req.params.id;

		    req.myresult        = reverseResults(properties.resources[req.params.id].data);

		    // Generate the Link headers 
		    if (properties.resources[req.params.id]['@context']) type = properties.resources[req.params.id]['@context'];
		    else type = 'http://model.webofthings.io/#properties-resource';

		    res.links({
		      type: type
		    });

		    next();
		  });
		};
function createDefaultData(resources) {
	  Object.keys(resources).forEach(function (resKey) {
	    var resource  = resources[resKey];
	    resource.data = [];
	  });
}//createDefaultData

function reverseResults(array) {
	  return array.slice(0).reverse();
}

createActionsRoutes = function (model) {
  var actions = model.links.actions;
  console.log( "createActionsRoutes " + model );

  // GET /actions
  router.route(actions.link).get(function (req, res, next) {
    req.myresult = utils.modelToResources(actions.resources, true);
    
    req.model = model;
    req.type = 'actions';
    req.entityId = 'actions';

    if (actions['@context']) type = actions['@context'];
    else type = 'http://model.webofthings.io/#actions-resource';

    res.links({
      type: type
    });

    next();
  });

  // POST /actions/{actionType}
  router.route(actions.link + '/:actionType').post(function (req, res, next) {
    console.log( "actions POST " + req.originalUrl ); //AN
    var action       = req.body;
    action.id        = "todo"; 		//uuid.v1();
    action.status    = "pending";
    action.timestamp = utils.isoTimestamp();
    console.log("createActionsRoutes action=" + action); //AN
    utils.cappedPush(actions.resources[req.params.actionType].data, action);
    res.location(req.originalUrl + '/' + action.id);
    console.log("createActionsRoutes location=" + (req.originalUrl + '/' + action.id) ); //AN
	//simulate observer plugin reaction AN
	if(req.originalUrl.indexOf( "/actions/ledState" ) >= 0 ){
		console.log("AD HOC" );
		res.send("exec the plugin");
		//res.redirect("/ledChange");
	}
    else next();
  });

  
//GET /actions/{actionType}
  router.route(actions.link + '/:actionType').get(function (req, res, next) {
    req.myresult    = reverseResults(actions.resources[req.params.actionType].data);
	console.log("route actions GET=" + req.myresult  ); //AN
    req.actionModel = actions.resources[req.params.actionType];
    req.model       = model;

    req.type        = 'action';
    req.entityId    = req.params.actionType;
    console.log("route actions GET entityId=" + req.entityId ); //AN
    if (actions.resources[req.params.actionType]['@context']) 
  	  type = actions.resources[req.params.actionType]['@context'];
    else type = 'http://model.webofthings.io/#actions-resource';

    res.links({
      type: type
    });


    next();
  });
  
  
  
  
  return router;
}


 
