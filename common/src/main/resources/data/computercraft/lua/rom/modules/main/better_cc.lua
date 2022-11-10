local better_cc = {}

better_cc.getModem = function (side)
    if peripheral.isPresent(side) and peripheral.getType(side) == "serial_modem" then
        luaMod = {}
        modem = peripheral.wrap(side)
        id = modem.getUUID()

        luaMod.id = id
        luaMod.send = modem.send
        return luaMod
    end
end

better_cc.getModems = function()
    modems = {}
    for _, side in pairs(rs.getSides()) do
        modems[side] = better_cc.getModem(side)
    end
    return modems
end

return better_cc