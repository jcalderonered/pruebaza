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
                                    if (!u.getRol().equals("DAPA") && !u.getRol().equals("MATCH") && !u.getRol().equals("UA")) {%>
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
                    <div class="col-md-8">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}${volver}'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <c:if test="${estado != 'formativa'}">
                            <br>
                            <h1 align="center"><strong>Familia "${expediente.getExpediente()}"</strong></h1>
                            <br>
                        </c:if>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row">
                            <li><a href="${pageContext.servletContext.contextPath}/laSolicitante?volver=${volver}">La Solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/elSolicitante?volver=${volver}" >El solicitante</a></li>
                            <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/compFamiliar" >Composición familiar</a></li>-->
                            <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/vivienda" >Vivienda</a></li>-->
                            <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/infoExpediente?volver=${volver}" >Información del Expediente</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/procesoAdopcion?volver=${volver}" >Historial de la Familia</a></li>
                            <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/antNna?volver=${volver}" >Antecedentes del NNA</a></li>
                            <li ${estado == 'formativa' || estado == 'evaluacion' || estado == 'espera' || estado == 'designacion' || estado == 'adopcion' || estado == 'reevaluacion' ? 'class="hidden"' : ''} ><a href="${pageContext.servletContext.contextPath}/nnaAsociado?volver=${volver}" >NNA Adoptado</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/atenciones?volver=${volver}" >Atenciones</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/EditUserPass?volver=${volver}" >Editar Perfil de Familia</a></li>
                        </ul>
                        <br>
                        <fieldset>
                            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->

                            <br>
                            <h3><strong>Sesiones asistidas</strong></h3>
                            <br>
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>Número de sesión</th>
                                            <th>Fecha</th>
                                            <th>Detalles de sesion</th>
                                        </tr>
                                    </thead>
                                    <c:if test="${!listaSesiones.isEmpty()}">
                                        <tbody>
                                            <c:forEach var="sesion" items="${listaSesiones}" varStatus="status"> 
                                                <tr>
                                                    <td>${sesion.getNSesion()}</td>
                                                    <td>${sesion.getFecha() != null ? df.dateToString(sesion.getFecha()) : ''}</td>
                                                    <td>
                                                        <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/DetalleSesion" method="post">
                                                            <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}" >
                                                            <button class="btn btn-default">Detalles</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </c:if>   
                                    <c:if test="${listaSesiones.isEmpty()}">
                                        <h3><strong>No existen sesiones relacionadas</strong></h3>
                                    </c:if>
                                </table>
                            </div>
                            <br>
                            <h3><strong>Talleres asistidos</strong></h3>
                            <br>
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th>Tipo taller</th>
                                            <th>Nombre</th>
                                            <th>Grupo</th>
                                            <th>Turno asistido</th>
                                            <th>Detalles</th>
                                        </tr>
                                    </thead>
                                    <c:if test="${!listaAsistenciaReuniones.isEmpty()}">
                                        <c:set var="grupo" value="0" />
                                        <c:set var="turno" value="0" />
                                        <tbody>
                                            <c:forEach var="reunion" items="${listaAsistenciaReuniones}" varStatus="status">
                                                <c:if test="${grupo == reunion.getReunion().getTurno2().getGrupo().getIdgrupo() && turno == reunion.getReunion().getTurno2().getIdturno2()}">

                                                </c:if>
                                                <c:if test="${grupo != reunion.getReunion().getTurno2().getGrupo().getIdgrupo() || turno != reunion.getReunion().getTurno2().getIdturno2()}">
                                                    <tr>
                                                        <td>${reunion.getReunion().getTurno2().getGrupo().getTaller().getTipoTaller()}</td>
                                                        <td>${reunion.getReunion().getTurno2().getGrupo().getTaller().getNombre()}</td>
                                                        <td>${reunion.getReunion().getTurno2().getGrupo().getNombre()}</td>
                                                        <td>${reunion.getReunion().getTurno2().getNombre()}</td>
                                                        <td>
                                                            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/DetalleTaller" method="post">
                                                                <input hidden name="tipoTaller" id="tipoTaller" value="${reunion.getReunion().getTurno2().getGrupo().getTaller().getTipoTaller()}" >    
                                                                <input hidden name="nombreTaller" id="nombreTaller" value="${reunion.getReunion().getTurno2().getGrupo().getTaller().getNombre()}" > 
                                                                <input hidden name="nombreGrupo" id="nombreGrupo" value="${reunion.getReunion().getTurno2().getGrupo().getNombre()}" > 
                                                                <input hidden name="nombreTurno" id="nombreTurno" value="${reunion.getReunion().getTurno2().getNombre()}" >
                                                                <input hidden name="idTurno" id="idTurno" value="${reunion.getReunion().getTurno2().getIdturno2()}" >
                                                                <button class="btn btn-default">Detalles</button>
                                                            </form>
                                                        </td>
                                                        <c:set var="grupo" value="${reunion.getReunion().getTurno2().getGrupo().getIdgrupo()}" />
                                                        <c:set var="turno" value="${reunion.getReunion().getTurno2().getIdturno2()}" />
                                                    </tr>
                                                </c:if>
                                            </c:forEach> 
                                        </tbody>
                                    </c:if>   
                                    <c:if test="${listaAsistenciaReuniones.isEmpty()}">
                                        <h3><strong>No existen reuniones relacionadas</strong></h3>
                                    </c:if>
                                </table>
                            </div>
                            <br>
                            <c:if test="${estado != 'formativa'}">
                                <h3><strong>Evaluaciones asociadas</strong></h3>
                                <br>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Tipo Evaluacion</th>
                                                <th>Número</th>
                                                <th>Fecha Asignacion</th>
                                                <th>Resultado</th>
                                                <th>Detalles</th>
                                            </tr>
                                        </thead>
                                        <c:if test="${!listaEvaluaciones.isEmpty()}">
                                            <tbody>
                                                <c:forEach var="evaluacion" items="${listaEvaluaciones}" varStatus="status">
                                                    <c:if test="${evaluacion.getTipo() != 'aceptacion'}">
                                                        <tr>
                                                            <td>${evaluacion.getTipo() == 'informe' ? 'integracion familiar' : evaluacion.getTipo()}</td>
                                                            <td>${evaluacion.getNumEval()}</td>
                                                            <td>${evaluacion.getFechaAsignacion() != null ? df.dateToString(evaluacion.getFechaAsignacion()) : ''}</td>
                                                            <td>${evaluacion.getResultado()}</td>
                                                            <td>
                                                                <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/DetalleEvaluacion" method="post">
                                                                    <input hidden name="idEval" id="idEval" value="${evaluacion.getIdevaluacion()}" >    
                                                                    <button class="btn btn-default">Detalles</button>
                                                                </form>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                </c:forEach> 
                                            </tbody>
                                        </c:if>   
                                        <c:if test="${listaEvaluaciones.isEmpty()}">
                                            <h3><strong>No existen evaluaciones relacionadas</strong></h3>
                                        </c:if>
                                    </table>
                                </div>
                                <br>
                                <h3><strong>Resoluciones</strong></h3>
                                <br>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Tipo Resolución</th>
                                                <th>Número</th>
                                                <th>Fecha de Resolución</th>
                                                <th>Fecha de Notificación</th>
                                            </tr>
                                        </thead>
                                        <c:if test="${!listaEvaluaciones.isEmpty()}">
                                            <tbody>
                                                <c:forEach var="evaluacion" items="${listaEvaluaciones}" varStatus="status">
                                                    <c:if test="${!evaluacion.getResolucions().isEmpty()}">
                                                        <c:forEach var="resolucion" items="${evaluacion.getResolucions()}" varStatus="status">
                                                            <tr>
                                                                <td>
                                                                    ${resolucion.getTipo() == 'apto' ? 'Apto' : '' }
                                                                    ${resolucion.getTipo() == 'improcedente' ? 'Improcedente' : '' }
                                                                    ${resolucion.getTipo() == 'fin' ? 'Fin de procedimiento' : '' }
                                                                    ${resolucion.getTipo() == 'observado' ? 'Observado' : '' }
                                                                    ${resolucion.getTipo() == 'prorroga' ? 'Prórroga' : '' }
                                                                    ${resolucion.getTipo() == 'reconsideracion' ? 'Reconsideración' : '' }
                                                                    ${resolucion.getTipo() == 'infundada' ? 'Infundada la reconsideración' : '' }
                                                                    ${resolucion.getTipo() == 'desistimiento' ? 'Desistimiento' : '' }
                                                                    ${resolucion.getTipo() == 'colfam' ? 'Integracion Familiar' : '' }
                                                                    ${resolucion.getTipo() == 'sinefecto' ? 'Deja sin efecto la designacion' : '' }
                                                                    ${resolucion.getTipo() == 'adopcion' ? 'Resolución de adopción' : '' }
                                                                    ${resolucion.getTipo() == 'revocacion' ? 'Resolución que revoca la integración familiar' : '' }
                                                                </td>
                                                                <td>${resolucion.getNumero()}</td>
                                                                <td>${resolucion.getFechaResol() != null ? df.dateToString(resolucion.getFechaResol()) : ''}</td>
                                                                <td>${resolucion.getFechaNotificacion() != null ? df.dateToString(resolucion.getFechaNotificacion()) : ''}</td>
                                                            </tr>
                                                        </c:forEach>
                                                    </c:if>
                                                </c:forEach> 
                                            </tbody>
                                        </c:if>
                                        <c:if test="${listaEvaluaciones.isEmpty()}">
                                            <h3><strong>No existen resoluciones relacionadas</strong></h3>
                                        </c:if> 
                                    </table>
                                </div>
                                <c:if test="${estado != 'esperainter'}">
                                    <br>
                                    <h3><strong>Estudios del caso</strong></h3>
                                    <br>
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Orden</th>
                                                    <th>Nombre NNA</th>
                                                    <th>Grupo Ref. NNA</th>
                                                    <th>Código de Grupo Ref.</th>
                                                    <th>Fecha de Estudio</th>
                                                    <th>Fecha de Solicitud Adopción</th>
                                                </tr>
                                            </thead>
                                            <c:if test="${!listaEstudios.isEmpty()}">
                                                <tbody>
                                                    <c:forEach var="estudio" items="${listaEstudios}" varStatus="status">
                                                        <tr>
                                                            <td>${estudio.getOrden()}</td>
                                                            <td>
                                                                ${estudio.getNna().getNombre()}
                                                                ${estudio.getNna().getApellidoP()}
                                                                ${estudio.getNna().getApellidoM()}
                                                            </td>
                                                            <td>
                                                                ${estudio.getNna().getEspecial() == 0 ? 'Necesidades Especiales' : ''}
                                                                ${estudio.getNna().getEnfermo() == 0 ? 'Problemas de salud ' : ''}
                                                                ${estudio.getNna().getMayor() == 0 ? 'Mayor de 9 años' : ''}
                                                                ${estudio.getNna().getAdolescente() == 0 ? 'Adolescente' : ''}
                                                                ${estudio.getNna().getHermano() == 0 ? 'Hermanos' : ''}
                                                            </td>
                                                            <td>
                                                                <c:forEach var="expediente" items="${estudio.getNna().getExpedienteNnas()}" varStatus="status">
                                                                    ${expediente.getCodigoReferencia()}
                                                                </c:forEach>
                                                            </td>
                                                            <td>${estudio.getFechaEstudio() != null ? df.dateToString(estudio.getFechaEstudio()) : ''}</td>
                                                            <td>${estudio.getFechaSolAdop() != null ? df.dateToString(estudio.getFechaSolAdop()) : ''}</td>
                                                        </tr>
                                                    </c:forEach>  
                                                </tbody>
                                            </c:if>
                                            <c:if test="${listaEstudios.isEmpty()}">
                                                <h3><strong>No existen Estudios de caso relacionados</strong></h3>
                                            </c:if> 

                                        </table>
                                    </div>
                                    <br>
                                    <h3><strong>Propuestas de designación</strong></h3>
                                    <br>
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Número</th>
                                                    <th>Prioridad</th>
                                                    <th>Tipo</th>
                                                    <th>¿NNA Prioritario?</th>
                                                    <th>Nombre NNA</th>
                                                    <th>Fecha de Propuesta</th>
                                                    <th>Fecha Consejo</th>
                                                    <th>Obs</th>
                                                </tr>
                                            </thead>
                                            <c:if test="${!listaDesignaciones.isEmpty()}">
                                                <tbody>
                                                    <c:forEach var="designacion" items="${listaDesignaciones}" > 
                                                        <tr>
                                                            <td>${designacion.getNDesignacion()}</td>
                                                            <td>${designacion.getPrioridad()}</td>
                                                            <td>${designacion.getTipoPropuesta()}</td>
                                                            <td>
                                                                ${designacion.getNna().getEspecial() == 0 || 
                                                                  designacion.getNna().getEnfermo() == 0 || 
                                                                  designacion.getNna().getMayor() == 0 || 
                                                                  designacion.getNna().getAdolescente() == 0 || 
                                                                  designacion.getNna().getHermano() == 0 ? 'Si' : 'No'}

                                                            <td>
                                                                ${designacion.getNna().getNombre()}
                                                                ${designacion.getNna().getApellidoP()}
                                                                ${designacion.getNna().getApellidoM()}
                                                            </td>
                                                            <td>${designacion.getFechaPropuesta() != null ? df.dateToString(designacion.getFechaPropuesta()) : ''}</td>
                                                            <td>${designacion.getFechaConsejo() != null ? df.dateToString(designacion.getFechaConsejo()) : ''}</td>
                                                            <td>${designacion.getObs()}</td>
                                                        </tr>
                                                    </c:forEach> 
                                                </tbody>
                                            </c:if>
                                            <c:if test="${listaDesignaciones.isEmpty()}">
                                                <h3><strong>No existen Designaciones relacionadas</strong></h3>
                                            </c:if> 
                                        </table>
                                    </div>
                                </c:if>
                            </c:if>      
                            <!--FIN DE CONTENIDO-->
                        </fieldset>
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
            <script type="text/javascript">

                            $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

            </script>
            <!-- Ubicar al final -->
    </body>
</html>

