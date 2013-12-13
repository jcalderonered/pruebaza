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
                        <li><a href="#">Inicio</a></li>
                        <li class="active"><a href="#">Inscripción a Sesión Informativa</a></li>
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
                <form class="form-inline" action="${pageContext.servletContext.contextPath}/inscSesGrp" role="form" method="post">
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
                                <select id="profesionEl" name="profesionEl" class="input-xlarge">
                                    <option value="ingeniero">Ingeniero</option>
                                    <option value="abogado">Abogado</option>
                                    <option value="psicologo">Psicólogo</option>
                                    <option value="otro">Otro</option>
                                </select>
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
                                <select id="profesionElla" name="profesionElla" class="input-xlarge">
                                    <option value="ingeniero">Ingeniero</option>
                                    <option value="abogado">Abogado</option>
                                    <option value="psicologo">Psicólogo</option>
                                    <option value="otro">Otro</option>
                                </select>
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

    </script>
    <!-- Ubicar al final -->
</body>
</html>
