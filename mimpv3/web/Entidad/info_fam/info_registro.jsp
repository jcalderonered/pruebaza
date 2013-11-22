<%-- 
    Document   : info_fam
    Created on : 7/11/2013, 11:46:35 AM
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
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <br>
                        <h1 align="center"><strong>Familia "ApellidoP-ApellidoM"</strong></h1>
                        <br>
                        <br>
                        <h3 align="left"><strong>Datos del Expediente</strong></h3>
                        <br>
                        <!--
                        <div class="row">
                            <div class="col-md-3">
                                <label class="control-label">Número</label>
                                <div class="controls">
                                    <input disabled id="nombre" name="full-name" value="00293-12442" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <div class="col-md-3">
                                <label class="control-label">Fecha de ingreso</label>
                                <div class="controls">
                                    <input disabled id="nombre" name="full-name" value="11-Nov-13" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <div class="col-md-3">
                                <label class="control-label">HT </label>
                                <div class="controls">
                                    <input disabled id="nombre" name="full-name" value="HR" type="text" class="input-xlarge">
                                </div>
                            </div>
                        </div> 
                        <br>
                        -->
                        <br>
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li ><a href="#" data-toggle="tab">La Solicitante</a></li>
                            <li ><a href="#" data-toggle="tab">El solicitante</a></li>
                         <!--   <li><a href="#" data-toggle="tab">Composición familiar</a></li> -->
                         <!--   <li><a href="#" data-toggle="tab">Vivienda</a></li> -->
                            <li><a href="#" data-toggle="tab">Antecedentes del NNA</a></li>
                            <li class="active"><a href="#" data-toggle="tab">Información del Expediente</a></li>
                            
                        </ul>
                        <br>
                        <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                        <h3><strong>Información del Expediente</strong></h3>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Número</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="00198" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Expediente</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="ApellidoP-ApellidoM" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">HT</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="AIEDGLA-097" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Número de Expediente</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="1-2013-MIMP-DGA-LIMA" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Fecha ingreso a DGA</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="11-Nov-2013" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Estado</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="En evaluación" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">TUPA</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="24-Dic-13" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Nacionalidad</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="Nacional" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Pertenece al RNSA: SI</label>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Pertenece al RNAA: SI</label>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Tipo de familia</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="PP" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Tipo de lista de espera</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="Nacionales" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Unidad de Adopción donde se realiza el trámite</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="LIMA" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Organismo Acreditado y/o Autoridad Central asociado</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="ItalianBaby International" type="text" class="input-xlarge">
                            </div>
                        </div>
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
