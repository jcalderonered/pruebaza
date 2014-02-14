<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
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
                            <li><a href="${pageContext.servletContext.contextPath}/inf"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de sesiones/talleres</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNA</a></li>
                                <%if (u.getRol().equals("admin")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/juzgado"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de juzgado</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ua"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UA</a></li>
                                <%}
                                if (!u.getRol().equals("DAPA") && !u.getRol().equals("MATCH")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/famint"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                                <%}
                                if (!u.getRol().equals("mpartes")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de registros</a></li>
                                <%if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/usuarios"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                                <%}
                                if (u.getRol().equals("admin") || u.getRol().equals("DEIA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de organismo acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de autoridad central</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                            <%if (u.getRol().equals("DAPA") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                            <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-8">
                        <form role="form">
                            <!-- <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  -->
                            <c:if test="${estado != 'formativa'}">
                            <br>
                            <h1 align="center"><strong>Familia "${expediente.getExpediente()}"</strong></h1>
                            <br>
                            </c:if>
                            <br>
                            <br>
                            <ul class="nav nav-tabs row">
                                <li><a href="${pageContext.servletContext.contextPath}/laSolicitante">La Solicitante</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/elSolicitante" >El solicitante</a></li>
                                <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/compFamiliar" >Composición familiar</a></li>-->
                                <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/vivienda" >Vivienda</a></li>-->
                                <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/infoExpediente" >Información del Expediente</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/procesoAdopcion" >Proceso de adopción</a></li>
                                <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/antNna" >Antecedentes del NNA</a></li>
                                <li ${estado == 'formativa' || estado == 'evaluacion' || estado == 'espera' || estado == 'designacion' || estado == 'adopcion' || estado == 'reevaluacion' ? 'class="hidden"' : ''} ><a href="${pageContext.servletContext.contextPath}/nnaAsociado" >NNA Adoptado</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/atenciones" >Atenciones</a></li>
                            </ul>
                            <br>
                            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
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
                                            <tr>
                                                <td><input type="text" class="input_width"></td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input disabled type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input disabled type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><input type="text" class="input_width"></td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input disabled type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
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
                                            <tr>
                                                <td><input type="text" class="input_width"></td>
                                                <td><input type="text" class="input_width"></td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input disabled type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><input type="text" class="input_width"></td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input disabled type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td><input type="text" class="input_width"></td>
                                                <td><input type="text" class="input_width"></td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input disabled type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
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
                                            <tr>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                                <td>
                                                    <input type="text" class="input_width">
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="control-group">
                                <div class="controls">
                                    <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
                                </div>
                            </div>
                        </form>
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
            <!-- core JavaScript
        ================================================== -->
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
            <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
            <script type="text/javascript">

                $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

            </script>
            <!-- Ubicar al final -->
    </body>
</html>