<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>Rate My Outfit</title>


<link href="css/main.css" rel="stylesheet" />


<!-- bootstrap v3.3.6 -->
<script src="/webjars/jquery/jquery.min.js"></script>
<link href="boostrap/bootstrap.css" rel="stylesheet" />
<link href="boostrap/bootstrap-theme.css" rel="stylesheet" />
<script src="boostrap/bootstrap.js"></script>

<!-- <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet"> -->
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>

<!-- lightbox -->
<link rel='stylesheet prefetch' href='https://fonts.googleapis.com/css?family=Open+Sans:600'>
<script src="js/jquery.lightbox_me.js"></script>

<!-- login form -->
<link href="css/login_form.css" rel="stylesheet" />

<script src="js/main.js"></script>

</head>
<body class="bg">
	<!-- 標題 -->
	<div class="row no-gutter" style="border-bottom: 1px solid #c0c0c0; width: 100%;"> <!-- width: 100% 讓螢幕寬度符合螢幕大小 -->
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 center">
			<h3>          </h3>
<!-- 			<h3>Rate My Outfit</h3> -->
		</div>
	</div>

	
	<div class="row" style="width: 100%;"> <!-- width: 100% 讓螢幕寬度符合螢幕大小 -->
		<!-- 覺得帥/美，想挑戰 -->
		<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 col-xs-3 panel panel-info nopadding"
			 >
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 panel-heading center nopadding">
				<h3>覺得帥/美，想挑戰</h3>
			</div>
<!-- 			<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1"></div> -->
<!-- 			<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2"></div> -->
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 panel-body"
				 >
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container">
					<form class="form-horizontal">
						<div class="form-group">
<!-- 							<label class="control-label col-xs-4 col-sm-4 col-md-4 col-lg-4" for="username">使用者:</label> -->
							<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8">
								<span class="form-control-static">使用者: </span>
								<span id="username" class="form-control-static">尚未登入</span>
							</div>
						</div>
<!-- 						<div class="form-group"> -->
<!-- 							<label class="control-label col-sm-2" for="pwd">Password:</label> -->
<!-- 							<div class="col-sm-10"> -->
<!-- 								<input type="password" class="form-control" id="pwd" -->
<!-- 									placeholder="Enter password" name="pwd"> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<div class="col-sm-offset-2 col-sm-10"> -->
<!-- 								<button type="submit" class="btn btn-default">Submit</button> -->
<!-- 							</div> -->
<!-- 						</div> -->
					</form>
<!-- 					<b>使用者</b>: <span id="username">尚未登入</span><br> -->
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container">	
					<form id="formUploadFile" method="POST" enctype="multipart/form-data" action="/uploadFile">
						<table>
							<tr><b>上傳圖片</b>:</tr>
							<tr><td></td>
								<td>
<!-- 										<label for="upload-photo">Browse...</label> -->
									<input type="file" name="file" id="upload-photo"/>
								</td>
							</tr>
<!-- 								<button class="btn btn-primary btn-sm" id="sendToRoom" onclick="sendtoRoom();">SEND</button> -->
							<tr> <td></td><td><input type="submit" class="btn btn-info" value="確定上傳"></td></tr>
<!-- 								<tr> <td></td><td><input type="submit" value="Upload" /></td></tr> -->
						</table>
					</form>
				</div>		
<!-- 				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6"> -->
<!-- 					<button class="btn btn-primary btn-sm" id="openChat" onclick="Login();">openChat</button> -->
<!-- 				</div>						 -->
<!-- 				<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6"> -->
<!-- 					<button class="btn btn-primary btn-sm" id="closeChat" onclick="Logout();">closeChat</button> -->
<!-- 				</div>	 -->
			</div>	
		</div>
		<!-- 鑑賞區 -->
		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 center" id="chatDialogue">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container panel panel-info center nopadding ">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 panel-heading nopadding">
					<h3>鑑賞區</h3>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container" style="padding-top: 20px;">
					<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
						平均分數:
						<span id="rateResult" class="input-xlarge uneditable-input">99.1</span>
					</div>						
					<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
						總評分人數:
						<span id="rateNum" class="input-xlarge uneditable-input">2</span>
					</div>						
