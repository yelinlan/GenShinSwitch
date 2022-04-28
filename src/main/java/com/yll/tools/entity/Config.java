package com.yll.tools.entity;

import lombok.Builder;
import lombok.Data;

/**
 *@项目名称: GenShinSwitch
 *@类名称: Config
 *@类描述:
 *@创建人: quanyixiang
 *@创建时间: 2022/4/29 2:49
 **/
@Data
public class Config {
	private String base = "C:\\Program Files\\Genshin Impact";
	private String inner = "\\Genshin Impact Game\\config.ini";
	private String outer = "\\config.ini";
	private String dll = "\\Genshin Impact Game\\YuanShen_Data\\Plugins\\PCGameSDK.dll";
	private String status;

	public Config() {
	}

	public Config(String base) {
		this.base = base;
		this.inner = base + this.inner;
		this.outer = base + this.outer;
		this.dll = base + this.dll;
	}
}