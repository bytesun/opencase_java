<%@include file="header.jsp" %>
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101073970" data-redirecturi="http://www.sunorth.org/qq/oauth2" charset="utf-8"></script>
 	<div class="row">
	<div class="col-md-8"> <!-- main right panel for question list -->
	
		<div class="jumbotron">
		  <h1>Get solution</h1>
		  <p> </p>
		  <p>		  
		  <form class="navbar-form" role="search" method="post" id="search-form" name="search-form" action="<%=request.getContextPath()%>/search">
		        <div class="input-group">
		            <input type="text" class="form-control" placeholder="An idea, project or issue?" id="tag" name="tag" value="" size="80" required>
			            <div class="input-group-btn">
		           		 <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-search"></span></button>
		            	</div>
		        </div>
			 </form>
		 </p>
		</div>

 
	<ul class="nav nav-tabs">
	  
	  <li  class="active unsolved-title"><a href="#issues" data-toggle="tab">
		<b><spring:message code="home.tab.1" text="Latest Cases" /></b></a>
	  </li>	
	</ul>	
		<div id="caselist">
							
			<c:forEach items="${cases}" var="suncase">
		   
          		   <div class="short-summary"><div class="question-summary-wrapper"><h4 class="list-heading">
          		   		<a href="<%=request.getContextPath()%>/case/${suncase.caseid}"><strong>
				 	${suncase.subject}</strong></a></h4>
				 	
				<c:if test="${suncase.tag!=null}">
					<div class="tags">
					<c:set var="tags" value="${fn:split(suncase.tag,' ')}"/>
					<c:forEach items="${tags}" var="tag">
						<a href="<%=request.getContextPath()%>/search?tag=${tag}">
						<c:out value="${tag}"/></a>
					</c:forEach>
					</div>
				</c:if>			 						 	
				</div>
			</div>

		</c:forEach>
		</div>
	</div>
	<!-- - RIGHT -->
	<div class="col-md-4">  <!-- right side panel -->
		<!-- Login -->
		<c:if test="${user==null}">
			<form action="<%=request.getContextPath()%>/user/login" class="form-horizontal " method="post">
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
							<a href="<%=request.getContextPath()%>/user/register/new"><spring:message code="common.signup" text="SignUp" /></a>

						</div>

					</div>
				</div>
			</div>
			
			<div class="form-group">
				<div class="rows">
					<div class="col-md-12">
						<div class="col-lg-12">
							<table>
								<tr>
									<td>
										<!-- qq -->
										<a id="qq_login_btn" href="<%=request.getContextPath()%>/qq/login">
										<img alt="" src="${pageContext.request.contextPath}/resources/img/qq_logo.png"></a>									
									</td>
									
									<td>
									
										<!-- google -->
									  <div id="gSignInWrapper">
									   &nbsp;&nbsp;&nbsp;
									  <a href="<%=request.getContextPath()%>/google/login">
									    <div id="googleBtn" class="customGPlusSignIn">
									      <span class="icon"></span>
									      <span class="buttonText">Google</span>
									    </div>
									    </a>
									  </div>									
									</td>
								</tr>
								
							</table>
							
						
						</div>

					</div>
				</div>
			</div>			
			</form>
		
		

		</c:if>


			<!-- hottest categories
			<div  class="panel panel-default">
			  <div class="panel-heading">
			  	<spring:message code="home.tag.hot" text="Hottest Tags"></spring:message>
			  </div>
			  <div class="panel-body">
			    <div id="top50cat"></div>
			  </div>
			</div>
 			-->
 			<!-- AD -->
 	
 			<div  class="panel panel-default"  align="center">
				<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
				<!-- p8ecm_main_Blog1_300x250_as -->
				<ins class="adsbygoogle"
				     style="display:inline-block;width:300px;height:250px"
				     data-ad-client="ca-pub-1018407477199873"
				     data-ad-slot="5142696541"></ins>
				<script>
				(adsbygoogle = window.adsbygoogle || []).push({});
				</script>
			</div>
			
	
	</div><!-- end of left side -->
	
	</div><!-- end of the whole row -->

 <script type="text/javascript">
   $(document).ready(function() {
	     
   });

 </script>

<%@include file="footer.jsp" %>
