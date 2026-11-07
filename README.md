# Tarea 2 - Final Reality Tactics

Benjamín Sánchez Leyton 

## Organización

El código está en `src/main/scala`, separado en `controller` y `model`. Dentro de `model` hay un paquete por cada parte del juego: `action` (acciones y hechizos), `item` (armas y pociones), `panel`, `player`, `turn` (barras de acción) y `unit` (personajes y enemigos). Los tests en `src/test/scala` tienen la misma estructura.

Cada concepto tiene un trait como contrato, una clase abstracta con el código en común y las clases concretas. Los atributos son privados con getters.

## Sistema de acciones

Para que una acción dependa del tipo de la unidad que la hace, de la acción y del objetivo, usé double dispatch encadenado:

1. La unidad llama a `doByCharacter`, `doByBlackWizard`, `doByWhiteWizard` o `doByEnemy` de la acción, según su tipo.
2. La acción llama al método `receive...` del objetivo que le corresponde (por ejemplo `receiveAttack` o `receiveThunder`).
3. El objetivo aplica el efecto o lanza `InvalidActionException`.

`AbstractTarget` y `AbstractAction` rechazan todo por defecto, y cada clase sobrescribe solo lo que sí puede hacer o recibir. Así no hay ningún `isInstanceOf`.

Equipar armas y consumir pociones también usan double dispatch entre el ítem y el personaje (por ejemplo `sword.receiveEquip(c)` llama a `c.equipSword(this)`), y cada personaje acepta solo las armas de la tabla del enunciado.

## Arma y panel vacíos

En vez de usar `Option` y preguntar si el arma está vacía, un personaje sin arma tiene `NoWeapon`, que pesa 0 y no permite atacar ni lanzar hechizos. Una unidad que no está en el mapa está en `NoPanel`, que no tiene vecinos.

## Restricciones

Todas las restricciones de la parcial 4 lanzan `InvalidActionException` con un mensaje. Por ejemplo, una unidad derrotada no puede actuar ni recibir acciones, y un mago necesita maná suficiente y un arma mágica para lanzar hechizos. Si un hechizo falla, el mago no pierde maná.

## Controlador

`GameController` usa el `TurnScheduler` para decidir quién juega, ejecuta la acción de la unidad del turno y reinicia el juego. Si una acción falla, el turno no cambia, se imprime el error y queda guardado en `lastError`.

## Decisiones propias

- Solo se pueden equipar armas o consumir pociones que estén en el inventario, y la poción se elimina al consumirla.
- El inventario guarda las armas y las pociones por separado.
- Por ahora el juego es un loop infinito de turnos.