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
                            <li><a href="${pageContext.servletContext.contextPath}/esperaInter"><span class="glyphicon glyphicon-chevron-right"></span>Adoptantes para la adopción en el extranjero</a></li>       
                                <%if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/usuarios"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                                <%}
                                if (u.getRol().equals("admin") || u.getRol().equals("DEIA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de organismo acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de autoridad central</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                            <%if (u.getRol().equals("DAPA") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                            <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-6 col-md-offset-1">
                        <!-- <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  -->
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li ><a href="${pageContext.servletContext.contextPath}/nna" >NNA Regulares</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/nnaPrioritarios" >NNA Prioritarios</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/nnaSeguimiento" >NNA en Seguimiento</a></li>
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
                                        <th class="col-sm-2 " >Información</th>
                                        <th class="col-sm-2 " >Estudio de Caso</th>
                                        <th class="col-sm-2 " >Prioridad</th>
                                        <th class="col-sm-2 " >Resultado</th>
                                        <th class="col-sm-2 " >Registrar</th>
                                    </tr>
                                </thead>
                                <c:if test="${!listaEstudios.isEmpty()}">
                                <tbody>
                                    <c:forEach var="estudio" items="${listaEstudios}" varStatus="status">
                                        <c:if test="${estudio.getResultado() == 'acep'}">
                                            <c:set var="token" value="aceptado"/>
                                            <c:set var="idEstudio" value="${estudio.getIdestudioCaso()}"/>
                                            <c:set var="orden" value="${estudio.getOrden()}"/>
                                        </c:if> 
                                        <c:if test="${estudio.getResultado() == 'acep' && estudio.getFechaSolAdop() != null}">
                                            <c:set var="tokenDesig" value="designado"/>
                                        </c:if> 
                                        <tr>
                                        <td>${estudio.getExpedienteFamilia().getExpediente()}</td>
                                        <td>
                                            <button id="singlebutton" name="singlebutton" class="btn btn-default">Ver</button>
                                        </td>
                                        <td>${estudio.getFechaEstudio() != null ? df.dateToString(estudio.getFechaEstudio()) : ''}</td>
                                        <td>${estudio.getPrioridad()}</td>
                                        <td>
                                            <select onchange="resultado(this.value)" ${estudio.getResultado() != null && estudio.getResultado() != 'obs'  ? 'disabled' : ''} id="result" name="result">
                                                <option ${estudio.getResultado() == 'obs' ? 'selected' : ''} ${estudio.getResultado() == null ? 'selected' : ''} value="obs">Observado</option>
                                                <option ${estudio.getResultado() == 'noobs' ? 'selected' : ''} value="noobs">No observado</option>
                                                <option ${estudio.getResultado() == 'acep' ? 'selected' : ''} value="acep">Aceptado</option>
                                                <option ${estudio.getResultado() == 'noacep' ? 'selected' : ''} value="noacep">No aceptado</option>
                                            </select>
                                        </td>
                                        <td>
                                          <form action="${pageContext.servletContext.contextPath}/ActualizarEstudio" method="post">
                                               <input hidden ${estudio.getResultado() != null && estudio.getResultado() != 'obs' ? 'name=""' : 'name="resultado"' }  ${estudio.getResultado() != null && estudio.getResultado() != 'obs' ? 'id=""' : 'id="resul"' }  value="obs" >
                                               <input hidden name="idEstudio" id="idEstudio" value="${estudio.getIdestudioCaso()}">
                                               <input hidden name="orden" id="orden" value="${estudio.getOrden()}">  
                                               <button ${estudio.getResultado() != null && estudio.getResultado() != 'obs'  ? 'disabled' : ''} id="singlebutton" name="singlebutton" class="btn btn-default">Registrar</button>
                                          </form>
                                        </td>
                                    </tr>
                                    </c:forEach> 
                                </tbody>
                                </c:if>
                                   <c:if test="${listaEstudios.isEmpty()}">
                                     <h3><strong>No existen Estudios de Caso</strong></h3>
                               </c:if> 
                            </table>
                        </div>
                        <br>
                        <h1>Información para la propuesta de adopción</h1>
                        <p>En caso una familia presente una solicitud de adopción (el resultado sea "Aceptado"), llenar la siguiente información:</p>
                        <br>
                        
                        <form action="${pageContext.servletContext.contextPath}/generarDesignacionPrioritario" method="post">
                        <div class="control-group">
                            <label class="control-label">Fecha de solicitud de adopción</label>
                            <div class="controls">
                                <input ${tokenDesig == 'designado' ? 'disabled' : ''} ${token == 'aceptado' ? '' : 'disabled'} id="fechaSolicitud" name="fechaSolicitud" type="text" class="datepicker input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">N° de Designación</label>
                            <div class="controls">
                                <input ${tokenDesig == 'designado' ? 'disabled' : ''} ${token == 'aceptado' ? '' : 'disabled'} id="numDesig" name="numDesig" type="text" class="span2" value="" id="designacion" >
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Fecha de propuesta</label>
                            <div class="controls">
                                <input ${tokenDesig == 'designado' ? 'disabled' : ''} ${token == 'aceptado' ? '' : 'disabled'} id="fechaPropuesta" name="fechaPropuesta" type="text" class="datepicker span2" >
                            </div>
                        </div>
                        <br>
                        <br>
                        <input hidden name="orden" id="orden" value="${orden}">
                        <input hidden name="idEstudio" id="idEstudio" value="${idEstudio}">
                        <button ${tokenDesig == 'designado' ? 'disabled' : ''} ${token == 'aceptado' ? '' : 'disabled'} id="singlebutton" name="singlebutton" class="btn btn-default">Generar Propuesta Directa</button>
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
        <script>
                function resultado(value)
                    {
                        var resul = document.getElementById("resul");
                        //you can get the value from arguments itself
                        //alert(value);
                        resul.value = value;
                        //alert(resul.value);
                    }
            </script>
        <!-- Placed at the end of the document so the pages load faster -->
    </body>
</html>