<%-- 
    Document   : inscripcion_sesion1
    Created on : 28/10/2013, 05:45:16 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Entidad"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
    Entidad u = (Entidad) request.getSession().getAttribute("usuario");
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
                        </button>
                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-left">
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/inicioEnt">Inicio</a></li>
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
                            <li><a href="${pageContext.servletContext.contextPath}/inicioEnt"><span class="glyphicon glyphicon-home"></span> Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/listaFam"><span class="glyphicon glyphicon-chevron-right"></span> Listado de familias</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/contraEnt"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>
                        </ul>
                    </div>

                    <div class="col-md-6 col-md-offset-1">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inicioEnt'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Lista de Familias</strong></h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="col-sm-3 ">El Adoptante</th>
                                        <th class="col-sm-3 ">La Adoptante</th>
                                        <th class="col-sm-3 ">Número de expediente</th>
                                        <th class="col-sm-3 ">Correo principal</th>
                                        <th class="col-sm-2 ">Mas informacion</th>
                                        <th class="col-sm-2 ">Estado de Trámite</th>                                          
                                    </tr>
                                </thead>
                                <c:if test="${!listaFam.isEmpty()}">
                                    <tbody>
                                        <c:forEach var="fam" items="${listaFam}" varStatus="status">
                                            <c:if test="${!fam.getInfoFamilias().isEmpty()}">
                                                <c:forEach var="infoFam" items="${fam.getInfoFamilias()}" varStatus="status">
                                                    <c:if test="${!infoFam.getAdoptantes().isEmpty()}">
                                                        <c:set var="idInfo" value="${infoFam.getIdinfoFamilia()}" scope="page" />
                                                        <c:forEach var="adop" items="${infoFam.getAdoptantes()}" varStatus="status">
                                                            <c:choose>
                                                                <c:when test="${adop.getSexo() == 109}">
                                                                    <c:set var="el" value="${adop}" scope="page" />
                                                                </c:when>
                                                                <c:when test="${adop.getSexo() == 102}">
                                                                    <c:set var="ella" value="${adop}" scope="page" />
                                                                </c:when> 
                                                            </c:choose>
                                                        </c:forEach>
                                                    </c:if>
                                                </c:forEach>
                                            </c:if>      
                                            <c:if test="${!fam.getExpedienteFamilias().isEmpty()}">
                                                <c:forEach var="TempExpFam" items="${fam.getExpedienteFamilias()}" varStatus="status">
                                                    <c:set var="ExpFam" value="${TempExpFam}" scope="page" />
                                                </c:forEach>
                                            </c:if>
                                            <tr>
                                                <td>
                                                    ${el.getNombre()}
                                                    ${el.getApellidoP()}
                                                    ${el.getApellidoM()}
                                                </td>
                                                <td>
                                                    ${ella.getNombre()}
                                                    ${ella.getApellidoP()}
                                                    ${ella.getApellidoM()}
                                                </td>
                                                <td>${ExpFam.getNumeroExpediente()}</td>
                                                <td>${ella.getCorreo()}</td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/Einfo" method="post">
                                                        <input hidden name="idInfo" id="idInfo" value="${idInfo}">
                                                        <button type="submit" class="btn btn-default">Info</button>
                                                    </form>
                                                </td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/Entestado" method="post">
                                                        <input hidden name="idfam" id="idfam" value="${fam.getIdfamilia()}">
                                                        <button type="submit" class="btn btn-default">Ver</button>
                                                    </form>
                                                </td>
                                            </tr>
                                            <c:set var="el" value="${null}" scope="page" />
                                            <c:set var="ella" value="${null}" scope="page" />
                                            <c:set var="ExpFam" value="${null}" scope="page" />
                                            <c:set var="idInfo" value="${null}" scope="page" />
                                        </c:forEach>
                                    </tbody>
                                </c:if>
                                <c:if test="${listaFam.isEmpty()}">
                                    <h3><strong>No existen familias asociadas</strong></h3>    
                                </c:if>
                            </table>
                        </div>
                        <br>
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