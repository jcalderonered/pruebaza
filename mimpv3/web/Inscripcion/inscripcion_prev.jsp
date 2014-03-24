<%-- 
    Document   : index
    Created on : 21-oct-2013, 10:25:57
    Author     : Hernán
--%>

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
                            <span class="icon-bar"></span>
                        </button>
                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-left">
                            <li><a href="${pageContext.servletContext.contextPath}/inicio">Inicio</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/SesionInfInicioPrev">Inscripción a Sesión Informativa</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/CronogramaAnualPrev">Ver el cronograma anual</a></li>
                            <!--<li><a href="#">Información Adicional</a></li>-->
                            <li><a href="${pageContext.servletContext.contextPath}/Contacto">Contacto</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div id="contenedor1" class="container">
                <div class="row">
                    <div class="col-md-6 col-md-offset-2">
                        <h3>Para inscribirse a la Sesión Informativa, seleccione la Unidad de Adopción que le corresponde de acuerdo a su residencia.</h3>
                        <br>
                        <div class="table-responsive">
                            <table id="mi_tabla" class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th class="col-sm-2 " >N°</th>
                                        <th class="col-sm-2 " >UNIDAD DE ADOPCIÓN</th>
                                        <th class="col-sm-2 " >COMPETENCIA:</th>
                                    </tr>
                                </thead>
                                <c:if test="${!listaUa.isEmpty()}">
                                    <tbody>      
                                        <c:forEach var="ua" items="${listaUa}" varStatus="status">
                                            <tr>
                                                <td>${status.count}</td>
                                                <td>${ua.getNombre()}</td>
                                                <td>${ua.getCompetenciaRegional()}</td>
                                            </tr>
                                        </c:forEach>   
                                    </tbody>
                                </c:if> 
                            </table>
                        </div>
                        <br>
                        <form class="form-signin" action="${pageContext.servletContext.contextPath}/SesionInfInicio" method="post">
                            <div>
                                <label class="control-label">Seleccionar Unidad de Adopción</label>
                                <div class="controls">
                                    <select id="ua" name="ua">
                                        <c:forEach var="ua" items="${listaUa}" varStatus="status"> 
                                            <option value="${ua.getDepartamento()}">${ua.getNombre()}</option>
                                        </c:forEach> 
                                    </select>
                                </div>
                                <!--
                                <div class="controls">
                                    <select id="ua" name="ua">
                                        <option value="Lima">Lima</option>
                                        <option value="Arequipa">Arequipa</option>
                                        <option value="Ayacucho">Ayacucho</option>
                                        <option value="Cusco">Cusco</option>
                                        <option value="Huanuco">Huanuco</option>
                                        <option value="Lambayeque">Lambayeque</option>
                                        <option value="Libertad">La Libertad</option>                                            
                                        <option value="Loreto">Loreto</option>
                                        <option value="Piura">Piura</option>
                                        <option value="Puno">Puno</option>
                                        <option value="Junin">Junin</option>
                                    </select>
                                </div>    
                                -->
                            </div>
                            <br>       
                            <div class="btn-toolbar">  
                                <button type="submit" class="btn btn-default">Ingresar</button>
                            </div>
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
                    <p align="right">Versión 1.0.3</p>
                </div>
            </div>
            <!-- core JavaScript
    ================================================== -->
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
            <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
            <!-- Ubicar al final -->
    </body>
</html>
