<%-- 
    Document   : inicio_personal
    Created on : 28/10/2013, 09:54:29 AM
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
                            <li><a href="${pageContext.servletContext.contextPath}/esperaInter"><span class="glyphicon glyphicon-chevron-right"></span>Adoptantes para la adopción en el extranjero</a></li>       
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
                    <div class="col-md-6 col-md-offset-1">                        
                        <h1 align="center"><strong>Editar NNA</strong></h1>
                        <br>
                        <c:if test="${nna == null}">
                            <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/MainCrearNna" method="post"> 
                                <input hidden name="idExpediente" id="idExpediente" value="${idExpediente}">
                            </c:if>  
                            <c:if test="${nna != null}">
                                <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/MainUpdateNna" method="post"> 
                                    <input hidden name="idNna" id="idNna" value="${nna.getIdnna()}">
                                    <input hidden name="idDesig" id="idDesig" value="${idDesig}">
                                </c:if>     
                            <fieldset>
                                <div class="control-group">
                                    <label class="control-label">Nombre</label>
                                    <div class="controls">
                                        <input id="nombre" name="nombre" value="${nna.getNombre()}" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Paterno</label>
                                    <div class="controls">
                                        <input id="apellidoP" value="${nna.getApellidoP()}" name="apellidoP" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Apellido Materno</label>
                                    <div class="controls">
                                        <input id="apellidoM" value="${nna.getApellidoM()}" name="apellidoM" type="text" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Sexo</label>
                                    <div class="controls">
                                        <select id="sexo" name="sexo">
                                            <option value="masculino" ${nna.getSexo() == 'masculino' ? 'selected' : ''} >Masculino</option>
                                            <option value="femenino"  ${nna.getSexo() == 'femenino' ? 'selected' : ''}>Femenino</option>
                                        </select>
                                    </div>    
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de nacimiento</label>
                                    <div class="controls">
                                        <input id="fechaNac" value="${nna.getFechaNacimiento() != null ? df.dateToStringNumeros(nna.getFechaNacimiento()) : ''}" name="fechaNac" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Edad </label>
                                    <div class="controls">
                                        <input id="edad" name="edad" type="text" value="${nna.getEdadAnhos()}" placeholder="Años" class="input-xlarge">
                                        &nbsp;
                                        <input id="meses" name="meses" type="text" value="${nna.getEdadMeses()}" placeholder="Meses" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">País de Nacimiento</label>
                                    <div class="controls">
                                        <input id="paisNac" name="paisNac" type="text" value="${nna.getPaisNacimiento()}" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Lugar de nacimiento</label>
                                    <div class="controls">
                                        <input id="dep" name="dep" type="text" placeholder="Departamento" value="${nna.getDepartamentoNacimiento()}" class="input-xlarge">
                                        &nbsp;
                                        <input id="prov" name="prov" type="text" placeholder="Provincia" value="${nna.getProvinciaNacimiento()}" class="input-xlarge">
                                        &nbsp;
                                        <input id="dist" name="dist" type="text" placeholder="Distrito" value="${nna.getDistritoNacimiento()}"  class="input-xlarge">
                                        &nbsp;
                                    </div>
                                    <br>
                                    <input id="direccion" name="direccion" type="text" value="${nna.getLugarNac()}" placeholder="Dirección" class="input-xlarge">
                                </div>
                                <br>
                                <br>
                            <h3><strong>Antecedentes, condiciones de salud y desarrollo del niño, niña o adolescente adoptado(NNA)</strong></h3>
                            <br>
                            <br>
                            <!-- Text input-->
                            <div class="row">
                                <div id="tabla_fam" class="table-responsive">
                                    <table id="hijos" class="table table-bordered table-striped ">
                                        <thead>
                                            <tr>
                                                <th>Antecedentes</th>
                                                <th>Si/No</th>
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
                                                    <select id="mental" name="mental" >
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
                                                <th>Si/No</th>
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
                                                    <select id="operacion" name="operacion" >
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
                                                    <select id="hiperactivo" name="hiperactivo">
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
                                                <th>Si/No</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <h4>Niñas, niños y adolescentes con necesidades especiales</h4>
                                                </td>
                                                <td>
                                                    <select id="especial" name="especial">
                                                        <option value="0" ${nna.getEspecial() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${nna.getEspecial() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niñas, niños y adolescentes con problemas de salud </h4>
                                                </td>
                                                <td>
                                                    <select id="salud" name="salud">
                                                        <option value="0" ${nna.getEnfermo() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${nna.getEnfermo() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niñas y niños mayores (A partir de 09 años)</h4>
                                                </td>
                                                <td>
                                                    <select id="mayor" name="mayor">
                                                        <option value="0" ${nna.getMayor() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${nna.getMayor() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Adolescentes (De 12 años hasta 17 años 11 meses)</h4>
                                                </td>
                                                <td>
                                                    <select id="adolescente" name="adolescente">
                                                        <option value="0" ${nna.getAdolescente() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${nna.getAdolescente() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Grupos de hermanos </h4>
                                                </td>
                                                <td>
                                                    <select id="hermanos" name="hermanos">
                                                        <option value="0" ${nna.getHermano() == 0 ? 'selected' : ''} >SI</option>
                                                        <option value="1" ${nna.getHermano() != 0 ? 'selected' : ''} >NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <br>
                                <div class="control-group">
                                    <label class="control-label">Fecha de Adopcion</label>
                                    <div class="controls">
                                        <input id="fechaAdopcion" value="${fechaAdopcion}" name="fechaAdopcion" type="text" class="datepicker input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Identificación de la Adopción</label>
                                    <div class="controls">
                                        <input id="numAdopcion" name="numAdopcion" type="text" value="${numAdopcion}" class="input-xlarge">
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <label class="control-label">Observaciones</label>
                                    <div class="controls">
                                        <textarea id="obs" name="obs" cols="25" rows="5">${nna.getObservaciones()}</textarea>
                                    </div>
                                </div>
                                <br>
                                <div class="control-group">
                                    <div class="controls">
                                        <button id="singlebutton" name="singlebutton" class="btn btn-default">Guardar Cambios</button>
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
              $(document).ready(function(){  
                $('.datepicker').datepicker({
                    "format": "dd/mm/yyyy", 
                    "weekStart": 1, 
                    "autoclose": true, 
                    "language": "es"
                });  
                  
                $('#fechaNac').datepicker({
                    "format": "dd/mm/yyyy", 
                    "weekStart": 1, 
                    "autoclose": true, 
                    "language": "es"
                }).on('changeDate', function (ev) {
                    
                    var nac =  document.getElementById("fechaNac").value;
                    var edad =  document.getElementById("edad");
                    var meses =  document.getElementById("meses");
                    
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
                    if (curr_month == birth_month) meses.value = 0;
                    if (curr_month != birth_month && curr_month > birth_month ) meses.value = curr_month - birth_month;
                    if (curr_month != birth_month && curr_month <= birth_month ) meses.value = curr_month + 12 - birth_month;
                     
                        });
                        
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
                        $("input[name="+name+"]:radio").attr('previousValue', false);
                        $(this).attr('previousValue', 'checked');
                        }
                     });   
              });

            </script>
            <!-- Ubicar al final -->
    </body>
</html>