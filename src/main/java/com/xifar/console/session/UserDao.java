package com.xifar.console.session;

public interface UserDao {  
    /** 
     * @param uid 
     * @param address 
     */  
    void save(User user);  
  
    /** 
     * @param uid 
     * @return 
     */  
    User read(String uid);  
  
    /** 
     * @param uid 
     */  
    void delete(String uid);  
}  