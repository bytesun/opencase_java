<%@include file="header.jsp" %>
<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-appid="101073970" data-redirecturi="http://www.sunorth.org/qq/oauth2" charset="utf-8"></script>
 	<div class="row">
	<div class="col-md-8"> <!-- main right panel for question list -->
	
	<div class="jumbotron home-notice">
	  <h1><spring:message code="home.welcome.ask" text="Find your solution." /></h1>

	 <ul>
  		<li><h4><spring:message code="home.welcome.notice1" text="Find your solution." /></h4></li>
  		<li><h4><spring:message code="home.welcome.notice2" text="Give others proposals.." /></h4></li>
  		<li><h4><spring:message code="home.welcome.notice3" text="Build your knowledge and skills." /></h4></li>
  		<li><h4><spring:message code="home.welcome.notice4" text="Build a project with other people. " /></h4></li>
  		<li><h4><spring:message code="home.welcome.notice5" text="To be an independent consultant." /></h4></li>
  	</ul>
	  
	</div>
	<ul class="nav nav-tabs">
	  
	  <li  class="active"><a href="#issues" data-toggle="tab">
		<b><spring:message code="home.questionlist.ordertype.unsolved" text="Unsolved" /></b></a>
	  </li>	
	</ul>	
		<div id="questionlist">
			<c:set var="qindex" value="0"/>				
			<c:forEach items="${newquestions}" var="question">
		   
          		   <div class="short-summary"><div class="question-summary-wrapper"><h4 class="list-heading">
          		   		<a href="<%=request.getContextPath()%>/question/${lang}/${question.qid}"><strong>
				 	${question.question}</strong></a></h4>
				 	
				<c:if test="${question.tag!=null}">
					<div class="tags">
					<c:set var="tags" value="${fn:split(question.tag,' ')}"/>
					<c:forEach items="${tags}" var="tag">
						<a href="<%=request.getContextPath()%>/question/searchtag?tag=${tag}">
						<c:out value="${tag}"/></a>
					</c:forEach>
					</div>
				</c:if>			 						 	
				</div>
			</div>
			<c:set var="qindex" value="${qindex+1}"/>
		</c:forEach>
		</div>
	  	<div class="btn-group btn-group-justified">
		  <div class="btn-group">
		    <button type="button" id="moreNewQuestion" class="btn btn-default">
		    	<spring:message code="global.action.more" text="More..." />
		    </button>
		  </div>
		</div>
	</div>
	<!-- - RIGHT -->
	<div class="col-md-4">  <!-- right side panel -->
		<!-- Login -->
		<c:if test="${user==null}">
		<div id="login" class="panel panel-default" align="center">
			<div class="panel-body">
			<span id="qqLoginBtn"></span>
		
			</div>
		</div>
		</c:if>
			 <!-- NEW ISSUE -->
				<c:if test="${user!=null}">
					<button class="btn btn-success btn-lg btn-block" data-toggle="modal" data-target=".newissue"><spring:message code="question.new" text="New Issue" /> </button>
					<br>
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
			
			<div  class="panel panel-default">
			  <div class="panel-heading">
			  	<spring:message code="home.msg.latest" text="The Latest Status"></spring:message>
			  </div>
			  <div class="panel-body">
			    <table id="latestMsg" class="table" style="table-layout: fixed; width: 100%"></table>
			  </div>
			</div>
			
			
			<div  class="panel panel-default">
			  <div class="panel-heading">
			  	<spring:message code="home.event.title" text="The Latest Event"></spring:message>
			  </div>
			  <div class="panel-body">


			  </div>
			</div>
			<!--desc -->



		
	</div><!-- end of left side -->

	
	</div><!-- end of the whole row -->

 <script type="text/javascript">
   $(document).ready(function() {
	   var qindex=${qindex+1};
       //top 20 categories 
	   $.ajax( {
           url:'<%=request.getContextPath()%>/cat/top50',
           dataType: 'json',
           success:function(data) {
          	   $.each(data, function(index, category) {
          		   $('#top50cat').append('<a class="btn btn-lg btn-default" role="button" href="<%=request.getContextPath()%>/question/searchtag?tag='+category.catname+'">'+category.catname+'</a>&nbsp;');
                 });
           }
        });
        
		$.ajax({
			url:'<%=request.getContextPath()%>/user/latest',
			dataType:'json',
			success:function(data){
				$.each(data, function(index, userlog){
					$('#latestMsg').append('<tr><td  style="word-wrap: break-word" width="100%"><a href="<%=request.getContextPath()%>/user/'+userlog.uid+'">'+userlog.subject+'</a><br>'+userlog.ulog+'</td></tr>');
				});
			}
		});
  
	      $("#moreNewQuestion").click(function(event){

	          $.ajax( {
	             url:'<%=request.getContextPath()%>/question/latestQuestions?start='+qindex,
	             dataType: 'json',
	             success:function(data) {
	            	 var count = 0;
	            	   $.each(data, function(index, question) {
	               		   var tags = question.tag.split(" ");
	            		   var strtags='<div class="tags">';
	                  		
	    				   if(tags != ''){
	    	           		   $.each(tags,function(index,tag){
	    	           			 strtags=strtags+'<a  href="<%=request.getContextPath()%>/question/searchtag?tag='+tag+'">'+tag+'</a>';
	    	           		   });
	    				   }
	               			
	               			strtags=strtags+'</div>';
	               		   $('#questionlist').append('<div class="short-summary"><div class="question-summary-wrapper"><h4 class="list-heading">'+
	               		   		'<a href="<%=request.getContextPath()%>/question/${lang}/'+question.qid+'"><strong>'+ 
	    					 	question.question+'</strong></a></h4>'+
	    					 	//'<div class="userinfo"><a href="<%=request.getContextPath()%>/user/'+question.user.uid+'">'+question.user.name+'</a></div> '+
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

 
   QC.Login({
      btnId:"qqLoginBtn",
      size:"A_XL"
	});
   
    (function () {
      var po = document.createElement('script');
      po.type = 'text/javascript';
      po.async = true;
      po.src = 'https://plus.google.com/js/client:plusone.js';
      var s = document.getElementsByTagName('script')[0];
      s.parentNode.insertBefore(po, s);
    })();
  </script>

<%@include file="footer.jsp" %>
