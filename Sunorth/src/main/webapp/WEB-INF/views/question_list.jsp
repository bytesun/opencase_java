<%@include file="header.jsp" %>


<div class="row">	
		<div class="col-md-9">
			<div class="row"><!-- menu -->
				
				<ol class="breadcrumb">
				<c:choose>
					<c:when test="${upcat!=null}">
						<li>
							<a href="<%=request.getContextPath()%>/cat/${lang}/${upcat.cid}"> 
								<c:out value="${upcat.catname}"/>
							</a>
						</li>
											
						<li class="active">
							<c:out value="${thecat.catname}"/>
						</li>
						 
					</c:when>
					<c:otherwise>
						<li><a href="<%=request.getContextPath()%>/"> 
						<spring:message code="common.top" text="Top" /></a>
						</li>
						<li class="active">
							<c:out value="${thecat.catname}"/>
						</li>
					</c:otherwise>
				
				</c:choose>
				
				<c:if test="${subcats!=null}">
				<div class="btn-group">
				  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
				    SubCategories <span class="caret"></span>
				  </button>
				  <ul class="dropdown-menu" role="menu">
					  <c:forEach items="${subcats}" var="category">
					    <li><a href="<%=request.getContextPath()%>/cat/${lang}/${category.cid}"><c:out value="${category.catname}"/></a></li>
					 </c:forEach>
				  </ul>
				</div>	
				</c:if>
				
				<!-- new issue -->
				<li>
				<c:if test="${user!=null }">
				<button class="btn btn-success" data-toggle="modal" data-target=".newissue"><spring:message code="question.new" text="New Issue" /> </button>
				
	
					<div class="modal fade newissue" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
					  <div class="modal-dialog modal-lg">
					    <div class="modal-content">
					    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/ask" method="POST">
	
							<div class="model-body">
							
								<input type="hidden" name="cid" value="${thecat.cid}">
	
							  	
							  	<input type="text" class="form-control input-default" name="questitle" placeholder="<spring:message code="common.question" text="Issue" /> " required>
								
								<textarea class="form-control richtextarea" rows="5" name="question" placeholder="<spring:message code="common.description" text="Description" /> "></textarea>
								
								<input type="text" name="tag" placeholder="<spring:message code="common.tag" text="Tag" />"> 
								<button type="submit" class="btn btn-success">
								<spring:message code="question.consult" text="Consult" /></button>						
							</div>
						</form>		
					    </div>
					  </div>
					</div>	<!-- end vote dialog -->	
				</c:if>
				</li>
				</ol>	
				
		</div>
		<div class="row"><!-- --------------All question list -->

		<table class="table" id="questionTable" style="table-layout: fixed; width: 100%">
			<c:set var="qindex" value="0"/>
	  		<c:forEach items="${questions}" var="question">
	  			<tr><td style="word-wrap: break-word" width="80%">  
	  				<span class="badge"><c:out value="${question.answercnt}"/></span> 
				 	<a href="<%=request.getContextPath()%>/question/${lang}/${question.qid}">
	
				 	<c:out value="${question.question}"/></a>
				 	</td>
				 	<td align="right">
					<c:if test="${question.tag!=null}">
						<c:set var="tags" value="${fn:split(question.tag,' ')}"/>
						<c:forEach items="${tags}" var="tag">
							<a href="<%=request.getContextPath()%>/question/searchtag?tag=${tag}">
							<span class="label label-default"><c:out value="${tag}"/></span></a>
						</c:forEach>
					</c:if>			 	
	    		</td></tr>
	    		<c:set var="qindex" value="${qindex+1}"/>
	  		</c:forEach>
	  	</table>
	  	
	  	<div class="btn-group btn-group-justified">
		  <div class="btn-group">
		    <button type="button" id="moreNewQuestion" class="btn btn-default">
		    	<spring:message code="global.action.more" text="More..." />
		    </button>
		  </div>
		</div>
		
		</div><!-- end of question list -->
	 	<div class="row"><!-- ASK question   -------------------- -->
	
			<c:choose>
				<c:when test="${user==null}">
					<!-- relogin -->
					<spring:message code="question.loginnotice" text="Please login first to ask question:" />
					<a href="<%=request.getContextPath()%>/redirectLogin?cid=${thecat.cid}">
						<spring:message code="common.login" text="Login" />
					</a>
				</c:when>	
				<c:otherwise>
					<form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/ask" method="POST">
						<input type="hidden" name="cid" value="${thecat.cid}">
					  <div class="form-group">
					  	 <input type="text" class="form-control input-default" name="questitle" placeholder="<spring:message code="common.question" text="Issue" />" required>
					  </div>
					  
					  <div class="form-group">
						 <textarea class="form-control richtextarea" rows="5" name="question" placeholder=""></textarea>
					  </div>
					   
					  <div class="form-group">
						
						<input type="text" name="tag" placeholder="<spring:message code="common.tag" text="Tag" />"> 
						
						<button type="submit" class="btn btn-success">
						<spring:message code="question.consult" text="Consult" /></button>
					  </div>	 
					</form>	
				</c:otherwise>
			</c:choose>
		</div>
	</div><!-- end of right side -->
	

	<!-- left side -->
	<div  class="col-md-3">
			<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
			<!-- sunorth-leftside-large -->
			<ins class="adsbygoogle"
			     style="display:inline-block;width:300px;height:600px"
			     data-ad-client="ca-pub-1018407477199873"
			     data-ad-slot="1749098942"></ins>
			<script>
			(adsbygoogle = window.adsbygoogle || []).push({});
			</script>
		
		
	</div>

</div><!-- end of top row -->
<script type="text/javascript">
   $(document).ready(function() {
		var qindex=${qindex+1};
	      $("#moreNewQuestion").click(function(event){

	          $.ajax( {
	             url:'<%=request.getContextPath()%>/question/moreByCID/${thecat.cid}?start='+qindex,
	             dataType: 'json',
	             success:function(data) {
	            	 var count = 0;
	            	   $.each(data, function(index, question) {
	               		   
	               		   var strtags='';
	               		   if(question.tag!=null && question.tag!=undefined){
	               				var tags = question.tag.split(" ");
		               		   $.each(tags,function(index,tag){
		               			 strtags=strtags+'<a href="<%=request.getContextPath()%>/question/searchtag?tag='+tag+'"><span class="label label-default ">'+tag+'</span></a>';
		               		   });
	               		   }
	               			strtags=strtags+'</td></tr>';
						   console.log(strtags);
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