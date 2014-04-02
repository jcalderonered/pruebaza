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
                              <%if (!u.getRol().equals("DEIA Prio") && !u.getRol().equals("UA")) {%>
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
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/reg'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <form role="form" action="${pageContext.servletContext.contextPath}/ActualizarVivienda" method="post" name="formulario" onsubmit="return(validar());">

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
                                <li ${estado == 'formativa' || estado == 'evaluacion' || estado == 'espera' || estado == 'designacion' || estado == 'adopcion' || estado == 'reevaluacion' ? 'class="hidden"' : ''} ><a href="${pageContext.servletContext.contextPath}/nnaAsociado" >NNA Adoptado</a></li>
                                <li><a href="${pageContext.servletContext.contextPath}/atenciones" >Atenciones</a></li>
                            </ul>
                            <br>
                            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                            <fieldset>
                                <br>
                                <h3><strong>Vivienda</strong></h3>
                                <br>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input onclick="Vivienda()" type="radio" name="propiedadVivienda" id="propia" value="Propia" ${infoFam.getPropiedadVivienda() == 'Propia'  ?  'checked' : ''} >Propia</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input onclick="Vivienda()" type="radio" name="propiedadVivienda" id="alquilada" value="Alquilada" ${infoFam.getPropiedadVivienda() == 'Alquilada'  ?  'checked' : ''} >Alquilada</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input onclick="Vivienda()" type="radio" name="propiedadVivienda" id="otro" value="Otros" ${infoFam.getPropiedadVivienda() != 'Propia' && infoFam.getPropiedadVivienda() != 'Alquilada'  ?  'checked' : ''}>Otros</label>
                                            <br>
                                            <input value="${infoFam.getPropiedadVivienda() != 'Propia' && infoFam.getPropiedadVivienda() != 'Alquilada' ? infoFam : ''}" id="propiedadDescrip" name="propiedadDescrip" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <br>
                                <h3><strong>Tipo</strong></h3>
                                <br>
                                <!-- Text input-->
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input onclick="Tipo()" type="radio" name="tipoVivienda" id="casa" value="Casa" ${infoFam.getTipoVivienda() == 'Casa'  ?  'checked' : ''}>Casa</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input onclick="Tipo()" type="radio" name="tipoVivienda" id="depa" value="Departamento" ${infoFam.getTipoVivienda() == 'Departamento'  ?  'checked' : ''}>Departamento</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input onclick="Tipo()" type="radio" name="tipoVivienda" id="otro" value="Otros" ${infoFam.getTipoVivienda() != 'Casa' && infoFam.getTipoVivienda() != 'Departamento' ?  'checked' : ''}>Otros(especificar)</label>
                                            <br>

                                            <input value="${infoFam.getTipoVivienda() != 'Casa' && infoFam.getTipoVivienda() != 'Departamento' ?  infoFam.getTipoVivienda() : ''}" id="tipoDescrip" name="tipoDescrip" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <br>
                                <br>
                                <h3><strong>Domicilio</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Dirección (Consignar dirección exacta)</label>
                                    <div class="controls">
                                        <textarea id="domicilio" class="input-xlarge" name="domicilio" rows="3" >${infoFam.getDomicilio()}</textarea>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Departamento de Residencia</label>
                                    <div class="controls">
                                        <input id="DepRes" name="DepRes" value="${infoFam.getDepRes()}" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">País de Residencia</label>
                                    <div class="controls">
                                        <input id="PaisRes" name="PaisRes" value="${infoFam.getPaisRes()}" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Teléfono</label>
                                    <div class="controls">
                                        <input id="telefono" name="telefono" value="${infoFam.getTelefono()}" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <br>
                                <h3><strong>Área de vivienda(en metros cuadrados)</strong></h3>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Total</label>
                                    <div class="controls">
                                        <input id="areaVivTotal" name="areaVivTotal" value="${infoFam.getAreaVivTotal()}" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Construida</label>
                                    <div class="controls">
                                        <input id="areaVivConst" name="areaVivConst" value="${infoFam.getAreaVivConst()}" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Distribución de la vivienda</label>
                                    <div class="controls">
                                        <textarea id="distViv" class="input-xlarge" name="distViv" placeholder="" rows="3" >${infoFam.getDistVivienda()}</textarea>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <h3><strong>Servicios</strong></h3>
                                <br>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" name="luz" id="luz" value="0" ${infoFam.getLuz() == 0  ?  'checked' : ''}>Energía Eléctrica</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">    
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" name="agua" id="agua" value="0" ${infoFam.getAgua() == 0  ?  'checked' : ''}>Agua Potable</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">    
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" name="desague" id="desague" value="0" ${infoFam.getDesague() == 0  ?  'checked' : ''}>Desague</label>
                                        </div>
                                    </div>
                                    <div class="col-md-3">   
                                        <div class="control-group">
                                            <label class="control-label">Otros servicios</label>
                                            <div class="controls">
                                                <input id="otrosServ" name="otrosServ" value="${infoFam.getOtrosServ()}" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <h3><strong>Material de construcción</strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input onclick="Material()" type="radio" name="materConst" id="noble" value="Noble" ${infoFam.getMaterConst() == 'Noble' ? 'checked' : ''}>Noble</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input onclick="Material()" type="radio" name="materConst" id="otros" value="Otros" ${infoFam.getMaterConst() != 'Noble' ? 'checked' : ''}>Otros</label>
                                            <br>

                                            <input value="${infoFam.getMaterConst() != 'Noble' ?  infoFam.getMaterConst() : ''}" id="materConstDesc" name="materConstDesc" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>

                                <h3><strong>Paredes</strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input onclick="Paredes()" type="radio" name="pared" id="pared" value="Ladrillo" ${infoFam.getPared() == 'Ladrillo' ? 'checked' : ''}>Ladrillo</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input onclick="Paredes()" type="radio" name="pared" id="otro" value="Otros" ${infoFam.getPared() != 'Ladrillo' ? 'checked' : ''}>Otros</label>
                                            <br>

                                            <input value="${infoFam.getPared() != 'Ladrillo' ?  infoFam.getPared() : ''}" id="paredDesc" name="paredDesc" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
                                <h3><strong>Techo</strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input onclick="Techo()" type="radio" name="techo" id="techo" value="Concreto" ${infoFam.getTecho() == 'Concreto' ? 'checked' : ''}>Concreto</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input onclick="Techo()" type="radio" name="techo" id="otro" value="Otros" ${infoFam.getTecho() != 'Concreto' ? 'checked' : ''}>Otros</label>
                                            <br>
                                            <input value="${infoFam.getTecho() != 'Concreto' ?  infoFam.getTecho() : ''}" id="techoDesc" name="techoDesc" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>

                                <h3><strong>Piso</strong></h3>
                                <div class="row">
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input onclick="Piso()" type="radio" name="piso" id="piso" value="Cemento" ${infoFam.getPiso() == 'Cemento' ? 'checked' : ''}>Cemento</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input onclick="Piso()" type="radio" name="piso" id="otro" value="Otros" ${infoFam.getPiso() != 'Cemento' ? 'checked' : ''}>Otros</label>
                                            <br>

                                            <input value="${infoFam.getPiso() != 'Cemento' ?  infoFam.getPiso() : ''}" id="pisoDesc" name="pisoDesc" type="text" class="input-xlarge">
                                        </div>                            
                                    </div>
                                </div>
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

            </script>
            <script>
                function Vivienda()
                {
                    var vivDesc = document.getElementById('propiedadDescrip');
                    if (document.getElementById('propia').checked || document.getElementById('alquilada').checked) {
                        vivDesc.disabled = true;
                    } else {
                        vivDesc.disabled = false;
                    }
                }
            </script>    

            <script>
                function Tipo()
                {
                    var tipoDesc = document.getElementById('tipoDescrip');
                    if (document.getElementById('casa').checked || document.getElementById('depa').checked) {
                        tipoDesc.disabled = true;
                    } else {
                        tipoDesc.disabled = false;
                    }
                }
            </script>    

            <script>
                function Material()
                {
                    var matDesc = document.getElementById('materConstDesc');
                    if (document.getElementById('noble').checked) {
                        matDesc.disabled = true;
                    } else {
                        matDesc.disabled = false;
                    }
                }
            </script>    

            <script>
                function Paredes()
                {
                    var paredDesc = document.getElementById('paredDesc');
                    if (document.getElementById('pared').checked) {
                        paredDesc.disabled = true;
                    } else {
                        paredDesc.disabled = false;
                    }
                }
            </script>    

            <script>
                function Techo()
                {
                    var techoDesc = document.getElementById('techoDesc');
                    if (document.getElementById('techo').checked) {
                        techoDesc.disabled = true;
                    } else {
                        techoDesc.disabled = false;
                    }
                }
            </script>    

            <script>
                function Piso()
                {
                    var pisoDesc = document.getElementById('pisoDesc');
                    if (document.getElementById('piso').checked) {
                        pisoDesc.disabled = true;
                    } else {
                        pisoDesc.disabled = false;
                    }
                }
            </script>    
            <script type="text/javascript">
                function funct() {

                    var vivDesc = document.getElementById('propiedadDescrip');
                    if (document.getElementById('propia').checked || document.getElementById('alquilada').checked) {
                        vivDesc.disabled = true;
                    } else {
                        vivDesc.disabled = false;
                    }

                    var tipoDesc = document.getElementById('tipoDescrip');
                    if (document.getElementById('casa').checked || document.getElementById('depa').checked) {
                        tipoDesc.disabled = true;
                    } else {
                        tipoDesc.disabled = false;
                    }

                    var matDesc = document.getElementById('materConstDesc');
                    if (document.getElementById('noble').checked) {
                        matDesc.disabled = true;
                    } else {
                        matDesc.disabled = false;
                    }

                    var paredDesc = document.getElementById('paredDesc');
                    if (document.getElementById('pared').checked) {
                        paredDesc.disabled = true;
                    } else {
                        paredDesc.disabled = false;
                    }

                    var techoDesc = document.getElementById('techoDesc');
                    if (document.getElementById('techo').checked) {
                        techoDesc.disabled = true;
                    } else {
                        techoDesc.disabled = false;
                    }

                    var pisoDesc = document.getElementById('pisoDesc');
                    if (document.getElementById('piso').checked) {
                        pisoDesc.disabled = true;
                    } else {
                        pisoDesc.disabled = false;
                    }
                }

            </script>
            <script type="text/javascript">
                function validar()
                {
                    var numericExpression = /^[0-9]+$/;
                    if (!document.formulario.areaVivTotal.value.match(numericExpression))
                    {
                        alert("El campo debe contener solo números");
                        document.formulario.areaVivTotal.focus();
                        return false;
                    }
                    if (!document.formulario.areaVivConst.value.match(numericExpression))
                    {

                        alert("El campo debe contener solo números");
                        document.formulario.areaVivConst.focus();
                        return false;
                    }
                    return true
                }
            </script>
            <!-- Ubicar al final -->
    </body>
</html>