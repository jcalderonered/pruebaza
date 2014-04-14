<%-- 
    Document   : agregar_exp
    Created on : 4/12/2013, 11:38:32 AM
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
                        <!-- <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  -->
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li><a href="${pageContext.servletContext.contextPath}/reg" >Búsqueda de Expedientes de Familia</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/buscarNna" >Búsqueda de NNA's</a></li>
                        </ul>
                        <br>
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inicioper'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <br>
                        <h1 align="left"><strong>Búsqueda de NNA's</strong></h1>
                        <br>
                        <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/FiltrarNna" method="post" name="formulario" onsubmit="return(validar());">
                            <fieldset>
                                <div class="row">
                                    <div class="col-md-3">
                                        <div class="control-group">
                                            <label class="control-label">Nombre</label>
                                            <div class="controls">
                                                <input id="nombre" name="nombre" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Apellido Paterno</label>
                                            <div class="controls">
                                                <input id="apellidoP" name="apellidoP" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Apellido Materno</label>
                                            <div class="controls">
                                                <input id="apellidoM" name="apellidoM" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div>
                                            <label class="control-label">Grupo etario</label>
                                            <div class="controls">
                                                <select id="edad" name="edad">
                                                    <option value="0"></option>
                                                    <option value="1">0-8</option>
                                                    <option value="2">9-11</option>
                                                    <option value="3">12-18</option>
                                                </select>
                                            </div>    
                                        </div>
                                        <br>
                                    </div>   
                                    <div class="col-md-4 col-md-offset-1">
                                        <div class="control-group">
                                            <label class="control-label">Nombre Adoptado</label>
                                            <div class="controls">
                                                <input id="nombreAdop" name="nombreAdop" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Apellido Paterno Adoptado</label>
                                            <div class="controls">
                                                <input id="apellidoPAdop" name="apellidoPAdop" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Apellido Materno Adoptado</label>
                                            <div class="controls">
                                                <input id="apellidoMAdop" name="apellidoMAdop" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">CAR</label>
                                            <div class="controls">
                                                <select id="idCar" name="idCar" >
                                                    <option value="0" selected></option>
                                                    <c:forEach var="car" items="${listaCar}" > 
                                                        <option value="${car.getIdcar()}">${car.getNombre()} ${car.getDepartamento()}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>    
                                        </div>
                                        <br>
                                    </div>
                                    <div class="col-md-4">
                                        <div>
                                            <label class="control-label">Estado</label>
                                            <div class="controls">
                                                <select id="estado" name="estado">
                                                    <option value="none" ></option>
                                                    <option value="eval" >Evaluación</option>
                                                    <option value="adoptable">Adoptable</option>
                                                    <option value="desig">Designado</option>
                                                    <option value="adop" >Adoptado</option>
                                                    <option value="arch" >Archivado</option>
                                                </select>
                                            </div>    
                                        </div>
                                        <br>
                                        <div>
                                            <label class="control-label">Condiciones</label>
                                            <div class="controls">
                                                <select id="prioritario" name="prioritario">
                                                    <option value="none" >Ninguna</option>
                                                    <option value="reg" >Regular</option>
                                                    <option value="prio" >Prioritario</option>
                                                    <option value="ne" >Necesidades Especiales</option>
                                                    <option value="ps">Problemas de Salud</option>
                                                    <option value="m" >Mayores</option>
                                                    <option value="a" >Adolescentes</option>
                                                    <option value="h" >Hermanos</option>
                                                </select>
                                            </div>    
                                        </div>
                                        <br>
                                        <div>
                                            <label class="control-label">Sexo</label>
                                            <div class="controls">
                                                <select id="sexo" name="sexo">
                                                    <option value="none"></option>
                                                    <option value="masculino">Masculino</option>
                                                    <option value="femenino">Femenino</option>
                                                </select>
                                            </div>    
                                        </div>
                                        <br>
                                    </div>    
                                </div>
                                <button id="singlebutton" name="singlebutton" class="btn btn-default">Buscar</button>
                            </fieldset>
                        </form>
                        <br>
                        <h1 align="center"><strong>Resultados de la Búsqueda</strong></h1>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="col-sm-2 " >Nombre Completo</th>
                                        <th class="col-sm-2 " >Nombre Completo Adoptado</th>
                                        <th class="col-sm-2 " >Estado</th>
                                        <th class="col-sm-2 " >Prioritario</th>
                                        <th class="col-sm-2 " >Información</th>
                                    </tr>
                                </thead>
                                <c:if test="${!listaBusqueda.isEmpty()}"> 
                                    <tbody>
                                        <c:forEach var="expediente" items="${listaBusqueda}" varStatus="status">
                                            <tr>
                                                <td>${expediente.getNna().getNombre()}
                                                    ${expediente.getNna().getApellidoP()}
                                                    ${expediente.getNna().getApellidoM()}
                                                </td>
                                                <td>${expediente.getNActual()}
                                                    ${expediente.getApellidopActual()}
                                                    ${expediente.getApellidomActual()}
                                                </td>
                                                <td>
                                                    ${expediente.getEstado() == 'eval' ? 'evaluación' : ''}
                                                    ${expediente.getEstado() == 'adoptable' ? 'adoptable' : ''}
                                                    ${expediente.getEstado() == 'desig' ? 'designado' : ''}
                                                    ${expediente.getEstado() == 'adop' ? 'adoptado' : ''}
                                                    ${expediente.getEstado() == 'arch' ? 'archivado' : ''}
                                                </td>
                                                <td>
                                                    ${expediente.getNna().getClasificacion() == 'prioritario'  ? 'Si' : 'No'} 
                                                </td>
                                                <td>
                                                    <form action="${pageContext.servletContext.contextPath}/editarNna2" method="post">
                                                        <input hidden name="idNna" id="idNna" value="${expediente.getNna().getIdnna()}">
                                                        <button type="submit" class="btn btn-default">Ver</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>  
                                    </tbody>
                                </c:if> 
                                <c:if test="${listaBusqueda.isEmpty()}">
                                    <h3><strong>No se encontraron resultados</strong></h3>
                                </c:if>  
                            </table>
                        </div>
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
        <!-- core JavaScript
================================================== -->
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
        <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
        <script type="text/javascript">

                            function validar()
                            {

                                if (document.formulario.nombre.value == "" && document.formulario.apellidoP.value == ""
                                        && document.formulario.apellidoM.value == "" && document.formulario.nombreAdop.value == ""
                                        && document.formulario.apellidoPAdop.value == "" && document.formulario.apellidoMAdop.value == ""
                                        && document.getElementById("edad").value == "0" && document.getElementById("sexo").value == "none"
                                        && document.getElementById("idCar").value == "0"
                                        && document.getElementById("estado").value == "none" && document.getElementById("prioritario").value == "none")
                                {
                                    alert("Debe elegir al menos un filtro de búsqueda");
                                    return false;
                                }

                                return(true);
                            }
        </script>
        <!-- Ubicar al final -->
    </body>
</html>
