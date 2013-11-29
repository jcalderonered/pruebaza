<%-- 
    Document   : lista_charlas
    Created on : 28/10/2013, 03:17:53 PM
    Author     : Ayner Pérez
--%>

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
                            <li class="active"><a href="#">Inicio</a></li>
                            <li><a href="#">Actualizar Información</a></li>
                            <li><a href="#">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>

            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div class="container">
                <div class="row">
                    <div class="col-md-4 ">
                        <ul class="nav nav-list well">
                            <li class="active"><a href=""><span class="glyphicon glyphicon-home"></span> Inicio</a></li>
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

                    <div class="col-md-6 col-md-offset-1">
                        <h1>Listado de sesiones</h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>Número de sesión</th>
                                        <th>Habilitado</th>
                                        <th>Modificar</th>
                                        <th>Ver Inscritos</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    <tr>
                                        <td>001-012</td>
                                        <td>Si</td>
                                        <td><button href="#" class="btn btn-default">Modificar</button></td>
                                        <td><button href="#" class="btn btn-default">Inscritos</button></td>
                                    </tr>
                                    <tr>
                                        <td>001-013</td>
                                        <td>Si</td>
                                        <td><button href="#" class="btn btn-default">Modificar</button></td>
                                        <td><button href="#" class="btn btn-default">Inscritos</button></td>
                                    </tr>
                                    <tr>
                                        <td>001-014</td>
                                        <td>Si</td>
                                        <td><button href="#" class="btn btn-default">Modificar</button></td>
                                        <td><button href="#" class="btn btn-default">Inscritos</button></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <button href="#" class="btn btn-default">Agregar nueva Sesión</button>
                        <br>
                        <br>
                        <h1>Listado de talleres</h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>Tipo taller</th>
                                        <th>Nombre</th>
                                        <th>N° de reuniones</th>
                                        <th>Habilitado</th>
                                        <th>Modificar</th>
                                        <th>Ver Inscritos</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Preparación</td>
                                        <td>Taller de capacitacion 2</td>
                                        <td>5</td>
                                        <td>Si</td>
                                        <td><button href="#" class="btn btn-default">Modificar</button></td>
                                        <td><button href="#" class="btn btn-default">Inscritos</button></td>
                                    </tr>
                                    <tr>
                                        <td>Preparación</td>
                                        <td>Taller de Capacitación 3</td>
                                        <td>4</td>
                                        <td>Si</td>
                                        <td><button href="#" class="btn btn-default">Modificar</button></td>
                                        <td><button href="#" class="btn btn-default">Inscritos</button></td>
                                    </tr>
                                    <tr>
                                        <td>Pre Adopción</td>
                                        <td>Atenciones especiales para niños especiales</td>
                                        <td>3</td>
                                        <td>Si</td>
                                        <td><button href="#" class="btn btn-default">Modificar</button></td>
                                        <td><button href="#" class="btn btn-default">Inscritos</button></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <button href="#" class="btn btn-default">Agregar nuevo Taller</button>
                        <br>
                        <br>
                        <h1>Talleres / Charlas Pasados o en Curso</h1>
                        <br>
                        <p>A continuación se presenta una lista de los talleres y charlas pasados que necesitan registrar su asistencia</p>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>Tipo taller</th>
                                        <th>Nombre / N° sesión</th>
                                        <th>Terminado</th>
                                        <th>Toma de asistencia</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>---</td>
                                        <td>001-009</td>
                                        <td>Si</td>
                                        <td><button href="#" class="btn btn-default">Asistencia</button></td>
                                    </tr>
                                    <tr>
                                        <td>---</td>
                                        <td>001-010</td>
                                        <td>Si</td>
                                        <td><button href="#" class="btn btn-default">Asistencia</button></td>
                                    </tr>
                                    <tr>
                                        <td>Capacitación</td>
                                        <td>Taller de Capacitacion 1</td>
                                        <td>No</td>
                                        <td><button href="#" class="btn btn-default">Asistencia</button></td>
                                    </tr>
                                </tbody>
                            </table>
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
                    <p align="right">Diseñado por RED<br>www.red.net.pe</p>
                </div>
            </div>
            <!-- core JavaScript
        ================================================== -->
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
            <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>

            <!-- Ubicar al final -->
    </body>
</html>