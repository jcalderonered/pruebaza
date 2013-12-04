<%-- 
    Document   : detalle_evaluacion
    Created on : 14/11/2013, 07:51:13 PM
    Author     : User
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
                            <li class="active"><a href="#" data-toggle="tab">Designación</a></li>
                            <li><a href="#" data-toggle="tab">Adopción</a></li>
                            <li><a href="#" data-toggle="tab">Post Adopción</a></li>
                        </ul>
                        <form role="form">
                            <fieldset>
                                <br>
                                <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                                <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  
                                <br>
                                <h1 align="center"><strong>NNA "NOMBRE APELLIDOS"</strong></h1>
                                <br>
                                <br>
                                <h3 align="left"><strong>Detalles de decisión de consejo</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha </label>
                                    <div class="controls">
                                        <input type="text" class="datepicker span2" value="02-16-2012" id="dp3" >
                                    </div>
                                </div>
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
                                                <th> Elegir </th>

                                            </tr>
                                        </thead>

                                        <tbody>
                                            <tr>
                                                <td>Gutierrez-Huaman </td>
                                                <td>Lima</td>
                                                <td>C</td>
                                                <td><button id="singlebutton" name="singlebutton" class="btn btn-default">Ver</button></td>
                                                <td>14-Nov-2012</td>
                                                <td>1
                                                <td>
                                                    <div class="radio">
                                                        <label>
                                                            <input type="radio" name="eleccion" id="" value="D"></label>
                                                    </div>   
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Morales-Loza</td>
                                                <td>Trujillo</td>
                                                <td>B</td>
                                                <td><button id="singlebutton" name="singlebutton" class="btn btn-default">Ver</button></td>
                                                <td>21-Agos-2011</td>
                                                <td>2</td>
                                                <td>
                                                    <div class="radio">
                                                        <label>
                                                            <input type="radio" name="eleccion" id="" value="D"></label>
                                                    </div>   
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">Tipo de Propuesta</label>
                                    <div class="controls">
                                        <select>
                                            <option value="sia"></option>
                                            <option value="mia">Directa</option>
                                            <option value="mia">Dupla</option>
                                            <option value="mia">Terna</option>
                                        </select>
                                    </div>    
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones</label>
                                    <div class="controls">
                                        <textarea type="text" cols="25" rows="5"></textarea>
                                    </div>
                                </div>
                                <br>
                                <br>

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