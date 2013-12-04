<%-- 
    Document   : inicio_personal
    Created on : 28/10/2013, 09:54:29 AM
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

                    <div class="col-md-6 col-md-offset-1">
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p> 
                        <h1 align="center"><strong>Expediente del NNA</strong></h1>
                        <br>
                        <form class="form-horizontal">
                            <fieldset>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label">Unidad de adopción</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" placeholder="LIMA" class="input-xlarge" disabled >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Número</label>
                                    <div class="controls">
                                        <input  id="full-name" name="full-name" type="text" placeholder="LIMA-001-2013" class="input-xlarge" disabled >
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de ingreso</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">HT</label>
                                    <div class="controls">
                                        <input  id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>                                                                    
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Número de expediente tutelar</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Procedencia tutelar</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Juzgado</label>
                                    <div>
                                    <select>
                                        <option value="sia">Nombre Juzgado 1 - Distrito Judicial 1</option>
                                        <option value="sia">Nombre Juzgado 2 - Distrito Judicial 2</option>
                                        
                                    </select>
                                    </div>     
                                </div>
                                <br>
                                <br>
                                <h3><strong>Ficha Integral</strong></h3>
                                <br>
                                <!-- Text input-->
                                <div class="row">
                                    <div class="col-md-2">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Ingreso</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="C">Solicitado</label>
                                        </div>                            
                                    </div>
                                </div>  
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Comentarios</label>
                                    <div class="controls">
                                        <textarea class="input-xlarge" name="message" placeholder="" rows="5" ></textarea>
                                    </div>
                                </div>
                                <br>
                                <h3><strong>Responsable Legal</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Nombre Completo</label>
                                    <div class="controls">
                                        <select>
                                            <option value="sia">Juan Luis</option>
                                            <option value="sia">Ronald</option>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <!--
                               <div class="control-group">
                                   <label class="control-label">Apellido Paterno</label>
                                   <div class="controls">
                                       <select>
                                           <option value="sia">Espinoza</option>
                                           <option value="sia">Salgado</option>
                                       </select>
                                   </div>
                               </div>
                               <br>
                               <div class="control-group">
                                   <label class="control-label">Apellido Materno</label>
                                   <div class="controls">
                                       <select>
                                           <option value="sia">Salazar</option>
                                           <option value="sia">Ruiz</option>
                                       </select>
                                   </div>
                               </div>
                               <br>
                                -->
                                <h3><strong>Responsable Psicosocial</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Nombre Completo</label>
                                    <div class="controls">
                                        <select>
                                            <option value="sia">Juan Luis</option>
                                            <option value="sia">Ronald</option>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <!--
                                <div class="control-group">
                                    <label class="control-label">Apellido Paterno</label>
                                    <div class="controls">
                                        <select>
                                            <option value="sia">Espinoza</option>
                                            <option value="sia">Salgado</option>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Materno</label>
                                    <div class="controls">
                                        <select>
                                            <option value="sia">Salazar</option>
                                            <option value="sia">Ruiz</option>
                                        </select>
                                    </div>
                                </div>
                                <br>
                                -->
                                <div>
                                    <label class="control-label">Estado</label>
                                    <div class="controls">
                                        <select>
                                            <option value="sia">Evaluación</option>
                                            <option value="sia">Designado</option>
                                            <option value="mia">Adoptado</option>
                                            <option value="sia">Archivado</option>
                                        </select>
                                    </div>    
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha del estado</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div>
                                    <label class="control-label">Clasificación</label>
                                    <div class="controls">
                                        <select  >
                                            <option value="sia">Prioritario</option>
                                            <option value="mia">Seguimiento</option>
                                            <option value="mia">Regular</option>
                                        </select>
                                    </div>    
                                </div>
                                <br>
                                <br>
                                <h3><strong>Datos de NNA prioritario</strong></h3>
                                <br>
                                <div>
                                    <label class="control-label">Grupo de referencia</label>
                                    <div class="controls">
                                        <select>
                                            <option value="sia">Mayores</option>
                                            <option value="sia">Adolescentes</option>
                                            <option value="mia">Hermanos</option>
                                            <option value="mia">Salud</option>
                                            <option value="mia">Seguimiento</option>
                                        </select>
                                    </div>  
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Código Mayores</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Código Adolescente</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Código Hermano</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Código Salud</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Código Seguimiento</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Diagnóstico</label>
                                    <div class="controls">
                                        <input id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Comentario</label>
                                    <div class="controls">
                                        <textarea id="full-name" name="full-name" type="text" class="input-xlarge"></textarea>
                                    </div>
                                </div>
                                <br>
                                <!-- Button -->
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <button id="singlebutton" name="singlebutton" class="btn btn-default">Editar</button>
                                        &nbsp;
                                        <button id="singlebutton" name="singlebutton" class="btn btn-default">Propuesta de Designación</button>
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
            <!-- Bootstrap core JavaScript
        ================================================== -->
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
            <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
            <script type="text/javascript">

                $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

            </script>
            <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>