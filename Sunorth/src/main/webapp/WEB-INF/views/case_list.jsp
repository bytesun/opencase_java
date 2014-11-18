<%@include file="header.jsp" %>

<!-- top section for the case name and desc -->
<div class="row">
	<div class="col-md-8"> <!-- main right panel for question list -->

		<table class="table">
			<c:forEach items="${cases}" var="ca">
				<tr>
					<td>
						<c:if test="${ca.status ==9}">
							<div class="accept-answer on" title="<spring:message code="case_list.status.tip" text="Closed" />"> </div>
						</c:if>
					
					</td>					
 				<td width="15%">[
          		   	<c:choose>
          		   		<c:when test="${ca.ctype==1}">
          		   			<spring:message code="case_list.ctype.idea" text="IDEA" />
          		   		</c:when>
          		   		<c:when test="${ca.ctype==2}">
          		   			<spring:message code="case_list.ctype.project" text="PROJECT" />
          		   		</c:when>
          		   		<c:when test="${ca.ctype==3}">
          		   		 	<spring:message code="case_list.ctype.issue" text="ISSUE" />
          		   		</c:when>
          		   	
          		   	
          		   	</c:choose>
          		   	]
          		   	</td>
          		   							
					<td><a href="<%=request.getContextPath()%>/case/${ca.caseid}"><c:out value="${ca.subject}"/></a></td>
					</tr>
			</c:forEach>
		</table>
	
	</div><!-- end list -->
	
		<div  class="col-md-4">

			<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
			<!-- sunorth_left -->
			<ins class="adsbygoogle"
			     style="display:inline-block;width:250px;height:600px"
			     data-ad-client="ca-pub-1018407477199873"
			     data-ad-slot="3799301345"></ins>
			<script>
			(adsbygoogle = window.adsbygoogle || []).push({});
			</script>			
	</div>	
</div>
<%@include file="footer.jsp" %>