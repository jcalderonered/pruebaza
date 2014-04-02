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
                                <%if (u.getRol().equals("DAPA") || u.getRol().equals("DCRI")|| u.getRol().equals("DEIA Prio")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-6 ">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inicioper'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Buscador de Registro por Etapa</strong></h1>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li ><a href="${pageContext.servletContext.contextPath}/fametap">Preparación</a></li>
                            <li ><a href="${pageContext.servletContext.contextPath}/EtapaEvalNac">Evaluación</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ListaEspera">Lista Espera</a></li>
                            <li ${usuario.getRol().equals("UA") ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/EtapaDesig">Propuesta de Designación</a></li>
                            <li ${usuario.getRol().equals("UA") ? 'class="hidden"' : ''} class="active"><a href="${pageContext.servletContext.contextPath}/EtapaAdopcion" >Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Reevaluacion" >Reevaluación</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/EtapaPostAdopcion" >Post Adopción</a></li>
                        </ul>
                        <br>
                        <p align="right">Filtrar: <input id="filtrar" type="text" /></p>
                        <br>
                        <div class="bs-example">
                            <table class="table table-bordered" id="mi_tabla">
                                <thead>
                                    <tr>
                                        <th>Expediente</th>
                                        <th>Info</th>
                                        <th>N° Propuesta</th>
                                        <th>Prioridad</th>
                                        <th>Nombres del NNA</th>
                                        <th>Informe de Empatía</th>
                                        <th>Resultado</th>
                                        <th>Resolución</th>
                                        <th>Informe Integración</th>
                                        <th>Resultado</th>
                                        <th>Resolución</th>  
                                    </tr>
                                </thead>
                                <c:if test="${!listaAdopciones.isEmpty()}">
                                    <c:set var="token" value="0"/>
                                    <c:set var="numDesig" value="${null}"/>
                                    <tbody>
                                        <c:forEach var="adopcion" items="${listaAdopciones}" varStatus="status">
                                            <c:set var="perteneceUA" value="1"/>
                                            <c:if test="${usuario.getUnidad().getDepartamento() == adopcion.getExpedienteFamilia().getUnidad().getDepartamento()
                                                          || usuario.getUnidad().getDepartamento() == 'Lima'}">
                                                <c:set var="perteneceUA" value="0"/>
                                            </c:if>  
                                            <c:choose>
                                                <c:when test="${token != adopcion.getNna().getIdnna() && numDesig != adopcion.getNDesignacion()}">
                                                    <c:set var="token" value="${adopcion.getNna().getIdnna()}"/>
                                                    <c:set var="numDesig" value="${adopcion.getNDesignacion()}"/>
                                                    <tr>
                                                        <td>${adopcion.getExpedienteFamilia().getExpediente()}</td>
                                                        <td>
                                                            <form action="${pageContext.servletContext.contextPath}/IrPersonalFamilia" method="post">
                                                                <input hidden name="estado" id="estado" value="adopcion">
                                                                <input hidden name="idExpediente" id="idExpediente" value="${adopcion.getExpedienteFamilia().getIdexpedienteFamilia()}">
                                                                <input hidden name="volver" id="volver" value="${volver}">
                                                                <button ${perteneceUA == 0 ? '' : 'disabled'} type="submit" class="btn btn-default">Ver</button>
                                                            </form>
                                                        </td>
                                                        <c:set var="numFilas" value="0"/>
                                                        <c:forEach var="adopcion2" items="${listaAdopciones}" varStatus="status">
                                                            <c:choose>
                                                                <c:when test="${adopcion.getTipoPropuesta() != 'directa'
                                                                              && adopcion.getNDesignacion() == adopcion2.getNDesignacion()}">
                                                                    <c:set var="numFilas" value="${numFilas + 1}"/>
                                                                </c:when>
                                                                <c:when test="${adopcion.getTipoPropuesta() == 'directa'}">
                                                                    <c:set var="numFilas" value="${1}"/>
                                                                </c:when>
                                                            </c:choose>
                                                        </c:forEach>
                                                        <td rowspan="${numFilas}" style="vertical-align: middle;"> 
                                                            ${adopcion.getNDesignacion()}
                                                        </td>
                                                        <td>${adopcion.getPrioridad()}</td>
                                                        <td rowspan="${numFilas}" style="vertical-align: middle;"> 
                                                            <c:forEach var="temp" items="${listaAdopciones}" varStatus="status">
                                                                <c:if test="${adopcion.getNDesignacion() == temp.getNDesignacion()
                                                                              && adopcion.getExpedienteFamilia().getIdexpedienteFamilia() == temp.getExpedienteFamilia().getIdexpedienteFamilia()
                                                                      }">
                                                                    ${temp.getNna().getNombre()}
                                                                    ${temp.getNna().getApellidoP()}
                                                                    <br>
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>
                                                        <c:set var="expediente" value="${adopcion.getExpedienteFamilia()}"/> 
                                                        <c:forEach var="evaluacion" items="${expediente.getEvaluacions()}" >
                                                            <c:choose>
                                                                <c:when test="${evaluacion.getTipo() == 'empatia'}">
                                                                    <c:set var="empatia" value="${evaluacion}" scope="page" />
                                                                    <c:if test="${!empatia.getResolucions().isEmpty()}">
                                                                        <c:forEach var="resol" items="${empatia.getResolucions()}" varStatus="stat" >
                                                                            <c:if test="${stat.first}">
                                                                                <c:set var="tokenRes" value="${resol.getTipo()}"/> 
                                                                            </c:if>
                                                                        </c:forEach>
                                                                    </c:if>
                                                                </c:when>
                                                                <c:when test="${evaluacion.getTipo() == 'informe'}">
                                                                    <c:set var="informe" value="${evaluacion}" scope="page" />
                                                                </c:when> 
                                                            </c:choose>
                                                        </c:forEach>
                                                        <td>
                                                            <form action="${pageContext.servletContext.contextPath}/evalEmpatia" method="post">
                                                                <input hidden name="idExpediente" id="idExpediente" value="${expediente.getIdexpedienteFamilia()}">
                                                                <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                                <input hidden name="idEmpatia" id="idEmpatia" value="${empatia.getIdevaluacion()}">
                                                                <input hidden name="volver" id="volver" value="${volver}">
                                                                <button ${usuario.getUnidad().getDepartamento() == 'Lima' ? '' : 'disabled'} type="submit" class="btn btn-default">Registrar</button>
                                                            </form> 
                                                        </td>

                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${empatia != null}">
                                                                    ${empatia.getResultado()}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    -----
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <form action="${pageContext.servletContext.contextPath}/resolEvalEmpatia" method="post">
                                                                <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                                <input hidden name="idEmpatia" id="idEmpatia" value="${empatia.getIdevaluacion()}">
                                                                <input hidden name="idNna" id="idNna" value="${adopcion.getNna().getIdnna()}">
                                                                <input hidden name="numDesig" id="numDesig" value="${adopcion.getNDesignacion()}">
                                                                <input hidden name="volver" id="volver" value="${volver}">
                                                                <button ${empatia == null || usuario.getUnidad().getDepartamento() != 'Lima' ? 'disabled' : ''} type="submit" class="btn btn-default">Registrar</button>
                                                            </form> 
                                                        </td>
                                                        <td>
                                                            <form action="${pageContext.servletContext.contextPath}/evalInforme" method="post">
                                                                <input hidden name="idExpediente" id="idExpediente" value="${expediente.getIdexpedienteFamilia()}">
                                                                <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                                <input hidden name="idInforme" id="idInforme" value="${informe.getIdevaluacion()}">
                                                                <input hidden name="volver" id="volver" value="${volver}">
                                                                <button ${tokenRes == 'sinefecto' ? 'disabled' : ''} ${tokenRes == null ? 'disabled' : ''} ${empatia == null ? 'disabled' : ''} ${empatia != null && empatia.getResultado() == 'desfavorable' ? 'disabled' : ''} ${usuario.getUnidad().getDepartamento() == 'Lima' ? '' : 'disabled'} type="submit" class="btn btn-default">Registrar</button>
                                                            </form> 
                                                        </td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${informe != null}">
                                                                    ${informe.getResultado()}
                                                                </c:when>
                                                                <c:otherwise>
                                                                    -----
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <form action="${pageContext.servletContext.contextPath}/resolEvalInforme" method="post">
                                                                <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                                <input hidden name="idInforme" id="idInforme" value="${informe.getIdevaluacion()}">
                                                                <input hidden name="idNna" id="idNna" value="${adopcion.getNna().getIdnna()}">
                                                                <input hidden name="numDesig" id="numDesig" value="${adopcion.getNDesignacion()}">
                                                                <input hidden name="idEmpatia" id="idEmpatia" value="${empatia.getIdevaluacion()}">
                                                                <input hidden name="volver" id="volver" value="${volver}">
                                                                <button ${informe == null ? 'disabled' : ''} ${usuario.getUnidad().getDepartamento() == 'Lima' ? '' : 'disabled'} type="submit" class="btn btn-default">Registrar</button>
                                                            </form> 
                                                        </td>
                                                    </tr>   
                                                    <c:set var="empatia" value="${null}" scope="page" />
                                                    <c:set var="informe" value="${null}" scope="page" />
                                                </c:when>
                                                <c:when test="${token != adopcion.getNna().getIdnna() && numDesig == adopcion.getNDesignacion()}">

                                                </c:when>    
                                                <c:otherwise>
                                                    <c:set var="numFilas" value="${numFilas - 1}"/>
                                                    <tr>
                                                        <td>${adopcion.getExpedienteFamilia().getExpediente()}</td>
                                                <input hidden name="volver" id="volver" value="${volver}">
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/IrPersonalFamilia" method="post">
                                                        <input hidden name="estado" id="estado" value="adopcion">
                                                        <input hidden name="idExpediente" id="idExpediente" value="${adopcion.getExpedienteFamilia().getIdexpedienteFamilia()}">
                                                        <input hidden name="volver" id="volver" value="${volver}">
                                                        <button ${perteneceUA == 0 ? '' : 'disabled'} type="submit" class="btn btn-default">Ver</button>
                                                    </form>
                                                </td>
                                                <c:if test="${(status.count mod 8) == 1}"> <!-- mod X donde X es el número de filas por página -->
                                                    <td rowspan="${numFilas}" style="vertical-align: middle;"> 
                                                        ${adopcion.getNDesignacion()}
                                                    </td>  
                                                </c:if>
                                                <td>${adopcion.getPrioridad()}</td>
                                                <c:if test="${(status.count mod 8) == 1}"> <!-- mod X donde X es el número de filas por página -->
                                                    <td rowspan="${numFilas}" style="vertical-align: middle;"> 

                                                    </td> 
                                                </c:if>
                                                <c:set var="expediente" value="${adopcion.getExpedienteFamilia()}"/> 
                                                <c:forEach var="evaluacion2" items="${expediente.getEvaluacions()}" >
                                                    <c:choose>
                                                        <c:when test="${evaluacion2.getTipo() == 'empatia'}">
                                                            <c:set var="empatia2" value="${evaluacion2}" scope="page" />
                                                            <c:if test="${!empatia2.getResolucions().isEmpty()}">
                                                                <c:forEach var="resol2" items="${empatia2.getResolucions()}" >
                                                                    <c:set var="tokenRes2" value="${resol2.getTipo()}"/> 
                                                                </c:forEach>
                                                            </c:if>
                                                        </c:when>
                                                        <c:when test="${evaluacion2.getTipo() == 'informe'}">
                                                            <c:set var="informe2" value="${evaluacion2}" scope="page" />
                                                        </c:when> 
                                                    </c:choose>
                                                </c:forEach>

                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/evalEmpatia" method="post">
                                                        <input hidden name="idExpediente" id="idExpediente" value="${expediente.getIdexpedienteFamilia()}">
                                                        <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                        <input hidden name="idEmpatia" id="idEmpatia" value="${empatia2.getIdevaluacion()}">
                                                        <input hidden name="volver" id="volver" value="${volver}">
                                                        <button ${usuario.getUnidad().getDepartamento() == 'Lima' ? '' : 'disabled'} type="submit" class="btn btn-default">Registrar</button>
                                                    </form> 
                                                </td>

                                                <td>
                                                    <c:choose>
                                                        <c:when test="${empatia2 != null}">
                                                            ${empatia2.getResultado()}
                                                        </c:when>
                                                        <c:otherwise>
                                                            -----
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/resolEvalEmpatia" method="post">
                                                        <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                        <input hidden name="idEmpatia" id="idEmpatia" value="${empatia2.getIdevaluacion()}">
                                                        <input hidden name="idNna" id="idNna" value="${adopcion.getNna().getIdnna()}">
                                                        <input hidden name="numDesig" id="numDesig" value="${adopcion.getNDesignacion()}">
                                                        <input hidden name="volver" id="volver" value="${volver}">
                                                        <button ${usuario.getUnidad().getDepartamento() == 'Lima' ? '' : 'disabled'} ${empatia2 == null ? 'disabled' : ''} type="submit" class="btn btn-default">Registrar</button>
                                                    </form> 
                                                </td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/evalInforme" method="post">
                                                        <input hidden name="idExpediente" id="idExpediente" value="${expediente.getIdexpedienteFamilia()}">
                                                        <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                        <input hidden name="idInforme" id="idInforme" value="${informe2.getIdevaluacion()}">
                                                        <input hidden name="volver" id="volver" value="${volver}">
                                                        <button ${usuario.getUnidad().getDepartamento() == 'Lima' ? '' : 'disabled'} ${tokenRes2 == null ? 'disabled' : ''} ${tokenRes2 == 'sinefecto' ? 'disabled' : ''} ${empatia2 == null ? 'disabled' : ''} ${empatia2 != null && empatia2.getResultado() == 'desfavorable' ? 'disabled' : ''} type="submit" class="btn btn-default">Registrar</button>
                                                    </form> 
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${informe2 != null}">
                                                            ${informe2.getResultado()}
                                                        </c:when>
                                                        <c:otherwise>
                                                            -----
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/resolEvalInforme" method="post">
                                                        <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                        <input hidden name="idInforme" id="idInforme" value="${informe2.getIdevaluacion()}">
                                                        <input hidden name="idNna" id="idNna" value="${adopcion.getNna().getIdnna()}">
                                                        <input hidden name="numDesig" id="numDesig" value="${adopcion.getNDesignacion()}">
                                                        <input hidden name="idEmpatia" id="idEmpatia" value="${empatia2.getIdevaluacion()}">
                                                        <input hidden name="volver" id="volver" value="${volver}">
                                                        <button ${usuario.getUnidad().getDepartamento() == 'Lima' ? '' : 'disabled'} ${informe2 == null ? 'disabled' : ''} type="submit" class="btn btn-default">Registrar</button>
                                                    </form> 
                                                </td>
                                                </tr>  
                                                <c:set var="empatia2" value="${null}" scope="page" />
                                                <c:set var="informe2" value="${null}" scope="page" />
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    </tbody>
                                </c:if>
                                <c:if test="${listaAdopciones.isEmpty()}">
                                    <h3><strong>No existen Familias en Estado de Adopción</strong></h3>
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
                        <br>
                        <!-- Button -->
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
        <!-- Bootstrap core JavaScript
        ================================================== -->
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
        <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
        <script type="text/javascript">

                            $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

        </script>
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
        <!-- Placed at the end of the document so the pages load faster -->        
    </body>
</html>
