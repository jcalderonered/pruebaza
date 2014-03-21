<%-- 
    Document   : lista_charlas
    Created on : 28/10/2013, 03:17:53 PM
    Author     : Ayner Pérez
--%>&

<%@page import="com.mimp.bean.Personal"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
    Personal u = (Personal) request.getSession().getAttribute("usuario");
    if (u == null) {
%>
<jsp:forward page="/salir"/>
<% }%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/inicioper">Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/act_info">Actualizar Información</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/salir">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div class="container">
                <div class="row">
                    <div class="col-md-4 ">
                        <ul class="nav nav-list well">
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/inicioper"><span class="glyphicon glyphicon-home"></span> Inicio</a></li>
                                <%if (u.getRol().equals("DCRI") || u.getRol().equals("DGA") || u.getRol().equals("admin")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/inf"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de sesiones/talleres</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNA</a></li>
                                <%if (u.getRol().equals("admin")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/juzgado"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de juzgado</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ua"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UA</a></li>
                                <%}
                                    if (u.getRol().equals("DEIA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li> 
                                <%}
                                    if (!u.getRol().equals("DAPA") && !u.getRol().equals("MATCH")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/famint"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                                <%}
                                    if (!u.getRol().equals("mpartes")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de registros</a></li>
                            <%if (!u.getRol().equals("DEIA Prio")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/esperaInter"><span class="glyphicon glyphicon-chevron-right"></span>Adoptantes para la adopción en el extranjero</a></li>
                                <%}%>
                                <%if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/usuarios"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                                <%}
                                    if (u.getRol().equals("admin") || u.getRol().equals("DEIA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de organismo acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de autoridad central</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                                <%if (u.getRol().equals("DAPA") || u.getRol().equals("DCRI") || u.getRol().equals("DEIA Prio")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-6 col-md-offset-1">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inicioper'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <c:set var="now" value="<%=new java.util.Date()%>" /> 
                        <fmt:formatDate var="year" value="${now}" pattern="y" />  
                        <h1>Listado de sesiones</h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>Número de sesión</th>
                                        <th>Habilitado</th>
                                        <th>Modificar</th>
                                        <th>Eliminar</th>
                                        <th>Ver Inscritos</th>
                                        <th>Asistencia</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${!listaSesiones.isEmpty()}">  
                                        <c:forEach var="sesion" items="${listaSesiones}" varStatus="status">
                                            <c:set var="fechaSesion" value="${sesion.getFecha()}" /> 
                                            <fmt:formatDate var="yearSesion" value="${fechaSesion}" pattern="y" />  
                                            <c:if test="${year == yearSesion}">    
                                                <tr>
                                                    <td>${sesion.getNSesion()}</td>
                                                    <c:if test="${sesion.getHabilitado() == 0}">
                                                        <td>Si</td> 
                                                    </c:if>  
                                                    <c:if test="${sesion.getHabilitado() == 1}">
                                                        <td>No</td> 
                                                    </c:if>     
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/PersonalEditarSesion" method="post">
                                                            <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                                            <button type="submit" class="btn btn-default">Modificar</button>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/PersonalEliminarSesion" method="post">
                                                            <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                                            <button ${sesion.getHabilitado() == 0 ? 'disabled' : ''} type="submit" class="btn btn-default">Eliminar</button>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/PersonalInscritosSesion" method="post">
                                                            <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                                            <button type="submit" class="btn btn-default">Inscritos</button>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/PersonalTomaAsistencia2" method="post">
                                                            <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                                            <button ${sesion.getHabilitado() == 1 ? 'disabled' : ''} type="submit" class="btn btn-default">Asistencia</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:if> 
                                        </c:forEach>
                                    </c:if>     
                                </tbody>
                            </table>
                        </div>
                        <br>
                        <form action="${pageContext.servletContext.contextPath}/PersonalAgregarSesion" method="post">     
                            <button href="#" class="btn btn-default">Agregar nueva Sesión</button>
                        </form>
                        <br>
                        <br>
                        <h1>Listado de talleres</h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>Tipo taller</th>
                                        <th>Nombre</th>
                                        <th>N° de reuniones</th>
                                        <th>Habilitado</th>
                                        <th>Modificar</th>
                                        <th>Eliminar</th>
                                        <th>Asistencia/Ver inscritos</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${!listaTalleres.isEmpty()}">   
                                        <c:forEach var="taller" items="${listaTalleres}" varStatus="status">
                                            <c:set var="mostrar" value="0" ></c:set>  
                                            <c:if test="${!taller.getGrupos().isEmpty()}">  
                                                <c:forEach var="grupo" items="${taller.getGrupos()}" varStatus="status">
                                                    <c:if test="${!grupo.getTurno2s().isEmpty()}">
                                                        <c:forEach var="turno2" items="${grupo.getTurno2s()}" varStatus="status">
                                                            <c:if test="${!turno2.getReunions().isEmpty()}">
                                                                <c:forEach var="reunion" items="${turno2.getReunions()}" varStatus="status">
                                                                    <c:if test="${ reunion.getFecha() != null}">
                                                                        <c:set var="fechaReunion" value="${reunion.getFecha()}" /> 
                                                                        <fmt:formatDate var="yearReunion" value="${fechaReunion}" pattern="y" />  
                                                                        <c:if test="${year != yearReunion}">
                                                                            <c:set var="mostrar" value="1" ></c:set>  
                                                                        </c:if> 
                                                                    </c:if>
                                                                </c:forEach>
                                                            </c:if>  
                                                        </c:forEach>
                                                    </c:if>  
                                                </c:forEach>
                                            </c:if>  
                                            <c:if test="${mostrar == '0'}">     
                                                <tr>
                                                    <td>${taller.getTipoTaller()}</td>
                                                    <td>${taller.getNombre()}</td> 
                                                    <td>${taller.getNReunion()}</td> 
                                                    <c:if test="${taller.getHabilitado() == 0}">
                                                        <td>Si</td> 
                                                    </c:if>  
                                                    <c:if test="${taller.getHabilitado() == 1}">
                                                        <td>No</td> 
                                                    </c:if>  
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/PersonalEditarTaller" method="post">
                                                            <input hidden name="idTaller" id="idTaller" value="${taller.getIdtaller()}">
                                                            <button type="submit" class="btn btn-default">Modificar</button>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/PersonalEliminarTaller" method="post">
                                                            <input hidden name="idTaller" id="idTaller" value="${taller.getIdtaller()}">
                                                            <button ${taller.getHabilitado() == 0 ? 'disabled' : ''} type="submit" class="btn btn-default">Eliminar</button>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/PersonalInscritosTallerInicio" method="post">
                                                            <input hidden name="idTaller" id="idTaller" value="${taller.getIdtaller()}">
                                                            <input hidden name="nombreTaller" id="nombreTaller" value="${taller.getNombre()}">
                                                            <input hidden name="historial" id="historial" value="false">
                                                            <button type="submit" class="btn btn-default">Detalles</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>   
                                </tbody>
                            </table>
                        </div>
                        <form action="${pageContext.servletContext.contextPath}/PersonalAgregarTaller" method="post">     
                            <button href="#" class="btn btn-default">Agregar nuevo Taller</button>
                        </form>
                        <br>    
                        <br>
                        <form action="${pageContext.servletContext.contextPath}/HistorioSesionesTalleres" method="post">     
                            <button href="#" class="btn btn-default">Ver lista de Sesiones y Talleres Histórico</button>
                        </form>
                        <br>
                    </div>
                </div>
            </div>
            <!--FIN DE CONTENIDO-->
            <br>
            <br>

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
