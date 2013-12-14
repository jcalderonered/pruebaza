<%-- 
    Document   : reg_desig
    Created on : 4/12/2013, 11:29:03 AM
    Author     : User
--%>

<%@page import="com.mimp.bean.Personal"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
    Personal u = (Personal) request.getSession().getAttribute("usuario");
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
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/inicioper">Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/act_info">Actualizar Información</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/salir">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div class="container">
                <div class="row">
                    <div class="col-md-4 ">
                        <ul class="nav nav-list well">
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/inicioper"><span class="glyphicon glyphicon-home"></span> Inicio</a></li>
                                <%if (u.getRol().equals("DCRI") || u.getRol().equals("DGA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/inf"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Sesiones/talleres</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNAs</a></li>
                                <%if (u.getRol().equals("admin")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/juzgado"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Juzgado</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ua"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UAs</a></li>
                                <%}
                                if (!u.getRol().equals("DAPA") && !u.getRol().equals("MATCH")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/famint"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                                <%}
                                if (!u.getRol().equals("mpartes")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de Registros</a></li>
                                <%if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/usuarios"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                                <%}
                                if (u.getRol().equals("admin")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Organismo Acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Autoridad Central</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-6 col-md-offset-1">
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="#" data-toggle="tab">NNA Regulares</a></li>
                            <li class="active"><a href="#" data-toggle="tab">NNA Prioritarios</a></li>
                            <li><a href="#" data-toggle="tab">NNA en Seguimiento</a></li>
                        </ul>
                        <br>
                        <br>
                        <h1 align="center"><strong>Familias que conforman el Estudio de Caso</strong></h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="col-sm-2 " >Expediente</th>
                                        <th class="col-sm-2 " >UA</th>
                                        <th class="col-sm-2 " >Nivel sociec</th>
                                        <th class="col-sm-2 " >Información</th>
                                        <th class="col-sm-2 " >Resolución de aptitud</th>
                                        <th class="col-sm-2 " >Estudio de Caso</th>
                                        <th class="col-sm-2 " >Prioridad</th>
                                        <th class="col-sm-2 " >Resultado</th>
                                        <th class="col-sm-2 " >Registrar</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Gutierrez-Huaman </td>
                                        <td>Lima</td>
                                        <td>C</td>
                                        <td><button id="singlebutton" name="singlebutton" class="btn btn-default">Ver</button></td>
                                        <td>14-Nov-2012</td>
                                        <td>14-Dic-2012</td>
                                        <td>1</td>
                                        <td>No aceptado</td>
                                        <td><button disabled id="singlebutton" name="singlebutton" class="btn btn-default">Registrar</button></td>
                                    </tr>
                                    <tr>
                                        <td>Morales-Loza</td>
                                        <td>Trujillo</td>
                                        <td>B</td>
                                        <td><button id="singlebutton" name="singlebutton" class="btn btn-default">Ver</button></td>
                                        <td>21-Agos-2011</td>
                                        <td>21-Set-2011</td>
                                        <td>2</td>
                                        <td>
                                            <select>
                                                <option value="sia">Aceptado</option>
                                                <option value="mia">No aceptado</option>
                                                <option value="sia" selected>Observado</option>
                                                <option value="mia">No observado</option>
                                            </select>
                                        </td>
                                        <td><button id="singlebutton" name="singlebutton" class="btn btn-default">Registrar</button></td>
                                    </tr>
                                    <tr>
                                        <td>Cuadros-Iparraguirre</td>
                                        <td>Lima</td>
                                        <td>C</td>
                                        <td><button id="singlebutton" name="singlebutton" class="btn btn-default">Ver</button></td>
                                        <td>15-Ene-2012</td>
                                        <td>15-Feb-2012</td>
                                        <td>2</td>
                                        <td>No observado</td>
                                        <td><button disabled id="singlebutton" name="singlebutton" class="btn btn-default">Registrar</button></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <br>
                        <h1>Información para la propuesta de adopción</h1>
                        <p>En caso una familia presente una solicitud de adopción (el resultado sea "Aceptado"), llenar la siguiente información:</p>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Fecha de solicitud de adopción</label>
                            <div class="controls">
                                <input id="full-name" name="full-name" type="text" class="datepicker input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">N° de Designación</label>
                            <div class="controls">
                                <input type="text" class="span2" value="" id="designacion" >
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Fecha de propuesta</label>
                            <div class="controls">
                                <input type="text" class="datepicker span2" value="02-16-2012" id="dp3" >
                            </div>
                        </div>
                        <br>
                        <br>
                        <button id="singlebutton" name="singlebutton" class="btn btn-default">Generar Propuesta Directa</button>
                    </div>
                </div>
            </div>
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
        <!-- Bootstrap core JavaScript
         ================================================== -->
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
        <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
        <script type="text/javascript">

            $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

        </script>
        <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>