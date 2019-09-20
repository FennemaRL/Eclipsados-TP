package ar.edu.unq.epers.bichomon.backend.model.ubicacion;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.random.RandomBusqueda;

import javax.persistence.*;

@Entity
public class Dojo extends Ubicacion{
    @ManyToOne
    private Entrenador entrenadorC;
    @ManyToOne
    private Bicho bichoC;
    @Transient
    private RandomBusqueda random ;

    public Dojo(String name, RandomBusqueda r) {
        super(name);
        random = r;
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
    public void retar(Entrenador entrenador,Bicho bichomon){ //preguntar por esto en clase
        if(entrenadorC == null){
            entrenadorC = entrenador;
            bichoC =bichomon;
        }
        if(!entrenadorC.tieneBichoConId(bichoC.getId())){
            entrenadorC = entrenador;
            bichoC = bichomon;
        }
        else{
            //duelo con toda la wea esa ;
        }

    }

}
