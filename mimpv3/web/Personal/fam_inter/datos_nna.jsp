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
                                if (u.getRol().equals("admin") || u.getRol().equals("DCRI")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de organismo acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de autoridad central</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-8">
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <br>
                        <h1 align="center"><strong>Datos de la familia internacional</strong></h1>
                        <br>                      
                        <ul class="nav nav-tabs row" >
                            <li><a href="${pageContext.servletContext.contextPath}/laSolicitanteInt" >La Solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/elSolicitanteInt" >El solicitante</a></li>
                            <!-- <li><a href="#" data-toggle="tab">Composición familiar</a></li> -->
                            <!-- <li><a href="#" data-toggle="tab">Vivienda</a></li> -->
                            <!--<li><a href="#" data-toggle="tab">Proceso de adopción</a></li> -->
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/antNnaInt" >Antecedentes del niño, niña o adolescente</a></li>
                        </ul>

                        <form role="form" action="${pageContext.servletContext.contextPath}/ActualizarInfoFamiliaInt" method="post" name="formulario" onsubmit="return(validar());"> 
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
                                                    <select id="incesto" name="incesto" >
                                                            <option value="0" ${infoFam.getNnaIncesto() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaIncesto() != 0 ? 'selected' : ''} >NO</option>
                                                        </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Padres con enfermedad psiquiátrica (esquizofrenia, paranoia, etc</h4>
                                                </td>
                                                <td>
                                                    <select id="mental" name="mental" >
                                                            <option value="0" ${infoFam.getNnaMental() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaMental() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Padres con epilepsia</h4>
                                                </td>
                                                <td>
                                                    <select id="epilepsia" name="epilepsia" >
                                                            <option value="0" ${infoFam.getNnaEpilepsia() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaEpilepsia() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niña, niño o adolescente víctima de abuso sexual</h4>
                                                </td>
                                                <td>
                                                    <select id="abuso" name="abuso" >
                                                            <option value="0" ${infoFam.getNnaAbuso() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaAbuso() != 0 ? 'selected' : ''} >NO</option>
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
                                                            <option value="0" ${infoFam.getNnaSifilis() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaSifilis() != 0 ? 'selected' : ''} >NO</option>
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
                                                    <select id="seguimiento" name="seguimiento" >
                                                            <option value="0" ${infoFam.getNnaSeguiMedico() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaSeguiMedico() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Condiciones  de  salud  que  requieran  intervención  quirúrgica  menor 
                                                        (labio leporino, estrabismo, etc.)</h4>
                                                </td>
                                                <td>
                                                    <select id="operacion" name="operacion" >
                                                            <option value="0" ${infoFam.getNnaOperacion() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaOperacion() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niña,  niño  o  adolescente  con  trastorno  de  déficit  de  atención  e 
                                                        hiperactividad (TDAH)</h4>
                                                </td>
                                                <td>
                                                    <select id="hiperactivo" name="hiperactivo">
                                                            <option value="0" ${infoFam.getNnaHiperactivo() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaHiperactivo() != 0 ? 'selected' : ''} >NO</option>
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
                                                    <select id="especial" name="especial">
                                                            <option value="0" ${infoFam.getNnaEspecial() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaEspecial() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niñas, niños y adolescentes con problemas de salud </h4>
                                                </td>
                                                <td>
                                                    <select id="salud" name="salud">
                                                            <option value="0" ${infoFam.getNnaEnfermo() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaEnfermo() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niñas y niños mayores (A partir de 09 años)</h4>
                                                </td>
                                                <td>
                                                    <select id="mayor" name="mayor">
                                                            <option value="0" ${infoFam.getNnaMayor() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaMayor() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Adolescentes (De 12 años hasta 17 años 11 meses)</h4>
                                                </td>
                                                <td>
                                                    <select id="adolescente" name="adolescente">
                                                            <option value="0" ${infoFam.getNnaAdolescente() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaAdolescente() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Grupos de hermanos </h4>
                                                </td>
                                                <td>
                                                    <select id="hermanos" name="hermanos">
                                                            <option value="0" ${infoFam.getNnaHermano() == 0 ? 'selected' : ''} >SI</option>
                                                            <option value="1" ${infoFam.getNnaHermano() != 0 ? 'selected' : ''} >NO</option>
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
                                            <input type="radio" name="viajar" id="viajarSi" value="0" ${infoFam.getPuedeViajar() == 0 ? 'checked' : ''} >Si</label>
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="viajar" id="viajarNo" value="1" ${infoFam.getPuedeViajar() == 1 ? 'checked' : ''} >No</label>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <h3><strong>Espectativas de Edad</strong></h3>
                            <br>
                            <div class="control-group">
                                <label class="control-label">¿Cuántos años podría tener como mínimo el niño, niña o adolescente a adoptar?</label>
                                <div class="controls">
                                    <input id="edadMin" name="edadMin" type="text" value="${infoFam.getExpectativaEdadMin()}" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <label class="control-label">¿Cuántos años podría tener como máximo el niño, niña o adolescente a adoptar?</label>
                                <div class="controls">
                                    <input id="edadMax" name="edadMax" type="text" value="${infoFam.getExpectativaEdadMax()}" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <p><strong>Género del niño, niña o adolescente a adoptar</strong></p>
                            <div class="row">
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="genero" id="masculino" value="masculino" ${infoFam.getExpectativaGenero() == 'masculino' ? 'checked' : ''} >Masculino</label>
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="genero" id="femenino" value="femenino" ${infoFam.getExpectativaGenero() == 'femenino' ? 'checked' : ''} >Femenino</label>
                                    </div>
                                </div>
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="genero" id="indistinto" value="indistinto" ${infoFam.getExpectativaGenero() == 'indistinto' ? 'checked' : ''} >Indistinto</label>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="control-group">
                                <div class="controls">
                                    <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
                                </div>
                            </div>
                        </form>
                        <br>
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
            <!-- core JavaScript
        ================================================== -->
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
            <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap-datepicker.js"></script>
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/locales/bootstrap-datepicker.es.js"></script>
            <script type="text/javascript">

                $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

            </script>
            <script type="text/javascript">
            function validar()
            {
            var numericExpression = /^[0-9]+$/;
            if(!document.getElementById('viajarSi').checked && !document.getElementById('viajarNo').checked){
                alert( "Debe elegir al menos una opción de viaje" );
                document.formulario.viajarSi.focus() ;
                return false;
            }
            if(!document.formulario.edadMin.value.match(numericExpression))
            {
                
                alert( "El campo debe contener solo números" );
                document.formulario.edadMin.focus() ;
                return false;
            }
            if(!document.formulario.edadMax.value.match(numericExpression))
            {
                
                alert( "El campo debe contener solo números" );
                document.formulario.edadMax.focus() ;
                return false;
            }
            if(!document.getElementById('masculino').checked && !document.getElementById('femenino').checked && !document.getElementById('indistinto').checked){
                alert( "Debe elegir al menos una opción de género" );
                document.formulario.masculino.focus() ;
                return false;
                
            }
            return true
            }
          </script>
            <!-- Ubicar al final -->
    </body>
</html>