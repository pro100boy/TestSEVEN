package com.seven.test.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seven.test.util.DateTimeUtil;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "company", callSuper = true)
@Entity
@EqualsAndHashCode(callSuper=true)
@Table(name = "report")
public class Report extends NamedEntity {
    @Column(name = "time", columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime date = LocalDateTime.now();

    @Column(name = "data", nullable = false)
    @NotBlank
    @SafeHtml
    @Length(min = 5)
    private String data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference(value = "company-reports")
    private Company company;
}
