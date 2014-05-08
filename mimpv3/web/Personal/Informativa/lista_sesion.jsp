<%-- 
    Document   : lista_sesion
    Created on : 28/10/2013, 03:38:34 PM
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
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inf'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <c:choose>
                            <c:when test="${ sesion == null }">
                                <form action="${pageContext.servletContext.contextPath}/PersonalCrearSesion" method="post">
                                </c:when>
                                <c:otherwise>
                                    <form action="${pageContext.servletContext.contextPath}/PersonalUpdateSesion" method="post">
                                        <input ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">  
                                    </c:otherwise>
                                </c:choose>
                                <div class="control-group">
                                    <label class="control-label" for="textinput"><h1>Sesión Informativa</h1></label>
                                    <div class="controls">
                                        <input ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} id="numSesion" name="numSesion" value= "${sesion.getNSesion()}" type="text" placeholder="número de sesión" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <c:choose>
                                    <c:when test="${ sesion != null && sesion.getHabilitado() == 0}">
                                        <p>Estado: Habilitado</p>
                                    </c:when>
                                    <c:otherwise>
                                        <p>Estado: Deshabilitado</p>
                                    </c:otherwise>
                                </c:choose>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Fecha de Sesión Informativa:</label>
                                    <div class="controls">
                                        <input ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} id="fecha" value= "${fecha}" name="fecha" type="text" placeholder="fecha" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Hora de inicio:</label>
                                    <div class="controls">
                                        <input ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} id="hora" value="${hora}" name="hora" type="text" placeholder="hora" class="timepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Duración:</label>
                                    <div class="controls">
                                        <input ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} id="duracion" value="${sesion.getDuracion()}" name="duracion" type="text" placeholder="duracion" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="textinput">UA</label>
                                    <div class="controls">
                                        <input disabled placeholder="${sesion.getUnidad() != null ? sesion.getUnidad() : usuario.getUnidad().getDepartamento()}" class="input-xlarge">
                                        <input hidden value="${sesion.getUnidad() != null ? sesion.getUnidad() : usuario.getUnidad().getDepartamento()}" id="ua" name="ua" >
                                    </div>
                                </div>
                                <br>        
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Dirección/Lugar de Sesión Informativa:</label>
                                    <div class="controls">
                                        <input ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} id="direccion" value="${sesion.getDireccion()}" name="direccion" type="text" placeholder="direccion" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Facilitador:</label>
                                    <div>
                                        <textarea ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} id="capacitador" name="capacitador" cols="20" rows="5">${sesion.getFacilitador()}</textarea>
                                    </div>
                                </div>
                                <br>
                                <button ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} type="submit" class="btn btn-default">Guardar Cambios</button>
                            </form>
                            <br>
                            <h3><strong>${mensaje}</strong></h3>
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Inicio Inscripción</th>
                                            <th>Fin Inscripción</th>
                                            <th>Vacantes</th>
                                            <th>Modificar</th>
                                            <th>Eliminar</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="turno" items="${listaTurnos}" varStatus="status">
                                            <tr>
                                                <td>Turno ${status.index + 1} </td>
                                                <td>${ts.DateToString(turno.getInicioInscripcion())} ${ts.HourToString(turno.getInicioInscripcion())}</td>
                                                <td>${ts.DateToString(turno.getFinInscripcion())} ${ts.HourToString(turno.getFinInscripcion())}</td>
                                                <td>${turno.getVacantes()}</td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/PersonalEditarTurno2" method="post">
                                                        <input hidden name="idTurno" id="idTurno" value="${turno.getIdturno()}">
                                                        <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                                        <input hidden name="index" id="index" value="${status.index + 1}">
                                                        <button ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} type="submit" class="btn btn-default">Modificar</button>
                                                    </form>    
                                                </td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/PersonalEliminarTurno" method="post">
                                                        <input hidden name="idTurno" id="idTurno" value="${turno.getIdturno()}">
                                                        <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                                        <button ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} type="submit" class="btn btn-default">Eliminar</button>
                                                    </form>    
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <br>
                            <br>
                            <c:choose>
                                <c:when test="${sesion != null && listaTurnos != null }">
                                    <form action="${pageContext.servletContext.contextPath}/PersonalEditarTurno" method="post">
                                        <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                        <input hidden name="index" id="index" value="${listaTurnos.size() + 1}">
                                        <button ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} type="submit" class="btn btn-default">Agregar nuevo turno</button>
                                    </form>    
                                </c:when>           
                                <c:when test="${sesion != null && listaTurnos == null }">
                                    <form action="${pageContext.servletContext.contextPath}/PersonalEditarTurno" method="post">
                                        <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                        <input hidden name="index" id="index" value="1">
                                        <button ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} type="submit" class="btn btn-default">Agregar nuevo turno</button>
                                    </form>   
                                </c:when>
                                <c:otherwise>
                                    <h3><strong>Debe crear una Sesión Informativa para luego insertar los turnos</strong></h3>
                                </c:otherwise>
                            </c:choose>
                            <br>        
                            <br>
                            <c:choose>
                                <c:when test="${sesion != null && sesion.getHabilitado() == 1}">
                                    <form action="${pageContext.servletContext.contextPath}/PersonalHabilitarSesion" method="post">
                                        <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                        <button type="submit" ${sesion != null && sesion.getHabilitado() == 0 ? 'disabled' : ''} class="btn btn-default">Habilitar sesión</button>
                                    </form>
                                </c:when>
                                <c:when test="${sesion != null && sesion.getHabilitado() == 0}">
                                    <form action="${pageContext.servletContext.contextPath}/PersonalDeshabilitarSesion" method="post">
                                        <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                        <button type="submit" ${sesion != null && sesion.getHabilitado() == 1 ? 'disabled' : ''} class="btn btn-default">Deshabilitar sesión</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <h3><strong>Luego de crear la sesión informativa podrá habilitarla</strong></h3>
                                </c:otherwise>
                            </c:choose>
                            <br>
                            <br>
                            <br>
                            <br>
                            <p>IMPORTANTE: Una vez iniciado el Turno 1 de Inscripción, no es recomendable Deshabilitar la sesión debido a los inscritos</p>
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
            <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery.timepicker.js"></script>
            <script type="text/javascript">

                            $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});
                            $('.timepicker').timepicker({'timeFormat': 'H:i'});
            </script>
            <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>
