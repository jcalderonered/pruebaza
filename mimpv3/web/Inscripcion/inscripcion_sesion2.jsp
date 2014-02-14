<%-- 
    Document   : inscripcion_sesion2
    Created on : 28/10/2013, 05:45:16 AM
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
            <div class="row">
                <div class="col-md-6 col-md-offset-4">
                    <form role="form" action="${pageContext.servletContext.contextPath}/SesionInfEstado2" method="post">
                        <!-- Form Name -->
                        <legend>Estado Civil</legend>
                        <!-- Multiple Radios -->

                        <div class="col-md-1 col-md-offset-4">
                            <div class="control-group">
                                <div class="controls">
                                    <label class="radio" for="radios-0">
                                        <input type="radio" name="estado" id="radios-0" value="casados" checked="checked">Casados</label>
                                    <label class="radio" for="radios-1">
                                        <input type="radio" name="estado" id="radios-1" value="soltero">Soltero</label>
                                    <label class="radio" for="radios-2">
                                        <input type="radio" name="estado" id="radios-2" value="soltera">Soltera</label>
                                    <label class="radio" for="radios-3">
                                        <input type="radio" name="estado" id="radios-3" value="viudo">Viudo</label>
                                    <label class="radio" for="radios-4">
                                        <input type="radio" name="estado" id="radios-4" value="viuda">Viuda</label>
                                    <label class="radio" for="radios-5">
                                        <input type="radio" name="estado" id="radios-5" value="divorciado">Divorciado</label>
                                    <label class="radio" for="radios-6">
                                        <input type="radio" name="estado" id="radios-6" value="divorciada">Divorciada</label>
                                </div>
                            </div>
                            <br>
                            <input hidden id="idTurno" name="idTurno" value="${idTurno}">
                            <div class="btn-toolbar">  
                                <button type="submit" class="btn btn-default">Continuar</button>
                            </div>  
                        </div>
                        <div class="col-md-4"></div>
                        <!-- Button -->

                    </form>
                </div>

            </div>
        </div> 
        <br>
        <br>
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

        <!-- Ubicar al final -->
    </body>
</html>