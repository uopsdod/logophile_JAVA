
var stompClient = null;
var currFileName = '';
var jwtStr = null;

// 測試資料 ('_g'代表全域變數) 
var guest_account_g = "sam";
var guest_password_g = "1111";

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
//        setConnected(true);
        console.log('Connected: ' + frame);
        // subscribe 1 - 
        stompClient.subscribe('/topic/ratingHistory', function (data) {
        	console.log("connect() ratingHistory - data: " + data);
        	console.log("connect() ratingHistory - data: " , data);
        	console.log("connect() ratingHistory - data.body: " + data.body);
        	console.log("connect() ratingHistory - data.body: " , data.body);
        	
        	var dataJson = jQuery.parseJSON(data.body);
        	
        	updateRatingHistoryPage(dataJson.rateList, dataJson.rateNum, dataJson.rateResult);
        });
        
        // subscribe 2 - 
        stompClient.subscribe('/topic/fileUploaded', function (data) {
        	console.log("connect() fileUploaded - data: " + data);
        	console.log("connect() fileUploaded - data: " , data);
        	console.log("connect() fileUploaded - data.body: " + data.body);
        	console.log("connect() fileUploaded - data.body: " , data.body);
        	
        	var dataJson = jQuery.parseJSON(data.body);
        	console.log("connect() fileUploaded - dataJson: " , dataJson);
        	console.log("connect() fileUploaded - dataJson.picUrl: " , dataJson.picUrl);
        	console.log("connect() fileUploaded - dataJson.picId: " , dataJson.picId);
        	if (dataJson.picUrl != undefined){
        		updateProfilePage(dataJson.picUrl, dataJson.picId);
        	}
        });
        
        // 檢查是否有要更新資訊  (觸發init)
        triggerInit();
        
    });
    
    
}

function triggerInit() {
	console.log("triggerInit");
	stompClient.send("/app/triggerInit", {}, JSON.stringify({'triggerInit': 'triggerInitText'}));
}

function triggerRatingHistoryBroadcast(aWordsToShow, aPicId, aScore) {
	
	if (aPicId == undefined){
		console.log("picId not provided");
		return;
	}
	
	var data = {
			wordsToShow : aWordsToShow,
			picId : aPicId,
			score : aScore
	}
	
    $.post("updatePic",data,
    	    function(data, status){
    	        console.log("updatePic - Data: " , data , "\nStatus: " , status);
    	    });
	
}

function updateRatingHistoryPage(ratingResultList, rateNum, rateResult){
//    var ratingHistoryList = ratingResult.split(",");
    /** 更新ratingHistory **/
	var result = "";
    for(var i in ratingResultList){
    	var ratingResultObj = ratingResultList[i];
    	var ratingResult = ratingResultObj.rateResult;
    	console.log("ratingResult: " + ratingResult);
    	result = ratingResult + "<br>" + result; // 讓最新資料在最上面
    }
	console.log("result: " + result);
	document.getElementById("historyRatingResult").innerHTML = result;	
	
	/** 更新總評分人數 **/
	$("#rateNum")[0].innerHTML = rateNum;
	
	/** 更新平均分數 **/
	$("#rateResult")[0].innerHTML = rateResult;
	
	
}

function updateProfilePage(picUrl, picId){
	console.log("updateProfilePage - currFileName: " + currFileName);
	console.log("updateProfilePage - picUrl: " + picUrl);
	console.log("updateProfilePage - picId: " + picId);
    if (currFileName != picUrl){
    	currFileName = picUrl;
    	$("#mainPic")[0].src = picUrl;
    	console.log("updateProfilePage - updated mainPic");
    	
    	/** 將picId放上頁面,以後更新使用 **/
    	$("#mainPic")[0].picId = picId;
    	
    }
}

