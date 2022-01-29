--[[- This is the Forge version.

A Randomizer wrapped as a peripheral.

This allows for basic interaction with adjacent randomizers.
@source src/main/java/net/ddns/minersonline/better_cc/forge/peripherals/RandomPeripheral.java:24
@module[kind=peripheral] randomizer forge
]]

--[[- Returns a random number that is also the redstone output of the Randomizer Block

@source src/main/java/net/ddns/minersonline/better_cc/forge/peripherals/RandomPeripheral.java:58
@treturn number The random number itself
]]
function getRandom() end

--[[- Sets the enabled state on the Randomizer Block

@source src/main/java/net/ddns/minersonline/better_cc/forge/peripherals/RandomPeripheral.java:69
@tparam boolean state True or False
]]
function toggle(state) end
