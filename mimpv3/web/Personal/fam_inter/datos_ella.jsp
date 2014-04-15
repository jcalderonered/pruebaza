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
                    <div class="col-md-8">
                        <form role="form" action="${pageContext.servletContext.contextPath}/DetallesFamInt?idExpediente=${idExpediente}" method="post">   
                            <p align="right"><button  id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        </form>                                                
                        <br>
                        <h1 align="center"><strong>Datos de la familia internacional </strong></h1>                         
                        <br>
                        <ul class="nav nav-tabs row"  >
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/laSolicitanteInt?idExpediente=${idExpediente}" >La Solicitante</a></li>
                            <li ><a href="${pageContext.servletContext.contextPath}/elSolicitanteInt?idExpediente=${idExpediente}" >El solicitante</a></li>
                            <!-- <li><a href="#" data-toggle="tab">Composición familiar</a></li> -->
                            <!-- <li><a href="#" data-toggle="tab">Vivienda</a></li> -->
                            <!--<li><a href="#" data-toggle="tab">Proceso de adopción</a></li> -->
                            <li><a href="${pageContext.servletContext.contextPath}/antNnaInt?idExpediente=${idExpediente}" >Antecedentes del niño, niña o adolescente</a></li>
                        </ul>

                        <form role="form" action="${pageContext.servletContext.contextPath}/ActualizarAdoptanteInt" method="post" name="formulario" onsubmit="return(validar());"> 
                            <br>
                            <input hidden name="idExpediente" id="idExpediente" value="${idExpediente}">
                            <input hidden id="adoptante" name="adoptante" value="ella">
                            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                            <fieldset>
                                <br>
                                <h3><strong>Generales</strong></h3> 
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label">Nombre</label>
                                    <div class="controls">
                                        <input value="${Ella.getNombre()}" id="nombre" name="nombre" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Paterno</label>
                                    <div class="controls">
                                        <input value="${Ella.getApellidoP()}" id="apellidoP" name="apellidoP" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Materno</label>
                                    <div class="controls">
                                        <input value="${Ella.getApellidoM()}" id="apellidoM" name="apellidoM" type="text" class="input-xlarge">
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
                                    <label class="control-label">Dirección de nacimiento</label>
                                    <div class="controls">
                                        <input value="${Ella.getLugarNac()}" id="lugarNac" name="lugarNac" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <!--<label class="control-label">Departamento de nacimiento</label>-->
                                    <div class="controls">
                                        <input hidden value="" id="depNac" name="depNac" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">País de Nacimiento<font style="color:red">(*)</font></label>
                                    <div class="controls">
                                        <select id="paisNac" name="paisNac">
                                            <option value="AF" <c:when test="${Ella.getPaisNac() == 'AF'}">selected</c:when>>Afganistán</option>
                                            <option value="AL" <c:when test="${Ella.getPaisNac() == 'AL'}">selected</c:when>>Albania</option>
                                            <option value="DE" <c:when test="${Ella.getPaisNac() == 'DE'}">selected</c:when>>Alemania</option>
                                            <option value="AD" <c:when test="${Ella.getPaisNac() == 'AD'}">selected</c:when>>Andorra</option>
                                            <option value="AO" <c:when test="${Ella.getPaisNac() == 'AO'}">selected</c:when>>Angola</option>
                                            <option value="AI" <c:when test="${Ella.getPaisNac() == 'AI'}">selected</c:when>>Anguilla</option>
                                            <option value="AQ" <c:when test="${Ella.getPaisNac() == 'AQ'}">selected</c:when>>Antártida</option>
                                            <option value="AG" <c:when test="${Ella.getPaisNac() == 'AG'}">selected</c:when>>Antigua y Barbuda</option>
                                            <option value="AN" <c:when test="${Ella.getPaisNac() == 'AN'}">selected</c:when>>Antillas Holandesas</option>
                                            <option value="SA" <c:when test="${Ella.getPaisNac() == 'SA'}">selected</c:when>>Arabia Saudí</option>
                                            <option value="DZ" <c:when test="${Ella.getPaisNac() == 'DZ'}">selected</c:when>>Argelia</option>
                                            <option value="AR" <c:when test="${Ella.getPaisNac() == 'AR'}">selected</c:when>>Argentina</option>
                                            <option value="AM" <c:when test="${Ella.getPaisNac() == 'AM'}">selected</c:when>>Armenia</option>
                                            <option value="AW" <c:when test="${Ella.getPaisNac() == 'AW'}">selected</c:when>>Aruba</option>
                                            <option value="AU" <c:when test="${Ella.getPaisNac() == 'AU'}">selected</c:when>>Australia</option>
                                            <option value="AT" <c:when test="${Ella.getPaisNac() == 'AT'}">selected</c:when>>Austria</option>
                                            <option value="AZ" <c:when test="${Ella.getPaisNac() == 'AZ'}">selected</c:when>>Azerbaiyán</option>
                                            <option value="BS" <c:when test="${Ella.getPaisNac() == 'BS'}">selected</c:when>>Bahamas</option>
                                            <option value="BH" <c:when test="${Ella.getPaisNac() == 'BH'}">selected</c:when>>Bahrein</option>
                                            <option value="BD" <c:when test="${Ella.getPaisNac() == 'BD'}">selected</c:when>>Bangladesh</option>
                                            <option value="BB" <c:when test="${Ella.getPaisNac() == 'BB'}">selected</c:when>>Barbados</option>
                                            <option value="BE" <c:when test="${Ella.getPaisNac() == 'BE'}">selected</c:when>>Bélgica</option>
                                            <option value="BZ" <c:when test="${Ella.getPaisNac() == 'BZ'}">selected</c:when>>Belice</option>
                                            <option value="BJ" <c:when test="${Ella.getPaisNac() == 'BJ'}">selected</c:when>>Benin</option>
                                            <option value="BM" <c:when test="${Ella.getPaisNac() == 'BM'}">selected</c:when>>Bermudas</option>
                                            <option value="BY" <c:when test="${Ella.getPaisNac() == 'BY'}">selected</c:when>>Bielorrusia</option>
                                            <option value="MM" <c:when test="${Ella.getPaisNac() == 'MM'}">selected</c:when>>Birmania</option>
                                            <option value="BO" <c:when test="${Ella.getPaisNac() == 'BO'}">selected</c:when>>Bolivia</option>
                                            <option value="BA" <c:when test="${Ella.getPaisNac() == 'BA'}">selected</c:when>>Bosnia y Herzegovina</option>
                                            <option value="BW" <c:when test="${Ella.getPaisNac() == 'BW'}">selected</c:when>>Botswana</option>
                                            <option value="BR" <c:when test="${Ella.getPaisNac() == 'BR'}">selected</c:when>>Brasil</option>
                                            <option value="BN" <c:when test="${Ella.getPaisNac() == 'BN'}">selected</c:when>>Brunei</option>
                                            <option value="BG" <c:when test="${Ella.getPaisNac() == 'BG'}">selected</c:when>>Bulgaria</option>
                                            <option value="BF" <c:when test="${Ella.getPaisNac() == 'BF'}">selected</c:when>>Burkina Faso</option>
                                            <option value="BI" <c:when test="${Ella.getPaisNac() == 'BI'}">selected</c:when>>Burundi</option>
                                            <option value="BT" <c:when test="${Ella.getPaisNac() == 'BT'}">selected</c:when>>Bután</option>
                                            <option value="CV" <c:when test="${Ella.getPaisNac() == 'CV'}">selected</c:when>>Cabo Verde</option>
                                            <option value="KH" <c:when test="${Ella.getPaisNac() == 'KH'}">selected</c:when>>Camboya</option>
                                            <option value="CM" <c:when test="${Ella.getPaisNac() == 'CM'}">selected</c:when>>Camerún</option>
                                            <option value="CA" <c:when test="${Ella.getPaisNac() == 'CA'}">selected</c:when>>Canadá</option>
                                            <option value="TD" <c:when test="${Ella.getPaisNac() == 'TD'}">selected</c:when>>Chad</option>
                                            <option value="CL" <c:when test="${Ella.getPaisNac() == 'CL'}">selected</c:when>>Chile</option>
                                            <option value="CN" <c:when test="${Ella.getPaisNac() == 'CN'}">selected</c:when>>China</option>
                                            <option value="CY" <c:when test="${Ella.getPaisNac() == 'CY'}">selected</c:when>>Chipre</option>
                                            <option value="VA" <c:when test="${Ella.getPaisNac() == 'VA'}">selected</c:when>>Ciudad del Vaticano (Santa Sede)</option>
                                            <option value="CO" <c:when test="${Ella.getPaisNac() == 'CO'}">selected</c:when>>Colombia</option>
                                            <option value="KM" <c:when test="${Ella.getPaisNac() == 'KM'}">selected</c:when>>Comores</option>
                                            <option value="CG" <c:when test="${Ella.getPaisNac() == 'CG'}">selected</c:when>>Congo</option>
                                            <option value="CD" <c:when test="${Ella.getPaisNac() == 'CD'}">selected</c:when>>Congo, República Democrática del</option>
                                            <option value="KR" <c:when test="${Ella.getPaisNac() == 'KR'}">selected</c:when>>Corea</option>
                                            <option value="KP" <c:when test="${Ella.getPaisNac() == 'KP'}">selected</c:when>>Corea del Norte</option>
                                            <option value="CI" <c:when test="${Ella.getPaisNac() == 'CI'}">selected</c:when>>Costa de Marfíl</option>
                                            <option value="CR" <c:when test="${Ella.getPaisNac() == 'CR'}">selected</c:when>>Costa Rica</option>
                                            <option value="HR" <c:when test="${Ella.getPaisNac() == 'HR'}">selected</c:when>>Croacia (Hrvatska)</option>
                                            <option value="CU" <c:when test="${Ella.getPaisNac() == 'CU'}">selected</c:when>>Cuba</option>
                                            <option value="DK" <c:when test="${Ella.getPaisNac() == 'DK'}">selected</c:when>>Dinamarca</option>
                                            <option value="DJ" <c:when test="${Ella.getPaisNac() == 'DJ'}">selected</c:when>>Djibouti</option>
                                            <option value="DM" <c:when test="${Ella.getPaisNac() == 'DM'}">selected</c:when>>Dominica</option>
                                            <option value="EC" <c:when test="${Ella.getPaisNac() == 'EC'}">selected</c:when>>Ecuador</option>
                                            <option value="EG" <c:when test="${Ella.getPaisNac() == 'EG'}">selected</c:when>>Egipto</option>
                                            <option value="SV" <c:when test="${Ella.getPaisNac() == 'SV'}">selected</c:when>>El Salvador</option>
                                            <option value="AE" <c:when test="${Ella.getPaisNac() == 'AE'}">selected</c:when>>Emiratos Árabes Unidos</option>
                                            <option value="ER" <c:when test="${Ella.getPaisNac() == 'ER'}">selected</c:when>>Eritrea</option>
                                            <option value="SI" <c:when test="${Ella.getPaisNac() == 'SI'}">selected</c:when>>Eslovenia</option>
                                            <option value="ES" <c:when test="${Ella.getPaisNac() == 'ES'}">selected</c:when>>España</option>
                                            <option value="US" <c:when test="${Ella.getPaisNac() == 'US'}">selected</c:when>>Estados Unidos</option>
                                            <option value="EE" <c:when test="${Ella.getPaisNac() == 'EE'}">selected</c:when>>Estonia</option>
                                            <option value="ET" <c:when test="${Ella.getPaisNac() == 'ET'}">selected</c:when>>Etiopía</option>
                                            <option value="FJ" <c:when test="${Ella.getPaisNac() == 'FJ'}">selected</c:when>>Fiji</option>
                                            <option value="PH" <c:when test="${Ella.getPaisNac() == 'PH'}">selected</c:when>>Filipinas</option>
                                            <option value="FI" <c:when test="${Ella.getPaisNac() == 'FI'}">selected</c:when>>Finlandia</option>
                                            <option value="FR" <c:when test="${Ella.getPaisNac() == 'FR'}">selected</c:when>>Francia</option>
                                            <option value="GA" <c:when test="${Ella.getPaisNac() == 'GA'}">selected</c:when>>Gabón</option>
                                            <option value="GM" <c:when test="${Ella.getPaisNac() == 'GM'}">selected</c:when>>Gambia</option>
                                            <option value="GE" <c:when test="${Ella.getPaisNac() == 'GE'}">selected</c:when>>Georgia</option>
                                            <option value="GH" <c:when test="${Ella.getPaisNac() == 'GH'}">selected</c:when>>Ghana</option>
                                            <option value="GI" <c:when test="${Ella.getPaisNac() == 'GI'}">selected</c:when>>Gibraltar</option>
                                            <option value="GD" <c:when test="${Ella.getPaisNac() == 'GD'}">selected</c:when>>Granada</option>
                                            <option value="GR" <c:when test="${Ella.getPaisNac() == 'GR'}">selected</c:when>>Grecia</option>
                                            <option value="GL" <c:when test="${Ella.getPaisNac() == 'GL'}">selected</c:when>>Groenlandia</option>
                                            <option value="GP" <c:when test="${Ella.getPaisNac() == 'GP'}">selected</c:when>>Guadalupe</option>
                                            <option value="GU" <c:when test="${Ella.getPaisNac() == 'GU'}">selected</c:when>>Guam</option>
                                            <option value="GT" <c:when test="${Ella.getPaisNac() == 'GT'}">selected</c:when>>Guatemala</option>
                                            <option value="GY" <c:when test="${Ella.getPaisNac() == 'GY'}">selected</c:when>>Guayana</option>
                                            <option value="GF" <c:when test="${Ella.getPaisNac() == 'GF'}">selected</c:when>>Guayana Francesa</option>
                                            <option value="GN" <c:when test="${Ella.getPaisNac() == 'GN'}">selected</c:when>>Guinea</option>
                                            <option value="GQ" <c:when test="${Ella.getPaisNac() == 'GQ'}">selected</c:when>>Guinea Ecuatorial</option>
                                            <option value="GW" <c:when test="${Ella.getPaisNac() == 'GW'}">selected</c:when>>Guinea-Bissau</option>
                                            <option value="HT" <c:when test="${Ella.getPaisNac() == 'HT'}">selected</c:when>>Haití</option>
                                            <option value="HN" <c:when test="${Ella.getPaisNac() == 'HN'}">selected</c:when>>Honduras</option>
                                            <option value="HU" <c:when test="${Ella.getPaisNac() == 'HU'}">selected</c:when>>Hungría</option>
                                            <option value="IN" <c:when test="${Ella.getPaisNac() == 'IN'}">selected</c:when>>India</option>
                                            <option value="ID" <c:when test="${Ella.getPaisNac() == 'ID'}">selected</c:when>>Indonesia</option>
                                            <option value="IQ" <c:when test="${Ella.getPaisNac() == 'IQ'}">selected</c:when>>Irak</option>
                                            <option value="IR" <c:when test="${Ella.getPaisNac() == 'IR'}">selected</c:when>>Irán</option>
                                            <option value="IE" <c:when test="${Ella.getPaisNac() == 'IE'}">selected</c:when>>Irlanda</option>
                                            <option value="BV" <c:when test="${Ella.getPaisNac() == 'BV'}">selected</c:when>>Isla Bouvet</option>
                                            <option value="CX" <c:when test="${Ella.getPaisNac() == 'CX'}">selected</c:when>>Isla de Christmas</option>
                                            <option value="IS" <c:when test="${Ella.getPaisNac() == 'IS'}">selected</c:when>>Islandia</option>
                                            <option value="KY" <c:when test="${Ella.getPaisNac() == 'KY'}">selected</c:when>>Islas Caimán</option>
                                            <option value="CK" <c:when test="${Ella.getPaisNac() == 'CK'}">selected</c:when>>Islas Cook</option>
                                            <option value="CC" <c:when test="${Ella.getPaisNac() == 'CC'}">selected</c:when>>Islas de Cocos o Keeling</option>
                                            <option value="FO" <c:when test="${Ella.getPaisNac() == 'FO'}">selected</c:when>>Islas Faroe</option>
                                            <option value="HM" <c:when test="${Ella.getPaisNac() == 'HM'}">selected</c:when>>Islas Heard y McDonald</option>
                                            <option value="FK" <c:when test="${Ella.getPaisNac() == 'FK'}">selected</c:when>>Islas Malvinas</option>
                                            <option value="MP" <c:when test="${Ella.getPaisNac() == 'MP'}">selected</c:when>>Islas Marianas del Norte</option>
                                            <option value="MH" <c:when test="${Ella.getPaisNac() == 'MH'}">selected</c:when>>Islas Marshall</option>
                                            <option value="UM" <c:when test="${Ella.getPaisNac() == 'UM'}">selected</c:when>>Islas menores de Estados Unidos</option>
                                            <option value="PW" <c:when test="${Ella.getPaisNac() == 'PW'}">selected</c:when>>Islas Palau</option>
                                            <option value="SB" <c:when test="${Ella.getPaisNac() == 'SB'}">selected</c:when>>Islas Salomón</option>
                                            <option value="SJ" <c:when test="${Ella.getPaisNac() == 'SJ'}">selected</c:when>>Islas Svalbard y Jan Mayen</option>
                                            <option value="TK" <c:when test="${Ella.getPaisNac() == 'TK'}">selected</c:when>>Islas Tokelau</option>
                                            <option value="TC" <c:when test="${Ella.getPaisNac() == 'TC'}">selected</c:when>>Islas Turks y Caicos</option>
                                            <option value="VI" <c:when test="${Ella.getPaisNac() == 'VI'}">selected</c:when>>Islas Vírgenes (EEUU)</option>
                                            <option value="VG" <c:when test="${Ella.getPaisNac() == 'VG'}">selected</c:when>>Islas Vírgenes (Reino Unido)</option>
                                            <option value="WF" <c:when test="${Ella.getPaisNac() == 'WF'}">selected</c:when>>Islas Wallis y Futuna</option>
                                            <option value="IL" <c:when test="${Ella.getPaisNac() == 'IL'}">selected</c:when>>Israel</option>
                                            <option value="IT" <c:when test="${Ella.getPaisNac() == 'IT'}">selected</c:when>>Italia</option>
                                            <option value="JM" <c:when test="${Ella.getPaisNac() == 'JM'}">selected</c:when>>Jamaica</option>
                                            <option value="JP" <c:when test="${Ella.getPaisNac() == 'JP'}">selected</c:when>>Japón</option>
                                            <option value="JO" <c:when test="${Ella.getPaisNac() == 'JO'}">selected</c:when>>Jordania</option>
                                            <option value="KZ" <c:when test="${Ella.getPaisNac() == 'KZ'}">selected</c:when>>Kazajistán</option>
                                            <option value="KE" <c:when test="${Ella.getPaisNac() == 'KE'}">selected</c:when>>Kenia</option>
                                            <option value="KG" <c:when test="${Ella.getPaisNac() == 'KG'}">selected</c:when>>Kirguizistán</option>
                                            <option value="KI" <c:when test="${Ella.getPaisNac() == 'KI'}">selected</c:when>>Kiribati</option>
                                            <option value="KW" <c:when test="${Ella.getPaisNac() == 'KW'}">selected</c:when>>Kuwait</option>
                                            <option value="LA" <c:when test="${Ella.getPaisNac() == 'LA'}">selected</c:when>>Laos</option>
                                            <option value="LS" <c:when test="${Ella.getPaisNac() == 'LS'}">selected</c:when>>Lesotho</option>
                                            <option value="LV" <c:when test="${Ella.getPaisNac() == 'LV'}">selected</c:when>>Letonia</option>
                                            <option value="LB" <c:when test="${Ella.getPaisNac() == 'LB'}">selected</c:when>>Líbano</option>
                                            <option value="LR" <c:when test="${Ella.getPaisNac() == 'LR'}">selected</c:when>>Liberia</option>
                                            <option value="LY" <c:when test="${Ella.getPaisNac() == 'LY'}">selected</c:when>>Libia</option>
                                            <option value="LI" <c:when test="${Ella.getPaisNac() == 'LI'}">selected</c:when>>Liechtenstein</option>
                                            <option value="LT" <c:when test="${Ella.getPaisNac() == 'LT'}">selected</c:when>>Lituania</option>
                                            <option value="LU" <c:when test="${Ella.getPaisNac() == 'LU'}">selected</c:when>>Luxemburgo</option>
                                            <option value="MK" <c:when test="${Ella.getPaisNac() == 'MK'}">selected</c:when>>Macedonia, Ex-República Yugoslava de</option>
                                            <option value="MG" <c:when test="${Ella.getPaisNac() == 'MG'}">selected</c:when>>Madagascar</option>
                                            <option value="MY" <c:when test="${Ella.getPaisNac() == 'MY'}">selected</c:when>>Malasia</option>
                                            <option value="MW" <c:when test="${Ella.getPaisNac() == 'MW'}">selected</c:when>>Malawi</option>
                                            <option value="MV" <c:when test="${Ella.getPaisNac() == 'MV'}">selected</c:when>>Maldivas</option>
                                            <option value="ML" <c:when test="${Ella.getPaisNac() == 'ML'}">selected</c:when>>Malí</option>
                                            <option value="MT" <c:when test="${Ella.getPaisNac() == 'MT'}">selected</c:when>>Malta</option>
                                            <option value="MA" <c:when test="${Ella.getPaisNac() == 'MA'}">selected</c:when>>Marruecos</option>
                                            <option value="MQ" <c:when test="${Ella.getPaisNac() == 'MQ'}">selected</c:when>>Martinica</option>
                                            <option value="MU" <c:when test="${Ella.getPaisNac() == 'MU'}">selected</c:when>>Mauricio</option>
                                            <option value="MR" <c:when test="${Ella.getPaisNac() == 'MR'}">selected</c:when>>Mauritania</option>
                                            <option value="YT" <c:when test="${Ella.getPaisNac() == 'YT'}">selected</c:when>>Mayotte</option>
                                            <option value="MX" <c:when test="${Ella.getPaisNac() == 'MX'}">selected</c:when>>México</option>
                                            <option value="FM" <c:when test="${Ella.getPaisNac() == 'FM'}">selected</c:when>>Micronesia</option>
                                            <option value="MD" <c:when test="${Ella.getPaisNac() == 'MD'}">selected</c:when>>Moldavia</option>
                                            <option value="MC" <c:when test="${Ella.getPaisNac() == 'MC'}">selected</c:when>>Mónaco</option>
                                            <option value="MN" <c:when test="${Ella.getPaisNac() == 'MN'}">selected</c:when>>Mongolia</option>
                                            <option value="MS" <c:when test="${Ella.getPaisNac() == 'MS'}">selected</c:when>>Montserrat</option>
                                            <option value="MZ" <c:when test="${Ella.getPaisNac() == 'MZ'}">selected</c:when>>Mozambique</option>
                                            <option value="NA" <c:when test="${Ella.getPaisNac() == 'NA'}">selected</c:when>>Namibia</option>
                                            <option value="NR" <c:when test="${Ella.getPaisNac() == 'NR'}">selected</c:when>>Nauru</option>
                                            <option value="NP" <c:when test="${Ella.getPaisNac() == 'NP'}">selected</c:when>>Nepal</option>
                                            <option value="NI" <c:when test="${Ella.getPaisNac() == 'NI'}">selected</c:when>>Nicaragua</option>
                                            <option value="NE" <c:when test="${Ella.getPaisNac() == 'NE'}">selected</c:when>>Níger</option>
                                            <option value="NG" <c:when test="${Ella.getPaisNac() == 'NG'}">selected</c:when>>Nigeria</option>
                                            <option value="NU" <c:when test="${Ella.getPaisNac() == 'NU'}">selected</c:when>>Niue</option>
                                            <option value="NF" <c:when test="${Ella.getPaisNac() == 'NF'}">selected</c:when>>Norfolk</option>
                                            <option value="NO" <c:when test="${Ella.getPaisNac() == 'NO'}">selected</c:when>>Noruega</option>
                                            <option value="NC" <c:when test="${Ella.getPaisNac() == 'NC'}">selected</c:when>>Nueva Caledonia</option>
                                            <option value="NZ" <c:when test="${Ella.getPaisNac() == 'NZ'}">selected</c:when>>Nueva Zelanda</option>
                                            <option value="OM" <c:when test="${Ella.getPaisNac() == 'OM'}">selected</c:when>>Omán</option>
                                            <option value="NL" <c:when test="${Ella.getPaisNac() == 'NL'}">selected</c:when>>Países Bajos</option>
                                            <option value="PA" <c:when test="${Ella.getPaisNac() == 'PA'}">selected</c:when>>Panamá</option>
                                            <option value="PG" <c:when test="${Ella.getPaisNac() == 'PG'}">selected</c:when>>Papúa Nueva Guinea</option>
                                            <option value="PK" <c:when test="${Ella.getPaisNac() == 'PK'}">selected</c:when>>Paquistán</option>
                                            <option value="PY" <c:when test="${Ella.getPaisNac() == 'PY'}">selected</c:when>>Paraguay</option>
                                            <option value="Peru" <c:when test="${Ella.getPaisNac() == 'Peru'}">selected</c:when>>Perú</option>
                                            <option value="PN" <c:when test="${Ella.getPaisNac() == 'PN'}">selected</c:when>>Pitcairn</option>
                                            <option value="PF" <c:when test="${Ella.getPaisNac() == 'PF'}">selected</c:when>>Polinesia Francesa</option>
                                            <option value="PL" <c:when test="${Ella.getPaisNac() == 'PL'}">selected</c:when>>Polonia</option>
                                            <option value="PT" <c:when test="${Ella.getPaisNac() == 'PT'}">selected</c:when>>Portugal</option>
                                            <option value="PR" <c:when test="${Ella.getPaisNac() == 'PR'}">selected</c:when>>Puerto Rico</option>
                                            <option value="QA" <c:when test="${Ella.getPaisNac() == 'QA'}">selected</c:when>>Qatar</option>
                                            <option value="UK" <c:when test="${Ella.getPaisNac() == 'UK'}">selected</c:when>>Reino Unido</option>
                                            <option value="CF" <c:when test="${Ella.getPaisNac() == 'CF'}">selected</c:when>>República Centroafricana</option>
                                            <option value="CZ" <c:when test="${Ella.getPaisNac() == 'CZ'}">selected</c:when>>República Checa</option>
                                            <option value="ZA" <c:when test="${Ella.getPaisNac() == 'ZA'}">selected</c:when>>República de Sudáfrica</option>
                                            <option value="DO" <c:when test="${Ella.getPaisNac() == 'DO'}">selected</c:when>>República Dominicana</option>
                                            <option value="SK" <c:when test="${Ella.getPaisNac() == 'SK'}">selected</c:when>>República Eslovaca</option>
                                            <option value="RE" <c:when test="${Ella.getPaisNac() == 'RE'}">selected</c:when>>Reunión</option>
                                            <option value="RW" <c:when test="${Ella.getPaisNac() == 'RW'}">selected</c:when>>Ruanda</option>
                                            <option value="RO" <c:when test="${Ella.getPaisNac() == 'RO'}">selected</c:when>>Rumania</option>
                                            <option value="RU" <c:when test="${Ella.getPaisNac() == 'RU'}">selected</c:when>>Rusia</option>
                                            <option value="EH" <c:when test="${Ella.getPaisNac() == 'EH'}">selected</c:when>>Sahara Occidental</option>
                                            <option value="KN" <c:when test="${Ella.getPaisNac() == 'KN'}">selected</c:when>>Saint Kitts y Nevis</option>
                                            <option value="WS" <c:when test="${Ella.getPaisNac() == 'WS'}">selected</c:when>>Samoa</option>
                                            <option value="AS" <c:when test="${Ella.getPaisNac() == 'AS'}">selected</c:when>>Samoa Americana</option>
                                            <option value="SM" <c:when test="${Ella.getPaisNac() == 'SM'}">selected</c:when>>San Marino</option>
                                            <option value="VC" <c:when test="${Ella.getPaisNac() == 'VC'}">selected</c:when>>San Vicente y Granadinas</option>
                                            <option value="SH" <c:when test="${Ella.getPaisNac() == 'SH'}">selected</c:when>>Santa Helena</option>
                                            <option value="LC" <c:when test="${Ella.getPaisNac() == 'LC'}">selected</c:when>>Santa Lucía</option>
                                            <option value="ST" <c:when test="${Ella.getPaisNac() == 'ST'}">selected</c:when>>Santo Tomé y Príncipe</option>
                                            <option value="SN" <c:when test="${Ella.getPaisNac() == 'SN'}">selected</c:when>>Senegal</option>
                                            <option value="SC" <c:when test="${Ella.getPaisNac() == 'SC'}">selected</c:when>>Seychelles</option>
                                            <option value="SL" <c:when test="${Ella.getPaisNac() == 'SL'}">selected</c:when>>Sierra Leona</option>
                                            <option value="SG" <c:when test="${Ella.getPaisNac() == 'SG'}">selected</c:when>>Singapur</option>
                                            <option value="SY" <c:when test="${Ella.getPaisNac() == 'SY'}">selected</c:when>>Siria</option>
                                            <option value="SO" <c:when test="${Ella.getPaisNac() == 'SO'}">selected</c:when>>Somalia</option>
                                            <option value="LK" <c:when test="${Ella.getPaisNac() == 'LK'}">selected</c:when>>Sri Lanka</option>
                                            <option value="PM" <c:when test="${Ella.getPaisNac() == 'PM'}">selected</c:when>>St Pierre y Miquelon</option>
                                            <option value="SZ" <c:when test="${Ella.getPaisNac() == 'SZ'}">selected</c:when>>Suazilandia</option>
                                            <option value="SD" <c:when test="${Ella.getPaisNac() == 'SD'}">selected</c:when>>Sudán</option>
                                            <option value="SE" <c:when test="${Ella.getPaisNac() == 'SE'}">selected</c:when>>Suecia</option>
                                            <option value="CH" <c:when test="${Ella.getPaisNac() == 'CH'}">selected</c:when>>Suiza</option>
                                            <option value="SR" <c:when test="${Ella.getPaisNac() == 'SR'}">selected</c:when>>Surinam</option>
                                            <option value="TH" <c:when test="${Ella.getPaisNac() == 'TH'}">selected</c:when>>Tailandia</option>
                                            <option value="TW" <c:when test="${Ella.getPaisNac() == 'TW'}">selected</c:when>>Taiwán</option>
                                            <option value="TZ" <c:when test="${Ella.getPaisNac() == 'TZ'}">selected</c:when>>Tanzania</option>
                                            <option value="TJ" <c:when test="${Ella.getPaisNac() == 'TJ'}">selected</c:when>>Tayikistán</option>
                                            <option value="TF" <c:when test="${Ella.getPaisNac() == 'TF'}">selected</c:when>>Territorios franceses del Sur</option>
                                            <option value="TP" <c:when test="${Ella.getPaisNac() == 'TP'}">selected</c:when>>Timor Oriental</option>
                                            <option value="TG" <c:when test="${Ella.getPaisNac() == 'TG'}">selected</c:when>>Togo</option>
                                            <option value="TO" <c:when test="${Ella.getPaisNac() == 'TO'}">selected</c:when>>Tonga</option>
                                            <option value="TT" <c:when test="${Ella.getPaisNac() == 'TT'}">selected</c:when>>Trinidad y Tobago</option>
                                            <option value="TN" <c:when test="${Ella.getPaisNac() == 'TN'}">selected</c:when>>Túnez</option>
                                            <option value="TM" <c:when test="${Ella.getPaisNac() == 'TM'}">selected</c:when>>Turkmenistán</option>
                                            <option value="TR" <c:when test="${Ella.getPaisNac() == 'TR'}">selected</c:when>>Turquía</option>
                                            <option value="TV" <c:when test="${Ella.getPaisNac() == 'TV'}">selected</c:when>>Tuvalu</option>
                                            <option value="UA" <c:when test="${Ella.getPaisNac() == 'UA'}">selected</c:when>>Ucrania</option>
                                            <option value="UG" <c:when test="${Ella.getPaisNac() == 'UG'}">selected</c:when>>Uganda</option>
                                            <option value="UY" <c:when test="${Ella.getPaisNac() == 'UY'}">selected</c:when>>Uruguay</option>
                                            <option value="UZ" <c:when test="${Ella.getPaisNac() == 'UZ'}">selected</c:when>>Uzbekistán</option>
                                            <option value="VU" <c:when test="${Ella.getPaisNac() == 'VU'}">selected</c:when>>Vanuatu</option>
                                            <option value="VE" <c:when test="${Ella.getPaisNac() == 'VE'}">selected</c:when>>Venezuela</option>
                                            <option value="VN" <c:when test="${Ella.getPaisNac() == 'VN'}">selected</c:when>>Vietnam</option>
                                            <option value="YE" <c:when test="${Ella.getPaisNac() == 'YE'}">selected</c:when>>Yemen</option>
                                            <option value="YU" <c:when test="${Ella.getPaisNac() == 'YU'}">selected</c:when>>Yugoslavia</option>
                                            <option value="ZM" <c:when test="${Ella.getPaisNac() == 'ZM'}">selected</c:when>>Zambia</option>
                                            <option value="ZW" <c:when test="${Ella.getPaisNac() == 'ZW'}">selected</c:when>>Zimbabue</option>
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
                                        <input value="${Ella.getNDoc()}" id="numDoc" name="numDoc" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <label class="control-label">Pasaporte</label>
                                        <input value="${Ella.getPasaporte()}" id="pasaporte" name="pasaporte" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Celular</label>
                                    <div class="controls">
                                        <input value="${Ella.getCelular()}" id="numCel" name="numCel" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Correo Electrónico</label>
                                    <div class="controls">
                                        <input value="${Ella.getCorreo()}" id="correo" name="correo" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Estado Civil</h3>
                                <div class="row">

                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="soltera" value="soltera" ${infoFam.getEstadoCivil() == 'soltera' ? 'checked' : ''}>Soltera</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="casada" value="casados" ${infoFam.getEstadoCivil() == 'casados' ? 'checked' : ''}>Casada</label>
                                            <br>
                                            <label class="control-label">Fecha de matrimonio Civil</label>
                                            <input ${infoFam.getEstadoCivil() == null || infoFam.getEstadoCivil() == 'casados' ? '' : 'disabled'} value="${infoFam.getFechaMatrimonio() != null ? df.dateToStringNumeros(infoFam.getFechaMatrimonio()) : ''}" id="fechaMat" name="fechaMat" type="text" class="datepicker input-xlarge">
                                        </div>                            
                                    </div>
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="viuda" value="viuda" ${infoFam.getEstadoCivil() == 'viuda' ? 'checked' : ''}>Viuda</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="divorciada" value="divorciada" ${infoFam.getEstadoCivil() == 'divorciada' ? 'checked' : ''}>Divorciada</label>
                                        </div>
                                    </div> 
                                </div>    
                                <!-- Aquí va lo de educación,ocupación,e ingresos económicos y tmb lo de Salud y previsión 
                                -->
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
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
    </body>
</html>