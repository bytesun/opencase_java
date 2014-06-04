<%@include file="header.jsp" %>

<div class="row">
	<div class="col-md-9"> <!-- main right panel for question list -->
	<h2> <span style="word-break:break-all;"><c:out value="${activity.subject}"/></span></h2>
	  	<table class="table">
			<tr>
				<td width="20%" align="right"><b><spring:message code="activity.organizer" text="Organizer" /></b></td>
				<td  colspan="3"><a href="<%=request.getContextPath()%>/user/${activity.organizer.uid}"><c:out value="${activity.organizer.name}"/></a></td>
			</tr>
			<tr>
				<td width="20%" align="right"><b><spring:message code="activity.location" text="Location" /></b></td>
				<td  colspan="3"><c:out value="${activity.location}"/></td>
			</tr>			
			<tr>
				<td align="right"><b><spring:message code="activity.astime" text="Start Time" /></b></td>
				<td><c:out value="${activity.astime}"/>
				</td>
				<td align="right"><b><spring:message code="activity.aetime" text="End Time" /></b></td>
				<td><c:out value="${activity.aetime}"/></td>
			</tr>			
			<tr>
				<td align="right"><b><spring:message code="activity.attcnt" text="Persons" /></b></td>
				<td><c:out value="${activity.attcnt}"/>
				</td>
				<td align="right"><b><spring:message code="activity" text="Cost per person($)" /></b></td>
				<td><c:out value="${activity.cost}"/></td>
			</tr>											
			<tr>
				<td  colspan="4">
				<c:out value="${activity.description}" escapeXml="false"/></td>
			</tr>		
	  	</table>

		<div>
	 	</div>

	
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

<script type="text/javascript">
   $(document).ready(function() {

	    
   });
   </script>



<%@include file="footer.jsp" %>

