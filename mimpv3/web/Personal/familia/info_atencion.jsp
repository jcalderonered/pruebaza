<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Personal"%>
<%
response.setHeader( "Pragma", "no-cache" );
response.addHeader( "Cache-Control", "must-revalidate" );
response.addHeader( "Cache-Control", "no-cache" );
response.addHeader( "Cache-Control", "no-store" );
response.setDateHeader("Expires", 0);
Personal u=(Personal)request.getSession().getAttribute("usuario");
if (u==null){
%>
<jsp:forward page="/salir"/>
<% } %>
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
                        </button>
                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-left">
                            <li class="active"><a href="#">Inicio</a></li>
                            <li><a href="#">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-3 ">
                        <ul class="nav nav-list well">
                            <li><a href=""><span class="glyphicon glyphicon-home"></span> Inicio</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Sesiones/talleres</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNAs</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Juzgado</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UAs</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de Registros</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Organismo Acreditado </a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Autoridad Central</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>
                        </ul>
                    </div>

                    <div class="col-md-8 col-md-offset-1">
                        <form role="form">
                            <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                            <br>
                            <h1 align="center"><strong>Familia "ApellidoP-ApellidoM"</strong></h1>
                            <br>
                            <br>
                            <h3 align="left"><strong>Datos de la ficha</strong></h3>
                            <br>
                            <div class="row">
                                <div class="col-md-3">
                                    <label class="control-label">Número</label>
                                    <div class="controls">
                                        <input id="nombre" name="full-name" value="00293-12442" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <label class="control-label">Fecha de ingreso</label>
                                    <div class="controls">
                                        <input id="nombre" name="full-name" value="11-Nov-13" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <label class="control-label">Hoja de ruta </label>
                                    <div class="controls">
                                        <input id="nombre" name="full-name" value="HR" type="text" class="input-xlarge">
                                    </div>
                                </div>
                            </div> 
                            <br>
                            <br>
                            <ul class="nav nav-tabs row" id="tabs" >
                                <li><a href="#" data-toggle="tab">La Solicitante</a></li>
                                <li><a href="#" data-toggle="tab">El solicitante</a></li>
                                <li><a href="#" data-toggle="tab">Composición familiar</a></li>
                                <li><a href="#" data-toggle="tab">Vivienda</a></li>
                                <li><a href="#" data-toggle="tab">Información del Expediente</a></li>
                                <li><a href="#" data-toggle="tab">Proceso de adopción</a></li>
                                <li><a href="#" data-toggle="tab">Antecedentes del NNA</a></li>
                                <li><a href="#" data-toggle="tab">NNA asociado</a></li>
                                <li class="active"><a href="#" data-toggle="tab">Atenciones</a></li>
                            </ul>
                            <br>
                            <fieldset>
                                <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                                <br>
                                <h3><strong>Listado de Atenciones</strong></h3>
                                <br>
                                <!-- Text input-->
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Personal contactado</th>
                                                <th>Fecha y Hora</th>
                                                <th>Tipo de atencion</th>
                                                <th>Detalle</th>
                                                <th>Observacioens</th>
                                                <th>Editar</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Juan Cabello</td>
                                                <td>14-Nov-13 15:01</td>
                                                <td>Teléfono</td>
                                                <td>Requeria informacion sobre estado actual del proceso</td>
                                                <td>Tuvimos problemas de estatica en la llamada</td>
                                                <td><button href="#" class="btn btn-default">Editar</button></td>
                                            </tr>
                                            <tr>
                                                <td>Juan Cabello</td>
                                                <td>14-Nov-13 15:09</td>
                                                <td>Teléfono</td>
                                                <td>Requeria informacion sobre estado actual del proceso</td>
                                                <td>2da vez que trata de contactarse para la misma información</td>
                                                <td><button href="#" class="btn btn-default">Editar</button></td>
                                            </tr>
                                            <tr>
                                                <td>Ernesto Zapata</td>
                                                <td>19-Nov-13 09:00</td>
                                                <td>Correo</td>
                                                <td>Requeria informacion sobre estado actual del proceso</td>
                                                <td></td>
                                                <td><button href="#" class="btn btn-default">Editar</button></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <button id="singlebutton" name="singlebutton" class="btn btn-default">Registrar nueva atención</button>
                                    </div>
                                </div>
                                <!--FIN DE CONTENIDO-->
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
            <br>
            <br>
            <div id="footer">
                <div id="ja-footer" class="wrap">
                    <hr width=80% align="center">
                    <p align="center"><h5 class="caption" align="center" style="text-align: center;">MINISTERIO DE LA MUJER Y POBLACIONES VULNERABLES<br>Jr. Camaná 616, Lima - Perú<br>Central telefónica: (511) 626-1600</h5></p>
                    <p align="right">Diseñado por RED<br>www.red.net.pe</p>
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

