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
@Path("videoSnimak")
public class ResourceVideoSnimak {
    @Resource(lookup = "myConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "q1")
    public Queue myQueue;
    
    @Resource(lookup = "q2")
    public Queue myQueue2;
    
    @Resource(lookup = "cq")
    public Queue centralQueue;
    
    @GET
    public Response testVidSnimak(){
        return Response.ok("testVidSnimak").build();
    }
    
    @GET
    @Path("kreirajVideoSnimak")
    public Response kreirajKat(@QueryParam("naziv") String nazivV,@QueryParam("trajanje") String trajanje,
            @QueryParam("vlasnik") String vlasnik,@QueryParam("datum") String datum){
        int task=6;
        
        try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu2");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", nazivV);
             textMsg.setStringProperty("strProp2", trajanje);
             textMsg.setStringProperty("strProp3", vlasnik);
             textMsg.setStringProperty("strProp4", datum);
             
             producer.send(myQueue2, textMsg);
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
             return Response.ok().entity("kreirajVideoSnimak").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("kreirajVideoSnimak").build();
    }
    
    @GET
    @Path("promenaNazivaVideoSnimka")
    public Response promeniNaziv(@QueryParam("id") String id,@QueryParam("naziv") String naziv){
        int task=7;
        
        try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu2");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", id);
             textMsg.setStringProperty("strProp2", naziv);
             
            producer.send(myQueue2, textMsg);
           
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
             return Response.ok().entity("promenaNazivaVideoSnimka").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("promenaNazivaVideoSnimka").build();
    }
    
    @GET
    @Path("dodavanjeKategorijeVideoSnimku")
    public Response dodavanjeKategorijeVidS(@QueryParam("idV") String idV,@QueryParam("idK") String idK){
        int task=8;
        
        try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu2");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", idV);
             textMsg.setStringProperty("strProp2", idK);
             
             producer.send(myQueue2, textMsg);
           
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
             return Response.ok().entity("dodavanjeKategorijeVideoSnimku").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("dodavanjeKategorijeVideoSnimku").build();
    }
    
    @GET
    @Path("dohvatiVideoSnimke")
    public Response dohvatiVideoS(){
        int task=20;
        
        try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu2");
             textMsg.setIntProperty("task", task);
             
            producer.send(myQueue2, textMsg);
           
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
             return Response.ok().entity("dohvatiVideoSnimak").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("dohvatiVideoSnimak").build();
    }
    
    @GET
    @Path("obrisivideoSnimak")
    public Response obrisivideoSnimak(@QueryParam("idV") String idV,@QueryParam("idK") String idK){
        int task=16;
        
        try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu2");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", idV);
             textMsg.setStringProperty("strProp2", idK);
             
             producer.send(myQueue2, textMsg);
           
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
             return Response.ok().entity("obrisivideoSnimak").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("obrisiVideoSnimak").build();
    }
}
