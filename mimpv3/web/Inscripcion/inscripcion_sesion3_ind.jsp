<%-- 
    Document   : inscripcion_sesion3_ind
    Created on : 28/10/2013, 06:21:34 AM
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
                            <span class="icon-bar"></span>
                        </button>

                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-left">
                            <li><a href="${pageContext.servletContext.contextPath}/inicio">Inicio</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/SesionInfInicioPrev">Inscripción a Sesión Informativa</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/CronogramaAnualPrev">Ver el cronograma anual</a></li>
                            <!--<li><a href="#">Información Adicional</a></li>-->
                            <li><a href="#">Contacto</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>

            <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
            <div id="contenedor1" class="container">
                <p class="text-center"><legend>FORMULARIO DE INSCRIPCIÓN A SESIÓN INFORMATIVA SOBRE ADOPCIÓN   ${turno.getSesion().getNSesion()}</legend></p>
                <br>  
                <br>
                <div class="row">
                    <div class="col-md-8 col-md-offset-3">
                        <form class="form-inline" action="${pageContext.servletContext.contextPath}/inscSesInd" role="form" method="post"  name="formulario" onsubmit="return(validar());" onkeypress="return enter(event)">
                            <input hidden id="idTurno" name="idTurno" value="${turno.getIdturno()}">
                            <input hidden id="estado" name="estado" value="${estado}">

                            <!-- Form Name -->

                            <p class="text-left"><legend>Información Personal</legend></p>
                            <br>
                            <!-- Text input-->
                            <div class="row">
                                <div class="col-lg-4">  
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Nombres <font style="color:red">(*)</font></label>
                                        <div class="controls">
                                            <input id="nombre" name="nombre" onkeyup="return(limitar());"  type="text" placeholder="Nombres" class="input-xlarge">
                                        </div>
                                    </div>
                                </div>    
                                <!-- Text input-->
                                <div class="col-lg-4">  
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Apellido Paterno <font style="color:red">(*)</font></label>
                                        <div class="controls">
                                            <input id="apellidoP" name="apellidoP" onkeyup="return(limitar());"  type="text" placeholder="Apellido Paterno" class="input-xlarge">
                                        </div>
                                    </div>
                                </div>        
                                <!-- Text input-->
                                <div class="col-lg-4">  
                                    <div class="control-group">
                                        <label class="control-label" for="textinput">Apellido Materno <font style="color:red">(*)</font></label>
                                        <div class="controls">
                                            <input id="apellidoM" name="apellidoM" onkeyup="return(limitar());"  type="text" placeholder="Apellido Materno" class="input-xlarge">
                                        </div>
                                    </div>
                                </div>     
                            </div>   
                            <br>
                            <div class="row">
                                <!--
                                <div class="col-lg-4">
                                    <label for="pa_nac">País de Nacimiento <font style="color:red">(*)</font></label> 
                                    <input id="paisNac" name="paisNac" type="text" placeholder="Pais" class="input-xlarge">
                                </div>
                                -->
                                <div class="col-lg-4">
                                    <label class="control-label">País de Nacimiento<font style="color:red">(*)</font></label>
                                    <div class="controls">
                                        <select id="paisNac" name="paisNac">
                                            <option value="" selected> </option>
                                            <option value="AF">Afganistán</option>
                                            <option value="AL">Albania</option>
                                            <option value="DE">Alemania</option>
                                            <option value="AD">Andorra</option>
                                            <option value="AO">Angola</option>
                                            <option value="AI">Anguilla</option>
                                            <option value="AQ">Antártida</option>
                                            <option value="AG">Antigua y Barbuda</option>
                                            <option value="AN">Antillas Holandesas</option>
                                            <option value="SA">Arabia Saudí</option>
                                            <option value="DZ">Argelia</option>
                                            <option value="AR">Argentina</option>
                                            <option value="AM">Armenia</option>
                                            <option value="AW">Aruba</option>
                                            <option value="AU">Australia</option>
                                            <option value="AT">Austria</option>
                                            <option value="AZ">Azerbaiyán</option>
                                            <option value="BS">Bahamas</option>
                                            <option value="BH">Bahrein</option>
                                            <option value="BD">Bangladesh</option>
                                            <option value="BB">Barbados</option>
                                            <option value="BE">Bélgica</option>
                                            <option value="BZ">Belice</option>
                                            <option value="BJ">Benin</option>
                                            <option value="BM">Bermudas</option>
                                            <option value="BY">Bielorrusia</option>
                                            <option value="MM">Birmania</option>
                                            <option value="BO">Bolivia</option>
                                            <option value="BA">Bosnia y Herzegovina</option>
                                            <option value="BW">Botswana</option>
                                            <option value="BR">Brasil</option>
                                            <option value="BN">Brunei</option>
                                            <option value="BG">Bulgaria</option>
                                            <option value="BF">Burkina Faso</option>
                                            <option value="BI">Burundi</option>
                                            <option value="BT">Bután</option>
                                            <option value="CV">Cabo Verde</option>
                                            <option value="KH">Camboya</option>
                                            <option value="CM">Camerún</option>
                                            <option value="CA">Canadá</option>
                                            <option value="TD">Chad</option>
                                            <option value="CL">Chile</option>
                                            <option value="CN">China</option>
                                            <option value="CY">Chipre</option>
                                            <option value="VA">Ciudad del Vaticano (Santa Sede)</option>
                                            <option value="CO">Colombia</option>
                                            <option value="KM">Comores</option>
                                            <option value="CG">Congo</option>
                                            <option value="CD">Congo, República Democrática del</option>
                                            <option value="KR">Corea</option>
                                            <option value="KP">Corea del Norte</option>
                                            <option value="CI">Costa de Marfíl</option>
                                            <option value="CR">Costa Rica</option>
                                            <option value="HR">Croacia (Hrvatska)</option>
                                            <option value="CU">Cuba</option>
                                            <option value="DK">Dinamarca</option>
                                            <option value="DJ">Djibouti</option>
                                            <option value="DM">Dominica</option>
                                            <option value="EC">Ecuador</option>
                                            <option value="EG">Egipto</option>
                                            <option value="SV">El Salvador</option>
                                            <option value="AE">Emiratos Árabes Unidos</option>
                                            <option value="ER">Eritrea</option>
                                            <option value="SI">Eslovenia</option>
                                            <option value="ES">España</option>
                                            <option value="US">Estados Unidos</option>
                                            <option value="EE">Estonia</option>
                                            <option value="ET">Etiopía</option>
                                            <option value="FJ">Fiji</option>
                                            <option value="PH">Filipinas</option>
                                            <option value="FI">Finlandia</option>
                                            <option value="FR">Francia</option>
                                            <option value="GA">Gabón</option>
                                            <option value="GM">Gambia</option>
                                            <option value="GE">Georgia</option>
                                            <option value="GH">Ghana</option>
                                            <option value="GI">Gibraltar</option>
                                            <option value="GD">Granada</option>
                                            <option value="GR">Grecia</option>
                                            <option value="GL">Groenlandia</option>
                                            <option value="GP">Guadalupe</option>
                                            <option value="GU">Guam</option>
                                            <option value="GT">Guatemala</option>
                                            <option value="GY">Guayana</option>
                                            <option value="GF">Guayana Francesa</option>
                                            <option value="GN">Guinea</option>
                                            <option value="GQ">Guinea Ecuatorial</option>
                                            <option value="GW">Guinea-Bissau</option>
                                            <option value="HT">Haití</option>
                                            <option value="HN">Honduras</option>
                                            <option value="HU">Hungría</option>
                                            <option value="IN">India</option>
                                            <option value="ID">Indonesia</option>
                                            <option value="IQ">Irak</option>
                                            <option value="IR">Irán</option>
                                            <option value="IE">Irlanda</option>
                                            <option value="BV">Isla Bouvet</option>
                                            <option value="CX">Isla de Christmas</option>
                                            <option value="IS">Islandia</option>
                                            <option value="KY">Islas Caimán</option>
                                            <option value="CK">Islas Cook</option>
                                            <option value="CC">Islas de Cocos o Keeling</option>
                                            <option value="FO">Islas Faroe</option>
                                            <option value="HM">Islas Heard y McDonald</option>
                                            <option value="FK">Islas Malvinas</option>
                                            <option value="MP">Islas Marianas del Norte</option>
                                            <option value="MH">Islas Marshall</option>
                                            <option value="UM">Islas menores de Estados Unidos</option>
                                            <option value="PW">Islas Palau</option>
                                            <option value="SB">Islas Salomón</option>
                                            <option value="SJ">Islas Svalbard y Jan Mayen</option>
                                            <option value="TK">Islas Tokelau</option>
                                            <option value="TC">Islas Turks y Caicos</option>
                                            <option value="VI">Islas Vírgenes (EEUU)</option>
                                            <option value="VG">Islas Vírgenes (Reino Unido)</option>
                                            <option value="WF">Islas Wallis y Futuna</option>
                                            <option value="IL">Israel</option>
                                            <option value="IT">Italia</option>
                                            <option value="JM">Jamaica</option>
                                            <option value="JP">Japón</option>
                                            <option value="JO">Jordania</option>
                                            <option value="KZ">Kazajistán</option>
                                            <option value="KE">Kenia</option>
                                            <option value="KG">Kirguizistán</option>
                                            <option value="KI">Kiribati</option>
                                            <option value="KW">Kuwait</option>
                                            <option value="LA">Laos</option>
                                            <option value="LS">Lesotho</option>
                                            <option value="LV">Letonia</option>
                                            <option value="LB">Líbano</option>
                                            <option value="LR">Liberia</option>
                                            <option value="LY">Libia</option>
                                            <option value="LI">Liechtenstein</option>
                                            <option value="LT">Lituania</option>
                                            <option value="LU">Luxemburgo</option>
                                            <option value="MK">Macedonia, Ex-República Yugoslava de</option>
                                            <option value="MG">Madagascar</option>
                                            <option value="MY">Malasia</option>
                                            <option value="MW">Malawi</option>
                                            <option value="MV">Maldivas</option>
                                            <option value="ML">Malí</option>
                                            <option value="MT">Malta</option>
                                            <option value="MA">Marruecos</option>
                                            <option value="MQ">Martinica</option>
                                            <option value="MU">Mauricio</option>
                                            <option value="MR">Mauritania</option>
                                            <option value="YT">Mayotte</option>
                                            <option value="MX">México</option>
                                            <option value="FM">Micronesia</option>
                                            <option value="MD">Moldavia</option>
                                            <option value="MC">Mónaco</option>
                                            <option value="MN">Mongolia</option>
                                            <option value="MS">Montserrat</option>
                                            <option value="MZ">Mozambique</option>
                                            <option value="NA">Namibia</option>
                                            <option value="NR">Nauru</option>
                                            <option value="NP">Nepal</option>
                                            <option value="NI">Nicaragua</option>
                                            <option value="NE">Níger</option>
                                            <option value="NG">Nigeria</option>
                                            <option value="NU">Niue</option>
                                            <option value="NF">Norfolk</option>
                                            <option value="NO">Noruega</option>
                                            <option value="NC">Nueva Caledonia</option>
                                            <option value="NZ">Nueva Zelanda</option>
                                            <option value="OM">Omán</option>
                                            <option value="NL">Países Bajos</option>
                                            <option value="PA">Panamá</option>
                                            <option value="PG">Papúa Nueva Guinea</option>
                                            <option value="PK">Paquistán</option>
                                            <option value="PY">Paraguay</option>
                                            <option value="Peru">Perú</option>
                                            <option value="PN">Pitcairn</option>
                                            <option value="PF">Polinesia Francesa</option>
                                            <option value="PL">Polonia</option>
                                            <option value="PT">Portugal</option>
                                            <option value="PR">Puerto Rico</option>
                                            <option value="QA">Qatar</option>
                                            <option value="UK">Reino Unido</option>
                                            <option value="CF">República Centroafricana</option>
                                            <option value="CZ">República Checa</option>
                                            <option value="ZA">República de Sudáfrica</option>
                                            <option value="DO">República Dominicana</option>
                                            <option value="SK">República Eslovaca</option>
                                            <option value="RE">Reunión</option>
                                            <option value="RW">Ruanda</option>
                                            <option value="RO">Rumania</option>
                                            <option value="RU">Rusia</option>
                                            <option value="EH">Sahara Occidental</option>
                                            <option value="KN">Saint Kitts y Nevis</option>
                                            <option value="WS">Samoa</option>
                                            <option value="AS">Samoa Americana</option>
                                            <option value="SM">San Marino</option>
                                            <option value="VC">San Vicente y Granadinas</option>
                                            <option value="SH">Santa Helena</option>
                                            <option value="LC">Santa Lucía</option>
                                            <option value="ST">Santo Tomé y Príncipe</option>
                                            <option value="SN">Senegal</option>
                                            <option value="SC">Seychelles</option>
                                            <option value="SL">Sierra Leona</option>
                                            <option value="SG">Singapur</option>
                                            <option value="SY">Siria</option>
                                            <option value="SO">Somalia</option>
                                            <option value="LK">Sri Lanka</option>
                                            <option value="PM">St Pierre y Miquelon</option>
                                            <option value="SZ">Suazilandia</option>
                                            <option value="SD">Sudán</option>
                                            <option value="SE">Suecia</option>
                                            <option value="CH">Suiza</option>
                                            <option value="SR">Surinam</option>
                                            <option value="TH">Tailandia</option>
                                            <option value="TW">Taiwán</option>
                                            <option value="TZ">Tanzania</option>
                                            <option value="TJ">Tayikistán</option>
                                            <option value="TF">Territorios franceses del Sur</option>
                                            <option value="TP">Timor Oriental</option>
                                            <option value="TG">Togo</option>
                                            <option value="TO">Tonga</option>
                                            <option value="TT">Trinidad y Tobago</option>
                                            <option value="TN">Túnez</option>
                                            <option value="TM">Turkmenistán</option>
                                            <option value="TR">Turquía</option>
                                            <option value="TV">Tuvalu</option>
                                            <option value="UA">Ucrania</option>
                                            <option value="UG">Uganda</option>
                                            <option value="UY">Uruguay</option>
                                            <option value="UZ">Uzbekistán</option>
                                            <option value="VU">Vanuatu</option>
                                            <option value="VE">Venezuela</option>
                                            <option value="VN">Vietnam</option>
                                            <option value="YE">Yemen</option>
                                            <option value="YU">Yugoslavia</option>
                                            <option value="ZM">Zambia</option>
                                            <option value="ZW">Zimbabue</option>
                                        </select>
                                    </div>    
                                </div>
                                <!--
                                <div class="col-lg-4">
                                    <label for="dep_nac">Departamento de Nacimiento<font style="color:red">(*)</font></label> 
                                    <input id="depNac" name="depNac" type="text" placeholder="Departamento" class="input-xlarge">
                                </div>
                                -->
                                <div class="col-lg-4">
                                    <label class="control-label">Departamento de Nacimiento<font style="color:red">(*)</font></label>
                                    <div class="controls">
                                        <select id="depNac" name="depNac">
                                            <option value="" selected> </option>
                                            <option value="---">No nacido en Perú</option>
                                            <option value="Amazonas">Amazonas</option><option value="Ancash">Ancash</option><option value="Apurimac">Apurimac</option><option value="Arequipa">Arequipa</option><option value="Ayacucho">Ayacucho</option><option value="Cajamarca">Cajamarca</option><option value="Callao">Callao</option><option value="Cusco">Cusco</option><option value="Huancavelica">Huancavelica</option><option value="Huanuco">Huanuco</option><option value="Ica">Ica</option><option value="Junin">Junin</option><option value="La Libertad">La Libertad</option><option value="Lambayeque">Lambayeque</option><option value="Lima">Lima</option><option value="Loreto">Loreto</option><option value="Madre de Dios">Madre de Dios</option><option value="Moquegua">Moquegua</option><option value="Pasco">Pasco</option><option value="Piura">Piura</option><option value="Puno">Puno</option><option value="San Martin">San Martin</option><option value="Tacna">Tacna</option><option value="Tumbes">Tumbes</option><option value="Ucayali">Ucayali</option>
                                        </select>
                                    </div>    
                                </div>
                                <div class="col-lg-4">
                                    <label for="dist_nac">Provincia de Nacimiento <font style="color:red">(*)</font></label> 
                                    <input id="proNac" name="proNac"  onkeyup="return(limitar());" type="text" placeholder="Provincia" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-lg-4">
                                    <label for="fecha">Fecha de Nacimiento <font style="color:red">(*)</font></label> 
                                    <input id="fechaNac" name="fechaNac" type="text" placeholder="dd/mm/yyyy" class="datepicker input-xlarge">
                                    <br>
                                    <strong><font style="color:red">Ejemplo: 23/12/1974</font></strong>
                                </div>                              
                                <div class="col-lg-4">
                                    <!--<label for="edad">Edad <font style="color:red">(*)</font></label> -->
                                    <input hidden id="edad" name="edad" type="text" placeholder="XY" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="doc" id="optionsRadios1" value="d" checked>
                                        DNI
                                    </label>
                                </div>
                                <br>
                                <div class="radio">
                                    <label>
                                        <input type="radio" name="doc" id="optionsRadios2" value="c">
                                        Carnet de Extranjería
                                    </label>
                                </div>                            
                            </div>
                            <br>
                            <div class="control-group">
                                <div class="controls">
                                    <label class="control-label">N° de Documento <font style="color:red">(*)</font></label>
                                    <div class="controls">
                                        <input id="numDoc" name="numDoc" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                    </div>
                                </div>
                            </div>                            
                            <br>
                            <!-- Select Basic -->
                            <div class="control-group">
                                <label class="control-label" for="selectbasic">Profesión/Ocupación <font style="color:red">(*)</font></label>
                                <div class="controls">
                                    <input id="profesion" name="profesion" onkeyup="return(limitar());"  type="text" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <!-- Text input-->
                            <div class="control-group">
                                <label class="control-label" for="textinput">Celular <font style="color:red">(*)</font></label>
                                <div class="controls">
                                    <input id="cel" name="cel" onkeyup="return(limitar());"  type="text" placeholder="Celular" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <!-- Text input-->
                            <div class="control-group">
                                <label class="control-label" for="textinput">Correo Electrónico <font style="color:red">(*)</font></label>
                                <div class="controls">
                                    <input id="correo" name="correo" onkeyup="return(limitar());"  type="text" placeholder="ejemplo@dominio.com" class="input-xlarge" >
                                </div>
                            </div>
                            <br>
                            <br>  

                            <p class="text-left"><legend>Residencia familiar</legend></p>
                            <br>
                            <div class="row">
                                <div class="col-lg-3">
                                    <label for="pais">País <font style="color:red">(*)</font></label>
                                    <input type="text" value="Peru" class="input-xlarge" disabled>
                                    <input hidden id="pais" name="pais" type="text" value="Peru" class="input-xlarge"/>
                                </div>
                                <!--
                                <div class="col-lg-3">
                                    <label for="dep">Departamento <font style="color:red">(*)</font></label> 
                                    <input id="dep" name="dep" type="text" placeholder="Departamento" class="input-xlarge">
                                </div>
                                -->
                                <div class="col-lg-3">
                                    <label class="control-label">Departamento<font style="color:red">(*)</font></label>
                                    <div class="controls">
                                        <select id="dep" name="dep">
                                            <option value="" selected> </option>
                                            <option value="Amazonas">Amazonas</option><option value="Ancash">Ancash</option><option value="Apurimac">Apurimac</option><option value="Arequipa">Arequipa</option><option value="Ayacucho">Ayacucho</option><option value="Cajamarca">Cajamarca</option><option value="Callao">Callao</option><option value="Cusco">Cusco</option><option value="Huancavelica">Huancavelica</option><option value="Huanuco">Huanuco</option><option value="Ica">Ica</option><option value="Junin">Junin</option><option value="La Libertad">La Libertad</option><option value="Lambayeque">Lambayeque</option><option value="Lima">Lima</option><option value="Loreto">Loreto</option><option value="Madre de Dios">Madre de Dios</option><option value="Moquegua">Moquegua</option><option value="Pasco">Pasco</option><option value="Piura">Piura</option><option value="Puno">Puno</option><option value="San Martin">San Martin</option><option value="Tacna">Tacna</option><option value="Tumbes">Tumbes</option><option value="Ucayali">Ucayali</option>
                                        </select>
                                    </div>    
                                </div>
                                <div class="col-lg-3">
                                    <label for="prov">Provincia <font style="color:red">(*)</font></label> 
                                    <input id="prov" name="prov" onkeyup="return(limitar());"  type="text" placeholder="Provincia" class="input-xlarge">
                                </div>
                                <div class="col-lg-3">
                                    <label for="dist">Distrito <font style="color:red">(*)</font></label> 
                                    <input id="dist" name="dist" onkeyup="return(limitar());"  type="text" placeholder="Distrito" class="input-xlarge">
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-lg-4">
                                    <label for="dir">Dirección domiciliaria <font style="color:red">(*)</font></label> 
                                    <input id="dir" name="dir" onkeyup="return(limitar());"  type="text" placeholder="Dirección Domiciliaria" class="input-xlarge">
                                </div>

                                <div class="col-lg-4">
                                    <label for="telf">Teléfono fijo <font style="color:red">(*)</font></label> 
                                    <input id="telf" name="telf" onkeyup="return(limitar());"  type="text" placeholder="Teléfono fijo" class="input-xlarge">
                                </div>
                            </div>
                            <br>

                            <!-- Button -->

                            <div class="row">
                                <div class="btn-toolbar">  
                                    <button onclick="window.location.href = '${pageContext.servletContext.contextPath}/inicio'" class="btn btn-default">Cancelar</button>

                                    <button type="submit" class="btn btn-default">Inscribirse</button>
                                </div> 
                                <br>   
                            </div>
                            <br>   
                        </form>
                    </div>
                </div>
                <p style="color: red">(*)IMPORTANTE: ES OBLIGATORIO EL LLENADO DE TODOS LOS CAMPOS</p>
                <br>
                <p>Según lo establecido en la Ley Nº29733 - Ley de protección de datos personales, la información proporcionada por usted/ustedes será de uso exclusivo, con la reserva y confidencialidad del caso, para los fines concernientes al procedimiento administrativo de adopción.</p>
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

                                        $('.datepicker').datepicker({"format": "dd/mm/yyyy", "weekStart": 1, "autoclose": true, "language": "es"});

                                        $('#fechaNac').on('changeDate', function(ev) {

                                            var nac = document.getElementById("fechaNac").value;
                                            var edad = document.getElementById("edad");

                                            var today = new Date();
                                            var curr_date = today.getDate();
                                            var curr_month = today.getMonth() + 1;
                                            var curr_year = today.getFullYear();

                                            var pieces = nac.split('/');
                                            var birth_date = pieces[0];
                                            var birth_month = pieces[1];
                                            var birth_year = pieces[2];


                                            if (curr_year != birth_year && birth_month > curr_month)
                                                edad.value = curr_year - birth_year - 1;
                                            if (curr_year != birth_year && birth_month == curr_month)
                                                edad.value = curr_year - birth_year;
                                            if (curr_year != birth_year && birth_month < curr_month)
                                                edad.value = curr_year - birth_year;
                                            if (curr_year == birth_year)
                                                edad.value = 0;


                                        });
            </script>

            <script type="text/javascript">

                function validar()
                {

                    if (document.formulario.nombre.value == "")
                    {
                        alert("Debe ingresar su nombre");
                        document.formulario.nombre.focus();
                        return false;
                    }
                    if (document.formulario.apellidoP.value == "")
                    {
                        alert("Debe ingresar su apellido paterno");
                        document.formulario.apellidoP.focus();
                        return false;
                    }
                    if (document.formulario.apellidoM.value == "")
                    {
                        alert("Debe ingresar su apellido materno");
                        document.formulario.apellidoM.focus();
                        return false;
                    }
                    if (document.formulario.paisNac.value == "")
                    {
                        alert("Debe ingresar su país de nacimiento");
                        document.formulario.paisNac.focus();
                        return false;
                    }
                    if (document.formulario.depNac.value == "")
                    {
                        alert("Debe ingresar su departamento de nacimiento. En caso extranjeros, seleccionar la opción 'No nacido en Perú'");
                        document.formulario.depNac.focus();
                        return false;
                    }
                    if (document.formulario.proNac.value == "")
                    {
                        alert("Debe ingresar su provincia de nacimiento. En caso extranjeros, brindar mayores detalles de procedencia");
                        document.formulario.proNac.focus();
                        return false;
                    }
                    if (document.formulario.fechaNac.value == "")
                    {
                        alert("Debe ingresar su fecha de nacimiento");
                        document.formulario.fechaNac.focus();
                        return false;
                    }
                    if (document.formulario.edad.value == "")
                    {
                        alert("Debe ingresar su edad");
                        document.formulario.fechaNac.focus();
                        return false;
                    } else if (document.formulario.edad.value > 100) {
                        alert("Ingrese una fecha de nacimiento válida (dd/mm/aaaa)");
                        document.formulario.fechaNac.focus();
                        return false;
                    }
                    if (document.formulario.doc.value == "")
                    {
                        alert("Debe elegir su documento de identidad");
                        document.formulario.doc.focus();
                        return false;
                    }
                    if (document.formulario.numDoc.value == "")
                    {
                        alert("Debe ingresar su número de documento de identidad");
                        document.formulario.numDoc.focus();
                        return false;
                    }
                    if (document.formulario.profesion.value == "")
                    {
                        alert("Debe ingresar su profesión");
                        document.formulario.profesion.focus();
                        return false;
                    }
                    if (document.formulario.cel.value == "")
                    {
                        alert("Debe ingresar su número celular");
                        document.formulario.cel.focus();
                        return false;
                    }
                    if (document.formulario.correo.value == "")
                    {
                        alert("Debe ingresar su correo electrónico");
                        document.formulario.correo.focus();
                        return false;
                    }
                    if (document.formulario.correo.value != "")
                    {
                        if (validateEmail(document.formulario.correo.value)) {

                        } else {
                            alert("Debe ingresar un correo válido");
                            document.formulario.correo.focus();
                            return false;
                        }


                    }
                    if (document.formulario.pais.value == "")
                    {
                        alert("Debe ingresar su país de residencia");
                        document.formulario.pais.focus();
                        return false;
                    }
                    if (document.formulario.dep.value == "")
                    {
                        alert("Debe ingresar su departamento de residencia");
                        document.formulario.dep.focus();
                        return false;
                    }
                    if (document.formulario.prov.value == "")
                    {
                        alert("Debe ingresar su provincia de residencia");
                        document.formulario.prov.focus();
                        return false;
                    }
                    if (document.formulario.dist.value == "")
                    {
                        alert("Debe ingresar su distrito de residencia");
                        document.formulario.dist.focus();
                        return false;
                    }
                    if (document.formulario.dir.value == "")
                    {
                        alert("Debe ingresar su dirección domiciliaria");
                        document.formulario.dir.focus();
                        return false;
                    }
                    if (document.formulario.telf.value == "")
                    {
                        alert("Debe ingresar su número de teléfono fijo");
                        document.formulario.telf.focus();
                        return false;
                    }
                    return(true);
                }
            </script>

            <script>
                function validateEmail(email) {
                    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                    return re.test(email);
                }
            </script>

            <script type="text/javascript">
                function enter(e) {
                    if (e.keyCode == 13) {
                        return false;
                    }
                }
            </script>
            <script type="text/javascript">
                function limitar()
                {
                    var nombre = document.getElementById('nombre');
                    var apellidoP = document.getElementById('apellidoP');
                    var apellidoM = document.getElementById('apellidoM');
                    var proNac = document.getElementById('proNac');
                    var numDoc = document.getElementById('numDoc');
                    var profesion = document.getElementById('profesion');
                    var correo = document.getElementById('correo');
                    var cel = document.getElementById('cel');
                    var prov = document.getElementById('prov');
                    var dist = document.getElementById('dist');
                    var dir = document.getElementById('dir');
                    var telf = document.getElementById('telf');

                    if (nombre.value.length < 0 || nombre.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        nombre.value = nombre.value.substring(0, 29);
                        return false;
                    } else if (apellidoP.value.length < 0 || apellidoP.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        apellidoP.value = apellidoP.value.substring(0, 29);
                        return false;
                    } else if (apellidoM.value.length < 0 || apellidoM.value.length > 29) {
                        alert("solo puede ingresar 30 caracteres");
                        apellidoM.value = apellidoM.value.substring(0, 29);
                        return false;
                    } else if (numDoc.value.length < 0 || numDoc.value.length > 14) {
                        alert("solo puede ingresar 15 caracteres");
                        numDoc.value = numDoc.value.substring(0, 14);
                        return false;
                    } else if (proNac.value.length < 0 || proNac.value.length > 49) {
                        alert("solo puede ingresar 50 caracteres");
                        proNac.value = proNac.value.substring(0, 49);
                        return false;
                    } else if (correo.value.length < 0 || correo.value.length > 49) {
                        alert("solo puede ingresar 50 caracteres");
                        correo.value = correo.value.substring(0, 49);
                        return false;
                    } else if (profesion.value.length < 0 || profesion.value.length > 49) {
                        alert("solo puede ingresar 50 caracteres");
                        profesion.value = profesion.value.substring(0, 49);
                        return false;
                    } else if (cel.value.length < 0 || cel.value.length > 14) {
                        alert("solo puede ingresar 15 caracteres");
                        cel.value = cel.value.substring(0, 14);
                        return false;
                    } else if (prov.value.length < 0 || prov.value.length > 99) {
                        alert("solo puede ingresar 100 caracteres");
                        prov.value = prov.value.substring(0, 99);
                        return false;
                    } else if (dist.value.length < 0 || dist.value.length > 99) {
                        alert("solo puede ingresar 100 caracteres");
                        dist.value = dist.value.substring(0, 99);
                        return false;
                    } else if (dir.value.length < 0 || dir.value.length > 199) {
                        alert("solo puede ingresar 200 caracteres");
                        dir.value = dir.value.substring(0, 199);
                        return false;
                    } else if (telf.value.length < 0 || telf.value.length > 14) {
                        alert("solo puede ingresar 15 caracteres");
                        telf.value = telf.value.substring(0, 14);
                        return false;
                    }
                }
            </script>
            <!-- Ubicar al final -->
    </body>
</html>
