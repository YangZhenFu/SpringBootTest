package com.stylefeng.guns.core.node;

import java.io.Serializable;
import java.util.List;

import com.stylefeng.guns.core.node.tree.BaseTreeObj;

/**
 * 
 * jquery ztree 插件的节点
 * 
 * @author fengshuonan
 * @date 2017年2月17日 下午8:25:14
 */
public class ZTreeNode extends BaseTreeObj<ZTreeNode, Serializable> {

	/** serialVersionUID*/
	private static final long serialVersionUID = 6162072782711456825L;

	private Long id;	//节点id
	
	private Long pId;//父节点id
	
	private String name;//节点名称
	
	private Boolean open;//是否打开节点
	
	private Boolean checked;//是否被选中
	
	private String icon;//图标
	
	private List<ZTreeNode> childList;//子节点
	
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon=icon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		super.setId(id);
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
		super.setParentId(pId);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getIsOpen() {
		return open;
	}

	public void setIsOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public static ZTreeNode createParent(){
		ZTreeNode zTreeNode = new ZTreeNode();
		zTreeNode.setChecked(true);
		zTreeNode.setId(0L);
		zTreeNode.setName("顶级");
		zTreeNode.setOpen(true);
		zTreeNode.setpId(0L);
		return zTreeNode;
	}

	/* (non-Javadoc)  
	 * <p>Title: toString</p>  
	 * <p>Description: </p>  
	 * @return  
	 * @see java.lang.Object#toString()  
	 */
	@Override
	public String toString() {
		return "ZTreeNode [id=" + id + ", pId=" + pId + ", name=" + name + ", open=" + open + ", checked=" + checked
				+ ", icon=" + icon + "]";
	}

	/**
	 * @return the childList  
	 */
	public List<ZTreeNode> getChildList() {
		return childList;
	}

	/**
	 * @param childList the childList to set
	 */
	public void setChildList(List<ZTreeNode> childList) {
		this.childList = childList;
		super.setChildsList(childList);
	}

	
}
