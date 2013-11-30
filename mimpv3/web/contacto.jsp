<%-- 
    Document   : contacto
    Created on : 7/11/2013, 10:42:21 AM
    Author     : Ayner Pérez
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
                            <li><a href="#">Inicio</a></li>
                            <li><a href="#">Inscripción a Sesión Informativa</a></li>
                            <li><a href="#">Información Adicional</a></li>
                            <li class="active"><a href="#">Contacto</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>

                <div class="row">
                    <h3><strong>
                            ${nombre}
                        </strong></h3>
                    <br>
                    <h3><strong>
                            ${personal.getApellidoP()}
                        </strong></h3>
                    <!--FIN DE CONTENIDO-->
                    <br>
                    <br>
                </div>
                <br>




                <button onclick="window.location.href = '${pageContext.servletContext.contextPath}/lista'" class="btn btn-default">Mostrar lista de personal</button>
                <br>
                <br>
                <div class="row">
                    <h3><strong>Lista de Personal</strong></h3> 
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped">
                            <thead>
                                <tr>
                                    <th class="col-sm-2 ">id</th>
                                    <th class="col-sm-2 ">Nombre</th>
                                    <th class="col-sm-2 ">Apellido Paterno</th>
                                    <th class="col-sm-2 ">Apellido Materno</th>
                                    <th class="col-sm-2 ">Usuario</th>
                                    <th class="col-sm-2 ">Información</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="personal" items="${listap}">
                                    <tr>
                                        <td>${personal.getIdpersonal()}</td>
                                        <td>${personal.getNombre()}</td>
                                        <td>${personal.getApellidoP()}</td>
                                        <td>${personal.getApellidoM()}</td>
                                        <td>${personal.getUser()}</td>
                                        <td>
                                            <form action="${pageContext.servletContext.contextPath}/info" method="post">
                                                <input hidden name="id" id="id" value="${personal.getIdpersonal()}">
                                                <button type="submit" class="btn btn-default">Ver</button>
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>

            </div> 
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

            <!-- Ubicar al final -->
    </body>
</html>
