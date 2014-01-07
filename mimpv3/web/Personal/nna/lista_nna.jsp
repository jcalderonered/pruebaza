<%-- 
    Document   : inscripcion_sesion1
    Created on : 28/10/2013, 05:45:16 AM
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
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNAs</a></li>
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
                                if (u.getRol().equals("admin")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Organismo Acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Autoridad Central</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-6 col-md-offset-1">
                        <p align="right"><button onclick="window.location.href = '${pageContext.servletContext.contextPath}/agregarNna'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Registrar NNA</button></p>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/nna" >NNA Regulares</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/nnaPrioritarios" >NNA Prioritarios</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/nnaSeguimiento" >NNA en Seguimiento</a></li>
                        </ul>
                        <br>
                        <br>
                        <h1 align="center"><strong>Lista de NNA's</strong></h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="col-sm-2 ">Nombre</th>
                                        <th class="col-sm-2 ">Apellido Paterno</th>
                                        <th class="col-sm-2 ">Apellido Materno</th>
                                        <th class="col-sm-2 ">Sexo</th>
                                        <th class="col-sm-2 ">Proceso de adopción</th>
                                        <th class="col-sm-2 ">Detalles</th> 
                                        <th class="col-sm-2 ">Expediente</th>
                                        <th class="col-sm-2 ">Registrar designación</th>
                                    </tr>
                                </thead>
                                <c:if test="${!listaNna.isEmpty()}">  
                                <tbody>
                                  <c:forEach var="nna" items="${listaNna}" varStatus="status"> 
                                    <tr>
                                        <td>${nna.getNombre()}</td>
                                        <td>${nna.getApellidoP()}</td>
                                        <td>${nna.getApellidoM()}</td>
                                        <td>${nna.getSexo()}</td>
                                        <td>
                                            <c:if test="${!nna.getDesignacions().isEmpty()}">
                                               <c:forEach var="designacion" items="${nna.getDesignacions()}" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${designacion.getAceptacionConsejo() == 0 || designacion.getAceptacionConsejo() == 1 || designacion.getAceptacionConsejo() == 2}">
                                                            <c:set var="tokenAdopcion" value="0" ></c:set>
                                                        </c:when> 
                                                        <c:otherwise> No </c:otherwise>            
                                                    </c:choose>
                                                </c:forEach>  
                                                ${tokenAdopcion == 0 ? 'Si' : 'No' }        
                                            </c:if>  
                                                
                                            <c:if test="${nna.getDesignacions().isEmpty()}">
                                                
                                                No
                                            
                                            </c:if>      
                                                                                               
                                        </td>
                                        <td>
                                            <form action="${pageContext.servletContext.contextPath}/editarNna" method="post">
                                                <input hidden name="idNna" id="idNna" value="${nna.getIdnna()}">
                                                <button type="submit" class="btn btn-default">Ver</button>
                                            </form>
                                        </td>        
                                        <td>
                                            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/editarExpedienteNna" method="post">
                                                    <input hidden name="idNna" id="idNna" value="${nna.getIdnna()}">    
                                                    <button ${nna.getExpedienteNnas().isEmpty() == true ? 'disabled' : ''} class="btn btn-default">Ver</button>
                                            </form>
                                        </td>
                                        <td>
                                            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/registrarDesignacion" method="post">
                                                    <input hidden name="idNna" id="idNna" value="${nna.getIdnna()}">    
                                                    <button ${tokenAdopcion == 0 ? 'disabled' : ''} ${nna.getExpedienteNnas().isEmpty() == true ? 'disabled' : ''} class="btn btn-default">Registrar</button>
                                            </form>
                                        </td>
                                    </tr>
                                  </c:forEach>  
                                </tbody>
                                </c:if> 
                               <c:if test="${listaNna.isEmpty()}">
                                    <h3><strong>No existen Nna en esta clasificación</strong></h3>
                                </c:if>  
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