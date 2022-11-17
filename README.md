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
  * GET metódus, QueryParameter formájában megkapja a program a listázandó kategóriát és átadja azt a Model réteg  megfelelő feldolgozó metódusának. A választ JSONArray formájában küldi, amely tartalmazza az összes megfelelő kategóriába tartozó adatot és egy üzenetet arról, hogy az adott kategória jelenleg mekkora részét teszi ki a tervezett költekezéseinknek.

* {expense}
 * GET metódus, PathParametert használ, annak értéke egy adott expense neve, ezt adja át a Model réteg megfelelő feldolgozó metódusának, majd JSON formátumban küld választ az adat tulajdonságaival és egy üzenettel, hogy az adott költekezés jelenleg mekkora részét teszi ki a tervezett költekezéseinknek.

* addExpense
 * POST metódus, célja újabb költekezés hozzáadása az adatokhoz, a request bodyból kapott megfelelő JSON formátumú Stringet továbbítja a Model réteg megfelelő feldolgozó metódusának és szintén JSON formátumban küld választ a felhasználónak az eredményéről.

* increaseBudget
 * POST metódus, célja, hogy növeljük a budget.xml-ben tárolt tervezett havi keretet, a request bodyból kapott megfelelő JSON formátumú Stringet továbbítja a Model réteg megfelelő feldolgozó metódusának és szintén JSON formátumban küld választ a felhasználónak az eredményéről.
