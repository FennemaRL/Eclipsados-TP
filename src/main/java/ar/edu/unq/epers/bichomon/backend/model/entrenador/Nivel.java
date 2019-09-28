package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import java.util.ArrayList;
import java.util.List;

public class Nivel {
    private List<Integer> niveles;

    public Nivel (List<Integer> niveles){//la lista tiene que estar ordenada de menor a mayor
        this.niveles = niveles;

    }

    public int getNivel(int experiencia){
        int contador = 0 ;
        while(! (contador == niveles.size()-1) && experiencia>niveles.get(contador)){
            contador += 1;
        }
        if (contador == 0)
            return 1;
        else
            return contador;
    }


}
