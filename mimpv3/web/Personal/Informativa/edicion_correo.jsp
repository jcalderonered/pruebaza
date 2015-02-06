<%-- 
    Document   : info_familia
    Created on : 30/10/2013, 06:50:32 PM
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
                            <%if (u.getRol().equals("mpartes")) {%>
                        <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <%}%>
                            <%if (u.getRol().equals("admin")) {%>
                        <li><a href="${pageContext.servletContext.contextPath}/juzgado"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de juzgado</a></li>
                        <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                        <li><a href="${pageContext.servletContext.contextPath}/ua"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UA</a></li>
                            <%}
                                if (u.getRol().equals("DEIA")) {%>
                        <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li> 
                            <%}
                                if (!u.getRol().equals("DAPA") && !u.getRol().equals("MATCH") && !u.getRol().equals("UA")) {%>
                        <li><a href="${pageContext.servletContext.contextPath}/famint"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                            <%}%>

                        <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>

                        <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de registros</a></li>
                            <%if (!u.getRol().equals("DEIA Prio") && !u.getRol().equals("UA") && !u.getRol().equals("DAPA") && !u.getRol().equals("mpartes")) {%>
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
                <div class="row">
                    <div class="col-md-6 col-md-offset-1">                        
                        <c:if test="${idSesion != null}">
                            <form action="${pageContext.servletContext.contextPath}/PersonalTomaAsistencia2?idSesion=${idSesion}" method="post">
                                <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                            </form>
                        </c:if>
                        <c:if test="${idSesion == null}"> 
                            <form onclick="location.href = '${pageContext.servletContext.contextPath}/inicioper'" method="post">
                                <p align="right"><button  id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                            </form>
                        </c:if>                         
                        <br>
                        <form action="${pageContext.servletContext.contextPath}/PersonalModificarCorreoSesionInf_2" name="formulario" onsubmit="return(validar());" method="post">
                            <div class="control-group">
                                <label class="control-label">Nuevo Correo</label>
                                <div class="controls">
                                    <input id="user" name="user" type="text" class="input-xlarge">                                    
                                </div>
                            </div>
                            <br>
                            <input hidden name="idFormulario" id="idFormulario" value="${idFormulario}">
                            <input hidden name="idSesion" id="idSesion" value="${idSesion}">
                            <input hidden name="idFamilia" id="idSesion" value="${idFamilia}">
                            <button id="singlebutton" name="singlebutton" class="btn btn-default">Modificar</button>
                            <br>
                            <p><strong>Nota: Al modificar el correo se enviarán las nuevas credenciales al usuario</strong></p>
                        </form>
                        <br>

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
            <script type="text/javascript">

                function validar()
                {          
                    if (document.formulario.user.value == "")
                    {
                        alert("Debe ingresar un correo electrónico");
                        document.formulario.user.focus();
                        return false;
                    }
                    if (document.formulario.user.value != "")
                    {
                        if (validateEmail(document.formulario.user.value)) {

                        } else {
                            alert("Debe ingresar un correo válido");
                            document.formulario.user.focus();
                            return false;
                        }


                    }                                                                             
                    return(true);
                }
            </script>
            <script>
                function validateEmail(email) {
                    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                    return re.test(email);
                }
            </script>
            <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>