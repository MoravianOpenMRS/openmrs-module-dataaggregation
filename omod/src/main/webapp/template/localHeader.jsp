<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
	<li class="first"><a
		href="${pageContext.request.contextPath}/admin"><spring:message
				code="admin.title.short" /></a></li>

	<li
		<c:if test='<%= request.getRequestURI().contains("/manage") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/dataaggregation/manage.form"><spring:message
				code="dataaggregation.manage" /></a>
	</li>
	
	<li <c:if test='<%= request.getRequestURI().contains("/queries") %>'>class="active"</c:if>>
		<a href="queries.form">
			<spring:message code="dataaggregation.queries"/>
		</a>
	</li>
	
	<!-- Add further links here -->
	
</ul>

