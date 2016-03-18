<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="MIW. Spring 4 MVC">
<meta name="author" content="J.Bernal">
<link href="<c:url value='/static/css/bootstrap.css' />"
	rel="stylesheet" />
<link href="<c:url value='/static/css/carousel.css' />" rel="stylesheet" />
<title>Home</title>
</head>
<body>
	<div class="navbar-wrapper">
		<div class="container">
			<nav class="navbar navbar-inverse navbar-static-top">
				<div class="container">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse" data-target="#navbar"
							aria-expanded="false" aria-controls="navbar">
							<span class="icon-bar"></span><span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="#">Paddle</a>
					</div>
					<div id="navbar" class="navbar-collapse collapse">
						<ul class="nav navbar-nav">
							<li class="active"><a href="#">Home</a></li>
							<li><a href="<c:url value="/list-courts"/>">Mostrar
									pistas</a></li>
							<li><a href="<c:url value="create-court"/>">Crear pistas</a></li>
						</ul>

					</div>
				</div>
			</nav>
			<div class="row">
				<div class="col-md-1"></div>
				<div class="col-md-10">
					<div class="container">
						<div class="row">
							<div class="col-md-8">
								<div class="page-header">
									<h2>Listado de Pistas</h2>
								</div>
								<div class="table-responsive">
									<table class="table table-hover table-condensed">
										<thead>
											<tr>
												<th>Id</th>
												<th>Disponibilidad</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${courtList}" var="court">
												<tr>
													<td>${court.courtId}</td>
													<td><c:if test="${court.active==true}">
															<h5>Activa</h5>
														</c:if> <c:if test="${court.active==false}">
															<h5>Inactiva</h5>
														</c:if></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<p>
										<a href="<c:url value='/create-court'/>">Crear Pista</a>
									</p>
									<p>
										<a href="<c:url value='/home'/>">Inicio</a>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="<c:url value='/static/js/bootstrap.js' />"></script>
</body>
</html>