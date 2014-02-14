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
                        <form role="form">
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
                                <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/infoExpediente" >Información del Expediente</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/procesoAdopcion" >Proceso de adopción</a></li>
                                <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/antNna" >Antecedentes del NNA</a></li>
                                <li ${estado == 'formativa' || estado == 'evaluacion' || estado == 'espera' || estado == 'designacion' || estado == 'adopcion' || estado == 'reevaluacion' ? 'class="hidden"' : 'class="active"'} ><a href="${pageContext.servletContext.contextPath}/nnaAsociado" >NNA Adoptado</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/atenciones" >Atenciones</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/EditUserPass" >Editar Perfil de Familia</a></li>
                            </ul>
                            <br>
                            <fieldset>
                                <br>
                                <h3><strong>Datos del NNA</strong></h3>
                                <br>
                                <h3><strong>Nombre registrado</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Nombre</label>
                                    <div class="controls">
                                        <input value="${nna.getNombre()}" id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Paterno</label>
                                    <div class="controls">
                                        <input value="${nna.getApellidoP()}" id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Materno</label>
                                    <div class="controls">
                                        <input  value="${nna.getApellidoM()}" id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3><strong>Nombre Adoptado</strong></h3>
                                <br>
                                <c:if test="${exp != null}">
                                    <div class="control-group">
                                        <label class="control-label">Nombre</label>
                                        <div class="controls">
                                            <input value="${exp.getNActual()}" id="full-name" name="full-name" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Apellido Paterno</label>
                                        <div class="controls">
                                            <input value="${exp.getApellidopActual()}" id="full-name" name="full-name" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                    <br>
                                    <div class="control-group">
                                        <label class="control-label">Apellido Materno</label>
                                        <div class="controls">
                                            <input value="${exp.getApellidomActual()}" id="full-name" name="full-name" type="text" class="input-xlarge">
                                        </div>
                                    </div>
                                </c:if>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Sexo</label>
                                    <div class="controls">
                                        <input value="${nna.getSexo() == 'femenino' ? 'Femenino' : 'Masculino'}" id="full-name" name="full-name" type="text" class="input-xlarge">
                                    </div>    
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de nacimiento</label>
                                    <div class="controls">
                                        <input value="${nna.getFechaNacimiento() != null ? df.dateToString(nna.getFechaNacimiento()): ''}" id="full-name" name="full-name" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Edad </label>
                                    <div class="controls">
                                        <input value="${nna.getEdadAnhos()}" id="full-name" name="full-name" type="text" placeholder="Años" class="input-xlarge">
                                        &nbsp;
                                        <input value="${nna.getEdadMeses()}" id="full-name" name="full-name" type="text" placeholder="Meses" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Juzgado</label>
                                    <div class="controls">
                                        <input value="${nna.getJuzgado().getNombre()}" class="input-xlarge">
                                    </div>    
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Car</label>
                                    <div class="controls">
                                        <input value="${nna.getCar().getNombre()}" class="input-xlarge">
                                    </div>    
                                </div>
                                <br>
                                <h3><strong>Presenta acta de nacimiento</strong></h3>
                                <br>
                                <!-- Text input-->
                                <div class="row">
                                    <div class="col-md-2">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" ${nna.getActaNacimiento() == 0 ? 'checked' : ''} >Si</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="optionsRadios" id="optionsRadios2" value="C" ${nna.getActaNacimiento() != 0 ? 'checked' : ''}>No</label>
                                        </div>                            
                                    </div>
                                </div>  
                                <br>

                                <div class="control-group">
                                    <label class="control-label">Condición de Salud</label>
                                    <div class="controls">
                                        <input value="${nna.getCondicionSalud()}" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Lugar de nacimiento</label>
                                    <div class="controls">
                                        <input value="${nna.getDepartamentoNacimiento()}" id="full-name" name="full-name" type="text" placeholder="Departamento" class="input-xlarge">
                                        &nbsp;
                                        <input value="${nna.getProvinciaNacimiento()}" id="full-name" name="full-name" type="text" placeholder="Provincia" class="input-xlarge">
                                        &nbsp;
                                        <input value="${nna.getDistritoNacimiento()}" id="full-name" name="full-name" type="text" placeholder="Distrito" class="input-xlarge">
                                        &nbsp;
                                    </div>
                                    <br>
                                    <input id="full-name" name="full-name" type="text" placeholder="Dirección" class="input-xlarge">
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Número de resolución de abandono</label>
                                    <div class="controls">
                                        <input id="numResolAband" name="numResolAband" value="${nna.getNResolAband()}" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de resolución de abandono</label>
                                    <div class="controls">
                                        <input id="fechaResolAband" name="fechaResolAband" value="${nna.getFechaResolAbandono() != null ? df.dateToString(nna.getFechaResolAbandono()) : ''}" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Número de resolución consentida</label>
                                    <div class="controls">
                                        <input id="numResolConsen" name="numResolConsen" value="${nna.getNResolAband()}" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de resolución consentida</label>
                                    <div class="controls">
                                        <input  id="fechaResolConsen" name="fechaResolConsen" value="${nna.getFechaResolConsentida() != null ? df.dateToString(nna.getFechaResolConsentida()) : ''}" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div>
                                    <label class="control-label">Clasificación</label>
                                    <div class="controls">
                                        <select disabled >
                                            <option value="prioritario" ${nna.getClasificacion().equals("prioritario") ? 'selected' : ''} >Prioritario</option>
                                            <option value="seguimiento" ${nna.getClasificacion().equals("seguimiento") ? 'selected' : ''} >Seguimiento</option>
                                            <option value="regular" ${nna.getClasificacion().equals("regular") ? 'selected' : ''} >Regular</option>
                                        </select>
                                    </div>    
                                </div>
                                <br>
                                <br>
                                <h3><strong>Antecedentes, condiciones de salud y desarrollo del niño, niña o adolescente</strong></h3>
                                <br> 

                                <div class="row">
                                    <div id="tabla_fam" class="table-responsive">
                                        <table id="hijos" class="table table-bordered table-striped ">
                                            <thead>
                                                <tr>
                                                    <th>Antecedentes</th>
                                                    <th>Cumple</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <h4>Nacido(a) como consecuencia del incesto</h4>
                                                    </td>
                                                    <td>
                                                        <select id="incesto" name="incesto" >
                                                            <option value="0" ${nna.getIncesto() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${nna.getIncesto() != 0 ? 'selected' : ''} >NO</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>Padres con enfermedad psiquiátrica (esquizofrenia, paranoia, etc</h4>
                                                    </td>
                                                    <td>
                                                        <select id="psiquiatrica" name="psiquiatrica" >
                                                            <option value="0" ${nna.getMental() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${nna.getMental() != 0 ? 'selected' : ''} >NO</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>Padres con epilepsia</h4>
                                                    </td>
                                                    <td>
                                                        <select id="epilepsia" name="epilepsia" >
                                                            <option value="0" ${nna.getEpilepsia() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${nna.getEpilepsia() != 0 ? 'selected' : ''} >NO</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>Niña, niño o adolescente víctima de abuso sexual</h4>
                                                    </td>
                                                    <td>
                                                        <select id="abuso" name="abuso" >
                                                            <option value="0" ${nna.getAbuso() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${nna.getAbuso() != 0 ? 'selected' : ''} >NO</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>Niña, niño o adolescente adolescente actualmente sano, que al nacer fue 
                                                            diagnosticado/a preliminarmente con sífilis congénita.</h4>
                                                    </td>
                                                    <td>
                                                        <select id="sifilis" name="sifilis" >
                                                            <option value="0" ${nna.getSifilis() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${nna.getSifilis() != 0 ? 'selected' : ''} >NO</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                                <div class="row">
                                    <div id="tabla_fam" class="table-responsive">
                                        <table id="hijos" class="table table-bordered table-striped ">
                                            <thead>
                                                <tr>
                                                    <th>Condiciones de salud y desarrollo</th>
                                                    <th>Cumple</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <h4>Salud  estable  que  requiere  seguimiento  médico  (soplo,  hipotiroidismo, 
                                                            desnutrición crónica, etc.)</h4>
                                                    </td>
                                                    <td>
                                                        <select id="estable" name="estable" >
                                                            <option value="0" ${nna.getSeguiMedico() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${nna.getSeguiMedico() != 0 ? 'selected' : ''} >NO</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>Condiciones  de  salud  que  requieran  intervención  quirúrgica  menor 
                                                            (labio leporino, estrabismo, etc.)</h4>
                                                    </td>
                                                    <td>
                                                        <select id="intervencion" name="intervencion" >
                                                            <option value="0" ${nna.getOperacion() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${nna.getOperacion() != 0 ? 'selected' : ''} >NO</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>Niña,  niño  o  adolescente  con  trastorno  de  déficit  de  atención  e 
                                                            hiperactividad (TDAH)</h4>
                                                    </td>
                                                    <td>
                                                        <select id="trastorno" name="trastorno">
                                                            <option value="0" ${nna.getHiperactivo() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${nna.getHiperactivo() != 0 ? 'selected' : ''} >NO</option>
                                                        </select>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="row">
                                    <div id="tabla_fam" class="table-responsive">
                                        <table id="hijos" class="table table-bordered table-striped ">
                                            <thead>
                                                <tr>
                                                    <th>Condiciones prioritarias</th>
                                                    <th>Cumple</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>
                                                        <h4>Niñas, niños y adolescentes con necesidades especiales</h4>
                                                    </td>
                                                    <td>
                                                        <div class="radio">
                                                            <label>
                                                                <input type="radio" name="prioritarios" id="nna1" value="e" ${nna.getEspecial() == 0 ? 'checked' : ''}></label>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>Niñas, niños y adolescentes con problemas de salud </h4>
                                                    </td>
                                                    <td>
                                                        <div class="radio">
                                                            <label>
                                                                <input type="radio" name="prioritarios" id="nna2" value="s" ${nna.getEnfermo() == 0 ? 'checked' : ''}></label>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>Niñas y niños mayores (A partir de 09 años)</h4>
                                                    </td>
                                                    <td>
                                                        <div class="radio">
                                                            <label>
                                                                <input type="radio" name="prioritarios" id="nna3" value="m" ${nna.getMayor() == 0 ? 'checked' : ''}></label>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>Adolescentes (De 12 años hasta 17 años 11 meses)</h4>
                                                    </td>
                                                    <td>
                                                        <div class="radio">
                                                            <label>
                                                                <input type="radio" name="prioritarios" id="nna4" value="a" ${nna.getAdolescente() == 0 ? 'checked' : ''}></label>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <h4>Grupos de hermanos </h4>
                                                    </td>
                                                    <td>
                                                        <div class="radio">
                                                            <label>
                                                                <input type="radio" name="prioritarios" id="nna5" value="h" ${nna.getHermano() == 0 ? 'checked' : ''}></label>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">Observaciones</label>
                                    <div class="controls">
                                        <textarea id="obs" name="obs" cols="25" rows="5">${nna.getObservaciones()}</textarea>
                                    </div>
                                </div>
                                <br>
                                <!--
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
                                    </div>
                                </div>
                                
                                FIN DE CONTENIDO-->
                            </fieldset>
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
            <!-- Ubicar al final -->

    </body>
</html>

