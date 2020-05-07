<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!
</h1>

<P>  The time on the server is ${serverTime}. </P>
	<form method="get" action="<%=request.getContextPath()%>/skillUpload">
		<div style="padding: 5px;">
			<button type="submit">Upload</button>
		</div>
	</form>
</body>
</html>
