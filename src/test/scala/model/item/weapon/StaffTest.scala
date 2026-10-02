package cl.uchile.dcc
package model.item.weapon

class StaffTest extends munit.FunSuite:
  var staff: Staff = null

  override def beforeEach(context: BeforeEach): Unit =
    staff = new Staff("Healing Staff", 10, 8, 30)

  test("A staff has a name, attack points, weight and magic attack points"):
    assertEquals(staff.name, "Healing Staff")
    assertEquals(staff.attack, 10)
    assertEquals(staff.weight, 8)
    assertEquals(staff.magicAttack, 30)

  test("A staff starts without an owner"):
    assertEquals(staff.owner, None)

  test("Two staves with the same attributes are equal"):
    val other = new Staff("Healing Staff", 10, 8, 30)
    assertEquals(staff, other)
    assertEquals(other, staff)

  test("Two staves with a different attribute are not equal"):
    assertNotEquals(staff, new Staff("Healing Staff II", 10, 8, 30))
    assertNotEquals(staff, new Staff("Healing Staff", 15, 8, 30))
    assertNotEquals(staff, new Staff("Healing Staff", 10, 10, 30))
    assertNotEquals(staff, new Staff("Healing Staff", 10, 8, 35))

  test("A staff is not equal to a wand with the same attributes"):
    val other: Weapon = new Wand("Healing Staff", 10, 8, 30)
    assertNotEquals(other, staff)