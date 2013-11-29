<%-- 
    Document   : info_familia
    Created on : 30/10/2013, 06:50:32 PM
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
         <!-- Datepicker -->
        <link href="${pageContext.servletContext.contextPath}/assets/css/datepicker3.css" rel="stylesheet">
    </head>

    <body id="bd" class="bd fs3 com_content">
        <br>
        <br>
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

                <div class="row">
                    <div class="col-md-6 col-md-offset-1">
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <br>
                        <form class="form-inline" role="form">
                            <!-- Form Name -->

                            <div class="col-md-4 col-md-offset-2">
                                <p class="text-left"><legend>Información Personal <br>(EL)</legend></p>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Nombres</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Nombres" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Apellido Paterno</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Apellido Paterno" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Apellido Materno</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Apellido Materno" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="textinput">País de Nacimiento</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="País " class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Departamento de nacimiento</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Departamento" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Provincia de nacimiento</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Provincia" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Fecha de nacimiento</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="dd/mm/yyyy" class=" datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Edad</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="XY" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios1" id="optionsRadios1" value="D" checked>
                                            DNI
                                        </label>
                                    </div>
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios1" id="optionsRadios2" value="C">
                                            Carnet de Extranjería
                                        </label>
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">N° documento</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Numero Documento" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <!-- Select Basic -->
                                <div class="control-group">
                                    <label class="control-label" for="selectbasic">Profesión/Ocupación</label>
                                    <div class="controls">
                                        <select id="selectbasic" name="selectbasic" class="input-xlarge">
                                            <option>Ingeniero</option>
                                            <option>Abogado</option>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Celular</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Celular" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Correo Electrónico</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="ejemplo@dominio.com" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <br>  
                            </div>

                            <div class="col-md-4 col-md-offset-1">
                                <p class="text-left"><legend>Información Personal <br> (ELLA) </legend></p>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Nombres</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Nombres" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Apellido Paterno</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Apellido Paterno" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Apellido Materno</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Apellido Materno" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="textinput">País de Nacimiento</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="País " class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Departamento de nacimiento</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Departamento" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Provincia de nacimiento</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Provincia" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Fecha de nacimiento</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="dd/mm/yyyy" class=" datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Edad</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="XY" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios2" id="optionsRadios1" value="D" checked>
                                            DNI
                                        </label>
                                    </div>
                                    <br>
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios2" id="optionsRadios2" value="C">
                                            Carnet de Extranjería
                                        </label>
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">N° documento</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Numero Documento" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <!-- Select Basic -->
                                <div class="control-group">
                                    <label class="control-label" for="selectbasic">Profesión/Ocupación</label>
                                    <div class="controls">
                                        <select id="selectbasic" name="selectbasic" class="input-xlarge">
                                            <option>Ingeniero</option>
                                            <option>Abogado</option>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Celular</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="Celular" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label" for="textinput">Correo Electrónico</label>
                                    <div class="controls">
                                        <input id="textinput" name="textinput" type="text" placeholder="ejemplo@dominio.com" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <br>  
                            </div>
                            <br>
                            <br>  

                            <p class="text-left"><legend>Residencia familiar</legend></p>
                            <br>
                            <div class="row">
                                <div class="col-lg-3">
                                    <label for="pais">País</label>
                                    <input id="pais" type="text" placeholder="Pais" class="input-xlarge">
                                </div>
                                <div class="col-lg-3">
                                    <label for="dep">Departamento</label> 
                                    <input id="dep" type="text" placeholder="Departamento" class="input-xlarge">
                                </div>
                                <div class="col-lg-3">
                                    <label for="prov">Provincia</label> 
                                    <input id="prov" type="text" placeholder="Provincia" class="input-xlarge">
                                </div>
                                <div class="col-lg-3">
                                    <label for="dist">Distrito</label> 
                                    <input id="dist" type="text" placeholder="Distrito" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-lg-4">
                                    <label for="dir">Dirección domiciliaria</label> 
                                    <input id="dir" type="text" placeholder="Dirección Domiciliaria" class="input-xlarge">
                                </div>

                                <div class="col-lg-4">
                                    <label for="telf">Teléfono fijo</label> 
                                    <input id="telf" type="text" placeholder="Teléfono fijo" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <p align="center"><button href="#" class="btn btn-default">Volver</button></p>
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
            <!-- Bootstrap core JavaScript
        ================================================== -->
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
        <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
        <script type="text/javascript">
           
             $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language":"es"});
            
        </script>
        <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>