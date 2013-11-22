<%-- 
    Document   : inscripcion_sesion1
    Created on : 28/10/2013, 05:45:16 AM
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
                            <li><a href="#">Inicio</a></li>
                            <li class="active"><a href="#">Inscripción a Sesión Informativa</a></li>
                            <li><a href="#">Información Adicional</a></li>
                            <li><a href="#">Contacto</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>

            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <h2 align="center">Las inscripciones están cerradas, agradecemos su interés y le informamos que la próxima sesión informativa dará inicio el:</h2>
            <br>
            <h2 align="center"><b>13-Nov-2013 17:30</b></h2>
            <br>
            <h2 align="center">Los turnos de inscripción para la próxima sesión informativa son según el siguiente cronograma</h2>
            <h2 align="center">Se le invita a inscribirse hasta que se completen las vacantes</h2>
            <br>
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Día Inscripción</th>
                                    <th>Inicio Inscripciones</th>
                                    <th>Fin Inscripciones</th>
                                    <th>Total de vacantes</th>
                                </tr>
                            </thead>

                            <tbody>
                                <tr>
                                    <th>Turno 1</th>
                                    <td>10-Nov-2013</td>
                                    <td>09:00</td>
                                    <td>12:00</td>
                                    <td>90</td>
                                </tr>
                                <tr>
                                    <th>Turno 2</th>
                                    <td>10-Nov-2013</td>
                                    <td>18:00</td>
                                    <td>20:00</td>
                                    <td>60</td>
                                </tr>
                                <tr>
                                    <th>Turno 3</th>
                                    <td>11-Nov-2013</td>
                                    <td>09:00</td>
                                    <td>12:00</td>
                                    <td>30</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <h2 align="center">Las próximas sesiones informativas dentro del año son las siguientes:</h2>
            <br>
            <h2 align="center"><b>20-Nov-2013 17:30</b></h2>
            <h2 align="center"><b>15-Dic-2013 17:30</b></h2>
            <br>
            <!--FIN DE CONTENIDO-->
            <br>
            <br>
        </div>   
        <div id="footer">
            <div id="ja-footer" class="wrap">
                <hr width=80% align="center">
                <p align="center"><h5 class="caption" align="center" style="text-align: center;">MINISTERIO DE LA MUJER Y POBLACIONES VULNERABLES<br>Jr. Camaná 616, Lima - Perú<br>Central telefónica: (511) 626-1600</h5></p>
                <p align="right">Diseñado por RED<br>www.red.net.pe</p>
            </div>
        </div>

    </body>
</html>