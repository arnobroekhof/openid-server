<?xml version="1.0" encoding="UTF-8"?>
<%@include file="taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh-CN">
	<head>
		<title>OpenID</title>
		<meta http-equiv="Content-Type"
			content="application/xhtml+xml; charset=UTF-8" />
		<meta name="robots" content="all" />
		<meta name="keywords" content="openid" />
		<meta name="description" content="OpenID Provider" />
	</head>
	<body>
		<div>
			<h1>
				OpenID is Unavailable
			</h1>
			<p>
				There was a fatal error initializing the OpenID application context.
				This is almost always because of an error in the Spring bean
				configuration files. Are the files valid XML? Do the beans they
				refer to all exist?
				<br />
				<br />
				Before placing OpenID in production, you should change this page to
				present a UI appropriate for the case where the OpenID web
				application is fundamentally broken. Perhaps "Sorry, OpenID is
				currently unavailable." with some links to your user support
				information.
			</p>

			<c:if
				test="${not empty applicationScope.exceptionCaughtByServlet and empty applicationScope.exceptionCaughtByListener}">
				<p>
					The Throwable representing the fatal error has been logged by the
					<em>SafeDispatcherServlet</em> via Commons Logging, via
					ServletContext logging, and to System.err.
				</p>
			</c:if>

			<c:if
				test="${empty applicationScope.exceptionCaughtByServlet and not empty applicationScope.exceptionCaughtByListener}">
				<p>
					The Throwable representing the fatal error has been logged by the
					SafeContextLoaderListener via Commons Logging, via ServletContext
					logging, and to System.err.
				</p>
			</c:if>

			<!-- Render information about the throwables themselves -->

			<c:if test="${not empty applicationScope.exceptionCaughtByListener}">
				<p>
					The Throwable encountered at context listener initialization was:
					<br />
					<br />
					<c:out value="${applicationScope.exceptionCaughtByListener}" />
				</p>
			</c:if>

			<c:if test="${not empty applicationScope.exceptionCaughtByServlet}">
				<p>
					The Throwable encountered at dispatcher servlet initialization was:
					<br />
					<br />
					<c:out value="${applicationScope.exceptionCaughtByServlet}" />
				</p>
			</c:if>
		</div>
	</body>
</html>
