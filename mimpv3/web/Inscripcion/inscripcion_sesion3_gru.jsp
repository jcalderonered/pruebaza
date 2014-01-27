<%-- 
    Document   : inscripcion_sesion3_ind
    Created on : 28/10/2013, 06:21:34 AM
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
                        <li><a href="${pageContext.servletContext.contextPath}/inicio">Inicio</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/SesionInfInicio">Inscripción a Sesión Informativa</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/CronogramaAnual">Ver el cronograma anual</a></li>
                            <li><a href="#">Información Adicional</a></li>
                            <li><a href="#">Contacto</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>

        <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
        <div id="contenedor1" class="container">
            <p class="text-center"><legend>FORMULARIO DE INSCRIPCIÓN A SESIÓN INFORMATIVA SOBRE ADOPCIÓN   ${turno.getSesion().getNSesion()}</legend></p>
        <br>  
        <br>
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <form class="form-inline" action="${pageContext.servletContext.contextPath}/inscSesGrp" role="form" method="post" name="formulario" onsubmit="return(validar());" onkeypress="return enter(event)">
                    <!-- Form Name -->
                    <input hidden id="idTurno" name="idTurno" value="${turno.getIdturno()}">
                    <input hidden id="estado" name="estado" value="${estado}">
                    <div class="col-md-4 col-md-offset-2">
                        <p class="text-left"><legend>Información Personal <br>(EL)</legend></p>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Nombres <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="nombreEl" name="nombreEl" type="text" placeholder="Nombres" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Apellido Paterno <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="apellidoPEl" name="apellidoPEl" type="text" placeholder="Apellido Paterno" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Apellido Materno <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="apellidoMEl" name="apellidoMEl" type="text" placeholder="Apellido Materno" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="textinput">País de Nacimiento <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="paisNacEl" name="paisNacEl" type="text" placeholder="País " class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="textinput">Departamento de nacimiento <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="depNacEl" name="depNacEl" type="text" placeholder="Departamento" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="textinput">Provincia de nacimiento <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="proNacEl" name="proNacEl" type="text" placeholder="Provincia" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="textinput">Fecha de nacimiento <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="fechaNacEl" name="fechaNacEl" type="text" placeholder="dd/mm/yyyy" class="datepicker input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="textinput">Edad <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="edadEl" name="edadEl" type="text" placeholder="XY" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="docEl" id="optionsRadios1" value="d" checked>
                                    DNI
                                </label>
                            </div>
                            <br>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="docEl" id="optionsRadios2" value="c">
                                    Carnet de Extranjería
                                </label>
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">N° documento <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="numDocEl" name="numDocEl" type="text" placeholder="Numero Documento" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Select Basic -->
                        <div class="control-group">
                            <label class="control-label" for="selectbasic">Profesión/Ocupación <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="profesionEl" name="profesionEl" type="text" placeholder="Profesión" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Celular <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="celEl" name="celEl" type="text" placeholder="Celular" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Correo Electrónico <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="correoEl" name="correoEl" type="text" placeholder="ejemplo@dominio.com" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <br>  
                    </div>
                    <div class="col-md-4 col-md-offset-1">
                        <p class="text-left"><legend>Información Personal <br>(ELLA) </legend></p>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Nombres <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="nombreElla" name="nombreElla" type="text" placeholder="Nombres" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Apellido Paterno <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="apellidoPElla" name="apellidoPElla" type="text" placeholder="Apellido Paterno" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Apellido Materno <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="apellidoMElla" name="apellidoMElla" type="text" placeholder="Apellido Materno" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="textinput">País de Nacimiento <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="paisNacElla" name="paisNacElla" type="text" placeholder="País " class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="textinput">Departamento de nacimiento <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="depNacElla" name="depNacElla" type="text" placeholder="Departamento" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="textinput">Provincia de nacimiento <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="proNacElla" name="proNacElla" type="text" placeholder="Provincia" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="textinput">Fecha de nacimiento <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="fechaNacElla" name="fechaNacElla" type="text" placeholder="dd/mm/yyyy" class="datepicker input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label" for="textinput">Edad <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="edadElla" name="edadElla" type="text" placeholder="XY" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <div class="radio">
                                <label>
                                    <input type="radio" name="docElla" id="optionsRadios1" value="d" checked>
                                    DNI
                                </label>
                            </div>
                            <br>
                            <div class="radio">
                                <label>
                                    <input type="radio" name="docElla" id="optionsRadios2" value="c">
                                    Carnet de Extranjería
                                </label>
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">N° documento <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="numDocElla" name="numDocElla" type="text" placeholder="Numero Documento" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Select Basic -->
                        <div class="control-group">
                            <label class="control-label" for="selectbasic">Profesión/Ocupación <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="profesionElla" name="profesionElla" type="text" placeholder="Profesión" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Celular <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="celElla" name="celELla" type="text" placeholder="Celular" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <!-- Text input-->
                        <div class="control-group">
                            <label class="control-label" for="textinput">Correo Electrónico <font style="color:red">(*)</font></label>
                            <div class="controls">
                                <input id="correoElla" name="correoElla" type="text" placeholder="ejemplo@dominio.com" class="input-xlarge">
                            </div>
                        </div> 
                    </div>
                    <br>
                    <br>

                    <p class="text-left"><legend>Residencia familiar</legend></p>
                    <br>
                    <div class="row">
                        <div class="col-lg-3">
                            <label for="pais">País <font style="color:red">(*)</font></label>
                            <input id="pais" name="pais" type="text" placeholder="Pais" class="input-xlarge">
                        </div>
                        <div class="col-lg-3">
                            <label for="dep">Departamento <font style="color:red">(*)</font></label> 
                            <input id="dep" name="dep" type="text" placeholder="Departamento" class="input-xlarge">
                        </div>
                        <div class="col-lg-3">
                            <label for="prov">Provincia <font style="color:red">(*)</font></label> 
                            <input id="prov" name="prov" type="text" placeholder="Provincia" class="input-xlarge">
                        </div>
                        <div class="col-lg-3">
                            <label for="dist">Distrito <font style="color:red">(*)</font></label> 
                            <input id="dist" name="dist" type="text" placeholder="Distrito" class="input-xlarge">
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-lg-4">
                            <label for="dir">Dirección domiciliaria <font style="color:red">(*)</font></label> 
                            <input id="dir" name="dir" type="text" placeholder="Dirección Domiciliaria" class="input-xlarge">
                        </div>
                        <div class="col-lg-4">
                            <label for="telf">Teléfono fijo <font style="color:red">(*)</font></label> 
                            <input id="telf" name="telf" type="text" placeholder="Teléfono fijo" class="input-xlarge">
                        </div>
                    </div>
                    <br>
                    <!-- Button -->
                    <div class="row">
                        <div class="btn-toolbar">  
                            <button onclick="window.location.href = '${pageContext.servletContext.contextPath}/inicio'" class="btn btn-default">Cancelar</button>
                            <button type="submit" class="btn btn-default">Inscribirse</button>
                        </div> 
                    </div>
                    <br>   
                </form>
            </div>
        </div>
        <p><b>El correo consignado en la columna ELLA será usado para futuras comunicaciones</b></p>
        <br>
        <p style="color: red">(*)IMPORTANTE: ES OBLIGATORIO EL LLENADO DE TODOS LOS CAMPOS</p>
        <br>
        <p>Según lo establecido en la Ley Nº29733 - Ley de protección de datos personales, la información proporcionada por usted/ustedes será de uso exclusivo, con la reserva y confidencialidad del caso, para los fines concernientes al procedimiento administrativo de adopción.</p>                
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

    <!-- core JavaScript
            ================================================== -->
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
    <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
    <script type="text/javascript">

        $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});
        
        $('#fechaNacEl').on('changeDate', function (ev) {
                    
                    var nac =  document.getElementById("fechaNacEl").value;
                    var edad =  document.getElementById("edadEl");
                    
                    var today = new Date();
                    var curr_date = today.getDate();
                    var curr_month = today.getMonth() + 1;
                    var curr_year = today.getFullYear();

                    var pieces = nac.split('/');
                    var birth_date = pieces[0];
                    var birth_month = pieces[1];
                    var birth_year = pieces[2];
                    
                    
                    if (curr_year != birth_year && birth_month > curr_month  ) edad.value = curr_year - birth_year - 1;
                    if (curr_year != birth_year && birth_month == curr_month  ) edad.value = curr_year - birth_year;
                    if (curr_year != birth_year && birth_month < curr_month  ) edad.value = curr_year - birth_year;
                    if (curr_year == birth_year) edad.value = 0;
                     
                     
                        });
                        
         $('#fechaNacElla').on('changeDate', function (ev) {
                    
                    var nac =  document.getElementById("fechaNacElla").value;
                    var edad =  document.getElementById("edadElla");
                    
                    var today = new Date();
                    var curr_date = today.getDate();
                    var curr_month = today.getMonth() + 1;
                    var curr_year = today.getFullYear();

                    var pieces = nac.split('/');
                    var birth_date = pieces[0];
                    var birth_month = pieces[1];
                    var birth_year = pieces[2];
                    
                    
                    if (curr_year != birth_year && birth_month > curr_month  ) edad.value = curr_year - birth_year - 1;
                    if (curr_year != birth_year && birth_month == curr_month  ) edad.value = curr_year - birth_year;
                    if (curr_year != birth_year && birth_month < curr_month  ) edad.value = curr_year - birth_year;
                    if (curr_year == birth_year) edad.value = 0;
                     
                     
                        });               

    </script>
    <script type="text/javascript">
     
            function validar()
            {
            //El  
            if( document.formulario.nombreEl.value == "" )
            {
            alert( "Debe ingresar el nombre del asistente" );
             document.formulario.nombreEl.focus() ;
            return false;
            }
            if( document.formulario.apellidoPEl.value == "" )
            {
            alert( "Debe ingresar el apellido paterno del asistente" );
             document.formulario.apellidoPEl.focus() ;
            return false;
            }
            if( document.formulario.apellidoMEl.value == "" )
            {
            alert( "Debe ingresar el apellido materno del asistente" );
             document.formulario.apellidoMEl.focus() ;
            return false;
            }
            if( document.formulario.paisNacEl.value == "" )
            {
            alert( "Debe ingresar el país de nacimiento del asistente" );
             document.formulario.paisNacEl.focus() ;
            return false;
            }
            if( document.formulario.depNacEl.value == "" )
            {
            alert( "Debe ingresar el departamento de nacimiento del asistente" );
             document.formulario.depNacEl.focus() ;
            return false;
            }
            if( document.formulario.proNacEl.value == "" )
            {
            alert( "Debe ingresar la provincia de nacimiento del asistente" );
             document.formulario.proNacEl.focus() ;
            return false;
            }
            if( document.formulario.fechaNacEl.value == "" )
            {
            alert( "Debe ingresar la fecha de nacimiento del asistente" );
             document.formulario.fechaNacEl.focus() ;
            return false;
            }
            if( document.formulario.edadEl.value == "" )
            {
            alert( "Debe ingresar la edad del asistente" );
             document.formulario.edadEl.focus() ;
            return false;
            }
            if( document.formulario.docEl.value == "" )
            {
            alert( "Debe elegir el tipo de documento del asistente" );
             document.formulario.docEl.focus() ;
            return false;
            }
            if( document.formulario.numDocEl.value == "" )
            {
            alert( "Debe ingresar el número de documento del asistente" );
             document.formulario.numDocEl.focus() ;
            return false;
            }
            if( document.formulario.profesionEl.value == "" )
            {
            alert( "Debe ingresar su profesión" );
             document.formulario.profesionEl.focus() ;
            return false;
            }
            if( document.formulario.celEl.value == "" )
            {
            alert( "Debe ingresar el número celular del asistente" );
             document.formulario.celEl.focus() ;
            return false;
            }
            if( document.formulario.correoEl.value == "" )
            {
            alert( "Debe ingresar el correo electrónico del asistente" );
             document.formulario.correoEl.focus() ;
            return false;
            }
            //Ella
            if( document.formulario.nombreElla.value == "" )
            {
            alert( "Debe ingresar el nombre de la asistente" );
             document.formulario.nombreElla.focus() ;
            return false;
            }
            if( document.formulario.apellidoPElla.value == "" )
            {
            alert( "Debe ingresar el apellido paterno de la asistente" );
             document.formulario.apellidoPElla.focus() ;
            return false;
            }
            if( document.formulario.apellidoMElla.value == "" )
            {
            alert( "Debe ingresar el apellido materno de la asistente" );
             document.formulario.apellidoMElla.focus() ;
            return false;
            }
            if( document.formulario.paisNacElla.value == "" )
            {
            alert( "Debe ingresar el país de nacimiento de la asistente" );
             document.formulario.paisNacElla.focus() ;
            return false;
            }
            if( document.formulario.depNacElla.value == "" )
            {
            alert( "Debe ingresar el departamento de nacimiento de la asistente" );
             document.formulario.depNacEl.focus() ;
            return false;
            }
            if( document.formulario.proNacElla.value == "" )
            {
            alert( "Debe ingresar la provincia de nacimiento de la asistente" );
             document.formulario.proNacElla.focus() ;
            return false;
            }
            if( document.formulario.fechaNacElla.value == "" )
            {
            alert( "Debe ingresar la fecha de nacimiento de la asistente" );
             document.formulario.fechaNacElla.focus() ;
            return false;
            }
            if( document.formulario.edadElla.value == "" )
            {
            alert( "Debe ingresar la edad de la asistente" );
             document.formulario.edadElla.focus() ;
            return false;
            }
            if( document.formulario.docElla.value == "" )
            {
            alert( "Debe elegir el tipo de documento de la asistente" );
             document.formulario.docElla.focus() ;
            return false;
            }
            if( document.formulario.profesionElla.value == "" )
            {
            alert( "Debe ingresar su profesión" );
             document.formulario.profesionElla.focus() ;
            return false;
            }
            if( document.formulario.numDocElla.value == "" )
            {
            alert( "Debe ingresar el número de documento de la asistente" );
             document.formulario.numDocElla.focus() ;
            return false;
            }
            if( document.formulario.celELla.value == "" )
            {
            alert( "Debe ingresar el número celular de la asistente" );
             document.formulario.celELla.focus() ;
            return false;
            }
            if( document.formulario.correoElla.value == "" )
            {
            alert( "Debe ingresar el correo electrónico de la asistente" );
             document.formulario.correoElla.focus() ;
            return false;
            }
            
            if( document.formulario.pais.value == "" )
            {
            alert( "Debe ingresar su país de residencia" );
             document.formulario.pais.focus() ;
            return false;
            }
            if( document.formulario.dep.value == "" )
            {
            alert( "Debe ingresar su departamento de residencia" );
             document.formulario.dep.focus() ;
            return false;
            }
            if( document.formulario.prov.value == "" )
            {
            alert( "Debe ingresar su provincia de residencia" );
             document.formulario.prov.focus() ;
            return false;
            }
            if( document.formulario.dist.value == "" )
            {
            alert( "Debe ingresar su distrito de residencia" );
             document.formulario.dist.focus() ;
            return false;
            }
            if( document.formulario.dir.value == "" )
            {
            alert( "Debe ingresar su dirección domiciliaria" );
             document.formulario.dir.focus() ;
            return false;
            }
            if( document.formulario.telf.value == "" )
            {
            alert( "Debe ingresar su número de teléfono fijo" );
             document.formulario.telf.focus() ;
            return false;
            }
            return( true );
            }
            </script>
            <script type="text/javascript">
                function enter(e) {
                     if (e.keyCode == 13) {
                     return false;
                    }
                }
            </script>
    <!-- Ubicar al final -->
</body>
</html>
