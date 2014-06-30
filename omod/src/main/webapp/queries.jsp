<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<h2><spring:message code="dataaggregation.queries.title" /></h2>

<p>Hello ${user.systemId}!</p>


<table border="1" width="100%">
<thead>
<tr>
<th>Query</th>
<th>Parameter</th>
<th>Description</th>
</tr>
</thead>

<tr>
  <td>Disease Counts</td>
  <td></td>		
  <td></td>
</tr>
<tr>
  <td></td>
  <td><i>List< String></i> diseases</td>
  <td>A list of diseases</td>
</tr>
<tr>
  <td></td>
  <td><i>List< String></i> cities</td>
  <td>A list of cities</td>
</tr>
<tr>
  <td></td>
  <td><i>String</i> startDate</td>
  <td>Start date</td>
</tr>
<tr>
  <td></td>
  <td><i>String</i> endDate</td>
  <td>End date</td>
</tr>
<tr>
  <td></td>
  <td><i>int</i> minNumber</td>
  <td>Minimum number of cases</td>
</tr>
<tr>
  <td></td>
  <td><i>int</i> maxNumber</td>
  <td>Maximum number of cases</td>
</tr>

<tr>
  <td>Tests Ordered</td>
  <td></td>		
  <td></td>
</tr>
<tr>
  <td></td>
  <td><i>List< String></i> testsOrdered</td>
  <td>A list of tests ordered</td>
</tr>
<tr>
  <td></td>
  <td><i>String</i> startDate</td>
  <td>Start date</td>
</tr>
<tr>
  <td></td>
  <td><i>String</i> endDate</td>
  <td>End date</td>
</tr>
<tr>
  <td></td>
  <td><i>int</i> minNumber</td>		
  <td>Minimum number of cases</td>
</tr>
<tr>
  <td></td>
  <td><i>int</i> maxNumber</td>
  <td>Maximum number of cases</td>
</tr>

<tr>
  <td>Weights</td>
  <td></td>
  <td></td>
</tr>
<tr>
  <td></td>
  <td><i>char</i> gender</td>
  <td>Gender of patients</td>
</tr>
<tr>
  <td></td>
  <td><i>int</i> minAge</td>
  <td>Minimum age of patients</td>
</tr>
<tr>
  <td></td>
  <td><i>int</i> maxAge</td>
  <td>Maximum age of patients</td>
</tr>

</table>



<%@ include file="/WEB-INF/template/footer.jsp"%>