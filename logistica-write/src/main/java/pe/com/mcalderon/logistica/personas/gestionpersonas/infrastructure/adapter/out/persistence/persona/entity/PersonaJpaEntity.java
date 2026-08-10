package pe.com.mcalderon.logistica.personas.gestionpersonas.infrastructure.adapter.out.persistence.persona.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(
        name = "persona",
        schema = "public"
)
public class PersonaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "PersonaId",
            nullable = false
    )
    private Long personaId;

    protected PersonaJpaEntity() {
        // Constructor requerido por JPA.
    }

    public Long personaId() {
        return personaId;
    }
}