/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.centralniserver1.resources;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author JA
 */
@Path("korisnik")
public class ResourceKorisnik {
    @Resource(lookup = "myConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "q1")
    public Queue myQueue;
    
    @Resource(lookup = "q2")
    public Queue myQueue2;
    
    @Resource(lookup = "cq")
    public Queue centralQueue;
    
    @GET
    public Response korisnikProba(){
        return Response.ok("korisnik").build();
    }
    
    @GET
    @Path("kreirajKorisnika")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response ostalo2(@QueryParam("ime") String nazivK,@QueryParam("email") String email,
            @QueryParam("godiste") String godiste,@QueryParam("pol") String pol,@QueryParam("mesto") String mesto){
        int task=2;
       
         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu1");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", nazivK);
             textMsg.setStringProperty("strProp2", email);
             textMsg.setStringProperty("strProp3", godiste);
             textMsg.setStringProperty("strProp4", pol);
             textMsg.setStringProperty("strProp5", mesto);
          
             producer.send(myQueue, textMsg);
           
             Message msg=consumer.receive(5000);
             if(msg == null)return Response.ok("greska").build();
             if(msg instanceof ObjectMessage){
                msg=(ObjectMessage)msg;
                ObjectMessage obj=(ObjectMessage)msg;
                ArrayList<ArrayList<String>> zaKlijenta = (ArrayList<ArrayList<String>>)obj.getObject();
                consumer.close();
                context.close();
                return Response.ok().entity(zaKlijenta).build();
             }
             else if(msg instanceof TextMessage){
                 TextMessage er=(TextMessage)msg;
                 consumer.close();
                 context.close();
                 return Response.ok().entity(er.getText()).build();
             }
             consumer.close();
             context.close();
             return Response.ok().entity("kreirajKoristnika").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response .ok("kreirajKoristnika").build();
    }
    
    @GET
    @Path("promeniEmail")
    public Response promeniEm(@QueryParam("id") String id,@QueryParam("email") String email){
        int task=3;
       
         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu1");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", id);
             textMsg.setStringProperty("strProp2", email);           
            
             producer.send(myQueue, textMsg);
             
             Message msg=consumer.receive(5000);
             if(msg == null)return Response.ok("greska").build();
             if(msg instanceof ObjectMessage){
                msg=(ObjectMessage)msg;
                ObjectMessage obj=(ObjectMessage)msg;
                ArrayList<ArrayList<String>> zaKlijenta = (ArrayList<ArrayList<String>>)obj.getObject();
                consumer.close();
                context.close();
                return Response.ok().entity(zaKlijenta).build();
             }
             else if(msg instanceof TextMessage){
                 TextMessage er=(TextMessage)msg;
                 consumer.close();
                 context.close();
                 return Response.ok().entity(er.getText()).build();
             } 
             consumer.close();
             context.close();
             return Response.ok().entity("promenaEmail").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }             
        return Response.ok("promenaEmaila").build();
    }
    
    @GET
    @Path("promeniMesto")
    public Response promeniMes(@QueryParam("id") String id,@QueryParam("mesto") String mesto){
        int task=4;
       
         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu1");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", id);
             textMsg.setStringProperty("strProp2", mesto);           
            
             producer.send(myQueue, textMsg);
             
             Message msg=consumer.receive(5000);
             if(msg == null)return Response.ok("greska").build();
             if(msg instanceof ObjectMessage){
                msg=(ObjectMessage)msg;
                ObjectMessage obj=(ObjectMessage)msg;
                ArrayList<ArrayList<String>> zaKlijenta = (ArrayList<ArrayList<String>>)obj.getObject();
                consumer.close();
                context.close();
                return Response.ok().entity(zaKlijenta).build();
             }
             else if(msg instanceof TextMessage){
                 TextMessage er=(TextMessage)msg;
                 consumer.close();
                 context.close();
                 return Response.ok().entity(er.getText()).build();
             }
             consumer.close();
             context.close();
             return Response.ok().entity("promenaMesta").build();

         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("promenaMesta").build();
    }
    
    @GET
    @Path("dohvatiKorisnike")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response dohvatiKorisnike(){
        int task=18;
        
        try {
            JMSContext context = cf.createContext();
            JMSConsumer consumer = context.createConsumer(centralQueue);
            JMSProducer producer = context.createProducer();
            TextMessage textMsg = context.createTextMessage();
            textMsg.setText("Text message servera podsistemu1");
            textMsg.setIntProperty("task", task);
           
            producer.send(myQueue, textMsg);
            
            Message msg=consumer.receive(5000);
             if(msg == null)return Response.ok("greska").build();
             if(msg instanceof ObjectMessage){
                msg=(ObjectMessage)msg;
                ObjectMessage obj=(ObjectMessage)msg;
                ArrayList<ArrayList<String>> zaKlijenta = (ArrayList<ArrayList<String>>)obj.getObject();
                consumer.close();
                context.close();
                return Response.ok().entity(zaKlijenta).build();
             }
             else if(msg instanceof TextMessage){
                 TextMessage er=(TextMessage)msg;
                 consumer.close();
                 context.close();
                 return Response.ok().entity(er.getText()).build();
             }
             consumer.close();
             context.close();
            return Response.ok().entity("dohvKorisnika").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("dohvKorisnika").build();
    }
}
