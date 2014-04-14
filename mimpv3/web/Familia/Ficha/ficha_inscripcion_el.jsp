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
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/inicioFam">Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc1">Ver Información</a></li>
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
                            <li><a href="${pageContext.servletContext.contextPath}/Flecturas"><span class="glyphicon glyphicon-chevron-right"></span> Lecturas </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Festado"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fcontra"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>
                    <div class="col-md-9">
                        <br>
                        <h1 align="center"><strong>Ficha de Inscripción para Solicitantes de Adopción</strong></h1>
                        <br>
                        <div class="row">
                            <div class="col-md-2">
                                <div class="control-group">
                                    <label class="control-label">Fecha</label>
                                    <div class="controls">
                                        <input disabled value="${factual}" id="full-name" name="fecha_ingreso" type="text" class="input-xlarge">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-2 col-md-offset-1">
                                <div class="control-group">
                                    <label class="control-label">Número</label>
                                    <div class="controls">
                                        <input disabled value="" id="full-name" name="numero" type="text" class="input-xlarge">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row">
                            <li><a href="${pageContext.servletContext.contextPath}/Fficha/opc1">La Solicitante</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/Fficha/opc2">El solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fficha/opc3">Composición familiar</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fficha/opc4">Vivienda</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fficha/opc5">Proceso de adopción</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/Fficha/opc6">Antecedentes del niño, niña o adolescente</a></li>
                        </ul>
                        <form role="form" action="${pageContext.servletContext.contextPath}/FfichaGuardar/opc2" method="post"> 
                            <fieldset>
                                <br>
                                <h3><strong>Generales</strong></h3>
                                <br>
                                <!-- Text input-->
                                <div class="control-group">
                                    <label class="control-label">Nombre</label>
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getNombre()}" name="nombre_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Paterno</label>
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getApellidoP()}" name="apellido_p_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Materno</label>
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getApellidoM()}" name="apellido_m_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Edad</label>  
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getEdad()}" name="edad_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <p style="color: red">${mensaje_edad}</p>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de nacimiento</label>
                                    <div class="controls">
                                        <input id="full-name" value="${fechanac}" name="fecha_nac_el" type="text" class="datepicker input-xlarge" disabled>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Dirección de nacimiento</label>
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getLugarNac()}" name="lugar_nac_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Departamento de nacimiento</label>
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getDepaNac()}" name="depa_nac_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">País de nacimiento</label>
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getPaisNac()}" name="pais_nac_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-2">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="TipoDoc" id="optionsRadios1" value="D" ${sol.getTipoDoc() == 'D' || sol.getTipoDoc() == null ? 'checked' : ''}>DNI</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="TipoDoc" id="optionsRadios2" value="C" ${sol.getTipoDoc() == 'C' ? 'checked' : ''}>Carnet de Extranjería</label>
                                        </div>                            
                                    </div>
                                </div>   
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <label class="control-label">N° de Documento</label>
                                        <input id="n_doc" value="${sol.getNDoc()}" name="n_doc_el" type="text" class="input-xlarge">
                                    </div>
                                </div>                            
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Domicilio (Consignar dirección exacta)</label>
                                    <div class="controls">
                                        <textarea class="input-xlarge" name="domicilio" rows="3">${domicilio}</textarea>
                                    </div>
                                </div>
                                <p>*En caso de parejas, consignar el domicilio de residencia de ambos</p>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Teléfono </label>
                                    <div class="controls">
                                        <input id="full-name" value="${fijo}" name="telefono" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <p>*En caso de parejas, consignar el telefono fijo de ambos</p>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Celular</label>
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getCelular()}" name="celular_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Correo Electrónico</label>
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getCorreo()}" name="correo_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Estado Civil</h3>
                                <c:choose>
                                    <c:when test="${estCivil == 'N'}">
                                        <div class="row">
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <input type="radio" name="estCivil" id="soltero" value="soltera">Soltero</label>
                                                </div>
                                            </div>   
                                            <div class="col-md-3">   
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="estCivil" id="casado" value="casados" checked>Casado</label>
                                                </div>                            
                                            </div>
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="estCivil" id="viudo" value="viuda">Viudo</label>
                                                </div>
                                            </div> 
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="estCivil" id="divorciado" value="divorciada">Divorciado</label>
                                                </div>
                                            </div> 
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="row">
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${estCivil == 's'}">
                                                                <input type="radio" name="estCivil" id="soltero" value="soltero" checked>Soltero</label>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <input type="radio" name="estCivil" id="soltero" value="soltero">Soltero</label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>   
                                            <div class="col-md-3">   
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${estCivil == 'c'}">
                                                                <input type="radio" name="estCivil" id="casado" value="casados" checked>Casado</label>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <input type="radio" name="estCivil" id="casado" value="casados">Casado</label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>                            
                                            </div>
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${estCivil == 'v'}">
                                                                <input type="radio" name="estCivil" id="viudo" value="viudo" checked>Viudo</label>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <input type="radio" name="estCivil" id="viudo" value="viudo">Viudo</label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div> 
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${estCivil == 'd'}">
                                                                <input type="radio" name="estCivil" id="divorciado" value="divorciado" checked>Divorciado</label>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <input type="radio" name="estCivil" id="divorciado" value="divorciado">Divorciado</label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div> 
                                        </div>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">Fecha de matrimonio Civil </label>
                                            <div class="controls">
                                                <input id="fecha_matrimonio" value="${fechaMatri}" name="fechaMatri" type="text" class="datepicker input-xlarge">
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <br>
                                <h3><strong>Educación, Ocupación e Ingresos Económicos</strong></h3>
                                <br>
                                <h3>Nivel de instrucción alcanzado</h3>
                                <c:choose>
                                    <c:when test="${sol.getNivelInstruccion() == null}">
                                        <div class="row">
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="nivel_inst_el" id="optionsRadios1" value="Ninguno" checked>Ninguno</label>
                                                </div>
                                            </div>   
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="nivel_inst_el" id="optionsRadios2" value="Primaria">Primaria</label>
                                                </div>
                                            </div>   
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="nivel_inst_el" id="optionsRadios3" value="Secundaria">Secundaria</label>
                                                </div>
                                            </div> 
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="nivel_inst_el" id="optionsRadios4" value="Tecnico">Técnico</label>
                                                </div>
                                            </div> 
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="nivel_inst_el" id="optionsRadios5" value="Superior">Superior</label>
                                                </div>
                                            </div> 
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <input type="radio" name="nivel_inst_el" id="optionsRadios6" value="Otros">Otros</label>
                                                </div>
                                            </div>   
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="row">
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${sol.getNivelInstruccion() == 'Ninguno'}">
                                                                <input type="radio" name="nivel_inst_el" id="optionsRadios1" value="Ninguno" checked>Ninguno</label>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <input type="radio" name="nivel_inst_el" id="optionsRadios1" value="Ninguno">Ninguno</label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>   
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${sol.getNivelInstruccion() == 'Primaria'}">
                                                                <input type="radio" name="nivel_inst_el" id="optionsRadios2" value="Primaria" checked>Primaria</label>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <input type="radio" name="nivel_inst_el" id="optionsRadios2" value="Primaria">Primaria</label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>   
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${sol.getNivelInstruccion() == 'Secundaria'}">
                                                                <input type="radio" name="nivel_inst_el" id="optionsRadios3" value="Secundaria" checked>Secundaria</label>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <input type="radio" name="nivel_inst_el" id="optionsRadios3" value="Secundaria">Secundaria</label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div> 
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${sol.getNivelInstruccion() == 'Tecnico'}">
                                                                <input type="radio" name="nivel_inst_el" id="optionsRadios4" value="Tecnico" checked>Técnico</label>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <input type="radio" name="nivel_inst_el" id="optionsRadios4" value="Tecnico">Técnico</label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div> 
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${sol.getNivelInstruccion() == 'Superior'}">
                                                                <input type="radio" name="nivel_inst_el" id="optionsRadios5" value="Superior" checked>Superior</label>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <input type="radio" name="nivel_inst_el" id="optionsRadios5" value="Superior">Superior</label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div> 
                                            <div class="col-md-3">  
                                                <div class="radio">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${sol.getNivelInstruccion() == 'Otros'}">
                                                                <input type="radio" name="nivel_inst_el" id="optionsRadios6" value="Otros" checked>Otros</label>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <input type="radio" name="nivel_inst_el" id="optionsRadios6" value="Otros">Otros</label>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </div>   
                                        </div> 
                                    </c:otherwise>
                                </c:choose>
                                <br>
                                <h3>Culminó el nivel de instrucción señalado</h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="culm_nivel_el" id="optionsRadios1" value="0" ${sol.getCulminoNivel() == 0 || sol.getCulminoNivel() == null ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="culm_nivel_el" id="optionsRadios2" value="1" ${sol.getCulminoNivel() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>  
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Profesión u Oficio </label>
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getProfesion()}" name="prof_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Trabajador dependiente (Elegir solo si corresponde)</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" onclick="dependiente(this.value)" name="Trabajador_Depend_el" id="optionsRadios1" value="0" ${sol.getTrabajadorDepend() == 0 || sol.getTrabajadorDepend() == null ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" onclick="dependiente(this.value)" name="Trabajador_Depend_el" id="optionsRadios2" value="1" ${sol.getTrabajadorDepend() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ocupación Actual</label>
                                    <div class="controls">
                                        <input id="ocup_actual" value="${sol.getOcupActualDep()}" name="ocup_act_dep_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Centro de Trabajo </label>
                                    <div class="controls">
                                        <input id="centro_trabajo" value="${sol.getCentroTrabajo()}" name="centro_trabajo_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Dirección del centro de trabajo</label>
                                    <div class="controls">
                                        <input id="direccion_centro" value="${sol.getDireccionCentro()}" name="dir_centro_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Teléfono del centro de trabajo </label>
                                    <div class="controls">
                                        <input id="telefono_centro" value="${sol.getTelefonoCentro()}" name="tel_centro_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ingreso mensual sustentable (sueldo bruto) </label>
                                    <div class="controls">
                                        <input id="ingreso_dep" value="${sol.getIngresoDep()}" name="ingreso_dep_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <p style="color: red">${mensaje_ing_dep}</p>
                                <br>
                                <h3>Trabajadora independiente (Elegir solo si corresponde)</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" onclick="independiente(this.value)" name="Trabajador_Indep_el" id="optionsRadios1" value="0" ${sol.getTrabajadorIndepend() == 0 || sol.getTrabajadorIndepend() == null ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" onclick="independiente(this.value)" name="Trabajador_Indep_el" id="optionsRadios2" value="1" ${sol.getTrabajadorIndepend() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ocupación Actual</label>
                                    <div class="controls">
                                        <input id="ocup_actual_in" value="${sol.getOcupActualInd()}" name="ocup_act_indep_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Ingreso mensual sustentable</label>
                                    <div class="controls">
                                        <input id="ingreso_indep" value="${sol.getIngresoIndep()}" name="ingreso_ind_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <p style="color: red">${mensaje_ing_indep}</p>
                                <br>
                                <br>
                                <h3><strong>Salud y Previsión</strong></h3>
                                <br>
                                <h3>Seguro de salud</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="seguro_salud_el" id="optionsRadios1" value="0" ${sol.getSeguroSalud() == 0 || sol.getSeguroSalud() == null ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="seguro_salud_el" id="optionsRadios2" value="1" ${sol.getSeguroSalud() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Tipo de Seguro</label>
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getTipoSeguro()}" name="tipo_seguro" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <h3>Seguro de vida</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="seguro_vida_el" id="optionsRadios1" value="0" ${sol.getSeguroVida() == 0 || sol.getSeguroVida() == null ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="seguro_vida_el" id="optionsRadios2" value="1" ${sol.getSeguroVida() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <h3>Afiliado al sistema de pensiones</h3>
                                <div class="row"> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="sist_pen_el" id="optionsRadios1" value="0" ${sol.getSistPensiones() == 0 || sol.getSistPensiones() == null ? 'checked' : ''}>Si</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="sist_pen_el" id="optionsRadios2" value="1" ${sol.getSistPensiones() == 1 ? 'checked' : ''}>No</label>
                                        </div>
                                    </div>
                                </div> 
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Estado de salud actual</label>
                                    <div class="controls">
                                        <input id="full-name" value="${sol.getSaludActual()}" name="est_salud_el" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <p style="color: red">IMPORTANTE: FAVOR DE LLENAR TODOS LOS CAMPOS</p>
                                <br>
                                <p>Según lo establecido en la Ley Nº29733 - Ley de protección de datos personales, la información proporcionada por 
                                    usted/ustedes será de uso exclusivo, con la reserva y confidencialidad del caso, para los fines concernientes al 
                                    procedimiento administrativo de adopción.
                                </p>    
                                <!-- Button -->
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar Cambios</button>
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
            <script>
                function dependiente(value) {
                    var ocup_actual = document.getElementById("ocup_actual");
                    var centro_trabajo = document.getElementById("centro_trabajo");
                    var direccion_centro = document.getElementById("direccion_centro");
                    var telefono_centro = document.getElementById("telefono_centro");
                    var ingreso_dep = document.getElementById("ingreso_dep");

                    if (value == '1') {
                        ocup_actual.disabled = true;
                        centro_trabajo.disabled = true;
                        direccion_centro.disabled = true;
                        telefono_centro.disabled = true;
                        ingreso_dep.disabled = true;
                    } else {
                        ocup_actual.disabled = false;
                        centro_trabajo.disabled = false;
                        direccion_centro.disabled = false;
                        telefono_centro.disabled = false;
                        ingreso_dep.disabled = false;
                    }
                }
            </script>
            <script>
                function independiente(value) {
                    var ocup_actual_in = document.getElementById("ocup_actual_in");
                    var ingreso_indep = document.getElementById("ingreso_indep");

                    if (value == '1') {
                        ocup_actual_in.disabled = true;
                        ingreso_indep.disabled = true;
                    } else {
                        ocup_actual_in.disabled = false;
                        ingreso_indep.disabled = false;
                    }
                }
            </script>
            <!-- Ubicar al final -->
    </body>
</html>