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
                            <li><a href="${pageContext.servletContext.contextPath}/esperaInter"><span class="glyphicon glyphicon-chevron-right"></span>Adoptantes para la adopción en el extranjero</a></li>       
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
                                <li><a href="${pageContext.servletContext.contextPath}/procesoAdopcion?volver=${volver}" >Proceso de adopción</a></li>
                                <li ${estado == 'formativa' ? 'class="hidden"' : ''}><a href="${pageContext.servletContext.contextPath}/antNna?volver=${volver}" >Antecedentes del NNA</a></li>
                                <li ${estado == 'formativa' || estado == 'evaluacion' || estado == 'espera' || estado == 'designacion' || estado == 'adopcion' || estado == 'reevaluacion' ? 'class="hidden"' : ''} ><a href="${pageContext.servletContext.contextPath}/nnaAsociado?volver=${volver}" >NNA Adoptado</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/atenciones?volver=${volver}" >Atenciones</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/EditUserPass?volver=${volver}" >Editar Perfil de Familia</a></li>
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
                                        <input value="${El.getNombre()}" id="nombre" name="nombre" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Paterno</label>
                                    <div class="controls">
                                        <input value="${El.getApellidoP()}" id="apellidoP" name="apellidoP" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Materno</label>
                                    <div class="controls">
                                        <input value="${El.getApellidoM()}" id="apellidoM" name="apellidoM" type="text" class="input-xlarge">
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
                                        <input value="${El.getLugarNac()}" id="lugarNac" name="lugarNac" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Departamento de nacimiento</label>
                                    <div class="controls">
                                        <input value="${El.getDepaNac()}" id="depNac" name="depNac" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">País de nacimiento</label>
                                    <div class="controls">
                                        <input value="${El.getPaisNac()}" id="paisNac" name="paisNac" type="text" class="input-xlarge">
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
                                        <input value="${El.getNDoc()}" id="numDoc" name="numDoc" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <c:if test="${expediente.getNacionalidad() == 'internacional'}">
                                <div class="control-group">
                                    <div class="controls">
                                        <label class="control-label">Pasaporte</label>
                                        <input value="${El.getPasaporte()}" id="pasaporte" name="pasaporte" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                </c:if>
                                <div class="control-group">
                                    <label class="control-label">Celular</label>
                                    <div class="controls">
                                        <input value="${El.getCelular()}" id="numCel" name="numCel" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Correo Electrónico</label>
                                    <div class="controls">
                                        <input value="${El.getCorreo()}" id="correo" name="correo" type="text" class="input-xlarge">
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
                                        <button ${El.getIdadoptante() == null || El.getIdadoptante() == 0 ? 'disabled' : '' } type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
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

