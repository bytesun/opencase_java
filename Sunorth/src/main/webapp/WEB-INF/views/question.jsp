<%@include file="header.jsp" %>

<div class="row">
	<div class="col-md-9"> <!-- main right panel for question list -->

	<!-- --------------Question detail -->
	<ol class="breadcrumb">
		<li><a href="<%=request.getContextPath()%>/cat/${lang}/${pcategory.cid}">
			<c:out value="${pcategory.catname}"/>
		</a></li>
		<li>
		<a href="<%=request.getContextPath()%>/question/${lang}/${pquestion.qid}">
			<c:out value="${pquestion.question}"/>
		</a></li>
		<!-- tags list -->
	</ol>
	<div>

		
	</div>


	  
	  <h2> <span style="word-break:break-all;"><c:out value="${question.question}"/></span></h2>

	  	<table class="table">
			<!-- question desc -->
	  		<tr>
	  			<td><!-- question desc -->
			  		<b><a href="<%=request.getContextPath()%>/user/${question.user.uid}"><c:out value="${question.user.name}"/> </a></b>
			   		<div class="question"><c:out value="${question.description}"  escapeXml="false"/></div>
			   		
			   		
   					<!-- operations : edit -->
					<div>				
			
						<c:if test="${user!=null && user.uid==question.user.uid}">
				  	  		<a href="#" data-toggle="modal" data-target=".editquestion"><spring:message code="global.action.edit" text="Edit" /></a>&nbsp;|
				  	  	</c:if>	
						<c:if test="${question.tag!=null}">
							<c:set var="tags" value="${fn:split(question.tag,' ')}"/>
							<c:forEach items="${tags}" var="tag">
								&nbsp;
								<a href="<%=request.getContextPath()%>/question/searchtag?tag=${tag}">
								<span class="label label-success"><c:out value="${tag}"/></span></a>
							</c:forEach>
						</c:if>					  	  	
					</div>
					

	  			</td>
	  		</tr>
			
	  	</table>

		<div>
	 	</div>

	<!-- edit -->				 	
	<div class="modal fade editquestion" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	  	 <form class="form-horizontal" action="<%=request.getContextPath()%>/question/${question.qid}/edit" method="POST">

		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							
							</div>
						</div>
					</div>
				</div>	

		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							<spring:message code="common.question" text="Issue" />
							<input type="text" class="form-control input-default" name="question" value="<c:out value="${question.question}"/>">
							</div>
						</div>
					</div>
				</div>	

		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							<spring:message code="question.description" text="Description" />
							<textarea class="form-control richtextarea" name="questiondesc" rows="10">
					  		<c:out value="${question.description}"  escapeXml="false"/>
					  		</textarea>
							</div>
						</div>
					</div>
				</div>									
		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							<spring:message code="common.tag" text="Tag" />
					  		<input type="text" name="tag" value="<c:out value="${question.tag}"/>">
						  	<input type="submit" class="btn btn-success" name="submit" value="<spring:message code="global.action.save" text="Save" />">							
							</div>
						</div>
					</div>
				</div>					
		  		
		  </form>		
	    </div>
	  </div>
	</div>	<!-- end of edit dialog -->		 	

			<!-- add comment diag -->
       <div class="modal fade addcomment" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/${question.qid}/comment/new" method="POST">

			<div class="model-body">

		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							
							</div>
						</div>
					</div>
				</div>		
					
		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							<spring:message code="question.comment.add" text="Comment" />
							<textarea class="form-control" cols="50" rows="5" name="comment" placeholder=""></textarea>
							</div>
						</div>
					</div>
				</div>	
				
		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							<button type="submit" class="btn btn-success">
							<spring:message code="question.comment.add" text="Add Comment" /></button>			
							</div>
						</div>
					</div>
				</div>																					
				
							
			</div>
		</form>		
	    </div>
	  </div>
	</div>	<!-- end add comment-->	

	<!-- ------------proposals------------------ -->
	<c:set var="hasAnswered" value="false"/> <!-- check the current user if answered it or not -->
	<div><h4><c:out value="${question.answercnt}"/> &nbsp;<spring:message code="question.proposal.title" text="Proposals" /></h4></div>
		<table class="table" style="table-layout: fixed; width: 100%">
	  		<c:forEach items="${answers}" var="answer">
	  			<tr>
				 	<td>
				 	<c:if test="${answer.user.uid==user.uid}">
				 		<c:set var="hasAnswered" value="true"/>
				 	</c:if>
				 	<a href="<%=request.getContextPath()%>/user/${answer.user.uid}"><c:out value="${answer.user.name}"/></a>				 	 
				 	<p><c:out value="${answer.answer}"  escapeXml="false"/></p>			  		
				  	<c:if test="${user!=null && user.uid==answer.user.uid}">
		  	  			<div><a href="#" data-toggle="modal" data-target=".editproposal"><spring:message code="global.action.edit" text="Edit" /></a></div>
		  	  		</c:if>	
	  	  		
					<!-- edit -->				 	
					<div class="modal fade editproposal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
					  <div class="modal-dialog modal-lg">
					    <div class="modal-content">
					  	 <form class="form-horizontal" action="<%=request.getContextPath()%>/question/${question.qid}/answer/${answer.aid}/edit" method="POST">

						  		<textarea class="form-control richtextarea" name="answer" rows="10">
						  		<c:out value="${answer.answer}"  escapeXml="false"/>
						  		</textarea>
							  	<input type="submit" class="btn btn-success" name="submit" value="<spring:message code="global.action.save" text="Save" />">
									
						  </form>		
					    </div>
					  </div>
					</div>	<!-- end of edit dialog -->		  	  		

	    		</td></tr>
	  		</c:forEach>
	  	</table>
		
		<c:choose>
			<c:when test="${user==null}">
				<!-- relogin -->
				<spring:message code="question.loginnotice" text="Please login to answer the question:" />
				<a href="<%=request.getContextPath()%>/user/redirectLogin?qid=${question.qid}">
					<spring:message code="common.login" text="Login" />
				</a>
			</c:when>
	
			<c:when	 test="${user!=null && user.uid!=question.user.uid && !hasAnswered && !question.resolved}">
				<form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/${question.qid}/answer/new" method="POST">
				  <div class="form-group">
					 <textarea class="form-control richtextarea" rows="5" name="answer" placeholder="" ></textarea>
				  </div>
				   
				  <div class="form-group">
					
					<button type="submit" class="btn btn-success">
					<spring:message code="question.proposal.submit" text="My Proposal" /></button>

				  </div>	 
				</form>	
			</c:when>
			
		</c:choose>	
	</div>

   	
	<!-- right column -->
		<div  class="col-md-3">

			<div class="row">
			<c:if test="${user!=null}">
				<button class="btn btn-success btn-lg btn-block" data-toggle="modal" data-target=".addcomment"><spring:message code="question.comment.add" text="Add Comment" /> </button>

			</c:if>
			</div>

			<!-- comments -->
			<div class="row">

				<table class="table"  style="table-layout: fixed; width: 100%">
				<c:forEach items="${comments}" var="comment">
						<tr>
						<td style="word-wrap: break-word">

				  			<p >  	
							 	<c:out value="${comment.comment}"  escapeXml="false"/>
							 	&nbsp;-&nbsp;<a href="<%=request.getContextPath()%>/user/${question.user.uid}"><c:out value="${comment.user.name}"/></a>
				    		</p>
			    		</td>
			    		</tr>

			  	</c:forEach>
			  	
		   		</table>
	   		</div><!-- end of comments -->	
	   		
			<!-- sub issue -->
			<div class="row">
			<c:if test="${user!=null}">

				<button class="btn btn-success btn-lg btn-block" data-toggle="modal" data-target=".newissue"><spring:message code="question.followup.new" text="New Issue" /> </button>
			</c:if>
			</div>	   								
			<div class="row">
			<table class="table" style="table-layout: fixed; width: 100%">
		  		<c:forEach items="${followups}" var="question">
		  			<tr><td style="word-wrap: break-word">  
		  				<span class="label label-default"><c:out value="${question.answercnt}"/></span> 
					 	<a href="<%=request.getContextPath()%>/question/${lang}/${question.qid}">
		
					 	<c:out value="${question.question}"/></a>
					 	
		    		</td></tr>
		  		</c:forEach>
		  	</table>			
			
					
			<div class="modal fade newissue" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
					  <div class="modal-dialog modal-lg">
					    <div class="modal-content">
					    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/${question.qid}/ask" method="POST">

							<div class="model-body">
						  	 	<div class="form-group"> 
						  	 		<div class="rows">
										<div class="col-md-12">
											<div class="col-lg-12">	
											
											</div>
										</div>
									</div>
								</div>	
							  	
							  	
						  	 	<div class="form-group"> 
						  	 		<div class="rows">
										<div class="col-md-12">
											<div class="col-lg-12">	
											<spring:message code="question.new" text="New Issue" />
											<input type="text" class="form-control input-default" name="questitle" placeholder=" " required>											
											</div>
										</div>
									</div>
								</div>								

						  	 	<div class="form-group"> 
						  	 		<div class="rows">
										<div class="col-md-12">
											<div class="col-lg-12">	
											<spring:message code="question.description" text="Description" />
											<textarea class="form-control richtextarea" rows="5" name="question" placeholder=" "></textarea>											
											</div>
										</div>
									</div>
								</div>	
						  	 	<div class="form-group"> 
						  	 		<div class="rows">
										<div class="col-md-12">
											<div class="col-lg-12">	
												<input type="text" name="tag" placeholder="<spring:message code="common.tag" text="Tag" />"> 
												<button type="submit" class="btn btn-success">
												<spring:message code="question.consult" text="Consult" /></button>												
											</div>
										</div>
									</div>
								</div>																
					
							</div>
						</form>		
					    </div>
					  </div>
					</div>	<!-- end issue dialog -->		
			
			</div>
			<div class="row">
				<!-- social sharing button -->
				<span class='st_facebook_large' displayText='Facebook'></span>
				<span class='st_googleplus_large' displayText='Google +'></span>
				<span class='st_baidu_large' displayText='Baidu'></span>
				<span class='st_twitter_large' displayText='Tweet'></span>
				<span class='st_linkedin_large' displayText='LinkedIn'></span>
				<span class='st_sina_large' displayText='Sina'></span>
				<span class='st_blogger_large' displayText='Blogger'></span>
			</div>	   					
		</div>
		
</div>

<script type="text/javascript">
   $(document).ready(function() {
      $("#answer_vote").click(function(event){
	          $.ajax({
	             url:$(this).attr('href'),
	             success:function(data) {
					var rtn=data;
					
	             }
	          });
	      });        

   });
   </script>

<%@include file="footer.jsp" %>

