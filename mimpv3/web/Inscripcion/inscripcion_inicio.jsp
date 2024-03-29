<%-- 
    Document   : inscripcion_sesion1
    Created on : 28/10/2013, 05:45:16 AM
    Author     : Ayner Pérez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Sistema de Adopciones</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/bootstrap.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/index_002.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/mimp_css.css">
    </head>

    <body id="bd" class="bd fs3 com_content">
        <br>
        <br>
        <div id="wrap">
            <div class="row">
                <div class="col-md-1"></div>
                <div class="col-md-5"><p align="left"><img src="<%=request.getContextPath()%>/assets/img/pastillasDGA.jpg" width="600" border="0"></p></div>
                <div class="col-md-5"><p align="right"><img src="<%=request.getContextPath()%>/assets/img/logoperu1.png" width="165" border="0"></p></div>
                <div class="col-md-1"></div>
            </div>
            <br>
            <br>
            <div class="container">
                <div class="navbar navbar-inverse">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>

                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-left">
                            <li><a href="${pageContext.servletContext.contextPath}/inicio">Inicio</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/SesionInfInicioPrev">Inscripción a Sesión Informativa</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/CronogramaAnualPrev">Ver el cronograma anual</a></li>
                            <!--<li><a href="#">Información Adicional</a></li>-->
                            <li><a href="#">Contacto</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>

            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div class="container">
                <div class="row">
                    <div class="col-md-8 col-md-offset-1">
                        <h1 align="center"><strong>Inscripción a la Sesión Informativa</strong></h1>
                        <br>
                        <p><b>Fecha de Sesión Informativa:</b> ${ts.DateToString(listaTurnos.get(0).getSesion().getFecha())}</p>
                        <p><b>Hora:</b> ${listaTurnos.get(0).getSesion().getHora()}</p>
                        <p><b>Direccion:</b> ${listaTurnos.get(0).getSesion().getDireccion()}</p>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Inicio Inscripción</th>
                                        <th>Fin Inscripción</th>
                                        <th>Total de vacantes</th>
                                        <th>Vacantes Disponibles</th>
                                        <th>Registrar</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="turno" items="${listaTurnos}" varStatus="status">
                                        <tr>
                                            <td>Turno ${status.index + 1}</td>
                                            <td>${ts.TimeToString(turno.getInicioInscripcion())}</td>
                                            <td>${ts.TimeToString(turno.getFinInscripcion())}</td>
                                            <td>${turno.getVacantes()}</td>
                                            <td>${turno.getVacantes() - turno.getAsistenciaFTs().size()}</td>

                                            <td>
                                                <c:set var="now" value="<%=new java.util.Date()%>" />
                                                <c:choose>
                                                    <c:when test="${turno.getVacantes() > turno.getAsistenciaFTs().size() && turno.getInicioInscripcion() < now && turno.getFinInscripcion() > now }">
                                                        <form action="${pageContext.servletContext.contextPath}/SesionInfElegirEstado" method="post">
                                                            <input hidden name="idTurno" id="idTurno" value="${turno.getIdturno()}">
                                                            <button type="submit" class="btn btn-default">Inscribirse</button>
                                                        </form>
                                                    </c:when>
                                                    <c:when test="${turno.getVacantes() <= turno.getAsistenciaFTs().size() || turno.getFinInscripcion() < now }">
                                                        
                                                        <p><strong>Se termino la inscripción</strong></p>
                                                        
                                                    </c:when>    
                                                    <c:otherwise>
                                                         <p><strong>En espera</strong></p>
                                                    </c:otherwise>
                                                </c:choose>

                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            
                        </div>
                    </div>
                </div>
            </div>
            <!--FIN DE CONTENIDO-->
            <br>
            <br>
        </div>   
        <div id="footer">
            <div id="ja-footer" class="wrap">
                <hr width=80% align="center">
                <p align="center"><h5 class="caption" align="center" style="text-align: center;">MINISTERIO DE LA MUJER Y POBLACIONES VULNERABLES<br>Jr. Camaná 616, Lima - Perú<br>Central telefónica: (511) 626-1600</h5></p>
            </div>
        </div>
        <!-- core JavaScript
================================================== -->
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
        <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>

        <!-- Ubicar al final -->
    </body>
</html>