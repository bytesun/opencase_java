<%@include file="header.jsp" %>

<!-- top section for the case name and desc -->
<div class="row">
	<div class="col-md-12"> <!-- main right panel for question list -->
		<a href="<%=request.getContextPath()%>/case/init">New Case</a>
		<ul class="list-group">
			<c:forEach items="${cases}" var="ca">
				<li class="list-group-item">
					<a href="<%=request.getContextPath()%>/case/${ca.caseid}"><c:out value="${ca.subject}"/></a>
				</li>
			</c:forEach>
		</ul>
	
	</div>
</div>
<%@include file="footer.jsp" %>