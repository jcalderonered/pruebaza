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
                                if (u.getRol().equals("admin")) {%>
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
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/EtapaEvalNac" >Evaluación</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ListaEspera" >Lista Espera</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/EtapaDesig" >Designación</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/EtapaAdopcion" >Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Reevaluación" >Reevaluación</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/EtapaPostAdopcion" >Post Adopción</a></li>
                        </ul>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/EtapaEvalNac" >Familias Nacionales</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/EtapaEvalInter" >Familias Internacionales</a></li>
                        </ul>    
                            
                        
                                <br>
                                <div class="bs-example">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Expediente</th>
                                                <th>Información</th>
                                                <th>Evaluación Psicológica</th>
                                                <th>Resultado</th>
                                                <th>Evaluación Social</th>
                                                <th>Resultado</th>
                                                <th>Evaluación Legal</th>
                                                <th>Resultado</th>
                                                <th>Fecha de Resolución</th>
                                                <th>Tipo de Resolución</th>
                                            </tr>
                                        </thead>
                                       <c:if test="${ListaExpedientes != null}">   
                                        <tbody>
                                            <c:forEach var="expediente" items="${ListaExpedientes}" varStatus="status">
                                                <c:forEach var="evaluacion" items="${expediente.getEvaluacions()}" >
                                                            <c:choose>
                                                                <c:when test="${evaluacion.getTipo() == 'psicologica'}">
                                                                    <c:set var="psicologica" value="${evaluacion}" scope="page" />
                                                                </c:when>
                                                                <c:when test="${evaluacion.getTipo() == 'social'}">
                                                                    <c:set var="social" value="${evaluacion}" scope="page" />
                                                                </c:when> 
                                                                <c:when test="${evaluacion.getTipo() == 'legal'}">
                                                                    <c:set var="legal" value="${evaluacion}" scope="page" />
                                                                </c:when> 
                                                            </c:choose>
                                                </c:forEach>
                                                <tr>
                                                <td>${expediente.getExpediente()}</td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/IrPersonalFamilia" method="post">
                                                        <input hidden name="estado" id="estado" value="evaluacion">
                                                        <input hidden name="idExpediente" id="idExpediente" value="${expediente.getIdexpedienteFamilia()}">
                                                        <button type="submit" class="btn btn-default">Ver</button>
                                                    </form>
                                                </td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/PersonalAgregarEvalPsicologica" method="post">
                                                          <input hidden name="idExpediente" id="idExpediente" value="${expediente.getIdexpedienteFamilia()}">
                                                          <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                          <input hidden name="idPsicologica" id="idPsicologica" value="${psicologica.getIdevaluacion()}">
                                                          <input hidden name="origen" id="origen" value="nacional">
                                                          <button type="submit" class="btn btn-default">Registrar</button>
                                                    </form>  
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${psicologica != null}">
                                                            ${psicologica.getResultado()}
                                                        </c:when>
                                                        <c:otherwise>
                                                            Pendiente
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/PersonalAgregarEvalSocial" method="post">
                                                          <input hidden name="idExpediente" id="idExpediente" value="${expediente.getIdexpedienteFamilia()}">
                                                          <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                          <input hidden name="idSocial" id="idSocial" value="${social.getIdevaluacion()}">
                                                          <input hidden name="origen" id="origen" value="nacional">
                                                          <button type="submit" class="btn btn-default">Registrar</button>
                                                    </form>  
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${social != null}">
                                                            ${social.getResultado()}
                                                        </c:when>
                                                        <c:otherwise>
                                                            Pendiente
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/PersonalAgregarEvalLegal" method="post">
                                                          <input hidden name="idExpediente" id="idExpediente" value="${expediente.getIdexpedienteFamilia()}">
                                                          <input hidden name="familia" id="familia" value="${expediente.getExpediente()}">
                                                          <input hidden name="idLegal" id="idLegal" value="${legal.getIdevaluacion()}">
                                                          <input hidden name="origen" id="origen" value="nacional">
                                                          <button type="submit" class="btn btn-default">Registrar</button>
                                                    </form>  
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${legal != null}">
                                                            ${legal.getResultado()}
                                                        </c:when>
                                                        <c:otherwise>
                                                            Pendiente
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <c:if test="${!legal.getResolucions().isEmpty()}">
                                                <c:forEach var="resolucion" items="${legal.getResolucions()}" varStatus="status">
                                                     <c:choose>
                                                        <c:when test="${status.index == 0}">
                                                            <td>
                                                            ${df.dateToString(resolucion.getFechaResol())}
                                                            </td>
                                                            <td>
                                                            ${resolucion.getTipo()}
                                                            </td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                               </c:if>
                                               <c:if test="${legal == null || legal.getResolucions().isEmpty()}">
                                                   <td>
                                                       Pendiente
                                                   </td>
                                                   <td>
                                                       Pendiente
                                                   </td>
                                               </c:if>
                                             </tr>
                                            </c:forEach>
                                    </tbody>
                                </c:if> 
                                 <c:if test="${ListaExpedientes == null}">
                                    <h3><strong>No existen familias en esta etapa</strong></h3>
                                </c:if> 
                                    </table>
                                  
                                </div>
                           
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