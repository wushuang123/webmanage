package com.xifar.console.session;

//public class Example {
//
//    // inject the actual template
//    @Autowired
//    private RedisTemplate<String, String> template;
//
//    // inject the template as ListOperations
//    // can also inject as Value, Set, ZSet, and HashOperations
//    @Resource(name="redisTemplate")
//    private ListOperations<String, String> listOps;
//
//    public void addLink(String userId, URL url) {
//        listOps.leftPush(userId, url.toExternalForm());
//        // or use template directly
//        template.boundListOps(userId).leftPush(url.toExternalForm());
//    }
//}
