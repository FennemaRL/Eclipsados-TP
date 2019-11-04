package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBichomon;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Dojo extends Ubicacion{
    @ManyToOne(cascade = CascadeType.ALL)
    private Entrenador entrenadorCampeon;
    @ManyToOne(cascade = CascadeType.ALL)
    private Bicho bichoCampeon;

    @ManyToOne( cascade = CascadeType.ALL)
    private RandomBichomon random ;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)  // no lo guardamos por que rompe el dao de entrenador
    private Set<Historial> historial ;

    public Dojo(){
        super();
    }
    public Dojo(String name, RandomBichomon rb) {
        super(name);
        random = rb;
        historial = new LinkedHashSet<>();
    }

    public Dojo(String nombre) {
        this.nombreUbicacion = nombre;
    }

    @Override
    public Bicho capturar(Entrenador e) {
        Bicho br = null;
        Boolean busqueda = random.busquedaExitosa(e,this);
        Boolean contieneBicho= entrenadorCampeon != null && entrenadorCampeon.tieneBichoConId(bichoCampeon.getId());
        if( contieneBicho && busqueda){
            Especie esp = bichoCampeon.getEspecie().getEspecieRaiz();
            br = new Bicho(esp);

        }
        if (!contieneBicho){
            throw new DojoSinEntrenador("No se puede capturar en esta zona todavia ya que no posee campeon");
        }
        if(!busqueda){
            throw new CapturaFallida("tu capura ha sido fallida");
        }
        return br;
    }
    @Override
    public ResultadoCombate retar(Entrenador entrenador, Bicho bichomon){ //preguntar por esto en clase
        if(entrenadorCampeon == null || !entrenadorCampeon.tieneBichoConId(bichoCampeon.getId()) && ! entrenadorCampeon.getNombre().equals(entrenador.getNombre())){
            entrenadorCampeon = entrenador;
            bichoCampeon =bichomon;
            Date fecha = new Date();
            Historial historial = new Historial (entrenadorCampeon, bichoCampeon,fecha);
            this.historial.add(historial);
            return new ResultadoCombate(bichoCampeon);

        }
        System.out.print(null == null +"null es igual a null");
        if(entrenadorCampeon.getNombre().equals(entrenador.getNombre())){
            throw new BichomonError("El campeon "+ entrenadorCampeon.getNombre()+"no puede desafiarse en su dojo");
        }
        else{
            ResultadoCombate resultado = new ResultadoCombate(bichomon, bichoCampeon);
            entrenador.aumentarExpPorCombate();
            bichoCampeon.aumentarEnergiaPorCombate();
            bichomon.aumentarEnergiaPorCombate();
            this.entrenadorCampeon.aumentarExpPorCombate();
            posibleCambioGanador(resultado,entrenador);
            return resultado;
        }

    }

    @Override
    public String getEntrenadorName() {
        return entrenadorCampeon.getNombre();
    }

    @Override
    public Entrenador getCampeon(){
        return entrenadorCampeon;
    }

    @Override
    public Bicho getBichoCampeon() {
        if(bichoCampeon != null && entrenadorCampeon.tieneBichoConId(bichoCampeon.getId()))
            return bichoCampeon;
        else
             throw new DojoSinEntrenador("No se puede capturar en esta zona todavia ya que no posee campeon");
    }

    @Override
    public String getBichomonName() {
        return bichoCampeon.getEspecie().getNombre();
    }
    private void posibleCambioGanador(ResultadoCombate resultado, Entrenador retador) {
        Date fecha = new Date();
        if(resultado.getGanador().getOwner()==retador){
            Historial mod =(Historial) (historial.toArray()[ historial.size()-1 ]);
            mod.setFechaFin(fecha);
            Historial historial = new Historial (retador,resultado.getGanador(),fecha);
            entrenadorCampeon = retador;
            bichoCampeon = resultado.getGanador();
            this.historial.add(historial);
        }
    }

}
