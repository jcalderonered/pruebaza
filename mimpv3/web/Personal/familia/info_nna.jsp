<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
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
        <!-- Datepicker -->
        <link href="${pageContext.servletContext.contextPath}/assets/css/datepicker3.css" rel="stylesheet">

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
                            <li><a href="${pageContext.servletContext.contextPath}/esperaInter"><span class="glyphicon glyphicon-chevron-right"></span>Adoptantes para la adopción en el extranjero</a></li>      
                                <%if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/usuarios"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                                <%}
                                    if (u.getRol().equals("admin") || u.getRol().equals("DEIA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de organismo acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de autoridad central</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                                <%if (u.getRol().equals("DAPA") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-8">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inicioper'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <c:if test="${estado != 'formativa'}">
                            <br>
                            <h1 align="center"><strong>Familia "${expediente.getExpediente()}"</strong></h1>
                            <br>
                        </c:if>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row">
                            <li><a href="${pageContext.servletContext.contextPath}/laSolicitante">La Solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/elSolicitante" >El solicitante</a></li>
                            <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/compFamiliar" >Composición familiar</a></li>-->
                            <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/vivienda" >Vivienda</a></li>-->
                            <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/infoExpediente" >Información del Expediente</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/procesoAdopcion" >Proceso de adopción</a></li>
                            <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/antNna" >Antecedentes del NNA</a></li>
                            <li ${estado == 'formativa' || estado == 'evaluacion' || estado == 'espera' || estado == 'designacion' || estado == 'adopcion' || estado == 'reevaluacion' ? 'class="hidden"' : 'class="active"'} ><a href="${pageContext.servletContext.contextPath}/nnaAsociado" >NNA Adoptado</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/atenciones" >Atenciones</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/EditUserPass" >Editar Perfil de Familia</a></li>
                        </ul>
                        <br>
                        <c:if test="${estado != 'esperainter'}">
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped" id="mi_tabla">
                                    <thead>
                                        <tr>
                                            <th class="col-sm-2 ">Nombre</th>
                                            <th class="col-sm-2 ">Apellido Paterno</th>
                                            <th class="col-sm-2 ">Apellido Materno</th>
                                            <th class="col-sm-2 ">Nombre Adoptado</th>
                                            <th class="col-sm-2 ">Apellido Paterno Adoptado</th>
                                            <th class="col-sm-2 ">Apellido Materno Adoptado</th>
                                            <th class="col-sm-2 ">Sexo</th>
                                            <th class="col-sm-2 ">Código</th>
                                            <th class="col-sm-2 ">Estado</th>
                                        </tr>
                                    </thead>
                                    <c:if test="${!listaNna.isEmpty()}"> 
                                        <tbody>
                                            <c:forEach var="nna" items="${listaNna}" varStatus="status">
                                                <c:if test="${!nna.getExpedienteNnas().isEmpty()}">
                                                    <c:forEach var="expNna" items="${nna.getExpedienteNnas()}" varStatus="status">
                                                        <c:set var="expediente" value="${expNna}"></c:set>
                                                    </c:forEach>
                                                </c:if>
                                                <tr>
                                                    <td>${nna.getNombre()}</td>
                                                    <td>${nna.getApellidoP()}</td>
                                                    <td>${nna.getApellidoM()}</td>
                                                    <td>${expediente.getNActual()}</td>
                                                    <td>${expediente.getApellidopActual()}</td>
                                                    <td>${expediente.getApellidomActual()}</td>
                                                    <td>${nna.getSexo()}</td>                                                                                                                    
                                                    <td>
                                                        ${expediente.getCodigoReferencia()}
                                                    </td>
                                                    <td>
                                                        ${expediente.getEstado()}
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
                        </c:if>
                        <c:if test="${estado == 'esperainter'}">
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped" id="mi_tabla">
                                    <thead>
                                        <tr>
                                            <th class="col-sm-2 ">Nombre</th>
                                            <th class="col-sm-2 ">Apellido Paterno</th>
                                            <th class="col-sm-2 ">Apellido Materno</th>
                                            <th class="col-sm-2 ">Sexo</th>
                                        </tr>
                                    </thead>
                                    <c:if test="${!listaDesig.isEmpty()}">  
                                        <tbody>
                                            <c:forEach var="desig" items="${listaDesig}" varStatus="status"> 
                                                <tr>
                                                    <td>${desig.getNna().getNombre()}</td>
                                                    <td>${desig.getNna().getApellidoP()}</td>
                                                    <td>${desig.getNna().getApellidoM()}</td>
                                                    <td>${desig.getNna().getSexo()}</td>                                                    
                                                </tr>
                                            </c:forEach>  
                                        </tbody>
                                    </c:if> 
                                    <c:if test="${listaDesig.isEmpty()}">
                                        <h3><strong>No existen Nna en esta clasificación</strong></h3>
                                    </c:if>  
                                </table>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
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
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
            <script type="text/javascript">

                                                                $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

            </script>
            <!-- Ubicar al final -->

    </body>
</html>

