package pe.com.mcalderon.logistica.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArquitecturaHexagonalTest {

    private final JavaClasses clases =
            new ClassFileImporter()
                    .importPackages("pe.com.mcalderon.logistica");


    /*
     * ==========================================================
     * DOMAIN
     * ==========================================================
     */

    @Test
    void dominio_no_debe_depender_de_infraestructura() {

        ArchRule regla = noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..infrastructure..");

        regla.check(clases);
    }


    @Test
    void dominio_no_debe_depender_de_application() {

        ArchRule regla = noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..application..");

        regla.check(clases);
    }


    @Test
    void dominio_no_debe_depender_de_spring() {

        ArchRule regla = noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "org.springframework.."
                );

        regla.check(clases);
    }


    @Test
    void dominio_no_debe_depender_de_jpa() {

        ArchRule regla = noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "jakarta.persistence.."
                );

        regla.check(clases);
    }


    /*
     * ==========================================================
     * APPLICATION
     * ==========================================================
     */

    @Test
    void application_no_debe_depender_de_infraestructura() {

        ArchRule regla = noClasses()
                .that()
                .resideInAPackage("..application..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..infrastructure..");

        regla.check(clases);
    }


    @Test
    void application_no_debe_depender_de_spring() {

        ArchRule regla = noClasses()
                .that()
                .resideInAPackage("..application..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "org.springframework.."
                );

        regla.check(clases);
    }


    @Test
    void application_no_debe_depender_de_jpa() {

        ArchRule regla = noClasses()
                .that()
                .resideInAPackage("..application..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "jakarta.persistence.."
                );

        regla.check(clases);
    }


    /*
     * ==========================================================
     * CQRS - INFRASTRUCTURE
     * ==========================================================
     */




    /*
     * ==========================================================
     * VERTICAL SLICING
     * ==========================================================
     */

    @Test
    void slices_de_application_no_deben_depender_entre_si() {

        String[] slices = {

                // WRITE - Usuario

                "..application.crearusuario..",
                "..application.modificarusuario..",
                "..application.dardebajausuario..",
                "..application.eliminarlogicamenteusuario..",
                "..application.eliminarfisicamenteusuario..",

                // READ - Usuario

                "..application.consultarusuarioporid..",
                "..application.consultarusuarios..",

                // Capability de Persona utilizada por WRITE

                "..application.existepersona.."
        };

        for (String sliceOrigen : slices) {

            for (String sliceDestino : slices) {

                if (sliceOrigen.equals(sliceDestino)) {
                    continue;
                }

                ArchRule regla = noClasses()
                        .that()
                        .resideInAPackage(sliceOrigen)
                        .should()
                        .dependOnClassesThat()
                        .resideInAPackage(sliceDestino);

                regla.check(clases);
            }
        }
    }
}