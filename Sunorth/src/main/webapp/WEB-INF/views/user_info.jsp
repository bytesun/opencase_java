<%@include file="header.jsp" %>

 	<div class="row">
		<div class="col-md-8"> <!-- log view -->
		<h2>
			<c:out value="${userinfo.name}"/>
		</h2>
		<table class="table" style="table-layout: fixed; width: 100%">
	  		<c:forEach items="${ulogs}" var="userlog">
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
	  		</c:forEach>
			  	</table>
		</div> <!-- end of right main view -->
		
		<div class="col-md-4"> <!-- side column -->
		</div><!-- end of side -->
		
	</div>
























<!--  -->
<%@include file="footer.jsp" %>