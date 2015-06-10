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

                        <form role="form" action="${pageContext.servletContext.contextPath}/ActualizarAdoptante" method="post" name="formulario" ><!--onsubmit="return(validar());" --> 
                            <input hidden id="adoptante" name="adoptante" value="ella">
                            <input hidden id="volver" name="volver" value="${volver}">
                            <input hidden id="volver" name="volver" value="${idFamilia}">
                            <input hidden id="volver" name="volver" value="${estado}">
                            
                            <c:if test="${estado != 'formativa'}">
                                <br>
                                <h1 align="center"><strong>Familia "${expediente.getExpediente()}" </strong></h1>
                                <br>
                            </c:if>  
                            <br>
                            <br>
                            <ul class="nav nav-tabs row">
                                <li class="active"><a href="${pageContext.servletContext.contextPath}/laSolicitante?volver=${volver}">La Solicitante</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/elSolicitante?volver=${volver}" >El solicitante</a></li>
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
                                        <input value="${Ella.getNombre()}" id="nombre" name="nombre" onkeyup="return(limitar());" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Paterno</label>
                                    <div class="controls">
                                        <input value="${Ella.getApellidoP()}" id="apellidoP" name="apellidoP" onkeyup="return(limitar());" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Materno</label>
                                    <div class="controls">
                                        <input value="${Ella.getApellidoM()}" id="apellidoM" name="apellidoM" onkeyup="return(limitar());" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de nacimiento</label>
                                    <div class="controls">
                                        <input value="${Ella.getFechaNac() != null ? df.dateToStringNumeros(Ella.getFechaNac()) : ''}" id="fechaNac" name="fechaNac" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Edad</label>  
                                    <div class="controls">
                                        <input disabled id="edad" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Lugar de nacimiento</label>
                                    <div class="controls">
                                        <input value="${Ella.getLugarNac()}" id="lugarNac" name="lugarNac" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Departamento de nacimiento</label>
                                    <div class="controls">
                                        <input value="${Ella.getDepaNac()}" id="depNac" name="depNac" onkeyup="return(limitar2());" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">País de Nacimiento<font style="color:red">(*)</font></label>
                                    <div class="controls">
                                        <select id="paisNac" name="paisNac">
                                            <option value="AF" ${Ella.getPaisNac() == "AF" ? 'selected' : ''} >Afganistán</option>
                                            <option value="AL" ${Ella.getPaisNac() == "AL" ? 'selected' : ''} >Albania</option>
                                            <option value="DE" ${Ella.getPaisNac() == "DE" ? 'selected' : ''} >Alemania</option>
                                            <option value="AD" ${Ella.getPaisNac() == "AD" ? 'selected' : ''} >Andorra</option>
                                            <option value="AO" ${Ella.getPaisNac() == "AO" ? 'selected' : ''} >Angola</option>
                                            <option value="AI" ${Ella.getPaisNac() == "AI" ? 'selected' : ''} >Anguilla</option>
                                            <option value="AQ" ${Ella.getPaisNac() == "AQ" ? 'selected' : ''} >Antártida</option>
                                            <option value="AG" ${Ella.getPaisNac() == "AG" ? 'selected' : ''} >Antigua y Barbuda</option>
                                            <option value="AN" ${Ella.getPaisNac() == "AN" ? 'selected' : ''} >Antillas Holandesas</option>
                                            <option value="SA" ${Ella.getPaisNac() == "SA" ? 'selected' : ''} >Arabia Saudí</option>
                                            <option value="DZ" ${Ella.getPaisNac() == "DZ" ? 'selected' : ''} >Argelia</option>
                                            <option value="AR" ${Ella.getPaisNac() == "AR" ? 'selected' : ''} >Argentina</option>
                                            <option value="AM" ${Ella.getPaisNac() == "AM" ? 'selected' : ''} >Armenia</option>
                                            <option value="AW" ${Ella.getPaisNac() == "AW" ? 'selected' : ''} >Aruba</option>
                                            <option value="AU" ${Ella.getPaisNac() == "AU" ? 'selected' : ''} >Australia</option>
                                            <option value="AT" ${Ella.getPaisNac() == "AT" ? 'selected' : ''} >Austria</option>
                                            <option value="AZ" ${Ella.getPaisNac() == "AZ" ? 'selected' : ''} >Azerbaiyán</option>
                                            <option value="BS" ${Ella.getPaisNac() == "BS" ? 'selected' : ''} >Bahamas</option>
                                            <option value="BH" ${Ella.getPaisNac() == "BH" ? 'selected' : ''} >Bahrein</option>
                                            <option value="BD" ${Ella.getPaisNac() == "BD" ? 'selected' : ''} >Bangladesh</option>
                                            <option value="BB" ${Ella.getPaisNac() == "BB" ? 'selected' : ''} >Barbados</option>
                                            <option value="BE" ${Ella.getPaisNac() == "BE" ? 'selected' : ''} >Bélgica</option>
                                            <option value="BZ" ${Ella.getPaisNac() == "BZ" ? 'selected' : ''} >Belice</option>
                                            <option value="BJ" ${Ella.getPaisNac() == "BJ" ? 'selected' : ''} >Benin</option>
                                            <option value="BM" ${Ella.getPaisNac() == "BM" ? 'selected' : ''} >Bermudas</option>
                                            <option value="BY" ${Ella.getPaisNac() == "BY" ? 'selected' : ''} >Bielorrusia</option>
                                            <option value="MM" ${Ella.getPaisNac() == "MM" ? 'selected' : ''} >Birmania</option>
                                            <option value="BO" ${Ella.getPaisNac() == "BO" ? 'selected' : ''} >Bolivia</option>
                                            <option value="BA" ${Ella.getPaisNac() == "BA" ? 'selected' : ''} >Bosnia y Herzegovina</option>
                                            <option value="BW" ${Ella.getPaisNac() == "BW" ? 'selected' : ''} >Botswana</option>
                                            <option value="BR" ${Ella.getPaisNac() == "BR" ? 'selected' : ''} >Brasil</option>
                                            <option value="BN" ${Ella.getPaisNac() == "BN" ? 'selected' : ''} >Brunei</option>
                                            <option value="BG" ${Ella.getPaisNac() == "BG" ? 'selected' : ''} >Bulgaria</option>
                                            <option value="BF" ${Ella.getPaisNac() == "BF" ? 'selected' : ''} >Burkina Faso</option>
                                            <option value="BI" ${Ella.getPaisNac() == "BI" ? 'selected' : ''} >Burundi</option>
                                            <option value="BT" ${Ella.getPaisNac() == "BT" ? 'selected' : ''} >Bután</option>
                                            <option value="CV" ${Ella.getPaisNac() == "CV" ? 'selected' : ''} >Cabo Verde</option>
                                            <option value="KH" ${Ella.getPaisNac() == "KH" ? 'selected' : ''} >Camboya</option>
                                            <option value="CM" ${Ella.getPaisNac() == "CM" ? 'selected' : ''} >Camerún</option>
                                            <option value="CA" ${Ella.getPaisNac() == "CA" ? 'selected' : ''} >Canadá</option>
                                            <option value="TD" ${Ella.getPaisNac() == "TD" ? 'selected' : ''} >Chad</option>
                                            <option value="CL" ${Ella.getPaisNac() == "CL" ? 'selected' : ''} >Chile</option>
                                            <option value="CN" ${Ella.getPaisNac() == "CN" ? 'selected' : ''} >China</option>
                                            <option value="CY" ${Ella.getPaisNac() == "CY" ? 'selected' : ''} >Chipre</option>
                                            <option value="VA" ${Ella.getPaisNac() == "VA" ? 'selected' : ''} >Ciudad del Vaticano (Santa Sede)</option>
                                            <option value="CO" ${Ella.getPaisNac() == "CO" ? 'selected' : ''} >Colombia</option>
                                            <option value="KM" ${Ella.getPaisNac() == "KM" ? 'selected' : ''} >Comores</option>
                                            <option value="CG" ${Ella.getPaisNac() == "CG" ? 'selected' : ''} >Congo</option>
                                            <option value="CD" ${Ella.getPaisNac() == "CD" ? 'selected' : ''} >Congo, República Democrática del</option>
                                            <option value="KR" ${Ella.getPaisNac() == "KR" ? 'selected' : ''} >Corea</option>
                                            <option value="KP" ${Ella.getPaisNac() == "KP" ? 'selected' : ''} >Corea del Norte</option>
                                            <option value="CI" ${Ella.getPaisNac() == "CI" ? 'selected' : ''} >Costa de Marfíl</option>
                                            <option value="CR" ${Ella.getPaisNac() == "CR" ? 'selected' : ''} >Costa Rica</option>
                                            <option value="HR" ${Ella.getPaisNac() == "HR" ? 'selected' : ''} >Croacia (Hrvatska)</option>
                                            <option value="CU" ${Ella.getPaisNac() == "CU" ? 'selected' : ''} >Cuba</option>
                                            <option value="DK" ${Ella.getPaisNac() == "DK" ? 'selected' : ''} >Dinamarca</option>
                                            <option value="DJ" ${Ella.getPaisNac() == "DJ" ? 'selected' : ''} >Djibouti</option>
                                            <option value="DM" ${Ella.getPaisNac() == "DM" ? 'selected' : ''} >Dominica</option>
                                            <option value="EC" ${Ella.getPaisNac() == "EC" ? 'selected' : ''} >Ecuador</option>
                                            <option value="EG" ${Ella.getPaisNac() == "EG" ? 'selected' : ''} >Egipto</option>
                                            <option value="SV" ${Ella.getPaisNac() == "SV" ? 'selected' : ''} >El Salvador</option>
                                            <option value="AE" ${Ella.getPaisNac() == "AE" ? 'selected' : ''} >Emiratos Árabes Unidos</option>
                                            <option value="ER" ${Ella.getPaisNac() == "ER" ? 'selected' : ''} >Eritrea</option>
                                            <option value="SI" ${Ella.getPaisNac() == "SI" ? 'selected' : ''} >Eslovenia</option>
                                            <option value="ES" ${Ella.getPaisNac() == "ES" ? 'selected' : ''} >España</option>
                                            <option value="US" ${Ella.getPaisNac() == "US" ? 'selected' : ''} >Estados Unidos</option>
                                            <option value="EE" ${Ella.getPaisNac() == "EE" ? 'selected' : ''} >Estonia</option>
                                            <option value="ET" ${Ella.getPaisNac() == "ET" ? 'selected' : ''} >Etiopía</option>
                                            <option value="FJ" ${Ella.getPaisNac() == "FJ" ? 'selected' : ''} >Fiji</option>
                                            <option value="PH" ${Ella.getPaisNac() == "PH" ? 'selected' : ''} >Filipinas</option>
                                            <option value="FI" ${Ella.getPaisNac() == "FI" ? 'selected' : ''} >Finlandia</option>
                                            <option value="FR" ${Ella.getPaisNac() == "FR" ? 'selected' : ''} >Francia</option>
                                            <option value="GA" ${Ella.getPaisNac() == "GA" ? 'selected' : ''} >Gabón</option>
                                            <option value="GM" ${Ella.getPaisNac() == "GM" ? 'selected' : ''} >Gambia</option>
                                            <option value="GE" ${Ella.getPaisNac() == "GE" ? 'selected' : ''} >Georgia</option>
                                            <option value="GH" ${Ella.getPaisNac() == "GH" ? 'selected' : ''} >Ghana</option>
                                            <option value="GI" ${Ella.getPaisNac() == "GI" ? 'selected' : ''} >Gibraltar</option>
                                            <option value="GD" ${Ella.getPaisNac() == "GD" ? 'selected' : ''} >Granada</option>
                                            <option value="GR" ${Ella.getPaisNac() == "GR" ? 'selected' : ''} >Grecia</option>
                                            <option value="GL" ${Ella.getPaisNac() == "GL" ? 'selected' : ''} >Groenlandia</option>
                                            <option value="GP" ${Ella.getPaisNac() == "GP" ? 'selected' : ''} >Guadalupe</option>
                                            <option value="GU" ${Ella.getPaisNac() == "GU" ? 'selected' : ''} >Guam</option>
                                            <option value="GT" ${Ella.getPaisNac() == "GT" ? 'selected' : ''} >Guatemala</option>
                                            <option value="GY" ${Ella.getPaisNac() == "GY" ? 'selected' : ''} >Guayana</option>
                                            <option value="GF" ${Ella.getPaisNac() == "GF" ? 'selected' : ''} >Guayana Francesa</option>
                                            <option value="GN" ${Ella.getPaisNac() == "GN" ? 'selected' : ''} >Guinea</option>
                                            <option value="GQ" ${Ella.getPaisNac() == "GQ" ? 'selected' : ''} >Guinea Ecuatorial</option>
                                            <option value="GW" ${Ella.getPaisNac() == "GW" ? 'selected' : ''} >Guinea-Bissau</option>
                                            <option value="HT" ${Ella.getPaisNac() == "HT" ? 'selected' : ''} >Haití</option>
                                            <option value="HN" ${Ella.getPaisNac() == "HN" ? 'selected' : ''} >Honduras</option>
                                            <option value="HU" ${Ella.getPaisNac() == "HU" ? 'selected' : ''} >Hungría</option>
                                            <option value="IN" ${Ella.getPaisNac() == "IN" ? 'selected' : ''} >India</option>
                                            <option value="ID" ${Ella.getPaisNac() == "ID" ? 'selected' : ''} >Indonesia</option>
                                            <option value="IQ" ${Ella.getPaisNac() == "IQ" ? 'selected' : ''} >Irak</option>
                                            <option value="IR" ${Ella.getPaisNac() == "IR" ? 'selected' : ''} >Irán</option>
                                            <option value="IE" ${Ella.getPaisNac() == "IE" ? 'selected' : ''} >Irlanda</option>
                                            <option value="BV" ${Ella.getPaisNac() == "BV" ? 'selected' : ''} >Isla Bouvet</option>
                                            <option value="CX" ${Ella.getPaisNac() == "CX" ? 'selected' : ''} >Isla de Christmas</option>
                                            <option value="IS" ${Ella.getPaisNac() == "IS" ? 'selected' : ''} >Islandia</option>
                                            <option value="KY" ${Ella.getPaisNac() == "KY" ? 'selected' : ''} >Islas Caimán</option>
                                            <option value="CK" ${Ella.getPaisNac() == "CK" ? 'selected' : ''} >Islas Cook</option>
                                            <option value="CC" ${Ella.getPaisNac() == "CC" ? 'selected' : ''} >Islas de Cocos o Keeling</option>
                                            <option value="FO" ${Ella.getPaisNac() == "FO" ? 'selected' : ''} >Islas Faroe</option>
                                            <option value="HM" ${Ella.getPaisNac() == "HM" ? 'selected' : ''} >Islas Heard y McDonald</option>
                                            <option value="FK" ${Ella.getPaisNac() == "FK" ? 'selected' : ''} >Islas Malvinas</option>
                                            <option value="MP" ${Ella.getPaisNac() == "MP" ? 'selected' : ''} >Islas Marianas del Norte</option>
                                            <option value="MH" ${Ella.getPaisNac() == "MH" ? 'selected' : ''} >Islas Marshall</option>
                                            <option value="UM" ${Ella.getPaisNac() == "UM" ? 'selected' : ''} >Islas menores de Estados Unidos</option>
                                            <option value="PW" ${Ella.getPaisNac() == "PW" ? 'selected' : ''} >Islas Palau</option>
                                            <option value="SB" ${Ella.getPaisNac() == "SB" ? 'selected' : ''} >Islas Salomón</option>
                                            <option value="SJ" ${Ella.getPaisNac() == "SJ" ? 'selected' : ''} >Islas Svalbard y Jan Mayen</option>
                                            <option value="TK" ${Ella.getPaisNac() == "TK" ? 'selected' : ''} >Islas Tokelau</option>
                                            <option value="TC" ${Ella.getPaisNac() == "TC" ? 'selected' : ''} >Islas Turks y Caicos</option>
                                            <option value="VI" ${Ella.getPaisNac() == "VI" ? 'selected' : ''} >Islas Vírgenes (EEUU)</option>
                                            <option value="VG" ${Ella.getPaisNac() == "VG" ? 'selected' : ''} >Islas Vírgenes (Reino Unido)</option>
                                            <option value="WF" ${Ella.getPaisNac() == "WF" ? 'selected' : ''} >Islas Wallis y Futuna</option>
                                            <option value="IL" ${Ella.getPaisNac() == "IL" ? 'selected' : ''} >Israel</option>
                                            <option value="IT" ${Ella.getPaisNac() == "IT" ? 'selected' : ''} >Italia</option>
                                            <option value="JM" ${Ella.getPaisNac() == "JM" ? 'selected' : ''} >Jamaica</option>
                                            <option value="JP" ${Ella.getPaisNac() == "JP" ? 'selected' : ''} >Japón</option>
                                            <option value="JO" ${Ella.getPaisNac() == "JO" ? 'selected' : ''} >Jordania</option>
                                            <option value="KZ" ${Ella.getPaisNac() == "KZ" ? 'selected' : ''} >Kazajistán</option>
                                            <option value="KE" ${Ella.getPaisNac() == "KE" ? 'selected' : ''} >Kenia</option>
                                            <option value="KG" ${Ella.getPaisNac() == "KG" ? 'selected' : ''} >Kirguizistán</option>
                                            <option value="KI" ${Ella.getPaisNac() == "KI" ? 'selected' : ''} >Kiribati</option>
                                            <option value="KW" ${Ella.getPaisNac() == "KW" ? 'selected' : ''} >Kuwait</option>
                                            <option value="LA" ${Ella.getPaisNac() == "LA" ? 'selected' : ''} >Laos</option>
                                            <option value="LS" ${Ella.getPaisNac() == "LS" ? 'selected' : ''} >Lesotho</option>
                                            <option value="LV" ${Ella.getPaisNac() == "LV" ? 'selected' : ''} >Letonia</option>
                                            <option value="LB" ${Ella.getPaisNac() == "LB" ? 'selected' : ''} >Líbano</option>
                                            <option value="LR" ${Ella.getPaisNac() == "LR" ? 'selected' : ''} >Liberia</option>
                                            <option value="LY" ${Ella.getPaisNac() == "LY" ? 'selected' : ''} >Libia</option>
                                            <option value="LI" ${Ella.getPaisNac() == "LI" ? 'selected' : ''} >Liechtenstein</option>
                                            <option value="LT" ${Ella.getPaisNac() == "LT" ? 'selected' : ''} >Lituania</option>
                                            <option value="LU" ${Ella.getPaisNac() == "LU" ? 'selected' : ''} >Luxemburgo</option>
                                            <option value="MK" ${Ella.getPaisNac() == "MK" ? 'selected' : ''} >Macedonia, Ex-República Yugoslava de</option>
                                            <option value="MG" ${Ella.getPaisNac() == "MG" ? 'selected' : ''} >Madagascar</option>
                                            <option value="MY" ${Ella.getPaisNac() == "MY" ? 'selected' : ''} >Malasia</option>
                                            <option value="MW" ${Ella.getPaisNac() == "MW" ? 'selected' : ''} >Malawi</option>
                                            <option value="MV" ${Ella.getPaisNac() == "MV" ? 'selected' : ''} >Maldivas</option>
                                            <option value="ML" ${Ella.getPaisNac() == "ML" ? 'selected' : ''} >Malí</option>
                                            <option value="MT" ${Ella.getPaisNac() == "MT" ? 'selected' : ''} >Malta</option>
                                            <option value="MA" ${Ella.getPaisNac() == "MA" ? 'selected' : ''} >Marruecos</option>
                                            <option value="MQ" ${Ella.getPaisNac() == "MQ" ? 'selected' : ''} >Martinica</option>
                                            <option value="MU" ${Ella.getPaisNac() == "MU" ? 'selected' : ''} >Mauricio</option>
                                            <option value="MR" ${Ella.getPaisNac() == "MR" ? 'selected' : ''} >Mauritania</option>
                                            <option value="YT" ${Ella.getPaisNac() == "YT" ? 'selected' : ''} >Mayotte</option>
                                            <option value="MX" ${Ella.getPaisNac() == "MX" ? 'selected' : ''} >México</option>
                                            <option value="FM" ${Ella.getPaisNac() == "FM" ? 'selected' : ''} >Micronesia</option>
                                            <option value="MD" ${Ella.getPaisNac() == "MD" ? 'selected' : ''} >Moldavia</option>
                                            <option value="MC" ${Ella.getPaisNac() == "MC" ? 'selected' : ''} >Mónaco</option>
                                            <option value="MN" ${Ella.getPaisNac() == "MN" ? 'selected' : ''} >Mongolia</option>
                                            <option value="MS" ${Ella.getPaisNac() == "MS" ? 'selected' : ''} >Montserrat</option>
                                            <option value="MZ" ${Ella.getPaisNac() == "MZ" ? 'selected' : ''} >Mozambique</option>
                                            <option value="NA" ${Ella.getPaisNac() == "NA" ? 'selected' : ''} >Namibia</option>
                                            <option value="NR" ${Ella.getPaisNac() == "NR" ? 'selected' : ''} >Nauru</option>
                                            <option value="NP" ${Ella.getPaisNac() == "NP" ? 'selected' : ''} >Nepal</option>
                                            <option value="NI" ${Ella.getPaisNac() == "NI" ? 'selected' : ''} >Nicaragua</option>
                                            <option value="NE" ${Ella.getPaisNac() == "NE" ? 'selected' : ''} >Níger</option>
                                            <option value="NG" ${Ella.getPaisNac() == "NG" ? 'selected' : ''} >Nigeria</option>
                                            <option value="NU" ${Ella.getPaisNac() == "NU" ? 'selected' : ''} >Niue</option>
                                            <option value="NF" ${Ella.getPaisNac() == "NF" ? 'selected' : ''} >Norfolk</option>
                                            <option value="NO" ${Ella.getPaisNac() == "NO" ? 'selected' : ''} >Noruega</option>
                                            <option value="NC" ${Ella.getPaisNac() == "NC" ? 'selected' : ''} >Nueva Caledonia</option>
                                            <option value="NZ" ${Ella.getPaisNac() == "NZ" ? 'selected' : ''} >Nueva Zelanda</option>
                                            <option value="OM" ${Ella.getPaisNac() == "OM" ? 'selected' : ''} >Omán</option>
                                            <option value="NL" ${Ella.getPaisNac() == "NL" ? 'selected' : ''} >Países Bajos</option>
                                            <option value="PA" ${Ella.getPaisNac() == "PA" ? 'selected' : ''} >Panamá</option>
                                            <option value="PG" ${Ella.getPaisNac() == "PG" ? 'selected' : ''} >Papúa Nueva Guinea</option>
                                            <option value="PK" ${Ella.getPaisNac() == "PK" ? 'selected' : ''} >Paquistán</option>
                                            <option value="PY" ${Ella.getPaisNac() == "PY" ? 'selected' : ''} >Paraguay</option>
                                            <option value="Peru" ${Ella.getPaisNac() == "Peru" ? 'selected' : ''} >Perú</option>
                                            <option value="PN" ${Ella.getPaisNac() == "PN" ? 'selected' : ''} >Pitcairn</option>
                                            <option value="PF" ${Ella.getPaisNac() == "PF" ? 'selected' : ''} >Polinesia Francesa</option>
                                            <option value="PL" ${Ella.getPaisNac() == "PL" ? 'selected' : ''} >Polonia</option>
                                            <option value="PT" ${Ella.getPaisNac() == "PT" ? 'selected' : ''} >Portugal</option>
                                            <option value="PR" ${Ella.getPaisNac() == "PR" ? 'selected' : ''} >Puerto Rico</option>
                                            <option value="QA" ${Ella.getPaisNac() == "QA" ? 'selected' : ''} >Qatar</option>
                                            <option value="UK" ${Ella.getPaisNac() == "UK" ? 'selected' : ''} >Reino Unido</option>
                                            <option value="CF" ${Ella.getPaisNac() == "CF" ? 'selected' : ''} >República Centroafricana</option>
                                            <option value="CZ" ${Ella.getPaisNac() == "CZ" ? 'selected' : ''} >República Checa</option>
                                            <option value="ZA" ${Ella.getPaisNac() == "ZA" ? 'selected' : ''} >República de Sudáfrica</option>
                                            <option value="DO" ${Ella.getPaisNac() == "DO" ? 'selected' : ''} >República Dominicana</option>
                                            <option value="SK" ${Ella.getPaisNac() == "SK" ? 'selected' : ''} >República Eslovaca</option>
                                            <option value="RE" ${Ella.getPaisNac() == "RE" ? 'selected' : ''} >Reunión</option>
                                            <option value="RW" ${Ella.getPaisNac() == "RW" ? 'selected' : ''} >Ruanda</option>
                                            <option value="RO" ${Ella.getPaisNac() == "RO" ? 'selected' : ''} >Rumania</option>
                                            <option value="RU" ${Ella.getPaisNac() == "RU" ? 'selected' : ''} >Rusia</option>
                                            <option value="EH" ${Ella.getPaisNac() == "EH" ? 'selected' : ''} >Sahara Occidental</option>
                                            <option value="KN" ${Ella.getPaisNac() == "KN" ? 'selected' : ''} >Saint Kitts y Nevis</option>
                                            <option value="WS" ${Ella.getPaisNac() == "WS" ? 'selected' : ''} >Samoa</option>
                                            <option value="AS" ${Ella.getPaisNac() == "AS" ? 'selected' : ''} >Samoa Americana</option>
                                            <option value="SM" ${Ella.getPaisNac() == "SM" ? 'selected' : ''} >San Marino</option>
                                            <option value="VC" ${Ella.getPaisNac() == "VC" ? 'selected' : ''} >San Vicente y Granadinas</option>
                                            <option value="SH" ${Ella.getPaisNac() == "SH" ? 'selected' : ''} >Santa Helena</option>
                                            <option value="LC" ${Ella.getPaisNac() == "LC" ? 'selected' : ''} >Santa Lucía</option>
                                            <option value="ST" ${Ella.getPaisNac() == "ST" ? 'selected' : ''} >Santo Tomé y Príncipe</option>
                                            <option value="SN" ${Ella.getPaisNac() == "SN" ? 'selected' : ''} >Senegal</option>
                                            <option value="SC" ${Ella.getPaisNac() == "SC" ? 'selected' : ''} >Seychelles</option>
                                            <option value="SL" ${Ella.getPaisNac() == "SL" ? 'selected' : ''} >Sierra Leona</option>
                                            <option value="SG" ${Ella.getPaisNac() == "SG" ? 'selected' : ''} >Singapur</option>
                                            <option value="SY" ${Ella.getPaisNac() == "SY" ? 'selected' : ''} >Siria</option>
                                            <option value="SO" ${Ella.getPaisNac() == "SO" ? 'selected' : ''} >Somalia</option>
                                            <option value="LK" ${Ella.getPaisNac() == "LK" ? 'selected' : ''} >Sri Lanka</option>
                                            <option value="PM" ${Ella.getPaisNac() == "PM" ? 'selected' : ''} >St Pierre y Miquelon</option>
                                            <option value="SZ" ${Ella.getPaisNac() == "SZ" ? 'selected' : ''} >Suazilandia</option>
                                            <option value="SD" ${Ella.getPaisNac() == "SD" ? 'selected' : ''} >Sudán</option>
                                            <option value="SE" ${Ella.getPaisNac() == "SE" ? 'selected' : ''} >Suecia</option>
                                            <option value="CH" ${Ella.getPaisNac() == "CH" ? 'selected' : ''} >Suiza</option>
                                            <option value="SR" ${Ella.getPaisNac() == "SR" ? 'selected' : ''} >Surinam</option>
                                            <option value="TH" ${Ella.getPaisNac() == "TH" ? 'selected' : ''} >Tailandia</option>
                                            <option value="TW" ${Ella.getPaisNac() == "TW" ? 'selected' : ''} >Taiwán</option>
                                            <option value="TZ" ${Ella.getPaisNac() == "TZ" ? 'selected' : ''} >Tanzania</option>
                                            <option value="TJ" ${Ella.getPaisNac() == "TJ" ? 'selected' : ''} >Tayikistán</option>
                                            <option value="TF" ${Ella.getPaisNac() == "TF" ? 'selected' : ''} >Territorios franceses del Sur</option>
                                            <option value="TP" ${Ella.getPaisNac() == "TP" ? 'selected' : ''} >Timor Oriental</option>
                                            <option value="TG" ${Ella.getPaisNac() == "TG" ? 'selected' : ''} >Togo</option>
                                            <option value="TO" ${Ella.getPaisNac() == "TO" ? 'selected' : ''} >Tonga</option>
                                            <option value="TT" ${Ella.getPaisNac() == "TT" ? 'selected' : ''} >Trinidad y Tobago</option>
                                            <option value="TN" ${Ella.getPaisNac() == "TN" ? 'selected' : ''} >Túnez</option>
                                            <option value="TM" ${Ella.getPaisNac() == "TM" ? 'selected' : ''} >Turkmenistán</option>
                                            <option value="TR" ${Ella.getPaisNac() == "TR" ? 'selected' : ''} >Turquía</option>
                                            <option value="TV" ${Ella.getPaisNac() == "TV" ? 'selected' : ''} >Tuvalu</option>
                                            <option value="UA" ${Ella.getPaisNac() == "UA" ? 'selected' : ''} >Ucrania</option>
                                            <option value="UG" ${Ella.getPaisNac() == "UG" ? 'selected' : ''} >Uganda</option>
                                            <option value="UY" ${Ella.getPaisNac() == "UY" ? 'selected' : ''} >Uruguay</option>
                                            <option value="UZ" ${Ella.getPaisNac() == "UZ" ? 'selected' : ''} >Uzbekistán</option>
                                            <option value="VU" ${Ella.getPaisNac() == "VU" ? 'selected' : ''} >Vanuatu</option>
                                            <option value="VE" ${Ella.getPaisNac() == "VE" ? 'selected' : ''} >Venezuela</option>
                                            <option value="VN" ${Ella.getPaisNac() == "VN" ? 'selected' : ''} >Vietnam</option>
                                            <option value="YE" ${Ella.getPaisNac() == "YE" ? 'selected' : ''} >Yemen</option>
                                            <option value="YU" ${Ella.getPaisNac() == "YU" ? 'selected' : ''} >Yugoslavia</option>
                                            <option value="ZM" ${Ella.getPaisNac() == "ZM" ? 'selected' : ''} >Zambia</option>
                                            <option value="ZW" ${Ella.getPaisNac() == "ZW" ? 'selected' : ''} >Zimbabue</option>
                                        </select>
                                    </div>    
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-2">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="doc" id="optionsRadios1" value="d" ${Ella.getTipoDoc() == 100 ? 'checked' : ''}>DNI</label> 
                                        </div>
                                    </div>   
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="doc" id="optionsRadios2" value="c" ${Ella.getTipoDoc() == 99 ? 'checked' : ''}>Carnet de Extranjería</label>
                                        </div>                            
                                    </div>
                                </div>    
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <label class="control-label">N° de Documento</label>
                                        <input value="${Ella.getNDoc()}" id="numDoc" name="numDoc" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <c:if test="${expediente.getNacionalidad() == 'internacional'}">
                                    <div class="control-group">
                                        <div class="controls">
                                            <label class="control-label">Pasaporte</label>
                                            <input value="${Ella.getPasaporte()}" id="pasaporte" name="pasaporte" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                </c:if>
                                <div class="control-group">
                                    <label class="control-label">Celular</label>
                                    <div class="controls">
                                        <input value="${Ella.getCelular()}" id="numCel" name="numCel" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Correo Electrónico</label>
                                    <div class="controls">
                                        <input value="${Ella.getCorreo()}" id="correo" name="correo" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Estado Civil</h3>
                                <div class="row">

                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="soltera" value="soltera" ${infoFam.getEstadoCivil() == 'soltera' || infoFam.getEstadoCivil() == 'soltero' ? 'checked' : ''}>Soltera</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="casada" value="casados" ${infoFam.getEstadoCivil() == 'casados' ? 'checked' : ''}>Casada</label>
                                            <br>
                                            <label class="control-label">Fecha de matrimonio Civil</label>
                                            <input ${infoFam.getEstadoCivil() == 'casados' ? '' : 'disabled'} value="${infoFam.getFechaMatrimonio() != null ? df.dateToStringNumeros(infoFam.getFechaMatrimonio()) : ''}" id="fechaMat" name="fechaMat" type="text" class="datepicker input-xlarge">
                                        </div>                            
                                    </div>
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="viuda" value="viuda" ${infoFam.getEstadoCivil() == 'viuda' || infoFam.getEstadoCivil() == 'viudo' ? 'checked' : ''}>Viuda</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="divorciada" value="divorciada" ${infoFam.getEstadoCivil() == 'divorciada' || infoFam.getEstadoCivil() == 'divorciado' ? 'checked' : ''}>Divorciada</label>
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
                                                <input type="radio" name="nivelInstruccion" id="ninguno" value="Ninguno" ${Ella.getNivelInstruccion() == 'Ninguno' ? 'checked' : ''}>Ninguno</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="nivelInstruccion" id="primaria" value="Primaria" ${Ella.getNivelInstruccion() == 'Primaria' ? 'checked' : ''}>Primaria</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="nivelInstruccion" id="secundaria" value="Secundaria" ${Ella.getNivelInstruccion() == 'Secundaria' ? 'checked' : ''}>Secundaria</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="nivelInstruccion" id="tecnico" value="Tecnico" ${Ella.getNivelInstruccion() == 'Tecnico' ? 'checked' : ''}>Técnico</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="nivelInstruccion" id="superior" value="Superior" ${Ella.getNivelInstruccion() == 'Superior' ? 'checked' : ''}>Superior</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="nivelInstruccion" id="otros" value="Otros" ${Ella.getNivelInstruccion() == 'Otros' ? 'checked' : ''}>Otros</label>
                                        </div>
                                    </div>   
                                </div> 
                                <br>
                                <h3>Culminó el nivel de instrucción señalado</h3>
                                <div class="row">

                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="culminoNivel" id="si" value="0" ${Ella.getCulminoNivel() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="culminoNivel" id="no" value="1" ${Ella.getCulminoNivel() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>  
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Profesión u Oficio </label>
                                    <div class="controls">
                                        <input value="${Ella.getProfesion()}" id="profesion" name="profesion" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Trabajadora dependiente (Elegir solo si corresponde)</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="checkbox">
                                            <label>
                                                <input onclick="Dep()" type="checkbox" name="trabDep" id="trabDep" value="0" ${Ella.getTrabajadorDepend() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>  
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ocupación Actual</label>
                                    <div class="controls">
                                        <input value="${Ella.getOcupActualDep()}" id="ocupacionDep" name="ocupacionDep" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Centro de Trabajo </label>
                                    <div class="controls">
                                        <input value="${Ella.getCentroTrabajo()}" id="centroTrabajo" name="centroTrabajo" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Dirección del centro de trabajo</label>
                                    <div class="controls">
                                        <input value="${Ella.getDireccionCentro()}" id="direccionTrabajo" name="direccionTrabajo" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Teléfono del centro de trabajo </label>
                                    <div class="controls">
                                        <input value="${Ella.getTelefonoCentro()}" id="telefonoTrabajo" name="telefonoTrabajo" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ingreso mensual sustentable (sueldo bruto) </label>
                                    <div class="controls">
                                        <input value="${Ella.getIngresoDep()}" id="ingresoDep" name="ingresoDep" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Trabajadora independiente (Elegir solo si corresponde)</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="checkbox">
                                            <label>
                                                <input onclick="Indep()" type="checkbox" name="trabIndep" id="trabIndep" value="0" ${Ella.getTrabajadorIndepend() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>  
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ocupación Actual</label>
                                    <div class="controls">
                                        <input value="${Ella.getOcupActualInd()}" id="ocupacionInd" name="ocupacionInd" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ingreso mensual sustentable</label>
                                    <div class="controls">
                                        <input value="${Ella.getIngresoIndep()}" id="ingresoInd" name="ingresoInd" type="text" class="input-xlarge">
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
                                                <input type="radio" name="seguroSalud" id="checkbox1" value="0" ${Ella.getSeguroSalud() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>  
                                    <div class="col-md-2">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="seguroSalud" id="seguro_no" value="1" ${Ella.getSeguroSalud() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Tipo de Seguro</label>
                                    <div class="controls">
                                        <input value="${Ella.getTipoSeguro()}" id="tipoSeguro" name="tipoSeguro" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Seguro de vida</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="seguroVida" id="vida_si" value="0" ${Ella.getSeguroVida() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>  
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="seguroVida" id="vida_no" value="1" ${Ella.getSeguroVida() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>
                                </div> 
                                <br>
                                <h3>Afiliada al sistema de pensiones</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="sisPensiones" id="afiliado_si" value="0" ${Ella.getSistPensiones() == 0 ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>  
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="sisPensiones" id="afiliado_no" value="1" ${Ella.getSistPensiones() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Estado de salud actual</label>
                                    <div class="controls">
                                        <input value="${Ella.getSaludActual()}" id="estadoActual" name="estadoActual" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                -->
                                <br>
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <button ${Ella.getIdadoptante() == null || Ella.getIdadoptante() == 0 || estado == 'formativa' ? 'disabled' : '' } type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
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
                    /*
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
                     */
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