<%-- 
    Document   : lista_sesion
    Created on : 28/10/2013, 03:38:34 PM
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
                            <span class="icon-bar"></span>
                        </button>

                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-left">
                            <li class="active"><a href="#">Inicio</a></li>
                            <li><a href="#">Actualizar Información</a></li>
                            <li><a href="#">Salir</a></li>
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
                            <li><a href="${pageContext.servletContext.contextPath}/inf"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Sesiones/talleres</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNAs</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/juzgado"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Juzgado</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ua"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UAs</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/famint"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de Registros</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/usuarios"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Organismo Acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Autoridad Central</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>    

                        </ul>
                    </div>

                    <div class="col-md-6 col-md-offset-1">
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1>Sesión ${sesion.getNSesion()}</h1>
                        <br>
                        <c:choose>
                            <c:when test="${ sesion != null && sesion.getHabilitado() == true}">
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
                                <input id="textinput" value= "${fecha}" name="textinput" type="text" placeholder="fecha" class="datepicker input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Hora de inicio:</label>
                            <div class="controls">
                                <input id="textinput" value="${hora}" name="textinput" type="text" placeholder="hora" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Duración:</label>
                            <div class="controls">
                                <input id="textinput" value="${sesion.getDuracion()}" name="textinput" type="text" placeholder="duracion" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Dirección/Lugar de Sesión Informativa:</label>
                            <div class="controls">
                                <input id="textinput" value="${sesion.getDireccion()}" name="textinput" type="text" placeholder="direccion" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Facilitador:</label>
                            <select id="capacitador" name="capacitador">
                                <c:forEach var="personal" items="${listaPersonal}" varStatus="status">
                                    <option value="${personal.getIdpersonal()}" ${sesion.getFacilitador() == personal.getIdpersonal() ? 'selected' : ''}> ${personal.getApellidoP()} ${personal.getApellidoM()} ${personal.getNombre()}</option> 
                                </c:forEach>
                            </select>
                        </div>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>Inicio Inscripción</th>
                                        <th>Fin Inscripción</th>
                                        <th>Vacantes</th>
                                        <th>Modificar</th>
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
                                                <form action="${pageContext.servletContext.contextPath}/" method="post">
                                                    <input hidden name="idSesion" id="idSesion" value="${turno.getIdturno()}">
                                                    <button type="submit" class="btn btn-default">Modificar</button>
                                                </form>    
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <br>
                        <br>
                        <button href="#" class="btn btn-default">Agregar nuevo turno</button>
                        <button href="#" ${sesion != null && sesion.getHabilitado() == true ? 'disabled' : ''} class="btn btn-default">Habilitar sesión</button>
                        <br>
                        <br>
                        <button href="#" class="btn btn-default">Guardar Cambios</button>
                        <br>
                        <br>
                        <p>IMPORTANTE: Una vez iniciado el Turno 1 de Inscripción, no es posible Deshabilitar la sesión debido a los inscritos</p>
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
            <!-- Bootstrap core JavaScript
       ================================================== -->
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
            <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
            <script type="text/javascript">

                $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

            </script>
            <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>
