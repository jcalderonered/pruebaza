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
        <style type="text/css">  
            .pg-normal {  
                color: #000000;  
                font-weight: normal;  
                text-decoration: none;  
                cursor: pointer;  
            }  

            .pg-selected {  
                color: #800080;  
                font-weight: bold;  
                text-decoration: underline;  
                cursor: pointer;  
            }  
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Sistema de Adopciones</title>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/bootstrap.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/index_002.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/mimp_css.css">
    </head>

    <script type="text/javascript">
        function Pager(tableName, itemsPerPage) {
            this.tableName = tableName;
            this.itemsPerPage = itemsPerPage;
            this.currentPage = 1;
            this.pages = 0;
            this.inited = false;

            this.showRecords = function(from, to) {
                var rows = document.getElementById(tableName).rows;
                // i starts from 1 to skip table header row  
                for (var i = 1; i < rows.length; i++) {
                    if (i < from || i > to)
                        rows[i].style.display = 'none';
                    else
                        rows[i].style.display = '';
                }
            }

            this.showPage = function(pageNumber) {
                if (!this.inited) {
                    alert("not inited");
                    return;
                }

                var oldPageAnchor = document.getElementById('pg' + this.currentPage);
                oldPageAnchor.className = 'pg-normal';

                this.currentPage = pageNumber;
                var newPageAnchor = document.getElementById('pg' + this.currentPage);
                newPageAnchor.className = 'pg-selected';

                var from = (pageNumber - 1) * itemsPerPage + 1;
                var to = from + itemsPerPage - 1;
                this.showRecords(from, to);
            }

            this.prev = function() {
                if (this.currentPage > 1)
                    this.showPage(this.currentPage - 1);
            }

            this.next = function() {
                if (this.currentPage < this.pages) {
                    this.showPage(this.currentPage + 1);
                }
            }

            this.init = function() {
                var rows = document.getElementById(tableName).rows;
                var records = (rows.length - 1);
                this.pages = Math.ceil(records / itemsPerPage);
                this.inited = true;
            }

            this.showPageNav = function(pagerName, positionId) {
                if (!this.inited) {
                    alert("not inited");
                    return;
                }
                var element = document.getElementById(positionId);

                var pagerHtml = '<span onclick="' + pagerName + '.prev();" class="pg-normal"> « Ant </span> | ';
                for (var page = 1; page <= this.pages; page++)
                    pagerHtml += '<span id="pg' + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> | ';
                pagerHtml += '<span onclick="' + pagerName + '.next();" class="pg-normal"> Sig »</span>';

                element.innerHTML = pagerHtml;
            }
        }
    </script>  	

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
                        <c:if test="${idReunion != null}">
                            <form action="${pageContext.servletContext.contextPath}/PersonalInscritosTallerInicio?idTaller=${idTaller}&nombreTaller=${nombre}&historial=${historial}" method="post">
                                <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                            </form>
                        </c:if>
                        <c:if test="${idReunion == null}"> 
                            <form onclick="location.href = '${pageContext.servletContext.contextPath}/inf'" method="post">
                                <p align="right"><button  id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                            </form>
                        </c:if>
                        <p class="text-left"><h1><strong> Listado de Asistentes</strong> </h1></p>
                        <br>

                        <div class="controls">
                            <label class="control-label" for="textinput">Nombre de evento&nbsp</label>
                            <input id="textinput" name="textinput" value="${nombre}" disabled type="text" placeholder="Taller Primera adopción" class="input-xlarge">
                        </div>
                        <br>
                        <div class="controls">
                            <label class="control-label" for="textinput">Tipo de evento &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  </label>
                            <input id="textinput" name="textinput" disabled type="text" placeholder="Taller" class="input-xlarge">
                        </div>
                        <br>
                        <div class="controls">
                            <label class="control-label" for="textinput">Grupo &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  </label>
                            <input id="textinput" name="textinput" value="${grupo}" disabled type="text" placeholder="1" class="input-xlarge">
                        </div>
                        <br>
                        <div class="controls">
                            <label class="control-label" for="textinput">Turno &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  </label>
                            <input id="textinput" name="textinput" value="${turno}" disabled type="text" placeholder="Mañana" class="input-xlarge">
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <label class="control-label" for="textinput">Fecha &nbsp</label>
                                <input id="textinput" name="textinput" value="${formato.dateToString(reunion.getFecha())}" disabled type="text" placeholder="28/10/2013" class="input-xlarge">
                            </div>
                            <div class="col-md-4">
                                <label class="control-label" for="textinput">Hora &nbsp</label>
                                <input id="textinput" name="textinput" value="${reunion.getHora()}" disabled type="text" placeholder="16:30" class="input-xlarge">
                            </div>

                        </div>
                        <br>
                        <br>

                        <div class="bs-example">
                            <table id="mi_tabla" class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>El asistente</th>
                                        <th>Edad</th>
                                        <th>La asistente</th>
                                        <th>Edad</th>
                                        <th>Correo</th>
                                        <th>Información</th>
                                        <th>Asistencia</th>
                                        <th>Inasistencia Justificada</th>
                                    </tr>
                                </thead>
                                <c:if test="${!listaFormularios.isEmpty()}">
                                    <tbody>
                                        <c:forEach var="formulario" items="${listaFormularios}" varStatus="status">
                                            <tr>
                                                <c:choose>
                                                    <c:when test="${formulario.getAsistentes().size() == 2}">     
                                                        <c:forEach var="asistente" items="${formulario.getAsistentes()}" varStatus="status">
                                                            <c:choose>
                                                                <c:when test="${asistente.getSexo() == 109}">
                                                                    <c:set var="el" value="${asistente}" scope="page" />

                                                                </c:when>
                                                                <c:when test="${asistente.getSexo() == 102}">
                                                                    <c:set var="ella" value="${asistente}" scope="page" />
                                                                </c:when> 
                                                            </c:choose>
                                                        </c:forEach>
                                                        <td>
                                                            ${el.getNombre()}
                                                            ${el.getApellidoP()}
                                                            ${el.getApellidoM()}
                                                        </td>
                                                        <td>
                                                            ${el.getEdad()}
                                                        </td>

                                                        <td>
                                                            ${ella.getNombre()}
                                                            ${ella.getApellidoP()}
                                                            ${ella.getApellidoM()}
                                                        </td>
                                                        <td>
                                                            ${ella.getEdad()}
                                                        </td>

                                                        <td>${ella.getCorreo()}</td>
                                                        <td>
                                                            <form action="${pageContext.servletContext.contextPath}/PersonalDetalleFamiliaInscritaSesion3" method="post">
                                                                <input hidden name="idFormulario" id="idFormulario" value="${formulario.getIdformularioSesion()}">
                                                                <input hidden name="idReunion" id="idReunion" value="${reunion.getIdreunion()}">
                                                                <input hidden name="idTaller" id="idTaller" value="${idTaller}">
                                                                <input hidden name="idFamilia" id="idFamilia" value="${formulario.getFamilia().getIdfamilia()}">
                                                                <input hidden name="asistencia" id="asistencia" value="A"> 
                                                                <input hidden name="nombre" id="nombre" value="${nombre}"> 
                                                                <input hidden name="grupo" id="grupo" value="${grupo}"> 
                                                                <input hidden name="turno" id="turno" value="${turno}"> 
                                                                <input hidden name="historial" id="turno" value="${historial}"> 
                                                                <button type="submit" class="btn btn-default">Ver</button>
                                                            </form>
                                                        </td>
                                                        <td>
                                                            <form action="${pageContext.servletContext.contextPath}/PersonalAsistenciaReunion" method="post">
                                                                <input hidden name="idReunion" id="idReunion" value="${reunion.getIdreunion()}">
                                                                <input hidden name="idFamilia" id="idFamilia" value="${formulario.getFamilia().getIdfamilia()}">
                                                                <input hidden name="asistencia" id="asistencia" value="A"> 
                                                                <input hidden name="nombre" id="nombre" value="${nombre}"> 
                                                                <input hidden name="grupo" id="grupo" value="${grupo}"> 
                                                                <input hidden name="turno" id="turno" value="${turno}"> 
                                                                <input hidden name="idTaller" id="idTaller" value="${idTaller}"> 
                                                                <input hidden name="historial" id="historial" value="${historial}"> 
                                                                <c:set var="token" value="0" scope="page" />
                                                                <c:forEach var="AFR" items="${formulario.getFamilia().getAsistenciaFRs()}" varStatus="status">
                                                                    <c:if test="${AFR.getReunion().getIdreunion() == reunion.getIdreunion()}">
                                                                        <c:if test="${token == '0'}">
                                                                            <button ${AFR.getAsistencia() == 65 || AFR.getInasJus() == 0 ? 'disabled' : ''} type="submit" class="btn btn-default">Asistencia</button>
                                                                            <c:set var="token" value="1" scope="page" />   
                                                                        </c:if>        
                                                                    </c:if>                 
                                                                </c:forEach>   
                                                            </form>
                                                        </td>
                                                        <td>
                                                            <form action="${pageContext.servletContext.contextPath}/PersonalInasistenciaReunion" method="post">
                                                                <input hidden name="idReunion" id="idReunion" value="${reunion.getIdreunion()}">
                                                                <input hidden name="idFamilia" id="idFamilia" value="${formulario.getFamilia().getIdfamilia()}">
                                                                <input hidden name="justificado" id="user" value="0"> 
                                                                <input hidden name="nombre" id="nombre" value="${nombre}"> 
                                                                <input hidden name="grupo" id="grupo" value="${grupo}"> 
                                                                <input hidden name="turno" id="turno" value="${turno}"> 
                                                                <input hidden name="idTaller" id="idTaller" value="${idTaller}"> 
                                                                <input hidden name="historial" id="historial" value="${historial}">
                                                                <c:set var="token" value="0" scope="page" />
                                                                <c:forEach var="AFR" items="${formulario.getFamilia().getAsistenciaFRs()}" varStatus="status">
                                                                    <c:if test="${AFR.getReunion().getIdreunion() == reunion.getIdreunion()}">
                                                                        <c:if test="${token == '0'}">
                                                                            <button ${AFR.getInasJus() == 0 || AFR.getAsistencia() == 65 ? 'disabled' : ''}  type="submit" class="btn btn-default">Justificado</button>
                                                                            <c:set var="token" value="1" scope="page" />   
                                                                        </c:if>        
                                                                    </c:if>                
                                                                </c:forEach>   
                                                            </form>
                                                        </td>
                                                <input hidden type="text" name="idFormulario" id="idFormulario" value="${formulario.getIdformularioSesion()}">
                                            </c:when>
                                            <c:when test="${formulario.getAsistentes().size() == 1}">
                                                <c:forEach var="asistente" items="${formulario.getAsistentes()}" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${asistente.getSexo() == 109}">
                                                            <td>
                                                                ${asistente.getNombre()}    
                                                                ${asistente.getApellidoP()}
                                                                ${asistente.getApellidoM()}
                                                            </td>
                                                            <td>${asistente.getEdad()}</td>
                                                            <td></td>
                                                            <td></td>
                                                            <td>${asistente.getCorreo()}</td>
                                                            <td>
                                                                <form action="${pageContext.servletContext.contextPath}/PersonalDetalleFamiliaInscritaSesion3" method="post">
                                                                    <input hidden name="idFormulario" id="idFormulario" value="${formulario.getIdformularioSesion()}">
                                                                    <input hidden name="idReunion" id="idReunion" value="${reunion.getIdreunion()}">
                                                                    <input hidden name="idTaller" id="idTaller" value="${idTaller}">
                                                                    <input hidden name="idFamilia" id="idFamilia" value="${formulario.getFamilia().getIdfamilia()}">
                                                                    <input hidden name="asistencia" id="asistencia" value="A"> 
                                                                    <input hidden name="nombre" id="nombre" value="${nombre}"> 
                                                                    <input hidden name="grupo" id="grupo" value="${grupo}"> 
                                                                    <input hidden name="turno" id="turno" value="${turno}"> 
                                                                    <input hidden name="historial" id="turno" value="${historial}"> 

                                                                    <button type="submit" class="btn btn-default">Ver</button>
                                                                </form>
                                                            </td>
                                                            <td>
                                                                <form action="${pageContext.servletContext.contextPath}/PersonalAsistenciaReunion" method="post">
                                                                    <input hidden name="idReunion" id="idReunion" value="${reunion.getIdreunion()}">
                                                                    <input hidden name="idFamilia" id="idFamilia" value="${formulario.getFamilia().getIdfamilia()}">
                                                                    <input hidden name="asistencia" id="user" value="A"> 
                                                                    <input hidden name="nombre" id="nombre" value="${nombre}"> 
                                                                    <input hidden name="grupo" id="grupo" value="${grupo}"> 
                                                                    <input hidden name="turno" id="turno" value="${turno}"> 
                                                                    <input hidden name="idTaller" id="idTaller" value="${idTaller}"> 
                                                                    <input hidden name="historial" id="historial" value="${historial}">
                                                                    <c:set var="token" value="0" scope="page" />
                                                                    <c:forEach var="AFR" items="${formulario.getFamilia().getAsistenciaFRs()}" varStatus="status">
                                                                        <c:if test="${AFR.getReunion().getIdreunion() == reunion.getIdreunion()}">
                                                                            <c:if test="${token == '0'}">
                                                                                <button ${AFR.getAsistencia() == 65 || AFR.getInasJus() == 0 ? 'disabled' : ''}  type="submit" class="btn btn-default">Asistencia</button>
                                                                                <c:set var="token" value="1" scope="page" />   
                                                                            </c:if>
                                                                        </c:if>
                                                                    </c:forEach>   
                                                                </form>
                                                            </td>
                                                            <td>
                                                                <form action="${pageContext.servletContext.contextPath}/PersonalInasistenciaReunion" method="post">
                                                                    <input hidden name="idReunion" id="idReunion" value="${reunion.getIdreunion()}">
                                                                    <input hidden name="idFamilia" id="idFamilia" value="${formulario.getFamilia().getIdfamilia()}">
                                                                    <input hidden name="justificado" id="user" value="0"> 
                                                                    <input hidden name="nombre" id="nombre" value="${nombre}"> 
                                                                    <input hidden name="grupo" id="grupo" value="${grupo}"> 
                                                                    <input hidden name="turno" id="turno" value="${turno}"> 
                                                                    <input hidden name="idTaller" id="idTaller" value="${idTaller}"> 
                                                                    <input hidden name="historial" id="historial" value="${historial}">
                                                                    <c:set var="token" value="0" scope="page" />
                                                                    <c:forEach var="AFR" items="${formulario.getFamilia().getAsistenciaFRs()}" varStatus="status">
                                                                        <c:if test="${AFR.getReunion().getIdreunion() == reunion.getIdreunion()}">
                                                                            <c:if test="${token == '0'}">
                                                                                <button ${AFR.getInasJus() == 0 || AFR.getAsistencia() == 65 ? 'disabled' : ''}  type="submit" class="btn btn-default">Justificado</button>
                                                                                <c:set var="token" value="1" scope="page" />   
                                                                            </c:if> 
                                                                        </c:if>   
                                                                    </c:forEach>   
                                                                </form>
                                                            </td>
                                                        </c:when>
                                                        <c:when test="${asistente.getSexo() == 102}">
                                                            <td></td>
                                                            <td></td>
                                                            <td>
                                                                ${asistente.getNombre()}
                                                                ${asistente.getApellidoP()}
                                                                ${asistente.getApellidoM()}
                                                            </td>
                                                            <td>${asistente.getEdad()}</td>
                                                            <td>${asistente.getCorreo()}</td>
                                                            <td>
                                                                <form action="${pageContext.servletContext.contextPath}/PersonalDetalleFamiliaInscritaSesion3" method="post">
                                                                    <input hidden name="idFormulario" id="idFormulario" value="${formulario.getIdformularioSesion()}">
                                                                    <input hidden name="idReunion" id="idReunion" value="${reunion.getIdreunion()}">
                                                                    <input hidden name="idTaller" id="idTaller" value="${idTaller}">
                                                                    <input hidden name="idFamilia" id="idFamilia" value="${formulario.getFamilia().getIdfamilia()}">
                                                                    <input hidden name="asistencia" id="asistencia" value="A"> 
                                                                    <input hidden name="nombre" id="nombre" value="${nombre}"> 
                                                                    <input hidden name="grupo" id="grupo" value="${grupo}"> 
                                                                    <input hidden name="turno" id="turno" value="${turno}"> 
                                                                    <input hidden name="historial" id="turno" value="${historial}"> 
                                                                    <button type="submit" class="btn btn-default">Ver</button>
                                                                </form>
                                                            </td>
                                                            <td>
                                                                <form action="${pageContext.servletContext.contextPath}/PersonalAsistenciaReunion" method="post">
                                                                    <input hidden name="idReunion" id="idReunion" value="${reunion.getIdreunion()}">
                                                                    <input hidden name="idFamilia" id="idFamilia" value="${formulario.getFamilia().getIdfamilia()}">
                                                                    <input hidden name="asistencia" id="user" value="A"> 
                                                                    <input hidden name="nombre" id="nombre" value="${nombre}"> 
                                                                    <input hidden name="grupo" id="grupo" value="${grupo}"> 
                                                                    <input hidden name="turno" id="turno" value="${turno}"> 
                                                                    <input hidden name="idTaller" id="idTaller" value="${idTaller}"> 
                                                                    <input hidden name="historial" id="historial" value="${historial}">
                                                                    <c:set var="token" value="0" scope="page" />
                                                                    <c:forEach var="AFR" items="${formulario.getFamilia().getAsistenciaFRs()}" varStatus="status">
                                                                        <c:if test="${AFR.getReunion().getIdreunion() == reunion.getIdreunion()}">
                                                                            <c:if test="${token == '0'}">
                                                                                <button ${AFR.getAsistencia() == 65 || AFR.getInasJus() == 0 ? 'disabled' : ''}  type="submit" class="btn btn-default">Asistencia</button>
                                                                                <c:set var="token" value="1" scope="page" />   
                                                                            </c:if> 
                                                                        </c:if>  
                                                                    </c:forEach>   
                                                                </form>
                                                            </td>
                                                            <td>
                                                                <form action="${pageContext.servletContext.contextPath}/PersonalInasistenciaReunion" method="post">
                                                                    <input hidden name="idReunion" id="idReunion" value="${reunion.getIdreunion()}">
                                                                    <input hidden name="idFamilia" id="idFamilia" value="${formulario.getFamilia().getIdfamilia()}">
                                                                    <input hidden name="justificado" id="user" value="0"> 
                                                                    <input hidden name="nombre" id="nombre" value="${nombre}"> 
                                                                    <input hidden name="grupo" id="grupo" value="${grupo}"> 
                                                                    <input hidden name="turno" id="turno" value="${turno}"> 
                                                                    <input hidden name="idTaller" id="idTaller" value="${idTaller}"> 
                                                                    <input hidden name="historial" id="historial" value="${historial}">
                                                                    <c:set var="token" value="0" scope="page" />
                                                                    <c:forEach var="AFR" items="${formulario.getFamilia().getAsistenciaFRs()}" varStatus="status">
                                                                        <c:if test="${AFR.getReunion().getIdreunion() == reunion.getIdreunion()}">
                                                                            <c:if test="${token == '0'}">
                                                                                <button ${AFR.getInasJus() == 0 || AFR.getAsistencia() == 65 ? 'disabled' : ''}  type="submit" class="btn btn-default">Justificado</button>
                                                                                <c:set var="token" value="1" scope="page" />   
                                                                            </c:if>   
                                                                        </c:if>   
                                                                    </c:forEach>   
                                                                </form>
                                                            </td>
                                                        </c:when>    
                                                    </c:choose>
                                                </c:forEach>
                                            </c:when>    
                                            <c:otherwise>
                                            </c:otherwise>
                                        </c:choose>
                                        </tr>
                                    </c:forEach>
                                    </tbody>

                                </c:if> 
                                <a href="${pageContext.servletContext.contextPath}/Reportes/AsistenciaTaller?idtaller=${idTaller}&idgrupo=${reunion.getTurno2().getGrupo().getIdgrupo()}&idturno2=${reunion.getTurno2().getIdturno2()}" class="btn btn-default">Exportar a Excel</a>
                            </table>
                            <c:if test="${listaFormularios.size() == 0}">
                                <h3><strong>Aún no hay personas inscritas</strong></h3>
                            </c:if>
                        </div><!-- /example -->
                        <br>       
                        <div class="col-md-offset-4" id="pageNavPosition"></div>  

                        <script type="text/javascript">
                            var pager = new Pager('mi_tabla', 8);
                            pager.init();
                            pager.showPageNav('pager', 'pageNavPosition');
                            pager.showPage(1);
                        </script>   
                        <br>
                        <p>IMPORTANTE: Una vez guardado, no se podrán hacer cambios</p>
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

            <!-- Ubicar al final -->
    </body>
</html>