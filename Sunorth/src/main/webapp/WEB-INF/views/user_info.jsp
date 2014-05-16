<%@include file="header.jsp" %>

 	<div class="row">
		<div class="col-md-9"> <!-- log view -->
		<div class="jumbotron">
			  <h1><c:out value="${userinfo.name}"/><small>--<c:out value="${userinfo.title}"/></small></h1>
			  <p>
			  	<c:out value="${userinfo.profile}"/>
			  </p>
			  
			</div>		

		
		<table id="ulogTable" class="table" style="table-layout: fixed; width: 100%">
			<c:set var="mindex" value="0"/>
			<c:set var="currentuid" value="${user.uid}"/>
			
	  		<c:forEach items="${ulogs}" var="userlog">
	  			<c:if test="${userlog.status !=0 || (userlog.status == 0 && currentuid==userlog.uid)}">
	  			<tr><td style="word-wrap: break-word">  
				 	<h3><a href="#"><c:out value="${userlog.subject}"/></a></h3>
				 	<p>
				 	<c:out value="${userlog.ulog}"  escapeXml="false"/>
				 	</p>
				 	<c:out value="${userlog.ltime}"/>
				 	<!-- tags -->
		 			<c:if test="${userlog.tag!=null && userlog.tag!=''}">
						<c:set var="tags" value="${fn:split(userlog.tag,' ')}"/>
						<c:forEach items="${tags}" var="tag">
							<a href="<%=request.getContextPath()%>/question/searchtag?tag=${tag}">
							<span class="label label-default "><c:out value="${tag}"/></span></a> 
						</c:forEach>
					</c:if>
					
					
	    		</td></tr>
	    		</c:if>
	    		<c:set var="mindex" value="${mindex+1}"/>
	  		</c:forEach>
	  		
		</table>
		<!-- more button -->
	  	<div class="btn-group btn-group-justified">
		  <div class="btn-group">
		    <button type="button" id="moreUlog" class="btn btn-default">
		    	<spring:message code="global.action.more" text="More..." />
		    </button>

		  </div>
		</div>
	  	
		</div> <!-- end of right main view -->
		
		<div class="col-md-3"> <!-- side column -->
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<!-- sunorth_left -->
<ins class="adsbygoogle"
     style="display:inline-block;width:300px;height:600px"
     data-ad-client="ca-pub-1018407477199873"
     data-ad-slot="3799301345"></ins>
<script>
(adsbygoogle = window.adsbygoogle || []).push({});
</script>	
		</div><!-- end of side -->
		
	</div>
<script type="text/javascript">
   $(document).ready(function() {
	   var lindex=${mindex+1};
	      $("#moreUlog").click(function(event){

	          $.ajax( {
	             url:'<%=request.getContextPath()%>/user/moreUlog/${userinfo.uid}?start='+lindex,
	             dataType: 'json',
	             success:function(data) {
	            	 var count = 0;
	            	   $.each(data, function(index, ulog) {
	               		   var tags = ulog.tag.split(" ");
	               		   var strtags='';
	               		   $.each(tags,function(index,tag){
	               			 strtags=strtags+'<a href="<%=request.getContextPath()%>/question/searchtag?tag='+tag+'"><span class="label label-default ">'+tag+'</span></a>';
	               		   });


	               		   $('#ulogTable').append('<tr><td style="word-wrap: break-word">'+
	               		   		'<h3><a href="#">'+ulog.subject+'</a></h3>'+ 
	               		   		'<p><c:out value="'+ulog.ulog+'"  escapeXml="false"/></p>'+
	        				 	ulog.ltime+strtags+'</td></tr>');
	               		count = index;
	                   });
	            	   lindex = lindex+count+1;
	             }
	          });
	      });        

   });
   </script>























<!--  -->
<%@include file="footer.jsp" %>