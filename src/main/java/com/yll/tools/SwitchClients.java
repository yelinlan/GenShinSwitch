package com.yll.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;

import com.yll.tools.entity.Config;
import com.yll.tools.enums.ClientsEnum;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardCopyOption;
import java.util.*;

/**
 *@项目名称: GenShinSwitch
 *@类名称: SwitchClients
 *@类描述:
 *@创建人: quanyixiang
 *@创建时间: 2022/4/29 2:34
 **/
public class SwitchClients {
	private String base = "C:\\Program Files\\Genshin Impact";
	private String suffix = "_copy";

	public void doSwitch() {
		//		base = getBasePath();
		Config config = new Config(base);
		config.setStatus(getStstus(config.getInner()));
		System.out.printf("当前所在服务器为：%s\n", config.getStatus());
		config.setStatus(ClientsEnum.getOposite(config.getStatus()));
		switchServer(config);
	}

	private void switchServer(Config config) {
		System.out.printf("即将切换到服务器：%s\n", config.getStatus());
		if (ClientsEnum.BILIBILI.name().equals(config.getStatus())) {
			copyBiliBiliConfigs(config);
			copyOrRenameDll(config);
		} else if (ClientsEnum.MIHOYO.name().equals(config.getStatus())) {
			copyMihoyoConfigs(config);
			renameDll(config);
		}
		System.out.println("服务器切换成功！");
		System.out.printf("当前服务器为：%s\n", config.getStatus());
	}

	private void copyOrRenameDll(Config config) {
		String back = config.getDll() + suffix;
		if (FileUtil.exist(back)) {
			File file = new File(back);
			FileUtil.rename(file, file.getName().replace(suffix, ""), true);
		} else {
			FileUtil.copyFile("src/main/java/com/yll/tools/files/bilibili/PCGameSDK.dll", config.getDll(),
					StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private void renameDll(Config config) {
		if (FileUtil.exist(config.getDll())) {
			File file = new File(config.getDll());
			FileUtil.rename(file, file.getName() + suffix, true);
		}
	}

	private void copyMihoyoConfigs(Config config) {
		FileUtil.copyFile("src/main/java/com/yll/tools/files/offical/inner/config.ini", config.getInner(),
				StandardCopyOption.REPLACE_EXISTING);
		FileUtil.copyFile("src/main/java/com/yll/tools/files/offical/outer/config.ini", config.getOuter(),
				StandardCopyOption.REPLACE_EXISTING);
	}

	private void copyBiliBiliConfigs(Config config) {
		FileUtil.copyFile("src/main/java/com/yll/tools/files/bilibili/inner/config.ini", config.getInner(),
				StandardCopyOption.REPLACE_EXISTING);
		FileUtil.copyFile("src/main/java/com/yll/tools/files/bilibili/outer/config.ini", config.getOuter(),
				StandardCopyOption.REPLACE_EXISTING);
	}

	private String getStstus(String configPath) {
		List<String> lines = FileUtil.readLines(configPath, StandardCharsets.UTF_8);
		return Optional.of(lines).orElse(Collections.emptyList()).stream().filter(StrUtil::isNotBlank)
				.anyMatch(p -> p.contains("pcadbdpz")) ? ClientsEnum.MIHOYO.name() : ClientsEnum.BILIBILI.name();
	}

	private String getBasePath() {
		while (true) {
			System.out.printf("请输入原神安装目录（像这样）%s：\n", base);
			Scanner sc = new Scanner(System.in);
			String filePath = sc.next();
			File file = new File(Optional.of(filePath).orElse(""));
			if (!file.exists() && !filePath.contains("Genshin Impact")) {
				System.out.println("输入的路径无效！");
			} else {
				return file.getAbsolutePath();
			}
		}
	}
}