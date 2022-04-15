package cn.service.impl;

import cn.dao.UserDao;
import cn.dao.impl.UserDaoImpl;
import cn.domain.PageBean;
import cn.domain.User;
import cn.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public List<User> findAll() {
        //调用dao完成查询
        return dao.findAll();
    }

    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public void add(User user) {
        dao.add(user);
    }

    @Override
    public void delete(String id) {
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public User findUserById(String id) {
        return dao.findUserById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Override
    public void deleteSelected(String[] uids) {
        if (uids != null && uids.length > 0) {
            for (String uid : uids) {
                dao.delete(Integer.parseInt(uid));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String s_currentPage, String s_rows, Map<String, String[]> condition) {
        int currentPage = Integer.parseInt(s_currentPage);
        int rows = Integer.parseInt(s_rows);
        if (currentPage <= 0) {
            currentPage = 1;
        }
        //1.创建空的PageBean对象
        PageBean<User> pb = new PageBean<User>();
        //2.设置currentPage属性和rows属性
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //3.调用dao查询totalCount,总记录数dao.findTotalCount( ;
        int totalCount = dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        //4.start = (currentPage - 1)*rows
        int start = (currentPage - 1) * rows;
        //5.调用dao查询list集合 dao.findByPage(int start,int rows)
        List<User> list = dao.findByPage(start, rows,condition);
        pb.setList(list);
        //6.计算总页码
        int totalPage = (totalCount % rows) == 0 ? (totalCount/rows) : (totalCount/rows) + 1;
        pb.setTotalPage(totalPage);
        //7.返回PageBean对象
        return pb;
    }
}
