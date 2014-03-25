<%-- 
    Document   : inscripcion_sesion1
    Created on : 28/10/2013, 05:45:16 AM
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
                        <p align="right"><button onclick="window.location.href = '${pageContext.servletContext.contextPath}/agregarNna'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Registrar NNA</button></p>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/nna" >NNA Regulares</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/nnaPrioritarios" >NNA Prioritarios</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/nnaSeguimiento" >NNA en Seguimiento</a></li>
                        </ul>
                        <br>
                        <br>
                        <p align="right">Filtrar: <input id="filtrar" type="text" /></p>
                        <br>
                        <h1 align="center"><strong>Lista de NNA's</strong></h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped" id="mi_tabla">
                                <thead>
                                    <tr>
                                        <th class="col-sm-2 ">Nombre</th>
                                        <th class="col-sm-2 ">Apellido Paterno</th>
                                        <th class="col-sm-2 ">Apellido Materno</th>
                                        <th class="col-sm-2 ">Sexo</th>
                                        <th class="col-sm-2 ">Detalles</th> 
                                        <th class="col-sm-2 ">Expediente</th>
                                        <th class="col-sm-2 ">Registrar designación</th>
                                    </tr>
                                </thead>
                                <c:if test="${!listaNna.isEmpty()}">  
                                    <tbody>
                                        <c:forEach var="nna" items="${listaNna}" varStatus="status"> 
                                            <c:if test="${!nna.getExpedienteNnas().isEmpty()}">
                                                <c:forEach var="expediente" items="${nna.getExpedienteNnas()}" varStatus="status">
                                                    <c:set var="unidad" value="${expediente.getUnidad().getIdunidad()}"></c:set>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${nna.getExpedienteNnas().isEmpty()}">
                                                <c:set var="nuevo" value="recienIngresado"></c:set>
                                            </c:if>
                                            <c:if test="${usuario.getUnidad().getIdunidad() == unidad || usuario.getUnidad().getDepartamento() == 'Lima' || nuevo == 'recienIngresado'}">  
                                                <tr>
                                                    <td>${nna.getNombre()}</td>
                                                    <td>${nna.getApellidoP()}</td>
                                                    <td>${nna.getApellidoM()}</td>
                                                    <td>${nna.getSexo()}</td>
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/editarNna" method="post">
                                                            <input hidden name="idNna" id="idNna" value="${nna.getIdnna()}">
                                                            <button type="submit" class="btn btn-default">Ver</button>
                                                        </form>
                                                    </td>        
                                                    <td>
                                                        <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/editarExpedienteNna" method="post">
                                                            <input hidden name="idNna" id="idNna" value="${nna.getIdnna()}">    
                                                            <button ${nna.getExpedienteNnas().isEmpty() == true ? 'disabled' : ''} class="btn btn-default">Ver</button>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <c:set var="tokenAdopcion" value="2" ></c:set>
                                                        <c:if test="${!nna.getDesignacions().isEmpty()}">
                                                            <c:forEach var="designacion" items="${nna.getDesignacions()}" varStatus="status">
                                                                <c:choose>
                                                                    <c:when test="${designacion.getAceptacionConsejo() == 0 || designacion.getAceptacionConsejo() == 1 || designacion.getAceptacionConsejo() == 2}">

                                                                        <c:set var="tokenAdopcion" value="0" ></c:set>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <c:if test="${tokenAdopcion != '0'}">
                                                                            <c:set var="tokenAdopcion" value="1" ></c:set>
                                                                        </c:if>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>      
                                                        </c:if>  
                                                        <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/registrarDesignacion" method="post">
                                                            <input hidden name="idNna" id="idNna" value="${nna.getIdnna()}">    
                                                            <button ${tokenAdopcion == '0' || nna.getExpedienteNnas().isEmpty() == true || usuario.getUnidad().getDepartamento() != 'Lima' ? 'disabled' : '' } class="btn btn-default">Registrar</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>  

                                    </tbody>
                                </c:if> 
                                <c:if test="${listaNna.isEmpty()}">
                                    <h3><strong>No existen Nna en esta clasificación</strong></h3>
                                </c:if>  
                            </table>
                        </div>
                        <br>       
                        <div class="col-md-offset-4" id="pageNavPosition"></div>  

                        <script type="text/javascript">
                            var pager = new Pager('mi_tabla', 8);
                            pager.init();
                            pager.showPageNav('pager', 'pageNavPosition');
                            pager.showPage(1);
                        </script>   
                    </div>
                </div>
            </div>
            <!--FIN DE CONTENIDO-->
            <br>
            <br>
        </div>   
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

                            function tablefilter(table_selector, input_selector, search_level, colspan) {

                                var table = $(table_selector);
                                if (table.length == 0)
                                    return;

                                var input = $(input_selector);
                                if (input.length == 0)
                                    return;

                                if (search_level == "undefined" || search_level < 1)
                                    search_level = 3;

                                if (colspan == "undefined" || colspan < 0)
                                    colspan = 2;

                                $(input).val("Buscar…");

                                $(input).focus(function() {
                                    if ($(this).val() == "Buscar…") {
                                        $(this).val("");
                                    }
                                    $(this).select();
                                });

                                $(input).blur(function() {
                                    if ($(this).val() == "") {
                                        $(this).val("Buscar…");
                                    }
                                });

                                $(input).keyup(function() {
                                    if ($(this).val().length >= search_level) {
                                        // Ocultamos las filas que no contienen el contenido del edit.
                                        $(table).find("tbody tr").not(":contains(\"" + $(this).val() + "\")").hide();

                                        // Si no hay resultados, lo indicamos.
                                        if ($(table).find("tbody tr:visible").length == 0) {
                                            $(table).find("tbody:first").append('<tr id="noresults" class="aligncenter"><td colspan="' + colspan + '">Lo siento pero no hay resultados para la búsqueda indicada.</td></tr>');
                                        }
                                    } else {
                                        // Borramos la fila de que no hay resultados.
                                        $(table).find("tbody tr#noresults").remove();

                                        // Mostramos todas las filas.
                                        $(table).find("tbody tr").show();
                                    }
                                });
                            }

                            jQuery.expr[':'].contains = function(a, i, m) {
                                return jQuery(a).text().toUpperCase().indexOf(m[3].toUpperCase()) >= 0;
                            };

                            $(document).ready(function() {
                                tablefilter("table#mi_tabla", "input#filtrar", 2, 2);
                            });

        </script>

        <!-- Ubicar al final -->
    </body>
</html>