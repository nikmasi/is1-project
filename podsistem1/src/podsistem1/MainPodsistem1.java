/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem1;

import entiteti.Korisnik;
import entiteti.Mesto;
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
public class MainPodsistem1 {

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
    
    
     public static Mesto kreirajMesto(String ime){
        Mesto mesto = new Mesto();
        mesto.setNaziv(ime);
        return mesto;
    }
     
    public static Korisnik kreirajKorisnika(String ime,String email,int godiste,String pol,Mesto mesto){
        Korisnik korisnik=new Korisnik();
        korisnik.setIme(ime);
        korisnik.setEmail(email);
        korisnik.setPol(pol);
        korisnik.setGodiste(godiste);
        korisnik.setMesto(mesto);
        return korisnik;
    }
     
    public static void main(String[] args) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(myQueue);
        JMSProducer producer = context.createProducer();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsistem1PU");
        EntityManager em = emf.createEntityManager();
        
        while(true){
            try {
                Message msg = consumer.receive();
                TextMessage txtMsg = (TextMessage)msg;
                System.out.println(txtMsg.getText());
                
                int task = txtMsg.getIntProperty("task");
                switch(task){
                    case 1:{
                        System.out.println("Kreiraj mesto");
                        String ime = txtMsg.getStringProperty("strProp1");
                        Mesto mesto = kreirajMesto(ime);
                        System.out.println(mesto);
                        if(mesto==null){
                            TextMessage errMsg=context.createTextMessage("mesto null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            try {
                                em.getTransaction().begin();
                                em.persist(mesto);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("Mesto kreirano!");
                            System.out.println("----------");
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("Mesto id:" + mesto.getIdmesto()+" naziv:"+ime + " je kreirano");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();
                            objMsg.setObject(zaCentralni);
                            producer.send(centralQueue, objMsg);
                        }
                        break;
                    }
                    case 2:{
                        System.out.println("Kreiraj korisnika");
                        String ime=txtMsg.getStringProperty("strProp1");
                        String email=txtMsg.getStringProperty("strProp2");
                        String godiste=txtMsg.getStringProperty("strProp3");
                        String pol=txtMsg.getStringProperty("strProp4");
                        String mesto=txtMsg.getStringProperty("strProp5");    
                        
                        List<Mesto> mesta = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", mesto).getResultList();
                        Mesto m = (mesta.isEmpty()? null : mesta.get(0));
                        if(m==null){
                            TextMessage errMsg=context.createTextMessage("mesto null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            Korisnik korisnik=kreirajKorisnika(ime, email,Integer.valueOf(godiste) ,pol, m);
                            
                            try {
                                em.getTransaction().begin();
                                em.persist(korisnik);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            
                            System.out.println("Korisnik kreiran!");
                            System.out.println("----------");
                        
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("Korisnik id:" +korisnik.getIdKorisnik()+" ime: "+korisnik.getIme()+" pol: "+
                                    korisnik.getPol()+"godiste: "+korisnik.getGodiste() +"mesto: "+korisnik.getMesto().getIdmesto() + " je kreiran");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();
                            objMsg.setObject(zaCentralni);
                            producer.send(centralQueue, objMsg);
                        }
                        break;
                    }
                    case 3:{
                        System.out.println("Promena email-a korisnika");
                        String id=txtMsg.getStringProperty("strProp1");
                        String email=txtMsg.getStringProperty("strProp2");
                        
                        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKorisnik", Korisnik.class).setParameter("idKorisnik", Integer.valueOf(id)).getResultList();
                        Korisnik k = (korisnici.isEmpty()? null : korisnici.get(0));
                        if(k==null){
                            TextMessage errMsg=context.createTextMessage("korisnik null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            try {
                                em.getTransaction().begin();
                                k.setEmail(email);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("Korisniku izmenjen email!");
                            System.out.println("----------");
                        
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("Korisnik id:" +k.getIdKorisnik()+" ime: "+k.getIme()+" pol: "+
                                    k.getPol()+"godiste: "+k.getGodiste() +"mesto: "+k.getMesto().getIdmesto() + " je izmenjen email");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();

                            producer.send(centralQueue, objMsg);
                        }

                        break;
                    }
                    case 4:{
                        System.out.println("Promena mesta korisniku");
                        String id=txtMsg.getStringProperty("strProp1");
                        String mesto=txtMsg.getStringProperty("strProp2");
                        
                        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdKorisnik", Korisnik.class).setParameter("idKorisnik", Integer.valueOf(id)).getResultList();
                        Korisnik k = (korisnici.isEmpty()? null : korisnici.get(0));
                        
                        List<Mesto> mes = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", mesto).getResultList();
                        Mesto m=(mes.isEmpty()?null:mes.get(0));
       
                        if(k==null || m==null){
                            TextMessage errMsg=context.createTextMessage("korisnik ili mesto je null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            try {
                                em.getTransaction().begin();
                                k.setMesto(m);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("Korisnik izmenjen mesto!");
                            System.out.println("----------");
                        
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("Korisnik id:" +k.getIdKorisnik()+" ime: "+k.getIme()+" pol: "+
                                    k.getPol()+"godiste: "+k.getGodiste() +"mesto: "+k.getMesto().getIdmesto() + " je izmenjeno mesto");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();

                            producer.send(centralQueue, objMsg);
                            
                        }
                        break;
                    }
                    
                    
                    case 17:{
                        System.out.println("Dohvati mesta");
                        TypedQuery<Mesto> mestaQuery = em.createNamedQuery("Mesto.findAll", Mesto.class);
                        List<Mesto> resultList = mestaQuery.getResultList();
                        for (Mesto mesto : resultList) System.out.println(mesto.getNaziv());
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        for(Mesto m: resultList){
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add(m.getIdmesto()+"");
                            poruka.add(m.getNaziv());
                            zaCentralni.add(poruka);
                        }
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        System.out.println("----------");
                        break;
                    }
                    case 18:{
                        System.out.println("Dohvati korisnike");
                        TypedQuery<Korisnik> korisnikQuery = em.createNamedQuery("Korisnik.findAll", Korisnik.class);
                        List<Korisnik> resultList = korisnikQuery.getResultList();
                        for (Korisnik kor : resultList) System.out.println(kor.getIme());
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        for(Korisnik k: resultList){
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add(k.getIdKorisnik()+"");
                            poruka.add(k.getIme());
                            poruka.add(k.getEmail());
                            poruka.add(String.valueOf(k.getGodiste()));
                            poruka.add(k.getPol());
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
                Logger.getLogger(MainPodsistem1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
    }
    
    
}
