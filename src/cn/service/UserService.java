package cn.service;

import cn.domain.PageBean;
import cn.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的业务接口
 */
public interface UserService {
    /**
     * 查询所有用户信息
     *
     * @return
     */
    public List<User> findAll();

    /**
     * 登录方法
     * @param user
     * @return
     */
    public User login(User user);

    /**
     * 添加方法
     * @param user
     */
    public void add(User user);

    /**
     * 删除方法
     * @param id
     */
    public void delete(String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public User findUserById(String id);

    /**
     * 修改信息
      * @param user
     */
    public void updateUser(User user);

    /**
     * 删除选中
     * @param uids
     */
    void deleteSelected(String[] uids);

    /**
     * 分页查询
     * 条件查询
     * @param currentPage
     * @param rows
     * @param condition
     * @return
     */
    PageBean<User> findUserByPage(String currentPage, String rows, Map<String, String[]> condition);
}
