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
                            <li><a href="${pageContext.servletContext.contextPath}/SesionInfInicio">Inscripción a Sesión Informativa</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/CronogramaAnual">Ver el cronograma anual</a></li>
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
                        <br>
                        <h2 align="center"><strong>Cronograma de Sesiones Informativas</strong></h2>
                        <br>
                        <div class="bs-example">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>Fecha de Inscripción</th>
                                                <th>Fecha de Sesión Informativa</th>
                                            </tr>
                                        </thead>
                                        <c:if test="${listaSesiones != null}">   
                                        <tbody>
                                            <c:forEach var="sesion" items="${listaSesiones}" varStatus="status">
                                                <tr>
                                                    <td>
                                                        Sesion ${status.count}
                                                    </td>
                                                    <td>
                                                        <c:if test="${!sesion.getTurnos().isEmpty()}">
                                                            <c:forEach var="turno" items="${sesion.getTurnos()}" varStatus="status">
                                                                    <c:if test="${turno.getInicioInscripcion() != null}">
                                                                         <c:set var="fechaInicio" value="${turno.getInicioInscripcion()}"/>
                                                                    </c:if>
                                                            </c:forEach>
                                                            <c:forEach var="turno" items="${sesion.getTurnos()}" varStatus="status">
                                                                    <c:if test="${turno.getInicioInscripcion() != null && turno.getInicioInscripcion() < fechaInicio}">
                                                                         <c:set var="fechaInicio" value="${turno.getInicioInscripcion()}"/>
                                                                    </c:if>
                                                            </c:forEach>
                                                        </c:if>
                                                        <c:if test="${fechaInicio != null && !sesion.getTurnos().isEmpty()}">
                                                                 ${df.dateToString(fechaInicio)}
                                                        </c:if>
                                                        <c:if test="${sesion.getTurnos().isEmpty()}">
                                                                 No se han programado turnos de inscripción
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        ${sesion.getFecha() != null ? df.dateToString(sesion.getFecha()) : ''}
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        </c:if> 
                                      <c:if test="${listaSesiones == null}">
                                        <h3><strong>No existen Sesiones Informativas Programadas</strong></h3>
                                      </c:if> 
                                    </table>
                                </div>
                        <br>
                        <h2 align="center"><strong>Cronograma de Talleres</strong></h2>
                        <h4> (Requisito: haber asistido a la Sesión Informativa)</h4>
                        <br>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>Taller</th>
                                        <th>Grupo</th>
                                        <th>Detalles</th>
                                        <th>Clausura</th>
                                    </tr>
                                </thead>
                                <c:if test="${!listaTalleres.isEmpty()}">
                                    <c:set var="idTaller" value="0"/>
                                    <tbody>
                                        <c:forEach var="taller" items="${listaTalleres}" varStatus="status1">
                                            <c:if test="${!taller.getGrupos().isEmpty()}">
                                                <c:set var="numFilasTaller" value="${taller.getGrupos().size()}"/>
                                            </c:if>
                                                 
                                             <c:if test="${!taller.getGrupos().isEmpty()}">  
                                            <c:forEach var="grupo" items="${taller.getGrupos()}" varStatus="status2">
                                                <c:choose>
                                                    <c:when test="${idTaller != taller.getIdtaller()}">
                                                        <c:set var="idTaller" value="${taller.getIdtaller()}"/>
                                                        <tr>
                                                            <td rowspan="${numFilasTaller}" style="vertical-align: middle;"> 
                                                                ${taller.getNombre()}
                                                             </td>
                                                            <td style="vertical-align: middle;">${grupo.getNombre()}</td>
                                                            <td>
                                                                <c:if test="${!grupo.getTurno2s().isEmpty()}">
                                                                    
                                                                    <c:forEach var="turno2" items="${grupo.getTurno2s()}" varStatus="status3">
                                                                        <c:if test="${!turno2.getReunions().isEmpty()}">
                                                                            
                                                                            <c:forEach var="reunion" items="${turno2.getReunions()}" varStatus="status4">
                                                                                <c:if test="${reunion.getFecha() != null}">
                                                                                Día : ${df.dateToString(reunion.getFecha())} &nbsp; Hora : ${reunion.getHora()}
                                                                                <br>
                                                                                </c:if>
                                                                            </c:forEach>
                                                                        </c:if>
                                                                        
                                                                    </c:forEach>
                                                                </c:if>
                                                            </td>
                                                            <td rowspan="${numFilasTaller}" style="vertical-align: middle;"> 
                                                                <c:set var="ultFecha" value="0"/>  
                                                                <c:if test="${!taller.getGrupos().isEmpty()}">  
                                                                    <c:forEach var="grupoCont" items="${taller.getGrupos()}" varStatus="status2">
                                                                        <c:if test="${!grupoCont.getTurno2s().isEmpty()}">
                                                                            <c:forEach var="turno2Cont" items="${grupoCont.getTurno2s()}" varStatus="status3">
                                                                                <c:if test="${!turno2Cont.getReunions().isEmpty()}">
                                                                                    <c:forEach var="reunionCont" items="${turno2Cont.getReunions()}" varStatus="status4">
                                                                                        <c:if test="${reunionCont.getFecha() != null && ultFecha < reunionCont.getFecha() }">
                                                                                            <c:set var="ultFecha" value="${reunionCont.getFecha()}"/>
                                                                                        </c:if>
                                                                                    </c:forEach>
                                                                                </c:if>
                                                                            </c:forEach>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:if>    
                                                                ${df.dateToString(ultFecha)}
                                                             </td>
                                                        </tr>
                                                    </c:when>
                                                    <c:when test="${idTaller == taller.getIdtaller()}">
                                                        <tr>
                                                            <td style="vertical-align: middle;">${grupo.getNombre()}</td>
                                                            <td>
                                                                <c:if test="${!grupo.getTurno2s().isEmpty()}">
                                                                    <c:forEach var="turno2" items="${grupo.getTurno2s()}" varStatus="status3">
                                                                        <c:if test="${!turno2.getReunions().isEmpty()}">
                                                                            <c:forEach var="reunion" items="${turno2.getReunions()}" varStatus="status4">
                                                                                <c:if test="${reunion.getFecha() != null}">
                                                                                Día : ${df.dateToString(reunion.getFecha())} &nbsp; Hora : ${reunion.getHora()}
                                                                                <br>
                                                                                </c:if>
                                                                            </c:forEach>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:when>    
                                                </c:choose>       
                                            </c:forEach>
                                         </c:if>               
                                        </c:forEach> 
                                    </tbody>
                                </c:if>   
                                <c:if test="${listaTalleres.isEmpty()}">
                                    <h3><strong>No existen Talleres Programados</strong></h3>
                                </c:if>
                            </table>
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
            <!-- core JavaScript
    ================================================== -->
            <script type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/jquery-1.10.2.min.js"></script> 
            <script  type="text/javascript" src="${pageContext.servletContext.contextPath}/assets/js/bootstrap.js"></script>
            <!-- Ubicar al final -->
    </body>
</html>
