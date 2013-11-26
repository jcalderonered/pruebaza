<%-- 
    Document   : inicio_personal
    Created on : 28/10/2013, 09:54:29 AM
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
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Editar Organismo</strong></h1>
                        <br>
                        <form class="form-horizontal">
                            <div class="row">
                                <div class="col-md-3 col-md-offset-1">   
                                    <br>
                                    <fieldset>
                                        <!-- Text input-->

                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Nombre</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Competencia</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">País</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Dirección</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Teléfono</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Resolución de </label>
                                                <label>autorización</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Fecha de emisión de </label>
                                                <label>resolución</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Resolución de </label>
                                                <label>renovación</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Fecha de renovación</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Fecha de vencimiento </label>
                                                <label>de autorización</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Observaciones</label>
                                                <textarea class="input-xlarge" name="message" placeholder="" rows="5" cols="25"></textarea>
                                            </div>
                                        </div>
                                        <br>
                                        <br>
                                        <!-- Button -->

                                    </fieldset>

                                </div>
                                <div class="col-md-4 col-md-offset-3">
                                    <h3 align="center"><strong>Representante Legal</strong></h3>


                                    <fieldset>
                                        <!-- Text input-->

                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Nombre</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Apellido Paterno</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Apellido Materno</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Usuario</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Contraseña</label>
                                                <input id="full-name" name="full-name" type="password" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Fecha de autorización</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Fecha de renovación</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Fecha de vencimiento </label>
                                                <label>de autorización</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Correo</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Celular</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Dirección</label>
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <div class="controls">
                                                <label class="control-label">Observaciones</label>
                                                 <textarea class="input-xlarge" name="message" placeholder="" rows="5" cols="25"></textarea>
                                            </div>
                                        </div>
                                        <br>
                                        <br>
                                    </fieldset>                              
                                </div>
                            </div> 
                        </form>
                        <div class="control-group">
                            <div class="controls">
                                <p align="center"><button id="singlebutton" name="singlebutton" class="btn btn-default">Editar</button></p>
                            </div>
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
    </body>
</html>