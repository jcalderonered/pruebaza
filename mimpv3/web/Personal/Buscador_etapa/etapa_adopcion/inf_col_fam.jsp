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

    <body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="" id="bd" class="bd fs3 com_content">
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

                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/EtapaAdopcion'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Buscador de Registro por Etapa</strong></h1>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li ><a href="${pageContext.servletContext.contextPath}/fametap">Preparación</a></li>
                            <li ><a href="${pageContext.servletContext.contextPath}/EtapaEvalNac" >Evaluación</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ListaEspera" >Lista Espera</a></li>
                            <li ${usuario.getRol().equals("UA") ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/EtapaDesig">Propuesta de Designación</a></li>
                            <li ${usuario.getRol().equals("UA") ? 'class="hidden"' : ''} class="active"><a href="${pageContext.servletContext.contextPath}/EtapaAdopcion" >Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Reevaluacion" >Reevaluación</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/EtapaPostAdopcion" >Post Adopción</a></li>
                        </ul>
                        <!-- <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  -->
                        <h1 align="center"><strong>Informe de Colocación Familiar</strong></h1>
                        <br>
                        <div class="row">
                            <div class="col-md-2 col-md-offset-2">
                                <c:if test="${informe == null}">
                                    <form name="formulario" class="form-horizontal" action="${pageContext.servletContext.contextPath}/crearEvalInforme" method="post" name="formulario" onsubmit="return(validar());" onkeypress="return enter(event)"> 
                                        <input hidden name="idExpediente" id="idExpediente" value="${idExpediente}">
                                    </c:if>  
                                    <c:if test="${informe != null}">
                                        <form name="formulario" class="form-horizontal" action="${pageContext.servletContext.contextPath}/updateEvalInforme" method="post"> 
                                            <input hidden name="idInforme" id="idInforme" value="${informe.getIdevaluacion()}">
                                        </c:if>  
                                        <fieldset>
                                            <!-- Text input-->
                                            <br>
                                            <h1 align="center"><strong>Familia "${familia}"</strong></h1>
                                            <br>
                                            <h3><strong>Responsable</strong></h3>
                                            <br>
                                            <div>
                                                <label class="control-label">Profesional</label>
                                                <div class="controls">
                                                    <select ${informe != null ? 'disabled' : ''} id="personal" name="personal" class="input-xlarge">
                                                        <c:forEach var="personal" items="${listaPersonal}" > 
                                                            <option value="${personal.getIdpersonal()}" ${informe.getPersonal().getIdpersonal() == personal.getIdpersonal() ? 'selected' : ''}>${personal.getNombre()} ${personal.getApellidoP()} ${personal.getApellidoM()}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>    
                                            </div>
                                            <br>
                                            <h3><strong>Evaluación</strong></h3>
                                            <br>
                                            <div class="control-group">
                                                <label class="control-label">Número de informe</label>
                                                <div class="controls">
                                                    <input onkeyup="return(limitar());" id="numEval" name="numEval" type="text" class="input-xlarge" value="${informe.getNumEval()}" >
                                                </div>
                                            </div>
                                            <br> 
                                            <div>
                                                <label class="control-label">Resultado</label>
                                                <div class="controls">
                                                    <select ${informe != null ? 'disabled' : ''} id="resultado" name="resultado" >
                                                        <option value="favorable" ${informe.getResultado() == 'favorable' ? 'selected' : ''}>Favorable</option>
                                                        <option value="desfavorable" ${informe.getResultado() == 'desfavorable' ? 'selected' : ''}>Desfavorable</option>
                                                    </select>
                                                </div>    
                                            </div>

                                            <br>
                                            <div class="control-group">
                                                <label class="control-label">Fecha de evaluación</label>
                                                <div class="controls">
                                                    <input ${informe != null ? 'disabled' : ''} id="fechaEval" name="fechaEval" type="text" value="${informe.getFechaResultado() != null ? df.dateToString(informe.getFechaResultado()) : ''}" class="datepicker input-xlarge">
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <label class="control-label">Personas Evaluadas(incluye niño/a)</label>
                                                <div class="controls">
                                                    <textarea onkeyup="return(limitar());" ${informe != null ? 'disabled' : ''} id="persInt" name="persInt" type="text" cols="25" rows="5">${informe.getPersInt()}</textarea>
                                                </div>
                                            </div>
                                            <br>
                                            <div class="control-group">
                                                <label class="control-label">Número de personas intervenidas</label>
                                                <div class="controls">
                                                    <input ${informe != null ? 'disabled' : ''} id="numPers" name="numPers" type="text" class="input-xlarge" value="${informe.getNumPersInt()}" >
                                                </div>
                                            </div>
                                            <br> 
                                            <div class="control-group">
                                                <label class="control-label">Comentarios</label>
                                                <div class="controls">                                                    
                                                    <textarea onkeyup="return(limitar());" ${informe != null ? 'disabled' : ''} id="obs" name="obs" cols="25" rows="5" class="input-xlarge">${informe.getObservacion()}</textarea>
                                                </div>
                                            </div>   
                                            <br>


                                            <!-- Button -->
                                            <div class="control-group">
                                                <div class="controls">
                                                    <button ${informe != null ? 'disabled' : ''} id="singlebutton" name="singlebutton" class="btn btn-default">Editar</button>
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
                    function enter(e) {
                        if (e.keyCode == 13) {
                            return false;
                        }
                    }
                </script>
                <script type="text/javascript">

                    function validar()
                    {
                        var numericExpression = /^[0-9]+$/;
                        if (!document.formulario.numPers.value.match(numericExpression))
                        {
                            alert("Debe ingresar un número");
                            document.formulario.numPers.focus();
                            return false;
                        }
                        if (document.formulario.numEval.value == "")
                        {
                            alert("Debe ingresar un número de informe");
                            document.formulario.numEval.focus();
                            return false;
                        }
                        if (document.formulario.fechaEval.value == "")
                        {
                            alert("Debe ingresar la fecha");
                            document.formulario.fechaEval.focus();
                            return false;
                        }

                        return true;
                    }
                </script>
                <SCRIPT type="text/javascript">
                    window.history.forward();
                    function noBack() {
                        window.history.forward();
                    }
                </SCRIPT>
                <script type="text/javascript">
                    function limitar()
                    {
                        var numEval = document.getElementById('numEval');
                        var persInt = document.getElementById('persInt');
                        var obs = document.getElementById('obs');

                        if (numEval.value.length < 0 || numEval.value.length > 39)
                        {
                            alert("solo puede ingresar 40 caracteres");
                            numEval.value = numEval.value.substring(0, 39);
                            document.formulario.numEval.focus();
                            return false;
                        } else if (persInt.value.length < 0 || persInt.value.length > 999) {
                            alert("solo puede ingresar 1000 caracteres");
                            persInt.value = persInt.value.substring(0, 999);
                            document.formulario.persInt.focus();
                            return false;
                        } else if (obs.value.length < 0 || obs.value.length > 499) {
                            alert("solo puede ingresar 500 caracteres");
                            obs.value = obs.value.substring(0, 499);
                            document.formulario.obs.focus();
                            return false;
                        }
                    }
                </script>
                <!-- Placed at the end of the document so the pages load faster -->               
                </body>
                </html>
