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
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <form role="form">
                            
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
                                <li class="active"><a href="#" data-toggle="tab">Proceso de adopción</a></li>
                                <li><a href="#" data-toggle="tab">Antecedentes del NNA</a></li>
                                <li><a href="#" data-toggle="tab">NNA asociado</a></li>
                                <li><a href="#" data-toggle="tab">Atenciones</a></li>
                            </ul>
                            <br>
                            <fieldset>
                                <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                               
                                <br>
                                <h3><strong>Sesiones asistidas</strong></h3>
                                <br>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Número de sesión</th>
                                                <th>Fecha</th>
                                                <th>Detalles de sesion</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>001-012</td>
                                                <td>17-Mar-12</td>
                                                <td><button href="#" class="btn btn-default">Detalles</button></td>
                                            </tr>
                                            <tr>
                                                <td>003-013</td>
                                                <td>15-Jul-13</td>
                                                <td><button href="#" class="btn btn-default">Detalles</button></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <h3><strong>Talleres asistidos</strong></h3>
                                <br>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Tipo taller</th>
                                                <th>Nombre</th>
                                                <th>N° de reuniones</th>
                                                <th>Grupo y Turno asistido</th>
                                                <th>Detalles</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Preparacion</td>
                                                <td>Taller de capacitacion 2</td>
                                                <td>5</td>
                                                <td>Grupo Lunes - Turno Mañana</td>
                                                <td><button href="#" class="btn btn-default">Detalles</button></td>
                                            </tr>
                                            <tr>
                                                <td>Pre Adopción</td>
                                                <td>Atenciones especiales para niños especiales</td>
                                                <td>3</td>
                                                <td>Grupo Lunes - Turno Mañana</td>
                                                <td><button href="#" class="btn btn-default">Detalles</button></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <h3><strong>Evaluaciones asociadas</strong></h3>
                                <br>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Tipo Evaluacion</th>
                                                <th>Fecha asignacion</th>
                                                <th>Resultado</th>
                                                <th>Detalles</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Socioeconomica</td>
                                                <td>11-Oct-13</td>
                                                <td>Favorable</td>
                                                <td><button href="#" class="btn btn-default">Detalles</button></td>
                                            </tr>
                                            <tr>
                                                <td>Legal</td>
                                                <td>18-Oct-13</td>
                                                <td>Favorable</td>
                                                <td><button href="#" class="btn btn-default">Detalles</button></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <h3><strong>Resoluciones</strong></h3>
                                <br>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Tipo Resolución</th>
                                                <th>Número</th>
                                                <th>Fecha de Resolución</th>
                                                <th>Fecha de Notificación</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Observaciones</td>
                                                <td>020273</td>
                                                <td>11-Oct-13</td>
                                                <td>11-Oct-13</td>
                                            </tr>
                                            <tr>
                                                <td>Reconsideración</td>
                                                <td>027313</td>
                                                <td>18-Oct-13</td>
                                                <td>18-Oct-13</td>
                                            </tr>
                                            <tr>
                                                <td>Aptitud</td>
                                                <td>029103</td>
                                                <td>28-Oct-13</td>
                                                <td>28-Oct-13</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <h3><strong>Estudios del caso</strong></h3>
                                <br>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Orden</th>
                                                <th>Nombre NNA</th>
                                                <th>Grupo Ref. NNA</th>
                                                <th>Código de Grupo Ref.</th>
                                                <th>Fecha de Estudio</th>
                                                <th>Fecha de Solicitud Adopción</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>0202</td>
                                                <td>Juanito Benitez</td>
                                                <td>Mayores</td>
                                                <td>MM</td>
                                                <td>11-Oct-13</td>
                                                <td></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <h3><strong>Designaciones</strong></h3>
                                <br>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Número</th>
                                                <th>Prioridad</th>
                                                <th>Tipo</th>
                                                <th>¿NNA Prioritario?</th>
                                                <th>Nombre NNA</th>
                                                <th>Fecha de Propuesta</th>
                                                <th>Fecha Consejo</th>
                                                <th>Obs</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>0390</td>
                                                <td>1</td>
                                                <td>Dupla</td>
                                                <td>No</td>
                                                <td>Arturo Benitez</td>
                                                <td>11-Oct-13</td>
                                                <td>13-Oct-13</td>
                                                <td>Se cambia la prioridad</td>
                                            </tr>
                                        </tbody>
                                    </table>
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

