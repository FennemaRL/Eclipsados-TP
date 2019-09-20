package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import org.joda.time.DateTime;
import org.joda.time.JodaTimePermission;

import javax.persistence.*;
import javax.validation.constraints.Null;

/**
 * Un {@link Bicho} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Especie} en particular.
 * 
 * @author Charly Backend
 */
@Entity
public class Bicho {
	@Id
	@GeneratedValue
	private Integer id;
	private String nombre;
	@ManyToOne
	private Especie especie;
	private int energiaDeCombate;
	private DateTime fechaCaptura;
	private Integer victorias;
	@ManyToOne
	private Entrenador owner;

	public Bicho(){}

	//constructor creado para testear la busqueda por id
	public Bicho(Especie especie){
		this.especie = especie;
		this.id = id;
		victorias = 0;
	}

	/**
	 * @return el nombre de un bicho (todos los bichos tienen
	 * nombre). Este NO es el nombre de su especie.
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * @return la especie a la que este bicho pertenece.
	 */
	public Especie getEspecie() {
		return this.especie;
	}
	
	/**
	 * @return la cantidad de puntos de energia de este bicho en
	 * particular. Dicha cantidad crecerá (o decrecerá) conforme
	 * a este bicho participe en combates contra otros bichomones.
	 */
	public int getEnergia() { return this.energiaDeCombate;	}
	public void setEnergia(int energia) {
		this.energiaDeCombate = energia;
	}

	public Integer getId() {
		return this.id;
	}

    public boolean puedeEvolucionar() {
		return ( especie.puedeEvolucionar(this));
    }

	public Integer getVictorias() { return this.victorias;}

	public DateTime getFechaCaptura() { return this.fechaCaptura; }


	public Integer getNivelEntrenador() {
		return owner.getNivel();
	}

	public void setFechaCaptura(DateTime unaFecha) {
		this.fechaCaptura = unaFecha;
	}

	public void setOwner(Entrenador entrenador) {
		this.owner = entrenador;
	}
}
