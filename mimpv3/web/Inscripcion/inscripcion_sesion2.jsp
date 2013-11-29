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
            <div class="row">
                <div class="col-md-6 col-md-offset-4">
                    <form role="form">
                        <!-- Form Name -->
                        <legend>Estado Civil</legend>
                        <!-- Multiple Radios -->

                        <div class="col-md-1 col-md-offset-4">
                            <div class="control-group">
                                <div class="controls">
                                    <label class="radio" for="radios-0">
                                        <input type="radio" name="radios" id="radios-0" value="Casado" checked="checked">Casado</label>
                                    <label class="radio" for="radios-1">
                                        <input type="radio" name="radios" id="radios-1" value="Soltero">Soltero</label>
                                    <label class="radio" for="radios-2">
                                        <input type="radio" name="radios" id="radios-2" value="Solter">Soltera</label>
                                    <label class="radio" for="radios-3">
                                        <input type="radio" name="radios" id="radios-3" value="Viudo">Viudo</label>
                                    <label class="radio" for="radios-4">
                                        <input type="radio" name="radios" id="radios-4" value="Viuda">Viuda</label>
                                    <label class="radio" for="radios-5">
                                        <input type="radio" name="radios" id="radios-5" value="Divorciado">Divorciado</label>
                                    <label class="radio" for="radios-6">
                                        <input type="radio" name="radios" id="radios-6" value="Divorciada">Divorciada</label>
                                </div>
                            </div>
                            <br>
                            <div class="btn-toolbar">  
                                <button href="#" class="btn btn-default">Continuar</button>
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
                <p align="right">Diseñado por RED<br>www.red.net.pe</p>
            </div>
        </div>
        <!-- core JavaScript
    ================================================== -->
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
        <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>

        <!-- Ubicar al final -->
    </body>
</html>