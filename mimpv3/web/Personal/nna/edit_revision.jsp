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
                                    if (!u.getRol().equals("DAPA") && !u.getRol().equals("MATCH") && !u.getRol().equals("UA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/famint"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                                <%}
                                    if (!u.getRol().equals("mpartes")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de registros</a></li>
                            <%if (!u.getRol().equals("DEIA Prio") && !u.getRol().equals("UA") && !u.getRol().equals("DAPA")) {%>
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
                         <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/listaRevision'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li ><a href="${pageContext.servletContext.contextPath}/nna" >NNA Regulares</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/nnaPrioritarios" >NNA Prioritarios</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/nnaSeguimiento" >NNA en Seguimiento</a></li>
                        </ul>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li><a href="${pageContext.servletContext.contextPath}/nnaPrioritarios" >Lista de NNA</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/listaRevision" >Lista de Revisión de Expediente</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/listaEstudio" >Lista de Estudio de Caso</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/nnaCol" >NNA en Acogimiento Familiar o Colocación Familiar</a></li>
                        </ul>
                        <br>
                        <h1 align="center"><strong>NNA que conforman la Revisión de Expediente</strong></h1>
                        <br>
                        <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/MainEdicionRevision" method="post" name="formulario" onsubmit="return(validar());" onkeypress="return enter(event)">
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped" id="mi_tabla">
                                    <thead>
                                        <tr>
                                            <th class="col-sm-2 ">Nombre</th>
                                            <th class="col-sm-2 ">Apellido Paterno</th>
                                            <th class="col-sm-2 ">Apellido Materno</th>
                                            <th class="col-sm-2 ">Código</th>
                                        </tr>
                                    </thead>
                                    <c:if test="${!listaNna.isEmpty()}"> 
                                        <tbody>
                                            <c:forEach var="nna" items="${listaNna}" varStatus="status">
                                                <tr>
                                                    <td>${nna.getNombre()}</td>
                                                    <td>${nna.getApellidoP()}</td>
                                                    <td>${nna.getApellidoM()}</td>
                                                    <c:if test="${!nna.getExpedienteNnas().isEmpty()}">
                                                        <c:forEach var="expediente" items="${nna.getExpedienteNnas()}" varStatus="status">
                                                            <td>
                                                                ${expediente.getCodigoReferencia()}
                                                            </td>
                                                        </c:forEach>
                                                    </c:if>
                                                    <c:if test="${nna.getExpedienteNnas().isEmpty()}">
                                                        <td>
                                                            No
                                                        </td>   
                                                    </c:if>                                                                                                       
                                                </tr>
                                            </c:forEach>  
                                        </tbody>
                                    </c:if>
                                    <c:if test="${listaNna.isEmpty()}">
                                        <h3><strong>No existen Nna en este proceso</strong></h3>
                                    </c:if>  
                                </table>
                            </div>
                            <br>
                            <h1 align="center"><strong>Familias que conforman la Revisión de Expediente</strong></h1>
                            <br>
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th class="col-sm-2 " >Expediente</th>
                                            <th class="col-sm-2 " >Fecha de revisión</th>
                                        </tr>
                                    </thead>
                                    <c:if test="${!listaExpedientes.isEmpty()}">
                                        <tbody>
                                            <c:forEach var="expediente" items="${listaExpedientes}" varStatus="status">
                                                <tr>
                                                    <td>${expediente.getExpediente()}</td>
                                                    <td>
                                                        <c:forEach var="rev" items="${listaRevisiones}" varStatus="status">
                                                            <c:if test="${expediente.getIdexpedienteFamilia() == rev.getExpedienteFamilia().getIdexpedienteFamilia()}">
                                                                <c:if test="${rev.getFechaRevision() != null}">
                                                                    <c:set var="fechaExp" value="${df.dateToStringNumeros(rev.getFechaRevision())}" ></c:set>  
                                                                </c:if>
                                                            </c:if>
                                                        </c:forEach>
                                                        ${fechaExp}
                                                    </td>  
                                                </tr>
                                                <c:set var="fechaExp" value="${null}" ></c:set> 
                                            </c:forEach> 
                                        </tbody>
                                    </c:if>
                                    <c:if test="${listaExpedientes.isEmpty()}">
                                        <h3><strong>Ninguna Familia solicitó la revisión de expediente</strong></h3>
                                    </c:if> 
                                </table>
                            </div>
                            <br>
                            <h1 align="center"><strong>Organismos que solicitaron la revisión de expediente</strong></h1>
                            <br>
                            <div class="table-responsive">
                                <table class="table table-bordered table-striped">
                                    <thead>
                                        <tr>
                                            <th class="col-sm-2 " >Nombre</th>
                                            <th class="col-sm-2 " >País</th>
                                            <th class="col-sm-2 " >Fecha de Revisión</th>
                                        </tr>
                                    </thead>
                                    <c:if test="${!listaEntidades.isEmpty()}">
                                        <tbody>
                                            <c:forEach var="entidad" items="${listaEntidades}" varStatus="status">
                                                <tr>
                                                    <td>${entidad.getNombre()}</td>
                                                    <td>${entidad.getPais()}</td>
                                                    <td>
                                                        <c:forEach var="rev" items="${listaRevisiones}" varStatus="status">
                                                            <c:if test="${entidad.getIdentidad() == rev.getIdEntidad()}">
                                                                <c:if test="${rev.getFechaRevision() != null}">
                                                                    <c:set var="fecha" value="${df.dateToStringNumeros(rev.getFechaRevision())}" ></c:set>                                                
                                                                </c:if>
                                                            </c:if>
                                                        </c:forEach>
                                                        ${fecha}
                                                    </td>  
                                                </tr>
                                                <c:set var="fecha" value="${null}" ></c:set> 
                                            </c:forEach> 
                                        </tbody>
                                    </c:if>
                                    <c:if test="${listaEntidades.isEmpty()}">
                                        <h3><strong>Ningún Organismo y/o Autoridad Centrales solicitó la revisión</strong></h3>
                                    </c:if>    
                                </table>
                            </div>
                            <br>                            
                        </form>
                        <br>
                        <h3><strong>Comentarios</strong></h3>
                        <form action="${pageContext.servletContext.contextPath}/MainGuardarRevision" method="post">
                            <br>
                            <c:set var="comentarios" value="${listaRevisiones.get(0).getComentarios()}" ></c:set>      
                            <textarea type="text" id="coments" name="coments" cols="25" rows="5">${comentarios}</textarea>
                            <input hidden id="numero" name="numero" value="${numero}" />
                            <br>
                            <br>
                            <button type="submit" class="btn btn-default">Guardar</button>
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