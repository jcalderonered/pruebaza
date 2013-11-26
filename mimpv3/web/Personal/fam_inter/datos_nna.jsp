<%-- 
    Document   : ficha_inscripcion
    Created on : 31/10/2013, 11:13:11 AM
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
                            <li class="active"><a href="#">Inicio</a></li>
                            <li><a href="#">Actualizar Información</a></li>
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
                            <li class="active"><a href=""><span class="glyphicon glyphicon-home"></span> Inicio</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Sesiones/talleres</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de NNAs</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Juzgado</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de CAR</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Administración de UAs</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Ingreso de familias internacionales</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Registro de familias por etapa</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Buscador de Registros</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Administración de usuarios</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Organismo Acreditado </a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Gestión de Autoridad Central</a></li>
                            <li><a href="#"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>    
                        
                        </ul>
                    </div>

                    <div class="col-md-9">
                        <p align="right"><button id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <br>
                        <h1 align="center"><strong>Datos de la familia internacional</strong></h1>
                        <br>                      
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="#" data-toggle="tab">La Solicitante</a></li>
                            <li><a href="#" data-toggle="tab">El solicitante</a></li>
                            <!-- <li><a href="#" data-toggle="tab">Composición familiar</a></li> -->
                           <!-- <li><a href="#" data-toggle="tab">Vivienda</a></li> -->
                            <!--<li><a href="#" data-toggle="tab">Proceso de adopción</a></li> -->
                            <li class="active"><a href="#" data-toggle="tab">Antecedentes del niño, niña o adolescente</a></li>
                        </ul>

                        <form class="form-horizontal"> 
                            <br>
                            <h3><strong>Antecedentes, condiciones de salud y desarrollo del niño, niña o adolescente a adoptar(NNA)</strong></h3>
                            <br>
                            <p>Teniendo en cuenta la información recibida, la familia se 
                                siente dispuesta para asumir la adopción de un NNA que presente lo/s siguiente/s: 
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
                            <h3><strong>Predisposición para viajar</strong></h3>
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
                            <div class="control-group">
                                <label class="control-label">En caso su respuesta sea negativa, indicar por qué</label>
                                <div class="controls">
                                    <textarea class="input-xlarge" name="message" placeholder="" rows="5" ></textarea>
                                </div>
                            </div>     


                            <!-- Button -->
                            <br>
                            <br>
                            <div class="control-group">
                                <div class="controls">
                                    <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-default">Guardar cambios</button>
                                </div>
                            </div>
                        </form>
                        <br>
                        <p>Únicamente tras haber llenado todos los campos de la familia, seguir el siguiente enlace:</p>
                        <br>
                        <div class="control-group">
                            <div class="controls">
                                <button id="singlebutton" name="singlebutton" class="btn btn-info">Confirmar y enviar información</button>
                            </div>
                        </div>
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