<%@include file="header.jsp" %>
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101073970" data-redirecturi="http://www.sunorth.org/qq/oauth2" charset="utf-8"></script>
 	<div class="row">
	<div class="col-md-8"> <!-- main right panel for question list -->
	
		<div class="jumbotron">
		  <h2 class="text-primary"><b><spring:message code="home.welcome.h2" text="My Business Management" /></b></h2>
		  
		 <p>
			 <a class="btn btn-success btn-lg" href="<%=request.getContextPath()%>/case/init" role="button"><spring:message code="home.btn.newcase" text="New Case" /></a>
			 <a class="btn btn-primary btn-lg" href="<%=request.getContextPath()%>/case/list" role="button"><spring:message code="home.btn.latestcases" text="Latest Cases" /></a>
			 <a class="btn btn-warning btn-lg" href="<%=request.getContextPath()%>/case/listactive" role="button"><spring:message code="home.btn.activecases" text="Active Cases" /></a>
			 <!-- 
			 <a class="btn btn-danger btn-lg" href="<%=request.getContextPath()%>/case/list/3" role="button">Help Cases</a>
			  -->
		 </p>
		  <p>		  
		  <form class="navbar-form" role="search" method="post" id="search-form" name="search-form" action="<%=request.getContextPath()%>/case/search">
		  		<input type="hidden" name="ctype" id="ctype" value="0">
		  		
		  		<!-- 
		  		<input type="radio" name="ctype" id="option0" value="0" checked> All
   				<input type="radio" name="ctype" id="option1" value="1"> Idea
				<input type="radio" name="ctype" id="option2" value="2"> Project
				<input type="radio" name="ctype" id="option3" value="3"> Issue
				 -->
				 
		        <div class="input-group">
            
		            <input type="text" class="form-control" placeholder="<spring:message code="home.search.placeholder" text="To find a solution... or new one" />" id="searchKey" name="searchKey" value="" size="80" required>
			            <div class="input-group-btn">
		           		 <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button>
		            	</div>
		            	
		        </div>
		        
			 </form>
		 </p>

		</div>
		
		<!-- Introduction -->
		
		<div class="row">
		  <div class="col-sm-6 col-md-3">
		    <div class="thumbnail">
		      <!-- img data-src="holder.js/300x300" alt="..."-->
		      <div class="caption">
		        <h3 class="text-success"><b>Digging</b></h3>
		        <p class="text-success">Analyze an <span class="text-success"><b>IDEA, PROJECT or ISSUE</b></span> with asking for open  help. </p>
		        
		      </div>
		    </div>
		  </div>

 		<div class="col-sm-6 col-md-3">
		    <div class="thumbnail">
		      <!-- img data-src="holder.js/300x300" alt="..."-->
		      <div class="caption">
		        <h3 class="text-primary"><b>Sharing</b></h3>
		        <p class="text-primary">Share your solution on any kinds of cases by showing the process. </p>
		        
		      </div>
		    </div>
		  </div>
		  
		 <div class="col-sm-6 col-md-3">
		    <div class="thumbnail">
		      <!-- img data-src="holder.js/300x300" alt="..."-->
		      <div class="caption">
		        <h3 class="text-info"><b>Collaborating</b></h3>
		        <p class="text-info">Launch a project and invite people to achieve it by cooperation.</p>
		        
		      </div>
		    </div>
		  </div>
		  
		  <div class="col-sm-6 col-md-3">
		    <div class="thumbnail">
		      <!-- img data-src="holder.js/300x300" alt="..."-->
		      <div class="caption">
		        <h3 class="text-danger"><b>Tracking</b></h3>
		        <p class="text-danger">Record any phase and checklist for you daily cases or make a plan and trace it.</p>
		        <!-- <p><a href="<%=request.getContextPath()%>/case/1" class="btn btn-primary" role="button">Demo</a> </p> -->
		      </div>
		    </div>
		  </div>
		</div>		
		<!-- latest update -->
		<div>
			<table class="table">
				
				<c:forEach var="comment" items="${comments}">
					<tr><td  class="text-danger" width="25%">
						 <c:out value="${comment.createtime}"/>
						 </td>
						 <td> 
						 <a href="<%=request.getContextPath()%>/case/1"><c:out value="${comment.comment}"/></a>
						 </td> 
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<!-- - RIGHT -->
	<div class="col-md-4">  <!-- right side panel -->
		<!-- Login -->
		<c:if test="${user==null}">
			<form action="<%=request.getContextPath()%>/user/login" class="form-horizontal " method="post">
			
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
							<button class="btn btn-lg btn-primary btn-lg" type="submit"><spring:message code="common.login" text="Login" /></button>
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


			<!-- top10 tags -->
			<div>
				<ul id="top10tag" class="list-group">

  				</ul>
			</div>

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
	     //fetch tag list
	   $.ajax({
           url:'<%=request.getContextPath()%>/tag/top10',
           dataType: 'json',
           success:function(data) {
        	   var strTagList='';
        	   $.each(data,function(index, tag){
        		   strTagList = strTagList+'<li class="list-group-item">'+
  						'<a  href="<%=request.getContextPath()%>/case/searchbytag?tag='+tag.tag+'">'+tag.tag+'</a>'+
  						'<span class="badge">'+tag.count+'</span></li>';
  						console.log(strTagList);
        		   
        	   });
        	   $("#top10tag").append(strTagList); 
				
           }
        });
   });

 </script>

<%@include file="footer.jsp" %>
