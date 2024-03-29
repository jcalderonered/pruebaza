<%-- 
    Document   : cuenta_familia1
    Created on : 28/10/2013, 07:01:45 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Familia"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");

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
                            <li><a href="${pageContext.servletContext.contextPath}/inicioFam">Inicio</a></li>
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
                            <li><a href="${pageContext.servletContext.contextPath}/Flecturas"><span class="glyphicon glyphicon-chevron-right"></span> Lecturas </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Festado"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/Fcontra"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>
                    <div class="col-md-6 col-md-offset-1">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inicioFam'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <form class="form-horizontal" role="form" action="${pageContext.servletContext.contextPath}/Fcambiarcontra" method="post" onsubmit="return(validar());">
                            <fieldset>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Contraseña Actual</label>
                                    <input id="oldpass" name="oldpass" type="password" placeholder="" class="input-xlarge">
                                    <br>
                                    <label class="control-label" for="textinput">Nueva Contraseña</label>
                                    <input id="newpass" name="newpass" type="password" placeholder="" onkeyup="return(limitar());" class="input-xlarge">
                                    <br>
                                    <label class="control-label" for="textinput">Reescribir nueva contraseña</label>
                                    <input id="newpassconf" name="newpassconf" type="password" placeholder="" onkeyup="return(limitar());" class="input-xlarge">
                                </div>
                                <br>
                                <p style="color: red">${mensaje}</p>
                                <br>
                                <!-- Button -->
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Cambiar Contraseña</button>
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
            <script type="text/javascript">
                                        function limitar()
                                        {
                                            var newpass = document.getElementById('newpass');
                                            var newpassconf = document.getElementById('newpassconf');

                                            if (newpass.value.length < 0 || newpass.value.length > 15) {
                                                alert("la contraseña tiene como máximo 15 caracteres");
                                                newpass.value = newpass.value.substring(0, 15);
                                                return false;
                                            } else if (newpassconf.value.length < 0 || newpassconf.value.length > 15) {
                                                alert("la contraseña tiene como máximo 15 caracteres");
                                                newpassconf.value = newpassconf.value.substring(0, 15);
                                                return false;
                                            }
                                        }
            </script>
            <script type="text/javascript">
                function validar()
                {
                    var newpass = document.getElementById('newpass');
                    var newpassconf = document.getElementById('newpassconf');
                    
                    if (newpass.value != newpassconf.value){
                        alert("Las contraseñas nuevas no coinciden");
                        return false;
                    }
                    if (newpass.value.length > 0 && newpass.value.length < 5){
                        alert("La contraseña debe ser de al menos 5 caracteres");
                        return false;
                    }
                    return(true);
                }
            </script>
            <!-- Ubicar al final -->
    </body>
</html>

