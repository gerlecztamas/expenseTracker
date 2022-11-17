/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Fio;
import model.ModelBusiness;

import org.json.JSONObject;

/**
 *
 * @author Gerlecz Tamás
 */
@Path("app")
public class RequestController {
    
    @GET
    @Path ("{expense}")
    public Response getExpense(@PathParam("expense") String name){
        return Response
                .ok(ModelBusiness.getExpense(name)
                    .toString())
                .build();
    }
    
    @POST
    @Path("addExpense")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addExpense(String bodycontent){
        JSONObject expense = new JSONObject(bodycontent);
        return Response
                .ok(ModelBusiness.addExpense(expense)
                    .toString())
                .build();
    }
    
    @GET
    @Path("getExpenses")
    public Response getExpenses(){
        Fio reader = new Fio();
        return Response
                .ok(reader.read()
                    .toString())
                .build();
    }
    
    @GET
    @Path("getExpensesByCategory")
    public Response getExpensesByCategory(@QueryParam("category") String category){
        return Response
                .ok(ModelBusiness.getExpensesByCategory(category)
                    .toString())
                .build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("increaseBudget")
    public Response increaseBudget(String bodycontent){
        return Response
                .ok(ModelBusiness.increaseBudget(bodycontent)
                    .toString())
                .build();
    }
}
