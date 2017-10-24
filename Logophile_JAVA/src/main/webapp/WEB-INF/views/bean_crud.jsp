<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
<html lang="en">
<head>

<!-- add space vertically -->
<style>
.spacer5 { height: 5px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer10 { height: 10px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer15 { height: 15px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer20 { height: 20px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer25 { height: 25px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer30 { height: 30px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer35 { height: 35px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer40 { height: 40px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer45 { height: 45px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer50 { height: 50px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer100 { height: 100px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
.spacer200 { height: 200px; width: 100%; font-size: 0; margin: 0; padding: 0; border: 0; display: block; }
</style>

<!-- bootstrap custom css -->
<style type="text/css">
.center {
	margin: auto;
	text-align: center;
}

.left {
	margin: auto;
	text-align: left;
}


.nopadding {
   padding: 0 !important;
   margin: 0 !important;
}

.nopadding-horizontal{
    padding-right: 0 !important;
    padding-left: 0 !important;	
}

</style>

<!-- jquery -->
<script
  src="https://code.jquery.com/jquery-3.2.1.min.js"
  integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
  crossorigin="anonymous">
</script>
<script type="text/javascript" src="js/jquery.serializejson.js"></script> <!-- must be put after jquery -->

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<link href="css/info360.css" rel="stylesheet">
<!-- <link href="resources/css/info360.css" rel="stylesheet"> -->

<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">

<link href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.5.3/css/bulma.css" rel="stylesheet">

<script src="https://unpkg.com/vue"></script>


</head>
<body>
	<div class="form-group col-sm-12">
	
		<div id="root">
			<div class="form-group col-sm-12 box center">
				<h1 class="title">CRUD 後端頁面 </h1>
			</div>
			<!-- 請求動作 -->
			<action v-bind:my_parent="rootObj">tblInteraction_agentToAgent</action>
<!-- 						<action action_name="tblinteraction_calllogtag" v-bind:my_parent="rootObj">準備就緒</action> -->
<!-- 						<action action_name="updatestatus_notready" v-bind:my_parent="rootObj">離席</action> -->
			<div class="spacer10"></div>
		</div> <!-- end of "root" div -->  
		
	</div>
</body>


<!-- JS -->
<script>
// 	var url_g = "${RESTful_protocol}://${RESTful_hostname}:${RESTful_port}/${RESTful_project}";
	var url_g = "http://localhost:8089";
	console.log("url_g: " + url_g);
	
	var sql = JSON.parse('{}');
	sql.tblInteraction_agentToAgent = '{"fromUserID":"301", "toUserID":"308", "msg":"hey yo", "date":"2017-09-30 18:00:12.000", "entityTypeID":"1", "tenantID":"3"}';
// 	'{"type":"message","userID":103,"text":"yo","sendto":"101","channel":"chat"}';
	
</script>

<!-- Vue -->
<script>
	Vue.component('action',{
		template:
// 			'<button @click="sendReq"><slot></slot></button>',
			`
			<div class="col-sm-12 panel panel-default">
				<div class="col-sm-1 panel panel-default nopadding">
				<select class="form-control" id="beanSelector" v-on:change="createForm">
			        <option>1</option>
			        <option>2</option>
			        <option>3</option>
			        <option>4</option>
		      	</select>					

				</div>			
				<div class="col-sm-5 panel panel-default">
					<form name="newform" action="" method="post"  class="form-horizontal input-prepend" id="newform">
						<div class="form-group">
						    <label class="control-label" for="test1">test4</label>
						    <input type="text" name="test4" class="form-control" id="test4" placeholder="">
						</div>
		
						<div class="form-group">
						    <label class="control-label" for="test5">test5</label>
						    <input type="text" name="test5" class="form-control" id="test5" placeholder="">
						</div>
					</form>
				</div>			
				<div class="col-sm-1 panel panel-default">
					<button @click="query" type="button" class="btn info360-btn-default" data-dismiss="modal"> QUERY </button>
					<button @click="insert" type="button" class="btn info360-btn-default" data-dismiss="modal"> INSERT </button>
					<button @click="update" type="button" class="btn info360-btn-default" data-dismiss="modal"> UPDATE </button>
					<button @click="deleteBean" type="button" class="btn info360-btn-default" data-dismiss="modal"> DELETE </button>
				</div>			
				<div class="col-sm-5 panel panel-default">
					<span>result</span>
				</div>			
			</div>
			`
		,props: {
			my_parent: {}
		}
	    ,data: function(){
	    	return {
	    		formParams : '{"dbid":"1"}'
	    		,response: '{}'
	    		,beanFieldsMap: '{"beanName":["f01","f02"]}'
	    		,currBeanName: ''
// 	    		,currQueryStr: 'key01=val01&key02=val02'
	//     		,coupon_code : ''
	    	}
	    }	
		,mounted:function(){
			console.log("this.my_parent.userID_agent: " + this.my_parent.userID_agent); // debug
			this.formParams = sql[this.action_name];
			
			// update beanFieldsMap
			this.updateBeanFieldsMap();
			
		}
		,methods: {
			query: function(){
				console.log("query");
				this.sendReq("query");
			},
			insert: function(){
				console.log("insert");
				this.sendReq("insert");
			},
			update: function(){
				console.log("update");
				this.sendReq("update");
			},
			deleteBean: function(){
				console.log("delete");
				this.sendReq("delete");
			},
			sendReq: function(action){
				
				// prepare queryString form query
// 				var queryStringJSON = $('#newform').serializeJSON();
				var queryStringJSON =$('#newform').find(":input").filter(function () {
											console.log("this.value: " , this.value);
											return $.trim(this.value).length > 0
										}).serializeJSON();
				console.log('queryStringJSON: ' , queryStringJSON);
				// ref: https://stackoverflow.com/questions/33559285/jquery-serialize-sends-default-values (warning: when user change the input value, html page will not change its default value for that input element)
				var queryString = $('#newform').find(":input").filter(function () {
										console.log("this.value: " , this.value);
										return $.trim(this.value).length > 0
									}).serialize(); // if value is empty string, then skip it
				console.log('queryString: ' , queryString);
				
				var url = url_g + '/' + 'crud' + '/' + this.currBeanName;
				if ('query' == action){
					if (queryString) url += '?'+ queryString;
					var promise = $.get(url, function(data) {
						console.log("success! " , data);
						this.response = "" + JSON.stringify(data);
					}.bind(this), "json");
				}else if ('insert' == action){
					var promise = $.post(url, queryStringJSON, function(data) {
						console.log("success! " , data);
						this.response = "" + JSON.stringify(data);
					}.bind(this), "json");					
				}else if ('update' == action){
					
				}else if ('delete' == action){
					// exception
					url = url_g + '/' + 'crud' + '/' + 'delete' + '/' + this.currBeanName;
					
				}
				
				console.log("url: " , url);
				console.log("this.currQueryStr: " , this.currQueryStr);
// 				var promise = $.post(url, this.formParams);

				// Assign handlers immediately after making the request,
				// and remember the jqxhr object for this request
// 				var jqxhr = $.post( "example.php", function() {
// 				  alert( "success" );
// 				})
				promise.done(function(data) {
					console.log( "second success: ", data );
				})
				promise.fail(function(data) {
					console.log( "error: ", data );
				})
				promise.always(function(data) {
					console.log( "finished: ", data );
				});
				 
				// Perform other work here ...
				 
				// Set another completion function for the request above
// 				jqxhr.always(function() {
// 				  alert( "second finished" );
// 				});
        	}
			,updateBeanFieldsMap: function(){
				console.log("updateBeanFieldsMap method called");
				var url = url_g + "/" + "getBeanFieldsMap";
				console.log("url: " , url);
				// 先到這邊
				var promise = $.get(url, function(data) {
					console.log("success! " , data);
					
					// update beanFieldsMap
// 					this.beanFieldsMap = "" + JSON.stringify(data);
					this.beanFieldsMap = data;
					
					// update page - bean select list 
					var Str="<option selected value='default' disabled>請選擇</option>";
					jQuery.each( this.beanFieldsMap, function( key, val ) {
						console.log("key: " , key, " val: " , val);
						Str+="<option value='" + key + "'>" + key + "</option>";
					});
					$("#beanSelector").html(Str);
					
				}.bind(this), "json");
				
				promise.done(function(data) {
					console.log( "second success: ", data );
				})
				promise.fail(function(data) {
					console.log( "error: ", data );
				})
				promise.always(function(data) {
					console.log( "finished: ", data );
				});
			}
			,createForm: function(e){
				console.log('createForm e: ' , e);
				console.log('createForm e.target.value: ' , e.target.value);
				
				// update current selected bean name
				this.currBeanName = e.target.value;
				
				// 建立 param form
				let beanParamList = this.beanFieldsMap[e.target.value];
				console.log('createForm beanParamList: ' , beanParamList);
				
				var Str = '';
				jQuery.each( beanParamList, function( index, val ) {
					Str+= '<div class="form-group">'
					console.log("index: " , index, " val: " , val);
					Str+= '<label class="control-label" for="' + val + '">' + val + '</label>'
					Str+= '<input type="text" name="' + val + '" id="' + val + '" value="" class="form-control">'
					Str+= '</div>';
				});
				console.log('Str: ' , Str);
				$("#newform").html(Str); 
				

				
				
			}
		}
	});

    var app = new Vue({
        el: '#root',
        data: function(){
        	return {
        		tenantID : '${tenantID}',
        		typeID : '${typeID}',
        		userID_agent : '${userID_agent}',
        		dialNO_agent : '${dialNO_agent}',
        		userName_agent : '${userName_agent}',
        		userID_client : '${userID_client}',
        		callID_client : '${callID_client}',
        		userName_client : '${userName_client}',
        		pilotID_client : '${pilotID_client}',
        		rootObj:this
//         		isCouponVerified : false,
//         		coupon_code : ''
        	}
        },
        methods: {

//         	onCouponVerified: function(aCouponCode){
//         		console.log("coupon was verified!");
//         		console.log("aCouponCode: ", aCouponCode);
//         		this.coupon_code = aCouponCode;
//         		this.isCouponVerified = true;
//         	}
        }
    });
  </script>
</html>
