--[[
   本脚本可以实现一个简单的限流功能
 --]]

local key =  KEYS[1]
local limit = tonumber(ARGV[1])
local expire_time = ARGV[2]
-- 该脚本的
local is_exists = redis.call("EXISTS", key)
if is_exists == 1 then -- 如果指定的key已经存在
    if redis.call("INCR", key) > limit then --将key对应的value+1，并判断是否已经大于限定值limit
        return 0 -- 如果大于了限定值返回0
    else
        return 1 --如果小于限定值返回1
    end
else -- 指定的key还不存在，将其value设置为1，并设置该key的失效时间
    redis.call("SET", key, 1)
    redis.call("EXPIRE", key, expire_time)
    return 1
end