<!-- 					<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2"> -->
<!-- 						<button class="btn btn-primary btn-sm" id="sendToRoom" onclick="sendtoRoom();">SEND</button> -->
<!-- 					</div>						 -->
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 panel-body left chatmaxHeight" id="ratingArea">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container center" style="padding-top: 20px;">
<!-- 						<img alt="" src="pic02.png"> -->
<%-- 						<span class="input-xlarge uneditable-input">"${files}"</span> --%>
						<img id="mainPic" src="" height="240">
<%-- 						<c:forEach var="item" items="${files}"> --%>
<%-- 							Access here item if needed <c:out value="${item}"/> --%>
<%-- 							<img id="innerMainPic" src="${item}"> --%>
<%-- 						</c:forEach> --%>
					</div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 container center" style="padding-top: 20px;">
						<form id='formRatingHistory' name='formName' action="/">
					        <input name="triggerRatingHistoryBroadcast" type="image"  src="img/mirror.png" border="0" alt="Submit" width="84" height="84" value="20" wordsToShow="有人送你一面鏡子"></button>
					        <input name="triggerRatingHistoryBroadcast" type="image"  src="img/ghost.png" border="0" alt="Submit" width="84" height="84" value="40" wordsToShow="看到鬼"></button>
					        <input name="triggerRatingHistoryBroadcast" type="image"  src="img/misery.png" border="0" alt="Submit" width="84" height="84" value="60" wordsToShow="慘"></button>
					        <input name="triggerRatingHistoryBroadcast" type="image"  src="img/nerdy.png" border="0" alt="Submit" width="84" height="84" value="80" wordsToShow="宅"></button>
					        <input name="triggerRatingHistoryBroadcast" type="image"  src="img/good.png" border="0" alt="Submit" width="84" height="84" value="100" wordsToShow="讚"></button>
					        
					        <!-- test -->
<!-- 					        <input id="initTest" type="button" value="init"></button> -->
<!-- 					        <input type='hidden' id='giveRatingResult02' name='giveRatingResult' value='可以去死一死了'> -->
<!-- 					        <input type="image" name="submit" src="go_die.png" border="0" alt="Submit" width="84" height="84"/> -->
						</form> 
<!-- 						<form id='formName' name='formName' action="/giveRating"> -->
<!-- 					        <input type='hidden' id='giveRatingResult02' name='giveRatingResult' value='看見鬼'> -->
<!-- 					        <input type="image" name="submit" src="ghost.png" border="0" alt="Submit" width="84" height="84"/> -->
<!-- 						</form>  -->
<!-- 						<form id='formName' name='formName' action="/giveRating"> -->
<!-- 					        <input type='hidden' id='giveRatingResult02' name='giveRatingResult' value='慘'> -->
<!-- 					        <input type="image" name="submit" src="misery.png" border="0" alt="Submit" width="84" height="84"/> -->
<!-- 						</form> -->
<!-- 						<form id='formName' name='formName' action="/giveRating"> -->
<!-- 					        <input type='hidden' id='giveRatingResult02' name='giveRatingResult' value='宅'> -->
<!-- 					        <input type="image" name="submit" src="nerdy.png" border="0" alt="Submit" width="84" height="84"/> -->
<!-- 						</form>  -->
<!-- 						<form id='formName' name='formName' action="/giveRating"> -->
<!-- 					        <input type='hidden' id='giveRatingResult01' name='giveRatingResult' value='讚'> -->
<!-- 					        <input type="image" name="submit" src="good.png" border="0" alt="Submit" width="84" height="84"/> -->
<!-- 						</form> 						 -->
						
						
<!-- 						<form method="POST" action="/giveRating"> -->
<!-- 							<table> -->
<!-- 								<tr><td>File to upload:</td><td><input type="file" name="file" /></td></tr> -->
<!-- 								<tr><td></td><td><input type="submit" value="Upload" /></td></tr> -->
<!-- 							</table> -->
<!-- 						</form> -->
					</div>
				</div>
			</div> <!-- end of container -->
		</div>
