--[[- This is the Fabric version.

A Randomizer wrapped as a peripheral.

This allows for basic interaction with adjacent randomizers.
@source src/main/java/net/ddns/minersonline/better_cc/fabric/peripherals/RandomPeripheral.java:23
@module[kind=peripheral] randomizer fabric
]]

--[[- Returns a random number that is also the redstone output of the Randomizer Block

@source src/main/java/net/ddns/minersonline/better_cc/fabric/peripherals/RandomPeripheral.java:56
@treturn number The random number itself
]]
function getRandom() end

--[[- Sets the enabled state on the Randomizer Block

@source src/main/java/net/ddns/minersonline/better_cc/fabric/peripherals/RandomPeripheral.java:67
@tparam boolean state True or False
]]
function toggle(state) end
