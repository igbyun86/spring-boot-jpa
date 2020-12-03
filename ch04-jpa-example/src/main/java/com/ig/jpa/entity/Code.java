package com.ig.jpa.entity;

import javax.persistence.*;

@Entity
@Table(name = "T_CODE")
@TableGenerator(
        name = "CODE_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnName = "CODE_SEQ"
        ,allocationSize = 1
)
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "CODE_SEQ_GENERATOR")
    private Long id;

    private String cd;

    private String cdNm;

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

    public String getCdNm() {
        return cdNm;
    }

    public void setCdNm(String cdNm) {
        this.cdNm = cdNm;
    }
}
