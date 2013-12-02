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
                        <h1 align="center"><strong>Editar Personal</strong></h1>
                        <br>
                        <c:if test="${personal.getIdpersonal() == null}">
                            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/editPersonal" method="post"> 
                            </c:if>  
                            <c:if test="${personal.getIdpersonal() != null && disabled == 'deshabilitar'}">
                                <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/updatePersonalUa" method="post">
                                    <input hidden name="idPers" id="idPers" value="${personal.getIdpersonal()}">    
                                </c:if> 
                                <c:if test="${personal.getIdpersonal() != null}">
                                    <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/updatePersonal" method="post"> 
                                        <input hidden name="idPers" id="idPers" value="${personal.getIdpersonal()}">
                                    </c:if>  

                                    <fieldset>
                                        <!-- Text input-->
                                        <div class="control-group">
                                            <label class="control-label">Nombre</label>
                                            <div class="controls">
                                                <input id="nombre" name="nombre" type="text" value="${personal.getNombre()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Apellido Paterno</label>
                                            <div class="controls">
                                                <input id="apellidoP" name="apellidoP" type="text" value="${personal.getApellidoP()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Apellido Materno</label>
                                            <div class="controls">
                                                <input  id="apellidoM" name="apellidoM" type="text" value="${personal.getApellidoM()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Usuario</label>
                                            <div class="controls">
                                                <input id="user" name="user" type="text" value="${personal.getUser()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Contraseña</label>
                                            <div class="controls">
                                                <input id="pass" name="pass" type="password" value="${personal.getPass()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Correo Trabajo</label>
                                            <div class="controls">
                                                <input id="correoT" name="correoT" type="text" value="${personal.getCorreoTrabajo()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Correo Personal</label>
                                            <div class="controls">
                                                <input id="correoP" name="correoP" type="text" value="${personal.getCorreoPersonal()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Profesión</label>
                                            <div class="controls">
                                                <input id="profesion" name="profesion" type="text" value="${personal.getProfesion()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Grado de instrucción</label>
                                            <div class="controls">
                                                <input id="grado" name="grado" type="text" value="${personal.getGradoInstruccion()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Cargo</label>
                                            <div class="controls">
                                                <input id="cargo" name="cargo" type="text" value="${personal.getCargo()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">DNI</label>
                                            <div class="controls">
                                                <input  id="dni" name="dni" type="text" value="${personal.getDni()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Fecha de nacimiento</label>
                                            <div class="controls">
                                                <input  id="fechaNac" name="fechaNac" type="text" value="${fechaNac}" class="datepicker input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Regimen</label>
                                            <div class="controls">
                                                <input id="regimen" name="regimen" type="text" value="${personal.getRegimen()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Fecha de ingreso</label>
                                            <div class="controls">
                                                <input id="fechaIng" name="fechaIng" type="text" value="${fechaIng}" class="datepicker input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Domicilio</label>
                                            <div class="controls">
                                                <input id="domicilio" name="domicilio" type="text" value="${personal.getDomicilio()}" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div>
                                            <label class="control-label">Rol</label>
                                            <div class="controls">
                                                <select  id="rol" name="rol">
                                                    <option value="deia" ${personal.getRol() == 'deia' ? 'selected' : ''}>DEIA</option>
                                                    <option value="dcri" ${personal.getRol() == 'dcri' ? 'selected' : ''}>DCRI</option>
                                                </select>
                                            </div>    
                                        </div>
                                        <br>
                                        <div>
                                            <label class="control-label">Unidad de adopción</label>
                                            <div class="controls">
                                                <select id="ua" name="ua" ${disabled == 'deshabilitar' ? 'disabled' : ''}>
                                                    <c:forEach var="ua" items="${listaUa}" varStatus="status"> 
                                                        <option value="${ua.getIdunidad()}" ${ua.getIdunidad() == personal.getUnidad().getIdunidad() ? 'selected' : ''}>${ua.getNombre()}</option>
                                                    </c:forEach> 
                                                </select>
                                            </div>    
                                        </div>
                                        <br>
                                        <br>
                                        <br>
                                        <!-- Button -->
                                        <div class="control-group">
                                            <div class="controls">
                                                <button id="singlebutton" name="singlebutton" class="btn btn-default">Editar</button>
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