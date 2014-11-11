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
	  			<!-- vote -->
	  			<td  style="width:70px;vertical-align:top">
	  				<div class="vote-buttons">
						<a id="issue-upvote" class="post-vote up " rel="nofollow" href="<%=request.getContextPath()%>/question/${question.qid}/vote/1" title="This post is useful (click again to undo)"> </a>
						<div id="issue-score" class="post-score" title="current number of votes">
							<c:out value="${question.rate}"/>
						</div>
						<a id="issue-downvote" class="post-vote down" rel="nofollow" href="<%=request.getContextPath()%>/question/${question.qid}/vote/-1" title="This post is not useful (click again to undo)"> </a>
						<a id="favorite-mark" class="favorite-mark " rel="nofollow" href="/answers/mark_favorite/12619/?community=bluemix" title="mark/unmark this question as favorite (click again to cancel)"> </a>
						<div id="favorite-count" class="favorite-count"> </div>
						<c:choose>
							<c:when test="${question.resolved}">
								<div class="accept-answer on" title="<spring:message code="question.status.resolved" text="Resolved" />"> </div>
							</c:when>
							<c:when test="${question.user.uid==user.uid}">
								<a id="issue-resolved" class="accept-answer" href="<%=request.getContextPath()%>/question/${question.qid}/resolve" title="<spring:message code="question.status.resolveclick.notice" text="Click it to mark the issue resolved" />"> </a>
							</c:when>
						</c:choose>
  
										
	  				</div>
	  			</td>
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
			<tr>
					<td style="width:70px;vertical-align:top"></td>
					<td>
						<div id="issue-comment-list">
							<c:forEach items="${comments}" var="comment">	  	
										 	<c:out value="${comment.comment}"  escapeXml="false"/>
										 	&nbsp;-&nbsp;<a href="<%=request.getContextPath()%>/user/${question.user.uid}"><c:out value="${comment.user.name}"/></a>
							    		
							<hr>
						  	</c:forEach>
					  	</div>
						<div>
							<a id="addComment" href=""><spring:message code="question.comment.add" text="Add Comment" /></a>
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

						  	<input type="submit" class="btn btn-success" name="submit" value="<spring:message code="global.action.save" text="Save" />">							
							</div>
						</div>
					</div>
				</div>					
		  		
		  </form>		
	    </div>
	  </div>
	</div>	<!-- end of edit dialog -->		 	



	<ul class="nav nav-tabs">
	  
	  <li  class="active"><a href="#proposals" data-toggle="tab">
		<b><c:out value="${question.answercnt}"/> &nbsp;<spring:message code="question.proposal.title" text="Proposals" /></b></a>
	  </li>	
	</ul>
	
	<div class="tab-content">
	  <div class="tab-pane active" id="proposals">
			<!-- ------------proposals------------------ -->
			<c:set var="hasAnswered" value="false"/> <!-- check the current user if answered it or not -->
				<table class="table" style="table-layout: fixed; width: 100%">
			  		<c:forEach items="${answers}" var="answer">
			  			<tr>
			  				<!-- vote -->
			  			<td  style="width:70px;vertical-align:top">
			  				<div class="vote-buttons">
								<a id="proposal-upvote-${answer.aid}" class="post-vote up " rel="nofollow" href="<%=request.getContextPath()%>/question/${question.qid}/answer/${answer.aid}/vote/1" title="This post is useful (click again to undo)"> </a>
								<div id="proposal-score-${answer.aid}" class="post-score" title="current number of votes"> <c:out value="${answer.rate}"></c:out> </div>
								<a id="proposal-downvote-${answer.aid}" class="post-vote down" rel="nofollow" href="<%=request.getContextPath()%>/question/${question.qid}/answer/${answer.aid}/vote/-1" title="This post is not useful (click again to undo)"> </a>

								<c:choose>
									<c:when test="${answer.answered}">
										<div class="accept-answer on" title="<spring:message code="question.status.resolved" text="Resolved" />"> </div>
									</c:when>
									<c:when test="${question.user.uid==user.uid}">
										<a id="answer-accepted-${answer.aid}" class="accept-answer" href="<%=request.getContextPath()%>/question/${question.qid}/answer/${answer.aid}/accept" title="<spring:message code="question.status.resolveclick.notice" text="Click it to mark the issue resolved" />"> </a>
									</c:when>
								</c:choose>												
			  				</div>
			  			</td>
			  				
						 	<td>
						 	<c:if test="${answer.user.uid==user.uid}">
						 		<c:set var="hasAnswered" value="true"/>
						 	</c:if>
						 	
						 	<a href="<%=request.getContextPath()%>/user/${answer.user.uid}"><c:out value="${answer.user.name}"/></a>				 	 
						 	<p><c:out value="${answer.answer}"  escapeXml="false"/></p>			  		
						  	<c:if test="${user!=null && user.uid==answer.user.uid}">
				  	  			<a href="#" data-toggle="modal" data-target=".editproposal"><spring:message code="global.action.edit" text="Edit" /></a>&nbsp;
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
			
					<c:when	 test="${user!=null && user.uid!=question.user.uid && !hasAnswered}">
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

	  </div><!-- end of tab panel -->	
	</div>	<!-- end of tab-content -->
	
	</div><!-- end of left side -->	
   	
	<!-- right column -->
		<div  class="col-md-3">

   		
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
					    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/ask" method="POST">
							<input type="hidden" name="cid" value="0">
							<input type="hidden" name="qid" value="${question.qid}">
							
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
		<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		<!-- sunorth_left -->
		<ins class="adsbygoogle"
		     style="display:inline-block;width:250px;height:600px"
		     data-ad-client="ca-pub-1018407477199873"
		     data-ad-slot="3799301345"></ins>
		<script>
		(adsbygoogle = window.adsbygoogle || []).push({});
		</script>			   					
		</div>
		
