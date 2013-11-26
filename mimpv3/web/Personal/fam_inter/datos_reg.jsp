<%-- 
    Document   : estado_proc
    Created on : 7/11/2013, 10:31:03 AM
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
                            <li><a href="#">Actualizar Información</a></li>
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

                    <div class="col-md-8 col-md-offset-1">
                        <!-- AQUI AGREGAR EL CONTENIDO QUE ESTARA AL COSTADO DEL MENU -->
                     <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>   
                    <br>
                    <h3><strong>Datos generales del registro</strong></h3>
                    <br>
                    <br>
                    <form role="form">    
                        <div class="control-group">
                                    <label class="control-label">Número </label>
                                    <div class="controls">
                                        <input id="nombre" name="full-name" type="text" class="input-xlarge">
                                    </div>
                        </div>
                        <br>
                        <div class="control-group">
                                    <label class="control-label">HT </label>
                                    <div class="controls">
                                        <input id="nombre" name="full-name" type="text" class="input-xlarge">
                                    </div>
                        </div>
                        <br>
                        <div class="control-group">
                                    <label class="control-label">Fecha de ingreso</label>
                                    <div class="controls">
                                        <input id="nombre" name="full-name" type="text" class="input-xlarge">
                                    </div>
                        </div>
                        <br>
                        <div class="control-group">
                              <label> Estado</label>
                              <div class="controls">
                               <select>
                                  <option value="sia" selected >Evaluación</option>
                                  <option value="mia" >Observado</option>
                                  <option value="mia" >Culminado</option>
                               </select>
                             </div>   
                        </div>
                        <br>
                        <div class="control-group">
                                    <label class="control-label">Tupa</label>
                                    <div class="controls">
                                        <input id="nombre" name="full-name" type="text" class="input-xlarge">
                                    </div>
                        </div>
                        <br>
                        <div class="control-group">
                              <label> Tipo de familia</label>
                              <div class="controls">
                               <select>
                                  <option value="sia" selected >PP</option>
                                  <option value="mia" >PE</option>
                                  <option value="mia" >MP</option>
                               </select>
                             </div>   
                        </div>
                        <br>
                       <div class="control-group">
                          <label> Tipo lista de espera</label>
                          <div class="controls">
                            <select>
                                  <option value="sia" selected >Nacionales</option>
                                  <option value="mia" >Peruanos residentes en el extranjero</option>
                                  <option value="mia" >Mixtos</option>
                            </select>
                         </div>    
                       </div> 
                         <br>
                        <br>
                       <div class="control-group">
                             <div class="controls">
                                 <button id="singlebutton" type="submit" name="singlebutton" class="btn btn-default">Editar</button>
                                   &nbsp;
                              <button id="singlebutton" name="singlebutton" class="btn btn-default">Registrar información de la familia</button>
                           </div>
                        </div>
                      </form>
                    </div>
                        
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