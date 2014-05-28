<%-- 
    Document   : detalle_evaluacion
    Created on : 14/11/2013, 07:51:13 PM
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
                    <div class="col-md-6 col-md-offset-1">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}${volver}'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Buscador de Registro por Etapa</strong></h1>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li ><a href="${pageContext.servletContext.contextPath}/fametap">Preparación</a></li>
                            <li ><a href="${pageContext.servletContext.contextPath}/EtapaEvalNac" >Evaluación</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ListaEspera" >Lista Espera</a></li>
                            <li ${usuario.getRol().equals("UA") ? 'class="hidden"' : ''} class="active"><a href="${pageContext.servletContext.contextPath}/EtapaDesig">Propuesta de Designación</a></li>
                            <li ${usuario.getRol().equals("UA") ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/EtapaAdopcion" >Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Reevaluacion" >Reevaluación</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/EtapaPostAdopcion" >Post Adopción</a></li>
                        </ul>
                        
                        <form role="form" action="${pageContext.servletContext.contextPath}/aceptacionConsejo" method="post" name="formulario" onsubmit="return(validar());" onkeypress="return enter(event)">
                            <fieldset>
                                <br>
                                <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                                <!-- <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  -->
                                <br>
                                <h3 align="center"><strong>NNA's</strong></h3>
                                <c:forEach var="nna" items="${listaNna}" varStatus="status">
                                <h4 align="center"><strong>"${nna.getNombre()} ${nna.getApellidoP()} ${nna.getApellidoM()}"</strong></h4>
                                <br>
                                </c:forEach>
                                <input hidden name="numDesig" id="numDesig" value="${numDesig}">
                                <br>
                                <h3 align="left"><strong>Detalles de decisión de consejo</strong></h3>
                                <br>
                                <p class="text-danger"><strong>${mensaje}</strong></p>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de reunión del consejo</label>
                                    <div class="controls">
                                        <input type="text" class="datepicker" value="" name="fechaConsejo" id="fechaConsejo" >
                                    </div>
                                </div>
                                <br>
                                <br>
                                <h3 align="left"><strong>Familias asignadas al mismo NNA</strong></h3>
                                <br>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th class="col-sm-2 " >Expediente</th>
                                                <th class="col-sm-2 " >Prioridad</th>
                                            </tr>
                                        </thead>
                                        <c:if test="${!listaDesignaciones.isEmpty()}">
                                        <tbody>
                                            <c:forEach var="familia" items="${listaFamilias}" varStatus="status">
                                            <tr>
                                                <td>
                                                    ${familia.getExpediente()}
                                                    <input hidden id="idExp" name="idExp" value="${familia.getIdexpedienteFamilia()}">                                                    
                                                </td>
                                                <td><select id="prioridad" name="prioridad">
                                                        <option value="1">1</option>
                                                        <option value="2">2</option>
                                                        <option value="3">3</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            </c:forEach> 
                                        </tbody>
                                        </c:if>
                                    </table>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">Tipo de Propuesta</label>
                                    ${propuesta}
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones</label>
                                    <div class="controls">
                                        <textarea onkeyup="return(limitar());" type="text" name="obs" id="obs" cols="25" rows="5"></textarea>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <!-- Button -->
                                <div class="control-group">
                                    <div class="controls">
                                        <input hidden name="volver" id="volver" value="${volver}">
                                        <button id="singlebutton" name="singlebutton" class="btn btn-default">Aceptar </button>
                                    </div>
                                </div>
                                <br>
                                <!-- Button -->
                                
                                <!--FIN DE CONTENIDO-->
                            </fieldset>
                        </form>
                        <div class="control-group">
                            <div class="controls">
                                <input hidden name="volver" id="volver" value="${volver}">
                                <button onclick="window.location.href = '${pageContext.servletContext.contextPath}/EtapaDesig'" id="singlebutton" name="singlebutton" class="btn btn-default">Cancelar </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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

                $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

            </script>
            <script type="text/javascript">
                function enter(e) {
                     if (e.keyCode == 13) {
                     return false;
                    }
                }
            </script>
            <script type="text/javascript">
     
            function validar()
            {
              
            if( document.formulario.fechaConsejo.value == "" )
            {
            alert( "Debe ingresar la fecha de consejo" );
             document.formulario.fechaConsejo.focus() ;
            return false;
            }
            
            return true;
            }
            </script>
            <script type="text/javascript">
                function limitar()
                {                    
                    var obs = document.getElementById('obs');

                    if (obs.value.length < 0 || obs.value.length > 1399)
                    {
                        alert("solo puede ingresar 1400 caracteres");
                        obs.value = obs.value.substring(0, 1399);
                        document.formulario.obs.focus();
                        return false;
                    } 
                }
            </script>
            <!-- Ubicar al final -->
    </body>
</html>