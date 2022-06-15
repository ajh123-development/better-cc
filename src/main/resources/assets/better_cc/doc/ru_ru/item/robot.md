# Робот
![Приветствую наших новых повелителей роботов](item:better_cc:robot)

Фактически роботы представляют из себя маленькие [компьютеры](../block/computer.md). Однако учитывая их нестационарное назначение, роботы несколько отличаются от обычных компьютеров. Они не могут подключаться к [интерфейсной шине](../block/bus_interface.md). Вместо карт расширения они поддерживают модульные устройства. Это специальные устройства, разработанные с учетом мобильности роботов.

Роботы имеют фиксированный по размеру инвентарь и суперсовременное емкое энергохранилище. Только предметный инвентарь роботов может быть автоматически заполнен и опустошен, например, с помощью таких устройств, как воронка. Компонентный инвентарь робота конфигурируется вручную.

В стандартной конфигурации робот не может работать со своим инвентарем. Используйте [модуль управления инвентарем](inventory_operations_module.md), дабы робот мог перемещать предметы в своем инвентаре, выкидывать и поднимать их с игрового мира или хранилища.

Для подзарядки робота необходимо использовать [зарядное устройство](../block/charger.md). Для зарядки достаточно передвинуть робота на верх устройства. Или вы можете поставить над зарядным устройством какое-либо хранилище и положить робота в него.

Дистрибутив Linux предоставляет специальную Lua библиотеку `robot`, упрощающую контроль над роботами. Базовое API предлагает асинхронные методы для перемещения. Библиотека же добавляет синхронные альтернативы для более удобного программирования.

## API
Название устройства: `robot`

Это устройство с высокоуровневым API. Оно может управляться через Lua в стандартном дистрибутиве Linux. Например:  
`local d = require("devices")`  
`local r = d:find("robot")`  
`r:move("forward")`

### Направления
Направления указываются от лица робота. Допускаются значения: `forward`, `backward`, `upward`, `downward` для движения, `left` и `right` для поворотов.

Для удобства доступны сокращения некоторых сторон: `back`, `up`, `down`. Для максимальной краткости можно использовать первую букву стороны.

### Методы
Эти методы доступны в стандартной конфигурации робота. Для более удобной работы существует обертка этого устройства. Рекомендуется использовать именно эту обертку, вместо прямого доступа к роботу.

`getEnergyStored():number` возвращает текущее количество запасенной энергии в энергохранилище робота.
- Возвращает текущее количество запасенной энергии.

`getEnergyCapacity():number` возвращает максимальное количество энергии, которое можно запасти в роботе.
- Возвращает максимальное количество энергии, которое можно запасти.

`getSelectedSlot():number` возвращает номер текущего слота в инвентаре робота. Этот слот используется большинством модулей для взаимодействия.
- Возвращает номер текущего слота в инвентаре.

`setSelectedSlot(slot:number):number` устанавливает текущий слот в инвентаре робота. Этот слот используется большинством модулей для взаимодействия.
- `slot` - номер слота в инвентаре для выбора.
- Возвращает номер нового текущего слота. Он может отличаться от указанного в переменной `slot`, если указанное там значение является некорректным.

`getStackInSlot(slot:number):table` получает описание предмета в указанном слоте.
- `slot` - номер слота, о котором нужно получить информацию.

`move(direction):boolean` пробует передвинуться в указанном направлении.
- `direction` - направление для перемещения.
- Возвращает статус выполненной задачи (`true` - удача, `false` - неудача).

`turn(direction):boolean` пробует повернуться в указанную сторону.
- `direction` - направление поворота.
- Возвращает статус выполненной задачи (`true` - удача, `false` - неудача).

`getLastActionId():number` возвращает id последнего совершенного действия. Вызовите его после успешного вызова `move()` или `turn()`, чтобы получить id, связанный с поставленным в очередь действием.
- Возвращает id последнего совершенного действия.

`getQueuedActionCount():number` возвращает количество поставленных в очередь действий, ожидающих выполнения. Используйте его, чтобы дождаться завершения действий при сбое постановки в очередь.
- Возвращает количество поставленных в очередь действий.

`getActionResult(actionId:number):string` возвращает результат действия по его id. Id последнего действия можно получить функцией `getLastActionId()`. Возможно посмотреть результаты только некоторого количества последних действий.
- Возвращает результат действия по его id или ничего, если результат недоступен. Доступные результаты имеют следующие статусы: `INCOMPLETE`, `SUCCESS` и `FAILURE`.

### Библиотека для API
- Название библиотеки: `robot`

Это Lua библиотека. Она может использоваться в стандартном дистрибутиве Linux. Например:  
`local r = require("robot")`  
`r.move("forward")`  
`r.turn("left")`

### Методы
`energy():number` возвращает текущее количество запасенной энергии в энергохранилище робота.
- Возвращает текущее количество запасенной энергии.

`capacity():number` возвращает максимальное количество энергии, которое можно запасти в роботе.
- Возвращает максимальное количество энергии, которое можно запасти.

`slot():number` возвращает номер текущего слота в инвентаре робота. Этот слот используется большинством модулей для взаимодействия.
- Возвращает номер текущего слота в инвентаре.

`slot(slot:number):number` устанавливает текущий слот в инвентаре робота. Этот слот используется большинством модулей для взаимодействия.
- `slot` - номер слота в инвентаре для выбора.
- Возвращает номер нового текущего слота. Он может отличаться от указанного в переменной `slot`, если указанное там значение является некорректным.

`stack([slot:number]):table` получает описание предмета в указанном слоте.
- `slot` - номер слота, о котором нужно получить информацию. Опционален, по умолчанию равен `slot()`.

`move(direction):boolean` пробует передвинуться в указанном направлении. Блокирует выполнение программы, пока операция не выполнится.
- `direction` - направление для перемещения.
- Возвращает статус выполненной задачи (`true` - удача, `false` - неудача).

`moveAsync(direction)` пробует асинхронно передвинуться в указанном направлении. Блокирует выполнение программы, пока операция не будет поставлена в очередь.
- `direction` - направление для перемещения.

`turn(direction):boolean` пробует повернуться в указанную сторону. Блокирует выполнение программы, пока операция не выполнится.
- `direction` - направление поворота.
- Возвращает статус выполненной задачи (`true` - удача, `false` - неудача).

`turnAsync(direction)` пробует асинхронно повернуться в указанную сторону. Блокирует выполнение программы, пока операция не будет поставлена в очередь.
- `direction` - направление поворота.