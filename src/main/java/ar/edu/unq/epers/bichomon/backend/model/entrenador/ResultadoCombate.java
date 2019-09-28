package ar.edu.unq.epers.bichomon.backend.model.entrenador;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.ubicacion.Turno;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResultadoCombate {


    private Bicho nuevoCampeon = null;
    private Bicho nuevoPerdedor = null;
    private List<Turno> turnos ;

    public ResultadoCombate() {
    }

    public ResultadoCombate(Bicho bRetador, Bicho bCampeon) {
        turnos = new ArrayList<>();
        int round = 0;
        int danioHechoARetador = 0;
        int danioHechoACampeon = 0;
        while (round <= 20 &&
                danioHechoARetador <= bRetador.getEnergia() ||
                danioHechoACampeon <= bCampeon.getEnergia()) {
            if (0 == round % 2) {
                int dañoact = (int) (bRetador.getEnergia() * Math.random() * (1.01 - 0.49));
                danioHechoACampeon += dañoact;
                turnos.add(new Turno(bRetador,dañoact));
            }
            if (1 == round % 2) {
                int dañoact = (int) (bCampeon.getEnergia() * Math.random() * (1.01 - 0.49));
                danioHechoARetador += dañoact;
                turnos.add(new Turno(bCampeon,dañoact));
            }
            round++;
        }
        if (danioHechoACampeon >= bCampeon.getEnergia()) {
            nuevoCampeon = bRetador;
            nuevoPerdedor = bCampeon;
        } else {
            nuevoCampeon = bCampeon;
            nuevoPerdedor = bRetador;
        }

    }

    public ResultadoCombate(Bicho bichoC) {
        this.nuevoCampeon = bichoC;
    }

    public Bicho getGanador() { return this.nuevoCampeon;
    }
    public List getTurnos(){
        return this.turnos;
    }
}
