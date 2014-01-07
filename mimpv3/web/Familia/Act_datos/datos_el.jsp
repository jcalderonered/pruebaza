<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Familia"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
    Familia u = (Familia) request.getSession().getAttribute("usuario");
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
                            <li><a href="${pageContext.servletContext.contextPath}/inicioFam">Inicio</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/FactDatos/opc1">Ver Información</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/salir">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div class="container">
                <div class="row">
                    <div class="col-md-3 ">
                        <ul class="nav nav-list well">
                            <li><a href="${pageContext.servletContext.contextPath}/inicioFam"><span class="glyphicon glyphicon-home"></span>Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Finscripcion"><span class="glyphicon glyphicon-chevron-right"></span> Inscripción a talleres</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Festado"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fcontra"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>
                    <div class="col-md-9">
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc1" data-toggle="tab">La Solicitante</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/FactDatos/opc2" data-toggle="tab">El solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc3" data-toggle="tab">Composición familiar</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc4" data-toggle="tab">Vivienda</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc5" data-toggle="tab">Antecedentes del niño, niña o adolescente</a></li>
                        </ul>
                        <form class="form-horizontal"> 
                            <fieldset>
                                <br>
                                <h3><strong>Generales</strong></h3>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label">Nombre </label>
                                    <div class="controls">
                                        <input id="nombre" name="full-name" type="text" value="${adop.getNombre()}" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Paterno </label>
                                    <div class="controls">
                                        <input id="apellido_p" name="full-name" type="text" value="${adop.getApellidoP()}" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Materno </label>
                                    <div class="controls">
                                        <input id="apellido_m" name="full-name" type="text" value="${adop.getApellidoM()}" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de nacimiento </label>
                                    <div class="controls">
                                        <input id="fecha_nac" name="full-name" value="${fechanac}" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Lugar de nacimiento </label>
                                    <div class="controls">
                                        <input id="direccion" name="full-name" value="${adop.getLugarNac()}" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Departamento de nacimiento </label>
                                    <div class="controls">
                                        <input id="departamento" name="full-name" value="${adop.getDepaNac()}" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">País de nacimiento </label>
                                    <div class="controls">
                                        <input id="pais" name="full-name" value="${adop.getPaisNac()}" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-2">  
                                        <div class="radio">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${adop.getTipoDoc() == 'D'}">
                                                        <input type="radio" name="optionsRadios1" id="optionsRadios1" checked>DNI</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios1" id="optionsRadios1">DNI</label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${adop.getTipoDoc() == 'C'}">
                                                        <input type="radio" name="optionsRadios1" id="optionsRadios2" checked>Carnet de Extranjería</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios1" id="optionsRadios2">Carnet de Extranjería</label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>                            
                                    </div>
                                </div>    
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <label class="control-label">N° de Documento </label>
                                        <input id="num_doc" value="${adop.getNDoc()}" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Celular </label>
                                    <div class="controls">
                                        <input id="celular" value="${adop.getCelular()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Correo Electrónico </label>
                                    <div class="controls">
                                        <input id="correo" value="${adop.getCorreo()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <h3>Estado Civil</h3>
                            <div class="row">
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <c:choose>
                                                    <c:when test="${estCivil == 'S'}">
                                                        <input type="radio" name="optionsRadios2" id="soltero" value="D" checked>Soltero</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios2" id="soltero" value="D">Soltero</label>
                                                </c:otherwise>
                                            </c:choose>
                                    </div>
                                </div>   
                                <div class="col-md-3">   
                                    <div class="radio">
                                        <label>
                                            <c:choose>
                                                    <c:when test="${estCivil == 'C'}">
                                                        <input type="radio" name="optionsRadios2" id="casado" value="C" checked>Casado</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios2" id="casado" value="C">Casado</label>
                                                </c:otherwise>
                                            </c:choose>
                                    </div>                            
                                </div>
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <c:choose>
                                                    <c:when test="${estCivil == 'V'}">
                                                        <input type="radio" name="optionsRadios2" id="viudo" value="D" checked>Viudo</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios2" id="viudo" value="D">Viudo</label>
                                                </c:otherwise>
                                            </c:choose>
                                            
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <c:choose>
                                                    <c:when test="${estCivil == 'D'}">
                                                        <input type="radio" name="optionsRadios2" id="divorciado" value="D" checked>Divorciado</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios2" id="divorciado" value="D">Divorciado</label>
                                                </c:otherwise>
                                            </c:choose>
                                    </div>
                                </div> 
                            </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de matrimonio Civil </label>
                                    <div class="controls">
                                        <input id="fecha_matrimonio" value="${fechaMatri}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <h3><strong>Educación, Ocupación e Ingresos Económicos</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Nivel de instrucción alcanzado</label>
                                    <div class="controls">
                                        <input id="profesion" value="${adop.getNivelInstruccion()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <h3>Culminó el nivel de instrucción señalado</h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${adop.getCulminoNivel() == 1}">
                                                        <input type="radio" name="optionsRadios4" id="si" value="D" checked>Si</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios4" id="si" value="D">Si</label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${adop.getCulminoNivel() == 0}">
                                                        <input type="radio" name="optionsRadios4" id="no" value="D" checked>No</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios4" id="no" value="D">No</label>
                                                </c:otherwise>
                                            </c:choose>

                                        </div>
                                    </div>  
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Profesión u Oficio </label>
                                    <div class="controls">
                                        <input id="profesion" value="${adop.getProfesion()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <h3>Trabajador dependiente</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="checkbox">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${adop.getTrabajadorDepend() == 1}">
                                                        <input type="checkbox" name="checkbox1" id="checkbox1" value="D" checked>Si</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="checkbox" name="checkbox1" id="checkbox1" value="D" >Si</label>
                                                </c:otherwise>
                                            </c:choose>                                 
                                        </div>
                                    </div>  
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ocupación Actual</label>
                                    <div class="controls">
                                        <input id="ocupacion" value="${adop.getOcupActualDep()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Centro de Trabajo </label>
                                    <div class="controls">
                                        <input id="trabajo" value="${adop.getCentroTrabajo()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Dirección del centro de trabajo</label>
                                    <div class="controls">
                                        <input id="direccion_trabajo" value="${adop.getDireccionCentro()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Teléfono del centro de trabajo </label>
                                    <div class="controls">
                                        <input id="telefono_trabajo" value="${adop.getTelefonoCentro()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ingreso mensual sustentable (sueldo bruto) </label>
                                    <div class="controls">
                                        <input id="ingreso_depen" value="${adop.getIngresoDep()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <h3>Trabajador independiente</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="checkbox">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${adop.getTrabajadorIndepend() == 1}">
                                                        <input type="checkbox" name="checkbox2" id="checkbox2" value="D" checked>Si</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="checkbox" name="checkbox2" id="checkbox2" value="D">Si</label>
                                                </c:otherwise>
                                            </c:choose>    
                                        </div>
                                    </div>  
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ocupación Actual</label>
                                    <div class="controls">
                                        <input id="ocupacion_indep" value="${adop.getOcupActualInd()}" name="full-name" type="text" class="input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ingreso mensual sustentable</label>
                                    <div class="controls">
                                        <input id="ingreso_indep" value="${adop.getIngresoIndep()}" name="full-name" type="text" class="input-xlarge" disabled>
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
                                                <c:choose>
                                                    <c:when test="${adop.getSeguroSalud() == 1}">
                                                        <input type="radio" name="optionsRadios5" id="seguro_si" value="D" checked>Si</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios5" id="seguro_si" value="D">Si</label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>  
                                    <div class="col-md-2">  
                                        <div class="radio">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${adop.getSeguroSalud() == 0}">
                                                        <input type="radio" name="optionsRadios5" id="seguro_no" value="D" checked>No</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios5" id="seguro_no" value="D">No</label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Tipo de Seguro</label>
                                    <div class="controls">
                                        <input id="tipo_seguro" value="${adop.getTipoSeguro()}" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Seguro de vida</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${adop.getSeguroVida() == 1}">
                                                        <input type="radio" name="optionsRadios6" id="vida_si" value="D" checked>Si</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios6" id="vida_si" value="D">Si</label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>  
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${adop.getSeguroVida() == 0}">
                                                        <input type="radio" name="optionsRadios6" id="vida_no" value="D" checked>No</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios6" id="vida_no" value="D">No</label>
                                                </c:otherwise>
                                            </c:choose>

                                        </div>
                                    </div>
                                </div> 
                                <br>
                                <h3>Afiliado al sistema de pensiones</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${adop.getSistPensiones() == 1}">
                                                        <input type="radio" name="optionsRadios7" id="afiliado_si" value="D" checked>Si</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios7" id="afiliado_si" value="D">Si</label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>  
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <c:choose>
                                                    <c:when test="${adop.getSistPensiones() == 0}">
                                                        <input type="radio" name="optionsRadios7" id="afiliado_no" value="D" checked>No</label>
                                                    </c:when>
                                                    <c:otherwise>
                                                    <input type="radio" name="optionsRadios7" id="afiliado_no" value="D">No</label>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Estado de salud actual</label>
                                    <div class="controls">
                                        <input id="estado_acutal" value="${adop.getSaludActual()}" name="full-name" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <p style="color: red">IMPORTANTE: SI DESEA REALIZAR ALGÚN CAMBIO DEBERÁ PRESENTAR UNA SOLICITUD</p>
                                <p style="color: red">EN MESA DE PARTES DE LA DGA</p>
                                <br>
                                <p>Según lo establecido en la Ley Nº29733 - Ley de protección de datos personales, la información proporcionada por 
                                    usted/ustedes será de uso exclusivo, con la reserva y confidencialidad del caso, para los fines concernientes al 
                                    procedimiento administrativo de adopción.
                                </p>
                                <br>
                            </fieldset>
                        </form>
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
            </div>
    </body>
</html>