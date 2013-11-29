package com.mimp.bean;
// Generated 29/11/2013 03:12:05 PM by Hibernate Tools 3.6.0



/**
 * EvalLegal generated by hbm2java
 */
public class EvalLegal  implements java.io.Serializable {


     private Integer idevalLegal;
     private Evaluacion evaluacion;
     private Boolean solicitudDga;
     private String solicitudDgaObs;
     private Boolean evalSocial;
     private String evalSocialObs;
     private Boolean evalPsicologica;
     private String evalPsicologicaObs;
     private Boolean certDomiciliario;
     private String certDomiciliarioObs;
     private Boolean partidaMatrimonio;
     private String partidaMatrimonioObs;
     private Boolean sentenciaDivorcio;
     private String sentenciaDivorcioObs;
     private Boolean partDefuncCony;
     private String partDefuncConyObs;
     private Boolean partNacHijBio;
     private String partNacHijBioObs;
     private Boolean partNacHijAdop;
     private String partNacHijAdopObs;
     private Boolean reportSegPostAdopDga;
     private String reportSegPostAdopDgaObs;
     private Boolean fotografiaAdopHogar;
     private String fotografiaAdopHogarObs;
     private Boolean compromisoPostAdop;
     private String compromisoPostAdopObs;

    public EvalLegal() {
    }

	
    public EvalLegal(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }
    public EvalLegal(Evaluacion evaluacion, Boolean solicitudDga, String solicitudDgaObs, Boolean evalSocial, String evalSocialObs, Boolean evalPsicologica, String evalPsicologicaObs, Boolean certDomiciliario, String certDomiciliarioObs, Boolean partidaMatrimonio, String partidaMatrimonioObs, Boolean sentenciaDivorcio, String sentenciaDivorcioObs, Boolean partDefuncCony, String partDefuncConyObs, Boolean partNacHijBio, String partNacHijBioObs, Boolean partNacHijAdop, String partNacHijAdopObs, Boolean reportSegPostAdopDga, String reportSegPostAdopDgaObs, Boolean fotografiaAdopHogar, String fotografiaAdopHogarObs, Boolean compromisoPostAdop, String compromisoPostAdopObs) {
       this.evaluacion = evaluacion;
       this.solicitudDga = solicitudDga;
       this.solicitudDgaObs = solicitudDgaObs;
       this.evalSocial = evalSocial;
       this.evalSocialObs = evalSocialObs;
       this.evalPsicologica = evalPsicologica;
       this.evalPsicologicaObs = evalPsicologicaObs;
       this.certDomiciliario = certDomiciliario;
       this.certDomiciliarioObs = certDomiciliarioObs;
       this.partidaMatrimonio = partidaMatrimonio;
       this.partidaMatrimonioObs = partidaMatrimonioObs;
       this.sentenciaDivorcio = sentenciaDivorcio;
       this.sentenciaDivorcioObs = sentenciaDivorcioObs;
       this.partDefuncCony = partDefuncCony;
       this.partDefuncConyObs = partDefuncConyObs;
       this.partNacHijBio = partNacHijBio;
       this.partNacHijBioObs = partNacHijBioObs;
       this.partNacHijAdop = partNacHijAdop;
       this.partNacHijAdopObs = partNacHijAdopObs;
       this.reportSegPostAdopDga = reportSegPostAdopDga;
       this.reportSegPostAdopDgaObs = reportSegPostAdopDgaObs;
       this.fotografiaAdopHogar = fotografiaAdopHogar;
       this.fotografiaAdopHogarObs = fotografiaAdopHogarObs;
       this.compromisoPostAdop = compromisoPostAdop;
       this.compromisoPostAdopObs = compromisoPostAdopObs;
    }
   
    public Integer getIdevalLegal() {
        return this.idevalLegal;
    }
    
