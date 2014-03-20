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
            <h2 align="center">Las inscripciones están cerradas, agradecemos su interés y le informamos que la próxima sesión informativa dará inicio el:</h2>
            <br>
            <h2 align="center"><b>${ts.DateToString(listaSesiones.get(0).getFecha())} ${ts.HourToString(listaSesiones.get(0).getHora())}</b></h2>
            <br>
            <h2 align="center">Los turnos de inscripción para la próxima sesión informativa son según el siguiente cronograma</h2>
            <h2 align="center">Se le invita a inscribirse hasta que se completen las vacantes</h2>
            <br>
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Día Inscripción</th>
                                    <th>Inicio Inscripciones</th>
                                    <th>Fin Inscripciones</th>
                                    <th>Total de vacantes</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="turno" items="${listaTurnos}" varStatus="status">
                                    <tr>
                                        <td>Turno ${status.index + 1} </td>
                                        <td>${ts.DateToString(turno.getInicioInscripcion())}</td>
                                        <td>${ts.HourToString(turno.getInicioInscripcion())}</td>
                                        <td>${ts.HourToString(turno.getFinInscripcion())}</td>
                                        <td>${turno.getVacantes()}</td> </tr>
                                    </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <h2 align="center">Las próximas sesiones informativas dentro del año son las siguientes:</h2>
            <br>
            <div class ="container">
            <c:choose>
                <c:when test="${listaSesiones.size()==1}">
                    <p><strong>No hay mas sesiones programadas</strong></p>
                </c:when>
                <c:otherwise>
                    <c:forEach begin="1" var="sesion" items="${listaSesiones}" varStatus="status">
                        <p class="text-center"><strong>${ts.DateToString(sesion.getFecha())} ${ts.HourToString(sesion.getHora())}</strong></p>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </div>            
            <br>
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