package com.whut.work.user.service.impl;

import com.whut.work.user.dao.impl.RoleDaoImpl;
import com.whut.work.user.dao.impl.UserRoleDaoImpl;
import com.whut.work.user.model.Role;
import com.whut.work.user.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.whut.work.base.model.Page;
import com.whut.work.user.dao.impl.UserDaoImpl;
import com.whut.work.user.model.User;
import com.whut.work.user.service.IUserService;
import com.whut.work.base.util.JavaStringUtil;
import com.whut.work.movie.dao.impl.SaleDaoImpl;
import com.whut.work.movie.service.ISaleService;

import java.util.*;

@Component
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserDaoImpl userDao;
    @Autowired
    private RoleDaoImpl roleDao;
    @Autowired
    private UserRoleDaoImpl urDao;
    @Autowired
    private ISaleService saleService;


	@Override
	public Page<User> getUserPageList(int currentPage, int pageSize,String blurUserName) throws Exception {
		String queryHql = "from User u where u.username like '%"+blurUserName+"%' ";
		String countHql = "select count(*) from User u where u.username like '%"+blurUserName+"%' ";
		Page<User> returnPage = userDao.findPage(currentPage, pageSize, queryHql, countHql);
		
		return returnPage;                                 
	}

    @Override
    public Page<User> getUserPageListForSearch(int currentPage, int pageSize,String blurUserName) throws Exception {
        String queryHql = " from User u where u.id!=0 and u.username like '%"+blurUserName+"%' ";
        String countHql = " select count(*) from User u where u.id!=0 and u.username like '%"+blurUserName+"%' ";
        Page<User> returnPage = userDao.findPage(currentPage, pageSize, queryHql, countHql);

        return returnPage;
    }

    @Override
    public Map<String, Object> deleteOneUser(Integer id) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();

        //String hql = "from User u where u.id='"+id+"'";
        User user = new User();
        try {
            user = userDao.getOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user != null){
            user.setIsDelete(true);
            userDao.update(user);
            saleService.deleteSaleByUser(id);
            //然后把user_role关系表中的对应记录删除
            /*String delhql = " delete UserRole where userId='"+id+"' ";
            urDao.deleteWithHql(delhql);*/

            returnMap.put("message", "封号成功");
            returnMap.put("success", true);
            return returnMap;
        }else{
            returnMap.put("message", "操作失败");
            returnMap.put("success", false);
            return returnMap;
        }
    }

    public Map<String, Object> deleteOneUserInverse(Integer id) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();

        //String hql = "from User u where u.id='"+id+"'";
        User user = new User();
        try {
            user = userDao.getOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user != null){
            user.setIsDelete(false);
            userDao.update(user);
            //然后把user_role关系表中的对应记录删除
            /*String delhql = " delete UserRole where userId='"+id+"' ";
            urDao.deleteWithHql(delhql);*/

            returnMap.put("message", "解封成功");
            returnMap.put("success", true);
            return returnMap;
        }else{
            returnMap.put("message", "操作失败");
            returnMap.put("success", false);
            return returnMap;
        }
    }

    @Override
    public Map<String, Object> getRoleBelongToUser(Integer id) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();

        String hql = " from UserRole ur where ur.userId='"+id+"' ";
        List<UserRole> urList = urDao.findList(hql);
        if(urList != null){
            returnMap.put("urList", urList);
            returnMap.put("message", "获取成功");
            returnMap.put("success", true);
            return returnMap;
        }else{
            returnMap.put("message", "没有找到相应结果");
            returnMap.put("success", false);
            return returnMap;
        }
    }

    @Override
    public Map<String, Object> getUsersOfOneRole(Integer id) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();

        //any关键字表示子查询中的结果每一条都分别与u.id做比较
        String hql = " select new com.whut.work.user.vo.UserVo(u.id,u.username,u.tel,u.email,u.createTime) from User u where " +
                "u.id=any(select ur.userId from UserRole ur where ur.roleId="+id+") ";

        /*String hqlCount = " select count(*) from User u where " +
                "u.id=any(select ur.userId from UserRole ur where ur.roleId="+id+") ";*/
        List<User> urList = userDao.findList(hql);
        if(urList != null){
            returnMap.put("urList", urList);
            returnMap.put("message", "获取成功");
            returnMap.put("success", true);
            return returnMap;
        }else{
            returnMap.put("message", "没有找到相应结果");
            returnMap.put("success", false);
            return returnMap;
        }
    }

    @Override
    public Map<String, Object> roleForOneUser(Integer id, String roleList) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        String hql = " from UserRole ur where ur.userId='"+id+"' ";
        String delhql = " delete UserRole where userId='"+id+"' ";

        List<UserRole> urFromJspList = new ArrayList<UserRole>();
        List<Integer> roleIdList = JavaStringUtil.stringToList(roleList,"-");
        for(int i=0;i<roleIdList.size();i++){
            UserRole ur = new UserRole();
            ur.setUserId(id);
            ur.setRoleId(roleIdList.get(i));
            urFromJspList.add(ur);
        }

        if(urDao.findList(hql) != null){
            urDao.deleteWithHql(delhql);
            urDao.batchSave(urFromJspList);
        }else{
            urDao.batchSave(urFromJspList);
        }
        JavaStringUtil.setListInt(new ArrayList<Integer>());
        returnMap.put("message", "为用户分配角色成功");
        returnMap.put("success", true);
        return returnMap;
    }

    @Override
    public Map<String,Object> addRole(String roleName) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();

        String hql = "from Role r where r.name='"+roleName+"'";
        Role role = new Role();
        if(roleDao.findOne(hql) != null){
            returnMap.put("message", "该角色已存在");
            returnMap.put("success", false);
            return returnMap;
        }else{
            role.setName(roleName);
            role.setCreateTime(new Date());
            roleDao.save(role);
            returnMap.put("value", role);
            returnMap.put("message", "新增角色成功");
            returnMap.put("success", true);
            return returnMap;
        }
    }

    @Override
    public Page<Role> getRolePageList(int currentPage, int pageSize) throws Exception {
        String queryHql = "from Role r where r.id!=0";
        String countHql = "select count(*) from Role r where r.id!=0";
        Page<Role> returnPage = roleDao.findPage(currentPage, pageSize, queryHql, countHql);

        return returnPage;
    }

    @Override
    public List<Role> getSimpleRolePageList() throws Exception {
        String queryHql = "select new com.whut.work.user.vo.RoleVo(r.id,r.name) from Role r where r.id!=0";
        List<Role> returnPage = roleDao.findList(queryHql);

        return returnPage;
    }

    @Override
    public Page<Role> getRolePageListForSearch(int currentPage, int pageSize,String blurRoleName) throws Exception {
        String queryHql = "from Role r where r.id!=0 and r.name like '%"+blurRoleName+"%'";
        String countHql = "select count(*) from Role r where r.id!=0 and r.name like '%"+blurRoleName+"%'";
        Page<Role> returnPage = roleDao.findPage(currentPage, pageSize, queryHql, countHql);

        return returnPage;
    }

    @Override
    public Map<String, Object> deleteOneRole(Integer id) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();

        //String hql = "from User u where u.id='"+id+"'";
        Role role = new Role();
        try {
            role = roleDao.getOne(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(role != null){
            roleDao.delete(role);
            //然后把user_role关系表中的对应记录删除
            String delhql = " delete UserRole where roleId='"+id+"' ";
            urDao.deleteWithHql(delhql);

            returnMap.put("message", "删除成功");
            returnMap.put("success", true);
            return returnMap;
        }else{
            returnMap.put("message", "删除失败");
            returnMap.put("success", false);
            return returnMap;
        }
    }

    @Override
    public Map<String, Object> unFriendUserOfRole(Integer roleId, Integer userId) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();

            String delhql = " delete UserRole where roleId='"+roleId+"' and userId='"+userId+"' ";
            urDao.deleteWithHql(delhql);

            returnMap.put("message", "删除成功");
            returnMap.put("success", true);
            return returnMap;
    }

    @Override
    public Map<String, Object> editOneRole(Integer id, String roleName) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();

        String hqlfirst = "from Role r where r.name='"+roleName+"'";
        String hql = "from Role r where r.id='"+id+"'";
        Role role = new Role();

        try {
            role = roleDao.findOne(hql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(role != null){
            if(roleDao.findOne(hqlfirst) != null){
                returnMap.put("message", "该角色已存在");
                returnMap.put("success", false);
                return returnMap;
            }
            role.setName(roleName);
            role.setCreateTime(new Date());
            roleDao.update(role);
            returnMap.put("value", role);
            returnMap.put("message", "修改角色成功");
            returnMap.put("success", true);
        }else{
            returnMap.put("message", "异常:编辑失败");
            returnMap.put("success", false);
        }
        return returnMap;
    }

}
