<%-- 
    Document   : inscripcion_sesion1
    Created on : 28/10/2013, 05:45:16 AM
    Author     : Ayner Pérez
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
                    <div class="col-md-6 ">
                        <h1 align="center"><strong>Buscador de Registro por Etapa</strong></h1>
                        <br>
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="#" data-toggle="tab">Informativa</a></li>
                            <li><a href="#" data-toggle="tab">Evaluativa</a></li>
                            <li ><a href="#" data-toggle="tab">Designación</a></li>
                            <li class="active"><a href="#" data-toggle="tab">Adopción</a></li>
                            <li ><a href="#" data-toggle="tab">Post Adopción</a></li>
                        </ul>
                        <form class="form-horizontal">
                            <fieldset>
                                <br>
                                <div class="bs-example">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>Expediente</th>
                                                <th>Info</th>
                                                <th>N° Propuesta</th>
                                                <th>Prioridad</th>
                                                <th>Info NNA</th>
                                                <th>Informe de Empatía</th>
                                                <th>Resultado</th>
                                                <th>Resolución</th>
                                                <th>Informe Colocación</th>
                                                <th>Resultado</th>
                                                <th>Resolución</th>  
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Alvarado-Gutierrez</td>
                                                <td><button href="#" class="btn btn-default">Ver</button></td>
                                                <td>001-2010</td>
                                                <td>1</td>
                                                <td><button href="#" class="btn btn-default">Ver</button></td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                                <td>Favorable</td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                                <td>Favorable</td>
                                                <td><button href="#" class="btn btn-default ">Registrar</button></td>
                                            </tr>
                                            <tr style="background: #BDBDBD">
                                                <td>Rosa-Flores</td>
                                                <td><button href="#" class="btn btn-default">Ver</button></td>
                                                <td rowspan="2" style="vertical-align: middle">001-2012</td>
                                                <td>2</td>
                                                <td rowspan="2" style="vertical-align: middle"><button href="#" class="btn btn-default">Ver</button></td>
                                                <td><button href="#" class="btn btn-default" disabled>Registrar</button></td>
                                                <td>Desfavorable</td>
                                                <td><button href="#" class="btn btn-default ">Registrar</button></td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                                <td></td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                            </tr>
                                            <tr style="background: #BDBDBD">
                                                <td>Durando-Iriarte</td>
                                                <td><button href="#" class="btn btn-default">Ver</button></td>
                                                <td>3</td>
                                                <td><button href="#" class="btn btn-default" disabled>Registrar</button></td>
                                                <td></td>
                                                <td><button href="#" class="btn btn-default" disabled>Registrar</button></td>
                                                <td><button href="#" class="btn btn-default" disabled>Registrar</button></td>
                                                <td></td>
                                                <td><button href="#" class="btn btn-default" disabled>Registrar</button></td>
                                            </tr>
                                            <tr>
                                                <td>Gallardo-Olivera</td>
                                                <td><button href="#" class="btn btn-default">Ver</button></td>
                                                <td rowspan="2" style="vertical-align: middle">022-2016</td>
                                                <td>1</td>
                                                <td rowspan="2" style="vertical-align: middle"><button href="#" class="btn btn-default">Ver</button></td>
                                                <td><button href="#" class="btn btn-default">Registrar</button></td>
                                                <td></td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                                <td></td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                            </tr>
                                            <tr>
                                                <td>Solano-Parco</td>
                                                <td><button href="#" class="btn btn-default">Ver</button></td>
                                                <td>2</td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                                <td></td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                                <td></td>
                                                <td><button href="#" class="btn btn-default disabled">Registrar</button></td>
                                            </tr> 
                                        </tbody>
                                    </table>
                                </div>
                                <br>
                                <!-- Button -->
                                <div class="control-group">
                                    <div class="controls">

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