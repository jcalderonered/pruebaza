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
                                <h3 align="left"><strong>Detalles de la evaluación</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Tipo de Evaluación</label>
                                    <div class="controls">
                                        <input id="tipo_eval" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha asignación</label>
                                    <div class="controls">
                                        <input type="text" class="span2" value="02-16-2012" id="dp3" >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Responsable</label>
                                    <div class="controls">
                                        <input id="responsable" name="full-name" type="text" class="input-xlarge">
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
                                        <input id="fecha_resul" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3 align="left"><strong>Detalles de la evaluación legal</strong></h3>
                                <p>A continuación se presentará información detallada sobre la evaluación legal desarrollada</p>
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