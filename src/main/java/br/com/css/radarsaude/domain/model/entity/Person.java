package br.com.css.radarsaude.domain.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(length = 200, nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 5, nullable = false)
    private Gender gender;

    @PastOrPresent(message = "Data de Nascimento é posterior a data atual")
    @Column(nullable = false)
    private LocalDate birthDate;

    @NotBlank(message = "Informe o numero do telefone.")
    @Column(length = 11, nullable = false)
    private String phoneNumber;

    @Email(message = "E-mail é obrigatório.")
    @Column(length = 50, nullable = false)
    private String email;

    @CreationTimestamp
    private LocalDateTime creationDate;
    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;


}
