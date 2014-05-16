<%@include file="header.jsp" %>


<div class="row">
	<div class="col-md-8"> <!-- main right panel for question list -->
	<h2>
	<a href="<%=request.getContextPath()%>/user/${user.uid}"><c:out value="${user.name}"/></a>
	
	<small><a href="#" data-toggle="modal" data-target=".edituser"><spring:message code="global.action.edit" text="Edit" /></a></small>
	
	</h2>
	
<!-- edit user -->				 	
	<div class="modal fade edituser" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	  	 <form class="form-horizontal" action="<%=request.getContextPath()%>/user/update" method="POST">
	  	 	<div class="form-group"> 
	  	 		<div class="rows">
					<div class="col-md-12">
						<div class="col-lg-12">
						</div>
					</div>
				</div>
			</div>		  	 
	  	 	<div class="form-group"> 
	  	 		<div class="rows">
					<div class="col-md-12">
						<div class="col-lg-12">
							<spring:message code="user.profile.name" text="Name" />:
							<input type="text" class="input-default" name="name" placeholder="<spring:message code="user.profile.name" text="Name" />" value="<c:out value="${user.name}"/>">
						</div>
					</div>
				</div>
			</div>	
	  	 	<div class="form-group"> 
	  	 		<div class="rows">
					<div class="col-md-12">
						<div class="col-lg-12">					
							<spring:message code="user.profile.title" text="Title" />:
							<input type="text" class="form-control input-default" name="title" placeholder="" value="<c:out value="${user.title}"  escapeXml="false"/>">
						</div>
					</div>
				</div>
			</div>					
	  	 	<div class="form-group"> 
	  	 		<div class="rows">
					<div class="col-md-12">
						<div class="col-lg-12">		
							<spring:message code="user.profile.summary" text="Summary" />:
							<textarea class="form-control" name="profile"  placeholder="<spring:message code="user.profile.summary" text="Summary" />">
							<c:out value="${user.profile}"/>
							</textarea>
						</div>
					</div>
				</div>
			</div>		
	  	 	<div class="form-group"> 
	  	 		<div class="rows">
					<div class="col-md-12">
						<div class="col-lg-12">						
							<spring:message code="user.profile.resume" text="Resume" />:
					  		<textarea class="form-control richtextarea" name="resume" rows="10" placeholder="<spring:message code="user.profile.resume" text="Resume" />">
					  		<c:out value="${user.resume}"  escapeXml="false"/>
					  		</textarea>
						  	<input type="submit" class="btn btn-success" name="submit" value="<spring:message code="global.action.save" text="Save" />">
						</div>
					</div>
				</div>
			</div>						
		  </form>		
	    </div>
	  </div>
	</div>	<!-- end of edit dialog -->		  	 	
	
	<!-- questions/answers/faoriates -->
	<ul class="nav nav-tabs">
	  
	  <li  class="active"><a href="#home" data-toggle="tab">
		<spring:message code="user.console.home" text="Home" />
	  </a></li>	
	  
	  <li><a href="#issues" data-toggle="tab">
		<spring:message code="user.myquestions" text="Issues" />
	  </a></li>

	  <li><a href="#projects" data-toggle="tab">
		<spring:message code="user.myprojects" text="Projects" />
	  </a></li>
	</ul>
	
	<div class="tab-content">
	  <!-- ---------------Home: message/todo----------------- -->
	  <div class="tab-pane active" id="home">
		  <!-- todo list -->

		   <table class="table">
			    <c:forEach items="${todos}" var="todo">
			    	<tr><td>
			    		<c:out value="${todo.todo}"/></td>
			    	</tr>
			    </c:forEach>
		    </table>	
	
	  </div>	
	  <!-- ---------------Issue list----------------- -->
		  <div class="tab-pane" id="issues">
		  
		  	<table class="table" style="table-layout: fixed; width: 100%">
		  		<c:forEach items="${myquestions}" var="q">
		  			<tr><td  style="word-wrap: break-word">  	
		  				<label class="badge"><c:out value="${q.answercnt}"></c:out></label>
					 	<a href="<%=request.getContextPath()%>/question/${lang}/${q.qid}">
					 	<c:out value="${q.question}"  escapeXml="false"/>
					 	</a> 
		    		</td></tr>
		  		</c:forEach>
		  	</table>		  
		  
	  </div><!-- end of tab panel -->
	
	</div>	<!-- end of tab-content -->
	
	</div><!-- end of left side -->
	<div class="col-md-4"> 
			<!-- new todo -->
			<button class="btn btn-success" data-toggle="modal" data-target=".newtodo">
				<spring:message code="user.todo.button" text="New TODO" />
			</button>
			
			<button class="btn btn-success" data-toggle="modal" data-target=".newlog">
			<spring:message code="user.ulog.newbtn" text="Write Log" />
			</button>
			
			<!-- new todo diag -->		
			<div class="modal fade newtodo" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			  <div class="modal-dialog modal-lg">
			    <div class="modal-content">
			    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/todo/add" method="POST">
	
					<div class="model-body">
				  	 	<div class="form-group"> 
				  	 		<div class="rows">
								<div class="col-md-12">
									<div class="col-lg-12">					  	
									  	
									</div>
								</div>
							</div>
						</div>						
				  	 	<div class="form-group"> 
				  	 		<div class="rows">
								<div class="col-md-12">
									<div class="col-lg-12">		
										<spring:message code="user.todo.button" text="New todo item" />			  	
									  	<input type="text" class="form-control input-default" name="todo" placeholder=" " required>
									</div>
								</div>
							</div>
						</div>	
			  	 	<div class="form-group"> 
			  	 		<div class="rows">
							<div class="col-md-12">
								<div class="col-lg-12">	
										<spring:message code="user.todo.note" text="Note" />									
										<textarea class="form-control" rows="5" name="note" placeholder=""></textarea>
									</div>
								</div>
							</div>
						</div>											
			  	 	<div class="form-group"> 
			  	 		<div class="rows">
							<div class="col-md-12">
								<div class="col-lg-12">												
										<spring:message code="user.todo.priority" text="Priority" /><input type="number" name="priority" placeholder="" value="10" min="1" max="10"> 
										<spring:message code="user.todo.deadline" text="Deadline" /><input type="date" name="deadline" placeholder="">
										
										<button type="submit" class="btn btn-success">
										<spring:message code="user.todo.add" text="Add TODO" /></button>	
									</div>
								</div>
							</div>
						</div>																
					</div>
				</form>		
			    </div>
			  </div>
			</div>	<!-- end new todo dialog -->	
			<!-- new log diag -->
			
			<div class="modal fade newlog" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
			  <div class="modal-dialog modal-lg">
			    <div class="modal-content">
				    <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/user/ulog/new" method="POST">
		
						<div class="model-body">
				  	 	<div class="form-group"> 
				  	 		<div class="rows">
								<div class="col-md-12">
									<div class="col-lg-12">	
									</div>
								</div>
							</div>
						</div>						
				  	 	<div class="form-group"> 
				  	 		<div class="rows">
								<div class="col-md-12">
									<div class="col-lg-12">	
										<spring:message code="user.ulog.subject" text="subject" />				  	
						  				<input type="text" class="form-control input-default" name="subject" placeholder=" " required>									  	
									</div>
								</div>
							</div>
						</div>	
				  	 	<div class="form-group"> 
				  	 		<div class="rows">
								<div class="col-md-12">
									<div class="col-lg-12">	
										<spring:message code="user.ulog.ulog" text="User input log here.." />				  	
										<textarea class="richtextarea"  name="ulog" placeholder=""></textarea>
									  	
									</div>
								</div>
							</div>
						</div>	
				  	 	<div class="form-group"> 
				  	 		<div class="rows">
								<div class="col-md-12">
									<div class="col-lg-12">					
										<input  type="text" name="tag" value="" placeholder="<spring:message code="user.ulog.tag" text="tags" />">									  	
										<button type="submit" class="btn btn-success">
										<spring:message code="user.ulog.submit" text="Save" /></button>	
										
										<input  type="checkbox" name="ispublic">	<spring:message code="user.ulog.publishflag" text="Is Published?" />									  	
									</div>
								</div>
							</div>
						</div>					
						</div>
					</form>		
					</div>
				</div>
			</div>		

		
		<!-- my consultants -->
		<div  class="panel panel-default">
		  <div class="panel-heading">
		  	<spring:message code="user.console.events" text="Events"></spring:message>
		  </div>
		  <div class="panel-body">
		    Comming sooooon!
		  </div>
		</div>		
		<div  class="panel panel-default">
		  <div class="panel-heading">
		  	<spring:message code="user.console.consultants" text="My Consultants"></spring:message>
		  </div>
		  <div class="panel-body">
		    Comming sooooon!
		  </div>
		</div>
		
		<!-- my favoriates -->
		<div  class="panel panel-default">
		  <div class="panel-heading">
		  	<spring:message code="user.console.favoriates" text="My Favoriates"></spring:message>
		  </div>
		  <div class="panel-body">
		    Comming sooooon!
		  </div>
		</div>		
		
		
	</div>
</div>


<%@include file="footer.jsp" %>