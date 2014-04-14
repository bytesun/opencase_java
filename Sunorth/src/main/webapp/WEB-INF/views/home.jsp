<%@include file="header.jsp" %>
 
 	<div class="row">
	<div class="col-md-8"> <!-- main right panel for question list -->

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
		<!-- order type -->
		<ul class="nav nav-tabs">
		
		  <li class="active"><a href="#latest" data-toggle="tab">
				<spring:message code="home.questionlist.ordertype.latest" text="Latest" />
			</a></li>
		
		  <li><a href="#hot" data-toggle="tab">   
		  		<spring:message code="home.questionlist.ordertype.hottest" text="Hottest" />
			</a></li>
		</ul>
		
		<!-- Tab panes -->
		<div class="tab-content">
		 <!-- -----------------proposals----------------- -->
			<div class="tab-pane active" id="latest">		
				<!-- --------------top question List-------------------- -->		
				<table class="table" style="table-layout: fixed; width: 100%">
			  		<c:forEach items="${newquestions}" var="question">
			  			<tr><td style="word-wrap: break-word" width=80%>  
			  				<span class="badge"><c:out value="${question.answercnt}"/></span> 
						 	<a href="<%=request.getContextPath()%>/question/${lang}/${question.qid}">	
						 	<c:out value="${question.question}"/></a>
						 	</td>
						 	<td align="right">
						 	<!-- tags -->
						 			<c:if test="${question.tag!=null && question.tag!=''}">
										<c:set var="tags" value="${fn:split(question.tag,' ')}"/>
										<c:forEach items="${tags}" var="tag">
											<a href="<%=request.getContextPath()%>/question/searchtag?tag=${tag}">
											<span class="label label-default "><c:out value="${tag}"/></span></a> 
											
										</c:forEach>
									</c:if>
			    		</td></tr>
			  		</c:forEach>
			  	</table>
			</div>
		</div>

	</div>
	<!-- - RIGHT -->
	<div class="col-md-4">  <!-- right side panel -->

	</div>
	</div>


	

<%@include file="footer.jsp" %>