</div>

<script type="text/javascript">
   $(document).ready(function() {

	      $("#issue-resolved").click(function(event){
    	  	  event.preventDefault();
    	  	  var resolveLink = $(this).attr('href');
    	  	  console.log(resolveLink);
	          $.ajax({
	             url:resolveLink,
	             success:function(data) {
					console.log('The issue has been changed status 1');
					$("#issue-resolved").html('<div class="accept-answer on" title="<spring:message code="question.status.resolved" text="Resolved" />"> </div>');
					
	             }
	          });
	      });
	   
	   //update a issue status to resolved
       


      $("#issue-upvote").click(function(event){
	  	  event.preventDefault();
	  	  var acceptLink = $(this).attr('href');
	  	  console.log(acceptLink);
          $.ajax({
             url:acceptLink,
             success:function(data) {
				console.log('upvote 1');
				var orivote = parseInt($('#issue-score').text());
				$('#issue-score').html(orivote+1);
             }
          });
      });  
	   
      $("#issue-downvote").click(function(event){
	  	  event.preventDefault();
	  	  var acceptLink = $(this).attr('href');
	  	  console.log(acceptLink);
          $.ajax({
             url:acceptLink,
             success:function(data) {
				console.log(' downvote -1');
				var orivote = parseInt($('#issue-score').text());
				$('#issue-score').html(orivote-1);
             }
          });
      });  
      
      $("a[id|='answer-accepted']").each(function() {
          $(this).click(function(e) {
	  	  event.preventDefault();
	  	  var resolveLink = $(this).attr('href');
	  	  console.log(resolveLink);
	  		var svid = $(this).attr('id');
      	 var vid = svid.substring(svid.lastIndexOf('-')+1);
          $.ajax({
             url:resolveLink,
             success:function(data) {
				console.log('the answer has been accept');
				$("#answer-accepted-"+vid).html('<div class="accept-answer on" title="<spring:message code="question.status.resolved" text="Resolved" />"> </div>');
             }
          });
     	 });   
      });           
      //upvote proposal
   	    $("a[id|='proposal-upvote']").each(function() {
 	            $(this).click(function(e) {
 	            	event.preventDefault();
               	 var acceptLink = $(this).attr('href');
               	 var svid = $(this).attr('id');
               	 var vid = svid.substring(svid.lastIndexOf('-')+1);
               	  console.log(vid);
	 	   	  	  console.log(acceptLink);
	 	          $.ajax({
	 	             url:acceptLink,
	 	             success:function(data) {
	 					console.log('vid='+vid);
	 					var orivote = parseInt($('#proposal-score-'+vid).text());
	 					$('#proposal-score-'+vid).html(orivote+1);
	 	             }
	 	          });					
 	            });
        });
       
   	    $("a[id|='proposal-downvote']").each(function() {
	            $(this).click(function(e) {
	            	event.preventDefault();
           	 var acceptLink = $(this).attr('href');
           	 var svid = $(this).attr('id');
           	 var vid = svid.substring(svid.lastIndexOf('-')+1);
           	  console.log(vid);
 	   	  	  console.log(acceptLink);
 	          $.ajax({
 	             url:acceptLink,
 	             success:function(data) {
 					console.log('vid='+vid);
 					var orivote = parseInt($('#proposal-score-'+vid).text());
 					$('#proposal-score-'+vid).html(orivote-1);
 	             }
 	          });					
	         });
    	}); 
   	    
   	    $("#addComment").click(function(e){
   	    	event.preventDefault();
   	    	$("#issue-comment-list").append('<form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/${question.qid}/comment/new" method="POST">'+
   	    			'<textarea  name="comment" cols="100" rows="5"></textarea><button type="submit" class="btn btn-success">'+
							'<spring:message code="question.comment.add" text="Add Comment" /></button>&nbsp;'+
							//'<button type="submit" class="btn btn-default">Cancel</button>'+
							'</form>');
   	    	$("#addComment").hide();
   	    });
   });
   </script>



<%@include file="footer.jsp" %>

