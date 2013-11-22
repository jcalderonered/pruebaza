<%-- 
    Document   : inscripcion_sesion1
    Created on : 28/10/2013, 05:45:16 AM
    Author     : Ayner Pérez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div class="container">
                <div class="row">
                    <div class="col-md-4 ">
                        <ul class="nav nav-list well">
                            <li><a href=""><span class="glyphicon glyphicon-home"></span> Inicio</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Listado de familias</a></li>
                            <li class="active"><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>
                        </ul>
                    </div>

                    <div class="col-md-6 col-md-offset-1">
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Lista de Familias</strong></h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="col-sm-3 ">El Adoptante</th>
                                        <th class="col-sm-3 ">La Adoptante</th>
                                        <th class="col-sm-3 ">País de Residencia</th>
                                        <th class="col-sm-3 ">Correo principal</th>
                                        <th class="col-sm-2 ">Mas informacion</th>
                                        <th class="col-sm-2 ">Estado de Adopción</th>                                          
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>El Adoptante 1</td>
                                        <td>La Adoptante 1</td>
                                        <td>España</td>
                                        <td>familia1@dominio.pe</td>
                                        <td><button href="#" class="btn btn-default">Info</button></td>
                                        <td><button href="#" class="btn btn-default">Ver</button></td>
                                    </tr>
                                    <tr>
                                        <td>El Adoptante 2</td>
                                        <td>La Adoptante 2</td>
                                        <td>Inglaterra</td>
                                        <td>familia2@dominio.pe</td>
                                        <td><button href="#" class="btn btn-default">Info</button></td>
                                        <td><button href="#" class="btn btn-default">Ver</button></td>
                                    </tr>
                                    <tr>
                                        <td>El Adoptante 1</td>
                                        <td>La Adoptante 1</td>
                                        <td>Italia</td>
                                        <td>familia1@dominio.pe</td>
                                        <td><button href="#" class="btn btn-default">Info</button></td>
                                        <td><button href="#" class="btn btn-default">Ver</button></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <br>
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
    </body>
</html>