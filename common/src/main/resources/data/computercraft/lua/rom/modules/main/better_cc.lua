local better_cc = {}

better_cc.getModems = function()
    modems = {}
    for _, side in pairs(rs.getSides()) do
        if peripheral.isPresent(side) and peripheral.getType(side) == "serial_modem" then
            modems[#modems] = side
        end
    end
    return modems
end

return better_cc