local function log(...)
    io.write(...)
end

local global = {
    log = log
}

return global