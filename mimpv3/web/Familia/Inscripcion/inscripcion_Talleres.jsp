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
                            <li><a href="${pageContext.servletContext.contextPath}/Festado"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fcontra"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>
                    <div class="col-md-8 col-md-offset-1">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inicioFam'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <c:if test="${inscrito == false}">
                        <h1 align="center"><strong>Inscripción a Talleres</strong></h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="col-sm-2 ">Nombre</th>
                                        <th class="col-sm-2 ">Tipo</th>
                                        <th class="col-sm-2 ">Grupos y Turnos</th>

                                    </tr>
                                </thead>
                                <c:if test="${!listaTalleres.isEmpty()}">
                                <tbody>
                                    <c:forEach var="taller" items="${listaTalleres}" varStatus="status">
                                    <tr>
                                        <td>${taller.getNombre()}</td>
                                        <td>${taller.getTipoTaller()}</td>
                                        <td>
                                            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/FamiliaDetalleTaller" method="post">
                                                    <input hidden id="idTaller" name="idTaller" value="${taller.getIdtaller()}">    
                                                    <input hidden id="nombreTaller" name="nombreTaller" value="${taller.getNombre()}">  
                                                    <button class="btn btn-default">Ver detalles</button>
                                            </form>
                                        </td>
                                    </tr>
                                    </c:forEach>
                                </tbody>
                                </c:if>
                                <c:if test="${listaTalleres.isEmpty()}">
                                            <h3><strong>No exiten talleres habilitados</strong></h3>
                                </c:if>
                            </table>
                        </div>
                        </c:if>
                        <c:if test="${inscrito == true}">
                            <h3><strong>Usted se ha inscrito a las siguientes Reuniones como parte de los talleres de Preparación</strong></h3>
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
                                    <c:set var="idReunion" value="0" />
                                    <c:forEach var="afr" items="${listaReuniones}" varStatus="status">
                                    <tr>
                                        <c:if test="${idReunion != afr.getReunion().getIdreunion()}">
                                        <td>${afr.getReunion().getFecha() != null ? formato.dateToString(afr.getReunion().getFecha()) : ''} - ${afr.getReunion().getHora()}</td>
                                        <td>${afr.getReunion().getDireccion()}</td>
                                        <c:set var="idReunion" value="${afr.getReunion().getIdreunion()}" />
                                        </c:if>
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
                        <p>- No se aceptará el ingreso de acompañantes. Sólo de las personas inscritas a la Reunión.</p>
                        </c:if>
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