import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

public class StylistTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", "postgres", null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteStylistsQuery = "DELETE FROM stylists *;";
      con.createQuery(deleteStylistsQuery).executeUpdate();
    }
  }

  @Test
  public void stylist_instantiatesCorrectly_true() {
    Stylist testStylist = new Stylist("Henry", "Brazilian Blowouts");
    assertEquals(true, testStylist instanceof Stylist);
  }

  @Test
  public void getName_stylistInstantiatesWithName_Henry() {
    Stylist testStylist = new Stylist("Henry", "Brazilian Blowouts");
    assertEquals("Henry", testStylist.getName());
  }

  @Test
  public void getExpertise_stylistInstantiatesWithExpertise_String() {
    Stylist testStylist = new Stylist("Henry", "Brazilian Blowouts");
    assertEquals("Brazilian Blowouts", testStylist.getExpertise());
  }

  @Test
  public void equals_returnsTrueIfNameAndEmailAreSame_true() {
    Stylist firstStylist = new Stylist("Henry", "Brazilian Blowouts");
    Stylist anotherStylist = new Stylist("Henry", "Brazilian Blowouts");
    assertTrue(firstStylist.equals(anotherStylist));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Stylist() {
    Stylist testStylist = new Stylist("Henry", "Brazilian Blowouts");
    testStylist.save();
    assertTrue(Stylist.all().get(0).equals(testStylist));
  }

  @Test
  public void all_returnsAllInstancesOfStylist_true() {
    Stylist firstStylist = new Stylist("Henry", "Brazilian Blowouts");
    firstStylist.save();
    Stylist secondStylist = new Stylist("Harriet", "Precision Haircuts");
    secondStylist.save();
    assertEquals(true, Stylist.all().get(0).equals(firstStylist));
    assertEquals(true, Stylist.all().get(1).equals(secondStylist));
  }
}
