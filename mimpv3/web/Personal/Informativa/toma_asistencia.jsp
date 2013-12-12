<%-- 
    Document   : inicio_personal
    Created on : 28/10/2013, 09:54:29 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Personal"%>
<%
response.setHeader( "Pragma", "no-cache" );
response.addHeader( "Cache-Control", "must-revalidate" );
response.addHeader( "Cache-Control", "no-cache" );
response.addHeader( "Cache-Control", "no-store" );
response.setDateHeader("Expires", 0);
Personal u=(Personal)request.getSession().getAttribute("usuario");
if (u==null){
%>
<jsp:forward page="/salir"/>
<% } %>
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
                        <p class="text-left"><h1><strong> Listado de Asistentes</strong> </h1></p>
                        <br>

                        <div class="controls">
                            <label class="control-label" for="textinput">Nombre de evento&nbsp</label>
                            <input id="textinput" name="textinput" disabled type="text" placeholder="Taller Primera adopción" class="input-xlarge">
                        </div>
                        <br>
                        <div class="controls">
                            <label class="control-label" for="textinput">Tipo de evento &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  </label>
                            <input id="textinput" name="textinput" disabled type="text" placeholder="Taller" class="input-xlarge">
                        </div>
                        <br>
                        <div class="controls">
                            <label class="control-label" for="textinput">Grupo &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  </label>
                            <input id="textinput" name="textinput" disabled type="text" placeholder="1" class="input-xlarge">
                        </div>
                        <br>
                        <div class="controls">
                            <label class="control-label" for="textinput">Turno &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp  </label>
                            <input id="textinput" name="textinput" disabled type="text" placeholder="Mañana" class="input-xlarge">
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-md-4">
                                <label class="control-label" for="textinput">Fecha &nbsp</label>
                                <input id="textinput" name="textinput" disabled type="text" placeholder="28/10/2013" class="input-xlarge">
                            </div>
                            <div class="col-md-4">
                                <label class="control-label" for="textinput">Hora &nbsp</label>
                                <input id="textinput" name="textinput" disabled type="text" placeholder="16:30" class="input-xlarge">
                            </div>

                        </div>
                        <br>
                        <br>

                        <div class="bs-example">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>El asistente</th>
                                        <th>Edad</th>
                                        <th>La asistente</th>
                                        <th>Edad</th>
                                        <th>Correo</th>
                                        <th>Información</th>
                                        <th>Asistencia</th>
                                        <th>Inasistencia Justificada</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Juan Alvarado Perez</td>
                                        <td>40</td>
                                        <td>María Gutierrez Espinoza</td>
                                        <td>43</td>
                                        <td>correo@dominio.com</td>
                                        <td><button href="#" class="btn btn-default">Ver</button></td>
                                        <td>
                                            <div class="checkbox">
                                                <label for="checkboxes-0">
                                                    <input type="checkbox" name="checkboxes" id="checkboxes-0" value="1">
                                                    Asistió
                                                </label>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="checkbox">
                                                <label for="checkboxes-0">
                                                    <input type="checkbox" name="checkboxes" id="checkboxes-0" value="1">
                                                    Justificado
                                                </label>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Miguel Rosa Flores</td>
                                        <td>35</td>
                                        <td>Marta Gadea Chávez</td>
                                        <td>40</td>
                                        <td>correo@dominio.com</td>
                                        <td><button href="#" class="btn btn-default">Ver</button></td>
                                        <td>
                                            <div class="checkbox">
                                                <label for="checkboxes-0">
                                                    <input type="checkbox" name="checkboxes" id="checkboxes-0" value="1">
                                                    Asistió
                                                </label>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="checkbox">
                                                <label for="checkboxes-0">
                                                    <input type="checkbox" name="checkboxes" id="checkboxes-0" value="1">
                                                    Justificado
                                                </label>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Marco Durando Miriarte</td>
                                        <td>53</td>
                                        <td>Rosa Miroquezada Cabello</td>
                                        <td>50</td>
                                        <td>correo@dominio.com</td>
                                        <td><button href="#" class="btn btn-default">Ver</button></td>
                                        <td>
                                            <div class="checkbox">
                                                <label for="checkboxes-0">
                                                    <input type="checkbox" name="checkboxes" id="checkboxes-0" value="1">
                                                    Asistió
                                                </label>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="checkbox">
                                                <label for="checkboxes-0">
                                                    <input type="checkbox" name="checkboxes" id="checkboxes-0" value="1">
                                                    Justificado
                                                </label>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>Gabriel Solano Parco </td>
                                        <td>20</td>
                                        <td>Gina Torre Rojas</td>
                                        <td>18</td>
                                        <td>correo@dominio.com</td>
                                        <td><button href="#" class="btn btn-default">Ver</button></td>
                                        <td>
                                            <div class="checkbox">
                                                <label for="checkboxes-0">
                                                    <input type="checkbox" name="checkboxes" id="checkboxes-0" value="1">
                                                    Asistió
                                                </label>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="checkbox">
                                                <label for="checkboxes-0">
                                                    <input type="checkbox" name="checkboxes" id="checkboxes-0" value="1">
                                                    Justificado
                                                </label>
                                            </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div><!-- /example -->
                        <br>
                        <p>IMPORTANTE: Una vez guardado, no se podrán hacer cambios</p>
                        <br>
                        <div class="row">
                            <div class="col-md-8 col-md-offset-5" > 
                                <div class="btn-toolbar">
                                    <button href="#" class="btn btn-default">Guardar cambios</button>
                                </div> 
                                <br>   
                            </div>
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