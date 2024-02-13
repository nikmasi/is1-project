/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package klijent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 *
 * @author JA
 */
public class Klijent {
    public static Scanner input;
    
    private static void slanjeKonekcije(String URLadresa){
        try {
            URL url = new URL(URLadresa);
            HttpURLConnection konekcija = (HttpURLConnection) url.openConnection();
            konekcija.setRequestMethod("GET");
            konekcija.setDoOutput(false);

            BufferedReader in = null;
            String podatak = null;
            
            in = new BufferedReader(new InputStreamReader(konekcija.getInputStream(), Charset.forName("UTF-8")));
            String inputLine;
            
            System.out.println(URLadresa);
            while ((inputLine = in.readLine()) != null) 
                System.out.println(inputLine);
            
            in.close();
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
   
    
    private static void kreiranjeGrada(){
        System.out.println("KREIRANJE GRADA------------");
        System.out.print("Unesi naziv: ");
        String nazivMesta="";
        try {
            nazivMesta = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/mesto/kreirajMesto?naziv="+nazivMesta;
        slanjeKonekcije(URLadresa);
    }
    
    private static void kreiranjeKorisnika(){
        String ime="";
        String email = "";
        String godiste="";
        String pol="";
        String mesto="";
        System.out.println("KREIRANJE KORISNIKA------------");
         try {
            System.out.print("Unesi ime: ");
            ime = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi email: ");
            email = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi godiste: ");
            godiste = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi pol: ");
            pol = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.println("Unesi mesto:");
            mesto = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/korisnik/kreirajKorisnika?ime="+ime+"&email="+email+"&godiste="+godiste+"&pol="+pol+"&mesto="+mesto;
        slanjeKonekcije(URLadresa);
    }
    
    private static void promenaEmailKorisnika(){
        String id="";
        String email="";
        System.out.println("PROMENA EMAIL KORISNIKA------------");
        try {
            System.out.print("Unesi ID korisnika: ");
            id = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.println("Unesi novi email: ");
            email = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/korisnik/promeniEmail?id="+id+"&email="+email;
        slanjeKonekcije(URLadresa);
    }
    
    private static void promenaMestaKorisnika(){
        String id="";
        String mesto="";
        System.out.println("PROMENA MESTA KORISNIKA------------");
        try {
            System.out.print("Unesi ID korisnika: ");
            id = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.println("Unesi novo mesto: ");
            mesto = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/korisnik/promeniMesto?id="+id+"&mesto="+mesto;
        slanjeKonekcije(URLadresa);
    }
    
    private static void kreiranjeKategorije(){
        String naziv="";
        System.out.println("KREIRANJE KATEGORIJE------------");
        try {
            System.out.print("Unesi naziv kategorije: ");
            naziv = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/kategorija/kreirajKategoriju?naziv="+naziv;
        slanjeKonekcije(URLadresa);
    }
    
    private static void kreiranjeVideoSnimka(){
        String naziv="";
        String trajanje="";
        String vlasnik="";
        String datum="";
        System.out.println("KREIRANJE VIDEO SNIMKA------------");
        try {
            System.out.print("Unesi naziv video snimka: ");
            naziv = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi trajanje: ");
            trajanje = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi vlasnika: ");
            vlasnik = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi datum: ");
            datum = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/videoSnimak/kreirajVideoSnimak?naziv="+naziv+"&trajanje="+trajanje+"&vlasnik="+vlasnik+"&datum="+datum;
        slanjeKonekcije(URLadresa);
    }
    
    private static void dohvatiSvaMesta(){
        System.out.println("DOHVATANJE MESTA------------");
        String URLadresa = "http://localhost:8080/centralniServer/resources/mesto/dohvatiMesta";
        slanjeKonekcije(URLadresa);
    }
    
    private static void dohvatiSveKorisnike(){
        System.out.println("DOHVATANJE SVIH KORISNIKA------------");
        String URLadresa = "http://localhost:8080/centralniServer/resources/korisnik/dohvatiKorisnike";
        slanjeKonekcije(URLadresa);
    }
    
    private static void promenaNazivaVideoSnimka(){
        String naziv="";
        String id="";
        System.out.println("PROMENA NAZIVA VIDEO SNIMKA------------");
        try {
            System.out.print("Unesi id video snimka: ");
            id = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi novi naziv video snimka: ");
            naziv = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/videoSnimak/promenaNazivaVideoSnimka?id="+id+"&naziv="+naziv;
        slanjeKonekcije(URLadresa);
    }
    
    private static void dodavanjeKategorijeVideoSnimku(){
        String idV="";
        String idK="";
        System.out.println("DODAVANJE KATEGORIJE VIDEO SNIMKU------------");
        try {
            System.out.print("Unesi id video snimka: ");
            idV = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi id kategorije: ");
            idK = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/videoSnimak/dodavanjeKategorijeVideoSnimku?idV="+idV+"&idK="+idK;
        slanjeKonekcije(URLadresa);
    }
    
    private static void dohvatiSveKategorije(){
        System.out.println("DOHVATANJE SVE KATEGORIJE------------");
        String URLadresa = "http://localhost:8080/centralniServer/resources/kategorija/dohvatiKategorije";
        slanjeKonekcije(URLadresa);
    }
    
    private static void dohvatiSveVideoSnimke(){
        System.out.println("DOHVATANJE SVE VIDEO SNIMKE------------");
        String URLadresa = "http://localhost:8080/centralniServer/resources/videoSnimak/dohvatiVideoSnimke";
        slanjeKonekcije(URLadresa);
    }
    
    private static void dohvatiKategorijuZaVideoSnimak(){
        System.out.println("DOHVATANJE KATEGORIJE ZA VIDEO SNIMAK------------");
        String idV="";
        try {
            System.out.print("Unesi id video snimka: ");
            idV = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/pripada/dohvatiKategorije?idV="+idV;
        slanjeKonekcije(URLadresa);
    }
    
    private static void kreiranjePaketa(){
        System.out.println("KREIRANJE PAKETA------------");
        String cena="";
        try {
            System.out.print("Unesi cenu: ");
            cena = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/paket/kreirajPaket?cena="+cena;
        slanjeKonekcije(URLadresa);
    }
    
    private static void promenaCenePaketa(){
        System.out.println("PROMENA CENE PAKETA------------");
        String cena="";
        String id="";
        try {
            System.out.println("Unesi id paketa: ");
            id = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi cenu: ");
            cena = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/paket/promenaCenePaketa?id="+id+"&cena="+cena;
        slanjeKonekcije(URLadresa);
    }
    
    private static void dohvatiSvePakete(){
        System.out.println("DOHVATANJE SVIH PAKETA------------");
        String URLadresa = "http://localhost:8080/centralniServer/resources/paket/dohvatiPakete";
        slanjeKonekcije(URLadresa);
    }
    
    private static void kreirajPretplatu(){
        System.out.println("KREIRANJE PRETPLATE------------");
        String cena="";
        String idK="";
        String idP="";
        String datum="";
        try {
            System.out.print("Unesi id korisnika: ");
            idK = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi id paketa: ");
            idP = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi cenu: ");
            cena = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi datum: ");
            datum = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/pretplata/kreirajPretplatu?idK="+idK+"&idP="+idP+"&cena="+cena+"&datum="+datum;
        slanjeKonekcije(URLadresa);
    }
    
    private static void dohvatiSvePretplate(){
        String idK="";
        System.out.println("DOHVATANJE SVIH PRETPLATA------------");
        try {
            System.out.print("Unesi id korisnika: ");
            idK = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        }catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/pretplata/dohvatiPretplate?idK="+idK;
        slanjeKonekcije(URLadresa);
    }
    
    private static void kreirajGledanje(){
        System.out.println("KREIRANJE GLEDA------------");
        String sekZap="";
        String sekOdg="";
        String idK="";
        String idV="";
        String datum="";
        try {
            System.out.print("Unesi id korisnika: ");
            idK = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi id video snimka: ");
            idV = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi datum: ");
            datum = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi zapocetu sekundu: ");
            sekZap = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi odgledane sekunde: ");
            sekOdg = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/gleda/kreirajGledanje?idK="+idK+"&idV="+idV+"&sekZap="+sekZap+"&sekOdg="+sekOdg+"&datum="+datum;
        slanjeKonekcije(URLadresa);
    }
    
    private static void kreirajOcenu(){
        System.out.println("KREIRANJE OCENE------------");
        String ocena="";
        String idK="";
        String idV="";
        String datum="";
        try {
            System.out.print("Unesi id korisnika: ");
            idK = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi id video snimka: ");
            idV = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi datum: ");
            datum = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi ocenu: ");
            ocena = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/ocena/kreirajOcenu?idK="+idK+"&idV="+idV+"&ocena="+ocena+"&datum="+datum;
        slanjeKonekcije(URLadresa);
    }
    
    private static void menjajOcenu(){
        System.out.println("MENJANJE OCENE------------");
        String ocena="";
        String idK="";
        String idV="";
        try {
            System.out.print("Unesi id korisnika: ");
            idK = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi id video snimka: ");
            idV = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi ocenu: ");
            ocena = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/ocena/promeniOcenu?idK="+idK+"&idV="+idV+"&ocena="+ocena;
        slanjeKonekcije(URLadresa);
    }
    
    private static void brisanjeOcene(){
        System.out.println("BRISANJE OCENE------------");
        String idK="";
        String idV="";
        try {
            System.out.print("Unesi id korisnika: ");
            idK = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi id video snimka: ");
            idV = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/ocena/obrisiOcenu?idK="+idK+"&idV="+idV;
        slanjeKonekcije(URLadresa);
    }
    
    private static void brisanjaVideoSnimka(){
        System.out.println("BRISANJE VIDEO SNIMKA------------");
        String idK="";
        String idV="";
        try {
            System.out.print("Unesi id video snimka: ");
            idV = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
            System.out.print("Unesi id korisnika: ");
            idK = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/videoSnimak/obrisivideoSnimak?idV="+idV+"&idK="+idK;
        slanjeKonekcije(URLadresa);
    }
    
    private static void dohvatiSveOcene(){
        System.out.println("DOHVATANJE SVE OCENE------------");
        String idV="";
        try {
            System.out.print("Unesi id video snimka: ");
            idV = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/ocena/dohvatiOcene?idV="+idV;
        slanjeKonekcije(URLadresa);
    }
    
    private static void dohvatiSvaGledanja(){
        System.out.println("DOHVATANJE SVA GLEDANJA------------");
        String idV="";
        try {
            System.out.print("Unesi id video snimka: ");
            idV = URLEncoder.encode(input.nextLine(), StandardCharsets.UTF_8.toString());

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Klijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        String URLadresa = "http://localhost:8080/centralniServer/resources/gleda/dohvatiGledanja?idV="+idV;
        slanjeKonekcije(URLadresa);
    }
    
    public static void main(String[] args) {
        input = new Scanner(System.in);
        
        while (true) {      
            MeniUtil.printMeni();
            System.out.print("Unesi broj: ");
            int task = input.nextInt();
            input.nextLine(); 
            
            switch (task) {
                case 1:
                    kreiranjeGrada();
                    break;
                case 2:
                    kreiranjeKorisnika();
                    break;
                case 3:
                    promenaEmailKorisnika();
                    break;
                case 4:
                    promenaMestaKorisnika();
                    break;
                case 5:
                    kreiranjeKategorije();
                    break;
                case 6:
                    kreiranjeVideoSnimka();
                    break;
                case 7:
                    promenaNazivaVideoSnimka();
                    break;
                case 8:
                    dodavanjeKategorijeVideoSnimku();
                    break;
                case 9:
                    kreiranjePaketa();
                    break;
                case 10:
                    promenaCenePaketa();
                    break;
                case 11:
                    kreirajPretplatu();
                    break;
                case 12:
                    kreirajGledanje();
                    break;
                case 13:
                    kreirajOcenu();
                    break;
                case 14:
                    menjajOcenu();
                    break;
                case 15:
                    brisanjeOcene();
                    break;
                case 16:
                    brisanjaVideoSnimka();
                    break;
                case 17:
                    dohvatiSvaMesta();
                    break;
                case 18:
                    dohvatiSveKorisnike();
                    break;
                case 19:
                    dohvatiSveKategorije();
                    break;
                case 20:
                    dohvatiSveVideoSnimke();
                    break;
                case 21:
                    dohvatiKategorijuZaVideoSnimak();
                    break;
                case 22:
                    dohvatiSvePakete();
                    break;
                case 23:
                    dohvatiSvePretplate();
                    break;
                case 24:
                    dohvatiSvaGledanja();
                    break;
                case 25:
                    dohvatiSveOcene();
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }
}