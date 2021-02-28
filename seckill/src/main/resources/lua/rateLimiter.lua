-- 获取key
local key1 = KEYS[1]

local val = redis.call('incr', key1)
local ttl = redis.call('ttl', key1)
-- 获取 argv里的参数
local expire = ARGV[1]
local limitNum = ARGV[2]

if val == 1 then
    redis.call('expire', key1, tonumber(expire))
else
    if ttl == -1 then
        redis.call('expire', key1, tonumber(expire))
    end
end
if val > tonumber(limitNum) then
    return 0
end
return 1