function checkIfJWTValid(aAccount, aPassword){
	console.log("checkIfJWTValid input aAccount: " + aAccount);
	console.log("checkIfJWTValid input aPassword: " + aPassword);
    $.post("login",
            {
    		  account: aAccount,
    		  password: aPassword
            },
            function(data,status){
                console.log(data)
                console.log("checkIfJWTValid data: " , data);
                console.log("checkIfJWTValid data.errorMsgs: " , data.errorMsgs);
                console.log("checkIfJWTValid data.errorMsgs.length: " , data.errorMsgs.length);
                console.log(status);
                if ("success" == status){
                	if (data.errorMsgs.length == 0){
                		console.log("checkIfJWTValid login success");
                		// 關閉lightbox
                		$("#loginDiv").trigger('close');
                		// 連上server,建立stompClient
                		connect();
                		// 更換使用者名稱資訊
                		console.log("checkIfJWTValid data.memName: " + data.memName);
//            	    $("#username")[0].innerHtml = data.memName; // not working
                		$('#username').text( data.memName );
                	}else{
                		
//                		var countries = ['United States', 'Canada', 'Argentina', 'Armenia'];
                		var errorMsgList = $('#errorMsgList')[0];
                		$('#errorMsgList').empty(); // 先清空
                		$.each(data.errorMsgs, function(i)
                		{
                		    var li = $('<li/>')
                		        .addClass('ui-menu-item')
                		        .attr('role', 'menuitem')
                		        .appendTo(errorMsgList);
                		    var aaa = $('<p/>')
                		        .addClass('ui-all')
                		        .attr('style','color:red')
                		        .text(data.errorMsgs[i])
                		        .appendTo(li);
                		});
                		
                		// debug
                		var errorMsgLength = data.errorMsgs.length;
                		for (var i = 0; i < errorMsgLength; i++) {
                		    console.log("checkIfJWTValid " + data.errorMsgs[i]);
                		    $("#errorMsgs").val();
                		    //Do something
                		}
                	}
                }else{
                	consoel.log("Please try login again");
                	
                }
   });
}// end of checkIfJWTValid(...)

$(document).ready(function(){
	console.log("document ready");
	
	// 建立測試用資料
//	$("#account").val("sam");
//	$("#password").val("1111");
	
	/** 開啟登入lightbox **/
	if (jwtStr == undefined){
		console.log("jwtStr is empty");
		
		$("#loginDiv").lightbox_me({
	        centered: true,
	        closeClick: false, // disallow user to close this lightbox by clicking the overlay
	        closeEsc: false,
	        onLoad: function() { 
	            $('#loginDiv').find('#loginAccount').focus()
	        }
		});
	}
	
	/** 註冊監聽事件 **/
    $("#formRatingHistory").on('submit', function (e) {
        e.preventDefault();
    });
    $("#loginForm").on('submit', function (e) {
    	e.preventDefault();
    });
    $("#signupForm").on('submit', function (e) {
    	e.preventDefault();
    });
    $("#formUploadFile").on('submit', function (e) {
        e.preventDefault();
		// Create an FormData object
        // Get form
        var form = $('#formUploadFile')[0];
        var data = new FormData(form);
		// If you want to add an extra field for the FormData
//        data.append("CustomField", "This is some extra data, testing");
        
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url : $(this).attr('action') || window.location.pathname,
            data: data,
            processData: false,
            contentType: false,
            cache: false,
//            timeout: 600000,            
            success: function (data) {
            	console.log("formUploadFile data: " , data);
            },
            error: function (jXHR, textStatus, errorThrown) {
                alert(errorThrown);
            }
        });
    });
    
    // 一次設定全部onClick事件
    $("[name=triggerRatingHistoryBroadcast]").click(function() { 
    	console.log("triggerRatingHistoryBroadcast this.value: " + this.value);
    	console.log("triggerRatingHistoryBroadcast this.getAttribute('wordsToShow'): " + this.getAttribute('wordsToShow'));
    	var picId = $("#mainPic")[0].picId;
//    	console.log($( "#triggerRatingHistoryBroadcast" )[0].value);
//    	console.log($( "#triggerRatingHistoryBroadcast" ).val());
//    	var contents = $('#contents')[0];
    	triggerRatingHistoryBroadcast(this.getAttribute('wordsToShow'), picId, this.value); 
    });
    
    $("#guestSignin").click(function() {
//    	doLogin();
    	checkIfJWTValid(guest_account_g, guest_password_g);
    });
    
    $("#Singin").click(function() {
		var account = $("#loginAccount").val();
		var password = $("#loginPwd").val();
    	checkIfJWTValid(account, password);
    });

    $("#singup").click(function() {
    	alert("抱歉,功能尚在開發中!");
    });
    
});
