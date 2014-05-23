<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Not only answer, but also solution!">
	<meta name="keywords" content="question,solution,troubleshooting,drill down,analyze,plan,consult">
	<meta name="author" content="Feng Sun">
   
    <title><spring:message code="header.title" text="SunSpace" /></title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

  <!-- BEGIN Pre-requisites -->
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js">  
  </script>

  <script type="text/javascript">
    (function () {
      var po = document.createElement('script');
      po.type = 'text/javascript';
      po.async = true;
      po.src = 'https://plus.google.com/js/client:plusone.js';
      var s = document.getElementsByTagName('script')[0];
      s.parentNode.insertBefore(po, s);
    })();
  </script>
  <!-- END Pre-requisites -->

</head>
 <body>  
<div class="container">



		<!-- left blank area -->
		<div class="col-md-2">
		
		</div>
		
		
		<!-- middle area for login -->
		<div class="col-md-8">
			<!-- Login Panel -->
				<div id="" class="well">

				
								<fieldset>
									<legend>
									<a href="<%=request.getContextPath()%>/">
										<spring:message code="header.sunspace" text="SunSpace" />
									</a>
									</legend>
				 
									<div class="row">
										<div id="loginPanel" class="col-xs-6">
											<form action="<%=request.getContextPath()%>/user/login" class="form-horizontal well" method="post">
											<input type="hidden" name="cid" value="${cid}">
											<input type="hidden" name="qid" value="${qid}">				
											<div class="form-group"> 
												<div class="rows">
													<div class="col-md-12">
														<div class="col-lg-12">
													
															<input class="form-control input-lg" id="email" name=
															"email" placeholder="<spring:message code="common.email" text="Email" />" type="email" required>
														</div>
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="rows">
													<div class="col-md-12">
														<div class="col-lg-12">
															<input class="form-control input-lg" id="passwd"
															name="passwd" placeholder="<spring:message code="common.pwd" text="Password" />" type=
															"password" required>
														</div>
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="rows">
													<div class="col-md-12">
														<div class="col-lg-12">
															<button class="btn btn-lg btn-success btn-lg" type="submit"><spring:message code="common.login" text="Login" /></button>
															<br>
													        <spring:message code="reg.askNoAccount" text="No Account?" /> 
													        <a id="linkSignup" href="#">
													        <spring:message code="common.signup" text="SignUp" /></a>
														</div>

													</div>
												</div>
											</div>
											</form>
										</div>
										
										
										<!-- register panel -->									
										<div id="registerPanel" class="col-xs-6">
											<form action="<%=request.getContextPath()%>/user/register" class="form-horizontal well" method="post">
												<input type="hidden" name="cid" value="${cid}">
												<input type="hidden" name="qid" value="${qid}">										
											<div class="form-group"> 
												<div class="rows">
													<div class="col-md-12">
														<div class="col-lg-12">
															<input class="form-control input-lg" id="p1" name=
															"name" placeholder="<spring:message code="common.user" text="UserName" />" type="text" required>										
					
														</div>
													</div>
												</div>
											</div>
				
											<div class="form-group"> 
												<div class="rows">
													<div class="col-md-12">
														<div class="col-lg-12">
													
															<input class="form-control input-lg" id="email" name=
															"email" placeholder="<spring:message code="common.email" text="Email" />" type="email" required>
														</div>
													</div>
												</div>
											</div>
				 
				 
											<div class="form-group">
												<div class="rows">
													<div class="col-md-12">
														<div class="col-lg-12">
															<input class="form-control input-lg" id="passwd"
															name="passwd" placeholder="<spring:message code="common.pwd" text="Password" />" type=
															"password" required>
														</div>
													</div>
												</div>
											</div>
				 
					
				 
				 
											<div class="form-group">
												<div class="rows">
													<div class="col-md-12">
														<div class="col-lg-12">
															<button class="btn btn-success btn-lg" type=
															"submit"><spring:message code="common.signup" text="SignUp" /></button>
															<br> <spring:message code="reg.askHaveAccount" text="Do you have account" />
															 <a id="linkSignin" href="#">
															<spring:message code="common.login" text="Login" /></a>
														</div>
																				
													</div>
												</div>
											</div>
											
										</form>
										</div>			<!-- end of register panel -->	
										
										<!-- login with 3rd party account -->						
										<div class="col-xs-6">
											<div class="form-group">
												<div class="rows">
													<div class="col-md-12">
														<!-- google account -->
														<div class="col-lg-12">
														<div id="signinButton">
														  <span class="g-signin"
														    data-scope="https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/plus.profile.emails.read"
														    data-clientid="902137185992-74tltkpbsqjose7e98o6mqjvuhb1beq6.apps.googleusercontent.com"
														    data-redirecturi="postmessage"
														    data-accesstype="offline"
														    data-cookiepolicy="single_host_origin"
														    data-width="wide"
														    data-callback="signInCallback">
														  </span>
														</div>
														<div id="loginresult"></div>
														</div>
														<!-- qq -->
														<span id="qqLoginBtn"></span>
														<script type="text/javascript">
														    QC.Login({
														       btnId:"qqLoginBtn"    //插入按钮的节点id
														});
														</script>

													</div>
												</div>
											</div>	
										</div>
									</div>
								</fieldset>
				
						</div><!-- end of well -->
				<!-- Register panel -->

		
		</div>
		
		<div class="col-md-2">

							
		</div><!-- end of right column -->
	</div><!-- /container -->

  	<!-- Last part of BODY element in file index.html -->
		<script type="text/javascript">
		function signInCallback(authResult) {
		  if (authResult['code']) {
		
		    // Hide the sign-in button now that the user is authorized, for example:
		    $('#signinButton').attr('style', 'display: none');
		
		    // Send the code to the server
		    $.ajax({
		      type: 'POST',
		      url: '<%=request.getContextPath()%>/google/storeToken?state=${state}&token='+authResult['access_token'],
		      contentType: 'application/octet-stream; charset=utf-8',
		      success: function(result) {
		    	  console.log('userid is :'+result);
		    	  window.location.href = '<%=request.getContextPath()%>/user/admin';
		      },
		      error: function(){
		    	  $('#loginresult').html('Failed to login with your google account!');
		      },
		      processData: false,
		      data: authResult['code']
		    });
		  } else if (authResult['error']) {
			 
		  }
		}
		$(document).ready(function(){
			$("#registerPanel").hide();
			$("#loginPanel").show();
			
			$( "#linkSignin" ).click(function() {
			  $("#loginPanel").show();
			  $("#registerPanel").hide();
			});
			
			$("#linkSignup").click(function() {
				  $("#registerPanel").show(); 	
				  $("#loginPanel").hide();
				});
		});
		</script> 

		<script type="text/javascript"
		src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" charset="utf-8" data-callback="true"></script>

	</body>
	</html>