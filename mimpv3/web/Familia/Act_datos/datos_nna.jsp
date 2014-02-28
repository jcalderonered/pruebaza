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
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inicioFam'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc1" data-toggle="tab">La Solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc2" data-toggle="tab">El solicitante</a></li>
                            <!--<li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc3" data-toggle="tab">Composición familiar</a></li>-->
                            <!-- <li><a href="${pageContext.servletContext.contextPath}/FactDatos/opc4" data-toggle="tab">Vivienda</a></li> -->
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/FactDatos/opc5" data-toggle="tab">Antecedentes del niño, niña o adolescente</a></li>
                        </ul>

                        <form class="form-horizontal"> 
                            <br>
                            <h3><strong>Antecedentes, condiciones de salud y desarrollo del niño, niña o adolescente a adoptar(NNA)</strong></h3>
                            <br>
                            <br>
                            <!-- Text input-->
                            <div class="row">
                                <div id="tabla_fam" class="table-responsive">
                                    <table id="hijos" class="table table-bordered table-striped ">
                                        <thead>
                                            <tr>
                                                <th>Antecedentes</th>
                                                <th>Acepta</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <h4>Nacido(a) como consecuencia del incesto</h4>
                                                </td>
                                                <td>
                                                    <select disabled id="incesto" name="incesto" >
                                                        <option value="0" ${ifa.getNnaIncesto() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaIncesto() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Padres con enfermedad psiquiátrica (esquizofrenia, paranoia, etc</h4>
                                                </td>
                                                <td>
                                                    <select disabled id="mental" name="mental" >
                                                        <option value="0" ${ifa.getNnaMental() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaMental() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Padres con epilepsia</h4>
                                                </td>
                                                <td>
                                                    <select disabled id="epilepsia" name="epilepsia" >
                                                        <option value="0" ${ifa.getNnaEpilepsia() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaEpilepsia() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niña, niño o adolescente víctima de abuso sexual</h4>
                                                </td>
                                                <td>
                                                    <select disabled id="abuso" name="abuso" >
                                                        <option value="0" ${ifa.getNnaAbuso() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaAbuso() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niña, niño o adolescente adolescente actualmente sano, que al nacer fue 
                                                        diagnosticado/a preliminarmente con sífilis congénita.</h4>
                                                </td>
                                                <td>
                                                    <select disabled id="sifilis" name="sifilis" >
                                                        <option value="0" ${ifa.getNnaSifilis() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaSifilis() != 0 ? 'selected' : ''} >NO</option>
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
                                                <th>Acepta</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <h4>Salud  estable  que  requiere  seguimiento  médico  (soplo,  hipotiroidismo, 
                                                        desnutrición crónica, etc.)</h4>
                                                </td>
                                                <td>
                                                    <select disabled id="seguimiento" name="seguimiento" >
                                                        <option value="0" ${ifa.getNnaSeguiMedico() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaSeguiMedico() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Condiciones  de  salud  que  requieran  intervención  quirúrgica  menor 
                                                        (labio leporino, estrabismo, etc.)</h4>
                                                </td>
                                                <td>
                                                    <select disabled id="operacion" name="operacion" >
                                                        <option value="0" ${ifa.getNnaOperacion() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaOperacion() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niña,  niño  o  adolescente  con  trastorno  de  déficit  de  atención  e 
                                                        hiperactividad (TDAH)</h4>
                                                </td>
                                                <td>
                                                    <select disabled id="hiperactivo" name="hiperactivo">
                                                        <option value="0" ${ifa.getNnaHiperactivo() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaHiperactivo() != 0 ? 'selected' : ''} >NO</option>
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
                                                <th>¿Se  siente/n  preparado/a/s  para  asumir  la  adopción  de  una 
                                                    niña, niño o adolescente de adopciones prioritarias?:</th>
                                                <th>Acepta</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <h4>Niñas, niños y adolescentes con necesidades especiales</h4>
                                                </td>
                                                <td>
                                                    <select disabled id="especial" name="especial">
                                                        <option value="0" ${ifa.getNnaEspecial() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaEspecial() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niñas, niños y adolescentes con problemas de salud </h4>
                                                </td>
                                                <td>
                                                    <select disabled id="salud" name="salud">
                                                        <option value="0" ${ifa.getNnaEnfermo() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaEnfermo() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niñas y niños mayores (A partir de 09 años)</h4>
                                                </td>
                                                <td>
                                                    <select disabled id="mayor" name="mayor">
                                                        <option value="0" ${ifa.getNnaMayor() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaMayor() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Adolescentes (De 12 años hasta 17 años 11 meses)</h4>
                                                </td>
                                                <td>
                                                    <select disabled id="adolescente" name="adolescente">
                                                        <option value="0" ${ifa.getNnaAdolescente() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaAdolescente() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Grupos de hermanos </h4>
                                                </td>
                                                <td>
                                                    <select disabled id="hermanos" name="hermanos">
                                                        <option value="0" ${ifa.getNnaHermano() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${ifa.getNnaHermano() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <br>
                            <h3><strong>Adopción fuera del lugar de residencia de los solicitantes</strong></h3>
                            <br>
                            <br>
                            <h3>En caso de ser designado/a para la adopción de un NNA que resida en una ciudad diferente 
                                a la suya, ¿tendría disponibilidad para viajar? </h3>
                            <br>
                            <div class="row">
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input disabled type="radio" name="viajar" id="viajarSi" value="0" ${ifa.getPuedeViajar() == 0 ? 'checked' : ''} >Si</label>
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input disabled type="radio" name="viajar" id="viajarNo" value="1" ${ifa.getPuedeViajar() == 1 ? 'checked' : ''} >No</label>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <h3><strong>Espectativas de Edad</strong></h3>
                            <br>
                            <div class="control-group">
                                <label class="control-label">¿Cuántos años podría tener como mínimo el niño, niña o adolescente a adoptar?</label>
                                <div class="controls">
                                    <input disabled id="edadMin" name="edadMin" type="text" value="${ifa.getExpectativaEdadMin()}" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">¿Cuántos años podría tener como máximo el niño, niña o adolescente a adoptar?</label>
                                <div class="controls">
                                    <input disabled id="edadMax" name="edadMax" type="text" value="${ifa.getExpectativaEdadMax()}" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <p><strong>Género del niño, niña o adolescente a adoptar</strong></p>
                            <div class="row">
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input disabled type="radio" name="genero" id="optionsRadios1" value="masculino" ${ifa.getExpectativaGenero() == 'masculino' ? 'checked' : ''} >Masculino</label>
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input disabled type="radio" name="genero" id="optionsRadios1" value="femenino" ${ifa.getExpectativaGenero() == 'femenino' ? 'checked' : ''} >Femenino</label>
                                    </div>
                                </div>
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input disabled type="radio" name="genero" id="optionsRadios1" value="indistinto" ${ifa.getExpectativaGenero() == 'indistinto' ? 'checked' : ''} >Indistinto</label>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <!-- Button -->
                            <br>
                            <p style="color: red">IMPORTANTE: SI DESEA REALIZAR ALGÚN CAMBIO DEBERÁ PRESENTAR UNA SOLICITUD</p>
                            <p style="color: red">EN MESA DE PARTES DE LA DGA</p>
                            <br>
                            <p>Según lo establecido en la Ley Nº29733 - Ley de protección de datos personales, la información proporcionada por 
                                usted/ustedes será de uso exclusivo, con la reserva y confidencialidad del caso, para los fines concernientes al 
                                procedimiento administrativo de adopción.
                            </p>
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
    </body>
</html>