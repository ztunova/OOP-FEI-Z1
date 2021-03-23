package sk.stuba.fei.uim.oop.hra;

import sk.stuba.fei.uim.oop.hraciaPlocha.Nehnutelnost;
import sk.stuba.fei.uim.oop.hraciaPlocha.Policko;
import sk.stuba.fei.uim.oop.hraciaPlocha.RohovePolicko;
import sk.stuba.fei.uim.oop.hraciaPlocha.Sance;

import java.util.ArrayList;
import java.util.Random;

public class NovaHra {
    Zklavesnice vstup;
    Random random;
    int pocetHracov;
    ArrayList<Hrac> zoznamHracov;
    ArrayList<Policko> sachovnica;
    //ArrayList<Sance> tahaciBalicek;
    //ArrayList<Sance> odhadzovaciBalicek;
    //kocka?

    private void nacitajHracov(){
        this.pocetHracov= vstup.readInt("zadaj pocet hracov: ");
        this.zoznamHracov= new ArrayList<Hrac>(pocetHracov);

        System.out.println("bude hrat "+pocetHracov+ " hracov");

        String meno;
        int suma= 5000;

        for(int i= 0; i< pocetHracov; i++){
            Hrac novyHrac= new Hrac();
            meno= vstup.readString("zadaj meno " +(i+1) + ". hraca: ");
            novyHrac.setMeno(meno);
            novyHrac.setSuma(suma);

            zoznamHracov.add(novyHrac);
        }

    }

    private void generSachovnicu(){
        this.sachovnica= new ArrayList<Policko>(24);
        String[] mena = {"Start", "Vazenie", "Policia", "Platba dane"};
        String[] popisy = {"Presiel si startom, dostanes ", "Stojis 1 kolo", "Stojis ", "Musis zaplatit dan "};
        int ktory= 0;

        double suma, stojne;
        Policko novePolicko;

        for(int i= 0; i< 24; i++){
            if(i% 6 == 0){
                if(i== 0){
                    //cena pri prechode startom
                    suma= 2000;
                    novePolicko= new RohovePolicko(i, mena[ktory], popisy[ktory], suma);
                }
                else if (i == 18){
                    //platba dane
                    suma= 900;
                    novePolicko= new RohovePolicko(i, mena[ktory], popisy[ktory], suma);
                }
                else {
                    novePolicko = new RohovePolicko(i, mena[ktory], popisy[ktory]);
                }
                ktory++;
            }
            else if (i% 6 == 3){
                novePolicko= new Sance(i, "Sanca", "Tahas si kartu");
            }
            else{
                suma= random.nextInt(5000)+1000;
                stojne= random.nextInt(2000)+500;
                novePolicko= new Nehnutelnost(i, "Nehnutelnost", "Policko nehnutelnosti", suma, stojne);
            }

            sachovnica.add(novePolicko);
        }
    }

    public void generKarty(){}

    public void zacniHru(){
        generSachovnicu();
        nacitajHracov();

        int i, hodKockou, novaPoz;
        int pocitadlo= 0;
        Hrac naTahu;
        Policko stojiNa;

        for (i= 0; i< 6; i++){
            naTahu= zoznamHracov.get(pocitadlo%pocetHracov);
            System.out.println("Na tahu je hrac: " +naTahu.getMeno());
            System.out.println("Zostatok na ucte: "+ naTahu.getUcet());
            System.out.println("Aktualna pozicia: "+ naTahu.getPozicia());

            hodKockou= random.nextInt(5)+1;
            System.out.println("Na kocke padlo: " +hodKockou);

            novaPoz= naTahu.posunSa(hodKockou);
            stojiNa= sachovnica.get(novaPoz%24);
            stojiNa.setStojiTam(naTahu);
            System.out.println("Nova pozicia: "+ naTahu.getPozicia());
            System.out.println("Policko "+ stojiNa.getMeno());

            if (stojiNa instanceof Nehnutelnost){
                stojiNa.akciaPolicka();
            }
            else{
                System.out.println("Nestojis na nehnutelnosti");
            }

            pocitadlo++;
            System.out.println("Zostatok na ucte: "+ naTahu.getUcet());
            System.out.println("-----------------------------------------");
        }
    }

    public NovaHra(){
        this.vstup= new Zklavesnice();
        this.random= new Random();
    }
}
