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
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1>Edición de taller</h1>
                        <br>
                        <!-- Select Basic -->
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Nombre del taller:</label>
                            <div class="controls">
                                <input id="textinput" name="textinput" type="text" placeholder="Nombre" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="selectbasic">Tipo de Taller</label>
                            <div class="controls">
                                <select id="selectbasic" name="selectbasic" class="input-xlarge">
                                    <option>Preparación</option>
                                    <option>Lista de espera</option>
                                    <option>Post Adopción</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="selectbasic">Asociado a sesión: (Sólo en caso se trate de un Taller de Preparación)</label>
                            <div class="controls">
                                <select id="selectbasic" name="selectbasic" class="input-xlarge">
                                    <option>Ninguno</option>
                                    <option>01-2013</option>
                                    <option>02-2013</option>
                                    <option>03-2013</option>
                                    <option>04-2013</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="textinput">Descripción:</label>
                            <div class="controls">
                                <textarea id="descripción" class="input-xlarge" name="message" placeholder="" rows="5" ></textarea>
                            </div>
                        </div>
                        <br>
                        <!-- Multiple Radios (inline) -->
                        <div class="control-group">
                            <label class="control-label" for="radios">Habilitado para inscripción:</label>
                            <div class="controls">
                                <label class="radio inline" for="radios-0">
                                    <input type="radio" name="radios" id="radios-0" value="1" checked="checked">
                                    Si
                                </label>
                                <label class="radio inline" for="radios-1">
                                    <input type="radio" name="radios" id="radios-1" value="2">
                                    No
                                </label>
                            </div>
                        </div>
                        <br>
                        <h1>Listado de Grupos</h1>
                        <br>
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>id de Grupo</th>
                                    <th>Grupo</th>
                                    <th>N° de Turnos Habilitados</th>
                                    <th>Edicion</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>001-012-001</td>
                                    <td>Grupo 1</td>
                                    <td>2</td>
                                    <td><button href="#" class="btn btn-default">Modificar</button></td>
                                </tr>
                                <tr>
                                    <td>001-012-002</td>
                                    <td>Grupo 2</td>
                                    <td>1</td>
                                    <td><button href="#" class="btn btn-default">Modificar</button></td>
                                </tr>
                                <tr>
                                    <td>001-012-003</td>
                                    <td>Grupo 3</td>
                                    <td>2</td>
                                    <td><button href="#" class="btn btn-default">Modificar</button></td>
                                </tr>
                            </tbody>
                        </table>
                        <br>
                        <button id="singlebutton" name="singlebutton" class="btn btn-primary">Agregar Grupo</button>
                        <br>
                        <br>
                        <button id="singlebutton" name="singlebutton" class="btn btn-primary">Guardar cambios</button>
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