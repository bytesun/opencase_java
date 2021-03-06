<%@include file="header.jsp" %>

 	<div class="row">
		<div class="col-md-9"> <!-- log view -->
		<div class="jumbotron">
			  <img alt="" src="${userinfo.photo1}">
			  <h1><c:out value="${userinfo.name}"/>
			  <small>--<c:out value="${userinfo.title}"/></small></h1>
			  <h2><a><c:out value="${userinfo.skill}"></c:out></a></h2>
			  <p>
			  	<c:out value="${userinfo.profile}"/>
			  </p>
			  <c:if test="${user!=null && user.uid!=userinfo.uid}">
				<div id="follow_user_div"><button id="follow_user" class="btn btn-success">
					<spring:message code="global.action.follow" text="Follow" />
				</button></div>
			  </c:if>
			  
		</div>		

		<!-- user tab -->
		<ul class="nav nav-tabs">
		  
		  <li  class="active"><a href="#message" data-toggle="tab">
			<spring:message code="user.info.message" text="Message" />
		  </a></li>	
		  
		  <li><a href="#resume" data-toggle="tab">
			<spring:message code="user.info.resume" text="Resume" />
		  </a></li>
		  
		</ul>
			
		<!-- tab detail -->
		<div class="tab-content">
		  <div class="tab-pane active" id="message">
			<table id="ulogTable" class="table" style="table-layout: fixed; width: 100%">
				<c:set var="mindex" value="0"/>
				<c:set var="currentuid" value="${user.uid}"/>
				
		  		<c:forEach items="${ulogs}" var="userlog">
		  			<c:if test="${userlog.status !=0 || (userlog.status == 0 && currentuid==userlog.uid)}">
		  			<tr><td style="word-wrap: break-word">  
					 	<h3><c:out value="${userlog.subject}"/></h3>
					 	<p>
					 	<c:out value="${userlog.ulog}"  escapeXml="false"/>
					 	</p>
					 	<c:out value="${userlog.ltime}"/>
					 	<!-- tags
			 			<c:if test="${userlog.tag!=null && userlog.tag!=''}">
							<c:set var="tags" value="${fn:split(userlog.tag,' ')}"/>
							<c:forEach items="${tags}" var="tag">
								<a href="<%=request.getContextPath()%>/question/searchtag?tag=${tag}">
								<span class="label label-default "><c:out value="${tag}"/></span></a> 
							</c:forEach>
						</c:if>
						 -->
						
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
		</div>
		<!-- resume -->
		 <div class="tab-pane" id="resume">
		 	<c:out value="${userinfo.resume}" escapeXml="false"></c:out>
		 </div>
		</div>
		
		
			

	  	
		</div> <!-- end of right main view -->
		
		<div class="col-md-3"> <!-- side column -->
		
			<!-- AD -->
				<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
				<!-- p8ecm_main_Blog1_300x250_as -->
				<ins class="adsbygoogle"
				     style="display:inline-block;width:300px;height:250px"
				     data-ad-client="ca-pub-1018407477199873"
				     data-ad-slot="5142696541"></ins>
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
	      //follow user
	      $("#follow_user").click(function(event){
	    	  console.log('following a user');
	    	  var uid=${userinfo.uid};
	    	  var request = $.ajax({
	    		  type:"POST",
	    		  url:'<%=request.getContextPath()%>/user/follow',
	    		  dataType:"json",
	    		  //contentType :"application/json; charset=utf-8",
	    		  data:{"fid":uid,"ftype":1,"followOp":1}
	
	    	  });
	    	  request.done(function( msg ) {
	    			  $("#follow_user_div").html('');
	    		});
	    		 
	    	  request.fail(function( jqXHR, textStatus ) {
	    			console.log("Request failed: " + textStatus +jqXHR);
	    		});
	    	  
	      });

   });
   </script>























<!--  -->
<%@include file="footer.jsp" %>