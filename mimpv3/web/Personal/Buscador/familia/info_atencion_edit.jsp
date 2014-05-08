<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
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
                    <div class="col-md-8">
                        <form action="${pageContext.servletContext.contextPath}/atenciones2?volver=/FiltrarFam&expediente=${expediente2}&HT=${HT}&nacionalidad=${nacionalidad}&estado=${estado2}&tipofamilia=${tipofamilia}" method="post">
                            <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        </form>
                        <c:if test="${estado != 'formativa'}">
                            <br>
                            <h1 align="center"><strong>Familia "${expediente.getExpediente()}"</strong></h1>
                            <br>
                        </c:if>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row">
                            <li><a href="${pageContext.servletContext.contextPath}/laSolicitante2?volver=${volver}&expediente=${expediente2}&HT=${HT}&nacionalidad=${nacionalidad}&estado=${estado2}&tipofamilia=${tipofamilia}">La Solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/elSolicitante2?volver=${volver}&expediente=${expediente2}&HT=${HT}&nacionalidad=${nacionalidad}&estado=${estado2}&tipofamilia=${tipofamilia}" >El solicitante</a></li>
                            <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/compFamiliar" >Composición familiar</a></li>-->
                            <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/vivienda" >Vivienda</a></li>-->
                            <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/infoExpediente2?volver=${volver}&expediente=${expediente2}&HT=${HT}&nacionalidad=${nacionalidad}&estado=${estado2}&tipofamilia=${tipofamilia}" >Información del Expediente</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/procesoAdopcion2?volver=${volver}&expediente=${expediente2}&HT=${HT}&nacionalidad=${nacionalidad}&estado=${estado2}&tipofamilia=${tipofamilia}" >Proceso de adopción</a></li>
                            <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/antNna2?volver=${volver}&expediente=${expediente2}&HT=${HT}&nacionalidad=${nacionalidad}&estado=${estado2}&tipofamilia=${tipofamilia}" >Antecedentes del NNA</a></li>
                            <li ${estado == 'formativa' || estado == 'evaluacion' || estado == 'espera' || estado == 'designacion' || estado == 'adopcion' || estado == 'reevaluacion' ? 'class="hidden"' : ''} ><a href="${pageContext.servletContext.contextPath}/nnaAsociado2?volver=${volver}&expediente=${expediente2}&HT=${HT}&nacionalidad=${nacionalidad}&estado=${estado2}&tipofamilia=${tipofamilia}" >NNA Adoptado</a></li>
                                <% if (!u.getRol().equals("DEIA")) {%>
                            <li  class="active"><a href="${pageContext.servletContext.contextPath}/atenciones2?volver=${volver}&expediente=${expediente2}&HT=${HT}&nacionalidad=${nacionalidad}&estado=${estado2}&tipofamilia=${tipofamilia}" >Atenciones</a></li>
                                <%}%>
                                <% if (u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/EditUserPass2?volver=${volver}&expediente=${expediente2}&HT=${HT}&nacionalidad=${nacionalidad}&estado=${estado2}&tipofamilia=${tipofamilia}" >Editar Perfil de Familia</a></li>
                                <%}%>
                        </ul>
                        <br>
                        <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                        <c:if test="${atencion == null}">
                            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/crearAtencion2" method="post"> 
                            </c:if>  
                            <c:if test="${atencion != null}">
                                <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/updateAtencion2" method="post"> 
                                    <input hidden name="idAtencion" id="idAtencion" value="${atencion.getIdatencion()}">
                                </c:if>  
                                <fieldset>
                                    <br>
                                    <h3><strong>Crear/Editar atención</strong></h3>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="selectbasic">Personal contactado</label>
                                        <div class="controls">
                                            <select id="personal" name="personal" class="input-xlarge">
                                                <c:forEach var="personal" items="${listaPersonal}" > 
                                                    <option value="${personal.getIdpersonal()}" ${atencion.getPersonal().getIdpersonal() == personal.getIdpersonal() ? 'selected' : ''}>${personal.getNombre()} ${personal.getApellidoP()} ${personal.getApellidoM()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <br> 
                                    <div class="control-group">
                                        <label class="control-label">Fecha</label>
                                        <div class="controls">
                                            <input id="fecha" name="fecha" value="${atencion.getFecha() != null ? df.dateToString(atencion.getFecha()): ''}" type="text" class="datepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Hora</label>
                                        <div class="controls">
                                            <input id="hora" name="hora" value="${atencion.getHora()}" type="text" class="timepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="selectbasic">Tipo</label>
                                        <div class="controls">
                                            <select id="tipo" name="tipo" class="input-xlarge">
                                                <option value="presencial" ${atencion.getTipoAtencion() != 'presencial' ? 'selected' : ''}>Presencial</option>
                                                <option value="correo" ${atencion.getTipoAtencion() != 'correo' ? 'selected' : ''}>Correo</option>
                                                <option value="telefono" ${atencion.getTipoAtencion() != 'telefono' ? 'selected' : ''}>Teléfono</option>
                                            </select>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Detalles de la comunicación</label>
                                        <div class="controls">
                                            <textarea id="detalle" class="input-xlarge" name="detalle" cols="25" rows="5" >${atencion.getDetalle()}</textarea>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Observaciones</label>
                                        <div class="controls">
                                            <textarea id="obs" class="input-xlarge" name="obs" cols="25" rows="5" >${atencion.getObservacion()}</textarea>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <div class="controls">
                                            <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
                                        </div>
                                    </div>
                                </fieldset>
                            </form>
                            <!--FIN DE CONTENIDO-->
                    </div>
                </div>
            </div>
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
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery.timepicker.js"></script>
            <script type="text/javascript">

                $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});
                $('.timepicker').timepicker({'timeFormat': 'H:i'});

            </script>
            <!-- Ubicar al final -->
    </body>
</html>

