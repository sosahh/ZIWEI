package com.jyss.ziwei.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jyss.ziwei.entity.*;
import com.jyss.ziwei.service.AccountUserService;
import com.jyss.ziwei.service.WebLoginService;
import com.jyss.ziwei.shiro.ShiroToken;
import com.jyss.ziwei.utils.CommTool;
import com.jyss.ziwei.utils.PasswordUtil;
import com.jyss.ziwei.utils.Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.rmi.server.UID;
import java.util.*;

@Controller
@RequestMapping("/accountUser")
public class AccountUserAction {
	@Autowired
	private AccountUserService auService;
	@Autowired
	private WebLoginService webLoginService;


	@RequestMapping("/shiro-getLogin")
	@ResponseBody
	public Map<String,Object> getLogin(@RequestParam("logName") String logName,
			@RequestParam("logPass") String logPass) {
		Map<String,Object> m =  new HashMap<String,Object>();
		Map<String,Object> mm =  new HashMap<String,Object>();
		m.put("status","0");
		m.put("msg","");
		m.put("data","");
		m.put("token","");
		m.put("user","");

		///业务操作
		int isOnly = 0;
		isOnly = auService.getAuNum(logName,null);
		if (isOnly == 0) {
			m.put("msg","账号不存在！");
			return m;
		}
		if (isOnly != 1) {
			m.put("msg","账号冲突！");
			return m;
		}
		AccountUser dbAu = auService.getAuBy(logName,null);
		if(!PasswordUtil.generate(logPass, dbAu.getSalt()).equals(dbAu.getPassword())){
			m.put("msg","密码错误！");
			return m;
		}
		mm.put("user",dbAu);
		//记录登陆信息
		String token = CommTool.getUUID();
		MobileLogin mobileLogin = new MobileLogin();
		mobileLogin.setuId(new Long(dbAu.getId()));
		mobileLogin.setToken(token);
		mobileLogin.setLastAccessTime(System.currentTimeMillis());
		mobileLogin.setStatus(1);
		mobileLogin.setCreatedAt(new Date());
		int count = webLoginService.insert(mobileLogin);
		if(count!=1){
			m.put("msg","登录失败！");
			return m;
		}
		auService.addLog(dbAu.getId()+"","当前用户-登录");
		mm.put("token",token);
		m.put("data",mm);
		return m;


	}


	@RequestMapping("/upHtPwd")
	@ResponseBody
	public ResponseEntity upHtPwd(@RequestParam("token")String token,@RequestParam("newPwd") String newPwd,@RequestParam("oldPwd") String oldPwd,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		int count = 0;
		////判断原密码
		List<AccountUser> aulist = auService.getPermissionAndName(null,null,uId.toString());
		if (aulist==null||aulist.size()!=1){
			return  ResponseEntity.op("0", "用户信息异常！");
		}
		System.out.print(oldPwd+"==================>"+aulist.get(0).getSalt()+"==================>"+aulist.get(0).getPassword());
		if(oldPwd==null||oldPwd.equals("")||!(PasswordUtil.generate(oldPwd, aulist.get(0).getSalt()).equals(aulist.get(0).getPassword()))){
			return  ResponseEntity.op("0", "原密码错误！");
		}
		String salt = CommTool.getSalt();
		count = auService.upHtPwd(null, newPwd, salt,uId.toString());
		if (count == 1) {
			auService.addLog(uId.toString(),"当前用户-修改密码");
			return  ResponseEntity.op("1", "操作成功！");
		}
		return  ResponseEntity.op("0", "操作失败！");
	}

