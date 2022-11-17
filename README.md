expenseTracker
=========

**A program célja**

Programozás módszertana II. egyéni beadandó, web API, ahol a felhasználó különböző költekezéseit tudja tárolni kategória szerint és számon tartani, hogy ez jelenleg mekkora része a tervezett költségvetésének.

Package-ek
-----

**Controller**

Ez a package összesen egyetlen osztályt, a RequestController-t tartalmaz, ahol a különböző requestek szerepelnek. Ezek mind kommunikálnak a Model réteggel majd az eredményt Stringgé alakított JSON formátumban küldi vissza Response függvény segítségével.

***Requestek/Endpointok***

* getExpenses
  * GET metódus, a Model réteg Fio fájlkezelő osztályának segítségével beolvassa az expenses.xml fájlban tárolt adatokat, amelyet JSONArray formátumban kap meg és ezt küldi vissza a felhasználónak.

* getExpensesByCategory
  * GET metódus, QueryParameter formájában megkapja a program a listázandó kategóriát és átadja azt a megfelelő Model réteg feldolgozó metódusának
