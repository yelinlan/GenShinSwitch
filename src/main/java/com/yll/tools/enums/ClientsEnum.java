package com.yll.tools.enums;

import java.util.Arrays;
import java.util.Optional;

/**
 *@项目名称: GenShinSwitch
 *@类名称: ClientsEnum
 *@类描述:
 *@创建人: quanyixiang
 *@创建时间: 2022/4/29 3:37
 **/
public enum ClientsEnum {
	BILIBILI("bilibili"), MIHOYO("mihoyo"),
	;


	private String source;

	ClientsEnum(String source) {
		this.source = source;
	}

	public static String getOposite(String source) {
		Optional<ClientsEnum> anEnum = Arrays.stream(ClientsEnum.values()).filter(p -> !p.name().equals(source))
				.findFirst();
		return anEnum.get().name();
	}
}