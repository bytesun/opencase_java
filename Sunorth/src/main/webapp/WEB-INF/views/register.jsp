<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%request.setCharacterEncoding("UTF-8");%>
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


</head>
 <body>  
<div class="container">

		<div class="col-md-4">
		
		</div>
		<div class="col-md-4">
		<div class="well">
			<form action="<%=request.getContextPath()%>/register" class="form-horizontal well" method="post">
				<input type="hidden" name="cid" value="${cid}">
				<input type="hidden" name="qid" value="${qid}">
				<fieldset>
					<legend><spring:message code="reg.createAccount" text="Create Account" /></legend>
 
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group"> 
								<div class="rows">
									<div class="col-md-12">
										<div class="col-lg-12">
											<input class="form-control input-lg" id="name" name=
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
											<br> <spring:message code="reg.askHaveAccount" text="Do you have account" /> <a href="<%=request.getContextPath()%>/redirectLogin">
														<spring:message code="common.login" text="Login" /></a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		</div>
		
		<div class="col-md-4">
		</div>
	</div><!-- /container -->
	
