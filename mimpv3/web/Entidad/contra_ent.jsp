<%-- 
    Document   : cuenta_familia1
    Created on : 28/10/2013, 07:01:45 AM
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
                            <!--<li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>-->
                            <li class="active"><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>

                    <div class="col-md-6 col-md-offset-1">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inicioEnt'"  id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Cambio de contraseña</strong></h1>
                        <br>
                        <form class="form-horizontal" role="form" action="${pageContext.servletContext.contextPath}/Orgcambiarcontra" method="post">
                            <fieldset>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="pass_actual">Contraseña Actual</label>
                                    <div>
                                        <input id="oldpass" name="oldpass" type="password" placeholder="" class="input-xlarge">
                                    </div>
                                    <br>
                                    <label class="control-label" for="pass_nuevo">Nueva Contraseña</label>
                                    <div>
                                        <input id="newpass" name="newpass" type="password" placeholder="" class="input-xlarge">
                                    </div>
                                    <br>
                                    <label class="control-label" for="pass_nuevo2">Reescribir nueva contraseña</label>
                                    <div>
                                        <input id="newpassconf" name="newpassconf" type="password" placeholder="" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <p style="color: red">${mensaje}</p>
                                <br>
                                <!-- Button -->
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" d="singlebutton" name="singlebutton" class="btn btn-default">Cambiar Contraseña</button>
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

            <!-- Ubicar al final -->
    </body>
</html>

