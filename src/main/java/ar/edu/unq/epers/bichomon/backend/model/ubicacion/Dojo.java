package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBichomon;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBusqueda;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Dojo extends Ubicacion{
    @ManyToOne
    private Entrenador entrenadorC;
    @ManyToOne
    private Bicho bichoC;
    @ManyToOne( cascade = CascadeType.ALL)
    private RandomBichomon random ;
    @ManyToMany
    private List<Historial> historial = new ArrayList<>();

    public Dojo(){
        super();
    }
    public Dojo(String name, RandomBichomon rb) {
        super(name);
        random = rb;
    }

    @Override
    public Bicho capturar(Entrenador e) {
        Bicho br = null;
        if(entrenadorC != null && entrenadorC.tieneBichoConId(bichoC.getId()) && random.busquedaExitosa(e,this)){
            Especie esp = bichoC.getEspecie().getEspecieRaiz();
            br = new Bicho(esp);
            esp.incrementarEnUnBicho();
            return br;
        }
        else{
            throw new DojoSinEntrenador("No se puede capturar en esta zona todavia ya que no posee campeon");
        }
    }
    @Override
    public ResultadoCombate retar(Entrenador entrenador, Bicho bichomon){ //preguntar por esto en clase
        if(entrenadorC == null){
            entrenadorC = entrenador;
            bichoC =bichomon;
            Date fecha = new Date();
            Historial historial = new Historial (entrenadorC,bichoC,fecha);
            this.historial.add(historial);
            ResultadoCombate resultado =new ResultadoCombate(bichoC);

            return resultado;
        }
        if(!entrenadorC.tieneBichoConId(bichoC.getId())){
            entrenadorC = entrenador;
            bichoC = bichomon;
            Date fecha = new Date();
            Historial historial = new Historial (entrenadorC,bichoC,fecha);
            this.historial.add(historial);
            ResultadoCombate resultado =new ResultadoCombate(bichoC);

            return resultado;
        }
        else{

            ResultadoCombate resultado = new ResultadoCombate(bichomon,bichoC);
            posibleCambioGanador(resultado,entrenador);
            return resultado;
        }

    }

    private void posibleCambioGanador(ResultadoCombate resultado, Entrenador retador) {
        Date fecha = new Date();
        if(resultado.getGanador().getOwner()==retador){
            (historial.get(historial.size()-1)).setFechaFin(fecha);
            Historial historial = new Historial (retador,resultado.getGanador(),fecha);
            this.historial.add(historial);
        }
    }

}
