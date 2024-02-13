/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.centralniserver1.resources;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author JA
 */
@Path("mesto")
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ResourceMesto {
    
    @Resource(lookup = "myConnectionFactory")
    public ConnectionFactory cf;
    
    @Resource(lookup = "q1")
    public Queue myQueue;
    
    @Resource(lookup = "q2")
    public Queue myQueue2;
    
    @Resource(lookup = "cq")
    public Queue centralQueue;
    
    @GET
    public Response mestoProba(){
        return Response.ok("mesto").build();
    }
    
    @GET
    @Path("kreirajMesto")
    public Response kreirajMesto(@QueryParam("naziv") String nazivM){
        int task=1;
       
         try {
             JMSContext context = cf.createContext();
             JMSConsumer consumer = context.createConsumer(centralQueue);
             JMSProducer producer = context.createProducer();
             TextMessage textMsg = context.createTextMessage();
             textMsg.setText("Text message servera podsistemu1");
             textMsg.setIntProperty("task", task);
             textMsg.setStringProperty("strProp1", nazivM);
          
             producer.send(myQueue, textMsg);
           
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
             
             return Response.ok().entity("kreirajMesto").build();

         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }           
        return Response.ok("kreirajMesto").build();
    }

    @GET
    @Path("dohvatiMesta")
    public Response dohvMesta(){
        int task=17;
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
                ObjectMessage objMsg = (ObjectMessage)msg;
                ArrayList<ArrayList<String>> zaKlijenta = (ArrayList<ArrayList<String>>)objMsg.getObject();
                consumer.close();
                context.close();
                return Response.ok().entity(zaKlijenta).build();
            } else if(msg instanceof TextMessage){
                 TextMessage er=(TextMessage)msg;
                 consumer.close();
                 context.close();
                 return Response.ok().entity(er.getText()).build();
             }
            consumer.close();
            context.close();
            return Response.ok("dohvMesta").build();
         } catch (JMSException ex) {
             Logger.getLogger(ResourceMesto.class.getName()).log(Level.SEVERE, null, ex);
         }
        return Response.ok("dohvMesta").build();
    }
}