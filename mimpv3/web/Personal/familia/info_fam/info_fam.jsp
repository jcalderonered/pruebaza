<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
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
            <div class="container">
                <div class="row">
                    <div class="col-md-3 ">
                        <ul class="nav nav-list well">
                            <li><a href=""><span class="glyphicon glyphicon-home"></span> Inicio</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Listado de familias</a></li>
                            <li class="active"><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
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
                                <li class="active"><a href="#" data-toggle="tab">Composición familiar</a></li>
                                <li><a href="#" data-toggle="tab">Vivienda</a></li>
                                <li><a href="#" data-toggle="tab">Información del Expediente</a></li>
                                <li><a href="#" data-toggle="tab">Proceso de adopción</a></li>
                                <li><a href="#" data-toggle="tab">Antecedentes del NNA</a></li>
                                <li><a href="#" data-toggle="tab">NNA asociado</a></li>
                                <li><a href="#" data-toggle="tab">Atenciones</a></li>
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
                    <p align="right">Diseñado por RED<br>www.red.net.pe</p>
                </div>
            </div>
    </body>
</html>