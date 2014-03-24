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
                            <%if (u.getRol().equals("DCRI") || u.getRol().equals("DGA") || u.getRol().equals("admin")) {%>
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
                        <%if (!u.getRol().equals("DEIA Prio")) {%>
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
                        <c:if test="${idReunion != null}">
                            <form action="${pageContext.servletContext.contextPath}/PersonalInscritosTallerReunion?idReunion=${idReunion}&idTaller=${idTaller}&nombreTaller=${nombre}&historial=${historial}" method="post">
                                <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                            </form>
                        </c:if>
                        <c:if test="${idReunion == null}"> 
                            <form onclick="location.href = '${pageContext.servletContext.contextPath}/inf'" method="post">
                                <p align="right"><button  id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                            </form>
                        </c:if>

                        <br>
                        <form class="form-inline" role="form">
                            <!-- Form Name -->
                            <c:forEach var="asistente" items="${listaAsistentes}" varStatus="status">
                                <c:choose>
                                    <c:when test="${asistente.getSexo() == 109}">
                                        <c:set var="el" value="${asistente}" scope="page" />

                                    </c:when>
                                    <c:when test="${asistente.getSexo() == 102}">
                                        <c:set var="ella" value="${asistente}" scope="page" />
                                    </c:when> 
                                </c:choose>
                            </c:forEach>
                            <c:if test="${el != null}"> 
                                <div class="col-md-4 col-md-offset-2">                                  
                                    <p class="text-left"><legend>Información Personal <br>(EL)</legend></p>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Nombres</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${el.getNombre()}" type="text" placeholder="Nombres" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Apellido Paterno</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${el.getApellidoP()}" type="text" placeholder="Apellido Paterno" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Apellido Materno</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${el.getApellidoM()}" type="text" placeholder="Apellido Materno" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">País de Nacimiento</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${el.getPaisNac()}" type="text" placeholder="País " class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Departamento de nacimiento</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${el.getDepNac()}" type="text" placeholder="Departamento" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Provincia de nacimiento</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${el.getProvNac()}" type="text" placeholder="Provincia" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Fecha de nacimiento</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${formato.dateToString(el.getFechaNac())}" type="text" placeholder="dd/mm/yyyy" class=" datepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Edad</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${el.getEdad()}" type="text" placeholder="XY" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <div class="radio">
                                            <label>
                                                <input disabled type="radio" name="optionsRadios1" id="optionsRadios1" value="d" ${el.getTipoDoc() == 100 ? 'checked' : ''}>
                                                DNI
                                            </label>
                                        </div>
                                        <div class="radio">
                                            <label>
                                                <input disabled type="radio" name="optionsRadios1" id="optionsRadios2" value="c" ${el.getTipoDoc() == 99 ? 'checked' : ''}>
                                                Carnet de Extranjería
                                            </label>
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">N° documento</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${el.getNDoc()}" type="text" placeholder="Numero Documento" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Select Basic -->
                                    <div class="control-group">
                                        <label class="control-label" for="selectbasic">Profesión/Ocupación</label>
                                        <div class="controls">
                                            <select disabled id="selectbasic" name="selectbasic" class="input-xlarge">
                                                <option ${el.getProfesion() == 'ingeniero' ? 'selected' : ''}>Ingeniero</option>
                                                <option ${el.getProfesion() == 'abogado' ? 'selected' : ''}>Abogado</option>
                                            </select>
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Celular</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${el.getCelular()}" type="text" placeholder="Celular" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Correo Electrónico</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${el.getCorreo()}" type="text" placeholder="ejemplo@dominio.com" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <br>  
                                </div>
                            </c:if>  

                            <c:if test="${ella != null}"> 
                                <div class="col-md-4 col-md-offset-1">
                                    <p class="text-left"><legend>Información Personal <br> (ELLA) </legend></p>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Nombres</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${ella.getNombre()}" type="text" placeholder="Nombres" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Apellido Paterno</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${ella.getApellidoP()}" type="text" placeholder="Apellido Paterno" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Apellido Materno</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${ella.getApellidoM()}" type="text" placeholder="Apellido Materno" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">País de Nacimiento</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${ella.getPaisNac()}" type="text" placeholder="País " class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Departamento de nacimiento</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${ella.getDepNac()}" type="text" placeholder="Departamento" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Provincia de nacimiento</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${ella.getProvNac()}" type="text" placeholder="Provincia" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Fecha de nacimiento</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${formato.dateToString(ella.getFechaNac())}" type="text" placeholder="dd/mm/yyyy" class=" datepicker input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Edad</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${ella.getEdad()}" type="text" placeholder="XY" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <div class="radio">
                                            <label>
                                                <input disabled type="radio" name="optionsRadios2" id="optionsRadios1" value="d" ${ella.getTipoDoc() == 100 ? 'checked' : ''}>
                                                DNI
                                            </label>
                                        </div>
                                        <br>
                                        <div class="radio">
                                            <label>
                                                <input disabled type="radio" name="optionsRadios2" id="optionsRadios2" value="c" ${ella.getTipoDoc() == 99 ? 'checked' : ''}>
                                                Carnet de Extranjería
                                            </label>
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">N° documento</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${ella.getNDoc()}" type="text" placeholder="Numero Documento" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Select Basic -->
                                    <div class="control-group">
                                        <label class="control-label" for="selectbasic">Profesión/Ocupación</label>
                                        <div class="controls">
                                            <select disabled id="selectbasic" name="selectbasic" class="input-xlarge">
                                                <option ${ella.getProfesion() == 'ingeniero' ? 'selected' : ''}>Ingeniero</option>
                                                <option ${ella.getProfesion() == 'abogado' ? 'selected' : ''}>Abogado</option>
                                            </select>
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Celular</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${ella.getCelular()}" type="text" placeholder="Celular" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <!-- Text input-->
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Correo Electrónico</label>
                                        <div class="controls">
                                            <input disabled id="textinput" name="textinput" value="${ella.getCorreo()}" type="text" placeholder="ejemplo@dominio.com" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <br>  
                                </div>
                            </c:if>
                            <br>
                            <br>  

                            <p class="text-left"><legend>Residencia familiar</legend></p>
                            <br>
                            <div class="row">
                                <div class="col-lg-3">
                                    <label for="pais">País</label>
                                    <input disabled id="pais" value="${formulario.getPaisRes()}" type="text" placeholder="Pais" class="input-xlarge">
                                </div>
                                <div class="col-lg-3">
                                    <label for="dep">Departamento</label> 
                                    <input disabled id="dep" value="${formulario.getDepRes()}" type="text" placeholder="Departamento" class="input-xlarge">
                                </div>
                                <div class="col-lg-3">
                                    <label for="prov">Provincia</label> 
                                    <input disabled id="prov" value="${formulario.getProvRes()}" type="text" placeholder="Provincia" class="input-xlarge">
                                </div>
                                <div class="col-lg-3">
                                    <label for="dist">Distrito</label> 
                                    <input disabled id="dist" value="${formulario.getDistritoRes()}" type="text" placeholder="Distrito" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-lg-4">
                                    <label for="dir">Dirección domiciliaria</label> 
                                    <input disabled id="dir" value="${formulario.getDireccionRes()}" type="text" placeholder="Dirección Domiciliaria" class="input-xlarge">
                                </div>

                                <div class="col-lg-4">
                                    <label for="telf">Teléfono fijo</label> 
                                    <input disabled id="telf" value="${formulario.getTelefono()}" type="text" placeholder="Teléfono fijo" class="input-xlarge">
                                </div>
                            </div>
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
                            <!-- Placed at the end of the document so the pages load faster -->
                            </body>
                            </html>