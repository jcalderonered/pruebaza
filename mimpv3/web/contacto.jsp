<%-- 
    Document   : contacto
    Created on : 7/11/2013, 10:42:21 AM
    Author     : Ayner Pérez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        
                        
                        <a href="${pageContext.servletContext.contextPath}/lista"></a>
                        <a class="btn" href="${pageContext.servletContext.contextPath}/lista">Link</a>
                        <div class="row">
                            <h3><strong>
                                 ${listap.get(0).getNombre()}
                                </strong></h3>
                            
                        </div>

            </div> 
            <div id="footer">
                <div id="ja-footer" class="wrap">
                    <hr width=80% align="center">
                    <p align="center"><h5 class="caption" align="center" style="text-align: center;">MINISTERIO DE LA MUJER Y POBLACIONES VULNERABLES<br>Jr. Camaná 616, Lima - Perú<br>Central telefónica: (511) 626-1600</h5></p>
                    <p align="right">Diseñado por RED<br>www.red.net.pe</p>
                </div>
            </div>
    </body>
</html>
