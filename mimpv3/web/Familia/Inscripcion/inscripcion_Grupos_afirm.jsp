<%-- 
    Document   : inscripcion_sesion1
    Created on : 28/10/2013, 05:45:16 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Familia"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    
    response.setDateHeader("Expires", 0);
    Familia u = (Familia) request.getSession().getAttribute("usuario");
    if (u == null) {
%>
<jsp:forward page="/salir"/>
<% }%>
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
                        </button>
                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-left">
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/inicioFam">Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc1">Ver Información</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/salir">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div class="container">
                <div class="row">
                    <div class="col-md-3 ">
                        <ul class="nav nav-list well">
                            <li><a href="${pageContext.servletContext.contextPath}/inicioFam"><span class="glyphicon glyphicon-home"></span>Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Finscripcion"><span class="glyphicon glyphicon-chevron-right"></span> Inscripción a talleres</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Flecturas"><span class="glyphicon glyphicon-chevron-right"></span> Lecturas </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Festado"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/Fcontra"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>
                    <div class="col-md-8 col-md-offset-1">
                        <c:if test="${mensaje != 'negativo'}">
                        <h1 align="center"><strong>Inscripción Exitosa</strong></h1>
                        <br>
                        <p>Sus datos han sido registrados, queda inscrita/o para asistir al Taller: ${nombreTaller}  - Grupo: ${nombreGrupo} - Turno: ${nombreTurno}</p>
                        <br>
                        <p class="lead">Detalles:</p>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="col-sm-2 ">Fechas</th>
                                        <th class="col-sm-2 ">Dirección</th>
                                    </tr>
                                </thead>
                                <c:if test="${!listaReuniones.isEmpty()}">
                                <tbody>
                                    <c:forEach var="reunion" items="${listaReuniones}" varStatus="status">
                                    <tr>
                                        <td>${reunion.getFecha() != null ? df.dateToString(reunion.getFecha()) : ''} - ${reunion.getHora()}</td>
                                        <td>${reunion.getDireccion()}</td>
                                    </tr>
                                    </c:forEach>
                                </tbody>
                                </c:if>
                                <c:if test="${listaReuniones.isEmpty()}">
                                </c:if>
                            </table>
                        </div>
                        <p><b>IMPORTANTE:</b></p>
                        <p>- Llevar DNI</p>
                        <p>- En caso de parejas casadas, ambos deben asistir a la reunión</p>
                        <p>- No se aceptará el ingreso de personas pasada la hora de inicio de la Reunión. Ser puntuales.</p>
                        <p>- Se dará 15 minutos para el Registro de Asistencia. No se aceptará el ingreso de acompañantes. Sólo de las personas inscritas a la Reunión.</p>
                        </c:if>
                        <c:if test="${mensaje == 'negativo'}">
                            <h1 align="center"><strong>Inscripción No Exitosa</strong></h1>
                        <br>
                        <h1>Sus datos no han sido registrados debido a que no hay vacantes disponibles en el Grupo: ${nombreGrupo} - Turno: ${nombreTurno}</h1>
                        <h1>Porfavor inscribirse en otro Grupo - Turno</h1>
                        <br>
                        </c:if>
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