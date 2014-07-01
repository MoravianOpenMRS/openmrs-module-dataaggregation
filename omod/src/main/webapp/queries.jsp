<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<h2><spring:message code="dataaggregation.queries.title" /></h2>

<p>Hello ${user.systemId}!</p>

<h3>Disease Counts</h3>
<table border="4" width="100%">
	<col width="50%">
	<col width="50%">
	<thead>
		<tr>
			<th style="text-align:center;color:white;background-color:purple">Parameter</th>
			<th style="text-align:center;color:white;background-color:purple">Description</th>
		</tr>
	</thead>
	<tr>
	  <td><i>List< String></i> diseases</td>
	  <td>A list of diseases</td>
	</tr>
	<tr>
	  <td><i>List< String></i> cities</td>
	  <td>A list of cities</td>
	</tr>
	<tr>
	  <td><i>String</i> startDate</td>
	  <td>Start date</td>
	</tr>
	<tr>
	  <td><i>String</i> endDate</td>
	  <td>End date</td>
	</tr>
	<tr>
	  <td><i>int</i> minNumber</td>
	  <td>Minimum number of cases</td>
	</tr>
	<tr>
	  <td><i>int</i> maxNumber</td>
	  <td>Maximum number of cases</td>
	</tr>
</table>


<p></p>
<h3>Tests Ordered</h3>
<table border="4" width="100%">
	<col width="50%">
	<col width="50%">
	<thead>
		<tr>
			<th style="text-align:center;color:white;background-color:purple">Parameter</th>
			<th style="text-align:center;color:white;background-color:purple">Description</th>
		</tr>
	</thead>
	<tr>
	  <td><i>List< String></i> testsOrdered</td>
	  <td>A list of tests ordered</td>
	</tr>
	<tr>
	  <td><i>String</i> startDate</td>
	  <td>Start date</td>
	</tr>
	<tr>
	  <td><i>String</i> endDate</td>
	  <td>End date</td>
	</tr>
	<tr>
	  <td><i>int</i> minNumber</td>
	  <td>Minimum number of cases</td>
	</tr>
	<tr>
	  <td><i>int</i> maxNumber</td>
	  <td>Maximum number of cases</td>
	</tr>
</table>


<p></p>
<h3>Weights</h3>
<table border="4" width="100%">
	<col width="50%">
	<col width="50%">
	<thead>
		<tr>
			<th style="text-align:center;color:white;background-color:purple">Parameter</th>
			<th style="text-align:center;color:white;background-color:purple">Description</th>
		</tr>
	</thead>
	<tr>
	  <td><i>char</i> gender</td>
	  <td>Gender of patients</td>
	</tr>
	<tr>
	  <td><i>int</i> minAge</td>
	  <td>Minimum age of patients</td>
	</tr>
	<tr>
	  <td><i>int</i> maxAge</td>
	  <td>Maximum age of patients</td>
	</tr>
</table>



<%@ include file="/WEB-INF/template/footer.jsp"%>