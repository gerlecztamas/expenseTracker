/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Gerlecz Tamás
 */
public class ModelBusiness {
    public static JSONArray getExpensesByCategory(String category){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        Fio reader = new Fio();
        JSONArray result = new JSONArray();
        JSONArray expenses = reader.read();
        JSONArray newExpenses = new JSONArray();
        float amount = 0;
        boolean categoryCheck = false;
        for(int i = 0; i < expenses.length(); i++){
            JSONObject expense = expenses.getJSONObject(i);
            if((expense.get("category").toString()).equals(category)){
                categoryCheck = true;
                amount += expense.getFloat("amount");  
                newExpenses.put(expense);
            }
        }
        if (categoryCheck){
            JSONObject message = new JSONObject();
            message.put("msg","This category takes up " + df.format((amount/reader.readBudget())*100.0) + "% of your monthly budget so far");
            result.put(message);
            result.put(newExpenses);
            return result;
        }
        JSONObject message = new JSONObject();
        message.put("msg","The category you entered is not in the database of expenses");
        result.put(message);
        return result;
    }
    
    public static Boolean expenseCheck(JSONObject expenseToCheck){
        
        Fio reader = new Fio();
        JSONArray expenses = reader.read();
        
        for(int i = 0; i < expenses.length(); i++){
            JSONObject expense = expenses.getJSONObject(i);
            if(expense.get("name").equals(expenseToCheck.get("name"))){
                Float amount1 = expense.getFloat("amount");
                Float amount2 = expenseToCheck.getFloat("amount");
                expense.remove("amount");
                expense.put("amount", amount1 + amount2);
                return true;
            }    
        }
        String name = expenseToCheck.getString("name");
        CategoryEnum category = CategoryEnum.valueOf(expenseToCheck.getString("category"));
        Float amount = expenseToCheck.getFloat("amount");
        Expense newExpense = new Expense(name, category,amount);
        reader.write(newExpense);
        return false;
    }
    
    public static JSONObject addExpense(String bodycontent){
        JSONObject result = new JSONObject();
        try{
        JSONObject expense = new JSONObject(bodycontent);
        if(expenseCheck(expense)){
            result.put("msg", "This expense has already been entered");
            return result;
        }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            result.put("msg", "Wrong input in request body");
            return result;
        }
        result.put("msg", "The expense has been added to the database");
        return result;
    }
    
    public static JSONArray getExpense(String name){
        JSONArray result = new JSONArray();
        String msg = "The expense you entered is not in the database of expenses!";
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        Fio reader = new Fio();
        JSONArray expenses = reader.read();
        
        for(int i = 0; i < expenses.length(); i++){
            JSONObject expense = expenses.getJSONObject(i);
            if(expense.get("name").equals(name)){
                float help = Float.parseFloat(expense.get("amount").toString());
                msg = "The amount of "+name+" is " + df.format(new Expense().amountOfExpense(help)*100.0) + " % of your current planned monthly budget!";
                result.put(expense);
                break;
            }
        }
        JSONObject message = new JSONObject();
        message.put("msg", msg);
        result.put(message);
        return result;
    }
    
    public static JSONObject increaseBudget(String bodycontent) {
        JSONObject result = new JSONObject();
        try{
            JSONObject json_deposit = new JSONObject(bodycontent);
            Float deposit = json_deposit.getFloat("budget");
            Fio reader = new Fio();
            Float updated_budget = deposit + reader.readBudget();
            reader.updateBudget(updated_budget);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            result.put("msg", "Wrong input in request body");
            return result;
        }
        
        result.put("msg", "Budget for this month has been updated");
        
        return result;
    }
}
