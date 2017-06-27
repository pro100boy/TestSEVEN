package com.seven.test.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "report")
public class Report extends NamedEntity {
    @Column(name = "time", columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    private Date date = new Date();

    @Column(name = "data", nullable = false)
    @NotBlank
    @SafeHtml
    @Length(min = 1)
    private String data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    public Report() {
    }

    public Report(String name, Date date, String data) {
        this(null, name, date, data);
    }

    public Report(Report report) {
        this(report.getId(), report.getName(), report.getDate(), report.getData());
    }

    public Report(Integer id, String name, Date date, String data) {
        super(id, name);
        this.date = date;
        this.data = data;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + getId() +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
