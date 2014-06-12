<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<p>Hello ${user.systemId}!</p>

<h1>The (Currently made up) disease burden data: </h1>

<p>${diseaseBurden}</p>

<%@ include file="/WEB-INF/template/footer.jsp"%>