	/**
	 * 添加账号
	 * @param au
	 * @return
     */
	@RequestMapping("/addAccount")
	@ResponseBody
	public ResponseEntity addAccount(@RequestParam("token")String token,AccountUser au) {
		// TODO Auto-generated method stub
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		int count = 0;
		int isOnly = 0;

		String description="";
		///保证用户名唯一
		List<AccountUser> alist = auService.getPermissionAndName(au.getUsername(),null,null);
		if (alist!=null&&alist.size()>=1) {
			if (au.getId()!=alist.get(0).getId()){
				return  ResponseEntity.op("0", "账号冲突！");
			}
		}
		if (au.getId() == 0) {
			// 新增
			description = "权限管理-新增用户";
			count = auService.addAccount(au);
		} else {
			// 修改
			if (au.getId()==0){
				return  ResponseEntity.op("0", "账号异常！");
			}
				////判断是总监助理
			if(au.getRoleId()==22){
				return  ResponseEntity.op("0", "总监助理身份不可修改，只能删除重新添加！");
			}
			description = "权限管理-修改用户";
			count = auService.upAccount(au);
		}

		if (count == 1) {
			auService.addLog(uId.toString(),description);
			return  ResponseEntity.op("1", "操作成功！");
		}
		return  ResponseEntity.op("0", "操作失败！");
	}

	@RequestMapping("/delAccount")
	@ResponseBody
	public ResponseEntity delAccount(@RequestParam("token")String token,String strIds) {
		// TODO Auto-generated method stub
		int count = 0;
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		List<Long> ids = Utils.stringToLongList(strIds, ",");
		count = auService.deleteAccounts(ids);
		if (count >= 1) {
			auService.addLog(uId.toString(),"权限管理-删除用户");
			return  ResponseEntity.op("0", "操作成功！");
		}
		return  ResponseEntity.op("0", "操作失败！");
	}

	@RequestMapping("/delRoles")
	@ResponseBody
	public ResponseEntity delRoles(@RequestParam("token")String token,String strIds) {
		// TODO Auto-generated method stub
		int count = 0;
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		List<Long> ids = Utils.stringToLongList(strIds, ",");
		List<AccountUser>  aalist = new ArrayList<AccountUser>();
		///删除前要判断此权限是否有关联用户，若关联，要先删除
		for(long id :ids){
		    aalist = auService.getPermissionAndName(null,null,uId.toString());
			if (aalist!=null&&aalist.size()>0){
				return ResponseEntity.op("0", "该权限已分配账号，请删除账号在进行操作！");
			}
		}
		count = auService.delRoles(ids);
		if (count >= 1) {
			auService.addLog(uId.toString(),"权限管理-删除角色");
			return ResponseEntity.op("1",  "操作成功！");
		}
		return ResponseEntity.op("0",  "操作失败！");
	}

	@RequestMapping("/addRoles")
	@ResponseBody
	public ResponseEntity addRoles(@RequestParam("token")String token,AccountUser au,String strIds) {
		// TODO Auto-generated method stub
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		if (strIds==null||strIds.length()==0){
			return new ResponseEntity("false", "请勾选权限菜单！");
		}
		int count = 0;
		List<Long> ids = Utils.stringToLongList(strIds, ",");
		////判断权限账号是否唯一冲突
		List<AccountUser> rlist = auService.getRoles(au.getRoleSign());
		if (rlist!=null&&rlist.size()>=1) {
			if (au.getId()==0){
				return  ResponseEntity.op("0",  "权限名称冲突！");
			}else if (au.getId()!=rlist.get(0).getId()){
				return  ResponseEntity.op("0",  "权限名称冲突！");
			}
		}
		String description="权限管理-新增角色";
		//0===新增，否则修改
		if (au.getId()==0) {
			count = auService.addMyRoles(au,ids);
		}else{
			///修改，（修改角色（角色修改，权限先删除，后添加））
			description="权限管理-修改角色";
			count = auService.updateMyRoles(au,ids);
		}
		if (count >= 1) {
			auService.addLog(uId.toString(),description);
			return  ResponseEntity.op("1",  "操作成功！");
		}
		return  ResponseEntity.op("0",  "操作失败！");
	}

