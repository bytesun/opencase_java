<%@include file="header.jsp" %>


<div class="row">
	<div class="col-md-8"> <!-- main right panel for question list -->
	<h2><c:out value="${user.name}"></c:out></h2>
	<!-- questions/answers/faoriates -->
	<ul class="nav nav-tabs">
	
	  <li class="active"><a href="#ulog" data-toggle="tab">
		<spring:message code="user.ulog" text="UserLog" />
	  </a></li>
		
	  <li><a href="#inbox" data-toggle="tab">
		<spring:message code="user.inbox" text="Inbox" />
	  </a></li>
			<!-- 				
	  <li><a href="#myquestions" data-toggle="tab">
		<spring:message code="user.myquestions" text="My Questions" />
		</a></li>
	
	  <li><a href="#myanswers" data-toggle="tab">
	  <c:out value="${question.answercnt}"/> 
	  <spring:message code="user.myanswers" text="My Answers" /></a></li>
	   -->
	</ul>
	
<div class="tab-content">
  <!-- ---------------todo list----------------- -->
	  <div class="tab-pane active" id="ulog">
	  
	  <div class="panel panel-default">
	  <!-- Default panel contents -->
	  <div class="panel-heading">

	  
	  </div>
		    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/user/ulog/new" method="POST">

				<div class="model-body">
				  	
				  	<input type="text" class="form-control input-default" name="subject" placeholder="<spring:message code="user.ulog.subject" text="subject" /> " required>
					
					<textarea class="form-control richtextarea"  name="ulog" placeholder=""></textarea>
					<input type="text" name="tag" value="<c:out value="${question.tag}"/>">

					<button type="submit" class="btn btn-success">
					<spring:message code="user.ulog.submit" text="Save" /></button>						
				</div>
			</form>		  
	

	</div>
		
			



  </div>

  <!-- ---------------inbox list----------------- -->
  <div class="tab-pane active" id="inbox">


  </div>
    
  
  <!-- -----------------questions----------------- -->
  <div class="tab-pane" id="myquestions">
	
  	<table class="table" style="table-layout: fixed; width: 100%">
  		<c:forEach items="${myquestions}" var="q">
  			<tr><td  style="word-wrap: break-word">  	
			 	<c:out value="${q.question}"  escapeXml="false"/> <label class="badge"><c:out value="${q.answercnt}"></c:out></label>
    		</td></tr>
  		</c:forEach>
  	</table>
  
  </div>
  
  <!-- -----------------answers----------------- -->
  <!-- 
  <div class="tab-pane" id="myanswers">

	  <table class="table" style="table-layout: fixed; width: 100%">
  		<c:forEach items="${myanswers}" var="answer">
  			<tr><td  style="word-wrap: break-word">  	
			 	<c:out value="${answer.answer}" escapeXml="false"/>
    		</td></tr>
  		</c:forEach>
  	</table>
	
  </div>
 -->
</div>	
	</div>
	<div class="col-md-4"> 
			<button class="btn btn-success" data-toggle="modal" data-target=".newtodo">
			<spring:message code="user.todo.button" text="New TODO" />
			</button>
			<div class="modal fade newtodo" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			  <div class="modal-dialog modal-lg">
			    <div class="modal-content">
			    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/todo/add" method="POST">
	
					<div class="model-body">
					  	
					  	<input type="text" class="form-control input-default" name="todo" placeholder="<spring:message code="user.todo.button" text="New todo item" /> " required>
						
						<textarea class="form-control" rows="5" name="note" placeholder=""></textarea>
						
						<spring:message code="user.todo.priority" text="Priority" /><input type="number" name="priority" placeholder="" value="10" min="1" max="10"> 
						<spring:message code="user.todo.deadline" text="Deadline" /><input type="date" name="deadline" placeholder="">
						
						<button type="submit" class="btn btn-success">
						<spring:message code="user.todo.add" text="Add TODO" /></button>						
					</div>
				</form>		
			    </div>
			  </div>
			</div>	<!-- end new todo dialog -->	
			
		  <!-- todo list -->

		   <table class="table">
		   <form action="<%=request.getContextPath()%>/todo/done" method="post">
		    <c:forEach items="${todos}" var="todo">
		    	<tr><td>
		    		<input type="checkbox" name="tid" value="<c:out value="${todo.tid}"/>">
		    		<c:out value="${todo.todo}"/></td>
		    	</tr>
		    </c:forEach>
				<tr><td>
		    	<button class="btn btn-default" type="submit" value="Done">
					<spring:message code="user.todo.done" text="Done" />
				</button></td></tr>
		    </form>
		    </table>
		</ol>
	</div>
</div>


<%@include file="footer.jsp" %>