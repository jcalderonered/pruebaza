<%-- 
    Document   : info_fam
    Created on : 7/11/2013, 11:46:35 AM
    Author     : Ayner Pérez
--%>

<%@page import="com.mimp.bean.Entidad"%>
<%
    response.setHeader("Pragma", "no-cache");
    response.addHeader("Cache-Control", "must-revalidate");
    response.addHeader("Cache-Control", "no-cache");
    response.addHeader("Cache-Control", "no-store");
    response.setDateHeader("Expires", 0);
    Entidad u = (Entidad) request.getSession().getAttribute("usuario");
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
                            <span class="icon-bar"></span>
                        </button>

                    </div>
                    <div class="navbar-collapse collapse">
                        <ul class="nav navbar-nav navbar-left">
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/inicioEnt">Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/salir">Salir</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-3 ">
                        <ul class="nav nav-list well">
                            <li><a href="${pageContext.servletContext.contextPath}/inicioEnt"><span class="glyphicon glyphicon-home"></span> Inicio</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/listaFam"><span class="glyphicon glyphicon-chevron-right"></span> Listado de familias</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/contraEnt"><span class="glyphicon glyphicon-chevron-right"></span> Cambio Contraseña</a></li>               
                        </ul>
                    </div>

                    <div class="col-md-8 col-md-offset-1">
                        <p align="right"><button onclick="location.href = '${pageContext.servletContext.contextPath}/listaFam'" id="singlebutton" name="singlebutton" style="background: black; color: white" class="btn btn-default">Volver</button></p>
                        <br>
                        <h1 align="center"><strong>Familia "${ElAdop.getApellidoP()} - ${LaAdop.getApellidoP()}"</strong></h1>
                        <br>
                        <br>
                        <h3 align="left"><strong>Datos del Expediente</strong></h3>
                        <br>
                        <br>
                        <ul class="nav nav-tabs row" id="tabs" >
                            <li><a href="${pageContext.servletContext.contextPath}/ElaAdop?idInfo=${idInfo}" data-toggle="tab">La Solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/ElAdop?idInfo=${idInfo}" data-toggle="tab">El solicitante</a></li>
                            <li><a href="${pageContext.servletContext.contextPath}/infoNNA?idInfo=${idInfo}" data-toggle="tab">Antecedentes del NNA</a></li>
                            <li class="active"><a href="${pageContext.servletContext.contextPath}/infoExp?idInfo=${LaAdop.getInfoFamilia().getIdinfoFamilia()}" data-toggle="tab">Información del Expediente</a></li>
                        </ul>
                        <br>
                        <!--A PARTIR DE AQUÍ COLOCAR EL CONTENIDO-->
                        <h3><strong>Información del Expediente</strong></h3>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Número</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="${InfoExp.getNumero()}" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Expediente</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="${ElAdop.getApellidoP()}  ${LaAdop.getApellidoP()}" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">HT</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="${InfoExp.getHt()}" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Número de Expediente</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="${InfoExp.getNumeroExpediente()}" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Fecha ingreso a DGA</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="${InfoExp.getFechaIngresoDga()}" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Estado</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="${InfoExp.getEstado()}" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">TUPA</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="${InfoExp.getTupa()}" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Nacionalidad</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="${InfoExp.getNacionalidad()}" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Pertenece al Registro Nacional de Solicitantes para la Adopción: 
                                <c:if test="${InfoExp.getRnsa() == '0' }"  >
                                    SI
                                </c:if>
                                <c:if test="${InfoExp.getRnsa() == '1' }"  >
                                    NO
                                </c:if>
                            </label>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Pertenece al Registro Nacional de Adoptantes para la Adopción: 
                                <c:if test="${InfoExp.getRnaa() == '0' }"  >
                                    SI
                                </c:if>
                                <c:if test="${InfoExp.getRnaa() == '1' }"  >
                                    NO
                                </c:if>
                            </label>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Tipo de lista de espera</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="${InfoExp.getTipoListaEspera()}" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Unidad de Adopción donde se realiza el trámite</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="${InfoExp.getUnidad().getNombre()}" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <br>
                        <div class="control-group">
                            <label class="control-label">Organismo Acreditado y/o Autoridad Central asociado</label>
                            <div class="controls">
                                <input disabled id="full-name" name="full-name" value="${InfoExp.getFamilia().getEntidad().getNombre()}" type="text" class="input-xlarge">
                            </div>
                        </div>
                        <!--FIN DE CONTENIDO-->
                    </div>
                </div>
            </div>
            <br>
            <br>
            <div id="footer">
                <div id="ja-footer" class="wrap">
                    <hr width=80% align="center">
                    <p align="center"><h5 class="caption" align="center" style="text-align: center;">MINISTERIO DE LA MUJER Y POBLACIONES VULNERABLES<br>Jr. Camaná 616, Lima - Perú<br>Central telefónica: (511) 626-1600</h5></p>
                </div>
            </div>
    </body>
</html>
