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
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/nna'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p> 
                        <h1 align="center"><strong>Expediente del NNA</strong></h1>
                        <br>
                        <c:if test="${expediente == null}">
                            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/crearExpedienteNna" method="post">
                                <input hidden name="idNna" id="idNna" value="${nna.getIdnna()}">  
                            </c:if>  
                            <c:if test="${expediente != null}">
                                <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/updateExpedienteNna" method="post">
                                    <input hidden name="idNna" id="idNna" value="${expediente.getNna().getIdnna()}">    
                                </c:if>   
                                <fieldset>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label">Unidad de adopción</label>
                                        <div class="controls">
                                            <select id="ua" name="ua">
                                                <c:forEach var="ua" items="${listaUa}" varStatus="status"> 
                                                    <option value="${ua.getIdunidad()}" ${ua.getIdunidad() == expediente.getUnidad().getIdunidad() ? 'selected' : ''}>${ua.getNombre()}</option>
                                                </c:forEach> 
                                            </select>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Número</label>
                                        <div class="controls">
                                            <input  id="numero" name="numero" type="text" value="${expediente.getNumero()}" placeholder="" class="input-xlarge" >
                                        </div>
                                    </div>
                                    <br>                                
                                    <div class="control-group">
                                        <label class="control-label">Fecha de ingreso al Sistema</label>
                                        <div class="controls">
                                            <input id="fechaIngreso" name="fechaIngreso" value="${fechaing}" type="text" class="datepicker input-xlarge">
                                        </div>
                                    </div>                                    
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">HT</label>
                                        <div class="controls">
                                            <input  id="ht" name="ht" value="${expediente.getHt()}" type="text" class="input-xlarge">
                                        </div>
                                    </div>                                                                    
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Número de investigación tutelar</label>
                                        <div class="controls">
                                            <input id="nInvTutelar" name="nInvTutelar" value="${expediente.getNExpTutelar()}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Fecha de investigación tutelar</label>
                                        <div class="controls">
                                            <input id="fechaInvTutelar" name="fechaInvTutelar" value="${fechainv}" type="text" class="datepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Procedencia tutelar</label>
                                        <div class="controls">
                                            <input id="procTutelar" name="procTutelar" value="${expediente.getProcTutelar()}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <br>
                                    <h3><strong>Ficha Integral</strong></h3>
                                    <br>
                                    <!-- Text input-->
                                    <div class="row">
                                        <div class="col-md-2">  
                                            <div class="radio">
                                                <label>
                                                    <input type="radio" name="fichaInt" id="optionsRadios1" value="0" ${expediente.getFichaIntegral() == 0 ? 'checked' : ''} >Ingreso</label>
                                            </div>
                                        </div>   
                                        <div class="col-md-3">   
                                            <div class="radio">
                                                <label>
                                                    <input type="radio" name="fichaInt" id="optionsRadios2" value="1" ${expediente.getFichaIntegral() == 1 ? 'checked' : ''} >Solicitado</label>
                                            </div>                            
                                        </div>
                                    </div>  
                                    <br>
                                    <h3><strong>Responsable Legal</strong></h3>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Nombre Completo</label>
                                        <div class="controls">
                                            <select id="respLegal" name="respLegal" >
                                                <c:forEach var="personal" items="${listaPersonal}" > 
                                                    <option value="${personal.getDni()}" ${expediente.getRespLegalNombre().equals(personal.getDni()) ? 'selected' : ''}>${personal.getNombre()} ${personal.getApellidoP()} ${personal.getApellidoM()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <br>
                                    <h3><strong>Responsable Psicosocial</strong></h3>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Nombre Completo</label>
                                        <div class="controls">
                                            <select id="respPsico" name="respPsico" >
                                                <c:forEach var="personal" items="${listaPersonal}" > 
                                                    <option value="${personal.getDni()}" ${expediente.getRespPsicosocialNombre().equals(personal.getDni()) ? 'selected' : ''}>${personal.getNombre()} ${personal.getApellidoP()} ${personal.getApellidoM()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <br>
                                    <h3><strong>Registro de informes de evaluación</strong></h3>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Lista de evaluaciones</label>
                                        <div class="controls">
                                            <c:choose>
                                                <c:when test="${expediente == null}">
                                                    <h3>Debe crear el expediente del NNA antes de ingresar sus informes de evaluación</h3>
                                                </c:when>
                                                <c:when test="${expediente != null}">
                                                    <input hidden name="idExpNna" id="idExpNna" value="${expediente.getIdexpedienteNna()}">
                                                </c:when>    
                                            </c:choose> 
                                            <input ${expediente == null ? 'disabled' : ''} type="submit" id="listaEval" name="listaEval" value="Ver" class="btn btn-default">                                            
                                        </div>
                                    </div>
                                    <br>
                                    <div>
                                        <label class="control-label">Estado</label>
                                        <div class="controls">
                                            <select ${expediente.getNna().getClasificacion().equals("seguimiento")  ? 'disabled' : ''} id="estado" name="estado">
                                                <option value="eval" ${expediente.getEstado().equals("eval") || expediente.getNna().getClasificacion().equals("seguimiento") ? 'selected' : ''} >Evaluación</option>
                                                <option ${expediente.getNna().getClasificacion().equals("regular") ? 'disabled': '' } id="segPrio" value="seg" ${expediente.getEstado().equals("seg") ? 'selected' : ''} >Seguimiento Prioritarios</option>
                                                <option value="adoptable" ${expediente.getEstado().equals("adoptable") ? 'selected' : ''} >Adoptable</option>
                                                <option value="desig" ${expediente.getEstado().equals("desig") ? 'selected' : ''} >Designado</option>
                                                <option value="adop" ${expediente.getEstado().equals("adop") ? 'selected' : ''} >Adoptado</option>
                                                <option value="arch" ${expediente.getEstado().equals("arch") ? 'selected' : ''} >Archivado</option>
                                            </select>
                                        </div>    
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Fecha del estado</label>
                                        <div class="controls">
                                            <input id="fechaEstado" name="fechaEstado" value="${fechaest}" type="text" class="datepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div>
                                        <label class="control-label">Clasificación</label>
                                        <div class="controls">
                                            <select onchange="clasif(this.value)" name="clasificacion" id="clasificacion" >
                                                <option value="prioritario" ${expediente.getNna().getClasificacion().equals("prioritario") || nna.getClasificacion().equals("prioritario") ? 'selected' : ''} >Prioritario</option>
                                                <option value="seguimiento" ${expediente.getNna().getClasificacion().equals("seguimiento") || nna.getClasificacion().equals("seguimiento") ? 'selected' : ''} >Seguimiento</option>
                                                <option value="regular" ${expediente.getNna().getClasificacion().equals("regular") || nna.getClasificacion().equals("regular") ? 'selected' : ''} >Regular</option>
                                            </select>
                                        </div>    
                                    </div>
                                    <br>
                                    <br>

                                    <h3><strong>Datos de NNA prioritario</strong></h3>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Fecha de ingreso a Prioritarios</label>
                                        <div class="controls">
                                            <input id="fechaIngresoPrio" name="fechaIngresoPrio" value="${expediente.getFechaIngPrio() != null ? df.dateToStringNumeros(expediente.getFechaIngPrio()) : ''}" type="text" class="datepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Fecha de Actualizacion</label>
                                        <div class="controls">
                                            <input id="fechaAct" name="fechaAct" value="${expediente.getFechaActualizacion() != null ? df.dateToStringNumeros(expediente.getFechaActualizacion()) : ''}" type="text" class="datepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div>
                                        <label class="control-label">Grupo de referencia</label>
                                        <div class="controls">
                                            <select onchange="prioritarios(this.value)" id="grpRef" name="grpRef" ${expediente.getNna().getClasificacion().equals("prioritario") || nna.getClasificacion().equals("prioritario") ? '' : 'disabled'}>
                                                <option></option>
                                                <option value="m" ${expediente.getNna().getMayor() == 0 || nna.getMayor() == 0 ? 'selected' : ''} >Mayores</option>
                                                <option value="a" ${expediente.getNna().getAdolescente() == 0 || nna.getAdolescente() == 0 ? 'selected' : ''} >Adolescentes</option>
                                                <option value="h" ${expediente.getNna().getHermano() == 0 || nna.getHermano() == 0 ? 'selected' : ''} >Hermanos</option>
                                                <option value="s" ${expediente.getNna().getEnfermo() == 0 || nna.getEnfermo() == 0 ? 'selected' : ''} >Salud</option>
                                                <option value="e" ${expediente.getNna().getEspecial() == 0 || nna.getEspecial() == 0 ? 'selected' : ''} >Necesidades especiales</option>
                                            </select>
                                        </div>  
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Historial de código de mayores</label>
                                        <div class="controls">
                                            <select id="histMayores" name="histMayores" ${expediente.getNna().getMayor() == 0 || nna.getMayor() == 0 ? '' : 'disabled'}>
                                                <option> </option>
                                                <c:forEach var="tempExp" items="${listaExpedientes}" > 
                                                    <c:if test="${!tempExp.getCodigoReferencia() != null && (tempExp.getCodigoReferencia().contains('M') || tempExp.getCodigoReferencia().contains('m'))}">
                                                        <option>${tempExp.getCodigoReferencia()}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>        
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Código Mayores</label>
                                        <div class="controls">
                                            <input id="codMayor" name="codMayor" ${expediente.getNna().getMayor() == 0 || nna.getMayor() == 0 ? '' : 'disabled'}  value="${expediente.getNna().getMayor() == 0 ? expediente.getCodigoReferencia() : ''}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Historial de código de Adolescentes</label>
                                        <div class="controls">
                                            <select id="histAdolescente" name="histAdolescente" ${expediente.getNna().getAdolescente() == 0 || nna.getAdolescente() == 0 ? '' : 'disabled'}>
                                                <option> </option>
                                                <c:forEach var="tempExp" items="${listaExpedientes}" > 
                                                    <c:if test="${!tempExp.getCodigoReferencia() != null && (tempExp.getCodigoReferencia().contains('A') || tempExp.getCodigoReferencia().contains('a'))}">
                                                        <option>${tempExp.getCodigoReferencia()}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>        
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Código Adolescente</label>
                                        <div class="controls">
                                            <input id="codAdoles" name="codAdoles" ${expediente.getNna().getAdolescente() == 0 || nna.getAdolescente() == 0 ? '' : 'disabled'} value="${expediente.getNna().getAdolescente() == 0 ? expediente.getCodigoReferencia() : ''}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Historial de código de Hermanos</label>
                                        <div class="controls">
                                            <select id="histHermano" name="histHermano" ${expediente.getNna().getHermano() == 0 || nna.getHermano() == 0 ? '' : 'disabled'} >
                                                <option> </option>
                                                <c:forEach var="tempExp" items="${listaExpedientes}" > 
                                                    <c:if test="${!tempExp.getCodigoReferencia() != null && (tempExp.getCodigoReferencia().contains('H') || tempExp.getCodigoReferencia().contains('h'))}">
                                                        <option>${tempExp.getCodigoReferencia()}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>        
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Código Hermano</label>
                                        <div class="controls">
                                            <input id="codHermano" name="codHermano" ${expediente.getNna().getHermano() == 0 || nna.getHermano() == 0 ? '' : 'disabled'} value="${expediente.getNna().getHermano() == 0 ? expediente.getCodigoReferencia() : ''}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Historial de código de Problemas de salud</label>
                                        <div class="controls">
                                            <select id="histSalud" name="histSalud" ${expediente.getNna().getEnfermo() == 0 || nna.getEnfermo() == 0 ? '' : 'disabled'} >
                                                <option> </option>
                                                <c:forEach var="tempExp" items="${listaExpedientes}" > 
                                                    <c:if test="${!tempExp.getCodigoReferencia() != null && (tempExp.getCodigoReferencia().contains('S') || tempExp.getCodigoReferencia().contains('s'))}">
                                                        <option>${tempExp.getCodigoReferencia()}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>        
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Código Problemas de Salud</label>
                                        <div class="controls">
                                            <input id="codSalud" name="codSalud" ${expediente.getNna().getEnfermo() == 0 || nna.getEnfermo() == 0 ? '' : 'disabled'} value="${expediente.getNna().getEnfermo() == 0 ? expediente.getCodigoReferencia() : ''}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <div class="control-group">
                                        <label class="control-label">Historial de código de Necesidades Especiales</label>
                                        <div class="controls">
                                            <select id="histEspeciales" name="histEspeciales" ${expediente.getNna().getEspecial() == 0 || nna.getEspecial() == 0 ? '' : 'disabled'} >
                                                <option> </option>
                                                <c:forEach var="tempExp" items="${listaExpedientes}" > 
                                                    <c:if test="${!tempExp.getCodigoReferencia() != null && (tempExp.getCodigoReferencia().contains('NE') || tempExp.getCodigoReferencia().contains('ne'))}">
                                                        <option>${tempExp.getCodigoReferencia()}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>    
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Código Necesidades Especiales</label>
                                        <div class="controls">
                                            <input id="codEspeciales" name="codEspeciales" ${expediente.getNna().getEspecial() == 0 || nna.getEspecial() == 0 ? '' : 'disabled'} value="${expediente.getNna().getEspecial() == 0 ? expediente.getCodigoReferencia() : ''}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Diagnóstico</label>
                                        <div class="controls">
                                            <textarea id="diagnostico" name="diagnostico" type="text" class="input-xlarge">${expediente.getComentarios()}</textarea>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Comentario</label>
                                        <div class="controls">
                                            <textarea id="comentario" name="comentario" type="text" class="input-xlarge">${expediente.getDiagnostico()}</textarea>
                                        </div>
                                    </div>
                                    <br>
                                    <h3><strong>LLenar luego que el NNA ha sido adoptado</strong></h3>
                                    <div class="control-group">
                                        <label class="control-label">Nombre Actual</label>
                                        <div class="controls">
                                            <input  id="nombreActual" name="nombreActual" value="${expediente.getNActual()}" type="text" placeholder="" class="input-xlarge" >
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Apellido Paterno Actual</label>
                                        <div class="controls">
                                            <input  id="apellidoPActual" name="apellidoPActual" value="${expediente.getApellidopActual()}" type="text" placeholder="" class="input-xlarge" >
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Apellido Materno Actual</label>
                                        <div class="controls">
                                            <input  id="apellidoMActual" name="apellidoMActual" value="${expediente.getApellidomActual()}" type="text" placeholder="" class="input-xlarge" >
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <div class="controls">
                                            <button ${usuario.getUnidad().getDepartamento() != 'Lima' ? 'disabled' : ''} id="singlebutton" name="singlebutton" class="btn btn-default">Guardar</button>     
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
            <!-- Bootstrap core JavaScript
        ================================================== -->
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
            <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
            <script type="text/javascript">

                                                $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

            </script>
            <script>
                function clasif(value)
                {
                    var segPri = document.getElementById("segPrio");
                    var est = document.getElementById("estado");
                    var op = document.getElementById("grpRef");
                    var histMayores = document.getElementById("histMayores");
                    var mayores = document.getElementById("codMayor");
                    var histAdolescente = document.getElementById("histAdolescente");
                    var adolescentes = document.getElementById("codAdoles");
                    var histHermano = document.getElementById("histHermano");
                    var hermanos = document.getElementById("codHermano");
                    var histSalud = document.getElementById("histSalud");
                    var salud = document.getElementById("codSalud");
                    var histEspeciales = document.getElementById("histEspeciales");
                    var especiales = document.getElementById("codEspeciales");
                    //you can get the value from arguments itself
                    //alert(value);
                    if (value == 'regular' || value == 'seguimiento') {
                        segPri.disabled = true;
                        est.disabled = false;
                        op.value = '';
                        op.disabled = true;
                        mayores.disabled = true;
                        adolescentes.disabled = true;
                        hermanos.disabled = true;
                        salud.disabled = true;
                        especiales.disabled = true;
                        histMayores.value = '';
                        histMayores.disabled = true;
                        histAdolescente.value = '';
                        histAdolescente.disabled = true;
                        histHermano.value = '';
                        histHermano.disabled = true;
                        histSalud.value = '';
                        histSalud.disabled = true;
                        histEspeciales.value = '';
                        histEspeciales.disabled = true;
                        mayores.value = '';
                        adolescentes.value = '';
                        hermanos.value = '';
                        salud.value = '';
                        especiales.value = '';
                    }
                    if (value == 'prioritario') {
                        segPri.disabled = false;
                        op.disabled = false;
                        est.disabled = false;
                    }
                    if (value == 'seguimiento') {
                        est.value = 'eval';
                        est.disabled = true;
                    }
                }
            </script>
            <script>
                function prioritarios(value)
                {
                    var histMayores = document.getElementById("histMayores");
                    var mayores = document.getElementById("codMayor");
                    var histAdolescente = document.getElementById("histAdolescente");
                    var adolescentes = document.getElementById("codAdoles");
                    var histHermano = document.getElementById("histHermano");
                    var hermanos = document.getElementById("codHermano");
                    var histSalud = document.getElementById("histSalud");
                    var salud = document.getElementById("codSalud");
                    var histEspeciales = document.getElementById("histEspeciales");
                    var especiales = document.getElementById("codEspeciales");

                    //you can get the value from arguments itself
                    //alert(value);
                    if (value == 'm') {
                        histMayores.disabled = false;
                        mayores.disabled = false;
                        adolescentes.disabled = true;
                        hermanos.disabled = true;
                        salud.disabled = true;
                        especiales.disabled = true;

                    }
                    if (value == 'a') {
                        mayores.disabled = true;
                        histAdolescente.disabled = false;
                        adolescentes.disabled = false;
                        hermanos.disabled = true;
                        salud.disabled = true;
                        especiales.disabled = true;

                    }
                    if (value == 'h') {
                        mayores.disabled = true;
                        adolescentes.disabled = true;
                        histHermano.disabled = false;
                        hermanos.disabled = false;
                        salud.disabled = true;
                        especiales.disabled = true;

                    }
                    if (value == 's') {
                        mayores.disabled = true;
                        adolescentes.disabled = true;
                        hermanos.disabled = true;
                        histSalud.disabled = false;
                        salud.disabled = false;
                        especiales.disabled = true;

                    }
                    if (value == 'e') {
                        mayores.disabled = true;
                        adolescentes.disabled = true;
                        hermanos.disabled = true;
                        salud.disabled = true;
                        histEspeciales.disabled = false;
                        especiales.disabled = false;

                    }
                }
            </script>
            <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>
