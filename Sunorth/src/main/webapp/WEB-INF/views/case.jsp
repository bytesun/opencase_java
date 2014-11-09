<%@include file="header.jsp" %>

<div class="row">
	<div class="col-md-9"> <!-- main right panel for question list -->
		<h2><c:out value="${thecase.subject}"/></h2>
		<hr/>
		<c:out value="${thecase.desc}"></c:out>
	
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

