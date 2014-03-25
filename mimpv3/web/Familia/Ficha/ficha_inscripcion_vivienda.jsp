<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Familia"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    
    response.setDateHeader("Expires", 0);
    Familia u = (Familia) request.getSession().getAttribute("usuario");
    if (u == null) {
%>
<jsp:forward page="/salir"/>
<% }%>
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
                        </button>
                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-left">
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/inicioFam">Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc1">Ver Información</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/salir">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div class="container">
                <div class="row">
                    <div class="col-md-3 ">
                        <ul class="nav nav-list well">
                            <li><a href="${pageContext.servletContext.contextPath}/inicioFam"><span class="glyphicon glyphicon-home"></span>Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Finscripcion"><span class="glyphicon glyphicon-chevron-right"></span> Inscripción a talleres</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Festado"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fcontra"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
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
                                        <input disabled value="${factual}" id="full-name" name="fecha_ingreso" type="text" class="input-xlarge">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2 col-md-offset-1">
                                <div class="control-group">
                                    <label class="control-label">Número</label>
                                    <div class="controls">
                                        <input disabled id="full-name" name="numero" type="text" class="input-xlarge">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="${pageContext.servletContext.contextPath}/Fficha/opc1">La Solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fficha/opc2">El solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fficha/opc3">Composición familiar</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/Fficha/opc4">Vivienda</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fficha/opc5">Proceso de adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fficha/opc6" data-toggle="tab">Antecedentes del niño, niña o adolescente</a></li>
                        </ul>
                        <form class="form-horizontal"> 
                            <fieldset>
                                <br>
                                <h3><strong>Vivienda</strong></h3>
                                <br>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="propiedad_vivienda" id="optionsRadios1" value="Propia" checked>Propia</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="propiedad_vivienda" id="optionsRadios2" value="Alquilada" >Alquilada</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="propiedad_vivienda" id="optionsRadios3" value="Otros">Otros</label>
                                            <br>
                                            <input name="propiedad_vivienda_otros" id="propiedad_vivienda_otros" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <br>
                                <h3><strong>Tipo</strong></h3>
                                <br>
                                <!-- Text input-->
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="tipo_vivienda" id="optionsRadios1" value="Casa" >Casa</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="tipo_vivienda" id="optionsRadios2" value="Departamento" >Departamento</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" onclick="dependiente(this.value)" name="tipo_vivienda" id="optionsRadios3" value="Otros">Otros(especificar)</label>
                                            <br>
                                            <input id="full-name" name="tipo_vivienda_otros" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <br>
                                <br>
                                <h3><strong>Área de vivienda(en metros cuadrados)</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Total</label>
                                    <div class="controls">
                                        <input id="full-name" name="area_viv_total" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Construida</label>
                                    <div class="controls">
                                        <input id="full-name" name="area_viv_const" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Distribución de la vivienda</label>
                                    <div class="controls">
                                        <textarea id="dist_vivienda" class="input-xlarge" name="dist_vivienda" rows="5"></textarea>
                                    </div>
                                </div> 
                                <br>
                                <br>
                                <h3><strong>Servicios</strong></h3>
                                <br>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="checkbox" name="luz" id="luz" value="0" >Energía Eléctrica</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">    
                                        <div class="radio">
                                            <label>
                                                <input type="checkbox" name="agua" id="agua" value="0" >Agua Potable</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">    
                                        <div class="radio">
                                            <label>
                                                <input type="checkbox" name="desague" id="desague" value="0" >Desague</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="checkbox" name="otros_serv" id="optionsRadios2" value="0">Otros servicios</label>
                                            <br>
                                            <input id="full-name" name="otros_serv" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <h3><strong>Material de construcción</strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="mater_cons" id="optionsRadios1" value="Noble" >Noble</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="mater_cons" id="optionsRadios2" value="Otros">Otros</label>
                                            <br>

                                            <input id="full-name" name="mater_cons_otros" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <h3><strong>Paredes</strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="pared" id="optionsRadios1" value="Ladrillo" >Ladrillo</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="pared" id="optionsRadios2" value="Otros">Otros</label>
                                            <br>
                                            <input id="full-name" name="pared_otros" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>

                                <h3><strong>Techo</strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="techo" id="optionsRadios1" value="Concreto" >Concreto</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="techo" id="optionsRadios2" value="Otros">Otros</label>
                                            <br>
                                            <input id="full-name" name="techo_otros" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <h3><strong>Piso</strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="piso" id="optionsRadios1" value="Cemento" >Cemento</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="piso" id="optionsRadios2" value="Otros">Otros</label>
                                            <br>
                                            <input id="full-name" name="piso_otros" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <br>
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
                                        <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar Cambios</button>
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