<%@include file="header.jsp" %>

<div class="row">	
	<div class="col-md-9">
	 <h3>Search :<c:out value="${searchkey}"></c:out></h3>
	<!-- --------------All question list -->
	<table class="table" id="questionTable">
	<c:set var="qindex" value="0"/>
  		<c:forEach items="${questions}" var="question">
  			<tr><td  style="word-wrap: break-word" width="85%">  	
			 	<span class="badge"><c:out value="${question.answercnt}"></c:out></span>
			 	<a href="<%=request.getContextPath()%>/question/${lang}/${question.qid}"> <c:out value="${question.question}"/></a>
				 	<!-- tags -->
				</td>
				<td align="right"> 	
				 			<c:if test="${question.tag!=null && question.tag!=''}">
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
  	
 </div>
 
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
	             url:'<%=request.getContextPath()%>/question/searchMore?searchkey=${searchkey}&start='+qindex,
	             dataType: 'json',
	             success:function(data) {
	            	 var count = 0;
	            	   $.each(data, function(index, question) {
	               		   
	               		   var strtags='';
		               		if(question.tag!=null && question.tag!=undefined){
		               			var tags = question.tag.split(" ");
		               		   $.each(tags,function(index,tag){
		               			 strtags=strtags+'<a href="<%=request.getContextPath()%>/question/searchtag?tag='+tag+'"><span class="label label-default">'+tag+'</span></a>';
		               		   });
		               		}
	               			strtags=strtags+'</td></tr>';
		
	               		   $('#questionTable').append('<tr><td style="word-wrap: break-word" width="85%">'+
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