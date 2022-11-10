local bcc = require('better_cc')

modems = bcc.getModems()
for i, modem in pairs(modems) do
    print(i.." = ?.?.?.?")
    print(" id = "..modem.id)
end