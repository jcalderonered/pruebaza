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
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Autoridad Central</a></li> 
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>    
                        
                        </ul>
                    </div>

                    <div class="col-md-6 ">
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Lista de Familias Internacionales </strong></h1>
                        
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th colspan="3">El Adoptante</th>
                                        <th colspan="3">La Adoptante</th>
                                        <th rowspan="2">Organismo Acreditado o Autoridad Central</th>
                                        <th rowspan="2">Detalles</th> 
                                    </tr>
                                    <tr>
                                        <th>Nombres y Apellidos</th>
                                        <th>Edad</th>
                                        <th>Correo</th>
                                        <th>Nombres y Apellidos</th> 
                                        <th>Edad</th>
                                        <th>Correo</th> 
                                    </tr>
                                </thead>

                                <tbody>
                                    <tr>
                                        <td>Rick Davis</td>
                                        <td>40</td>
                                        <td>rdaivs@gmail.com</td>
                                        <td>Lori Davis</td>
                                        <td>40</td>
                                        <td>ldaivs@gmail.com</td>
                                        <td>Australia</td>
                                        <td><button href="#" class="btn btn-default">
                                        <span class="glyphicon glyphicon-search"></span> 
                                         Ver</button></td>
                                    </tr>
                                    <tr>
                                        <td>Daryl Wilshere</td>
                                        <td>40</td>
                                        <td>dwilshere@gmail.com</td>
                                        <td>Carol Wilshere</td>
                                        <td>40</td>
                                        <td>cwilshere@gmail.com</td>
                                        <td>Canada</td>
                                        <td><button href="#" class="btn btn-default">
                                        <span class="glyphicon glyphicon-search"></span> 
                                         Ver</button></td>
                                    </tr>
                                    <tr>
                                        <td>Glenn Williams</td>
                                        <td>40</td>
                                        <td>gwilliams@gmail.com</td>
                                        <td>Maggie Williams</td>
                                        <td>40</td>
                                        <td>mwilliams@gmail.com</td>
                                        <td>Canada</td>
                                        <td><button href="#" class="btn btn-default">
                                        <span class="glyphicon glyphicon-search"></span> 
                                         Ver</button></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                       
                              <div class="control-group">
                                    <div class="controls">
                                        <button id="singlebutton" name="singlebutton" class="btn btn-default">Agregar</button>
                                    </div>
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