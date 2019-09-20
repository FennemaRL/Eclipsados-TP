package ar.edu.unq.epers.bichomon.backend.service.data;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

import java.util.ArrayList;

public class DataServiceImp implements DataService {
    private final EspecieDAO dao;

    public DataServiceImp (EspecieDAO dao){
        this.dao = dao;
    }
    @Override
    public void eliminarDatos() {
        this.dao.restart();
    }

    @Override
    public void crearSetDatosIniciales() {
        Especie red = new Especie( "Rojomon", TipoBicho.FUEGO);
        red.setAltura(180);
        red.setPeso(75);
        red.setEnergiaIncial(100);
        red.setUrlFoto("/image/rojomon.jpg");

        Especie amarillo = new Especie( "Amarillomon", TipoBicho.ELECTRICIDAD);
        amarillo.setAltura(170);
        amarillo.setPeso(69);
        amarillo.setEnergiaIncial(300);
        amarillo.setUrlFoto("/image/amarillomon.png");

        Especie green = new Especie( "Verdemon", TipoBicho.PLANTA);
        green.setAltura(150);
        green.setPeso(55);
        green.setEnergiaIncial(5000);
        green.setUrlFoto("/image/verdemon.jpg");

        Especie turronmon = new Especie( "Tierramon", TipoBicho.TIERRA);
        turronmon.setAltura(1050);
        turronmon.setPeso(99);
        turronmon.setEnergiaIncial(5000);
        turronmon.setUrlFoto("/image/tierramon.jpg");

        Especie fantasmon = new Especie( "Fantasmon", TipoBicho.AIRE);
        fantasmon.setAltura(1050);
        fantasmon.setPeso(99);
        fantasmon.setEnergiaIncial(5000);
        fantasmon.setUrlFoto("/image/fantasmon.jpg");

        Especie vampiron = new Especie("Vanpiron", TipoBicho.AIRE);
        vampiron.setAltura(1050);
        vampiron.setPeso(99);
        vampiron.setEnergiaIncial(5000);
        vampiron.setUrlFoto("/image/vampiromon.jpg");

        Especie fortmon = new Especie( "Fortmon", TipoBicho.CHOCOLATE);
        fortmon.setAltura(1050);
        fortmon.setPeso(99);
        fortmon.setEnergiaIncial(5000);
        fortmon.setUrlFoto("/image/fortmon.png");

        Especie dientemon = new Especie( "Dientemon", TipoBicho.AGUA);
        dientemon.setAltura(1050);
        dientemon.setPeso(99);
        dientemon.setEnergiaIncial(5000);
        dientemon.setUrlFoto("/image/dientmon.jpg");

        ArrayList<Especie> especiesB = new ArrayList<>();

        especiesB.add(red);especiesB.add(amarillo);especiesB.add(green);especiesB.add(turronmon);especiesB.add(fantasmon);
        especiesB.add(vampiron);especiesB.add(fortmon);especiesB.add(dientemon);
        ArrayList<Especie> especiesA = new ArrayList<>();
        /*especiesA.addAll(dao.recuperarTodos());

        * Preguntar por el caso en el que alguno del los datos exita en el dao que hacer
        */
        especiesB.forEach(especie -> {dao.guardar(especie);});

    }
}
