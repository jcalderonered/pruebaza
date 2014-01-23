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
                                <tbody>
                                    <c:forEach var="fam" items="${listaFam}" varStatus="status">
                                        <tr> 
                                            <c:forEach var="Adop" items="${listaAdop}" varStatus="status">
                                                <c:if test="${Adop.getInfoFamilia().getFamilia().getIdfamilia() == fam.getIdfamilia() && Adop.getSexo() == '109' }"  >
                                                    <td>${Adop.getNombre()}</td>
                                                </c:if>
                                        <form action="${pageContext.servletContext.contextPath}/Einfo" method="post">

                                            <c:if test="${Adop.getInfoFamilia().getFamilia().getIdfamilia() == fam.getIdfamilia() && Adop.getSexo() == '102' }"  >
                                                <td>${Adop.getNombre()}</td>
                                                <input hidden name="idInfo" id="idInfo" value="${Adop.getInfoFamilia().getIdinfoFamilia()}">
                                            </c:if>
                                        </c:forEach>
                                        <c:forEach var="Exp" items="${listaExp}" varStatus="status">
                                            <c:if test="${Exp.getFamilia().getIdfamilia() == fam.getIdfamilia()}"  >
                                                <td>${Exp.getNumeroExpediente()}</td>
                                            </c:if>

                                        </c:forEach>
                                        <td>${fam.getCorreo()}</td>
                                        <td>

                                            <input hidden name="idfam" id="idfam" value="${fam.getIdfamilia()}">

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
                                </c:forEach>
                                </tbody>
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