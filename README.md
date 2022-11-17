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
  * **GET** metódus, a Model réteg Fio fájlkezelő osztályának segítségével beolvassa az expenses.xml fájlban tárolt adatokat, amelyet JSONArray formátumban kap meg és ezt küldi vissza a felhasználónak.

* getExpensesByCategory
  * **GET** metódus, QueryParameter formájában megkapja a program a listázandó kategóriát és átadja azt a Model réteg  megfelelő feldolgozó metódusának. A választ JSONArray formájában küldi, amely tartalmazza az összes megfelelő kategóriába tartozó adatot és egy üzenetet arról, hogy az adott kategória jelenleg mekkora részét teszi ki a tervezett költekezéseinknek.

* {expense}
  * **GET** metódus, PathParametert használ, annak értéke egy adott expense neve, ezt adja át a Model réteg megfelelő feldolgozó metódusának, majd JSON formátumban küld választ az adat tulajdonságaival és egy üzenettel, hogy az adott költekezés jelenleg mekkora részét teszi ki a tervezett költekezéseinknek.

* addExpense
  * **POST** metódus, célja újabb költekezés hozzáadása az adatokhoz, a request bodyból kapott megfelelő JSON formátumú Stringet továbbítja a Model réteg megfelelő feldolgozó metódusának és szintén JSON formátumban küld választ a felhasználónak az eredményéről.

* increaseBudget
  * **POST** metódus, célja, hogy növeljük a budget.xml-ben tárolt tervezett havi keretet, a request bodyból kapott megfelelő JSON formátumú Stringet továbbítja a Model réteg megfelelő feldolgozó metódusának és szintén JSON formátumban küld választ a felhasználónak az eredményéről.
-----

**Model**

Ez a package tartalmazza az összes olyan osztályt és metódust melyek az adatok változtatásához és a Controller réteg által küldött kérések kiszolgálásához szükséges.

***Expense***

Ez az úgymond fő entitásosztály, amely meghatározza milyen adatokat tárolunk, az AmountInterface-t implementálja ami egy metódust ad az osztálynak, amely segítségével kiszámolható az adott költekezés mekkora része a tervezett költekezéseinknek. Ezekhez mind tartoznak a szükséges getterek is.

Attribútumai:
* String name - az adott költekezés neve
* CategoryEnum category - az adott költekezés kategóriája
* Float amount - az adott költekezés mennyisége

***CategoryEnum***

Az Expense osztály enumja, ahol definiálva vannak a költekezések ketgóriái.

Értékei:
* FOOD
* HEALTH
* CLOTHING
* HOUSING
* TRANSPORTATION
* OTHER

***Fio***

Ez az osztály kezeli az összes fájlba írást és fájlból olvasást, több helyen is használatban áll a programon belül. A metódusai a következők:

* public JSONArray read()
  * a metódus megnyitja az expenses.xml dokumentumot, azt megfelelően kezeli, parseolja, majd a fájl minden egyes expense elementjéből egy JSONObjectet készít, amelyeket egy JSONArraybe pakolja és ezt is küldi vissza. A metódust például a getExpenses request közvetlenül használja, de több helyen is használatban van.

* public void write(Expense newExpense)
  * a metódus egy Expense osztályú objektumot kap paraméterül, a feladata, hogy ezt megfelelő xml formátumba alakítsa és hozzáadja az expense.xml-ben tárolt adatokhoz.

* public float readBudget()
  * a metódus megnyitja a budget.xml dokumentumot, hogy onnan beolvassa a tervezett költekezések mennyiségét, hogy az vissza küldött float típusú adatot számolásokhoz használhassuk.

* public void updateBudget(float updatedBudget)
  * a metódus az increaseBudget request során kerül elő, egész egyszerűen megnyitja a budget.xml dokumentumot, majd annak tartalmát megváltoztatja az updatelt összegre.

***ModelBusiness***

Ez az osztály tartalmazza a legtöbb requesthez szükséges business logikát/metódust. A Fio osztály xml manipulációs függvényeit is használja.

* public static JSONArray getExpense(String name)
  * a metódus a request által lekért expense nevét kapja paraméterként, végignézi a Fio osztály segítségével beolvasott adatainkat és ha azok között szerepel, akkor az objektumot belerakja egy JSONObjectbe majd azt a vissza küldésre váró JSONArraybe, emellé társít egy sikert jelző JSONObject üzenetet is, ha hibás nevet ad meg csupán az üzenet tér vissza azzal, hogy a folyamat sikertelen volt.

* public static JSONArray getExpensesByCategory(String category)
  * a metódus a request által lekért category nevét kapja paraméterként végignézi a Fio osztály segítségével beolvasott adatainkat majd az egyező kategóriájú adatokat egy JSONArraybe teszi, ezt hozzáadja egy újabb JSONArrayhez és társít mellé egy üzenetet amelyben feltünteti az adott kategória a tervezett költségeink mekkora része. Ha hibás kategóriát ad meg a felhasználó akkor ez csak a hibát jelzö üzenetet fogja tartalmazni.
 
* public static JSONObject increaseBudget(String bodycontent)
  * a metódus a requestbody-ban tárolt JSONObjectet kapja meg String formátumban, kiszámolja a Fió osztály metódusainak segítségével az új budget mennyiségét majd átírja azt a budget.xml fájlban. A metódus a sikeres változtatás üzenetét küldi vissza. Ha nem megfelelő formátumú JSONObject van a request body-ban akkor pedig az ennek megfelelő üzenetet.

* public static JSONObject addExpense(JSONObject expense)
  * a metódus a requestbody-ban tárolt JSONObjectet kapja meg String formátumban, ha nem megfelelő JSON formátumban van akkor hibaüzenetet küld vissza, ha megfelelő akkor odaadja az az expenseCheck metódusnak, amely leellenőrzi a Fio osztály metódusai segítségével, hogy szerepel-e már az adat az adatok között és ha nem akkor hozzáadja azt az expenses.xml fájlhoz és ennek a segítő metódus eredményétől függően küld vissza a metódus egy megfelelő JSON típúsú választ.
-----

**XML dokumentumok**

***expenses.xml***

Itt vannak a különböző expensek elválasztva tárolva.

* expense - ezekben az elementekben tároljuk a különböző expensek tulajdonságait
  * name - expense neve/leírása
  * category - expense kategóriája enum kompatibilis formában
  * amount - expense mennyisége

***budget.xml***

Itt van a tervezett költségvetés mennyisége tárolva.
