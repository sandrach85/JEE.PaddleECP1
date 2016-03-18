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
				<div class="col-md-2"></div>
				<div class="col-md-10">
					<div class="container">
						<div class="row">
							<div class="col-md-8">
								<div class="panel-group">
									<div class="panel-group">
										<div class="panel panel-default">
											<div class="panel panel-success">
												<div class="panel-heading">
													<strong>Creación de pista correcta</strong>
												</div>
												<div class="panel-body">
													<h3>La pista (${court.courtId-1}) ha sido creada
														satisfactoriamente</h3>
												</div>
												<div class="panel-footer">
													<p>
														<a href="<c:url value='/list-courts' />">Ir a Lista de
															Pistas</a>
													</p>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>