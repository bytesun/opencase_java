
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="zh_cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="<spring:message code="header.description" text="Sunorth" />">
	<meta name="keywords" content="<spring:message code="header.keywords" text="Sunorth" />">
	<meta name="author" content="Sun">
    <meta property="qc:admins" content="0762654177635672406727" /><!-- qq verify -->
   
    <title><spring:message code="header.title" text="Sunorth" /></title>

    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/sunorth.css" rel="stylesheet">
	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

	<script src="//tinymce.cachefly.net/4.0/tinymce.min.js"></script>
	<script>
	        tinymce.init({selector:'.richtextarea'});
	</script> 
	
	<!-- social sharing -->
	<script type="text/javascript">var switchTo5x=true;</script>
	<script type="text/javascript" src="http://w.sharethis.com/button/buttons.js"></script>
	
</head>
 <body style="padding-top: 70px;">  

		
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation" >
			<div class="navbar-inner">
				
			<div class="container">		
		    <!-- Brand and toggle get grouped for better mobile display -->
		    <div class="navbar-header">
		      <a class="navbar-brand" href="<%=request.getContextPath()%>/"><img alt="" src="${pageContext.request.contextPath}/resources/img/sunorth.png" height="25" width="25"><b><spring:message code="header.sunspace" text="SunSpace" /></b></a>
		    </div>
		
		    <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		      
		      <form class="navbar-form navbar-left" role="search" action="<%=request.getContextPath()%>/question/search" method="GET">
		        <div class="form-group">
		          <input type="text" class="form-control" placeholder="<spring:message code="common.search" text="search"/>" name="searchkey" title="Search input" required>
		        </div>
		        <button type="submit" class="btn btn-seccess"><spring:message code="common.search" text="search" /></button>
	        
		      </form>
			  
      
			 <ul class="nav navbar-nav navbar-right">
		        <li class="dropdown">
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="language.title" text="Language" /> <b class="caret"></b></a>
		          <ul class="dropdown-menu">
		            <li><a href="<%=request.getContextPath()%>/chglang?lang=en">
		            <spring:message code="language.english" text="English" /></a></li>
		            <li><a href="<%=request.getContextPath()%>/chglang?lang=zh">
		            <spring:message code="language.chinese" text="Chinese" /></a></li>	
		          </ul>		          
		        </li>
			</ul>			
    	    
		    <c:if test="${user!=null}">
			 <ul class="nav navbar-nav navbar-right">
		        <li class="dropdown">
		        
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><img alt="" src="${user.photo1}" height="25" width="25"> &nbsp;<c:out value="${user.name}"/> <b class="caret"></b></a>
		          <ul class="dropdown-menu">
		            <li>
		            
		            	<a href="<%=request.getContextPath()%>/user/admin">
		            	<spring:message code="user.mypage" text="MyPage" />
		            </li>
		          
		            <li><a href="<%=request.getContextPath()%>/user/logout">
		            <spring:message code="common.logout" text="Logout" /></a></li>
	
		          </ul>		          
		        </li>
			</ul>
		     </c:if>
		 </div>
		

			
			</div>
		</div>	<!-- end of inner -->
	</nav> <!-- end of nav -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<div class="container">