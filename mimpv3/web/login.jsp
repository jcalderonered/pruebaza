<%-- 
    Document   : index
    Created on : 21-oct-2013, 10:25:57
    Author     : Hernán
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
                        <li class="active"><a href="#">Inicio</a></li>
                        <li><a href="#">Inscripción a Sesión Informativa</a></li>
                        <li><a href="#">Información Adicional</a></li>
                        <li><a href="#">Contacto</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>
        
        <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
        <div id="contenedor1" class="container">
            <div class="row">
                <div class="col-md-6 col-md-offset-2">
                <form role="form" action="${pageContext.servletContext.contextPath}/login" method="post">
                    <p align="center"><img src="<%=request.getContextPath()%>/assets/img/logo.png" width="200" border="0"></p>
                    <h2>Iniciar sesión:</h2>
                    <br>
                    <div class="form-group">
                        <label for="exampleInputEmail1">Usuario</label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="Ingrese el usuario">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputPassword1">Contraseña</label>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Contraseña">
                    </div>
                    <div class="btn-toolbar">  
                        <button type="submit" class="btn btn-default">Ingresar</button>
                        <button href="#" class="btn btn-default">Recordar contraseña</button>
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
                <p align="right">Diseñado por RED<br>www.red.net.pe</p>
            </div>
        </div>
    </body>
</html>
