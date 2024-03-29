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
                        <p align="right"><button id="singlebutton" onclick="location.href = '${pageContext.servletContext.contextPath}/autoridad'" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Editar Autoridad</strong></h1>
                        <p style="color: red">${mensaje}</p>
                        <br>
                        <c:if test="${autoridad.getIdautoridad() == null}">
                            <form name="formulario" class="form-horizontal" action="${pageContext.servletContext.contextPath}/editAut" method="post" onsubmit="return(validar());" onkeypress="return enter(event)"> 
                            </c:if>  
                            <c:if test="${autoridad.getIdautoridad() != null}">
                                <form name="formulario" class="form-horizontal" action="${pageContext.servletContext.contextPath}/updateAut" method="post" onsubmit="return(validar());" onkeypress="return enter(event)"> 
                                    <input hidden name="id" id="id" value="${autoridad.getIdautoridad()}">
                                </c:if>     

                                <fieldset>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label">Nombre</label>
                                        <div class="controls">
                                            <input onkeyup="return(limitar());" id="nombre" name="nombre" type="text" value="${autoridad.getEntidad().getNombre()}" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div>
                                        <label class="control-label">Tipo</label>
                                        <div class="controls">
                                            <select id="tipo" name="tipo" >
                                                <option value="sia" ${autoridad.getTipo() == 'sia' ? 'selected' : ''}>SIA</option>
                                                <option value="mia" ${autoridad.getTipo() == 'mia' ? 'selected' : ''}>MIA</option>
                                            </select>
                                        </div>    
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">País</label>
                                        <div class="controls">
                                            <input onkeyup="return(limitar());"  id="pais" name="pais" value="${autoridad.getEntidad().getPais()}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Dirección</label>
                                        <div class="controls">
                                            <input onkeyup="return(limitar());"  id="direccion" name="direccion" value="${autoridad.getEntidad().getDireccion()}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Teléfono</label>
                                        <div class="controls">
                                            <input onkeyup="return(limitar());"  id="telefono" name="telefono" value="${autoridad.getEntidad().getTelefono()}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Usuario</label>  
                                        <div class="controls">
                                            <input onkeyup="return(limitar());"  id="user" name="user" value="${autoridad.getEntidad().getUser()}" type="text" class="input-xlarge">
                                        </div>
                                    </div>

                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Contraseña</label>
                                        <div class="controls">
                                            <input onkeyup="return(limitar());"  id="pass" name="pass" type="password" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>

                                    <div class="control-group">
                                        <label class="control-label">Ingresar nuevamente Contraseña</label>
                                        <div class="controls">
                                            <input onkeyup="return(limitar());"  id="pass2" name="pass2" type="password" value="" class="input-xlarge">
                                        </div>
                                    </div>

                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Correo</label>  
                                        <div class="controls">
                                            <input onkeyup="return(limitar());"  id="correo" name="correo" value="${autoridad.getEntidad().getCorreo()}" type="text" class="input-xlarge">
                                        </div>
                                    </div>

                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Resolución de </label>
                                        <label>autorización</label>
                                        <div class="controls">
                                            <input onkeyup="return(limitar());"  id="resol_aut" name="resol_aut" type="text" value="${autoridad.getEntidad().getResolAuto()}" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Fecha de emisión de </label>
                                        <label>resolución</label>
                                        <div class="controls">
                                            <input id="fecha_emis_resol" name="fecha_emis_resol" value="${fechaEmision}" type="text" class="datepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Resolución de </label>
                                        <label>renovación</label>
                                        <div class="controls">
                                            <input onkeyup="return(limitar());"  id="resol_renov" name="resol_renov" value="${autoridad.getEntidad().getResolRenov()}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Fecha de renovación</label>
                                        <div class="controls">
                                            <input id="fecha_renov" name="fecha_renov" type="text" value="${fechaRenov}" class="datepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Fecha de vencimiento </label>
                                        <label>de autorización</label>
                                        <div class="controls">
                                            <input id="fecha_venc_aut" name="fecha_venc_aut" type="text" value="${fechaVenc}" class="datepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Observaciones</label>
                                        <div class="controls">
                                            <textarea  onkeyup="return(limitar());" class="input-xlarge" name="obs" id="obs" placeholder="" rows="5" cols="25">${autoridad.getEntidad().getObs()}</textarea>
                                        </div>
                                    </div>
                                    <br>
                                    <br>
                                    <!-- Button -->
                                    <div class="control-group">
                                        <div class="controls">
                                            <button id="singlebutton" name="singlebutton" class="btn btn-default">Guardar</button>
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
            <script type="text/javascript">

                function validar()
                {
                    var str1 = document.getElementById("pass").value;
                    var str2 = document.getElementById("pass2").value;

                    if (str1 !== str2)
                    {
                        alert("Las contraseñas ingresadas no son iguales. Por favor ingresar nuevamente.");
                        return false;
                    }
                    return(true);
                }

            </script>
            <script type="text/javascript">
                function enter(e) {
                    if (e.keyCode == 13) {
                        return false;
                    }
                }
            </script>
            <script type="text/javascript">
                function limitar()
                {                    
                    var nombre = document.getElementById('nombre');
                    var pais = document.getElementById('pais');
                    var direccion = document.getElementById('direccion');
                    var telefono = document.getElementById('telefono');
                    var user = document.getElementById('user');
                    var pass = document.getElementById('pass');
                    var pass2 = document.getElementById('pass2');
                    var correo = document.getElementById('correo');
                    var resol_aut = document.getElementById('resol_aut');
                    var resol_renov = document.getElementById('resol_renov');
                    var obs = document.getElementById('obs');

                    if (nombre.value.length < 0 || nombre.value.length > 39)
                    {
                        alert("solo puede ingresar 40 caracteres");
                        nombre.value = nombre.value.substring(0, 39);
                        document.formulario.nombre.focus();
                        return false;
                    } else if (pais.value.length < 0 || pais.value.length > 14) {
                        alert("solo puede ingresar 15 caracteres");
                        pais.value = pais.value.substring(0, 14);
                        document.formulario.pais.focus();
                        return false;
                    }else if (direccion.value.length < 0 || direccion.value.length > 199) {
                        alert("solo puede ingresar 200 caracteres");
                        direccion.value = direccion.value.substring(0, 199);
                        document.formulario.direccion.focus();
                        return false;
                    }else if (telefono.value.length < 0 || telefono.value.length > 14) {
                        alert("solo puede ingresar 15 caracteres");
                        telefono.value = telefono.value.substring(0, 14);
                        document.formulario.telefono.focus();
                        return false;
                    }else if (user.value.length < 0 || user.value.length > 44) {
                        alert("solo puede ingresar 45 caracteres");
                        user.value = user.value.substring(0, 44);
                        document.formulario.user.focus();
                        return false;
                    }else if (pass.value.length < 0 || pass.value.length > 511) {
                        alert("solo puede ingresar 512 caracteres");
                        pass.value = pass.value.substring(0, 511);
                        document.formulario.pass.focus();
                        return false;
                    }else if (pass2.value.length < 0 || pass2.value.length > 511) {
                        alert("solo puede ingresar 512 caracteres");
                        pass2.value = pass2.value.substring(0, 511);
                        document.formulario.pass2.focus();
                        return false;
                    }else if (correo.value.length < 0 || correo.value.length > 49) {
                        alert("solo puede ingresar 50 caracteres");
                        correo.value = correo.value.substring(0, 49);
                        document.formulario.correo.focus();
                        return false;
                    }else if (resol_aut.value.length < 0 || resol_aut.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        resol_aut.value = resol_aut.value.substring(0, 29);
                        document.formulario.resol_aut.focus();
                        return false;
                    }else if (resol_renov.value.length < 0 || resol_renov.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        resol_renov.value = resol_renov.value.substring(0, 29);
                        document.formulario.resol_renov.focus();
                        return false;
                    }else if (obs.value.length < 0 || obs.value.length > 199) {
                        alert("solo puede ingresar 200 caracteres");
                        obs.value = obs.value.substring(0, 199);
                        document.formulario.obs.focus();
                        return false;
                    }

                }
            </script>
            <!-- Ubicar al final -->
    </body>
</html>