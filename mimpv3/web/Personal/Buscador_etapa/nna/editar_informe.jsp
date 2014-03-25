<%-- 
    Document   : inicio_personal
    Created on : 28/10/2013, 09:54:29 AM
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
                                <%if (u.getRol().equals("DCRI") || u.getRol().equals("DGA") || u.getRol().equals("admin") || u.getRol().equals("UA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/inf"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de sesiones/talleres</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNA</a></li>
                                <%if (u.getRol().equals("admin")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/juzgado"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de juzgado</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ua"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UA</a></li>
                                <%}
                                    if (u.getRol().equals("DEIA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li> 
                                <%}
                                    if (!u.getRol().equals("DAPA") && !u.getRol().equals("MATCH")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/famint"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                                <%}
                                    if (!u.getRol().equals("mpartes")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de registros</a></li>
                                <%if (!u.getRol().equals("DEIA Prio") && !u.getRol().equals("UA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/esperaInter"><span class="glyphicon glyphicon-chevron-right"></span>Adoptantes para la adopción en el extranjero</a></li>
                                <%}%>
                                <%if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/usuarios"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                                <%}
                                    if (u.getRol().equals("admin") || u.getRol().equals("DEIA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de organismo acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de autoridad central</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                                <%if (u.getRol().equals("DAPA") || u.getRol().equals("DCRI") || u.getRol().equals("DEIA Prio")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-6 col-md-offset-1">
                        <!--<p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/nna'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  -->
                        <ul class="nav nav-tabs row" >
                            <li ${clasificacion == 'regular' ? 'class="active"' : ''} ><a href="${pageContext.servletContext.contextPath}/nna" >NNA Regulares</a></li>
                            <li ${clasificacion == 'prioritario' ? 'class="active"' : ''}><a href="${pageContext.servletContext.contextPath}/nnaPrioritarios" >NNA Prioritarios</a></li>
                            <li ${clasificacion == 'seguimiento' ? 'class="active"' : ''}><a href="${pageContext.servletContext.contextPath}/nnaSeguimiento" >NNA en Seguimiento</a></li>
                        </ul>
                        <br>
                        <h1 align="center"><strong>Editar Informe</strong></h1>
                        <br>
                        <c:if test="${informe == null}">
                            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/NnaCrearInforme" method="post"> 
                                <input hidden name="idExpNna" id="idExpNna" value="${idExpNna}">
                            </c:if>  
                            <c:if test="${informe != null}">
                                <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/NnaUpdateInforme" method="post"> 
                                    <input hidden name="idInf" id="idInf" value="${informe.getIdinformeNna()}">
                                    <input hidden name="idExpNna" id="idExpNna" value="${idExpNna}">
                                </c:if>     
                                <fieldset>
                                    <input hidden name="clasificacion" id="clasificacion" value="${clasificacion}">
                                    <!-- Text input-->
                                    <h3><strong>${mensaje}</strong></h3>
                                    <div class="control-group">
                                        <label class="control-label">Número de Informe</label>
                                        <div class="controls">
                                            <input id="numInf" name="numInf" value="${informe.getNumero()}" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Fecha</label>
                                        <div class="controls">
                                            <input id="fechaInf" value="${informe.getFecha() != null ? df.dateToStringNumeros(informe.getFecha()) : ''}" name="fechaInf" type="text" class="datepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br> 
                                    <div class="control-group">
                                        <label class="control-label">Resultado del informe</label>
                                        <div class="controls">
                                            <textarea id="result" name="result" cols="25" rows="5">${informe.getResultado()}</textarea>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Observaciones</label>
                                        <div class="controls">
                                            <textarea id="obs" name="obs" cols="25" rows="5">${informe.getObservaciones()}</textarea>
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <div class="controls">
                                            <button ${usuario.getUnidad().getDepartamento() != 'Lima' ? 'disabled' : ''} id="singlebutton" name="singlebutton" class="btn btn-default">Guardar</button>     
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
    $(document).ready(function() {
        $('.datepicker').datepicker({
            "format": "dd/mm/yyyy",
            "weekStart": 1,
            "autoclose": true,
            "language": "es"
        });

        $('#fechaNac').datepicker({
            "format": "dd/mm/yyyy",
            "weekStart": 1,
            "autoclose": true,
            "language": "es"
        }).on('changeDate', function(ev) {

            var nac = document.getElementById("fechaNac").value;
            var edad = document.getElementById("edad");
            var meses = document.getElementById("meses");

            var today = new Date();
            var curr_date = today.getDate();
            var curr_month = today.getMonth() + 1;
            var curr_year = today.getFullYear();

            var pieces = nac.split('/');
            var birth_date = pieces[0];
            var birth_month = pieces[1];
            var birth_year = pieces[2];


            if (curr_year != birth_year && birth_month > curr_month)
                edad.value = curr_year - birth_year - 1;
            if (curr_year != birth_year && birth_month == curr_month)
                edad.value = curr_year - birth_year;
            if (curr_year != birth_year && birth_month < curr_month)
                edad.value = curr_year - birth_year;
            if (curr_year == birth_year)
                edad.value = 0;
            if (curr_month == birth_month)
                meses.value = 0;
            if (curr_month != birth_month && curr_month > birth_month)
                meses.value = curr_month - birth_month;
            if (curr_month != birth_month && curr_month <= birth_month)
                meses.value = curr_month + 12 - birth_month;

        });

        $("input[type='radio']").click(function()
        {
            var previousValue = $(this).attr('previousValue');
            var name = $(this).attr('name');

            if (previousValue == 'checked')
            {
                $(this).removeAttr('checked');
                $(this).attr('previousValue', false);
            }
            else
            {
                $("input[name=" + name + "]:radio").attr('previousValue', false);
                $(this).attr('previousValue', 'checked');
            }
        });
    });

            </script>
            <!-- Ubicar al final -->
    </body>
</html>