<%-- 
    Document   : info_fam
    Created on : 7/11/2013, 11:46:35 AM
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
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                            <%if (u.getRol().equals("DAPA") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                            <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-8">
                        <form role="form" action="${pageContext.servletContext.contextPath}/ActualizarRegistro" method="post">
                            <!-- <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  -->
                            <c:if test="${estado != 'formativa'}">
                                <br>
                                <h1 align="center"><strong>Familia "${expediente.getExpediente()}"</strong></h1>
                                <br>
                            </c:if>
                            <br>
                            <br>
                            <ul class="nav nav-tabs row">
                                <li><a href="${pageContext.servletContext.contextPath}/laSolicitante">La Solicitante</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/elSolicitante" >El solicitante</a></li>
                                <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/compFamiliar" >Composición familiar</a></li>-->
                                <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/vivienda" >Vivienda</a></li>-->
                                <li ${estado == 'formativa' ? 'class="hidden"' : 'class="active"'} ><a href="${pageContext.servletContext.contextPath}/infoExpediente" >Información del Expediente</a></li>
                                <li ><a href="${pageContext.servletContext.contextPath}/procesoAdopcion" >Proceso de adopción</a></li>
                                <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/antNna" >Antecedentes del NNA</a></li>
                                <li ${estado == 'formativa' || estado == 'evaluacion' || estado == 'espera' || estado == 'designacion' || estado == 'adopcion' || estado == 'reevaluacion' ? 'class="hidden"' : ''} ><a href="${pageContext.servletContext.contextPath}/nnaAsociado" >NNA Adoptado</a></li>
                                <li ><a href="${pageContext.servletContext.contextPath}/atenciones" >Atenciones</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/EditUserPass" >Editar Perfil de Familia</a></li>
                            </ul>
                            <br>
                            <h3><strong>Información de la Ficha de Inscripción de Solicitantes de Adopción</strong></h3>
                            <br>
                            <div class="control-group">
                                <label class="control-label">HT</label>
                                <div class="controls">
                                    <input id="htFicha" name="htFicha" value="${expediente.getHtFicha()}" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Número de la ficha</label>
                                <div class="controls">
                                    <input id="nFicha" name="nFicha" value="${expediente.getnFicha()}" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>    
                            <div class="control-group">
                                <label class="control-label">Fecha de ingreso</label>
                                <div class="controls">
                                    <input id="fechaIngresoFicha" name="fechaIngresoFicha" value="${expediente.getFechaIngresoFicha() != null ? df.dateToStringNumeros(expediente.getFechaIngresoFicha()) : ''}" type="text" class="datepicker input-xlarge">
                                </div>
                            </div>
                            <br>
                            <h3><strong>Información del Expediente</strong></h3>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Expediente</label>
                                <div class="controls">
                                    <input disabled id="expediente" name="expediente" value="${expediente.getExpediente()}" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">HT</label>
                                <div class="controls">
                                    <input id="ht" name="ht" value="${expediente.getHt()}" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Número de Expediente</label>
                                <div class="controls">
                                    <input id="numeroExp" name="numeroExp" value="${expediente.getNumeroExpediente()}" type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Fecha ingreso a DGA</label>
                                <div class="controls">
                                    <input id="fechaIngreso" name="fechaIngreso" value="${expediente.getFechaIngresoDga() != null ? df.dateToStringNumeros(expediente.getFechaIngresoDga()) : ''}" type="text" class="datepicker input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Estado</label>
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
                                <label class="control-label">TUPA</label>
                                <div class="controls">
                                    <input id="tupa" name="tupa" value="${expediente.getTupa() != null ? df.dateToStringNumeros(expediente.getTupa()) : ''}" type="text" class="datepicker input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Nacionalidad</label>
                                <div class="controls">
                                    <select onchange="funcNacion(this.value)" id="nacionalidad" name="nacionalidad">
                                        <option value="nacional" ${expediente.getNacionalidad() == 'nacional' ? 'selected' : ''} >N</option>
                                        <option value="internacional" ${expediente.getNacionalidad() == 'internacional' ? 'selected' : ''} >I</option>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Pertenece al RNSA:</label>
                                <select id="rnsa" name="rnsa" disabled>
                                    <option value="0" ${expediente.getRnsa() == 0 ? 'selected' : ''} >Si</option>
                                    <option value="1" ${expediente.getRnsa() == 1 ? 'selected' : ''} >No</option>
                                </select>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Pertenece al RNAA:</label>
                                <select id="rnaa" name="rnaa" disabled>
                                    <option value="0" ${expediente.getRnaa() == 0 ? 'selected' : ''} >Si</option>
                                    <option value="1" ${expediente.getRnaa() == 1 ? 'selected' : ''} >No</option>
                                </select>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Tipo de familia</label>
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
                                <label class="control-label">Tipo de Lista de Espera</label>
                                <div class="controls">
                                    <select id="tipoEspera" name="tipoEspera">
                                        <option value="nac" ${expediente.getTipoListaEspera() == 'nac' ? 'selected' : ''} >Nacionales</option>
                                        <option value="pre" ${expediente.getTipoListaEspera() == 'pre' ? 'selected' : ''} >Peruanos Residentes en el Extranjero</option>
                                        <option value="mix" ${expediente.getTipoListaEspera() == 'mix' ? 'selected' : ''} >Mixtos</option>
                                        <option value="ext" ${expediente.getTipoListaEspera() == 'ext' ? 'selected' : ''} >Extranjeros</option>
                                    </select>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">Unidad de Adopción donde se realiza el trámite</label>
                                <div class="controls">
                                    <select id="unidad" name="unidad" >
                                        <c:forEach var="ua" items="${listaUA}" > 
                                            <option value="${ua.getIdunidad()}" ${expediente.getUnidad().getIdunidad() ==  ua.getIdunidad() ? 'selected' : ''}>${ua.getNombre()}</option>
                                        </c:forEach>
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
                                    <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
                                </div>
                            </div>
                            <!--FIN DE CONTENIDO-->
                        </form>
                    </div>
                </div>
            </div>
            <br>
            <br>
            <div id="footer">
                <div id="ja-footer" class="wrap">
                    <hr width=80% align="center">
                    <p align="center"><h5 class="caption" align="center" style="text-align: center;">MINISTERIO DE LA MUJER Y POBLACIONES VULNERABLES<br>Jr. Camaná 616, Lima - Perú<br>Central telefónica: (511) 626-1600</h5></p>
                    <p align="right">Diseñado por RED<br>www.red.net.pe</p>
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
                                        $('#fechaIngreso').on('changeDate', function(ev) {

                                            var ingreso = document.getElementById("fechaIngreso").value;
                                            var tupa = document.getElementById("tupa");
                                            var nacionalidad = document.getElementById("nacionalidad");
                                            var opcion = nacionalidad.options[nacionalidad.selectedIndex].value;

                                            var dia = 0;
                                            var mes = 0;
                                            var anho = 0;

                                            var pieces = ingreso.split('/');
                                            var date = parseInt(pieces[0]);
                                            var month = parseInt(pieces[1]);
                                            var year = parseInt(pieces[2]);
                                            //alert(opcion);
                                            if (opcion == 'nacional') {
                                                dia = date + 15;
                                                if (dia > 30) {
                                                    dia = dia - 30;
                                                    mes = month + 1;
                                                } else {
                                                    mes = month;
                                                }
                                                if (mes > 12) {
                                                    mes = mes - 12;
                                                    anho = year + 1;
                                                } else {
                                                    anho = year;
                                                }
                                            } else {
                                                dia = date;
                                                mes = month + 3;
                                                if (mes > 12) {
                                                    mes = mes - 12;
                                                    anho = year + 1;
                                                } else {
                                                    anho = year;
                                                }
                                            }
                                            tupa.value = dia + "/" + mes + "/" + anho;


                                        });
            </script>
            <script>
                function funcNacion(value)
                {
                    var tipoFam = document.getElementById("tipoFamilia");
                    var tipoListEsp = document.getElementById("tipoEspera");

                    //you can get the value from arguments itself
                    //alert(value);
                    if (value == 'nacional') {
                        tipoFam.value = 'PP';
                        tipoListEsp.value = 'nac';
                    }
                    if (value == 'internacional') {
                        tipoFam.value = 'EE';
                        tipoListEsp.value = 'ext';
                    }
                }


            </script>
            <script>
                function funcTipoFam(value)
                {
                    var tipoListEsp = document.getElementById("tipoEspera");

                    //you can get the value from arguments itself
                    //alert(value);
                    if (value == 'PP' || value == 'EP' || value == 'MP') {
                        tipoListEsp.value = 'nac';
                    }
                    if (value == 'PE') {
                        tipoListEsp.value = 'pre';
                    }
                    if (value == 'ME') {
                        tipoListEsp.value = 'mix';
                    }
                    if (value == 'EE') {
                        tipoListEsp.value = 'ext';
                    }
                }


            </script>
            <!-- Ubicar al final -->
    </body>
</html>
