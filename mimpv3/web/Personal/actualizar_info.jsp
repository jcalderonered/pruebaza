<%-- 
    Document   : inicio_personal
    Created on : 28/10/2013, 09:54:29 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Personal"%>
<%
response.setHeader( "Pragma", "no-cache" );
response.addHeader( "Cache-Control", "must-revalidate" );
response.addHeader( "Cache-Control", "no-cache" );
response.addHeader( "Cache-Control", "no-store" );
response.setDateHeader("Expires", 0);
Personal u=(Personal)request.getSession().getAttribute("usuario");
if (u==null){
%>
<jsp:forward page="/salir"/>
<% } %>
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
                                <%if (u.getRol().equals("DCRI") || u.getRol().equals("DGA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/inf"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Sesiones/talleres</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNA</a></li>
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
                                if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Organismo Acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Autoridad Central</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>    
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
                                                <input id="full-name" value="<%=u.getCorreoTrabajo()%>" name="correo_trabajo" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Correo Personal</label>
                                            <div class="controls">
                                              <input id="full-name" value="<%=u.getCorreoPersonal()%>" name="correo_personal" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Profesión</label>
                                            <div class="controls">
                                              <input id="full-name" value="<%=u.getProfesion()%>" name="profesion" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                         <label class="control-label">Grado de instrucción</label>
                                            <div class="controls">
                                              <input id="full-name" value="<%=u.getGradoInstruccion()%>" name="grado_instruccion" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                         <label class="control-label">Cargo</label>
                                            <div class="controls">
                                              <input id="full-name" value="<%=u.getCargo()%>" name="cargo" type="text" class="input-xlarge">
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
                                              <input value="<%=u.getRegimen()%>" id="full-name" name="regimen" type="text" class="input-xlarge">
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
                                                <input id="full-name" value="<%=u.getDomicilio()%>" name="domicilio" type="text" class="input-xlarge">
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
                    <p align="right">Diseñado por RED<br>www.red.net.pe</p>
                </div>
            </div>
    </body>
</html>