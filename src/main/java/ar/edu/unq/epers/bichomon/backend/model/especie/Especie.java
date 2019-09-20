package ar.edu.unq.epers.bichomon.backend.model.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Representa una {@link Especie} de bicho.
 * 
 * @author Charly Backend
 */
@Entity
public class Especie {
	@Id
	@GeneratedValue
	private Integer id;
	private String nombre;
	private int altura;
	private int peso;
	private TipoBicho tipo;

	private int energiaInicial;
	
	private String urlFoto;
	
	private int cantidadBichos;


	//variables temporales a resolver posteriormente para determinar si puede evolucionar
	private int nivelEvolucionActual;
	private Integer nivelDeEnergiaNecesario;
	private Integer cantidadDeVictoriasNecesarias;
	private Integer nivelDelEntrenadorNecesario;
	private DateTime fechaDeCaptura ;

	//especie raiz
	@OneToOne
	private Especie especieRaiz;
	@OneToOne
	private Especie evo = null;

	public Especie(){
	}
	
	public Especie(int id, String nombre, TipoBicho tipo) {
	    this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.nivelEvolucionActual = 1;
		especieRaiz = this;
	}
	public Especie( String nombre, TipoBicho tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
		especieRaiz = this;
		this.evo = null;
	}
	
	/**
	 * @return el nombre de la especie (por ejemplo: Perromon)
	 */
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return la altura de todos los bichos de esta especie
	 */
	public int getAltura() {
		return this.altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	/**
	 * @return el peso de todos los bichos de esta especie
	 */
	public int getPeso() {
		return this.peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	/**
	 * @return una url que apunta al un recurso imagen el cual será
	 * utilizado para mostrar un thumbnail del bichomon por el frontend.
	 */
	public String getUrlFoto() {
		return this.urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	/**
	 * @return la cantidad de energia de poder iniciales para los bichos
	 * de esta especie.
	 */
	public int getEnergiaInicial() {
		return this.energiaInicial;
	}
	public void setEnergiaIncial(int energiaInicial) {
		this.energiaInicial = energiaInicial;
	}

	/**
	 * @return el tipo de todos los bichos de esta especie
	 */
	public TipoBicho getTipo() {
		return this.tipo;
	}
	public void setTipo(TipoBicho tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return la cantidad de bichos que se han creado para esta
	 * especie.
	 */
	public int getCantidadBichos() {
		return this.cantidadBichos;
	}
	public void setCantidadBichos(int i) {
		this.cantidadBichos = i;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Bicho crearBicho(String nombreBicho){
		this.cantidadBichos++;
		return new Bicho(this);
	}

	public void setCondicionesEvolucion(Integer energia, Integer victorias, Integer lvlEntrenador, DateTime fechaCaptura){
		this.nivelDeEnergiaNecesario = energia;
		this.cantidadDeVictoriasNecesarias = victorias;
		this.nivelDelEntrenadorNecesario = lvlEntrenador;
		this.fechaDeCaptura = fechaCaptura;
	}

	public Boolean tieneSiguienteEvolucion() {
	 return	this.nivelEvolucionActual < 3 ;
	}

	public boolean cumpleCondicion(Bicho bicho) {

		return (bicho.getEnergia()>nivelDeEnergiaNecesario ||
				bicho.getVictorias()> cantidadDeVictoriasNecesarias ||
				bicho.getFechaCaptura().isBefore(fechaDeCaptura) ||
				bicho.getNivelEntrenador()> nivelDelEntrenadorNecesario);
	}
	public void setEspecieRaiz(Especie e){
		especieRaiz= e;
	}
    public Especie getEspecieRaiz() {
		return (especieRaiz.nombre == this.nombre)? especieRaiz: especieRaiz.getEspecieRaiz();

    }

    public void incrementarEnUnBicho() {
		cantidadBichos = cantidadBichos +1;
    }
	public void setEspecieEvo(Especie evo){this.evo = evo;}
}
