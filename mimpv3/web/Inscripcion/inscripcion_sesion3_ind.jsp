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
                            <li><a href="${pageContext.servletContext.contextPath}/inicio">Inicio</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/SesionInfInicioPrev">Inscripción a Sesión Informativa</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/CronogramaAnualPrev">Ver el cronograma anual</a></li>
                            <!--<li><a href="#">Información Adicional</a></li>-->
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
                        <form class="form-inline" action="${pageContext.servletContext.contextPath}/inscSesInd" role="form" method="post"  name="formulario" onsubmit="return(validar());" onkeypress="return enter(event)">
                            <input hidden id="idTurno" name="idTurno" value="${turno.getIdturno()}">
                            <input hidden id="estado" name="estado" value="${estado}">
                            
                            <!-- Form Name -->
                            
                            <p class="text-left"><legend>Información Personal</legend></p>
                            <br>
                            <!-- Text input-->
                            <div class="row">
                                <div class="col-lg-4">  
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Nombres <font style="color:red">(*)</font></label>
                                        <div class="controls">
                                            <input id="nombre" name="nombre" type="text" placeholder="Nombres" class="input-xlarge">
                                        </div>
                                    </div>
                                </div>    
                                <!-- Text input-->
                                <div class="col-lg-4">  
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Apellido Paterno <font style="color:red">(*)</font></label>
                                        <div class="controls">
                                            <input id="apellidoP" name="apellidoP" type="text" placeholder="Apellido Paterno" class="input-xlarge">
                                        </div>
                                    </div>
                                </div>        
                                <!-- Text input-->
                                <div class="col-lg-4">  
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Apellido Materno <font style="color:red">(*)</font></label>
                                        <div class="controls">
                                            <input id="apellidoM" name="apellidoM" type="text" placeholder="Apellido Materno" class="input-xlarge">
                                        </div>
                                    </div>
                                </div>     
                            </div>   
                            <br>
                            <div class="row">
                                <div class="col-lg-4">
                                    <label for="pa_nac">País de Nacimiento <font style="color:red">(*)</font></label> 
                                    <input id="paisNac" name="paisNac" type="text" placeholder="Pais" class="input-xlarge">
                                </div>
                                <div class="col-lg-4">
                                    <label for="dep_nac">Departamento de Nacimiento <font style="color:red">(*)</font></label> 
                                    <input id="depNac" name="depNac" type="text" placeholder="Departamento" class="input-xlarge">
                                </div>
                                <div class="col-lg-4">
                                    <label for="dist_nac">Provincia de Nacimiento <font style="color:red">(*)</font></label> 
                                    <input id="proNac" name="proNac" type="text" placeholder="Provincia" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-lg-4">
                                    <label for="fecha">Fecha de Nacimiento <font style="color:red">(*)</font></label> 
                                    <input id="fechaNac" name="fechaNac" type="text" placeholder="dd/mm/yyyy" class="datepicker input-xlarge">
                                </div>
                                <div class="col-lg-4">
                                    <label for="edad">Edad <font style="color:red">(*)</font></label> 
                                    <input id="edad" name="edad" type="text" placeholder="XY" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="doc" id="optionsRadios1" value="d" checked>
                                        DNI
                                    </label>
                                </div>
                                <br>
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="doc" id="optionsRadios2" value="c">
                                        Carnet de Extranjería
                                    </label>
                                </div>                            
                            </div>
                            <br>
                            <div class="control-group">
                                <div class="controls">
                                    <label class="control-label">N° de Documento <font style="color:red">(*)</font></label>
                                    <div class="controls">
                                        <input id="numDoc" name="numDoc" type="text" class="input-xlarge">
                                    </div>
                                </div>
                            </div>                            
                            <br>
                            <!-- Select Basic -->
                            <div class="control-group">
                                <label class="control-label" for="selectbasic">Profesión/Ocupación <font style="color:red">(*)</font></label>
                                <div class="controls">
                                        <input id="profesion" name="profesion" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <!-- Text input-->
                            <div class="control-group">
                                <label class="control-label" for="textinput">Celular <font style="color:red">(*)</font></label>
                                <div class="controls">
                                    <input id="cel" name="cel" type="text" placeholder="Celular" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <!-- Text input-->
                            <div class="control-group">
                                <label class="control-label" for="textinput">Correo Electrónico <font style="color:red">(*)</font></label>
                                <div class="controls">
                                    <input id="correo" name="correo" type="text" placeholder="ejemplo@dominio.com" class="input-xlarge">
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
                                <br>   
                            </div>
                            <br>   
                        </form>
                    </div>
                </div>
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
                
                $('#fechaNac').on('changeDate', function (ev) {
                    
                    var nac =  document.getElementById("fechaNac").value;
                    var edad =  document.getElementById("edad");
                    
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
              
            if( document.formulario.nombre.value == "" )
            {
            alert( "Debe ingresar su nombre" );
             document.formulario.nombre.focus() ;
            return false;
            }
            if( document.formulario.apellidoP.value == "" )
            {
            alert( "Debe ingresar su apellido paterno" );
             document.formulario.apellidoP.focus() ;
            return false;
            }
            if( document.formulario.apellidoM.value == "" )
            {
            alert( "Debe ingresar su apellido materno" );
             document.formulario.apellidoM.focus() ;
            return false;
            }
            if( document.formulario.paisNac.value == "" )
            {
            alert( "Debe ingresar su país de nacimiento" );
             document.formulario.paisNac.focus() ;
            return false;
            }
            if( document.formulario.depNac.value == "" )
            {
            alert( "Debe ingresar su país departamento de nacimiento" );
             document.formulario.depNac.focus() ;
            return false;
            }
            if( document.formulario.proNac.value == "" )
            {
            alert( "Debe ingresar su provincia de nacimiento" );
             document.formulario.proNac.focus() ;
            return false;
            }
            if( document.formulario.fechaNac.value == "" )
            {
            alert( "Debe ingresar su fecha de nacimiento" );
             document.formulario.fechaNac.focus() ;
            return false;
            }
            if( document.formulario.edad.value == "" )
            {
            alert( "Debe ingresar su edad" );
             document.formulario.edad.focus() ;
            return false;
            }
            if( document.formulario.doc.value == "" )
            {
            alert( "Debe elegir su documento de identidad" );
             document.formulario.doc.focus() ;
            return false;
            }
            if( document.formulario.numDoc.value == "" )
            {
            alert( "Debe ingresar su número de documento de identidad" );
             document.formulario.numDoc.focus() ;
            return false;
            }
            if( document.formulario.profesion.value == "" )
            {
            alert( "Debe ingresar su profesión" );
             document.formulario.profesion.focus() ;
            return false;
            }
            if( document.formulario.cel.value == "" )
            {
            alert( "Debe ingresar su número celular" );
             document.formulario.cel.focus() ;
            return false;
            }
            if( document.formulario.correo.value == "" )
            {
            alert( "Debe ingresar su correo electrónico" );
             document.formulario.correo.focus() ;
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
