package cn.dao.impl;

import cn.dao.UserDao;
import cn.domain.User;
import cn.utils.JDBCUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<User> findAll() {
        //使用JDBC操作数据库
        String sql = "select * from user";
        return template.query(sql, new BeanPropertyRowMapper<User>(User.class));
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try {
            String sql = "select * from user where username = ? and password = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
            return user;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(User user) {
        String sql = "insert into user values(?,null,?,?,?,?,?,?,null)";
        template.update(sql, user.getId(), user.getName(),
                user.getGender(), user.getAge(), user.getAddress(), user.getNumber(), user.getPhone());
    }

    @Override
    public void delete(int id) {
        String sql = "delete from user where id = ?";
        template.update(sql, id);
    }

    @Override
    public User findUserById(int id) {
        String sql = "select * from user where id = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), id);
    }

    @Override
    public void updateUser(User user) {
        String sql = "update user set name = ?, gender = ? ,age = ? ,address = ?, number = ? , phone = ? where id = ?";
        template.update(sql,user.getName(),user.getGender(),
                user.getAge(),user.getAddress(),user.getNumber(),user.getPhone(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //定义sql模板
        String sql = "select count(*) from user where 1 = 1";
        StringBuilder sb = new StringBuilder(sql);
        //遍历condition
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否为空
            if (value != null && !"".equals(value) ){
                sb.append(" and ").append(key).append(" like ? ");
                params.add("%"+value+"%");
            }
        }
        return template.queryForObject(sb.toString(),Integer.class,params.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from user where 1 =1 ";
        StringBuilder sb = new StringBuilder(sql);
        //遍历condition
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {
            //排除分页条件参数
            if ("currentPage".equals(key) || "rows".equals(key)){
                continue;
            }
            //获取value
            String value = condition.get(key)[0];
            //判断value是否为空
            if (value != null && !"".equals(value) ){
                sb.append(" and ").append(key).append(" like ? ");
                params.add("%"+value+"%");
            }
        }
        sb.append(" limit ? , ? ");
        params.add(start);
        params.add(rows);
        sql = sb.toString();
        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }
}
