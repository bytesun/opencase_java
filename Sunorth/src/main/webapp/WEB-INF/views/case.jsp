<%@include file="header.jsp" %>

<!-- top section for the case name and desc -->
<div class="row">
	<div class="col-md-12"> <!-- main right panel for question list -->
		<div class="panel panel-default">
			<div class="panel-heading">
			<h2><c:out value="${thecase.subject}"/></h2>
			</div>
			
			<div class="panel-body">
				<c:out value="${thecase.desc}" escapeXml="false"></c:out>
			</div>
			
		</div>
		
	</div><!-- end of left side -->
</div>		
<!-- end top  -->


<!-- middle section for phase  -->
<div class="row">   	
		<div  class="col-md-12">
			<div class="row"><!-- phase bar -->
			<div  class="col-md-12"> 
				<!-- phase number list -->
				
				<div class="btn-group">
					<c:if test="${thecase.phaseid>0}">
						<c:forEach var="i" begin="1" end="${thecase.phaseid}">
							<c:if test="${i==thephase.phaseid}">
								<a class="btn btn-success" href="<%=request.getContextPath()%>/case/${thecase.caseid}/${i}"><c:out value="${i}"/></a>
							</c:if>
							<c:if test="${i!=thephase.phaseid}">
								<a class="btn btn-default" href="<%=request.getContextPath()%>/case/${thecase.caseid}/${i}"><c:out value="${i}"/></a>
							</c:if>
							
						</c:forEach>
					</c:if>
					
				</div>	
				
				<c:if test="${(user != null && user.uid==thecase.uid || cookie.mysuncase.value==thecase.caseid) && thecase.status!=9}">
					<button class="btn btn-primary" data-toggle="modal" data-target=".newphase">
					<spring:message code="case.phase.btn.new" text="Follow UP" /> </button>
				</c:if>
				</div>		
			</div> <!-- end phase bar -->
			
			<c:if test="${thephase != null}">
		<div class="row">
			<div class="col-md-4">	
				<!-- the current phase desc -->
				<div class="panel panel-success">
					<div class="panel-heading">
					<c:out value="${thephase.phase}"/>
					</div>
					<div class="panel-body">
					<p><c:out value="${thephase.note}" escapeXml="false"></c:out></p>	
					</div>
								
				</div>
			</div>
			<!-- right : item list -->
			<div class="col-md-8">
				<div id="phase-item-list">
					<table class="table">
						<c:forEach items="${theitems}" var="item">
							<tr>
				
								<td><c:out value="${item.item}"/></td>
								<td width="5%"></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="2" align="right">
								<c:if test="${(user != null && user.uid==thecase.uid|| cookie.mysuncase.value==thecase.caseid) && thecase.phaseid==thephase.phaseid && thecase.status!=9}">
									<a  id="addItem" href=""><spring:message code="case.item.btn.new" text="New Check-Item" /></a>
								</c:if>							
							</td>
						</tr>
					</table>
				</div>
			</div>
		
		</div>
		</c:if>
</div><!-- end of the phase section -->


<!-- comment/suggestion from others -->
<div class="row">
	<div class="col-md-12">
			<div class="row">
			<div  class="col-md-12"> 
				<c:if test="${ thecase.phaseid==thephase.phaseid}">	
					<hr/>
					<div id="comment-list">
						<a class="btn btn-primary" id="addComment" href=""><spring:message code="case.comment.btn.new" text="Add" /></a> 
						<spring:message code="case.comment.label.comments" text="Comment / Suggestion / Proposal..." />
					</div>
				</c:if>
				<table class="table">
					
					<c:forEach var="comment" items="${comments}">
						<tr><td width="20%">
							 [<c:out value="${comment.createtime}"/>]
							 </td>
							 <td> <c:out value="${comment.comment}"/>
							 </td> 
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>

</div>

		<!-- dialog : new phase -->		
			<div class="modal fade newphase" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
					  <div class="modal-dialog modal-lg">
					    <div class="modal-content">
					    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/case/newphase" method="POST">
							<input type="hidden" name="thephaseid" value="${thecase.phaseid}">
							<input type="hidden" name="thecaseid" value="${thecase.caseid}">
							
							<div class="model-body">
						  	
							  	
						  	 	<div class="form-group"> 
						  	 		<div class="rows">
										<div class="col-md-12">
											<div class="col-lg-12">	
											<input type="text" class="form-control input-default" name="phase" placeholder="<spring:message code="case.form.phase.phase.placeholder" text="Input follow up things..." />" required>											
											</div>
										</div>
									</div>
								</div>								

						  	 	<div class="form-group"> 
						  	 		<div class="rows">
										<div class="col-md-12">
											<div class="col-lg-12">	
											<spring:message code="case.form.phase.description" text="Description" />
											<textarea class="form-control richtextarea" rows="5" name="description" placeholder=" "></textarea>											
											</div>
										</div>
									</div>
								</div>	
						  	 	<div class="form-group"> 
						  	 		<div class="rows">
										<div class="col-md-12">
											<div class="col-lg-12">	
												<input type="text" name="tag" placeholder="<spring:message code="case.form.phase.tag" text="Tag" />"> 
												<input type="checkbox" name="isClose"><spring:message code="case.form.phase.label.closecase" text="Close Case?" />
												<button type="submit" class="btn btn-primary">
												<spring:message code="case.form.phase.btn.save" text="Save" /></button>												
											</div>
										</div>
									</div>
								</div>																
					
							</div>
						</form>		
					    </div>
					  </div>
					</div>	<!-- end phase dialog -->	

<script type="text/javascript">
   $(document).ready(function() {
  	    
   	    $("#addItem").click(function(e){
   	    	event.preventDefault();
   	    	$("#phase-item-list").append('<form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/case/newitem" method="POST">'+
   	    			'<input type="hidden" name="thephaseid" value="${thecase.phaseid}">'+
							'<input type="hidden" name="thecaseid" value="${thecase.caseid}">'+
   	    			'<input  name="item" size="100" required/><button type="submit" class="btn btn-primary">'+
							'<spring:message code="case.form.item.btn.new" text="Save" /></button>&nbsp;'+
							//'<button type="submit" class="btn btn-default">Cancel</button>'+
							'</form>');
   	    	$("#addItem").hide();
   	    });
   	    
   	    
   	    $("#addComment").click(function(e){
   	    	event.preventDefault();
   	    	$("#comment-list").append('<form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/case/newcomment" method="POST">'+
   	    			'<input type="hidden" name="thephaseid" value="${thecase.phaseid}">'+
							'<input type="hidden" name="thecaseid" value="${thecase.caseid}"/>'+
   	    			'<textarea  name="comment" cols="100" rows="6" required></textarea><br><button type="submit" class="btn btn-primary">'+
							'<spring:message code="case.form.comment.btn.save" text="Save" /></button>&nbsp;'+
							//'<button type="submit" class="btn btn-default">Cancel</button>'+
							'</form>');
   	    	$("#addComment").hide();
   	    });
   });
   </script>

<%@include file="footer.jsp" %>

