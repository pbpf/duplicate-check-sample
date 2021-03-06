package Sample1;

import cn.papercheck.engine.CheckManager;
import cn.papercheck.engine.checker.CheckTask;
import cn.papercheck.engine.algorithm.ClauseCheck;
import cn.papercheck.engine.algorithm.ContinuityCheck;
import cn.papercheck.engine.pojo.Paper;
import cn.papercheck.engine.pojo.LocalPaperLibrary;
import cn.papercheck.licence.Auth;

import java.io.File;
import java.io.IOException;

/**
 * SDK入门使用范例
 * 本实例主要演示：使用本地库的情况下，SDK最基本朴素的使用方式，让您可以以最简单的方式看到一个直观的效果
 */
public class Main1 {

    public static void main(String[] args) throws IOException, InterruptedException {
        //获取机器码
        System.out.println(Auth.getMachineCode());
        //设置注册码（免费获取：https://dreamspark.com.cn/blog/?id=7）
        CheckManager.INSTANCE.setRegCode("muQyymFW0ysAZZhKVOzkh/jbuGMMfBg9IihiT2Fq9xEZxfIA=");
        //检查注册状态
        System.out.println(CheckManager.INSTANCE.regState());

        //加载本地比对库（支持pdf、txt、doc、docx）
        LocalPaperLibrary paperLibrary = new LocalPaperLibrary("C:\\Users\\admin\\Desktop\\Library");//初始化对比库对象。路径为比对库所在文件夹
        paperLibrary.build(); //构建比对库

        //读取待查重的文件（支持pdf、txt、doc、docx）
        Paper toCheckPaper = new Paper(new File("C:\\Users\\admin\\Desktop\\test.docx")); //读取本地文件

        //构建并启动任务
        CheckTask checkTask = CheckManager.INSTANCE
                .getCheckTaskBuilder() //获取查重任务构造器
                .setLibrary(paperLibrary) //设置比对库
                .setToCheckPaper(toCheckPaper) //设置待查Paper
                .build(); //构建任务，返回checkTask对象
        checkTask.start(); //启动任务
        checkTask.join(); //等待查重结束

        //保存查重报告
        checkTask.getReporter().saveAsFile("C:\\Users\\admin\\Desktop\\report1.mht", 1); //保存查重报告（全文标红）
        checkTask.getReporter().saveAsFile("C:\\Users\\admin\\Desktop\\report2.mht", 2); //保存查重报告（原文对照）
    }

}
