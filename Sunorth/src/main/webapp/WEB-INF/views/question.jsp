<%@include file="header.jsp" %>

<div class="row">
	<div class="col-md-8"> <!-- main right panel for question list -->
	<jsp:useBean id="dateValue" class="java.util.Date" />
	<!-- --------------Question detail -->
	<ol class="breadcrumb">
		<li><a href="<%=request.getContextPath()%>/cat/${pcategory.cid}">
			<c:out value="${pcategory.catname}"/>
		</a></li>
		<li>
		<a href="<%=request.getContextPath()%>/question/${pquestion.qid}">
			<c:out value="${pquestion.question}"/>
		</a></li>
		<!-- tags list -->
	</ol>
	<div>
		<c:if test="${question.tag!=null}">
			<c:set var="tags" value="${fn:split(question.tag,' ')}"/>
			<c:forEach items="${tags}" var="tag">
				<a href="<%=request.getContextPath()%>/question/searchtag?tag=${tag}">
				<span class="label label-success"><c:out value="${tag}"/></span></a>
			</c:forEach>
		</c:if>
		
	</div>


	<div class="panel panel-default">
	  <div class="panel-heading"><span style="word-break:break-all;">
	  <h2> <c:out value="${question.question}"/></h2></span></div>
	  <div class="panel-body">
	   		<c:out value="${question.description}"  escapeXml="false"/>
	  </div>
	  <div class="panel-foot">
		<div>
	  		<c:out value="${question.user.name}"/>
	  	</div>
	  </div>
	</div>		
	<!-- comments -->
		<div class="panel-group" id="accordion">
		  <div class="panel panel-default">
		    <div class="panel-heading">
		      <h4 class="panel-title">

  				<!-- add comment -->
  				<c:if test="${user!=null}">
		         <button class="btn btn-default" data-toggle="modal" data-target=".addcomment"><spring:message code="question.comment.add" text="Add Comment" /> </button>
		         <div class="modal fade addcomment" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
				  <div class="modal-dialog modal-lg">
				    <div class="modal-content">
				    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/comment" method="POST">

						<div class="model-body">
							<input type="hidden" name="qid" value="${question.qid}">
							<textarea class="form-control" rows="5" name="comment" placeholder=""></textarea>
							<button type="submit" class="btn btn-success">
							<spring:message code="question.comment.add" text="Add Comment" /></button>						
						</div>
					</form>		
				    </div>
				  </div>
				</div>	<!-- end add comment-->	
				</c:if> <!-- make sure user sessiion -->	
				<a data-toggle="collapse" data-parent="#accordion" href="#comments">
		         <spring:message code="question.comment.title" text="Comments" />
		        </a>
		      </h4>
				      
		    </div>
		    <div id="comments" class="panel-collapse collapse">
		      <div class="panel-body">
		       <table class="table" style="table-layout: fixed; width: 100%">
			  		<c:forEach items="${comments}" var="comment">
			  			<tr><td  style="word-wrap: break-word">  	
						 	<c:out value="${comment.comment}"  escapeXml="false"/><br>
						 	<!-- time -->
					  		<c:out value="${comment.user.name}"/>
			    		</td></tr>
			  		</c:forEach>
			  	</table>
		      </div>
		    </div>
		  </div>

		</div>	

	<!-- -------------Add more question or comments  -->
<ul class="nav nav-tabs">

  <li class="active"><a href="#proposals" data-toggle="tab">
  	<c:out value="${question.answercnt}"/>
	<spring:message code="question.proposal.title" text="Proposals" />
	</a></li>

  <li><a href="#followup" data-toggle="tab">   
  <spring:message code="question.followup.title" text="FollowUp" /></a></li>
</ul>

