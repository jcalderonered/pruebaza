<%-- 
    Document   : agregar_exp
    Created on : 4/12/2013, 11:38:32 AM
    Author     : User
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
                    <div class="col-md-6 col-md-offset-1">
                        <!-- <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  -->
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li ><a href="${pageContext.servletContext.contextPath}/nna" >NNA Regulares</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/nnaPrioritarios" >NNA Prioritarios</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/nnaSeguimiento" >NNA en Seguimiento</a></li>
                        </ul>
                        <br>
                        <br>
                        <h1 align="center"><strong>Lista de Familias Afines</strong></h1>
                        <br>
                        <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/buscarExpedientePrioritario" method="post">
                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label">Expediente (ApellidoEl-ApellidoElla)</label>
                                    <br>
                                    <div class="controls">
                                        <input id="exp" name="exp" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <button id="singlebutton" name="singlebutton" class="btn btn-default">Buscar</button>
                            </fieldset>
                        </form>
                        <br>
                        <h1 align="center"><strong>Expedientes encontrados</strong></h1>
                        <br>
                        <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/agregarExpedientePrioritario" method="post">
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="col-sm-2 " >Expediente</th>
                                        <th class="col-sm-2 " >Nivel sociec</th>
                                        <th class="col-sm-2 " >Información</th>
                                        <th class="col-sm-2 " >Resolución de aptitud</th>
                                        <th class="col-sm-2 " >Seleccionar</th>
                                    </tr>
                                </thead>
                                <c:if test="${!listaBusqueda.isEmpty()}"> 
                                <tbody>
                                    <c:forEach var="familia" items="${listaBusqueda}" varStatus="status">
                                        <c:set var="agregado" value="1" />
                                            <tr>
                                                <td>${familia.getExpediente()}</td>
                                                <td>
                                                    <c:forEach var="info" items="${familia.getFamilia().getInfoFamilias()}" varStatus="status">
                                                        ${info.getNivelSocioeconomico()}
                                                    </c:forEach>
                                                </td>
                                                <td><button id="singlebutton" name="singlebutton" class="btn btn-default">Ver</button></td>
                                                <td>
                                                    <c:forEach var="eval" items="${familia.getEvaluacions()}" varStatus="status">
                                                    <c:forEach var="resolucion" items="${eval.getResolucions()}" varStatus="status">
                                                        ${resolucion.getFechaResol() != null ? df.dateToString(resolucion.getFechaResol()) : ''}
                                                    </c:forEach>
                                                    </c:forEach>
                                                </td>
                                                <td>
                                                    <c:if test="${!listaEstudioCaso.isEmpty()}">
                                                        <c:forEach var="familia2" items="${listaEstudioCaso}" varStatus="status">
                                                            <c:if test="${familia.getIdexpedienteFamilia() == familia2.getIdexpedienteFamilia()}">
                                                                <c:set var="agregado" value="0" />
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if> 
                                                    <div class="checkbox">
                                                    <label>
                                                        <input ${agregado == 0 ? 'disabled' : ''} ${agregado == 0 ? 'checked' : ''} name="idExpediente" value="${familia.getIdexpedienteFamilia()}" type="checkbox"> 
                                                    </label>
                                                        <c:if test="${agregado == 0}">
                                                            <h4><strong>Expediente ya agregado</strong></h4>
                                                        </c:if>
                                                    </div>
                                                </td>
                                             </tr>
                                  </c:forEach>  
                                </tbody>
                              </c:if> 
                               <c:if test="${listaBusqueda.isEmpty()}">
                                    <h3><strong>No se encontraron expedientes</strong></h3>
                                </c:if>  
                            </table>
                        </div>
                        <br>
                        <button id="singlebutton" name="singlebutton" class="btn btn-default">Agregar</button>
                        </form>
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