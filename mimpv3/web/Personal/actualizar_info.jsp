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
                            <li><a href="${pageContext.servletContext.contextPath}/inicioper">Inicio</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/act_info">Actualizar Información</a></li>
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
                                <%if (u.getRol().equals("DCRI") || u.getRol().equals("DGA") || u.getRol().equals("admin") || u.getRol().equals("UA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/inf"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de sesiones/talleres</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNA</a></li>
                                <%if (u.getRol().equals("mpartes")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                                <%}%>
                                <%if (u.getRol().equals("admin")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/juzgado"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de juzgado</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ua"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UA</a></li>
                                <%}
                                    if (u.getRol().equals("DEIA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li> 
                                <%}
                                    if (!u.getRol().equals("DAPA") && !u.getRol().equals("MATCH") && !u.getRol().equals("UA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/famint"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                                <%}%>

                            <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>

                            <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de registros</a></li>
                                <%if (!u.getRol().equals("DEIA Prio") && !u.getRol().equals("UA") && !u.getRol().equals("DAPA") && !u.getRol().equals("mpartes")) {%>
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
                        <h1 align="center"><strong>Actualizar información</strong></h1>
                        <br>
                        <form class="form-horizontal" role="form" action="${pageContext.servletContext.contextPath}/act_info/act" method="post">
                            <fieldset>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label">Nombre</label>
                                    <div class="controls">
                                        <input disabled value="<%=u.getNombre()%>" id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Paterno</label>
                                    <div class="controls">
                                        <input disabled value="<%=u.getApellidoP()%>" id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Materno</label>
                                    <div class="controls">
                                        <input disabled value="<%=u.getApellidoM()%>" id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Usuario</label>
                                    <div class="controls">
                                        <input disabled value="<%=u.getUser()%>" id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Correo Trabajo</label>
                                    <div class="controls">
                                        <input id="correo_trabajo" value="<%=u.getCorreoTrabajo()%>" name="correo_trabajo" type="text" onkeyup="return(limitar());" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Correo Personal</label>
                                    <div class="controls">
                                        <input id="correo_personal" value="<%=u.getCorreoPersonal()%>" name="correo_personal" type="text" onkeyup="return(limitar());" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Profesión</label>
                                    <div class="controls">
                                        <input id="profesion" value="<%=u.getProfesion()%>" name="profesion" type="text" onkeyup="return(limitar());" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Grado de instrucción</label>
                                    <div class="controls">
                                        <input id="grado_instruccion" value="<%=u.getGradoInstruccion()%>" name="grado_instruccion" type="text" onkeyup="return(limitar());" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Cargo</label>
                                    <div class="controls">
                                        <input id="cargo" value="<%=u.getCargo()%>" name="cargo" type="text" onkeyup="return(limitar());" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">DNI</label>
                                    <div class="controls">
                                        <input disabled value="<%=u.getDni()%>" id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de nacimiento</label>
                                    <div class="controls">
                                        <input disabled value="${fechanac}" id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Regimen</label>
                                    <div class="controls">
                                        <input value="<%=u.getRegimen()%>" id="regimen" name="regimen" type="text" onkeyup="return(limitar());" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de ingreso</label>
                                    <div class="controls">
                                        <input disabled id="full-name" value="${fechaing}" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Domicilio</label>
                                    <div class="controls">
                                        <input id="domicilio" value="<%=u.getDomicilio()%>" name="domicilio" type="text" onkeyup="return(limitar());" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div>
                                    <label class="control-label">Rol: <%=u.getRol()%></label>
                                </div>
                                <br>
                                <div>
                                    <label class="control-label">Unidad de adopción: <%=u.getUnidad().getNombre()%></label> 
                                </div>
                                <br>
                                <br>
                                <br>
                                <!-- Button -->
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Actualizar datos</button>
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

            <script type="text/javascript">
                function limitar()
                {
                    var correo_trabajo = document.getElementById('correo_trabajo');
                    var correo_personal = document.getElementById('correo_personal');
                    var profesion = document.getElementById('profesion');
                    var grado_instruccion = document.getElementById('grado_instruccion');
                    var cargo = document.getElementById('cargo');
                    var regimen = document.getElementById('regimen');
                    var domicilio = document.getElementById('domicilio');

                    if (correo_trabajo.value.length < 0 || correo_trabajo.value.length > 99) {
                        alert("El campo no debe exceder más de 100 caracteres");
                        correo_trabajo.value = correo_trabajo.value.substring(0, 99);
                        return false;
                    } else if (correo_personal.value.length < 0 || correo_personal.value.length > 99) {
                        alert("El campo no debe exceder más de 100 caracteres");
                        correo_personal.value = correo_personal.value.substring(0, 99);
                        return false;
                    } else if (profesion.value.length < 0 || profesion.value.length > 49) {
                        alert("El campo no debe exceder más de 50 caracteres");
                        profesion.value = profesion.value.substring(0, 49);
                        return false;
                    } else if (grado_instruccion.value.length < 0 || grado_instruccion.value.length > 29) {
                        alert("El campo no debe exceder más de 30 caracteres");
                        grado_instruccion.value = grado_instruccion.value.substring(0, 29);
                        return false;
                    } else if (cargo.value.length < 0 || cargo.value.length > 29) {
                        alert("El campo no debe exceder más de 30 caracteres");
                        cargo.value = cargo.value.substring(0, 29);
                        return false;
                    } else if (regimen.value.length < 0 || regimen.value.length > 49) {
                        alert("El campo no debe exceder más de 50 caracteres");
                        regimen.value = regimen.value.substring(0, 49);
                        return false;
                    } else if (domicilio.value.length < 0 || domicilio.value.length > 199) {
                        alert("El campo no debe exceder más de 200 caracteres");
                        domicilio.value = domicilio.value.substring(0, 199);
                        return false;
                    }
                }
            </script>
    </body>
</html>