<!-- Tab panes -->
<div class="tab-content">
 <!-- -----------------proposals----------------- -->
	<div class="tab-pane active" id="proposals">
		
		<table class="table" style="table-layout: fixed; width: 100%">
	  		<c:forEach items="${answers}" var="answer">
	  			<tr><td  style="word-wrap: break-word">  	
				 	 
				 	<h3>
				 	<button class="btn btn-success" data-toggle="modal" data-target=".proposalVote">
				 	<c:out value="${answer.rate}"/></button>
				 	
				 	<c:out value="${answer.user.name}"/></h3>
				 	 
				 	<c:out value="${answer.answer}"  escapeXml="false"/><br>
			  		
			  		<!-- vote -->
				 	<c:if test="${user!=null}">
						<!-- vote comment dialog -->
						<div class="modal fade proposalVote" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
						  <div class="modal-dialog modal-lg">
						    <div class="modal-content">
						  	 <form class="form-horizontal" action="<%=request.getContextPath()%>/question/answer/vote" method="POST">
			
							  		<input type="hidden" name="qid" value="${question.qid}">
							  		<input type="hidden" name="aid" value="${answer.aid}">
							  		<textarea name="vcomment"  placeholder="<spring:message code="vote.comment.notice" text="Please give a comment about your vote..." />"></textarea>
								  	<input type="submit" class="btn btn-success" name="vup" value="<spring:message code="vote.up" text="Vup" />">
								  	<input type="submit" class="btn btn-success" name="vdown" value="<spring:message code="vote.down" text="Vdown" />">
									<c:if test="${user.uid!=answer.user.uid}">
										<input type="checkbox" name="isanswer" value="1"><spring:message code="question.proposal.accept" text="Accept the proposal" />
									</c:if>
										
							  </form>		
						    </div>
						  </div>
						</div>	<!-- end vote dialog -->		 	
					</c:if>


	    		</td></tr>
	  		</c:forEach>
	  	</table>
		
		<c:choose>
			<c:when test="${user==null}">
				<!-- relogin -->
				<spring:message code="question.loginnotice" text="Please login to answer the question:" />
				<a href="<%=request.getContextPath()%>/redirectLogin?qid=${question.qid}">
					<spring:message code="common.login" text="Login" />
				</a>
			</c:when>
	
			<c:when	 test="${user!=null && !question.resolved}">
				<form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/answer" method="POST">
					<input type="hidden" name="qid" value="${question.qid}">
				  
				  <div class="form-group">
					 <textarea class="form-control" rows="5" name="answer" placeholder="" ></textarea>
				  </div>
				   
				  <div class="form-group">
					
					<button type="submit" class="btn btn-success">
					<spring:message code="question.proposal.submit" text="My Proposal" /></button>
					<c:if test="${user.uid==question.user.uid}">
						<input type="checkbox" value="IsResolved" name="isresolved">
						<spring:message code="common.itsresolved" text="IsResolved"/>
					</c:if>
				  </div>	 
				</form>	
			</c:when>
			
		</c:choose>	
	  </div>

   
  <!-- ---------------followup list----------------- -->
      <div class="tab-pane" id="followup">  	
			<table class="table" style="table-layout: fixed; width: 100%">
		  		<c:forEach items="${followups}" var="question">
		  			<tr><td style="word-wrap: break-word">  
		  				<span class="badge"><c:out value="${question.answercnt}"/></span> 
					 	<a href="<%=request.getContextPath()%>/question/${question.qid}">
		
					 	<c:out value="${question.question}"/></a>
					 	
		    		</td></tr>
		  		</c:forEach>
		  	</table> 
	  	
	
		<!-- follow up with a question -->
		<c:choose>
			<c:when test="${user==null}">
				<!-- relogin -->
				<spring:message code="question.loginnotice" text="Please login first to consult a issue:" />
				<a href="<%=request.getContextPath()%>/redirectLogin?qid=${question.qid}">
					<spring:message code="common.login" text="Login" />
				</a>
			</c:when>	
			<c:otherwise>
				<form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/question/ask" method="POST">
					<input type="hidden" name="pid" value="${question.qid}">
				  <div class="form-group">
				  	 <input type="text" class="form-control input-default" name="questitle" placeholder="<spring:message code="question.followup.title" text="FollowUp" />" required>
				  </div>
				  
				  <div class="form-group">
					 <textarea class="form-control" rows="5" name="question" placeholder="<spring:message code="common.description" text="Description" />"></textarea>
				  </div>
				   
				  <div class="form-group">
					
					<input type="text" name="tag" placeholder="<spring:message code="common.tag" text="Tag" />"> 
					
					<button type="submit" class="btn btn-success">
					<spring:message code="question.consult" text="Consult" /></button>
				  </div>	 
				</form>	
			</c:otherwise>
		</c:choose>	
		</div><!-- end of follow tab	 -->	
	</div><!-- END tab panel -->
	</div><!-- end of left column -->
	
	
	<!-- right column -->
		<div  class="col-md-4">
		
		</div>
</div>

<%@include file="footer.jsp" %>

