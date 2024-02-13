/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem2;

import entiteti.Kategorija;
import entiteti.Korisnik;
import entiteti.Mesto;
import entiteti.Pripada;
import entiteti.PripadaPK;
import entiteti.Videosnimak;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author JA
 */
public class MainPodsistem2 {

    /**
     * @param args the command line arguments
     */
    
    @Resource(lookup = "myConnectionFactory")
    public static ConnectionFactory cf;
    
    @Resource(lookup = "q1")
    public static Queue myQueue;
    
    @Resource(lookup = "q2")
    public static Queue myQueue2;
    
    @Resource(lookup = "cq")
    public static Queue centralQueue;
    
    private static Kategorija kreirajKategoriju(String naziv){
        Kategorija kategorija=new Kategorija();
        kategorija.setNaziv(naziv);
        return kategorija;
    }
    
    private static Videosnimak kreirajVideoSnimak(String naziv,String trajanje,Korisnik vlasnik,String datum){
        Videosnimak videosnimak=new Videosnimak();
        videosnimak.setNaziv(naziv);
        videosnimak.setTrajanje(Integer.valueOf(trajanje));
        videosnimak.setVlasnik(vlasnik);
        videosnimak.setDatum(Integer.valueOf(datum));
        return videosnimak;
    }
    
    
    public static void main(String[] args) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(myQueue2);
        JMSProducer producer = context.createProducer();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsistem2PU");
        EntityManager em = emf.createEntityManager();
        
        
        while(true){
            try {
                Message msg = consumer.receive();
                TextMessage txtMsg = (TextMessage)msg;
                System.out.println(txtMsg.getText());
                
                int task = txtMsg.getIntProperty("task");
                switch(task){
                    case 5:{
                        System.out.println("Kreiraj kategoriju");
                        String naziv = txtMsg.getStringProperty("strProp1");
                        Kategorija kategorija = kreirajKategoriju(naziv);
                        if(kategorija==null){
                            TextMessage errMsg=context.createTextMessage("kategorija null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            try {
                                em.getTransaction().begin();
                                em.persist(kategorija);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("Kategorija kreirana!");
                            System.out.println("Kategorija id:" + kategorija.getIdkategorija()+" naziv:"+kategorija.getNaziv() + " je kreirana");
                            System.out.println("----------");
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("Kategorija id:" + kategorija.getIdkategorija()+" naziv:"+kategorija.getNaziv() + " je kreirana");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();
                            objMsg.setObject(zaCentralni);
                            producer.send(centralQueue, objMsg);
                        }
                        break;
                    }
                    case 6:{
                        System.out.println("Kreiraj video snimak");
                        String naziv = txtMsg.getStringProperty("strProp1");
                        String trajanje =txtMsg.getStringProperty("strProp2");
                        String vlasnik=txtMsg.getStringProperty("strProp3");
                        String datum=txtMsg.getStringProperty("strProp4");
                        
                        List<Korisnik> korisnik = em.createNamedQuery("Korisnik.findByIme", Korisnik.class).setParameter("ime", vlasnik).getResultList();
                        Korisnik k = (korisnik.isEmpty()? null : korisnik.get(0));
                        if(k==null){
                            TextMessage errMsg=context.createTextMessage("korisnik null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            Videosnimak videosnimak=kreirajVideoSnimak(naziv,trajanje,k,datum);
                            
                            try {
                                em.getTransaction().begin();
                                em.persist(videosnimak);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("Video Snimak kreiran!");
                            System.out.println("Video snimak id:" + videosnimak.getIdvideosnimak()+" naziv:"+videosnimak.getNaziv() +"vlasnik: "+videosnimak.getVlasnik()+ 
                                    "datum: "+videosnimak.getDatum()+" trajanje: "+videosnimak.getTrajanje()+" je kreirana");
                            System.out.println("----------");
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("Video snimak id:" + videosnimak.getIdvideosnimak()+" naziv:"+videosnimak.getNaziv() +"vlasnik: "+videosnimak.getVlasnik()+ 
                                    "datum: "+videosnimak.getDatum()+" trajanje: "+videosnimak.getTrajanje()+" je kreirana");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();
                            objMsg.setObject(zaCentralni);
                            producer.send(centralQueue, objMsg);
                            
                        }
                        break;
                    }
                    case 7:{
                        System.out.println("Promena nazivaa video snimka");
                        String id = txtMsg.getStringProperty("strProp1");
                        String naziv =txtMsg.getStringProperty("strProp2");

                        List<Videosnimak> videi = em.createNamedQuery("Videosnimak.findByIdvideosnimak", Videosnimak.class).setParameter("idvideosnimak", Integer.valueOf(id)).getResultList();
                        Videosnimak v = (videi.isEmpty()? null : videi.get(0));
                        if(v==null){
                            TextMessage errMsg=context.createTextMessage("video null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            try {
                                em.getTransaction().begin();
                                v.setNaziv(naziv);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("Video Snimak izmenjen!");
                            System.out.println("Video snimak id:" + v.getIdvideosnimak()+" naziv:"+v.getNaziv() +"vlasnik: "+v.getVlasnik()+ 
                                    "datum: "+v.getDatum()+" trajanje: "+v.getTrajanje()+" je izmenjen");
                            System.out.println("----------");
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("Video snimak id:" + v.getIdvideosnimak()+" naziv:"+v.getNaziv() +"vlasnik: "+v.getVlasnik()+ 
                                    "datum: "+v.getDatum()+" trajanje: "+v.getTrajanje()+" je izmenjen");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();
                            objMsg.setObject(zaCentralni);
                            producer.send(centralQueue, objMsg);
                        }
                        break;
                    }
                    case 8:{
                        System.out.println("Dodavanje kategorije video snimku");
                        String idV = txtMsg.getStringProperty("strProp1");
                        String idK =txtMsg.getStringProperty("strProp2");

                        List<Videosnimak> videi = em.createNamedQuery("Videosnimak.findByIdvideosnimak", Videosnimak.class).setParameter("idvideosnimak", Integer.valueOf(idV)).getResultList();
                        Videosnimak v = (videi.isEmpty()? null : videi.get(0));
                        
                        List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findByIdkategorija", Kategorija.class).setParameter("idkategorija", Integer.valueOf(idV)).getResultList();
                        Kategorija k = (kategorije.isEmpty()? null : kategorije.get(0));
                        
                        if(v==null || k==null){
                            TextMessage errMsg=context.createTextMessage("video ili kategorija je null ");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            PripadaPK pk=new PripadaPK();
                            pk.setVideoSnimakP(v.getIdvideosnimak());
                            pk.setKategorijaP(k.getIdkategorija());
                            Pripada p=new Pripada();
                            p.setPripadaPK(pk);
                            
                            try {
                                em.getTransaction().begin();
                                em.persist(p);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            
                            System.out.println("Pripada kreirana!");
                            System.out.println("----------");
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("Pripada  idV:"  +p.getPripadaPK().getVideoSnimakP()+" idK: "+p.getPripadaPK().getKategorijaP()+ " je kreirana");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();
                            objMsg.setObject(zaCentralni);
                            producer.send(centralQueue, objMsg);
                        }
                        break;
                    }
                    case 16:{
                        System.out.println("Obrisi video snimak");
                        String idV = txtMsg.getStringProperty("strProp1");
                        String idK=txtMsg.getStringProperty("strProp2");
                        
                        //List<Videosnimak> videi = em.createNamedQuery("Videosnimak.findByKorisnikAndVideo", Videosnimak.class).
                          //      setParameter("idvideosnimak", Integer.valueOf(idV)).setParameter("vlasnik", Integer.valueOf(idK))
                          //    .getResultList();
                          List<Videosnimak> videi = em.createNamedQuery("Videosnimak.findByIdvideosnimak", Videosnimak.class).
                                setParameter("idvideosnimak", Integer.valueOf(idV))
                              .getResultList();
                        Videosnimak k = (videi.isEmpty()? null : videi.get(0));
                        if(k==null){
                            TextMessage errMsg=context.createTextMessage("video  je null ");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                             try {
                                em.getTransaction().begin();
                                em.remove(k);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("video obrisan!");
                            System.out.println("----------");
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("video "  + " je obrisan");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();
                            objMsg.setObject(zaCentralni);
                            producer.send(centralQueue, objMsg);
                        }
                        
                        break;
                    }
                    case 19:{
                        System.out.println("Dohvatanje svih kategorija");
                        List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findAll", Kategorija.class).getResultList();
                        for (Kategorija kategorija : kategorije) {
                            System.out.println(kategorija.getIdkategorija()+" "+kategorija.getNaziv());
                        }
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        for(Kategorija kategorija : kategorije){
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add(kategorija.getIdkategorija()+"");
                            poruka.add(kategorija.getNaziv());
                            zaCentralni.add(poruka);
                        }
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        System.out.println("----------");
                        break;
                    }
                    case 20:{
                        System.out.println("Dohvatanje svih video snimaka");
                        List<Videosnimak> videi = em.createNamedQuery("Videosnimak.findAll", Videosnimak.class).getResultList();
                        for (Videosnimak videosnimak : videi) {
                            System.out.println(videosnimak.getIdvideosnimak()+" "+videosnimak.getNaziv()+" "+videosnimak.getTrajanje()+" "+videosnimak.getVlasnik()+" "+videosnimak.getDatum());
                        }
                       
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                         ArrayList<String> poruka = new ArrayList<>();
                        for(Videosnimak videosnimak : videi){
                            poruka.add(videosnimak.getIdvideosnimak()+"");
                            poruka.add(videosnimak.getNaziv()+"");
                            poruka.add(videosnimak.getTrajanje()+"");
                            poruka.add(videosnimak.getVlasnik()+"");
                            poruka.add(videosnimak.getDatum()+"");
                            zaCentralni.add(poruka);
                        }
                        
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        System.out.println("----------");
                        break;
                    }
                    case 21:{
                        System.out.println("Dohvatanje kategorija za video snimaka");
                        String idV = txtMsg.getStringProperty("strProp1");
                        List<Pripada> videi = em.createNamedQuery("Pripada.findByVideoSnimakP", Pripada.class).setParameter("videoSnimakP", Integer.valueOf(idV)).getResultList();
                        for (Pripada v : videi) {
                            System.out.println(v.getPripadaPK().getKategorijaP()+" "+v.getPripadaPK().getVideoSnimakP());
                        }

                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        for(Pripada v : videi){
                            poruka.add(v.getPripadaPK().getVideoSnimakP()+" ");
                            poruka.add(v.getPripadaPK().getKategorijaP()+" ");
                           
                            zaCentralni.add(poruka);
                        }
                        
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        System.out.println("----------");
                        break;
                    }

                    default:{
                        System.out.println("Nepoznat ID operacije");
                    }
                }
            } catch (JMSException ex) {
                Logger.getLogger(MainPodsistem2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
