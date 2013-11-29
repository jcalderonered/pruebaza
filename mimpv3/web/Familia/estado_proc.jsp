<%-- 
    Document   : estado_proc
    Created on : 7/11/2013, 10:31:03 AM
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
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li class="active"><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>

                    <div class="col-md-8 col-md-offset-1">
                        <!-- AQUI AGREGAR EL CONTENIDO QUE ESTARA AL COSTADO DEL MENU -->
                        <br>
                        <h3><strong>Etapas del proceso por las que ha pasado la familia</strong></h3>
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>Asistencia a sesion informativa</strong></h3>
                            </div> 
                            <div class="col-md-3 has-success">
                                <label class="control-label" > SI </label>
                            </div> 
                            <div class="col-md-3">

                            </div> 
                        </div> 
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>Completó talleres</strong></h3>
                            </div> 
                            <div class="col-md-3 has-success">
                                <label class="control-label" > SI </label>
                            </div> 
                            <div class="col-md-3">

                            </div> 
                        </div>  
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>Presentó ficha de inscripción para solicitantes de adopción</strong></h3>
                            </div> 
                            <div class="col-md-1 has-error">
                                <label class="control-label"> NO </label>
                            </div> 
                            <div class="col-md-3">
                                <button id="singlebutton" name="singlebutton" class="btn btn-default">Llenar ficha</button>
                            </div> 
                        </div>  
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>Completó etapa de evaluación</strong></h3>
                            </div> 
                            <div class="col-md-3 has-error">
                                <label class="control-label" > NO </label>
                            </div> 
                            <div class="col-md-3">

                            </div> 
                        </div>    
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>En lista de espera</strong></h3>
                            </div> 
                            <div class="col-md-3 has-error">
                                <label class="control-label" > NO </label>
                            </div> 
                            <div class="col-md-3">

                            </div> 
                        </div>   
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>En proceso de adopción</strong></h3>
                            </div> 
                            <div class="col-md-3 has-error">
                                <label class="control-label" > NO </label>
                            </div> 
                            <div class="col-md-3">

                            </div> 
                        </div> 
                        <br>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <h3><strong>En etapa post-adopción</strong></h3>
                            </div> 
                            <div class="col-md-3 has-error">
                                <label class="control-label" > NO </label>
                            </div> 
                            <div class="col-md-3">

                            </div> 
                        </div>  
                        <br>
                        <br>
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
        <!-- core JavaScript
    ================================================== -->
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
        <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>

        <!-- Ubicar al final -->
    </body>
</html>