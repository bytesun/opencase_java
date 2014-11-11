<%@include file="header.jsp" %>

<div class="row">
	<div class="col-md-9"> <!-- main right panel for question list -->

	  	 <form class="form-horizontal" action="<%=request.getContextPath()%>/case/new" method="POST">

		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							<spring:message code="case.subject" text="Create a new case" />
							<input type="text" class="form-control input-default" name="subject" value="" placeholder="Input case title">
							</div>
						</div>
					</div>
				</div>	

		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							<spring:message code="question.description" text="Description" />
							<textarea class="form-control richtextarea" name="description" rows="10">
					  		
					  		</textarea>
							</div>
						</div>
					</div>
				</div>									
		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							<input type="text" class="form-control input-default" name="tag" value="" placeholder="input tags">
						  	<input type="submit" class="btn btn-success" name="submit" value="<spring:message code="global.action.save" text="Save" />">							
							</div>
						</div>
					</div>
				</div>					
		  		
		  </form>		

	
	</div><!-- end of left side -->	
   	
	<!-- right column -->
		<div  class="col-md-3">


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

