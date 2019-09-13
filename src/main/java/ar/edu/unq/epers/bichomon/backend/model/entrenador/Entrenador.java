package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Ubicacion;

import javax.persistence.*;
import java.util.List;

@Entity
public class Entrenador {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Integer experiencia;
    private Integer nivel;

    @OneToMany(mappedBy="owner", cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Bicho> bichos;

    private Ubicacion ubicacion;


}
