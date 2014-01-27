<%-- 
    Document   : lista_sesion
    Created on : 28/10/2013, 03:38:34 PM
    Author     : Ayner Pérez
--%>

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
                                <%if (u.getRol().equals("DCRI") || u.getRol().equals("DGA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/inf"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Sesiones/talleres</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNA</a></li>
                                <%if (u.getRol().equals("admin")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/juzgado"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Juzgado</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ua"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UAs</a></li>
                                <%}
                                if (!u.getRol().equals("DAPA") && !u.getRol().equals("MATCH")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/famint"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                                <%}
                                if (!u.getRol().equals("mpartes")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de Registros</a></li>
                                <%if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/usuarios"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                                <%}
                                if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Organismo Acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Autoridad Central</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-6 ">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inf'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Lista de familias Inscritas</strong></h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="col-sm-3" colspan="4">El Inscrito</th>
                                        <th class="col-sm-3" colspan="4">La Inscrita</th>
                                        <th class="col-sm-1" rowspan="2">Correo</th>
                                        <th class="col-sm-1" rowspan="2">Detalles</th>   
                                    </tr>
                                    <tr>
                                        <th>Apellido Paterno</th>
                                        <th>Apellido Materno</th>
                                        <th>Nombre</th>
                                        <th>Edad</th>
                                        <th>Apellido Paterno</th>
                                        <th>Apellido Materno</th>
                                        <th>Nombre</th>
                                        <th>Edad</th>
                                    </tr>

                                </thead>
                                <c:if test="${listaFormularios != null}">
                                    <tbody>
                                        <c:forEach var="formulario" items="${listaFormularios}" varStatus="status">
                                            <tr>
                                                <c:choose>
                                                    <c:when test="${formulario.getAsistentes().size() == 2}">     
                                                        <c:forEach var="asistente" items="${formulario.getAsistentes()}" varStatus="status">
                                                            <c:choose>
                                                                <c:when test="${asistente.getSexo() == 109}">
                                                                    <c:set var="el" value="${asistente}" scope="page" />
                                                                    
                                                                </c:when>
                                                                <c:when test="${asistente.getSexo() == 102}">
                                                                    <c:set var="ella" value="${asistente}" scope="page" />
                                                                </c:when> 
                                                            </c:choose>
                                                        </c:forEach>
                                                                    
                                                                    <td>${el.getApellidoP()}</td>
                                                                    <td>${el.getApellidoM()}</td>
                                                                    <td>${el.getNombre()}</td>
                                                                    <td>${el.getEdad()}</td>
                                                                    <td>${ella.getApellidoP()}</td>
                                                                    <td>${ella.getApellidoM()}</td>
                                                                    <td>${ella.getNombre()}</td>
                                                                    <td>${ella.getEdad()}</td>

                                                                    <td>${ella.getCorreo()}</td>
                                                                    <td>
                                                                        <form action="${pageContext.servletContext.contextPath}/PersonalDetalleFamiliaInscritaSesion" method="post">
                                                                            <input hidden name="idFormulario" id="idFormulario" value="${formulario.getIdformularioSesion()}">
                                                                            <button type="submit" class="btn btn-default">Ver</button>
                                                                        </form>
                                                                    </td>
                                                    </c:when>
                                                    <c:when test="${formulario.getAsistentes().size() == 1}">
                                                        <c:forEach var="asistente" items="${formulario.getAsistentes()}" varStatus="status">
                                                            <c:choose>
                                                                <c:when test="${asistente.getSexo() == 109}">
                                                                    <td>${asistente.getApellidoP()}</td>
                                                                    <td>${asistente.getApellidoM()}</td>
                                                                    <td>${asistente.getNombre()}</td>
                                                                    <td>${asistente.getEdad()}</td>
                                                                    <td></td>
                                                                    <td></td>
                                                                    <td></td>
                                                                    <td></td>
                                                                    <td>${asistente.getCorreo()}</td>
                                                                    <td>
                                                                        <form action="${pageContext.servletContext.contextPath}/PersonalDetalleFamiliaInscritaSesion" method="post">
                                                                            <input hidden name="idFormulario" id="idFormulario" value="${formulario.getIdformularioSesion()}">
                                                                            <button type="submit" class="btn btn-default">Ver</button>
                                                                        </form>
                                                                    </td>
                                                                </c:when>
                                                                <c:when test="${asistente.getSexo() == 102}">
                                                                    <td></td>
                                                                    <td></td>
                                                                    <td></td>
                                                                    <td></td>
                                                                    <td>${asistente.getApellidoP()}</td>
                                                                    <td>${asistente.getApellidoM()}</td>
                                                                    <td>${asistente.getNombre()}</td>
                                                                    <td>${asistente.getEdad()}</td>

                                                                    <td>${asistente.getCorreo()}</td>
                                                                    <td>
                                                                        <form action="${pageContext.servletContext.contextPath}/PersonalDetalleFamiliaInscritaSesion" method="post">
                                                                            <input hidden name="idFormulario" id="idFormulario" value="${formulario.getIdformularioSesion()}">
                                                                            <button type="submit" class="btn btn-default">Ver</button>
                                                                        </form>
                                                                    </td>
                                                                </c:when>    

                                                            </c:choose>
                                                        </c:forEach>
                                                    </c:when>    
                                                    <c:otherwise>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </c:if>
                                <c:if test="${listaFormularios == null}">
                                    <h3><strong>Aún no hay personas inscritas</strong></h3>
                                </c:if>
                            </table>

                        </div>


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
                    <p align="right">Diseñado por RED<br>www.red.net.pe</p>
                </div>
            </div>
            <!-- core JavaScript
        ================================================== -->
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
            <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>

            <!-- Ubicar al final -->
    </body>
</html>
