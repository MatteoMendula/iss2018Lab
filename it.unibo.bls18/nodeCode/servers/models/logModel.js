/*
* models/dataModel.js
*/
var mongoose = require( 'mongoose' ); //npm install --save mongoose

var dataSchema = new mongoose.Schema({
    log:      { type: String, required: true }
});
//Create a data model using the schema;
var Bls18DataLog = mongoose.model('bls18Log', dataSchema);	//collection bls18Logs;	
module.exports = Bls18DataLog;