    public void setIdevalLegal(Integer idevalLegal) {
        this.idevalLegal = idevalLegal;
    }
    public Evaluacion getEvaluacion() {
        return this.evaluacion;
    }
    
    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }
    public Boolean getSolicitudDga() {
        return this.solicitudDga;
    }
    
    public void setSolicitudDga(Boolean solicitudDga) {
        this.solicitudDga = solicitudDga;
    }
    public String getSolicitudDgaObs() {
        return this.solicitudDgaObs;
    }
    
    public void setSolicitudDgaObs(String solicitudDgaObs) {
        this.solicitudDgaObs = solicitudDgaObs;
    }
    public Boolean getEvalSocial() {
        return this.evalSocial;
    }
    
    public void setEvalSocial(Boolean evalSocial) {
        this.evalSocial = evalSocial;
    }
    public String getEvalSocialObs() {
        return this.evalSocialObs;
    }
    
    public void setEvalSocialObs(String evalSocialObs) {
        this.evalSocialObs = evalSocialObs;
    }
    public Boolean getEvalPsicologica() {
        return this.evalPsicologica;
    }
    
    public void setEvalPsicologica(Boolean evalPsicologica) {
        this.evalPsicologica = evalPsicologica;
    }
    public String getEvalPsicologicaObs() {
        return this.evalPsicologicaObs;
    }
    
    public void setEvalPsicologicaObs(String evalPsicologicaObs) {
        this.evalPsicologicaObs = evalPsicologicaObs;
    }
    public Boolean getCertDomiciliario() {
        return this.certDomiciliario;
    }
    
    public void setCertDomiciliario(Boolean certDomiciliario) {
        this.certDomiciliario = certDomiciliario;
    }
    public String getCertDomiciliarioObs() {
        return this.certDomiciliarioObs;
    }
    
    public void setCertDomiciliarioObs(String certDomiciliarioObs) {
        this.certDomiciliarioObs = certDomiciliarioObs;
    }
    public Boolean getPartidaMatrimonio() {
        return this.partidaMatrimonio;
    }
    
    public void setPartidaMatrimonio(Boolean partidaMatrimonio) {
        this.partidaMatrimonio = partidaMatrimonio;
    }
    public String getPartidaMatrimonioObs() {
        return this.partidaMatrimonioObs;
    }
    
    public void setPartidaMatrimonioObs(String partidaMatrimonioObs) {
        this.partidaMatrimonioObs = partidaMatrimonioObs;
    }
    public Boolean getSentenciaDivorcio() {
        return this.sentenciaDivorcio;
    }
    
    public void setSentenciaDivorcio(Boolean sentenciaDivorcio) {
        this.sentenciaDivorcio = sentenciaDivorcio;
    }
    public String getSentenciaDivorcioObs() {
        return this.sentenciaDivorcioObs;
    }
    
    public void setSentenciaDivorcioObs(String sentenciaDivorcioObs) {
        this.sentenciaDivorcioObs = sentenciaDivorcioObs;
    }
    public Boolean getPartDefuncCony() {
        return this.partDefuncCony;
    }
    
    public void setPartDefuncCony(Boolean partDefuncCony) {
        this.partDefuncCony = partDefuncCony;
    }
    public String getPartDefuncConyObs() {
        return this.partDefuncConyObs;
    }
    
    public void setPartDefuncConyObs(String partDefuncConyObs) {
        this.partDefuncConyObs = partDefuncConyObs;
    }
    public Boolean getPartNacHijBio() {
        return this.partNacHijBio;
    }
    
    public void setPartNacHijBio(Boolean partNacHijBio) {
        this.partNacHijBio = partNacHijBio;
    }
    public String getPartNacHijBioObs() {
        return this.partNacHijBioObs;
    }
    
    public void setPartNacHijBioObs(String partNacHijBioObs) {
        this.partNacHijBioObs = partNacHijBioObs;
    }
    public Boolean getPartNacHijAdop() {
        return this.partNacHijAdop;
    }
    
    public void setPartNacHijAdop(Boolean partNacHijAdop) {
        this.partNacHijAdop = partNacHijAdop;
    }
    public String getPartNacHijAdopObs() {
        return this.partNacHijAdopObs;
    }
    
    public void setPartNacHijAdopObs(String partNacHijAdopObs) {
        this.partNacHijAdopObs = partNacHijAdopObs;
    }
    public Boolean getReportSegPostAdopDga() {
        return this.reportSegPostAdopDga;
    }
    
    public void setReportSegPostAdopDga(Boolean reportSegPostAdopDga) {
        this.reportSegPostAdopDga = reportSegPostAdopDga;
    }
    public String getReportSegPostAdopDgaObs() {
        return this.reportSegPostAdopDgaObs;
    }
    
    public void setReportSegPostAdopDgaObs(String reportSegPostAdopDgaObs) {
        this.reportSegPostAdopDgaObs = reportSegPostAdopDgaObs;
    }
    public Boolean getFotografiaAdopHogar() {
        return this.fotografiaAdopHogar;
    }
    
    public void setFotografiaAdopHogar(Boolean fotografiaAdopHogar) {
        this.fotografiaAdopHogar = fotografiaAdopHogar;
    }
    public String getFotografiaAdopHogarObs() {
        return this.fotografiaAdopHogarObs;
    }
    
    public void setFotografiaAdopHogarObs(String fotografiaAdopHogarObs) {
        this.fotografiaAdopHogarObs = fotografiaAdopHogarObs;
    }
    public Boolean getCompromisoPostAdop() {
        return this.compromisoPostAdop;
    }
    
    public void setCompromisoPostAdop(Boolean compromisoPostAdop) {
        this.compromisoPostAdop = compromisoPostAdop;
    }
    public String getCompromisoPostAdopObs() {
        return this.compromisoPostAdopObs;
    }
    
    public void setCompromisoPostAdopObs(String compromisoPostAdopObs) {
        this.compromisoPostAdopObs = compromisoPostAdopObs;
    }




}


