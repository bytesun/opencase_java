<%@include file="header.jsp" %>

<div class="row">	
	<div class="col-md-8">
	 <h3>Search :<c:out value="${searchkey}"></c:out></h3>
	<!-- --------------All question list -->
	<table class="table">
  		<c:forEach items="${questions}" var="question">
  			<tr><td>  	
			 	<span class="badge"><c:out value="${question.answercnt}"></c:out></span>
			 	<a href="<%=request.getContextPath()%>/question/${lang}/${question.qid}"> <c:out value="${question.question}"/></a>
				 	<!-- tags -->
				 			<c:if test="${question.tag!=null && question.tag!=''}">
								<c:set var="tags" value="${fn:split(question.tag,' ')}"/>
								<c:forEach items="${tags}" var="tag">
									<a href="<%=request.getContextPath()%>/question/searchtag?tag=${tag}">
									<span class="badge label-default pull-right"><c:out value="${tag}"/></span></a>
								</c:forEach>
							</c:if>			 	
    		</td>
    		</tr>
  		</c:forEach>
  	</table>
 </div></div> 	
  	

<%@include file="footer.jsp" %>