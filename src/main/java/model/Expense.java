/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Gerlecz Tamás
 */
public class Expense implements AmountInterface {
    private String name;
    private CategoryEnum category;
    private float amount;

    public Expense() {
    }

    public Expense(String name, CategoryEnum category, float amount) {
        this.name = name;
        this.category = category;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public float getAmount() {
        return amount;
    }
    
    

    @Override
    public float amountOfExpense(float amount) {
        Fio reader  = new Fio();
        this.amount = amount;
        return amount/reader.readBudget();
    }
}
