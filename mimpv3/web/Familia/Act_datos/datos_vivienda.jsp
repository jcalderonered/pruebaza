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
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>

                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-left">
                            <li><a href="#">Inicio</a></li>
                            <li class="active"><a href="#">Actualizar Información</a></li>
                            <li><a href="#">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>

            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div class="container">
                <div class="row">
                    <div class="col-md-3 ">
                        <ul class="nav nav-list well">
                            <li><a href=""><span class="glyphicon glyphicon-home"></span>  Inicio</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Inscripción a talleres</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>

                    <div class="col-md-9">
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="#" data-toggle="tab">La Solicitante </a></li>
                            <li><a href="#" data-toggle="tab">El solicitante</a></li>
                            <li><a href="#" data-toggle="tab">Composición familiar</a></li>
                            <li class="active"><a href="#" data-toggle="tab">Vivienda</a></li>
                            <li><a href="#" data-toggle="tab">Antecedentes del niño, niña o adolescente</a></li>
                        </ul>
                        <form class="form-horizontal"> 
                            <fieldset>
                                <br>
                                <h3><strong>Vivienda <font style="color:red">(*)</font></strong></h3>
                                <br>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Propia</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Alquilada</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="C">Otros</label>
                                            <br>

                                            <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <br>
                                <h3><strong>Tipo <font style="color:red">(*)</font></strong></h3>
                                <br>
                                <!-- Text input-->
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Casa</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Departamento</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="C">Otros(especificar)</label>
                                            <br>

                                            <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <br>
                                <br>
                                <h3><strong>Domicilio<font style="color:red">(*)</font></strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Dirección (Consignar dirección exacta)</label>
                                    <div class="controls">
                                        <textarea id="domicilio" class="input-xlarge" name="message" placeholder="" rows="3" ></textarea>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Departamento de Residencia</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">País de Residencia</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Teléfono</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <br>
                                <h3><strong>Área de vivienda(en metros cuadrados)<font style="color:red">(*)</font></strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Total</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Construida</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Distribución de la vivienda</label>
                                    <div class="controls">
                                        <textarea id="domicilio" class="input-xlarge" name="message" placeholder="" rows="3" ></textarea>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <h3><strong>Servicios <font style="color:red">(*)</font></strong></h3>
                                <br>

                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" name="optionsRadios" id="optionsRadios1" value="D" >Energía Eléctrica</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">    
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" name="optionsRadios" id="optionsRadios1" value="D" >Agua Potable</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">    
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" name="optionsRadios" id="optionsRadios1" value="D" >Desague</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">   
                                        <div class="control-group">
                                            <label class="control-label">Otros servicios</label>
                                            <div class="controls">
                                                <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <h3><strong>Material de construcción <font style="color:red">(*)</font></strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Noble</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="C">Otros</label>
                                            <br>

                                            <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>

                                <h3><strong>Paredes <font style="color:red">(*)</font></strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Ladrillo</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="C">Otros</label>
                                            <br>

                                            <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>

                                <h3><strong>Techo <font style="color:red">(*)</font></strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Concreto</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="C">Otros</label>
                                            <br>

                                            <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>

                                <h3><strong>Piso <font style="color:red">(*)</font></strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Cemento</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="C">Otros</label>
                                            <br>

                                            <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <br>
                                <p style="color: red">(*)IMPORTANTE: NO SE PUEDEN DEJAR CAMPOS EN BLANCO</p> 
                                <br>
                                <p>Según lo establecido en la Ley Nº29733 - Ley de protección de datos personales, la información proporcionada por 
                                    usted/ustedes será de uso exclusivo, con la reserva y confidencialidad del caso, para los fines concernientes al 
                                    procedimiento administrativo de adopción.
                                </p>
                                <!-- Button -->
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
                                    </div>
                                </div>
                            </fieldset>
                        </form>   

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