	/**
	 * 用户禁用
	 * @param token
	 * @param strIds
	 * @param status
     * @return
     */
	@RequestMapping("/upAccountZt")
	@ResponseBody
	public ResponseEntity upAccountZt(@RequestParam("token")String token,String strIds,
			@RequestParam("status") String status) {
		// TODO Auto-generated method stub
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		int count = 0;
		List<Long> ids = Utils.stringToLongList(strIds, ",");
		count = auService.upAccountStatus(ids, status);
		if (count >= 1) {
			auService.addLog(uId.toString(),"禁用/解禁用户");
			return ResponseEntity.op("1", "操作成功！");
		}
		return ResponseEntity.op("0", "操作失败！");
	}

	// 用户列表
	@RequestMapping("/accountsCx")
	@ResponseBody
	public ResponseEntity accountsCx(
			@RequestParam("token")String token,@RequestParam("username") String username,@RequestParam("page")int page,
			@RequestParam("limit")int limit) {
		List<AccountUser> list = new ArrayList<AccountUser>();
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		PageHelper.startPage(page, limit);// 分页语句
		list = auService.getAuByUsername(username,null);
		PageInfo<AccountUser> pageInfo = new PageInfo<AccountUser>(list);
		auService.addLog(uId.toString(),"权限管理-用户列表");
		return ResponseEntity.se(new Page<AccountUser>(pageInfo));
	}

	@RequestMapping("/accountsSx")
	@ResponseBody
	public ResponseEntity accountsSx(@RequestParam("token")String token,@RequestParam("page")int page,
									 @RequestParam("limit")int limit) {
		List<AccountUser> list = new ArrayList<AccountUser>();
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		PageHelper.startPage(page, limit);// 分页语句
		list = auService.getAuByUsername("",null);
		PageInfo<AccountUser> pageInfo = new PageInfo<AccountUser>(list);
		auService.addLog(uId.toString(),"权限管理-用户列表");
		return ResponseEntity.se(new Page<AccountUser>(pageInfo));
	}

	/**
	 * 获取健康管理师
	 * @param token
	 * @param page
	 * @param limit
     * @return
     */
	@RequestMapping("/getHealthDealer")
	@ResponseBody
	public ResponseEntity getHealthDealer(@RequestParam("token")String token,@RequestParam("page")int page,
									 @RequestParam("limit")int limit) {
		List<AccountUser> list = new ArrayList<AccountUser>();
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		PageHelper.startPage(page, limit);// 分页语句
		list = auService.getZlRole(null,"3");
		PageInfo<AccountUser> pageInfo = new PageInfo<AccountUser>(list);
		auService.addLog(uId.toString(),"权限管理-用户列表");
		return ResponseEntity.se(new Page<AccountUser>(pageInfo));
	}


	@RequestMapping("/roleList")
	@ResponseBody
	public ResponseEntity roleList(@RequestParam("token")String token,@RequestParam("page")int page,
								   @RequestParam("limit")int limit) {
		List<AccountUser> list = new ArrayList<AccountUser>();
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		PageHelper.startPage(page, limit);// 分页语句
		list = auService.getRoles(null);
		PageInfo<AccountUser> pageInfo = new PageInfo<AccountUser>(list);
		auService.addLog(uId.toString(),"权限管理-角色列表");
		return ResponseEntity.se(new Page<AccountUser>(pageInfo));
	}
	@RequestMapping("/accountlogList")
	@ResponseBody
	public ResponseEntity accountlogList(@RequestParam("token")String token,@RequestParam("page")int page,
										   @RequestParam("limit")int limit) {
		List<AccountLog> list = new ArrayList<AccountLog>();
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		PageHelper.startPage(page, limit);// 分页语句
		list = auService.getAccountLog(null);
		PageInfo<AccountLog> pageInfo = new PageInfo<AccountLog>(list);
		auService.addLog(uId.toString(),"日志管理-日志查询");
		return ResponseEntity.se(new Page<AccountLog>(pageInfo));
	}


