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
                        <c:if test="${idUA != 0 && idUA != null}">
                            <form action="${pageContext.servletContext.contextPath}/irListaPersonalUa" method="post" >
                                <input hidden name="idUA" id="idUA" value="${idUA}">
                                <p align="right"><button type="submit" class="btn btn-default" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                            </form>
                        </c:if>
                        <c:if test="${personal.getIdpersonal() == null}">     
                            <form action="${pageContext.servletContext.contextPath}/ua" method="post" >
                                <input hidden name="idUA" id="idUA" value="${idUA}">
                                <!-- <p align="right"><button type="submit" class="btn btn-default" style="background: black; color: white" class="btn btn-default">Volver</button></p>-->
                            </form>
                            <h1 align="center"><strong>Editar Personal</strong></h1>
                            <p style="color: red">${mensaje}</p>
                            <br>
                            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/editPersonal" method="post"  onsubmit="return(validar());" onkeypress="return enter(event)"> 
                            </c:if>  
                            <c:if test="${personal.getIdpersonal() != null && disabled == 'deshabilitar'}">

                                <h1 align="center"><strong>Editar Personal</strong></h1>

                                <br>
                                <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/updatePersonalUa" method="post" onsubmit="return(validar());" onkeypress="return enter(event)">
                                    <input hidden name="idPers" id="idPers" value="${personal.getIdpersonal()}">    
                                </c:if> 
                                <c:if test="${personal.getIdpersonal() != null && disabled != 'deshabilitar'}">
                                    <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/usuarios'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                                    <h1 align="center"><strong>Editar Personal</strong></h1>
                                    <p style="color: red">${mensaje}</p>
                                    <br>
                                    <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/updatePersonal" method="post" onsubmit="return(validar());" onkeypress="return enter(event)"> 
                                        <input hidden name="idPers" id="idPers" value="${personal.getIdpersonal()}">
                                    </c:if>  
                                    <fieldset>
                                        <!-- Text input-->
                                        <div class="control-group">
                                            <label class="control-label">Nombre</label>
                                            <div class="controls">
                                                <input id="nombre" name="nombre" type="text" value="${personal.getNombre()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Apellido Paterno</label>
                                            <div class="controls">
                                                <input id="apellidoP" name="apellidoP" type="text" value="${personal.getApellidoP()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Apellido Materno</label>
                                            <div class="controls">
                                                <input  id="apellidoM" name="apellidoM" type="text" value="${personal.getApellidoM()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Usuario</label>
                                            <div class="controls">
                                                <input id="user" name="user" type="text" value="${personal.getUser()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Contraseña</label>
                                            <div class="controls">
                                                <input id="pass" name="pass" type="password" value="" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>

                                        <div class="control-group">
                                            <label class="control-label">Ingresar nuevamente Contraseña</label>
                                            <div class="controls">
                                                <input id="pass2" name="pass2" type="password" value="" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>

                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Correo Trabajo</label>
                                            <div class="controls">
                                                <input id="correoT" name="correoT" type="text" value="${personal.getCorreoTrabajo()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Correo Personal</label>
                                            <div class="controls">
                                                <input id="correoP" name="correoP" type="text" value="${personal.getCorreoPersonal()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Profesión</label>
                                            <div class="controls">
                                                <input id="profesion" name="profesion" type="text" value="${personal.getProfesion()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Grado de instrucción</label>
                                            <div class="controls">
                                                <input id="grado" name="grado" type="text" value="${personal.getGradoInstruccion()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Cargo</label>
                                            <div class="controls">
                                                <input id="cargo" name="cargo" type="text" value="${personal.getCargo()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">DNI</label>
                                            <div class="controls">
                                                <input  id="dni" name="dni" type="text" value="${personal.getDni()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Fecha de nacimiento</label>
                                            <div class="controls">
                                                <input  id="fechaNac" name="fechaNac" type="text" value="${fechaNac}" class="datepicker input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Regimen</label>
                                            <div class="controls">
                                                <input id="regimen" name="regimen" type="text" value="${personal.getRegimen()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Fecha de ingreso</label>
                                            <div class="controls">
                                                <input id="fechaIng" name="fechaIng" type="text" value="${fechaIng}" class="datepicker input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Domicilio</label>
                                            <div class="controls">
                                                <input id="domicilio" name="domicilio" type="text" value="${personal.getDomicilio()}" onkeyup="return(limitar());" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div>
                                            <label class="control-label">Rol</label>
                                            <div class="controls">
                                                <select  id="rol" name="rol">
                                                    <option value="DEIA" ${personal.getRol() == 'DEIA' ? 'selected' : ''}>DEIA</option>
                                                    <option value="DCRI" ${personal.getRol() == 'DCRI' ? 'selected' : ''}>DCRI</option>
                                                    <option value="DAPA" ${personal.getRol() == 'DAPA' ? 'selected' : ''}>DAPA</option>
                                                    <option value="DGA" ${personal.getRol() == 'DGA' ? 'selected' : ''}>DGA</option>
                                                    <option value="DEIA Prio" ${personal.getRol() == 'DEIA Prio' ? 'selected' : ''}>DEIA Prioritario</option>
                                                    <option value="MATCH" ${personal.getRol() == 'MATCH' ? 'selected' : ''}>MATCH</option>
                                                    <option value="mpartes" ${personal.getRol() == 'mpartes' ? 'selected' : ''}>Mesa de Partes</option>
                                                    <option value="UA" ${personal.getRol() == 'UA' ? 'selected' : ''}>UA</option>
                                                    <option value="admin" ${personal.getRol() == 'admin' ? 'selected' : ''}>admin</option>
                                                </select>
                                            </div>    
                                        </div>
                                        <br>
                                        <div>
                                            <label class="control-label">Unidad de adopción</label>
                                            <div class="controls">
                                                <select id="ua" name="ua" ${disabled == 'deshabilitar' ? 'disabled' : ''}>
                                                    <c:forEach var="ua" items="${listaUa}" varStatus="status"> 
                                                        <option value="${ua.getIdunidad()}" ${ua.getIdunidad() == personal.getUnidad().getIdunidad() ? 'selected' : ''}>${ua.getNombre()}</option>
                                                    </c:forEach> 
                                                </select>
                                            </div>    
                                        </div>
                                        <br>
                                        <br>
                                        <br>
                                        <!-- Button -->
                                        <div class="control-group">
                                            <div class="controls">
                                                <button id="singlebutton" name="singlebutton" class="btn btn-default" >Guardar</button>
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
                                        var apellidoP = document.getElementById('apellidoP');
                                        var apellidoM = document.getElementById('apellidoM');
                                        var user = document.getElementById('user');
                                        var pass = document.getElementById('pass');
                                        var pass2 = document.getElementById('pass2');
                                        var correoT = document.getElementById('correoT');
                                        var correoP = document.getElementById('correoP');
                                        var profesion = document.getElementById('profesion');
                                        var grado = document.getElementById('grado');
                                        var cargo = document.getElementById('cargo');
                                        var dni = document.getElementById('dni');
                                        var regimen = document.getElementById('regimen');
                                        var domicilio = document.getElementById('domicilio');

                                        if (nombre.value.length < 0 || nombre.value.length > 29) {
                                            alert("El campo no debe exceder más de 30 caracteres");
                                            nombre.value = nombre.value.substring(0, 29);
                                            return false;
                                        } else if (apellidoP.value.length < 0 || apellidoP.value.length > 29) {
                                            alert("El campo no debe exceder más de 30 caracteres");
                                            apellidoP.value = apellidoP.value.substring(0, 29);
                                            return false;
                                        } else if (apellidoM.value.length < 0 || apellidoM.value.length > 29) {
                                            alert("El campo no debe exceder más de 30 caracteres");
                                            apellidoM.value = apellidoM.value.substring(0, 29);
                                            return false;
                                        } else if (user.value.length < 0 || user.value.length > 99) {
                                            alert("El campo no debe exceder más de 100 caracteres");
                                            user.value = user.value.substring(0, 99);
                                            return false;
                                        } else if (pass.value.length < 0 || pass.value.length > 15) {
                                            alert("El campo no debe exceder más de 15 caracteres");
                                            pass.value = pass.value.substring(0, 15);
                                            return false;
                                        } else if (pass2.value.length < 0 || pass2.value.length > 15) {
                                            alert("El campo no debe exceder más de 15 caracteres");
                                            pass2.value = pass2.value.substring(0, 15);
                                            return false;
                                        } else if (correoT.value.length < 0 || correoT.value.length > 99) {
                                            alert("El campo no debe exceder más de 100 caracteres");
                                            correoT.value = correoT.value.substring(0, 99);
                                            return false;
                                        } else if (correoP.value.length < 0 || correoP.value.length > 99) {
                                            alert("El campo no debe exceder más de 100 caracteres");
                                            correoP.value = correoP.value.substring(0, 99);
                                            return false;
                                        } else if (profesion.value.length < 0 || profesion.value.length > 49) {
                                            alert("El campo no debe exceder más de 50 caracteres");
                                            profesion.value = profesion.value.substring(0, 49);
                                            return false;
                                        } else if (grado.value.length < 0 || grado.value.length > 29) {
                                            alert("El campo no debe exceder más de 30 caracteres");
                                            grado.value = grado.value.substring(0, 29);
                                            return false;
                                        } else if (cargo.value.length < 0 || cargo.value.length > 29) {
                                            alert("El campo no debe exceder más de 30 caracteres");
                                            cargo.value = cargo.value.substring(0, 29);
                                            return false;
                                        } else if (dni.value.length < 0 || dni.value.length > 9) {
                                            alert("El campo no debe exceder más de 10 caracteres");
                                            dni.value = dni.value.substring(0, 9);
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
                                <!-- Ubicar al final -->
                                </body>
                                </html>