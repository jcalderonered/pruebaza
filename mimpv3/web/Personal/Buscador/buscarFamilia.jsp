<%-- 
    Document   : agregar_exp
    Created on : 4/12/2013, 11:38:32 AM
    Author     : User
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
                                <%if (u.getRol().equals("DCRI") || u.getRol().equals("DGA") || u.getRol().equals("admin") || u.getRol().equals("UA")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/inf"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de sesiones/talleres</a></li>
                                <%}%>
                            <li><a href="${pageContext.servletContext.contextPath}/nna"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNA</a></li>
                                <%if (u.getRol().equals("mpartes")) {%>
                            <li><a href="${pageContext.servletContext.contextPath}/car"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                                <%}%>
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
                                <%}%>

                            <li><a href="${pageContext.servletContext.contextPath}/fametap"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>

                            <li><a href="${pageContext.servletContext.contextPath}/reg"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de registros</a></li>
                                <%if (!u.getRol().equals("DEIA Prio") && !u.getRol().equals("UA") && !u.getRol().equals("DAPA") && !u.getRol().equals("mpartes")) {%>
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
                    <div class="col-md-6 col-md-offset-1">
                        <!-- <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>  -->
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" >
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/reg" >Búsqueda de Expedientes de Familia</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/buscarNna" >Búsqueda de NNA's</a></li>
                        </ul>
                        <br>
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/inicioper'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <h1 align="center"><strong>Búsqueda de Expedientes de Familia</strong></h1>
                        <br>
                        <form class="form-horizontal" action="${pageContext.servletContext.contextPath}/FiltrarFam" method="post" name="formulario" onsubmit="return(validar());">
                            <fieldset>
                                <div class="row">
                                    <div class="col-md-3">
                                        <div class="control-group">
                                            <label class="control-label">Apellidos (Paterno/Materno)</label>
                                            <div class="controls">
                                                <input id="expediente" name="expediente" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>
                                        <br>
                                        <div class="control-group">
                                            <label class="control-label">HT</label>
                                            <div class="controls">
                                                <input id="HT" name="HT" type="text" class="input-xlarge">
                                            </div>
                                        </div>
                                        <br>

                                        <br>
                                    </div>   
                                    <br>
                                    <div class="col-md-4 col-md-offset-1">
                                        <div class="control-group">
                                            <label class="control-label">Nacionalidad</label>
                                            <div class="controls">
                                                <select id="nacionalidad" name="nacionalidad">
                                                    <option value="none" >Todas</option>
                                                    <option value="nacional" >Nacional</option>
                                                    <option value="internacional">Internacional</option>               
                                                </select>
                                            </div>
                                        </div>
                                        <br>
                                        <br>
                                        <div>
                                            <label class="control-label">Estado</label>
                                            <div class="controls">
                                                <select id="estado" name="estado">
                                                    <option value="none" ></option>
                                                    <option value="evaluacion" >En evaluación</option>
                                                    <option value="espera">Lista de espera</option>
                                                    <option value="estudio" >Asig. a un estudio de caso</option>
                                                    <option value="designado" >Designado</option>
                                                    <option value="adopcion" >En proceso de adopción</option>
                                                    <option value="reevaluacion" >Reevaluación</option>
                                                    <option value="post" >En post adopción</option>
                                                    <option value="eliminado" >Eliminado</option>
                                                </select>
                                            </div>    
                                        </div>
                                        <br>                                
                                    </div>
                                    <div class="col-md-4">                                                                                          
                                        <div>
                                            <label class="control-label">Tipo Familia</label>
                                            <div class="controls">
                                                <select id="tipofamilia" name="tipofamilia">
                                                    <option value="none" >Ninguna</option>
                                                    <option value="PP">PP</option>
                                                    <option value="PE">PE</option>
                                                    <option value="MP" >MP</option>
                                                    <option value="ME" >ME</option>
                                                    <option value="EP" >EP</option>
                                                    <option value="EE" >EE</option>
                                                </select>
                                            </div>    
                                        </div>
                                        <br>
                                        <br>
                                        <!--
                                        <div>
                                            <label class="control-label">Residencia familia (para hab. de Peru)</label>
                                            <div class="controls">
                                                <select id="resfamilia" name="resfamilia">
                                                    <option value="none"> </option>
                                                    <option value="Amazonas">Amazonas</option><option value="Ancash">Ancash</option><option value="Apurimac">Apurimac</option><option value="Arequipa">Arequipa</option><option value="Ayacucho">Ayacucho</option><option value="Cajamarca">Cajamarca</option><option value="Callao">Callao</option><option value="Cusco">Cusco</option><option value="Huancavelica">Huancavelica</option><option value="Huanuco">Huanuco</option><option value="Ica">Ica</option><option value="Junin">Junin</option><option value="La Libertad">La Libertad</option><option value="Lambayeque">Lambayeque</option><option value="Lima">Lima</option><option value="Loreto">Loreto</option><option value="Madre de Dios">Madre de Dios</option><option value="Moquegua">Moquegua</option><option value="Pasco">Pasco</option><option value="Piura">Piura</option><option value="Puno">Puno</option><option value="San Martin">San Martin</option><option value="Tacna">Tacna</option><option value="Tumbes">Tumbes</option><option value="Ucayali">Ucayali</option>
                                                </select>
                                            </div>    
                                        </div>
                                        -->
                                    </div>    
                                </div>
                                <button id="singlebutton" name="singlebutton" class="btn btn-default">Buscar</button>
                            </fieldset>
                        </form>
                        <br>
                        <h1 align="center"><strong>Resultados de la Búsqueda</strong></h1>
                        <br>

                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="col-sm-2 " >Expediente</th>
                                        <th class="col-sm-2 " >Nacionalidad</th>                                            
                                        <th class="col-sm-2 " >Estado</th>
                                        <th class="col-sm-2 " >Información</th>
                                    </tr>
                                </thead>
                                <c:if test="${!listaBusqueda.isEmpty()}"> 
                                    <tbody>
                                        <c:forEach var="familia" items="${listaBusqueda}" varStatus="status">
                                            <c:set var="agregado" value="1" />
                                            <c:if test="${usuario.getUnidad().getDepartamento() == familia.getUnidad().getDepartamento() || usuario.getUnidad().getDepartamento() == 'Lima'}">
                                                <tr>
                                                    <td>${familia.getExpediente()}</td>                                                                               
                                                    <td>${familia.getNacionalidad()}</td> 
                                                    <td>${familia.getEstado()}</td> 
                                                    <td>
                                                        <form action="${pageContext.servletContext.contextPath}/IrPersonalFamilia2" method="post">

                                                            <c:if test="${familia.getEstado() == 'post' }" > 
                                                                <input hidden name="estado" id="estado" value="reevaluacion">
                                                            </c:if> 

                                                            <c:if test="${familia.getEstado() != 'post' }" > 
                                                                <input hidden name="estado" id="estado" value="${familia.getEstado()}">
                                                            </c:if>

                                                            <input hidden name="idExpediente" id="idExpediente" value="${familia.getIdexpedienteFamilia()}">
                                                            <input hidden name="volver" id="volver" value="${volver}">
                                                            <input hidden name="expediente" id="expediente" value="${expediente}">
                                                            <input hidden name="HT" id="HT" value="${HT}">
                                                            <input hidden name="nacionalidad" id="nacionalidad" value="${nacionalidad}">                                                        
                                                            <input hidden name="tipofamilia" id="tipofamilia" value="${tipofamilia}">
                                                            <input hidden name="estado2" id="estado2" value="${estado}">
                                                            <button type="submit" class="btn btn-default">Ver</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>  
                                    </tbody>
                                </c:if> 
                                <c:if test="${listaBusqueda.isEmpty()}">
                                    <h3><strong>No se encontraron expedientes</strong></h3>
                                </c:if>  
                            </table>
                        </div>
                        <br>

                    </div>
                </div>
            </div>
            <!--FIN DE CONTENIDO-->
            <br>
            <br>
        </div>   
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
        <script type="text/javascript">

                            function validar()
                            {

                                if (document.formulario.expediente.value == "" && document.formulario.HT.value == ""
                                        && document.getElementById("nacionalidad").value == "none" && document.getElementById("estado").value == "none"
                                        && document.getElementById("tipofamilia").value == "none" && document.getElementById("resfamilia").value == "none")
                                {
                                    alert("Debe elegir al menos un filtro de búsqueda");
                                    return false;
                                }

                                return(true);
                            }
        </script>
        <!-- Ubicar al final -->
    </body>
</html>
