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
    @ManyToOne
    private Entrenador entrenadorC;
    @ManyToOne
    private Bicho bichoC;

    @ManyToOne( cascade = CascadeType.ALL)
    private RandomBichomon random ;
/*
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)  // no lo guardamos por que rompe el dao de entrenador
    private Set<Historial> historial ;
*/
    public Dojo(){
        super();
    }
    public Dojo(String name, RandomBichomon rb) {
        super(name);
        random = rb;
        //historial = new LinkedHashSet<>();
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
            //this.historial.add(historial);
            ResultadoCombate resultado =new ResultadoCombate(bichoC);
            return resultado;
        }
        if(entrenadorC.getNombre().equals(entrenador.getNombre())){
            throw new BichomonError("El campeon no puede desafiarse en su dojo");
        }
        if(!entrenadorC.tieneBichoConId(bichoC.getId())){
            entrenadorC = entrenador;
            bichoC = bichomon;
            Date fecha = new Date();
            Historial historial = new Historial (entrenadorC,bichoC,fecha);
            //this.historial.add(historial);
            ResultadoCombate resultado =new ResultadoCombate(bichoC);

            return resultado;
        }
        else{

            ResultadoCombate resultado = new ResultadoCombate(bichomon,bichoC);
            posibleCambioGanador(resultado,entrenador);
            entrenador.aumentarExpPorCombate();
            bichoC.aumentarEnergiaPorCombate();
            bichomon.aumentarEnergiaPorCombate();
            this.entrenadorC.aumentarExpPorCombate();
            return resultado;
        }

    }

    @Override
    public String getEntrenadorName() {
        return entrenadorC.getNombre();
    }

    @Override
    public String getBichomonName() {
        return bichoC.getEspecie().getNombre();
    }
    private void posibleCambioGanador(ResultadoCombate resultado, Entrenador retador) {/*
        Date fecha = new Date();
        if(resultado.getGanador().getOwner()==retador){
            Historial mod =(Historial) (historial.toArray()[ historial.size()-1 ]);
                    mod.setFechaFin(fecha);
            Historial historial = new Historial (retador,resultado.getGanador(),fecha);
            this.historial.add(historial);
        }*/
    }

}
