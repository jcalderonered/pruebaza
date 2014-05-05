<%-- 
    Document   : detalle_evaluacion
    Created on : 14/11/2013, 07:51:13 PM
    Author     : User
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
                    <div class="col-md-8">
                        <!-- <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  -->
                        <form role="form">
                            <c:if test="${estado != 'formativa'}">
                                <br>
                                <h1 align="center"><strong>Familia "${expediente.getExpediente()}"</strong></h1>
                                <br>
                            </c:if>
                            <br>
                            <br>
                            <ul class="nav nav-tabs row">
                                <ul class="nav nav-tabs row">
                                    <li><a href="${pageContext.servletContext.contextPath}/laSolicitante">La Solicitante</a></li>
                                    <li><a href="${pageContext.servletContext.contextPath}/elSolicitante" >El solicitante</a></li>
                                    <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/compFamiliar" >Composición familiar</a></li>-->
                                    <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/vivienda" >Vivienda</a></li>-->
                                    <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/infoExpediente" >Información del Expediente</a></li>
                                    <li class="active"><a href="${pageContext.servletContext.contextPath}/procesoAdopcion" >Proceso de adopción</a></li>
                                    <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/antNna" >Antecedentes del NNA</a></li>
                                    <li ${estado == 'formativa' || estado == 'evaluacion' || estado == 'espera' || estado == 'designacion' || estado == 'adopcion' || estado == 'reevaluacion' ? 'class="hidden"' : ''} ><a href="${pageContext.servletContext.contextPath}/nnaAsociado" >NNA Adoptado</a></li>
                                        <% if (!u.getRol().equals("DEIA")) {%>
                                    <li><a href="${pageContext.servletContext.contextPath}/atenciones" >Atenciones</a></li>
                                        <%}%>
                                        <% if (u.getRol().equals("DCRI")) {%>
                                    <li><a href="${pageContext.servletContext.contextPath}/EditUserPass" >Editar Perfil de Familia</a></li>
                                        <%}%>
                                </ul>
                            </ul>
                            <br>
                            <fieldset>

                                <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                                <br>
                                <h3 align="left"><strong>Detalles de la evaluación</strong></h3>
                                <br>

                                <div class="control-group">
                                    <label class="control-label">Tipo de Evaluación</label>
                                    <div class="controls">
                                        <input value="${evaluacion.getTipo()}" id="tipo_eval" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha asignación</label>
                                    <div class="controls">
                                        <input value="${evaluacion.getFechaAsignacion() != null ? df.dateToString(evaluacion.getFechaAsignacion()) : ''}" type="text" class="datepicker span2" value="" id="dp3" >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Responsable</label>
                                    <div class="controls">
                                        <input value="${evaluacion.getPersonal().getNombre()} ${evaluacion.getPersonal().getApellidoP()} ${evaluacion.getPersonal().getApellidoM()}" id="responsable" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Resultado</label>
                                    <div class="controls">
                                        <input value="${evaluacion.getResultado()}" id="resul" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha del resultado</label>
                                    <div class="controls">
                                        <input value="${evaluacion.getFechaResultado() != null ? df.dateToString(evaluacion.getFechaResultado()) : ''}" id="fecha_resul" name="full-name" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>


                                <!--
                                <h3 align="left"><strong>Detalles de la evaluación legal</strong></h3>
                                <p>A continuación se presentará información detallada sobre la evaluación legal desarrollada</p>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Solicitud DGA</label>
                                    <div class="controls">
                                        <input id="sol_dga" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones DGA</label>
                                    <div class="controls">
                                        <input id="obs_dga" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se realizó la Evaluación Social?</label>
                                    <div class="controls">
                                        <input id="eval_social" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones Eval. Social</label>
                                    <div class="controls">
                                        <input id="obs_social" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se realizó la Evaluación Psicológica?</label>
                                    <div class="controls">
                                        <input id="eval_psico" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones Eval. Psicológica</label>
                                    <div class="controls">
                                        <input id="obs_psico" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Cuenta con Certificado domiciliario vigente?</label>
                                    <div class="controls">
                                        <input id="cert_dom" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre el Certificado domiciliario</label>
                                    <div class="controls">
                                        <input id="obs_cert_dom" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se ha hecho presente la Partida de Matrimonio?</label>
                                    <div class="controls">
                                        <input id="part_mat" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre la Partida de Matrimonio</label>
                                    <div class="controls">
                                        <input id="obs_part_mat" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se ha hecho presente la Sentencia de Divorcio?</label>
                                    <div class="controls">
                                        <input id="sent_divor" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre la Sentencia de Divorcio</label>
                                    <div class="controls">
                                        <input id="obs_sent_divor" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se ha hecho presente la Partida de Defunción del Cónyuge?</label>
                                    <div class="controls">
                                        <input id="part_defun_cony" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre la Partida de Defunción del Cónyuge</label>
                                    <div class="controls">
                                        <input id="obs_part_defun_cony" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se ha hecho presente la Partida de Nacimiento del Niño Biológico?</label>
                                    <div class="controls">
                                        <input id="part_nac_bio" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre la Partida de Nacimiento del Niño Biológico</label>
                                    <div class="controls">
                                        <input id="obs_part_nac_bio" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se ha hecho presente la Partida de Nacimiento del Niño Adoptivo?</label>
                                    <div class="controls">
                                        <input id="part_nac_adop" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre la Partida de Nacimiento del Niño Adoptivo</label>
                                    <div class="controls">
                                        <input id="obs_part_nac_adop" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Presenta reportes de seguimiento post adoptivo que no hayan sido tramitadas por la DGA?</label>
                                    <div class="controls">
                                        <input id="post_dga" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre los seguimientos post adoptivos</label>
                                    <div class="controls">
                                        <input id="obs_post_dga" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Presenta fotografía familiar y su hogar?</label>
                                    <div class="controls">
                                        <input id="foto_fam" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre las fotografias</label>
                                    <div class="controls">
                                        <input id="obs_foto_fam" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Presenta compromiso de seguimiento post adoptivo?</label>
                                    <div class="controls">
                                        <input id="compromiso" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre el compromiso de seguimiento</label>
                                    <div class="controls">
                                        <input id="obs_compromiso" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                -->
                                <!--FIN DE CONTENIDO-->
                            </fieldset>
                        </form>
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