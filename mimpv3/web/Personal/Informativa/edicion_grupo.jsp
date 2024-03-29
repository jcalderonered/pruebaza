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
                        <form action="${pageContext.servletContext.contextPath}/PersonalEditarTaller" method="post">
                            <input hidden name="idTaller" id="idTaller" value="${idTaller}">
                            <input hidden name="idGrupo" id="idGrupo" value="${idGrupo}">
                            <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        </form>
                        <c:choose>
                            <c:when test="${ grupo == null }">
                                <form name="formulario"  action="${pageContext.servletContext.contextPath}/PersonalCrearGrupo" method="post">
                                <input hidden name="idTaller" id="idTaller" value="${idTaller}">      
                            </c:when>
                            <c:otherwise>
                                    <form name="formulario"  action="${pageContext.servletContext.contextPath}/PersonalUpdateGrupo" method="post">
                                    <input hidden name="idGrupo" id="idGrupo" value="${grupo.getIdgrupo()}"> 
                                    <input hidden name="idTaller" id="idTaller" value="${idTaller}">
                            </c:otherwise>
                        </c:choose>
                        <h1>Edición de grupo</h1>
                        <br>
                        <!-- Select Basic -->
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Nombre del grupo:</label>
                            <div class="controls">
                                <input onkeyup="return(limitar());" id="nombreGrupo" name="nombreGrupo" value="${grupo.getNombre()}" type="text" placeholder="Nombre" class="input-xlarge">
                            </div>
                        </div>
                        <br>    
                        <button id="singlebutton" name="singlebutton" class="btn btn-primary">Guardar cambios</button>
                        </form>
                        <br>
                        <h1>Listado de Turnos</h1>
                        <br>
                        <h3><strong>${mensaje}</strong></h3>
                        <br>
                        <table class="table table-striped table-bordered table-hover">
                            <thead>
                                <tr>
                                    <th>Turno</th>
                                    <th>N° de Reuniones</th>
                                    <th>Edición</th>
                                    <th>Eliminar</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:if test="${grupo != null}">  
                                <c:forEach var="turno" items="${grupo.getTurno2s()}" varStatus="status">
                                  <tr>
                                  <td>${turno.getNombre()}  </td>
                                  <td>${turno.getReunions().size()}  </td>
                                  <td>
                                     <form action="${pageContext.servletContext.contextPath}/PersonalEditarTurnoGrupo" method="post">
                                            <input hidden name="idTurno2" id="idTurno2" value="${turno.getIdturno2()}">
                                            <input hidden name="idGrupo" id="idGrupo" value="${grupo.getIdgrupo()}">
                                            <input hidden name="idTaller" id="idTaller" value="${idTaller}">
                                            <button type="submit" class="btn btn-default">Modificar</button>
                                     </form>
                                  </td>
                                  <td>
                                     <form action="${pageContext.servletContext.contextPath}/PersonalEliminarTurnoGrupo" method="post">
                                            <input hidden name="idTurno2" id="idTurno2" value="${turno.getIdturno2()}">
                                            <input hidden name="idGrupo" id="idGrupo" value="${grupo.getIdgrupo()}">
                                            <input hidden name="idTaller" id="idTaller" value="${idTaller}">
                                            <button type="submit" class="btn btn-default">Eliminar</button>
                                     </form>
                                  </td>
                                  </tr>   
                                  </c:forEach>
                                </c:if>  
                            </tbody>
                        </table>
                        <c:if test="${grupo == null}"> 
                            <h3><strong>Debe crear un Grupo antes de poder agregar los Turnos de Reuniones</strong></h3>    
                        </c:if>   
                        <br>
                        <c:if test="${grupo != null}">  
                         <form action="${pageContext.servletContext.contextPath}/PersonalAgregarTurnoGrupo" method="post">
                                <input hidden name="idGrupo" id="idGrupo" value="${grupo.getIdgrupo()}">
                                <input hidden name="idTaller" id="idTaller" value="${idTaller}">
                                <button id="singlebutton" name="singlebutton" class="btn btn-primary">Agregar Turno</button>
                         </form>                
                        </c:if>
                        
                        <br>
                        <br>
                        
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
                    var nombreGrupo = document.getElementById('nombreGrupo');

                    if (nombreGrupo.value.length < 0 || nombreGrupo.value.length > 24)
                    {
                        alert("solo puede ingresar 25 caracteres");
                        nombreGrupo.value = nombreGrupo.value.substring(0, 24);
                        document.formulario.nombreGrupo.focus();
                        return false;
                    } 
                }
            </script>
            <!-- Ubicar al final -->
    </body>
</html>