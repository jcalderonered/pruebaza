<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
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
                            <li><a href="${pageContext.servletContext.contextPath}/inicioFam">Inicio</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/FactDatos/opc1">Ver Información</a></li>
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
                            <li><a href="${pageContext.servletContext.contextPath}/Festado"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fcontra"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>
                    <div class="col-md-9">
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc1" data-toggle="tab">La Solicitante </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc2" data-toggle="tab">El solicitante</a></li>
                            <!--<li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc3" data-toggle="tab">Composición familiar</a></li>-->
                            <!-- <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc4" data-toggle="tab">Vivienda</a></li> -->
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc5" data-toggle="tab">Antecedentes del niño, niña o adolescente</a></li>
                        </ul>
                        <form class="form-horizontal"> 
                            <fieldset>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Vivienda</label>
                                    <div class="controls">
                                        <input id="propvivienda" value="${ifa.getPropiedadVivienda()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Tipo</label>
                                    <div class="controls">
                                        <input id="propvivienda" value="${ifa.getTipoVivienda()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <h3><strong>Domicilio</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Dirección</label>
                                    <div class="controls">
                                        <textarea id="domicilio" class="input-xlarge" name="message" placeholder="" rows="3" disabled>${ifa.getDomicilio()}</textarea>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Departamento de Residencia</label>
                                    <div class="controls">
                                        <input id="full-name" value="${ifa.getDepRes()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">País de Residencia</label>
                                    <div class="controls">
                                        <input id="full-name" value="${ifa.getPaisRes()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <!--
                            <br>
                            <div class="control-group">
                                <label class="control-label">Teléfono</label>
                                <div class="controls">
                                    <input id="full-name" name="full-name" type="text" class="input-xlarge" disabled>
                                </div>
                            </div>
                                -->
                                <br>
                                <br>
                                <h3><strong>Área de vivienda(en metros cuadrados)</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Total</label>
                                    <div class="controls">
                                        <input id="full-name" value="${ifa.getAreaVivTotal()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Construida</label>
                                    <div class="controls">
                                        <input id="full-name" value="${ifa.getAreaVivConst()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Distribución de la vivienda</label>
                                    <div class="controls">
                                        <textarea id="domicilio" class="input-xlarge" name="message" placeholder="" rows="3" disabled>${ifa.getDistVivienda()}</textarea>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <h3><strong>Servicios</strong></h3>
                                <br>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="checkbox">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${ifa.getLuz() == 1}">
                                                        <input type="checkbox" name="optionsRadios" id="optionsRadios1" value="D" checked>Energía Eléctrica</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="checkbox" name="optionsRadios" id="optionsRadios1" value="D">Energía Eléctrica</label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">    
                                        <div class="checkbox">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${ifa.getAgua() == 1}">
                                                        <input type="checkbox" name="optionsRadios" id="optionsRadios1" value="D" checked>Agua Potable</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="checkbox" name="optionsRadios" id="optionsRadios1" value="D" >Agua Potable</label>
                                                </c:otherwise>
                                            </c:choose> 
                                        </div>
                                    </div>
                                    <div class="col-md-3">    
                                        <div class="checkbox">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${ifa.getDesague() == 1}">
                                                        <input type="checkbox" name="optionsRadios" id="optionsRadios1" value="D" checked>Desague</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="checkbox" name="optionsRadios" id="optionsRadios1" value="D" >Desague</label>
                                                </c:otherwise>
                                            </c:choose>  
                                        </div>
                                    </div>
                                    <div class="col-md-3">   
                                        <div class="control-group">
                                            <label class="control-label">Otros servicios</label>
                                            <div class="controls">
                                                <input id="full-name" value="${ifa.getOtrosServ()}" name="full-name" type="text" class="input-xlarge" disabled>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Material de construcción</label>
                                    <div class="controls">
                                        <input id="full-name" value="${ifa.getMaterConst()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Paredes</label>
                                    <div class="controls">
                                        <input id="full-name" value="${ifa.getPared()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Techo</label>
                                    <div class="controls">
                                        <input id="full-name" value="${ifa.getTecho()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Piso</label>
                                    <div class="controls">
                                        <input id="full-name" value="${ifa.getPiso()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <p style="color: red">IMPORTANTE: SI DESEA REALIZAR ALGÚN CAMBIO DEBERÁ PRESENTAR UNA SOLICITUD</p>
                                <p style="color: red">EN MESA DE PARTES DE LA DGA</p>
                                <br>
                                <p>Según lo establecido en la Ley Nº29733 - Ley de protección de datos personales, la información proporcionada por 
                                    usted/ustedes será de uso exclusivo, con la reserva y confidencialidad del caso, para los fines concernientes al 
                                    procedimiento administrativo de adopción.
                                </p>
                                </div>
                                <br>
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
    </body>
</html>