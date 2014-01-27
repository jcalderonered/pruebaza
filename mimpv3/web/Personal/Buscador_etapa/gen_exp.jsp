<%-- 
    Document   : estado_proc
    Created on : 7/11/2013, 10:31:03 AM
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
                            <li><a href="${pageContext.servletContext.contextPath}/inf"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de sesiones/talleres</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNA</a></li>
                                <%if (u.getRol().equals("admin")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/juzgado"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de juzgado</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ua"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UA</a></li>
                                <%}
                                if (!u.getRol().equals("DAPA") && !u.getRol().equals("MATCH")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/famint"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                                <%}
                                if (!u.getRol().equals("mpartes")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de registros</a></li>
                                <%if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/usuarios"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                                <%}
                                if (u.getRol().equals("admin") || u.getRol().equals("DEIA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de organismo acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de autoridad central</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-8 ">
                        <!-- AQUI AGREGAR EL CONTENIDO QUE ESTARA AL COSTADO DEL MENU -->
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>   
                        <br>
                        <h3><strong>Datos generales del expediente</strong></h3>
                        <br>
                        <br>
                            <form role="form" action="${pageContext.servletContext.contextPath}/CrearExpNac" method="post">
                            <input hidden name="idFamilia" id="idFamilia" value="${idFamilia}"  >  
                            <input hidden name="exp" id="idFamilia" value="${exp}"  >  
                            <div class="control-group">
                                <label class="control-label">Número de Expediente </label>
                                <div class="controls">
                                    <input id="numeroExp" name="numeroExp" type="text" class="input-xlarge" value="${expediente.getNumeroExpediente()}">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">HT </label>
                                <div class="controls">
                                    <input id="ht" name="ht" type="text" class="input-xlarge" value="${expediente.getHt()}">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Fecha de ingreso</label>
                                <div class="controls">
                                    <input id="fechaIngreso" name="fechaIngreso" type="text" class="datepicker input-xlarge" value="${expediente.getFechaIngresoDga() != null ? df.dateToStringNumeros(expediente.getFechaIngresoDga()) : ''}">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label> Estado</label>
                                <div class="controls">
                                    <select disabled>
                                        <option value="evaluacion" ${expediente.getEstado() == 'evaluacion' ? 'selected' : ''} >Evaluación</option>
                                        <option value="espera" ${expediente.getEstado() == 'espera' ? 'selected' : ''} >Lista de espera</option>
                                        <option value="estudio" ${expediente.getEstado() == 'estudio' ? 'selected' : ''} >Estudio de caso</option>
                                        <option value="designacion" ${expediente.getEstado() == 'designacion' ? 'selected' : ''} >Designado</option>
                                        <option value="adopcion" ${expediente.getEstado() == 'adopcion' ? 'selected' : ''} >Adopción</option>
                                        <option value="post" ${expediente.getEstado() == 'post' ? 'selected' : ''} >Post Adopción</option>
                                    </select>
                                </div>   
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Tupa</label>
                                <div class="controls">
                                    <input id="tupa" name="tupa" type="text" class="datepicker input-xlarge" value="${expediente.getTupa() != null ? df.dateToStringNumeros(expediente.getTupa()) : ''}">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label> Tipo de familia</label>
                                <div class="controls">
                                    <select onchange="funcTipoFam(this.value)" id="tipoFamilia" name="tipoFamilia">
                                        <option value="PP" ${expediente.getTipoFamilia() == 'PP' ? 'selected' : ''} >PP</option>
                                        <option value="PE" ${expediente.getTipoFamilia() == 'PE' ? 'selected' : ''} >PE</option>
                                        <option value="MP" ${expediente.getTipoFamilia() == 'MP' ? 'selected' : ''} >MP</option>
                                        <option value="ME" ${expediente.getTipoFamilia() == 'ME' ? 'selected' : ''} >ME</option>
                                        <option value="EP" ${expediente.getTipoFamilia() == 'EP' ? 'selected' : ''} >EP</option>
                                        <option value="EE" ${expediente.getTipoFamilia() == 'EE' ? 'selected' : ''} >EE</option>
                                    </select>
                                </div>   
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Organismo Acreditado y/o Autoridad Central asociado</label>
                                <div class="controls">
                                    <c:if test="${expediente.getFamilia().getEntidad() != null}">
                                        <select id="entAsoc" name="entAsoc" >
                                            <c:forEach var="entidad" items="${listaEntidad}" > 
                                                <option value="${entidad.getIdentidad()}" ${expediente.getFamilia().getEntidad().getIdentidad() == entidad.getIdentidad() ? 'selected' : ''}>${entidad.getNombre()}</option>
                                            </c:forEach>
                                        </select>
                                    </c:if>
                                    
                                    <c:if test="${expediente.getFamilia().getEntidad() == null}">
                                        <select id="entAsoc" name="entAsoc" >
                                            <c:forEach var="entidad" items="${listaEntidad}" > 
                                                <option value="${entidad.getIdentidad()}" >${entidad.getNombre()}</option>
                                            </c:forEach>
                                        </select>
                                    </c:if>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <div class="controls">
                                    <button id="singlebutton" type="submit" name="singlebutton" class="btn btn-default">Guardar cambios</button>
                                </div>
                            </div>
                            <br>
                        </form>
                    </div>

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
                <p align="right">Diseñado por RED<br>www.red.net.pe</p>
            </div>
        </div><!-- core JavaScript
    ================================================== -->
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
        <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
        <script type="text/javascript">

            $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

        </script>
        <!-- Ubicar al final -->
    </body>
</html>