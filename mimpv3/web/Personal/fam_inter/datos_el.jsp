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
                                if (u.getRol().equals("admin") || u.getRol().equals("DEIA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/organismo"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de organismo acreditado </a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/autoridad"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de autoridad central</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/reporte"><span class="glyphicon glyphicon-chevron-right"></span> Reportes</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/password"><span class="glyphicon glyphicon-chevron-right"></span> Cambio contraseña</a></li>    
                        </ul>
                    </div>
                    <div class="col-md-8">
                        <!-- <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  -->
                        <br>
                        <h1 align="center"><strong>Datos de la familia internacional</strong></h1>                         
                        <br>
                        <ul class="nav nav-tabs row"  >
                            <li><a href="${pageContext.servletContext.contextPath}/laSolicitanteInt" >La Solicitante</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/elSolicitanteInt" >El solicitante</a></li>
                            <!-- <li><a href="#" data-toggle="tab">Composición familiar</a></li> -->
                            <!-- <li><a href="#" data-toggle="tab">Vivienda</a></li> -->
                            <!--<li><a href="#" data-toggle="tab">Proceso de adopción</a></li> -->
                            <li><a href="${pageContext.servletContext.contextPath}/antNnaInt" >Antecedentes del niño, niña o adolescente</a></li>
                        </ul>

                        <form role="form" action="${pageContext.servletContext.contextPath}/ActualizarAdoptanteInt" method="post" name="formulario" onsubmit="return(validar());"> 
                            <br>
                            <input hidden id="adoptante" name="adoptante" value="el">
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
                                                <input type="radio" name="estadoCivil" id="soltera" value="soltero" ${infoFam.getEstadoCivil() == 'soltero' ? 'checked' : ''}>Soltero</label>
                                        </div>
                                    </div>   
                                    <div class="col-md-3">   
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="casada" value="casados" ${infoFam.getEstadoCivil() == 'casados' ? 'checked' : ''}>Casado</label>
                                            <br>
                                            <label class="control-label">Fecha de matrimonio Civil</label>
                                            <input ${infoFam.getEstadoCivil() == null || infoFam.getEstadoCivil() == 'casados' ? '' : 'disabled'} value="${infoFam.getFechaMatrimonio() != null ? df.dateToStringNumeros(infoFam.getFechaMatrimonio()) : ''}" id="fechaMat" name="fechaMat" type="text" class="datepicker input-xlarge">
                                        </div>                            
                                    </div>
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="viuda" value="viudo" ${infoFam.getEstadoCivil() == 'viudo' ? 'checked' : ''}>Viudo</label>
                                        </div>
                                    </div> 
                                    <div class="col-md-3">  
                                        <div class="radio">
                                            <label>
                                                <input type="radio" name="estadoCivil" id="divorciada" value="divorciado" ${infoFam.getEstadoCivil() == 'divorciado' ? 'checked' : ''}>Divorciado</label>
                                        </div>
                                    </div> 
                                </div>    
                                <!-- Aquí va lo de educación,ocupación,e ingresos económicos y tmb lo de Salud y previsión 
                                -->
                                
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <button  type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
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
                
            $('#fechaNac').on('changeDate', function (ev) {
                    
                    var nac =  document.getElementById("fechaNac").value;
                    var edad =  document.getElementById("edad");
                    
                    var today = new Date();
                    var curr_date = today.getDate();
                    var curr_month = today.getMonth() + 1;
                    var curr_year = today.getFullYear();

                    var pieces = nac.split('/');
                    var birth_date = pieces[0];
                    var birth_month = pieces[1];
                    var birth_year = pieces[2];
                    
                    
                    if (curr_year != birth_year && birth_month > curr_month  ) edad.value = curr_year - birth_year - 1;
                    if (curr_year != birth_year && birth_month == curr_month  ) edad.value = curr_year - birth_year;
                    if (curr_year != birth_year && birth_month < curr_month  ) edad.value = curr_year - birth_year;
                    if (curr_year == birth_year) edad.value = 0;
                     
                        });
            </script>
            <script type="text/javascript">
              function funct(){
                    var nac =  document.getElementById("fechaNac").value;
                    var edad =  document.getElementById("edad");
                    
                    var today = new Date();
                    var curr_date = today.getDate();
                    var curr_month = today.getMonth() + 1;
                    var curr_year = today.getFullYear();

                    var pieces = nac.split('/');
                    var birth_date = pieces[0];
                    var birth_month = pieces[1];
                    if (birth_month == 'ene') birth_month = 1;
                    if (birth_month == 'feb') birth_month = 2;
                    if (birth_month == 'mar') birth_month = 3;
                    if (birth_month == 'abr') birth_month = 4;
                    if (birth_month == 'may') birth_month = 5;
                    if (birth_month == 'jun') birth_month = 6;
                    if (birth_month == 'jul') birth_month = 7;
                    if (birth_month == 'ago') birth_month = 8;
                    if (birth_month == 'sep') birth_month = 9;
                    if (birth_month == 'oct') birth_month = 10;
                    if (birth_month == 'nov') birth_month = 11;
                    if (birth_month == 'dic') birth_month = 12;
                    
                    var birth_year = pieces[2];
                    
                    if (curr_year != birth_year && birth_month > curr_month  ) edad.value = curr_year - birth_year - 1;
                    if (curr_year != birth_year && birth_month == curr_month  ) edad.value = curr_year - birth_year;
                    if (curr_year != birth_year && birth_month < curr_month  ) edad.value = curr_year - birth_year;
                    if (curr_year == birth_year) edad.value = 0;
                     
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
            if(document.getElementById('trabIndep').checked){
            if(document.formulario.ingresoInd.value.match(numericExpression))
            {
             return true
            }else{
                
                alert( "El campo debe contener solo números" );
                document.formulario.ingresoInd.focus() ;
                return false;
            }
            }
            if(document.getElementById('trabDep').checked){
            if( document.formulario.ingresoDep.value.match(numericExpression))
            {
             return true
            }else{
                
                alert( "El campo debe contener solo números" );
                document.formulario.ingresoDep.focus() ;
                return false;
            }
            }
            if(!document.getElementById('trabDep').checked || !document.getElementById('trabIndep').checked){
                alert( "Debe elegir al menos un tipo de trabajo" );
                document.formulario.trabDep.focus() ;
                return false;
                
            }
            
            }
          </script>
    </body>
</html>


