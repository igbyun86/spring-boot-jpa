package com.ig.jpa.entity;

import javax.persistence.*;

@Entity
public class CodeDtl {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)       //strategy = GenerationType.AUTO 생략가능
    @GeneratedValue
    private Long id;

    @Column(name = "CD")
    private String cd;

    @Column(name = "CD_DTL")
    private String cdDtl;

    @Column(name = "CD_DTL_NM")
    private String cdDtlNm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getCdDtl() {
        return cdDtl;
    }

    public void setCdDtl(String cdDtl) {
        this.cdDtl = cdDtl;
    }

    public String getCdDtlNm() {
        return cdDtlNm;
    }

    public void setCdDtlNm(String cdDtlNm) {
        this.cdDtlNm = cdDtlNm;
    }
}
