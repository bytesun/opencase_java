<%@include file="header.jsp" %>
 
 	<div class="row">
	<div class="col-md-8"> <!-- main right panel for question list -->

		<!-- -------------Welcome ------------------- -->
		<div class="jumbotron">
		  <h1><spring:message code="home.welcome.ask" text="How can I do that?" /></h1>
		  <p><spring:message code="home.welcome.notic" text="Let's work together to deal with it!" /></p>
		  <p>
		  	<c:forEach items="${topcats}" var="cat">
		  	  <a class="btn btn-success btn-lg" role="button" href="<%=request.getContextPath()%>/cat/${cat.cid}">
				<c:out value="${cat.catname}"/>
				</a>
		  	</c:forEach>
		  </p>
		</div>	  
		

		
		<!-- --------------top question List-------------------- -->		
		<table class="table" style="table-layout: fixed; width: 100%">
	  		<c:forEach items="${newquestions}" var="question">
	  			<tr><td style="word-wrap: break-word">  
	  				<span class="badge"><c:out value="${question.answercnt}"/></span> 
				 	<a href="<%=request.getContextPath()%>/question/${question.qid}">
	
				 	<c:out value="${question.question}"/></a>
				 	
	    		</td></tr>
	  		</c:forEach>
	  	</table>


	</div>
	<!-- - RIGHT -->
	<div class="col-md-4">  <!-- right side panel -->

	</div>
	</div>


	

<%@include file="footer.jsp" %>
