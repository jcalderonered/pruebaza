<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Entidad"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
    Entidad u = (Entidad) request.getSession().getAttribute("usuario");
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
                            <span class="icon-bar"></span>
                        </button>

                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-left">
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/inicioEnt">Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/salir">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-3 ">
                        <ul class="nav nav-list well">
                            <li><a href="${pageContext.servletContext.contextPath}/inicioEnt"><span class="glyphicon glyphicon-home"></span> Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/listaFam"><span class="glyphicon glyphicon-chevron-right"></span> Listado de familias</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/contraEnt"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>

                    <div class="col-md-8 col-md-offset-1">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/listaFam'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <br>
                        <h1 align="center"><strong>Familia "${ElAdop.getApellidoP()} - ${LaAdop.getApellidoP()}"</strong></h1>
                        <br>
                        <br>
                        <h3 align="left"><strong>Datos del Expediente</strong></h3>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li class="active"><a href="#" data-toggle="tab">La Solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ElAdop?idInfo=${LaAdop.getInfoFamilia().getIdinfoFamilia()}"  data-toggle="tab">El solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/infoNNA?idInfo=${LaAdop.getInfoFamilia().getIdinfoFamilia()}" data-toggle="tab">Antecedentes del NNA</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/infoExp?idInfo=${LaAdop.getInfoFamilia().getIdinfoFamilia()}" data-toggle="tab">Información del Expediente</a></li>

                        </ul>
                        <br>
                        <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                        <fieldset>
                            <br>
                            <h3><strong>Generales</strong></h3>
                            <br>
                            <!-- Text input-->
                            <div class="control-group">
                                <label class="control-label">Nombre</label>
                                <div class="controls">
                                    <input disabled id="nombre" name="full-name" type="text" class="input-xlarge" value ="${LaAdop.getNombre()}">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Apellido Paterno</label>
                                <div class="controls">
                                    <input disabled id="apellido_p" name="full-name" type="text" class="input-xlarge" value ="${LaAdop.getApellidoP()}">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Apellido Materno</label>
                                <div class="controls">
                                    <input disabled id="apellido_m" name="full-name" type="text" class="input-xlarge" value ="${LaAdop.getApellidoM()}">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Fecha de nacimiento</label>
                                <div class="controls">
                                    <input disabled id="fecha_nac" name="full-name" type="password" class="input-xlarge" value ="${LaAdop.getFechaNac()}" >
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Lugar de nacimiento</label>
                                <div class="controls">
                                    <input disabled id="direccion" name="full-name" type="text" class="input-xlarge" value ="${LaAdop.getLugarNac()}" >
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Departamento de nacimiento</label>
                                <div class="controls">
                                    <input disabled id="departamento" name="full-name" type="text" class="input-xlarge" value ="${LaAdop.getDepaNac()}">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">País de nacimiento</label>
                                <div class="controls">
                                    <input disabled id="pais" name="full-name" type="text" class="input-xlarge" value ="${LaAdop.getPaisNac()}">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="radio">
                                        <label>
                                            <c:choose>
                                                <c:when test="${doc == 'd'}">
                                                    <input type="radio" name="optionsRadios1" id="optionsRadios1" value="D" checked>DNI
                                                </label>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="radio" name="optionsRadios1" id="optionsRadios1" value="D">DNI</label>
                                            </c:otherwise>
                                        </c:choose>                                                                                                              
                                    </div>
                                </div>   
                                <div class="col-md-3">   
                                    <div class="radio">

                                        <label>
                                            <c:choose>
                                                <c:when test="${doc == 'c'}">
                                                    <input type="radio" name="optionsRadios1" id="optionsRadios2" value="C" checked>Carnet de Extranjería
                                                </label>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="radio" name="optionsRadios1" id="optionsRadios2" value="C">Carnet de Extranjería</label>
                                            </c:otherwise>
                                        </c:choose>

                                    </div>                            
                                </div>
                            </div>    
                            <br>
                            <div class="control-group">
                                <div class="controls">
                                    <label class="control-label">N° de Documento</label>
                                    <input disabled id="num_doc" placeholder="Número" type="text" class="input-xlarge" value ="${LaAdop.getNDoc()}">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Celular</label>
                                <div class="controls">
                                    <input disabled id="celular" name="full-name" type="text" class="input-xlarge" value ="${LaAdop.getCelular()}">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Correo Electrónico</label>
                                <div class="controls">
                                    <input disabled id="correo" name="full-name" type="text" class="input-xlarge" value ="${LaAdop.getCorreo()}">
                                </div>
                            </div>
                            <br>
                            <h3>Estado Civil</h3>
                            <div class="row">
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <c:choose>
                                                <c:when test="${estCivil == '115'}">
                                                    <input type="radio" name="optionsRadios2" id="soltero" value="D" checked>Soltera
                                                </label>
                                            </c:when>
                                            <c:otherwise>
                                                <input type="radio" name="optionsRadios2" id="soltero" value="D">Soltera</label>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>   
                                <div class="col-md-3">   
                                    <div class="radio">
                                        <label>
                                            <c:choose>
                                                <c:when test="${estCivil == '99'}">
                                                    <input type="radio" name="optionsRadios2" id="casado" value="C" checked>Casada</label>
                                                </c:when>
                                                <c:otherwise>
                                                <input type="radio" name="optionsRadios2" id="casado" value="C">Casada</label>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>                            
                                </div>
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <c:choose>
                                                <c:when test="${estCivil == '118'}">
                                                    <input type="radio" name="optionsRadios2" id="viudo" value="D" checked>Viuda</label>
                                                </c:when>
                                                <c:otherwise>
                                                <input type="radio" name="optionsRadios2" id="viudo" value="D">Viuda</label>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <c:choose>
                                                <c:when test="${estCivil == '100'}">
                                                    <input type="radio" name="optionsRadios2" id="divorciado" value="D" checked>Divorciada</label>
                                                </c:when>
                                                <c:otherwise>
                                                <input type="radio" name="optionsRadios2" id="divorciado" value="D">Divorciada</label>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div> 
                            </div>   

                            <br>
                            <!--<p style="color: red">IMPORTANTE: SE HA DESHABILITADO LA VISTA DEBIDO AL ESTADO CIVIL MARCADO EN EL SOLICITANTE</p>-->
                        </fieldset>
                        <!--FIN DE CONTENIDO-->
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
    </body>
</html>