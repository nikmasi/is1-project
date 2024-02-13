/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package podsistem3;

//import entiteti.Gleda;
import entiteti.Gleda;
import entiteti.Korisnik;
import entiteti.Ocena;
import entiteti.OcenaPK;
import entiteti.Paket;
import entiteti.Pretplata;
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

/**
 *
 * @author JA
 */
public class MainPodsistem3 {

    /**
     * @param args the command line arguments
     */
    @Resource(lookup = "myConnectionFactory")
    public static ConnectionFactory cf;
    
    @Resource(lookup = "q1")
    public static Queue myQueue;
    
    @Resource(lookup = "q2")
    public static Queue myQueue2;
    
    @Resource(lookup = "q3")
    public static Queue myQueue3;
    
    @Resource(lookup = "cq")
    public static Queue centralQueue;
    
    private static Paket kreirajPaket(String cena){
        Paket paket=new Paket();
        paket.setCena(Integer.valueOf(cena));
        return paket;
    }
    private static Pretplata kreirajPretplatu(Korisnik idK,Paket idP,String cena,String datum){
        Pretplata pretplata=new Pretplata();
        pretplata.setCena(cena);
        pretplata.setDatum(datum);
        pretplata.setIkorisnik(idK);
        pretplata.setPaket(idP);
        return pretplata;
    }
    
    private static Gleda kreirajGleda(Korisnik k,Videosnimak v,String sekZap,String sekOdg,String datum){
        Gleda gleda=new Gleda();
        gleda.setSekOdg(Integer.valueOf(sekOdg));
        gleda.setSekZap(Integer.valueOf(sekZap));
        gleda.setDatum(Integer.valueOf(datum));
        gleda.setIdkorisnik(k);
        gleda.setIdvideosnimak(v);
        return gleda;
    }
    
    private static Ocena kreirajOcenu(Korisnik k,Videosnimak v,String oc,String datum){
        Ocena ocena=new Ocena();
        OcenaPK opk=new OcenaPK();
        opk.setKorisnik(k.getIdkorisnik());
        opk.setVideosnimak(v.getIdvideosnimak());
        ocena.setDatumVreme(Integer.valueOf(datum));
        ocena.setOcenaPK(opk);
        ocena.setOcena(Integer.valueOf(oc));
        return ocena;
    }
    
    public static void main(String[] args) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(myQueue3);
        JMSProducer producer = context.createProducer();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("podsistem3PU");
        EntityManager em = emf.createEntityManager();
        
