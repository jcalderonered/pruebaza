<%-- 
    Document   : lista_charlas
    Created on : 28/10/2013, 03:17:53 PM
    Author     : Ayner Pérez
--%>&

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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
        <!-- Datepicker -->
        <link href="${pageContext.servletContext.contextPath}/assets/css/datepicker3.css" rel="stylesheet">
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
                                <%if (u.getRol().equals("DCRI") || u.getRol().equals("DGA") || u.getRol().equals("admin")) {%>
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
                            <%if (!u.getRol().equals("DEIA Prio")) {%>
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
                        <br>
                        <div class="row">
                            <form action="${pageContext.servletContext.contextPath}/PersonalFiltrarSesionTalleres" method="post">  
                                <div class="col-md-4">
                                    <br>
                                    <h2><strong>Filtrar por año</strong></h2>
                                    <br>
                                    <div class="control-group">
                                        <div class="controls">
                                            <label class="control-label">Seleccionar año</label>
                                            <input id="year" name="year" type="text" class="datepicker" value="${year}">
                                        </div>
                                    </div>
                                    <br>    
                                    <button type="submit" class="btn btn-default">filtrar</button>
                                </div> 
                            </form> 
                        </div>
                        <br>
                        <h1>Listado de sesiones</h1>
                        <br>
                        <div class="table-responsive">
                            <table id="tabla_sesion" class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>Número de sesión</th>
                                        <th>Año de creación</th>
                                        <th>Detalles</th>
                                        <th>Ver Inscritos</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${!listaSesiones.isEmpty()}">  
                                        <c:forEach var="sesion" items="${listaSesiones}" varStatus="status">
                                            <c:choose>
                                                <c:when test="${sesion.getFecha() != null}">
                                                    <c:set var="fechaSesion" value="${sesion.getFecha()}" /> 
                                                    <fmt:formatDate var="yearSesion" value="${fechaSesion}" pattern="y" />  
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="yearSesion" value="0" /> 
                                                </c:otherwise>
                                            </c:choose>
                                            <c:if test="${year != null && year == yearSesion}">
                                                <tr>
                                                    <td>${sesion.getNSesion()}</td> 
                                                    <td>
                                                        ${yearSesion}    
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/PersonalEditarSesion" method="post">
                                                            <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                                            <button type="submit" class="btn btn-default">Ver</button>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/PersonalInscritosSesion" method="post">
                                                            <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                                            <button type="submit" class="btn btn-default">Inscritos</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:if> 
                                            <c:if test="${year == null}">
                                                <tr>
                                                    <td>${sesion.getNSesion()}</td> 
                                                    <td>
                                                        ${yearSesion} 
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/PersonalEditarSesion" method="post">
                                                            <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                                            <button type="submit" class="btn btn-default">Ver</button>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/PersonalInscritosSesion" method="post">
                                                            <input hidden name="idSesion" id="idSesion" value="${sesion.getIdsesion()}">
                                                            <button type="submit" class="btn btn-default">Inscritos</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:if> 
                                        </c:forEach>
                                    </c:if>     
                                </tbody>
                            </table>
                        </div>
                        <br>       
                        <div class="col-md-offset-4" id="pageNavPosition"></div>  

                        <script type="text/javascript"> 
                                var pager = new Pager('tabla_sesion', 8);  
                            pager.init();
                            pager.showPageNav('pager', 'pageNavPosition');
                            pager.showPage(1);
                        </script>   
                        <br>
                        <br>
                        <h1>Listado de talleres</h1>
                        <br>
                        <div class="table-responsive">
                            <table id="tabla_taller" class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>Tipo taller</th>
                                        <th>Nombre</th>
                                        <th>N° de reuniones</th>
                                        <th>Año de creación</th>
                                        <th>Asistencia/Ver inscritos</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:if test="${!listaTalleres.isEmpty()}">   
                                        <c:forEach var="taller" items="${listaTalleres}" varStatus="status">
                                            <c:set var="mostrar" value="0" ></c:set>  
                                            <c:if test="${!taller.getGrupos().isEmpty()}">  
                                                <c:forEach var="grupo" items="${taller.getGrupos()}" varStatus="status">
                                                    <c:if test="${!grupo.getTurno2s().isEmpty()}">
                                                        <c:forEach var="turno2" items="${grupo.getTurno2s()}" varStatus="status">
                                                            <c:if test="${!turno2.getReunions().isEmpty()}">
                                                                <c:forEach var="reunion" items="${turno2.getReunions()}" varStatus="status">
                                                                    <c:if test="${ reunion.getFecha() != null}">
                                                                        <c:set var="fechaReunion" value="${reunion.getFecha()}" /> 
                                                                        <fmt:formatDate var="yearReunion" value="${fechaReunion}" pattern="y" />  
                                                                        <c:set var="mostrar" value="1" ></c:set>  
                                                                    </c:if>
                                                                </c:forEach>
                                                            </c:if>  
                                                        </c:forEach>
                                                    </c:if>  
                                                </c:forEach>
                                            </c:if>  
                                            <c:if test="${mostrar == '0'}">
                                                <c:set var="yearReunion" value="0" ></c:set> 
                                            </c:if>
                                            <c:if test="${year != null && year == yearReunion}">
                                            <tr>
                                                <td>${taller.getTipoTaller()}</td>
                                                <td>${taller.getNombre()}</td> 
                                                <td>${taller.getNReunion()}</td> 
                                                <td>
                                                    ${yearReunion}
                                                </td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/PersonalInscritosTallerInicio" method="post">
                                                        <input hidden name="idTaller" id="idTaller" value="${taller.getIdtaller()}">
                                                        <input hidden name="nombreTaller" id="nombreTaller" value="${taller.getNombre()}">
                                                        <input hidden name="historial" id="historial" value="true">
                                                        <button type="submit" class="btn btn-default">Detalles</button>
                                                    </form>
                                                </td>
                                            </tr>
                                            </c:if>
                                            <c:if test="${year == null}">
                                            <tr>
                                                <td>${taller.getTipoTaller()}</td>
                                                <td>${taller.getNombre()}</td> 
                                                <td>${taller.getNReunion()}</td> 
                                                <td>
                                                    ${yearReunion}
                                                </td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/PersonalInscritosTallerInicio" method="post">
                                                        <input hidden name="idTaller" id="idTaller" value="${taller.getIdtaller()}">
                                                        <input hidden name="nombreTaller" id="nombreTaller" value="${taller.getNombre()}">
                                                        <input hidden name="historial" id="historial" value="true">
                                                        <button type="submit" class="btn btn-default">Detalles</button>
                                                    </form>
                                                </td>
                                            </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>   
                                </tbody>
                            </table>
                        </div>
                        <div class="col-md-offset-4" id="pageNavPosition2"></div>  

                        <script type="text/javascript"> 
                                var pager = new Pager('tabla_taller', 8);  
                            pager.init();
                            pager.showPageNav('pager', 'pageNavPosition2');
                            pager.showPage(1);
                        </script>   
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
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
            <script type="text/javascript">

                $('.datepicker').datepicker({
                    format: "yyyy",
                    startView: 1,
                    minViewMode: 2,
                    autoclose: true
                });
            </script>


            <!-- Ubicar al final -->
    </body>
</html>
