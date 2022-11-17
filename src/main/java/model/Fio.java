/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Gerlecz Tamás
 */
public class Fio {
    public JSONArray read() {
        JSONArray expenses = new JSONArray();
        
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("D:\\expenseTracker\\src\\main\\resources\\expenses.xml");
            Document doc = db.parse(f);
            doc.normalize();
            
            NodeList nodeList = doc.getElementsByTagName("expense");
            for(int i = 0; i < nodeList.getLength(); i++){
                Element el = (Element) nodeList.item(i);
                JSONObject expense = new JSONObject();
                
                    String name = el.getElementsByTagName("name").item(0).getTextContent();
                    String category = el.getElementsByTagName("category").item(0).getTextContent();
                    String date = el.getElementsByTagName("date").item(0).getTextContent();
                    String amount = el.getElementsByTagName("amount").item(0).getTextContent();
                    
                    expense.put("name", name);
                    expense.put("category", category);
                    expense.put("date", date);
                    expense.put("amount", amount);
                    
                expenses.put(expense);
            }
            
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        return expenses;
    }
    
    
    public void write(Expense newExpense) {
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("D:\\expenseTracker\\src\\main\\resources\\expenses.xml");
            Document doc = db.parse(f);
            doc.normalize();
            
            Element root = doc.getDocumentElement();
            Element expense = doc.createElement("expense");
            root.appendChild(expense);
            
            Element name = doc.createElement("name");
            Element category = doc.createElement("category");
            Element date = doc.createElement("date");
            Element amount = doc.createElement("amount");
            
            name.setTextContent(newExpense.getName());
            category.setTextContent(newExpense.getCategory().toString());
            date.setTextContent(newExpense.getDate().toString());
            amount.setTextContent(Float.toString(newExpense.getAmount()));
            
            expense.appendChild(name);
            expense.appendChild(category);
            expense.appendChild(date);
            expense.appendChild(amount);
            
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(f);
            
            t.transform(source, result);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public float readBudget() {
        float budget = 0;
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("D:\\expenseTracker\\src\\main\\resources\\budget.xml");
            Document doc = db.parse(f);
            doc.normalize();
            Element rootElement = doc.getDocumentElement();
            budget = Float.parseFloat(rootElement.getTextContent());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return budget;
    }
    
    public void updateBudget(Float updatedBudget){
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File f = new File("D:\\expenseTracker\\src\\main\\resources\\budget.xml");
            Document doc = db.parse(f);
            doc.normalize();
            Element rootElement = doc.getDocumentElement();
            doc.removeChild(rootElement);
            Node node = doc.createElement("budget");
            doc.appendChild(node);
            Element root = doc.getDocumentElement();
            root.setTextContent(updatedBudget.toString());

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File("D:\\expenseTracker\\src\\main\\resources\\budget.xml"));
            Source input = new DOMSource(doc);

            transformer.transform(input, output);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
