/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Gerlecz Tamás
 */
public class Expense implements AmountInterface {
    private String name;
    private CategoryEnum category;
    private LocalDate date;
    private float amount;

    public Expense() {
    }

    public Expense(String name, CategoryEnum category, LocalDate date, float amount) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public float getAmount() {
        return amount;
    }
    
    

    @Override
    public float amountOfExpense(float amount) {
        //hány százaléka az adott expense az összesnek
        Fio reader  = new Fio();
        
        this.amount = amount;
        System.out.println("helobello amount vagyok" + amount);
        System.out.println("helobello budget vagyok" + reader.readBudget());
        return amount/reader.readBudget();
    }
}
