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
                        <br>
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li ><a href="#" data-toggle="tab">La Solicitante</a></li>
                            <li class="active"><a href="#" data-toggle="tab">El solicitante</a></li>
                            <li><a href="#" data-toggle="tab">Antecedentes del NNA</a></li>
                            <li><a href="#" data-toggle="tab">Información del Expediente</a></li>
                        </ul>
                        <br>
                        <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                        <fieldset>
                            <br>
                            <h3><strong>Generales</strong></h3>
                            <br>
                            <!-- Text input-->
                            <div class="control-group">
                                <label class="control-label">Nombre</label>
                                <div class="controls">
                                    <input id="nombre" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Apellido Paterno</label>
                                <div class="controls">
                                    <input id="apellido_p" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Apellido Materno</label>
                                <div class="controls">
                                    <input id="apellido_m" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Edad</label>  
                                <div class="controls">
                                    <input id="edad" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Fecha de nacimiento</label>
                                <div class="controls">
                                    <input id="fecha_nac" name="full-name" type="password" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Lugar de nacimiento</label>
                                <div class="controls">
                                    <input id="direccion" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Departamento de nacimiento</label>
                                <div class="controls">
                                    <input id="departamento" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">País de nacimiento</label>
                                <div class="controls">
                                    <input id="pais" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-md-2">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios1" id="optionsRadios1" value="D" checked>DNI</label>
                                    </div>
                                </div>   
                                <div class="col-md-3">   
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios1" id="optionsRadios2" value="C">Carnet de Extranjería</label>
                                    </div>                            
                                </div>
                            </div>    
                            <br>
                            <div class="control-group">
                                <div class="controls">
                                    <label class="control-label">N° de Documento</label>
                                    <input id="num_doc" placeholder="Número" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Celular</label>
                                <div class="controls">
                                    <input id="celular" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Correo Electrónico</label>
                                <div class="controls">
                                    <input id="correo" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <h3>Estado Civil</h3>
                            <div class="row">

                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios2" id="soltero" value="D">Soltero</label>
                                    </div>
                                </div>   
                                <div class="col-md-3">   
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios2" id="casado" value="C" checked>Casado</label>
                                        <br>
                                        <label class="control-label">Fecha de matrimonio Civil</label>
                                        <input id="fecha_matrimonio" name="full-name" type="text" class="input-xlarge">
                                    </div>                            
                                </div>
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios2" id="viudo" value="D">Viudo</label>
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios2" id="divorciado" value="D">Divorciado</label>
                                    </div>
                                </div> 
                            </div>    
                            <br>
                            <h3><strong>Educación, Ocupación e Ingresos Económicos</strong></h3>
                            <br>
                            <h3>Nivel de instrucción alcanzado</h3>
                            <div class="row">

                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios3" id="ninguno" value="D" checked>Ninguno</label>
                                    </div>
                                </div>   
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios3" id="primaria" value="D">Primaria</label>
                                    </div>
                                </div>   
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios3" id="secundaria" value="D">Secundaria</label>
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios3" id="tecnico" value="D">Técnico</label>
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios3" id="superior" value="D">Superior</label>
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios3" id="otros" value="D">Otros</label>
                                    </div>
                                </div>   
                            </div> 
                            <br>
                            <h3>Culminó el nivel de instrucción señalado</h3>
                            <div class="row">

                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios4" id="si" value="D" checked>Si</label>
                                    </div>
                                </div>   
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios4" id="no" value="D" >No</label>
                                    </div>
                                </div>  
                            </div> 
                            <br>
                            <div class="control-group">
                                <label class="control-label">Profesión u Oficio </label>
                                <div class="controls">
                                    <input id="profesion" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <h3>Trabajador dependiente (Elegir solo si corresponde)</h3>
                            <div class="row"> 
                                <div class="col-md-3">  
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="checkbox1" id="checkbox1" value="D" >Si</label>
                                    </div>
                                </div>  
                            </div> 
                            <br>
                            <div class="control-group">
                                <label class="control-label">Ocupación Actual</label>
                                <div class="controls">
                                    <input id="ocupacion" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Centro de Trabajo </label>
                                <div class="controls">
                                    <input id="trabajo" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Dirección del centro de trabajo</label>
                                <div class="controls">
                                    <input id="direccion_trabajo" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Teléfono del centro de trabajo </label>
                                <div class="controls">
                                    <input id="telefono_trabajo" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Ingreso mensual sustentable (sueldo bruto) </label>
                                <div class="controls">
                                    <input id="ingreso_depen" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <h3>Trabajador independiente (Elegir solo si corresponde)</h3>
                            <div class="row"> 
                                <div class="col-md-3">  
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="checkbox2" id="checkbox2" value="D">Si</label>
                                    </div>
                                </div>  
                            </div> 
                            <br>
                            <div class="control-group">
                                <label class="control-label">Ocupación Actual</label>
                                <div class="controls">
                                    <input id="ocupacion_indep" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Ingreso mensual sustentable</label>
                                <div class="controls">
                                    <input id="ingreso_indep" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <br>
                            <h3><strong>Salud y Previsión</strong></h3>
                            <br>
                            <h3>Seguro de salud</h3>
                            <div class="row"> 
                                <div class="col-md-2">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios5" id="seguro_si" value="D">Si</label>
                                    </div>
                                </div>  
                                <div class="col-md-2">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios5" id="seguro_no" value="D" checked>No</label>
                                    </div>
                                </div>
                            </div> 
                            <br>
                            <div class="control-group">
                                <label class="control-label">Tipo de Seguro</label>
                                <div class="controls">
                                    <input id="tipo_seguro" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <h3>Seguro de vida</h3>
                            <div class="row"> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios6" id="vida_si" value="D">Si</label>
                                    </div>
                                </div>  
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios6" id="vida_no" value="D" checked>No</label>
                                    </div>
                                </div>
                            </div> 
                            <br>
                            <h3>Afiliado al sistema de pensiones</h3>
                            <div class="row"> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios7" id="afiliado_si" value="D">Si</label>
                                    </div>
                                </div>  
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios7" id="afiliado_no" value="D" checked>No</label>
                                    </div>
                                </div>
                            </div> 
                            <br>
                            <div class="control-group">
                                <label class="control-label">Estado de salud actual</label>
                                <div class="controls">
                                    <input id="estado_acutal" name="full-name" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <p style="color: red">IMPORTANTE: SE HA DESHABILITADO LA VISTA DEBIDO AL ESTADO CIVIL MARCADO EN LA SOLICITANTE</p>
                            <br>
                        </fieldset>
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

