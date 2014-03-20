<%-- 
    Document   : reg_desig
    Created on : 4/12/2013, 11:29:03 AM
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
                                <%if (u.getRol().equals("DAPA") || u.getRol().equals("DCRI") || u.getRol().equals("DEIA Prio")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-6 col-md-offset-1">
                         <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/nnaPrioritarios'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li><a href="${pageContext.servletContext.contextPath}/nna" >NNA Regulares</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/nnaPrioritarios" >NNA Prioritarios</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/nnaSeguimiento" >NNA en Seguimiento</a></li>
                        </ul>
                        <br>
                        <br>
                        <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/insertarEstudio" method="post" name="formulario" onkeypress="return enter(event)">
                            <br>
                            <div class="control-group">
                                <label class="control-label">Identificación del Estudio de Caso</label>
                                <div class="controls">
                                    <input id="orden" name="orden" type="text" value="" >
                                </div>
                            </div>
                            <br>
                            <br>
                            <h1 align="center"><strong>Familias que conforman el Estudio de Caso</strong></h1>
                            <br>
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th class="col-sm-2 " >Expediente</th>
                                            <th class="col-sm-2 " >Nivel sociec</th>
                                            <th class="col-sm-2 " >Resolución de aptitud</th>
                                            <th class="col-sm-2 " >Prioridad</th>
                                            <th class="col-sm-2 " >Eliminar</th>
                                        </tr>
                                    </thead>
                                    <c:if test="${!listaEstudioCaso.isEmpty()}">
                                        <tbody>
                                            <c:forEach var="familia" items="${listaEstudioCaso}" varStatus="indexFam">
                                                <tr>
                                                    <td>${familia.getExpediente()}</td>
                                                    <td>
                                                        <c:forEach var="info" items="${familia.getFamilia().getInfoFamilias()}" varStatus="status">
                                                            ${info.getNivelSocioeconomico()}
                                                        </c:forEach>
                                                    </td>
                                                    <td>
                                                        <c:forEach var="eval" items="${familia.getEvaluacions()}" varStatus="status">
                                                            <c:forEach var="resolucion" items="${eval.getResolucions()}" varStatus="status">
                                                                ${resolucion.getFechaResol() != null ? df.dateToString(resolucion.getFechaResol()) : ''}
                                                            </c:forEach>
                                                        </c:forEach>
                                                    </td>
                                                    <td>
                                                        <select id="prioridad" name="prioridad">
                                                            <c:forEach var="familia2" items="${listaEstudioCaso}" varStatus="indexPrioridad">
                                                                <option ${indexFam.count == indexPrioridad.count ? 'selected' : ''} value="${indexPrioridad.count}" >${indexPrioridad.count}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <div class="checkbox">
                                                            <label>
                                                                <input id="delete" name="delete" value="${indexFam.index}" type="checkbox"> 
                                                            </label>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </c:if> 
                                    <c:if test="${listaEstudioCaso.isEmpty()}">
                                        <h3><strong>No existen Familias propuestas</strong></h3>
                                    </c:if>  
                                </table>
                            </div>
                            <br>
                            <br>
                            <input hidden name="idNna" id="idNna" value="${idNna}"> 
                            <input hidden name="numero" id="numero" value="${numero}"> 
                            <input type="submit" id="agregar" name="agregar" value="Agregar Familia" class="btn btn-default">


                            <input type="submit" id="eliminar" name="eliminar" value="Eliminar Familia" class="btn btn-default">
                            <br>
                            <br>
                            <input type="submit" id="registrar" name="registrar" value="Registrar" class="btn btn-default">

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
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
        <script type="text/javascript">

                            $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

        </script>
        <script type="text/javascript">
            function enter(e) {
                if (e.keyCode == 13) {
                    return false;
                }
            }
        </script>
        <script type="text/javascript">

            function validar()
            {

                if (document.formulario.orden.value == "")
                {
                    alert("Debe ingresar un número de orden");
                    document.formulario.orden.focus();
                    return false;
                }
                if (document.formulario.fechaEval.value == "")
                {
                    alert("Debe ingresar la fecha");
                    document.formulario.fechaEval.focus();
                    return false;
                }

                return true;
            }
        </script>
        <!-- Ubicar al final -->
    </body>
</html>