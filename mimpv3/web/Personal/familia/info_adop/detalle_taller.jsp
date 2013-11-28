<%-- 
    Document   : detalle_taller
    Created on : 14/11/2013, 07:51:25 PM
    Author     : User
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
                                        <input id="nombre" name="full-name" value="11-Nov-13" type="text" class="input-xlarge">
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
                                <h3 align="left"><strong>Detalles del taller</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Tipo de Taller</label>
                                    <div class="controls">
                                        <input id="tipo_taller" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Nombre del taller</label>
                                    <div class="controls">
                                        <input type="text" class="span2" value="" id="nombre" >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Nombre del grupo</label>
                                    <div class="controls">
                                        <input id="nombre_grupo" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Nombre del turno</label>
                                    <div class="controls">
                                        <input type="texto" class="span2" value="" id="turno" >
                                    </div>
                                </div>
                                <br>
                                <h3 align="left"><strong>Listado de reuniones</strong></h3>
                                <br>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Fecha</th>
                                                <th>Hora</th>
                                                <th>Duración</th>
                                                <th>Dirección</th>
                                                <th>Facilitador</th>
                                                <th>Asistencia</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>10-Nov-13</td>
                                                <td>10:00</td>
                                                <td>2 horas</td>
                                                <td>"Agregar dirección"</td>
                                                <td>Fernando Ramirez</td>
                                                <td>A</td>
                                            </tr>
                                            <tr>
                                                <td>11-Nov-13</td>
                                                <td>10:00</td>
                                                <td>2 horas</td>
                                                <td>"Agregar dirección"</td>
                                                <td>Juan Palomino</td>
                                                <td>A</td>
                                            </tr>
                                            <tr>
                                                <td>13-Nov-13</td>
                                                <td>10:00</td>
                                                <td>2 horas</td>
                                                <td>"Agregar dirección"</td>
                                                <td>Alonso Herrera</td>
                                                <td>A</td>
                                            </tr>
                                            <tr>
                                                <td>14-Nov-13</td>
                                                <td>10:00</td>
                                                <td>2 horas</td>
                                                <td>"Agregar dirección"</td>
                                                <td>Alonso Herrera</td>
                                                <td>A</td>
                                            </tr>
                                            <tr>
                                                <td>17-Nov-13</td>
                                                <td>10:00</td>
                                                <td>2 horas</td>
                                                <td>"Agregar dirección"</td>
                                                <td>Oscar Barreto</td>
                                                <td>J</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <p>A: Asistió / J:  Inasistencia Justificada / I: Inasistencia</p>
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
    </body>
</html>