<!-- 		<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 center" id="chatDialogueReverse"></div> -->

		<!-- 評分紀錄 -->
		<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 panel panel-info center nopadding">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 panel-heading nopadding">
			<h3>評分紀錄</h3>
			</div>
			<div class="pre-scrollable col-xs-12 col-sm-12 col-md-12 col-lg-12 panel-body left logmaxHeight" id="historyRatingResult">
<!-- 				Here -->
				<c:forEach var="ratingHistory" items="${ratingHistoryList}">
<%-- 					Access here item if needed <c:out value="${ratingHistory}"/> --%>
					<c:out value="${ratingHistory}"/><br>
<%-- 					<img src="${item}"> --%>
				</c:forEach>				
			</div>
		</div>
	</div> <!-- end of row -->
	
	
<!-- 	    <div class="form-group"> -->
<!-- 	        <label for="connect">WebSocket connection:</label> -->
<!-- 	        <button id="login" class="btn btn-default" type="submit">login</button> -->
<!-- 	        <button id="Guest Login" class="btn btn-default" type="submit">Guest Login -->
<!-- 	        </button> -->
<!-- 	    </div> -->
		<div id="loginDiv" class="login-wrap form-group" style="display: none;">
			<div class="login-html">
				<input id="tab-1" type="radio" name="tab" class="sign-in" checked><label
					for="tab-1" class="tab">Sign In</label> <input id="tab-2"
					type="radio" name="tab" class="sign-up"><label for="tab-2"
					class="tab">Sign Up</label>
				<div class="login-form">
					<form method="post" id="loginForm">
					<div class="sign-in-htm">
						<div class="group">
							<label for="loginAccount" class="label">Account</label> <input id="loginAccount"
								type="text" class="input">
						</div>
						<div class="group">
							<label for="loginPwd" class="label">Password</label> <input id="loginPwd"
								type="password" class="input" data-type="password">
						</div>
						<div class="group">
							<input id="check" type="checkbox" class="check" checked>
							<label for="check"><span class="icon"></span> Keep me
								Signed in</label>
						</div>
						<div class="group">
							<input id="Singin" type="submit" class="button" value="Sign In">
						</div>
						<div class="group">
							<input id="guestSignin" type="submit" class="button" value="Guest Sign In">
						</div>
						<div class="group">
							<ul id="errorMsgList">
							</ul>
<!-- 							<input id="errorMsgs" type="errorMsgs" name="errorMsgs"> -->
						</div>
						<div class="hr"></div>
						<div class="foot-lnk">
							<a href="#forgot">Forgot Password?</a>
						</div>
					</div>
					</form>
					<form method="post" id="signupForm">
					<div class="sign-up-htm">
						<div class="group">
							<label for="account" class="label">Account</label> <input id="account"
								type="text" class="input">
						</div>
						<div class="group">
							<label for="pass" class="label">Password</label> <input id="pass"
								type="password" class="input" data-type="password">
						</div>
						<div class="group">
							<label for="pass" class="label">Repeat Password</label> <input
								id="pass" type="password" class="input" data-type="password">
						</div>
						<div class="group">
							<label for="pass" class="label">Email Address</label> <input
								id="pass" type="text" class="input">
						</div>
						<div class="group">
							<input id="singup" type="submit" class="button" value="Sign Up">
						</div>
						<div class="hr"></div>
						<div class="foot-lnk">
							<label for="tab-1">Already Member?</label>
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>	
	
</body>

</html>
