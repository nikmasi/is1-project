/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.centralniserver1.resources;

import java.util.ArrayList;
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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author JA
 */
@Path("paket")
public class ResourcePaket {
    @Resource(lookup = "myConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "q1")
    public Queue myQueue;
    
    @Resource(lookup = "q2")
    public Queue myQueue2;
    
    @Resource(lookup = "q3")
    public Queue myQueue3;
    
    @Resource(lookup = "cq")
    public Queue centralQueue;
    
    @GET
    public Response paketProba(){
        return Response.ok("paket").build();
    }
    
    @GET
    @Path("kreirajPaket")
    public Response kreirajPak(@QueryParam("cena") String cena){
        int task=9;
       
         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu3");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", cena);      
            
             producer.send(myQueue3, textMsg);
           
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
             return Response.ok().entity("kreirajPaket").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourcePaket.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("kreirajPaket").build();
    }
    
    @GET
    @Path("promenaCenePaketa")
    public Response promenaCene(@QueryParam("id") String id,@QueryParam("cena") String cena){
        int task=10;

         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu3");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", id);
             textMsg.setStringProperty("strProp2", cena);      
            
             producer.send(myQueue3, textMsg);
           
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
             return Response.ok().entity("promenaCene").build();

         } catch (JMSException ex) {
             Logger.getLogger(ResourcePaket.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("promenaCene").build();
    }
    
    @GET
    @Path("dohvatiPakete")
    public Response dohvatiPak(){
        int task=22;
        
         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu3");
             textMsg.setIntProperty("task", task);    
            
             producer.send(myQueue3, textMsg);
           
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
             return Response.ok().entity("dohvatanjePaketa").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourcePaket.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("dohvatanjePaketa").build();
    }
}
