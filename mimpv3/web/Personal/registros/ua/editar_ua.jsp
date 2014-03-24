<%-- 
    Document   : inicio_personal
    Created on : 28/10/2013, 09:54:29 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Personal"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    
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
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/ua'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Editar Unidad de Adopción</strong></h1>
                        <br>
                        <c:if test="${ua.getIdunidad() == null}">
                            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/editUa" method="post">
                            </c:if>  
                            <c:if test="${ua.getIdunidad() != null}">
                                <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/updateUa" method="post"> 
                                    <input hidden name="id" id="id" value="${ua.getIdunidad()}">
                                </c:if>  
                                <fieldset>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label">Nombre</label>
                                        <div class="controls">
                                            <input id="nombre" name="nombre" type="text" value="${ua.getNombre()}" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Dirección</label>
                                        <div class="controls">
                                            <input id="direccion" name="direccion" type="text" value="${ua.getDireccion()}" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div>
                                        <label class="control-label">Departamento</label>
                                        <div class="controls">
                                            <select id="departamento" name="departamento">
                                                <option value="Lima" ${ua.getDepartamento().equals("Lima") ? 'selected' : ''} >Lima</option>
                                                <option value="Arequipa" ${ua.getDepartamento().equals("Arequipa") ? 'selected' : ''}>Arequipa</option>
                                                <option value="Ayacucho" ${ua.getDepartamento().equals("Ayacucho") ? 'selected' : ''} >Ayacucho</option>
                                                <option value="Cusco" ${ua.getDepartamento().equals("Cusco") ? 'selected' : ''} >Cusco</option>
                                                <option value="Huanuco" ${ua.getDepartamento().equals("Huanuco") ? 'selected' : ''} >Huanuco</option>
                                                <option value="Lambayeque" ${ua.getDepartamento().equals("Lambayeque") ? 'selected' : ''} >Lambayeque</option>
                                                <option value="Libertad" ${ua.getDepartamento().equals("Libertad") ? 'selected' : ''} >La Libertad</option>                                            
                                                <option value="Loreto" ${ua.getDepartamento().equals("Loreto") ? 'selected' : ''} >Loreto</option>
                                                <option value="Piura" ${ua.getDepartamento().equals("Piura") ? 'selected' : ''} >Piura</option>
                                                <option value="Puno" ${ua.getDepartamento().equals("Puno") ? 'selected' : ''} >Puno</option>
                                                <option value="Junin" ${ua.getDepartamento().equals("Junin") ? 'selected' : ''} >Junin</option>
                                            </select>
                                        </div>    
                                    </div>
                                    <br>   
                                    <!--
                                    <div class="control-group">
                                        <label class="control-label">Departamento</label>
                                        <div class="controls">
                                            <input id="departamento" name="departamento" type="text" value="${ua.getDepartamento()}" class="input-xlarge">
                                        </div>
                                    </div>                                    
                                    <br>
                                    -->
                                    <div class="control-group">
                                        <label class="control-label">Provincia</label>
                                        <div class="controls">
                                            <input id="provincia" name="provincia" type="text" value="${ua.getProvincia()}" class="input-xlarge">
                                        </div>
                                    </div>                                    
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Distrito</label>
                                        <div class="controls">
                                            <input id="distrito" name="distrito" type="text" value="${ua.getDistrito()}" class="input-xlarge">
                                        </div>
                                    </div>                                    
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Competencia Regional</label>
                                        <div class="controls">
                                            <input id="competenciaR" name="competenciaR" type="text" value="${ua.getCompetenciaRegional()}" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Correo</label>
                                        <div class="controls">
                                            <input id="correo" name="correo" type="text" value="${ua.getCorreo()}" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>                                    
                                    <div class="control-group">
                                        <label class="control-label">Teléfono</label>
                                        <div class="controls">
                                            <input id="telefono" name="telefono" type="text" value="${ua.getTelefono()}" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>                                   
                                    <div class="control-group">
                                        <label class="control-label">Celular</label>
                                        <div class="controls">
                                            <input id="celular" name="celular" type="text" value="${ua.getCelular()}" class="input-xlarge">
                                        </div>
                                    </div>                                    
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Observaciones</label>
                                        <div class="controls">
                                            <textarea class="input-xlarge" id="obs" name="obs" placeholder="Ingrese sus observaciones" rows="5" >${ua.getObs()}</textarea>
                                        </div>
                                    </div>                                    
                                    <br>
                                    <br>
                                    <!-- Button -->
                                    <div class="control-group">
                                        <div class="controls">
                                            <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar</button>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>

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
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
            <script type="text/javascript">

                            $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

            </script>
            <!-- Ubicar al final -->
    </body>
</html>