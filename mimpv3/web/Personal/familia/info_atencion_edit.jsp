<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
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
                                        <input id="nombre" name="full-name" value="11-Nov-13" type="text" class="datepicker input-xlarge">
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
                                <li><a href="#" data-toggle="tab">Proceso de adopción</a></li>
                                <li><a href="#" data-toggle="tab">Antecedentes del NNA</a></li>
                                <li><a href="#" data-toggle="tab">NNA asociado</a></li>
                                <li class="active"><a href="#" data-toggle="tab">Atenciones</a></li>
                            </ul>
                            <br>
                            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                            <fieldset>
                                <br>
                                <h3><strong>Crear/Editar atención</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label" for="selectbasic">Personal contactado</label>
                                    <div class="controls">
                                        <select id="selectbasic" name="selectbasic" class="input-xlarge">
                                            <option>Juan Cabello</option>
                                            <option>Arturito Cervelo</option>
                                        </select>
                                    </div>
                                </div>
                                <br> 
                                <div class="control-group">
                                    <label class="control-label">Fecha</label>
                                    <div class="controls">
                                        <input id="fecha" name="full-name" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Hora</label>
                                    <div class="controls">
                                        <input id="hora" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <<div class="control-group">
                                    <label class="control-label" for="selectbasic">Tipo</label>
                                    <div class="controls">
                                        <select id="selectbasic" name="selectbasic" class="input-xlarge">
                                            <option>Presencial</option>
                                            <option>Correo</option>
                                            <option>Teléfono</option>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Detalles de la comunicación</label>
                                    <div class="controls">
                                        <textarea id="detalle" class="input-xlarge" name="message" placeholder="" rows="3" ></textarea>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones</label>
                                    <div class="controls">
                                        <textarea id="obs" class="input-xlarge" name="message" placeholder="" rows="3" ></textarea>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
                                    </div>
                                </div>
                            </fieldset>
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

