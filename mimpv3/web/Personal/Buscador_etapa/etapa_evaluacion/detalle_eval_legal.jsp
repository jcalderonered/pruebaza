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
                            <li class="active"><a href="#" data-toggle="tab">Evaluativa</a></li>
                            <li><a href="#" data-toggle="tab">Adopción</a></li>
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
                                <h3 align="left"><strong>Detalles de la evaluación legal</strong></h3>
                                <p>A continuación se presentará información detallada sobre la evaluación legal desarrollada</p>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha asignación</label>
                                    <div class="controls">
                                        <input type="text" class="datepicker span2" value="02-16-2012" id="dp3" >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Responsable</label>
                                    <div class="controls">
                                        <select>
                                            <option value="sia">Gordon Freeman</option>
                                            <option value="mia">Sofia Lamb</option>
                                            <option value="mia" selected>Carlos Cornejo</option>
                                        </select>
                                    </div>    
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Resultado</label>
                                    <div class="controls">
                                        <input id="resul" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha del resultado</label>
                                    <div class="controls">
                                        <input id="fecha_resul" name="full-name" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Solicitud DGA</label>
                                    <div class="controls">
                                        <input id="sol_dga" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones DGA</label>
                                    <div class="controls">
                                        <input id="obs_dga" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se realizó la Evaluación Social?</label>
                                    <div class="controls">
                                        <input id="eval_social" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones Eval. Social</label>
                                    <div class="controls">
                                        <input id="obs_social" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se realizó la Evaluación Psicológica?</label>
                                    <div class="controls">
                                        <input id="eval_psico" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones Eval. Psicológica</label>
                                    <div class="controls">
                                        <input id="obs_psico" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Cuenta con Certificado domiciliario vigente?</label>
                                    <div class="controls">
                                        <input id="cert_dom" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre el Certificado domiciliario</label>
                                    <div class="controls">
                                        <input id="obs_cert_dom" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se ha hecho presente la Partida de Matrimonio?</label>
                                    <div class="controls">
                                        <input id="part_mat" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre la Partida de Matrimonio</label>
                                    <div class="controls">
                                        <input id="obs_part_mat" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se ha hecho presente la Sentencia de Divorcio?</label>
                                    <div class="controls">
                                        <input id="sent_divor" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre la Sentencia de Divorcio</label>
                                    <div class="controls">
                                        <input id="obs_sent_divor" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se ha hecho presente la Partida de Defunción del Cónyuge?</label>
                                    <div class="controls">
                                        <input id="part_defun_cony" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre la Partida de Defunción del Cónyuge</label>
                                    <div class="controls">
                                        <input id="obs_part_defun_cony" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se ha hecho presente la Partida de Nacimiento del Niño Biológico?</label>
                                    <div class="controls">
                                        <input id="part_nac_bio" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre la Partida de Nacimiento del Niño Biológico</label>
                                    <div class="controls">
                                        <input id="obs_part_nac_bio" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Se ha hecho presente la Partida de Nacimiento del Niño Adoptivo?</label>
                                    <div class="controls">
                                        <input id="part_nac_adop" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre la Partida de Nacimiento del Niño Adoptivo</label>
                                    <div class="controls">
                                        <input id="obs_part_nac_adop" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Presenta reportes de seguimiento post adoptivo que no hayan sido tramitadas por la DGA?</label>
                                    <div class="controls">
                                        <input id="post_dga" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre los seguimientos post adoptivos</label>
                                    <div class="controls">
                                        <input id="obs_post_dga" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Presenta fotografía familiar y su hogar?</label>
                                    <div class="controls">
                                        <input id="foto_fam" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre las fotografias</label>
                                    <div class="controls">
                                        <input id="obs_foto_fam" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">¿Presenta compromiso de seguimiento post adoptivo?</label>
                                    <div class="controls">
                                        <input id="compromiso" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones sobre el compromiso de seguimiento</label>
                                    <div class="controls">
                                        <input id="obs_compromiso" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3><strong>Resoluciones</strong></h3>
                                <br>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Tipo Resolución</th>
                                                <th>Número</th>
                                                <th>Fecha de Resolución</th>
                                                <th>Fecha de Notificación</th>
                                                <th>Editar</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Observado</td>
                                                <td>020273</td>
                                                <td>11-Oct-13</td>
                                                <td>11-Oct-13</td>
                                                <td><button href="#" class="btn btn-default">Ver</button></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <!-- Button -->
                                <div class="control-group">
                                    <div class="controls">
                                        <button id="singlebutton" name="singlebutton" class="btn btn-default">Registrar nueva resolución</button>
                                    </div>
                                </div>
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