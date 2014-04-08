<%@include file="header.jsp" %>
<table >
  <c:forEach items="${nodes}" var="n">
  
  <tr>
     <td><a href="<%=request.getContextPath()%>/node/${n.id}"><c:out value="${n.node}"/></a> </td>
  </tr>
  </c:forEach>
  

</table>

  <form action="<%=request.getContextPath()%>/node/add" method="post">
  	<input type="hidden" name="ntype" value="1">
  	<input type="hidden" name="pid" value="0">
	New Question: 	<input name="node" size="40" type="text"/><br>
  	<input type="submit" value="Save">

  </form>
