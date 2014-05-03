<%@include file="header.jsp" %>
 
 	<div class="row">

	<div class="col-md-9"> <!-- main right panel for question list -->
		<div class="row"> <!-- jumbotron row -->
			<!-- -------------Welcome ------------------- -->
			<!-- 
			<div class="jumbotron">
			  <h2><spring:message code="home.welcome.ask" text="How can I do that?" /></h2>
			  <p>
			  	<ul>
			  		<li><spring:message code="home.welcome.notice1" text="Find your solution." /></li>
			  		<li><spring:message code="home.welcome.notice2" text="Give others proposals.." /></li>
			  		<li><spring:message code="home.welcome.notice3" text="Build your knowledge and skills." /></li>
			  		<li><spring:message code="home.welcome.notice4" text="Build a project with other people. " /></li>
			  		<li><spring:message code="home.welcome.notice5" text="To be an independent consultant." /></li>
			  	</ul>
			  </p>
			  <p>
			  	<c:forEach items="${topcats}" var="cat">
			  	  <a class="btn btn-success btn-lg" role="button" href="<%=request.getContextPath()%>/cat/${lang}/${cat.cid}">
					<c:out value="${cat.catname}"/>
					</a>
			  	</c:forEach>
			  </p>
			</div>	 -->  
		</div>
		
		<div class="row"><!-- question list -->
		<!-- order type -->
		<!-- 
		<ul class="nav nav-tabs">
		
		  <li class="active"><a href="#latest" data-toggle="tab">
				<spring:message code="home.questionlist.ordertype.latest" text="Latest" />
			</a></li>
		
		  <li><a href="#hot" data-toggle="tab">   
		  		<spring:message code="home.questionlist.ordertype.hottest" text="Hottest" />
			</a></li>
			
		</ul>
		 -->
		<!-- Tab panes 
		<div class="tab-content">
		 
			<div class="tab-pane active" id="latest">		-->
				<!-- --------------top question List-------------------- -->		
				<div id="questionlist">
				</div><!-- list A -->	
	

			  	<div class="btn-group btn-group-justified">
				  <div class="btn-group">
				    <button type="button" id="moreNewQuestion" class="btn btn-default">
				    	<spring:message code="global.action.more" text="More..." />
				    </button>
				  </div>
				</div>
			  	</div>
			</div>
		<!--	
		</div> end of question list
	</div> end of row of jumbotron -->
	
	<!-- - RIGHT -->
	<div class="col-md-3">  <!-- right side panel -->
		<div class="row">
			<table class="table">

			 
			 <!-- NEW ISSUE -->
			
				<c:if test="${user!=null}">
				 <tr><td>
				<button class="btn btn-success btn-lg btn-block" data-toggle="modal" data-target=".newissue"><spring:message code="question.new" text="New Issue" /> </button>
				
	
					<div class="modal fade newissue" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
					  <div class="modal-dialog modal-lg">
					    <div class="modal-content">
					    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/0/ask" method="POST">
	
							<div class="model-body">
							
								<input type="hidden" name="cid" value="${thecat.cid}">
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
					</div>	<!-- end vote dialog -->
				</td></tr>		
				</c:if>			
			<!--desc -->
			<tr><td>
			  	<ul>
			  		<li><spring:message code="home.welcome.notice1" text="Find your solution." /></li>
			  		<li><spring:message code="home.welcome.notice2" text="Give others proposals.." /></li>
			  		<li><spring:message code="home.welcome.notice3" text="Build your knowledge and skills." /></li>
			  		<li><spring:message code="home.welcome.notice4" text="Build a project with other people. " /></li>
			  		<li><spring:message code="home.welcome.notice5" text="To be an independent consultant." /></li>
			  	</ul>
			 </td></tr>				 
			<!-- top 20 category

				<tr><td id="top20cat">
				<h4><spring:message code="home.category.hotcats" text="Hotest Categories" /></h4>
				</td></tr>
			 -->

			<!-- top 20 tags 
				<tr><td>
				</td></tr>
			-->
			<!-- <div id="top20tag" class="panel panel-default">
				<div class="panel-heading">
					<h4><spring:message code="home.tag.hot" text="Hotest Tags" /></h4>
				</div>
				<div id="top20tag" class="panel-body">
				</div>
			</div>
			 -->
			 </table>
		</div>
	</div><!-- end of left side -->

	</div><!-- end of the whole row -->

 <script type="text/javascript">
   $(document).ready(function() {
	   var qindex=0;
       //top 20 categories 
	   $.ajax( {
           url:'<%=request.getContextPath()%>/cat/top20',
           dataType: 'json',
           success:function(data) {
          	   $.each(data, function(index, category) {
          		   $('#top20cat').append('<a class="btn btn-default" role="button" href="<%=request.getContextPath()%>/cat/${lang}/'+category.cid+'">'+category.catname+'</a>');
                 });
           }
        });
        
        //top tags
	   $.ajax( {
           url:'<%=request.getContextPath()%>/tag/top20',
           dataType: 'json',
           success:function(data) {
          	   $.each(data, function(index, tag) {
          		   $('#top20tag').append('<a class="btn btn-success" role="button" href="<%=request.getContextPath()%>/tag/${lang}/'+category.cid+'">'+tag.name+'</a>');
                 });
           }
        });
        //latest questions
         $.ajax( {
            url:'<%=request.getContextPath()%>/question/latestQuestions',
            dataType: 'json',
            success:function(data) {
            	var count = 0;
           	   $.each(data, function(index, question) {
           		   var tags = question.tag.split(" ");
           		 var strtags='<div class="tags">';
           		
				   if(tags != ''){
	           		   $.each(tags,function(index,tag){
	           			 strtags=strtags+'<a class="tag-link-bluemix" href="<%=request.getContextPath()%>/question/searchtag?tag='+tag+'">'+tag+'</a>&nbsp;&nbsp;';
	           		   });
				   }
           			
				   strtags=strtags+'</div>';
           		   $('#questionlist').append('<div class="short-summary"><div class="question-summary-wrapper"><h4 class="list-heading">'+
           		   		'<a href="<%=request.getContextPath()%>/question/${lang}/'+question.qid+'"><strong>'+ 
					 	question.question+'</strong></a></h4>'+
					 	'<div class="userinfo"><a href="<%=request.getContextPath()%>/user/'+question.user.uid+'">'+question.user.name+'</a></div> '+
					 	strtags+
					 	//'</div><div class="counts"><div  class="status  answered-accepted"><div class="item-count">'+
					 	//question.answercnt+'</div></div></div>'+
					 	'</div>');
           		   count = index;
                  });
           	   qindex = qindex+count+1;
            }
         });
       
	      $("#moreNewQuestion").click(function(event){

	          $.ajax( {
	             url:'<%=request.getContextPath()%>/question/newQuestions?start='+qindex,
	             dataType: 'json',
	             success:function(data) {
	            	 var count = 0;
	            	   $.each(data, function(index, question) {
	               		   var tags = question.tag.split(" ");
	            		   var strtags='<div class="tags">';
	                  		
	    				   if(tags != ''){
	    	           		   $.each(tags,function(index,tag){
	    	           			 strtags=strtags+'<a class="tag-link-bluemix" href="<%=request.getContextPath()%>/question/searchtag?tag='+tag+'">'+tag+'</a>';
	    	           		   });
	    				   }
	               			
	               			strtags=strtags+'</div>';
	               		   $('#questionlist').append('<div class="short-summary"><div class="question-summary-wrapper"><h4 class="list-heading">'+
	               		   		'<a href="<%=request.getContextPath()%>/question/${lang}/'+question.qid+'"><strong>'+ 
	    					 	question.question+'</strong></a></h4>'+
	    					 	'<div class="userinfo"><a href="<%=request.getContextPath()%>/user/'+question.user.uid+'">'+question.user.name+'</a></div> '+
	    					 	strtags+
	    					 	//'</div><div class="counts"><div  class="status  answered-accepted"><div class="item-count">'+
	    					 	//question.answercnt+'</div></div></div>'+
	    					 	'</div>');
	               		count = index;
	                   });
	            	   qindex = qindex+count+1;
	             }
	          });
	      });        

   });
   </script>	
	
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-50600818-1', 'sunorth.org');
  ga('send', 'pageview');

</script>

<%@include file="footer.jsp" %>
