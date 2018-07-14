package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.AccountLog;
import com.jyss.ziwei.entity.AccountUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;


public interface AccountUserService {
	/**
	 * 获取登录用户信息
	 * @param username
	 * @param id
	 * @return
	 */
	AccountUser getAuBy(@Param("username") String username,@Param("id") String id);

	/**
	 * 获取角色列表信息
	 *
	 * @param roleSign
	 * @return
	 */
	List<AccountUser> getRoles(@Param("roleSign") String roleSign);

	/**
	 * 获取用户列表信息
	 * @param username
	 * @param id
	 * @return
	 */
	List<AccountUser> getAuByUsername(@Param("username") String username,@Param("id") String id);

	/**
	 * 获取登录用户权限信息
	 * @param username
	 * @param id
	 * @return
	 */

	List<AccountUser> getPermissionLsitBy(@Param("username") String username,@Param("id") String id);

	/**
	 * 获取菜单树
	 *
	 * @param code
	 * @return
	 */

	List<AccountUser> getMennuTree(@Param("code") String code);


	/**
	 * 获取登录用户权限信息
	 *
	 * @param username
	 * @param id
	 * @return
	 */

	Set<AccountUser> getPermissionBy(@Param("username") String username,@Param("id") String id);

	/**
	 * 获取配菜单信息
	 *
	 * @param roleId
	 * @return
	 */
	List<AccountUser> getRolePermission(@Param("roleId") String roleId);


	/**
	 * 判断当前登录用户是否唯一
	 * @param username
	 * @param id
	 * @return
	 */
	int getAuNum(@Param("username") String username,@Param("id") String id);

	int upHtPwd(@Param("username") String username,
				@Param("password") String password, @Param("salt") String salt,@Param("id") String id);

	AccountUser getAuByUsernameAndPassword(@Param("username") String username,
										   @Param("password") String password,@Param("id") String id);

	/**
	 * 增加用户
	 *
	 * @param au
	 * @return
	 *
	 */
	int addAccount(AccountUser au);

	/**
	 * 增加角色
	 *
	 * @param au
	 * @return
	 *
	 */
	int addRoles(AccountUser au);

	/**
	 * 增加权限关联
	 *
	 */
	int addrolePermission(@Param("roleId") String roleId,@Param("permissionId") String permissionId);

	/**
	 * 修改用户
	 *
	 * @param au
	 * @return
	 *
	 */
	int upAccount(AccountUser au);

	/**
	 * 删除
	 *
	 * @param ids
	 * @return
	 *
	 */
	int deleteAccounts(@Param("ids") List<Long> ids);

	/**
	 * 删除
	 *
	 * @param ids
	 * @return
	 *
	 */
	int delRoles(@Param("ids") List<Long> ids);


	/**
	 * 禁用
	 *
	 * @param ids
	 * @return
	 *
	 */
	int upAccountStatus(@Param("ids") List<Long> ids,
						@Param("status") String status);

	//////20180330增加/////

	/**
	 * 获取用户根据姓名和权限
	 * @param username
	 * @param roleId
	 * @param id
	 * @return
	 */

	List<AccountUser> getPermissionAndName(@Param("username") String username,@Param("roleId") String roleId,@Param("id") String id);

	/**
	 * 总监助理获取信息
	 * @param id
	 * @param roleId
	 * @return
	 */
	List<AccountUser> getZlRole(@Param("id") String id,@Param("roleId") String roleId);


	/**
	 * 修改角色
	 *
	 * @param au
	 * @return
	 *
	 */
	int upRoles(AccountUser au);

	/**
	 * 删除权限
	 *
	 * @param roleId
	 * @return
	 *
	 */
	int delRolePermission(@Param("roleId") String roleId);

	/**
	 * 增加用户操作日志
	 *
	 * @param description
	 * @param username
	 * @return
	 *
	 */
	int addLog(@Param("username") String username,@Param("description") String description);

	/**
	 * 查询用户操作日志
	 * @param username
	 * @return
	 */
	List<AccountLog> getAccountLog(@Param("username") String username);

	int updateMyRoles(AccountUser au, @Param("ids") List<Long> ids);
	int addMyRoles(AccountUser au, @Param("ids") List<Long> ids);



}
