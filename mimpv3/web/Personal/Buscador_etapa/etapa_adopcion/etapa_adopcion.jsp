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
                                <%if (u.getRol().equals("DCRI") || u.getRol().equals("DGA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/inf"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Sesiones/talleres</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNAs</a></li>
                                <%if (u.getRol().equals("admin")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/juzgado"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Juzgado</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ua"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UAs</a></li>
                                <%}
                                if (!u.getRol().equals("DAPA") && !u.getRol().equals("MATCH")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/famint"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                                <%}
                                if (!u.getRol().equals("mpartes")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de Registros</a></li>
                                <%if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/usuarios"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                                <%}
                                if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Organismo Acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Autoridad Central</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-6 ">
                        <h1 align="center"><strong>Buscador de Registro por Etapa</strong></h1>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li ><a href="${pageContext.servletContext.contextPath}/fametap">Preparación</a></li>
                            <li ><a href="${pageContext.servletContext.contextPath}/EtapaEvalNac" >Evaluación</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ListaEspera" >Lista Espera</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/EtapaDesig" >Designación</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/EtapaAdopcion" >Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Reevaluación" >Reevaluación</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/EtapaPostAdopcion" >Post Adopción</a></li>
                        </ul>
                                <br>
                                <div class="bs-example">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Expediente</th>
                                                <th>Info</th>
                                                <th>N° Propuesta</th>
                                                <th>Prioridad</th>
                                                <th>Info NNA</th>
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
                                        <tbody>
                                            <c:forEach var="adopcion" items="${listaAdopciones}" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${token != adopcion.getNna().getIdnna()}">
                                                        <c:set var="token" value="${adopcion.getNna().getIdnna()}"/>
                                                        <tr>
                                                            <td>${adopcion.getExpedienteFamilia().getExpediente()}</td>
                                                            <td>
                                                              <form action="${pageContext.servletContext.contextPath}/IrPersonalFamilia" method="post">
                                                                    <input hidden name="estado" id="estado" value="adopcion">
                                                                    <input hidden name="idExpediente" id="idExpediente" value="${adopcion.getExpedienteFamilia().getIdexpedienteFamilia()}">
                                                                    <button type="submit" class="btn btn-default">Ver</button>
                                                              </form>
                                                            </td>
                                                            <c:set var="numFilas" value="0"/>
                                                                <c:forEach var="adopcion2" items="${listaAdopciones}" varStatus="status2">
                                                                    <c:if test="${adopcion.getNDesignacion() == adopcion2.getNDesignacion()}">
                                                                        <c:set var="numFilas" value="${numFilas + 1}"/>
                                                                    </c:if>
                                                                </c:forEach>
                                                            <td rowspan="${numFilas}" style="vertical-align: middle;"> 
                                                                ${adopcion.getNDesignacion()}
                                                            </td>
                                                            <td>${adopcion.getPrioridad()}</td>
                                                            <td rowspan="${numFilas}" style="vertical-align: middle;"> 
                                                                 <form action="${pageContext.servletContext.contextPath}/editarNna" method="post">
                                                                    <input hidden name="idNna" id="idNna" value="${adopcion.getNna().getIdnna()}">
                                                                    <h3><strong>${adopcion.getNna().getNombre()} ${adopcion.getNna().getApellidoP()}</strong></h3>
                                                                    <button type="submit" class="btn btn-default">Ver</button>
                                                                 </form>   
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
                                                                     <button type="submit" class="btn btn-default">Registrar</button>
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
                                                                     <button ${empatia == null ? 'disabled' : ''} type="submit" class="btn btn-default">Registrar</button>
                                                                </form> 
                                                            </td>
                                                            <td>
                                                                <form action="${pageContext.servletContext.contextPath}/evalInforme" method="post">
                                                                     <input hidden name="idExpediente" id="idExpediente" value="${expediente.getIdexpedienteFamilia()}">
                                                                     <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                                     <input hidden name="idInforme" id="idInforme" value="${informe.getIdevaluacion()}">
                                                                     <button ${tokenRes == 'sinefecto' ? 'disabled' : ''} ${tokenRes == null ? 'disabled' : ''} ${empatia == null ? 'disabled' : ''} ${empatia != null && empatia.getResultado() == 'desfavorable' ? 'disabled' : ''} type="submit" class="btn btn-default">Registrar</button>
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
                                                                     <input hidden name="idEmpatia" id="idEmpatia" value="${empatia.getIdevaluacion()}">
                                                                     <button ${informe == null ? 'disabled' : ''} type="submit" class="btn btn-default">Registrar</button>
                                                                </form> 
                                                            </td>
                                                        </tr>   
                                                    </c:when>
                                                    <c:otherwise>
                                                         <tr>
                                                             <td>${adopcion.getExpedienteFamilia().getExpediente()}</td>
                                                             <td><button href="#" class="btn btn-default">Ver</button></td>
                                                             <td>${adopcion.getPrioridad()}</td>
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
                                                                     <button type="submit" class="btn btn-default">Registrar</button>
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
                                                                     <button ${empatia2 == null ? 'disabled' : ''} type="submit" class="btn btn-default">Registrar</button>
                                                                </form> 
                                                            </td>
                                                            <td>
                                                                <form action="${pageContext.servletContext.contextPath}/evalInforme" method="post">
                                                                     <input hidden name="idExpediente" id="idExpediente" value="${expediente.getIdexpedienteFamilia()}">
                                                                     <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                                     <input hidden name="idInforme" id="idInforme" value="${informe2.getIdevaluacion()}">
                                                                     <button ${tokenRes2 == null ? 'disabled' : ''} ${tokenRes2 == 'sinefecto' ? 'disabled' : ''} ${empatia2 == null ? 'disabled' : ''} ${empatia2 != null && empatia2.getResultado() == 'desfavorable' ? 'disabled' : ''} type="submit" class="btn btn-default">Registrar</button>
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
                                                                     <input hidden name="idEmpatia" id="idEmpatia" value="${empatia2.getIdevaluacion()}">
                                                                     <button ${informe2 == null ? 'disabled' : ''} type="submit" class="btn btn-default">Registrar</button>
                                                                </form> 
                                                            </td>
                                                         </tr>  
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