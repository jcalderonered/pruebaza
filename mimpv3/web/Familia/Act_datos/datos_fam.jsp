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
                            <li><a href="${pageContext.servletContext.contextPath}/Flecturas"><span class="glyphicon glyphicon-chevron-right"></span> Lecturas </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Festado"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fcontra"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>
                    <div class="col-md-9">
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc1" data-toggle="tab">La Solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc2" data-toggle="tab">El solicitante</a></li>
                            <!--<li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc3" data-toggle="tab">Composición familiar</a></li>-->
                            <!-- <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc4" data-toggle="tab">Vivienda</a></li> -->
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc5" data-toggle="tab">Antecedentes del niño, niña o adolescente</a></li>
                        </ul>

                        <form class="form-horizontal"> 
                            <br>
                            <h3><strong>Hijo/a/s biológico/a/s</strong></h3>
                            <br>
                            <div class="row">
                                <div id="tabla_fam" class="table-responsive">
                                    <table id="hijos" class="table table-bordered table-striped ">
                                        <thead>
                                            <tr>
                                                <th colspan="3">Nombres y Apellidos</th>
                                                <th colspan="2">Edad y Fecha de nacimiento</th>
                                                <th rowspan="2">Ocupación</th>
                                                <th rowspan="2">Estado de Salud</th>
                                                <th rowspan="2">Reside con usted?</th>                                          
                                            </tr>
                                            <tr>
                                                <th>Apellido Paterno</th>
                                                <th>Apellido Materno</th>
                                                <th>Nombre</th>
                                                <th>Edad</th>
                                                <th>Fecha de nacimiento</th>                                          
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="hijo" items="${listaHijos}" varStatus="status">
                                                <tr>
                                                    <td><input type="text" class="input_width" value="${hijo.getApellidoP()}" disabled></td>
                                                    <td>
                                                        <input type="text" class="input_width" value="${hijo.getApellidoM()}" disabled>
                                                    </td>
                                                    <td>
                                                        <input type="text" class="input_width" value="${hijo.getNombre()}" disabled>
                                                    </td>
                                                    <td>
                                                        <input type="text" class="input_width" value="${hijo.getEdad()}" disabled>
                                                    </td>
                                                    <td>
                                                        <input type="text" class="input_width" value="${hijo.getFechaNacString()}" disabled>
                                                    </td>
                                                    <td>
                                                        <input type="text" class="input_width" value="${hijo.getOcupacion()}" disabled>
                                                    </td>
                                                    <td>
                                                        <input type="text" class="input_width" value="${hijo.getEstadoSalud()}" disabled>
                                                    </td>
                                                    <td>
                                                        <select>
                                                            <c:choose>
                                                                <c:when test="${hijo.getReside() == 0}">
                                                                    <option value="0">SI</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="1">NO</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <br>
                            <h3><strong>Hijo/a/s adoptivo/a/s</strong></h3>
                            <br>
                            <div class="row">
                                <div id="tabla_fam" class="table-responsive">
                                    <table id="hijos" class="table table-bordered table-striped ">
                                        <thead>
                                            <tr>
                                                <th colspan="3">Nombres y Apellidos</th>
                                                <th rowspan="2">Fecha de Adopción</th>
                                                <th colspan="2">Edad y Fecha de nacimiento</th>
                                                <th rowspan="2">Ocupación</th>
                                                <th rowspan="2">Estado de Salud</th>
                                                <th rowspan="2">Reside con usted?</th>                                          
                                            </tr>
                                            <tr>
                                                <th>Apellido Paterno</th>
                                                <th>Apellido Materno</th>
                                                <th>Nombre</th>
                                                <th>Edad</th>
                                                <th>Fecha de nacimiento</th>                                          
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="hijoadop" items="${listaHijosAdop}" varStatus="status">
                                            <tr>
                                                <td><input type="text" class="input_width" value="${hijoadop.getApellidoP()}" disabled></td>
                                                <td><input type="text" class="input_width" value="${hijoadop.getApellidoM()}" disabled></td>
                                                <td>
                                                    <input type="text" class="input_width" value="${hijoadop.getNombre()}" disabled>
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width" value="${hijoadop.getFechaAdopString()}" disabled>
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width" value="${hijoadop.getEdad()}" disabled>
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width" value="${hijoadop.getFechaNacString()}" disabled>
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width" value="${hijoadop.getOcupacion()}" disabled>
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width" value="${hijoadop.getEstadoSalud()}" disabled>
                                                </td>
                                                <td>
                                                    <select>
                                                        <c:choose>
                                                                <c:when test="${hijoadop.getReside() == 0}">
                                                                    <option value="0">SI</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="1">NO</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </select>
                                                </td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <br>
                            <h3><strong>Otras personas que residan con usted/es en la vivienda</strong></h3>
                            <br>
                            <div class="row">
                                <div id="tabla_fam" class="table-responsive">
                                    <table id="hijos" class="table table-bordered table-striped ">
                                        <thead>
                                            <tr>
                                                <th colspan="3">Nombres y Apellidos</th>
                                                <th rowspan="2">Parentesco/Relación</th>
                                                <th rowspan="2">Edad</th>      
                                                <th rowspan="2">Ocupación</th>
                                                <th rowspan="2">Estado de Salud</th>                    
                                            </tr>
                                            <tr>
                                                <th>Apellido Paterno</th>
                                                <th>Apellido Materno</th>
                                                <th>Nombre</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="res" items="${listaRes}" varStatus="status">
                                            <tr>
                                                <td>
                                                    <input type="text" class="input_width" value="${res.getApellidoP()}" disabled>
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width" value="${res.getApellidoM()}" disabled>
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width" value="${res.getNombre()}" disabled>
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width" value="${res.getParentesco()}" disabled>
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width" value="${res.getEdad()}" disabled>
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width" value="${res.getOcupacion()}" disabled>
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width" value="${res.getEstadoSalud()}" disabled>
                                                </td>
                                            </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <br>
                                <p style="color: red">IMPORTANTE: SI DESEA REALIZAR ALGÚN CAMBIO DEBERÁ PRESENTAR UNA SOLICITUD</p>
                                <p style="color: red">EN MESA DE PARTES DE LA DGA</p>
                                <br>
                                <p>Según lo establecido en la Ley Nº29733 - Ley de protección de datos personales, la información proporcionada por 
                                    usted/ustedes será de uso exclusivo, con la reserva y confidencialidad del caso, para los fines concernientes al 
                                    procedimiento administrativo de adopción.
                                </p>
                            </div>
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