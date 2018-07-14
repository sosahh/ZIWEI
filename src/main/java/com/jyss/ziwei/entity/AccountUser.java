package com.jyss.ziwei.entity;

import java.util.Date;

public class AccountUser {

	private int id;
	private int roleId;
	private String roleName;
	private String roleSign;
	private String permissionName;
	private String permissionSign;
	private String permissionId;
	private String name;
	private String salt;
	private String username;
	private String password;
	private String code;//标题排序码
	private String href;//点击链接
	private String title;//UI显示列表标题
	private String icon;//图标

	private String description;
	private int status;
	private Date lastLoginTime;//
	private Date createdAt;// 创建时间
	private String cjsj;// 创建时间
	private String dlsj;// 登录时间
	private Integer userId;   //对应app端用户id

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode(){return code;}
	public void setCode(String code) {this.code = code ;}

	public String getIcon(){return icon;}
	public void setIcon(String icon) {this.icon = icon ;}

	public String getHref(){return href;}
	public void setHref(String href) {this.href = href ;}

	public String getTitle(){return title;}
	public void setTitle(String title) {this.title = title ;}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return roleId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionSign() {
		return permissionSign;
	}

	public void setPermissionSign(String permissionSign) {
		this.permissionSign = permissionSign;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleSign() {
		return roleSign;
	}

	public void setRoleSign(String roleSign) {
		this.roleSign = roleSign;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCjsj() {
		return cjsj;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getDlsj() {
		return dlsj;
	}

	public void setDlsj(String dlsj) {
		this.dlsj = dlsj;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