        while(true){
            try {
                Message msg = consumer.receive();
                TextMessage txtMsg = (TextMessage)msg;
                System.out.println(txtMsg.getText());
                
                int task = txtMsg.getIntProperty("task");
                switch(task){
                    case 9:{
                        System.out.println("Kreiraj paket");
                        String cena = txtMsg.getStringProperty("strProp1");
                        Paket paket = kreirajPaket(cena);
                        if (paket==null) {
                            TextMessage errMsg=context.createTextMessage("paket null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            try {
                                em.getTransaction().begin();
                                em.persist(paket);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("Paket kreiran!");
                            System.out.println("Paket id: " + paket.getIdpaket()+" cena: "+paket.getCena() + " je kreirana");
                            System.out.println("----------");
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("Paket id:" + paket.getIdpaket()+" cena: "+paket.getCena() + " je kreirana");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();
                            objMsg.setObject(zaCentralni);
                            producer.send(centralQueue, objMsg);
                        }
                        break;
                    }
                    case 10:{
                        System.out.println("Promena cene paketa");
                        String id=txtMsg.getStringProperty("strProp1");
                        String cena = txtMsg.getStringProperty("strProp2");
                        List<Paket> paketi = em.createNamedQuery("Paket.findByIdpaket", Paket.class).setParameter("idpaket", Integer.valueOf(id)).getResultList();
                        Paket p = (paketi.isEmpty()? null : paketi.get(0));
                        if (p ==null) {
                            TextMessage errMsg=context.createTextMessage("paket null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            try {
                                em.getTransaction().begin();
                                p.setCena(Integer.valueOf(cena));
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("Paketu promenjena cena!");
                            System.out.println("Paket id: " + p.getIdpaket()+" cena: "+p.getCena() + " je kreirana");
                            System.out.println("----------");
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("Paket id:" + p.getIdpaket()+" cena: "+p.getCena() + " je kreirana");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();
                            objMsg.setObject(zaCentralni);
                            producer.send(centralQueue, objMsg);
                        }
                        break;
                    }
                    case 11:{
                        System.out.println("Kreiraj pretplatu");
                        String idK = txtMsg.getStringProperty("strProp1");
                        String idP = txtMsg.getStringProperty("strProp2");
                        String cena = txtMsg.getStringProperty("strProp3");
                        String datum = txtMsg.getStringProperty("strProp4");
                        
                        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdkorisnik", Korisnik.class).setParameter("idkorisnik", Integer.valueOf(idK)).getResultList();
                        Korisnik k = (korisnici.isEmpty()? null : korisnici.get(0));
                        
                        List<Paket> paketi = em.createNamedQuery("Paket.findByIdpaket", Paket.class).setParameter("idpaket", Integer.valueOf(idP)).getResultList();
                        Paket p = (paketi.isEmpty()? null : paketi.get(0));
                        if (k==null || p ==null) {
                            TextMessage errMsg=context.createTextMessage("paket ili korisnik je null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            Pretplata pretplata=kreirajPretplatu(k,p,cena,datum);
                            
                            try {
                                em.getTransaction().begin();
                                em.persist(pretplata);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("pretplata kreiran!");
                            System.out.println("Pretplata id:" + pretplata.getIdpretplata()+" cena: "+pretplata.getCena() + " je kreirana");
                            System.out.println("----------");
                            ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                            ArrayList<String> poruka = new ArrayList<>();
                            poruka.add("Pretplata id:" + pretplata.getIdpretplata()+" cena: "+pretplata.getCena() + " je kreirana");
                            zaCentralni.add(poruka);
                            ObjectMessage objMsg = context.createObjectMessage();
                            objMsg.setObject(zaCentralni);
                            producer.send(centralQueue, objMsg);
                        }
                        break;
                    }
                    case 12:{
                        System.out.println("Kreiraj gledanje");
                        String idK = txtMsg.getStringProperty("strProp1");
                        String idV = txtMsg.getStringProperty("strProp2");
                        String sekZap = txtMsg.getStringProperty("strProp3");
                        String sekOdg = txtMsg.getStringProperty("strProp4");
                        String datum = txtMsg.getStringProperty("strProp5");
                        
                        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdkorisnik", Korisnik.class).setParameter("idkorisnik", Integer.valueOf(idK)).getResultList();
                        Korisnik k = (korisnici.isEmpty()? null : korisnici.get(0));
                        
                        List<Videosnimak> videi = em.createNamedQuery("Videosnimak.findByIdvideosnimak", Videosnimak.class).setParameter("idvideosnimak", Integer.valueOf(idV)).getResultList();
                        Videosnimak v = (videi.isEmpty()? null : videi.get(0));
                        
                        if (k==null || v==null) {
                            TextMessage errMsg=context.createTextMessage("korisnik ili videosnimak je null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            Gleda gleda=kreirajGleda(k,v,sekZap,sekOdg,datum);
                            
                            try {
                                em.getTransaction().begin();
                                em.persist(gleda);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
  
                        System.out.println("gleda kreiran!");
                        System.out.println("----------");
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        poruka.add("gleda " + gleda.getIdgleda() + " je kreirano");
                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        }
                        break;
                    }
                    case 13:{
                        System.out.println("Kreiraj ocenu");
                        String idK = txtMsg.getStringProperty("strProp1");
                        String idV = txtMsg.getStringProperty("strProp2");
                        String oc = txtMsg.getStringProperty("strProp3");
                        String datum = txtMsg.getStringProperty("strProp4");
                        
                        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIdkorisnik", Korisnik.class).setParameter("idkorisnik", Integer.valueOf(idK)).getResultList();
                        Korisnik k = (korisnici.isEmpty()? null : korisnici.get(0));
                        
                        List<Videosnimak> videi = em.createNamedQuery("Videosnimak.findByIdvideosnimak", Videosnimak.class).setParameter("idvideosnimak", Integer.valueOf(idV)).getResultList();
                        Videosnimak v = (videi.isEmpty()? null : videi.get(0));
                        
                        if (k==null || v==null) {
                            TextMessage errMsg=context.createTextMessage("korisnik ili videosnimak je null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            Ocena ocena=kreirajOcenu(k,v,oc,datum);
                            try {
                                em.getTransaction().begin();
                                em.persist(ocena);
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("ocena kreiran!");
                        System.out.println("----------");
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        poruka.add("ocena k:" + ocena.getKorisnik1() + " je kreirano");
                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                            
                        }
                        
                        break;
                    }
                    case 14:{
                        System.out.println("Promeni ocenu");
                        String idK = txtMsg.getStringProperty("strProp1");
                        String idV = txtMsg.getStringProperty("strProp2");
                        String oc = txtMsg.getStringProperty("strProp3");
                        
                        List<Ocena> ocene = em.createNamedQuery("Ocena.findByKorisnikAndVideo", Ocena.class).setParameter("korisnik", Integer.valueOf(idK))
                                .setParameter("videosnimak", Integer.valueOf(idV)).getResultList();
                        Ocena k = (ocene.isEmpty()? null : ocene.get(0));
  
                        if (k==null) {
                            TextMessage errMsg=context.createTextMessage("korisnik je null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            try {
                                em.getTransaction().begin();
                                k.setOcena(Integer.valueOf(oc));
                                em.flush();
                                em.clear();
                                em.getTransaction().commit();
                            }
                            finally {
                                if (em.getTransaction().isActive())
                                    em.getTransaction().rollback();
                            }
                            System.out.println("ocena izmenjena!");
                        System.out.println("----------");
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        poruka.add("ocena k:" + k.getKorisnik1() + " je izmenjena");
                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        }

                        break;
                    }
                    case 15:{
                        System.out.println("Obrisi ocenu");
                        String idK = txtMsg.getStringProperty("strProp1");
                        String idV = txtMsg.getStringProperty("strProp2");
                        
                        List<Ocena> ocene = em.createNamedQuery("Ocena.findByKorisnikAndVideo", Ocena.class).setParameter("korisnik", Integer.valueOf(idK))
                                .setParameter("videosnimak", Integer.valueOf(idV)).getResultList();
                        Ocena k = (ocene.isEmpty()? null : ocene.get(0));
                        
                        if (k==null) {
                            TextMessage errMsg=context.createTextMessage("korisnik je null");
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
                            System.out.println("ocena obrisana!");
                        System.out.println("----------");
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        poruka.add("ocena "  + " je obrisana");
                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        }
                        break;
                    }
                    
                    case 22:{
                        System.out.println("Dohvatanje svih paketa");
                        List<Paket> paketi = em.createNamedQuery("Paket.findAll", Paket.class).getResultList();
                        for (Paket paket : paketi) {
                            System.out.println(paket.getIdpaket()+" "+paket.getCena());
                        }
       
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        for (Paket paket : paketi) {
                            poruka.add("Paket "+paket.getIdpaket()+" ");
                            poruka.add(""+paket.getCena());
                        }

                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        System.out.println("----------");
                        break;
                    }
                    case 23:{
                        System.out.println("Dohvatanje svih pretplata");
                        String idK = txtMsg.getStringProperty("strProp1");
        
                        if(idK==null){
                             TextMessage errMsg=context.createTextMessage("korisnik je null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
  
                        int idKo=Integer.valueOf(idK);
   
                       
                        List<Pretplata> pretplate = em.createNamedQuery("Pretplata.findAll", Pretplata.class).getResultList();
                        for (Pretplata pretplata : pretplate) {
                            if(idKo==pretplata.getIkorisnik().getIdkorisnik())
                                System.out.println(pretplata.getIdpretplata()+" "+pretplata.getIkorisnik()+" "+pretplata.getPaket()+" "+pretplata.getCena()+" "+pretplata.getDatum());
                        }
       
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        for (Pretplata pretplata : pretplate) {
                            if(idKo==pretplata.getIkorisnik().getIdkorisnik()){
                            poruka.add("Pretplata "+pretplata.getIdpretplata()+" ");
                            poruka.add(""+pretplata.getIkorisnik());
                            poruka.add(""+pretplata.getPaket());
                            poruka.add(" "+ pretplata.getCena());
                            poruka.add(" "+pretplata.getDatum());
                            }
                        }

                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        System.out.println("----------");
                        }
                        
                        break;
                    }
                    case 24:{
                        System.out.println("Dohvatanje sva gledanja");
                        String idK = txtMsg.getStringProperty("strProp1");

                        if(idK==null){
                             TextMessage errMsg=context.createTextMessage("video je null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            int idKo=Integer.valueOf(idK);
                            List<Gleda> gledanja = em.createNamedQuery("Gleda.findAll", Gleda.class).getResultList();
                        for (Gleda gleda : gledanja) {
                            if(idKo==gleda.getIdkorisnik().getIdkorisnik())
                            System.out.println(gleda.getIdgleda()+" "+gleda.getIdkorisnik()+" "+gleda.getIdvideosnimak()+" "+gleda.getDatum()+" "+gleda.getSekOdg()+" "+gleda.getSekZap());
                        }
       
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        for (Gleda gleda : gledanja) {
                            if(idKo==gleda.getIdkorisnik().getIdkorisnik()){
                            poruka.add("Gledanja "+gleda.getIdgleda()+" ");
                            poruka.add("Gledanja "+gleda.getIdkorisnik()+" ");
                            poruka.add("Gledanja "+gleda.getIdvideosnimak()+" ");
                            }
                        }

                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        System.out.println("----------");
                        }
                        
                        
                        break;
                    }
                   
                    case 25:{
                        System.out.println("Dohvatanje svih ocena");
                        String idK = txtMsg.getStringProperty("strProp1");

                        if(idK==null){
                             TextMessage errMsg=context.createTextMessage("video je null");
                            producer.send(centralQueue, errMsg);
                        }
                        else{
                            int idKo=Integer.valueOf(idK);
                            List<Ocena> ocene = em.createNamedQuery("Ocena.findAll", Ocena.class).getResultList();
                        for (Ocena ocena : ocene) {
                            if(idKo==ocena.getKorisnik1().getIdkorisnik())
                            System.out.println(ocena.getKorisnik1()+" "+ocena.getVideosnimak1()+" "+ocena.getDatumVreme()+" "+ocena.getOcena());
                        }
       
                        
                        ArrayList<ArrayList<String>> zaCentralni = new ArrayList<>();
                        ArrayList<String> poruka = new ArrayList<>();
                        for (Ocena ocena : ocene) {
                            if(idKo==ocena.getKorisnik1().getIdkorisnik())
                                poruka.add("Ocena "+ocena.getOcena()+" ");
                            
                        }

                        zaCentralni.add(poruka);
                        ObjectMessage objMsg = context.createObjectMessage();
                        objMsg.setObject(zaCentralni);
                        producer.send(centralQueue, objMsg);
                        System.out.println("----------");
                        }
                        
                        break;
                    }
                    default:{
                        System.out.println("Nepoznat ID operacije");
                    }
                }
            } catch (JMSException ex) {
                Logger.getLogger(MainPodsistem3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
