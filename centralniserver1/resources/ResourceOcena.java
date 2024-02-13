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
@Path("ocena")
public class ResourceOcena {
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
    public Response ocenaProba(){
        return Response.ok("ocenaProba").build();
    }
    
    @GET
    @Path("kreirajOcenu")
    public Response kreirajOcena(@QueryParam("idK") String idK,@QueryParam("idV") String idV,@QueryParam("ocena") String ocena,@QueryParam("datum") String datum){
        int task=13;
       
         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu3");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", idK);   
             textMsg.setStringProperty("strProp2", idV);   
             textMsg.setStringProperty("strProp3", ocena); 
             textMsg.setStringProperty("strProp4", datum);   

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
             return Response.ok().entity("kreirajOcenu").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourcePaket.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("kreirajOcenu").build();
    }
    
    @GET
    @Path("promeniOcenu")
    public Response promeniOcenu(@QueryParam("idK") String idK,@QueryParam("idV") String idV,@QueryParam("ocena") String ocena){
        int task=14;
       
         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu3");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", idK);
             textMsg.setStringProperty("strProp2", idV);
             textMsg.setStringProperty("strProp3", ocena); 

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
             return Response.ok().entity("promeniOcenu").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourcePaket.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("promeniOcenu").build();
    }
    
    @GET
    @Path("obrisiOcenu")
    public Response obrisiOcenu(@QueryParam("idK") String idK,@QueryParam("idV") String idV){
        int task=15;

         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu3");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", idK);
             textMsg.setStringProperty("strProp2", idV);

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
             return Response.ok().entity("obrisiOcenu").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourcePaket.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("obrisiOcenu").build();
    }
    
    @GET
    @Path("dohvatiOcene")
    public Response dohvatiOcene(@QueryParam("idV") String idV){
        int task=25;
       
         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu3");
             textMsg.setIntProperty("task", task);
              textMsg.setStringProperty("strProp1", idV);

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
             return Response.ok().entity("dohvatiOcenu").build();

         } catch (JMSException ex) {
             Logger.getLogger(ResourcePaket.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("dohvatiOcenu").build();
    }
}