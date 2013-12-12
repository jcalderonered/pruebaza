<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Familia"%>
<%
response.setHeader( "Pragma", "no-cache" );
response.addHeader( "Cache-Control", "must-revalidate" );
response.addHeader( "Cache-Control", "no-cache" );
response.addHeader( "Cache-Control", "no-store" );
response.setDateHeader("Expires", 0);
Familia u=(Familia)request.getSession().getAttribute("usuario");
if (u==null){
%>
<jsp:forward page="/salir"/>
<% } %>
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
                            <li class="active"><a href="#">Ver Información</a></li>
                            <li><a href="#">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>

            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div class="container">
                <div class="row">
                    <div class="col-md-3 ">
                        <ul class="nav nav-list well">
                            <li><a href=""><span class="glyphicon glyphicon-home"></span>  Inicio</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Inscripción a talleres</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Estado del proceso de Adopción</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>

                    <div class="col-md-9">
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="#" data-toggle="tab">La Solicitante</a></li>
                            <li><a href="#" data-toggle="tab">El solicitante</a></li>
                            <li><a href="#" data-toggle="tab">Composición familiar</a></li>
                            <li><a href="#" data-toggle="tab">Vivienda</a></li>
                            <li class="active"><a href="#" data-toggle="tab">Antecedentes del niño, niña o adolescente</a></li>
                        </ul>

                        <form class="form-horizontal"> 
                            <br>
                            <h3><strong>Antecedentes, condiciones de salud y desarrollo del niño, niña o adolescente a adoptar(NNA)</strong></h3>
                            <br>
                            <p>Teniendo en cuenta la información recibida en la sesión informativa y taller de preparación, se 
                                siente/n dispuesto/a/s para asumir la adopción de un NNA que presente lo/s siguiente/s: 
                            </p>
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
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Padres con enfermedad psiquiátrica (esquizofrenia, paranoia, etc</h4>
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Padres con epilepsia</h4>
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niña, niño o adolescente víctima de abuso sexual</h4>
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niña, niño o adolescente adolescente actualmente sano, que al nacer fue 
                                                        diagnosticado/a preliminarmente con sífilis congénita.</h4>
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
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
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Condiciones  de  salud  que  requieran  intervención  quirúrgica  menor 
                                                        (labio leporino, estrabismo, etc.)</h4>
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niña,  niño  o  adolescente  con  trastorno  de  déficit  de  atención  e 
                                                        hiperactividad (TDAH)</h4>
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
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
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niñas, niños y adolescentes con problemas de salud </h4>
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Niñas y niños mayores (A partir de 09 años)</h4>
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Adolescentes (De 12 años hasta 17 años 11 meses)</h4>
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <h4>Grupos de hermanos </h4>
                                                </td>
                                                <td>
                                                    <select>
                                                        <option value="sia">SI</option>
                                                        <option value="mia">NO</option>
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
                                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Si</label>
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >No</label>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <h3><strong>Espectativas</strong></h3>
                            <br>
                            <div class="control-group">
                                <label class="control-label">¿Cuántos años podría tener el niño, niña o adolescente a adoptar?</label>
                                <div class="controls">
                                    <input id="apellido_m" name="full-name" type="text" class="input-xlarge" disabled>
                                </div>
                            </div>
                            <br>
                            <p><strong>Género del niño, niña o adolescente a adoptar </strong></p>
                            <div class="row">
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Masculino</label>
                                    </div>
                                </div> 
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Femenino</label>
                                    </div>
                                </div>
                                <div class="col-md-3">  
                                    <div class="radio">
                                        <label>
                                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="D" >Indistinto</label>
                                    </div>
                                </div>
                            </div>
                            <!-- Button -->
                            <br>
                            <p style="color: red">IMPORTANTE: SI DESEA REALIZAR ALGÚN CAMBIO DEBERÁ PRESENTAR UNA SOLICITUDA</p>
                            <p style="color: red">EN MESA DE PARTES DE LA DGA</p>
                            <br>
                            <p>Según lo establecido en la Ley Nº29733 - Ley de protección de datos personales, la información proporcionada por 
                                usted/ustedes será de uso exclusivo, con la reserva y confidencialidad del caso, para los fines concernientes al 
                                procedimiento administrativo de adopción.
                            </p>
                             <!-- Button -->
                                <br>
                                <!--
                                    <div class="control-group">
                                        <div class="controls">
                                            <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
                                        </div>
                                    </div>
                                -->
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