package Wypozyczalnia;

import java.sql.Connection;
import java.util.List;

public class Tests {
    public static void main(String[] args){
        Main main = new Main();
        Car chosenSamochod = main.wybierzSamochod("B","Manual");
        main.generateInvoice(chosenSamochod);
//        System.out.println(main.getCars().getLast().getModel());
    }
}
