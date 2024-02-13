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
@Path("kategorija")
public class ResourceKategorija {
    @Resource(lookup = "myConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "q1")
    public Queue myQueue;
    
    @Resource(lookup = "q2")
    public Queue myQueue2;
    
    @Resource(lookup = "cq")
    public Queue centralQueue;
    
    
    @GET
    public Response testKat(){
        return Response.ok("testKat").build();
    }
    
    @GET
    @Path("kreirajKategoriju")
    public Response kreirajKat(@QueryParam("naziv") String nazivK){
        int task=5;
        
        try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu2");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", nazivK);
             
             producer.send(myQueue2, textMsg);
           
             Message msg=consumer.receive(5000);
             if(msg == null)return Response.ok("greska").build();
             if(msg instanceof ObjectMessage){
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
             return Response.ok().entity("kreiranjeKategorije").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("kreiranjeKategorije").build();
    }
    
    @GET
    @Path("dohvatiKategorije")
    public Response dohvKat(){
        int task=19;
        
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
             return Response.ok().entity("dohvatanjeKategorija").build();

         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("dohvatanjeKategorija").build();
    }
}
