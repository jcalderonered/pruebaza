<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
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

    <body onload="funct()" id="bd" class="bd fs3 com_content">
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
                    <div class="col-md-8">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}${volver}'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <form role="form" action="${pageContext.servletContext.contextPath}/ActualizarAdoptante" method="post" name="formulario"> <!--onsubmit="return(validar());">-->
                            <input hidden id="adoptante" name="adoptante" value="el">
                            <input hidden id="volver" name="volver" value="${volver}">

                            <c:if test="${estado != 'formativa'}">
                                <br>
                                <h1 align="center"><strong>Familia "${expediente.getExpediente()}"</strong></h1>
                                <br>
                            </c:if>
                            <br>
                            <br>
                            <ul class="nav nav-tabs row">
                                <li><a href="${pageContext.servletContext.contextPath}/laSolicitante?volver=${volver}">La Solicitante</a></li>
                                <li class="active"><a href="${pageContext.servletContext.contextPath}/elSolicitante?volver=${volver}" >El solicitante</a></li>
                                <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/compFamiliar" >Composición familiar</a></li>-->
                                <!--<li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/vivienda" >Vivienda</a></li>-->
                                <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/infoExpediente?volver=${volver}" >Información del Expediente</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/procesoAdopcion?volver=${volver}" >Historial de la Familia</a></li>
                                <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/antNna?volver=${volver}" >Antecedentes del NNA</a></li>
                                <li ${estado == 'formativa' || estado == 'evaluacion' || estado == 'espera' || estado == 'designacion' || estado == 'adopcion' || estado == 'reevaluacion' ? 'class="hidden"' : ''} ><a href="${pageContext.servletContext.contextPath}/nnaAsociado?volver=${volver}" >NNA Adoptado</a></li>
                                    <% if (!u.getRol().equals("DEIA")) {%>
                                <li><a href="${pageContext.servletContext.contextPath}/atenciones?volver=${volver}" >Atenciones</a></li>
                                    <%}%>
                                    <% if (u.getRol().equals("DCRI")) {%>
                                <li><a href="${pageContext.servletContext.contextPath}/EditUserPass?volver=${volver}" >Editar Perfil de Familia</a></li>
                                    <%}%>
                            </ul>
                            <br>
                            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                            <fieldset>
                                <br>
                                <h3><strong>Generales</strong></h3>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label">Nombre</label>
                                    <div class="controls">
                                        <input value="${El.getNombre()}" id="nombre" name="nombre" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Paterno</label>
                                    <div class="controls">
                                        <input value="${El.getApellidoP()}" id="apellidoP" name="apellidoP" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Materno</label>
                                    <div class="controls">
                                        <input value="${El.getApellidoM()}" id="apellidoM" name="apellidoM" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de nacimiento</label>
                                    <div class="controls">
                                        <input value="${El.getFechaNac() != null ? df.dateToStringNumeros(El.getFechaNac()) : ''}" id="fechaNac" name="fechaNac" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Edad</label>  
                                    <div class="controls">
                                        <input disabled id="edad" name="edad" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Lugar de nacimiento</label>
                                    <div class="controls">
                                        <input value="${El.getLugarNac()}" id="lugarNac" name="lugarNac" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Departamento de nacimiento</label>
                                    <div class="controls">
                                        <input value="${El.getDepaNac()}" id="depNac" name="depNac" onkeyup="return(limitar2());" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">País de Nacimiento<font style="color:red">(*)</font></label>
                                    <div class="controls">
                                        <select id="paisNac" name="paisNac">
                                            <option value="AF" ${El.getPaisNac() == "AF" ? 'selected' : ''} >Afganistán</option>
                                            <option value="AL" ${El.getPaisNac() == "AL" ? 'selected' : ''} >Albania</option>
                                            <option value="DE" ${El.getPaisNac() == "DE" ? 'selected' : ''} >Alemania</option>
                                            <option value="AD" ${El.getPaisNac() == "AD" ? 'selected' : ''} >Andorra</option>
                                            <option value="AO" ${El.getPaisNac() == "AO" ? 'selected' : ''} >Angola</option>
                                            <option value="AI" ${El.getPaisNac() == "AI" ? 'selected' : ''} >Anguilla</option>
                                            <option value="AQ" ${El.getPaisNac() == "AQ" ? 'selected' : ''} >Antártida</option>
                                            <option value="AG" ${El.getPaisNac() == "AG" ? 'selected' : ''} >Antigua y Barbuda</option>
                                            <option value="AN" ${El.getPaisNac() == "AN" ? 'selected' : ''} >Antillas Holandesas</option>
                                            <option value="SA" ${El.getPaisNac() == "SA" ? 'selected' : ''} >Arabia Saudí</option>
                                            <option value="DZ" ${El.getPaisNac() == "DZ" ? 'selected' : ''} >Argelia</option>
                                            <option value="AR" ${El.getPaisNac() == "AR" ? 'selected' : ''} >Argentina</option>
                                            <option value="AM" ${El.getPaisNac() == "AM" ? 'selected' : ''} >Armenia</option>
                                            <option value="AW" ${El.getPaisNac() == "AW" ? 'selected' : ''} >Aruba</option>
                                            <option value="AU" ${El.getPaisNac() == "AU" ? 'selected' : ''} >Australia</option>
                                            <option value="AT" ${El.getPaisNac() == "AT" ? 'selected' : ''} >Austria</option>
                                            <option value="AZ" ${El.getPaisNac() == "AZ" ? 'selected' : ''} >Azerbaiyán</option>
                                            <option value="BS" ${El.getPaisNac() == "BS" ? 'selected' : ''} >Bahamas</option>
                                            <option value="BH" ${El.getPaisNac() == "BH" ? 'selected' : ''} >Bahrein</option>
                                            <option value="BD" ${El.getPaisNac() == "BD" ? 'selected' : ''} >Bangladesh</option>
                                            <option value="BB" ${El.getPaisNac() == "BB" ? 'selected' : ''} >Barbados</option>
                                            <option value="BE" ${El.getPaisNac() == "BE" ? 'selected' : ''} >Bélgica</option>
                                            <option value="BZ" ${El.getPaisNac() == "BZ" ? 'selected' : ''} >Belice</option>
                                            <option value="BJ" ${El.getPaisNac() == "BJ" ? 'selected' : ''} >Benin</option>
                                            <option value="BM" ${El.getPaisNac() == "BM" ? 'selected' : ''} >Bermudas</option>
                                            <option value="BY" ${El.getPaisNac() == "BY" ? 'selected' : ''} >Bielorrusia</option>
                                            <option value="MM" ${El.getPaisNac() == "MM" ? 'selected' : ''} >Birmania</option>
                                            <option value="BO" ${El.getPaisNac() == "BO" ? 'selected' : ''} >Bolivia</option>
                                            <option value="BA" ${El.getPaisNac() == "BA" ? 'selected' : ''} >Bosnia y Herzegovina</option>
                                            <option value="BW" ${El.getPaisNac() == "BW" ? 'selected' : ''} >Botswana</option>
                                            <option value="BR" ${El.getPaisNac() == "BR" ? 'selected' : ''} >Brasil</option>
                                            <option value="BN" ${El.getPaisNac() == "BN" ? 'selected' : ''} >Brunei</option>
                                            <option value="BG" ${El.getPaisNac() == "BG" ? 'selected' : ''} >Bulgaria</option>
                                            <option value="BF" ${El.getPaisNac() == "BF" ? 'selected' : ''} >Burkina Faso</option>
                                            <option value="BI" ${El.getPaisNac() == "BI" ? 'selected' : ''} >Burundi</option>
                                            <option value="BT" ${El.getPaisNac() == "BT" ? 'selected' : ''} >Bután</option>
                                            <option value="CV" ${El.getPaisNac() == "CV" ? 'selected' : ''} >Cabo Verde</option>
                                            <option value="KH" ${El.getPaisNac() == "KH" ? 'selected' : ''} >Camboya</option>
                                            <option value="CM" ${El.getPaisNac() == "CM" ? 'selected' : ''} >Camerún</option>
                                            <option value="CA" ${El.getPaisNac() == "CA" ? 'selected' : ''} >Canadá</option>
                                            <option value="TD" ${El.getPaisNac() == "TD" ? 'selected' : ''} >Chad</option>
                                            <option value="CL" ${El.getPaisNac() == "CL" ? 'selected' : ''} >Chile</option>
                                            <option value="CN" ${El.getPaisNac() == "CN" ? 'selected' : ''} >China</option>
                                            <option value="CY" ${El.getPaisNac() == "CY" ? 'selected' : ''} >Chipre</option>
                                            <option value="VA" ${El.getPaisNac() == "VA" ? 'selected' : ''} >Ciudad del Vaticano (Santa Sede)</option>
                                            <option value="CO" ${El.getPaisNac() == "CO" ? 'selected' : ''} >Colombia</option>
                                            <option value="KM" ${El.getPaisNac() == "KM" ? 'selected' : ''} >Comores</option>
                                            <option value="CG" ${El.getPaisNac() == "CG" ? 'selected' : ''} >Congo</option>
                                            <option value="CD" ${El.getPaisNac() == "CD" ? 'selected' : ''} >Congo, República Democrática del</option>
                                            <option value="KR" ${El.getPaisNac() == "KR" ? 'selected' : ''} >Corea</option>
                                            <option value="KP" ${El.getPaisNac() == "KP" ? 'selected' : ''} >Corea del Norte</option>
                                            <option value="CI" ${El.getPaisNac() == "CI" ? 'selected' : ''} >Costa de Marfíl</option>
                                            <option value="CR" ${El.getPaisNac() == "CR" ? 'selected' : ''} >Costa Rica</option>
                                            <option value="HR" ${El.getPaisNac() == "HR" ? 'selected' : ''} >Croacia (Hrvatska)</option>
                                            <option value="CU" ${El.getPaisNac() == "CU" ? 'selected' : ''} >Cuba</option>
                                            <option value="DK" ${El.getPaisNac() == "DK" ? 'selected' : ''} >Dinamarca</option>
                                            <option value="DJ" ${El.getPaisNac() == "DJ" ? 'selected' : ''} >Djibouti</option>
                                            <option value="DM" ${El.getPaisNac() == "DM" ? 'selected' : ''} >Dominica</option>
                                            <option value="EC" ${El.getPaisNac() == "EC" ? 'selected' : ''} >Ecuador</option>
                                            <option value="EG" ${El.getPaisNac() == "EG" ? 'selected' : ''} >Egipto</option>
                                            <option value="SV" ${El.getPaisNac() == "SV" ? 'selected' : ''} >El Salvador</option>
                                            <option value="AE" ${El.getPaisNac() == "AE" ? 'selected' : ''} >Emiratos Árabes Unidos</option>
                                            <option value="ER" ${El.getPaisNac() == "ER" ? 'selected' : ''} >Eritrea</option>
                                            <option value="SI" ${El.getPaisNac() == "SI" ? 'selected' : ''} >Eslovenia</option>
                                            <option value="ES" ${El.getPaisNac() == "ES" ? 'selected' : ''} >España</option>
                                            <option value="US" ${El.getPaisNac() == "US" ? 'selected' : ''} >Estados Unidos</option>
                                            <option value="EE" ${El.getPaisNac() == "EE" ? 'selected' : ''} >Estonia</option>
                                            <option value="ET" ${El.getPaisNac() == "ET" ? 'selected' : ''} >Etiopía</option>
                                            <option value="FJ" ${El.getPaisNac() == "FJ" ? 'selected' : ''} >Fiji</option>
                                            <option value="PH" ${El.getPaisNac() == "PH" ? 'selected' : ''} >Filipinas</option>
                                            <option value="FI" ${El.getPaisNac() == "FI" ? 'selected' : ''} >Finlandia</option>
                                            <option value="FR" ${El.getPaisNac() == "FR" ? 'selected' : ''} >Francia</option>
                                            <option value="GA" ${El.getPaisNac() == "GA" ? 'selected' : ''} >Gabón</option>
                                            <option value="GM" ${El.getPaisNac() == "GM" ? 'selected' : ''} >Gambia</option>
                                            <option value="GE" ${El.getPaisNac() == "GE" ? 'selected' : ''} >Georgia</option>
                                            <option value="GH" ${El.getPaisNac() == "GH" ? 'selected' : ''} >Ghana</option>
                                            <option value="GI" ${El.getPaisNac() == "GI" ? 'selected' : ''} >Gibraltar</option>
                                            <option value="GD" ${El.getPaisNac() == "GD" ? 'selected' : ''} >Granada</option>
                                            <option value="GR" ${El.getPaisNac() == "GR" ? 'selected' : ''} >Grecia</option>
                                            <option value="GL" ${El.getPaisNac() == "GL" ? 'selected' : ''} >Groenlandia</option>
                                            <option value="GP" ${El.getPaisNac() == "GP" ? 'selected' : ''} >Guadalupe</option>
                                            <option value="GU" ${El.getPaisNac() == "GU" ? 'selected' : ''} >Guam</option>
                                            <option value="GT" ${El.getPaisNac() == "GT" ? 'selected' : ''} >Guatemala</option>
                                            <option value="GY" ${El.getPaisNac() == "GY" ? 'selected' : ''} >Guayana</option>
                                            <option value="GF" ${El.getPaisNac() == "GF" ? 'selected' : ''} >Guayana Francesa</option>
                                            <option value="GN" ${El.getPaisNac() == "GN" ? 'selected' : ''} >Guinea</option>
                                            <option value="GQ" ${El.getPaisNac() == "GQ" ? 'selected' : ''} >Guinea Ecuatorial</option>
                                            <option value="GW" ${El.getPaisNac() == "GW" ? 'selected' : ''} >Guinea-Bissau</option>
                                            <option value="HT" ${El.getPaisNac() == "HT" ? 'selected' : ''} >Haití</option>
                                            <option value="HN" ${El.getPaisNac() == "HN" ? 'selected' : ''} >Honduras</option>
                                            <option value="HU" ${El.getPaisNac() == "HU" ? 'selected' : ''} >Hungría</option>
                                            <option value="IN" ${El.getPaisNac() == "IN" ? 'selected' : ''} >India</option>
                                            <option value="ID" ${El.getPaisNac() == "ID" ? 'selected' : ''} >Indonesia</option>
                                            <option value="IQ" ${El.getPaisNac() == "IQ" ? 'selected' : ''} >Irak</option>
                                            <option value="IR" ${El.getPaisNac() == "IR" ? 'selected' : ''} >Irán</option>
                                            <option value="IE" ${El.getPaisNac() == "IE" ? 'selected' : ''} >Irlanda</option>
                                            <option value="BV" ${El.getPaisNac() == "BV" ? 'selected' : ''} >Isla Bouvet</option>
                                            <option value="CX" ${El.getPaisNac() == "CX" ? 'selected' : ''} >Isla de Christmas</option>
                                            <option value="IS" ${El.getPaisNac() == "IS" ? 'selected' : ''} >Islandia</option>
                                            <option value="KY" ${El.getPaisNac() == "KY" ? 'selected' : ''} >Islas Caimán</option>
                                            <option value="CK" ${El.getPaisNac() == "CK" ? 'selected' : ''} >Islas Cook</option>
                                            <option value="CC" ${El.getPaisNac() == "CC" ? 'selected' : ''} >Islas de Cocos o Keeling</option>
                                            <option value="FO" ${El.getPaisNac() == "FO" ? 'selected' : ''} >Islas Faroe</option>
                                            <option value="HM" ${El.getPaisNac() == "HM" ? 'selected' : ''} >Islas Heard y McDonald</option>
                                            <option value="FK" ${El.getPaisNac() == "FK" ? 'selected' : ''} >Islas Malvinas</option>
                                            <option value="MP" ${El.getPaisNac() == "MP" ? 'selected' : ''} >Islas Marianas del Norte</option>
                                            <option value="MH" ${El.getPaisNac() == "MH" ? 'selected' : ''} >Islas Marshall</option>
                                            <option value="UM" ${El.getPaisNac() == "UM" ? 'selected' : ''} >Islas menores de Estados Unidos</option>
                                            <option value="PW" ${El.getPaisNac() == "PW" ? 'selected' : ''} >Islas Palau</option>
                                            <option value="SB" ${El.getPaisNac() == "SB" ? 'selected' : ''} >Islas Salomón</option>
                                            <option value="SJ" ${El.getPaisNac() == "SJ" ? 'selected' : ''} >Islas Svalbard y Jan Mayen</option>
                                            <option value="TK" ${El.getPaisNac() == "TK" ? 'selected' : ''} >Islas Tokelau</option>
                                            <option value="TC" ${El.getPaisNac() == "TC" ? 'selected' : ''} >Islas Turks y Caicos</option>
                                            <option value="VI" ${El.getPaisNac() == "VI" ? 'selected' : ''} >Islas Vírgenes (EEUU)</option>
                                            <option value="VG" ${El.getPaisNac() == "VG" ? 'selected' : ''} >Islas Vírgenes (Reino Unido)</option>
                                            <option value="WF" ${El.getPaisNac() == "WF" ? 'selected' : ''} >Islas Wallis y Futuna</option>
                                            <option value="IL" ${El.getPaisNac() == "IL" ? 'selected' : ''} >Israel</option>
                                            <option value="IT" ${El.getPaisNac() == "IT" ? 'selected' : ''} >Italia</option>
                                            <option value="JM" ${El.getPaisNac() == "JM" ? 'selected' : ''} >Jamaica</option>
                                            <option value="JP" ${El.getPaisNac() == "JP" ? 'selected' : ''} >Japón</option>
                                            <option value="JO" ${El.getPaisNac() == "JO" ? 'selected' : ''} >Jordania</option>
                                            <option value="KZ" ${El.getPaisNac() == "KZ" ? 'selected' : ''} >Kazajistán</option>
                                            <option value="KE" ${El.getPaisNac() == "KE" ? 'selected' : ''} >Kenia</option>
                                            <option value="KG" ${El.getPaisNac() == "KG" ? 'selected' : ''} >Kirguizistán</option>
                                            <option value="KI" ${El.getPaisNac() == "KI" ? 'selected' : ''} >Kiribati</option>
                                            <option value="KW" ${El.getPaisNac() == "KW" ? 'selected' : ''} >Kuwait</option>
                                            <option value="LA" ${El.getPaisNac() == "LA" ? 'selected' : ''} >Laos</option>
                                            <option value="LS" ${El.getPaisNac() == "LS" ? 'selected' : ''} >Lesotho</option>
                                            <option value="LV" ${El.getPaisNac() == "LV" ? 'selected' : ''} >Letonia</option>
                                            <option value="LB" ${El.getPaisNac() == "LB" ? 'selected' : ''} >Líbano</option>
                                            <option value="LR" ${El.getPaisNac() == "LR" ? 'selected' : ''} >Liberia</option>
                                            <option value="LY" ${El.getPaisNac() == "LY" ? 'selected' : ''} >Libia</option>
                                            <option value="LI" ${El.getPaisNac() == "LI" ? 'selected' : ''} >Liechtenstein</option>
                                            <option value="LT" ${El.getPaisNac() == "LT" ? 'selected' : ''} >Lituania</option>
                                            <option value="LU" ${El.getPaisNac() == "LU" ? 'selected' : ''} >Luxemburgo</option>
                                            <option value="MK" ${El.getPaisNac() == "MK" ? 'selected' : ''} >Macedonia, Ex-República Yugoslava de</option>
                                            <option value="MG" ${El.getPaisNac() == "MG" ? 'selected' : ''} >Madagascar</option>
                                            <option value="MY" ${El.getPaisNac() == "MY" ? 'selected' : ''} >Malasia</option>
                                            <option value="MW" ${El.getPaisNac() == "MW" ? 'selected' : ''} >Malawi</option>
                                            <option value="MV" ${El.getPaisNac() == "MV" ? 'selected' : ''} >Maldivas</option>
                                            <option value="ML" ${El.getPaisNac() == "ML" ? 'selected' : ''} >Malí</option>
                                            <option value="MT" ${El.getPaisNac() == "MT" ? 'selected' : ''} >Malta</option>
                                            <option value="MA" ${El.getPaisNac() == "MA" ? 'selected' : ''} >Marruecos</option>
                                            <option value="MQ" ${El.getPaisNac() == "MQ" ? 'selected' : ''} >Martinica</option>
                                            <option value="MU" ${El.getPaisNac() == "MU" ? 'selected' : ''} >Mauricio</option>
                                            <option value="MR" ${El.getPaisNac() == "MR" ? 'selected' : ''} >Mauritania</option>
                                            <option value="YT" ${El.getPaisNac() == "YT" ? 'selected' : ''} >Mayotte</option>
                                            <option value="MX" ${El.getPaisNac() == "MX" ? 'selected' : ''} >México</option>
                                            <option value="FM" ${El.getPaisNac() == "FM" ? 'selected' : ''} >Micronesia</option>
                                            <option value="MD" ${El.getPaisNac() == "MD" ? 'selected' : ''} >Moldavia</option>
                                            <option value="MC" ${El.getPaisNac() == "MC" ? 'selected' : ''} >Mónaco</option>
                                            <option value="MN" ${El.getPaisNac() == "MN" ? 'selected' : ''} >Mongolia</option>
                                            <option value="MS" ${El.getPaisNac() == "MS" ? 'selected' : ''} >Montserrat</option>
                                            <option value="MZ" ${El.getPaisNac() == "MZ" ? 'selected' : ''} >Mozambique</option>
                                            <option value="NA" ${El.getPaisNac() == "NA" ? 'selected' : ''} >Namibia</option>
                                            <option value="NR" ${El.getPaisNac() == "NR" ? 'selected' : ''} >Nauru</option>
                                            <option value="NP" ${El.getPaisNac() == "NP" ? 'selected' : ''} >Nepal</option>
                                            <option value="NI" ${El.getPaisNac() == "NI" ? 'selected' : ''} >Nicaragua</option>
                                            <option value="NE" ${El.getPaisNac() == "NE" ? 'selected' : ''} >Níger</option>
                                            <option value="NG" ${El.getPaisNac() == "NG" ? 'selected' : ''} >Nigeria</option>
                                            <option value="NU" ${El.getPaisNac() == "NU" ? 'selected' : ''} >Niue</option>
                                            <option value="NF" ${El.getPaisNac() == "NF" ? 'selected' : ''} >Norfolk</option>
                                            <option value="NO" ${El.getPaisNac() == "NO" ? 'selected' : ''} >Noruega</option>
                                            <option value="NC" ${El.getPaisNac() == "NC" ? 'selected' : ''} >Nueva Caledonia</option>
                                            <option value="NZ" ${El.getPaisNac() == "NZ" ? 'selected' : ''} >Nueva Zelanda</option>
                                            <option value="OM" ${El.getPaisNac() == "OM" ? 'selected' : ''} >Omán</option>
                                            <option value="NL" ${El.getPaisNac() == "NL" ? 'selected' : ''} >Países Bajos</option>
                                            <option value="PA" ${El.getPaisNac() == "PA" ? 'selected' : ''} >Panamá</option>
                                            <option value="PG" ${El.getPaisNac() == "PG" ? 'selected' : ''} >Papúa Nueva Guinea</option>
                                            <option value="PK" ${El.getPaisNac() == "PK" ? 'selected' : ''} >Paquistán</option>
                                            <option value="PY" ${El.getPaisNac() == "PY" ? 'selected' : ''} >Paraguay</option>
                                            <option value="Peru" ${El.getPaisNac() == "Peru" ? 'selected' : ''} >Perú</option>
                                            <option value="PN" ${El.getPaisNac() == "PN" ? 'selected' : ''} >Pitcairn</option>
                                            <option value="PF" ${El.getPaisNac() == "PF" ? 'selected' : ''} >Polinesia Francesa</option>
                                            <option value="PL" ${El.getPaisNac() == "PL" ? 'selected' : ''} >Polonia</option>
                                            <option value="PT" ${El.getPaisNac() == "PT" ? 'selected' : ''} >Portugal</option>
                                            <option value="PR" ${El.getPaisNac() == "PR" ? 'selected' : ''} >Puerto Rico</option>
                                            <option value="QA" ${El.getPaisNac() == "QA" ? 'selected' : ''} >Qatar</option>
                                            <option value="UK" ${El.getPaisNac() == "UK" ? 'selected' : ''} >Reino Unido</option>
                                            <option value="CF" ${El.getPaisNac() == "CF" ? 'selected' : ''} >República Centroafricana</option>
                                            <option value="CZ" ${El.getPaisNac() == "CZ" ? 'selected' : ''} >República Checa</option>
                                            <option value="ZA" ${El.getPaisNac() == "ZA" ? 'selected' : ''} >República de Sudáfrica</option>
                                            <option value="DO" ${El.getPaisNac() == "DO" ? 'selected' : ''} >República Dominicana</option>
                                            <option value="SK" ${El.getPaisNac() == "SK" ? 'selected' : ''} >República Eslovaca</option>
                                            <option value="RE" ${El.getPaisNac() == "RE" ? 'selected' : ''} >Reunión</option>
                                            <option value="RW" ${El.getPaisNac() == "RW" ? 'selected' : ''} >Ruanda</option>
                                            <option value="RO" ${El.getPaisNac() == "RO" ? 'selected' : ''} >Rumania</option>
                                            <option value="RU" ${El.getPaisNac() == "RU" ? 'selected' : ''} >Rusia</option>
                                            <option value="EH" ${El.getPaisNac() == "EH" ? 'selected' : ''} >Sahara Occidental</option>
                                            <option value="KN" ${El.getPaisNac() == "KN" ? 'selected' : ''} >Saint Kitts y Nevis</option>
                                            <option value="WS" ${El.getPaisNac() == "WS" ? 'selected' : ''} >Samoa</option>
                                            <option value="AS" ${El.getPaisNac() == "AS" ? 'selected' : ''} >Samoa Americana</option>
                                            <option value="SM" ${El.getPaisNac() == "SM" ? 'selected' : ''} >San Marino</option>
                                            <option value="VC" ${El.getPaisNac() == "VC" ? 'selected' : ''} >San Vicente y Granadinas</option>
                                            <option value="SH" ${El.getPaisNac() == "SH" ? 'selected' : ''} >Santa Helena</option>
                                            <option value="LC" ${El.getPaisNac() == "LC" ? 'selected' : ''} >Santa Lucía</option>
                                            <option value="ST" ${El.getPaisNac() == "ST" ? 'selected' : ''} >Santo Tomé y Príncipe</option>
                                            <option value="SN" ${El.getPaisNac() == "SN" ? 'selected' : ''} >Senegal</option>
                                            <option value="SC" ${El.getPaisNac() == "SC" ? 'selected' : ''} >Seychelles</option>
                                            <option value="SL" ${El.getPaisNac() == "SL" ? 'selected' : ''} >Sierra Leona</option>
                                            <option value="SG" ${El.getPaisNac() == "SG" ? 'selected' : ''} >Singapur</option>
                                            <option value="SY" ${El.getPaisNac() == "SY" ? 'selected' : ''} >Siria</option>
                                            <option value="SO" ${El.getPaisNac() == "SO" ? 'selected' : ''} >Somalia</option>
                                            <option value="LK" ${El.getPaisNac() == "LK" ? 'selected' : ''} >Sri Lanka</option>
                                            <option value="PM" ${El.getPaisNac() == "PM" ? 'selected' : ''} >St Pierre y Miquelon</option>
                                            <option value="SZ" ${El.getPaisNac() == "SZ" ? 'selected' : ''} >Suazilandia</option>
                                            <option value="SD" ${El.getPaisNac() == "SD" ? 'selected' : ''} >Sudán</option>
                                            <option value="SE" ${El.getPaisNac() == "SE" ? 'selected' : ''} >Suecia</option>
                                            <option value="CH" ${El.getPaisNac() == "CH" ? 'selected' : ''} >Suiza</option>
                                            <option value="SR" ${El.getPaisNac() == "SR" ? 'selected' : ''} >Surinam</option>
                                            <option value="TH" ${El.getPaisNac() == "TH" ? 'selected' : ''} >Tailandia</option>
                                            <option value="TW" ${El.getPaisNac() == "TW" ? 'selected' : ''} >Taiwán</option>
                                            <option value="TZ" ${El.getPaisNac() == "TZ" ? 'selected' : ''} >Tanzania</option>
                                            <option value="TJ" ${El.getPaisNac() == "TJ" ? 'selected' : ''} >Tayikistán</option>
                                            <option value="TF" ${El.getPaisNac() == "TF" ? 'selected' : ''} >Territorios franceses del Sur</option>
                                            <option value="TP" ${El.getPaisNac() == "TP" ? 'selected' : ''} >Timor Oriental</option>
                                            <option value="TG" ${El.getPaisNac() == "TG" ? 'selected' : ''} >Togo</option>
                                            <option value="TO" ${El.getPaisNac() == "TO" ? 'selected' : ''} >Tonga</option>
                                            <option value="TT" ${El.getPaisNac() == "TT" ? 'selected' : ''} >Trinidad y Tobago</option>
                                            <option value="TN" ${El.getPaisNac() == "TN" ? 'selected' : ''} >Túnez</option>
                                            <option value="TM" ${El.getPaisNac() == "TM" ? 'selected' : ''} >Turkmenistán</option>
                                            <option value="TR" ${El.getPaisNac() == "TR" ? 'selected' : ''} >Turquía</option>
                                            <option value="TV" ${El.getPaisNac() == "TV" ? 'selected' : ''} >Tuvalu</option>
                                            <option value="UA" ${El.getPaisNac() == "UA" ? 'selected' : ''} >Ucrania</option>
                                            <option value="UG" ${El.getPaisNac() == "UG" ? 'selected' : ''} >Uganda</option>
                                            <option value="UY" ${El.getPaisNac() == "UY" ? 'selected' : ''} >Uruguay</option>
                                            <option value="UZ" ${El.getPaisNac() == "UZ" ? 'selected' : ''} >Uzbekistán</option>
                                            <option value="VU" ${El.getPaisNac() == "VU" ? 'selected' : ''} >Vanuatu</option>
                                            <option value="VE" ${El.getPaisNac() == "VE" ? 'selected' : ''} >Venezuela</option>
                                            <option value="VN" ${El.getPaisNac() == "VN" ? 'selected' : ''} >Vietnam</option>
                                            <option value="YE" ${El.getPaisNac() == "YE" ? 'selected' : ''} >Yemen</option>
                                            <option value="YU" ${El.getPaisNac() == "YU" ? 'selected' : ''} >Yugoslavia</option>
                                            <option value="ZM" ${El.getPaisNac() == "ZM" ? 'selected' : ''} >Zambia</option>
                                            <option value="ZW" ${El.getPaisNac() == "ZW" ? 'selected' : ''} >Zimbabue</option>
                                        </select>
                                    </div>    
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-2">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="doc" id="optionsRadios1" value="d" ${El.getTipoDoc() == 100 ? 'checked' : ''}>DNI</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="doc" id="optionsRadios2" value="c" ${El.getTipoDoc() == 99 ? 'checked' : ''}>Carnet de Extranjería</label>
                                        </div>                            
                                    </div>
                                </div>    
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <label class="control-label">N° de Documento</label>
                                        <input value="${El.getNDoc()}" id="numDoc" name="numDoc" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <c:if test="${expediente.getNacionalidad() == 'internacional'}">
                                    <div class="control-group">
                                        <div class="controls">
                                            <label class="control-label">Pasaporte</label>
                                            <input value="${El.getPasaporte()}" id="pasaporte" name="pasaporte" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                </c:if>
                                <div class="control-group">
                                    <label class="control-label">Celular</label>
                                    <div class="controls">
                                        <input value="${El.getCelular()}" id="numCel" name="numCel" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Correo Electrónico</label>
                                    <div class="controls">
                                        <input value="${El.getCorreo()}" id="correo" name="correo" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Estado Civil</h3>
                                <div class="row">

                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="soltera" value="soltero" ${infoFam.getEstadoCivil() == 'soltero' || infoFam.getEstadoCivil() == 'soltera' ? 'checked' : ''}>Soltero</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="casada" value="casados" ${infoFam.getEstadoCivil() == 'casados' ? 'checked' : ''}>Casado</label>
                                            <br>
                                            <label class="control-label">Fecha de matrimonio Civil</label>
                                            <input ${infoFam.getEstadoCivil() == 'casados' ? '' : 'disabled'} value="${infoFam.getFechaMatrimonio() != null ? df.dateToStringNumeros(infoFam.getFechaMatrimonio()) : ''}" id="fechaMat" name="fechaMat" type="text" class="datepicker input-xlarge">
                                        </div>                            
                                    </div>
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="viuda" value="viudo" ${infoFam.getEstadoCivil() == 'viudo' || infoFam.getEstadoCivil() == 'viuda' ? 'checked' : ''}>Viudo</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="divorciada" value="divorciado" ${infoFam.getEstadoCivil() == 'divorciado' || infoFam.getEstadoCivil() == 'divorciada' ? 'checked' : ''}>Divorciado</label>
                                        </div>
                                    </div> 
                                </div>
                                <!--        
                                <br>
                                <h3><strong>Educación, Ocupación e Ingresos Económicos</strong></h3>
                                <br>
                                <h3>Nivel de instrucción alcanzado</h3>
                                <div class="row">

                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="nivelInstruccion" id="ninguno" value="Ninguno" ${El.getNivelInstruccion() == 'Ninguno' ? 'checked' : ''}>Ninguno</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="nivelInstruccion" id="primaria" value="Primaria" ${El.getNivelInstruccion() == 'Primaria' ? 'checked' : ''}>Primaria</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="nivelInstruccion" id="secundaria" value="Secundaria" ${El.getNivelInstruccion() == 'Secundaria' ? 'checked' : ''}>Secundaria</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="nivelInstruccion" id="tecnico" value="Tecnico" ${El.getNivelInstruccion() == 'Tecnico' ? 'checked' : ''}>Técnico</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="nivelInstruccion" id="superior" value="Superior" ${El.getNivelInstruccion() == 'Superior' ? 'checked' : ''}>Superior</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="nivelInstruccion" id="otros" value="Otros" ${El.getNivelInstruccion() == 'Otros' ? 'checked' : ''}>Otros</label>
                                        </div>
                                    </div> 
                                </div> 
                                <br>
                                <h3>Culminó el nivel de instrucción señalado</h3>
                                <div class="row">

                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="culminoNivel" id="si" value="0" ${El.getCulminoNivel() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="culminoNivel" id="no" value="1" ${El.getCulminoNivel() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>  
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Profesión u Oficio </label>
                                    <div class="controls">
                                        <input value="${El.getProfesion()}" id="profesion" name="profesion" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Trabajador dependiente (Elegir solo si corresponde)</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="checkbox">
                                            <label>
                                                <input onclick="Dep()" type="checkbox" name="trabDep" id="trabDep" value="0" ${El.getTrabajadorDepend() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>  
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ocupación Actual</label>
                                    <div class="controls">
                                        <input value="${El.getOcupActualDep()}" id="ocupacionDep" name="ocupacionDep" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Centro de Trabajo </label>
                                    <div class="controls">
                                        <input value="${El.getCentroTrabajo()}" id="centroTrabajo" name="centroTrabajo" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Dirección del centro de trabajo</label>
                                    <div class="controls">
                                        <input value="${El.getDireccionCentro()}" id="direccionTrabajo" name="direccionTrabajo" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Teléfono del centro de trabajo </label>
                                    <div class="controls">
                                        <input value="${El.getTelefonoCentro()}" id="telefonoTrabajo" name="telefonoTrabajo" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ingreso mensual sustentable (sueldo bruto) </label>
                                    <div class="controls">
                                        <input value="${El.getIngresoDep()}" id="ingresoDep" name="ingresoDep" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Trabajador independiente (Elegir solo si corresponde)</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="checkbox">
                                            <label>
                                                <input onclick="Indep()()" type="checkbox" name="trabIndep" id="trabIndep" value="0" ${El.getTrabajadorIndepend() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>  
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ocupación Actual</label>
                                    <div class="controls">
                                        <input value="${El.getOcupActualInd()}" id="ocupacionInd" name="ocupacionInd" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ingreso mensual sustentable</label>
                                    <div class="controls">
                                        <input value="${El.getIngresoIndep()}" id="ingresoInd" name="ingresoInd" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <br>
                                <h3><strong>Salud y Previsión</strong></h3>
                                <br>
                                <h3>Seguro de salud</h3>
                                <div class="row"> 
                                    <div class="col-md-2">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="seguroSalud" id="checkbox1" value="0" ${El.getSeguroSalud() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>  
                                    <div class="col-md-2">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="seguroSalud" id="checkbox1" value="1" ${El.getSeguroSalud() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Tipo de Seguro</label>
                                    <div class="controls">
                                        <input value="${El.getTipoSeguro()}" id="tipoSeguro" name="tipoSeguro" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Seguro de vida</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="seguroVida" id="vida_si" value="0" ${El.getSeguroVida() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>  
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="seguroVida" id="vida_no" value="1" ${El.getSeguroVida() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>
                                </div> 
                                <br>
                                <h3>Afiliado al sistema de pensiones</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="sisPensiones" id="afiliado_si" value="0" ${El.getSistPensiones() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>  
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="sisPensiones" id="afiliado_no" value="1" ${El.getSistPensiones() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Estado de salud actual</label>
                                    <div class="controls">
                                        <input value="${El.getSaludActual()}" id="estadoActual" name="estadoActual" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                -->    
                                <br>
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <button ${El.getIdadoptante() == null || El.getIdadoptante() == 0 || estado == 'formativa' ? 'disabled' : '' } type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                        <!--FIN DE CONTENIDO-->
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

                            $('#fechaNac').on('changeDate', function(ev) {

                                var nac = document.getElementById("fechaNac").value;
                                var edad = document.getElementById("edad");

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

                            });
            </script>
            <script type="text/javascript">
                function funct() {
                    var nac = document.getElementById("fechaNac").value;
                    var edad = document.getElementById("edad");

                    var today = new Date();
                    var curr_date = today.getDate();
                    var curr_month = today.getMonth() + 1;
                    var curr_year = today.getFullYear();

                    var pieces = nac.split('/');
                    var birth_date = pieces[0];
                    var birth_month = pieces[1];
                    if (birth_month == 'ene')
                        birth_month = 1;
                    if (birth_month == 'feb')
                        birth_month = 2;
                    if (birth_month == 'mar')
                        birth_month = 3;
                    if (birth_month == 'abr')
                        birth_month = 4;
                    if (birth_month == 'may')
                        birth_month = 5;
                    if (birth_month == 'jun')
                        birth_month = 6;
                    if (birth_month == 'jul')
                        birth_month = 7;
                    if (birth_month == 'ago')
                        birth_month = 8;
                    if (birth_month == 'sep')
                        birth_month = 9;
                    if (birth_month == 'oct')
                        birth_month = 10;
                    if (birth_month == 'nov')
                        birth_month = 11;
                    if (birth_month == 'dic')
                        birth_month = 12;

                    var birth_year = pieces[2];

                    if (curr_year != birth_year && birth_month > curr_month)
                        edad.value = curr_year - birth_year - 1;
                    if (curr_year != birth_year && birth_month == curr_month)
                        edad.value = curr_year - birth_year;
                    if (curr_year != birth_year && birth_month < curr_month)
                        edad.value = curr_year - birth_year;
                    if (curr_year == birth_year)
                        edad.value = 0;

                    var indep = document.getElementById('trabIndep');
                    var ocupInd = document.getElementById('ocupacionInd');
                    var ingInd = document.getElementById('ingresoInd');

                    var ocupDep = document.getElementById('ocupacionDep');
                    var centTra = document.getElementById('centroTrabajo');
                    var direcTrab = document.getElementById('direccionTrabajo');
                    var telfTrab = document.getElementById('telefonoTrabajo');
                    var ingDep = document.getElementById('ingresoDep');
                    var dep = document.getElementById('trabDep');

                    if (document.getElementById('trabDep').checked) {
                        indep.checked = false;
                        ocupInd.disabled = true;
                        ingInd.disabled = true;
                        ocupDep.disabled = false;
                        centTra.disabled = false;
                        direcTrab.disabled = false;
                        telfTrab.disabled = false;
                        ingDep.disabled = false;
                    }

                    if (document.getElementById('trabIndep').checked) {
                        dep.checked = false;
                        ocupInd.disabled = false;
                        ingInd.disabled = false;

                        ocupDep.disabled = true;
                        centTra.disabled = true;
                        direcTrab.disabled = true;
                        telfTrab.disabled = true;
                        ingDep.disabled = true;


                    }
                }

            </script>
            <script>
                function Dep()
                {
                    var indep = document.getElementById('trabIndep');
                    var ocupInd = document.getElementById('ocupacionInd');
                    var ingInd = document.getElementById('ingresoInd');

                    var ocupDep = document.getElementById('ocupacionDep');
                    var centTra = document.getElementById('centroTrabajo');
                    var direcTrab = document.getElementById('direccionTrabajo');
                    var telfTrab = document.getElementById('telefonoTrabajo');
                    var ingDep = document.getElementById('ingresoDep');

                    if (document.getElementById('trabDep').checked) {
                        indep.checked = false;
                        ocupInd.disabled = true;
                        ingInd.disabled = true;
                        ocupDep.disabled = false;
                        centTra.disabled = false;
                        direcTrab.disabled = false;
                        telfTrab.disabled = false;
                        ingDep.disabled = false;
                    } else {

                    }
                }
            </script>  
            <script>
                function Indep()
                {
                    var dep = document.getElementById('trabDep');
                    var ocupInd = document.getElementById('ocupacionInd');
                    var ingInd = document.getElementById('ingresoInd');

                    var ocupDep = document.getElementById('ocupacionDep');
                    var centTra = document.getElementById('centroTrabajo');
                    var direcTrab = document.getElementById('direccionTrabajo');
                    var telfTrab = document.getElementById('telefonoTrabajo');
                    var ingDep = document.getElementById('ingresoDep');

                    if (document.getElementById('trabIndep').checked) {
                        dep.checked = false;
                        ocupInd.disabled = false;
                        ingInd.disabled = false;

                        ocupDep.disabled = true;
                        centTra.disabled = true;
                        direcTrab.disabled = true;
                        telfTrab.disabled = true;
                        ingDep.disabled = true;


                    } else {

                    }
                }
            </script>  
            <script type="text/javascript">
                function validar()
                {
                    var numericExpression = /^[0-9]+$/;
                    if (document.getElementById('trabIndep').checked) {
                        if (document.formulario.ingresoInd.value.match(numericExpression))
                        {
                            return true
                        } else {

                            alert("El campo debe contener solo números");
                            document.formulario.ingresoInd.focus();
                            return false;
                        }
                    }
                    if (document.getElementById('trabDep').checked) {
                        if (document.formulario.ingresoDep.value.match(numericExpression))
                        {
                            return true
                        } else {

                            alert("El campo debe contener solo números");
                            document.formulario.ingresoDep.focus();
                            return false;
                        }
                    }
                    if (!document.getElementById('trabDep').checked || !document.getElementById('trabIndep').checked) {
                        alert("Debe elegir al menos un tipo de trabajo");
                        document.formulario.trabDep.focus();
                        return false;

                    }

                }
            </script>
            <script type="text/javascript">
                function limitar()
                {
                    var address = document.getElementById('lugarNac');
                    var nombre = document.getElementById('nombre');
                    var apellidoP = document.getElementById('apellidoP');
                    var apellidoM = document.getElementById('apellidoM');
                    var numDoc = document.getElementById('numDoc');
                    var pasaporte = document.getElementById('pasaporte');
                    var correo = document.getElementById('correo');
                    var celular = document.getElementById('numCel');
                    var depNac = document.getElementById('depNac');

                    if (address.value.length < 0 || address.value.length > 99)
                    {
                        alert("solo puede ingresar 100 caracteres");
                        address.value = address.value.substring(0, 99);
                        return false;
                    } else if (nombre.value.length < 0 || nombre.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        nombre.value = nombre.value.substring(0, 29);
                        return false;
                    } else if (apellidoP.value.length < 0 || apellidoP.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        apellidoP.value = apellidoP.value.substring(0, 29);
                        return false;
                    } else if (apellidoM.value.length < 0 || apellidoM.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        apellidoM.value = apellidoM.value.substring(0, 29);
                        return false;
                    } else if (numDoc.value.length < 0 || numDoc.value.length > 14) {
                        alert("solo puede ingresar 15 caracteres");
                        numDoc.value = numDoc.value.substring(0, 14);
                        return false;
                    } else if (celular.value.length < 0 || celular.value.length > 14) {
                        alert("solo puede ingresar 15 caracteres");
                        celular.value = celular.value.substring(0, 14);
                        return false;
                    } else if (correo.value.length < 0 || correo.value.length > 49) {
                        alert("solo puede ingresar 50 caracteres");
                        correo.value = correo.value.substring(0, 49);
                        return false;
                    } else if (pasaporte.value.length < 0 || pasaporte.value.length > 49) {
                        alert("solo puede ingresar 50 caracteres");
                        pasaporte.value = pasaporte.value.substring(0, 49);
                        return false;
                    } else if (depNac.value.length < 0 || depNac.value.length > 49) {
                        alert("solo puede ingresar 50 caracteres");
                        pasaporte.value = pasaporte.value.substring(0, 49);
                        return false;
                    }
                }
            </script>
            <script type="text/javascript">
                function limitar2()
                {
                    var depNac = document.getElementById('depNac');

                    if (depNac.value.length < 0 || depNac.value.length > 49) {
                        alert("solo puede ingresar 50 caracteres");
                        pasaporte.value = pasaporte.value.substring(0, 49);
                        return false;
                    }
                }
            </script>
            <script type="text/javascript">
                $(document).ready(function() {
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
    </body>
</html>

