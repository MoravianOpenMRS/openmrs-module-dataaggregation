<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<h2>
	<spring:message code="dataaggregation.title" />
</h2>

<p>Hello ${user.systemId}!</p>
<p>${diseases}</p>
<p>${cities}</p>
<p>Tests Ordered:  ${testsOrdered}</p>
<p>${weights}</p>

<img src='chart.jsp' />


<%@ include file="/WEB-INF/template/footer.jsp"%>