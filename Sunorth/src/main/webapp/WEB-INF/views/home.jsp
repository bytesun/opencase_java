<%@include file="header.jsp" %>
 
 	<div class="row">
	<div class="col-md-9"> <!-- main right panel for question list -->
		<div class="row"> <!-- jumbotron row -->
			<!-- -------------Welcome ------------------- -->
			<div class="jumbotron">
			  <h1><spring:message code="home.welcome.ask" text="How can I do that?" /></h1>
			  <p><spring:message code="home.welcome.notic" text="Let's work together to deal with it!" /></p>
			  <p>
			  	<c:forEach items="${topcats}" var="cat">
			  	  <a class="btn btn-success btn-lg" role="button" href="<%=request.getContextPath()%>/cat/${lang}/${cat.cid}">
					<c:out value="${cat.catname}"/>
					</a>
			  	</c:forEach>
			  </p>
			</div>	  
		</div>
		
		<div class="row"><!-- question list -->
		<!-- order type -->
		<ul class="nav nav-tabs">
		
		  <li class="active"><a href="#latest" data-toggle="tab">
				<spring:message code="home.questionlist.ordertype.latest" text="Latest" />
			</a></li>
		<!-- 
		  <li><a href="#hot" data-toggle="tab">   
		  		<spring:message code="home.questionlist.ordertype.hottest" text="Hottest" />
			</a></li>
			 -->
		</ul>
		
		<!-- Tab panes -->
		<div class="tab-content">
		 <!-- -----------------proposals----------------- -->
			<div class="tab-pane active" id="latest">		
				<!-- --------------top question List-------------------- -->		
				<table id="questionTable" class="table" style="table-layout: fixed; width: 100%">
			  	</table>

			  	<div class="btn-group btn-group-justified">
				  <div class="btn-group">
				    <button type="button" id="moreNewQuestion" class="btn btn-default">
				    	<spring:message code="global.action.more" text="More..." />
				    </button>
				  </div>
				</div>
			  	</div>
			</div>
		</div><!-- end of question list -->
	</div><!-- end of row of jumbotron -->
	
	<!-- - RIGHT -->
	<div class="col-md-3">  <!-- right side panel -->
		<div class="row">
			<!-- AD -->
				<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
				<!-- performancengeering_main_Blog1_300x250_as -->
				<ins class="adsbygoogle"
				     style="display:inline-block;width:300px;height:250px"
				     data-ad-client="ca-pub-1018407477199873"
				     data-ad-slot="3366331746"></ins>
				<script>
				(adsbygoogle = window.adsbygoogle || []).push({});
				</script>
			<!-- top 20 category -->
			<div  class="panel panel-default">
				<div class="panel-heading">
					<h4><spring:message code="home.category.hotcats" text="Hotest Categories" /></h4>
				</div>
				<div id="top20cat" class="panel-body">
				</div>
			</div>
			<!-- top 20 tags -->
			<div id="top20tag" class="panel panel-default">
				<div class="panel-heading">
					<h4><spring:message code="home.tag.hot" text="Hotest Tags" /></h4>
				</div>
				<div id="top20tag" class="panel-body">
				</div>
			</div>
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
            url:'<%=request.getContextPath()%>/question/newQuestions',
            dataType: 'json',
            success:function(data) {
            	var count = 0;
           	   $.each(data, function(index, question) {
           		   var tags = question.tag.split(" ");
           		   var strtags='';
           		   $.each(tags,function(index,tag){
           			 strtags=strtags+'<a href="<%=request.getContextPath()%>/question/searchtag?tag='+tag+'"><span class="label label-default ">'+tag+'</span></a>';
           		   });
           			strtags=strtags+'</td></tr>';

           		   $('#questionTable').append('<tr><td style="word-wrap: break-word" width="80%">'+
           		   		'<a href="<%=request.getContextPath()%>/question/${lang}/'+question.qid+'"><span class="badge">'+question.answercnt+'</span>'+ 
					 	question.question+'</a></td><td align="right">'+strtags);
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
	               		   var strtags='';
	               		   $.each(tags,function(index,tag){
	               			 strtags=strtags+'<a href="<%=request.getContextPath()%>/question/searchtag?tag='+tag+'"><span class="label label-default ">'+tag+'</span></a>';
	               		   });
	               			strtags=strtags+'</td></tr>';

	               		   $('#questionTable').append('<tr><td style="word-wrap: break-word" width="80%">'+
	               		   		'<a href="<%=request.getContextPath()%>/question/${lang}/'+question.qid+'"><span class="badge">'+question.answercnt+'</span>'+ 
	    					 	question.question+'</a></td><td align="right">'+strtags);
	               		count = index;
	                   });
	            	   qindex = qindex+count+1;
	             }
	          });
	      });        

   });
   </script>	
	

<%@include file="footer.jsp" %>
