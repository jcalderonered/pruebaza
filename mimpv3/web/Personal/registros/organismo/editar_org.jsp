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
                        <p align="right"><button id="singlebutton" onclick="location.href = '${pageContext.servletContext.contextPath}/organismo'" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Editar Organismo</strong></h1>
                        <p style="color: red">${mensaje}</p>
                        <br>
                        <c:if test="${organismo.getIdorganismo() == null}">
                            <form name="formulario" class="form-horizontal" action="${pageContext.servletContext.contextPath}/editOrg" method="post">
                            </c:if>  
                            <c:if test="${organismo.getIdorganismo() != null}">
                                <form name="formulario" class="form-horizontal" action="${pageContext.servletContext.contextPath}/updateOrg" method="post"> 
                                    <input hidden name="id" id="id" value="${organismo.getIdorganismo()}">
                                </c:if>  
                                <div class="row">
                                    <div class="col-md-3 col-md-offset-1">   
                                        <br>
                                        <fieldset>
                                            <!-- Text input-->

                                            <div class="control-group">
                                                <div class="controls">
                                                    <label class="control-label">Nombre</label>
                                                    <input onkeyup="return(limitar());"  id="nombre" name="nombre" type="text" value="${organismo.getEntidad().getNombre()}" class="input-xlarge">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <div class="controls">
                                                    <label class="control-label">Competencia</label>
                                                    <input onkeyup="return(limitar());"  id="competencia" name="competencia" type="text" value="${organismo.getCompetencia()}" class="input-xlarge">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <div class="controls">
                                                    <label class="control-label">País</label>
                                                    <input onkeyup="return(limitar());"  id="pais" name=pais type="text" value="${organismo.getEntidad().getPais()}" class="input-xlarge">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <div class="controls">
                                                    <label class="control-label">Dirección</label>
                                                    <input onkeyup="return(limitar());"  id="direccion" name="direccion" type="text" value="${organismo.getEntidad().getDireccion()}" class="input-xlarge">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <div class="controls">
                                                    <label class="control-label">Teléfono</label>
                                                    <input onkeyup="return(limitar());"  id="telefono" name="telefono" type="text" value="${organismo.getEntidad().getTelefono()}" class="input-xlarge">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <div class="controls">
                                                    <label class="control-label">Resolución de </label>
                                                    <label>autorización</label>
                                                    <input onkeyup="return(limitar());"  id="resol_aut" name="resol_aut" type="text" value="${organismo.getEntidad().getResolAuto()}" class="input-xlarge">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <div class="controls">
                                                    <label class="control-label">Fecha de emisión de </label>
                                                    <label>resolución</label>
                                                    <input id="fecha_emis_resol" name="fecha_emis_resol" type="text" value="${fechaEmision}" class="datepicker input-xlarge">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <div class="controls">
                                                    <label class="control-label">Resolución de </label>
                                                    <label>renovación</label>
                                                    <input onkeyup="return(limitar());"  id="resol_renov" name="resol_renov" type="text" value="${organismo.getEntidad().getResolRenov()}" class="input-xlarge">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <div class="controls">
                                                    <label class="control-label">Fecha de renovación</label>
                                                    <input id="fecha_renov" name="fecha_renov" type="text" value="${fechaRenov}" class="datepicker input-xlarge">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <div class="controls">
                                                    <label class="control-label">Fecha de vencimiento </label>
                                                    <label>de autorización</label>
                                                    <input id="fecha_venc_aut" name="fecha_venc_aut" type="text" value="${fechaVenc}" class="datepicker input-xlarge">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <div class="controls">
                                                    <label class="control-label">Observaciones</label>
                                                    <textarea onkeyup="return(limitar());"  class="input-xlarge" id="obs" name="obs" placeholder="" rows="5" cols="25">${organismo.getEntidad().getObs()}</textarea>
                                                </div>
                                            </div>
                                            <br>
                                            <br>
                                            <!-- Button -->

                                        </fieldset>

                                    </div>
                                    <div class="col-md-4 col-md-offset-3">
                                        <h3 align="center"><strong>Representante Legal</strong></h3>
                                        <c:if test="${organismo.getIdorganismo() != null}">
                                            <c:forEach var="representante" items="${organismo.getRepresentantes()}" >

                                                <fieldset>
                                                    <!-- Text input-->

                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Nombre</label>
                                                            <input onkeyup="return(limitar2());"  id="nombreR" name="nombreR" type="text" value="${representante.getNombre()}" class="input-xlarge">
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Apellido Paterno</label>
                                                            <input onkeyup="return(limitar2());"  id="apellidoP" name="apellidoP" type="text" value="${representante.getApellidoP()}" class="input-xlarge">
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Apellido Materno</label>
                                                            <input onkeyup="return(limitar2());"  id="apellidoM" name="apellidoM" type="text" value="${representante.getApelldoM()}" class="input-xlarge">
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Usuario</label>
                                                            <input onkeyup="return(limitar2());"  id="user" name="user" type="text" value="${organismo.getEntidad().getUser()}" class="input-xlarge">
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Contraseña</label>
                                                            <input onkeyup="return(limitar2());"  id="pass" name="pass" type="password" class="input-xlarge">
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Fecha de autorización</label>
                                                            <input id="fechaAut" name="fechaAutR" type="text" value="${fechaAutR}" class="datepicker input-xlarge">
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Fecha de renovación</label>
                                                            <input id="fechaRenov" name="fechaRenovR" type="text" value="${fechaRenovR}" class="datepicker input-xlarge">
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Fecha de vencimiento </label>
                                                            <label>de autorización</label>
                                                            <input id="fechaVenc" name="fechaVencR" type="text" value="${fechaVencR}" class="datepicker input-xlarge">
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Correo</label>
                                                            <input onkeyup="return(limitar2());"  id="correo" name="correo" type="text" value="${representante.getCorreo()}" class="input-xlarge">
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Celular</label>
                                                            <input onkeyup="return(limitar2());"  id="celular" name="celular" type="text" value="${representante.getCelular()}" class="input-xlarge">
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Dirección</label>
                                                            <input onkeyup="return(limitar2());"  id="direccionR" name="direccionR" type="text" value="${representante.getDireccion()}" class="input-xlarge">
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="control-group">
                                                        <div class="controls">
                                                            <label class="control-label">Observaciones</label>
                                                            <textarea onkeyup="return(limitar2());"  class="input-xlarge" name="obsR" id="obsR" placeholder="" rows="5" cols="25">${representante.getObs()}</textarea>
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <br>
                                                </fieldset>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${organismo.getIdorganismo() == null}">
                                            <fieldset>
                                                <!-- Text input-->

                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Nombre</label>
                                                        <input  onkeyup="return(limitar2());" id="nombreR" name="nombreR" type="text" value="${representante.getNombre()}" class="input-xlarge">
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Apellido Paterno</label>
                                                        <input onkeyup="return(limitar2());"  id="apellidoP" name="apellidoP" type="text" value="${representante.getApellidoP()}" class="input-xlarge">
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Apellido Materno</label>
                                                        <input onkeyup="return(limitar2());"  id="apellidoM" name="apellidoM" type="text" value="${representante.getApelldoM()}" class="input-xlarge">
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Usuario</label>
                                                        <input  onkeyup="return(limitar2());" id="user" name="user" type="text" value="${representante.getUser()}" class="input-xlarge">
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Contraseña</label>
                                                        <input onkeyup="return(limitar2());"  id="pass" name="pass" type="password" class="input-xlarge">
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Fecha de autorización</label>
                                                        <input id="fechaAut" name="fechaAutR" type="text" value="${fechaAutR}" class="datepicker input-xlarge">
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Fecha de renovación</label>
                                                        <input id="fechaRenov" name="fechaRenovR" type="text" value="${fechaRenovR}" class="datepicker input-xlarge">
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Fecha de vencimiento </label>
                                                        <label>de autorización</label>
                                                        <input id="fechaVenc" name="fechaVencR" type="text" value="${fechaVencR}" class="datepicker input-xlarge">
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Correo</label>
                                                        <input onkeyup="return(limitar2());"  id="correo" name="correo" type="text" value="${representante.getCorreo()}" class="input-xlarge">
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Celular</label>
                                                        <input onkeyup="return(limitar2());"  id="celular" name="celular" type="text" value="${representante.getCelular()}" class="input-xlarge">
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Dirección</label>
                                                        <input  onkeyup="return(limitar2());" id="direccionR" name="direccionR" type="text" value="${representante.getDireccion()}" class="input-xlarge">
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="control-group">
                                                    <div class="controls">
                                                        <label class="control-label">Observaciones</label>
                                                        <textarea  onkeyup="return(limitar2());" class="input-xlarge" name="obsR" id="obsR" placeholder="" rows="5" cols="25">${representante.getObs()}</textarea>
                                                    </div>
                                                </div>
                                                <br>
                                                <br>
                                            </fieldset>

                                        </c:if>
                                    </div>
                                </div> 
                                <div class="control-group">
                                    <div class="controls">
                                        <p align="center"><button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar</button></p>
                                    </div>
                                </div>
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
                function limitar()
                {                    
                    var nombre = document.getElementById('nombre');
                    var competencia = document.getElementById('competencia');
                    var pais = document.getElementById('pais');
                    var direccion = document.getElementById('direccion');
                    var telefono = document.getElementById('telefono');
                    var resol_aut = document.getElementById('resol_aut');
                    var resol_renov = document.getElementById('resol_renov');
                    var obs = document.getElementById('obs');

                    if (nombre.value.length < 0 || nombre.value.length > 39)
                    {
                        alert("solo puede ingresar 40 caracteres");
                        nombre.value = nombre.value.substring(0, 40);
                        document.formulario.nombre.focus();
                        return false;
                    } else if (competencia.value.length < 0 || competencia.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        competencia.value = competencia.value.substring(0, 30);
                        document.formulario.competencia.focus();
                        return false;
                    }else if (pais.value.length < 0 || pais.value.length > 14) {
                        alert("solo puede ingresar 15 caracteres");
                        pais.value = pais.value.substring(0, 15);
                        document.formulario.pais.focus();
                        return false;
                    }else if (direccion.value.length < 0 || direccion.value.length > 199) {
                        alert("solo puede ingresar 200 caracteres");
                        direccion.value = direccion.value.substring(0, 200);
                        document.formulario.direccion.focus();
                        return false;
                    }else if (telefono.value.length < 0 || telefono.value.length > 14) {
                        alert("solo puede ingresar 15 caracteres");
                        telefono.value = telefono.value.substring(0, 15);
                        document.formulario.telefono.focus();
                        return false;
                    }else if (resol_aut.value.length < 0 || resol_aut.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        resol_aut.value = resol_aut.value.substring(0, 30);
                        document.formulario.resol_aut.focus();
                        return false;
                    }else if (resol_renov.value.length < 0 || resol_renov.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        resol_renov.value = resol_renov.value.substring(0, 30);
                        document.formulario.resol_renov.focus();
                        return false;
                    }else if (obs.value.length < 0 || obs.value.length > 199) {
                        alert("solo puede ingresar 200 caracteres");
                        obs.value = obs.value.substring(0, 200);
                        document.formulario.obs.focus();
                        return false;
                    }

                }
            </script>
            <script type="text/javascript">
                function limitar2()
                {                    
                    var nombreR = document.getElementById('nombreR');
                    var apellidoP = document.getElementById('apellidoP');
                    var apellidoM = document.getElementById('apellidoM');
                    var user = document.getElementById('user');
                    var pass = document.getElementById('pass');
                    var correo = document.getElementById('correo');
                    var celular = document.getElementById('celular');
                    var direccionR = document.getElementById('direccionR');
                    var obsR = document.getElementById('obsR');

                    if (nombreR.value.length < 0 || nombreR.value.length > 29)
                    {
                        alert("solo puede ingresar 30 caracteres");
                        nombreR.value = nombreR.value.substring(0, 30);
                        document.formulario.nombreR.focus();
                        return false;
                    } else if (apellidoP.value.length < 0 || apellidoP.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        apellidoP.value = apellidoP.value.substring(0, 30);
                        document.formulario.apellidoP.focus();
                        return false;
                    }else if (apellidoM.value.length < 0 || apellidoM.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        apellidoM.value = apellidoM.value.substring(0, 30);
                        document.formulario.apellidoM.focus();
                        return false;
                    }else if (user.value.length < 0 || user.value.length > 44) {
                        alert("solo puede ingresar 45 caracteres");
                        user.value = user.value.substring(0, 45);
                        document.formulario.user.focus();
                        return false;
                    }else if (pass.value.length < 0 || pass.value.length > 511) {
                        alert("solo puede ingresar 512 caracteres");
                        pass.value = pass.value.substring(0, 512);
                        document.formulario.pass.focus();
                        return false;
                    }else if (correo.value.length < 0 || correo.value.length > 39) {
                        alert("solo puede ingresar 40 caracteres");
                        correo.value = correo.value.substring(0, 40);
                        document.formulario.correo.focus();
                        return false;
                    }else if (celular.value.length < 0 || celular.value.length > 14) {
                        alert("solo puede ingresar 15 caracteres");
                        celular.value = celular.value.substring(0, 15);
                        document.formulario.celular.focus();
                        return false;
                    }else if (direccionR.value.length < 0 || direccionR.value.length > 199) {
                        alert("solo puede ingresar 200 caracteres");
                        direccionR.value = direccionR.value.substring(0, 200);
                        document.formulario.direccionR.focus();
                        return false;
                    }else if (obsR.value.length < 0 || obsR.value.length > 199) {
                        alert("solo puede ingresar 200 caracteres");
                        obsR.value = obsR.value.substring(0, 200);
                        document.formulario.obsR.focus();
                        return false;
                    }

                }
            </script>
            <!-- Ubicar al final -->
    </body>
</html>