package cn.dao;

import cn.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作的dao接口
 */
public interface UserDao {
    public List<User> findAll();
    public User findUserByUsernameAndPassword(String username,String password);
    public void add(User user);
    public void delete(int id);
    public User findUserById(int id);
    public void updateUser(User user);
    public int findTotalCount(Map<String, String[]> condition);
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
