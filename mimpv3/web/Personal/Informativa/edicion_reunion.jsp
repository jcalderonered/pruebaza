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
        <link href="${pageContext.servletContext.contextPath}/assets/css/jquery.timepicker.css" rel="stylesheet">
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
                            <%if (!u.getRol().equals("DEIA Prio") && !u.getRol().equals("UA")) {%>
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
                        <form action="${pageContext.servletContext.contextPath}/PersonalEditarTurnoGrupo" method="post">
                            <input hidden name="idTaller" id="idTaller" value="${idTaller}">  
                            <input hidden name="idGrupo" id="idGrupo" value="${idGrupo}"> 
                            <input hidden name="idTurno2" id="idGrupo" value="${idTurno2}"> 
                            <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        </form>
                        <c:choose>
                            <c:when test="${ reunion == null }">
                                <form action="${pageContext.servletContext.contextPath}/PersonalCrearReunion" method="post" name="formulario" onsubmit="return(validar());">
                                    <input hidden name="idTurno2" id="idTurno2" value="${turno2.getIdturno2()}">  
                                   
                                </c:when>
                                <c:otherwise>
                                    <form action="${pageContext.servletContext.contextPath}/PersonalUpdateReunion" method="post" name="formulario" onsubmit="return(validar());">
                                        <input hidden name="idReunion" id="idReunion" value="${reunion.getIdreunion()}">
                                        <input hidden name="idTurno2" id="idTurno2" value="${idTurno2}">
                                 
                                    </c:otherwise>
                                </c:choose>
                                <h1>Agregar / Editar Reunión</h1>
                                <br>


                                <fieldset>

                                    <!-- Form Name -->
                                    <legend>Datos de Reunión</legend>

                                    <!-- Select Basic -->
                                    <div class="control-group">
                                        <label class="control-label" for="selectbasic">Asociado a grupo: ${turno2.getGrupo().getNombre()} - Turno ${turno2.getNombre()}</label>
                                    </div>

                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Fecha y Hora</label>
                                        <div class="controls">
                                            <input id="fecha" name="fecha" value="${fecha}" type="text"  class="datepicker"> &nbsp;<input id="hora" name="hora" type="text" value="${reunion.getHora()}" class="timepicker input-xlarge"> 
                                        </div>
                                    </div>

                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Duracion</label>
                                        <div class="controls">
                                            <input id="duracion" name="duracion" value="${reunion.getDuracion()}" type="text" class="input-xlarge">                                        
                                        </div>
                                    </div>

                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Direccion</label>
                                        <div class="controls">
                                            <input id="direccion" name="direccion" value="${reunion.getDireccion()}" type="text" value="Instalaciones del MIMP" class="input-xlarge">                                        
                                        </div>
                                    </div>       

                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Capacidad Maxima</label>
                                        <div class="controls">
                                            <input id="capacidad" name="capacidad" value="${reunion.getCapacidad()}" type="text" value="25" class="input-xlarge">                                        
                                        </div>
                                    </div>

                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Facilitador</label>
                                        <div class="controls">
                                            <textarea id="facilitador" name="facilitador" cols="20" rows="5">${reunion.getFacilitador()}</textarea>
                                        </div>
                                    </div>


                                    <!-- Button -->
                                    <br>
                                    <input hidden name="idTaller" id="idTaller" value="${idTaller}">  
                                    <input hidden name="idGrupo" id="idGrupo" value="${idGrupo}"> 
                                    
                                    <button id="singlebutton" name="singlebutton" class="btn btn-primary">Guardar</button>
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

            <!-- Bootstrap core JavaScript
        ================================================== -->
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery.timepicker.js"></script>
            <script type="text/javascript">

                                        $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});
                                        $('.timepicker').timepicker({'timeFormat': 'H:i'});
            </script>
            <script type="text/javascript">
                function validar()
                {
                    var numericExpression = /^[0-9]+$/;
                    if (!document.formulario.capacidad.value.match(numericExpression))
                    {
                        alert("El campo debe contener solo números");
                        document.formulario.capacidad.focus();
                        return false;
                    }
                    if (document.formulario.capacidad.value == "")
                    {
                        alert("Debe ingresar la capacidad");
                        document.formulario.capacidad.focus();
                        return false;
                    }
                    if (document.formulario.fecha.value == "")
                    {
                        alert("Debe ingresar una fecha");
                        document.formulario.fecha.focus();
                        return false;
                    }
                    if (document.formulario.hora.value == "")
                    {
                        alert("Debe ingresar una hora");
                        document.formulario.hora.focus();
                        return false;
                    }
                    if (document.formulario.duracion.value == "")
                    {
                        alert("Debe ingresar la duración");
                        document.formulario.duracion.focus();
                        return false;
                    }
                    if (document.formulario.direccion.value == "")
                    {
                        alert("Debe ingresar la dirección");
                        document.formulario.direccion.focus();
                        return false;
                    }
                    if (document.formulario.capacidad.value == "")
                    {
                        alert("Debe ingresar la capacidad");
                        document.formulario.capacidad.focus();
                        return false;
                    }
                    return true;
                }
            </script>
            <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>