<%-- 
    Document   : detalle_evaluacion
    Created on : 14/11/2013, 07:51:13 PM
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
        <script src="<%=request.getContextPath()%>/assets/js/bootstrap.js"></script>
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
                            <li class="active"><a href="#">Inicio</a></li>
                            <li><a href="#">Actualizar Información</a></li>
                            <li><a href="#">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-4 ">
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
                    <div class="col-md-6 col-md-offset-1">
                        <h1 align="center"><strong>Buscador de Registro por Etapa</strong></h1>
                        <br>
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="#" data-toggle="tab">Informativa</a></li>
                            <li><a href="#" data-toggle="tab">Evaluativa</a></li>
                            <li class="active"><a href="#" data-toggle="tab">Adopción</a></li>
                            <li><a href="#" data-toggle="tab">Post Adopción</a></li>
                        </ul>
                        <form role="form">
                            <fieldset>
                                <br>
                                <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                                <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  
                                <br>
                                <h1 align="center"><strong>Familia "ApellidoP-ApellidoM"</strong></h1>
                                <br>
                                <br>
                                <h3 align="left"><strong>Detalles de resolución de consejo</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha consejo</label>
                                    <div class="controls">
                                        <input type="text" class="span2" value="02-16-2012" id="dp3" >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Resultado</label>
                                    <div class="controls">
                                        <select>
                                            <option value="sia"></option>
                                            <option value="mia">Favorable</option>
                                            <option value="mia">Desfavorable</option>
                                        </select>
                                    </div>    
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observación</label>
                                    <div class="controls">
                                        <textarea type="text" class="span2" value="02-16-2012" id="dp3" ></textarea>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <br>
                                <h3 align="left"><strong>Familias asignadas al mismo NNA</strong></h3>
                                <br>
                                <div class="table-responsive">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th class="col-sm-2 " >Expediente</th>
                                            <th class="col-sm-2 " >UA</th>
                                            <th class="col-sm-2 " >Nivel sociec</th>
                                            <th class="col-sm-2 " >Información</th>
                                            <th class="col-sm-2 " >Resolución de aptitud</th>
                                            <th class="col-sm-2 " >Prioridad</th>
                                            
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <tr>
                                            <td>Gutierrez-Huaman </td>
                                            <td>Lima</td>
                                            <td>C</td>
                                            <td><button id="singlebutton" name="singlebutton" class="btn btn-default">Ver</button></td>
                                            <td>14-Nov-2012</td>
                                            <td>1</tr>
                                        <tr>
                                            <td>Morales-Loza</td>
                                            <td>Trujillo</td>
                                            <td>B</td>
                                            <td><button id="singlebutton" name="singlebutton" class="btn btn-default">Ver</button></td>
                                            <td>21-Agos-2011</td>
                                            <td>2</td></tr>
                                    </tbody>
                                </table>
                            </div>
                                <!-- Button -->
                                <div class="control-group">
                                    <div class="controls">
                                        <button id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
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
    </body>
</html>