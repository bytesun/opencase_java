<%@include file="header.jsp" %>

<div class="row">
	<!-- --------------category -->
		
		<c:forEach items="${categories}" var="cat">
			<a href="<%=request.getContextPath()%>/cat/admin/${cat.cid}">
			<span class="label label-info">
			 <c:out value="${cat.catname}"/></span></a> 
		</c:forEach>

</div>
<div class="row">
	<form action="<%=request.getContextPath()%>/cat/add" method="post">
		<input type="hidden" value="${pid}" name="pid">
		Sub Category : <input type="text" name="catName">
		<input type="submit" value="Save">
	</form>
	
</div>


<%@include file="footer.jsp" %>