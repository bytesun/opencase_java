<%@include file="header.jsp" %>


<div class="row">	
	<div class="col-md-8">
	<!-- --------------category -->
	<!-- parent category -->
			
			<ol class="breadcrumb">
			<c:choose>
				<c:when test="${upcat!=null}">
					<li>
						<a href="<%=request.getContextPath()%>/cat/${upcat.cid}"> 
							<c:out value="${upcat.catname}"/>
						</a>
					</li>
										
					<li class="active">
						<c:out value="${thecat.catname}"/>
					</li>
					 
				</c:when>
				<c:otherwise>
					<li><a href="<%=request.getContextPath()%>/"> 
					<spring:message code="common.top" text="Top" /></a>
					</li>
					<li class="active">
						<c:out value="${thecat.catname}"/>
					</li>
				</c:otherwise>
			
			</c:choose>
			
			<c:if test="${subcats!=null}">
			<div class="btn-group">
			  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
			    SubCategories <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu" role="menu">
				  <c:forEach items="${subcats}" var="category">
				    <li><a href="<%=request.getContextPath()%>/cat/${category.cid}"><c:out value="${category.catname}"/></a></li>
				 </c:forEach>
			  </ul>
			</div>	
			</c:if>
			
			<!-- new issue -->
			<li>
			<c:if test="${user!=null }">
			<button class="btn btn-success" data-toggle="modal" data-target=".newissue"><spring:message code="question.new" text="New Issue" /> </button>
			

				<div class="modal fade newissue" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				  <div class="modal-dialog modal-lg">
				    <div class="modal-content">
				    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/ask" method="POST">

						<div class="model-body">
						
							<input type="hidden" name="cid" value="${thecat.cid}">

						  	
						  	<input type="text" class="form-control input-default" name="questitle" placeholder="<spring:message code="common.question" text="Issue" /> " required>
							
							<textarea class="form-control" rows="5" name="question" placeholder="<spring:message code="common.description" text="Description" /> "></textarea>
							
							<input type="text" name="tag" placeholder="<spring:message code="common.tag" text="Tag" />"> 
							<button type="submit" class="btn btn-success">
							<spring:message code="question.consult" text="Consult" /></button>						
						</div>
					</form>		
				    </div>
				  </div>
				</div>	<!-- end vote dialog -->	
			</c:if>
			</li>
			</ol>	
			


	<!-- --------------All question list -->
	
	<table class="table" style="table-layout: fixed; width: 100%">
  		<c:forEach items="${questions}" var="question">
  			<tr><td style="word-wrap: break-word">  
  				<span class="badge"><c:out value="${question.answercnt}"/></span> 
			 	<a href="<%=request.getContextPath()%>/question/${question.qid}">

			 	<c:out value="${question.question}"/></a>
			 	
    		</td></tr>
  		</c:forEach>
  	</table>

 	<!-- ASK question   -------------------- -->

	<c:choose>
		<c:when test="${user==null}">
			<!-- relogin -->
			<spring:message code="question.loginnotice" text="Please login first to ask question:" />
			<a href="<%=request.getContextPath()%>/redirectLogin?cid=${thecat.cid}">
				<spring:message code="common.login" text="Login" />
			</a>
		</c:when>	
		<c:otherwise>
			<form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/ask" method="POST">
				<input type="hidden" name="cid" value="${thecat.cid}">
			  <div class="form-group">
			  	 <input type="text" class="form-control input-default" name="questitle" placeholder="<spring:message code="common.question" text="Issue" />" required>
			  </div>
			  
			  <div class="form-group">
				 <textarea class="form-control" rows="5" name="question" placeholder=""></textarea>
			  </div>
			   
			  <div class="form-group">
				
				<input type="text" name="tag" placeholder="<spring:message code="common.tag" text="Tag" />"> 
				
				<button type="submit" class="btn btn-success">
				<spring:message code="question.consult" text="Consult" /></button>
			  </div>	 
			</form>	
		</c:otherwise>
	</c:choose>
</div>

<div  class="col-md-4">

</div>
</div>
<%@include file="footer.jsp" %>