	/**
	 *
	 * 权限菜单列表=
	 * @return
     */
	@RequestMapping("/getMenuList")
	@ResponseBody
	public ResponseEntity getMenuList(@RequestParam("token")String token) {
		List <MennuBean> menuList =  new ArrayList<MennuBean>();
		List <ChildBean> childList =  new ArrayList<ChildBean>();
		Map<String ,Object> m = new HashMap<String,Object>();
		m.put("fMenu","1");
		MennuBean mb = new MennuBean();
		int count =0;//是否只有一组菜单列表
		ChildBean chlilBean = new ChildBean();
		//1=验证登录
		Long uId = 0l;
		if (token != null && !(token.equals(""))) {
			//判断登录
			List<MobileLogin> loginList = webLoginService.findUserByToken(token);
			if (loginList != null && loginList.size() == 1) {
				MobileLogin mobileLogin = loginList.get(0);
				uId = mobileLogin.getuId();

			} else {
				return ResponseEntity.op("0", "token失效！");
			}
		} else {
			return ResponseEntity.op("0", "token失效！");
		}
		/////循环权限列表，进行格式规划
		List <AccountUser> permissionList = auService.getPermissionLsitBy(null,uId.toString());
		for(AccountUser au : permissionList){
			if (au.getCode().length()==2){
				count ++;
				mb = new MennuBean();
				mb.setHref(au.getHref());
				mb.setIcon(au.getIcon());
				mb.setTitle(au.getTitle());
				mb.setSpread(false);
				if (!m.get("fMenu").equals("1")){
					MennuBean mbF =(MennuBean)m.get("fMenu");
					mbF.setChildren(childList);
					menuList.add(mbF);
					childList = new ArrayList<ChildBean>();
				}
				m.put("fMenu",mb);
			}else if (au.getCode().length()==4){
				chlilBean = new ChildBean();
				chlilBean.setHref(au.getHref());
				chlilBean.setIcon(au.getIcon());
				chlilBean.setTitle(au.getTitle());
				chlilBean.setSpread(false);
				childList.add(chlilBean);
			}
		}
		////不止一组列表，最后一组不会自动加上
		MennuBean mbF =(MennuBean)m.get("fMenu");
		mbF.setChildren(childList);
		menuList.add(mbF);
		return ResponseEntity.se(menuList);

	}


	/**
	 *
	 * 菜单树
	 * @return
	 */
	@RequestMapping("/getMenuTree")
	@ResponseBody
	public TreeBean getMenuTree(@RequestParam("token")String token) {
		///顶点节点
		TreeBean mennuTree = new TreeBean();
		Map<String,Boolean> state = new HashMap<String,Boolean>();
		state.put("opened",true);
		mennuTree.setState(state);
		mennuTree.setChecked(false);
		mennuTree.setId("-1");
		mennuTree.setText("顶级节点");
		mennuTree.setHasChildren(true);
		mennuTree.setHasParent(false);
		//////顶点节点下面的子节点/////
		List <TreeBean> menuList =  new ArrayList<TreeBean>();
		List <TreeBean> childList =  new ArrayList<TreeBean>();
		Map<String ,Object> m = new HashMap<String,Object>();
		m.put("fMenu","1");
		TreeBean tb = new TreeBean();
		int count =0;//是否只有一组菜单列表
		TreeBean chlilBean = new TreeBean();
		/////循环权限菜单，进行格式规划
		List <AccountUser> treeList = auService.getMennuTree(null);
		for(AccountUser au : treeList){
			state.put("selected",false);
			if (au.getCode().length()==2){
				count ++;
				tb = new TreeBean();
				tb.setState(state);
				tb.setChecked(false);
				tb.setId(au.getId()+"");
				tb.setText(au.getTitle());
				tb.setHasChildren(true);
				tb.setHasParent(true);
				tb.setParentId("0");
				if (!m.get("fMenu").equals("1")){
					TreeBean tbF =(TreeBean)m.get("fMenu");
					tbF.setChildren(childList);
					menuList.add(tbF);
					childList = new ArrayList<TreeBean>();
				}
				m.put("fMenu",tb);
			}else if (au.getCode().length()==4){
				TreeBean cbF =(TreeBean)m.get("fMenu");
				chlilBean = new TreeBean();
				chlilBean.setState(state);
				chlilBean.setChecked(false);
				chlilBean.setId(au.getId()+"");
				chlilBean.setText(au.getTitle());
				chlilBean.setHasChildren(false);
				chlilBean.setHasParent(true);
				chlilBean.setParentId(cbF.getId()+"");
				childList.add(chlilBean);
			}
		}
		////不止一组列表，最后一组不会自动加上
		TreeBean tbF =(TreeBean)m.get("fMenu");
		tbF.setChildren(childList);
		menuList.add(tbF);
		mennuTree.setChildren(menuList);
		return mennuTree;

	}

