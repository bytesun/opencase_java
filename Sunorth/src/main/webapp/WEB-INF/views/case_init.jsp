<%@include file="header.jsp" %>

<div class="row">
	<div class="col-md-9"> <!-- main right panel for question list -->

	  	 <form class="form-horizontal" action="<%=request.getContextPath()%>/case/save" method="POST">

		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
									    <input type="radio" name="ctype" id="option1" value="1" checked><spring:message code="case_init.form.radio.ctype.idea" text="Idea" /> 
		     							<input type="radio" name="ctype" id="option2" value="2"> <spring:message code="case_init.form.radio.ctype.project" text="Project" /> 
  										<input type="radio" name="ctype" id="option3" value="3"> <spring:message code="case_init.form.radio.ctype.issue" text="Issue" /> 

							        	<input type="text" class="form-control input-default" name="subject" value="" placeholder="<spring:message code="case_init.form.subject.placeholder" text="Input your idea , project name or issue" /> ">
							
							</div>
						</div>
					</div>
				</div>	

		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							<spring:message code="case_init.form.description" text="Description" />
							<textarea class="form-control richtextarea" name="description" rows="10">
					  		
					  		</textarea>
							</div>
						</div>
					</div>
				</div>									
		  	 	<div class="form-group"> 
		  	 		<div class="rows">
						<div class="col-md-12">
							<div class="col-lg-12">	
							<input type="text" class="form-control input-default" name="tags" value="" placeholder="input tags">
							<c:if test="${user != null }">
								<input type="checkbox" name="isOpen" checked> <spring:message code="case_init.form.label.isPrivate" text="Set Private?" />
							</c:if>
						  	<input type="submit" class="btn btn-primary" name="submit" value="<spring:message code="case_init.form.btn.save" text="Save" />">							
							</div>
						</div>
					</div>
				</div>					
		  		
		  </form>		

	
	</div><!-- end of left side -->	
   	
	<!-- right column -->
		<div  class="col-md-3">


			<div class="row">
				<!-- social sharing button -->
				<span class='st_facebook_large' displayText='Facebook'></span>
				<span class='st_googleplus_large' displayText='Google +'></span>
				<span class='st_baidu_large' displayText='Baidu'></span>
				<span class='st_twitter_large' displayText='Tweet'></span>
				<span class='st_linkedin_large' displayText='LinkedIn'></span>
				<span class='st_sina_large' displayText='Sina'></span>
				<span class='st_blogger_large' displayText='Blogger'></span>
			</div>	
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

