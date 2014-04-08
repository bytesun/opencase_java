<%@include file="header.jsp" %>

<div class="row">	
	<div class="col-md-8">
	 <h3>Search :<c:out value="${searchkey}"></c:out></h3>
	<!-- --------------All question list -->
	<table class="table">
  		<c:forEach items="${questions}" var="question">
  			<tr><td>  	
			 	<span class="badge"><c:out value="${question.rate}"></c:out></span>
			 	<span class="badge"><c:out value="${question.answercnt}"></c:out></span>
			 	<a href="<%=request.getContextPath()%>/question/${question.qid}"> <c:out value="${question.question}"/></a>
    		</td>
    		</tr>
  		</c:forEach>
  	</table>
 </div></div> 	
  	

<%@include file="footer.jsp" %>