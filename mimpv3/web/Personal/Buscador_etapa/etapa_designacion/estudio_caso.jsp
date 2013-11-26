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
                                <button id="singlebutton" name="singlebutton" class="btn btn-default">Registrar nuevo Estudio de caso</button>
                                <br>
                                <br>
                                <h3 align="left"><strong>Detalles de estudio de caso</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Orden</label>
                                    <div class="controls">
                                        <input type="text" class="span2" value="" id="" >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de estudio</label>
                                    <div class="controls">
                                        <input type="text" class="span2" value="" id="" >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha solicitud de adopción</label>
                                    <div class="controls">
                                        <input type="text" class="span2" value="" id="" >
                                    </div>
                                </div>
                                <br>
                                <h1 align="center"><strong>Filtrar Lista</strong></h1>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Nombre</label>
                                    <div class="controls">
                                        <input type="text" class="span2" value="" id="" >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Paterno</label>
                                    <div class="controls">
                                        <input type="text" class="span2" value="" id="" >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Materno</label>
                                    <div class="controls">
                                        <input type="text" class="span2" value="" id="" >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Sexo</label>
                                    <div>
                                    <select>
                                        <option value="mia" selected></option>
                                        <option value="sia">Masculino</option>
                                        <option value="mia">Femenino</option>
                                    </select>
                                   </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">UA</label>
                                    <div class="controls">
                                        <select>
                                            <option value="mia" selected></option>
                                            <option value="sia">Lima</option>
                                            <option value="mia">Chiclayo</option>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <div>
                                    <label class="control-label">Grupo de referencia</label>
                                    <div class="controls">
                                        <select>
                                            <option value="sia">Mayores</option>
                                            <option value="sia">Adolescentes</option>
                                            <option value="mia">Hermanos</option>
                                            <option value="mia">Salud</option>
                                            <option value="mia">Seguimiento</option>
                                        </select>
                                    </div>  
                                </div>
                                <br>
                                <h1 align="center"><strong>Lista de NNA Prioritarios</strong></h1>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th class="col-sm-2 " >Nombre completo</th>
                                                <th class="col-sm-2 " >UA</th>
                                                <th class="col-sm-2 " >Sexo</th>
                                                <th class="col-sm-2 " >Edad</th>
                                                <th class="col-sm-2 " >Información</th>
                                                <th class="col-sm-2 " >Seleccionar</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <tr>
                                                <td>NN </td>
                                                <td>Lima</td>
                                                <td>Masculino</td>
                                                <td>4</td>
                                                <td><button id="singlebutton" name="singlebutton" class="btn btn-default">Ver</button></td>
                                                <td>
                                                    <div class="radio" >
                                                        <label>
                                                            <input type="radio" name="optionsRadios1" id="optionsRadios1" value="D" checked></label>
                                                    </div>
                                                </td>

                                            </tr>
                                            <tr>
                                                <td>Luis Montoya</td>
                                                <td>Trujillo</td>
                                                <td>Masculino</td>
                                                <td>5</td>
                                                <td><button id="singlebutton" name="singlebutton" class="btn btn-default">Ver</button></td>
                                                <td>
                                                    <div class="radio" >
                                                        <label>
                                                            <input type="radio" name="optionsRadios1" id="optionsRadios1" value="E" ></label>
                                                    </div>
                                                </td>

                                            </tr>
                                        </tbody>
                                    </table>
                                </div>

                                <!-- Button -->
                                <div class="control-group">
                                    <div class="controls">
                                        <button id="singlebutton" name="singlebutton" class="btn btn-default">Buscar NNA</button>
                                        &nbsp;
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