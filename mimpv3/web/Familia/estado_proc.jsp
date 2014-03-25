<%-- 
    Document   : estado_proc
    Created on : 7/11/2013, 10:31:03 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Familia"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    
    response.setDateHeader("Expires", 0);
    Familia u = (Familia) request.getSession().getAttribute("usuario");
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
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/inicioFam">Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc1">Ver Información</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/salir">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div class="container">
                <div class="row">
                    <div class="col-md-3 ">
                        <ul class="nav nav-list well">
                            <li><a href="${pageContext.servletContext.contextPath}/inicioFam"><span class="glyphicon glyphicon-home"></span>Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Finscripcion"><span class="glyphicon glyphicon-chevron-right"></span> Inscripción a talleres</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/Festado"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fcontra"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>
                    <div class="col-md-8 col-md-offset-1">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inicioFam'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <!-- AQUI AGREGAR EL CONTENIDO QUE ESTARA AL COSTADO DEL MENU -->
                        <br>
                        <h3><strong>Etapas del proceso por las que ha pasado la familia</strong></h3>
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>Asistencia a sesion informativa</strong></h3>
                            </div> 
                            <c:choose>
                                <c:when test="${sesion == 'SI'}">
                                    <div class="col-md-3 has-success">
                                        <label class="control-label" > ${sesion} </label>
                                    </div> 
                                </c:when>
                                <c:otherwise>
                                    <div class="col-md-3 has-error">
                                        <label class="control-label" > ${sesion} </label>
                                    </div> 
                                </c:otherwise>
                            </c:choose>
                            <div class="col-md-3">

                            </div> 
                        </div> 
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>Completó talleres</strong></h3>
                            </div>
                            <c:choose>
                                <c:when test="${taller == 'SI'}">
                                    <div class="col-md-3 has-success">
                                        <label class="control-label" > ${taller} </label>
                                    </div> 
                                </c:when>
                                <c:otherwise>
                                    <div class="col-md-3 has-error">
                                        <label class="control-label" > ${taller} </label>
                                    </div> 
                                </c:otherwise>
                            </c:choose>
                            <div class="col-md-3">

                            </div> 
                        </div>  
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>Presentó ficha de inscripción para solicitantes de adopción</strong></h3>
                            </div> 
                            <c:choose>
                                <c:when test="${ficha == 'SI'}">
                                    <div class="col-md-1 has-success">
                                        <label class="control-label" > ${ficha} </label>
                                    </div> 
                                </c:when>
                                <c:otherwise>
                                    <div class="col-md-1 has-error">
                                        <label class="control-label" > ${ficha} </label>
                                    </div> 
                                </c:otherwise>
                            </c:choose>
                            <div class="col-md-3">
                                <!--
                                <c:choose>
                                    <c:when test="${boton == 1}">
                                        <button onclick="location.href = 'http://www.mimp.gob.pe/files/direcciones/dga/FichaInscripcion_Para_Solicitantes_Adopcion.pdf'" id="singlebutton" name="singlebutton" class="btn btn-default">Descargar ficha</button> 
                                    </c:when>
                                    <c:otherwise>
                                        <button id="singlebutton" name="singlebutton" class="btn btn-default" disabled>Llenar ficha</button>
                                    </c:otherwise>
                                </c:choose>
                                -->
                                <button onclick="window.open('http://www.mimp.gob.pe/files/direcciones/dga/FichaInscripcion_Para_Solicitantes_Adopcion.pdf')" id="singlebutton" name="singlebutton" class="btn btn-default">Descargar ficha</button> 
                            </div> 
                        </div>  
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>Completó etapa de evaluación</strong></h3>
                            </div> 
                            <c:choose>
                                <c:when test="${eval == 'SI'}">
                            <div class="col-md-3 has-success">
                                <label class="control-label" > ${eval} </label>
                            </div> 
                                </c:when>
                                <c:otherwise>
                                    <div class="col-md-3 has-error">
                                <label class="control-label" > ${eval} </label>
                            </div> 
                                </c:otherwise>
                            </c:choose>
                            <div class="col-md-3">

                            </div> 
                        </div>    
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>En lista de espera</strong></h3>
                            </div> 
                             <c:choose>
                                <c:when test="${espera == 'SI'}">
                            <div class="col-md-3 has-success">
                                <label class="control-label" > ${espera} </label>
                            </div> 
                                </c:when>
                                <c:otherwise>
                                    <div class="col-md-3 has-error">
                                <label class="control-label" > ${espera} </label>
                            </div> 
                                </c:otherwise>
                            </c:choose>
                            <div class="col-md-3">

                            </div> 
                        </div>   
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>En proceso de adopción</strong></h3>
                            </div> 
                             <c:choose>
                                <c:when test="${adop == 'SI'}">
                            <div class="col-md-3 has-success">
                                <label class="control-label" > ${adop} </label>
                            </div> 
                                </c:when>
                                <c:otherwise>
                                    <div class="col-md-3 has-error">
                                <label class="control-label" > ${adop} </label>
                            </div> 
                                </c:otherwise>
                            </c:choose>
                            <div class="col-md-3">

                            </div> 
                        </div> 
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>En etapa post-adopción</strong></h3>
                            </div> 
                             <c:choose>
                                <c:when test="${postadop == 'SI'}">
                            <div class="col-md-3 has-success">
                                <label class="control-label" > ${postadop} </label>
                            </div> 
                                </c:when>
                                <c:otherwise>
                                    <div class="col-md-3 has-error">
                                <label class="control-label" > ${postadop} </label>
                            </div> 
                                </c:otherwise>
                            </c:choose>
                            <div class="col-md-3">
                            </div> 
                        </div>  
                        <br>
                        <br>
                    </div>
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