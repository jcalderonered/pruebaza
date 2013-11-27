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
                            <li class="active"><a href="#">Inicio</a></li>
                            <li><a href="#">Ver Información</a></li>
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
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Ficha de inscripción de solicitante de adopción</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li class="active"><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>

                    <div class="col-md-9">
                        <br>
                        <h1 align="center"><strong>Ficha de Inscripción para Solicitantes de Adopción</strong></h1>
                        <br>
                        <div class="row">
                            <div class="col-md-2">
                                <div class="control-group">
                                           <label class="control-label">Fecha</label>
                                            <div class="controls">
                                                <input disabled placeholder="10/11/2013" id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                            </div>
                            <div class="col-md-2 col-md-offset-1">
                                <div class="control-group">
                                           <label class="control-label">Número</label>
                                            <div class="controls">
                                                <input disabled placeholder="001-2013" id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                            </div>
                        </div>
                        <br>
                        <br>
                            
                         <ul class="nav nav-tabs row" id="tabs" >
                            <li class="active"><a href="#" data-toggle="tab">La Solicitante</a></li>
                            <li><a href="#" data-toggle="tab">El solicitante</a></li>
                            <li><a href="#" data-toggle="tab">Composición familiar</a></li>
                            <li><a href="#" data-toggle="tab">Vivienda</a></li>
                            <li><a href="#" data-toggle="tab">Proceso de adopción</a></li>
                            <li><a href="#" data-toggle="tab">Antecedentes del niño, niña o adolescente</a></li>
                        </ul>
                        
                        <form class="form-horizontal"> 
                            <fieldset>
                                <br>
                                <h3><strong>Generales</strong></h3>
                                <br>
                                <!-- Text input-->
                                    <div class="control-group">
                                           <label class="control-label">Nombre</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Apellido Paterno</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                     <div class="control-group">
                                         <label class="control-label">Apellido Materno</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                           <label class="control-label">Edad</label>  
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Fecha de nacimiento</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="password" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Dirección de nacimiento</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Departamento de nacimiento</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">País de nacimiento</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="row">
                                      <div class="col-md-2">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" checked>DNI</label>
                                        </div>
                                       </div>   
                                        <div class="col-md-3">   
                                        <div class="radio">
                                        <label>
                                        <input type="radio" name="optionsRadios" id="optionsRadios2" value="C">Carnet de Extranjería</label>
                                         </div>                            
                                    </div>
                                    </div>    
                                    <br>
                                     <div class="control-group">
                                        <div class="controls">
                                           <label class="control-label">N° de Documento</label>
                                           <input id="n_doc" placeholder="Número" type="text" class="input-xlarge">
                                      </div>
                                      </div>                            
                                     <br>
                                     <div class="control-group">
                                        <label class="control-label">Domicilio (Consignar dirección exacta)</label>
                                            <div class="controls">
                                               <textarea class="input-xlarge" name="message" placeholder="" rows="3" ></textarea>
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Teléfono </label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Celular</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Correo Electrónico</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <h3>Estado Civil</h3>
                                         <div class="row">
                                        
                                      <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" checked>Soltera</label>
                                        </div>
                                       </div>   
                                        <div class="col-md-3">   
                                        <div class="radio">
                                        <label>
                                        <input type="radio" name="optionsRadios" id="optionsRadios2" value="C">Casada</label>
                                        <br>
                                        <label class="control-label">Fecha de matrimonio Civil</label>
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                         </div>                            
                                         </div>
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" checked>Viuda</label>
                                        </div>
                                       </div> 
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" checked>Divorciada</label>
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
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" checked>Ninguno</label>
                                        </div>
                                       </div>   
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" checked>Primaria</label>
                                        </div>
                                       </div>   
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" checked>Secundaria</label>
                                        </div>
                                       </div> 
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" checked>Técnico</label>
                                        </div>
                                       </div> 
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" checked>Superior</label>
                                        </div>
                                       </div> 
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" checked>Otros</label>
                                        </div>
                                       </div>   
                                    </div> 
                                    <br>
                                    <h3>Culminó el nivel de instrucción señalado</h3>
                                    <div class="row">
                                        
                                      <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Si</label>
                                        </div>
                                       </div>   
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >No</label>
                                        </div>
                                       </div>  
                                    </div> 
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Profesión u Oficio </label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <h3>Trabajadora dependiente (Elegir solo si corresponde)</h3>
                                    <div class="row"> 
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Si</label>
                                        </div>
                                       </div>  
                                    </div> 
                                    <br>
                                     <div class="control-group">
                                        <label class="control-label">Ocupación Actual</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                     <div class="control-group">
                                        <label class="control-label">Centro de Trabajo </label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                     <div class="control-group">
                                        <label class="control-label">Dirección del centro de trabajo</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                     <div class="control-group">
                                        <label class="control-label">Teléfono del centro de trabajo </label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                     <div class="control-group">
                                        <label class="control-label">Ingreso mensual sustentable (sueldo bruto) </label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <h3>Trabajadora independiente (Elegir solo si corresponde)</h3>
                                    <div class="row"> 
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D">Si</label>
                                        </div>
                                       </div>  
                                    </div> 
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Ocupación Actual</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Ingreso mensual sustentable</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
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
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D">Si</label>
                                        </div>
                                       </div>  
                                        <div class="col-md-2">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D">No</label>
                                        </div>
                                       </div>
                                    </div> 
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Tipo de Seguro</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <h3>Seguro de vida</h3>
                                    <div class="row"> 
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D">Si</label>
                                        </div>
                                       </div>  
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D">No</label>
                                        </div>
                                       </div>
                                    </div> 
                                    <br>
                                    <h3>Afiliada al sistema de pensiones</h3>
                                    <div class="row"> 
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D">Si</label>
                                        </div>
                                       </div>  
                                        <div class="col-md-3">  
                                        <div class="radio">
                                           <label>
                                           <input type="radio" name="optionsRadios" id="optionsRadios1" value="D">No</label>
                                        </div>
                                       </div>
                                    </div> 
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Estado de salud actual</label>
                                            <div class="controls">
                                              <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                            </div>
                                    </div>
                                    <br>
                                    <p style="color: red">IMPORTANTE: FAVOR DE LLENAR TODOS LOS CAMPOS</p>
                                      <br>
                                     <p>Según lo establecido en la Ley Nº29733 - Ley de protección de datos personales, la información proporcionada por 
                                      usted/ustedes será de uso exclusivo, con la reserva y confidencialidad del caso, para los fines concernientes al 
                                     procedimiento administrativo de adopción.
                                    </p>
                                <!-- Button -->
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">confirmar</button>
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