	/**
	 *
	 * 菜单树
	 * @return
	 */
	@RequestMapping("/getEditMenuTree")
	@ResponseBody
	public TreeBean getEditMenuTree(@RequestParam("token")String token,@RequestParam("roleId") String roleId) {
		///顶点节点
		TreeBean mennuTree = new TreeBean();
		Map<String,Boolean> state = new HashMap<String,Boolean>();
		state.put("opened",true);
		state.put("selected",false);
		mennuTree.setState(state);
		mennuTree.setChecked(false);
		mennuTree.setId("-1");
		mennuTree.setText("顶级节点");
		mennuTree.setHasChildren(true);
		mennuTree.setHasParent(false);
		//////顶点节点下面的子节点/////
		List <TreeBean> menuList =  new ArrayList<TreeBean>();
		List <TreeBean> childList =  new ArrayList<TreeBean>();
		Map<String ,Object> m = new HashMap<String,Object>();
		m.put("fMenu","1");
		TreeBean tb = new TreeBean();
		int count =0;//是否只有一组菜单列表
		TreeBean chlilBean = new TreeBean();
		/////循环权限菜单，进行格式规划
		List <AccountUser> treeList = auService.getMennuTree(null);
		/////用户已有菜单
		List <AccountUser> myMenuList = auService.getRolePermission(roleId);
		for(AccountUser au : treeList){
			////循环判断用户菜单数据
			boolean checkFlag =false;
			state = new HashMap<String,Boolean>();
			state.put("opened",true);
			for(AccountUser aus : myMenuList){
				if((au.getId()+"").equals(aus.getPermissionId())){
					checkFlag = true;
				}
			}
			state.put("selected",checkFlag);
			if (au.getCode().length()==2){
				count ++;
				tb = new TreeBean();
				state.put("selected",false);
				tb.setState(state);
				tb.setChecked(false);
				tb.setId(au.getId()+"");
				tb.setText(au.getTitle());
				tb.setHasChildren(true);
				tb.setHasParent(true);
				tb.setParentId("0");
				if (!m.get("fMenu").equals("1")){
					TreeBean tbF =(TreeBean)m.get("fMenu");
					tbF.setChildren(childList);
					menuList.add(tbF);
					childList = new ArrayList<TreeBean>();
				}
				m.put("fMenu",tb);
			}else if (au.getCode().length()==4){
				TreeBean cbF =(TreeBean)m.get("fMenu");
				chlilBean = new TreeBean();
				chlilBean.setState(state);
				chlilBean.setChecked(checkFlag);
				chlilBean.setId(au.getId()+"");
				chlilBean.setText(au.getTitle());
				chlilBean.setHasChildren(false);
				chlilBean.setHasParent(true);
				chlilBean.setParentId(cbF.getId()+"");
				childList.add(chlilBean);
			}
		}
		////不止一组列表，最后一组不会自动加上
		TreeBean tbF =(TreeBean)m.get("fMenu");
		tbF.setChildren(childList);
		menuList.add(tbF);
		mennuTree.setChildren(menuList);
		return mennuTree;

	}








}
