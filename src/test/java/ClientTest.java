import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class MonsterTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteClientsQuery = "DELETE FROM clients *;";
      con.createQuery(deleteClientsQuery).executeUpdate();
    }
  }

  @Test
  public void client_instantiatesCorrectly_true() {
    Client testClient = new Client("Vain Valerie", 1);
    assertEquals(true, testClient instanceof Client);
  }

  @Test
  public void Client_instantiatesWithName_String() {
    Client testClient = new Client("Vain Valerie", "206-XXX-XXXX", 1);
    assertEquals("Vain Valerie", testClient.getName());
  }

  @Test
  public void Client_instantiatesWithStylistId_int() {
    Client testClient = new Client("Vain Valerie", 1);
    assertEquals(1, testClient.getStylistId());
  }

  @Test
  public void equals_returnsTrueIfNameAndStylistIdAreSame_true() {
    Client testClient = new Client("Vain Valerie", "206-XXX-XXXX", 1);
    Client anotherClient = new Client("Vain Valerie", "206-XXX-XXXX", 1);
    assertTrue(testClient.equals(anotherClient));
  }

  @Test
  public void save_returnsTrueIfNamesAretheSame() {
    Client testClient = new Client("Vain Valerie", "206-XXX-XXXX", 1);
    testClient.save();
    assertTrue(Client.all().get(0).equals(testClient));
  }

  @Test
  public void save_assignsIdToClient() {
    Client testClient = new Client("Vain Valerie", "206-XXX-XXXX", 1);
    testClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(savedClient.getId(), testClient.getId());
  }

  @Test
  public void all_returnsAllInstancesOfClient_true() {
    Client firstClient = new Client("Vain Valerie", "206-XXX-XXXX", 1);
    firstClient.save();
    Client secondClient = new Client("Narcissistic Nate", "206-XXX-XXXX", 1);
    secondClient.save();
    assertEquals(true, Client.all().get(0).equals(firstClient));
    assertEquals(true, Client.all().get(1).equals(secondClient));
  }

  @Test
  public void find_returnsClientWithSameId_secondClient() {
    Client firstClient = new Client("Vain Valerie", "206-XXX-XXXX", 1);
    firstClient.save();
    Client secondClient = new Client("Narcissistic Nate", "206-XXX-XXXX", 3);
    secondClient.save();
    assertEquals(Client.find(secondClient.getId()), secondClient);
  }

  @Test
  public void save_savesStylistIdIntoDB_true() {
    Stylist testStylist = new Stylist("Henry", "Brazilian Blowouts");
    testStylist.save();
    Client testClient = new Client("Vain Valerie", "206-XXX-XXXX", testClient.getId());
    testClient.save();
    Client savedClient = Client.find(testClient.getId());
    assertEquals(savedClient.getStylistId(), testStylist.getId());
  }

  @Test
  public void Client_showAssignedClientsToStylist_ArrayList() {
    assertTrue(Client.assigned(1).size() > 0);
  }
}
